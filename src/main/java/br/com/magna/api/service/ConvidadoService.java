package br.com.magna.api.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.entity.ConvidadoEntity;
import br.com.magna.api.repository.ConvidadoRepository;

@Service
public class ConvidadoService {

	private static Logger logger = LoggerFactory.getLogger(ConvidadoService.class);
	
	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private ModelMapper modelMapper;

//	// Listando todos os eventos com Page(pagina e quantidade)
//	public Page<ConvidadoDto> listPage(String cpf, Pageable paginacao) {
//		if (cpf == null) {
//			Page<ConvidadoEntity> convidados = convidadoRepository.findAll(paginacao);
//			return pageDto(convidados);
//		} else {
//			Page<ConvidadoEntity> convidados = convidadoRepository.findByCpf(cpf, paginacao);
//			return pageDto(convidados);
//		}
//	}

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<ConvidadoDto> listPage(Pageable paginacao) throws Exception{
		try {
			Page<ConvidadoEntity> convidados = convidadoRepository.findAll(paginacao);
			logger.info("Convidados listados");
			return pageDto(convidados);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}	
	}

	// Listando usuario por CPF
	public ConvidadoDto getCpf(String cpf) throws NotFoundException, Exception {
		try {
			Optional<ConvidadoEntity> convidadoOptional = convidadoRepository.findByCpf(cpf);
			ConvidadoEntity convidado = convidadoOptional.orElseThrow(() -> new NotFoundException());
			ConvidadoDto convidadoDto = convertDto(convidado);
			logger.info("Convidado com CPF: " + "'" + cpf + "'" + " listado");
			return convidadoDto;
		} catch(NotFoundException ex) {
			logger.error(ex.getMessage());
		} catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	// Verificando se o convidado já tem um cadastro
	public Boolean verificaConvidado(ConvidadoDto cpfConvidado) throws NotFoundException, Exception {
		Boolean verificaConvidado = false;
		try {
			verificaConvidado = convidadoRepository.existsByCpf(cpfConvidado.getCpf());
			return verificaConvidado;
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			return verificaConvidado;
		}
		
	}

	// Criando convidado
	public ConvidadoDto createConvidadoDto(ConvidadoDto convidadoDto) throws IllegalArgumentException,
	NotFoundException {
		try {
			if (verificaConvidado(convidadoDto)) {
				logger.info("Convidado já cadastrado");
				return null;
			} else {
				if (validCpf(convidadoDto.getCpf())) {
					ConvidadoEntity convidado = convidadoRepository.save(convertEntity(convidadoDto));
					ConvidadoDto convidadoDtoSave = convertDto(convidado);
					logger.info("Convidado cadastrado com sucesso");
					return convidadoDtoSave;
				} else {
					logger.error("CPF inválido");
				}
			}
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		} catch(NotFoundException ex) {
			logger.error(ex.getMessage());
		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
		
	}

	// Atualizando convidado
	public ConvidadoDto update(String cpf, ConvidadoDto convidadoDto) throws NotFoundException,
	EntityNotFoundException ,Exception {
		try {
			ConvidadoEntity convidado = convidadoRepository.findByCpf(cpf).orElseThrow(() -> new NotFoundException());
			ConvidadoDto convidadoDtoAntigo = convertDto(convidado);
			BeanUtils.copyProperties(convidadoDto, convidadoDtoAntigo, "cpf");
			ConvidadoEntity convertEntity = convertEntity(convidadoDto);
			convertEntity.setId(convidado.getId());
			ConvidadoEntity convidadoAtualizado = convidadoRepository.save(convertEntity);
			logger.info("Convidado com CPF: " + "'" + convidadoDto.getCpf() + "'" + " atualizado");
			return convertDto(convidadoAtualizado);
		} catch(NotFoundException ex) {
			logger.error("NotFound");
		} catch(EntityNotFoundException ex) {
			logger.error("Evento não encontrado");
		} catch(Exception ex) {
			logger.error("Evento não encontrado");
		}	
		return null;	
	}

	// Deletando um convidado
	public void delete(String cpf) throws NotFoundException, Exception {
		try {
			convidadoRepository.deleteByCpf(cpf);
			logger.info("Convidado com CPF: " + cpf+ " deletado");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	// Valindo se o CPF existe
	public boolean validCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception ex) {
			ex.getMessage();
			return false;
		}
	}

	// CONVERSORES//

	// Construtor do ConvidadoDto
	public ConvidadoDto convidadoDto(ConvidadoEntity convidado) {
		ConvidadoDto dto = new ConvidadoDto();
		dto.setCpf(convidado.getCpf());
		dto.setNome(convidado.getNome());
		dto.setEvento(convidado.getEvento());
		return dto;
	}

	// Conversor ModelMapper de Entity para Dto
	public ConvidadoDto convertDto(ConvidadoEntity convidado) {
		return modelMapper.map(convidado, ConvidadoDto.class);
	}

	// Conversor ModelMapper de Entity para Dto
	public ConvidadoEntity convertEntity(ConvidadoDto convidado) {
		return modelMapper.map(convidado, ConvidadoEntity.class);
	}

	// Conversor Page de Entity para Dto
	public Page<ConvidadoDto> pageDto(Page<ConvidadoEntity> convidadoEntity) {
		return convidadoEntity.map(convert -> this.convidadoDto(convert));
	}

}

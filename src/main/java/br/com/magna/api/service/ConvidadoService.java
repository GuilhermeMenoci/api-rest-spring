package br.com.magna.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.entity.ConvidadoEntity;
import br.com.magna.api.repository.ConvidadoRepository;

@Service
public class ConvidadoService {

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<ConvidadoDto> listPage(String cpf, Pageable paginacao) {
		if (cpf == null) {
			Page<ConvidadoEntity> convidados = convidadoRepository.findAll(paginacao);
			return pageDto(convidados);
		} else {
			Page<ConvidadoEntity> convidados = convidadoRepository.findByCpf(cpf, paginacao);
			return pageDto(convidados);
		}
	}

	// Listando usuario por CPF
	public ConvidadoDto getCpf(String cpf) throws NotFoundException {
		ConvidadoEntity convidado = convidadoRepository.findByCpf(cpf);
		ConvidadoDto convidadoDto = convertDto(convidado);

//		if (convidadoDto == null) {
//			throw new NotFoundException();
//		}
		return convidadoDto;
	}

	// Verificando se o convidado já tem um cadastro
	public Boolean verificaConvidado(ConvidadoDto cpfConvidado) throws NotFoundException {
		Boolean verificaConvidado = convidadoRepository.existsByCpf(cpfConvidado.getCpf());
		return verificaConvidado;
	}

	// Criando convidado
	public ConvidadoDto createConvidadoDto(ConvidadoDto convidadoDto) throws NotFoundException {
		if (verificaConvidado(convidadoDto))
			System.out.println("Convidado já cadastrado");
		else {
			ConvidadoEntity convidado = convidadoRepository.save(convertEntity(convidadoDto));
			ConvidadoDto convidadoDtoSave = convertDto(convidado);
			return convidadoDtoSave;
		}
		return null;
	}

	// Atualizando convidado
	public ConvidadoDto update(String cpf, ConvidadoDto convidadoDto) throws NotFoundException {
		ConvidadoEntity convidado = convidadoRepository.findByCpf(cpf);
		ConvidadoDto convidadoDtoAntigo = convertDto(convidado);
		BeanUtils.copyProperties(convidadoDto, convidadoDtoAntigo, "cpf");
		ConvidadoEntity convertEntity = convertEntity(convidadoDto);
		convertEntity.setId(convidado.getId());
		ConvidadoEntity convidadoAtualizado = convidadoRepository.save(convertEntity);
		return convertDto(convidadoAtualizado);
	}

	// Deletando um convidado
	public void delete(String cpf) throws NotFoundException {
		convidadoRepository.deleteByCpf(cpf);
	}

	// CONVERSORES//

	// Conversor ModelMapper de Entity para Dto
	public ConvidadoDto convertDto(ConvidadoEntity convidado) {
		return modelMapper.map(convidado, ConvidadoDto.class);
	}

	// Conversor ModelMapper de Entity para Dto
	public ConvidadoEntity convertEntity(ConvidadoDto convidado) {
		return modelMapper.map(convidado, ConvidadoEntity.class);
	}

	// Convertando a lista de Entity para Dto
	public List<ConvidadoDto> listDto(List<ConvidadoEntity> convidado) {
		List<ConvidadoDto> convidados = convidado.stream().map(ConvidadoDto::new).collect(Collectors.toList());
		return convidados;
	}

	// Convertando a Page de Entity para Dto
	public Page<ConvidadoDto> pageDto(Page<ConvidadoEntity> convidado) {
		return convidado.map(ConvidadoDto::new);
	}

}

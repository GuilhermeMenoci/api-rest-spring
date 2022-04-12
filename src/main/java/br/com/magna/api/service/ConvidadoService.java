package br.com.magna.api.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.entity.ConvidadoEntity;
import br.com.magna.api.exception.NaoEncontradoException;
import br.com.magna.api.exception.ParametroInvalidoException;
import br.com.magna.api.repository.ConvidadoRepository;

@Service
public class ConvidadoService {

	private static Logger logger = LoggerFactory.getLogger(ConvidadoService.class);

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<ConvidadoDto> listPage(Pageable paginacao) {

		Page<ConvidadoEntity> convidados = convidadoRepository.findAll(paginacao);
		return pageDto(convidados);

	}

	public ConvidadoDto getCpf(String cpf) {

		ConvidadoEntity convidado = convidadoRepository.findByCpf(cpf)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum convidado encontrad com CPF: " + cpf));
		ConvidadoDto convidadoDto = convertDto(convidado);
		logger.info("Convidado listado com CPF: {}", cpf);
		return convidadoDto;

	}

	public Boolean verificaConvidado(ConvidadoDto cpfConvidado) {
		Boolean verificaConvidado = false;

		if (Boolean.TRUE.equals(convidadoRepository.existsByCpf(cpfConvidado.getCpf()))) {
			throw new ParametroInvalidoException("Já existe um cadastro para esse CPF!");
		}

		return verificaConvidado;

	}

	public ConvidadoDto createConvidadoDto(ConvidadoDto convidadoDto) {

		verificaConvidado(convidadoDto);
		validCpf(convidadoDto.getCpf());

		ConvidadoEntity convidado = convidadoRepository.save(convertEntity(convidadoDto));
		ConvidadoDto convidadoDtoSave = convertDto(convidado);
		logger.info("Convidado cadastrado com sucesso");
		return convidadoDtoSave;

	}

	public ConvidadoDto update(String cpf, ConvidadoDto convidadoDto) {

		ConvidadoEntity convidado = convidadoRepository.findByCpf(cpf)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum convidado encontrado com CPF: " + cpf));
		ConvidadoDto convidadoDtoAntigo = convertDto(convidado);
		BeanUtils.copyProperties(convidadoDto, convidadoDtoAntigo, "cpf");
		ConvidadoEntity convertEntity = convertEntity(convidadoDto);
		convertEntity.setId(convidado.getId());
		ConvidadoEntity convidadoAtualizado = convidadoRepository.save(convertEntity);
		logger.info("Convidado atualizado com CPF: {}", cpf);
		return convertDto(convidadoAtualizado);

	}

	public void delete(String cpf) {

		convidadoRepository.deleteByCpf(cpf);
		logger.info("Convidado deletado com CPF: {}", cpf);

	}

	public boolean validCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();

		cpfValidator.assertValid(cpf);
		return true;
	}

	public ConvidadoDto convidadoDto(ConvidadoEntity convidado) {
		ConvidadoDto dto = new ConvidadoDto();
		dto.setCpf(convidado.getCpf());
		dto.setNome(convidado.getNome());
		dto.setEvento(convidado.getEvento());
		return dto;
	}

	public ConvidadoDto convertDto(ConvidadoEntity convidado) {
		return modelMapper.map(convidado, ConvidadoDto.class);
	}

	public ConvidadoEntity convertEntity(ConvidadoDto convidado) {
		return modelMapper.map(convidado, ConvidadoEntity.class);
	}

	public Page<ConvidadoDto> pageDto(Page<ConvidadoEntity> convidadoEntity) {
		return convidadoEntity.map(this::convidadoDto);
	}

}

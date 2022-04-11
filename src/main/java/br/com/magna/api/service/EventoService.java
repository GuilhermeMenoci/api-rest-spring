package br.com.magna.api.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.dto.EventoDto;
import br.com.magna.api.entity.EventoEntity;
import br.com.magna.api.exception.DadosJaCadastradosException;
import br.com.magna.api.exception.NaoEncontradoException;
import br.com.magna.api.exception.ParametroInvalidoException;
import br.com.magna.api.repository.EventoRepository;

@Service
public class EventoService {

	private static Logger logger = LoggerFactory.getLogger(EventoService.class);

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<EventoDto> listPage(Pageable paginacao) {

		Page<EventoEntity> eventos = eventoRepository.findAll(paginacao);
		logger.info("Eventos listados");
		return eventos.map(map -> modelMapper.map(map, EventoDto.class));

	}

	// Listando evento por codigo
	public EventoDto getCodigo(int codigo) {

		EventoEntity eventoOptional = eventoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum evento encontrado com codigo: " + codigo));

		return convertDto(eventoOptional);

	}

	// Verificando se o evento já tem um cadastro
	public Boolean verificaEvento(EventoDto codigoEvento) {
		Boolean verificaEvento = false;

		if (eventoRepository.existsByCodigo(codigoEvento.getCodigo())) {
			logger.error("Evento já cadastrado com esse codigo: {}", codigoEvento.getCodigo());
			throw new DadosJaCadastradosException("Evento já cadastrado com esse codigo:" + codigoEvento.getCodigo());
		}

		return verificaEvento;

	}

	// Criando um evento
	public EventoDto createEventoDto(EventoDto eventoDto) {

		verificaEvento(eventoDto);

		EventoEntity evento = eventoRepository.save(convertEntity(eventoDto));
		EventoDto eventoDtoSave = convertDto(evento);
		logger.info("Evento cadastrado com sucesso");
		return eventoDtoSave;

	}

	// Atualizando evento
	public EventoDto update(int codigo, EventoDto eventoDto) {

		EventoEntity evento = eventoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum evento encontrado com codigo: " + codigo));
		EventoDto eventoDtoAntigo = convertDto(evento);
		BeanUtils.copyProperties(eventoDto, eventoDtoAntigo, "codigo");
		EventoEntity convertEntity = convertEntity(eventoDto);
		convertEntity.setId(evento.getId());
		EventoEntity eventoAtualizado = eventoRepository.save(convertEntity);
		logger.info("Evento com atualizado com código: {}", eventoDto.getCodigo());
		return convertDto(eventoAtualizado);

	}

	// Deletando um evento
	public void delete(int codigo) {

		eventoRepository.deleteByCodigo(codigo);
		logger.info("Evento deletado com código: {}", codigo);

	}

	// Construtor do EventoDto
	public EventoDto eventoDto(EventoEntity evento) {
		EventoDto dto = new EventoDto();
		dto.setCodigo(evento.getCodigo());
		dto.setNomeEvento(evento.getNomeEvento());
		dto.setCidade(evento.getCidade());
		return dto;
	}

	// Conversor ModelMapper de Entity para Dto
	public EventoDto convertDto(EventoEntity evento) {
		return modelMapper.map(evento, EventoDto.class);
	}

	// Conversor ModelMapper de Entity para Dto
	public EventoEntity convertEntity(EventoDto evento) {
		return modelMapper.map(evento, EventoEntity.class);
	}

	// Conversor Page de Entity para Dto
	public Page<EventoDto> pageDto(Page<EventoEntity> eventoEntity) {
		return eventoEntity.map(convert -> this.eventoDto(convert));
	}

}

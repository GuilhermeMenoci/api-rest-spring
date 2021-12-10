package br.com.magna.api.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magna.api.dto.EventoDto;
import br.com.magna.api.entity.EventoEntity;
import br.com.magna.api.repository.EventoRepository;

@Service
public class EventoService {

	private static Logger logger = LoggerFactory.getLogger(EventoService.class);

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<EventoDto> listPage(Pageable paginacao) throws Exception {
		try {
			Page<EventoEntity> eventos = eventoRepository.findAll(paginacao);
			logger.info("Eventos listados");
			return eventos.map(map -> modelMapper.map(map, EventoDto.class));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}

//	// Listando evento por codigo
//	public EventoDto getCodigo(Long codigo) throws NotFoundException, Exception {
//		try {
//			Optional<EventoEntity> eventoOptional = eventoRepository.findByCodigo(codigo);
//			EventoEntity evento = eventoOptional.orElseThrow(() -> new NotFoundException());
//			EventoDto eventoDto = convertDto(evento);
//			logger.info("Evento com código: " + codigo);
//			return eventoDto;
//		} catch (NotFoundException ex) {
//			logger.error("Não existe esse evento com código: " + codigo);
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//		}
//		return null;
//	}

	// Listando evento por codigo
	public EventoDto getCodigo(int codigo) throws NotFoundException, Exception {
		try {
			Optional<EventoEntity> eventoOptional = eventoRepository.findByCodigo(codigo);
			EventoEntity evento = eventoOptional.orElseThrow(() -> new NotFoundException());
			EventoDto eventoDto = convertDto(evento);
			logger.info("Evento com código: " + codigo);
			return eventoDto;
		} catch (NotFoundException ex) {
			logger.error("Não existe esse evento com código: " + codigo);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	// Verificando se o evento já tem um cadastro
	public Boolean verificaEvento(EventoDto codigoEvento) throws Exception {
		Boolean verificaEvento = false;
		try {
			verificaEvento = eventoRepository.existsByCodigo(codigoEvento.getCodigo());
			return verificaEvento;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return verificaEvento;
		}
	}

	// Criando um evento
	public EventoDto createEventoDto(EventoDto eventoDto) throws IllegalArgumentException, Exception {
		try {
			if (verificaEvento(eventoDto))
				logger.info("Evento com código: " + eventoDto.getCodigo() + " já cadastrado");
			else {
				EventoEntity evento = eventoRepository.save(convertEntity(eventoDto));
				EventoDto eventoDtoSave = convertDto(evento);
				logger.info("Evento cadastrado com sucesso");
				return eventoDtoSave;
			}
		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

//	// Atualizando evento
//	public EventoDto update(Long codigo, EventoDto eventoDto) throws NotFoundException, Exception {
//		try {
//			EventoEntity evento = eventoRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException());
//			EventoDto eventoDtoAntigo = convertDto(evento);
//			BeanUtils.copyProperties(eventoDto, eventoDtoAntigo, "codigo");
//			EventoEntity convertEntity = convertEntity(eventoDto);
//			convertEntity.setId(evento.getId());
//			EventoEntity eventoAtualizado = eventoRepository.save(convertEntity);
//			logger.info("Evento com código: " + eventoDto.getCodigo() + " atualizado");
//			return convertDto(eventoAtualizado);
//		} catch (NotFoundException ex) {
//			logger.error(ex.getMessage());
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//		}
//		return null;
//	}

	// Atualizando evento
	public EventoDto update(int codigo, EventoDto eventoDto) throws NotFoundException, Exception {
		try {
			EventoEntity evento = eventoRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException());
			EventoDto eventoDtoAntigo = convertDto(evento);
			BeanUtils.copyProperties(eventoDto, eventoDtoAntigo, "codigo");
			EventoEntity convertEntity = convertEntity(eventoDto);
			convertEntity.setId(evento.getId());
			EventoEntity eventoAtualizado = eventoRepository.save(convertEntity);
			logger.info("Evento com código: " + eventoDto.getCodigo() + " atualizado");
			return convertDto(eventoAtualizado);
		} catch (NotFoundException ex) {
			logger.error(ex.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	// Deletando um evento
	public void delete(int codigo) throws Exception {
		try {
			eventoRepository.deleteByCodigo(codigo);
			logger.info("Evento com código: " + codigo + " deletado");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} 
	}
//	// Deletando um evento
//	public void delete(Long codigo) throws Exception {
//		try {
//			eventoRepository.deleteByCodigo(codigo);
//			logger.info("Evento com código: " + codigo + " deletado");
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//		}
//	}

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

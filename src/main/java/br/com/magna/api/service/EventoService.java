package br.com.magna.api.service;

import org.modelmapper.ModelMapper;
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
	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ModelMapper modelMapper;

//	// Listando todos os eventos com Page
//	public Page<EventoDto> list(Pageable pageable) {
//		Page<EventoEntity> evento = eventoRepository.findAll(pageable);
//		return evento.map(item -> modelMapper.map(item, EventoDto.class));
//	}

	// Listando todos os eventos com Page(pagina e quantidade)
	public Page<EventoDto> listPage(Long codigo, Pageable paginacao) {
		if (codigo == null) {
			Page<EventoEntity> eventos = eventoRepository.findAll(paginacao);
			return pageDto(eventos);
		} else {
			Page<EventoEntity> eventos = eventoRepository.findByCodigo(codigo, paginacao);
			return pageDto(eventos);
		}
	}

	// Listando evento por codigo
	public EventoDto getCodigo(Long codigo) throws NotFoundException {
		EventoEntity evento = eventoRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException());
		EventoDto eventoDto = convertDto(evento);
		return eventoDto;
	}

	// Verificando se o evento já tem um cadastro
	public Boolean verificaEvento(EventoDto codigoEvento) throws NotFoundException {
		Boolean verificaEvento = eventoRepository.existsByCodigo(codigoEvento.getCodigo());
		return verificaEvento;
	}

	// Criando um evento
	public EventoDto createEventoDto(EventoDto eventoDto) throws NotFoundException {
		if (verificaEvento(eventoDto))
			System.out.println("Evento já cadastrado");
		else {
			EventoEntity evento = eventoRepository.save(convertEntity(eventoDto));
			EventoDto eventoDtoSave = convertDto(evento);
			return eventoDtoSave;
		}
		return null;
	}

	// Atualizando evento
	public EventoDto update(Long codigo, EventoDto eventoDto) throws NotFoundException {
		EventoEntity evento = eventoRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException());
		EventoDto eventoDtoAntigo = convertDto(evento);
		BeanUtils.copyProperties(eventoDto, eventoDtoAntigo, "codigo");
		EventoEntity convertEntity = convertEntity(eventoDto);
		convertEntity.setId(evento.getId());
		EventoEntity eventoAtualizado = eventoRepository.save(convertEntity);
		return convertDto(eventoAtualizado);
	}

	// Deletando um evento
	public void delete(Long codigo) throws NotFoundException {
		eventoRepository.deleteByCodigo(codigo);
	}
	
	// ------------------------------- //

	// CONVERSORES//

	//Construtor do EventoDto
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
	
//	// Convertando a lista de Entity para Dto
//	public List<EventoDto> listDto(List<EventoEntity> evento) {
//		List<EventoDto> eventos = evento.stream().map(EventoDto::new).collect(Collectors.toList());
//		return eventos;
//	}

//	// Convertando a lista de Entity para Dto
//	public Page<EventoDto> pageDto(Page<EventoEntity> evento) {
//		// Page<EventoDto> eventos =
//		// evento.stream().map(EventoDto::new).collect(Collectors.toList());
//		return evento.map(EventoDto::new);
//	}

}

package br.com.magna.api.controller;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.api.dto.EventoDto;
import br.com.magna.api.service.EventoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;
	
	@ApiOperation("Listando eventos com Page")
	// Listando evento com Page e ordem crescente
	@GetMapping
	public Page<EventoDto> listAllEventos(Pageable pagina) {
		try {
			return eventoService.listPage(pagina);
		} catch (NotFoundException ex) {
			ex.getMessage();
		} catch (Exception ex) {
			ex.getMessage();
		}
		return null;
	}
	
//	@ApiOperation("Listando eventos com codigo")
//	// Listando eventos por CODIGO
//	@GetMapping("/{codigo}")
//	public ResponseEntity<EventoDto> listCodigo(@PathVariable Long codigo) {
//		try {
//			return ResponseEntity.ok(eventoService.getCodigo(codigo));
//		} catch (NotFoundException ex) {
//			ex.getMessage();
//			return ResponseEntity.notFound().build();
//		} catch (Exception ex) {
//			return ResponseEntity.badRequest().build();
//		}
//	}
	@ApiOperation("Listando eventos com codigo")
	// Listando eventos por CODIGO
	@GetMapping("/{codigo}")
	public ResponseEntity<EventoDto> listCodigo(@PathVariable int codigo) {
		try {
			return ResponseEntity.ok(eventoService.getCodigo(codigo));
		} catch (NotFoundException ex) {
			ex.getMessage();
			return ResponseEntity.notFound().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Adicionando eventos")
	// Adicionando eventos
	@PostMapping
	public ResponseEntity<EventoDto> createEvento(@RequestBody @Valid EventoDto eventoDto) {
		try {
			EventoDto eventoDtoCreate = eventoService.createEventoDto(eventoDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(eventoDtoCreate);
		} catch (IllegalArgumentException ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.badRequest().build();
		}
	}

//	@ApiOperation("Atualizando eventos")
//	// Atualizando evento
//	@PutMapping("/{codigo}")
//	@Transactional
//	public ResponseEntity<EventoDto> updateEvento(@PathVariable Long codigo, @RequestBody EventoDto eventoDto){
//		try {
//			EventoDto eventoDtoUpdate = eventoService.update(codigo, eventoDto);
//			return ResponseEntity.ok(eventoDtoUpdate);
//		} catch (NotFoundException ex) {
//			ex.getMessage();
//			return ResponseEntity.notFound().build();
//		} catch(EntityNotFoundException ex) {
//			//ex.getMessage();
//			return ResponseEntity.badRequest().build();
//		} catch (Exception ex) {
//			ex.getMessage();
//			return ResponseEntity.badRequest().build();
//		}
//	}
	@ApiOperation("Atualizando eventos")
	// Atualizando evento
	@PutMapping("/{codigo}")
	@Transactional
	public ResponseEntity<EventoDto> updateEvento(@PathVariable int codigo, @RequestBody EventoDto eventoDto){
		try {
			EventoDto eventoDtoUpdate = eventoService.update(codigo, eventoDto);
			return ResponseEntity.ok(eventoDtoUpdate);
		} catch (NotFoundException ex) {
			ex.getMessage();
			return ResponseEntity.notFound().build();
		} catch(EntityNotFoundException ex) {
			//ex.getMessage();
			return ResponseEntity.badRequest().build();
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.badRequest().build();
		}
	}

//	@ApiOperation("Deletando eventos")
//	// Deletando evento
//	@DeleteMapping("/{codigo}")
//	@Transactional
//	public ResponseEntity<EventoDto> deleteEvento(@PathVariable Long codigo) {
//		try {
//			eventoService.delete(codigo);
//			return ResponseEntity.ok().build();
//		} catch (Exception ex) {
//			ex.getMessage();
//			return ResponseEntity.notFound().build();
//		}
//	}
	@ApiOperation("Deletando eventos")
	// Deletando evento
	@DeleteMapping("/{codigo}")
	@Transactional
	public ResponseEntity<EventoDto> deleteEvento(@PathVariable int codigo) {
		try {
			eventoService.delete(codigo);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.notFound().build();
		}
	}
}

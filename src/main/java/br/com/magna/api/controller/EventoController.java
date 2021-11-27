package br.com.magna.api.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.api.dto.EventoDto;
import br.com.magna.api.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	// Listando usuario por CODIGO
	@GetMapping("/{codigo}")
	public ResponseEntity<EventoDto> listCodigo(@PathVariable Long codigo) throws NotFoundException {
		try {
			return ResponseEntity.ok(eventoService.getCodigo(codigo));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			return ResponseEntity.notFound().build();
		}
	}
	
	// Listando evento com Page e ordem crescente
	@GetMapping
	public Page<EventoDto> listCodigo(@RequestParam(required = false) Long codigo, Pageable pagina)
			throws NotFoundException {
		try {
			return eventoService.listPage(codigo, pagina);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			return null;
		}
	}

	// Adicionando eventos
	@PostMapping
	public ResponseEntity<EventoDto> post(@RequestBody @Valid EventoDto eventoDto) throws NotFoundException {
		try {
			EventoDto eventoDtoCreate = eventoService.createEventoDto(eventoDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(eventoDtoCreate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Evento não cadastrado");
			return ResponseEntity.noContent().build();
		}
	}

	// Atualizando evento
	@PutMapping("/{codigo}")
	@Transactional
	public ResponseEntity<EventoDto> put(@PathVariable Long codigo, @RequestBody EventoDto eventoDto)
			throws NotFoundException {
		try {
			EventoDto eventoDtoUpdate = eventoService.update(codigo, eventoDto);
			return ResponseEntity.ok(eventoDtoUpdate);
		} catch (Exception ex) {
			ex.getMessage();
			System.out.println("Evento não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	// Deletando evento
	@DeleteMapping("/{codigo}")
	@Transactional
	public ResponseEntity<EventoDto> delete(@PathVariable Long codigo) {
		try {
			eventoService.delete(codigo);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Evento não encontrado");
			return ResponseEntity.notFound().build();
		}
	}
}

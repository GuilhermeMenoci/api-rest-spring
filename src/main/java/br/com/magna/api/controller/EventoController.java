package br.com.magna.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	@GetMapping
	@Cacheable(value = "listaDeEventos")
	public Page<EventoDto> listAllEventos(Pageable pagina) {

		return eventoService.listPage(pagina);

	}

	@ApiOperation("Listando eventos com codigo")
	@GetMapping("/{codigo}")
	@Cacheable(value = "listaPorCodigo")
	public ResponseEntity<EventoDto> listCodigo(@PathVariable int codigo) {

		return ResponseEntity.ok(eventoService.getCodigo(codigo));

	}

	@ApiOperation("Adicionando eventos")
	@PostMapping
	public ResponseEntity<EventoDto> createEvento(@RequestBody @Valid EventoDto eventoDto) {

		EventoDto eventoDtoCreate = eventoService.createEventoDto(eventoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(eventoDtoCreate);

	}

	@ApiOperation("Atualizando eventos")
	@PutMapping("/{codigo}")
	@Transactional
	public ResponseEntity<EventoDto> updateEvento(@PathVariable int codigo, @RequestBody EventoDto eventoDto) {

		EventoDto eventoDtoUpdate = eventoService.update(codigo, eventoDto);
		return ResponseEntity.ok(eventoDtoUpdate);

	}

	@ApiOperation("Deletando eventos")
	@DeleteMapping("/{codigo}")
	@Transactional
	public ResponseEntity<Void> deleteEvento(@PathVariable int codigo) {

		eventoService.delete(codigo);
		return ResponseEntity.ok().build();

	}
}

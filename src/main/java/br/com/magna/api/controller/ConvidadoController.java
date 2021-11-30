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

import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.service.ConvidadoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convidados")
public class ConvidadoController {
	
	@Autowired
	private ConvidadoService convidadoService;

	@ApiOperation("Listando convidados com Page")
	// Listando convidados com Page e ordem crescente
	@GetMapping
	public Page<ConvidadoDto> listAllConvidados(Pageable pagina) {
		try {
			return convidadoService.listPage(pagina);
		} catch (NotFoundException ex) {
			ex.getMessage();
		} catch(Exception ex) {
			ex.getMessage();
		}
		return null;
	}

	@ApiOperation("Listando convidados com cpf")
	// Listando convidados por CPF
	@GetMapping("/{cpf}")
	public ResponseEntity<ConvidadoDto> listCpf(@PathVariable String cpf) {
		try {
			return ResponseEntity.ok(convidadoService.getCpf(cpf));
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Adicionando convidado")
	// Adicionando convidados
	@PostMapping
	@Transactional
	public ResponseEntity<ConvidadoDto> post(@RequestBody @Valid ConvidadoDto convidadoDto) {
		try {
			ConvidadoDto convidadoDtoCreate = convidadoService.createConvidadoDto(convidadoDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(convidadoDtoCreate);
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.noContent().build();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Atualizando convidado")
	// Atualizando convidado
	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> put(@PathVariable String cpf, @RequestBody ConvidadoDto convidadoDto){
		try {
			ConvidadoDto convidadoDtoUpdate = convidadoService.update(cpf, convidadoDto);
			return ResponseEntity.ok(convidadoDtoUpdate);
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Deletando convidado")
	// Deletando convidados
	@DeleteMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> delete(@PathVariable String cpf) {
		try {
			convidadoService.delete(cpf);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(Exception ex){
			//ex.getMessage();
			return ResponseEntity.badRequest().build();
		}
	}

}

package br.com.magna.api.controller;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@ApiOperation("Listando usuarios com Page")
	// Listando usuario com Page e ordem crescente
	@GetMapping
	@Cacheable(value = "listaDeUsers")
	public Page<UsuarioDto> listAllUser(Pageable pagina) {
		try {
			return usuarioService.listPage(pagina);
		} catch (NotFoundException ex) {
			ex.getMessage();
		} catch(Exception ex) {
			ex.getMessage();
		}
		return null;
	}

	@ApiOperation("Listando usuarios com login")
	// Listando usuario por LOGIN
	@GetMapping("/{login}")
	@Cacheable(value = "listaPorLogin")
	public ResponseEntity<UsuarioDto> listLogin(@PathVariable String login) {
		try {
			return ResponseEntity.ok(usuarioService.getLogin(login));
		} catch (NotFoundException ex) {
		//	ex.getMessage();
			return ResponseEntity.notFound().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Adicionando usuarios")
	// Adicionando usuario
	@PostMapping
	public ResponseEntity<UsuarioDto> createUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
		try {
			UsuarioDto usuarioDtoCreate = usuarioService.createUsuarioDto(usuarioDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtoCreate);
		} catch (IllegalArgumentException ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		} catch(Exception ex) {
			ex.getMessage();
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Atualizando usuario")
	// Atualizando usuario
	@PutMapping("/{login}")
	@Transactional
	public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable String login, 
			@RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioDto usuarioDtoUpdate = usuarioService.update(login, usuarioDto);	
			return ResponseEntity.ok(usuarioDtoUpdate);
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();	
		} catch(EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation("Deletando usuario")
	// Deletando usuario
	@DeleteMapping("/{login}")
	@Transactional
	public ResponseEntity<UsuarioDto> deleteUsuario(@PathVariable String login) {
		try {
			usuarioService.delete(login);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch(Exception ex){
			//ex.getMessage();
			return ResponseEntity.badRequest().build();
		}
	}

}

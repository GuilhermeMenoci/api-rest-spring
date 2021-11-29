package br.com.magna.api.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.com.magna.api.ApiApplication;
import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	@Autowired
	private UsuarioService usuarioService;

	// Listando usuario por LOGIN
	@GetMapping("/{login}")
	public ResponseEntity<UsuarioDto> listLogin(@PathVariable String login) throws NotFoundException {
		try {
			logger.info("Usuario com login: " + login+ " encontrado");
			return ResponseEntity.ok(usuarioService.getLogin(login));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			logger.info("Usuario com login: " + login + " não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	// Listando usuario com Page e ordem crescente
	@GetMapping
	public Page<UsuarioDto> listLogin(@RequestParam(required = false) String login, Pageable pagina)
			throws NotFoundException {
		try {
			logger.info("Usuarios: " + pagina);
			return usuarioService.listPage(login, pagina);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			logger.info("Eventos não encontrados");
			return null;
		}
	}

	// Adicionando usuario
	@PostMapping
	public ResponseEntity<UsuarioDto> post(@RequestBody @Valid UsuarioDto usuarioDto) throws NotFoundException {
		try {
			UsuarioDto usuarioDtoCreate = usuarioService.createUsuarioDto(usuarioDto);
			logger.info("Usuario cadastrado" );
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtoCreate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não cadastrado/já cadastrado");
			logger.info("Usuario não cadastrado");
			return ResponseEntity.noContent().build();
		}
	}

	// Atualizando usuario
	@PutMapping("/{login}")
	@Transactional
	public ResponseEntity<UsuarioDto> put(@PathVariable String login, @RequestBody UsuarioDto usuarioDto)
			throws NotFoundException {
		try {
			UsuarioDto usuarioDtoUpdate = usuarioService.update(login, usuarioDto);
			logger.info("Usuario com login: " + login + " atualizado");
			return ResponseEntity.ok(usuarioDtoUpdate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			logger.info("Usuario com login: " + login + " não atualizado/encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	// Deletando usuario
	@DeleteMapping("/{login}")
	@Transactional
	public ResponseEntity<UsuarioDto> delete(@PathVariable String login) throws NotFoundException {
		try {
			usuarioService.delete(login);
			logger.info("Usuario com login: " + login + " deletado");
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			logger.info("Usuario com login: " + login + " não deletado/encontrado");
			return ResponseEntity.notFound().build();
		}
	}

}

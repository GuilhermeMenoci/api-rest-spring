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
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

//	// Listando todos os usuarios com LIST
//	@GetMapping
//	public List<UsuarioDto> list() {
//		List<UsuarioEntity> listUsuario = usuarioService.listEntity();
//		return usuarioService.listDto(listUsuario);
//	}

	// Listando todos os usuarios com Page
	@GetMapping
	public ResponseEntity<Page<UsuarioDto>> list(Pageable pageable) {
		return ResponseEntity.ok(usuarioService.listEntity(pageable));
	}

	// Listando usuario por LOGIN
	@GetMapping("/{login}")
	public ResponseEntity<UsuarioDto> listLogin(@PathVariable String login) throws NotFoundException {
		try {
			return ResponseEntity.ok(usuarioService.getLogin(login));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	// Adicionando usuario
	@PostMapping
	public ResponseEntity<UsuarioDto> post(@RequestBody @Valid UsuarioDto usuarioDto) throws NotFoundException {
		try {
			UsuarioDto usuarioDtoCreate = usuarioService.createUsuarioDto(usuarioDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtoCreate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não cadastrado/já cadastrado");
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
			return ResponseEntity.ok(usuarioDtoUpdate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			return ResponseEntity.notFound().build();
		}
	}
	
	// Deletando usuario
	@DeleteMapping("/{login}")
	@Transactional
	public ResponseEntity<UsuarioDto> delete(@PathVariable String login) throws NotFoundException {
		try {
			usuarioService.delete(login);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

}

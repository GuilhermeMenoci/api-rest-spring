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

import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.service.ConvidadoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convidados")
public class ConvidadoController {

	@Autowired
	private ConvidadoService convidadoService;

	@ApiOperation("Listando convidados com Page")
	@GetMapping
	@Cacheable(value = "listaDeConvidados")
	public Page<ConvidadoDto> listAllConvidados(Pageable pagina) {

		return convidadoService.listPage(pagina);

	}

	@ApiOperation("Listando convidados com cpf")
	@GetMapping("/{cpf}")
	@Cacheable(value = "listandoPorCpf")
	public ResponseEntity<ConvidadoDto> listCpf(@PathVariable String cpf) {

		return ResponseEntity.ok(convidadoService.getCpf(cpf));

	}

	@ApiOperation("Adicionando convidado")
	@PostMapping
	@Transactional
	public ResponseEntity<ConvidadoDto> post(@RequestBody @Valid ConvidadoDto convidadoDto) {

		ConvidadoDto convidadoDtoCreate = convidadoService.createConvidadoDto(convidadoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(convidadoDtoCreate);

	}

	@ApiOperation("Atualizando convidado")
	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> put(@PathVariable String cpf, @RequestBody ConvidadoDto convidadoDto) {

		ConvidadoDto convidadoDtoUpdate = convidadoService.update(cpf, convidadoDto);
		return ResponseEntity.ok(convidadoDtoUpdate);

	}

	@ApiOperation("Deletando convidado")
	@DeleteMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> delete(@PathVariable String cpf) {

		convidadoService.delete(cpf);
		return ResponseEntity.ok().build();

	}

}

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

import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.service.ConvidadoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convidados")
public class ConvidadoController {
	
	//private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	@Autowired
	private ConvidadoService convidadoService;

	@ApiOperation("Listando convidados com Page")
	// Listando convidados com Page e ordem crescente
	@GetMapping
	public Page<ConvidadoDto> listCpf(@RequestParam(required = false) String cpf, Pageable pagina)
			throws NotFoundException {
		try {
			//logger.info("Convidados: " + pagina);
			return convidadoService.listPage(cpf, pagina);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Convidado não encontrado");
			//logger.info("Convidado não encontrados");
			return null;
		}
	}

	@ApiOperation("Listando convidados com cpf")
	// Listando eventos por CODIGO
	// Listando convidados por CPF
	@GetMapping("/{cpf}")
	public ResponseEntity<ConvidadoDto> listCpf(@PathVariable String cpf) throws NotFoundException {
		try {
			//logger.info("Convidado com codigo: " + cpf + " encontrado");
			return ResponseEntity.ok(convidadoService.getCpf(cpf));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Usuario não encontrado");
			//logger.info("Convidado com codigo: " + cpf + " não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Adicionando convidado")
	// Adicionando convidados
	@PostMapping
	@Transactional
	public ResponseEntity<ConvidadoDto> post(@RequestBody @Valid ConvidadoDto convidadoDto) throws Exception {
		try {
			ConvidadoDto convidadoDtoCreate = convidadoService.createConvidadoDto(convidadoDto);
			//logger.info("Convidado cadastrado");
			return ResponseEntity.status(HttpStatus.CREATED).body(convidadoDtoCreate);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Convidado não cadastrado");
			//logger.info("Evento não cadastrado");
			return ResponseEntity.noContent().build();
		}
	}

	@ApiOperation("Atualizando convidado")
	// Atualizando convidado
	@PutMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> put(@PathVariable String cpf, @RequestBody ConvidadoDto convidadoDto)
			throws NotFoundException {
		try {
			ConvidadoDto convidadoDtoUpdate = convidadoService.update(cpf, convidadoDto);
			//logger.info("Convidado com CPF: " + cpf + " atualizado");
			return ResponseEntity.ok(convidadoDtoUpdate);
		} catch (Exception ex) {
			ex.getMessage();
			System.out.println("Convidado não encontrado");
			//logger.info("Convidado com CPF: " + cpf + " não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Deletando convidado")
	// Deletando convidados
	@DeleteMapping("/{cpf}")
	@Transactional
	public ResponseEntity<ConvidadoDto> delete(@PathVariable String cpf) {
		try {
			convidadoService.delete(cpf);
			//logger.info("Convidado com CPF: " + cpf + " deletado");
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Convidado não encontrado");
			//logger.info("Convidado com CPF: " + cpf + " não encontrado");
			return ResponseEntity.notFound().build();
		}
	}

}

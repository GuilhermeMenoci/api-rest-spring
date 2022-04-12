package br.com.magna.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.magna.api.controller.implementation.RestPageImpl;
import br.com.magna.api.dto.UsuarioDto;
import br.com.magna.api.entity.EventoEntity;
import br.com.magna.api.entity.UsuarioEntity;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerTest extends AbstractControllerIT {

	private static final String USUARIO = "/usuarios/";

	private static final String ERROR = "invalido";

	private static StringBuilder path = new StringBuilder(URL);

	@Test
	@Order(1)
	void salvarUsuario() {

		UsuarioDto usuario = gerarVO();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UsuarioDto> entity = new HttpEntity<UsuarioDto>(usuario, headers);

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity,
				responseType);

		UsuarioEntity usuarioEntity = usuarioRepository.findByLogin(response.getBody().getLogin()).get();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(usuarioEntity.getLogin(), usuario.getLogin());
	}

	@Test
	@Order(2)
	void esperaAlterarUsuario() {

		path.append("adm");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<>() {
		};

		UsuarioDto evento = gerarVO();
		evento.setSenha("put");

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(evento, headers), responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(3)
	void esperaEncontrarUsuarioPorLogin() {
		path.append("teste");

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<UsuarioDto>() {
		};

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		UsuarioEntity entity = usuarioRepository.findByLogin("teste").get();
		assertEquals(response.getBody().getLogin(), entity.getLogin());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(4)
	void esperaNaoAlterarUsuario() {

		path.append(ERROR);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<>() {
		};

		UsuarioDto evento = gerarVO();

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(evento, headers), responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(5)
	void esperaNaoEncontrarUsuarioPorLogin() {
		path.append(ERROR);

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<UsuarioDto>() {
		};

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(6)
	void esperaEncontrarTodosUsuarios() {

		ParameterizedTypeReference<RestPageImpl<UsuarioDto>> responseType = new ParameterizedTypeReference<RestPageImpl<UsuarioDto>>() {
		};

		ResponseEntity<RestPageImpl<UsuarioDto>> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET,
				null, responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(7)
	void esperaDeletarUsuarioPorLogin() {
		path.append("teste");

		ParameterizedTypeReference<UsuarioDto> responseType = new ParameterizedTypeReference<UsuarioDto>() {
		};

		ResponseEntity<UsuarioDto> response = restTemplate.exchange(path.toString(), HttpMethod.DELETE, null,
				responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	private UsuarioDto gerarVO() {

		UsuarioDto vo = new UsuarioDto();

		EventoEntity evento = eventoRepository.findByCodigo(1).get();
		vo.setEvento(evento);
		vo.setLogin("root");
		vo.setSenha("root");

		return vo;

	}

	@BeforeEach
	void inicializar() {
		path.append(USUARIO);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}

}

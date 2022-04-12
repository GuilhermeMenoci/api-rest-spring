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
import br.com.magna.api.dto.ConvidadoDto;
import br.com.magna.api.entity.ConvidadoEntity;
import br.com.magna.api.entity.EventoEntity;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConvidadoControllerTest extends AbstractControllerIT{

	private static final String CONVIDADO = "/convidados/";

	private static final String ERROR = "invalido";

	private static StringBuilder path = new StringBuilder(URL);
	
	@Test
	@Order(1)
	void salvarConvidado() {

		ConvidadoDto usuario = gerarVO();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConvidadoDto> entity = new HttpEntity<ConvidadoDto>(usuario, headers);

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity,
				responseType);

		ConvidadoEntity convidadoEntity = convidadoRepository.findByCpf(response.getBody().getCpf()).get();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(convidadoEntity.getCpf(), usuario.getCpf());
	}
	
	@Test
	@Order(2)
	void esperaAlterarConvidado() {

		path.append("14126816003");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<>() {
		};

		ConvidadoDto convidado = gerarVO();
		convidado.setNome("put");

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(convidado, headers), responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	@Order(3)
	void esperaEncontrarConvidadoPorCpf() {
		path.append("14126816003");

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<ConvidadoDto>() {
		};

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		ConvidadoEntity entity = convidadoRepository.findByCpf("14126816003").get();
		assertEquals(response.getBody().getCpf(), entity.getCpf());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@Order(4)
	void esperaNaoAlterarConvidado() {

		path.append(ERROR);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<>() {
		};

		ConvidadoDto convidado = gerarVO();

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(convidado, headers), responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}
	
	@Test
	@Order(5)
	void esperaNaoEncontrarConvidadoPorCpf() {
		path.append(ERROR);

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<ConvidadoDto>() {
		};

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Order(6)
	void esperaEncontrarTodosConvidados() {

		ParameterizedTypeReference<RestPageImpl<ConvidadoDto>> responseType = new ParameterizedTypeReference<RestPageImpl<ConvidadoDto>>() {
		};

		ResponseEntity<RestPageImpl<ConvidadoDto>> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET,
				null, responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	@Order(7)
	void esperaNaoSalvarUsuarioPorJaExistir() {

		ConvidadoDto atividade = gerarVO();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConvidadoDto> entity = new HttpEntity<ConvidadoDto>(atividade, headers);

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Order(8)
	void esperaDeletarConvidadoPorCpf() {
		path.append("14126816003");

		ParameterizedTypeReference<ConvidadoDto> responseType = new ParameterizedTypeReference<ConvidadoDto>() {
		};

		ResponseEntity<ConvidadoDto> response = restTemplate.exchange(path.toString(), HttpMethod.DELETE, null,
				responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	private ConvidadoDto gerarVO() {

		ConvidadoDto vo = new ConvidadoDto();

		EventoEntity evento = eventoRepository.findByCodigo(1).get();
		vo.setEvento(evento);
		vo.setCpf("14126816003");
		vo.setNome("Convidado 1");

		return vo;

	}
	
	@BeforeEach
	void inicializar() {
		path.append(CONVIDADO);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}
	
}

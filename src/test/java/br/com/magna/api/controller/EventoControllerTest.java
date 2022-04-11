package br.com.magna.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.magna.api.controller.implementation.RestPageImpl;
import br.com.magna.api.dto.EventoDto;
import br.com.magna.api.entity.EventoEntity;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventoControllerTest extends AbstractControllerIT {

	private static final String CONSULTA_EVENTO = "/eventos/";
	private static final String IDCOMPLETO = "1";
	private static final Long ERROR = 1111111L;

	private static StringBuilder path = new StringBuilder(URL);

	@Test
	@Order(1)
	void salvarEvento() {

		EventoDto atividade = gerarVO();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EventoDto> entity = new HttpEntity<EventoDto>(atividade, headers);

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity,
				responseType);

		EventoEntity eventoEntity = eventoRepository.findByCodigo(response.getBody().getCodigo()).get();

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(eventoEntity.getCodigo(), atividade.getCodigo());
	}

	@Test
	@Order(2)
	void esperaAlterarEvento() {

		path.append("3");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Pageable pg = Pageable.ofSize(1);

		EventoEntity entity = eventoRepository.findAll(pg).stream().findFirst().get();

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<>() {
		};

		EventoDto evento = alterarVO();

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(evento, headers), responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(3)
	void esperaEncontrarEventoPorCodigo() {
		path.append("3");

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<EventoDto>() {
		};

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null, responseType);

		EventoEntity entity = eventoRepository.findByCodigo(3).get();
		assertEquals(response.getBody().getCodigo(), entity.getCodigo());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(4)
	void esperaNaoAlterarEvento() {

		path.append(ERROR);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<>() {
		};

		EventoDto evento = alterarVO();

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.PUT,
				new HttpEntity<>(evento, headers), responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(5)
	void esperaNaoEncontrarEventoPorCodigo() {
		path.append(ERROR);

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<EventoDto>() {
		};

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.GET, null, responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(6)
	void esperaEncontrarTodosEventos() {

		ParameterizedTypeReference<RestPageImpl<EventoDto>> responseType = new ParameterizedTypeReference<RestPageImpl<EventoDto>>() {
		};

		ResponseEntity<RestPageImpl<EventoDto>> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET,
				null, responseType);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(7)
	void esperaDeletarEventoPorCodigo() {
		path.append("3");

		ParameterizedTypeReference<EventoDto> responseType = new ParameterizedTypeReference<EventoDto>() {
		};

		ResponseEntity<EventoDto> response = restTemplate.exchange(path.toString(), HttpMethod.DELETE, null, responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	private EventoDto alterarVO() {

		EventoDto vo = new EventoDto();

		vo.setCidade("Minas Gerais");
		vo.setCodigo(3);
		vo.setData(LocalDate.now());
		vo.setNomeEvento("Evento");

		return vo;

	}

	private EventoDto gerarVO() {

		EventoDto vo = new EventoDto();

		vo.setCidade("Rio de Janeiro");
		vo.setCodigo(3);
		vo.setData(LocalDate.now());
		vo.setNomeEvento("Aniversario");

		return vo;

	}

	@BeforeEach
	void inicializar() {
		path.append(CONSULTA_EVENTO);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}

}

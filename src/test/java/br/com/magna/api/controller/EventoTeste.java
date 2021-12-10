package br.com.magna.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.magna.api.dto.EventoDto;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class EventoTeste {

	private static final String PORT = "8091";
	private static final String URL = "http://localhost:" + PORT + "/eventos";

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test //GET
	void testeSeEstaRetonandoEventoCodigo() {
		ParameterizedTypeReference<EventoDto> responseType =
				new ParameterizedTypeReference<>() {
		};
		ResponseEntity<EventoDto> response = this.
				restTemplate.exchange(URL + "/100",
						HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test //POST
	public void verificaSeEstaCadastrandoEventoNoBancoDados() throws URISyntaxException  {
		EventoDto eventoDto = new EventoDto();
		eventoDto.setCodigo(200);
		eventoDto.setNomeEvento("Teste 2 com JUnit");
		eventoDto.setCidade("cidade aaa");
	    ResponseEntity<EventoDto> result = this.restTemplate.postForEntity(URL, 
	    		eventoDto, EventoDto.class); 
	    assertEquals(201, result.getStatusCodeValue());
	}
	
	
	@Test //DELETE
	public void verificaSeEstaDeletandoEventoNoBancoDados() {
		ParameterizedTypeReference<EventoDto> responseType = 
				new ParameterizedTypeReference<EventoDto>() {
		};
		ResponseEntity<EventoDto> result = restTemplate.exchange(URL + "/200", 
				HttpMethod.DELETE, null,  responseType);
		assertEquals(HttpStatus.OK, result.getStatusCodeValue());	
	}

	@Test  //PUT
	public void verificaSeEstaAtualizandoEventoNoBancoDadoso() {
		EventoDto eventoDto = new EventoDto();
		eventoDto.setCodigo(200);
		eventoDto.setNomeEvento("Teste 2 com JUnit PUT");
		eventoDto.setCidade("cidade aaa PUT");
		
		restTemplate.put(URL + "/200", eventoDto);	
	}
	
	
}

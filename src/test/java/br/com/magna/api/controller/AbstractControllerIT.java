package br.com.magna.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import br.com.magna.api.repository.ConvidadoRepository;
import br.com.magna.api.repository.EventoRepository;
import br.com.magna.api.repository.UsuarioRepository;

public class AbstractControllerIT implements BaseControllerIT {

	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected ConvidadoRepository convidadoRepository;

	@Autowired
	protected UsuarioRepository usuarioRepository;

	@Autowired
	protected EventoRepository eventoRepository;

}

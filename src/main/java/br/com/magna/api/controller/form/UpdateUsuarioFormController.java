package br.com.magna.api.controller.form;

import br.com.magna.api.entity.EventoEntity;
import br.com.magna.api.entity.UsuarioEntity;
import br.com.magna.api.repository.UsuarioRepository;

public class UpdateUsuarioFormController {
	
	private String login;
	private String senha;
	private EventoEntity evento;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public EventoEntity getEvento() {
		return evento;
	}
	
	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}
	
	public UsuarioEntity update(Long id, UsuarioRepository usuarioRepository) {
		UsuarioEntity usuario = usuarioRepository.getById(id);
		
		usuario.setLogin(this.login);
		usuario.setSenha(this.senha);
		usuario.setEvento(this.evento);
		
		return usuario;
	}
	
}	

package br.com.magna.api.controller.form;

import br.com.magna.api.entity.EventoEntity;

public class UsuarioFormController {
	
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
	
	
}

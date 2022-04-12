package br.com.magna.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.magna.api.entity.EventoEntity;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String login;
	@NotNull
	@NotEmpty
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

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	public EventoEntity getEvento() {
		return evento;
	}

}

package br.com.magna.api.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.magna.api.entity.EventoEntity;
import br.com.magna.api.entity.UsuarioEntity;

public class UsuarioDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull @NotEmpty
	private String login;
	@NotNull @NotEmpty
	private String senha;
	private EventoEntity evento;

	public UsuarioDto() {
	}
	
//	public UsuarioDto(@NotNull @NotEmpty String login, @NotNull String senha, EventoEntity evento) {
//		this.login = login;
//		this.senha = senha;
//		this.evento = evento;
//	}
//
	public UsuarioDto(UsuarioEntity usuario) {
		this.login = usuario.getLogin();
		this.senha = usuario.getSenha();
		this.evento = usuario.getEvento();
	}

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
	
	@Override
	public String toString() {
		return "Login: " + this.login + ", Senha: " + this.senha + ", Evento: " + this.evento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(evento, login, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDto other = (UsuarioDto) obj;
		return Objects.equals(evento, other.evento) 
				&& Objects.equals(login, other.login) && Objects.equals(senha, other.senha);
	}

	

}
package br.com.magna.api.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "usuario", schema = "eventos")
public class UsuarioEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String senha;
	@OneToOne(fetch = FetchType.EAGER)
	private EventoEntity evento;

	public UsuarioEntity() {
	}
	
	public UsuarioEntity(Long id, String login, String senha, EventoEntity evento) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.evento = evento;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EventoEntity getEvento() {
		return evento;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "UsuarioEntity [id=" + id + ", login=" + login + ", senha=" + senha + ", evento=" + evento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(evento, id, login, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioEntity other = (UsuarioEntity) obj;
		return Objects.equals(evento, other.evento) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login) && Objects.equals(senha, other.senha);
	}

	
}

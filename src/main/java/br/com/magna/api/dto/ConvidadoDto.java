package br.com.magna.api.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.magna.api.entity.ConvidadoEntity;
import br.com.magna.api.entity.EventoEntity;

public class ConvidadoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull @NotEmpty
	private String cpf;
	@NotNull @NotEmpty
	private String nome;
	private EventoEntity evento;
	
	public ConvidadoDto() {
	}
	
//	public ConvidadoDto(@NotNull @NotEmpty String cpf, @NotNull @NotEmpty String nome, EventoEntity evento) {
//		this.cpf = cpf;
//		this.nome = nome;
//		this.evento = evento;
//	}
	
	public ConvidadoDto(ConvidadoEntity convidado) {
		this.cpf = convidado.getCpf();
		this.nome = convidado.getNome();
		this.evento = convidado.getEvento();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EventoEntity getEvento() {
		return evento;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "Nome: " + this.nome + ", CPF: " + this.cpf + ", Evento: " + this.evento;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cpf, evento, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvidadoDto other = (ConvidadoDto) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(evento, other.evento)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
	
}

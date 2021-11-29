package br.com.magna.api.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Ignorando os relacionamento lazy do hibernate porque eles vem inicialmente vazios e o jackson vai tentar fazer o parse dele pra json/xml
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "convidado")
public class ConvidadoEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String nome;
	@ManyToOne
	private EventoEntity evento;
	
	public ConvidadoEntity() {
	}
	
	public ConvidadoEntity(Long id, String cpf, String nome, EventoEntity evento) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.evento = evento;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public EventoEntity getEvento() {
		return evento;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "ConvidadoEntity [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", evento=" + evento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, evento, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvidadoEntity other = (ConvidadoEntity) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(evento, other.evento) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
	
	
}

package br.com.magna.api.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "evento")
public class EventoEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int codigo;
	//private Long codigo;
	private String nomeEvento;
	private LocalDate data = LocalDate.now();
	private String cidade;
	
	public EventoEntity() {}
	
//	public EventoEntity(Long id, Long codigo, String nomeEvento, String cidade) {
//		this.id = id;
//		this.codigo = codigo;
//		this.nomeEvento = nomeEvento;
//		this.cidade = cidade;
//	}
	
	public EventoEntity(Long id, int codigo, String nomeEvento, String cidade) {
		this.id = id;
		this.codigo = codigo;
		this.nomeEvento = nomeEvento;
		this.cidade = cidade;
	}

//	public Long getCodigo() {
//		return codigo;
//	}
	public int getCodigo() {
		return codigo;
	}
	
//	public void setCodigo(Long codigo) {
//		this.codigo = codigo;
//	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Long getId() {
		return id;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	

	public String getCidade() {
		return cidade;
	}

	@Override
	public String toString() {
		return "EventoEntity [id=" + id + ", codigo=" + codigo + ", nomeEvento=" + nomeEvento + ", data=" + data
				+ ", cidade=" + cidade + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cidade, codigo, data, id, nomeEvento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoEntity other = (EventoEntity) obj;
		return Objects.equals(cidade, other.cidade) && Objects.equals(codigo, other.codigo)
				&& Objects.equals(data, other.data) && Objects.equals(id, other.id)
				&& Objects.equals(nomeEvento, other.nomeEvento);
	}
}

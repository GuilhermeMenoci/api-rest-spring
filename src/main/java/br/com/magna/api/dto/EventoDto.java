package br.com.magna.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private int codigo;

	@NotNull
	@NotEmpty
	private String nomeEvento;
	@JsonProperty()
	private LocalDate data = LocalDate.now();
	@NotNull
	@NotEmpty
	private String cidade;

	public EventoDto() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public String getNomeEvento() {
		return nomeEvento;
	}

	public String getCidade() {
		return cidade;
	}

	@Override
	public String toString() {
		return "EventoDto [codigo=" + codigo + ", nomeEvento=" + nomeEvento + ", data=" + data + ", cidade=" + cidade
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cidade, codigo, data, nomeEvento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoDto other = (EventoDto) obj;
		return Objects.equals(cidade, other.cidade) && Objects.equals(codigo, other.codigo)
				&& Objects.equals(data, other.data) && Objects.equals(nomeEvento, other.nomeEvento);
	}

}

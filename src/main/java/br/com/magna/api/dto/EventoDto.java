package br.com.magna.api.dto;

import java.io.Serializable;
import java.time.LocalDate;

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

}

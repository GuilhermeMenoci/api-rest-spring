package br.com.magna.api.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.magna.api.entity.EventoEntity;

//Dto - Dados que vou enviar/mostrar para o client
public class EventoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long codigo;
	@NotNull @NotEmpty
	private String nomeEvento;
	private LocalDate data = LocalDate.now();
	@NotNull @NotEmpty
	private String cidade;
	
	public EventoDto() {
	}
	
//	public EventoDto(@NotNull @NotEmpty Long codigo, @NotNull @NotEmpty String nomeEvento, @NotNull @NotEmpty String cidade) {
//		this.codigo = codigo;
//		this.nomeEvento = nomeEvento;
//		this.cidade = cidade;
//	}
	
	public EventoDto(EventoEntity evento) {
		this.codigo = evento.getCodigo();
		this.nomeEvento = evento.getNomeEvento();
		this.data = evento.getData();
		this.cidade = evento.getCidade();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
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

	public LocalDate getData() {
		return data;
	}

	public String getCidade() {
		return cidade;
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
				&& Objects.equals(data, other.data)
				&& Objects.equals(nomeEvento, other.nomeEvento);
	}
	
	
	
	
}

package br.com.magna.api.controller.form;

import java.util.List;

import br.com.magna.api.entity.ConvidadoEntity;

//Form - Dados que vou receber do cliente
public class EventoFormController {
	private String nomeEvento;
	//private LocalDate data = LocalDate.now();
	private String cidade;
	private List<ConvidadoEntity> convidados;
	
	public String getNomeEvento() {
		return nomeEvento;
	}
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	/*public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}*/
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public List<ConvidadoEntity> getConvidados() {
		return convidados;
	}
	public void setConvidados(List<ConvidadoEntity> convidados) {
		this.convidados = convidados;
	}
	/*public Evento convert(ConvidadoRepository convidadoRepository) {
		List<Convidado> convidado = convidadoRepository.findByName(convidados);
	
		//String nomesConvidado = convidado.stream().map(c -> c.getNome()).toString();
		//List<Convidado> novoConvidado = convidado.stream().map(c -> c.getNome()).collect(Collectors.toList());
		
		return new Evento(nomeEvento, cidade, convidado);
	}*/
	
	
	
}

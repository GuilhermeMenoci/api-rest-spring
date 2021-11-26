package br.com.magna.config;

public class ErrorCamposDto {
	
	private String campo;
	private String erro;
	
	public ErrorCamposDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
	
	
	
}

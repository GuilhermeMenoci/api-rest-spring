package br.com.magna.api.exception;

import java.io.Serializable;

public class DadosJaCadastradosException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;

	public DadosJaCadastradosException(String msg) {
		super(msg);
	}
	
}

package br.com.magna.api.exception;

import java.io.Serializable;

public class ParametroInvalidoException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;

	public ParametroInvalidoException(String msg) {
		super(msg);
	}
	
}

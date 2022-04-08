package br.com.magna.api.exception;

import java.io.Serializable;

public class NaoEncontradoException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;

	public NaoEncontradoException(String msg) {
		super(msg);
	}
	
}

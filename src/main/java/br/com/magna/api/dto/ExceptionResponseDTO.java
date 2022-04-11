package br.com.magna.api.dto;

import java.io.Serializable;
import java.util.List;

public class ExceptionResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title;
	private String type;
	private List<String> detail;
	private String instance;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getDetail() {
		return detail;
	}

	public void setDetail(List<String> detail) {
		this.detail = detail;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

}

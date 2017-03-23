package com.ntua.ote.logger.web.rest.model;

import java.util.List;

public class ErrorResponse {

	private String code;
	
	private String message;
	
	private List<ErrorDetails> errors;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorDetails> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDetails> errors) {
		this.errors = errors;
	}
	
}

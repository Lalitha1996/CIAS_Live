package com.cias.utility;

public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String error;
	private String message;

	public ConnectionException(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

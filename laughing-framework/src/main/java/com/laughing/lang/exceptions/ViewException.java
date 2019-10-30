package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class ViewException extends LaughingException {

	private static final long serialVersionUID = 2545454546030277458L;

	public ViewException() {
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}

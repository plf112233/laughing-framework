package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class ConvertException extends LaughingException {

	private static final long serialVersionUID = 7538330651811721720L;

	public ConvertException() {
		super();
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

}

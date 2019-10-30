package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class UrlException extends LaughingException {

	private static final long serialVersionUID = -5225236375159307740L;

	public UrlException() {
	}

	public UrlException(String message, Throwable cause) {
		super(message, cause);
	}

	public UrlException(String message) {
		super(message);
	}

	public UrlException(Throwable cause) {
		super(cause);
	}

}

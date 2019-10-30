package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class ResourceException extends LaughingException {

	private static final long serialVersionUID = -7615296012736364519L;

	public ResourceException() {
		super();
	}

	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceException(String message) {
		super(message);
	}

	public ResourceException(Throwable cause) {
		super(cause);
	}

}

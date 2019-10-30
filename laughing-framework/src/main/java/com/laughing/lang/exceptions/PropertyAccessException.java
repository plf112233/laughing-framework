package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class PropertyAccessException extends LaughingException {

	private static final long serialVersionUID = -2053413110258751349L;

	public PropertyAccessException() {
		super();
	}

	public PropertyAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyAccessException(String message) {
		super(message);
	}

	public PropertyAccessException(Throwable cause) {
		super(cause);
	}

}

package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class CanNotFindFormException extends LaughingException {

	private static final long serialVersionUID = 4701032846322493376L;

	public CanNotFindFormException() {
		super();
	}

	public CanNotFindFormException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanNotFindFormException(String message) {
		super(message);
	}

	public CanNotFindFormException(Throwable cause) {
		super(cause);
	}

}

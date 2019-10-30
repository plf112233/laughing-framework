package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class CanNotFindObjectException extends LaughingException {

	private static final long serialVersionUID = 3828233352416695616L;

	public CanNotFindObjectException() {
	}

	public CanNotFindObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanNotFindObjectException(String message) {
		super(message);
	}

	public CanNotFindObjectException(Throwable cause) {
		super(cause);
	}

}

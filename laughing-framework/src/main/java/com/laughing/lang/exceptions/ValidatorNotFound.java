package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class ValidatorNotFound extends LaughingException {

	private static final long serialVersionUID = -2730604747095024468L;

	public ValidatorNotFound() {
	}

	public ValidatorNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorNotFound(String message) {
		super(message);
	}

	public ValidatorNotFound(Throwable cause) {
		super(cause);
	}

}

package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class TypeNotMatchException extends LaughingException {

	private static final long serialVersionUID = -679095444915586962L;

	public TypeNotMatchException() {
	}

	public TypeNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeNotMatchException(String message) {
		super(message);
	}

	public TypeNotMatchException(Throwable cause) {
		super(cause);
	}

}

package com.laughing.lang.exceptions;

public class MethodInvokeException extends MagicException {

	private static final long serialVersionUID = -3261104312151620123L;

	public MethodInvokeException() {
	}

	public MethodInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MethodInvokeException(String message) {
		super(message);
	}

	public MethodInvokeException(Throwable cause) {
		super(cause);
	}

}

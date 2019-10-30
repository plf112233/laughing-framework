package com.laughing.lang.exceptions;

public class TooManyMethodMatchesException extends MagicException {

	private static final long serialVersionUID = -6989550061167592760L;

	public TooManyMethodMatchesException() {
		super();
	}

	public TooManyMethodMatchesException(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyMethodMatchesException(String message) {
		super(message);
	}

	public TooManyMethodMatchesException(Throwable cause) {
		super(cause);
	}

}

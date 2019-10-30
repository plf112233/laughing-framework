package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

public class DateParseException extends LaughingException {

	private static final long serialVersionUID = 5065084266979004996L;

	public DateParseException() {
		super();
	}

	public DateParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateParseException(String message) {
		super(message);
	}

	public DateParseException(Throwable cause) {
		super(cause);
	}

}

package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;


public class ConfigException extends LaughingException {

	private static final long serialVersionUID = -9002377494680175600L;

	public ConfigException() {
		super();
	}

	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(Throwable cause) {
		super(cause);
	}

}

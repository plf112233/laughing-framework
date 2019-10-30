package com.laughing.lang.exceptions;

import com.laughing.lang.LaughingException;

/**
 * @ClassName: MagicException
 * @author lifei.pan
 * @email plfnet@163.com
 * @date 2016年10月20日 下午5:44:23
 *
 */
public class MagicException extends LaughingException {

	private static final long serialVersionUID = 3754251068770045184L;

	public MagicException() {
	}

	public MagicException(String message, Throwable cause) {
		super(message, cause);
	}

	public MagicException(String message) {
		super(message);
	}

	public MagicException(Throwable cause) {
		super(cause);
	}

}

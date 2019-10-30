package com.laughing.lang.exceptions;


/**
* @ClassName: AccessMethodException 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午6:14:31 
*
 */
public class AccessMethodException extends MagicException {

	private static final long serialVersionUID = -2100441222208149924L;

	public AccessMethodException() {
	}

	public AccessMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessMethodException(String message) {
		super(message);
	}

	public AccessMethodException(Throwable cause) {
		super(cause);
	}

}

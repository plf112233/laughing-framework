package com.laughing.lang;

/**
* @Description:laughing通用异常
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:43:31 
*
 */
public class LaughingException extends RuntimeException {

	private static final long serialVersionUID = -4899890936328064231L;

	public LaughingException() {
		super();
	}

	public LaughingException(String message, Throwable cause) {
		super(message, cause);
	}

	public LaughingException(String message) {
		super(message);
	}

	public LaughingException(Throwable cause) {
		super(cause);
	}

}

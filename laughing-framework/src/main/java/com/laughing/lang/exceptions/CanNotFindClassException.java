package com.laughing.lang.exceptions;


/**
* @ClassName: CanNotFindClassException 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午5:45:58 
*
 */
public class CanNotFindClassException extends MagicException {

	private static final long serialVersionUID = 3223099941081750645L;

	public CanNotFindClassException() {
	}

	public CanNotFindClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanNotFindClassException(String message) {
		super(message);
	}

	public CanNotFindClassException(Throwable cause) {
		super(cause);
	}

}

package com.laughing.lang.exceptions;
/**
* @ClassName: CanNotFindMethodException 
* @author lifei.pan
* @email plfnet@163.com
* @date 2016年10月20日 下午6:15:18 
*
 */
public class CanNotFindMethodException extends MagicException {

	private static final long serialVersionUID = -1550914706009777296L;

	public CanNotFindMethodException() {
	}

	public CanNotFindMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public CanNotFindMethodException(String message) {
		super(message);
	}

	public CanNotFindMethodException(Throwable cause) {
		super(cause);
	}

}

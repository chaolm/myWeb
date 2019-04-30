package com.dripop.core.exception;

/**
 * 自定义异常
 */
public class SystemException extends RuntimeException {
	/**
	 * @属性 serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8800509240122625517L;

	private Integer code;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}


	public SystemException(String message) {
		super(message);

	}

	public SystemException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}

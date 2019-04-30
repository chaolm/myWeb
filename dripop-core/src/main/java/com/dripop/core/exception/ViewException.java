package com.dripop.core.exception;

/**
 * 自定义异常
 */
public class ViewException extends RuntimeException {
	/**
	 * @属性 serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8800509240122625517L;

	private Integer code;

	public ViewException() {
		super();

	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);

	}
	public ViewException(String message) {
		super(message);
	}

	public ViewException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}

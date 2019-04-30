package com.dripop.core.exception;

/**
 * 自定义异常
 */
public class RestException extends RuntimeException {
	/**
	 * @属性 serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8800509240122625517L;

	private Integer code;

	private Object data;

	public RestException() {
		super();
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);

	}
	public RestException(String message) {
		super(message);
	}

	public RestException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public RestException(Integer code, Object data, String message) {
		super(message);
		this.code = code;
		this.data = data;
	}

	public RestException(Throwable cause) {
		super(cause);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

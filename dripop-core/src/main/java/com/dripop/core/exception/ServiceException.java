package com.dripop.core.exception;

/**
 * 自定义异常
 */
public class ServiceException extends RuntimeException {
	/** 
	 * @属性 serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 8800509240122625517L;

	private Integer code;

	private Object data;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);

	}
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(Integer code, Object data, String message) {
		super(message);
		this.code = code;
		this.data = data;
	}
	
	public ServiceException(Throwable cause) {
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

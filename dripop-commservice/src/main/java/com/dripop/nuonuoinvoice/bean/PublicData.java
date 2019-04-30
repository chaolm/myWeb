package com.dripop.nuonuoinvoice.bean;


/**
 * 请求体中公共参数model
 * @author sdk.jss.com.cn
 * @version 1.0
 * @since jdk1.7
 */
public class PublicData {
	
	
	/**
	 * 调用方法
	 */
	private String method;
	/**
	 * API版本
	 */
	private String version;
	/**
	 * 时间戳 
	 */
	private String timestamp;
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}

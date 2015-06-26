package com.cartmatic.estore.core.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JFieldError implements Serializable{
	/**
	 * message key
	 */
	private String key;
	
	/**
	 * 具体错误信息
	 */
	private String message;
	
	/**
	 * 错误字段或属性
	 */
	private String field;
	
	/**
	 * 错误对象
	 */
	private String objectName;
	
	/**
	 * 错误值
	 */
	private String rejectedValue;
	
	/**
	 * 0=js提示
	 * 1=alert提示
	 */
	private Short showType=new Short("0");

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public void setShowType(Short showType) {
		this.showType = showType;
	}

	public Short getShowType() {
		return showType;
	}
}

package com.cartmatic.estore.common.model.catalog;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SearchAttributeItem {
	private String operator;
	private String value;
	private Integer dataType;
	
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
	
	public String toString() {
		return new ToStringBuilder(this)
				.append("operator", this.operator) 
				.append("value", this.value) 
				.append("dataType", this.dataType) 
				.toString();
	}
	
}

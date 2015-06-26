package com.cartmatic.estore.common.model.attribute;

public interface AttributeValue {

	public Object getAttributeValue() throws Exception;
	
	public String getAttributeValueDataType();
	
	public String getAttributeName();
	
}


package com.cartmatic.estore.common.model.attribute;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.model.BaseObject;

public abstract class BaseAttributeValue extends BaseObject implements
		AttributeValue, Serializable {

	protected Integer												attributeDataType;

	protected String												shortTextValue;
	protected String												longTextValue;
	protected Integer												intValue;
	protected java.math.BigDecimal									decimalValue;
	protected Short													booleanValue;
	protected java.util.Date										dateValue;

	protected com.cartmatic.estore.common.model.attribute.Attribute	attribute;

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="attributeDateType" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getAttributeDataType() {
		return this.attributeDataType;
	}

	/**
	 * Set the attributeDateType
	 * 
	 * @spring.validator type="required"
	 */
	public void setAttributeDataType(Integer aValue) {
		this.attributeDataType = aValue;
	}
	
	public void setShortTextValue(String shortTextValue) {
			this.shortTextValue = shortTextValue;
	}

	public void setLongTextValue(String longTextValue) {
			this.longTextValue = longTextValue;
	}

	public void setIntValue(Integer intValue) {
			this.intValue = intValue;
	}

	public void setDecimalValue(java.math.BigDecimal decimalValue) {
			this.decimalValue = decimalValue;
	}

	public void setBooleanValue(Short booleanValue) {
			this.booleanValue = booleanValue;
	}

	public void setDateValue(java.util.Date dateValue) {
			this.dateValue = dateValue;
	}

	public com.cartmatic.estore.common.model.attribute.Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(
			com.cartmatic.estore.common.model.attribute.Attribute in_attribute) {
		this.attribute = in_attribute;
	}

	public String getAttributeName() {
		return this.attribute.getAttributeName();
	}

	public Integer getIntValue() {
		return this.intValue;
	}

	public BigDecimal getDecimalValue() {
		return this.decimalValue;
	}

	public Short getBooleanValue() {
		return this.booleanValue;
	}

	public Date getDateValue() throws ParseException {
		return this.dateValue;
	}

	public String getLongTextValue() {
		return this.longTextValue;
	}

	public String getShortTextValue() {
		return this.shortTextValue;
	}

	public Object getAttributeValue(){
		// TODO Auto-generated method stub
		int type = this.attributeDataType.intValue();
		if (type == Constants.DATETYPE_INT.intValue())
			return getIntValue();
		else if (type == Constants.DATETYPE_FLOAT.intValue())
			return getDecimalValue();
		else if (type == Constants.DATETYPE_BOOLEAN.intValue())
			return getBooleanValue().shortValue();
		else if (type == Constants.DATETYPE_DATE.intValue()){
			try {
				if(null == getDateValue())return null;
				return DateUtil.convertDateToString(getDateValue());
			} catch (ParseException e) {
				return null;
			}
		}
		else if (type == Constants.DATETYPE_LONGTEXT.intValue())
			return getLongTextValue();
		else
			return getShortTextValue();
	}

	public String getAttributeValueDataType() {
		int point = getAttributeDataType();
		if (point == Constants.DATETYPE_BOOLEAN.intValue())
			return "Boolean";
		else if (point == Constants.DATETYPE_CHECKBOX.intValue())
			return "Multi CheckBoxex";
		else if (point == Constants.DATETYPE_DATE.intValue())
			return "Date";
		else if (point == Constants.DATETYPE_FLOAT.intValue())
			return "Float";
		else if (point == Constants.DATETYPE_IMAGE.intValue())
			return "Image";
		else if (point == Constants.DATETYPE_INT.intValue())
			return "Int";
		else if (point == Constants.DATETYPE_LONGTEXT.intValue())
			return "Long Text";
		else if (point == Constants.DATETYPE_RADIOBUTTON.intValue())
			return "Radio Buttons";
		else if (point == Constants.DATETYPE_SELECTLIST.intValue())
			return "Select List";
		else if (point == Constants.DATETYPE_SHORTTEXT.intValue())
			return "Short Text";
		else
			return "URL";
	}

	public void setAttributeValue(Object attributeValue) {
		int type = this.attributeDataType.intValue();
		if (type == Constants.DATETYPE_INT.intValue()){
			//int
			String intStr = attributeValue.toString();
			if(!intStr.equals(""))
			   setIntValue(Integer.valueOf(intStr));
		}  
			
		else if (type == Constants.DATETYPE_FLOAT.intValue()){
			//decimal
			String str = attributeValue.toString();
			if(!str.equals("")){
			   Double dou = Double.valueOf(str);
		 	   setDecimalValue(BigDecimal.valueOf(dou));
			}
		}
			
		else if (type == Constants.DATETYPE_BOOLEAN.intValue()){
			//boolean
			Short bool = null;
			if(attributeValue.toString().equals("on"))
			   bool = Short.valueOf("1");
			else 
				bool = 0;
			setBooleanValue(bool);
		}
			
		else if (type == Constants.DATETYPE_DATE.intValue()){
			//date
			try {
				Date date = DateUtil.convertStringToDate(attributeValue.toString());
				setDateValue(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		else if (type == Constants.DATETYPE_LONGTEXT.intValue()){
			//long
			setLongTextValue(attributeValue.toString());
		}
			
		else{
			//shorttext
			setShortTextValue(attributeValue.toString());
		}
			
	}
	
	


}

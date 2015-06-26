package com.cartmatic.estore.common.model.attribute.base;

import java.io.Serializable;
import java.util.Date;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ContentAttrValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ContentAttrValueTbl extends BaseAttributeValue implements Serializable {

    protected Integer contentAttrValueId;
	protected com.cartmatic.estore.common.model.content.Content content;

	/**
	 * Default Empty Constructor for class ContentAttrValue
	 */
	public ContentAttrValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ContentAttrValue
	 */
	public ContentAttrValueTbl (
		 Integer in_contentAttrValueId
        ) {
		this.setContentAttrValueId(in_contentAttrValueId);
    }

	
	public com.cartmatic.estore.common.model.content.Content getContent () {
		return content;
	}	
	
	public void setContent (com.cartmatic.estore.common.model.content.Content in_content) {
		this.content = in_content;
	}
	
	/**
	 * 	 * @return Integer
     * @hibernate.id column="contentAttrValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getContentAttrValueId() {
		return this.contentAttrValueId;
	}
	
	/**
	 * Set the contentAttrValueId
	 */	
	public void setContentAttrValueId(Integer aValue) {
		this.contentAttrValueId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAttributeId() {
		return this.getAttribute()==null?null:this.getAttribute().getAttributeId();
	}
	
	/**
	 * Set the attributeId
	 */	
	public void setAttributeId(Integer aValue) {
	    if (aValue==null) {
	    	attribute = null;
	    } else if (attribute == null) {
	        attribute = new com.cartmatic.estore.common.model.attribute.Attribute(aValue);
	        attribute.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			attribute.setAttributeId(aValue);
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getContentId() {
		return this.getContent()==null?null:this.getContent().getContentId();
	}
	
	/**
	 * Set the contentId
	 */	
	public void setContentId(Integer aValue) {
	    if (aValue==null) {
	    	content = null;
	    } else if (content == null) {
	        content = new com.cartmatic.estore.common.model.content.Content(aValue);
	        content.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			content.setContentId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ContentAttrValueTbl)) {
			return false;
		}
		ContentAttrValueTbl rhs = (ContentAttrValueTbl) object;
		return new EqualsBuilder()
				.append(this.contentAttrValueId, rhs.contentAttrValueId)
								.append(this.shortTextValue, rhs.shortTextValue)
				.append(this.longTextValue, rhs.longTextValue)
				.append(this.intValue, rhs.intValue)
				.append(this.decimalValue, rhs.decimalValue)
				.append(this.booleanValue, rhs.booleanValue)
				.append(this.attributeDataType, rhs.attributeDataType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.contentAttrValueId) 
								.append(this.shortTextValue) 
				.append(this.longTextValue) 
				.append(this.intValue) 
				.append(this.decimalValue) 
				.append(this.booleanValue) 
				.append(this.attributeDataType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("contentAttrValueId", this.contentAttrValueId) 
								.append("shortTextValue", this.shortTextValue) 
				.append("longTextValue", this.longTextValue) 
				.append("intValue", this.intValue) 
				.append("decimalValue", this.decimalValue) 
				.append("booleanValue", this.booleanValue) 
				.append("attributeDateType", this.attributeDataType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "contentAttrValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return contentAttrValueId;
	}
}
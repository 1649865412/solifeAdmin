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
 * CategoryAttrValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CategoryAttrValueTbl extends BaseAttributeValue implements Serializable {

    protected Integer categoryAttrValueId;
	protected com.cartmatic.estore.common.model.catalog.Category category;


	/**
	 * Default Empty Constructor for class CategoryAttrValue
	 */
	public CategoryAttrValueTbl () {
		super();
	}
	
	/**  
	 * Default Key Fields Constructor for class CategoryAttrValue
	 */
	public CategoryAttrValueTbl (
		 Integer in_categoryAttrValueId
        ) {
		this.setCategoryAttrValueId(in_categoryAttrValueId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Category getCategory () {
		return category;
	}	
	
	public void setCategory (com.cartmatic.estore.common.model.catalog.Category in_category) {
		this.category = in_category;
	}
	
	/**
	 * 	 * @return Integer
     * @hibernate.id column="categoryAttrValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCategoryAttrValueId() {
		return this.categoryAttrValueId;
	}
	
	/**
	 * Set the categoryAttrValueId
	 */	
	public void setCategoryAttrValueId(Integer aValue) {
		this.categoryAttrValueId = aValue;
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
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the categoryId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else if (category == null) {
	        category = new com.cartmatic.estore.common.model.catalog.Category(aValue);
	        category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			category.setCategoryId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CategoryAttrValueTbl)) {
			return false;
		}
		CategoryAttrValueTbl rhs = (CategoryAttrValueTbl) object;
		return new EqualsBuilder()
				.append(this.categoryAttrValueId, rhs.categoryAttrValueId)
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
				.append(this.categoryAttrValueId) 
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
				.append("categoryAttrValueId", this.categoryAttrValueId) 
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
		return "categoryAttrValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return categoryAttrValueId;
	}

}
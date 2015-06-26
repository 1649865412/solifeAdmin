package com.cartmatic.estore.common.model.attribute.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Attribute Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * All attributes used by product/category
 */
public class AttributeTbl extends BaseObject implements Serializable {

    protected Integer attributeId;
	protected String attributeName;
	protected Short attributeType;
	protected Integer attributeDataType;
	protected String attributeUnit;
	protected String defaultValue;
	protected Boolean isRequired;
	protected String attributeCode;
	protected String description;

	protected Set categoryAttrValues = new HashSet();
	protected Set contentAttrValues = new HashSet();
	protected Set customerAttrValues = new HashSet();
	protected Set productAttrValues = new HashSet();
	protected Set salesorderAttrValues = new HashSet();

	protected java.util.Set productAttGroupItems = new java.util.HashSet();
	/**
	 * Default Empty Constructor for class Attribute
	 */
	public AttributeTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Attribute
	 */
	public AttributeTbl (
		 Integer in_attributeId
        ) {
		this.setAttributeId(in_attributeId);
    }


	public java.util.Set getCategoryAttrValues () {
		return categoryAttrValues;
	}	
	
	public void setCategoryAttrValues (java.util.Set in_categoryAttrValues) {
		this.categoryAttrValues = in_categoryAttrValues;
	}

	public java.util.Set getContentAttrValues () {
		return contentAttrValues;
	}	
	
	public void setContentAttrValues (java.util.Set in_contentAttrValues) {
		this.contentAttrValues = in_contentAttrValues;
	}

	public java.util.Set getCustomerAttrValues () {
		return customerAttrValues;
	}	
	
	public void setCustomerAttrValues (java.util.Set in_customerAttrValues) {
		this.customerAttrValues = in_customerAttrValues;
	}

	public java.util.Set getProductAttrValues () {
		return productAttrValues;
	}	
	
	public void setProductAttrValues (java.util.Set in_productAttrValues) {
		this.productAttrValues = in_productAttrValues;
	}

	public java.util.Set getSalesorderAttrValues () {
		return salesorderAttrValues;
	}	
	
	public void setSalesorderAttrValues (java.util.Set in_salesorderAttrValues) {
		this.salesorderAttrValues = in_salesorderAttrValues;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="attributeId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAttributeId() {
		return this.attributeId;
	}
	
	/**
	 * Set the attributeId
	 */	
	public void setAttributeId(Integer aValue) {
		this.attributeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="attributeName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getAttributeName() {
		return this.attributeName;
	}
	
	/**
	 * Set the attributeName
	 * @spring.validator type="required"
	 */	
	public void setAttributeName(String aValue) {
		this.attributeName = aValue;
	}	

	/**
	 * Define the attribute is used for what purpose
            0=product/category
            1=customer
            2=order
            3=other
            9=general	 
     * @return Short
	 * @hibernate.property column="attributeType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getAttributeType() {
		return this.attributeType;
	}
	
	/**
	 * Set the attributeType
	 * @spring.validator type="required"
	 */	
	public void setAttributeType(Short aValue) {
		this.attributeType = aValue;
	}	

	/**
	 * 1=Multi CheckBoxes 2=Url 3=image 4=int 5=float 6=boolean 7=date time 8=date 9=Select List 10=Radio Buttons
            11=text
            12=long text
            	 * @return Integer
	 * @hibernate.property column="attributeDataType" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getAttributeDataType() {
		return this.attributeDataType;
	}
	
	/**
	 * Set the attributeDateType
	 * @spring.validator type="required"
	 */	
	public void setAttributeDataType(Integer aValue) {
		this.attributeDataType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="attributeUnit" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getAttributeUnit() {
		return this.attributeUnit;
	}
	
	/**
	 * Set the attributeUnit
	 */	
	public void setAttributeUnit(String aValue) {
		this.attributeUnit = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="defaultValue" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	/**
	 * Set the defaultValue
	 */	
	public void setDefaultValue(String aValue) {
		this.defaultValue = aValue;
	}	

	/**
	 * 0=No
            1=Yes	 * @return Short
	 * @hibernate.property column="isRequired" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Boolean getIsRequired() {
		return this.isRequired;
	}
	
	/**
	 * Set the isRequired
	 */	
	public void setIsRequired(Boolean aValue) {
		this.isRequired = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="attributeCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getAttributeCode() {
		return this.attributeCode;
	}
	
	/**
	 * Set the attributeCode
	 * @spring.validator type="required"
	 */	
	public void setAttributeCode(String aValue) {
		this.attributeCode = aValue;
	}	



	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AttributeTbl)) {
			return false;
		}
		AttributeTbl rhs = (AttributeTbl) object;
		return new EqualsBuilder()
				.append(this.attributeId, rhs.attributeId)
				.append(this.attributeName, rhs.attributeName)
				.append(this.attributeType, rhs.attributeType)
				.append(this.attributeDataType, rhs.attributeDataType)
				.append(this.attributeUnit, rhs.attributeUnit)
				.append(this.defaultValue, rhs.defaultValue)
				.append(this.isRequired, rhs.isRequired)
				.append(this.attributeCode, rhs.attributeCode)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.attributeId) 
				.append(this.attributeName) 
				.append(this.attributeType) 
				.append(this.attributeDataType) 
				.append(this.attributeUnit) 
				.append(this.defaultValue) 
				.append(this.isRequired) 
				.append(this.attributeCode) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("attributeId", this.attributeId) 
				.append("attributeName", this.attributeName) 
				.append("attributeType", this.attributeType) 
				.append("attributeDateType", this.attributeDataType) 
				.append("attributeUnit", this.attributeUnit) 
				.append("defaultValue", this.defaultValue) 
				.append("isRequired", this.isRequired) 
				.append("attributeCode", this.attributeCode) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "attributeId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return attributeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Set getProductAttGroupItems () {
		return productAttGroupItems;
	}	
	
	public void setProductAttGroupItems (java.util.Set in_productAttGroupItems) {
		this.productAttGroupItems = in_productAttGroupItems;
	}
}
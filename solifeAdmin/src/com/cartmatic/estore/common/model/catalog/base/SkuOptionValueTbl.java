package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SkuOptionValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SkuOptionValueTbl extends BaseObject implements Serializable {

    protected Integer skuOptionValueId;
	protected String skuOptionValue;
	protected String skuOptionValueName;
	protected Integer sortOrder;
	protected Short status;
	protected Short skuOptionValueType;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.SkuOption skuOption;

	protected java.util.Set productSkuOptionValues = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class SkuOptionValue
	 */
	public SkuOptionValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SkuOptionValue
	 */
	public SkuOptionValueTbl (
		 Integer in_skuOptionValueId
        ) {
		this.setSkuOptionValueId(in_skuOptionValueId);
    }

	
	public com.cartmatic.estore.common.model.catalog.SkuOption getSkuOption () {
		return skuOption;
	}	
	
	public void setSkuOption (com.cartmatic.estore.common.model.catalog.SkuOption in_skuOption) {
		this.skuOption = in_skuOption;
	}

	public java.util.Set getProductSkuOptionValues () {
		return productSkuOptionValues;
	}	
	
	public void setProductSkuOptionValues (java.util.Set in_productSkuOptionValues) {
		this.productSkuOptionValues = in_productSkuOptionValues;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="skuOptionValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSkuOptionValueId() {
		return this.skuOptionValueId;
	}
	
	/**
	 * Set the skuOptionValueId
	 */	
	public void setSkuOptionValueId(Integer aValue) {
		this.skuOptionValueId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSkuOptionId() {
		return this.getSkuOption()==null?null:this.getSkuOption().getSkuOptionId();
	}
	
	/**
	 * Set the skuOptionId
	 */	
	public void setSkuOptionId(Integer aValue) {
	    if (aValue==null) {
	    	skuOption = null;
	    } else {
	    	skuOption = new com.cartmatic.estore.common.model.catalog.SkuOption(aValue);
	        skuOption.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 变种属性选项值	 * @return String
	 * @hibernate.property column="skuOptionValue" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSkuOptionValue() {
		return this.skuOptionValue;
	}
	
	/**
	 * Set the skuOptionValue
	 * @spring.validator type="required"
	 */	
	public void setSkuOptionValue(String aValue) {
		this.skuOptionValue = aValue;
	}	

	/**
	 * 变种属性选项名称	 * @return String
	 * @hibernate.property column="skuOptionValueName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSkuOptionValueName() {
		return this.skuOptionValueName;
	}
	
	/**
	 * Set the skuOptionValueName
	 * @spring.validator type="required"
	 */	
	public void setSkuOptionValueName(String aValue) {
		this.skuOptionValueName = aValue;
	}	

	/**
	 * 前台变种属性选项显示排序	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Set the sortOrder
	 */	
	public void setSortOrder(Integer aValue) {
		this.sortOrder = aValue;
	}	

	/**
	 * 1=激活
            2=非激活	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 * @spring.validator type="required"
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SkuOptionValueTbl)) {
			return false;
		}
		SkuOptionValueTbl rhs = (SkuOptionValueTbl) object;
		return new EqualsBuilder()
				.append(this.skuOptionValueId, rhs.skuOptionValueId)
						.append(this.skuOptionValue, rhs.skuOptionValue)
				.append(this.skuOptionValueName, rhs.skuOptionValueName)
				.append(this.skuOptionValueType, rhs.skuOptionValueType)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.skuOptionValueId) 
						.append(this.skuOptionValue) 
				.append(this.skuOptionValueName) 
				.append(this.skuOptionValueType) 
				.append(this.sortOrder) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("skuOptionValueId", this.skuOptionValueId) 
						.append("skuOptionValue", this.skuOptionValue) 
				.append("skuOptionValueName", this.skuOptionValueName) 
				.append("skuOptionValueType", this.skuOptionValueType) 
				.append("sortOrder", this.sortOrder) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "skuOptionValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return skuOptionValueId;
	}

	public Short getSkuOptionValueType() {
		return skuOptionValueType;
	}

	public void setSkuOptionValueType(Short skuOptionValueType) {
		this.skuOptionValueType = skuOptionValueType;
	}

}
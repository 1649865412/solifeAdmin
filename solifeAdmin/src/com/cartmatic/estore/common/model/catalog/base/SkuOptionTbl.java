package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SkuOption Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SkuOptionTbl extends BaseObject implements Serializable {

    protected Integer skuOptionId;
	protected String skuOptionName;
	protected String skuOptionCode;
	protected Short status;
	protected Integer version;

	protected java.util.Set productTypeSkuOptions = new java.util.HashSet();
	protected java.util.Set skuOptionValues = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class SkuOption
	 */
	public SkuOptionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SkuOption
	 */
	public SkuOptionTbl (
		 Integer in_skuOptionId
        ) {
		this.setSkuOptionId(in_skuOptionId);
    }


	public java.util.Set getProductTypeSkuOptions () {
		return productTypeSkuOptions;
	}	
	
	public void setProductTypeSkuOptions (java.util.Set in_productTypeSkuOptions) {
		this.productTypeSkuOptions = in_productTypeSkuOptions;
	}

	public java.util.Set getSkuOptionValues () {
		return skuOptionValues;
	}	
	
	public void setSkuOptionValues (java.util.Set in_skuOptionValues) {
		this.skuOptionValues = in_skuOptionValues;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="skuOptionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSkuOptionId() {
		return this.skuOptionId;
	}
	
	/**
	 * Set the skuOptionId
	 */	
	public void setSkuOptionId(Integer aValue) {
		this.skuOptionId = aValue;
	}	

	/**
	 * 变种属性名称	 * @return String
	 * @hibernate.property column="skuOptionName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSkuOptionName() {
		return this.skuOptionName;
	}
	
	/**
	 * Set the skuOptionName
	 * @spring.validator type="required"
	 */	
	public void setSkuOptionName(String aValue) {
		this.skuOptionName = aValue;
	}	

	/**
	 * 变种属性编码	 * @return String
	 * @hibernate.property column="skuOptionCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getSkuOptionCode() {
		return this.skuOptionCode;
	}
	
	/**
	 * Set the skuOptionCode
	 * @spring.validator type="required"
	 */	
	public void setSkuOptionCode(String aValue) {
		this.skuOptionCode = aValue;
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
		if (!(object instanceof SkuOptionTbl)) {
			return false;
		}
		SkuOptionTbl rhs = (SkuOptionTbl) object;
		return new EqualsBuilder()
				.append(this.skuOptionId, rhs.skuOptionId)
				.append(this.skuOptionName, rhs.skuOptionName)
				.append(this.skuOptionCode, rhs.skuOptionCode)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.skuOptionId) 
				.append(this.skuOptionName) 
				.append(this.skuOptionCode) 
				.append(this.status) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("skuOptionId", this.skuOptionId) 
				.append("skuOptionName", this.skuOptionName) 
				.append("skuOptionCode", this.skuOptionCode) 
				.append("status", this.status) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "skuOptionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return skuOptionId;
	}

}
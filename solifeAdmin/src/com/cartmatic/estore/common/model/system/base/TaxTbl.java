package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.system.Tax;

/**
 * Tax Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class TaxTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

    protected Integer taxId;
	protected String taxName;
	protected String taxRegisterNo;
	protected Short status;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;

	protected java.util.Set taxRates = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Tax
	 */
	public TaxTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Tax
	 */
	public TaxTbl (
		 Integer in_taxId
        ) {
		this.setTaxId(in_taxId);
    }


	public java.util.Set getTaxRates () {
		return taxRates;
	}	
	
	public void setTaxRates (java.util.Set in_taxRates) {
		this.taxRates = in_taxRates;
	}
    

	/**
	 * @return Integer
     * @hibernate.id column="taxId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTaxId() {
		return this.taxId;
	}
	
	/**
	 * Set the taxId
	 */	
	public void setTaxId(Integer aValue) {
		this.taxId = aValue;
	}	

	/**
	 * @return String
	 * @hibernate.property column="taxName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getTaxName() {
		return this.taxName;
	}
	
	/**
	 * Set the taxName
	 * @spring.validator type="required"
	 */	
	public void setTaxName(String aValue) {
		this.taxName = aValue;
	}	


	/**
	 * @return String
	 * @hibernate.property column="taxRegisterNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getTaxRegisterNo() {
		return this.taxRegisterNo;
	}
	
	/**
	 * Set the taxRegisterNo
	 */	
	public void setTaxRegisterNo(String aValue) {
		this.taxRegisterNo = aValue;
	}	

	/**
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="6" not-null="false" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createBy
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}	

	/**
	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}
	
	/**
	 * Set the updateBy
	 */	
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
	}	

	/**
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Tax)) {
			return false;
		}
		Tax rhs = (Tax) object;
		return new EqualsBuilder()
				.append(this.taxId, rhs.taxId)
				.append(this.taxName, rhs.taxName)
				.append(this.taxRegisterNo, rhs.taxRegisterNo)
				.append(this.status, rhs.status)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.taxId) 
				.append(this.taxName) 
				.append(this.taxRegisterNo) 
				.append(this.status) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("taxId", this.taxId) 
				.append("taxName", this.taxName) 
				.append("taxRegisterNo", this.taxRegisterNo) 
				.append("status", this.status) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "taxId";
	}
	
}
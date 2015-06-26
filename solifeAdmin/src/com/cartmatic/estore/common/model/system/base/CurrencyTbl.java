package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Currency Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CurrencyTbl extends BaseObject implements Serializable {

    protected Integer currencyId;
	protected String currencyCode;
	protected String currencyName;
	protected Short isDefault;
	protected Short status;
	protected Integer sortOrder;
	protected java.math.BigDecimal exchangeRate;
	protected String currencySymbol;
	protected Integer version;


	/**
	 * Default Empty Constructor for class Currency
	 */
	public CurrencyTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Currency
	 */
	public CurrencyTbl (
		 Integer in_currencyId
        ) {
		this.setCurrencyId(in_currencyId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="currencyId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCurrencyId() {
		return this.currencyId;
	}
	
	/**
	 * Set the currencyId
	 */	
	public void setCurrencyId(Integer aValue) {
		this.currencyId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="currencyCode" type="java.lang.String" length="8" not-null="true" unique="false"
	 */
	public String getCurrencyCode() {
		return this.currencyCode;
	}
	
	/**
	 * Set the currencyCode
	 * @spring.validator type="required"
	 */	
	public void setCurrencyCode(String aValue) {
		this.currencyCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="currencyName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCurrencyName() {
		return this.currencyName;
	}
	
	/**
	 * Set the currencyName
	 * @spring.validator type="required"
	 */	
	public void setCurrencyName(String aValue) {
		this.currencyName = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isDefault" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDefault() {
		return this.isDefault;
	}
	
	/**
	 * Set the isDefault
	 */	
	public void setIsDefault(Short aValue) {
		this.isDefault = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
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
	 * 	 * @return Integer
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="exchangeRate" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}
	
	/**
	 * Set the exchangeRate
	 */	
	public void setExchangeRate(java.math.BigDecimal aValue) {
		this.exchangeRate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="currencySymbol" type="java.lang.String" length="16" not-null="true" unique="false"
	 */
	public String getCurrencySymbol() {
		return this.currencySymbol;
	}
	
	/**
	 * Set the currencySymbol
	 * @spring.validator type="required"
	 */	
	public void setCurrencySymbol(String aValue) {
		this.currencySymbol = aValue;
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
		if (!(object instanceof CurrencyTbl)) {
			return false;
		}
		CurrencyTbl rhs = (CurrencyTbl) object;
		return new EqualsBuilder()
				.append(this.currencyId, rhs.currencyId)
				.append(this.currencyCode, rhs.currencyCode)
				.append(this.currencyName, rhs.currencyName)
				.append(this.isDefault, rhs.isDefault)
				.append(this.status, rhs.status)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.exchangeRate, rhs.exchangeRate)
				.append(this.currencySymbol, rhs.currencySymbol)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.currencyId) 
				.append(this.currencyCode) 
				.append(this.currencyName) 
				.append(this.isDefault) 
				.append(this.status) 
				.append(this.sortOrder) 
				.append(this.exchangeRate) 
				.append(this.currencySymbol) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("currencyId", this.currencyId) 
				.append("currencyCode", this.currencyCode) 
				.append("currencyName", this.currencyName) 
				.append("isDefault", this.isDefault) 
				.append("status", this.status) 
				.append("sortOrder", this.sortOrder) 
				.append("exchangeRate", this.exchangeRate) 
				.append("currencySymbol", this.currencySymbol) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "currencyId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return currencyId;
	}

}
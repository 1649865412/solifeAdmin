package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ValidationSession Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ValidationSessionTbl extends BaseObject implements Serializable {

    protected Integer validationSessionId;
	protected String url;
	protected java.util.Date expiredDate;
	protected String email;
	protected Short vsType;


	/**
	 * Default Empty Constructor for class ValidationSession
	 */
	public ValidationSessionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ValidationSession
	 */
	public ValidationSessionTbl (
		 Integer in_validationSessionId
        ) {
		this.setValidationSessionId(in_validationSessionId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="validationSessionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getValidationSessionId() {
		return this.validationSessionId;
	}
	
	/**
	 * Set the validationSessionId
	 */	
	public void setValidationSessionId(Integer aValue) {
		this.validationSessionId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="url" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Set the url
	 * @spring.validator type="required"
	 */	
	public void setUrl(String aValue) {
		this.url = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="expiredDate" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getExpiredDate() {
		return this.expiredDate;
	}
	
	/**
	 * Set the expiredDate
	 * @spring.validator type="required"
	 */	
	public void setExpiredDate(java.util.Date aValue) {
		this.expiredDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 * @spring.validator type="required"
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="vsType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getVsType() {
		return this.vsType;
	}
	
	/**
	 * Set the vsType
	 * @spring.validator type="required"
	 */	
	public void setVsType(Short aValue) {
		this.vsType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ValidationSessionTbl)) {
			return false;
		}
		ValidationSessionTbl rhs = (ValidationSessionTbl) object;
		return new EqualsBuilder()
				.append(this.validationSessionId, rhs.validationSessionId)
				.append(this.url, rhs.url)
				.append(this.expiredDate, rhs.expiredDate)
				.append(this.email, rhs.email)
				.append(this.vsType, rhs.vsType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.validationSessionId) 
				.append(this.url) 
				.append(this.expiredDate) 
				.append(this.email) 
				.append(this.vsType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("validationSessionId", this.validationSessionId) 
				.append("url", this.url) 
				.append("expiredDate", this.expiredDate) 
				.append("email", this.email) 
				.append("vsType", this.vsType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "validationSessionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return validationSessionId;
	}

}
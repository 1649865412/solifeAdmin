package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.system.SystemLanguage;

/**
 * SystemLanguage Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * Supported languages
 */
public class SystemLanguageTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

    protected Integer systemLanguageId;
	protected String languageNameKey;
	protected String localeCode;
	protected Short status;
	protected Integer version;
	protected Short isDefault;
	protected Integer sortOrder;
	

	
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * Default Empty Constructor for class SystemLanguage
	 */
	public SystemLanguageTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemLanguage
	 */
	public SystemLanguageTbl (
		 Integer in_systemLanguageId
        ) {
		this.setSystemLanguageId(in_systemLanguageId);
    }

    

	/**
	 * 
	 * @return Integer
     * @hibernate.id column="systemLanguageId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSystemLanguageId() {
		return this.systemLanguageId;
	}
	
	/**
	 * Set the systemLanguageId
	 */	
	public void setSystemLanguageId(Integer aValue) {
		this.systemLanguageId = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="languageNameKey" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getLanguageNameKey() {
		return this.languageNameKey;
	}
	
	/**
	 * Set the languageNameKey
	 * @spring.validator type="required"
	 */	
	public void setLanguageNameKey(String aValue) {
		this.languageNameKey = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="localeCode" type="java.lang.String" length="8" not-null="true" unique="false"
	 */
	public String getLocaleCode() {
		return this.localeCode;
	}
	
	/**
	 * Set the localeCode
	 * @spring.validator type="required"
	 */	
	public void setLocaleCode(String aValue) {
		this.localeCode = aValue;
	}	

	/**
	 * 0=active
            1=inactive
            2=deleted
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
	 * 
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
	 * 
	 * @return Short
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemLanguage)) {
			return false;
		}
		SystemLanguage rhs = (SystemLanguage) object;
		return new EqualsBuilder()
				.append(this.systemLanguageId, rhs.systemLanguageId)
				.append(this.languageNameKey, rhs.languageNameKey)
				.append(this.localeCode, rhs.localeCode)
				.append(this.status, rhs.status)
				.append(this.version, rhs.version)
				.append(this.isDefault, rhs.isDefault)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.systemLanguageId) 
				.append(this.languageNameKey) 
				.append(this.localeCode) 
				.append(this.status) 
				.append(this.version) 
				.append(this.isDefault) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("systemLanguageId", this.systemLanguageId) 
				.append("languageNameKey", this.languageNameKey) 
				.append("localeCode", this.localeCode) 
				.append("status", this.status) 
				.append("version", this.version) 
				.append("isDefault", this.isDefault) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "systemLanguageId";
	}
	
	/* (non-Javadoc)
	 * @see com.cartmatic.estore.model.BaseObject#getId()
	 */
	@Override
	public Integer getId() {
		return systemLanguageId;
	}
}
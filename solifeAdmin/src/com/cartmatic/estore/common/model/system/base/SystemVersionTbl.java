package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SystemVersion Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemVersionTbl extends BaseObject implements Serializable {

    protected Integer systemVersionId;
	protected String sysVersion;
	protected String productCode;
	protected String domainName;
	protected String licenseKey;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;


	/**
	 * Default Empty Constructor for class SystemVersion
	 */
	public SystemVersionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemVersion
	 */
	public SystemVersionTbl (
		 Integer in_systemVersionId
        ) {
		this.setSystemVersionId(in_systemVersionId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="systemVersionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSystemVersionId() {
		return this.systemVersionId;
	}
	
	/**
	 * Set the systemVersionId
	 */	
	public void setSystemVersionId(Integer aValue) {
		this.systemVersionId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sysVersion" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getSysVersion() {
		return this.sysVersion;
	}
	
	/**
	 * Set the sysVersion
	 * @spring.validator type="required"
	 */	
	public void setSysVersion(String aValue) {
		this.sysVersion = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productCode" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getProductCode() {
		return this.productCode;
	}
	
	/**
	 * Set the productCode
	 * @spring.validator type="required"
	 */	
	public void setProductCode(String aValue) {
		this.productCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="domainName" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getDomainName() {
		return this.domainName;
	}
	
	/**
	 * Set the domainName
	 * @spring.validator type="required"
	 */	
	public void setDomainName(String aValue) {
		this.domainName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="licenseKey" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getLicenseKey() {
		return this.licenseKey;
	}
	
	/**
	 * Set the licenseKey
	 * @spring.validator type="required"
	 */	
	public void setLicenseKey(String aValue) {
		this.licenseKey = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="false" unique="false"
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
		if (!(object instanceof SystemVersionTbl)) {
			return false;
		}
		SystemVersionTbl rhs = (SystemVersionTbl) object;
		return new EqualsBuilder()
				.append(this.systemVersionId, rhs.systemVersionId)
				.append(this.sysVersion, rhs.sysVersion)
				.append(this.productCode, rhs.productCode)
				.append(this.domainName, rhs.domainName)
				.append(this.licenseKey, rhs.licenseKey)
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
				.append(this.systemVersionId) 
				.append(this.sysVersion) 
				.append(this.productCode) 
				.append(this.domainName) 
				.append(this.licenseKey) 
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
				.append("systemVersionId", this.systemVersionId) 
				.append("sysVersion", this.sysVersion) 
				.append("productCode", this.productCode) 
				.append("domainName", this.domainName) 
				.append("licenseKey", this.licenseKey) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "systemVersionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return systemVersionId;
	}

}
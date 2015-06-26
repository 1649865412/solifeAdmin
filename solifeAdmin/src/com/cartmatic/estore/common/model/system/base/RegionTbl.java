package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Region Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class RegionTbl extends BaseObject implements Serializable {

    protected Integer regionId;
	protected String regionCode;
	protected Integer regionType;
	protected String regionName;
	protected String zipCode;
	protected String telephoneCode;
	protected String regionIcon;
	protected Integer priority;
	protected Integer parentRegionId;
	protected String description;
	protected Integer status;
	protected Integer createBy;
	protected java.util.Date createTime;
	protected Integer updateBy;
	protected java.util.Date updateTime;
	protected Integer version;

	protected java.util.Set regionItems = new java.util.HashSet();
	protected java.util.Set shippingRates = new java.util.HashSet();
	protected java.util.Set taxRates = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Region
	 */
	public RegionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Region
	 */
	public RegionTbl (
		 Integer in_regionId
        ) {
		this.setRegionId(in_regionId);
    }


	public java.util.Set getRegionItems () {
		return regionItems;
	}	
	
	public void setRegionItems (java.util.Set in_regionItems) {
		this.regionItems = in_regionItems;
	}

	public java.util.Set getShippingRates () {
		return shippingRates;
	}	
	
	public void setShippingRates (java.util.Set in_shippingRates) {
		this.shippingRates = in_shippingRates;
	}

	public java.util.Set getTaxRates () {
		return taxRates;
	}	
	
	public void setTaxRates (java.util.Set in_taxRates) {
		this.taxRates = in_taxRates;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="regionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getRegionId() {
		return this.regionId;
	}
	
	/**
	 * Set the regionId
	 */	
	public void setRegionId(Integer aValue) {
		this.regionId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="regionCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getRegionCode() {
		return this.regionCode;
	}
	
	/**
	 * Set the regionCode
	 * @spring.validator type="required"
	 */	
	public void setRegionCode(String aValue) {
		this.regionCode = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="regionType" type="java.lang.Integer" length="5" not-null="false" unique="false"
	 */
	public Integer getRegionType() {
		return this.regionType;
	}
	
	/**
	 * Set the regionType
	 */	
	public void setRegionType(Integer aValue) {
		this.regionType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="regionName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getRegionName() {
		return this.regionName;
	}
	
	/**
	 * Set the regionName
	 * @spring.validator type="required"
	 */	
	public void setRegionName(String aValue) {
		this.regionName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="zipCode" type="java.lang.String" length="12" not-null="false" unique="false"
	 */
	public String getZipCode() {
		return this.zipCode;
	}
	
	/**
	 * Set the zipCode
	 */	
	public void setZipCode(String aValue) {
		this.zipCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="telephoneCode" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public String getTelephoneCode() {
		return this.telephoneCode;
	}
	
	/**
	 * Set the telephoneCode
	 */	
	public void setTelephoneCode(String aValue) {
		this.telephoneCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="regionIcon" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getRegionIcon() {
		return this.regionIcon;
	}
	
	/**
	 * Set the regionIcon
	 */	
	public void setRegionIcon(String aValue) {
		this.regionIcon = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="priority" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPriority() {
		return this.priority;
	}
	
	/**
	 * Set the priority
	 */	
	public void setPriority(Integer aValue) {
		this.priority = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="parentRegionId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getParentRegionId() {
		return this.parentRegionId;
	}
	
	/**
	 * Set the parentRegionId
	 * @spring.validator type="required"
	 */	
	public void setParentRegionId(Integer aValue) {
		this.parentRegionId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set the description
	 */	
	public void setDescription(String aValue) {
		this.description = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Integer" length="5" not-null="false" unique="false"
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Integer aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
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
		if (!(object instanceof RegionTbl)) {
			return false;
		}
		RegionTbl rhs = (RegionTbl) object;
		return new EqualsBuilder()
				.append(this.regionId, rhs.regionId)
				.append(this.regionCode, rhs.regionCode)
				.append(this.regionType, rhs.regionType)
				.append(this.regionName, rhs.regionName)
				.append(this.zipCode, rhs.zipCode)
				.append(this.telephoneCode, rhs.telephoneCode)
				.append(this.regionIcon, rhs.regionIcon)
				.append(this.priority, rhs.priority)
				.append(this.parentRegionId, rhs.parentRegionId)
				.append(this.description, rhs.description)
				.append(this.status, rhs.status)
				.append(this.createBy, rhs.createBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.regionId) 
				.append(this.regionCode) 
				.append(this.regionType) 
				.append(this.regionName) 
				.append(this.zipCode) 
				.append(this.telephoneCode) 
				.append(this.regionIcon) 
				.append(this.priority) 
				.append(this.parentRegionId) 
				.append(this.description) 
				.append(this.status) 
				.append(this.createBy) 
				.append(this.createTime) 
				.append(this.updateBy) 
				.append(this.updateTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("regionId", this.regionId) 
				.append("regionCode", this.regionCode) 
				.append("regionType", this.regionType) 
				.append("regionName", this.regionName) 
				.append("zipCode", this.zipCode) 
				.append("telephoneCode", this.telephoneCode) 
				.append("regionIcon", this.regionIcon) 
				.append("priority", this.priority) 
				.append("parentRegionId", this.parentRegionId) 
				.append("description", this.description) 
				.append("status", this.status) 
				.append("createBy", this.createBy) 
				.append("createTime", this.createTime) 
				.append("updateBy", this.updateBy) 
				.append("updateTime", this.updateTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "regionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return regionId;
	}

}
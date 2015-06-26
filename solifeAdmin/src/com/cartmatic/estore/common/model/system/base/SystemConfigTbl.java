package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SystemConfig Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemConfigTbl extends BaseObject implements Serializable {

    protected Integer systemConfigId;
	protected String configKey;
	protected String description;
	protected String category;
	protected Short configType;
	protected String configValue;
	protected String options;
	protected Short dataType;
	protected Short isSystem;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;
	protected Store store;

	/**
	 * Default Empty Constructor for class SystemConfig
	 */
	public SystemConfigTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemConfig
	 */
	public SystemConfigTbl (
		 Integer in_systemConfigId
        ) {
		this.setSystemConfigId(in_systemConfigId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="systemConfigId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSystemConfigId() {
		return this.systemConfigId;
	}
	
	/**
	 * Set the systemConfigId
	 */	
	public void setSystemConfigId(Integer aValue) {
		this.systemConfigId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="configKey" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getConfigKey() {
		return this.configKey;
	}
	
	/**
	 * Set the configKey
	 * @spring.validator type="required"
	 */	
	public void setConfigKey(String aValue) {
		this.configKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="512" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="category" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCategory() {
		return this.category;
	}
	
	/**
	 * Set the category
	 */	
	public void setCategory(String aValue) {
		this.category = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="configType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getConfigType() {
		return this.configType;
	}
	
	/**
	 * Set the configType
	 */	
	public void setConfigType(Short aValue) {
		this.configType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="configValue" type="java.lang.String" length="1024" not-null="true" unique="false"
	 */
	public String getConfigValue() {
		return this.configValue;
	}
	
	/**
	 * Set the configValue
	 * @spring.validator type="required"
	 */	
	public void setConfigValue(String aValue) {
		this.configValue = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="options" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getOptions() {
		return this.options;
	}
	
	/**
	 * Set the options
	 */	
	public void setOptions(String aValue) {
		this.options = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="dataType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDataType() {
		return this.dataType;
	}
	
	/**
	 * Set the dataType
	 */	
	public void setDataType(Short aValue) {
		this.dataType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isSystem" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsSystem() {
		return this.isSystem;
	}
	
	/**
	 * Set the isSystem
	 */	
	public void setIsSystem(Short aValue) {
		this.isSystem = aValue;
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

	
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the catalogId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemConfigTbl)) {
			return false;
		}
		SystemConfigTbl rhs = (SystemConfigTbl) object;
		return new EqualsBuilder()
				.append(this.systemConfigId, rhs.systemConfigId)
				.append(this.configKey, rhs.configKey)
				.append(this.description, rhs.description)
				.append(this.category, rhs.category)
				.append(this.configType, rhs.configType)
				.append(this.configValue, rhs.configValue)
				.append(this.options, rhs.options)
				.append(this.dataType, rhs.dataType)
				.append(this.isSystem, rhs.isSystem)
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
				.append(this.systemConfigId) 
				.append(this.configKey) 
				.append(this.description) 
				.append(this.category) 
				.append(this.configType) 
				.append(this.configValue) 
				.append(this.options) 
				.append(this.dataType) 
				.append(this.isSystem) 
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
				.append("systemConfigId", this.systemConfigId) 
				.append("configKey", this.configKey) 
				.append("description", this.description) 
				.append("category", this.category) 
				.append("configType", this.configType) 
				.append("configValue", this.configValue) 
				.append("options", this.options) 
				.append("dataType", this.dataType) 
				.append("isSystem", this.isSystem) 
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
		return "systemConfigId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return systemConfigId;
	}

}
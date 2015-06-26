package com.cartmatic.estore.common.model.content.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AdPositionType Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AdPositionTypeTbl extends BaseObject implements Serializable {

    protected Integer adPositionTypeId;
	protected String positionName;
	protected Integer height;
	protected Integer width;
	protected Short displayType;
	protected Short status;
	protected String description;
	protected String templatePath;
	protected Integer version;
	protected Store store;

	protected java.util.Set advertisements = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class AdPositionType
	 */
	public AdPositionTypeTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AdPositionType
	 */
	public AdPositionTypeTbl (
		 Integer in_adPositionTypeId
        ) {
		this.setAdPositionTypeId(in_adPositionTypeId);
    }


	public java.util.Set getAdvertisements () {
		return advertisements;
	}	
	
	public void setAdvertisements (java.util.Set in_advertisements) {
		this.advertisements = in_advertisements;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="adPositionTypeId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAdPositionTypeId() {
		return this.adPositionTypeId;
	}
	
	/**
	 * Set the adPositionTypeId
	 */	
	public void setAdPositionTypeId(Integer aValue) {
		this.adPositionTypeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="positionName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPositionName() {
		return this.positionName;
	}
	
	/**
	 * Set the positionName
	 */	
	public void setPositionName(String aValue) {
		this.positionName = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="height" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getHeight() {
		return this.height;
	}
	
	/**
	 * Set the height
	 */	
	public void setHeight(Integer aValue) {
		this.height = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="width" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getWidth() {
		return this.width;
	}
	
	/**
	 * Set the width
	 */	
	public void setWidth(Integer aValue) {
		this.width = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="displayType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDisplayType() {
		return this.displayType;
	}
	
	/**
	 * Set the displayType
	 */	
	public void setDisplayType(Short aValue) {
		this.displayType = aValue;
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
	 * @hibernate.property column="templatePath" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTemplatePath() {
		return this.templatePath;
	}
	
	/**
	 * Set the templatePath
	 */	
	public void setTemplatePath(String aValue) {
		this.templatePath = aValue;
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
		if (!(object instanceof AdPositionTypeTbl)) {
			return false;
		}
		AdPositionTypeTbl rhs = (AdPositionTypeTbl) object;
		return new EqualsBuilder()
				.append(this.adPositionTypeId, rhs.adPositionTypeId)
				.append(this.positionName, rhs.positionName)
				.append(this.height, rhs.height)
				.append(this.width, rhs.width)
				.append(this.displayType, rhs.displayType)
				.append(this.status, rhs.status)
				.append(this.description, rhs.description)
				.append(this.templatePath, rhs.templatePath)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.adPositionTypeId) 
				.append(this.positionName) 
				.append(this.height) 
				.append(this.width) 
				.append(this.displayType) 
				.append(this.status) 
				.append(this.description) 
				.append(this.templatePath) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("adPositionTypeId", this.adPositionTypeId) 
				.append("positionName", this.positionName) 
				.append("height", this.height) 
				.append("width", this.width) 
				.append("displayType", this.displayType) 
				.append("status", this.status) 
				.append("description", this.description) 
				.append("templatePath", this.templatePath) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "adPositionTypeId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return adPositionTypeId;
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
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

}
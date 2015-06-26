package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.system.AppResource;

/**
 * Resource Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppResourceTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

    private Integer appResourceId;
	private String resourceName;
	private Short resourceType;
	private String resourceString;
	private String resourceDesc;
	private String resPermission;
	
	protected AppResource parentAppResource;
	
	protected Set appResources = new java.util.HashSet();

	protected Set roleRess = new java.util.HashSet();
	

	public AppResource getParentAppResource()
    {
        return parentAppResource;
    }

    public void setParentAppResource(AppResource parentAppResource)
    {
        this.parentAppResource = parentAppResource;
    }

    public String getResPermission()
    {
        return resPermission;
    }

    public void setResPermission(String resPermission)
    {
        this.resPermission = resPermission;
    }

    public java.util.Set getAppResources()
    {
        return appResources;
    }

    public void setAppResources(java.util.Set appResources)
    {
        this.appResources = appResources;
    }

    /**
	 * Default Empty Constructor for class Resource
	 */
	public AppResourceTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Resource
	 */
	public AppResourceTbl (
		 Integer in_resourceId
        ) {
		this.setAppResourceId(in_resourceId);
    }


	public java.util.Set getRoleRess() {
		return roleRess;
	}

	public void setRoleRess(java.util.Set avalue) {
		this.roleRess = avalue;
	}

/**
	 * 	 * @return Integer
     * @hibernate.id column="appResourceId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAppResourceId() {
		return this.appResourceId;
	}
	
	/**
	 * Set the appResourceId
	 */	
	public void setAppResourceId(Integer aValue) {
		this.appResourceId = aValue;
	}	
	/**
	 * 
	 * @return String
	 * @hibernate.property column="resourceName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getResourceName() {
		return this.resourceName;
	}
	
	/**
	 * Set the resourceName
	 * @spring.validator type="required"
	 */	
	public void setResourceName(String aValue) {
		this.resourceName = aValue;
	}	

	/**
	 * 0=url
1=function
2=acl
	 * @return Short
	 * @hibernate.property column="resourceType" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getResourceType() {
		return this.resourceType;
	}
	
	/**
	 * Set the resourceType
	 * @spring.validator type="required"
	 */	
	public void setResourceType(Short aValue) {
		this.resourceType = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="resourceString" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getResourceString() {
		return this.resourceString;
	}
	
	/**
	 * Set the resourceString
	 * @spring.validator type="required"
	 */	
	public void setResourceString(String aValue) {
		this.resourceString = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="resourceDesc" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getResourceDesc() {
		return this.resourceDesc;
	}
	
	/**
	 * Set the resourceDesc
	 */	
	public void setResourceDesc(String aValue) {
		this.resourceDesc = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppResourceTbl)) {
			return false;
		}
		AppResourceTbl rhs = (AppResourceTbl) object;
		return new EqualsBuilder()
				.append(this.appResourceId, rhs.appResourceId)
				.append(this.resourceName, rhs.resourceName)
				.append(this.resourceType, rhs.resourceType)
				.append(this.resourceString, rhs.resourceString)
				.append(this.resourceDesc, rhs.resourceDesc)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appResourceId) 
				.append(this.resourceName) 
				.append(this.resourceType) 
				.append(this.resourceString) 
				.append(this.resourceDesc) 
				.append(this.resPermission)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("appResourceId", this.appResourceId) 
				.append("resourceName", this.resourceName) 
				.append("resourceType", this.resourceType) 
				.append("resourceString", this.resourceString) 
				.append("resourceDesc", this.resourceDesc) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "appResourceId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return appResourceId;
	}

}
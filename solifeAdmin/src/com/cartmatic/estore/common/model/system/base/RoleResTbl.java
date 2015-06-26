package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * RoleRes Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class RoleResTbl extends BaseObject implements Serializable {

    protected Integer roleResId;
	protected Integer permissionMask;
	protected com.cartmatic.estore.common.model.system.AppRole appRole;
	protected com.cartmatic.estore.common.model.system.AppResource appResource;


	/**
	 * Default Empty Constructor for class RoleRes
	 */
	public RoleResTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RoleRes
	 */
	public RoleResTbl (
		 Integer in_roleResId
        ) {
		this.setRoleResId(in_roleResId);
    }

	
	public com.cartmatic.estore.common.model.system.AppRole getAppRole () {
		return appRole;
	}	
	
	public void setAppRole (com.cartmatic.estore.common.model.system.AppRole in_appRole) {
		this.appRole = in_appRole;
	}
	
	public com.cartmatic.estore.common.model.system.AppResource getAppResource () {
		return appResource;
	}	
	
	public void setAppResource (com.cartmatic.estore.common.model.system.AppResource in_appResource) {
		this.appResource = in_appResource;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="roleResId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getRoleResId() {
		return this.roleResId;
	}
	
	/**
	 * Set the roleResId
	 */	
	public void setRoleResId(Integer aValue) {
		this.roleResId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAppResourceId() {
		return this.getAppResource()==null?null:this.getAppResource().getAppResourceId();
	}
	
	/**
	 * Set the appResourceId
	 */	
	public void setAppResourceId(Integer aValue) {
	    if (aValue==null) {
	    	appResource = null;
	    } else if (appResource == null) {
	        appResource = new com.cartmatic.estore.common.model.system.AppResource(aValue);
	        appResource.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appResource.setAppResourceId(aValue);
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAppRoleId() {
		return this.getAppRole()==null?null:this.getAppRole().getAppRoleId();
	}
	
	/**
	 * Set the appRoleId
	 */	
	public void setAppRoleId(Integer aValue) {
	    if (aValue==null) {
	    	appRole = null;
	    } else if (appRole == null) {
	        appRole = new com.cartmatic.estore.common.model.system.AppRole(aValue);
	        appRole.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appRole.setAppRoleId(aValue);
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="rolePermission" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPermissionMask() {
		return this.permissionMask;
	}
	
	/**
	 * Set the rolePermission
	 */	
	public void setPermissionMask(Integer aValue) {
		this.permissionMask = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RoleResTbl)) {
			return false;
		}
		RoleResTbl rhs = (RoleResTbl) object;
		return new EqualsBuilder()
				.append(this.roleResId, rhs.roleResId)
								.append(this.permissionMask, rhs.permissionMask)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.roleResId) 
								.append(this.permissionMask) 
								.append(this.appRole)
								.append(this.appResource)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("roleResId", this.roleResId) 
								.append("rolePermission", this.permissionMask) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "roleResId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return roleResId;
	}

}
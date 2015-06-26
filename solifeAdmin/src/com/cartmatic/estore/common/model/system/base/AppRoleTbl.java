package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AppRole Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppRoleTbl extends BaseObject implements Serializable {

    protected Integer appRoleId;
	protected String roleName;
	protected String roleDetail;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Short status;
	protected Short isSystem;
	protected Integer version;

	protected java.util.Set roleRess = new java.util.HashSet();
//	protected java.util.Set userRoles = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class AppRole
	 */
	public AppRoleTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppRole
	 */
	public AppRoleTbl (
		 Integer in_appRoleId
        ) {
		this.setAppRoleId(in_appRoleId);
    }


	public java.util.Set getRoleRess () {
		return roleRess;
	}	
	
	public void setRoleRess (java.util.Set in_roleRess) {
		this.roleRess = in_roleRess;
	}

/*	
 	public java.util.Set getUserRoles () {
		return userRoles;
	}	
	
	public void setUserRoles (java.util.Set in_userRoles) {
		this.userRoles = in_userRoles;
	}
*/
	/**
	 * 	 * @return Integer
     * @hibernate.id column="appRoleId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAppRoleId() {
		return this.appRoleId;
	}
	
	/**
	 * Set the appRoleId
	 */	
	public void setAppRoleId(Integer aValue) {
		this.appRoleId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="roleName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getRoleName() {
		return this.roleName;
	}
	
	/**
	 * Set the roleName
	 * @spring.validator type="required"
	 */	
	public void setRoleName(String aValue) {
		this.roleName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="roleDetail" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRoleDetail() {
		return this.roleDetail;
	}
	
	/**
	 * Set the roleDetail
	 */	
	public void setRoleDetail(String aValue) {
		this.roleDetail = aValue;
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
		if (!(object instanceof AppRoleTbl)) {
			return false;
		}
		AppRoleTbl rhs = (AppRoleTbl) object;
		return new EqualsBuilder()
				.append(this.appRoleId, rhs.appRoleId)
				.append(this.roleName, rhs.roleName)
				.append(this.roleDetail, rhs.roleDetail)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.status, rhs.status)
				.append(this.isSystem, rhs.isSystem)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appRoleId) 
				.append(this.roleName) 
				.append(this.roleDetail) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.status) 
				.append(this.isSystem) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("appRoleId", this.appRoleId) 
				.append("roleName", this.roleName) 
				.append("roleDetail", this.roleDetail) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("status", this.status) 
				.append("isSystem", this.isSystem) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "appRoleId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return appRoleId;
	}

}
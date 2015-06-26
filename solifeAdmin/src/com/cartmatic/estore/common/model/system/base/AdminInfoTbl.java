package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AdminInfo Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AdminInfoTbl extends BaseObject implements Serializable {

    protected Integer adminInfoId;
	protected String department;
	protected Integer pagingSetting = 10;
	protected Short productViewSetting = Short.valueOf("0");
	
	protected java.util.Set appUsers = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class AdminInfo
	 */
	public AdminInfoTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AdminInfo
	 */
	public AdminInfoTbl (
		 Integer in_adminInfoId
        ) {
		this.setAdminInfoId(in_adminInfoId);
    }


	public java.util.Set getAppUsers () {
		return appUsers;
	}	
	
	public void setAppUsers (java.util.Set in_appUsers) {
		this.appUsers = in_appUsers;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="adminInfoId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAdminInfoId() {
		return this.adminInfoId;
	}
	
	/**
	 * Set the adminInfoId
	 */	
	public void setAdminInfoId(Integer aValue) {
		this.adminInfoId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="department" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getDepartment() {
		return this.department;
	}
	
	/**
	 * Set the department
	 */	
	public void setDepartment(String aValue) {
		this.department = aValue;
	}	

	/**
	 * 分页的默认行数	 * @return Integer
	 * @hibernate.property column="pagingSetting" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPagingSetting() {
		return this.pagingSetting;
	}
	
	/**
	 * Set the pagingSetting
	 */	
	public void setPagingSetting(Integer aValue) {
		this.pagingSetting = aValue;
	}	
		

	public Short getProductViewSetting() {
		return productViewSetting;
	}

	public void setProductViewSetting(Short productViewSetting) {
		this.productViewSetting = productViewSetting;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AdminInfoTbl)) {
			return false;
		}
		AdminInfoTbl rhs = (AdminInfoTbl) object;
		return new EqualsBuilder()
				.append(this.adminInfoId, rhs.adminInfoId)
				.append(this.department, rhs.department)
				.append(this.pagingSetting, rhs.pagingSetting)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.adminInfoId) 
				.append(this.department) 
				.append(this.pagingSetting) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("adminInfoId", this.adminInfoId) 
				.append("department", this.department) 
				.append("pagingSetting", this.pagingSetting) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "adminInfoId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return adminInfoId;
	}

}
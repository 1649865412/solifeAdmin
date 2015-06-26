package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.common.model.system.AppUser;

/**
 * AppAdmin Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class AppAdminTbl extends AppUser implements Serializable {

	protected String userPosition;
	protected Integer version;
	//protected com.cartmatic.estore.common.model.system.AppUser appUser;
	protected Supplier supplier;
	protected AdminInfo adminInfo;

	/**
	 * Default Empty Constructor for class AppAdmin
	 */
	public AppAdminTbl () {
		super();
		
	}
	
	/**
	 * Default Key Fields Constructor for class AppAdmin
	 */
	public AppAdminTbl (
		 Integer in_appAdminId
        ) {
		this.setAppAdminId(in_appAdminId);
    }

	
//	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
//		return appUser;
//	}	
//	
//	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
//		this.appUser = in_appUser;
//	}
    

	/**
	 * @return Integer
     * @hibernate.id column="appAdminId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAppAdminId() {
		return this.appuserId;
	}
	
	/**
	 * Set the appAdminId
	 */	
	public void setAppAdminId(Integer aValue) {
//	    if (aValue==null) {
//	    	appUser = null;
//	    } else if (appUser == null) {
//	        appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
//	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			appUser.setAppuserId(aValue);
//	    }
		this.appuserId = aValue;
	}	

	
	public void setSupplier(Supplier avalue) {
		this.supplier = avalue;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @return String
	 * @hibernate.property column="userPosition" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getUserPosition() {
		return this.userPosition;
	}
	
	/**
	 * Set the userPosition
	 */	
	public void setUserPosition(String aValue) {
		this.userPosition = aValue;
	}	

	/**
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppAdmin)) {
			return false;
		}
		AppAdmin rhs = (AppAdmin) object;
		return new EqualsBuilder()
				.append(this.appuserId, rhs.appuserId)
				.append(this.userPosition, rhs.userPosition)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.appuserId) 
				.append(this.userPosition) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("appAdminId", this.appuserId) 
				.append("userPosition", this.userPosition) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "appAdminId";
	}

	public AdminInfo getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(AdminInfo adminInfo) {
		this.adminInfo = adminInfo;
	}
	
	/**
	 * @return Integer
	 */
//	public Integer getAppuserId() {
//		//return this.getAppUser()==null?null:this.getAppUser().getAppuserId();
//		return this.getAppuserId();
//	}
	
	/**
	 * Set the appuserId
	 */	
	//public void setAppuserId(Integer aValue) {
//	    if (aValue==null) {
//	    	appUser = null;
//	    } else if (appUser == null) {
//	        appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
//	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//			appUser.setAppuserId(aValue);
//	    }
		//this.appuserId
	//}	

	
}
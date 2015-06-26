package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Membership Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class MembershipTbl extends BaseObject implements Serializable {

    protected Integer membershipId;
	protected String membershipName;
	protected String membershipDetail;
	protected Integer membershipLevel;
	protected Integer upgradeShopPoint;
	protected Short status;
	protected Short deleted = Constants.MARK_NOT_DELETED;
	protected Integer version;

	protected java.util.Set appUsers = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Membership
	 */
	public MembershipTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Membership
	 */
	public MembershipTbl (
		 Integer in_membershipId
        ) {
		this.setMembershipId(in_membershipId);
    }


	public java.util.Set getAppUsers () {
		return appUsers;
	}	
	
	public void setAppUsers (java.util.Set in_appUsers) {
		this.appUsers = in_appUsers;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="membershipId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getMembershipId() {
		return this.membershipId;
	}
	
	/**
	 * Set the membershipId
	 */	
	public void setMembershipId(Integer aValue) {
		this.membershipId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="membershipName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getMembershipName() {
		return this.membershipName;
	}
	
	/**
	 * Set the membershipName
	 * @spring.validator type="required"
	 */	
	public void setMembershipName(String aValue) {
		this.membershipName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="membershipDetail" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getMembershipDetail() {
		return this.membershipDetail;
	}
	
	/**
	 * Set the membershipDetail
	 */	
	public void setMembershipDetail(String aValue) {
		this.membershipDetail = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="membershipLevel" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getMembershipLevel() {
		return this.membershipLevel;
	}
	
	/**
	 * Set the membershipLevel
	 * @spring.validator type="required"
	 */	
	public void setMembershipLevel(Integer aValue) {
		this.membershipLevel = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="upgradeShopPoint" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUpgradeShopPoint() {
		return this.upgradeShopPoint;
	}
	
	/**
	 * Set the upgradeShopPoint
	 */	
	public void setUpgradeShopPoint(Integer aValue) {
		this.upgradeShopPoint = aValue;
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
	 * @hibernate.property column="deleted" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDeleted() {
		return this.deleted;
	}
	
	/**
	 * Set the deleted
	 * @spring.validator type="required"
	 */	
	public void setDeleted(Short aValue) {
		this.deleted = aValue;
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
		if (!(object instanceof MembershipTbl)) {
			return false;
		}
		MembershipTbl rhs = (MembershipTbl) object;
		return new EqualsBuilder()
				.append(this.membershipId, rhs.membershipId)
				.append(this.membershipName, rhs.membershipName)
				.append(this.membershipDetail, rhs.membershipDetail)
				.append(this.membershipLevel, rhs.membershipLevel)
				.append(this.upgradeShopPoint, rhs.upgradeShopPoint)
				.append(this.status, rhs.status)
				.append(this.deleted, rhs.deleted)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.membershipId) 
				.append(this.membershipName) 
				.append(this.membershipDetail) 
				.append(this.membershipLevel) 
				.append(this.upgradeShopPoint) 
				.append(this.status) 
				.append(this.deleted) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("membershipId", this.membershipId) 
				.append("membershipName", this.membershipName) 
				.append("membershipDetail", this.membershipDetail) 
				.append("membershipLevel", this.membershipLevel) 
				.append("upgradeShopPoint", this.upgradeShopPoint) 
				.append("status", this.status) 
				.append("deleted", this.deleted) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "membershipId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return membershipId;
	}

}
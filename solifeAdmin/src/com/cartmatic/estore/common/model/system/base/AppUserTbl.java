package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * AppUser Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppUserTbl extends BaseObject implements Serializable {

    protected Integer appuserId;
	protected String username;
	protected String password;
	protected Short userType;
	protected String title;
	protected String firstname;
	protected String lastname;
	protected String email;
	protected String fax;
	protected String telephone;
	protected String preferredLanguage;
	protected java.util.Date lastLoginTime;
	//protected Short status;
	//protected Short isLocked;
	protected Short status = Constants.STATUS_ACTIVE;
	protected Short isLocked = AppUser.CONST_UNLOCKED;
	protected String userPosition;
	protected Short deleted = Constants.MARK_NOT_DELETED;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;

	protected java.util.Set userRoles = new java.util.HashSet();
	
	protected java.util.Set addresss = new java.util.HashSet();
	
	protected java.util.Set feedbacks = new java.util.HashSet();
	protected java.util.Set orderReturns = new java.util.HashSet();
	protected java.util.Set productReviews = new java.util.HashSet();
	protected java.util.Set reviewVotes = new java.util.HashSet();
	protected java.util.Set shopPoints = new java.util.HashSet();
	protected java.util.Set shopPointHistorys = new java.util.HashSet();
	protected java.util.Set systemMessages = new java.util.HashSet();
	protected java.util.Set wishlists = new java.util.HashSet();
	
	protected Set<Coupon> userCoupons = new HashSet<Coupon>();

	/**
	 * Default Empty Constructor for class AppUser
	 */
	public AppUserTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppUser
	 */
	public AppUserTbl (
		 Integer in_appuserId
        ) {
		this.setAppuserId(in_appuserId);
    }

	public java.util.Set getAddresss () {
		return addresss;
	}	
	
	public void setAddresss (java.util.Set in_addresss) {
		this.addresss = in_addresss;
	}

	public java.util.Set getFeedbacks () {
		return feedbacks;
	}	
	
	public void setFeedbacks (java.util.Set in_feedbacks) {
		this.feedbacks = in_feedbacks;
	}

	public java.util.Set getOrderReturns () {
		return orderReturns;
	}	
	
	public void setOrderReturns (java.util.Set in_orderReturns) {
		this.orderReturns = in_orderReturns;
	}

	public java.util.Set getProductReviews () {
		return productReviews;
	}	
	
	public void setProductReviews (java.util.Set in_productReviews) {
		this.productReviews = in_productReviews;
	}

	public java.util.Set getReviewVotes () {
		return reviewVotes;
	}	
	
	public void setReviewVotes (java.util.Set in_reviewVotes) {
		this.reviewVotes = in_reviewVotes;
	}

	public java.util.Set getShopPoints () {
		return shopPoints;
	}	
	
	public void setShopPoints (java.util.Set in_shopPoints) {
		this.shopPoints = in_shopPoints;
	}

	public java.util.Set getShopPointHistorys () {
		return shopPointHistorys;
	}	
	
	public void setShopPointHistorys (java.util.Set in_shopPointHistorys) {
		this.shopPointHistorys = in_shopPointHistorys;
	}

	public java.util.Set getSystemMessages () {
		return systemMessages;
	}	
	
	public void setSystemMessages (java.util.Set in_systemMessages) {
		this.systemMessages = in_systemMessages;
	}

	public java.util.Set getUserRoles () {
		return userRoles;
	}	
	
	public void setUserRoles (java.util.Set in_userRoles) {
		this.userRoles = in_userRoles;
	}

	public java.util.Set getWishlists () {
		return wishlists;
	}	
	
	public void setWishlists (java.util.Set in_wishlists) {
		this.wishlists = in_wishlists;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="appuserId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAppuserId() {
		return this.appuserId;
	}
	
	/**
	 * Set the appuserId
	 */	
	public void setAppuserId(Integer aValue) {
		this.appuserId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="username" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Set the username
	 * @spring.validator type="required"
	 */	
	public void setUsername(String aValue) {
		this.username = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="password" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Set the password
	 */	
	public void setPassword(String aValue) {
		this.password = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="usertype" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getUserType() {
		return this.userType;
	}
	
	/**
	 * Set the usertype
	 * @spring.validator type="required"
	 */	
	public void setUserType(Short aValue) {
		this.userType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="firstname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFirstname() {
		return this.firstname;
	}
	
	/**
	 * Set the firstname
	 */	
	public void setFirstname(String aValue) {
		this.firstname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lastname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getLastname() {
		return this.lastname;
	}
	
	/**
	 * Set the lastname
	 */	
	public void setLastname(String aValue) {
		this.lastname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 * @spring.validator type="required"
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}
	
	/**
	 * Set the fax
	 */	
	public void setFax(String aValue) {
		this.fax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="telephone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getTelephone() {
		return this.telephone;
	}
	
	/**
	 * Set the telephone
	 */	
	public void setTelephone(String aValue) {
		this.telephone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="preferredLanguage" type="java.lang.String" length="8" not-null="false" unique="false"
	 */
	public String getPreferredLanguage() {
		return this.preferredLanguage;
	}
	
	/**
	 * Set the preferredLanguage
	 */	
	public void setPreferredLanguage(String aValue) {
		this.preferredLanguage = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="lastLoginTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	
	/**
	 * Set the lastLoginTime
	 */	
	public void setLastLoginTime(java.util.Date aValue) {
		this.lastLoginTime = aValue;
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
	 * @hibernate.property column="isLocked" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsLocked() {
		return this.isLocked;
	}
	
	/**
	 * Set the isLocked
	 */	
	public void setIsLocked(Short aValue) {
		this.isLocked = aValue;
	}	

	/**
	 * 	 * @return String
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

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppUserTbl)) {
			return false;
		}
		AppUserTbl rhs = (AppUserTbl) object;
		return new EqualsBuilder()
				.append(this.appuserId, rhs.appuserId)
								.append(this.username, rhs.username)
				.append(this.password, rhs.password)
				.append(this.userType, rhs.userType)
				.append(this.title, rhs.title)
				.append(this.firstname, rhs.firstname)
				.append(this.lastname, rhs.lastname)
				.append(this.email, rhs.email)
				.append(this.fax, rhs.fax)
				.append(this.telephone, rhs.telephone)
				.append(this.preferredLanguage, rhs.preferredLanguage)
				.append(this.lastLoginTime, rhs.lastLoginTime)
				.append(this.status, rhs.status)
				.append(this.isLocked, rhs.isLocked)
				.append(this.userPosition, rhs.userPosition)
				.append(this.deleted, rhs.deleted)
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
				.append(this.appuserId) 
								.append(this.username) 
				.append(this.password) 
				.append(this.userType) 
				.append(this.title) 
				.append(this.firstname) 
				.append(this.lastname) 
				.append(this.email) 
				.append(this.fax) 
				.append(this.telephone) 
				.append(this.preferredLanguage) 
				.append(this.lastLoginTime) 
				.append(this.status) 
				.append(this.isLocked) 
				.append(this.userPosition) 
				.append(this.deleted) 
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
				.append("appuserId", this.appuserId) 
								.append("username", this.username) 
				.append("password", this.password) 
				.append("usertype", this.userType) 
				.append("title", this.title) 
				.append("firstname", this.firstname) 
				.append("lastname", this.lastname) 
				.append("email", this.email) 
				.append("fax", this.fax) 
				.append("telephone", this.telephone) 
				.append("preferredLanguage", this.preferredLanguage) 
				.append("lastLoginTime", this.lastLoginTime) 
				.append("status", this.status) 
				.append("isLocked", this.isLocked) 
				.append("userPosition", this.userPosition) 
				.append("deleted", this.deleted) 
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
		return "appuserId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return appuserId;
	}

	public Set<Coupon> getUserCoupons() {
		return userCoupons;
	}

	public void setUserCoupons(Set<Coupon> userCoupons) {
		this.userCoupons = userCoupons;
	}

}
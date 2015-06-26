package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Wishlist Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WishlistTbl extends BaseObject implements Serializable {

    protected Integer wishListId;
	protected String wishListTitle;
	protected Short wishListType;
	protected String wishListDetail;
	protected Integer shareLevel;
	protected Short isDefault;
	protected String recipientEmailList;
	protected java.util.Date eventDate;
	protected Integer priority;
	protected java.util.Date createTime;
	protected Integer version;
	protected com.cartmatic.estore.common.model.customer.Address address;
	protected Customer customer;

	protected java.util.Set wishlistItems = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Wishlist
	 */
	public WishlistTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Wishlist
	 */
	public WishlistTbl (
		 Integer in_wishListId
        ) {
		this.setWishListId(in_wishListId);
    }

	
	public com.cartmatic.estore.common.model.customer.Address getAddress () {
		return address;
	}	
	
	public void setAddress (com.cartmatic.estore.common.model.customer.Address in_address) {
		this.address = in_address;
	}
	
    public com.cartmatic.estore.common.model.customer.Customer getCustomer() {
        return customer;
    }

    public void setCustomer(
            com.cartmatic.estore.common.model.customer.Customer in_customer) {
        this.customer = in_customer;
    }
    

	public java.util.Set getWishlistItems () {
		return wishlistItems;
	}	
	
	public void setWishlistItems (java.util.Set in_wishlistItems) {
		this.wishlistItems = in_wishlistItems;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="wishListId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getWishListId() {
		return this.wishListId;
	}
	
	/**
	 * Set the wishListId
	 */	
	public void setWishListId(Integer aValue) {
		this.wishListId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getShippingAddressId() {
		return this.getAddress()==null?null:this.getAddress().getAddressId();
	}
	
	/**
	 * Set the shippingAddressId
	 */	
	public void setShippingAddressId(Integer aValue) {
	    if (aValue==null) {
	    	address = null;
	    } else {
	        address = new com.cartmatic.estore.common.model.customer.Address(aValue);
	        address.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getCustomerId() {
		return this.getCustomer()==null?null:this.getCustomer().getCustomerId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Integer aValue) {
	    if (aValue==null) {
	    	customer = null;
	    } else {
	    	customer = new Customer(aValue);
	    	customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wishListTitle" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getWishListTitle() {
		return this.wishListTitle;
	}
	
	/**
	 * Set the wishListTitle
	 * @spring.validator type="required"
	 */	
	public void setWishListTitle(String aValue) {
		this.wishListTitle = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="wishListType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getWishListType() {
		return this.wishListType;
	}
	
	/**
	 * Set the wishListType
	 * @spring.validator type="required"
	 */	
	public void setWishListType(Short aValue) {
		this.wishListType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wishListDetail" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getWishListDetail() {
		return this.wishListDetail;
	}
	
	/**
	 * Set the wishListDetail
	 */	
	public void setWishListDetail(String aValue) {
		this.wishListDetail = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="shareLevel" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getShareLevel() {
		return this.shareLevel;
	}
	
	/**
	 * Set the shareLevel
	 */	
	public void setShareLevel(Integer aValue) {
		this.shareLevel = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isDefault" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDefault() {
		return this.isDefault;
	}
	
	/**
	 * Set the isDefault
	 */	
	public void setIsDefault(Short aValue) {
		this.isDefault = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="recipientEmailList" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getRecipientEmailList() {
		return this.recipientEmailList;
	}
	
	/**
	 * Set the recipientEmailList
	 */	
	public void setRecipientEmailList(String aValue) {
		this.recipientEmailList = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="eventDate" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getEventDate() {
		return this.eventDate;
	}
	
	/**
	 * Set the eventDate
	 */	
	public void setEventDate(java.util.Date aValue) {
		this.eventDate = aValue;
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
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
		if (!(object instanceof WishlistTbl)) {
			return false;
		}
		WishlistTbl rhs = (WishlistTbl) object;
		return new EqualsBuilder()
				.append(this.wishListId, rhs.wishListId)
								.append(this.wishListTitle, rhs.wishListTitle)
				.append(this.wishListType, rhs.wishListType)
				.append(this.wishListDetail, rhs.wishListDetail)
				.append(this.shareLevel, rhs.shareLevel)
				.append(this.isDefault, rhs.isDefault)
				.append(this.recipientEmailList, rhs.recipientEmailList)
				.append(this.eventDate, rhs.eventDate)
				.append(this.priority, rhs.priority)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.wishListId) 
								.append(this.wishListTitle) 
				.append(this.wishListType) 
				.append(this.wishListDetail) 
				.append(this.shareLevel) 
				.append(this.isDefault) 
				.append(this.recipientEmailList) 
				.append(this.eventDate) 
				.append(this.priority) 
				.append(this.createTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("wishListId", this.wishListId) 
								.append("wishListTitle", this.wishListTitle) 
				.append("wishListType", this.wishListType) 
				.append("wishListDetail", this.wishListDetail) 
				.append("shareLevel", this.shareLevel) 
				.append("isDefault", this.isDefault) 
				.append("recipientEmailList", this.recipientEmailList) 
				.append("eventDate", this.eventDate) 
				.append("priority", this.priority) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "wishListId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return wishListId;
	}

}
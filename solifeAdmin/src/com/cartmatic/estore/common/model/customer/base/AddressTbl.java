package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Address Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AddressTbl extends BaseObject implements Serializable {

    protected Integer addressId;
	protected Short addressType;
	protected String title;
	protected String firstname;
	protected String lastname;
	protected String telephone;
	protected String zip;
	protected String fax;
	protected String companyName;
	protected Short isDefaultShippingAddress;
	protected Short isDefaultBillingAddress;
	protected String address;
	protected String address2;
	protected String countryName;
	protected String stateName;
	protected String cityName;
	
	protected String sectionName;
	
	protected Integer countryId;
	protected Integer stateId;
	protected Integer cityId;
	protected Integer userDefinedId;
	protected String email;
	protected com.cartmatic.estore.common.model.system.AppUser appUser;

	protected java.util.Set wishlists = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Address
	 */
	public AddressTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Address
	 */
	public AddressTbl (
		 Integer in_addressId
        ) {
		this.setAddressId(in_addressId);
    }

	
	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public java.util.Set getWishlists () {
		return wishlists;
	}	
	
	public void setWishlists (java.util.Set in_wishlists) {
		this.wishlists = in_wishlists;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="addressId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getAddressId() {
		return this.addressId;
	}
	
	/**
	 * Set the addressId
	 */	
	public void setAddressId(Integer aValue) {
		this.addressId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAppuserId() {
		return this.getAppUser()==null?null:this.getAppUser().getAppuserId();
	}
	
	/**
	 * Set the appuserId
	 */	
	public void setAppuserId(Integer aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else {
	        appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="addressType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getAddressType() {
		return this.addressType;
	}
	
	/**
	 * Set the addressType
	 * @spring.validator type="required"
	 */	
	public void setAddressType(Short aValue) {
		this.addressType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="8" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="firstname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFirstname() {
		return this.firstname;
	}
	
	/**
	 * Set the firstname
	 * @spring.validator type="required"
	 */	
	public void setFirstname(String aValue) {
		this.firstname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lastname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getLastname() {
		return this.lastname;
	}
	
	/**
	 * Set the lastname
	 * @spring.validator type="required"
	 */	
	public void setLastname(String aValue) {
		this.lastname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="telephone" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getTelephone() {
		return this.telephone;
	}
	
	/**
	 * Set the telephone
	 * @spring.validator type="required"
	 */	
	public void setTelephone(String aValue) {
		this.telephone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="zip" type="java.lang.String" length="12" not-null="false" unique="false"
	 */
	public String getZip() {
		return this.zip;
	}
	
	/**
	 * Set the zip
	 */	
	public void setZip(String aValue) {
		this.zip = aValue;
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
	 * @hibernate.property column="companyName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCompanyName() {
		return this.companyName;
	}
	
	/**
	 * Set the companyName
	 */	
	public void setCompanyName(String aValue) {
		this.companyName = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isDefaultShippingAddress" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDefaultShippingAddress() {
		return this.isDefaultShippingAddress;
	}
	
	/**
	 * Set the isDefaultShippingAddress
	 */	
	public void setIsDefaultShippingAddress(Short aValue) {
		this.isDefaultShippingAddress = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isDefaultBillingAddress" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDefaultBillingAddress() {
		return this.isDefaultBillingAddress;
	}
	
	/**
	 * Set the isDefaultBillingAddress
	 */	
	public void setIsDefaultBillingAddress(Short aValue) {
		this.isDefaultBillingAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 * @spring.validator type="required"
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address2" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getAddress2() {
		return this.address2;
	}
	
	/**
	 * Set the address2
	 */	
	public void setAddress2(String aValue) {
		this.address2 = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="countryName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCountryName() {
		return this.countryName;
	}
	
	/**
	 * Set the countryName
	 * @spring.validator type="required"
	 */	
	public void setCountryName(String aValue) {
		this.countryName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="stateName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getStateName() {
		return this.stateName;
	}
	
	/**
	 * Set the stateName
	 */	
	public void setStateName(String aValue) {
		this.stateName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cityName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCityName() {
		return this.cityName;
	}
	
	/**
	 * Set the cityName
	 */	
	public void setCityName(String aValue) {
		this.cityName = aValue;
	}	

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="countryId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getCountryId() {
		return this.countryId;
	}
	
	/**
	 * Set the countryId
	 * @spring.validator type="required"
	 */	
	public void setCountryId(Integer aValue) {
		this.countryId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="stateId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getStateId() {
		return this.stateId;
	}
	
	/**
	 * Set the stateId
	 */	
	public void setStateId(Integer aValue) {
		this.stateId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="cityId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCityId() {
		return this.cityId;
	}
	
	/**
	 * Set the cityId
	 */	
	public void setCityId(Integer aValue) {
		this.cityId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="userDefinedId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUserDefinedId() {
		return this.userDefinedId;
	}
	
	/**
	 * Set the userDefinedId
	 */	
	public void setUserDefinedId(Integer aValue) {
		this.userDefinedId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AddressTbl)) {
			return false;
		}
		AddressTbl rhs = (AddressTbl) object;
		return new EqualsBuilder()
				.append(this.addressId, rhs.addressId)
						.append(this.addressType, rhs.addressType)
				.append(this.title, rhs.title)
				.append(this.firstname, rhs.firstname)
				.append(this.lastname, rhs.lastname)
				.append(this.telephone, rhs.telephone)
				.append(this.zip, rhs.zip)
				.append(this.fax, rhs.fax)
				.append(this.companyName, rhs.companyName)
				.append(this.isDefaultShippingAddress, rhs.isDefaultShippingAddress)
				.append(this.isDefaultBillingAddress, rhs.isDefaultBillingAddress)
				.append(this.address, rhs.address)
				.append(this.address2, rhs.address2)
				.append(this.countryName, rhs.countryName)
				.append(this.stateName, rhs.stateName)
				.append(this.cityName, rhs.cityName)
				.append(this.countryId, rhs.countryId)
				.append(this.stateId, rhs.stateId)
				.append(this.cityId, rhs.cityId)
				.append(this.userDefinedId, rhs.userDefinedId)
				.append(this.email, rhs.email)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.addressId) 
						.append(this.addressType) 
				.append(this.title) 
				.append(this.firstname) 
				.append(this.lastname) 
				.append(this.telephone) 
				.append(this.zip) 
				.append(this.fax) 
				.append(this.companyName) 
				.append(this.isDefaultShippingAddress) 
				.append(this.isDefaultBillingAddress) 
				.append(this.address) 
				.append(this.address2) 
				.append(this.countryName) 
				.append(this.stateName) 
				.append(this.cityName) 
				.append(this.countryId) 
				.append(this.stateId) 
				.append(this.cityId) 
				.append(this.userDefinedId) 
				.append(this.email) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("addressId", this.addressId) 
						.append("addressType", this.addressType) 
				.append("title", this.title) 
				.append("firstname", this.firstname) 
				.append("lastname", this.lastname) 
				.append("telephone", this.telephone) 
				.append("zip", this.zip) 
				.append("fax", this.fax) 
				.append("companyName", this.companyName) 
				.append("isDefaultShippingAddress", this.isDefaultShippingAddress) 
				.append("isDefaultBillingAddress", this.isDefaultBillingAddress) 
				.append("address", this.address) 
				.append("address2", this.address2) 
				.append("countryName", this.countryName) 
				.append("stateName", this.stateName) 
				.append("cityName", this.cityName) 
				.append("countryId", this.countryId) 
				.append("stateId", this.stateId) 
				.append("cityId", this.cityId) 
				.append("userDefinedId", this.userDefinedId) 
				.append("email", this.email) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "addressId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return addressId;
	}

}
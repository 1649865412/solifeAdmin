package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderAddress Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderAddressTbl extends BaseObject implements Serializable {

    protected Integer orderAddressId;
	protected String firstname;
	protected String lastname;
	protected String title;
	protected String phoneNumber;
	protected String faxNumber;
	protected String postalcode;
	protected String country;
	protected String state;
	protected String city;
	protected String section;
	protected String address1;
	protected String address2;

	protected java.util.Set orderShipments = new java.util.HashSet();
	protected java.util.Set salesOrders = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OrderAddress
	 */
	public OrderAddressTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderAddress
	 */
	public OrderAddressTbl (
		 Integer in_orderAddressId
        ) {
		this.setOrderAddressId(in_orderAddressId);
    }


	public java.util.Set getOrderShipments () {
		return orderShipments;
	}	
	
	public void setOrderShipments (java.util.Set in_orderShipments) {
		this.orderShipments = in_orderShipments;
	}

	public java.util.Set getSalesOrders () {
		return salesOrders;
	}	
	
	public void setSalesOrders (java.util.Set in_salesOrders) {
		this.salesOrders = in_salesOrders;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderAddressId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderAddressId() {
		return this.orderAddressId;
	}
	
	/**
	 * Set the orderAddressId
	 */	
	public void setOrderAddressId(Integer aValue) {
		this.orderAddressId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="firstname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFirstname() {
		return this.firstname;
	}
	
	/**
	 * Set the firstName
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="phoneNumber" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	/**
	 * Set the phoneNumber
	 */	
	public void setPhoneNumber(String aValue) {
		this.phoneNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="faxNumber" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFaxNumber() {
		return this.faxNumber;
	}
	
	/**
	 * Set the faxNumber
	 */	
	public void setFaxNumber(String aValue) {
		this.faxNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="postalcode" type="java.lang.String" length="6" not-null="false" unique="false"
	 */
	public String getPostalcode() {
		return this.postalcode;
	}
	
	/**
	 * Set the postalcode
	 */	
	public void setPostalcode(String aValue) {
		this.postalcode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="country" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCountry() {
		return this.country;
	}
	
	/**
	 * Set the country
	 * @spring.validator type="required"
	 */	
	public void setCountry(String aValue) {
		this.country = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="state" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(String aValue) {
		this.state = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="city" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCity() {
		return this.city;
	}
	
	/**
	 * Set the city
	 */	
	public void setCity(String aValue) {
		this.city = aValue;
	}	

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="address1" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getAddress1() {
		return this.address1;
	}
	
	/**
	 * Set the address1
	 * @spring.validator type="required"
	 */	
	public void setAddress1(String aValue) {
		this.address1 = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderAddressTbl)) {
			return false;
		}
		OrderAddressTbl rhs = (OrderAddressTbl) object;
		return new EqualsBuilder()
				.append(this.orderAddressId, rhs.orderAddressId)
				.append(this.firstname, rhs.firstname)
				.append(this.lastname, rhs.lastname)
				.append(this.phoneNumber, rhs.phoneNumber)
				.append(this.faxNumber, rhs.faxNumber)
				.append(this.postalcode, rhs.postalcode)
				.append(this.country, rhs.country)
				.append(this.state, rhs.state)
				.append(this.city, rhs.city)
				.append(this.address1, rhs.address1)
				.append(this.address2, rhs.address2)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderAddressId) 
				.append(this.firstname) 
				.append(this.lastname) 
				.append(this.phoneNumber) 
				.append(this.faxNumber) 
				.append(this.postalcode) 
				.append(this.country) 
				.append(this.state) 
				.append(this.city) 
				.append(this.address1) 
				.append(this.address2) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderAddressId", this.orderAddressId) 
				.append("firstname", this.firstname) 
				.append("lastName", this.lastname) 
				.append("phoneNumber", this.phoneNumber) 
				.append("faxNumber", this.faxNumber) 
				.append("postalcode", this.postalcode) 
				.append("country", this.country) 
				.append("state", this.state) 
				.append("city", this.city) 
				.append("address1", this.address1) 
				.append("address2", this.address2) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderAddressId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderAddressId;
	}

}
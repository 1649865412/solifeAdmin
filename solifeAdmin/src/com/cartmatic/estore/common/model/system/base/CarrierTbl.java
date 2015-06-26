package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Carrier Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CarrierTbl extends BaseObject implements Serializable {

    protected Integer carrierId;
	protected String carrierName;
	protected String carrierAddress;
	protected String carrierAddress2;
	protected String linkman;
	protected String telephone;
	protected String fax;
	protected String email;
	protected String zip;
	protected Short status;
	protected Short deleted = Constants.MARK_NOT_DELETED;
	protected Integer version;
	protected String logo;

	protected java.util.Set shippingMethods = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Carrier
	 */
	public CarrierTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Carrier
	 */
	public CarrierTbl (
		 Integer in_carrierId
        ) {
		this.setCarrierId(in_carrierId);
    }


	public java.util.Set getShippingMethods () {
		return shippingMethods;
	}	
	
	public void setShippingMethods (java.util.Set in_shippingMethods) {
		this.shippingMethods = in_shippingMethods;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="carrierId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getCarrierId() {
		return this.carrierId;
	}
	
	/**
	 * Set the carrierId
	 */	
	public void setCarrierId(Integer aValue) {
		this.carrierId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="carrierName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getCarrierName() {
		return this.carrierName;
	}
	
	/**
	 * Set the carrierName
	 * @spring.validator type="required"
	 */	
	public void setCarrierName(String aValue) {
		this.carrierName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="carrierAddress" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCarrierAddress() {
		return this.carrierAddress;
	}
	
	/**
	 * Set the carrierAddress
	 */	
	public void setCarrierAddress(String aValue) {
		this.carrierAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="carrierAddress2" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCarrierAddress2() {
		return this.carrierAddress2;
	}
	
	/**
	 * Set the carrierAddress2
	 */	
	public void setCarrierAddress2(String aValue) {
		this.carrierAddress2 = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="linkman" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getLinkman() {
		return this.linkman;
	}
	
	/**
	 * Set the linkman
	 */	
	public void setLinkman(String aValue) {
		this.linkman = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="logo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getLogo() {
		return this.logo;
	}
	
	/**
	 * Set the logo
	 */	
	public void setLogo(String aValue) {
		this.logo = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CarrierTbl)) {
			return false;
		}
		CarrierTbl rhs = (CarrierTbl) object;
		return new EqualsBuilder()
				.append(this.carrierId, rhs.carrierId)
				.append(this.carrierName, rhs.carrierName)
				.append(this.carrierAddress, rhs.carrierAddress)
				.append(this.carrierAddress2, rhs.carrierAddress2)
				.append(this.linkman, rhs.linkman)
				.append(this.telephone, rhs.telephone)
				.append(this.fax, rhs.fax)
				.append(this.email, rhs.email)
				.append(this.zip, rhs.zip)
				.append(this.status, rhs.status)
				.append(this.deleted, rhs.deleted)
				.append(this.version, rhs.version)
				.append(this.logo, rhs.logo)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.carrierId) 
				.append(this.carrierName) 
				.append(this.carrierAddress) 
				.append(this.carrierAddress2) 
				.append(this.linkman) 
				.append(this.telephone) 
				.append(this.fax) 
				.append(this.email) 
				.append(this.zip) 
				.append(this.status) 
				.append(this.deleted) 
				.append(this.version) 
				.append(this.logo) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("carrierId", this.carrierId) 
				.append("carrierName", this.carrierName) 
				.append("carrierAddress", this.carrierAddress) 
				.append("carrierAddress2", this.carrierAddress2) 
				.append("linkman", this.linkman) 
				.append("telephone", this.telephone) 
				.append("fax", this.fax) 
				.append("email", this.email) 
				.append("zip", this.zip) 
				.append("status", this.status) 
				.append("deleted", this.deleted) 
				.append("version", this.version) 
				.append("logo", this.logo) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "carrierId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return carrierId;
	}

}
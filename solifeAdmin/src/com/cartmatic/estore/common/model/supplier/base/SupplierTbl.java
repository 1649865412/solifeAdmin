package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Supplier Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SupplierTbl extends BaseObject implements Serializable {

    protected Integer supplierId;
	protected String supplierName;
	protected String supplierCode;
	protected String email;
	protected String address;
	protected String webSite;
	protected String contacts;
	protected String bankAccount;
	protected String fax;
	protected String zip;
	protected Integer adminId;
	protected String comments;
	protected Short status;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer createBy;
	protected Integer updateBy;
	protected Integer version;

	protected java.util.Set customers = new java.util.HashSet();
	protected java.util.Set purchaseOrders = new java.util.HashSet();
	protected java.util.Set supplierProducts = new java.util.HashSet();

	
	protected String prodDescConvert;

	public String getProdDescConvert() {
		return prodDescConvert;
	}

	public void setProdDescConvert(String prodDescConvert) {
		this.prodDescConvert = prodDescConvert;
	}
	
	/**
	 * Default Empty Constructor for class Supplier
	 */
	public SupplierTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Supplier
	 */
	public SupplierTbl (
		 Integer in_supplierId
        ) {
		this.setSupplierId(in_supplierId);
    }

	public java.util.Set getCustomers() {
		return customers;
	}

	public void setCustomers(java.util.Set customers) {
		this.customers = customers;
	}

	public java.util.Set getPurchaseOrders () {
		return purchaseOrders;
	}	
	
	public void setPurchaseOrders (java.util.Set in_purchaseOrders) {
		this.purchaseOrders = in_purchaseOrders;
	}

	public java.util.Set getSupplierProducts () {
		return supplierProducts;
	}	
	
	public void setSupplierProducts (java.util.Set in_supplierProducts) {
		this.supplierProducts = in_supplierProducts;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="supplierId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSupplierId() {
		return this.supplierId;
	}
	
	/**
	 * Set the supplierId
	 */	
	public void setSupplierId(Integer aValue) {
		this.supplierId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="supplierName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSupplierName() {
		return this.supplierName;
	}
	
	/**
	 * Set the supplierName
	 * @spring.validator type="required"
	 */	
	public void setSupplierName(String aValue) {
		this.supplierName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="supplierCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getSupplierCode() {
		return this.supplierCode;
	}
	
	/**
	 * Set the supplierCode
	 * @spring.validator type="required"
	 */	
	public void setSupplierCode(String aValue) {
		this.supplierCode = aValue;
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
	 * @hibernate.property column="address" type="java.lang.String" length="256" not-null="true" unique="false"
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
	 * @hibernate.property column="telephone" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getWebSite() {
		return this.webSite;
	}
	
	/**
	 * Set the telephone
	 * @spring.validator type="required"
	 */	
	public void setWebSite(String aValue) {
		this.webSite = aValue;
	}	
	
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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
	 * 	 * @return Integer
	 * @hibernate.property column="adminId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getAdminId() {
		return this.adminId;
	}
	
	/**
	 * Set the adminId
	 */	
	public void setAdminId(Integer aValue) {
		this.adminId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="comments" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getComments() {
		return this.comments;
	}
	
	/**
	 * Set the comments
	 */	
	public void setComments(String aValue) {
		this.comments = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 * @spring.validator type="required"
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
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
		if (!(object instanceof SupplierTbl)) {
			return false;
		}
		SupplierTbl rhs = (SupplierTbl) object;
		return new EqualsBuilder()
				.append(this.supplierId, rhs.supplierId)
				.append(this.supplierName, rhs.supplierName)
				.append(this.supplierCode, rhs.supplierCode)
				.append(this.email, rhs.email)
				.append(this.address, rhs.address)
				.append(this.webSite, rhs.webSite)
				.append(this.fax, rhs.fax)
				.append(this.zip, rhs.zip)
				.append(this.adminId, rhs.adminId)
				.append(this.comments, rhs.comments)
				.append(this.status, rhs.status)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.supplierId) 
				.append(this.supplierName) 
				.append(this.supplierCode) 
				.append(this.email) 
				.append(this.address) 
				.append(this.webSite) 
				.append(this.contacts)
				.append(this.bankAccount)
				.append(this.fax) 
				.append(this.zip) 
				.append(this.adminId) 
				.append(this.comments) 
				.append(this.status) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("supplierId", this.supplierId) 
				.append("supplierName", this.supplierName) 
				.append("supplierCode", this.supplierCode) 
				.append("email", this.email) 
				.append("address", this.address) 
				.append("telephone", this.webSite) 
				.append("fax", this.fax) 
				.append("zip", this.zip) 
				.append("adminId", this.adminId) 
				.append("comments", this.comments) 
				.append("status", this.status) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "supplierId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return supplierId;
	}

}
package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.TransferRecord;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TransferRecord Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TransferRecordTbl extends BaseObject implements Serializable {

    protected Integer transferRecordId;
	protected Short type=TransferRecord.TYPE_WESTERN_UNION;
	protected String orderNo;
	protected String mtcnNo;
	protected String firstname;
	protected String middlename;
	protected String lastname;
	protected String address;
	protected String country;
	protected BigDecimal amount;
	/**
	 * 0=未处理
	 * 1=已处理
	 * 2=作废
	 */
	protected String state; 
	protected String city;
	protected String taxCode;
	protected String zip;
	protected String phone;
	protected String question;
	protected String answer;
	protected Short status=Constants.STATUS_NOT_PUBLISHED;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer updateBy;


	/**
	 * Default Empty Constructor for class TransferRecord
	 */
	public TransferRecordTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TransferRecord
	 */
	public TransferRecordTbl (
		 Integer in_transferRecordId
        ) {
		this.setTransferRecordId(in_transferRecordId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="transferRecordId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTransferRecordId() {
		return this.transferRecordId;
	}
	
	/**
	 * Set the transferRecordId
	 */	
	public void setTransferRecordId(Integer aValue) {
		this.transferRecordId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="type" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 * @spring.validator type="required"
	 */	
	public void setType(Short aValue) {
		this.type = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orderNo" type="java.lang.String" length="20" not-null="true" unique="false"
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * Set the orderNo
	 * @spring.validator type="required"
	 */	
	public void setOrderNo(String aValue) {
		this.orderNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mtcnNo" type="java.lang.String" length="16" not-null="false" unique="false"
	 */
	public String getMtcnNo() {
		return this.mtcnNo;
	}
	
	/**
	 * Set the mtcnNo
	 */	
	public void setMtcnNo(String aValue) {
		this.mtcnNo = aValue;
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
	 * @hibernate.property column="middlename" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getMiddlename() {
		return this.middlename;
	}
	
	/**
	 * Set the middlename
	 */	
	public void setMiddlename(String aValue) {
		this.middlename = aValue;
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
	 * @hibernate.property column="address" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="country" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCountry() {
		return this.country;
	}
	
	/**
	 * Set the country
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

	/**
	 * 	 * @return String
	 * @hibernate.property column="zip" type="java.lang.String" length="16" not-null="false" unique="false"
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
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="question" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getQuestion() {
		return this.question;
	}
	
	/**
	 * Set the question
	 */	
	public void setQuestion(String aValue) {
		this.question = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="answer" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getAnswer() {
		return this.answer;
	}
	
	/**
	 * Set the answer
	 */	
	public void setAnswer(String aValue) {
		this.answer = aValue;
	}	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}
	
	/**
	 * Set the updateBy
	 * @spring.validator type="required"
	 */	
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TransferRecordTbl)) {
			return false;
		}
		TransferRecordTbl rhs = (TransferRecordTbl) object;
		return new EqualsBuilder()
				.append(this.transferRecordId, rhs.transferRecordId)
				.append(this.type, rhs.type)
				.append(this.orderNo, rhs.orderNo)
				.append(this.mtcnNo, rhs.mtcnNo)
				.append(this.firstname, rhs.firstname)
				.append(this.middlename, rhs.middlename)
				.append(this.lastname, rhs.lastname)
				.append(this.address, rhs.address)
				.append(this.country, rhs.country)
				.append(this.state, rhs.state)
				.append(this.city, rhs.city)
				.append(this.zip, rhs.zip)
				.append(this.phone, rhs.phone)
				.append(this.question, rhs.question)
				.append(this.answer, rhs.answer)
				.append(this.status, rhs.status)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updateBy, rhs.updateBy)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.transferRecordId) 
				.append(this.type) 
				.append(this.orderNo) 
				.append(this.mtcnNo) 
				.append(this.firstname) 
				.append(this.middlename) 
				.append(this.lastname) 
				.append(this.address) 
				.append(this.country) 
				.append(this.state) 
				.append(this.city) 
				.append(this.zip) 
				.append(this.phone) 
				.append(this.question) 
				.append(this.answer) 
				.append(this.status) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.updateBy) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("transferRecordId", this.transferRecordId) 
				.append("type", this.type) 
				.append("orderNo", this.orderNo) 
				.append("mtcnNo", this.mtcnNo) 
				.append("firstname", this.firstname) 
				.append("middlename", this.middlename) 
				.append("lastname", this.lastname) 
				.append("address", this.address) 
				.append("country", this.country) 
				.append("state", this.state) 
				.append("city", this.city) 
				.append("zip", this.zip) 
				.append("phone", this.phone) 
				.append("question", this.question) 
				.append("answer", this.answer) 
				.append("status", this.status) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("updateBy", this.updateBy) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "transferRecordId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return transferRecordId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

}
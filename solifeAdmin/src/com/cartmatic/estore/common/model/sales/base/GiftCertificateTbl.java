package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.GiftCertificate;

/**
 * GiftCertificate Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 */
public class GiftCertificateTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

	private static final long	serialVersionUID	= 2371016977397750099L;
	protected Integer giftCertificateId;
	protected Integer customerId;
	protected String giftCertificateNo;
	protected String purchaser;
	protected String recipient;
	protected Short isSentByEmail;
	protected String recipientEmail;
	protected String recipientFullname;
	protected String recipientAddress;
	protected String recipientZip;
	protected String recipientPhone;
	protected String message;
	protected java.math.BigDecimal giftCertAmt;
	protected java.math.BigDecimal remainedAmt;
	protected java.math.BigDecimal m1Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m2Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m3Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m4Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m5Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m6Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m7Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m8Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m9Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m10Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m11Amt = BigDecimal.ZERO;
	protected java.math.BigDecimal m12Amt = BigDecimal.ZERO;
	protected java.util.Date registerTime;
	protected java.util.Date expireTime;
	protected Short status;
	protected Short giftType = new Short("0");
	protected java.util.Date createTime;
	protected Integer version;
	protected Integer recipientContryId;
	protected String recipientCity;
	protected String recipientState;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date updateTime;
	protected String orderNo;


	/**
	 * Default Empty Constructor for class GiftCertificate
	 */
	public GiftCertificateTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GiftCertificate
	 */
	public GiftCertificateTbl (
		 Integer in_giftCertificateId
        ) {
		this.setGiftCertificateId(in_giftCertificateId);
    }


	
	/**
	 * 
	 * @return Integer
     * @hibernate.id column="giftCertificateId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getGiftCertificateId() {
		return this.giftCertificateId;
	}
	
	/**
	 * Set the giftCertificateId
	 */	
	public void setGiftCertificateId(Integer aValue) {
		this.giftCertificateId = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="giftCertificateNo" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getGiftCertificateNo() {
		return this.giftCertificateNo;
	}
	
	/**
	 * Set the giftCertificateNo
	 * @spring.validator type="required"
	 */	
	public void setGiftCertificateNo(String aValue) {
		this.giftCertificateNo = aValue;
	}	

	/**
	 * OYYDDDNNNN
            1 bit is O
            2-6 bit is date string ,for example 06220
            7-10 bit is increased nunber,from 0001 to 9999
	 * @return String
	 * @hibernate.property column="orderNo" type="java.lang.String" length="12" not-null="true" unique="false"
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
	 * 
	 * @return String
	 * @hibernate.property column="purchaser" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getPurchaser() {
		return this.purchaser;
	}
	
	/**
	 * Set the purchaser
	 * @spring.validator type="required"
	 */	
	public void setPurchaser(String aValue) {
		this.purchaser = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipient" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getRecipient() {
		return this.recipient;
	}
	
	/**
	 * Set the recipient
	 * @spring.validator type="required"
	 */	
	public void setRecipient(String aValue) {
		this.recipient = aValue;
	}	

	/**
	 * whether gift Certificate should be sent by email
            0=No
            1=Yes
	 * @return Short
	 * @hibernate.property column="isSentByEmail" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getIsSentByEmail() {
		return this.isSentByEmail;
	}
	
	/**
	 * Set the isSentByEmail
	 * @spring.validator type="required"
	 */	
	public void setIsSentByEmail(Short aValue) {
		this.isSentByEmail = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientEmail" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecipientEmail() {
		return this.recipientEmail;
	}
	
	/**
	 * Set the recipientEmail
	 */	
	public void setRecipientEmail(String aValue) {
		this.recipientEmail = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientFirstname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecipientFullname() {
		return this.recipientFullname;
	}
	
	/**
	 * Set the recipientFirstname
	 */	
	public void setRecipientFullname(String aValue) {
		this.recipientFullname = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientAddress" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getRecipientAddress() {
		return this.recipientAddress;
	}
	
	/**
	 * Set the recipientAddress
	 */	
	public void setRecipientAddress(String aValue) {
		this.recipientAddress = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientZip" type="java.lang.String" length="6" not-null="false" unique="false"
	 */
	public String getRecipientZip() {
		return this.recipientZip;
	}
	
	/**
	 * Set the recipientZip
	 */	
	public void setRecipientZip(String aValue) {
		this.recipientZip = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientPhone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecipientPhone() {
		return this.recipientPhone;
	}
	
	/**
	 * Set the recipientPhone
	 */	
	public void setRecipientPhone(String aValue) {
		this.recipientPhone = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="message" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message
	 */	
	public void setMessage(String aValue) {
		this.message = aValue;
	}	

	/**
	 * gift certificate total amount
	 * @return java.math.BigDecimal
	 * @hibernate.property column="giftCertAmt" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getGiftCertAmt() {
		return this.giftCertAmt;
	}
	
	/**
	 * Set the giftCertAmt
	 * @spring.validator type="required"
	 */	
	public void setGiftCertAmt(java.math.BigDecimal aValue) {
		this.giftCertAmt = aValue;
	}	

	/**
	 * remained gift certificate amount
	 * @return java.math.BigDecimal
	 * @hibernate.property column="remainedAmt" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRemainedAmt() {
		return this.remainedAmt;
	}
	
	/**
	 * Set the remainedAmt
	 */	
	public void setRemainedAmt(java.math.BigDecimal aValue) {
		this.remainedAmt = aValue;
	}	

	/**
	 * expire Time
	 * @return java.util.Date
	 * @hibernate.property column="expireTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getExpireTime() {
		return this.expireTime;
	}
	
	/**
	 * Set the expireTime
	 */	
	public void setExpireTime(java.util.Date aValue) {
		this.expireTime = aValue;
	}	

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="6" not-null="true" unique="false"
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
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
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
	 * 
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
	 * 
	 * @return Integer
	 * @hibernate.property column="recipientContryId" type="java.lang.Integer" length="11" not-null="true" unique="false"
	 */
	public Integer getRecipientContryId() {
		return this.recipientContryId;
	}
	
	/**
	 * Set the recipientContryId
	 * @spring.validator type="required"
	 */	
	public void setRecipientContryId(Integer aValue) {
		this.recipientContryId = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientCity" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecipientCity() {
		return this.recipientCity;
	}
	
	/**
	 * Set the recipientCity
	 */	
	public void setRecipientCity(String aValue) {
		this.recipientCity = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="recipientState" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecipientState() {
		return this.recipientState;
	}
	
	/**
	 * Set the recipientState
	 */	
	public void setRecipientState(String aValue) {
		this.recipientState = aValue;
	}	

	/**
	 * 
	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="11" not-null="false" unique="false"
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
	 * 
	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="11" not-null="false" unique="false"
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
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="19" not-null="false" unique="false"
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
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public java.math.BigDecimal getM1Amt() {
		return m1Amt;
	}

	public void setM1Amt(java.math.BigDecimal m1Amt) {
		this.m1Amt = m1Amt;
	}

	public java.math.BigDecimal getM2Amt() {
		return m2Amt;
	}

	public void setM2Amt(java.math.BigDecimal m2Amt) {
		this.m2Amt = m2Amt;
	}

	public java.math.BigDecimal getM3Amt() {
		return m3Amt;
	}

	public void setM3Amt(java.math.BigDecimal m3Amt) {
		this.m3Amt = m3Amt;
	}

	public java.math.BigDecimal getM4Amt() {
		return m4Amt;
	}

	public void setM4Amt(java.math.BigDecimal m4Amt) {
		this.m4Amt = m4Amt;
	}

	public java.math.BigDecimal getM5Amt() {
		return m5Amt;
	}

	public void setM5Amt(java.math.BigDecimal m5Amt) {
		this.m5Amt = m5Amt;
	}

	public java.math.BigDecimal getM6Amt() {
		return m6Amt;
	}

	public void setM6Amt(java.math.BigDecimal m6Amt) {
		this.m6Amt = m6Amt;
	}

	public java.math.BigDecimal getM7Amt() {
		return m7Amt;
	}

	public void setM7Amt(java.math.BigDecimal m7Amt) {
		this.m7Amt = m7Amt;
	}

	public java.math.BigDecimal getM8Amt() {
		return m8Amt;
	}

	public void setM8Amt(java.math.BigDecimal m8Amt) {
		this.m8Amt = m8Amt;
	}

	public java.math.BigDecimal getM9Amt() {
		return m9Amt;
	}

	public void setM9Amt(java.math.BigDecimal m9Amt) {
		this.m9Amt = m9Amt;
	}

	public java.math.BigDecimal getM10Amt() {
		return m10Amt;
	}

	public void setM10Amt(java.math.BigDecimal m10Amt) {
		this.m10Amt = m10Amt;
	}

	public java.math.BigDecimal getM11Amt() {
		return m11Amt;
	}

	public void setM11Amt(java.math.BigDecimal m11Amt) {
		this.m11Amt = m11Amt;
	}

	public java.math.BigDecimal getM12Amt() {
		return m12Amt;
	}

	public void setM12Amt(java.math.BigDecimal m12Amt) {
		this.m12Amt = m12Amt;
	}

	public java.util.Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}

	public Short getGiftType() {
		return giftType;
	}

	public void setGiftType(Short giftType) {
		this.giftType = giftType;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GiftCertificate)) {
			return false;
		}
		GiftCertificate rhs = (GiftCertificate) object;
		return new EqualsBuilder()
				.append(this.giftCertificateId, rhs.giftCertificateId)
				.append(this.giftCertificateNo, rhs.giftCertificateNo)
				.append(this.purchaser, rhs.purchaser)
				.append(this.recipient, rhs.recipient)
				.append(this.isSentByEmail, rhs.isSentByEmail)
				.append(this.recipientEmail, rhs.recipientEmail)
				.append(this.recipientFullname, rhs.recipientFullname)
				.append(this.recipientAddress, rhs.recipientAddress)
				.append(this.recipientZip, rhs.recipientZip)
				.append(this.recipientPhone, rhs.recipientPhone)
				.append(this.message, rhs.message)
				.append(this.giftCertAmt, rhs.giftCertAmt)
				.append(this.remainedAmt, rhs.remainedAmt)
				.append(this.expireTime, rhs.expireTime)
				.append(this.status, rhs.status)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.append(this.recipientContryId, rhs.recipientContryId)
				.append(this.recipientCity, rhs.recipientCity)
				.append(this.recipientState, rhs.recipientState)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.giftCertificateId) 
				.append(this.giftCertificateNo) 
				.append(this.purchaser) 
				.append(this.recipient) 
				.append(this.isSentByEmail) 
				.append(this.recipientEmail) 
				.append(this.recipientFullname) 
				.append(this.recipientAddress) 
				.append(this.recipientZip) 
				.append(this.recipientPhone) 
				.append(this.message) 
				.append(this.giftCertAmt) 
				.append(this.remainedAmt) 
				.append(this.expireTime) 
				.append(this.status) 
				.append(this.createTime) 
				.append(this.version) 
				.append(this.recipientContryId) 
				.append(this.recipientCity) 
				.append(this.recipientState) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("giftCertificateId", this.giftCertificateId) 
				.append("giftCertificateNo", this.giftCertificateNo) 
				.append("purchaser", this.purchaser) 
				.append("recipient", this.recipient) 
				.append("isSentByEmail", this.isSentByEmail) 
				.append("recipientEmail", this.recipientEmail) 
				.append("recipientFullname", this.recipientFullname) 
				.append("recipientAddress", this.recipientAddress) 
				.append("recipientZip", this.recipientZip) 
				.append("recipientPhone", this.recipientPhone) 
				.append("message", this.message) 
				.append("giftCertAmt", this.giftCertAmt) 
				.append("remainedAmt", this.remainedAmt) 
				.append("expireTime", this.expireTime) 
				.append("status", this.status) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.append("recipientContryId", this.recipientContryId) 
				.append("recipientCity", this.recipientCity) 
				.append("recipientState", this.recipientState) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("updateTime", this.updateTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "giftCertificateId";
	}
	
}
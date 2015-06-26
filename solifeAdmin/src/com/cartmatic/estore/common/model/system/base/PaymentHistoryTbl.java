package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PaymentHistory Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PaymentHistoryTbl extends BaseObject implements Serializable {

    protected Integer paymentHistoryId;
	protected String orderNo;
	protected Short flag;
	protected String flowNo;
	protected java.math.BigDecimal amount;
	protected String errorMessage;
	protected String errorCode;
	protected String remoteIp;
	protected String receiveData;
	protected Integer paymentMethodId;
	protected Short isBrowsed;
	protected java.util.Date createTime;
	protected Integer version;


	/**
	 * Default Empty Constructor for class PaymentHistory
	 */
	public PaymentHistoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PaymentHistory
	 */
	public PaymentHistoryTbl (
		 Integer in_paymentHistoryId
        ) {
		this.setPaymentHistoryId(in_paymentHistoryId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="paymentHistoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPaymentHistoryId() {
		return this.paymentHistoryId;
	}
	
	/**
	 * Set the paymentHistoryId
	 */	
	public void setPaymentHistoryId(Integer aValue) {
		this.paymentHistoryId = aValue;
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
	 * 	 * @return Short
	 * @hibernate.property column="flag" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getFlag() {
		return this.flag;
	}
	
	/**
	 * Set the flag
	 * @spring.validator type="required"
	 */	
	public void setFlag(Short aValue) {
		this.flag = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="flowNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getFlowNo() {
		return this.flowNo;
	}
	
	/**
	 * Set the flowNo
	 */	
	public void setFlowNo(String aValue) {
		this.flowNo = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="amount" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 */	
	public void setAmount(java.math.BigDecimal aValue) {
		this.amount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="errorMessage" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * Set the errorMessage
	 */	
	public void setErrorMessage(String aValue) {
		this.errorMessage = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="errorCode" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getErrorCode() {
		return this.errorCode;
	}
	
	/**
	 * Set the errorCode
	 */	
	public void setErrorCode(String aValue) {
		this.errorCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remoteIp" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRemoteIp() {
		return this.remoteIp;
	}
	
	/**
	 * Set the remoteIp
	 */	
	public void setRemoteIp(String aValue) {
		this.remoteIp = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="receiveData" type="java.lang.String" length="2048" not-null="false" unique="false"
	 */
	public String getReceiveData() {
		return this.receiveData;
	}
	
	/**
	 * Set the receiveData
	 */	
	public void setReceiveData(String aValue) {
		this.receiveData = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="paymentMethodId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPaymentMethodId() {
		return this.paymentMethodId;
	}
	
	/**
	 * Set the paymentMethodId
	 */	
	public void setPaymentMethodId(Integer aValue) {
		this.paymentMethodId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isBrowsed" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsBrowsed() {
		return this.isBrowsed;
	}
	
	/**
	 * Set the isBrowsed
	 */	
	public void setIsBrowsed(Short aValue) {
		this.isBrowsed = aValue;
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
		if (!(object instanceof PaymentHistoryTbl)) {
			return false;
		}
		PaymentHistoryTbl rhs = (PaymentHistoryTbl) object;
		return new EqualsBuilder()
				.append(this.paymentHistoryId, rhs.paymentHistoryId)
				.append(this.orderNo, rhs.orderNo)
				.append(this.flag, rhs.flag)
				.append(this.flowNo, rhs.flowNo)
				.append(this.amount, rhs.amount)
				.append(this.errorMessage, rhs.errorMessage)
				.append(this.errorCode, rhs.errorCode)
				.append(this.remoteIp, rhs.remoteIp)
				.append(this.receiveData, rhs.receiveData)
				.append(this.paymentMethodId, rhs.paymentMethodId)
				.append(this.isBrowsed, rhs.isBrowsed)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.paymentHistoryId) 
				.append(this.orderNo) 
				.append(this.flag) 
				.append(this.flowNo) 
				.append(this.amount) 
				.append(this.errorMessage) 
				.append(this.errorCode) 
				.append(this.remoteIp) 
				.append(this.receiveData) 
				.append(this.paymentMethodId) 
				.append(this.isBrowsed) 
				.append(this.createTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("paymentHistoryId", this.paymentHistoryId) 
				.append("orderNo", this.orderNo) 
				.append("flag", this.flag) 
				.append("flowNo", this.flowNo) 
				.append("amount", this.amount) 
				.append("errorMessage", this.errorMessage) 
				.append("errorCode", this.errorCode) 
				.append("remoteIp", this.remoteIp) 
				.append("receiveData", this.receiveData) 
				.append("paymentMethodId", this.paymentMethodId) 
				.append("isBrowsed", this.isBrowsed) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "paymentHistoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return paymentHistoryId;
	}

}
package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderPayment Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderPaymentTbl extends BaseObject implements Serializable {

    protected Integer orderPaymentId;
	protected java.math.BigDecimal paymentAmount;
	protected java.math.BigDecimal balance;
	protected Short transactionType;
	protected String paymentGatewayName;
	protected String ipAddress;
	protected String giftCertificateNo;
	protected String addedBy;
	protected java.util.Date createTime;
	protected Integer version;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;


	/**
	 * Default Empty Constructor for class OrderPayment
	 */
	public OrderPaymentTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPayment
	 */
	public OrderPaymentTbl (
		 Integer in_orderPaymentId
        ) {
		this.setOrderPaymentId(in_orderPaymentId);
    }

	
	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderPaymentId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderPaymentId() {
		return this.orderPaymentId;
	}
	
	/**
	 * Set the orderPaymentId
	 */	
	public void setOrderPaymentId(Integer aValue) {
		this.orderPaymentId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSalesOrderId() {
		return this.getSalesOrder()==null?null:this.getSalesOrder().getSalesOrderId();
	}
	
	/**
	 * Set the salesOrderId
	 */	
	public void setSalesOrderId(Integer aValue) {
	    if (aValue==null) {
	    	salesOrder = null;
	    } else {
	        salesOrder = new com.cartmatic.estore.common.model.order.SalesOrder(aValue);
	        salesOrder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="paymentAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPaymentAmount() {
		return this.paymentAmount;
	}
	
	/**
	 * Set the paymentAmount
	 * @spring.validator type="required"
	 */	
	public void setPaymentAmount(java.math.BigDecimal aValue) {
		this.paymentAmount = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="balance" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getBalance() {
		return this.balance;
	}
	
	/**
	 * Set the balance
	 * @spring.validator type="required"
	 */	
	public void setBalance(java.math.BigDecimal aValue) {
		this.balance = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="transactionType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getTransactionType() {
		return this.transactionType;
	}
	
	/**
	 * Set the transactionType
	 * @spring.validator type="required"
	 */	
	public void setTransactionType(Short aValue) {
		this.transactionType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="paymentGatewayName" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getPaymentGatewayName() {
		return this.paymentGatewayName;
	}
	
	/**
	 * Set the paymentGatewayName
	 */	
	public void setPaymentGatewayName(String aValue) {
		this.paymentGatewayName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="ipAddress" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getIpAddress() {
		return this.ipAddress;
	}
	
	/**
	 * Set the ipAddress
	 */	
	public void setIpAddress(String aValue) {
		this.ipAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="giftCertificateNo" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getGiftCertificateNo() {
		return this.giftCertificateNo;
	}
	
	/**
	 * Set the giftCertificateNo
	 */	
	public void setGiftCertificateNo(String aValue) {
		this.giftCertificateNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="addedBy" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getAddedBy() {
		return this.addedBy;
	}
	
	/**
	 * Set the addedBy
	 * @spring.validator type="required"
	 */	
	public void setAddedBy(String aValue) {
		this.addedBy = aValue;
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
		if (!(object instanceof OrderPaymentTbl)) {
			return false;
		}
		OrderPaymentTbl rhs = (OrderPaymentTbl) object;
		return new EqualsBuilder()
				.append(this.orderPaymentId, rhs.orderPaymentId)
						.append(this.paymentAmount, rhs.paymentAmount)
				.append(this.balance, rhs.balance)
				.append(this.transactionType, rhs.transactionType)
				.append(this.paymentGatewayName, rhs.paymentGatewayName)
				.append(this.ipAddress, rhs.ipAddress)
				.append(this.giftCertificateNo, rhs.giftCertificateNo)
				.append(this.addedBy, rhs.addedBy)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderPaymentId) 
						.append(this.paymentAmount) 
				.append(this.balance) 
				.append(this.transactionType) 
				.append(this.paymentGatewayName) 
				.append(this.ipAddress) 
				.append(this.giftCertificateNo) 
				.append(this.addedBy) 
				.append(this.createTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderPaymentId", this.orderPaymentId) 
						.append("paymentAmount", this.paymentAmount) 
				.append("balance", this.balance) 
				.append("transactionType", this.transactionType) 
				.append("paymentGatewayName", this.paymentGatewayName) 
				.append("ipAddress", this.ipAddress) 
				.append("giftCertificateNo", this.giftCertificateNo) 
				.append("addedBy", this.addedBy) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderPaymentId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderPaymentId;
	}

}
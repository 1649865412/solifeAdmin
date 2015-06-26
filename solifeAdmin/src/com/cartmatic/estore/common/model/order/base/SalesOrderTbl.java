package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SalesOrder Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SalesOrderTbl extends BaseObject implements Serializable {

    protected Integer salesOrderId;
	protected Integer customerId;
	protected String orderNo;
	protected Integer membershipId;
	protected String customerFirstname;
	protected String customerLastname;
	protected String customerTitle;
	protected String customerEmail;
	protected Integer shopPoint;
	protected java.math.BigDecimal totalAmount;
	protected java.math.BigDecimal paidAmount;
	protected Short orderStatus;
	protected Short paymentStatus;
	protected Short isExchangeOrder;
	protected Short isCod;
	protected Integer paymentMethodId;
	protected String invoiceTitle;
	protected Short hasInvoice;
	protected Short hasProblem = Short.valueOf("0");
	protected Integer gainedPoint;
	protected Integer gainedCouponTypeId;
	protected String ipAddress;
	protected Short isOnHold;
	protected Short isHoldByCustomer;
	protected Short isLocked;
	protected Integer lockedBy;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer updateBy;
	protected Integer version;
	
	protected Integer isSub = 0; //
	/**
	 * 发票地址
	 */
	protected com.cartmatic.estore.common.model.order.OrderAddress orderAddress;
	protected com.cartmatic.estore.common.model.customer.Customer customer;
	
	protected Store store;

	protected java.util.Set orderAudits = new java.util.HashSet();
	protected java.util.Set orderPayments = new java.util.HashSet();
	protected java.util.Set orderPromotions = new java.util.HashSet();
	protected java.util.Set orderReturns = new java.util.HashSet();
	protected java.util.Set orderShipments = new java.util.HashSet();
	protected java.util.Set orderMessages = new java.util.HashSet();
	
	/**
	 * Default Empty Constructor for class SalesOrder
	 */
	public SalesOrderTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SalesOrder
	 */
	public SalesOrderTbl (
		 Integer in_salesOrderId
        ) {
		this.setSalesOrderId(in_salesOrderId);
    }

	
	public com.cartmatic.estore.common.model.order.OrderAddress getOrderAddress () {
		return orderAddress;
	}	
	
	public void setOrderAddress (com.cartmatic.estore.common.model.order.OrderAddress in_orderAddress) {
		this.orderAddress = in_orderAddress;
	}

	public java.util.Set getOrderAudits () {
		return orderAudits;
	}	
	
	public void setOrderAudits (java.util.Set in_orderAudits) {
		this.orderAudits = in_orderAudits;
	}

	public java.util.Set getOrderPayments () {
		return orderPayments;
	}	
	
	public void setOrderPayments (java.util.Set in_orderPayments) {
		this.orderPayments = in_orderPayments;
	}

	public java.util.Set getOrderPromotions () {
		return orderPromotions;
	}	
	
	public void setOrderPromotions (java.util.Set in_orderPromotions) {
		this.orderPromotions = in_orderPromotions;
	}

	public java.util.Set getOrderReturns () {
		return orderReturns;
	}	
	
	public void setOrderReturns (java.util.Set in_orderReturns) {
		this.orderReturns = in_orderReturns;
	}

	public java.util.Set getOrderShipments () {
		return orderShipments;
	}	
	
	public void setOrderShipments (java.util.Set in_orderShipments) {
		this.orderShipments = in_orderShipments;
	}
	
	public java.util.Set getOrderMessages () {
		return orderMessages;
	}	
	
	public void setOrderMessages (java.util.Set avalue) {
		this.orderMessages = avalue;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="salesOrderId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSalesOrderId() {
		return this.salesOrderId;
	}
	
	/**
	 * Set the salesOrderId
	 */	
	public void setSalesOrderId(Integer aValue) {
		this.salesOrderId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getBillingAddressId() {
		return this.getOrderAddress()==null?null:this.getOrderAddress().getOrderAddressId();
	}
	
	/**
	 * Set the billingAddressId
	 */	
	public void setBillingAddressId(Integer aValue) {
	    if (aValue==null) {
	    	orderAddress = null;
	    } else {
	        orderAddress = new com.cartmatic.estore.common.model.order.OrderAddress(aValue);
	        orderAddress.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="customerId" type="java.lang.Integer" length="10" not-null="false" unique="false"
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
	    	customer = new com.cartmatic.estore.common.model.customer.Customer(aValue);
	    	customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	public com.cartmatic.estore.common.model.customer.Customer getCustomer() {
		return customer;
	}

	public void setCustomer(
			com.cartmatic.estore.common.model.customer.Customer customer) {
		this.customer = customer;
	}

	/**
	 * OYYDDDNNNN
            1 bit is O
            2-6 bit is date string ,for example 06220
            7-10 bit is increased number,from 0001 to 9999
             
            	 * @return String
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
	 * 	 * @return Integer
	 * @hibernate.property column="membershipId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMembershipId() {
		return this.membershipId;
	}
	
	/**
	 * Set the membershipId
	 */	
	public void setMembershipId(Integer aValue) {
		this.membershipId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerFirstname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCustomerFirstname() {
		return this.customerFirstname;
	}
	
	/**
	 * Set the customerFirstname
	 * @spring.validator type="required"
	 */	
	public void setCustomerFirstname(String aValue) {
		this.customerFirstname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerLastname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCustomerLastname() {
		return this.customerLastname;
	}
	
	/**
	 * Set the customerLastname
	 */	
	public void setCustomerLastname(String aValue) {
		this.customerLastname = aValue;
	}
	
	public String getCustomerTitle() {
		return customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerEmail" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCustomerEmail() {
		return this.customerEmail;
	}
	
	/**
	 * Set the customerEmail
	 * @spring.validator type="required"
	 */	
	public void setCustomerEmail(String aValue) {
		this.customerEmail = aValue;
	}	

	/**
	 * 购物时使用的积分	 * @return Integer
	 * @hibernate.property column="shopPoint" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getShopPoint() {
		return this.shopPoint;
	}
	
	/**
	 * Set the shopPoint
	 */	
	public void setShopPoint(Integer aValue) {
		this.shopPoint = aValue;
	}	

	/**
	 * total=subtotal+wrapTotalCost+shippingTotalCost+taxAmt
            	 * @return java.math.BigDecimal
	 * @hibernate.property column="totalAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getTotalAmount() {
		return this.totalAmount;
	}
	
	/**
	 * Set the totalAmount
	 * @spring.validator type="required"
	 */	
	public void setTotalAmount(java.math.BigDecimal aValue) {
		this.totalAmount = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="paidAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPaidAmount() {
		return this.paidAmount;
	}
	
	/**
	 * Set the paidAmount
	 * @spring.validator type="required"
	 */	
	public void setPaidAmount(java.math.BigDecimal aValue) {
		this.paidAmount = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="orderStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getOrderStatus() {
		return this.orderStatus;
	}
	
	/**
	 * Set the orderStatus
	 * @spring.validator type="required"
	 */	
	public void setOrderStatus(Short aValue) {
		this.orderStatus = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="paymentStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getPaymentStatus() {
		return this.paymentStatus;
	}
	
	/**
	 * Set the paymentStatus
	 * @spring.validator type="required"
	 */	
	public void setPaymentStatus(Short aValue) {
		this.paymentStatus = aValue;
	}	

	/**
	 * 是否换货订单	 * @return Short
	 * @hibernate.property column="isExchangeOrder" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsExchangeOrder() {
		return this.isExchangeOrder;
	}
	
	/**
	 * Set the isExchangeOrder
	 */	
	public void setIsExchangeOrder(Short aValue) {
		this.isExchangeOrder = aValue;
	}

	/**
	 * is Cash on Delivery	 * @return Short
	 * @hibernate.property column="isCod" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsCod() {
		return this.isCod;
	}
	
	/**
	 * Set the isCod
	 * @spring.validator type="required"
	 */	
	public void setIsCod(Short aValue) {
		this.isCod = aValue;
	}	

	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	/**
	 * 发票抬头
            
             
            	 * @return String
	 * @hibernate.property column="invoiceTitle" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}
	
	/**
	 * Set the invoiceTitle
	 */	
	public void setInvoiceTitle(String aValue) {
		this.invoiceTitle = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="hasInvoice" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getHasInvoice() {
		return this.hasInvoice;
	}
	
	/**
	 * Set the hasInvoice
	 * @spring.validator type="required"
	 */	
	public void setHasInvoice(Short aValue) {
		this.hasInvoice = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="gainedPoint" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getGainedPoint() {
		return this.gainedPoint;
	}
	
	/**
	 * Set the gainedPoint
	 */	
	public void setGainedPoint(Integer aValue) {
		this.gainedPoint = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="gainedCouponTypeId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getGainedCouponTypeId() {
		return this.gainedCouponTypeId;
	}
	
	/**
	 * Set the gainedCouponTypeId
	 */	
	public void setGainedCouponTypeId(Integer aValue) {
		this.gainedCouponTypeId = aValue;
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
	 * 是否被hold住.	 * @return Short
	 * @hibernate.property column="isOnHold" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsOnHold() {
		return this.isOnHold;
	}
	
	/**
	 * Set the isOnHold
	 * @spring.validator type="required"
	 */	
	public void setIsOnHold(Short aValue) {
		this.isOnHold = aValue;
	}	

	/**
	 * 申请取消情况下	 * @return Short
	 * @hibernate.property column="isHoldByCustomer" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsHoldByCustomer() {
		if(isHoldByCustomer==null)
			return Constants.FLAG_FALSE;
		return this.isHoldByCustomer;
	}
	
	/**
	 * Set the isHoldByCustomer
	 */	
	public void setIsHoldByCustomer(Short aValue) {
		this.isHoldByCustomer = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isLocked" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsLocked() {
		return this.isLocked;
	}
	
	/**
	 * Set the isLocked
	 * @spring.validator type="required"
	 */	
	public void setIsLocked(Short aValue) {
		this.isLocked = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="lockedBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLockedBy() {
		return this.lockedBy;
	}
	
	/**
	 * Set the lockedBy
	 */	
	public void setLockedBy(Integer aValue) {
		this.lockedBy = aValue;
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
		if (!(object instanceof SalesOrderTbl)) {
			return false;
		}
		SalesOrderTbl rhs = (SalesOrderTbl) object;
		return new EqualsBuilder()
				.append(this.salesOrderId, rhs.salesOrderId)
						.append(this.customerId, rhs.customerId)
				.append(this.orderNo, rhs.orderNo)
				.append(this.membershipId, rhs.membershipId)
				.append(this.customerFirstname, rhs.customerFirstname)
				.append(this.customerLastname, rhs.customerLastname)
				.append(this.customerEmail, rhs.customerEmail)
				.append(this.shopPoint, rhs.shopPoint)
				.append(this.totalAmount, rhs.totalAmount)
				.append(this.paidAmount, rhs.paidAmount)
				.append(this.orderStatus, rhs.orderStatus)
				.append(this.paymentStatus, rhs.paymentStatus)
				.append(this.isExchangeOrder, rhs.isExchangeOrder)
				.append(this.isCod, rhs.isCod)
				.append(this.paymentMethodId, rhs.paymentMethodId)
				.append(this.invoiceTitle, rhs.invoiceTitle)
				.append(this.hasInvoice, rhs.hasInvoice)
				.append(this.gainedPoint, rhs.gainedPoint)
				.append(this.gainedCouponTypeId, rhs.gainedCouponTypeId)
				.append(this.ipAddress, rhs.ipAddress)
				.append(this.isOnHold, rhs.isOnHold)
				.append(this.isHoldByCustomer, rhs.isHoldByCustomer)
				.append(this.isLocked, rhs.isLocked)
				.append(this.lockedBy, rhs.lockedBy)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.updateBy, rhs.updateBy)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.salesOrderId) 
						.append(this.customerId) 
				.append(this.orderNo) 
				.append(this.membershipId) 
				.append(this.customerFirstname) 
				.append(this.customerLastname) 
				.append(this.customerEmail) 
				.append(this.shopPoint) 
				.append(this.totalAmount) 
				.append(this.paidAmount) 
				.append(this.orderStatus) 
				.append(this.paymentStatus) 
				.append(this.isExchangeOrder) 
				.append(this.isCod) 
				.append(this.paymentMethodId)
				.append(this.invoiceTitle) 
				.append(this.hasInvoice) 
				.append(this.gainedPoint) 
				.append(this.gainedCouponTypeId) 
				.append(this.ipAddress) 
				.append(this.isOnHold) 
				.append(this.isHoldByCustomer) 
				.append(this.isLocked) 
				.append(this.lockedBy) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.updateBy) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("salesOrderId", this.salesOrderId) 
						.append("customerId", this.customerId) 
				.append("orderNo", this.orderNo) 
				.append("membershipId", this.membershipId) 
				.append("customerFirstname", this.customerFirstname) 
				.append("customerLastname", this.customerLastname) 
				.append("customerEmail", this.customerEmail) 
				.append("shopPoint", this.shopPoint) 
				.append("totalAmount", this.totalAmount) 
				.append("paidAmount", this.paidAmount) 
				.append("orderStatus", this.orderStatus) 
				.append("paymentStatus", this.paymentStatus) 
				.append("isExchangeOrder", this.isExchangeOrder) 
				.append("isCod", this.isCod) 
				.append("paymentMethodId", this.paymentMethodId) 
				.append("invoiceTitle", this.invoiceTitle) 
				.append("hasInvoice", this.hasInvoice) 
				.append("gainedPoint", this.gainedPoint) 
				.append("gainedCouponTypeId", this.gainedCouponTypeId) 
				.append("ipAddress", this.ipAddress) 
				.append("isOnHold", this.isOnHold) 
				.append("isHoldByCustomer", this.isHoldByCustomer) 
				.append("isLocked", this.isLocked) 
				.append("lockedBy", this.lockedBy) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("updateBy", this.updateBy) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "salesOrderId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return salesOrderId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public Short getHasProblem() {
		return hasProblem;
	}

	public void setHasProblem(Short hasProblem) {
		this.hasProblem = hasProblem;
	}

	public Integer getIsSub() {
		return isSub;
	}

	public void setIsSub(Integer isSub) {
		this.isSub = isSub;
	}
	
	
}
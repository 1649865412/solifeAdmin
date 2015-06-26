package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.core.model.BaseObject;

/**
 * OrderReturn Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderReturnTbl extends BaseObject implements Serializable {

    protected Integer orderReturnId;
	protected String rmaNo;
	protected Integer exchangeOrderId;
	protected Short returnType;
	protected Short status;
	protected Short isPhysicalReturn;
	protected java.math.BigDecimal itemSubTotal;
	protected java.math.BigDecimal itemTax;
	protected java.math.BigDecimal shippingTax;
	protected java.math.BigDecimal shippingCost;
	protected java.math.BigDecimal discountAmount;
	protected java.math.BigDecimal lessRestockAmount;
	protected String note;
	protected java.util.Date createTime;
	protected Integer createBy;
	protected Integer version;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;
	protected com.cartmatic.estore.common.model.system.AppUser appUser;

	protected java.util.Set orderReturnSkus = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OrderReturn
	 */
	public OrderReturnTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderReturn
	 */
	public OrderReturnTbl (
		 Integer in_orderReturnId
        ) {
		this.setOrderReturnId(in_orderReturnId);
    }

	
	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
	}
	
	public com.cartmatic.estore.common.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.cartmatic.estore.common.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	public java.util.Set getOrderReturnSkus () {
		return orderReturnSkus;
	}	
	
	public void setOrderReturnSkus (java.util.Set in_orderReturnSkus) {
		this.orderReturnSkus = in_orderReturnSkus;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderReturnId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderReturnId() {
		return this.orderReturnId;
	}
	
	/**
	 * Set the orderReturnId
	 */	
	public void setOrderReturnId(Integer aValue) {
		this.orderReturnId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="rmaNo" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getRmaNo() {
		return this.rmaNo;
	}
	
	/**
	 * Set the rmaNo
	 * @spring.validator type="required"
	 */	
	public void setRmaNo(String aValue) {
		this.rmaNo = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="exchangeOrderId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getExchangeOrderId() {
		return this.exchangeOrderId;
	}
	
	/**
	 * Set the exchangeOrderId
	 */	
	public void setExchangeOrderId(Integer aValue) {
		this.exchangeOrderId = aValue;
	}	

	/**
	 * 收货人	 * @return Integer
	 */
	public Integer getReceivedBy() {
		return this.getAppUser()==null?null:this.getAppUser().getAppuserId();
	}
	
	/**
	 * Set the receivedBy
	 */	
	public void setReceivedBy(Integer aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else {
	        appUser = new com.cartmatic.estore.common.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 0 -- RETURN
            1 -- EXCHANGE	 * @return Short
	 * @hibernate.property column="returnType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getReturnType() {
		return this.returnType;
	}
	
	/**
	 * Set the returnType
	 * @spring.validator type="required"
	 */	
	public void setReturnType(Short aValue) {
		this.returnType = aValue;
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
	 * 	 * @return Short
	 * @hibernate.property column="isPhysicalReturn" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsPhysicalReturn() {
		return this.isPhysicalReturn;
	}
	
	/**
	 * Set the isPhysicalReturn
	 * @spring.validator type="required"
	 */	
	public void setIsPhysicalReturn(Short aValue) {
		this.isPhysicalReturn = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="itemSubTotal" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getItemSubTotal() {
		return this.itemSubTotal;
	}
	
	/**
	 * Set the itemSubTotal
	 * @spring.validator type="required"
	 */	
	public void setItemSubTotal(java.math.BigDecimal aValue) {
		this.itemSubTotal = aValue;
	}	
	
	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="itemTax" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getItemTax() {
		return this.itemTax;
	}
	
	/**
	 * Set the itemTax
	 * @spring.validator type="required"
	 */	
	public void setItemTax(java.math.BigDecimal aValue) {
		this.itemTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="shippingTax" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getShippingTax() {
		return this.shippingTax;
	}
	
	/**
	 * Set the shippingTax
	 * @spring.validator type="required"
	 */	
	public void setShippingTax(java.math.BigDecimal aValue) {
		this.shippingTax = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="shippingCost" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getShippingCost() {
		return this.shippingCost;
	}
	
	/**
	 * Set the shippingCost
	 * @spring.validator type="required"
	 */	
	public void setShippingCost(java.math.BigDecimal aValue) {
		this.shippingCost = aValue;
	}
	
	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="discountAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDiscountAmount() {
		return this.discountAmount;
	}
	
	/**
	 * Set the discountAmount
	 * @spring.validator type="required"
	 */	
	public void setDiscountAmount(java.math.BigDecimal aValue) {
		this.discountAmount = aValue;
	}
	
	/**
	 * 退换货手续费	 * @return java.math.BigDecimal
	 * @hibernate.property column="lessRestockAmount" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLessRestockAmount() {
		return this.lessRestockAmount;
	}
	
	/**
	 * Set the lessRestockAmount
	 */	
	public void setLessRestockAmount(java.math.BigDecimal aValue) {
		this.lessRestockAmount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="note" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getNote() {
		return this.note;
	}
	
	/**
	 * Set the note
	 */	
	public void setNote(String aValue) {
		this.note = aValue;
	}	

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createBy
	 * @spring.validator type="required"
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
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
		if (!(object instanceof OrderReturnTbl)) {
			return false;
		}
		OrderReturnTbl rhs = (OrderReturnTbl) object;
		return new EqualsBuilder()
				.append(this.orderReturnId, rhs.orderReturnId)
				.append(this.rmaNo, rhs.rmaNo)
						.append(this.exchangeOrderId, rhs.exchangeOrderId)
						.append(this.returnType, rhs.returnType)
				.append(this.status, rhs.status)
				.append(this.isPhysicalReturn, rhs.isPhysicalReturn)
				.append(this.itemSubTotal, rhs.itemSubTotal)
				.append(this.itemTax, rhs.itemTax)
				.append(this.shippingTax, rhs.shippingTax)
				.append(this.shippingCost, rhs.shippingCost)
				.append(this.discountAmount, rhs.discountAmount)
				.append(this.lessRestockAmount, rhs.lessRestockAmount)
				.append(this.note, rhs.note)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderReturnId) 
				.append(this.rmaNo) 
						.append(this.exchangeOrderId) 
						.append(this.returnType) 
				.append(this.status) 
				.append(this.isPhysicalReturn) 
				.append(this.itemSubTotal) 
				.append(this.itemTax) 
				.append(this.shippingTax) 
				.append(this.shippingCost)
				.append(this.discountAmount)
				.append(this.lessRestockAmount) 
				.append(this.note) 
				.append(this.createTime) 
				.append(this.createBy) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderReturnId", this.orderReturnId) 
				.append("rmaNo", this.rmaNo) 
						.append("exchangeOrderId", this.exchangeOrderId) 
						.append("returnType", this.returnType) 
				.append("status", this.status) 
				.append("isPhysicalReturn", this.isPhysicalReturn) 
				.append("itemSubTotal", this.itemSubTotal) 
				.append("itemTax", this.itemTax) 
				.append("shippingTax", this.shippingTax) 
				.append("shippingCost", this.shippingCost)
				.append("discountAmount", this.discountAmount)
				.append("lessRestockAmount", this.lessRestockAmount) 
				.append("note", this.note) 
				.append("createTime", this.createTime) 
				.append("createBy", this.createBy) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderReturnId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderReturnId;
	}

}
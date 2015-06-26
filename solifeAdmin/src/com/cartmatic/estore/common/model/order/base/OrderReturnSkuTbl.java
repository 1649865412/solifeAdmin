package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderReturnSku Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderReturnSkuTbl extends BaseObject implements Serializable {

    protected Integer orderReturnSkuId;
	protected Integer quantity;
	protected java.math.BigDecimal returnAmount;
	protected Integer receivedQuantity;
	protected Short receivedStatus;
	protected Short reasonType;
	protected com.cartmatic.estore.common.model.order.OrderSku orderSku;
	protected com.cartmatic.estore.common.model.order.OrderReturn orderReturn;


	/**
	 * Default Empty Constructor for class OrderReturnSku
	 */
	public OrderReturnSkuTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderReturnSku
	 */
	public OrderReturnSkuTbl (
		 Integer in_orderReturnSkuId
        ) {
		this.setOrderReturnSkuId(in_orderReturnSkuId);
    }

	
	public com.cartmatic.estore.common.model.order.OrderSku getOrderSku () {
		return orderSku;
	}	
	
	public void setOrderSku (com.cartmatic.estore.common.model.order.OrderSku in_orderSku) {
		this.orderSku = in_orderSku;
	}
	
	public com.cartmatic.estore.common.model.order.OrderReturn getOrderReturn () {
		return orderReturn;
	}	
	
	public void setOrderReturn (com.cartmatic.estore.common.model.order.OrderReturn in_orderReturn) {
		this.orderReturn = in_orderReturn;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderReturnSkuId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderReturnSkuId() {
		return this.orderReturnSkuId;
	}
	
	/**
	 * Set the orderReturnSkuId
	 */	
	public void setOrderReturnSkuId(Integer aValue) {
		this.orderReturnSkuId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getOrderReturnId() {
		return this.getOrderReturn()==null?null:this.getOrderReturn().getOrderReturnId();
	}
	
	/**
	 * Set the orderReturnId
	 */	
	public void setOrderReturnId(Integer aValue) {
	    if (aValue==null) {
	    	orderReturn = null;
	    } else {
	        orderReturn = new com.cartmatic.estore.common.model.order.OrderReturn(aValue);
	        orderReturn.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getOrderSkuId() {
		return this.getOrderSku()==null?null:this.getOrderSku().getOrderSkuId();
	}
	
	/**
	 * Set the orderSkuId
	 */	
	public void setOrderSkuId(Integer aValue) {
	    if (aValue==null) {
	    	orderSku = null;
	    } else {
	        orderSku = new com.cartmatic.estore.common.model.order.OrderSku(aValue);
	        orderSku.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="quantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Set the quantity
	 * @spring.validator type="required"
	 */	
	public void setQuantity(Integer aValue) {
		this.quantity = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="returnAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getReturnAmount() {
		return this.returnAmount;
	}
	
	/**
	 * Set the returnAmount
	 * @spring.validator type="required"
	 */	
	public void setReturnAmount(java.math.BigDecimal aValue) {
		this.returnAmount = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="receivedQuantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getReceivedQuantity() {
		return this.receivedQuantity;
	}
	
	/**
	 * Set the receivedQuantity
	 * @spring.validator type="required"
	 */	
	public void setReceivedQuantity(Integer aValue) {
		this.receivedQuantity = aValue;
	}	

	public Short getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(Short receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	/**
	 * 	 * @return Short
	 * @hibernate.property column="reasonType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getReasonType() {
		return this.reasonType;
	}
	
	/**
	 * Set the reasonType
	 * @spring.validator type="required"
	 */	
	public void setReasonType(Short aValue) {
		this.reasonType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderReturnSkuTbl)) {
			return false;
		}
		OrderReturnSkuTbl rhs = (OrderReturnSkuTbl) object;
		return new EqualsBuilder()
				.append(this.orderReturnSkuId, rhs.orderReturnSkuId)
								.append(this.quantity, rhs.quantity)
				.append(this.returnAmount, rhs.returnAmount)
				.append(this.receivedQuantity, rhs.receivedQuantity)
				.append(this.receivedStatus, rhs.receivedStatus)
				.append(this.reasonType, rhs.reasonType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderReturnSkuId) 
								.append(this.quantity) 
				.append(this.returnAmount) 
				.append(this.receivedQuantity) 
				.append(this.receivedStatus)
				.append(this.reasonType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderReturnSkuId", this.orderReturnSkuId) 
								.append("quantity", this.quantity) 
				.append("returnAmount", this.returnAmount) 
				.append("receivedQuantity", this.receivedQuantity) 
				.append("receivedStatus", this.receivedStatus)
				.append("reasonType", this.reasonType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderReturnSkuId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderReturnSkuId;
	}

}
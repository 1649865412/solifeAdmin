package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * OrderShipment Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderShipmentTbl extends BaseObject implements Serializable {

    protected Integer orderShipmentId;
	protected String shipmentNo;
	protected String trackingNo;
	protected Integer shippingRateId;
	protected String carrierName;
	protected BigDecimal itemSubTotal;
	protected BigDecimal itemTax;
	protected BigDecimal shippingTax;
	protected BigDecimal shippingCost;
	protected BigDecimal discountAmount;
	protected BigDecimal shippingCostPaid;
	protected String wrapName;
	protected java.math.BigDecimal wrapUnitPrice;
	protected String wrapNote;
	protected Short hasRobotReviewed;
	protected Short isConfirmedByRobot;
	protected Short status;
	protected java.util.Date deliverTime;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer version;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;
	protected com.cartmatic.estore.common.model.order.OrderAddress orderAddress;
	protected com.cartmatic.estore.common.model.order.OrderPickList orderPickList;
	protected com.cartmatic.estore.common.model.system.ShippingRate shippingRate;

	protected java.util.Set orderSkus = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OrderShipment
	 */
	public OrderShipmentTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderShipment
	 */
	public OrderShipmentTbl (
		 Integer in_orderShipmentId
        ) {
		this.setOrderShipmentId(in_orderShipmentId);
    }

	
	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
	}
	
	public com.cartmatic.estore.common.model.order.OrderAddress getOrderAddress () {
		return orderAddress;
	}	
	
	public void setOrderAddress (com.cartmatic.estore.common.model.order.OrderAddress in_orderAddress) {
		this.orderAddress = in_orderAddress;
	}
	
	public com.cartmatic.estore.common.model.order.OrderPickList getOrderPickList () {
		return orderPickList;
	}	
	
	public void setOrderPickList (com.cartmatic.estore.common.model.order.OrderPickList in_orderPickList) {
		this.orderPickList = in_orderPickList;
	}
	
	public com.cartmatic.estore.common.model.system.ShippingRate getShippingRate() {
		return shippingRate;
	}

	public void setShippingRate(
			com.cartmatic.estore.common.model.system.ShippingRate shippingRate) {
		this.shippingRate = shippingRate;
	}

	public java.util.Set<OrderSku> getOrderSkus () {
		return orderSkus;
	}	
	
	public void setOrderSkus (java.util.Set in_orderSkus) {
		this.orderSkus = in_orderSkus;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderShipmentId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderShipmentId() {
		return this.orderShipmentId;
	}
	
	/**
	 * Set the orderShipmentId
	 */	
	public void setOrderShipmentId(Integer aValue) {
		this.orderShipmentId = aValue;
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
	 */
	public Integer getShippingAddressId() {
		return this.getOrderAddress()==null?null:this.getOrderAddress().getOrderAddressId();
	}
	
	/**
	 * Set the shippingAddressId
	 */	
	public void setShippingAddressId(Integer aValue) {
	    if (aValue==null) {
	    	orderAddress = null;
	    } else {
	        orderAddress = new com.cartmatic.estore.common.model.order.OrderAddress(aValue);
	        orderAddress.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getOrderPickListId() {
		return this.getOrderPickList()==null?null:this.getOrderPickList().getOrderPickListId();
	}
	
	/**
	 * Set the orderPickListId
	 */	
	public void setOrderPickListId(Integer aValue) {
	    if (aValue==null) {
	    	orderPickList = null;
	    } else {
	        orderPickList = new com.cartmatic.estore.common.model.order.OrderPickList(aValue);
	        orderPickList.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shipmentNo" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getShipmentNo() {
		return this.shipmentNo;
	}
	
	/**
	 * Set the shipmentNo
	 * @spring.validator type="required"
	 */	
	public void setShipmentNo(String aValue) {
		this.shipmentNo = aValue;
	}	

	/**
	 * tracking no of this shipment	 * @return String
	 * @hibernate.property column="trackingNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTrackingNo() {
		return this.trackingNo;
	}
	
	/**
	 * Set the trackingNo
	 */	
	public void setTrackingNo(String aValue) {
		this.trackingNo = aValue;
	}
	
	/**
	 * 	 * @return Integer
	 * @hibernate.property column="shippingRateId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getShippingRateId() {
		return this.getShippingRate()==null?null:this.getShippingRate().getShippingRateId();
	}
	
	/**
	 * Set the shippingRateId
	 */	
	public void setShippingRateId(Integer aValue) {
		if (aValue==null) {
			shippingRateId = null;
	    } else {
	    	shippingRate = new com.cartmatic.estore.common.model.system.ShippingRate(aValue);
	    	shippingRate.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="carrierName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCarrierName() {
		return this.carrierName;
	}
	
	/**
	 * Set the carrierName
	 */	
	public void setCarrierName(String aValue) {
		this.carrierName = aValue;
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

	public BigDecimal getShippingCostPaid() {
		return shippingCostPaid;
	}

	public void setShippingCostPaid(BigDecimal shippingCostPaid) {
		this.shippingCostPaid = shippingCostPaid;
	}

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="discountAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDiscountAmount() {
		return this.discountAmount==null?BigDecimal.ZERO.setScale(2):this.discountAmount;
	}
	
	/**
	 * Set the discountAmount
	 * @spring.validator type="required"
	 */	
	public void setDiscountAmount(java.math.BigDecimal aValue) {
		this.discountAmount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wrapName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getWrapName() {
		return this.wrapName;
	}
	
	/**
	 * Set the wrapName
	 */	
	public void setWrapName(String aValue) {
		this.wrapName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="wrapUnitPrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWrapUnitPrice() {
		return this.wrapUnitPrice==null?BigDecimal.ZERO.setScale(2):this.wrapUnitPrice;
	}
	
	/**
	 * Set the wrapUnitPrice
	 */	
	public void setWrapUnitPrice(java.math.BigDecimal aValue) {
		this.wrapUnitPrice = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wrapNote" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getWrapNote() {
		return this.wrapNote;
	}
	
	/**
	 * Set the wrapNote
	 */	
	public void setWrapNote(String aValue) {
		this.wrapNote = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="hasRobotReviewed" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getHasRobotReviewed() {
		return this.hasRobotReviewed;
	}
	
	/**
	 * Set the hasRobotReviewed
	 * @spring.validator type="required"
	 */	
	public void setHasRobotReviewed(Short aValue) {
		this.hasRobotReviewed = aValue;
	}

	public Short getIsConfirmedByRobot() {
		return isConfirmedByRobot;
	}

	public void setIsConfirmedByRobot(Short isConfirmedByRobot) {
		this.isConfirmedByRobot = isConfirmedByRobot;
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

	
	
	public java.util.Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(java.util.Date deliverTime) {
		this.deliverTime = deliverTime;
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
		if (!(object instanceof OrderShipmentTbl)) {
			return false;
		}
		OrderShipmentTbl rhs = (OrderShipmentTbl) object;
		return new EqualsBuilder()
				.append(this.orderShipmentId, rhs.orderShipmentId)
										.append(this.shipmentNo, rhs.shipmentNo)
				.append(this.trackingNo, rhs.trackingNo)
				.append(this.carrierName, rhs.carrierName)
				.append(this.itemSubTotal, rhs.itemSubTotal)
				.append(this.itemTax, rhs.itemTax)
				.append(this.shippingTax, rhs.shippingTax)
				.append(this.shippingCost, rhs.shippingCost)
				.append(this.shippingCostPaid, rhs.shippingCostPaid)
				.append(this.discountAmount, rhs.discountAmount)
				.append(this.wrapName, rhs.wrapName)
				.append(this.wrapUnitPrice, rhs.wrapUnitPrice)
				.append(this.wrapNote, rhs.wrapNote)
				.append(this.hasRobotReviewed, rhs.hasRobotReviewed)
				.append(this.isConfirmedByRobot, rhs.isConfirmedByRobot)
				.append(this.status, rhs.status)
				.append(this.deliverTime, rhs.deliverTime)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderShipmentId) 
										.append(this.shipmentNo) 
				.append(this.trackingNo) 
				.append(this.carrierName) 
				.append(this.itemSubTotal) 
				.append(this.itemTax) 
				.append(this.shippingTax) 
				.append(this.shippingCost) 
				.append(this.shippingCostPaid)
				.append(this.discountAmount) 
				.append(this.wrapName) 
				.append(this.wrapUnitPrice) 
				.append(this.wrapNote) 
				.append(this.hasRobotReviewed) 
				.append(this.isConfirmedByRobot)
				.append(this.status) 
				.append(this.deliverTime)
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderShipmentId", this.orderShipmentId) 
										.append("shipmentNo", this.shipmentNo) 
				.append("trackingNo", this.trackingNo) 
				.append("carrierName", this.carrierName) 
				.append("itemSubTotal", this.itemSubTotal) 
				.append("itemTax", this.itemTax) 
				.append("shippingTax", this.shippingTax) 
				.append("shippingCost", this.shippingCost) 
				.append("discountAmount", this.discountAmount) 
				.append("wrapName", this.wrapName) 
				.append("wrapUnitPrice", this.wrapUnitPrice) 
				.append("wrapNote", this.wrapNote) 
				.append("intelligentReview", this.hasRobotReviewed) 
				.append("isConfirmedByRobot", this.isConfirmedByRobot) 
				.append("status", this.status) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderShipmentId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderShipmentId;
	}

}
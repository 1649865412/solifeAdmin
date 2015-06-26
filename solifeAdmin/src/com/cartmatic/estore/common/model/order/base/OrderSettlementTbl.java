package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.core.model.BaseObject;

/**
 * OrderSettlement Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderSettlementTbl extends BaseObject implements Serializable {

    protected Integer orderSettlementId;
    protected Integer shipmentId;
	protected String shipmentNo;
	protected Integer orderId;
	protected String orderNo;
	protected String carrierName;
	protected String trackingNo;
	protected java.math.BigDecimal originalTotal;
	protected java.math.BigDecimal settlementAmount;
	protected Short isComplete;
	protected String addedBy;


	/**
	 * Default Empty Constructor for class OrderSettlement
	 */
	public OrderSettlementTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderSettlement
	 */
	public OrderSettlementTbl (
		 Integer in_orderSettlementId
        ) {
		this.setOrderSettlementId(in_orderSettlementId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderSettlementId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderSettlementId() {
		return this.orderSettlementId;
	}
	
	/**
	 * Set the orderSettlementId
	 */	
	public void setOrderSettlementId(Integer aValue) {
		this.orderSettlementId = aValue;
	}	

	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
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
	 * 	 * @return Integer
	 * @hibernate.property column="orderId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getOrderId() {
		return this.orderId;
	}
	
	/**
	 * Set the orderId
	 * @spring.validator type="required"
	 */	
	public void setOrderId(Integer aValue) {
		this.orderId = aValue;
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
	 * 物流公司名称	 * @return String
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
	 * @hibernate.property column="trackingNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getTrackingNo() {
		return this.trackingNo;
	}
	
	/**
	 * Set the trackingNo
	 * @spring.validator type="required"
	 */	
	public void setTrackingNo(String aValue) {
		this.trackingNo = aValue;
	}	

	/**
	 * 发货项原总计金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="originalTotal" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getOriginalTotal() {
		return this.originalTotal;
	}
	
	/**
	 * Set the originalTotal
	 * @spring.validator type="required"
	 */	
	public void setOriginalTotal(java.math.BigDecimal aValue) {
		this.originalTotal = aValue;
	}	

	/**
	 * 商家收到结算金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="settlementAmount" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}
	
	/**
	 * Set the settlementAmount
	 * @spring.validator type="required"
	 */	
	public void setSettlementAmount(java.math.BigDecimal aValue) {
		this.settlementAmount = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isComplete" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsComplete() {
		return this.isComplete;
	}
	
	/**
	 * Set the isComplete
	 * @spring.validator type="required"
	 */	
	public void setIsComplete(Short aValue) {
		this.isComplete = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="addedBy" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getAddedBy() {
		return this.addedBy;
	}
	
	/**
	 * Set the addedBy
	 */	
	public void setAddedBy(String aValue) {
		this.addedBy = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderSettlementTbl)) {
			return false;
		}
		OrderSettlementTbl rhs = (OrderSettlementTbl) object;
		return new EqualsBuilder()
				.append(this.orderSettlementId, rhs.orderSettlementId)
				.append(this.shipmentNo, rhs.shipmentNo)
				.append(this.orderId, rhs.orderId)
				.append(this.orderNo, rhs.orderNo)
				.append(this.carrierName, rhs.carrierName)
				.append(this.trackingNo, rhs.trackingNo)
				.append(this.originalTotal, rhs.originalTotal)
				.append(this.settlementAmount, rhs.settlementAmount)
				.append(this.isComplete, rhs.isComplete)
				.append(this.addedBy, rhs.addedBy)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderSettlementId) 
				.append(this.shipmentNo) 
				.append(this.orderId) 
				.append(this.orderNo) 
				.append(this.carrierName) 
				.append(this.trackingNo) 
				.append(this.originalTotal) 
				.append(this.settlementAmount) 
				.append(this.isComplete) 
				.append(this.addedBy) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderSettlementId", this.orderSettlementId) 
				.append("shipmentNo", this.shipmentNo) 
				.append("orderId", this.orderId) 
				.append("orderNo", this.orderNo) 
				.append("carrierName", this.carrierName) 
				.append("trackingNo", this.trackingNo) 
				.append("originalTotal", this.originalTotal) 
				.append("settlementAmount", this.settlementAmount) 
				.append("isComplete", this.isComplete) 
				.append("addedBy", this.addedBy) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderSettlementId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderSettlementId;
	}

}
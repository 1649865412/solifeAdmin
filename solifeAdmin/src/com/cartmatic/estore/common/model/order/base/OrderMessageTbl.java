package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderMessage Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderMessageTbl extends BaseObject implements Serializable {

    protected Integer orderMessageId;
	protected String subject;
	protected String message;
	protected Integer customerId;
	protected String orderNo;
	protected Short status;
	protected java.util.Date createTime;
	protected Integer createBy;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;


	/**
	 * Default Empty Constructor for class OrderMessage
	 */
	public OrderMessageTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderMessage
	 */
	public OrderMessageTbl (
		 Integer in_orderMessageId
        ) {
		this.setOrderMessageId(in_orderMessageId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderMessageId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderMessageId() {
		return this.orderMessageId;
	}
	
	/**
	 * Set the orderMessageId
	 */	
	public void setOrderMessageId(Integer aValue) {
		this.orderMessageId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 * @spring.validator type="required"
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="message" type="java.lang.String" length="65535" not-null="true" unique="false"
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message
	 * @spring.validator type="required"
	 */	
	public void setMessage(String aValue) {
		this.message = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="customerId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	/**
	 * Set the customerId
	 * @spring.validator type="required"
	 */	
	public void setCustomerId(Integer aValue) {
		this.customerId = aValue;
	}	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderMessageTbl)) {
			return false;
		}
		OrderMessageTbl rhs = (OrderMessageTbl) object;
		return new EqualsBuilder()
				.append(this.orderMessageId, rhs.orderMessageId)
				.append(this.subject, rhs.subject)
				.append(this.message, rhs.message)
				.append(this.customerId, rhs.customerId)
				.append(this.status, rhs.status)
				.append(this.createTime, rhs.createTime)
				.append(this.createBy, rhs.createBy)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderMessageId) 
				.append(this.subject) 
				.append(this.message) 
				.append(this.customerId) 
				.append(this.status) 
				.append(this.createTime) 
				.append(this.createBy) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderMessageId", this.orderMessageId) 
				.append("subject", this.subject) 
				.append("message", this.message) 
				.append("customerId", this.customerId) 
				.append("status", this.status) 
				.append("createTime", this.createTime) 
				.append("createBy", this.createBy) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderMessageId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderMessageId;
	}

	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
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
}
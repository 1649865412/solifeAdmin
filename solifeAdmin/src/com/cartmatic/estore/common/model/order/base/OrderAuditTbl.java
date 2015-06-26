package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderAudit Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderAuditTbl extends BaseObject implements Serializable {

    protected Integer orderAuditId;
	protected String addedBy;
	protected java.util.Date createTime;
	protected String transactionType;
	protected String detail;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;


	/**
	 * Default Empty Constructor for class OrderAudit
	 */
	public OrderAuditTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderAudit
	 */
	public OrderAuditTbl (
		 Integer in_orderAuditId
        ) {
		this.setOrderAuditId(in_orderAuditId);
    }

	
	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderAuditId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderAuditId() {
		return this.orderAuditId;
	}
	
	/**
	 * Set the orderAuditId
	 */	
	public void setOrderAuditId(Integer aValue) {
		this.orderAuditId = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="transactionType" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getTransactionType() {
		return this.transactionType;
	}
	
	/**
	 * Set the transactionType
	 * @spring.validator type="required"
	 */	
	public void setTransactionType(String aValue) {
		this.transactionType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="detail" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getDetail() {
		return this.detail;
	}
	
	/**
	 * Set the detail
	 */	
	public void setDetail(String aValue) {
		this.detail = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderAuditTbl)) {
			return false;
		}
		OrderAuditTbl rhs = (OrderAuditTbl) object;
		return new EqualsBuilder()
				.append(this.orderAuditId, rhs.orderAuditId)
						.append(this.addedBy, rhs.addedBy)
				.append(this.createTime, rhs.createTime)
				.append(this.transactionType, rhs.transactionType)
				.append(this.detail, rhs.detail)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderAuditId) 
						.append(this.addedBy) 
				.append(this.createTime) 
				.append(this.transactionType) 
				.append(this.detail) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderAuditId", this.orderAuditId) 
						.append("addedBy", this.addedBy) 
				.append("createTime", this.createTime) 
				.append("transactionType", this.transactionType) 
				.append("detail", this.detail) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderAuditId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderAuditId;
	}

}
package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.core.model.BaseObject;

/**
 * OrderPickList Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderPickListTbl extends BaseObject implements Serializable {

    protected Integer orderPickListId;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer createBy;
	protected Short isActive;

	protected java.util.Set orderShipments = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OrderPickList
	 */
	public OrderPickListTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPickList
	 */
	public OrderPickListTbl (
		 Integer in_orderPickListId
        ) {
		this.setOrderPickListId(in_orderPickListId);
    }


	public java.util.Set getOrderShipments () {
		return orderShipments;
	}	
	
	public void setOrderShipments (java.util.Set in_orderShipments) {
		this.orderShipments = in_orderShipments;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderPickListId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderPickListId() {
		return this.orderPickListId;
	}
	
	/**
	 * Set the orderPickListId
	 */	
	public void setOrderPickListId(Integer aValue) {
		this.orderPickListId = aValue;
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

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createdBy
	 * @spring.validator type="required"
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}	

	/**
	 * 备货单所有发货项是否完成发货，未完成－1（active） * @return Short
	 * @hibernate.property column="isActive" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsActive() {
		return this.isActive;
	}
	
	/**
	 * Set the isActive
	 * @spring.validator type="required"
	 */	
	public void setIsActive(Short aValue) {
		this.isActive = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderPickListTbl)) {
			return false;
		}
		OrderPickListTbl rhs = (OrderPickListTbl) object;
		return new EqualsBuilder()
				.append(this.orderPickListId, rhs.orderPickListId)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.createBy, rhs.createBy)
				.append(this.isActive, rhs.isActive)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderPickListId) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.createBy) 
				.append(this.isActive) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderPickListId", this.orderPickListId) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("createBy", this.createBy) 
				.append("isActive", this.isActive) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderPickListId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderPickListId;
	}

}
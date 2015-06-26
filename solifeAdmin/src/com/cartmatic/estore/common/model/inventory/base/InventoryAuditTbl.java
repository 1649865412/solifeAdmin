package com.cartmatic.estore.common.model.inventory.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * InventoryAudit Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class InventoryAuditTbl extends BaseObject implements Serializable {

    protected Integer inventoryAuditId;
	protected Integer quantity;
	protected Short eventType;
	protected String reason;
	protected String comment;
	protected String eventHandler;
	protected java.util.Date createTime;
	protected Integer											quantityOnHand;
	protected Integer											allocatedQuantity;
	
	protected com.cartmatic.estore.common.model.inventory.Inventory inventory;
	protected com.cartmatic.estore.common.model.catalog.ProductSku	productSku;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;

	/**
	 * Default Empty Constructor for class InventoryAudit
	 */
	public InventoryAuditTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class InventoryAudit
	 */
	public InventoryAuditTbl (
		 Integer in_inventoryAuditId
        ) {
		this.setInventoryAuditId(in_inventoryAuditId);
    }

	
	public com.cartmatic.estore.common.model.inventory.Inventory getInventory () {
		return inventory;
	}	
	
	public void setInventory (com.cartmatic.estore.common.model.inventory.Inventory in_inventory) {
		this.inventory = in_inventory;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="inventoryAuditId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getInventoryAuditId() {
		return this.inventoryAuditId;
	}
	
	/**
	 * Set the inventoryAuditId
	 */	
	public void setInventoryAuditId(Integer aValue) {
		this.inventoryAuditId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getInventoryId() {
		return this.getInventory()==null?null:this.getInventory().getInventoryId();
	}
	
	/**
	 * Set the inventoryId
	 */	
	public void setInventoryId(Integer aValue) {
	    if (aValue==null) {
	    	inventory = null;
	    } else {
	    	 inventory = new com.cartmatic.estore.common.model.inventory.Inventory(aValue);
		     inventory.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 操作数量	 * @return Integer
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
	 * 操作类型，库存调整（加库存、减库存）、库存分配、取消库存分配、释放库存	 * @return Short
	 * @hibernate.property column="eventType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getEventType() {
		return this.eventType;
	}
	
	/**
	 * Set the eventType
	 * @spring.validator type="required"
	 */	
	public void setEventType(Short aValue) {
		this.eventType = aValue;
	}	

	/**
	 * 操作理由
            进货、退货、损坏、盗窃	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getReason() {
		return this.reason;
	}
	
	/**
	 * Set the reason
	 */	
	public void setReason(String aValue) {
		this.reason = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="comment" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Set the comment
	 */	
	public void setComment(String aValue) {
		this.comment = aValue;
	}	

	/**
	 * 操作者，可能是前台、后台管理员、系统	 * @return String
	 * @hibernate.property column="eventHandler" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getEventHandler() {
		return this.eventHandler;
	}
	
	/**
	 * Set the eventHandler
	 * @spring.validator type="required"
	 */	
	public void setEventHandler(String aValue) {
		this.eventHandler = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InventoryAuditTbl)) {
			return false;
		}
		InventoryAuditTbl rhs = (InventoryAuditTbl) object;
		return new EqualsBuilder()
				.append(this.inventoryAuditId, rhs.inventoryAuditId)
						.append(this.quantity, rhs.quantity)
				.append(this.eventType, rhs.eventType)
				.append(this.reason, rhs.reason)
				.append(this.comment, rhs.comment)
				.append(this.eventHandler, rhs.eventHandler)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.inventoryAuditId) 
						.append(this.quantity) 
				.append(this.eventType) 
				.append(this.reason) 
				.append(this.comment) 
				.append(this.eventHandler) 
				.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("inventoryAuditId", this.inventoryAuditId) 
						.append("quantity", this.quantity) 
				.append("eventType", this.eventType) 
				.append("reason", this.reason) 
				.append("comment", this.comment) 
				.append("eventHandler", this.eventHandler) 
				.append("createTime", this.createTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "inventoryAuditId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return inventoryAuditId;
	}

	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(
			com.cartmatic.estore.common.model.order.SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}
	/**
	 * 	 * @return Integer
	 */
	public Integer getSalesOrderId() {
		return this.getSalesOrder()==null?null:this.getSalesOrder().getSalesOrderId();
	}
	
	/**
	 * Set the inventoryId
	 */	
	public void setSalesOrderId(Integer aValue) {
	    if (aValue==null) {
	    	salesOrder = null;
	    } else {
	    	salesOrder = new com.cartmatic.estore.common.model.order.SalesOrder(aValue);
	    	salesOrder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(
			com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}
	
	/**
	 * *
	 * 
	 * @return Integer
	 */
	public Integer getProductSkuId() {
		return this.getProductSku() == null ? null : this.getProductSku()
				.getProductSkuId();
	}

	/**
	 * Set the productSkuId
	 */
	public void setProductSkuId(Integer aValue) {
		if (aValue == null) {
			productSku = null;
		} else {
			productSku = new com.cartmatic.estore.common.model.catalog.ProductSku(
					aValue);
			productSku.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		}
	}

	public Integer getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(Integer quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public Integer getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(Integer allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

}
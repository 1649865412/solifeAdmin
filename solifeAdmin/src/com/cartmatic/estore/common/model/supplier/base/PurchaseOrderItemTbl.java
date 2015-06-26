package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * PurchaseOrderItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PurchaseOrderItemTbl extends BaseObject implements Serializable {

    protected Integer purchaseOrderItemId;
	protected String productName;
	protected String supplierProductName;
	protected String skuDisplay;
	protected String accessories;
	protected Integer purchaseQuantity = 0;
	protected Integer passedQuantity = 0;
	protected java.math.BigDecimal purchasePrice = BigDecimal.ZERO;
	protected Short status = 0;
	//protected com.cartmatic.estore.common.model.inventory.Inventory inventory;
	protected OrderSku orderSku;
	protected com.cartmatic.estore.common.model.supplier.PurchaseOrder purchaseOrder;


	/**
	 * Default Empty Constructor for class PurchaseOrderItem
	 */
	public PurchaseOrderItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PurchaseOrderItem
	 */
	public PurchaseOrderItemTbl (
		 Integer in_purchaseOrderItemId
        ) {
		this.setPurchaseOrderItemId(in_purchaseOrderItemId);
    }
	
	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public com.cartmatic.estore.common.model.order.OrderSku getOrderSku () {
		return orderSku;
	}	
	
	public void setOrderSku (com.cartmatic.estore.common.model.order.OrderSku in_orderSku) {
		this.orderSku = in_orderSku;
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
	
	public com.cartmatic.estore.common.model.supplier.PurchaseOrder getPurchaseOrder () {
		return purchaseOrder;
	}	
	
	public void setPurchaseOrder (com.cartmatic.estore.common.model.supplier.PurchaseOrder in_purchaseOrder) {
		this.purchaseOrder = in_purchaseOrder;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="purchaseOrderItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPurchaseOrderItemId() {
		return this.purchaseOrderItemId;
	}
	
	/**
	 * Set the purchaseOrderItemId
	 */	
	public void setPurchaseOrderItemId(Integer aValue) {
		this.purchaseOrderItemId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getPurchaseOrderId() {
		return this.getPurchaseOrder()==null?null:this.getPurchaseOrder().getPurchaseOrderId();
	}
	
	/**
	 * Set the purchaseOrderId
	 */	
	public void setPurchaseOrderId(Integer aValue) {
	    if (aValue==null) {
	    	purchaseOrder = null;
	    } else {
	        purchaseOrder = new com.cartmatic.estore.common.model.supplier.PurchaseOrder(aValue);
	        purchaseOrder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	
	
	/**
	 * 	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 * @spring.validator type="required"
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="supplierProductName" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getSupplierProductName() {
		return this.supplierProductName;
	}
	
	/**
	 * Set the supplierProductName
	 */	
	public void setSupplierProductName(String aValue) {
		this.supplierProductName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="skuDisplay" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSkuDisplay() {
		return this.skuDisplay;
	}
	
	/**
	 * Set the skuDisplay
	 */	
	public void setSkuDisplay(String aValue) {
		this.skuDisplay = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="purchaseQuantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getPurchaseQuantity() {
		return this.purchaseQuantity;
	}
	
	/**
	 * Set the purchaseQuantity
	 * @spring.validator type="required"
	 */	
	public void setPurchaseQuantity(Integer aValue) {
		this.purchaseQuantity = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="passedQuantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getPassedQuantity() {
		return this.passedQuantity;
	}
	
	/**
	 * Set the passedQuantity
	 * @spring.validator type="required"
	 */	
	public void setPassedQuantity(Integer aValue) {
		this.passedQuantity = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="purchasePrice" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPurchasePrice() {
		return this.purchasePrice;
	}
	
	/**
	 * Set the purchasePrice
	 * @spring.validator type="required"
	 */	
	public void setPurchasePrice(java.math.BigDecimal aValue) {
		this.purchasePrice = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PurchaseOrderItemTbl)) {
			return false;
		}
		PurchaseOrderItemTbl rhs = (PurchaseOrderItemTbl) object;
		return new EqualsBuilder()
				.append(this.purchaseOrderItemId, rhs.purchaseOrderItemId)
				.append(this.productName, rhs.productName)
				.append(this.supplierProductName, rhs.supplierProductName)
				.append(this.accessories, rhs.accessories)
				.append(this.status, rhs.status)
				.append(this.skuDisplay, rhs.skuDisplay)
				.append(this.purchaseQuantity, rhs.purchaseQuantity)
				.append(this.passedQuantity, rhs.passedQuantity)
				.append(this.purchasePrice, rhs.purchasePrice)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.purchaseOrderItemId) 
				.append(this.productName) 
				.append(this.supplierProductName) 
				.append(this.accessories)
				.append(this.status)
				.append(this.skuDisplay) 
				.append(this.purchaseQuantity) 
				.append(this.passedQuantity) 
				.append(this.purchasePrice) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("purchaseOrderItemId", this.purchaseOrderItemId) 
				.append("productName", this.productName) 
				.append("supplierProductName", this.supplierProductName) 
				.append("accessories", this.accessories)
				.append("status", this.status)
				.append("skuDisplay", this.skuDisplay) 
				.append("purchaseQuantity", this.purchaseQuantity) 
				.append("passedQuantity", this.passedQuantity) 
				.append("purchasePrice", this.purchasePrice) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "purchaseOrderItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return purchaseOrderItemId;
	}

}
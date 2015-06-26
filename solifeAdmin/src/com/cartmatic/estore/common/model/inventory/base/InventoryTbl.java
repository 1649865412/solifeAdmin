
package com.cartmatic.estore.common.model.inventory.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Inventory Base Java Bean, base class for the model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
public class InventoryTbl extends BaseObject implements Serializable {

	protected Integer											inventoryId;
	protected Integer											quantityOnHand = 0;
	protected Integer											allocatedQuantity = 0;
	protected Integer											reservedQuantity = 0;
	protected java.util.Date									expectedRestockDate;
	protected Integer											reorderQuantity = 0;
	protected Integer											preOrBackOrderedQty = 0;
	protected Integer											reorderMinimnm = 0;
	protected java.util.Date									createTime;
	protected java.util.Date									updateTime;
	protected Integer											version;
	protected com.cartmatic.estore.common.model.catalog.ProductSku	productSku;

	protected java.util.Set										inventoryAudits	= new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Inventory
	 */
	public InventoryTbl() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Inventory
	 */
	public InventoryTbl(Integer in_inventoryId) {
		this.setInventoryId(in_inventoryId);
	}

	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(
			com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}

	public java.util.Set getInventoryAudits() {
		return inventoryAudits;
	}

	public void setInventoryAudits(java.util.Set in_inventoryAudits) {
		this.inventoryAudits = in_inventoryAudits;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.id column="inventoryId" type="java.lang.Integer"
	 *               generator-class="native"
	 */
	public Integer getInventoryId() {
		return this.inventoryId;
	}

	/**
	 * Set the inventoryId
	 */
	public void setInventoryId(Integer aValue) {
		this.inventoryId = aValue;
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

	/**
	 * 现有库存 *
	 * 
	 * @return Integer
	 * @hibernate.property column="quantityOnHand" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getQuantityOnHand() {
		return this.quantityOnHand;
	}

	/**
	 * Set the quantityOnHand
	 * 
	 * @spring.validator type="required"
	 */
	public void setQuantityOnHand(Integer aValue) {
		this.quantityOnHand = aValue;
	}

	/**
	 * 已分配数量 *
	 * 
	 * @return Integer
	 * @hibernate.property column="allocatedQuantity" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getAllocatedQuantity() {
		return this.allocatedQuantity;
	}

	/**
	 * Set the allocatedQuantity
	 * 
	 * @spring.validator type="required"
	 */
	public void setAllocatedQuantity(Integer aValue) {
		this.allocatedQuantity = aValue;
	}

	/**
	 * 保留数量 *
	 * 
	 * @return Integer
	 * @hibernate.property column="reservedQuantity" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getReservedQuantity() {
		return this.reservedQuantity;
	}

	/**
	 * Set the reservedQuantity
	 * 
	 * @spring.validator type="required"
	 */
	public void setReservedQuantity(Integer aValue) {
		this.reservedQuantity = aValue;
	}

	/**
	 * 预计再进货时间 *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="expectedRestockDate" type="java.util.Date"
	 *                     length="0" not-null="false" unique="false"
	 */
	public java.util.Date getExpectedRestockDate() {
		return this.expectedRestockDate;
	}

	/**
	 * Set the expectedRestockDate
	 */
	public void setExpectedRestockDate(java.util.Date aValue) {
		this.expectedRestockDate = aValue;
	}

	/**
	 * 再进货数量 *
	 * 
	 * @return Integer
	 * @hibernate.property column="reorderQuantity" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getReorderQuantity() {
		return this.reorderQuantity;
	}

	/**
	 * Set the reorderQuantity
	 */
	public void setReorderQuantity(Integer aValue) {
		this.reorderQuantity = aValue;
	}

	/**
	 * 已分配的预订数量 *
	 * 
	 * @return Integer
	 * @hibernate.property column="preOrBackOrderedQty" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getPreOrBackOrderedQty() {
		return this.preOrBackOrderedQty;
	}

	/**
	 * Set the preOrBackOrderedQty
	 * 
	 * @spring.validator type="required"
	 */
	public void setPreOrBackOrderedQty(Integer aValue) {
		this.preOrBackOrderedQty = aValue;
	}	


	/**
	 * 最少库存，当少于这值时提示低库存 *
	 * 
	 * @return Integer
	 * @hibernate.property column="reorderMinimnm" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getReorderMinimnm() {
		return this.reorderMinimnm;
	}

	/**
	 * Set the reorderMinimnm
	 * 
	 * @spring.validator type="required"
	 */
	public void setReorderMinimnm(Integer aValue) {
		this.reorderMinimnm = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * Set the createTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Set the updateTime
	 * 
	 * @spring.validator type="required"
	 */
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}

	/**
	 * *
	 * 
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10"
	 *                     not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Set the version
	 * 
	 * @spring.validator type="required"
	 */
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InventoryTbl)) {
			return false;
		}
		InventoryTbl rhs = (InventoryTbl) object;
		return new EqualsBuilder().append(this.inventoryId, rhs.inventoryId)
				.append(this.quantityOnHand, rhs.quantityOnHand).append(
						this.allocatedQuantity, rhs.allocatedQuantity).append(
						this.reservedQuantity, rhs.reservedQuantity).append(
						this.expectedRestockDate, rhs.expectedRestockDate)
				.append(this.reorderQuantity, rhs.reorderQuantity)
				.append(this.preOrBackOrderedQty, rhs.preOrBackOrderedQty)
				.append(this.reorderMinimnm, rhs.reorderMinimnm).append(
						this.createTime, rhs.createTime).append(
						this.updateTime, rhs.updateTime).append(this.version,
						rhs.version).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
				this.inventoryId).append(this.quantityOnHand).append(
				this.allocatedQuantity).append(this.reservedQuantity).append(
				this.expectedRestockDate).append(this.reorderQuantity)
				.append(this.preOrBackOrderedQty)
				.append(this.reorderMinimnm).append(
				this.createTime).append(this.updateTime).append(this.version)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("inventoryId", this.inventoryId).append(
						"quantityOnHand", this.quantityOnHand).append(
						"allocatedQuantity", this.allocatedQuantity).append(
						"reservedQuantity", this.reservedQuantity).append(
						"expectedRestockDate", this.expectedRestockDate)
				.append("reorderQuantity", this.reorderQuantity).append(
						"preOrBackOrderedQty", this.preOrBackOrderedQty)
				.append("reorderMinimnm", this.reorderMinimnm).append(
						"createTime", this.createTime).append("updateTime",
						this.updateTime).append("version", this.version)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "inventoryId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return inventoryId;
	}

}
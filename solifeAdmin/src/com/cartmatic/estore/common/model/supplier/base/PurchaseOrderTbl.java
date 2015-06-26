package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PurchaseOrder Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PurchaseOrderTbl extends BaseObject implements Serializable {

    protected Integer purchaseOrderId;
	protected String supplierName;
	protected String purchaseOrderNo;
	protected String trackingNo;
	protected Short poStatus = 0;
	protected Short poResult = 0;
	protected Integer createBy;
	protected Integer updateBy;
	protected java.util.Date updateTime;
	protected java.util.Date createTime;
	protected String comments;
	protected Integer version;
	protected com.cartmatic.estore.common.model.supplier.Supplier supplier;

	protected java.util.Set purchaseOrderItems = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class PurchaseOrder
	 */
	public PurchaseOrderTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PurchaseOrder
	 */
	public PurchaseOrderTbl (
		 Integer in_purchaseOrderId
        ) {
		this.setPurchaseOrderId(in_purchaseOrderId);
    }

	
	public com.cartmatic.estore.common.model.supplier.Supplier getSupplier () {
		return supplier;
	}	
	
	public void setSupplier (com.cartmatic.estore.common.model.supplier.Supplier in_supplier) {
		this.supplier = in_supplier;
	}

	public java.util.Set<PurchaseOrderItem> getPurchaseOrderItems () {
		return purchaseOrderItems;
	}	
	
	public void setPurchaseOrderItems (java.util.Set in_purchaseOrderItems) {
		this.purchaseOrderItems = in_purchaseOrderItems;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="purchaseOrderId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getPurchaseOrderId() {
		return this.purchaseOrderId;
	}
	
	/**
	 * Set the purchaseOrderId
	 */	
	public void setPurchaseOrderId(Integer aValue) {
		this.purchaseOrderId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSupplierId() {
		return this.getSupplier()==null?null:this.getSupplier().getSupplierId();
	}
	
	/**
	 * Set the supplierId
	 */	
	public void setSupplierId(Integer aValue) {
	    if (aValue==null) {
	    	supplier = null;
	    } else {
	        supplier = new com.cartmatic.estore.common.model.supplier.Supplier(aValue);
	        supplier.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="supplierName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getSupplierName() {
		return this.supplierName;
	}
	
	/**
	 * Set the supplierName
	 * @spring.validator type="required"
	 */	
	public void setSupplierName(String aValue) {
		this.supplierName = aValue;
	}	

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	/**
	 * 	 * @return String
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
	 * 	 * @return Short
	 * @hibernate.property column="poStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getPoStatus() {
		return this.poStatus;
	}
	
	/**
	 * Set the poStatus
	 * @spring.validator type="required"
	 */	
	public void setPoStatus(Short aValue) {
		this.poStatus = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="poResult" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getPoResult() {
		return this.poResult;
	}
	
	/**
	 * Set the poResult
	 * @spring.validator type="required"
	 */	
	public void setPoResult(Short aValue) {
		this.poResult = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="createBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCreateBy() {
		return this.createBy;
	}
	
	/**
	 * Set the createBy
	 */	
	public void setCreateBy(Integer aValue) {
		this.createBy = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="updateBy" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUpdateBy() {
		return this.updateBy;
	}
	
	/**
	 * Set the updateBy
	 */	
	public void setUpdateBy(Integer aValue) {
		this.updateBy = aValue;
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
	 * @hibernate.property column="comments" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getComments() {
		return this.comments;
	}
	
	/**
	 * Set the comments
	 */	
	public void setComments(String aValue) {
		this.comments = aValue;
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
		if (!(object instanceof PurchaseOrderTbl)) {
			return false;
		}
		PurchaseOrderTbl rhs = (PurchaseOrderTbl) object;
		return new EqualsBuilder()
				.append(this.purchaseOrderId, rhs.purchaseOrderId)
				.append(this.supplierName, rhs.supplierName)
				.append(this.trackingNo, rhs.trackingNo)
				.append(this.purchaseOrderNo, rhs.purchaseOrderNo)
				.append(this.poStatus, rhs.poStatus)
				.append(this.poResult, rhs.poResult)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.updateTime, rhs.updateTime)
				.append(this.createTime, rhs.createTime)
				.append(this.comments, rhs.comments)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.purchaseOrderId) 
				.append(this.supplierName) 
				.append(this.purchaseOrderNo)
				.append(this.trackingNo) 
				.append(this.poStatus) 
				.append(this.poResult) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.updateTime) 
				.append(this.createTime) 
				.append(this.comments) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("purchaseOrderId", this.purchaseOrderId) 
				.append("supplierName", this.supplierName) 
				.append("purchaseOrderNo", this.purchaseOrderNo)
				.append("trackingNo", this.trackingNo) 
				.append("poStatus", this.poStatus) 
				.append("poResult", this.poResult) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("updateTime", this.updateTime) 
				.append("createTime", this.createTime) 
				.append("comments", this.comments) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "purchaseOrderId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return purchaseOrderId;
	}

}
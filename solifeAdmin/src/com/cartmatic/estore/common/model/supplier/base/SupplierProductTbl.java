package com.cartmatic.estore.common.model.supplier.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SupplierProduct Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SupplierProductTbl extends BaseObject implements Serializable {

    protected Integer supplierProductId;
	protected String productName;
	protected String productDesc;
	protected String wholesalePrice;
	protected String productCode;
	protected Short status;
	protected String mediaUrl;
	protected String modifyLogs;
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	protected Integer createBy;
	protected Integer updateBy;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	protected com.cartmatic.estore.common.model.supplier.Supplier supplier;
	protected com.cartmatic.estore.common.model.catalog.Category category;
	
	protected Long tbCId;
	protected String tbCatProps;
	protected String tbSellCatProps;
	protected Long tbId;
	protected String updateLogs;


	/**
	 * Default Empty Constructor for class SupplierProduct
	 */
	public SupplierProductTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SupplierProduct
	 */
	public SupplierProductTbl (
		 Integer in_supplierProductId
        ) {
		this.setSupplierProductId(in_supplierProductId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
	
	public com.cartmatic.estore.common.model.supplier.Supplier getSupplier () {
		return supplier;
	}	
	
	public void setSupplier (com.cartmatic.estore.common.model.supplier.Supplier in_supplier) {
		this.supplier = in_supplier;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="supplierProductId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getSupplierProductId() {
		return this.supplierProductId;
	}
	
	/**
	 * Set the supplierProductId
	 */	
	public void setSupplierProductId(Integer aValue) {
		this.supplierProductId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductId() {
		return this.getProduct()==null?null:this.getProduct().getProductId();
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Integer aValue) {
	    if (aValue==null) {
	    	product = null;
	    } else {
	        product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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
	 * @hibernate.property column="productName" type="java.lang.String" length="256" not-null="true" unique="false"
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
	 * @hibernate.property column="productDesc" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProductDesc() {
		return this.productDesc;
	}
	
	/**
	 * Set the productDesc
	 */	
	public void setProductDesc(String aValue) {
		this.productDesc = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="wholesalePrice" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getWholesalePrice() {
		return this.wholesalePrice;
	}
	
	/**
	 * Set the wholesalePrice
	 * @spring.validator type="required"
	 */	
	public void setWholesalePrice(String aValue) {
		this.wholesalePrice = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productCode" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getProductCode() {
		return this.productCode;
	}
	
	/**
	 * Set the productCode
	 * @spring.validator type="required"
	 */	
	public void setProductCode(String aValue) {
		this.productCode = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="mediaUrl" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMediaUrl() {
		return this.mediaUrl;
	}
	
	/**
	 * Set the mediaUrl
	 */	
	public void setMediaUrl(String aValue) {
		this.mediaUrl = aValue;
	}	

	

	/**
	 * 	 * @return String
	 * @hibernate.property column="modifyLogs" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getModifyLogs() {
		return this.modifyLogs;
	}
	
	/**
	 * Set the modifyLogs
	 */	
	public void setModifyLogs(String aValue) {
		this.modifyLogs = aValue;
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
		if (!(object instanceof SupplierProductTbl)) {
			return false;
		}
		SupplierProductTbl rhs = (SupplierProductTbl) object;
		return new EqualsBuilder()
				.append(this.supplierProductId, rhs.supplierProductId)
								.append(this.productName, rhs.productName)
				.append(this.productDesc, rhs.productDesc)
				.append(this.wholesalePrice, rhs.wholesalePrice)
				.append(this.productCode, rhs.productCode)
				.append(this.status, rhs.status)
				.append(this.mediaUrl, rhs.mediaUrl)
				.append(this.modifyLogs, rhs.modifyLogs)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.createBy, rhs.createBy)
				.append(this.updateBy, rhs.updateBy)
				.append(this.version, rhs.version)
				.append(this.tbCId, rhs.tbCId)
				.append(this.tbCatProps, rhs.tbCatProps)
				.append(this.tbSellCatProps, rhs.tbSellCatProps)
				.append(this.tbId, rhs.tbId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.supplierProductId) 
								.append(this.productName) 
				.append(this.productDesc) 
				.append(this.wholesalePrice) 
				.append(this.productCode) 
				.append(this.status) 
				.append(this.mediaUrl) 
				.append(this.modifyLogs) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.createBy) 
				.append(this.updateBy) 
				.append(this.version) 
				.append(this.tbCId) 
				.append(this.tbCatProps) 
				.append(this.tbSellCatProps) 
				.append(this.tbId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("supplierProductId", this.supplierProductId) 
								.append("productName", this.productName) 
				.append("productDesc", this.productDesc) 
				.append("wholesalePrice", this.wholesalePrice) 
				.append("productCode", this.productCode) 
				.append("status", this.status) 
				.append("mediaUrl", this.mediaUrl) 
				.append("modifyLogs", this.modifyLogs) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("createBy", this.createBy) 
				.append("updateBy", this.updateBy) 
				.append("version", this.version) 
				.append("tbCId", this.tbCId) 
				.append("tbCatProps", this.tbCatProps) 
				.append("tbSellCatProps", this.tbSellCatProps) 
				.append("tbId", this.tbId) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "supplierProductId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return supplierProductId;
	}

	public com.cartmatic.estore.common.model.catalog.Category getCategory() {
		return category;
	}

	public void setCategory(com.cartmatic.estore.common.model.catalog.Category category) {
		this.category = category;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getCategoryId() {
		return this.getCategory()==null?null:this.getCategory().getCategoryId();
	}
	
	/**
	 * Set the categoryId
	 */	
	public void setCategoryId(Integer aValue) {
	    if (aValue==null) {
	    	category = null;
	    } else {
	    	category = new com.cartmatic.estore.common.model.catalog.Category(aValue);
	        category.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public Long getTbCId() {
		return tbCId;
	}

	public void setTbCId(Long tbCId) {
		this.tbCId = tbCId;
	}

	public String getTbCatProps() {
		return tbCatProps;
	}

	public void setTbCatProps(String tbCatProps) {
		this.tbCatProps = tbCatProps;
	}

	public String getTbSellCatProps() {
		return tbSellCatProps;
	}

	public void setTbSellCatProps(String tbSellCatProps) {
		this.tbSellCatProps = tbSellCatProps;
	}

	public Long getTbId() {
		return tbId;
	}

	public void setTbId(Long tbId) {
		this.tbId = tbId;
	}

	public String getUpdateLogs() {
		return updateLogs;
	}

	public void setUpdateLogs(String updateLogs) {
		this.updateLogs = updateLogs;
	}
}
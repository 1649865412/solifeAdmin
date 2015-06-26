package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductPackageItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductPackageItemTbl extends BaseObject implements Serializable {

    protected Integer productPackageItemId;
	protected Integer sortOrder;
	protected Integer quantity;
	protected com.cartmatic.estore.common.model.catalog.ProductSku itemSku;
	protected com.cartmatic.estore.common.model.catalog.ProductSku productSku;


	/**
	 * Default Empty Constructor for class ProductPackageItem
	 */
	public ProductPackageItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductPackageItem
	 */
	public ProductPackageItemTbl (
		 Integer in_productPackageItemId
        ) {
		this.setProductPackageItemId(in_productPackageItemId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku () {
		return productSku;
	}	
	
	public void setProductSku (com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productPackageItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductPackageItemId() {
		return this.productPackageItemId;
	}
	
	/**
	 * Set the productPackageItemId
	 */	
	public void setProductPackageItemId(Integer aValue) {
		this.productPackageItemId = aValue;
	}	

	/**
	 * 产品包对应的sku	 * @return Integer
	 */
	public Integer getPackageSkuId() {
		return this.getProductSku()==null?null:this.getProductSku().getProductSkuId();
	}
	
	/**
	 * Set the packageSkuId
	 */	
	public void setPackageSkuId(Integer aValue) {
	    if (aValue==null) {
	    	productSku = null;
	    } else {
	    	productSku = new com.cartmatic.estore.common.model.catalog.ProductSku(aValue);
	        productSku.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	


	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sortOrder" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	/**
	 * Set the sortOrder
	 */	
	public void setSortOrder(Integer aValue) {
		this.sortOrder = aValue;
	}	

	/**
	 * 包含该sku的数量	 * @return Integer
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductPackageItemTbl)) {
			return false;
		}
		ProductPackageItemTbl rhs = (ProductPackageItemTbl) object;
		return new EqualsBuilder()
				.append(this.productPackageItemId, rhs.productPackageItemId)
				.append(this.sortOrder, rhs.sortOrder)
				.append(this.quantity, rhs.quantity)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productPackageItemId) 
				.append(this.sortOrder) 
				.append(this.quantity) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productPackageItemId", this.productPackageItemId) 
				.append("sortOrder", this.sortOrder) 
				.append("quantity", this.quantity) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productPackageItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productPackageItemId;
	}

	public com.cartmatic.estore.common.model.catalog.ProductSku getItemSku() {
		return itemSku;
	}

	public void setItemSku(com.cartmatic.estore.common.model.catalog.ProductSku itemSku) {
		this.itemSku = itemSku;
	}
	
	/**
	 * 产品包所包含的产品的sku	 * @return Integer
	 * @hibernate.property column="itemSkuId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getItemSkuId() {
		return this.getItemSku()==null?null:this.getItemSku().getProductSkuId();
	}
	
	/**
	 * Set the itemSkuId
	 * @spring.validator type="required"
	 */	
	public void setItemSkuId(Integer aValue) {
	    if (aValue==null) {
	    	itemSku = null;
	    } else {
	    	itemSku = new com.cartmatic.estore.common.model.catalog.ProductSku(aValue);
	    	itemSku.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

}
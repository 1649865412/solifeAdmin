package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductTypeSkuOption Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductTypeSkuOptionTbl extends BaseObject implements Serializable {

    protected Integer productTypeSkuOptionId;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.ProductType productType;
	protected com.cartmatic.estore.common.model.catalog.SkuOption skuOption;


	/**
	 * Default Empty Constructor for class ProductTypeSkuOption
	 */
	public ProductTypeSkuOptionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductTypeSkuOption
	 */
	public ProductTypeSkuOptionTbl (
		 Integer in_productTypeSkuOptionId
        ) {
		this.setProductTypeSkuOptionId(in_productTypeSkuOptionId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductType getProductType () {
		return productType;
	}	
	
	public void setProductType (com.cartmatic.estore.common.model.catalog.ProductType in_productType) {
		this.productType = in_productType;
	}
	
	public com.cartmatic.estore.common.model.catalog.SkuOption getSkuOption () {
		return skuOption;
	}	
	
	public void setSkuOption (com.cartmatic.estore.common.model.catalog.SkuOption in_skuOption) {
		this.skuOption = in_skuOption;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productTypeSkuOptionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductTypeSkuOptionId() {
		return this.productTypeSkuOptionId;
	}
	
	/**
	 * Set the productTypeSkuOptionId
	 */	
	public void setProductTypeSkuOptionId(Integer aValue) {
		this.productTypeSkuOptionId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductTypeId() {
		return this.getProductType()==null?null:this.getProductType().getProductTypeId();
	}
	
	/**
	 * Set the productTypeId
	 */	
	public void setProductTypeId(Integer aValue) {
	    if (aValue==null) {
	    	productType = null;
	    } else {
	    	productType = new com.cartmatic.estore.common.model.catalog.ProductType(aValue);
	        productType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSkuOptionId() {
		return this.getSkuOption()==null?null:this.getSkuOption().getSkuOptionId();
	}
	
	/**
	 * Set the skuOptionId
	 */	
	public void setSkuOptionId(Integer aValue) {
	    if (aValue==null) {
	    	skuOption = null;
	    } else {
	    	skuOption = new com.cartmatic.estore.common.model.catalog.SkuOption(aValue);
	        skuOption.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 前台变种属性显示排序	 * @return Integer
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductTypeSkuOptionTbl)) {
			return false;
		}
		ProductTypeSkuOptionTbl rhs = (ProductTypeSkuOptionTbl) object;
		return new EqualsBuilder()
				.append(this.productTypeSkuOptionId, rhs.productTypeSkuOptionId)
								.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productTypeSkuOptionId) 
								.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productTypeSkuOptionId", this.productTypeSkuOptionId) 
								.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productTypeSkuOptionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productTypeSkuOptionId;
	}

}
package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductSkuOptionValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductSkuOptionValueTbl extends BaseObject implements Serializable {

    protected Integer productSkuOptionValueId;
	protected com.cartmatic.estore.common.model.catalog.SkuOptionValue skuOptionValue;
	protected com.cartmatic.estore.common.model.catalog.ProductSku productSku;


	/**
	 * Default Empty Constructor for class ProductSkuOptionValue
	 */
	public ProductSkuOptionValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductSkuOptionValue
	 */
	public ProductSkuOptionValueTbl (
		 Integer in_productSkuOptionValueId
        ) {
		this.setProductSkuOptionValueId(in_productSkuOptionValueId);
    }

	
	public com.cartmatic.estore.common.model.catalog.SkuOptionValue getSkuOptionValue () {
		return skuOptionValue;
	}	
	
	public void setSkuOptionValue (com.cartmatic.estore.common.model.catalog.SkuOptionValue in_skuOptionValue) {
		this.skuOptionValue = in_skuOptionValue;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku () {
		return productSku;
	}	
	
	public void setProductSku (com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productSkuOptionValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductSkuOptionValueId() {
		return this.productSkuOptionValueId;
	}
	
	/**
	 * Set the productSkuOptionValueId
	 */	
	public void setProductSkuOptionValueId(Integer aValue) {
		this.productSkuOptionValueId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSkuOptionValueId() {
		return this.getSkuOptionValue()==null?null:this.getSkuOptionValue().getSkuOptionValueId();
	}
	
	/**
	 * Set the skuOptionValueId
	 */	
	public void setSkuOptionValueId(Integer aValue) {
	    if (aValue==null) {
	    	skuOptionValue = null;
	    } else {
	    	skuOptionValue = new com.cartmatic.estore.common.model.catalog.SkuOptionValue(aValue);
	        skuOptionValue.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductSkuId() {
		return this.getProductSku()==null?null:this.getProductSku().getProductSkuId();
	}
	
	/**
	 * Set the productSkuId
	 */	
	public void setProductSkuId(Integer aValue) {
	    if (aValue==null) {
	    	productSku = null;
	    } else {
	    	productSku = new com.cartmatic.estore.common.model.catalog.ProductSku(aValue);
	        productSku.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductSkuOptionValueTbl)) {
			return false;
		}
		ProductSkuOptionValueTbl rhs = (ProductSkuOptionValueTbl) object;
		return new EqualsBuilder()
				.append(this.productSkuOptionValueId, rhs.productSkuOptionValueId)
								.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productSkuOptionValueId) 
								.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productSkuOptionValueId", this.productSkuOptionValueId) 
								.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productSkuOptionValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productSkuOptionValueId;
	}

}
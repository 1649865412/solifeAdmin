package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * WholesalePrice Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WholesalePriceTbl extends BaseObject implements Serializable {

    protected Integer wholesalePriceId;
	protected java.math.BigDecimal price;
	protected Integer minQuantity;
	protected com.cartmatic.estore.common.model.catalog.ProductSku productSku;


	/**
	 * Default Empty Constructor for class WholesalePrice
	 */
	public WholesalePriceTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WholesalePrice
	 */
	public WholesalePriceTbl (
		 Integer in_wholesalePriceId
        ) {
		this.setWholesalePriceId(in_wholesalePriceId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku () {
		return productSku;
	}	
	
	public void setProductSku (com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="wholesalePriceId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getWholesalePriceId() {
		return this.wholesalePriceId;
	}
	
	/**
	 * Set the wholesalePriceId
	 */	
	public void setWholesalePriceId(Integer aValue) {
		this.wholesalePriceId = aValue;
	}	

	/**
	 * 批发价	 * @return java.math.BigDecimal
	 * @hibernate.property column="price" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Set the price
	 * @spring.validator type="required"
	 */	
	public void setPrice(java.math.BigDecimal aValue) {
		this.price = aValue;
	}	

	/**
	 * 批发的最少数量	 * @return Integer
	 * @hibernate.property column="minQuantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getMinQuantity() {
		return this.minQuantity;
	}
	
	/**
	 * Set the minQuantity
	 * @spring.validator type="required"
	 */	
	public void setMinQuantity(Integer aValue) {
		this.minQuantity = aValue;
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
		if (!(object instanceof WholesalePriceTbl)) {
			return false;
		}
		WholesalePriceTbl rhs = (WholesalePriceTbl) object;
		return new EqualsBuilder()
				.append(this.wholesalePriceId, rhs.wholesalePriceId)
				.append(this.price, rhs.price)
				.append(this.minQuantity, rhs.minQuantity)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.wholesalePriceId) 
				.append(this.price) 
				.append(this.minQuantity) 
						.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("wholesalePriceId", this.wholesalePriceId) 
				.append("price", this.price) 
				.append("minQuantity", this.minQuantity) 
						.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "wholesalePriceId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return wholesalePriceId;
	}

}
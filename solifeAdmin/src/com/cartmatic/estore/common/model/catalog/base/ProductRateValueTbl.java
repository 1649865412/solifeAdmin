package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductRateValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductRateValueTbl extends BaseObject implements Serializable {

    protected Integer productRateValueId;
	protected Integer rateValue;
	protected com.cartmatic.estore.common.model.catalog.ProductReview productReview;
	protected com.cartmatic.estore.common.model.catalog.ProductRateItem productRateItem;


	/**
	 * Default Empty Constructor for class ProductRateValue
	 */
	public ProductRateValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductRateValue
	 */
	public ProductRateValueTbl (
		 Integer in_productRateValueId
        ) {
		this.setProductRateValueId(in_productRateValueId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductReview getProductReview () {
		return productReview;
	}	
	
	public void setProductReview (com.cartmatic.estore.common.model.catalog.ProductReview in_productReview) {
		this.productReview = in_productReview;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductRateItem getProductRateItem () {
		return productRateItem;
	}	
	
	public void setProductRateItem (com.cartmatic.estore.common.model.catalog.ProductRateItem in_productRateItem) {
		this.productRateItem = in_productRateItem;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productRateValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductRateValueId() {
		return this.productRateValueId;
	}
	
	/**
	 * Set the productRateValueId
	 */	
	public void setProductRateValueId(Integer aValue) {
		this.productRateValueId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductRateItemId() {
		return this.getProductRateItem()==null?null:this.getProductRateItem().getProductRateItemId();
	}
	
	/**
	 * Set the productRateItemId
	 */	
	public void setProductRateItemId(Integer aValue) {
	    if (aValue==null) {
	    	productRateItem = null;
	    } else {
	    	productRateItem = new com.cartmatic.estore.common.model.catalog.ProductRateItem(aValue);
	        productRateItem.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductReviewId() {
		return this.getProductReview()==null?null:this.getProductReview().getProductReviewId();
	}
	
	/**
	 * Set the productReviewId
	 */	
	public void setProductReviewId(Integer aValue) {
	    if (aValue==null) {
	    	productReview = null;
	    }  else {
	    	productReview = new com.cartmatic.estore.common.model.catalog.ProductReview(aValue);
	        productReview.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 产品该评分项得分值	 * @return Integer
	 * @hibernate.property column="rateValue" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getRateValue() {
		return this.rateValue;
	}
	
	/**
	 * Set the rateValue
	 * @spring.validator type="required"
	 */	
	public void setRateValue(Integer aValue) {
		this.rateValue = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductRateValueTbl)) {
			return false;
		}
		ProductRateValueTbl rhs = (ProductRateValueTbl) object;
		return new EqualsBuilder()
				.append(this.productRateValueId, rhs.productRateValueId)
								.append(this.rateValue, rhs.rateValue)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productRateValueId) 
								.append(this.rateValue) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productRateValueId", this.productRateValueId) 
								.append("rateValue", this.rateValue) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productRateValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productRateValueId;
	}

}
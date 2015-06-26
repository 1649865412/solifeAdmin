package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductRateItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductRateItemTbl extends BaseObject implements Serializable {

    protected Integer productRateItemId;
	protected String rateName;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.ProductType productType;

	protected java.util.Set productRateValues = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class ProductRateItem
	 */
	public ProductRateItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductRateItem
	 */
	public ProductRateItemTbl (
		 Integer in_productRateItemId
        ) {
		this.setProductRateItemId(in_productRateItemId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductType getProductType () {
		return productType;
	}	
	
	public void setProductType (com.cartmatic.estore.common.model.catalog.ProductType in_productType) {
		this.productType = in_productType;
	}

	public java.util.Set getProductRateValues () {
		return productRateValues;
	}	
	
	public void setProductRateValues (java.util.Set in_productRateValues) {
		this.productRateValues = in_productRateValues;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productRateItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductRateItemId() {
		return this.productRateItemId;
	}
	
	/**
	 * Set the productRateItemId
	 */	
	public void setProductRateItemId(Integer aValue) {
		this.productRateItemId = aValue;
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
	 * 产品评分项名称	 * @return String
	 * @hibernate.property column="rateName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getRateName() {
		return this.rateName;
	}
	
	/**
	 * Set the rateName
	 * @spring.validator type="required"
	 */	
	public void setRateName(String aValue) {
		this.rateName = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductRateItemTbl)) {
			return false;
		}
		ProductRateItemTbl rhs = (ProductRateItemTbl) object;
		return new EqualsBuilder()
				.append(this.productRateItemId, rhs.productRateItemId)
						.append(this.rateName, rhs.rateName)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productRateItemId) 
						.append(this.rateName) 
				.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productRateItemId", this.productRateItemId) 
						.append("rateName", this.rateName) 
				.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productRateItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productRateItemId;
	}

}
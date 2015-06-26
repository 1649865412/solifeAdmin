package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductAttGroup Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductAttGroupTbl extends BaseObject implements Serializable {

    protected Integer productAttGroupId;
	protected Integer sortOrder;
	protected String productAttGroupName;
	protected com.cartmatic.estore.common.model.catalog.ProductType productType;

	protected java.util.Set productAttGroupItems = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class ProductAttGroup
	 */
	public ProductAttGroupTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAttGroup
	 */
	public ProductAttGroupTbl (
		 Integer in_productAttGroupId
        ) {
		this.setProductAttGroupId(in_productAttGroupId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductType getProductType () {
		return productType;
	}	
	
	public void setProductType (com.cartmatic.estore.common.model.catalog.ProductType in_productType) {
		this.productType = in_productType;
	}

	public java.util.Set getProductAttGroupItems () {
		return productAttGroupItems;
	}	
	
	public void setProductAttGroupItems (java.util.Set in_productAttGroupItems) {
		this.productAttGroupItems = in_productAttGroupItems;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productAttGroupId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductAttGroupId() {
		return this.productAttGroupId;
	}
	
	/**
	 * Set the productAttGroupId
	 */	
	public void setProductAttGroupId(Integer aValue) {
		this.productAttGroupId = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="productAttGroupName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getProductAttGroupName() {
		return this.productAttGroupName;
	}
	
	/**
	 * Set the productAttGroupName
	 * @spring.validator type="required"
	 */	
	public void setProductAttGroupName(String aValue) {
		this.productAttGroupName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductAttGroupTbl)) {
			return false;
		}
		ProductAttGroupTbl rhs = (ProductAttGroupTbl) object;
		return new EqualsBuilder()
				.append(this.productAttGroupId, rhs.productAttGroupId)
						.append(this.sortOrder, rhs.sortOrder)
				.append(this.productAttGroupName, rhs.productAttGroupName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productAttGroupId) 
						.append(this.sortOrder) 
				.append(this.productAttGroupName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productAttGroupId", this.productAttGroupId) 
						.append("sortOrder", this.sortOrder) 
				.append("productAttGroupName", this.productAttGroupName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productAttGroupId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productAttGroupId;
	}

}
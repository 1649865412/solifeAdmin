package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductAttGroupItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductAttGroupItemTbl extends BaseObject implements Serializable {

    protected Integer productAttGroupItemId;
	protected Integer productTypeId;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.ProductAttGroup productAttGroup;
	protected com.cartmatic.estore.common.model.attribute.Attribute attribute;


	/**
	 * Default Empty Constructor for class ProductAttGroupItem
	 */
	public ProductAttGroupItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAttGroupItem
	 */
	public ProductAttGroupItemTbl (
		 Integer in_productAttGroupItemId
        ) {
		this.setProductAttGroupItemId(in_productAttGroupItemId);
    }

	
	public com.cartmatic.estore.common.model.catalog.ProductAttGroup getProductAttGroup () {
		return productAttGroup;
	}	
	
	public void setProductAttGroup (com.cartmatic.estore.common.model.catalog.ProductAttGroup in_productAttGroup) {
		this.productAttGroup = in_productAttGroup;
	}
	
	public com.cartmatic.estore.common.model.attribute.Attribute getAttribute () {
		return attribute;
	}	
	
	public void setAttribute (com.cartmatic.estore.common.model.attribute.Attribute in_attribute) {
		this.attribute = in_attribute;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productAttGroupItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductAttGroupItemId() {
		return this.productAttGroupItemId;
	}
	
	/**
	 * Set the productAttGroupItemId
	 */	
	public void setProductAttGroupItemId(Integer aValue) {
		this.productAttGroupItemId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getProductAttGroupId() {
		return this.getProductAttGroup()==null?null:this.getProductAttGroup().getProductAttGroupId();
	}
	
	/**
	 * Set the productAttGroupId
	 */	
	public void setProductAttGroupId(Integer aValue) {
	    if (aValue==null) {
	    	productAttGroup = null;
	    }else {
	    	productAttGroup = new com.cartmatic.estore.common.model.catalog.ProductAttGroup(aValue);
	        productAttGroup.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getAttributeId() {
		return this.getAttribute()==null?null:this.getAttribute().getAttributeId();
	}
	
	/**
	 * Set the attributeId
	 */	
	public void setAttributeId(Integer aValue) {
	    if (aValue==null) {
	    	attribute = null;
	    } else {
	    	attribute = new com.cartmatic.estore.common.model.attribute.Attribute(aValue);
	        attribute.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 冗余字段	 * @return Integer
	 * @hibernate.property column="productTypeId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getProductTypeId() {
		return this.productTypeId;
	}
	
	/**
	 * Set the productTypeId
	 * @spring.validator type="required"
	 */	
	public void setProductTypeId(Integer aValue) {
		this.productTypeId = aValue;
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
		if (!(object instanceof ProductAttGroupItemTbl)) {
			return false;
		}
		ProductAttGroupItemTbl rhs = (ProductAttGroupItemTbl) object;
		return new EqualsBuilder()
				.append(this.productAttGroupItemId, rhs.productAttGroupItemId)
								.append(this.productTypeId, rhs.productTypeId)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productAttGroupItemId) 
								.append(this.productTypeId) 
				.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productAttGroupItemId", this.productAttGroupItemId) 
								.append("productTypeId", this.productTypeId) 
				.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productAttGroupItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productAttGroupItemId;
	}

}
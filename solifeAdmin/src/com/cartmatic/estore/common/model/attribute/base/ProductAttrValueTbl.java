package com.cartmatic.estore.common.model.attribute.base;

import java.io.Serializable;
import java.util.Date;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductAttrValue Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductAttrValueTbl extends BaseAttributeValue implements Serializable {

    protected Integer productAttrValueId;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	protected ProductAttGroupItem productAttGroupItem;


	public ProductAttGroupItem getProductAttGroupItem() {
		return productAttGroupItem;
	}

	public void setProductAttGroupItem(ProductAttGroupItem productAttGroupItem) {
		this.productAttGroupItem = productAttGroupItem;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getProductAttGroupItemId() {
		return this.getProductAttGroupItem()==null?null:this.getProductAttGroupItem().getProductAttGroupItemId();
	}
	
	/**
	 * Set the productAttGroupItemId
	 */	
	public void setProductAttGroupItemId(Integer aValue) {
	    if (aValue==null) {
	    	productAttGroupItem = null;
	    } else if (productAttGroupItem == null) {
	    	productAttGroupItem = new com.cartmatic.estore.common.model.catalog.ProductAttGroupItem(aValue);
	    	productAttGroupItem.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	productAttGroupItem.setProductAttGroupItemId(aValue);
	    }
	}

	/**
	 * Default Empty Constructor for class ProductAttrValue
	 */
	public ProductAttrValueTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAttrValue
	 */
	public ProductAttrValueTbl (
		 Integer in_productAttrValueId
        ) {
		this.setProductAttrValueId(in_productAttrValueId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
	
	/**
	 * 	 * @return Integer
     * @hibernate.id column="productAttrValueId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductAttrValueId() {
		return this.productAttrValueId;
	}
	
	/**
	 * Set the productAttrValueId
	 */	
	public void setProductAttrValueId(Integer aValue) {
		this.productAttrValueId = aValue;
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
	    } else if (attribute == null) {
	        attribute = new com.cartmatic.estore.common.model.attribute.Attribute(aValue);
	        attribute.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			attribute.setAttributeId(aValue);
	    }
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
	    } else if (product == null) {
	        product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			product.setProductId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductAttrValueTbl)) {
			return false;
		}
		ProductAttrValueTbl rhs = (ProductAttrValueTbl) object;
		return new EqualsBuilder()
				.append(this.productAttrValueId, rhs.productAttrValueId)
								.append(this.shortTextValue, rhs.shortTextValue)
				.append(this.longTextValue, rhs.longTextValue)
				.append(this.intValue, rhs.intValue)
				.append(this.decimalValue, rhs.decimalValue)
				.append(this.booleanValue, rhs.booleanValue)
				.append(this.attributeDataType, rhs.attributeDataType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productAttrValueId) 
								.append(this.shortTextValue) 
				.append(this.longTextValue) 
				.append(this.intValue) 
				.append(this.decimalValue) 
				.append(this.booleanValue) 
				.append(this.attributeDataType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productAttrValueId", this.productAttrValueId) 
								.append("shortTextValue", this.shortTextValue) 
				.append("longTextValue", this.longTextValue) 
				.append("intValue", this.intValue) 
				.append("decimalValue", this.decimalValue) 
				.append("booleanValue", this.booleanValue) 
				.append("attributeDateType", this.attributeDataType) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productAttrValueId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productAttrValueId;
	}
}
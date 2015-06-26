package com.cartmatic.estore.common.model.catalog.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ProductCategory Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProductCategoryTbl extends BaseObject implements Serializable {

    protected Integer productCategoryId;
	protected Short isMainCategory;
	protected String categoryPath;
	protected Integer sortOrder;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	protected com.cartmatic.estore.common.model.catalog.Category category;


	/**
	 * Default Empty Constructor for class ProductCategory
	 */
	public ProductCategoryTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductCategory
	 */
	public ProductCategoryTbl (
		 Integer in_productCategoryId
        ) {
		this.setProductCategoryId(in_productCategoryId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
	
	public com.cartmatic.estore.common.model.catalog.Category getCategory () {
		return category;
	}	
	
	public void setCategory (com.cartmatic.estore.common.model.catalog.Category in_category) {
		this.category = in_category;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="productCategoryId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getProductCategoryId() {
		return this.productCategoryId;
	}
	
	/**
	 * Set the productCategoryId
	 */	
	public void setProductCategoryId(Integer aValue) {
		this.productCategoryId = aValue;
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

	/**
	 * 是否主目录	 * @return Short
	 * @hibernate.property column="isMainCategory" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsMainCategory() {
		return this.isMainCategory;
	}
	
	/**
	 * Set the isMainCategory
	 * @spring.validator type="required"
	 */	
	public void setIsMainCategory(Short aValue) {
		this.isMainCategory = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="categoryPath" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCategoryPath() {
		return this.categoryPath;
	}
	
	/**
	 * Set the categoryPath
	 * @spring.validator type="required"
	 */	
	public void setCategoryPath(String aValue) {
		this.categoryPath = aValue;
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
		if (!(object instanceof ProductCategoryTbl)) {
			return false;
		}
		ProductCategoryTbl rhs = (ProductCategoryTbl) object;
		return new EqualsBuilder()
				.append(this.productCategoryId, rhs.productCategoryId)
								.append(this.isMainCategory, rhs.isMainCategory)
				.append(this.categoryPath, rhs.categoryPath)
				.append(this.sortOrder, rhs.sortOrder)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productCategoryId) 
								.append(this.isMainCategory) 
				.append(this.categoryPath) 
				.append(this.sortOrder) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productCategoryId", this.productCategoryId) 
								.append("isMainCategory", this.isMainCategory) 
				.append("categoryPath", this.categoryPath) 
				.append("sortOrder", this.sortOrder) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "productCategoryId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return productCategoryId;
	}

}
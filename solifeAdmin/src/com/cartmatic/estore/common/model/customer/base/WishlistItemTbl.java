package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;
import java.util.HashMap;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * WishlistItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WishlistItemTbl extends BaseObject implements Serializable {

    protected Integer wishlistItemId;
	protected Integer quantity;
	protected Integer purchasedQty;
	protected String shortDescription;
	protected HashMap productAttributes;
	protected java.util.Date createTime;
	protected Integer version;
	protected com.cartmatic.estore.common.model.catalog.Product product;
	protected com.cartmatic.estore.common.model.customer.Wishlist wishlist;


	/**
	 * Default Empty Constructor for class WishlistItem
	 */
	public WishlistItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WishlistItem
	 */
	public WishlistItemTbl (
		 Integer in_wishlistItemId
        ) {
		this.setWishlistItemId(in_wishlistItemId);
    }

	
	public com.cartmatic.estore.common.model.catalog.Product getProduct () {
		return product;
	}	
	
	public void setProduct (com.cartmatic.estore.common.model.catalog.Product in_product) {
		this.product = in_product;
	}
	
	public com.cartmatic.estore.common.model.customer.Wishlist getWishlist () {
		return wishlist;
	}	
	
	public void setWishlist (com.cartmatic.estore.common.model.customer.Wishlist in_wishlist) {
		this.wishlist = in_wishlist;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="wishlistItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getWishlistItemId() {
		return this.wishlistItemId;
	}
	
	/**
	 * Set the wishlistItemId
	 */	
	public void setWishlistItemId(Integer aValue) {
		this.wishlistItemId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getWishListId() {
		return this.getWishlist()==null?null:this.getWishlist().getWishListId();
	}
	
	/**
	 * Set the wishListId
	 */	
	public void setWishListId(Integer aValue) {
	    if (aValue==null) {
	    	wishlist = null;
	    } else {
	        wishlist = new com.cartmatic.estore.common.model.customer.Wishlist(aValue);
	        wishlist.setVersion(new Integer(0));//set a version to cheat hibernate only
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
	    } else {
	        product = new com.cartmatic.estore.common.model.catalog.Product(aValue);
	        product.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="quantity" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Set the quantity
	 */	
	public void setQuantity(Integer aValue) {
		this.quantity = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="purchasedQty" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPurchasedQty() {
		return this.purchasedQty;
	}
	
	/**
	 * Set the purchasedQty
	 */	
	public void setPurchasedQty(Integer aValue) {
		this.purchasedQty = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shortDescription" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	/**
	 * Set the shortDescription
	 */	
	public void setShortDescription(String aValue) {
		this.shortDescription = aValue;
	}	



	public HashMap getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(HashMap productAttributes) {
		this.productAttributes = productAttributes;
	}

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 * @spring.validator type="required"
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WishlistItemTbl)) {
			return false;
		}
		WishlistItemTbl rhs = (WishlistItemTbl) object;
		return new EqualsBuilder()
				.append(this.wishlistItemId, rhs.wishlistItemId)
								.append(this.quantity, rhs.quantity)
				.append(this.purchasedQty, rhs.purchasedQty)
				.append(this.shortDescription, rhs.shortDescription)
				.append(this.productAttributes, rhs.productAttributes)
				.append(this.createTime, rhs.createTime)
				.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.wishlistItemId) 
								.append(this.quantity) 
				.append(this.purchasedQty) 
				.append(this.shortDescription) 
				.append(this.productAttributes) 
				.append(this.createTime) 
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("wishlistItemId", this.wishlistItemId) 
								.append("quantity", this.quantity) 
				.append("purchasedQty", this.purchasedQty) 
				.append("shortDescription", this.shortDescription) 
				.append("productAttributes", this.productAttributes) 
				.append("createTime", this.createTime) 
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "wishlistItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return wishlistItemId;
	}

}
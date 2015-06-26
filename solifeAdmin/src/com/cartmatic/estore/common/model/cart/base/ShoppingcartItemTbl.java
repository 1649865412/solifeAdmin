package com.cartmatic.estore.common.model.cart.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShoppingcartItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShoppingcartItemTbl extends BaseObject implements Serializable {

    protected Integer shoppingcartItemId;
	protected ShoppingcartItem parent;
	protected ProductSku productSku;
	protected Short skuType;
	protected java.math.BigDecimal price;
	protected java.math.BigDecimal discountPrice;
	protected Integer quantity;
	protected java.math.BigDecimal itemDiscountAmount = new BigDecimal(0);
	protected Short isWholesale = 0;
	protected Short isOnSale = 0;
	protected Short isSaved;
	protected java.util.Date addTime;
	protected Short itemType;
	protected String accessories;
	
	private Shoppingcart shoppingcart;
	
	private java.util.Set<ShoppingcartItem> shoppingcartItems = new java.util.HashSet<ShoppingcartItem>();
	private ShoppingcartItemGc shoppingcartItemGc;


	public ShoppingcartItem getParent() {
		return parent;
	}

	public void setParent(ShoppingcartItem parent) {
		this.parent = parent;
	}

	public java.util.Set<ShoppingcartItem> getShoppingcartItems() {
		return shoppingcartItems;
	}

	public void setShoppingcartItems(
			java.util.Set<ShoppingcartItem> shoppingcartItems) {
		this.shoppingcartItems = shoppingcartItems;
	}
	
	

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	/**
	 * Default Empty Constructor for class ShoppingcartItem
	 */
	public ShoppingcartItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShoppingcartItem
	 */
	public ShoppingcartItemTbl (
		 Integer in_shoppingcartItemId
        ) {
		this.setShoppingcartItemId(in_shoppingcartItemId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shoppingcartItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShoppingcartItemId() {
		return this.shoppingcartItemId;
	}
	
	/**
	 * Set the shoppingcartItemId
	 */	
	public void setShoppingcartItemId(Integer aValue) {
		this.shoppingcartItemId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="itemType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getSkuType() {
		return this.skuType;
	}
	
	/**
	 * Set the itemType
	 * @spring.validator type="required"
	 */	
	public void setSkuType(Short aValue) {
		this.skuType = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="discountPrice" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDiscountPrice() {
		return this.discountPrice;
	}
	
	/**
	 * Set the discountPrice
	 */	
	public void setDiscountPrice(java.math.BigDecimal aValue) {
		this.discountPrice = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="quantity" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getQuantity() {
		return this.quantity;
	}
	
	/**
	 * Set the quantity
	 * @spring.validator type="required"
	 */	
	public void setQuantity(Integer aValue) {
		this.quantity = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="itemDiscountAmount" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getItemDiscountAmount() {
		return this.itemDiscountAmount;
	}
	
	/**
	 * Set the itemDiscountAmount
	 */	
	public void setItemDiscountAmount(java.math.BigDecimal aValue) {
		this.itemDiscountAmount = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isWholesale" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsWholesale() {
		return this.isWholesale;
	}
	
	/**
	 * Set the isWholesale
	 * @spring.validator type="required"
	 */	
	public void setIsWholesale(Short aValue) {
		this.isWholesale = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isOnSale" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsOnSale() {
		return this.isOnSale;
	}
	
	/**
	 * Set the isOnSale
	 * @spring.validator type="required"
	 */	
	public void setIsOnSale(Short aValue) {
		this.isOnSale = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isSaved" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsSaved() {
		return this.isSaved;
	}
	
	/**
	 * Set the isSaved
	 * @spring.validator type="required"
	 */	
	public void setIsSaved(Short aValue) {
		this.isSaved = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="addTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getAddTime() {
		return this.addTime;
	}
	
	/**
	 * Set the addTime
	 * @spring.validator type="required"
	 */	
	public void setAddTime(java.util.Date aValue) {
		this.addTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShoppingcartItemTbl)) {
			return false;
		}
		ShoppingcartItemTbl rhs = (ShoppingcartItemTbl) object;
		return new EqualsBuilder()
				.append(this.shoppingcartItemId, rhs.shoppingcartItemId)
				.append(this.skuType, rhs.skuType)
				.append(this.price, rhs.price)
				.append(this.discountPrice, rhs.discountPrice)
				.append(this.quantity, rhs.quantity)
				.append(this.itemDiscountAmount, rhs.itemDiscountAmount)
				.append(this.isWholesale, rhs.isWholesale)
				.append(this.isOnSale, rhs.isOnSale)
				.append(this.isSaved, rhs.isSaved)
				.append(this.addTime, rhs.addTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shoppingcartItemId) 
				.append(this.skuType) 
				.append(this.price) 
				.append(this.discountPrice) 
				.append(this.quantity) 
				.append(this.itemDiscountAmount) 
				.append(this.isWholesale) 
				.append(this.isOnSale) 
				.append(this.isSaved) 
				.append(this.addTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shoppingcartItemId", this.shoppingcartItemId) 
				.append("itemType", this.skuType) 
				.append("price", this.price) 
				.append("discountPrice", this.discountPrice) 
				.append("quantity", this.quantity) 
				.append("itemDiscountAmount", this.itemDiscountAmount) 
				.append("isWholesale", this.isWholesale) 
				.append("isOnSale", this.isOnSale) 
				.append("isSaved", this.isSaved) 
				.append("addTime", this.addTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shoppingcartItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shoppingcartItemId;
	}

	public Shoppingcart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(Shoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public Short getItemType() {
		return itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public ShoppingcartItemGc getShoppingcartItemGc() {
		return shoppingcartItemGc;
	}

	public void setShoppingcartItemGc(ShoppingcartItemGc shoppingcartItemGc) {
		this.shoppingcartItemGc = shoppingcartItemGc;
	}
}
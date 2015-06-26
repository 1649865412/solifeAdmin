package com.cartmatic.estore.common.model.cart.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.util.CheckoutUtil;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * Shoppingcart Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShoppingcartTbl extends BaseObject implements Serializable {

    protected Integer shoppingcartId;
	protected String uuid;
	protected String usedCouponNo;
	protected Short isUsedCoupon;
	protected java.math.BigDecimal cartDiscountAmount= new BigDecimal(0);
	protected String shippingDiscountInfo = "";
	protected Integer gainedPoint= new Integer(0);
	protected Integer gainedCouponTypeId= null;
	protected java.math.BigDecimal subtotal = new BigDecimal(0);
	protected java.math.BigDecimal total = new BigDecimal(0);
	protected Integer itemsCount = new Integer(0);
	protected Integer buyNowItemsCount= new Integer(0);
	protected Integer buyLaterItemsCount= new Integer(0);
	protected java.util.Date createTime;
	protected java.util.Date updateTime;
	
	protected Integer customerId;
	
	private Set<ShoppingcartItem> shoppingCartItems = new HashSet<ShoppingcartItem>();
	private Set<ShoppingcartPromotion> shoppingCartPromotions = new HashSet<ShoppingcartPromotion>();

	protected Store store;
	
	protected String giftCertificateNos;
	protected Integer shopPoint;

	/**
	 * Default Empty Constructor for class Shoppingcart
	 */
	public ShoppingcartTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Shoppingcart
	 */
	public ShoppingcartTbl (
		 Integer in_shoppingCartId
        ) {
		this.setShoppingcartId(in_shoppingCartId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shoppingCartId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShoppingcartId() {
		return this.shoppingcartId;
	}
	
	/**
	 * Set the shoppingCartId
	 */	
	public void setShoppingcartId(Integer aValue) {
		this.shoppingcartId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="Uuid" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getUuid() {
		return this.uuid;
	}
	
	/**
	 * Set the uuid
	 * @spring.validator type="required"
	 */	
	public void setUuid(String aValue) {
		this.uuid = aValue;
	}	


	/**
	 * Set the usedCouponNo
	 */	

	public String getUsedCouponNo() {
		return usedCouponNo;
	}
	/**
	 * 	 * @return String
	 * @hibernate.property column="usedCouponNo" type="java.lang.String" length="16" not-null="false" unique="false"
	 */
	public void setUsedCouponNo(String usedCouponNo) {
		this.usedCouponNo = usedCouponNo;
	}

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isUsedCoupon" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsUsedCoupon() {
		return this.isUsedCoupon;
	}
	
	/**
	 * Set the isUsedCoupon
	 * @spring.validator type="required"
	 */	
	public void setIsUsedCoupon(Short aValue) {
		this.isUsedCoupon = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="cartDiscountAmount" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCartDiscountAmount() {
		return this.cartDiscountAmount;
	}
	
	/**
	 * Set the cartDiscountAmount
	 */	
	public void setCartDiscountAmount(java.math.BigDecimal aValue) {
		this.cartDiscountAmount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shippingDiscountInfo" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getShippingDiscountInfo() {
		return this.shippingDiscountInfo;
	}
	
	/**
	 * Set the shippingDiscountInfo
	 */	
	public void setShippingDiscountInfo(String aValue) {
		this.shippingDiscountInfo = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="gainedPoint" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getGainedPoint() {
		return this.gainedPoint;
	}
	
	/**
	 * Set the gainedPoint
	 */	
	public void setGainedPoint(Integer aValue) {
		this.gainedPoint = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="gainedCouponTypeId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getGainedCouponTypeId() {
		return this.gainedCouponTypeId;
	}
	
	/**
	 * Set the gainedCouponTypeId
	 */	
	public void setGainedCouponTypeId(Integer aValue) {
		this.gainedCouponTypeId = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="subtotal" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getSubtotal() {
		return this.subtotal;
	}
	
	/**
	 * Set the subtotal
	 * @spring.validator type="required"
	 */	
	public void setSubtotal(java.math.BigDecimal aValue) {
		this.subtotal = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="total" type="java.math.BigDecimal" length="12" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getTotal() {
		return this.total;
	}
	
	/**
	 * Set the total
	 * @spring.validator type="required"
	 */	
	public void setTotal(java.math.BigDecimal aValue) {
		this.total = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="itemsCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getItemsCount() {
		if(this.shoppingCartItems==null)
			return Integer.valueOf(0);
        Integer con = 0;		
		for(ShoppingcartItem item:shoppingCartItems){
			if(item.getParent()!=null&&item.getProductSku().getIsFictitious())continue;
			con +=item.getQuantity();
		}
		return con;
	}
	
	/**
	 * Set the itemsCount
	 */	
	public void setItemsCount(Integer aValue) {
		if(this.shoppingCartItems==null){
			this.itemsCount = Integer.valueOf(0);
			return;
		}
        Integer con = 0;		
		for(ShoppingcartItem item:shoppingCartItems){
			if(item.getParent()!=null&&item.getProductSku().getIsFictitious())continue;
			con += item.getQuantity();
		}
		this.itemsCount = con;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="buyNowItemsCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBuyNowItemsCount() {
		if(this.shoppingCartItems==null)
			return 0;
		else{
			int con = 0;
			for(ShoppingcartItem item:this.shoppingCartItems){
				if(item.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_YES)
					continue;
				else if(item.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()&&item.getProductSku().getIsFictitious()&&item.getParent()!=null)
					continue;
				else{
					con += item.getQuantity();
				}
			}
			return con;
		}
	}
	
	/**
	 * Set the buyNowItemsCount
	 */	
	public void setBuyNowItemsCount(Integer aValue) {
		if(this.shoppingCartItems==null)
			this.buyNowItemsCount = 0;
		else{
			int con = 0;
			for(ShoppingcartItem item:this.shoppingCartItems){
				if(item.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_YES)
					continue;
				else if(item.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()&&item.getProductSku().getIsFictitious()&&item.getParent()!=null)
					continue;
				else{
					con += item.getQuantity();
				}
			}
			this.buyNowItemsCount = con;
		}
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="buyLaterItemsCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBuyLaterItemsCount() {
		if(this.shoppingCartItems==null)
			return 0;
		else{
			int con = 0;
			for(ShoppingcartItem item:this.shoppingCartItems){
				if(item.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_NO)
					continue;
				else if(item.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()&&item.getProductSku().getIsFictitious()&&item.getParent()!=null)
					continue;
				else{
					con += item.getQuantity();
				}
			}
			return con;
		}
	}
	
	/**
	 * Set the buyLaterItemsCount
	 */	
	public void setBuyLaterItemsCount(Integer aValue) {
		if(this.shoppingCartItems==null)
			this.buyLaterItemsCount = 0;
		else{
			int con = 0;
			for(ShoppingcartItem item:this.shoppingCartItems){
				if(item.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_NO)
					continue;
				else if(item.getItemType().shortValue()==Constants.ITEM_TYPE_PRODUCT.shortValue()&&item.getProductSku().getIsFictitious()&&item.getParent()!=null)
					continue;
				else{
					con += item.getQuantity();
				}
			}
			this.buyLaterItemsCount = con;
		}
	}	  

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 * @spring.validator type="required"
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="0" not-null="true" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 * @spring.validator type="required"
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShoppingcartTbl)) {
			return false;
		}
		ShoppingcartTbl rhs = (ShoppingcartTbl) object;
		return new EqualsBuilder()
				.append(this.shoppingcartId, rhs.shoppingcartId)
				.append(this.uuid, rhs.uuid)
				.append(this.isUsedCoupon, rhs.isUsedCoupon)
				.append(this.cartDiscountAmount, rhs.cartDiscountAmount)
				.append(this.shippingDiscountInfo, rhs.shippingDiscountInfo)
				.append(this.gainedPoint, rhs.gainedPoint)
				.append(this.gainedCouponTypeId, rhs.gainedCouponTypeId)
				.append(this.subtotal, rhs.subtotal)
				.append(this.total, rhs.total)
				.append(this.itemsCount, rhs.itemsCount)
				.append(this.buyNowItemsCount, rhs.buyNowItemsCount)
				.append(this.buyLaterItemsCount, rhs.buyLaterItemsCount)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shoppingcartId) 
				.append(this.uuid) 
				.append(this.isUsedCoupon) 
				.append(this.cartDiscountAmount) 
				.append(this.shippingDiscountInfo) 
				.append(this.gainedPoint) 
				.append(this.gainedCouponTypeId) 
				.append(this.subtotal) 
				.append(this.total) 
				.append(this.itemsCount) 
				.append(this.buyNowItemsCount) 
				.append(this.buyLaterItemsCount) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shoppingCartId", this.shoppingcartId) 
				.append("uuid", this.uuid) 
				.append("isUsedCoupon", this.isUsedCoupon) 
				.append("cartDiscountAmount", this.cartDiscountAmount) 
				.append("shippingDiscountInfo", this.shippingDiscountInfo) 
				.append("gainedPoint", this.gainedPoint) 
				.append("gainedCouponTypeId", this.gainedCouponTypeId) 
				.append("subtotal", this.subtotal) 
				.append("total", this.total) 
				.append("itemsCount", this.itemsCount) 
				.append("buyNowItemsCount", this.buyNowItemsCount) 
				.append("buyLaterItemsCount", this.buyLaterItemsCount) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shoppingCartId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shoppingcartId;
	}

	public Set<ShoppingcartItem> getShoppingCartItems() {
		return this.shoppingCartItems;
	}

	public void setShoppingCartItems(Set<ShoppingcartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}
	
	public void addShoppingCartItem(ShoppingcartItem _cartItem){
		this.shoppingCartItems.add(_cartItem);
	}

	public Set<ShoppingcartPromotion> getShoppingCartPromotions() {
		return shoppingCartPromotions;
	}
	
	
	public void setShoppingCartPromotions(
			Set<ShoppingcartPromotion> shoppingCartPromotions) {
		this.shoppingCartPromotions = shoppingCartPromotions;
	}
	
	public void addShoppingCartPromotion(ShoppingcartPromotion _promo){
		this.shoppingCartPromotions.add(_promo);
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void removeShoppingcartItem(ShoppingcartItem item){
		this.shoppingCartItems.remove(item);
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getStoreId() {
		return this.getStore()==null?null:this.getStore().getStoreId();
	}
	
	/**
	 * Set the storeId
	 */	
	public void setStoreId(Integer aValue) {
	    if (aValue==null) {
	    	store = null;
	    } else {
	    	store = new Store(aValue);
	    	store.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}

	public String getGiftCertificateNos() {
		return giftCertificateNos;
	}

	public void setGiftCertificateNos(String giftCertificateNos) {
		this.giftCertificateNos = giftCertificateNos;
	}

	public Integer getShopPoint() {
		return shopPoint;
	}

	public void setShopPoint(Integer shopPoint) {
		this.shopPoint = shopPoint;
	}

}
package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderSku Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderSkuTbl extends BaseObject implements Serializable {

    protected Integer orderSkuId;
    protected Integer productId;
	protected String productName;
	protected String productSkuCode;
	protected Short itemType;
	protected Integer quantity;
	protected java.math.BigDecimal price;
	protected java.math.BigDecimal discountPrice;
	protected Short isOnSale;
	protected Short isWholesale;
	protected java.math.BigDecimal tax;
	protected java.math.BigDecimal costPrice;
	protected String taxName;
	protected java.math.BigDecimal itemDiscountAmount;
	protected java.math.BigDecimal weight;
	protected Integer allocatedQuantity;
	protected Integer allocatedPreQty=0;
	protected Integer version;
	protected String displaySkuOptions;
	protected String accessories;
	protected com.cartmatic.estore.common.model.sales.GiftCertificate giftCertificate;
	protected com.cartmatic.estore.common.model.catalog.ProductSku productSku;
	protected com.cartmatic.estore.common.model.order.OrderShipment orderShipment;

	protected java.util.Set orderReturnSkus = new java.util.HashSet();
	protected java.util.Set purchaseOrderItems = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OrderSku
	 */
	public OrderSkuTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderSku
	 */
	public OrderSkuTbl (
		 Integer in_orderSkuId
        ) {
		this.setOrderSkuId(in_orderSkuId);
    }

	
	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public com.cartmatic.estore.common.model.sales.GiftCertificate getGiftCertificate () {
		return giftCertificate;
	}	
	
	public void setGiftCertificate (com.cartmatic.estore.common.model.sales.GiftCertificate in_giftCertificate) {
		this.giftCertificate = in_giftCertificate;
	}
	
	public com.cartmatic.estore.common.model.catalog.ProductSku getProductSku () {
		return productSku;
	}	
	
	public void setProductSku (com.cartmatic.estore.common.model.catalog.ProductSku in_productSku) {
		this.productSku = in_productSku;
	}
	
	public com.cartmatic.estore.common.model.order.OrderShipment getOrderShipment () {
		return orderShipment;
	}	
	
	public void setOrderShipment (com.cartmatic.estore.common.model.order.OrderShipment in_orderShipment) {
		this.orderShipment = in_orderShipment;
	}

	public java.util.Set getOrderReturnSkus () {
		return orderReturnSkus;
	}	
	
	public void setOrderReturnSkus (java.util.Set in_orderReturnSkus) {
		this.orderReturnSkus = in_orderReturnSkus;
	}
	
	public java.util.Set getPurchaseOrderItems () {
		return purchaseOrderItems;
	}	
	
	public void setPurchaseOrderItems (java.util.Set in_orderReturnSkus) {
		this.purchaseOrderItems = in_orderReturnSkus;
	}
	

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderSkuId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderSkuId() {
		return this.orderSkuId;
	}
	
	/**
	 * Set the orderSkuId
	 */	
	public void setOrderSkuId(Integer aValue) {
		this.orderSkuId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getGiftCertificateId() {
		return this.getGiftCertificate()==null?null:this.getGiftCertificate().getGiftCertificateId();
	}
	
	/**
	 * Set the giftCertificateId
	 */	
	public void setGiftCertificateId(Integer aValue) {
	    if (aValue==null) {
	    	giftCertificate = null;
	    } else {
	        giftCertificate = new com.cartmatic.estore.common.model.sales.GiftCertificate(aValue);
	        giftCertificate.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
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
	 * 	 * @return Integer
	 */
	public Integer getOrderShipmentId() {
		return this.getOrderShipment()==null?null:this.getOrderShipment().getOrderShipmentId();
	}
	
	/**
	 * Set the orderShipmentId
	 */	
	public void setOrderShipmentId(Integer aValue) {
	    if (aValue==null) {
	    	orderShipment = null;
	    } else {
	        orderShipment = new com.cartmatic.estore.common.model.order.OrderShipment(aValue);
	        orderShipment.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 * @spring.validator type="required"
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="productSkuCode" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getProductSkuCode() {
		return this.productSkuCode;
	}
	
	/**
	 * Set the productSkuCode
	 */	
	public void setProductSkuCode(String aValue) {
		this.productSkuCode = aValue;
	}	

	/**
	 * types of items/goods	 * @return Short
	 * @hibernate.property column="itemType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getItemType() {
		return this.itemType;
	}
	
	/**
	 * Set the itemType
	 * @spring.validator type="required"
	 */	
	public void setItemType(Short aValue) {
		this.itemType = aValue;
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
	 * price after discounted	 * @return java.math.BigDecimal
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

	
	public java.math.BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(java.math.BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * 是否特价产品	 * @return Short
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
	 * 是否批发产品	 * @return Short
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="tax" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getTax() {
		if(this.tax==null)
			return java.math.BigDecimal.ZERO;
		return this.tax;
	}
	
	/**
	 * Set the tax
	 */	
	public void setTax(java.math.BigDecimal aValue) {
		this.tax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="taxName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getTaxName() {
		return this.taxName;
	}
	
	/**
	 * Set the taxName
	 */	
	public void setTaxName(String aValue) {
		this.taxName = aValue;
	}	

	/**
	 * itemTotal (before tax) = discountPrice * quantity  - itemDiscountAmount	 * @return java.math.BigDecimal
	 * @hibernate.property column="itemDiscountAmount" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getItemDiscountAmount() {
		if(this.itemDiscountAmount==null)
			return java.math.BigDecimal.ZERO;
		return this.itemDiscountAmount;
	}
	
	/**
	 * Set the itemDiscountAmount
	 */	
	public void setItemDiscountAmount(java.math.BigDecimal aValue) {
		this.itemDiscountAmount = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="weight" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getWeight() {
		return this.weight;
	}
	
	/**
	 * Set the weight
	 */	
	public void setWeight(java.math.BigDecimal aValue) {
		this.weight = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="allocatedQuantity" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getAllocatedQuantity() {
		return this.allocatedQuantity;
	}
	
	/**
	 * Set the allocatedQuantity
	 */	
	public void setAllocatedQuantity(Integer aValue) {
		this.allocatedQuantity = aValue;
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
		if (!(object instanceof OrderSkuTbl)) {
			return false;
		}
		OrderSkuTbl rhs = (OrderSkuTbl) object;
		return new EqualsBuilder()
				.append(this.orderSkuId, rhs.orderSkuId)
										.append(this.productName, rhs.productName)
				.append(this.productSkuCode, rhs.productSkuCode)
				.append(this.itemType, rhs.itemType)
				.append(this.quantity, rhs.quantity)
				.append(this.price, rhs.price)
				.append(this.discountPrice, rhs.discountPrice)
				.append(this.costPrice, rhs.costPrice)
				.append(this.isOnSale, rhs.isOnSale)
				.append(this.isWholesale, rhs.isWholesale)
				.append(this.tax, rhs.tax)
				.append(this.taxName, rhs.taxName)
				.append(this.itemDiscountAmount, rhs.itemDiscountAmount)
				.append(this.weight, rhs.weight)
				.append(this.allocatedQuantity, rhs.allocatedQuantity)
				.append(this.accessories, rhs.accessories)
				.append(this.version, rhs.version)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderSkuId) 
										.append(this.productName) 
				.append(this.productSkuCode) 
				.append(this.itemType) 
				.append(this.quantity) 
				.append(this.price) 
				.append(this.discountPrice) 
				.append(this.isOnSale) 
				.append(this.isWholesale) 
				.append(this.tax) 
				.append(this.taxName) 
				.append(this.itemDiscountAmount)
				.append(this.costPrice)
				.append(this.weight) 
				.append(this.allocatedQuantity) 
				.append(this.accessories)
				.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderSkuId", this.orderSkuId) 
										.append("productName", this.productName) 
				.append("productSkuCode", this.productSkuCode) 
				.append("itemType", this.itemType) 
				.append("quantity", this.quantity) 
				.append("price", this.price) 
				.append("discountPrice", this.discountPrice) 
				.append("isOnSale", this.isOnSale) 
				.append("isWholesale", this.isWholesale) 
				.append("tax", this.tax) 
				.append("taxName", this.taxName) 
				.append("itemDiscountAmount", this.itemDiscountAmount) 
				.append("weight", this.weight) 
				.append("allocatedQuantity", this.allocatedQuantity) 
				.append("accessories", this.accessories)
				.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderSkuId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderSkuId;
	}

	public String getDisplaySkuOptions() {
		return displaySkuOptions;
	}

	public void setDisplaySkuOptions(String displaySkuOptions) {
		this.displaySkuOptions = displaySkuOptions;
	}

	public Integer getAllocatedPreQty() {
		return allocatedPreQty;
	}

	public void setAllocatedPreQty(Integer allocatedPreQty) {
		this.allocatedPreQty = allocatedPreQty;
	}

}
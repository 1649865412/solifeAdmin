package com.cartmatic.estore.common.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.base.ShoppingcartItemTbl;
import com.cartmatic.estore.common.model.catalog.Product;

/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class ShoppingcartItem extends ShoppingcartItemTbl {
	
	public static final short ISONSALE_YES = 1;
	public static final short ISONSALE_NO = 0;
	public static final short ISWHOLESALE_YES = 1;
	public static final short ISWHOLESALE_NO = 0;
	public static final short ISSAVED_YES = 1;
	public static final short ISSAVED_NO = 0;

    private boolean hasDiscount = false;
    private boolean dbDiscount = false;
    
    private String taxName;
    private BigDecimal tax = new BigDecimal(0);
    /**
     * for conplict shipping info i18nkey
     */
    private String shippingInfoCode = null;
    
    private List shippingRates=new ArrayList();
    /**
     * this shoppingcartItem is conplict or not
     */
    private boolean isConplict = false;
    
    private BigDecimal discountPriceRAM = new BigDecimal(0);
    
    private Integer discountQuantity = 0;
    
    private Integer discountQuantityRAM = 0;
    
    private boolean catalogConditonFlagRAM = false;
    private boolean catalogConditionRunInAllFlagRAM = true;
    private boolean catalogConditionRunInAnyFlagRAM = false;
       
    private BigDecimal total;
    
    private Integer maxOrderQuantity;
    
    public boolean isDbDiscount()
    {
        return dbDiscount;
    }
    public void setDbDiscount(boolean dbDiscount)
    {
        this.dbDiscount = dbDiscount;
    }
    public boolean isHasDiscount()
    {
        return hasDiscount;
    }
    public void setHasDiscount(boolean hasDiscount)
    {
        this.hasDiscount = hasDiscount;
    }	
	
  	/**
	 * Default Empty Constructor for class ShoppingcartItem
	 */
	public ShoppingcartItem () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShoppingcartItem
	 */
	public ShoppingcartItem (
		 Integer in_shoppingcartItemId
		) {
		super (
		  in_shoppingcartItemId
		);
	}
	

	
	/**
	 * @return isConplict
	 */
	public boolean getIsConplict() {
		return isConplict;
	}
	/**
	 * @param isConplict setter isConplict
	 */
	public void setConplict(boolean isConplict) {
		this.isConplict = isConplict;
	}
	/**
	 * @return shippingInfoCode
	 */
	public String getShippingInfoCode() {
		return shippingInfoCode;
	}
	/**
	 * @param shippingInfoCode setter shippingInfoCode
	 */
	public void setShippingInfoCode(String shippingInfoCode) {
		this.shippingInfoCode = shippingInfoCode;
	}
	
    /**
     * @return shippingRates
     */
    public List getShippingRates() {
        return shippingRates;
    }
    /**
     * @param shippingRates shippingRates
     */
    public void setShippingRates(List shippingRates) {
        this.shippingRates = shippingRates;
    }
    
    public Integer getDiscountQuantity() {
		return this.discountQuantity;
	}
	
	
	public void setDiscountQuantity(Integer aValue) {
		this.discountQuantity = aValue;
	}
	
	public BigDecimal getDiscountPriceRAM() {
		return discountPriceRAM;
	}
	public void setDiscountPriceRAM(BigDecimal discountPriceRAM) {
		this.discountPriceRAM = discountPriceRAM;
	}
	public Integer getDiscountQuantityRAM() {
		return discountQuantityRAM;
	}
	public void setDiscountQuantityRAM(Integer discountQuantityRAM) {
		this.discountQuantityRAM = discountQuantityRAM;
	}
	
	/**
	 * 用于shopping cart显示
	 * (原价*原来数量) - (优惠价*优惠后的数量)
	 * @return
	 */
	public BigDecimal getDisplayItemDiscountAmount()
	{
		// (原价*原来数量) - (优惠价*优惠后的数量)
		BigDecimal result = (getPrice().multiply(new BigDecimal(getQuantity())).subtract(getDiscountPrice().multiply(new BigDecimal(getDiscountQuantity()))));
		return result;
	}
	
	
	
	
	public boolean isCatalogConditonFlagRAM() {
		return catalogConditonFlagRAM;
	}
	public void setCatalogConditonFlagRAM(boolean catalogConditonFlagRAM) {
		this.catalogConditonFlagRAM = catalogConditonFlagRAM;
	}
	public boolean isCatalogConditionRunInAllFlagRAM() {
		return catalogConditionRunInAllFlagRAM;
	}
	public void setCatalogConditionRunInAllFlagRAM(
			boolean catalogConditionRunInAllFlagRAM) {
		this.catalogConditionRunInAllFlagRAM = catalogConditionRunInAllFlagRAM;
	}
	public boolean isCatalogConditionRunInAnyFlagRAM() {
		return catalogConditionRunInAnyFlagRAM;
	}
	public void setCatalogConditionRunInAnyFlagRAM(
			boolean catalogConditionRunInAnyFlagRAM) {
		this.catalogConditionRunInAnyFlagRAM = catalogConditionRunInAnyFlagRAM;
	}
	/**
	 *  1:product (default)
		2:gift certificate
		3:service
		4:software
		5:document
		6:music
		7:video
		8:others
	 * @return
	 */
	public String getShoppingcartItemTypeName(){
		switch(this.skuType){  
			case 2:
				return "Gift Certificate";
			case 3:
				return "Serivce";
			case 4:
				return "Software";
			case 5:
				return "Document";
			case 6:
				return "Music";
			case 7:
				return "Video";
			case 8:
				return "Others";
		    default:
		    	return "Product";
					
		}
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Integer getMaxOrderQuantity() {
		return maxOrderQuantity;
	}
	public void setMaxOrderQuantity(Integer maxOrderQuantity) {
		this.maxOrderQuantity = maxOrderQuantity;
	}
	
}

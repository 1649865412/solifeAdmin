package com.cartmatic.estore.common.model.cart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.base.ShoppingcartTbl;
import com.cartmatic.estore.sales.engine.PRule;

/**
 * Model - Business object  
 * Shoppingcart Object
 */
public class Shoppingcart extends ShoppingcartTbl implements Cloneable{
	public static final short ISUSECOUPON_YES = 1;
	public static final short ISUSECOUPON_NO = 0;

	//private ShoppingcartRelatedModel shoppingcartRelatedModel=new ShoppingcartRelatedModel();		
	
  	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	
	//*RAM用于暂时保存促销规则模拟运行后的结果
	//保存购物车折扣金额RAM
	private BigDecimal cartDiscountAmountRAM = new BigDecimal(0);
	//保存运输信息折扣RAM
	private String shippingDiscountInfoRAM = "";
	//保存获得积分RAM
	private Integer gainedPointRAM = new Integer(0);
	//保存获得优惠券类型RAM
	private Integer gainedCouponTypeIdRAM = null;
	//是否已经对金额进行优惠
	private boolean hasDiscountOfMoney = false;
	//是否已经对运输方式进行优惠
	private boolean hasDiscountOfShipment = false;
	//是否已经对积分进行优惠
	private boolean hasDiscountOfPoint = false;
	//能进行购物车运算的shoppingcartItems
	private Set<ShoppingcartItem> shoppingCartItemsForCalculation = new HashSet<ShoppingcartItem>();
	//能进行促销运算的shoppingcartItems
	private Set<ShoppingcartItem> shoppingCartItemsForPromo = new HashSet<ShoppingcartItem>();
	//能进行促销运算条件的shoppingcartItems
	private Set<ShoppingcartItem> shoppingCartItemsForPromoInCondition = new HashSet<ShoppingcartItem>();
	//能进行促销运算动作的shoppingcartItems
	private Set<ShoppingcartItem> shoppingCartItemsForPromoInAction = new HashSet<ShoppingcartItem>();
	
	private boolean isOnlyVirtualItem;
	
	
	public Shoppingcart () {
		super();
	}
	

	/**
	 * Default Key Fields Constructor for class Shoppingcart
	 */
	public Shoppingcart (
		 Integer in_shoppingCartId
		) {
		super (
		  in_shoppingCartId
		);
	}
	public Object clone(){
		Shoppingcart cart=null;
		try{
			cart=(Shoppingcart)super.clone();			
		}catch(CloneNotSupportedException  e){
			e.printStackTrace();	
		}
		return cart;
	}
	
    public Set<ShoppingcartItem> getCartItems(){
    	Set<ShoppingcartItem> items = getShoppingCartItems();
    	Set<ShoppingcartItem> returnSet = new HashSet<ShoppingcartItem>();
    	for(ShoppingcartItem item:items){
    		if(item.getIsSaved().shortValue()==0){
    			returnSet.add(item);
    		}
    	}
    	return returnSet;
    }
    
    public Set<ShoppingcartItem> getFavoriteItems(){
    	Set<ShoppingcartItem> items = getShoppingCartItems();
    	Set<ShoppingcartItem> returnSet = new HashSet<ShoppingcartItem>();
    	for(ShoppingcartItem item:items){
    		if(item.getIsSaved().shortValue()==1){
    			returnSet.add(item);
    		}
    	}
    	return returnSet;
    }
    
    /**
     * 此方法返回不作为子产品的产品
     * @param sku
     * @return
     */
    public ShoppingcartItem getItemFromCart(String sku){
    	Set<ShoppingcartItem> items = getCartItems();
    	for(ShoppingcartItem i:items){
    	   if(!i.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
    	   if(i.getProductSku().getProductSkuCode().equals(sku)&&i.getParent()==null){
    		   return i;
    	   }
    	}
    	return null;
    }
    public ShoppingcartItem getItemFromCart(Integer itemId){
    	Set<ShoppingcartItem> items = getCartItems();
    	for(ShoppingcartItem i:items){
    	   if(!i.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
    	   if(i.getShoppingcartItemId().equals(itemId) && i.getParent()==null){
    		   return i;
    	   }
    	}
    	return null;
    }
    /**
     * 此方法返回不作为子产品的产品
     * @param sku
     * @return
     */
    public ShoppingcartItem getItemFromFavorite(String sku){
    	Set<ShoppingcartItem> items = getFavoriteItems();
    	for(ShoppingcartItem i:items){
    	   if(i.getProductSku().getProductSkuCode().equals(sku)&&i.getParent()==null){
    		   return i;
    	   }
    	}
    	return null;
    }
    public ShoppingcartItem getItemFromFavorite(Integer itemId){
    	Set<ShoppingcartItem> items = getFavoriteItems();
    	for(ShoppingcartItem i:items){
    	   if(i.getShoppingcartItemId().equals(itemId)&&i.getParent()==null){
    		   return i;
    	   }
    	}
    	return null;
    }
    
  
    

	public java.math.BigDecimal getCartDiscountAmountRAM() {
		return cartDiscountAmountRAM;
	}


	public void setCartDiscountAmountRAM(java.math.BigDecimal cartDiscountAmountRAM) {
		this.cartDiscountAmountRAM = cartDiscountAmountRAM;
	}


	public String getShippingDiscountInfoRAM() {
		return shippingDiscountInfoRAM;
	}


	public void setShippingDiscountInfoRAM(String shippingDiscountInfoRAM) {
		this.shippingDiscountInfoRAM = shippingDiscountInfoRAM;
	}


	public Integer getGainedPointRAM() {
		return gainedPointRAM;
	}


	public void setGainedPointRAM(Integer gainedPointRAM) {
		this.gainedPointRAM = gainedPointRAM;
	}


	public Integer getGainedCouponTypeIdRAM() {
		return gainedCouponTypeIdRAM;
	}


	public void setGainedCouponTypeIdRAM(Integer gainedCouponTypeIdRAM) {
		this.gainedCouponTypeIdRAM = gainedCouponTypeIdRAM;
	}


	public boolean isHasDiscountOfMoney() {
		return hasDiscountOfMoney;
	}


	public void setHasDiscountOfMoney(boolean hasDiscountOfMoney) {
		this.hasDiscountOfMoney = hasDiscountOfMoney;
	}
	
	public boolean isHasDiscountOfShipment() {
		return hasDiscountOfShipment;
	}


	public void setHasDiscountOfShipment(boolean avalue) {
		this.hasDiscountOfShipment = avalue;
	}
	

	public boolean isHasDiscountOfPoint() {
		return hasDiscountOfPoint;
	}


	public void setHasDiscountOfPoint(boolean hasDiscountOfPoint) {
		this.hasDiscountOfPoint = hasDiscountOfPoint;
	}

    public boolean hasVirtualItem(){
    	Set<ShoppingcartItem> items = getCartItems();
    	for(ShoppingcartItem item:items){
    		if(item.getShoppingcartItemGc()!=null||item.getProductSku().getIsFictitious())
    			return true;
    	}
    	return false;
    }
	
	public BigDecimal getCartItemsTotalTax(){
		BigDecimal tax = new BigDecimal(0);
		Set<ShoppingcartItem> items = getCartItems();
		for(ShoppingcartItem item:items){
			tax = tax.add(item.getTax());
		}
		return tax;
	}
	
	/**
	 * 用于shopping cart显示 only
	 * @return
	 */
	public BigDecimal getItemsTotalDiscountAmount()
	{
		BigDecimal result = new BigDecimal(0);
		Set<ShoppingcartItem> items = getCartItems();
		for(ShoppingcartItem item:items){
			result = result.add(item.getDisplayItemDiscountAmount());
		}
		return result;
	}

	public Set<ShoppingcartItem> getShoppingCartItemsForCalculation() {
		return shoppingCartItemsForCalculation;
	}

	public void setShoppingCartItemsForCalculation(
			Set<ShoppingcartItem> shoppingCartItemsForCalculation) {
		this.shoppingCartItemsForCalculation = shoppingCartItemsForCalculation;
	}
	
	public void addShoppingCartItemForCalculation(
			ShoppingcartItem shoppingCartItemForCalculation) {
		this.shoppingCartItemsForCalculation.add(shoppingCartItemForCalculation);
	}
	
	public void clearShoppingCartItemsForCalculation(){
		this.shoppingCartItemsForCalculation.clear();
	}
	

	public Set<ShoppingcartItem> getShoppingCartItemsForPromo() {
		return shoppingCartItemsForPromo;
	}

	public void setShoppingCartItemsForPromo(
			Set<ShoppingcartItem> shoppingCartItemsForPromo) {
		this.shoppingCartItemsForPromo = shoppingCartItemsForPromo;
	}
	
	public void addShoppingCartItemForPromo(
			ShoppingcartItem shoppingCartItemForPromo) {
		this.shoppingCartItemsForPromo.add(shoppingCartItemForPromo);
	}
	
	public void clearShoppingCartItemsForPromo(){
		this.shoppingCartItemsForPromo.clear();
	}
	
	public Set<ShoppingcartItem> getShoppingCartItemsForPromoInCondition() {
		return shoppingCartItemsForPromoInCondition;
	}

	public void setShoppingCartItemsForPromoInCondition(
			Set<ShoppingcartItem> shoppingCartItemsForPromo) {
		this.shoppingCartItemsForPromoInCondition = shoppingCartItemsForPromo;
	}
	
	public void addShoppingCartItemForPromoInCondition(ShoppingcartItem shoppingCartItemForPromoInCondition){
		this.shoppingCartItemsForPromoInCondition.add(shoppingCartItemForPromoInCondition);
	}
	
	public void clearShoppingCartItemsForPromoInCondition(){
		this.shoppingCartItemsForPromoInCondition.clear();
	}


	public Set<ShoppingcartItem> getShoppingCartItemsForPromoInAction() {
		return shoppingCartItemsForPromoInAction;
	}

	public void setShoppingCartItemsForPromoInAction(
			Set<ShoppingcartItem> shoppingCartItemsForPromoInAction) {
		this.shoppingCartItemsForPromoInAction = shoppingCartItemsForPromoInAction;
	}
	
	public void addShoppingCartItemForPromoInAction(
			ShoppingcartItem shoppingCartItemForPromoInAction) {
		this.shoppingCartItemsForPromoInAction.add(shoppingCartItemForPromoInAction);
	}
	
	public void clearShoppingCartItemsForPromoInAction(){
		this.shoppingCartItemsForPromoInAction.clear();
	}
	
	/**
	 * 购物车中是否只有虚拟产品
	 * @return
	 */
	public boolean getIsOnlyVirtualItem(){
		Set<ShoppingcartItem> cartItems = getCartItems();
		boolean point = true;
		for(ShoppingcartItem item:cartItems){
			if(item.getItemType().shortValue()!=Constants.ITEM_TYPE_GC.shortValue()&&!item.getProductSku().getIsFictitious()){
				point = false;
				break;
			}
		}
		return point;
	}


	/**
	 * 不使用优惠劵
	 */
	public void notUseCoupon(){
		this.setIsUsedCoupon(Shoppingcart.ISUSECOUPON_NO);
		this.setUsedCouponNo(null);
	}
    
}

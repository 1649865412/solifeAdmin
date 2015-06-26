package com.cartmatic.estore.common.model.cart;

import com.cartmatic.estore.common.model.cart.base.ShoppingcartPromotionTbl;

/**
 * Model class for ShoppingcartPromotion. Add not database mapped fileds in this class.
 */
public class ShoppingcartPromotion extends ShoppingcartPromotionTbl {

	private Short isUsedCoupon = Shoppingcart.ISUSECOUPON_NO;
	private String usedCouponNo;
	private String type;
  	public Short getIsUsedCoupon() {
		return isUsedCoupon;
	}

	public void setIsUsedCoupon(Short isUsedCoupon) {
		this.isUsedCoupon = isUsedCoupon;
	}

	public String getUsedCouponNo() {
		return usedCouponNo;
	}

	public void setUsedCouponNo(String usedCouponNo) {
		this.usedCouponNo = usedCouponNo;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Default Empty Constructor for class ShoppingcartPromotion
	 */
	public ShoppingcartPromotion () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； shoppingcartPromotionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
//	public String getShoppingcartPromotionName () {
//		if (shoppingcartPromotionId == null)
//	        return "";
//	    else
//			//返回一个指定有业务意义的属性值;
//			//如：product的VO就用product.productName
//	        return this.;
//	}
	
	/**
	 * Default Key Fields Constructor for class ShoppingcartPromotion
	 */
	public ShoppingcartPromotion (
		 Integer in_shoppingcartPromotionId
		) {
		super (
		  in_shoppingcartPromotionId
		);
	}

}

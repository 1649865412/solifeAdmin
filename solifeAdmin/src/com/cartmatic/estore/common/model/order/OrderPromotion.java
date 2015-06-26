package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderPromotionTbl;

/**
 * Model class for OrderPromotion. Add not database mapped fileds in this class.
 */
public class OrderPromotion extends OrderPromotionTbl {

  	/**
	 * Default Empty Constructor for class OrderPromotion
	 */
	public OrderPromotion () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderPromotionName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderPromotionName () {
		if (orderPromotionId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.promotionName;
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPromotion
	 */
	public OrderPromotion (
		 Integer in_orderPromotionId
		) {
		super (
		  in_orderPromotionId
		);
	}

}

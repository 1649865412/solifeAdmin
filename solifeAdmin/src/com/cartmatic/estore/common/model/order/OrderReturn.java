package com.cartmatic.estore.common.model.order;

import java.math.BigDecimal;

import com.cartmatic.estore.common.model.order.base.OrderReturnTbl;
import com.cartmatic.estore.order.OrderConstants;

/**
 * Model class for OrderReturn. Add not database mapped fileds in this class.
 */
public class OrderReturn extends OrderReturnTbl {

  	/**
	 * Default Empty Constructor for class OrderReturn
	 */
	public OrderReturn () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderReturnName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderReturnName () {
		if (orderReturnId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.rmaNo;
	}
	
	/**
	 * Default Key Fields Constructor for class OrderReturn
	 */
	public OrderReturn (
		 Integer in_orderReturnId
		) {
		super (
		  in_orderReturnId
		);
	}
	
	public boolean isExchange(){
		return this.getReturnType().shortValue() == OrderConstants.RETURN_TYPE_EXCHANGE.shortValue();
	}
	
	public BigDecimal getReturnTotalAmount(){
		return this.getItemSubTotal()
					.add(this.getShippingCost())
					.add(this.getDiscountAmount().negate())
					.add(this.getItemTax())
					.add(this.getShippingTax())
					.add(this.getLessRestockAmount().negate());
	}
	
	public static BigDecimal getItemReturmAmount(BigDecimal discountPrice, BigDecimal itemDiscountAmount, Integer skuQty, Integer returnQty){
		BigDecimal itemReturnAmount = discountPrice.multiply(BigDecimal.valueOf(returnQty));
		//考虑全退回，部分退回需要人工干预
		if(itemDiscountAmount.compareTo(BigDecimal.ZERO)==1)
			itemReturnAmount = itemReturnAmount.add(itemDiscountAmount.negate());
//			itemReturnAmount = itemReturnAmount.add(
//					( itemDiscountAmount.divide( BigDecimal.valueOf(returnQty).divide(BigDecimal.valueOf(skuQty)) )
//						).negate() );
		return itemReturnAmount;
	}
	
}

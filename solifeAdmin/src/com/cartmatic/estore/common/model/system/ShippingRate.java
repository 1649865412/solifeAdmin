package com.cartmatic.estore.common.model.system;

import java.math.BigDecimal;

import com.cartmatic.estore.common.model.system.base.ShippingRateTbl;

/**
 * Model class for ShippingRate. Add not database mapped fileds in this class.
 */
public class ShippingRate extends ShippingRateTbl {
	/**
	 * 购物车使用此运输方式的费用（checkout时临时赋值）
	 */
	private  BigDecimal cartShipping;

  	/**
	 * Default Empty Constructor for class ShippingRate
	 */
	public ShippingRate () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； shippingRateName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getShippingRateName () {
		if (shippingRateId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.shippingRateId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class ShippingRate
	 */
	public ShippingRate (
		 Integer in_shippingRateId
		) {
		super (
		  in_shippingRateId
		);
	}

	public BigDecimal getCartShipping() {
		return cartShipping;
	}

	public void setCartShipping(BigDecimal cartShipping) {
		this.cartShipping = cartShipping;
	}

}

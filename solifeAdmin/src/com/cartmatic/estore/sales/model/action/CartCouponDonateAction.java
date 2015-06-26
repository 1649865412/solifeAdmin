package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
/**
 * CartCouponDonateAction
 * donate coupon (key=COUPON_TYPE)
 * @author CartMatic
 *
 */
public class CartCouponDonateAction extends CartAbstractAction {
	private static final Log logger = LogFactory.getLog(CartCouponDonateAction.class);
	public static final String COUPON_TYPE = "COUPON_TYPE";
	private Integer couponType;
	private Map<String,String> params;
	
	public CartCouponDonateAction(Map<String, String> _params) throws Exception {
		this.couponType = Integer.parseInt(_params.get(COUPON_TYPE));
		this.params = _params;
	}
	public Map<String, String> getParams()
	{
		return params;
	}
	
	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		logger.debug(new StringBuffer().append("[COUPON_TYPE|").append(couponType)
				.append("]"));

		
		cart.setGainedCouponTypeIdRAM(couponType);

		return cart;
	}
}

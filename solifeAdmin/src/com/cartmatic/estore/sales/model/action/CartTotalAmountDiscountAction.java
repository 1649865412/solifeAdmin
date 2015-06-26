
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartTotalAmountDiscountAction XX$(key=DISCOUNT_AMOUNT) off cart subTotal
 * 
 * @author CartMatic
 * 
 */
public class CartTotalAmountDiscountAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartTotalAmountDiscountAction.class);
	public static final String	DISCOUNT_AMOUNT	= "DISCOUNT_AMOUNT";
	private BigDecimal			paramDiscountAmount;
	private Map<String, String>	params;

	public CartTotalAmountDiscountAction(Map<String, String> _params) {
		this.paramDiscountAmount = new BigDecimal(_params
				.get(DISCOUNT_AMOUNT));
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(paramDiscountAmount).append("]").toString());

		cart.setCartDiscountAmountRAM(cart.getCartDiscountAmountRAM().add(paramDiscountAmount));

		return cart;
	}
}

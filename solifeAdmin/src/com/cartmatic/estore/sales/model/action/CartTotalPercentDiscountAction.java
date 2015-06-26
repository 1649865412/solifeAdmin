
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartTotalPercentDiscountAction XX%(key=DISCOUNT_PERCENT) off cart subTotal
 * 
 * @author CartMatic
 * 
 */
public class CartTotalPercentDiscountAction extends CartAbstractAction {
	private static final Log	logger				= LogFactory
															.getLog(CartTotalPercentDiscountAction.class);
	public static final String	DISCOUNT_PERCENT	= "DISCOUNT_PERCENT";
	private BigDecimal			paramDiscountPercent;
	private Map<String, String>	params;

	public CartTotalPercentDiscountAction(Map<String, String> _params)
			throws Exception {
		this.paramDiscountPercent = new BigDecimal(_params
				.get(DISCOUNT_PERCENT));
		if (paramDiscountPercent.compareTo(new BigDecimal(0)) == -1
				|| paramDiscountPercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartTotalPercentDiscountAction--error percent,it should between 0 to 100");
		} else {
			paramDiscountPercent = paramDiscountPercent.divide(new BigDecimal(
					100));
		}
		this.params = _params;

	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(
				paramDiscountPercent).append("]").toString());

		cart.setCartDiscountAmountRAM(cart.getCartDiscountAmountRAM().add(
				cart.getTotal().multiply(paramDiscountPercent)));

		return cart;
	}
}

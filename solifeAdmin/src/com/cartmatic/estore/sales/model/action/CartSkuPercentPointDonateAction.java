
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;

/**
 * CartProductPercentPointDonateAction donate point by the SKU(key=SKU) percent
 * (key=DONATE_PERCENT)
 * 
 * @author CartMatic
 * 
 */
public class CartSkuPercentPointDonateAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartSkuPercentPointDonateAction.class);
	public static final String	SKU				= "SKU";
	public static final String	DONATE_PERCENT	= "DONATE_PERCENT";
	private String				paramSku;
	private BigDecimal			paramDonatePercent;
	private Map<String, String>	params;

	public CartSkuPercentPointDonateAction(Map<String, String> _params)
			throws Exception {

		this.paramSku = _params.get(SKU);
		this.paramDonatePercent = new BigDecimal(_params.get(DONATE_PERCENT));
		if (paramDonatePercent.compareTo(new BigDecimal(0)) == -1
				|| paramDonatePercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartSkuPercentPointDonateAction--error percent,it should between 0 to 100");
		} else {
			paramDonatePercent = paramDonatePercent.divide(new BigDecimal(100));
		}
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		BigDecimal total = new BigDecimal(0);
		logger.debug(new StringBuffer().append("[DONATE_PERCENT|").append(
				paramDonatePercent).append("]").append("[SKU|")
				.append(paramSku).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getProductSku().getProductSkuId().toString().equals(
					paramSku)) {
				// found it
				total = total.add(cartItem.getDiscountPrice().multiply(
						new BigDecimal(cartItem.getDiscountQuantity())));
			}
		}
		cart.setGainedPointRAM(cart.getGainedPointRAM()
				+ total.multiply(paramDonatePercent).intValue());

		return cart;
	}
}


package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;

/**
 * CartFreeSkuDiscountAction get free XX(key=numItems) of sku YY(key=SKU)
 * 
 * @author CartMatic
 * 
 */
public class CartFreeSkuAction extends CartAbstractAction {
	private static final Log	logger				= LogFactory
															.getLog(CartFreeSkuAction.class);
	public static final String	SKU					= "SKU";
	public static final String	DISCOUNT_QUANTITY	= "DISCOUNT_QUANTITY";
	private String				paramSku;
	private int					paramDiscountQuantity;
	private Map<String, String>	params;

	public CartFreeSkuAction(Map<String, String> _params) throws Exception {

		this.paramSku = _params.get(SKU);
		this.paramDiscountQuantity = Integer.parseInt(_params
				.get(DISCOUNT_QUANTITY));

		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;

		logger.debug(new StringBuffer().append("[DISCOUNT_QUANTITY|").append(
				paramDiscountQuantity).append("]").append("[SKU|").append(
				paramSku).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getProductSku().getProductSkuId().toString().equals(
					paramSku)) {
				// found it
				cartItem.setDiscountQuantityRAM(cartItem
						.getDiscountQuantityRAM()
						+ paramDiscountQuantity);
				break;
			}
		}

		return cart;
	}
}

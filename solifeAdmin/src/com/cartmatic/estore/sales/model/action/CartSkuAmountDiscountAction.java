
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CartSkuAmountDiscountAction XX$(key=DISCOUNT_PERCENT) off OF product
 * SKU(key=SKU)
 * 
 * @author CartMatic
 * 
 */
public class CartSkuAmountDiscountAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartSkuAmountDiscountAction.class);
	public static final String	SKU				= "SKU";
	public static final String	DISCOUNT_AMOUNT	= "DISCOUNT_AMOUNT";
	private String				paramSku;
	private BigDecimal			paramDiscountAmount;
	private Map<String, String>	params;

	public CartSkuAmountDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramSku = _params.get(SKU);
		this.paramDiscountAmount = new BigDecimal(_params.get(DISCOUNT_AMOUNT));

		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").append("[SKU|").append(
				paramSku).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getProductSku().getProductSkuId().toString().equals(
					paramSku)) {
				// found it
				cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
						.add(paramDiscountAmount));
				break;
			}
		}

		return cart;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").append("[SKU|").append(
				paramSku).append("]").toString());

		if (sku.getProductSkuId().toString().equals(paramSku)) {
			// found it
			sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
					paramDiscountAmount));
		}

		return sku;

	}
}

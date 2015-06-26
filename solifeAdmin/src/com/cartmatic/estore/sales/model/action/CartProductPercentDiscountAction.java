
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CartProductPercentDiscountAction XX%(key=DISCOUNT_PERCENT) off OF product
 * YY(key=PRODUCT)
 * 
 * @author CartMatic
 * 
 */
public class CartProductPercentDiscountAction extends CartAbstractAction {
	private static final Log	logger				= LogFactory
															.getLog(CartProductPercentDiscountAction.class);
	public static final String	PRODUCT				= "PRODUCT";
	public static final String	DISCOUNT_PERCENT	= "DISCOUNT_PERCENT";
	private String				paramProduct;
	private BigDecimal			paramDiscountPercent;
	private Map<String, String>	params;

	public CartProductPercentDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramProduct = _params.get(PRODUCT);
		this.paramDiscountPercent = new BigDecimal(_params
				.get(DISCOUNT_PERCENT));
		if (paramDiscountPercent.compareTo(new BigDecimal(0)) == -1
				|| paramDiscountPercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartSubtotalPercentDiscountAction--error percent,it should between 0 to 100");
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
				paramDiscountPercent).append("]").append("[PRODUCT|").append(
				paramProduct).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getProductSku().getProductId().toString().equals(
					paramProduct)) {
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())) {
					// found it
					cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
							.add(
									cartItem.getDiscountPrice().multiply(
											paramDiscountPercent)));
				}
			}
		}

		return cart;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;

		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(
				paramDiscountPercent).append("]").append("[PRODUCT|").append(
				paramProduct).append("]").toString());

		if (sku.getProductId().toString().equals(paramProduct)) {
			if (!isSkuExcluded(params, sku.getProductSkuId().toString())) {
				// found it
				sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
						sku.getDiscountPrice().multiply(paramDiscountPercent)));
			}
		}

		return sku;
	}
}


package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CartProductAmountDiscountAction XX(key=DISCOUNT_AMOUNT) off OF product
 * YY(key=PRODUCT)
 * 
 * @author CartMatic
 * 
 */
public class CartProductAmountDiscountAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartProductAmountDiscountAction.class);
	public static final String	PRODUCT			= "PRODUCT";
	public static final String	DISCOUNT_AMOUNT	= "DISCOUNT_AMOUNT";
	private String				paramProduct;
	private BigDecimal			paramDiscountAmount;
	private Map<String, String>	params;

	public CartProductAmountDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramProduct = _params.get(PRODUCT);
		this.paramDiscountAmount = new BigDecimal(_params.get(DISCOUNT_AMOUNT));

		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").append("[PRODUCT|").append(
				paramProduct).append("]"));

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getProductSku().getProductId().toString().equals(
					paramProduct)) {
				// found it
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())) {
					cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
							.add(paramDiscountAmount));
				}
			}
		}

		return cart;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").append("[PRODUCT|").append(
				paramProduct).append("]"));

		if (sku.getProductId().toString().equals(paramProduct)) {
			// found it
			if (!isSkuExcluded(params, sku.getProductSkuId().toString())) {
				sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
						paramDiscountAmount));
			}
		}

		return sku;
	}
}

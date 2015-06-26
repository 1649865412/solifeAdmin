
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CatalogSkuPercentDiscountAction XX%(key=DISCOUNT_PERCENT) off OF product
 * SKU(key=SKU)
 * 
 * @author CartMatic
 * 
 */
public class CatalogSkuPercentDiscountAction extends CatalogAbstractAction {
	private static final Log	logger				= LogFactory
															.getLog(CatalogSkuPercentDiscountAction.class);
	public static final String	DISCOUNT_PERCENT	= "DISCOUNT_PERCENT";
	private BigDecimal			paramDiscountPercent;
	private Map<String, String>	params;

	public CatalogSkuPercentDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramDiscountPercent = new BigDecimal(_params
				.get(DISCOUNT_PERCENT));
		if (paramDiscountPercent.compareTo(new BigDecimal(0)) == -1
				|| paramDiscountPercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CatalogSkuPercentDiscountAction--error percent,it should between 0 to 100");
		} else {
			paramDiscountPercent = paramDiscountPercent.divide(new BigDecimal(
					100));
		}
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;
		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(
				paramDiscountPercent).append("]").append("[SKU|").append(
				sku.getProductSkuId()).append("]").toString());

		if (null == sku.getSalePrice()) {
			sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
					sku.getDiscountPrice().multiply(paramDiscountPercent)));
		}

		return sku;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;

		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(
				paramDiscountPercent).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.isCatalogConditonFlagRAM()) {
				// found it
				logger.debug("(SKU|"
						+ cartItem.getProductSku().getProductSkuId() + ")");
				cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
						.add(
								cartItem.getDiscountPrice().multiply(
										paramDiscountPercent)));

			}
		}

		return cart;

	}
}


package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CatalogSkuAmountDiscountAction XX$(key=DISCOUNT_AMOUNT) off OF product
 * SKU(key=SKU)
 * 
 * @author CartMatic
 * 
 */
public class CatalogSkuAmountDiscountAction extends CatalogAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CatalogSkuAmountDiscountAction.class);
	public static final String	DISCOUNT_AMOUNT	= "DISCOUNT_AMOUNT";
	private BigDecimal			paramDiscountAmount;
	private Map<String, String>	params;

	public CatalogSkuAmountDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramDiscountAmount = new BigDecimal(_params.get(DISCOUNT_AMOUNT));

		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").toString());

		if (null == sku.getSalePrice()) {
			sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
					paramDiscountAmount));
		}

		return sku;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;

		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(
				paramDiscountAmount).append("]").toString());

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {

			if (cartItem.isCatalogConditonFlagRAM()) {
				// found it
				logger.debug("(SKU|"
						+ cartItem.getProductSku().getProductSkuId() + ")");
				cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
						.add(paramDiscountAmount));
			}
		}

		return cart;

	}
}

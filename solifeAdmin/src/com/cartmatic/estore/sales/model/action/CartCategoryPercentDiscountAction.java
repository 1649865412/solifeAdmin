
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.util.PromoDependServicesUtil;

/**
 * CartCategoryPercentDiscountAction XX%(key=DISCOUNT_PERCENT) off in category
 * YY(key=CATEGORY)
 * 
 * @author CartMatic
 * 
 */
public class CartCategoryPercentDiscountAction extends CartAbstractAction {
	private static final Log	logger				= LogFactory
															.getLog(CartCategoryPercentDiscountAction.class);
	public static final String	CATEGORY			= "CATEGORY";
	public static final String	DISCOUNT_PERCENT	= "DISCOUNT_PERCENT";
	private String				paramCategory;
	private BigDecimal			paramDiscountPercent;
	private Map<String, String>	params;

	public CartCategoryPercentDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramCategory = _params.get(CATEGORY);
		this.paramDiscountPercent = new BigDecimal(_params
				.get(DISCOUNT_PERCENT));
		if (paramDiscountPercent.compareTo(new BigDecimal(0)) == -1
				|| paramDiscountPercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartCategoryPercentDiscountAction--error percent,it should between 0 to 100");
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
				paramDiscountPercent).append("]").append("[CATEGORY|").append(
				paramCategory).append("]").toString());

		for (ShoppingcartItem cartItem : _cart.getShoppingCartItemsForPromoInAction()) {
			if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
					cartItem.getProductSku().getProductSkuId(),
					new Integer(paramCategory))) {
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())
						&& !isProductExcluded(params, cartItem.getProductSku()
								.getProductId().toString())
						&& !isCategoryExcluded(params, cartItem.getProductSku()
								.getProductSkuId().toString())) {
					cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
							.add(
									cartItem.getDiscountPrice().multiply(
											paramDiscountPercent)));
					
//					cart.setCartDiscountAmount(cart.getCartDiscountAmount().add(cartItem.getDiscountPriceRAM()));//为购物车增加相应的优惠金额
				}
			}
		}

		return cart;
	}

	public ProductSku run(ProductSku _sku) {

		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(
				paramDiscountPercent).append("]").append("[CATEGORY|").append(
				paramCategory).append("]").toString());
		ProductSku sku = _sku;

		if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
				sku.getProductSkuId(), new Integer(paramCategory))) {
			if (!isSkuExcluded(params, sku.getProductSkuId().toString())
					&& !isProductExcluded(params, sku.getProductId().toString())
					&& !isCategoryExcluded(params, sku.getProductSkuId()
							.toString())) {
				sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
						sku.getDiscountPrice().multiply(paramDiscountPercent)));
			}
		}

		return sku;
	}

}

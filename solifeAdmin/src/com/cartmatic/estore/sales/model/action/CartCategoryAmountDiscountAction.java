
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
 * CartCategoryAmountDiscountAction XX(key=DISCOUNT_AMOUNT) off OF product
 * YY(key=CATEGORY)
 * 
 * @author CartMatic
 * 
 */
public class CartCategoryAmountDiscountAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartCategoryAmountDiscountAction.class);
	public static final String	CATEGORY		= "CATEGORY";
	public static final String	DISCOUNT_AMOUNT	= "DISCOUNT_AMOUNT";
	private String				paramCategory;
	private BigDecimal			paramDiscountAmount;
	private Map<String, String>	params;

	public CartCategoryAmountDiscountAction(Map<String, String> _params)
			throws Exception {

		this.paramCategory = _params.get(CATEGORY);
		this.paramDiscountAmount = new BigDecimal(_params.get(DISCOUNT_AMOUNT));

		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		StringBuffer sb = new StringBuffer();
		sb.append("[DISCOUNT_AMOUNT|").append(paramDiscountAmount).append("]");
		sb.append("[CATEGORY|").append(paramCategory).append("]");
		logger.debug(sb);

		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {
			// 判断该item是否在指定的目录中
			if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
					cartItem.getProductSku().getProductSkuId(),
					new Integer(paramCategory))) {
				// 判断该item是否"被排除"
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())
						&& !isProductExcluded(params, cartItem.getProductSku()
								.getProductId().toString())
						&& !isCategoryExcluded(params, cartItem.getProductSku()
								.getProductSkuId().toString())) {
					// found it
					cartItem.setDiscountPriceRAM(cartItem.getDiscountPriceRAM()
							.add(paramDiscountAmount));
				}
			}
		}

		return cart;
	}

	public ProductSku run(ProductSku _sku) {
		StringBuffer sb = new StringBuffer();
		sb.append("[DISCOUNT_AMOUNT|").append(paramDiscountAmount).append("]");
		sb.append("[CATEGORY|").append(paramCategory).append("]");
		logger.debug(sb);
		ProductSku sku = _sku;
		// 判断该item是否在指定的目录中
		if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
				sku.getProductSkuId(), new Integer(paramCategory))) {
			// 判断该item是否"被排除"
			if (!isSkuExcluded(params, sku.getProductSkuId().toString())
					&& !isProductExcluded(params, sku.getProductId().toString())
					&& !isCategoryExcluded(params, sku.getProductSkuId()
							.toString())) {
				// found it
				sku.setDiscountPriceRAM(sku.getDiscountPriceRAM().add(
						paramDiscountAmount));
			}
		}
		return sku;
	}

}

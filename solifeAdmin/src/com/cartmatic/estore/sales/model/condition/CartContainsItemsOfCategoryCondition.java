
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.util.PromoDependServicesUtil;

/**
 * CartContainsItemsOfCategoryCondition QUANTIFIER(key=NUMITEMS_QUANTIFIER)
 * $XX(key=NUMITEMS) of $YY(key=CATEGORY) are in the shopping cart
 * 
 * @author CartMatic
 * 
 */
public class CartContainsItemsOfCategoryCondition extends CartAbstractCondition {
	private static final Log	logger				= LogFactory
															.getLog(CartContainsItemsOfCategoryCondition.class);
	public static final String	NUMITEMS_QUANTIFIER	= "NUMITEMS_QUANTIFIER";
	public static final String	NUMITEMS			= "NUMITEMS";
	public static final String	CATEGORY			= "CATEGORY";
	private int					paramNumItemsQuantifier;
	private Integer				paramNumItems;
	private String				paramCategory;
	private Map<String, String>	params;

	public CartContainsItemsOfCategoryCondition(Map<String, String> _params) {
		this.paramNumItemsQuantifier = Integer.parseInt(_params
				.get(NUMITEMS_QUANTIFIER));
		this.paramNumItems = Integer.parseInt(_params.get(NUMITEMS));
		this.paramCategory = _params.get(CATEGORY);
		this.params = _params;

	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(Shoppingcart _cart) {
		Integer numItems = new Integer(0);
		for (ShoppingcartItem cartItem : _cart.getShoppingCartItemsForPromoInCondition()) {
			if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
					cartItem.getProductSku().getProductSkuId(),
					new Integer(paramCategory))) {
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())
						&& !isProductExcluded(params, cartItem.getProductSku()
								.getProductId().toString())
						&& !isCategoryExcluded(params, cartItem.getProductSku()
								.getProductSkuId().toString())) {
					// found it
					numItems += cartItem.getQuantity();
				}
			}
		}

		logger.debug(new StringBuffer().append("[CATEGORY|").append(
				paramCategory).append("]").append("[NUMITEMS_QUANTIFIER|")
				.append(paramNumItemsQuantifier).append("]").append(
						"[NUMITEMS|").append(paramNumItems).append("]~~{")
				.append(numItems).append("}").toString());

		switch (paramNumItemsQuantifier) {
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ:
				return (numItems >= paramNumItems) ? true : false;
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_EQ:
				return (numItems == paramNumItems) ? true : false;
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_LT_OR_EQ:
				return (numItems <= paramNumItems) ? true : false;

			default:
				return false;

		}

	}

	public boolean getResult(Product product) {
		boolean flag = false;
		for(ProductSku sku : product.getProductSkus()){
			if (PromoDependServicesUtil.getProductService().isInCategoryBySku(sku.getId(),new Integer(paramCategory))) {
				if (!isSkuExcluded(params, sku.getId().toString())
						&& !isProductExcluded(params, sku.getId().toString())
						&& !isCategoryExcluded(params, sku.getId().toString())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}

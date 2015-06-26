
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * SKUInCartCondition QUANTIFIER(key=NUMITEMS_QUANTIFIER) $XX(key=NUMITEMS) of
 * $YY(key=SKU) are in the shopping cart
 * 
 * @author CartMatic
 * 
 */
public class CartSkuInCartCondition extends CartAbstractCondition {
	private static final Log	logger				= LogFactory
															.getLog(CartSkuInCartCondition.class);

	public static final String	NUMITEMS_QUANTIFIER	= "NUMITEMS_QUANTIFIER";
	public static final String	NUMITEMS			= "NUMITEMS";
	public static final String	SKU					= "SKU";
	private int					paramNumItemsQuantifier;
	private Integer				paramNumItems;
	private String				paramSku;
	private Map<String, String>	params;

	public CartSkuInCartCondition(Map<String, String> _params) {
		this.paramNumItemsQuantifier = Integer.parseInt(_params
				.get(NUMITEMS_QUANTIFIER));
		this.paramNumItems = Integer.parseInt(_params.get(NUMITEMS));
		this.paramSku = _params.get(SKU);
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(Shoppingcart _cart) {
		Integer numItems = new Integer(0);
		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInCondition()) {
			if (cartItem.getProductSku().getProductSkuId().toString().equals(
					paramSku)) {
				// found it
				numItems += cartItem.getQuantity();
				break;
			}
		}
		logger.debug(new StringBuffer().append("[SKU|").append(paramSku)
				.append("]").append("[NUMITEMS_QUANTIFIER|").append(
						paramNumItemsQuantifier).append("]").append(
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
		for(ProductSku sku : product.getProductSkus()) {
			if(sku.getProductSkuId().toString().equals(paramSku)){
				flag = true;
				break;
			}
		}
		return flag;
	}
}

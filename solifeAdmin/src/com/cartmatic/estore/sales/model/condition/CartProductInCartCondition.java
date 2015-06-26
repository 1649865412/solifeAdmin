
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;

/**
 * ProductInCartCondition QUANTIFIER(key=NUMITEMS_QUANTIFIER) $XX(key=NUMITEMS)
 * of $YY(key=PRODUCT) are in the shopping cart
 * 
 * @author CartMatic
 * 
 */
public class CartProductInCartCondition extends CartAbstractCondition {
	private static final Log	logger				= LogFactory
															.getLog(CartProductInCartCondition.class);

	public static final String	NUMITEMS_QUANTIFIER	= "NUMITEMS_QUANTIFIER";
	public static final String	NUMITEMS			= "NUMITEMS";
	public static final String	PRODUCT				= "PRODUCT";
	private int					paramNumItemsQuantifier;
	private Integer				paramNumItems;
	private String				paramProduct;
	private Map<String, String>	params;

	public CartProductInCartCondition(Map<String, String> _params) {
		this.paramNumItemsQuantifier = Integer.parseInt(_params
				.get(NUMITEMS_QUANTIFIER));
		this.paramNumItems = Integer.parseInt(_params.get(NUMITEMS));
		this.paramProduct = _params.get(PRODUCT);
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(Shoppingcart _cart) {
		Integer numItems = new Integer(0);
		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInCondition()) {
			if (cartItem.getProductSku().getProductId().toString().equals(
					paramProduct)) {
				if (!isSkuExcluded(params, cartItem.getProductSku()
						.getProductSkuId().toString())) {
					// found it
					numItems += cartItem.getQuantity();
				}
			}
		}
		logger.debug(new StringBuffer().append("[PRODUCT|")
				.append(paramProduct).append("]").append(
						"[NUMITEMS_QUANTIFIER|")
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
		if(product.getId().toString().equals(paramProduct)){
			flag = true;
		}
		return flag;
	}
}

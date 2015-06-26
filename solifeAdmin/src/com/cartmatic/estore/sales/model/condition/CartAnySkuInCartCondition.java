
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;

/**
 * AnySkuInCartCondition QUANTIFIER(key=NUMITEMS_QUANTIFIER) $XX(key=NUMITEMS)
 * are in the shopping cart
 * 
 * @author CartMatic
 * 
 */
public class CartAnySkuInCartCondition extends CartAbstractCondition {
	private static final Log	logger				= LogFactory
															.getLog(CartAnySkuInCartCondition.class);
	public static final String	NUMITEMS_QUANTIFIER	= "NUMITEMS_QUANTIFIER";
	public static final String	NUMITEMS			= "NUMITEMS";
	private int					paramNumItemsQuantifier;
	private Integer				paramNumItems;
	private Map<String, String>	params;

	public CartAnySkuInCartCondition(Map<String, String> _params) {
		this.paramNumItemsQuantifier = Integer.parseInt(_params
				.get(NUMITEMS_QUANTIFIER));
		this.paramNumItems = Integer.parseInt(_params.get(NUMITEMS));
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(Shoppingcart _cart) {
		
		logger.debug(new StringBuffer().append("[NUMITEMS_QUANTIFIER|").append(paramNumItemsQuantifier).append("]").append("[NUMITEMS|").append(paramNumItems).append("]~~{").append(_cart.getItemsCount()).append("}").toString());
		
		switch (paramNumItemsQuantifier) {
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ:
				return (_cart.getBuyNowItemsCount() >= paramNumItems) ? true : false;
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_EQ:
				return (_cart.getBuyNowItemsCount() == paramNumItems) ? true : false;
			case CartAbstractCondition.NUMITEMS_QUANTIFIER_TYPE_OF_LT_OR_EQ:
				return (_cart.getBuyNowItemsCount() <= paramNumItems) ? true : false;

			default:
				return false;

		}
	}
	
	public boolean getResult(Product Product){
		return true;
	}

}

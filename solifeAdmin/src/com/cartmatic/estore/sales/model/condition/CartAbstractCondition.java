
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.BaseElement;

/**
 * 所有ShoppingcartPromotion类的条件必须扩展该类 这个一个适配器类
 * 
 * @author CartMatic
 * 
 */
public abstract class CartAbstractCondition extends BaseElement implements
		Condition {

	public static final int	NUMITEMS_QUANTIFIER_TYPE_OF_EQ			= 0;
	public static final int	NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ	= 1;
	public static final int	NUMITEMS_QUANTIFIER_TYPE_OF_LT_OR_EQ	= -1;

	public abstract boolean getResult(Shoppingcart _cart);

	public abstract Map<String, String> getParams();

	public final boolean getResult(ProductSku _sku) {
		return false;
	}
	
	public boolean getResult(Product product){
		return false;
	}

}

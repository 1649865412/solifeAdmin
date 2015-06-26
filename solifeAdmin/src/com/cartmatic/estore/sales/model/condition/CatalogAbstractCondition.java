
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.BaseElement;

/**
 * 所有CatalogPromotion类的条件必须扩展该类 这个一个适配器类
 * 
 * @author CartMatic
 * 
 */
public abstract class CatalogAbstractCondition extends BaseElement implements
		Condition {

	public abstract boolean getResult(ProductSku _sku);

	public abstract boolean getResult(Shoppingcart _cart);
	
	public abstract Map<String, String> getParams();
	
	public boolean getResult(Product product) {
		return false;
	}

}

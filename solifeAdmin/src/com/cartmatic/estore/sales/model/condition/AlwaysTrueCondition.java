
package com.cartmatic.estore.sales.model.condition;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * 
 * 
 * @author CartMatic
 * 
 */
public class AlwaysTrueCondition implements Condition {
	public boolean getResult(Shoppingcart _cart) {
		return true;
	}

	public boolean getResult(ProductSku _sku) {
		return true;
	}

	public Map<String, String> getParams() {
		return new HashMap<String, String>();
	}

	@Override
	public boolean getResult(Product product) {
		// TODO Auto-generated method stub
		return true;
	}

}

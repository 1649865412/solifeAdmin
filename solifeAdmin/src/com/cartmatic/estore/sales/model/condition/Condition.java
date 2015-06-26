
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public interface Condition {
	/**
	 * 判断该Shoppingcart是否满足该条件
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @return true 如果满足
	 */
	public boolean getResult(Shoppingcart _cart);

	/**
	 * 判断该ProductSku是否满足该条件
	 * 
	 * @param _cart
	 *            ProductSku
	 * @return true 如果满足
	 */
	public boolean getResult(ProductSku _sku);
	
	/**
	 * 判断该product是否满足条件
	 * @param product
	 * @return true 满足
	 */
	public boolean getResult(Product product);

	/**
	 * 获得该条件的参数
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getParams();
}

package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public interface Action {
	/**
	 * 运行该动作,并获得Shoppingcart的运行结果
	 * @param _cart Shoppingcart
	 * @return Shoppingcart
	 */
	public Shoppingcart run(Shoppingcart _cart);
	/**
	 * 运行该动作,并获得ProductSku的运行结果
	 * @param _sku ProductSku
	 * @return ProductSku
	 */
	public ProductSku run(ProductSku _sku);
	/**
	 * 获得该动作的参数
	 * @return Map<String,String>
	 */
	public Map<String,String> getParams();
}

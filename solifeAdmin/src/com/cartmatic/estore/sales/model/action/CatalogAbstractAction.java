package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.BaseElement;

/**
 *所有CatalogPromotion类的动作必须扩展该类
 * 这个一个适配器类
 * @author CartMatic
 *
 */
public abstract class CatalogAbstractAction extends BaseElement implements Action{
	public abstract ProductSku run(ProductSku _sku);
	
	public abstract Shoppingcart run(Shoppingcart _cart);
	
	public abstract Map<String,String> getParams();
}

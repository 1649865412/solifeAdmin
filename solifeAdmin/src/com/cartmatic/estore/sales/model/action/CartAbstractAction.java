package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.BaseElement;

/**
 *所有ShoppingcartPromotion类的动作必须扩展该类
 * 这个一个适配器类
 * @author CartMatic
 *
 */
public abstract class CartAbstractAction extends BaseElement implements Action{
	public abstract Shoppingcart run(Shoppingcart _cart);
	
	public ProductSku run(ProductSku _sku){
		return _sku;
	}
	public abstract Map<String,String> getParams();
	
	
	
	
}

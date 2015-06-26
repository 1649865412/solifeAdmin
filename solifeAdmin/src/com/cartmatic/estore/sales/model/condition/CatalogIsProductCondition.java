
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CatalogIsProductCondition is $YY(key=PRODUCT)
 * 
 * @author CartMatic
 * 
 */
public class CatalogIsProductCondition extends CatalogAbstractCondition {
	private static final Log	logger	= LogFactory
												.getLog(CatalogIsProductCondition.class);

	public static final String	PRODUCT	= "PRODUCT";

	private String				paramProduct;
	private Map<String, String>	params;

	public CatalogIsProductCondition(Map<String, String> _params) {

		this.paramProduct = _params.get(PRODUCT);
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(ProductSku _sku){
		ProductSku sku = _sku;
		logger
				.debug(new StringBuffer().append("[PRODUCT|").append(
						paramProduct).append("]").append("~~(PRODUCT|").append(
						sku.getProduct().getProductId()).append(")(SKU|").append(sku.getProductSkuId()).append(")").toString());
		if (sku.getSalePrice() == null) {
			if (sku.getProduct().getProductId().toString().equals(paramProduct)) {
				// found it
				return true;
			}
		}
		return false;

	}

	public boolean getResult(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		for (ShoppingcartItem cartItem : cart.getShoppingCartItemsForPromoInCondition()) {
			if (getResult(cartItem.getProductSku())) {
				cartItem.setCatalogConditionRunInAnyFlagRAM(true);
			} else {
				cartItem.setCatalogConditionRunInAllFlagRAM(false);
			}
		}
		// must return true;
		return true;

	}
	
	public boolean getResult(Product product) {
		boolean flag = false;
		for(ProductSku sku : product.getProductSkus()){
			if(getResult(sku)){
				flag = true;
				break;
			}
		}
		return flag;
	}

}

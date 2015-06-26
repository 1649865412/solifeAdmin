
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

/**
 * CatalogIsBrandCondition is $YY(key=BRAND)
 * 
 * @author CartMatic
 * 
 */
public class CatalogIsBrandCondition extends CatalogAbstractCondition {
	private static final Log	logger	= LogFactory
												.getLog(CatalogIsBrandCondition.class);

	public static final String	BRAND	= "BRAND";

	private String				paramBrand;
	private Map<String, String>	params;

	public CatalogIsBrandCondition(Map<String, String> _params) {

		this.paramBrand = _params.get(BRAND);
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(ProductSku _sku) {
		ProductSku sku = _sku;
		logger.debug(new StringBuffer().append("[BRAND|").append(paramBrand)
				.append("]").append("~~(BRAND|").append(sku.getProduct().getBrand()==null?null:sku.getProduct().getBrand().getBrandId()).append(")(SKU|").append(sku.getProductSkuId()).append(")").toString());
		if (sku.getSalePrice() == null) {
			if (sku.getProduct().getBrand()!=null && sku.getProduct().getBrand().getBrandId().toString().equals(
					paramBrand)) {
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
			}else{
				cartItem.setCatalogConditionRunInAllFlagRAM(false);
				
			}
		}
		//must return true;
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

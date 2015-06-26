
package com.cartmatic.estore.sales.model.condition;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.util.PromoDependServicesUtil;

/**
 * CatalogInCategoryCondition in $YY(key=CATEGORY)
 * 
 * @author CartMatic
 * 
 */
public class CatalogInCategoryCondition extends CatalogAbstractCondition {
	private static final Log	logger		= LogFactory
													.getLog(CatalogInCategoryCondition.class);

	public static final String	CATEGORY	= "CATEGORY";

	private String				paramCategory;
	private Map<String, String>	params;

	public CatalogInCategoryCondition(Map<String, String> _params) {

		this.paramCategory = _params.get(CATEGORY);
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public boolean getResult(ProductSku _sku) {
		ProductSku sku = _sku;
		logger.debug(new StringBuffer().append("[CATEGORY|").append(
				paramCategory).append("]").append("~~(SKU|").append(sku.getProductSkuId()).append(")").toString());
		if (sku.getSalePrice() == null) {
			if (PromoDependServicesUtil.getProductService().isInCategoryBySku(
					sku.getProductSkuId(), new Integer(paramCategory))) {
				if (!isSkuExcluded(params, sku.getProductSkuId().toString())
						&& !isProductExcluded(params, sku.getProductId()
								.toString())
						&& !isCategoryExcluded(params, sku.getProductSkuId()
								.toString())) {
					// found it
					return true;
				}
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

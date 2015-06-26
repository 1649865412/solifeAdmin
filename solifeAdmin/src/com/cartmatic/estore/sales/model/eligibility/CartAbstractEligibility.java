
package com.cartmatic.estore.sales.model.eligibility;

import java.util.Map;

import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.model.BaseElement;

/**
 * 所有ShoppingcartPromotion类的资质必须扩展该类 这个一个适配器类
 * 
 * @author CartMatic
 * 
 */
public abstract class CartAbstractEligibility extends BaseElement implements
		Eligibility {
	public abstract boolean isMatch(AppUser _customer);

	public abstract Map<String, String> getParams();
}


package com.cartmatic.estore.sales.engine.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.action.Action;

/**
 * ActionManager do actions
 * 
 * @author CartMatic
 * 
 */
public class ActionHandler {
	private static final Log	logger	= LogFactory
	.getLog(ActionHandler.class);
	public static final String	SEPARATOR_BEW_PARAM_AND_PARAM	= ",";
	private List<Action>		actions;

	public ActionHandler(List<Action> _actions) {
		this.actions = _actions;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		for (Action action : actions) {
			try {
				cart = action.run(cart);
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace(System.out);
				
			}
		}
		return cart;
	}

	public ProductSku run(ProductSku _sku) {
		ProductSku sku = _sku;
		for (Action action : actions) {
			try {
				sku = action.run(sku);
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
		}
		return sku;
	}

}

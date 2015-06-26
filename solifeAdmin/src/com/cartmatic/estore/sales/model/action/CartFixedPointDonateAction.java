
package com.cartmatic.estore.sales.model.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartFixedPointDonateAction donate fixed pointed (key=POINT)
 * 
 * @author CartMatic
 * 
 */
public class CartFixedPointDonateAction extends CartAbstractAction {
	private static final Log	logger	= LogFactory
												.getLog(CartFixedPointDonateAction.class);
	public static final String	POINT	= "POINT";
	private Integer				point;
	private Map<String, String>	params;

	public CartFixedPointDonateAction(Map<String, String> _params)
			throws Exception {
		this.point = Integer.parseInt(_params.get(POINT));
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		logger.debug(new StringBuffer().append("[POINT|").append(point).append(
				"]").toString());

		cart.setGainedPointRAM(cart.getGainedPointRAM() + point);

		return cart;
	}
}

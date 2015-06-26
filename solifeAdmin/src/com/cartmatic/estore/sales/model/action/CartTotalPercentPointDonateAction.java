
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartTotalPercentPointDonateAction donate point by the total percent (key=
 * DONATE_PERCENT)
 * 
 * @author CartMatic
 * 
 */
public class CartTotalPercentPointDonateAction extends CartAbstractAction {
	private static final Log	logger			= LogFactory
														.getLog(CartTotalPercentPointDonateAction.class);
	public static final String	DONATE_PERCENT	= "DONATE_PERCENT";
	private BigDecimal			paramDonatePercent;
	private Map<String, String>	params;

	public CartTotalPercentPointDonateAction(Map<String, String> _params)
			throws Exception {
		this.paramDonatePercent = new BigDecimal(_params.get(DONATE_PERCENT));
		if (paramDonatePercent.compareTo(new BigDecimal(0)) == -1
				|| paramDonatePercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartTotalPercentPointDonateAction--error percent,it should between 0 to 100");
		} else {
			paramDonatePercent = paramDonatePercent.divide(new BigDecimal(100));
		}
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		StringBuffer sb = new StringBuffer();
		sb.append("[DONATE_PERCENT|").append(paramDonatePercent).append("]");
		logger.debug(sb);

		cart.setGainedPointRAM(cart.getGainedPointRAM()
				+ cart.getTotal().multiply(paramDonatePercent).intValue());

		return cart;
	}
}

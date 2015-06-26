
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartShippingFeePercentDiscountAction XX%(key=DISCOUNT_PERCENT) off OF
 * shipping method
 * 
 * @author CartMatic
 * 
 */
public class CartShippingFeePercentDiscountAction extends CartAbstractAction {
	private static final Log	logger							= LogFactory
																		.getLog(CartShippingFeePercentDiscountAction.class);
	public static final String	DISCOUNT_PERCENT				= "DISCOUNT_PERCENT";
	public static final String	SHIPPING_METHOD					= "SHIPPING_METHOD";
	public static final String	SEPARATOR_BEW_PARAM_AND_PARAM		= ",";
	public static final String	SEPARATOR_BEW_METHOD_AND_PERCENT	= "-";
	public static final String	FLAG_AFTER_PERCENT					= "%";
	private BigDecimal		paramDiscountPercent;
	private String			paramShippingmethod;
	private Map<String, String>	params;

	public CartShippingFeePercentDiscountAction(Map<String, String> _params)
			throws Exception {
		this.paramShippingmethod = _params.get(SHIPPING_METHOD);
		this.paramDiscountPercent = new BigDecimal(_params.get(DISCOUNT_PERCENT));
		if (paramDiscountPercent.compareTo(new BigDecimal(0)) == -1
				|| paramDiscountPercent.compareTo(new BigDecimal(100)) == 1) {
			throw new Exception(
					"CartShippingFeePercentDiscountAction-error percent,it should between 0 to 100");
		} else {
			paramDiscountPercent = paramDiscountPercent
					.divide(new BigDecimal(100));
		}
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		
		logger.debug(new StringBuffer().append("[DISCOUNT_PERCENT|").append(paramDiscountPercent)
				.append("]").append("[SHIPPING_METHOD|").append(paramShippingmethod)
				.append("]").toString());
		
		
		StringBuffer info = new StringBuffer();
		cart.setShippingDiscountInfoRAM(cart.getShippingDiscountInfoRAM()+info.append(SEPARATOR_BEW_PARAM_AND_PARAM).append(paramShippingmethod).append(SEPARATOR_BEW_METHOD_AND_PERCENT).append(FLAG_AFTER_PERCENT).append(paramDiscountPercent).toString());

		return cart;
	}
}

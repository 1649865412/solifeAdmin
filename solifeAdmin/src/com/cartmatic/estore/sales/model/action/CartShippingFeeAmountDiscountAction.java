
package com.cartmatic.estore.sales.model.action;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;

/**
 * CartShippingFeeAmountDiscountAction XX%(key=DISCOUNT_AMOUNT) off OF
 * shipping method
 * 
 * @author CartMatic
 * 
 */
public class CartShippingFeeAmountDiscountAction extends CartAbstractAction {
	private static final Log	logger							= LogFactory
																		.getLog(CartShippingFeeAmountDiscountAction.class);
	public static final String	DISCOUNT_AMOUNT				= "DISCOUNT_AMOUNT";
	public static final String	SHIPPING_METHOD					= "SHIPPING_METHOD";
	public static final String	SEPARATOR_BEW_PARAM_AND_PARAM		= ",";
	public static final String	SEPARATOR_BEW_METHOD_AND_PERCENT	= "-";
	public static final String	FLAG_AFTER_AMOUNT					= "$";
	private BigDecimal		paramDiscountAmount;
	private String			paramShippingmethod;
	private Map<String, String>	params;

	public CartShippingFeeAmountDiscountAction(Map<String, String> _params)
			throws Exception {
		this.paramShippingmethod = _params.get(SHIPPING_METHOD);
		this.paramDiscountAmount = new BigDecimal(_params.get(DISCOUNT_AMOUNT));
		
		this.params = _params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Shoppingcart run(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		logger.debug(new StringBuffer().append("[DISCOUNT_AMOUNT|").append(paramDiscountAmount)
				.append("]").append("[SHIPPING_METHOD|").append(paramShippingmethod)
				.append("]").toString());
		
		
		StringBuffer info = new StringBuffer();
		cart.setShippingDiscountInfoRAM(info.append(cart.getShippingDiscountInfoRAM()).append(SEPARATOR_BEW_PARAM_AND_PARAM).append(paramShippingmethod).append(SEPARATOR_BEW_METHOD_AND_PERCENT).append(FLAG_AFTER_AMOUNT).append(paramDiscountAmount).toString());

		return cart;
	}
}

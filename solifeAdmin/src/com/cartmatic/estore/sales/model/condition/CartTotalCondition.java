package com.cartmatic.estore.sales.model.condition;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.Product;

/**
 * CartSubtotalCondition cart subTotal(standardTotoalPrice) is at least
 * $XX(key=SUBTOTAL_AMOUNT)
 * 
 * @author CartMatic
 * 
 */
public class CartTotalCondition extends CartAbstractCondition {
	private static final Log logger = LogFactory.getLog(CartTotalCondition.class);
	public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
	private BigDecimal paramTotalAmount;
	private Map<String, String> params;

	public CartTotalCondition(Map<String, String> _params) {
		this.paramTotalAmount = new BigDecimal( _params.get(TOTAL_AMOUNT));
		this.params = _params;
	}
	public Map<String, String> getParams()
	{
		return params;
	}
	
	public boolean getResult(Shoppingcart _cart) {
		logger.debug(new StringBuffer().append("[TOTAL_AMOUNT|").append(paramTotalAmount).append("]~~{").append(_cart.getTotal()).append("}").toString());
		return ((paramTotalAmount.compareTo(_cart.getTotal())) == 1) ? false
				: true;

	}

	public boolean getResult(Product product) {
		return true;
	}
}

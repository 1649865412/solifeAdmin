
package com.cartmatic.estore.sales.engine.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.sales.model.condition.Condition;

/**
 * ConditionMaintainManager condition maintain
 * 
 * @author CartMatic
 * 
 */
public class ConditionMaintainHandler {
	private static final Log	logger							= LogFactory
																		.getLog(ConditionMaintainHandler.class);
	public static final short	CONDITION_OPERATOR_ALL			= 1;
	public static final short	CONDITION_OPERATOR_ANY			= 0;
	public static final short	CATALOG_CONDITION_OPERATOR_ALL	= 3;
	public static final short	CATALOG_CONDITION_OPERATOR_ANY	= 2;

	private List<Condition>		conditions;
	private short				conditionOperator;

	public ConditionMaintainHandler(short _conditionOperator,
			List<Condition> _conditions) {
		this.conditionOperator = _conditionOperator;
		this.conditions = _conditions;
	}

	public boolean run(Shoppingcart _cart) {
		if (null != conditions && conditions.size() != 0) {
			if (CONDITION_OPERATOR_ALL == conditionOperator) {
				return runInAllCase(_cart);
			}
			if (CONDITION_OPERATOR_ANY == conditionOperator) {
				return runInAnyCase(_cart);
			}
			if (CATALOG_CONDITION_OPERATOR_ALL == conditionOperator) {
				return runCatalogPromoInAllCase(_cart);
			}
			if (CATALOG_CONDITION_OPERATOR_ANY == conditionOperator) {
				return runCatalogPromoInAnyCase(_cart);
			}
			return false;
		} else {
			logger
			.debug(new StringBuffer()
					.append("*****No conditions! ConditionMaintainHandler get a [FALSE] result*****"));
			return false;
		}
	}

	public boolean runCatalogPromoInAllCase(Shoppingcart _cart) {
		for (Condition condition : conditions) {
			
			try {
				 condition.getResult(_cart);
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
			
		}
		Shoppingcart cart = _cart;
		for (ShoppingcartItem cartItem : cart.getShoppingCartItemsForPromoInAction()) {
				cartItem.setCatalogConditonFlagRAM(cartItem
						.isCatalogConditionRunInAllFlagRAM());
		}
		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [True] result*****"));
		return true;
	}
	
	
	public boolean runCatalogPromoInAnyCase(Shoppingcart _cart) {
		for (Condition condition : conditions) {
			try {
				 condition.getResult(_cart);
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
		}
		Shoppingcart cart = _cart;
		for (ShoppingcartItem cartItem : cart.getShoppingCartItemsForPromoInAction()) {
				cartItem.setCatalogConditonFlagRAM(cartItem
						.isCatalogConditionRunInAnyFlagRAM());
		}
		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [True] result*****"));
		return true;
	}

	public boolean runInAllCase(Shoppingcart _cart) {
		for (Condition condition : conditions) {
			boolean flag;
			try {
				flag = condition.getResult(_cart);
			} catch (Exception e) {
				flag = false;
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
			if (!flag) {
				logger.debug(new StringBuffer().append("Condition ").append(
						condition.getClass().getSimpleName()).append(
						" get a [False] result"));
				logger
						.debug(new StringBuffer()
								.append("*****ConditionMaintainHandler get a [False] result*****"));
				return false;
			}
		}

		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [True] result*****"));
		return true;
	}

	public boolean runInAnyCase(Shoppingcart _cart) {
		for (Condition condition : conditions) {
			boolean flag;
			try {
				flag = condition.getResult(_cart);
			} catch (Exception e) {
				flag = false;
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
			if (flag) {
				logger.debug(new StringBuffer().append("Condition ").append(
						condition.getClass().getSimpleName()).append(
						" get a [True] result"));
				logger
						.debug(new StringBuffer()
								.append("*****ConditionMaintainHandler get a [True] result*****"));
				return true;
			}
		}
		
		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [False] result*****"));
		return false;
	}

	public boolean run(ProductSku _sku) {
		if (null != conditions && conditions.size() != 0) {
			if (CONDITION_OPERATOR_ALL == conditionOperator) {
				return runInAllCase(_sku);
			}
			if (CONDITION_OPERATOR_ANY == conditionOperator) {
				return runInAnyCase(_sku);
			}
			return false;
		} else {
			logger
			.debug(new StringBuffer()
					.append("*****No conditions! ConditionMaintainHandler get a [True] result*****"));
			return true;
		}
	}

	public boolean runInAllCase(ProductSku _sku) {
		for (Condition condition : conditions) {
			boolean flag;
			try {
				flag = condition.getResult(_sku);
			} catch (Exception e) {
				flag = false;
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
			if (!flag) {
				logger.debug(new StringBuffer().append("Condition ").append(
						condition.getClass().getSimpleName()).append(
						" get a [False] result"));
				logger
						.debug(new StringBuffer()
								.append("*****ConditionMaintainHandler get a [False] result*****"));
				return false;
			}
		}
		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [True] result*****"));
		return true;
	}

	public boolean runInAnyCase(ProductSku _sku) {
		for (Condition condition : conditions) {
			boolean flag;
			try {
				flag = condition.getResult(_sku);
			} catch (Exception e) {
				flag = false;
				logger.error(e.toString());
				e.printStackTrace(System.out);
			}
			if (flag) {
				logger.debug(new StringBuffer().append("Condition ").append(
						condition.getClass().getSimpleName()).append(
						" get a [True] result"));
				logger
						.debug(new StringBuffer()
								.append("*****ConditionMaintainHandler get a [True] result*****"));
				return true;
			}
		}
		logger
				.debug(new StringBuffer()
						.append("*****ConditionMaintainHandler get a [False] result*****"));
		return false;
	}
	
	public boolean run(Product product){
		boolean flag = false;
		for(Condition condition : conditions){
			flag = condition.getResult(product);
		}
		return flag ;
	}
}

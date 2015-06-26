package com.cartmatic.estore.sales.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.sales.model.condition.CartAbstractCondition;
import com.cartmatic.estore.sales.model.condition.CartAnySkuInCartCondition;
import com.cartmatic.estore.sales.model.condition.CartContainsItemsOfCategoryCondition;
import com.cartmatic.estore.sales.model.condition.CartProductInCartCondition;
import com.cartmatic.estore.sales.model.condition.CartSkuInCartCondition;
import com.cartmatic.estore.sales.model.condition.CartTotalCondition;

public class ConditionMaintainMocker {
	private static final Log logger = LogFactory.getLog(ConditionMaintainMocker.class);

	public static List<CartAbstractCondition> getData() {
		List<CartAbstractCondition> conditions = new ArrayList<CartAbstractCondition>();
		CartAbstractCondition condition;
		Map<String, String> params;
		// 1 ProductInCartCondition-------------------
		params = new HashMap<String, String>();
		params.put(CartProductInCartCondition.NUMITEMS_QUANTIFIER,
				String.valueOf(CartProductInCartCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ));
		params.put(CartProductInCartCondition.NUMITEMS, "3");
		params.put(CartProductInCartCondition.PRODUCT, "101");

		condition = new CartProductInCartCondition(params);
		conditions.add(condition);

		// 2 CartSubtotalCondition-------------------
		params = new HashMap<String, String>();
		params.put(CartTotalCondition.TOTAL_AMOUNT, "5000");

		condition = new CartTotalCondition(params);
		conditions.add(condition);

		// 3 SKUInCartCondition-------------------
		params = new HashMap<String, String>();
		params.put(CartSkuInCartCondition.NUMITEMS_QUANTIFIER,
				String.valueOf(CartSkuInCartCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ));
		params.put(CartSkuInCartCondition.NUMITEMS, "3");
		params.put(CartSkuInCartCondition.SKU, "nokia-600-1");

		condition = new CartSkuInCartCondition(params);
		conditions.add(condition);

		// 4 SKUInCartCondition-------------------
		params = new HashMap<String, String>();
		params
				.put(
						CartContainsItemsOfCategoryCondition.NUMITEMS_QUANTIFIER,
						String.valueOf(CartContainsItemsOfCategoryCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ));
		params.put(CartContainsItemsOfCategoryCondition.NUMITEMS, "3");
		params.put(CartContainsItemsOfCategoryCondition.CATEGORY,
				"/promotion-B");

		condition = new CartContainsItemsOfCategoryCondition(params);
		conditions.add(condition);

		// 5 AnySkuInCartCondition-------------------
		params = new HashMap<String, String>();
		params.put(CartAnySkuInCartCondition.NUMITEMS_QUANTIFIER,
				String.valueOf(CartAnySkuInCartCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ));
		params.put(CartAnySkuInCartCondition.NUMITEMS, "3");

		condition = new CartAnySkuInCartCondition(params);
		conditions.add(condition);
		
		

		return conditions;
	}

	public static void print(List<CartAbstractCondition> conditions){
		for(CartAbstractCondition condition : conditions){
			logger.debug(new StringBuffer().append("the condition _class == ").append(condition.getClass().getSimpleName()));
			
			StringBuffer sb = new StringBuffer();
			sb.append("parameters : ");
			for(String key: condition.getParams().keySet()){
				sb.append("["+key).append("--").append(condition.getParams().get(key)+"]");
			}
			logger.debug(sb);
		}
		
		return;
	}
}

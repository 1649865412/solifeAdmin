package com.cartmatic.estore.sales.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.sales.model.action.CartAbstractAction;
import com.cartmatic.estore.sales.model.action.CartTotalAmountDiscountAction;
import com.cartmatic.estore.sales.model.action.CartTotalPercentDiscountAction;

public class ActionMocker {
	private static final Log logger = LogFactory.getLog(ActionMocker.class);

	public static List<CartAbstractAction> getData() throws Exception{
		List<CartAbstractAction> actions = new ArrayList<CartAbstractAction>();
		CartAbstractAction action;
		Map<String, String> params;
		
		// 1 CartSubtotalAmountDiscountAction-------------------
		params = new HashMap<String, String>();
		
		params.put(CartTotalAmountDiscountAction.DISCOUNT_AMOUNT, "35");

		action = new CartTotalAmountDiscountAction(params);
		actions.add(action);
		
		// 2 CartSubtotalPercentDiscountAction-------------------
		params = new HashMap<String, String>();
		
		params.put(CartTotalPercentDiscountAction.DISCOUNT_PERCENT, "50");

		action = new CartTotalPercentDiscountAction(params);
		actions.add(action);
		
		
//		// 1 CartCategoryAmountDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartCategoryAmountDiscountAction.DISCOUNT_AMOUNT, "300");
//		params.put(CartCategoryAmountDiscountAction.CATEGORY, "/promotion-B");
//
//		action = new CartCategoryAmountDiscountAction(params);
//		actions.add(action);
//
//		// 2 CartCategoryAmountDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartCategoryPercentDiscountAction.DISCOUNT_PERCENT, "80");
//		params.put(CartCategoryPercentDiscountAction.CATEGORY_ID, "/promotion-B");
//
//		action = new CartCategoryPercentDiscountAction(params);
//		actions.add(action);
//		
//		// 3 CartFreeProductDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartFreeProductDiscountAction.NUMITEMS, "1");
//		params.put(CartFreeProductDiscountAction.PRODUCT_ID, "301");
//
//		action = new CartFreeProductDiscountAction(params);
//		actions.add(action);
//		
//		// 4 CartProductAmountDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartProductAmountDiscountAction.DISCOUNT_AMOUNT, "50");
//		params.put(CartProductAmountDiscountAction.PRODUCT, "301");
//
//		action = new CartProductAmountDiscountAction(params);
//		actions.add(action);
//		
//		// 5 CartProductPercentDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartProductPercentDiscountAction.DISCOUNT_PERCENT, "60");
//		params.put(CartProductPercentDiscountAction.PRODUCT, "101");
//
//		action = new CartProductPercentDiscountAction(params);
//		actions.add(action);
//		
//		// 6 CartSkuAmountDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartSkuAmountDiscountAction.DISCOUNT_AMOUNT, "30");
//		params.put(CartSkuAmountDiscountAction.SKU, "sony-800-1");
//
//		action = new CartSkuAmountDiscountAction(params);
//		actions.add(action);
//		
//		// 7 CartSkuPercentDiscountAction-------------------
//		params = new HashMap<String, String>();
//		
//		params.put(CartSkuPercentDiscountAction.DISCOUNT_PERCENT, "90");
//		params.put(CartSkuPercentDiscountAction.SKU, "sony-800-1");
//
//		action = new CartSkuPercentDiscountAction(params);
//		actions.add(action);
//

		
		return actions;
		
	}

	public static void print(List<CartAbstractAction> actions){
		for(CartAbstractAction action : actions){
			logger.debug(new StringBuffer().append("the action _class == ").append(action.getClass().getSimpleName()));
			
			StringBuffer sb = new StringBuffer();
			sb.append("parameters : ");
			for(String key: action.getParams().keySet()){
				sb.append("["+key).append("--").append(action.getParams().get(key)+"]");
			}
			logger.debug(sb);
		}
		
		return;
	}
}

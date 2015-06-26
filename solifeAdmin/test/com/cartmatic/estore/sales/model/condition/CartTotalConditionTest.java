package com.cartmatic.estore.sales.model.condition;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.sales.model.condition.CartTotalCondition;

public class CartTotalConditionTest extends TestCase {
	
	private static final Log logger = LogFactory.getLog(CartTotalConditionTest.class);
	private CartTotalCondition condition;
	public Map<String,String> getMapData(){
		Map<String,String> params = new HashMap<String,String>();
		params.put(CartTotalCondition.TOTAL_AMOUNT, "500");
		return params;
	}
	public void testGetResult(){
		
//		condition = new CartSubtotalCondition(getMapData());
//		List<CartItem> data = CartItemsHelper.getData();
//		CartItemsHelper.print(data);
//
//		if(condition.getResult(data)){
//			logger.debug("the condition == TRUE");
//		}else{
//			logger.debug("the condition == FALSE");
//		}
		
	}
}

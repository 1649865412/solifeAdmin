package com.cartmatic.estore.sales.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.sales.engine.PRule;
import com.cartmatic.estore.sales.engine.handler.ConditionMaintainHandler;
import com.cartmatic.estore.sales.model.action.CartTotalPercentDiscountAction;
import com.cartmatic.estore.sales.model.condition.CartAnySkuInCartCondition;

public class RuleMocker {
	private static final Log logger = LogFactory.getLog(RuleMocker.class);
	public static List<PromoRule> getData(){
		List<PromoRule> rules = new ArrayList<PromoRule>();
		PromoRule rule;
		PromoRuleElement element;
		Set<PromoRuleParameter> params;
		rule = new PromoRule();
		rule.setConditionOperator(ConditionMaintainHandler.CONDITION_OPERATOR_ALL);
		rule.setEligibilityOperator(ConditionMaintainHandler.CONDITION_OPERATOR_ALL);
		rule.setEnableDiscountAgain(new Short("1"));
		
		
		//set condition
		element = new PromoRuleElement();
		element.setKind(PromoRuleElement.KIND_OF_CONDITION);
		element.setType(CartAnySkuInCartCondition.class.getSimpleName());
		params = new HashSet<PromoRuleParameter>(); 
		params.add(new PromoRuleParameter(CartAnySkuInCartCondition.NUMITEMS,"5"));
		params.add(new PromoRuleParameter(CartAnySkuInCartCondition.NUMITEMS_QUANTIFIER,String.valueOf(CartAnySkuInCartCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ)));
		element.setPromoRuleParameters(params);
		rule.addPromoRuleElement(element);
		
//		element = new PromoRuleElement();
//		element.setKind(PromoRuleElement.KIND_OF_CONDITION);
//		element.setType(CartProductInCartCondition.class.getSimpleName());
//		params = new HashSet<PromoRuleParameter>(); 
//		params.add(new PromoRuleParameter(CartProductInCartCondition.NUMITEMS,"2"));
//		params.add(new PromoRuleParameter(CartProductInCartCondition.NUMITEMS_QUANTIFIER,String.valueOf(CartProductInCartCondition.NUMITEMS_QUANTIFIER_TYPE_OF_GT_OR_EQ)));
//		params.add(new PromoRuleParameter(CartProductInCartCondition.PRODUCT,"301"));
//		element.setPromoRuleParameters(params);
//		rule.addPromoRuleElement(element);
		
		//set action

		element = new PromoRuleElement();
		element.setKind(PromoRuleElement.KIND_OF_ACTION);
		element.setType(CartTotalPercentDiscountAction.class.getSimpleName());
		params = new HashSet<PromoRuleParameter>(); 
		params.add(new PromoRuleParameter(CartTotalPercentDiscountAction.DISCOUNT_PERCENT,"80"));
		element.setPromoRuleParameters(params);
		rule.addPromoRuleElement(element);
		
		rules.add(rule);
		
		return rules;
		
		
	}
	
	
	
	public static void print(List<PromoRule> rules){
		for(PromoRule rule : rules){
			logger.debug(new StringBuffer().append("PRule===").append("[condition-operator--").append(rule.getConditionOperator()).append("]"));
			for(PromoRuleElement element: rule.getPromoRuleElements()){
				logger.debug(new StringBuffer().append(" Element===").append("[kind-").append(element.getKind()).append("][type-").append(element.getType()).append("]"));
				
				for(PromoRuleParameter param : element.getPromoRuleParameters()){
					logger.debug(new StringBuffer().append(" ------Param=== [").append(param.getParamName()).append("-").append(param.getParamValue()).append("]"));
				}
			}
		}
	}
	
}

package com.cartmatic.estore.sales.util;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.sales.util.PromoRuleUtil;

public class PromoRuleUtilTest extends TestCase {
	private static final Log logger = LogFactory.getLog(PromoRuleUtilTest.class);
	public void testGet(){
		String elementString = "elementString=e0,eligibility,EveryoneEligibility|";
		logger.info(PromoRuleUtil.getElement(elementString));
		logger.info(PromoRuleUtil.getElement(elementString).getPromoRuleElementIdString());
		List<PromoRuleParameter> paramers = PromoRuleUtil.getParameters(elementString);
		for(PromoRuleParameter param :paramers){
			logger.info(param.getPromoRuleParameterIdString()+":"+param.getParamName()+":"+param.getParamValue());
			
			
		}
	}
}


package com.cartmatic.estore.sales.engine.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.model.eligibility.Eligibility;

/**
 * EligibilityManager manager eligibility
 * 
 * @author CartMatic
 * 
 */
public class EligibilityHandler {
	private static final Log			logger						= LogFactory
																			.getLog(EligibilityHandler.class);
	public static final short			ELIGIBILITY_OPERATOR_ALL	= 1;
	public static final short			ELIGIBILITY_OPERATOR_ANY	= 0;
	private short						eligibilityOperator;

	private List<Eligibility>	eligibilitys;

	public EligibilityHandler(Short _eligibilityOperator,
			List<Eligibility> _eligibilitys) {
		this.eligibilityOperator = _eligibilityOperator;
		this.eligibilitys = _eligibilitys;
	}

	public boolean isMatch(AppUser _customer) {
		if (null != eligibilitys && eligibilitys.size() != 0) {
			if (ELIGIBILITY_OPERATOR_ALL == eligibilityOperator) {
				return isMatchInAllCase(_customer);
			}
			if (ELIGIBILITY_OPERATOR_ANY == eligibilityOperator) {
				return isMatchInAnyCase(_customer);
			}
			return false;
		}else{
			logger.debug(new StringBuffer()
			.append("*****No eligibility! EligibilityHandler get a [FALSE] result*****"));
			return false;
		}
	}

	public boolean isMatchInAllCase(AppUser _customer) {
		for (Eligibility eligibility : eligibilitys) {
			boolean flag;
			try{
				flag = eligibility.isMatch(_customer);
			}catch(Exception e){
				flag = false;
			}
			if (!flag) {
				logger.debug(new StringBuffer().append("Eligibility ").append(
						eligibility.getClass().getSimpleName()).append(
						" get a [False] result"));
				logger
						.debug(new StringBuffer()
								.append("*****EligibilityHandler get a [False] result*****"));
				return false;
			}
		}
		logger.debug(new StringBuffer()
				.append("*****EligibilityHandler get a [True] result*****"));
		return true;
	}

	public boolean isMatchInAnyCase(AppUser _customer) {
		for (Eligibility eligibility : eligibilitys) {
			boolean flag;
			try{
				flag = eligibility.isMatch(_customer);
			}catch(Exception e){
				flag = false;
			}
			if (flag) {
				logger.debug(new StringBuffer().append("Eligibility ").append(
						eligibility.getClass().getSimpleName()).append(
						" get a [True] result"));
				logger
						.debug(new StringBuffer()
								.append("*****EligibilityHandler get a [True] result*****"));
				return true;
			}
		}
		logger.debug(new StringBuffer()
				.append("*****EligibilityHandler get a [False] result*****"));
		return false;
	}
	
	
}


package com.cartmatic.estore.sales.model.eligibility;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.util.PromoDependServicesUtil;

public class FirstTimeBuyerEligibility extends CartAbstractEligibility {
	private static final Log	logger	= LogFactory
												.getLog(FirstTimeBuyerEligibility.class);
	private Map<String, String>	params;

	public FirstTimeBuyerEligibility(Map<String, String> _params) {
		this.params = _params;
	}

	public boolean isMatch(AppUser _customer) {
		try {
			if (_customer != null
					&& PromoDependServicesUtil.getOrderService()
							.isCustomerOrderNumZero(_customer.getAppuserId())) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public Map<String, String> getParams() {
		return params;
	}
}


package com.cartmatic.estore.sales.model.eligibility;

import java.util.Map;

import com.cartmatic.estore.core.model.AppUser;


public class SampleEligibility extends CartAbstractEligibility {
	private Map<String, String>	params;

	public SampleEligibility(Map<String, String> _params) {
		this.params = _params;
	}

	public boolean isMatch(AppUser _customer) {
		return true;
	}
	public  Map<String, String> getParams(){
		return params;
	}
}

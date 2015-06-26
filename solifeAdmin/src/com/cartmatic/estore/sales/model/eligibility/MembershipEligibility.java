package com.cartmatic.estore.sales.model.eligibility;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.model.AppUser;


public class MembershipEligibility extends CartAbstractEligibility {
	
	private static final Log logger = LogFactory.getLog(MembershipEligibility.class);
	public static final String MEMBERSHIP = "MEMBERSHIP";
	private String paramMembership;
	private Map<String, String> params;
	
	public MembershipEligibility(Map<String, String> _params) {
		this.paramMembership = _params.get(MEMBERSHIP);
		this.params = _params;
	}
	public Map<String, String> getParams()
	{
		return params;
	}
	
	public boolean isMatch(AppUser _customer) {
		logger.debug(new StringBuffer().append("[MEMBERSHIP|").append(paramMembership).append("]~~{").append((_customer!=null && ((Customer)_customer).getMembershipId()!=null)?((Customer)_customer).getMembershipId():null).append("}").toString());
		
		if(_customer!=null && ((Customer)_customer).getMembershipId()!=null && ((Customer)_customer).getMembershipId().toString().equals(paramMembership)){
			return true;
		}
		return false;
	}

}

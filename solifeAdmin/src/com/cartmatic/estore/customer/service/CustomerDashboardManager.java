package com.cartmatic.estore.customer.service;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.service.GenericManager;


public interface CustomerDashboardManager extends GenericManager<Customer>{
	Long getCustomerTotalByStatus(Short status);

	Long getCustomerTotalByMembership(Integer membership);
	
	public List getCustomerMembershipTotals();
	
	/**
	 * 统计一日增加的客户数量
	 * 忽略客户状态
	 * @param date
	 * @return
	 */
	public long countAddedCustomer4Day(Date date);
}

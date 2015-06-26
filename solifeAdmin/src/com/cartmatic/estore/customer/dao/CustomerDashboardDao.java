package com.cartmatic.estore.customer.dao;

import java.util.Date;
import java.util.List;


public interface CustomerDashboardDao {
	Long getCustomerTotalByStatus(Short status);

	Long getCustomerTotalByMembership(Integer membership);
	
	public List getCustomerMembershipTotals();
	
	/**
	 * 指定时间内新增的客户数量
	 * 范围包含endDate,忽略所有状态
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public long countAddedCustomer4Time(Date beginDate,Date endDate);
}

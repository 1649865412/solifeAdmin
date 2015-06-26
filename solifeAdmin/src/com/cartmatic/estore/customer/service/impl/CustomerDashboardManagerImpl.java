package com.cartmatic.estore.customer.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.CustomerDashboardDao;
import com.cartmatic.estore.customer.service.CustomerDashboardManager;

public class CustomerDashboardManagerImpl extends GenericManagerImpl<Customer> implements CustomerDashboardManager{
	CustomerDashboardDao customerDashboardDao;
	public Long getCustomerTotalByStatus(Short status) {
		return customerDashboardDao.getCustomerTotalByStatus(status);
	}

	public void setCustomerDashboardDao(CustomerDashboardDao customerDashboardDao) {
		this.customerDashboardDao = customerDashboardDao;
	}

	public Long getCustomerTotalByMembership(Integer membership) {
		return customerDashboardDao.getCustomerTotalByMembership(membership);
	}

	public List getCustomerMembershipTotals(){
		return customerDashboardDao.getCustomerMembershipTotals();
	}
	
	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDelete(Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(Customer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long countAddedCustomer4Day(Date date) {
		Date beginDate=DateUtil.getStartOfThisDay(date);
		Date endDate=DateUtil.getEndOfThisDay(date);
		return customerDashboardDao.countAddedCustomer4Time(beginDate, endDate);
	}
}

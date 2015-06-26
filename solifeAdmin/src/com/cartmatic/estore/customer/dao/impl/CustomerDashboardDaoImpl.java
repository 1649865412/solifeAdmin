package com.cartmatic.estore.customer.dao.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.CustomerDashboardDao;


@SuppressWarnings("unchecked")
public class CustomerDashboardDaoImpl extends HibernateGenericDaoImpl<Customer> implements CustomerDashboardDao{

	
	public Long getCustomerTotalByStatus(Short status) {
		List<Long> result=getHibernateTemplate().find("select count(*) from Customer a where a.userType=0 and a.status=? and a.deleted=0",new Object[]{status});
		return result.get(0);
	}

	public Long getCustomerTotalByMembership(Integer membership) {
		List<Long> result=getHibernateTemplate().find("select count(*) from Customer a where a.userType=0 and a.membershipId=? and a.deleted=0",new Object[]{membership});
		return result.get(0);
	}
	
	public List getCustomerMembershipTotals() {
		List<Long> result=getHibernateTemplate().find("select count(membershipId),membershipId from Customer a where a.userType=0 and a.deleted=0 and a.membershipId is not null group by membershipId");
		return result;
	}

	@Override
	public long countAddedCustomer4Time(Date beginDate, Date endDate) {
		List<Long> result=getHibernateTemplate().find("select count(*) from Customer a where a.userType=0 and a.createTime>=? and a.createTime<=? ",new Object[]{beginDate,endDate});
		return result.get(0);
	}
}

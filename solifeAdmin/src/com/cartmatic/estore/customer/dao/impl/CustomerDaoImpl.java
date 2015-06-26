package com.cartmatic.estore.customer.dao.impl;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.CustomerDao;

/**
 * Dao implementation for AppUser.
*/
public class CustomerDaoImpl extends HibernateGenericDaoImpl<Customer> implements CustomerDao {

	public Long getCustomerCountsByMembershipId(Integer membershipId) {
		String hql="from Customer c where c.membershipId=? and c.deleted=0";
		Long count=countByHql(hql, membershipId);
		return count;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		String hql="from Customer c where c.userType=0 and c.email=?";
		Customer customer=(Customer) findUnique(hql, email);
		return customer;
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		String hql="from Customer c where c.userType=0 and c.username=?";
		Customer customer=(Customer) findUnique(hql, username);
		return customer;
	}

}

package com.cartmatic.estore.customer.dao;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for AppUser.
 */
public interface CustomerDao extends GenericDao<Customer> {
	public Long getCustomerCountsByMembershipId(Integer membershipId);
	
	/**
	 * 根据用户名获取客户
	 * @param username
	 * @return
	 */
	public Customer getCustomerByUsername(String username);
	
	/**
	 * 根据email获取客户
	 * @param email
	 * @return
	 */
	public Customer getCustomerByEmail(String email);
}
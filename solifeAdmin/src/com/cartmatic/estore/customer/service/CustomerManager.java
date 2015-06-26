package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Customer, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface CustomerManager extends GenericManager<Customer> {
	/**
	 * 保存客户资料
	 * 
	 * @param customer
	 * @param customerAddreibuteValue
	 */
	public void saveCustomer(Customer customer,List<AttributeValue> customerAddreibuteValue);
	
	
	/**
	 * register a new customer,
	 * do in a transaction.
	 * @param customer
	 * @param mailEngine
	 * @param mailDataMap
	 * @throws Exception
	 */
	//public void doRegisterCustomerTransaction(Customer customer,MailEngine mailEngine,Map mailDataMap) throws Exception;
	
	/**
	 * 会员等级自动升级
	 */
	public void upgradeMembershipLevelJob();
	
	public Long getCustomerCountsByMembershipId(Integer membershipId);
	
	/**
	 * 获取以客户身份登录前台的URL
	 * @param customerId
	 * @return
	 */
	public String getVirtualLoginUrl(Integer customerId,Integer storeId);
	
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
	
	/**
	 * 为用户发送优惠劵
	 * @param couponTypeId
	 * @param customerId
	 */
	public void addCoupon(Integer couponTypeId, Integer customerId);
}

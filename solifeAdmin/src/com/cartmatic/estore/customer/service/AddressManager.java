package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.AddressModel;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Address, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AddressManager extends GenericManager<Address> {
	/**
	 * 获取用户所有的运输地址
	 * @param appuserId 当前登陆用户的id
	 * @return
	 */
	public List<Address> getAllShippingAddressByAppuserId(Integer appuserId);
	
	
	/**
	 * 获取用户所有的发票地址
	 * @param appuserId
	 * @return
	 */
	public List<Address> getAllBillingAddressByAppuserId(Integer appuserId);
	
	/**
	 * 获取默认的运输地址
	 * @param appuserId
	 * @return
	 */
	public Address getDefShippingAddr(Integer appuserId);
	
	/**
	 * 获取默认发票地址
	 * @param appuserId
	 * @return
	 */
	public Address getDefBillingAddress(Integer appuserId);
	
	/**
	 * 根据地址Id及用户Id获取地址
	 * @param addressId
	 * @param appuserId
	 * @return
	 */
	public Address getAddressByIdAndAppUserId(Integer addressId,Integer appuserId);
	
	/**
	 * 同时创建保存Shipping和Billing
	 * @param addressModel
	 */
	public void createShippingAndBilling(AddressModel addressModel);
	
	
}

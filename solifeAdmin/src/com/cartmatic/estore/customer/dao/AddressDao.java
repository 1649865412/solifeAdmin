package com.cartmatic.estore.customer.dao;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Address.
 */
public interface AddressDao extends GenericDao<Address> {
	/**
	 * 获取用户所有的运输地址
	 * @param appuserId
	 *        当前登陆用户的id
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
	
}
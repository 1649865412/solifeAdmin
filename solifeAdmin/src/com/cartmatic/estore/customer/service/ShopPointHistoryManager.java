package com.cartmatic.estore.customer.service;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ShopPointHistory, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ShopPointHistoryManager extends GenericManager<ShopPointHistory> {
	/**
	 * 供侵入式的代码调用。
	 * @param shopPointType
	 * @param customer
	 */
	public void saveNewShopPointHistory(Short shopPointType,Customer customer);
	
	/**
	 * 添加积分记录，并同时更新该客户对应的积分账户信息
	 * @param shopPointHistory
	 */
	public void saveShopPointHistoryAndUpdateTotal(ShopPointHistory shopPointHistory);
	
	/**
	 * 赠送积分
	 * @param shopPointType
	 * @param customer
	 * @param shopPointTotal
	 */
	public void saveNewShopPointHistory(Short shopPointType,Customer customer,Integer shopPointTotal);
	
	/**
	 * if customer login in the same day more than one time, then return true.
	 * @param customerId
	 * @return
	 */
	public boolean getIsLoginToday(Integer customerId);
	
	//public List<ShopPointHistory> getAllByCustomerId(Integer customerId,PagingBean pb);
}

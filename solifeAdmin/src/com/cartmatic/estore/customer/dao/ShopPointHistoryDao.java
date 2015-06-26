package com.cartmatic.estore.customer.dao;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.model.PagingBean;
/**
 * Dao interface for ShopPointHistory.
 */
public interface ShopPointHistoryDao extends GenericDao<ShopPointHistory> {
	public List<ShopPointHistory> getByCustomerIdAndDateAndType(Integer customerId,Date startDate,Date endDate,Short shopPointType);
	//public List<ShopPointHistory> getAllByCustomerId(Integer customerId, PagingBean pb);
}
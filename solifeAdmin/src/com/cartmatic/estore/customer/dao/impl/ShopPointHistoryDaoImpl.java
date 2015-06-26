package com.cartmatic.estore.customer.dao.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.customer.dao.ShopPointHistoryDao;

/**
 * Dao implementation for ShopPointHistory.
*/
public class ShopPointHistoryDaoImpl extends HibernateGenericDaoImpl<ShopPointHistory> implements ShopPointHistoryDao {

	public List<ShopPointHistory> getByCustomerIdAndDateAndType(Integer customerId, Date startDate, Date endDate, Short shopPointType) {
		String hql="from ShopPointHistory vo where vo.customer.appuserId=? and vo.createTime>=? and vo.createTime<=? and vo.shopPointType=?";
		Object[]paramValues={customerId,startDate,endDate,shopPointType};
		return findByHql(hql, paramValues);
	}

	/*public List<ShopPointHistory> getAllByCustomerId(Integer customerId, PagingBean pb) {
		String hql="from ShopPointHistory vo where vo.customer.appuserId=? order by vo.createTime desc";
		return find(hql,pb,customerId);
	}*/

}

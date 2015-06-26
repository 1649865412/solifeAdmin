/**
 * 
 */
package com.cartmatic.estore.order.service.impl;

import java.util.Date;
import java.util.Map;

import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.dao.SalesOrderDao;
import com.cartmatic.estore.order.service.OrderDashboardManager;

/**
 * @author Administrator
 *
 */
public class OrderDashboardManagerImpl extends GenericManagerImpl<SalesOrder> implements OrderDashboardManager {
	
	private SalesOrderDao salesOrderDao = null;
	
	public void setSalesOrderDao(SalesOrderDao salesOrderDao) {
		this.salesOrderDao = salesOrderDao;
	}

	/**
	 * 后台主页订单统计
	 * @return 统计项key-value对
	 */
	public Map<String, Long> count4OrderDashboard(){
		return this.salesOrderDao.count4OrderDashboard();
	}

	@Override
	protected void initManager() {
	
	}

	@Override
	protected void onDelete(SalesOrder entity) {
		
	}

	@Override
	protected void onSave(SalesOrder entity) {
		
	}

	@Override
	public long countCreatedOrder4Day(Date date) {
		return 0;
	}
	
}

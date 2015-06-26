package com.cartmatic.estore.order.service;

import java.util.Date;
import java.util.Map;

import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * 订单统计接口
 * 
 * @author pengzhirong
 *
 */
public interface OrderDashboardManager extends GenericManager<SalesOrder> {
	
	/**
	 * 后台主页订单统计
	 * @return 统计项key-value对
	 */
	public Map<String, Long> count4OrderDashboard();
	
	/**
	 * 统计一日增加的订单数量
	 * @param date
	 * @return
	 */
	public long countCreatedOrder4Day(Date date);
}

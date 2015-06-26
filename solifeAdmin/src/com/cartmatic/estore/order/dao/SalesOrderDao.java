package com.cartmatic.estore.order.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.vo.OrderFilter;
/**
 * Dao interface for SalesOrder.
 */
public interface SalesOrderDao extends GenericDao<SalesOrder> {
	public String getMaxOrderNo();
	
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId);
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param email 前台会员email
	 * @return
	 */
	public SalesOrder getSalesOrder(String orderNo, String email);
	
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId);
	
	/**
	 * 统计会员订单
	 * @param userId
	 * @param complete 是否只统计已完成的订单
	 * @return
	 */
	public long countCustomerOrder(Integer userId, boolean complete);
	
	public void clearAll();
	
	/**
	 * 后台主页订单统计
	 * @return 统计项key-value对
	 */
	public Map<String, Long> count4OrderDashboard();
	
	/**
	 * 根据订单号列出找出相应的订单
	 * @param orderNos
	 * @return
	 */
	public List<SalesOrder> getSalesOrder(List<String> orderNos);
	

	/**
	 * 获取过期的订单
	 * @param expireDate 超过的天数
	 * @return
	 */
	public List<SalesOrder> findExpiredSalesOrder(Integer expireDate);
	
	/**
	 * 统计指定时间内增加的订单数量
	 * 范围包含endDate
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public long countCreatedCustomer4Time(Date beginDate, Date endDate);
	
	/**
	 * 某用户在某段时间区间内已支付的订单
	 * @param userId
	 * @param paymentStatus
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<SalesOrder> getSalesOrder(Integer userId, Serializable paymentStatus, Date begin, Date end);
	
}
package com.cartmatic.estore.order.dao;

import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for OrderShipment.
 */
public interface OrderShipmentDao extends GenericDao<OrderShipment> {
	
	/**
	 * 查询最新的ordershipment
	 * @param limit 返回的数量
	 * @return 返回最新的orderShipment对象List.
	 */
	public List<OrderSku> getRecentShipments(int limit);
	/**
	 * 获取所有订单的发货项，按shipmentNo排序
	 * @param salesOrderId
	 * @return
	 */
	public List<OrderShipment> getAllShipmentsByOrderId(Integer salesOrderId);
	
	/**
	 * 统计待备货的发货项，不包括已被暂停处理的发货项
	 * @return
	 */
	public Integer countShipments4Picking();
	
	/**
	 * 获取待备货的发货项，不包括已被暂停处理的发货项
	 * @return
	 */
	public List<OrderShipment> getShipments4Picking();
	
	/**
	 *  获取指定订单下的未确认备货的发货项的ID与编码
	 * @param salesOrderId
	 * @param orderShipmentId  不包含在内的发货项ID
	 * @return
	 */
	public Map<Integer, String> getForMoveItemByOrderId(Integer salesOrderId, Integer orderShipmentId);
	
	/**
	 * 返回指定订单下的下一个发货项编码
	 * @param salesOrderId
	 * @return
	 */
	public String getNextShipmentNo(Integer salesOrderId);
	
	/**
	 * 搜索提示功能，返回指定前缀的前N条记录
	 * @param prefix
	 * @param pageSize
	 * @return
	 */
	public List<String> autoCompleteShipmentNo(String prefix, Integer pageSize);
	
	/**
	 * 获取订单，用于智能审核
	 * @param sc
	 * @return
	 */
	public List<OrderShipment> getOrderShipment4RobotReview(SearchCriteria sc);
	
	/**
	 * 是否有类同的发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param shippingAddress
	 * @return
	 */
	public boolean hasSimilarOrderShipment(SalesOrder salesOrder, OrderShipment orderShipment, OrderAddress shippingAddress);
	
}
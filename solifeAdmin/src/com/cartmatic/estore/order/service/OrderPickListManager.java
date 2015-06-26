package com.cartmatic.estore.order.service;

import java.io.Serializable;
import java.util.List;

import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for OrderPickList, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface OrderPickListManager extends GenericManager<OrderPickList> {
	
	/**
	 * 获取用户创建的备货单列表
	 * @param createdBy
	 * @return
	 */
	public List<OrderPickList> getActivePickLists(Integer createdBy);
	
	/**
	 * 获取最近的N条历史备货单
	 * @param fetchSize
	 * @param createdBy
	 * @return
	 */
	public List<OrderPickList> getInActivePickLists(Integer fetchSize, Integer createdBy);
	
	/**
	 * 从备货单中移除发货项，更改发货项与备货单状态（isActive）
	 * @param orderShipmentId
	 */
	public void removeFromPickList(Serializable orderShipmentId);
	
	/**
	 * 设置备货单激活状态，如果备货单中无发货项则删除备货单
	 * @param orderPickListId
	 */
	public void doSetActive(Serializable orderPickListId);
	
	public List<OrderSku> getRecentShipments(int limit);
	
	public void delete(OrderPickList orderPickList);
}

package com.cartmatic.estore.order.dao;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for OrderPickList.
 */
public interface OrderPickListDao extends GenericDao<OrderPickList> {
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
}
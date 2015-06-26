package com.cartmatic.estore.order.dao;

import java.util.List;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for OrderSku.
 */
public interface OrderSkuDao extends GenericDao<OrderSku> {
	
	/**
	 * 获取指定商品的订单SKU
	 * @param productSkuId
	 * @return
	 */
	public List<OrderSku> getOrderSkuAwaitingInventoryByProductSkuId(Integer productSkuId);

}
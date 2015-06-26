package com.cartmatic.estore.order.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderSkuDao;

/**
 * Dao implementation for OrderSku.
*/
public class OrderSkuDaoImpl extends HibernateGenericDaoImpl<OrderSku> implements OrderSkuDao {

	/**
	 * 获取指定商品的订单SKU
	 * @param productSkuId
	 * @return
	 */
	public List<OrderSku> getOrderSkuAwaitingInventoryByProductSkuId(Integer productSkuId){
		String hql = "select sku from OrderSku sku,OrderShipment os where sku.orderShipment.orderShipmentId=os.orderShipmentId and os.status=? and sku.productSku.productSkuId=? and (sku.quantity-sku.allocatedQuantity>0) order by os.createTime";
		return this.findByHql(hql, OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY, productSkuId);
	}
}

package com.cartmatic.estore.order.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.dao.OrderPaymentDao;

/**
 * Dao implementation for OrderPayment.
*/
public class OrderPaymentDaoImpl extends HibernateGenericDaoImpl<OrderPayment> implements OrderPaymentDao {
	
	/**
	 * 获取取后一条订单支付记录
	 * @param salesOrderId
	 * @return
	 */
	public OrderPayment getLatestOrderPayment(Integer salesOrderId){
		String hql = "from OrderPayment op where op.salesOrder.salesOrderId=? order by op.createTime desc, op.orderPaymentId desc";
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance(hql.toString(), new Object[]{salesOrderId}, 1, 1, null);
		List<OrderPayment> list = this.searchByCriteria(sc);
		if(list!=null && list.size()==1)
			return list.get(0);
		return null;
	}
}

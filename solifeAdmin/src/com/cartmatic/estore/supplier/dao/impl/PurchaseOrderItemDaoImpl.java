package com.cartmatic.estore.supplier.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.supplier.dao.PurchaseOrderItemDao;

/**
 * Dao implementation for PurchaseOrderItem.
*/
public class PurchaseOrderItemDaoImpl extends HibernateGenericDaoImpl<PurchaseOrderItem> implements PurchaseOrderItemDao {

	private final static String hql_getAwaitingPoItemBySupplier = "from PurchaseOrderItem p " +
			"where p.status=0 and p.orderSku.productSku.product.supplier.supplierId = ?";
	
	public List<PurchaseOrderItem> getAwaitingPoItemBySupplier(Integer supplierId)
	{
		return this.findByHql(hql_getAwaitingPoItemBySupplier, new Object[]{supplierId});
	}
}

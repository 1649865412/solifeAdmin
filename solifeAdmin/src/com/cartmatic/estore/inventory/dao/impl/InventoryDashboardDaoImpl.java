package com.cartmatic.estore.inventory.dao.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.inventory.dao.InventoryDashboardDao;

@SuppressWarnings("unchecked")
public class InventoryDashboardDaoImpl extends HibernateGenericDaoImpl<Inventory> implements InventoryDashboardDao{
	
	public Long getLackStockProductSkuTotal() {
		List<Long> result=getHibernateTemplate().find("select count(i) from Inventory i where (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<0");
		return result.get(0);
	}
	
	
	public Long getLackStockActiveProductSkuTotal() {
		List<Long> result=getHibernateTemplate().find("select count(i) from Inventory i where i.productSku.product.status=1 and (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<0");
		return result.get(0);
	}
	
	public Long getLowStockProductSkuTotal() {
		List<Long> result=getHibernateTemplate().find("select count(i) from Inventory i where (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<i.reorderMinimnm");
		return result.get(0);
	}
	
	
	public Long getLowStockActiveProductSkuTotal() {
		List<Long> result=getHibernateTemplate().find("select count(i) from Inventory i where i.productSku.product.status=1 and (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<i.reorderMinimnm");
		return result.get(0);
	}
	
	
	public Long getAlreadyExpectedRestockDateInventoryTotal() {
		List<Long> result=getHibernateTemplate().find("select count(i) from Inventory i where i.expectedRestockDate<=?",new Object[]{new Date()});
		return result.get(0);
	}
}

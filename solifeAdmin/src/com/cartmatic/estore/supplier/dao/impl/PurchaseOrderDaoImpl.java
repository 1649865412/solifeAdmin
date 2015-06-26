package com.cartmatic.estore.supplier.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.supplier.dao.PurchaseOrderDao;

/**
 * Dao implementation for PurchaseOrder.
*/
public class PurchaseOrderDaoImpl extends HibernateGenericDaoImpl<PurchaseOrder> implements PurchaseOrderDao {

	public String getMaxOrderNo() 
	{
		String orderNoPrefix = ConfigUtil.getInstance().getPoNoPrefix();
		String hql="select po.purchaseOrderNo from PurchaseOrder po where purchaseOrderNo like ? order by po.purchaseOrderId desc".intern();
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance(hql.toString(), new Object[]{orderNoPrefix+"%"}, 1, 1, null);
		List<String> list = this.searchByCriteria(sc);
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
}

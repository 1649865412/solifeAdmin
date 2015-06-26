package com.cartmatic.estore.supplier.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.supplier.dao.SupplierDao;

/**
 * Dao implementation for Supplier.
*/
public class SupplierDaoImpl extends HibernateGenericDaoImpl<Supplier> implements SupplierDao {

	public Supplier getLast()
	{
		String hql="select so from Supplier so order by so.supplierId desc".intern();
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance(hql.toString(), new Object[]{}, 1, 1, null);
		List<Supplier> list = this.searchByCriteria(sc);
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
}

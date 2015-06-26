package com.cartmatic.estore.supplier.dao.impl;

import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.supplier.dao.TbCategoryReferDao;

/**
 * Dao implementation for TbCategoryRefer.
*/
public class TbCategoryReferDaoImpl extends HibernateGenericDaoImpl<TbCategoryRefer> implements TbCategoryReferDao {

	@Override
	public TbCategoryRefer getTbCategoryReferByCategoryId(Integer categoryId) {
		String hql="select c from TbCategoryRefer c where c.category.categoryId=?";
		return (TbCategoryRefer) findUnique(hql, categoryId);
	}

}

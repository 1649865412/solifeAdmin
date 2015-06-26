package com.cartmatic.estore.supplier.dao;

import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for TbCategoryRefer.
 */
public interface TbCategoryReferDao extends GenericDao<TbCategoryRefer> {
	public TbCategoryRefer getTbCategoryReferByCategoryId(Integer categoryId);
}
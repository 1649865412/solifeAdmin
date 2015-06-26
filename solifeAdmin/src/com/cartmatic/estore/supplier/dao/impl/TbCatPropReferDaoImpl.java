package com.cartmatic.estore.supplier.dao.impl;

import com.cartmatic.estore.supplier.dao.TbCatPropReferDao;
import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for TbCatPropRefer.
*/
public class TbCatPropReferDaoImpl extends HibernateGenericDaoImpl<TbCatPropRefer> implements TbCatPropReferDao {

	@Override
	public TbCatPropRefer getTbCatPropReferByAccGroupCode(String code) {
		String hql="select c from TbCatPropRefer c where c.accessoryGroup.groupCode=?";
		return (TbCatPropRefer) findUnique(hql, code);
	}

}

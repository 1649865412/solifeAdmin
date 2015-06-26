package com.cartmatic.estore.supplier.dao;

import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for TbCatPropRefer.
 */
public interface TbCatPropReferDao extends GenericDao<TbCatPropRefer> {
	public TbCatPropRefer getTbCatPropReferByAccGroupCode(String code);
}
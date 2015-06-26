package com.cartmatic.estore.supplier.service.impl;

import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.service.TbCatPropValueReferManager;
import com.cartmatic.estore.supplier.dao.TbCatPropValueReferDao;


/**
 * Manager implementation for TbCatPropValueRefer, responsible for business processing, and communicate between web and persistence layer.
 */
public class TbCatPropValueReferManagerImpl extends GenericManagerImpl<TbCatPropValueRefer> implements TbCatPropValueReferManager {

	private TbCatPropValueReferDao tbCatPropValueReferDao = null;

	/**
	 * @param tbCatPropValueReferDao
	 *            the tbCatPropValueReferDao to set
	 */
	public void setTbCatPropValueReferDao(TbCatPropValueReferDao tbCatPropValueReferDao) {
		this.tbCatPropValueReferDao = tbCatPropValueReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = tbCatPropValueReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TbCatPropValueRefer entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TbCatPropValueRefer entity) {

	}

	@Override
	public TbCatPropValueRefer getTbCatPropValueReferByCatePropValueId(Long tbCatPropValueId) {
		return tbCatPropValueReferDao.findUniqueByProperty("tbCatPropValueId", tbCatPropValueId);
	}

}

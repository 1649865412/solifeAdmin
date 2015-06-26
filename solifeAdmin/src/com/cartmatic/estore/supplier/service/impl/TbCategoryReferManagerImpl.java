package com.cartmatic.estore.supplier.service.impl;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.service.TbCategoryReferManager;
import com.cartmatic.estore.supplier.dao.TbCategoryReferDao;
import com.cartmatic.extend.supplier.util.TaoBaoApiHelper;


/**
 * Manager implementation for TbCategoryRefer, responsible for business processing, and communicate between web and persistence layer.
 */
public class TbCategoryReferManagerImpl extends GenericManagerImpl<TbCategoryRefer> implements TbCategoryReferManager {

	private TbCategoryReferDao tbCategoryReferDao = null;

	/**
	 * @param tbCategoryReferDao
	 *            the tbCategoryReferDao to set
	 */
	public void setTbCategoryReferDao(TbCategoryReferDao tbCategoryReferDao) {
		this.tbCategoryReferDao = tbCategoryReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = tbCategoryReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TbCategoryRefer entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TbCategoryRefer entity) {

	}

	@Override
	public TbCategoryRefer addTbCategoryRefer(Long tbCategoryId) {
		TbCategoryRefer tbCategoryRefer=getTbCategoryReferByTbCId(tbCategoryId);
		if(tbCategoryRefer==null){
			tbCategoryRefer=new TbCategoryRefer();
			tbCategoryRefer.setTbCategoryId(tbCategoryId);
			//查name
			String catName=getTaobaoName(tbCategoryId);
			tbCategoryRefer.setTbCategoryName(catName);
			save(tbCategoryRefer);
		}else{
			String catName=tbCategoryRefer.getTbCategoryName();
			if(StringUtils.isBlank(catName)){
				//查name
				catName=getTaobaoName(tbCategoryId);
				tbCategoryRefer.setTbCategoryName(catName);
				save(tbCategoryRefer);
			}
		}
		return tbCategoryRefer;
	}

	private String getTaobaoName(Long tbCategoryId) {
		String name=TaoBaoApiHelper.getInstance().getCatFullName(tbCategoryId);
		return name;
	}

	@Override
	public TbCategoryRefer getTbCategoryReferByTbCId(Long tbCategoryId) {
		return tbCategoryReferDao.findUniqueByProperty("tbCategoryId", tbCategoryId);
	}
	
	
	@Override
	public TbCategoryRefer getTbCategoryReferByCategoryId(Integer categoryId) {
		return tbCategoryReferDao.getTbCategoryReferByCategoryId(categoryId);
	}

}

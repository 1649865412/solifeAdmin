package com.cartmatic.estore.supplier.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.service.TbCatPropReferManager;
import com.cartmatic.estore.supplier.service.TbCatPropValueReferManager;
import com.cartmatic.estore.supplier.dao.TbCatPropReferDao;
import com.cartmatic.extend.supplier.util.TaoBaoApiHelper;


/**
 * Manager implementation for TbCatPropRefer, responsible for business processing, and communicate between web and persistence layer.
 */
public class TbCatPropReferManagerImpl extends GenericManagerImpl<TbCatPropRefer> implements TbCatPropReferManager {

	private TbCatPropReferDao tbCatPropReferDao = null;
	
	private TbCatPropValueReferManager tbCatPropValueReferManager;

	public void setTbCatPropValueReferManager(
			TbCatPropValueReferManager tbCatPropValueReferManager) {
		this.tbCatPropValueReferManager = tbCatPropValueReferManager;
	}

	/**
	 * @param tbCatPropReferDao
	 *            the tbCatPropReferDao to set
	 */
	public void setTbCatPropReferDao(TbCatPropReferDao tbCatPropReferDao) {
		this.tbCatPropReferDao = tbCatPropReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = tbCatPropReferDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TbCatPropRefer entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TbCatPropRefer entity) {

	}

	@Override
	public void addTbCatPropRefer(Long tb_cid,Long tbCatPropId, Long tbCatPropValueId) {
		TbCatPropRefer tbCatPropRefer=getTbCatPropReferByTbCatPropId(tbCatPropId);
		if(tbCatPropRefer==null){
			tbCatPropRefer=new TbCatPropRefer();
			tbCatPropRefer.setTbCatPropId(tbCatPropId);
			String tbCatPropName=getTbCatPropName(tb_cid,tbCatPropId);
			tbCatPropRefer.setTbCatPropName(tbCatPropName);
			save(tbCatPropRefer);
			//检查name
		}else{
			//检查name
			String tbCatPropName=tbCatPropRefer.getTbCatPropName();
			if(StringUtils.isBlank(tbCatPropName)){
				tbCatPropName=getTbCatPropName(tb_cid,tbCatPropId);
				tbCatPropRefer.setTbCatPropName(tbCatPropName);
				save(tbCatPropRefer);
			}
		}
		TbCatPropValueRefer tbCatPropValueRefer=tbCatPropValueReferManager.getTbCatPropValueReferByCatePropValueId(tbCatPropValueId);
		if(tbCatPropValueRefer==null){
			tbCatPropValueRefer=new TbCatPropValueRefer();
			tbCatPropValueRefer.setTbCatPropId(tbCatPropId);
			tbCatPropValueRefer.setTbCatPropRefer(tbCatPropRefer);
			tbCatPropValueRefer.setTbCatPropValueId(tbCatPropValueId);
			//检查name
			String tbCatPropValueName=getTCatPropValueName(tb_cid,tbCatPropId,tbCatPropValueId);
			tbCatPropValueRefer.setTbCatPropValueName(tbCatPropValueName);
			tbCatPropValueReferManager.save(tbCatPropValueRefer);
		}else{
			//检查name
			String tbCatPropValueName=tbCatPropValueRefer.getTbCatPropValueName();
			if(StringUtils.isBlank(tbCatPropValueName)){
				tbCatPropValueName=getTCatPropValueName(tb_cid,tbCatPropId,tbCatPropValueId);
				tbCatPropValueRefer.setTbCatPropValueName(tbCatPropValueName);
				tbCatPropValueReferManager.save(tbCatPropValueRefer);
			}
		}
	}

	/**
	 * 检索淘宝销售属性值名
	 * @param tbCatPropId
	 * @return
	 */
	private String getTCatPropValueName(Long tbCategoryId,Long tbCatPropId,Long tbCatPropValueId) {
		String name=TaoBaoApiHelper.getInstance().getPropValueName(tbCategoryId, tbCatPropId, tbCatPropValueId);
		return name;
	}

	/**
	 * 检索淘宝销售属性名
	 * @param tbCatPropId
	 * @return
	 */
	private String getTbCatPropName(Long tbCategoryId,Long tbCatPropId) {
		String name=TaoBaoApiHelper.getInstance().getItemPropName(tbCategoryId, tbCatPropId);
		return name;
	}
	

	@Override
	public TbCatPropRefer getTbCatPropReferByTbCatPropId(Long tbCatPropId) {
		return tbCatPropReferDao.findUniqueByProperty("tbCatPropId", tbCatPropId);
	}

	@Override
	public void addTbCatPropRefers(Long tb_cid,List<TbCatPropValueRefer> tbCatPropValueReferList) {
		for (TbCatPropValueRefer tbCatPropValueRefer : tbCatPropValueReferList) {
			addTbCatPropRefer(tb_cid, tbCatPropValueRefer.getTbCatPropId(), tbCatPropValueRefer.getTbCatPropValueId());
		}
	}

	@Override
	public TbCatPropRefer getTbCatPropReferByAccGroupCode(String code) {
		return tbCatPropReferDao.getTbCatPropReferByAccGroupCode(code);
//		return tbCatPropReferDao.findUniqueByProperty("accessoryGroup.groupCode", code);
	}

}

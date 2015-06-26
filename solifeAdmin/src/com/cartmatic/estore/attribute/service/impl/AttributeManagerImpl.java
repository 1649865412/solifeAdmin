package com.cartmatic.estore.attribute.service.impl;

import java.util.List;

import com.cartmatic.estore.attribute.dao.AttributeDao;
import com.cartmatic.estore.attribute.service.AttributeManager;
import com.cartmatic.estore.attribute.service.AttributeValueManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for Attribute, responsible for business processing, and communicate between web and persistence layer.
 */
public class AttributeManagerImpl extends GenericManagerImpl<Attribute> implements AttributeManager {

	private AttributeDao attributeDao = null;

	

	/**
	 * @param attributeDao
	 *            the attributeDao to set
	 */
	public void setAttributeDao(AttributeDao attributeDao) {
		this.attributeDao = attributeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = attributeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(Attribute entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(Attribute entity) {

	}

	public Attribute getAttribute(String attributeCode) {
		// TODO Auto-generated method stub
		Attribute attr = attributeDao.getAttribute(attributeCode);
		return attr;
	}

	public List<Attribute> getAttributes(Short attributeType) {
		// TODO Auto-generated method stub
		List list = attributeDao.getAttributes(attributeType);
		return list;
	}

	public Attribute saveOrUpdateAttribute(Attribute attribute) {
		// TODO Auto-generated method stub
		if(attribute == null){
			if(logger.isDebugEnabled())
			  logger.debug("the attribute is null");
		}
		else{
			attributeDao.saveOrUpAttribute(attribute);
		}
		return attribute;
	}

	public List<Attribute> searchByCriteria(SearchCriteria sc) {
		// TODO Auto-generated method stub
		if(sc != null)
			return attributeDao.getAttributeForPaging(sc);
		else
			return null;
	}

	public int bulkUpdateAttrValue(String fromValue, String toValue, Attribute attribute)
	{
		return attributeDao.bulkUpdateAttrValue(fromValue, toValue, attribute);
	}

}
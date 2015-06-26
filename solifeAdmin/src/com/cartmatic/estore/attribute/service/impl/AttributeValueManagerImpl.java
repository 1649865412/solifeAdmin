package com.cartmatic.estore.attribute.service.impl;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.attribute.dao.CategoryAttrValueDao;
import com.cartmatic.estore.attribute.dao.ContentAttrValueDao;
import com.cartmatic.estore.attribute.dao.CustomerAttrValueDao;
import com.cartmatic.estore.attribute.dao.ProductAttrValueDao;
import com.cartmatic.estore.attribute.service.AttributeValueManager;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for CategoryAttrValue, responsible for business processing, and communicate between web and persistence layer.
 */
public class AttributeValueManagerImpl extends GenericManagerImpl<AttributeValue> implements AttributeValueManager {

	private CategoryAttrValueDao categoryAttrValueDao = null;

	private ContentAttrValueDao contentAttrValueDao = null;
	
	private CustomerAttrValueDao customerAttrValueDao = null;
	
	private ProductAttrValueDao productAttrValueDao = null;
	


	public void setCategoryAttrValueDao(CategoryAttrValueDao categoryAttrValueDao) {
		this.categoryAttrValueDao = categoryAttrValueDao;
	}

	public void setContentAttrValueDao(ContentAttrValueDao contentAttrValueDao) {
		this.contentAttrValueDao = contentAttrValueDao;
	}

	public void setCustomerAttrValueDao(CustomerAttrValueDao customerAttrValueDao) {
		this.customerAttrValueDao = customerAttrValueDao;
	}

	public void setProductAttrValueDao(ProductAttrValueDao productAttrValueDao) {
		this.productAttrValueDao = productAttrValueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(AttributeValue entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(AttributeValue entity) {

	}

	public AttributeValue saveOrUpdate(AttributeValue attributeValue) {
		// TODO Auto-generated method stub
		if(attributeValue instanceof CategoryAttrValue){
			categoryAttrValueDao.saveOrUpdate((CategoryAttrValue)attributeValue);
		}
		else if(attributeValue instanceof ContentAttrValue){
			contentAttrValueDao.saveOrUpdate((ContentAttrValue)attributeValue);
		}
		else if(attributeValue instanceof CustomerAttrValue){
			customerAttrValueDao.saveOrUpdate((CustomerAttrValue)attributeValue);
		}
		else if(attributeValue instanceof ProductAttrValue){
			productAttrValueDao.saveOrUpdate((ProductAttrValue)attributeValue);
		}

		return attributeValue;
	}

	public BaseAttributeValue getAttributeValueById(Integer id, Integer type) {
		// TODO Auto-generated method stub
		BaseAttributeValue returnValue = null;
		if(type.intValue() == Constants.MODULE_CATEGORY.intValue()){
			returnValue = categoryAttrValueDao.getById(id);
		}
		else if(type.intValue() == Constants.MODULE_CONTENT.intValue()){
			returnValue = contentAttrValueDao.getById(id);
		}
		else if(type.intValue() == Constants.MODULE_CUSTOMER.intValue()){
			returnValue = customerAttrValueDao.getById(id);
		}
		else if(type.intValue() == Constants.MODULE_PRODUCT.intValue()){
		    returnValue = productAttrValueDao.getById(id);	
		}
		return returnValue;
	}
	
	public void deleteAttributeValues(Integer attributeId, Integer type){
		if(type.intValue() == Constants.MODULE_CATEGORY.intValue()){
			categoryAttrValueDao.deleteValues(attributeId);
		}
		else if(type.intValue() == Constants.MODULE_CONTENT.intValue()){
			contentAttrValueDao.deleteValues(attributeId);
		}
		else if(type.intValue() == Constants.MODULE_CUSTOMER.intValue()){
			customerAttrValueDao.deleteValues(attributeId);
		}
		else if(type.intValue() == Constants.MODULE_PRODUCT.intValue()){
		    productAttrValueDao.deleteValues(attributeId);	
		}
	}

	public ProductAttrValue getProductAttrValue(Integer pId, Integer aId) {
		// TODO Auto-generated method stub
		ProductAttrValue pav =  productAttrValueDao.getProductAttrValue(pId, aId);
		return pav;
	}

	@Override
	public CategoryAttrValue getCategoryAttrValue(Integer categoryId, Integer attrId) {
		return categoryAttrValueDao.getCategoryAttrValue(categoryId, attrId);
	}

	@Override
	public void deleteValuesByProductId(Integer productId) {
		productAttrValueDao.deleteValuesByProductId(productId);
	}

}

package com.cartmatic.estore.attribute.service;

import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for CategoryAttrValue, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AttributeValueManager extends GenericManager<AttributeValue> {

	public AttributeValue saveOrUpdate(AttributeValue attributeValue);
	
	public BaseAttributeValue getAttributeValueById(Integer id, Integer type);
	
	public void deleteAttributeValues(Integer attributeId, Integer type);
	
	/**
	 * 通过产品ID，自定义属性ID放回某个产品某个自定义属性的值
	 * @param pId
	 * @param aId
	 * @return
	 */
	public ProductAttrValue getProductAttrValue(Integer pId, Integer aId);
	public CategoryAttrValue getCategoryAttrValue(Integer categoryId,Integer attrId);
	public void deleteValuesByProductId(Integer productId);
}

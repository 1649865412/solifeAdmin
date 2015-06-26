package com.cartmatic.estore.attribute.dao;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for CategoryAttrValue.
 */
public interface CategoryAttrValueDao extends GenericDao<CategoryAttrValue> {

	public CategoryAttrValue saveOrUpdate(CategoryAttrValue categoryAttrValue);
	
	public void deleteCategoryAttrValue(CategoryAttrValue categoryAttrValue);
	
	public List<CategoryAttrValue> getCategoryAttrValueByCategoryId(Integer categoryId);
	
	public List<CategoryAttrValue> getCategoryAttrValueByAttributeId(Integer attributeId);
	
	public CategoryAttrValue getCategoryAttrValue(Integer categoryId,Integer attrId);

	public void deleteValues(Integer attributeId);
}
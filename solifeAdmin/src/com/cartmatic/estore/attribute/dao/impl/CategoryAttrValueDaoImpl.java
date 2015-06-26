package com.cartmatic.estore.attribute.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cartmatic.estore.attribute.dao.CategoryAttrValueDao;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AttributeValue.
*/
public class CategoryAttrValueDaoImpl extends HibernateGenericDaoImpl<CategoryAttrValue> implements CategoryAttrValueDao {

	public void deleteCategoryAttrValue(CategoryAttrValue categoryAttrValue) {
		// TODO Auto-generated method stub
		super.delete(categoryAttrValue);
	}

	public List<CategoryAttrValue> getCategoryAttrValueByAttributeId(
			Integer attributeId) {
		// TODO Auto-generated method stub
		String hql = "from CategoryAttrValue c where c.attribute.attributeId=?";
		List list = findByHql(hql, attributeId);
		return list;
	}

	public List<CategoryAttrValue> getCategoryAttrValueByCategoryId(
			Integer categoryId) {
		// TODO Auto-generated method stub
		String hql = "from CategoryAttrValue c where c.category.categoryId=?";
		List list = findByHql(hql, categoryId);
		return list;
	}

	public CategoryAttrValue saveOrUpdate(CategoryAttrValue categoryAttrValue) {
		// TODO Auto-generated method stub
		super.save(categoryAttrValue);
		return categoryAttrValue;
	}
	
	public void deleteValues(Integer attributeId) {
		// TODO Auto-generated method stub
		List values = getCategoryAttrValueByAttributeId(attributeId);
		HibernateTemplate ht = getHibernateTemplate();
		ht.deleteAll(values);
	}

	@Override
	public CategoryAttrValue getCategoryAttrValue(Integer categoryId, Integer attrId) {
		String hql = "from CategoryAttrValue c where c.category.categoryId=? and c.attribute.attributeId=?";
		CategoryAttrValue cav = (CategoryAttrValue) findUnique(hql, categoryId,attrId);
		return cav;
	}
}

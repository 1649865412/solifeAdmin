package com.cartmatic.estore.attribute.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cartmatic.estore.attribute.dao.ContentAttrValueDao;
import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AttributeValue.
*/
public class ContentAttrValueDaoImpl extends HibernateGenericDaoImpl<ContentAttrValue> implements ContentAttrValueDao {

	public void deleteContentAttrValue(ContentAttrValue contentAttrValue) {
		// TODO Auto-generated method stub
		super.delete(contentAttrValue);
	}

	public List<ContentAttrValue> getContentAttrValueByAttributeId(
			Integer attributeId) {
		// TODO Auto-generated method stub
		String hql = "from ContentAttrValue c where c.attribute.attributeId=?";
		List list = findByHql(hql, attributeId);
		return list;
	}

	public List<ContentAttrValue> getContentAttrValueByContentId(
			Integer contentId) {
		// TODO Auto-generated method stub
		String hql = "from ContentAttrValue c where c.content.contentId=?";
		List list = findByHql(hql, contentId);
		return list;
	}

	public ContentAttrValue saveOrUpdate(ContentAttrValue contentAttrValue) {
		// TODO Auto-generated method stub
		super.save(contentAttrValue);
		return contentAttrValue;
	}
	
	public void deleteValues(Integer attributeId) {
		// TODO Auto-generated method stub
		List values = getContentAttrValueByAttributeId(attributeId);
		HibernateTemplate ht = getHibernateTemplate();
		ht.deleteAll(values);
	}

}

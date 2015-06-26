package com.cartmatic.estore.attribute.dao;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ContentAttrValue.
 */
public interface ContentAttrValueDao extends GenericDao<ContentAttrValue> {

    public ContentAttrValue saveOrUpdate(ContentAttrValue contentAttrValue);
	
	public void deleteContentAttrValue(ContentAttrValue contentAttrValue);
	
	public List<ContentAttrValue> getContentAttrValueByContentId(Integer contentId);
	
	public List<ContentAttrValue> getContentAttrValueByAttributeId(Integer attributeId);
	
	public void deleteValues(Integer attributeId);
}
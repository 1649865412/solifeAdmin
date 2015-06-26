package com.cartmatic.estore.attribute.dao;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for Attribute.
 */
public interface AttributeDao extends GenericDao<Attribute> {

	/**
	 * get the self definition attribute by code
	 * @param attributeCode
	 * @return
	 * 
	 */
	public Attribute getAttribute(String attributeCode);
	
	/**
	 * get all the self definition attribute in a module
	 * @param attributeType
	 *        the module code
	 * @return
	 */
	public List<Attribute> getAttributes(Short attributeType);
	
	/**
	 * save/update a attribute po
	 * @param attribute
	 *        the attribute po you want to save/update
	 * @return
	 *        the saved/updated po
	 */
	public Attribute saveOrUpAttribute(Attribute attribute);
	
	/**
	 * get all attribute po by paged;
	 * @return
	 */
	public List getAttributeForPaging(SearchCriteria sc);
	
	public int bulkUpdateAttrValue(String fromValue, String toValue, Attribute attribute);	
}
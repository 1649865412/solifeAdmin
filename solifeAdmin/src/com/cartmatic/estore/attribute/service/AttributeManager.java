package com.cartmatic.estore.attribute.service;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Attribute, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AttributeManager extends GenericManager<Attribute> {
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
	 * save a attribute po
	 * @param attribute
	 *        the attribute po you want to save
	 * @return
	 *        the saved po
	 */
	public Attribute saveOrUpdateAttribute(Attribute attribute);
	
	public int bulkUpdateAttrValue(String fromValue, String toValue, Attribute attribute);
}

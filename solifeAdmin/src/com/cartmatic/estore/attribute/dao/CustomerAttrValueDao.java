package com.cartmatic.estore.attribute.dao;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for CustomerAttrValue.
 */
public interface CustomerAttrValueDao extends GenericDao<CustomerAttrValue> {

public CustomerAttrValue saveOrUpdate(CustomerAttrValue customerAttrValue);
	
	public void deleteCustomerAttrValue(CustomerAttrValue customerAttrValue);
	
	public List<CustomerAttrValue> getCustomerAttrValueByCustomerId(Integer customerId);
	
	public List<CustomerAttrValue> getCustomerAttrValueByAttributeId(Integer attributeId);
	
	public void deleteValues(Integer attributeId);
}
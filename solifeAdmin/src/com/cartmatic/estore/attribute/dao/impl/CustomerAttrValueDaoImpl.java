package com.cartmatic.estore.attribute.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cartmatic.estore.attribute.dao.CustomerAttrValueDao;
import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AttributeValue.
*/
public class CustomerAttrValueDaoImpl extends HibernateGenericDaoImpl<CustomerAttrValue> implements CustomerAttrValueDao {

	public void deleteCustomerAttrValue(CustomerAttrValue customerAttrValue) {
		// TODO Auto-generated method stub
		super.delete(customerAttrValue);
	}

	public List<CustomerAttrValue> getCustomerAttrValueByAttributeId(
			Integer attributeId) {
		// TODO Auto-generated method stub
		String hql = "from CustomerAttrValue c where c.attribute.attributeId=?";
		List list = findByHql(hql, attributeId);
		return list;
	}

	public List<CustomerAttrValue> getCustomerAttrValueByCustomerId(
			Integer customerId) {
		// TODO Auto-generated method stub
		String hql = "from CustomerAttrValue c where c.customer.customerId=?";
		List list = findByHql(hql, customerId);
		return list;
	}

	public CustomerAttrValue saveOrUpdate(CustomerAttrValue customerAttrValue) {
		// TODO Auto-generated method stub
		super.save(customerAttrValue);
		return customerAttrValue;
	}
	
	public void deleteValues(Integer attributeId) {
		// TODO Auto-generated method stub
		List values = getCustomerAttrValueByAttributeId(attributeId);
		HibernateTemplate ht = getHibernateTemplate();
		ht.deleteAll(values);
	}

}

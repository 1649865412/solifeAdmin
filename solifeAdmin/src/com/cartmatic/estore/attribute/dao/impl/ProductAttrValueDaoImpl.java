package com.cartmatic.estore.attribute.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cartmatic.estore.attribute.dao.ProductAttrValueDao;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for AttributeValue.
*/
public class ProductAttrValueDaoImpl extends HibernateGenericDaoImpl<ProductAttrValue> implements ProductAttrValueDao {

	public void deleteProductAttrValue(ProductAttrValue productAttrValue) {
		// TODO Auto-generated method stub
		super.delete(productAttrValue);
	}

	public List<ProductAttrValue> getProductAttrValueByAttributeId(
			Integer attributeId) {
		// TODO Auto-generated method stub
		String hql = "from ProductAttrValue c where c.attribute.attributeId=?";
		List list = findByHql(hql, attributeId);
		return list;
	}

	public List<ProductAttrValue> getProductAttrValueByProductId(
			Integer productId) {
		// TODO Auto-generated method stub
		String hql = "from ProductAttrValue c where c.product.productId=?";
		List list = findByHql(hql, productId);
		return list;
	}
	
	public ProductAttrValue getProductAttrValue(Integer pId, Integer aId){
		String hql = "from ProductAttrValue c where c.product.productId=? and c.attribute.attributeId=?";
		ProductAttrValue pav = (ProductAttrValue) findUnique(hql, pId,aId);
		return pav;
	}

	public ProductAttrValue saveOrUpdate(ProductAttrValue productAttrValue) {
		// TODO Auto-generated method stub
		super.save(productAttrValue);
		return productAttrValue;
	}

	public void deleteValues(Integer attributeId) {
		// TODO Auto-generated method stub
		List values = getProductAttrValueByAttributeId(attributeId);
		HibernateTemplate ht = getHibernateTemplate();
		ht.deleteAll(values);
	}

	@Override
	public void deleteValuesByProductId(Integer productId) {
		Query activeQuery =getSession().createQuery("delete from ProductAttrValue pav where pav.product.productId=?");
		activeQuery.setInteger(0, productId);
		activeQuery.executeUpdate();
	}

}

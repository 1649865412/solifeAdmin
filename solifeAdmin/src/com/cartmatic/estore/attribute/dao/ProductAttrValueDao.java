package com.cartmatic.estore.attribute.dao;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductAttrValue.
 */
public interface ProductAttrValueDao extends GenericDao<ProductAttrValue> {

    public ProductAttrValue saveOrUpdate(ProductAttrValue productAttrValue);
	
	public void deleteProductAttrValue(ProductAttrValue productAttrValue);
	
	public List<ProductAttrValue> getProductAttrValueByProductId(Integer productId);
	
	public List<ProductAttrValue> getProductAttrValueByAttributeId(Integer attributeId);
	
	public void deleteValues(Integer attributeId);
	
	public ProductAttrValue getProductAttrValue(Integer pId, Integer aId);
	
	public void deleteValuesByProductId(Integer productId);
}
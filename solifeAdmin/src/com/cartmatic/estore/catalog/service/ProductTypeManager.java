package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductType, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductTypeManager extends GenericManager<ProductType> {
	/**
	 * 获取所有的激活产品类型
	 * @return
	 */
	public List<ProductType> findActiveProductTypes();

	
	/**
	 * 根据产品名称查找相应的产品类型
	 * @param productTypeName
	 * @return
	 */
	public ProductType getProductTypeByName(String productTypeName);
	

	
	/**
	 * 获取该产品类型没有关联的属性
	 * @param ProductTypeId
	 * @return
	 */ 
	public List<Attribute> findAttributesExcludeRefProductType(Integer ProductTypeId);
	
	/**
	 * 获取用激活SkuOption的产品类型
	 * @return
	 */
	public List<ProductType> findActiveSkuOptionsProductType();
	
	
	/**
	 * 保存产品类型
	 * @param productType
	 * @param attGroupIds
	 * @param attGroupNames
	 * @param attGroupItems
	 * @param skuOptionIds
	 * @param rateItemNames
	 * @param rateItemIds
	 */
	public void saveProductTypeAction(ProductType productType,String attGroupIds[],String attGroupNames[],String attGroupItems[],String skuOptionIds[],String rateItemNames[],String rateItemIds[]);
	
}

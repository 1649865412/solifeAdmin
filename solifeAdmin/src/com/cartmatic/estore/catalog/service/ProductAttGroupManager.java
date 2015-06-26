package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductAttGroup, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductAttGroupManager extends GenericManager<ProductAttGroup> {
	/**
	 * 根据产品类型获取相应的属性组(按属性组的sortOrder排序)
	 * @param productTypeId
	 * @return
	 */
	public List<ProductAttGroup> findProductAttGroupsByProductType(Integer productTypeId);
	
	/**
	 * TODO 应该没有用上，考虑删除
	 * 获取本属性组其他的产品自定义属性
	 * @param attGroupId
	 * @return
	 */
	/*public List<Attribute> findAttributesExcludeRefGroup(Integer attGroupId);*/
	
	
}

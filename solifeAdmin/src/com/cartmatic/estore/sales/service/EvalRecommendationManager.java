package com.cartmatic.estore.sales.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.GenericManager;


public interface EvalRecommendationManager extends GenericManager<Product>
{
	// 手动+自动评估 根据[推荐类型Id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeNameByCategoryId(final Store store,
			final String recommendedTypeName, final Integer sourceId,
			final int firstResult, final int maxResults);
			
	public List<Product> getProductsByRecommendedTypeNameByProductId(final Store store,
			final String recommendedTypeName, final Integer sourceId, 
			final int firstResult, final int maxResults);
	// 手动+自动评估 根据[推荐类型Id][源Id的list]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeNameBySourceIdList(final Store store,
			final String recommendedTypeName, final List<Integer> sourceIds,
			final int firstResult, final int maxResults);
}

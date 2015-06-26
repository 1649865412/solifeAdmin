package com.cartmatic.estore.sales.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.GenericManager;


public interface AutoEvalRecommendationManager extends GenericManager<Product>
{
	// 自动评估 根据[推荐规则code][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRuleCodeBySourceId(final Store store,
			final Integer ruleCode, final Integer sourceId,
			final int firstResult, final int maxResults);
	
	// 自动评估 根据[推荐规则code][源IdList]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRuleCodeBySourceIdList(final Store store,
			final Integer ruleCode, final List<Integer> sourceIds,
			final int firstResult, final int maxResults);

	// 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：购买数量排序
	public List<Product> getBuyCountProductsByCategoryId(final Integer storeId,final Integer categoryId,
			final int firstResult, final int maxResults);
	public List<Product> getFavoriteCountProductsByCategoryId(final Integer storeId,final Integer categoryId, 
			final int firstResult, final int maxResults);
	// 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：新产品，发布时间排序
	public List<Product> getNewProductsByCategoryId(final Integer categoryId,
			final int firstResult, final int maxResults);

	// 自动评估 根据[源Id=productId]获得特定范围(限定主目录)的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：相似价格(默认SKU)
	public List<Product> getSimilarPriceProductsByProductId(
			final Integer categoryId,final Integer productId, final int firstResult, final int maxResults);

	// 自动评估 根据[源Id=productId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：相同品牌
	public List<Product> getSameBrandProductsByProductId(final Integer categoryId,final Integer productId,
			final int firstResult, final int maxResults);
	
	// 自动评估 根据[源Id=productId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：AlsoBuy
	public List<Product> getAlsoBuyProductsBySourceId(final Integer categoryId,final Integer sourceId,
			final int firstResult, final int maxResults);
}

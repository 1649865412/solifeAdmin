
package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.dao.GenericDao;

public interface AutoEvalRecommendationDao extends GenericDao<Product> {

	/**
	 * 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：购买数量排序
	 * @param storeId 商店 (主要是指定哪个商店的统计)
	 * @param categoryId 商品分类Id
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getBuyCountProductsByCategoryId(final Integer storeId,final Integer categoryId,final int firstResult, final int maxResults);

	/**
	 * 收藏的count来排序
	 * @param storeId
	 * @param categoryId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getFavoriteCountProductsByCategoryId(final Integer storeId,Integer categoryId, int firstResult, int maxResults);
	/**
	 * 自动评估 根据[源Id=categoryId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：新产品，发布时间排序
	 * @param sourceId 目录Id
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getNewProductsByCategoryId(final Integer categoryId, final int firstResult, final int maxResults);

	/**
	 * 自动评估 根据[源Id=productId]获得特定范围(限定主目录)的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：相似价格(默认SKU)
	 * @param categoryId catalog默认目录Id
	 * @param productId 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getSimilarPriceProductsByProductId(final Integer categoryId,final Integer productId, final int firstResult, final int maxResults);

	/**
	 * 自动评估 根据[源Id=productId]获得特定范围的推荐产品(不能重复,而且必须是激活的),采用的推荐规则是：相同品牌
	 * @param categoryId catalog默认目录Id
	 * @param prdouctId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getSameBrandProductsByProductId(final Integer categoryId,final Integer prdouctId,final int firstResult, final int maxResults);
	
	// 自动评估 根据[源Id=productId]获得特定范围的推荐,基于productCode来查出相似productCode的产品.
	public List<Product> getSimilarCodeProductsBySourceId(final Integer sourceId,
			final int firstResult, final int maxResults);
}
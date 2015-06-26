
package com.cartmatic.estore.sales.service;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.GenericManager;

public interface RecommendedProductManager extends
		GenericManager<RecommendedProduct> {
	// 根据推荐类型和源商品或目录Id来获得其推荐商品列表
	public List<RecommendedProduct> getRecommendedProductsByRecommendedTypeIdBySourceId(final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults);


	// 根据推荐类型、源商品或目录Id和推荐商品Id获得该推荐商品
	public RecommendedProduct getRecommendedProduct(Integer recommendedTypeId,
			Integer sourceId, Integer productId);

	// 保存相似商品，要把关联的相似商品也要保存
	public void saveSimilarProducts(RecommendedProduct recommendedProduct);

	// 删除相似商品，要把关联的相似商品也要删除
	public void removeSimilarProducts(RecommendedProduct recommendedProduct);

	//手动评估  根据[推荐类型name][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
/*	public List<Product> getProductsByRecommendedTypeNameBySourceId(
			final String recommendedTypeName, final Integer sourceId,
			final int firstResult, final int maxResults) ;
	//手动评估  根据[推荐类型name][源IdList]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeNameBySourceIdList(
			final String recommendedTypeName, final List<Integer> sourceIds,
			final int firstResult, final int maxResults);
	//手动评估  根据[推荐类型id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeIdBySourceId(
			final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults);*/
	//根据productId移除关联推荐商品实体
	public void removeRecommendedProductsByProductId(final Integer productId);
	//获得默认过期时间
	public Date getDefaultRecommendedProductExpireTime();
	//delete方法
	public void delete(RecommendedProduct recommendedProduct);
	//根据content的status、pulishTime、expireTime设置state，主要方便显示
	public void setState(RecommendedProduct recommendedProduct);
	
	/**
	 * 
	 * @param recommendedType 推荐类型
	 * @param store 商店
	 * @param sourceId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getProductsByRecommendedTypeName(final RecommendedType recommendedType,final Store store, final Integer sourceId,
			final int firstResult, final int maxResults);
	
	/**
	 * 
	 * @param recommendedType 推荐类型
	 * @param store 商店
	 * @param sourceIds
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List<Product> getProductsByRecommendedTypeName(final RecommendedType recommendedType,final Store store, final List<Integer> sourceIds,
			final int firstResult, final int maxResults);
	
}

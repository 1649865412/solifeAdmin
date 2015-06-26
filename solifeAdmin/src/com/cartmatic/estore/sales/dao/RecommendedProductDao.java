
package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.dao.GenericDao;


public interface RecommendedProductDao extends GenericDao<RecommendedProduct> {
	// 根据推荐类型和源商品或目录Id来获得其推荐商品列表
	public List<RecommendedProduct> getRecommendedProductsByRecommendedTypeIdBySourceId(final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults);
	// 根据推荐类型、源商品或目录Id和推荐商品Id获得该推荐商品
	public RecommendedProduct getRecommendedProduct(Integer recommendedTypeId,
			Integer sourceId, Integer productId);
	
	//手动评估  根据[推荐类型name][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	/*public List<Product> getProductsByRecommendedTypeNameBySourceId(
			final String recommendedTypeName, final Integer sourceId,
			final int firstResult, final int maxResults) ;
	//手动评估  根据[推荐类型name][源IdList]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeNameBySourceIdList(
			final String recommendedTypeName, final List<Integer> sourceIds,
			final int firstResult, final int maxResults);*/
			
	//手动评估  根据[推荐类型id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	/*public List<Product> getProductsByRecommendedTypeIdBySourceId(
			final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults);*/
	//根据productId移除关联推荐商品实体
	public void removeRecommendedProductsByProductId(final Integer productId); 
	
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

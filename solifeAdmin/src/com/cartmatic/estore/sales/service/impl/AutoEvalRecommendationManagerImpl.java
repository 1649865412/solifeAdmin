
package com.cartmatic.estore.sales.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.AutoEvalRecommendationDao;
import com.cartmatic.estore.sales.service.AlsoBuyManager;
import com.cartmatic.estore.sales.service.AutoEvalRecommendationManager;


public class AutoEvalRecommendationManagerImpl extends GenericManagerImpl<Product> implements AutoEvalRecommendationManager
{
	private AutoEvalRecommendationDao autoEvalRecommendationDao;
	
	private AlsoBuyManager  alsoBuyManager;
	
	public void setAutoEvalRecommendationDao(
			AutoEvalRecommendationDao autoEvalRecommendationDao) {
		this.autoEvalRecommendationDao = autoEvalRecommendationDao;
	}
	
	public void setAlsoBuyManager(AlsoBuyManager alsoBuyManager) {
		this.alsoBuyManager = alsoBuyManager;
	}

	protected void initManager() {
		dao = autoEvalRecommendationDao;
	}

	
	protected void onDelete(Product entity) {
	}

	protected void onSave(Product entity) {
	}
	
	// 自动评估 根据[推荐规则code][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRuleCodeBySourceId(final Store store,
			final Integer ruleCode, final Integer sourceId,
			final int firstResult, final int maxResults) {
		switch (ruleCode.intValue()) {
			case SalesConstants.RECOMMENDED_RULE_CODE_NONE:
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_BUY_COUNT:
				return this.getBuyCountProductsByCategoryId(store.getStoreId(),sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_FAVORIATE:
				return this.getFavoriteCountProductsByCategoryId(store.getStoreId(),sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_NEW:	
				return this.getNewProductsByCategoryId(sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_ALSO_BUY:	
				return this.getAlsoBuyProductsBySourceId(store.getCatalog().getCategoryId(),sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT:
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRICE:	
				return this.getSimilarPriceProductsByProductId(store.getCatalog().getCategoryId(),sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_SAME_BRAND:	
				return this.getSameBrandProductsByProductId(store.getCatalog().getCategoryId(),sourceId, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_CODE:
				return this.getSimilarCodeProductsBySourceId(sourceId, firstResult, maxResults);
			default:
				return new ArrayList<Product>();
		}
	}

	//TODO 目前只是做了AlsoBuy部分，如果需要可再扩充。
	public List<Product> getProductsByRuleCodeBySourceIdList(final Store store,
			final Integer ruleCode, final List<Integer> sourceIds,
			final int firstResult, final int maxResults){
		switch (ruleCode.intValue()) {
			case SalesConstants.RECOMMENDED_RULE_CODE_NONE:
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_BUY_COUNT:
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_NEW:	
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_ALSO_BUY:	
				return this.getAlsoBuyProductsBySourceIdList(store.getCatalog().getCategoryId(),sourceIds, firstResult, maxResults);
			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT:
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRICE:	
				return new ArrayList<Product>();
			case SalesConstants.RECOMMENDED_RULE_CODE_SAME_BRAND:	
				return new ArrayList<Product>();
			default:
				return new ArrayList<Product>();
		}
	}
	

	public List<Product> getBuyCountProductsByCategoryId(final Integer storeId,final Integer categoryId,
			final int firstResult, final int maxResults){
		return autoEvalRecommendationDao.getBuyCountProductsByCategoryId(storeId,categoryId, firstResult, maxResults);
	}
	
	public List<Product> getFavoriteCountProductsByCategoryId(final Integer storeId,final Integer categoryId, final int firstResult, final int maxResults)
	{
		return autoEvalRecommendationDao.getFavoriteCountProductsByCategoryId(storeId,categoryId, firstResult, maxResults);
	}

	public List<Product> getNewProductsByCategoryId(final Integer categoryId,final int firstResult, final int maxResults){
		return autoEvalRecommendationDao.getNewProductsByCategoryId(categoryId, firstResult, maxResults);
	}

	public List<Product> getSimilarPriceProductsByProductId(
			final Integer categoryId,final Integer productId, final int firstResult, final int maxResults){
		return autoEvalRecommendationDao.getSimilarPriceProductsByProductId(categoryId,productId, firstResult, maxResults);
	}

	public List<Product> getSameBrandProductsByProductId(final Integer categoryId,
			final Integer productId,final int firstResult, final int maxResults){
		return autoEvalRecommendationDao.getSameBrandProductsByProductId(categoryId,productId, firstResult, maxResults);
	}
	public List<Product> getAlsoBuyProductsBySourceId(final Integer categoryId,final Integer sourceId,
			final int firstResult, final int maxResults){
		return alsoBuyManager.getProductsBySourceId(categoryId,sourceId, firstResult, maxResults);
	}
	public List<Product> getAlsoBuyProductsBySourceIdList(final Integer categoryId,final List<Integer> sourceIds,
			final int firstResult, final int maxResults){
		return alsoBuyManager.getProductsBySourceIdList(categoryId,sourceIds, firstResult, maxResults);
	}
	public List<Product> getSimilarCodeProductsBySourceId(final Integer sourceId,
			final int firstResult, final int maxResults){
		return autoEvalRecommendationDao.getSimilarCodeProductsBySourceId(sourceId, firstResult, maxResults);
	}
}

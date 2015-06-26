
package com.cartmatic.estore.sales.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.RecommendedProductDao;
import com.cartmatic.estore.sales.dao.RecommendedTypeDao;
import com.cartmatic.estore.sales.service.RecommendedProductManager;

public class RecommendedProductManagerImpl extends
		GenericManagerImpl<RecommendedProduct> implements
		RecommendedProductManager {

	private RecommendedProductDao	recommendedProductDao	= null;
	private RecommendedTypeDao		recommendedTypeDao		= null;

	public void setRecommendedProductDao(
			RecommendedProductDao recommendedProductDao) {
		this.recommendedProductDao = recommendedProductDao;
	}

	public RecommendedTypeDao getRecommendedTypeDao() {
		return recommendedTypeDao;
	}

	public void setRecommendedTypeDao(RecommendedTypeDao recommendedTypeDao) {
		this.recommendedTypeDao = recommendedTypeDao;
	}

	protected void initManager() {
		dao = recommendedProductDao;
	}

	protected void onDelete(RecommendedProduct entity) {

	}

	protected void onSave(RecommendedProduct entity) {

	}

	public List<RecommendedProduct> getRecommendedProductsByRecommendedTypeIdBySourceId(
			final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults) {
		return recommendedProductDao
				.getRecommendedProductsByRecommendedTypeIdBySourceId(
						recommendedTypeId, sourceId, firstResult, maxResults);
	}

	
	public RecommendedProduct getRecommendedProduct(Integer recommendedTypeId,
			Integer sourceId, Integer productId) {
		return recommendedProductDao.getRecommendedProduct(recommendedTypeId,
				sourceId, productId);
	}

	
	public void removeRecommendedProductsByProductId(final Integer productId){
		recommendedProductDao.removeRecommendedProductsByProductId(productId);
	}

	// 重载save方法
	public void save(RecommendedProduct recommendedProduct) {
		RecommendedType recommendedType = recommendedTypeDao
				.getById(recommendedProduct.getRecommendedTypeId());
		if (!recommendedType.getRuleCode().equals(
				SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT)) {
			recommendedProductDao.save(recommendedProduct);
		} else {
			saveSimilarProducts(recommendedProduct);
		}
	}

	// 重载deleteById方法
	public void deleteById(Integer recommendedProductId) {
		RecommendedProduct recommendedProduct = loadById(recommendedProductId);
		delete(recommendedProduct);
	}

	//增加delete方法
	public void delete(RecommendedProduct recommendedProduct) {
		RecommendedType recommendedType = recommendedTypeDao
				.getById(recommendedProduct.getRecommendedTypeId());
		if (!recommendedType.getRuleCode().equals(
				SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT)) {
			recommendedProductDao.delete(recommendedProduct);
		} else {
			removeSimilarProducts(recommendedProduct);
		}
	}

	public void saveSimilarProducts(RecommendedProduct recommendedProduct) {
		recommendedProductDao.save(recommendedProduct);
		RecommendedProduct similarProduct = recommendedProductDao
				.getRecommendedProduct(recommendedProduct.getRecommendedTypeId(), recommendedProduct
						.getProductId(), recommendedProduct.getSourceId());
		if (similarProduct == null) {
			similarProduct = new RecommendedProduct();
		}
		similarProduct.setSourceId(recommendedProduct.getProductId());
		similarProduct.setProductId(recommendedProduct.getSourceId());
		similarProduct.setExpireTime(recommendedProduct.getExpireTime());
		similarProduct.setStartTime(recommendedProduct.getStartTime());
		similarProduct.setRecommendedTypeId(recommendedProduct
				.getRecommendedTypeId());
		similarProduct.setStatus(recommendedProduct.getStatus());
		similarProduct.setSortOrder(recommendedProduct.getSortOrder());
		recommendedProductDao.save(similarProduct);
	}

	public void removeSimilarProducts(RecommendedProduct recommendedProduct) {
		if (recommendedProduct != null) {
			// 相似产品要同时删除两个相对应的产品，这时查找的sourceId与productId位置相换
			RecommendedProduct similarProduct = recommendedProductDao
					.getRecommendedProduct(recommendedProduct
							.getRecommendedTypeId(), recommendedProduct
							.getProductId(), recommendedProduct.getSourceId());

			if (similarProduct != null) {
				recommendedProductDao.delete(similarProduct);
			}
			recommendedProductDao.delete(recommendedProduct);
		}
	}

	public Date getDefaultRecommendedProductExpireTime() {
		int conf = ConfigUtil.getInstance().getRecommendedProductExpireYears();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, new Integer(conf).intValue());
		return cal.getTime();
	}
	
	public void setState(RecommendedProduct _recommendedProduct){
		RecommendedProduct recommendedProduct = _recommendedProduct; 
		Date now = new Date();
		int i = now.compareTo(recommendedProduct.getStartTime());
		int j;
		if (recommendedProduct.getExpireTime() != null) {
			j = now.compareTo(recommendedProduct.getExpireTime());
		} else {
			j = -1;
		}
		
		if(recommendedProduct.getStatus() == 1 ){
			if ((i > 0) && (j < 0)) {
				recommendedProduct.setState(String.valueOf(recommendedProduct.STATE_DOING));
			} else if ((i < 0) && (j < 0)) {
				recommendedProduct.setState(String.valueOf(recommendedProduct.STATE_FUTURE));
			} else {
				recommendedProduct.setState(String.valueOf(recommendedProduct.STATE_PAST));
			}	
		}else{
			recommendedProduct.setState(String.valueOf(recommendedProduct.STATE_INVALID));
		}
		
		return;
	}

	@Override
	public List<Product> getProductsByRecommendedTypeName(RecommendedType recommendedType, Store store, Integer sourceId, int firstResult,
			int maxResults) {
		return recommendedProductDao.getProductsByRecommendedTypeName(recommendedType, store, sourceId, firstResult, maxResults);
	}

	@Override
	public List<Product> getProductsByRecommendedTypeName(RecommendedType recommendedType, Store store, List<Integer> sourceIds,
			int firstResult, int maxResults) {
		return recommendedProductDao.getProductsByRecommendedTypeName(recommendedType, store, sourceIds, firstResult, maxResults);
	}

}

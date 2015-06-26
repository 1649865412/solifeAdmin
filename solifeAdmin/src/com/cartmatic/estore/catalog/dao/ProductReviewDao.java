package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for ProductReview.
 */
public interface ProductReviewDao extends GenericDao<ProductReview> {
	
	/**
	 * 获得客户所有评论的总数.
	 * @param customerId
	 * @return
	 */
	public int getCountCustomerReviews(Integer storeId,Integer customerId);
	
	
	/**
	 * 产品激活评论数量
	 * @param productId
	 * @return
	 */
	public Integer getCountActiceProductReviews(Integer storeId,Integer productId);
	
	/**
	 * 产品激活评论总得分
	 * @param productId
	 * @return
	 */
	public Long getSumActiceProductReviewRates(Integer storeId,Integer productId);
}
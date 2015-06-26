package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductReview, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductReviewManager extends GenericManager<ProductReview> {
	
	/**
	 * 获取回复列表
	 * @param productReviewId
	 * @return
	 */
	public List<ProductReview> findReplyListByReview(Integer productReviewId);
	
	/**
	 * 删除评论回复
	 * @param reviewId
	 */
	public void deleteReviewReplyById(Integer reviewReplyId);
	
	/**
	 * 保存评论回复
	 * @param reviewReply
	 */
	public void saveReviewReply(ProductReview reviewReply);
	

	/**
	 * 激活多个产品评论
	 * @param ids
	 * @return 激活的产品评论数量
	 */
	public Integer  doActiveAllByIds(String[] ids);
	
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
	//public Integer getCountActiceProductReviews(Integer productId);
	
	/**
	 * 产品激活评论总得分
	 * @param productId
	 * @return
	 */
	//public Long getSumActiceProductReviewRates(Integer productId);
	
	/**
	 * 更新产品评论统计
	 * @param productId
	 */
	public void updateProductReviewStat(Integer productId);
	/**
	 * 更新产品评论统计
	 * @param productId
	 */
	public void updateProductReviewStat(Integer storeId,Integer productId);
}

package com.cartmatic.estore.catalog.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductReviewDao;
import com.cartmatic.estore.catalog.service.ProductReviewManager;
import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for ProductReview, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductReviewManagerImpl extends GenericManagerImpl<ProductReview> implements ProductReviewManager {

    private ProductStatManager productStatManager = null;
	private ProductReviewDao productReviewDao = null;
	private ShopPointHistoryManager shopPointHistoryManager=null;

	/**
	 * @param productReviewDao
	 *            the productReviewDao to set
	 */
	public void setProductReviewDao(ProductReviewDao productReviewDao) {
		this.productReviewDao = productReviewDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productReviewDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductReview entity) {
		//删除评论时要减去原来对产品的总体评价
		if(entity.getStatus().intValue()==Constants.STATUS_ACTIVE&&entity.getRate().intValue()>0){
			updateProductReviewStat(entity.getStoreId(),entity.getProductId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductReview entity) {
		//设置赠送积分
		if(entity.getGivenTime()==null&&entity.getGivenPoint()!=null&&entity.getGivenPoint()>=0){
			Integer givenPoint=entity.getGivenPoint();
			//指定赠送积分不在系统配置的，就直接取系统配置的最少值
			int []productReviewGivenPoints=ConfigUtil.getInstance().getProductReviewGivenPoints();
			if(!ArrayUtils.contains(productReviewGivenPoints, givenPoint)){
				givenPoint=NumberUtils.min(productReviewGivenPoints);
			}
			entity.setGivenPoint(givenPoint);
			entity.setGivenTime(new Date());
			entity.setGrantorId(RequestContext.getCurrentUserId());
			//赠送积分
			ShopPointHistory shopPointHistory = new ShopPointHistory();
			shopPointHistory.setShopPointType(ShopPointHistory.SHOPPOINT_TYPE_PRODUCTREVIEW);
			shopPointHistory.setAmount(entity.getGivenPoint());
			shopPointHistory.setCustomerId(entity.getReviewUserId());
			shopPointHistory.setDescription("Write a Product Review");
			shopPointHistoryManager .saveShopPointHistoryAndUpdateTotal(shopPointHistory);
		}
	}
	
	public void save(ProductReview entity) {
		onSave(entity);
		this.dao.save(entity);
		//添加对产品的整体评价到产品
		//TODO 通过对状态来判断是否处理，考虑添加字段记录
		if(entity.getId()!=null&&entity.getStatus()!=null&&entity.getStatus().intValue()==Constants.STATUS_ACTIVE.intValue()&&entity.getOrigStatus().intValue()==Constants.STATUS_INACTIVE.intValue()&&entity.getRate().intValue()>0){
			updateProductReviewStat(entity.getStoreId(),entity.getProductId());
		}
	}

	public List<ProductReview> findReplyListByReview(Integer productReviewId) {
		return productReviewDao.findByPropertyOrdered("reviewId", productReviewId,"createTime",true);
	}

	public void deleteReviewReplyById(Integer reviewReplyId) {
		ProductReview productReview=productReviewDao.loadById(reviewReplyId);
		productReviewDao.delete(productReview);
	}
	
	
	public void saveReviewReply(ProductReview reviewReply){
		productReviewDao.save(reviewReply);
	}

	public void setProductStatManager(ProductStatManager productStatManager) {
		this.productStatManager = productStatManager;
	}

	public void setShopPointHistoryManager(
			ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
	}

	public Integer doActiveAllByIds(String[] ids) {
		Integer activeCount=0;
		for (String id : ids) {
			ProductReview productReview=getById(Integer.parseInt(id));
			if(productReview!=null&&productReview.getStatus().intValue()!=Constants.STATUS_ACTIVE.intValue()){
				productReview.setStatus(Constants.STATUS_ACTIVE);
				dao.save(productReview);
				updateProductReviewStat(productReview.getStoreId(),productReview.getProductId());
				activeCount++;
			}
		}
		return activeCount;
	}
	
	public int getCountCustomerReviews(Integer storeId,Integer customerId)
	{
		return productReviewDao.getCountCustomerReviews(storeId,customerId);
	}

	/**
	 * 产品激活评论数量
	 * @param productId
	 * @return
	 */
	private Integer getCountActiceProductReviews(Integer storeId,Integer productId) {
		return productReviewDao.getCountActiceProductReviews(storeId,productId);
	}
	
	/**
	 * 产品激活评论总得分
	 * @param productId
	 * @return
	 */
	private Long getSumActiceProductReviewRates(Integer storeId,Integer productId) {
		return productReviewDao.getSumActiceProductReviewRates(storeId,productId);
	}
	
	public void updateProductReviewStat(Integer storeId,Integer productId){
		Assert.notNull(storeId);
		Assert.notNull(productId);
		ProductStat productStat=productStatManager.getProductStatForUpdate(storeId, productId);
		Integer reviewCount=getCountActiceProductReviews(storeId,productId);
		Long sumRate=getSumActiceProductReviewRates(storeId,productId);
		BigDecimal averageRate=new BigDecimal(0);
		if(reviewCount>0){
			averageRate=new BigDecimal(sumRate).divide(new BigDecimal(reviewCount), RoundingMode.HALF_UP);
		}
		productStat.setAverageRate(averageRate);
		productStat.setReviewCount(reviewCount);
		productStatManager.save(productStat);
	}

	@Override
	public void updateProductReviewStat(Integer productId) {
		// TODO Auto-generated method stub
		
	}
}
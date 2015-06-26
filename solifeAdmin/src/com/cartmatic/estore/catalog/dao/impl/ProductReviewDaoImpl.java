package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductReviewDao;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

/**
 * Dao implementation for ProductReview.
*/
public class ProductReviewDaoImpl extends HibernateGenericDaoImpl<ProductReview> implements ProductReviewDao {
	
	public int getCountCustomerReviews(Integer storeId,Integer customerId)
	{
		String hql = "FROM ProductReview pr where pr.store.storeId=? and pr.reviewUser.appuserId =?";
		Long count = this.countByHql(hql, new Object[]{storeId,customerId});
		return count.intValue();
	}
	
	public Integer getCountActiceProductReviews(Integer storeId,Integer productId){
		String hql = "FROM ProductReview pr where pr.store.storeId=? and pr.reviewId is null and pr.status=? and pr.product.productId =?";
		Long count = this.countByHql(hql, new Object[]{storeId,Constants.STATUS_ACTIVE,productId});
		return count.intValue();
	}
	
	public Long getSumActiceProductReviewRates(Integer storeId,Integer productId){
		String hql = "select sum(pr.rate) FROM ProductReview pr where pr.store.storeId=? and pr.status=? and pr.product.productId =?";
        Long sumRate=(Long)findUnique(hql, new Object[]{storeId,Constants.STATUS_ACTIVE,productId});
        if(sumRate==null)
        	sumRate=new Long(0);
		return sumRate;
	}
	
}

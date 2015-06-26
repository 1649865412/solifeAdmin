package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.cartmatic.estore.catalog.dao.ProductRateValueDao;
import com.cartmatic.estore.common.model.catalog.ProductRateValue;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductRateValue.
*/
public class ProductRateValueDaoImpl extends HibernateGenericDaoImpl<ProductRateValue> implements ProductRateValueDao {

	@Override
	public void deleteAllByProductId(Integer productId) {
		List<Integer>productReviewIdList =findByHql("select pr.productReviewId from ProductReview pr where pr.product.productId=? and pr.reviewId is null", productId);
		if(productReviewIdList!=null&&productReviewIdList.size()>0){
			Query activeQuery =getSession().createQuery("delete prv from ProductRateValue prv where prv.productRateValueId in("+StringUtils.join(productReviewIdList.toArray(new Integer []{}),",")+")");
			activeQuery.setInteger(0, productId);
			activeQuery.executeUpdate();	
		}
	}
}

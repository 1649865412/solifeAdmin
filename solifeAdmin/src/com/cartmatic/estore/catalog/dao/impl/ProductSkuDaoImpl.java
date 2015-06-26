package com.cartmatic.estore.catalog.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductSkuDao;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductSku.
*/
public class ProductSkuDaoImpl extends HibernateGenericDaoImpl<ProductSku> implements ProductSkuDao {

	@SuppressWarnings("unchecked")
	public List<ProductSku> findActiveProductSkusByProductId(Integer productId) {
		List<ProductSku>productSkus=findByHql("from ProductSku ps where ps.status=1 and ps.product.productId=?",productId);
		return productSkus;
	}
	
	/**
	 * 根据productId 获取默认productSku
	 * @param productSkuId
	 * @return
	 */
	public ProductSku getDefaultProductSkuByProductId(final Integer productId){
		return (ProductSku) findUnique("select p.defaultProductSku from Product p where p.productId = ?", productId);
	}

	@Override
	public boolean existImageReference(String imageUrl) {
		String hql = "from ProductSku p where p.image = ? and p.product.status <>?";
		Long count=countByHql(hql, imageUrl,Constants.STATUS_DELETED);
		return count>0;
	}
}

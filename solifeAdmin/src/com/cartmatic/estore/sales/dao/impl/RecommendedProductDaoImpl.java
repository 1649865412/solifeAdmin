
package com.cartmatic.estore.sales.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.RecommendedProductDao;

public class RecommendedProductDaoImpl extends
		HibernateGenericDaoImpl<RecommendedProduct> implements
		RecommendedProductDao {

	public List<RecommendedProduct> getRecommendedProductsByRecommendedTypeIdBySourceId(
			final Integer recommendedTypeId, final Integer sourceId,
			final int firstResult, final int maxResults) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer(200);
				sb.append("from RecommendedProduct where ");
				sb.append(" recommendedTypeId=" + recommendedTypeId);
				sb.append(" and sourceId=" + sourceId);
				sb.append(" order by recommendedProductId desc ");
				Query query = session.createQuery(sb.toString());
				query.setFirstResult(firstResult);
				if (maxResults != -1)
					query.setMaxResults(maxResults);
				List<RecommendedProduct> rows = (List<RecommendedProduct>) query
						.list();
				
				return rows;
			}

		});
		return list;
	}

	public RecommendedProduct getRecommendedProduct(Integer recommendedTypeId,
			Integer sourceId, Integer productId) {
		StringBuffer sb = new StringBuffer(200);
		sb.append("from RecommendedProduct where ");
		sb.append(" recommendedType.recommendedTypeId=" + recommendedTypeId);
		sb.append(" and sourceId=" + sourceId);
		sb.append(" and product.productId=" + productId);
		sb.append(" order by recommendedProductId desc ");

		List list = getHibernateTemplate().find(sb.toString());
		if (list.size() > 0) {
			return (RecommendedProduct) list.get(0);
		} else {
			return null;
		}
	}
	
	public List<Product> getProductsByRecommendedTypeName(final RecommendedType recommendedType,final Store store, final Integer sourceId,
			final int firstResult, final int maxResults) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer(200);
				sb.append("select distinct(rp.product) from RecommendedProduct rp, Product p ");
				if (store!=null&&!Constants.FLAG_TRUE.equals(recommendedType.getIsApplyToCategory())){
					sb.append(",ProductCategory pc ");
				}
				sb.append(" where ");
				sb.append(" rp.recommendedType.recommendedTypeId='"+ recommendedType.getRecommendedTypeId() + "'");
				sb.append(" and rp.sourceId=" + sourceId);
				sb.append(" and rp.product.productId= p.productId");
				sb.append(" and rp.status = 1 ");
				sb.append(" and rp.startTime < :now ");
				sb.append(" and (:now < rp.expireTime or rp.expireTime is null) ");
				sb.append(" and p.status = 1");
				if (store!=null&&!Constants.FLAG_TRUE.equals(recommendedType.getIsApplyToCategory())){
					sb.append(" and pc.product.productId= p.productId");
					//@TODO 只考虑catalog默认的目录Id
					sb.append(" and pc.categoryPath like '"+store.getCatalog().getCategoryId()+".%'");
				}
				sb.append(" order by rp.sortOrder asc ,p.publishTime desc, rp.recommendedProductId desc");
				Query query = session.createQuery(sb.toString());
				
				query.setTimestamp("now", now);
				query.setFirstResult(firstResult);
				if (maxResults != -1)
					query.setMaxResults(maxResults);
				List<Product> rows = (List<Product>) query.list();
				 
				return rows;
			}

		});
		return list;
	}
	
	public List<Product> getProductsByRecommendedTypeName(final RecommendedType recommendedType,final Store store, final List<Integer> sourceIds,
			final int firstResult, final int maxResults) {
		List list;
		if (sourceIds.size() != 0) {
			list = getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Date now = new Date();
					String sourceIdString = "";
					for (Integer sourceId : sourceIds) {
						sourceIdString += sourceId.toString() + ",";
					}
					sourceIdString = sourceIdString.substring(0, sourceIdString.length() - 1);

					StringBuffer sb = new StringBuffer(200);
					sb.append("select distinct(rp.product) from RecommendedProduct rp, Product p ");
					if (store!=null&&!Constants.FLAG_TRUE.equals(recommendedType.getIsApplyToCategory())){
						sb.append(",ProductCategory pc ");
					}
					sb.append(" where ");
					sb.append(" rp.recommendedType.recommendedTypeId='"+ recommendedType.getRecommendedTypeId() + "'");
					sb.append(" and rp.sourceId in ( " + sourceIdString + " )");
					sb.append(" and rp.product.productId= p.productId");
					sb.append(" and rp.status = 1 ");
					sb.append(" and rp.startTime < :now ");
					sb.append(" and (:now < rp.expireTime or rp.expireTime is null) ");
					sb.append(" and p.status = 1");
					if (store!=null&&!Constants.FLAG_TRUE.equals(recommendedType.getIsApplyToCategory())){
						sb.append(" and pc.product.productId= p.productId");
						//@TODO 只考虑catalog默认的目录Id
						sb.append(" and pc.categoryPath like '"+store.getCatalog().getCategoryId()+".%'");
					}
					sb.append(" order by rp.sortOrder asc ,p.publishTime desc, rp.recommendedProductId desc");
					Query query = session.createQuery(sb.toString());
					query.setTimestamp("now", now);
					query.setFirstResult(firstResult);
					if (maxResults != -1)
						query.setMaxResults(maxResults);
					List<Product> rows = (List<Product>) query.list();
					
					return rows;
				}

			});
			return list;
		} else {
			return new ArrayList();
		}
	}
	
	public void removeRecommendedProductsByProductId(final Integer productId){
		List list;
		list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sb = new StringBuffer(200);
						sb.append("from RecommendedProduct rp where (rp.product.productId ="
								+ productId +")");
						sb.append(" or (rp.sourceId = "+productId+ " and  rp.recommendedType.isApplyToProduct = 1)");
						Query query = session.createQuery(sb.toString());
						List<RecommendedProduct> rows = (List<RecommendedProduct>) query.list();
						
						return rows;
					}

				});
		getHibernateTemplate().deleteAll(list);
	}

}
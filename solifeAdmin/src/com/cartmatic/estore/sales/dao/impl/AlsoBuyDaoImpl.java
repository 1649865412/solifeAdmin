
package com.cartmatic.estore.sales.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.AlsoBuy;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.AlsoBuyDao;

public class AlsoBuyDaoImpl extends HibernateGenericDaoImpl<AlsoBuy> implements
		AlsoBuyDao {

	public List<Product> getProductsBySourceId(final Integer categoryId,final Integer productId,
			final int firstResult, final int maxResults) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer(200);
				sb
						.append("select distinct(p) from AlsoBuy ab, Product p,ProductCategory pc where ");
				sb.append(" p.status = 1");
				sb.append(" and pc.product.productId= p.productId");
				//@TODO 只考虑catalog默认的目录Id
				sb.append(" and pc.categoryPath like '"+categoryId+".%'");
				sb.append(" and ab.productId=" + productId);
				sb.append(" and ab.alsoProduct.productId= p.productId");
				sb
						.append(" order by ab.times desc , p.publishTime desc, p.productId desc");
				Query query = session.createQuery(sb.toString());
				query.setFirstResult(firstResult);
				if (maxResults != -1)
					query.setMaxResults(maxResults);
				List<Product> rows = (List<Product>) query.list();
				return rows;
			}
		});
		return list;
	}

	public List<Product> getProductsBySourceIdList(final Integer categoryId,
			final List<Integer> productIds, final int firstResult,
			final int maxResults) {
		List list;
		if (productIds.size() != 0) {
			list = getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					String productIdString = "";
					for (Integer productId : productIds) {
						productIdString += productId.toString() + ",";
					}

					productIdString = productIdString.substring(0,productIdString.length() - 1);
					StringBuffer sb = new StringBuffer(200);
					sb
							.append("select distinct(p) from AlsoBuy ab, Product p,ProductCategory pc where ");

					sb.append(" p.status = 1");
					sb.append(" and pc.product.productId= p.productId");
					//@TODO 只考虑catalog默认的目录Id
					sb.append(" and pc.categoryPath like '"+categoryId+".%'");
					sb.append(" and ab.productId in ( " + productIdString + " )");
					sb.append(" and ab.alsoProduct.productId= p.productId");
					sb.append(" order by p.publishTime desc, p.productId desc");
					Query query = session.createQuery(sb.toString());
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

	public AlsoBuy getAlsoBuyByProductIdByAlsoProductId(Integer productId,
			Integer alsoProductId) {
		List list = getHibernateTemplate().find(
				"from AlsoBuy  where productId=" + productId
						+ " and alsoProduct.productId=" + alsoProductId);

		if (list.size() == 0) {
			return null;
		}
		return (AlsoBuy) list.get(0);
	}

	public int removeAlsoBuyProductsByProductId(final Integer productId) {
		int size = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuffer sb = new StringBuffer(200);
						sb.append("delete from AlsoBuy where productId ="
								+ productId + " or alsoProduct.productId="
								+ productId);
						Query query = session.createQuery(sb.toString());
						return query.executeUpdate();
					}

				});
		return size;
	}

}
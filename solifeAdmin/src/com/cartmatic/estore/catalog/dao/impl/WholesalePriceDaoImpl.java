package com.cartmatic.estore.catalog.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.WholesalePriceDao;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for WholesalePrice.
*/
public class WholesalePriceDaoImpl extends HibernateGenericDaoImpl<WholesalePrice> implements WholesalePriceDao {
	public WholesalePrice getSalePriceBySkuIdByMinQuantity(final Integer productSkuId,final Integer minQuantity){
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Date now = new Date();
				StringBuffer sb = new StringBuffer();
				sb.append("from WholesalePrice w where ");
				sb.append("w.productSku.productSkuId = ").append(productSkuId);
				sb.append(" and w.minQuantity=").append(minQuantity);
				Query query = session.createQuery(sb.toString());
				List<PromoRule> rows = (List<PromoRule>) query.list();
				return rows;
			}

		});
		if(list.size()!=0){
			return (WholesalePrice)(list.get(0));
		}else{
			return null;
		}
	}
	
}

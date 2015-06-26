package com.cartmatic.estore.sales.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.SalesDashboardDao;

public class SalesDashboardDaoImpl extends HibernateGenericDaoImpl<PromoRule> implements SalesDashboardDao{
	public List<PromoRule> getAllPromotionRulesInProcess(Integer count){
		String hql="from PromoRule r where r.status = ? and r.startTime < ?  and ? < r.endTime or r.endTime is null)  order by r.createTime desc";
		Date now = new Date();
		List<PromoRule> list=null;
		if(count!=null&&count>0){
			list=findByHqlPaged(hql, 0, count, new Object[]{Constants.STATUS_ACTIVE,now,now});
		}else{
			list=findByHql(hql, 0, count, new Object[]{Constants.STATUS_ACTIVE,now,now});
		}
		return list;

	}
}

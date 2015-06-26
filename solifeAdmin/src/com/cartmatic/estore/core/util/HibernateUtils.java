/**
 * 
 */

package com.cartmatic.estore.core.util;

import org.hibernate.Session;
import org.hibernate.engine.query.HQLQueryPlan;
import org.hibernate.hql.QueryTranslator;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.impl.SessionImpl;

/**
 * @author Ryan
 * 
 */
public class HibernateUtils {
	/**
	 * @param hql只支持单条Hql,不支持"(),"分隔表示多条hql
	 * @param session
	 * @return
	 */
	public static String hqlToSql(String hql,Session session){
		SessionImpl sessionImpl=(SessionImpl)session;
		SessionFactoryImpl sessionFactoryImpl=(SessionFactoryImpl)sessionImpl.getFactory();
		HQLQueryPlan hqlQueryPlan=sessionFactoryImpl.getQueryPlanCache().getHQLQueryPlan(hql, false, sessionImpl.getEnabledFilters());
		QueryTranslator translators[]=hqlQueryPlan.getTranslators();
		if(translators.length>0){
			return translators[0].getSQLString();
		}
		return null;
	}
}

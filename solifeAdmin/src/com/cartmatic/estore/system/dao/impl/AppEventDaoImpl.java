package com.cartmatic.estore.system.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.cartmatic.estore.common.model.system.AppEvent;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.AppEventDao;

/**
 * Dao implementation for AppEvent.
 */
public class AppEventDaoImpl extends HibernateGenericDaoImpl<AppEvent> implements AppEventDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.system.dao.AppEventDao#findEventsAfterTime(long)
	 */
	public List<AppEvent> findEventsAfterTime(long updateTime) {
		return findByHql("from AppEvent where updateTime>? order by updateTime asc", new Date(updateTime));
	}
	
	/**
	 * 
	 * @param hql
	 * @param numOfPerTime 每次读取的最大值
	 * @param page 由0开始
	 * @return
	 */
	public List fetchEntitysToProcess(String hql, int numOfPerTime, int page)
	{
        Query queryObject = getSession().createQuery(hql);
        queryObject.setFirstResult(numOfPerTime * page);
        queryObject.setMaxResults(numOfPerTime);
        queryObject.setFetchSize(numOfPerTime);
        return queryObject.list();
	}
}

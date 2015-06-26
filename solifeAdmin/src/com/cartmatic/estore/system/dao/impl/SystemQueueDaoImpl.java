package com.cartmatic.estore.system.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.SystemQueueDao;

/**
 * Dao implementation for SystemQueue.
*/
public class SystemQueueDaoImpl extends HibernateGenericDaoImpl<SystemQueue> implements SystemQueueDao {

	public List<SystemQueue> fetchEmailListToProcess(int numOfEmailsPerTime) {
		Query queryObject = getSession().createQuery(
				"from SystemQueue q where queueStatus=? and nextRetryTime<?".intern());
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(numOfEmailsPerTime);
		queryObject.setFetchSize(numOfEmailsPerTime);
		queryObject.setParameter(0, SystemQueue.STATUS_NOT_SEND);
		queryObject.setParameter(1, DateUtil.getNow());
		queryObject.setLockMode("q", LockMode.UPGRADE);
		SessionFactoryUtils.applyTransactionTimeout(queryObject,
				getSessionFactory());
		return queryObject.list();
	}

	public List<SystemQueue> findExpiredEmails(int daysToKeep, int numOfEmailsPerTime) {
		Date date = DateUtil.getNumOfDaysBeforeNow(daysToKeep);
		
		Query queryObject = getSession().createQuery("from SystemQueue q where updateTime<?".intern());
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(numOfEmailsPerTime);
		queryObject.setFetchSize(numOfEmailsPerTime);
		queryObject.setParameter(0, date);
		
		return queryObject.list();
	}

	public List<SystemQueue> findIncorrectEmails(int numOfEmailsPerTime) {
		Date date = DateUtil.getNumOfDaysBeforeNow(1);
		
		Query queryObject = getSession().createQuery("from SystemQueue q where queueStatus=? and updateTime<?".intern());
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(numOfEmailsPerTime);
		queryObject.setFetchSize(numOfEmailsPerTime);
		queryObject.setParameter(0, SystemQueue.STATUS_SENDING);
		queryObject.setParameter(1, date);
		
		return queryObject.list();
	}
}

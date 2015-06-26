package com.cartmatic.estore.system.service.impl;

import java.util.Iterator;
import java.util.List;

import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.SystemQueueDao;
import com.cartmatic.estore.system.service.SystemQueueManager;


/**
 * Manager implementation for SystemQueue, responsible for business processing, and communicate between web and persistence layer.
 */
public class SystemQueueManagerImpl extends GenericManagerImpl<SystemQueue> implements SystemQueueManager {

	private SystemQueueDao systemQueueDao = null;
	
	public int			daysToKeep		= 10;
	
	public void cleanExpiredQueue() {
		logger.debug("Cleaning expried emails which is older than x days: "
				+ daysToKeep);
		List<SystemQueue> items = null;
		do{
			items = systemQueueDao.findExpiredEmails(daysToKeep, FETCH_SIZE_PER_TIME);
			if(items==null || items.size()==0)
				return;
			for (Iterator<SystemQueue> iterator = items.iterator(); iterator.hasNext();) {
				SystemQueue item = iterator.next();
				logger.debug("Deleting an expired queue:" + item.getSystemQueueName());
				delete(item);
			}
			this.flush();
		}while(FETCH_SIZE_PER_TIME == items.size());
	}
	
	public List<SystemQueue> fetchQueueListToProcess(int numOfEmailsPerTime) {
		List<SystemQueue> items = systemQueueDao.fetchEmailListToProcess(numOfEmailsPerTime);
		logger.debug("Locking " + items.size() + " queued emails to send.");
		for (Iterator<SystemQueue> iterator = items.iterator(); iterator
				.hasNext();) {
			SystemQueue item = iterator.next();
			item.setQueueStatus(SystemQueue.STATUS_SENDING);
			save(item);
		}
		return items;
	}
	
	public void resetIncorrectQueue() {
		if(logger.isDebugEnabled())
			logger.debug("Reseting emails with not normal status (e.g. sending).");
		
		List<SystemQueue> items = null;
		do{
			items = systemQueueDao.findIncorrectEmails(FETCH_SIZE_PER_TIME);
			if(items==null || items.size()==0)
				return;
			for (Iterator<SystemQueue> iterator = items.iterator(); iterator
					.hasNext();) {
				SystemQueue item = iterator.next();
				item.setQueueStatus(SystemQueue.STATUS_NOT_SEND);
				item.appendErrorMsg("Being in sending status too long, server shutdowned or due to other cause.");
				if (logger.isDebugEnabled())
					logger.debug("Reset an incorrect email (in sending status too long):" + item.getSystemQueueId());
				save(item);
			}
			this.flush();
		}while(FETCH_SIZE_PER_TIME == items.size());
	}

	public void setDaysToKeep(int daysToKeep) {
		this.daysToKeep = daysToKeep;
	}
	/**
	 * @param systemQueueDao
	 *            the systemQueueDao to set
	 */
	public void setSystemQueueDao(SystemQueueDao systemQueueDao) {
		this.systemQueueDao = systemQueueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = systemQueueDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SystemQueue entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SystemQueue entity) {

	}
	
}

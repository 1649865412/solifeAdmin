package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for SystemQueue.
 */
public interface SystemQueueDao extends GenericDao<SystemQueue> {
	List<SystemQueue> fetchEmailListToProcess(int numOfEmailsPerTime);

	public List<SystemQueue> findExpiredEmails(int daysToKeep, int numOfEmailsPerTime);

	public List<SystemQueue> findIncorrectEmails(int numOfEmailsPerTime);
}
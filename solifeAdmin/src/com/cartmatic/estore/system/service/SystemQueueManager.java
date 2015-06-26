package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SystemQueue, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SystemQueueManager extends GenericManager<SystemQueue> {
	public static int FETCH_SIZE_PER_TIME = 20;
	
	public void cleanExpiredQueue();

	public List<SystemQueue> fetchQueueListToProcess(int numOfEmailsPerTime);

	public void resetIncorrectQueue();
}

package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.AppEvent;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for AppEvent.
 */
public interface AppEventDao extends GenericDao<AppEvent> {
	public List<AppEvent> findEventsAfterTime(long updateTime);
	public List fetchEntitysToProcess(String hql, int numOfPerTime, int page);
}
package com.cartmatic.estore.system.service.impl;

import java.util.Iterator;
import java.util.List;

import com.cartmatic.estore.common.model.system.AppEvent;
import com.cartmatic.estore.core.event.AppEventHandler;
import com.cartmatic.estore.core.event.ApplicationEvent;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.system.dao.AppEventDao;
import com.cartmatic.estore.system.service.AppEventManager;


/**
 * Manager implementation for AppEvent, responsible for business processing, and communicate between web and persistence layer.
 */
public class AppEventManagerImpl extends GenericManagerImpl<AppEvent> implements AppEventManager {

	private AppEventDao appEventDao = null;
	//private List<AppEventHandler> appEventHandlers;
	private AppEventHandler appEventHandler = null;
	//private IndexNotifyEventHandler indexNotifyEventHandler = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = appEventDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(AppEvent entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(AppEvent entity) {

	}

	private long lastProcessTime=0;
	
	public void fireApplicationEvent(ApplicationEvent event) {
        logger.info("Firing application event:" + event);
        appEventHandler.handleApplicationEvent(event);
        // persiste application event
        AppEvent appEvent = new AppEvent();
        appEvent.setAppEvent(event);
        save(appEvent);
    }
	
	/* (non-Javadoc)
	 * @see com.cartmatic.estore.system.service.AppEventManager#handleApplicationEvents()
	 */
	public void processApplicationEvents() {
		if (lastProcessTime==0) {
			lastProcessTime=ContextUtil.getInstance().getStartupTime();
		}
		List<AppEvent> eventList = appEventDao.findEventsAfterTime(lastProcessTime);
		for (Iterator iter = eventList.iterator(); iter.hasNext();) {
			AppEvent appEvent = (AppEvent) iter.next();
			try {
			    appEventHandler.handleApplicationEvent((ApplicationEvent)appEvent.getAppEvent());				
			} catch (Throwable e) {
				logger.error("Error processing application event!", e);
			}
			lastProcessTime=appEvent.getUpdateTime().getTime();
		}
	}
	
	/**
	 * 定时任务器调度
	 * 专门用于处理index的event
	 * 
	 */
	/*public void processIndexNotifyEvents() {
        if (lastProcessTime==0) {
            lastProcessTime=ContextUtil.getInstance().getStartupTime();
        }
        List<AppEvent> eventList = appEventDao.findEventsAfterTime(lastProcessTime);
        for (Iterator iter = eventList.iterator(); iter.hasNext();) {
            AppEvent appEvent = (AppEvent) iter.next();
            try {
                //ContextUtil.getInstance().handleApplicationEvent((ApplicationEvent)appEvent.getAppEvent());
                if (appEvent.getAppEvent() != null 
                                && appEvent.getAppEvent() instanceof IndexNotifyEvent)
                {
                    indexNotifyEventHandler.handleApplicationEvent((ApplicationEvent) appEvent.getAppEvent());
                }
            } catch (Throwable e) {
                logger.error("Error processing application event!", e);
            }
            lastProcessTime=appEvent.getUpdateTime().getTime();
        }
    }*/

	/**
     * @param appEventDao
     *            the appEventDao to set
     */
    public void setAppEventDao(AppEventDao appEventDao) {
        this.appEventDao = appEventDao;
    }
	public void setAppEventHandler(AppEventHandler avalue)
	{
	    appEventHandler = avalue;
	}
}

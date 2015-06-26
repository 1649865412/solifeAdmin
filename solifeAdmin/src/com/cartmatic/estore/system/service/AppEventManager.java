package com.cartmatic.estore.system.service;

import com.cartmatic.estore.common.model.system.AppEvent;
import com.cartmatic.estore.core.event.ApplicationEvent;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AppEvent, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AppEventManager extends GenericManager<AppEvent> {
	public void processApplicationEvents();
	public void fireApplicationEvent(ApplicationEvent event);
}

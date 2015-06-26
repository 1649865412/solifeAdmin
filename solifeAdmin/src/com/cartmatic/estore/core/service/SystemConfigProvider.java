/*
 * Created on Jan 16, 2008
 * 
 */

package com.cartmatic.estore.core.service;

import java.util.Map;

public interface SystemConfigProvider {
	Object getCompanyInfo();

	Map<String, String> getConfigAsMap();
}

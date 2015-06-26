/*
 * Created on Jan 9, 2008
 * 
 */

package com.cartmatic.estore.core.hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

/**
 * 修改说明：要灵活的支持src和jar里面都有hbm文件，所以在mapping那里记录、过滤重复的文件。由于处理比较麻烦，所以只支持包名为/com/cartmatic/的情况。
 * 
 * @author Ryan
 * 
 */
public class LocalSessionFactoryBean extends
		org.springframework.orm.hibernate3.LocalSessionFactoryBean {
	@Override
	public void setMappingLocations(Resource[] mappingLocations) {
		List<Resource> mappingLocationsList = new ArrayList<Resource>();
		List<String> urlList = new ArrayList<String>();
		try {
			for (int i = 0; i < mappingLocations.length; i++) {
				Resource resource = mappingLocations[i];
				String resourceFileName = resource.getFilename();
				if (!urlList.contains(resourceFileName)
						|| resource.getURL().toString().indexOf("/com/cartmatic/") == -1) {
					mappingLocationsList.add(resource);
					urlList.add(resourceFileName);
				} else {
					if (logger.isTraceEnabled()) {
						logger.trace("Skip mapping location (already added): "
								+ resource.getURL());
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(
					"Unexpected error when adding hibernate mapping location, invalid mapping url?",
					e);
		}

		super.setMappingLocations(mappingLocationsList
				.toArray(new Resource[mappingLocationsList.size()]));
	}
}

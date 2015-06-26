package com.cartmatic.estore.content.service;

import java.util.List;

import com.cartmatic.estore.common.model.content.Advertisement;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Advertisement, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AdvertisementManager extends GenericManager<Advertisement> {
	public Advertisement getAdvertisementByName(String advertisementName);
	
	public List<Advertisement> getAvalidAdvertisements(String categoryPath, Integer positionId);
	
	
	public void saveAdvertisement(Advertisement advertisement, String[] categoryIds);
}

package com.cartmatic.estore.content.service;

import java.util.Map;

import com.cartmatic.estore.common.model.content.AdPositionType;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for AdPositionType, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface AdPositionTypeManager extends GenericManager<AdPositionType> {
	public AdPositionType getAdPositionTypeByName(Integer storeId,String positionName);
	public Map<String,AdPositionType> getAllsForFront(String storeCode);
}

package com.cartmatic.estore.supplier.service;

import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for TbCategoryRefer, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface TbCategoryReferManager extends GenericManager<TbCategoryRefer> {
	public TbCategoryRefer getTbCategoryReferByTbCId(Long tbCategoryId);
	
	public TbCategoryRefer addTbCategoryRefer(Long tbCategoryId);
	
	public TbCategoryRefer getTbCategoryReferByCategoryId(Integer categoryId);
}

package com.cartmatic.estore.supplier.service;

import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for TbCatPropValueRefer, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface TbCatPropValueReferManager extends GenericManager<TbCatPropValueRefer> {
	public TbCatPropValueRefer getTbCatPropValueReferByCatePropValueId(Long tbCatPropValueId);
}

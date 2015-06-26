package com.cartmatic.estore.supplier.service;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for TbCatPropRefer, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface TbCatPropReferManager extends GenericManager<TbCatPropRefer> {
	public TbCatPropRefer getTbCatPropReferByTbCatPropId(Long tbCatPropId);
	public void addTbCatPropRefer(Long tb_cid,Long tbCatPropId,Long tbCatPropValueId);
	public void addTbCatPropRefers(Long tb_cid,List<TbCatPropValueRefer> tbCatPropValueReferList);
	
	public TbCatPropRefer getTbCatPropReferByAccGroupCode(String code);
}

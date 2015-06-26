package com.cartmatic.estore.supplier.service;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Supplier, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SupplierManager extends GenericManager<Supplier> {
	/**
	 * 获得最后一个
	 * @return
	 */
	public Supplier getLast();
	
	/**
	 * 根据供应商编码查找相应的供应商
	 * @param supplierCode
	 * @return
	 */
	public Supplier getSupplierByCode(String supplierCode);
	
	
	/**
	 * 获取所有激活的供应商
	 */
	public List<Supplier> findActiveSuppliers();
	
	/**
	 * 获取所有供应供应商，按默认排序（默认的供应拍第一，其他嗯按顺序）
	 * @return
	 */
	public List<Supplier>getAllByDefaultSortBy();
}

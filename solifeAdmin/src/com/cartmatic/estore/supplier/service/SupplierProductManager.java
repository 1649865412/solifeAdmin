package com.cartmatic.estore.supplier.service;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SupplierProduct, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SupplierProductManager extends GenericManager<SupplierProduct> {
	/**
	 *获取所有与系统产品关联的供应商产品
	 * @return
	 */
	public List<SupplierProduct> findSupplierProductByProductId(Integer productId);
	
	/**
	 * 获取产品关联的供应商,（激活的，pending的）
	 * @param productId
	 * @return
	 */
	public List<Supplier> findSuppliersByProductId(Integer productId);
	
	/**
	 * 物理删除产品时，与该产品关联的供应商产品设为null
	 * @param productId
	 */
	public void deleteProductToSetNull(Integer productId);
	
	/**
	 * 根据供应商Id及产品编码查找产品
	 * @param supplierId
	 * @param productCode
	 * @return
	 */
	public SupplierProduct getSupplierProductBySupplierIdAndProductCode(Integer supplierId,String productCode);
	

	public void setUploadLogs(SupplierProduct supplierProduct);
}

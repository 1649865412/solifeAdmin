package com.cartmatic.estore.supplier.dao;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for SupplierProduct.
 */
public interface SupplierProductDao extends GenericDao<SupplierProduct> {
	/**
	 * 获取产品关联的供应商,（激活的，pending的）
	 * @param productId
	 * @return
	 */
	public List<Supplier> findSuppliersByProductId(Integer productId);
	
	/**
	 * 根据供应商Id及产品编码查找产品
	 * @param supplierId
	 * @param productCode
	 * @return
	 */
	public SupplierProduct getSupplierProductBySupplierIdAndProductCode(Integer supplierId,String productCode);
	
	/**
	 * 查找供应商产品比较属性数据
	 * @param supplierProductId
	 * @return
	 */
	public Object [] getSupplierProductData4Compare(Integer supplierProductId);
}
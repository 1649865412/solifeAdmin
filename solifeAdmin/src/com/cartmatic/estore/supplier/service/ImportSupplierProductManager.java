package com.cartmatic.estore.supplier.service;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;

public interface ImportSupplierProductManager {
	public void doImportSupplierProduct(Supplier supplier, String importImageFile,String productName,String productCode,Long tb_cid, String price, String description,String image,String tb_catProps, String tb_sell_catProps, Long tb_id);
}

package com.cartmatic.estore.supplier.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.supplier.dao.SupplierProductDao;

/**
 * Dao implementation for SupplierProduct.
*/
public class SupplierProductDaoImpl extends HibernateGenericDaoImpl<SupplierProduct> implements SupplierProductDao {

	public List<Supplier> findSuppliersByProductId(Integer productId) {
		String hql="select sp.supplier from SupplierProduct sp where sp.status <=? and sp.product.productId=?";
		List<Supplier> supplierList=findByHql(hql, new Object[]{Constants.STATUS_ACTIVE,productId});
		return supplierList;
	}

	@Override
	public SupplierProduct getSupplierProductBySupplierIdAndProductCode(Integer supplierId, String productCode) {
		String hql="select sp from SupplierProduct sp where sp.supplier.supplierId =? and sp.productCode=?";
		List<SupplierProduct> supplierList=findByHql(hql, new Object[]{supplierId,productCode});
		if(supplierList.size()>0){
			return supplierList.get(0);
		}
		return null;
	}

	@Override
	public Object [] getSupplierProductData4Compare(Integer supplierProductId) {
		String hql="select sp.productName,sp.productCode,sp.wholesalePrice,sp.mediaUrl,sp.tbSellCatProps,sp.productDesc,sp.tbCId,sp.category.categoryId from SupplierProduct sp where sp.supplierProductId =?";
		List<Object[]> supplierProductData=findByHql(hql, supplierProductId);
		if(supplierProductData.size()>0){
			return supplierProductData.get(0);
		}else{
			return null;
		}
	}

}

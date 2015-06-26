package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductPackageItem, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductPackageItemManager extends GenericManager<ProductPackageItem> {
	
	/**
	 * 保存产品包，
	 * @param productSkuId
	 * @param productPackageItemIds 产品包包含的Item
	 * @param quantitys 包含的Item的相应数量
	 */
	public void saveProductPackageItemAction(Integer productSkuId,Integer []productPackageItemIds,Integer[]quantitys);
	
	/**
	 * 查找某产品在哪些产品包出现
	 * @param itemSkuId  某产品包Item Id
	 * @return
	 */
	public List<ProductPackageItem> findProductPackageItemListByItem(Integer itemSkuId);
	
	public List<Product> getProductPackageByItemProduct(Integer itemProductId);
}

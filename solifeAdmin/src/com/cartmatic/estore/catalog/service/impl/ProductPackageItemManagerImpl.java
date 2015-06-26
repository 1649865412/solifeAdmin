package com.cartmatic.estore.catalog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import com.cartmatic.estore.catalog.dao.ProductPackageItemDao;
import com.cartmatic.estore.catalog.service.ProductPackageItemManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductPackageItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductPackageItemManagerImpl extends GenericManagerImpl<ProductPackageItem> implements ProductPackageItemManager {

	private ProductSkuManager	productSkuManager	= null;
	private ProductPackageItemDao productPackageItemDao = null;

	/**
	 * @param productPackageItemDao
	 *            the productPackageItemDao to set
	 */
	public void setProductPackageItemDao(ProductPackageItemDao productPackageItemDao) {
		this.productPackageItemDao = productPackageItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productPackageItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductPackageItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductPackageItem entity) {

	}


	public void saveProductPackageItemAction(Integer productSkuId,Integer []itemSkuIds,Integer[]quantitys) {
		ProductSku productSku=productSkuManager.getById(productSkuId);
		Set<ProductPackageItem>productPackageItems=productSku.getProductPackageItems();
		if(productPackageItems==null)productPackageItems=new HashSet<ProductPackageItem>();
		//删除没有关联的Item
		for (ProductPackageItem productPackageItem : productPackageItems) {
			/*boolean exist = false;
			for (Integer itemSkuId : itemSkuIds) {
				if(itemSkuId.intValue()==productPackageItem.getItemSkuId().intValue()){
					exist=true;
					break;
				}
			}*/
			boolean exist =ArrayUtils.contains(itemSkuIds, productPackageItem.getItemSkuId());
			if(!exist){
				delete(productPackageItem);
			}
		}
		//新增或更新Item
		for (int i = 0; i < itemSkuIds.length; i++) {
			Integer itemSkuId = itemSkuIds[i];
			ProductPackageItem temp_productPackageItem=null;
			for (ProductPackageItem productPackageItem : productPackageItems) {
				if(itemSkuId.intValue()==productPackageItem.getItemSkuId().intValue()){
					temp_productPackageItem=productPackageItem;
					break;
				}
			}
			if(temp_productPackageItem==null){
				temp_productPackageItem=new ProductPackageItem();
				temp_productPackageItem.setProductSku(productSku);
				ProductSku itemSku=productSkuManager.getById(itemSkuId);
				temp_productPackageItem.setItemSku(itemSku);
			}
			temp_productPackageItem.setQuantity(quantitys[i]);
			temp_productPackageItem.setSortOrder(i);
			save(temp_productPackageItem);
		}
		
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public List<ProductPackageItem> findProductPackageItemListByItem(Integer itemSkuId) {
		List<ProductPackageItem> result=findByPropertyOrdered("itemSku.productSkuId", itemSkuId, "sortOrder", true);
		return result;
	}

	public List<Product> getProductPackageByItemProduct(Integer itemProductId) {
		List<Product> productPackageList=productPackageItemDao.getProductPackageByItemProduct(itemProductId);
		return productPackageList;
	}

}

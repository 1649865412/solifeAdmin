package com.cartmatic.estore.catalog.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductAttGroupDao;
import com.cartmatic.estore.catalog.service.ProductAttGroupManager;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductAttGroup, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductAttGroupManagerImpl extends GenericManagerImpl<ProductAttGroup> implements ProductAttGroupManager {
	
	private ProductAttGroupDao productAttGroupDao = null;

	/**
	 * @param productAttGroupDao
	 *            the productAttGroupDao to set
	 */
	public void setProductAttGroupDao(ProductAttGroupDao productAttGroupDao) {
		this.productAttGroupDao = productAttGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productAttGroupDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductAttGroup entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductAttGroup entity) {

	}

	public List<ProductAttGroup> findProductAttGroupsByProductType(
			Integer productTypeId) {
		List<ProductAttGroup>productAttGroups=findByPropertyOrdered("productType.productTypeId",productTypeId,"sortOrder", true);
		return productAttGroups;
	}

	/*@SuppressWarnings("unchecked")
	public List<Attribute> findAttributesExcludeRefGroup(Integer attGroupId) {
		ProductAttGroup productAttGroup=this.getById(attGroupId);
		List<Attribute> productAllAttributes=attributeService.getAttributes(Contants.MODULE_PRODUCT.shortValue());
		if(productAttGroup!=null){
			Set<ProductAttGroupItem>productAttGroupItems=productAttGroup.getProductAttGroupItems();
			if(productAttGroupItems!=null){
				for (ProductAttGroupItem productAttGroupItem : productAttGroupItems) {
					int index=productAllAttributes.size();
					for(;index>=0;index--){
						if(productAllAttributes.get(index).getAttributeId()==productAttGroupItem.getAttributeId()){
							productAllAttributes.remove(index);
							break;
						}
					}
				}
			}
		}
		return productAllAttributes;
	}*/

}

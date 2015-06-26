
package com.cartmatic.estore.catalog.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductTypeDao;
import com.cartmatic.estore.catalog.service.ProductAttGroupItemManager;
import com.cartmatic.estore.catalog.service.ProductAttGroupManager;
import com.cartmatic.estore.catalog.service.ProductRateItemManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.catalog.service.ProductTypeSkuOptionManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.ProductTypeSkuOption;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductType, responsible for business processing,
 * and communicate between web and persistence layer.
 */
public class ProductTypeManagerImpl extends GenericManagerImpl<ProductType>
		implements ProductTypeManager {
	private ProductAttGroupManager		productAttGroupManager		= null;
	private ProductAttGroupItemManager	productAttGroupItemManager	= null;
	private ProductRateItemManager		productRateItemManager		= null;
	private ProductTypeDao				productTypeDao				= null;
	private AttributeService attributeService=null;
	private ProductTypeSkuOptionManager productTypeSkuOptionManager = null;

	public void setProductTypeSkuOptionManager(ProductTypeSkuOptionManager productTypeSkuOptionManager) {
		this.productTypeSkuOptionManager = productTypeSkuOptionManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setProductTypeDao(ProductTypeDao productTypeDao) {
		this.productTypeDao = productTypeDao;
	}


	@Override
	protected void initManager() {
		dao = productTypeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductType entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductType entity) {

	}

	public List<ProductType> findActiveProductTypes() {
		List<ProductType> productTypes = productTypeDao.findByProperty("status", Constants.STATUS_ACTIVE);
		return productTypes;
	}


	/**
	 * 更新保存产品类型关联的自定义属性
	 * @param productTypeId 
	 * @param attGroupNames 普通自定义属性组名
	 * @param attGroupIds 普通自定义属性组Id，0表示新增
	 * @param allAttGroupItems 自定义属性组Item
	 */
	@SuppressWarnings("unchecked")
	private void saveProductTypeAttributes(Integer productTypeId,String []attGroupNames,String []attGroupIds,String []allAttGroupItems) {
		/**
		 * 属性组=attGroupNames （独立Input）
		 * 属性组id=attGroupIds （独立Input） 0表示新增，否则为属性组Id
		 * 属性组属性=attGroupItems  1,11,5,16,|3,15,85,45,|97,62,  （每组属性独立Input）
		 **/

		//保存的属性组
		List<ProductAttGroup> tempProductAttGroups=new ArrayList<ProductAttGroup>();
		//保存本产品类型关联的所有属性
		List<ProductAttGroupItem>tempproductAttGroupItems=new ArrayList<ProductAttGroupItem>();
		for (int i = 0; i < attGroupNames.length; i++) {
			ProductAttGroup productAttGroup=new ProductAttGroup();
			tempProductAttGroups.add(productAttGroup);
			productAttGroup.setProductAttGroupId(Integer.valueOf(attGroupIds[i]));
			productAttGroup.setProductAttGroupName(attGroupNames[i]);
			productAttGroup.setSortOrder(i);
			Set<ProductAttGroupItem> ProductAttGroupItems=new HashSet<ProductAttGroupItem>();
			productAttGroup.setProductAttGroupItems(ProductAttGroupItems);
			if(allAttGroupItems.length>0&&StringUtils.isNotEmpty(allAttGroupItems[i])){
				String attGroupItems[]=allAttGroupItems[i].split(",");
				for (int j = 0; j < attGroupItems.length; j++) {
					ProductAttGroupItem productAttGroupItem=new ProductAttGroupItem();
					productAttGroupItem.setAttributeId(Integer.valueOf(attGroupItems[j]));
					productAttGroupItem.setSortOrder(j);
					productAttGroupItem.setProductTypeId(productTypeId);
					ProductAttGroupItems.add(productAttGroupItem);
					tempproductAttGroupItems.add(productAttGroupItem);
				}
			}
		}
		//清除没有关联的属性
		List<ProductAttGroupItem> productAttGroupItems = productAttGroupItemManager.findProductAttGroupItemsByProductTypeId(productTypeId);
		for (ProductAttGroupItem productAttGroupItem : productAttGroupItems) {
			boolean flag = false;
			for (ProductAttGroupItem tempproductAttGroupItem : tempproductAttGroupItems) {
				if (tempproductAttGroupItem.getAttributeId().intValue() == productAttGroupItem.getAttributeId().intValue()) {
					//保存Id以便处理更新
					tempproductAttGroupItem.setProductAttGroupItemId(productAttGroupItem.getProductAttGroupItemId());
					flag = true;
					break;
				}
			}
			if (!flag) {
				productAttGroupItemManager.deleteById(productAttGroupItem.getProductAttGroupItemId());
			}
		}
		
		//记录没有关联的属性组（待删除）
		List<ProductAttGroup> onDeleteProductAttGroups=new ArrayList<ProductAttGroup>();
		List<ProductAttGroup>productAttGroups=productAttGroupManager.findProductAttGroupsByProductType(productTypeId);
		for (ProductAttGroup productAttGroup : productAttGroups) {
			boolean flag = false;
			for (ProductAttGroup tempProductAttGroup : tempProductAttGroups) {
				if(tempProductAttGroup.getProductAttGroupId().intValue()==0){
					continue;
				}
				if(tempProductAttGroup.getProductAttGroupId().intValue()== productAttGroup.getId().intValue()){
					flag = true;
					break;
				}
			}
			if (!flag) {
				onDeleteProductAttGroups.add(productAttGroup);
			}
		}
		
		
		//更新、新增属性组及属性item信息
		for (ProductAttGroup tempProductAttGroup : tempProductAttGroups) {
			ProductAttGroup productAttGroup=null;
			if(tempProductAttGroup.getProductAttGroupId().intValue()!=0){
				productAttGroup=productAttGroupManager.getById(tempProductAttGroup.getProductAttGroupId());
			}
			if(productAttGroup==null){
				productAttGroup=new ProductAttGroup();
				productAttGroup.setProductTypeId(productTypeId);
			}
			productAttGroup.setProductAttGroupName(tempProductAttGroup.getProductAttGroupName());
			productAttGroup.setSortOrder(tempProductAttGroup.getSortOrder());
			productAttGroupManager.save(productAttGroup);
			//添加属性Item到属性值
			addItemToAttGroup(productAttGroup,tempProductAttGroup.getProductAttGroupItems());
		}
		//删除没有关联的属性组
		for (ProductAttGroup onDeleteProductAttGroup : onDeleteProductAttGroups) {
			productAttGroupManager.deleteById(onDeleteProductAttGroup.getId());
		}
	}
	
	/**
	 * 对自定义属性组更新其属性Item
	 * @param productAttGroup  自定义属性组
	 * @param tempProductAttGroupItems 包含的Item
	 */
	private void addItemToAttGroup(ProductAttGroup productAttGroup,Set<ProductAttGroupItem>tempProductAttGroupItems){
		for (ProductAttGroupItem tempProductAttGroupItem : tempProductAttGroupItems) {
			ProductAttGroupItem productAttGroupItem=null;
			if(tempProductAttGroupItem.getProductAttGroupItemId()==null||tempProductAttGroupItem.getProductAttGroupItemId().intValue()==0){
				productAttGroupItem=new ProductAttGroupItem();
				productAttGroupItem.setAttributeId(tempProductAttGroupItem.getAttributeId());
				productAttGroupItem.setProductTypeId(tempProductAttGroupItem.getProductTypeId());
			}else{
				productAttGroupItem=productAttGroupItemManager.getById(tempProductAttGroupItem.getProductAttGroupItemId());
			}
			productAttGroupItem.setProductAttGroup(productAttGroup);
			productAttGroupItem.setSortOrder(tempProductAttGroupItem.getSortOrder());
			productAttGroupItemManager.save(productAttGroupItem);
		}
	}

	/**
	 * 更新保存产品评论项
	 * @param productType
	 * @param rateItemNames 评论项名
	 * @param rateItemIds 评论项Id，0表示新增
	 */
	private void saveProductTypeRateItems(ProductType productType,String rateItemNames[],String[] rateItemIds) {
		/**
		 * 评论项名=rateItemNames （独立Input）
		 * 属性组id=rateItemIds （独立Input） 0表示新增，否则为评论项Id
		 */
		Set<ProductRateItem> productRateItems = productType.getProductRateItems();
		//删除没有关联的评论项
		for (ProductRateItem productRateItem : productRateItems) {
			if(!ArrayUtils.contains(rateItemIds, productRateItem.getProductRateItemId().toString())){
				productRateItemManager.deleteById(productRateItem.getProductRateItemId());
			}
		}
		//更新/新增评论项
		for (int i = 0; i < rateItemIds.length; i++) {
			Integer rateItemId = Integer.valueOf(rateItemIds[i]);
			ProductRateItem productRateItem = null;
			if(rateItemId!=0){
				productRateItem=productRateItemManager.getById(rateItemId);
			}
			if(productRateItem==null){
				productRateItem= new ProductRateItem();
			}
			productRateItem.setRateName(rateItemNames[i]);
			productRateItem.setSortOrder(i);
			productRateItem.setProductType(productType);
			productRateItemManager.save(productRateItem);
		}
	}

	public void setProductAttGroupManager(ProductAttGroupManager productAttGroupManager) {
		this.productAttGroupManager = productAttGroupManager;
	}

	public void setProductAttGroupItemManager(ProductAttGroupItemManager productAttGroupItemManager) {
		this.productAttGroupItemManager = productAttGroupItemManager;
	}

	public void setProductRateItemManager(ProductRateItemManager productRateItemManager) {
		this.productRateItemManager = productRateItemManager;
	}

	public List<Attribute> findAttributesExcludeRefProductType(Integer ProductTypeId) {
		List<Attribute> productAllAttributes=attributeService.getAttributes(com.cartmatic.estore.attribute.Constants.MODULE_PRODUCT.shortValue());
		List<ProductAttGroupItem>productAttGroupItems=productAttGroupItemManager.findProductAttGroupItemsByProductTypeId(ProductTypeId);
		for (ProductAttGroupItem productAttGroupItem : productAttGroupItems) {
			if(productAllAttributes.contains(productAttGroupItem.getAttribute())){
				productAllAttributes.remove(productAttGroupItem.getAttribute());
			}
		}
		return productAllAttributes;
	}

	/**
	 * 更新保存产品类型关联SkuOption
	 * @param productType
	 * @param skuOptionIds
	 */
	private void saveProductTypeSkuOptions(ProductType productType,String[] skuOptionIds) {
		Set<ProductTypeSkuOption>productTypeSkuOptions=productType.getProductTypeSkuOptions();
		//删除没有关联的SkuOption
		for (ProductTypeSkuOption productTypeSkuOption : productTypeSkuOptions) {
			if(!ArrayUtils.contains(skuOptionIds, productTypeSkuOption.getSkuOption().getSkuOptionId().toString())){
				productTypeSkuOptionManager.deleteById(productTypeSkuOption.getProductTypeSkuOptionId());
			}
		}
		productAttGroupItemManager.flush();
		//更新/新增SkuOption
		for (int i = 0; i < skuOptionIds.length; i++) {
			String skuOptionId = skuOptionIds[i];
			ProductTypeSkuOption tempProductTypeSkuOption=null;
			for (ProductTypeSkuOption productTypeSkuOption : productTypeSkuOptions) {
				tempProductTypeSkuOption=null;
				if(productTypeSkuOption.getSkuOption().getSkuOptionId().toString().equals(skuOptionId)){
					tempProductTypeSkuOption=productTypeSkuOption;
					break;
				}
			}
			if(tempProductTypeSkuOption==null){
				tempProductTypeSkuOption=new ProductTypeSkuOption();
				tempProductTypeSkuOption.setSkuOptionId(Integer.valueOf(skuOptionId));
				tempProductTypeSkuOption.setProductType(productType);
			}
			tempProductTypeSkuOption.setSortOrder(i);
			productTypeSkuOptionManager.save(tempProductTypeSkuOption);
		}
	}

	public ProductType getProductTypeByName(String productTypeName) {
		ProductType productType=dao.findUniqueByProperty("productTypeName", productTypeName);
		return productType;
	}

	public List<ProductType> findActiveSkuOptionsProductType() {
		return productTypeSkuOptionManager.findActiveSkuOptionsProductType();
	}

	public void saveProductTypeAction(ProductType productType, String[] attGroupIds, String[] attGroupNames, String[] attGroupItems, String[] skuOptionIds, String[] rateItemNames, String[] rateItemIds) {
		//保存更新产品类型信息
		save(productType);
		//保存更新产品类型关联的属性
		saveProductTypeAttributes(productType.getId(), attGroupNames, attGroupIds, attGroupItems);
		//保存更新产品类型关联SkuOption
		saveProductTypeSkuOptions(productType, skuOptionIds);
		//保存更新产品类型关联的评论项
		saveProductTypeRateItems(productType, rateItemNames, rateItemIds);
	}
}

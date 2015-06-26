package com.cartmatic.estore.catalog.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.CategoryDao;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

/**
 * Category Business Delegate (Proxy) implementation to handle communication between web and
 * persistence layer. Implementation of CategoryManager interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public class CategoryManagerImpl extends GenericManagerImpl<Category> implements CategoryManager {
	
	private CategoryDao categoryDao = null;
	
    private ProductCategoryManager productCategoryManager=null;
	private AttributeService attributeService=null;
	
	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}


	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}


	public void setProductCategoryManager(
			ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}	
   
    
    protected ProductCategoryManager getProductCategoryManager() {
		return productCategoryManager;
	}

    
    /**
	 * 获取指定目录下的所有下级目录中的最大排序值
	 * @param categoryId  目录ID
	 * @return
	 * @since 3.0
	 */
	public int getMaxSortOrder(Integer categoryId){
		Assert.notNull(categoryId);
		return this.categoryDao.getMaxSortOrder(new Integer(categoryId.toString()));
	}
	
	/**
	 * 统计指定目录下的子目录数
	 * @param categoryId 目录ID
	 * @since 3.0
	 */
	public Integer countSubCategoryById(Serializable categoryId){
		Assert.notNull(categoryId);
		return categoryDao.countSubCategoryById(new Integer(categoryId.toString()));
	}
	
    @Override
	protected void initManager() {
		dao = this.categoryDao;
	}
    
    @Override
	protected void onDelete(Category entity) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	protected void onSave(Category entity) {
		// TODO Auto-generated method stub
		
	}

	public void saveCategorySortOrder(Integer[] categoryIds) {
		for (int i = 0; i < categoryIds.length; i++) {
			Integer categoryId = categoryIds[i];
			Category category=getById(categoryId);
			if(category==null)continue;
			category.setSortOrder(i+1);
			save(category);
		}
	}

	public void saveCategoryAction(Category category,List<AttributeValue> attributeValues) {
		boolean isNew=category.getId()==null;
		if(category.getCategoryId()==null){
			Category parentCategory=getById(category.getCategory().getCategoryId());
			category.setCatalog(parentCategory.getCatalog());
			category.setStore(parentCategory.getStore());
			//设置CateogryPath
			String parentCategoryPath=parentCategory.getCategoryPath();
			parentCategoryPath=parentCategoryPath==null?"":parentCategoryPath;
			category.setCategoryPath(parentCategoryPath+parentCategory.getCategoryId()+".");
			//设置最大SortOrder
			if(category.getSortOrder()==null){
				Integer maxSortOrder=getMaxSortOrder(category.getCategory().getCategoryId());
				category.setSortOrder(maxSortOrder);
			}
			
		}
		save(category);
		//更新目录自定义属性
		if(attributeValues!=null){
			attributeService.saveOrUpdateAttributeValue(attributeValues, category);
		}
		
		//同步链接目录产品
		if(category.getCategoryType().intValue()==Constants.CATEGORY_TYPE_CATALOG&&category.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.intValue()){
			Integer oldLinkedCategoryId=category.getOldLinkedCategoryId();
			if(oldLinkedCategoryId!=null&&(category.getLinkedCategoryId()==null||oldLinkedCategoryId.compareTo(category.getLinkedCategoryId())!=0)){
				//删除关联产品目录
				List<ProductCategory>oldProductCategoryList=productCategoryManager.findByProperty("category.categoryId", oldLinkedCategoryId);
				for (ProductCategory productCategory : oldProductCategoryList){
					if(productCategory.getIsMainCategory().intValue()==Constants.FLAG_TRUE){
						Set<ProductCategory> allProductCategory=productCategory.getProduct().getProductCategorys();
						for (ProductCategory productCategory2 : allProductCategory){
							if(productCategory2.getCategory().getCatalogId().intValue()==category.getCatalogId()){
								productCategoryManager.deleteById(productCategory2.getProductCategoryId());
							}
						}
					}else{
						productCategoryManager.deleteById(productCategory.getProductCategoryId());
					}
				}

			}
			if(category.getLinkedCategoryId()!=null&&(oldLinkedCategoryId==null||oldLinkedCategoryId.compareTo(category.getLinkedCategoryId())!=0)){
				addLinkedCategoryProduct(category);
				if(isNew&&category.getIsCreateSubLinkedCategory()!=null&&category.getIsCreateSubLinkedCategory().intValue()==Constants.FLAG_TRUE){
					Category linkedCategory=getById(category.getLinkedCategoryId());
					Set<Category>linkedCategoryAllChildren=linkedCategory.getCategorys();
					for (Category linkedCategoryChild : linkedCategoryAllChildren)
					{
						Category newCategory=new Category();
						newCategory.setCategoryName(linkedCategoryChild.getCategoryName());
						newCategory.setCategoryCode(linkedCategoryChild.getCategoryCode());
						newCategory.setCategoryType(Constants.CATEGORY_TYPE_CATALOG);
						newCategory.setCatalog(category.getCatalog());
						Category parentCategory=category;
						newCategory.setCategory(parentCategory);
						newCategory.setStatus(Constants.STATUS_ACTIVE);
						newCategory.setLinkedCategory(linkedCategoryChild);
						newCategory.setIsCreateSubLinkedCategory(category.getIsCreateSubLinkedCategory());
						newCategory.setSortOrder(linkedCategoryChild.getSortOrder());
						
						Set<Category> subCategories=category.getCategorys();
						if(subCategories==null){
							subCategories=new HashSet<Category>();
							category.setCategorys(subCategories);
						}
						subCategories.add(newCategory);
						
						saveCategoryAction(newCategory,null);
						
					}
				}
			}
		}
	}
	
	/**
	 * 添加链接目录产品
	 * @param category
	 */
	private void addLinkedCategoryProduct(Category category){
		List<ProductCategory>productCategoryList=productCategoryManager.findByProperty("category.categoryId", category.getLinkedCategoryId());
		for (ProductCategory productCategory : productCategoryList){
			ProductCategory newProductCategory=new ProductCategory();
			newProductCategory.setCategory(category);
			newProductCategory.setProduct(productCategory.getProduct());
			newProductCategory.setCategoryPath((StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + productCategory.getProduct().getProductId());
			newProductCategory.setSortOrder(productCategory.getSortOrder());
			Short isMainCategory=Constants.FLAG_TRUE;
			Set<ProductCategory> allProductCategory=productCategory.getProduct().getProductCategorys();
			for (ProductCategory productCategory2 : allProductCategory){
				if(productCategory2.getCategory().getCatalogId().intValue()==category.getCatalogId()){
					isMainCategory=Constants.FLAG_FALSE;
					break;
				}
			}
			newProductCategory.setIsMainCategory(isMainCategory);
			
			//检查该目录是否已经存在该产品
			List<ProductCategory> tempProductCateogryList=productCategoryManager.findByProperty("categoryPath", newProductCategory.getCategoryPath());
			if(tempProductCateogryList==null||tempProductCateogryList.size()==0){
				productCategoryManager.save(newProductCategory);
			}
		}
	}
	
	public Category getActiveCategoryById(Integer categoryId) {
		Category category=categoryDao.getById(categoryId);
		if(category==null||category.getStatus().intValue()!=Constants.STATUS_ACTIVE){
			return null;
		}
		return category;
	}

	public boolean getIsHasActiveSubCategory(Integer categoryId) {
		return categoryDao.getIsHasActiveSubCategory(categoryId);
	}

	public List<Category> findCategoryByParentIdAndName(Integer parentCategoryId, String categoryName) {
		return categoryDao.findCategoryByParentIdAndName(parentCategoryId, categoryName);
	}
	
	public List<Category> findProductCategoryByName(Integer catalogId,String categoryName){
		return categoryDao.findProductCategoryByName(catalogId, categoryName);
	}
	public List<Category> findContentCategoryByName(Integer storegId,String categoryName){
		return categoryDao.findContentCategoryByName(storegId, categoryName);
	}


	@Override
	public List<CategoryTreeItem> getCatalogTreeItemList() {
		return categoryDao.getCatalogTreeItemList();
	}
	
	@Override
	public boolean isContainSubCategoriesOrProducts(Integer categoryId) {
		Integer subCount = countSubCategoryById(categoryId);
		if (subCount == null || subCount.intValue() == 0)
			subCount = productCategoryManager.getNotDeletedProductCountByCategory(categoryId);
		if (subCount != null && subCount.intValue() > 0)
			return true;
		return false; 
	}


	@Override
	public Category getProductCategoryByCode(Integer catalogId, String categoryCode) {
		return categoryDao.getProductCategoryByCode(catalogId, categoryCode);
	}
	
	@Override
	public Category getContentCategoryByCode(Integer storeId, String categoryCode) {
		return categoryDao.getContentCategoryByCode(storeId,categoryCode);
	}


	@Override
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId, Integer virtual) {
		return categoryDao.getCatalogTreeItemList(catalogId, virtual,null);
	}	
	@Override
	public List<CategoryTreeItem> getCatalogTreeItemList4Front(Integer catalogId) {
		Assert.notNull(catalogId);
		CatalogHelper catalogHelper=CatalogHelper.getInstance();
		List<CategoryTreeItem> categoryTreeItems=categoryDao.getCatalogTreeItemList(catalogId,null,Constants.STATUS_ACTIVE,true);
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			//获取目录访问的URI
			String url=catalogHelper.getProductCategoryUrl(categoryTreeItem);
			categoryTreeItem.setUrl(url);
		}
		return categoryTreeItems;
	}

	@Override
	public Integer getLinkedCategoryId(Integer categoryId) {
		return categoryDao.getLinkedCategoryId(categoryId);
	}


	@Override
	public List<Category> getCategoryListByLinkedCategory(Integer linkedCategoryId) {
		return categoryDao.findByProperty("linkedCategory.categoryId", linkedCategoryId);
	}


	@Override
	public List<CategoryTreeItem> getContentTreeItemList() {
		return categoryDao.getContentTreeItemList();
	}


	@Override
	public List<CategoryTreeItem> getContentTreeItemList(Integer storeId) {
		return categoryDao.getContentTreeItemList(storeId,null);
	}


	@Override
	public List<CategoryTreeItem> getContentTreeItemList4Front(Integer storeId) {
		Assert.notNull(storeId);
		CatalogHelper catalogHelper=CatalogHelper.getInstance();
		List<CategoryTreeItem> categoryTreeItems=categoryDao.getContentTreeItemList(storeId,Constants.STATUS_ACTIVE);
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			//获取目录访问的URI
			if(Constants.FLAG_TRUE.equals(categoryTreeItem.getIsLinkCategory())){
				categoryTreeItem.setUrl(categoryTreeItem.getLinkUrl());
			}else{
				String url=catalogHelper.getContentCategoryUri(categoryTreeItem);
				categoryTreeItem.setUrl(url);
			}
		}
		return categoryTreeItems;
	}


	@Override
	public List<CategoryTreeItem> getActionCatalogTreeItemList(Integer catalogId) {
		Assert.notNull(catalogId);
		List<CategoryTreeItem> categoryTreeItems=categoryDao.getCatalogTreeItemList(catalogId,null,Constants.STATUS_ACTIVE,false);
		return categoryTreeItems;
	}

}
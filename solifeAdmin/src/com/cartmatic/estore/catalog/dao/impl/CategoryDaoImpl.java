package com.cartmatic.estore.catalog.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.CatalogDao;
import com.cartmatic.estore.catalog.dao.CategoryDao;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.StoreDao;

/**
 * Category Data Access Object (DAO) implementation. Developer introduced
 * interfaces should be declared here. Won't get overwritten.
 */
public class CategoryDaoImpl extends HibernateGenericDaoImpl<Category> implements CategoryDao {
	private CatalogDao catalogDao=null;
	
	private StoreDao storeDao=null;
	
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	/**
	 * 获取指定目录下的所有下级目录中的最大排序值
	 * 
	 * @param categoryId 目录ID
	 * @return
	 * @since 3.0
	 */
	public int getMaxSortOrder(Integer categoryId) {
		String hql = "select max(c.sortOrder) from Category c where c.category.categoryId=?";
		Object obj = this.findUnique(hql, new Object[] { categoryId });
		int maxSortOrder = 0;
		if (obj != null)
			maxSortOrder = Integer.parseInt(obj.toString());

		return maxSortOrder;
	}

	/**
	 * 统计指定目录下的子目录数
	 * 
	 * @param categoryId
	 *            目录ID
	 * @since 3.0
	 */
	public Integer countSubCategoryById(Integer categoryId) {
		String hql = "select count(c.categoryId) from Category c where c.category.categoryId=?";
		Long subCount = (Long) this.findUnique(hql, categoryId);
		if (subCount == null)
			subCount = 0L;
		return new Integer(subCount.toString());
	}

	public boolean getIsHasActiveSubCategory(Integer categoryId) {
		Long count = this.countByHql("from Category c where c.category.categoryId=? and c.status=1", categoryId);
		return count.intValue() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<Category> findCategoryByParentIdAndName(Integer parentCategoryId, String categoryName) {
		String hql = "from Category c where c.category.categoryId=? and c.categoryName=?  order by c.sortOrder, c.categoryId";
		List<Category> categoryList = findByHql(hql, new Object[] { parentCategoryId, categoryName });
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Category> findProductCategoryByName(Integer catalogId,String categoryName) {
		String hql = "from Category c where c.catalog.catalogId=? and c.categoryName=?  order by c.sortOrder, c.categoryId";
		List<Category> categoryList = findByHql(hql, new Object[] { catalogId, categoryName });
		return categoryList;
	}
	
	public List<Category> findContentCategoryByName(Integer storegId,String categoryName) {
		String hql = "from Category c where c.store.storeId=? and c.categoryName=?  order by c.sortOrder, c.categoryId";
		List<Category> categoryList = findByHql(hql, new Object[] { storegId, categoryName });
		return categoryList;
	}
	
	public List<CategoryTreeItem> getCatalogTreeItemList(){
		List<CategoryTreeItem> categoryTreeItemList=new ArrayList<CategoryTreeItem>();
		getCatalogTreeItemList(null,null,0,new CategoryTreeItem[]{null},null,categoryTreeItemList,null,null);
		return categoryTreeItemList;
	}
	
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId,Integer virtual,Short status){
		return getCatalogTreeItemList(catalogId,virtual,status,null);
	}
	
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId,Integer virtual,Short status,Boolean isIncludeRoot){
		List<CategoryTreeItem> categoryTreeItemList=new ArrayList<CategoryTreeItem>();
		String statusWcl=null;
		if(virtual!=null&&virtual.intValue()==1){
			statusWcl="c.catalog.isVirtual=1 ";
		}else if(virtual!=null&&virtual.intValue()==0){
			statusWcl="c.catalog.isVirtual=0 ";
		}
		if(status!=null){
			if(StringUtils.isNotBlank(statusWcl)){
				statusWcl="and "+statusWcl;
			}
			statusWcl="c.status="+status.intValue()+" ";
		}
		Catalog catalog=null;
		if(catalogId!=null){
			catalog=catalogDao.getById(catalogId);
		}
		if(catalog!=null){
			getCatalogTreeItemList(catalogId,catalog.getCategoryId(),0,new CategoryTreeItem[]{null},null,categoryTreeItemList,statusWcl,isIncludeRoot);
		}else{
			getCatalogTreeItemList(null,null,0,new CategoryTreeItem[]{null},null,categoryTreeItemList,statusWcl,null);
		}
		setMaxLevel(categoryTreeItemList);
		return categoryTreeItemList;
	}
	
	private void setMaxLevel(List<CategoryTreeItem> categoryTreeItemList){
		for (CategoryTreeItem categoryTreeItem : categoryTreeItemList)
		{
			if(!categoryTreeItem.isHasChildren()){
				categoryTreeItem.setMaxLevel(categoryTreeItem.getLevel());
				CategoryTreeItem parentItem=(CategoryTreeItem) categoryTreeItem.getCategory();
				while (parentItem!=null)
				{
					int maxLevel=parentItem.getMaxLevel();
					if(categoryTreeItem.getMaxLevel()-maxLevel>0){
						parentItem.setMaxLevel(categoryTreeItem.getMaxLevel());
						parentItem=(CategoryTreeItem)parentItem.getCategory();
					}else{
						break;
					}
				}
			}
		}
	}
	
	private void getCatalogTreeItemList(Integer catalogId,Integer pCatId,Integer level,CategoryTreeItem[] preCategoryTreeItem_t,CategoryTreeItem parentCategoryTreeItem_t,List<CategoryTreeItem> categoryTreeItemList, String statusWcl,Boolean isRoot){
		CategoryTreeItem preCategoryTreeItem = preCategoryTreeItem_t[0];
		CategoryTreeItem parentCategoryTreeItem=parentCategoryTreeItem_t;
		level++;
		StringBuffer hql = new StringBuffer("SELECT c.catalog,c.categoryId,c.categoryName,c.categoryCode,c.categoryPath,c.linkedCategory.categoryId,c.imageUrl,c.metaKeyword,c.metaDescription,c.categoryDescription,c.templatePath FROM Category c WHERE c.categoryType=? ");
		if (StringUtils.isNotEmpty(statusWcl)) {
			hql.append(" and " + statusWcl);
		}
		List tempList =null;
		if(catalogId==null){
			hql.append(" and c.category.categoryId is null order by c.catalog.catalogId");
			tempList = findByHql(hql.toString(), new Object[] {Constants.CATEGORY_TYPE_CATALOG});
		}else if(isRoot!=null&&isRoot){
			hql.append(" and c.category.categoryId is null and c.catalog.catalogId=? order by c.catalog.catalogId");
			tempList = findByHql(hql.toString(), new Object[] {Constants.CATEGORY_TYPE_CATALOG,catalogId});
		}else{
			hql.append(" and c.catalog.catalogId=? and c.category.categoryId=? ORDER BY c.sortOrder");
			tempList = findByHql(hql.toString(), new Object[] {Constants.CATEGORY_TYPE_CATALOG,catalogId, pCatId });
		}
		Integer parentItemsCount = tempList.size();
		if (preCategoryTreeItem != null) {
			preCategoryTreeItem.setHasChildren(parentItemsCount > 0);
			preCategoryTreeItem.setItemCount(parentItemsCount);
			if (preCategoryTreeItem.isParentLastChild() && (!preCategoryTreeItem.isHasChildren())) {
				preCategoryTreeItem.setNextDifference(preCategoryTreeItem.getLevel() - 1);
			}
		}
		for (int i = 0; i < parentItemsCount; i++) {
			Object[] array = (Object[]) tempList.get(i);
			Catalog catalog = (Catalog) array[0];
			Integer categoryId = (Integer) array[1];
			String categoryName = (String) array[2];
			String categoryCode = (String) array[3];
			String categoryPath = (String) array[4];
			Integer linkedCategoryId = (Integer) array[5];
			String imageUrl=(String) array[6];
			String metaKeyword=(String) array[7];
			String metaDescription=(String) array[8];
			String categoryDescription=(String) array[9];
			String templatePath=(String)array[10];
			
			CategoryTreeItem categorTreeItem = new CategoryTreeItem();
			Catalog catalogItem =new Catalog();
			catalogItem.setCatalogId(catalog.getId());
			catalogItem.setIsVirtual(catalog.getIsVirtual());
			categorTreeItem.setCatalog(catalogItem);
			categorTreeItem.setCategoryId(categoryId);
			categorTreeItem.setCategoryName(categoryName);
			categorTreeItem.setCategoryCode(categoryCode);
			categorTreeItem.setCategoryPath(categoryPath);
			categorTreeItem.setLinkedCategory(linkedCategoryId==null?null:new Category(linkedCategoryId));
			
			categorTreeItem.setImageUrl(imageUrl);
			categorTreeItem.setMetaKeyword(metaKeyword);
			categorTreeItem.setMetaDescription(metaDescription);
			categorTreeItem.setCategoryDescription(categoryDescription);
			categorTreeItem.setTemplatePath(templatePath);
			
			categorTreeItem.setParentFirstChild(i == 0);
			categorTreeItem.setParentLastChild(i == parentItemsCount - 1);
			categorTreeItem.setLevel(level);
			categorTreeItem.setIsCatalog(catalog.getCategoryId()==categoryId.intValue()?Constants.FLAG_TRUE:Constants.FLAG_FALSE);
			if(catalogId==null){
				categorTreeItem.setCategoryName(catalog.getName());
				categorTreeItem.setCategoryCode(catalog.getCode());
			}
			
			preCategoryTreeItem = preCategoryTreeItem_t[0];
			if (preCategoryTreeItem != null && preCategoryTreeItem.isParentLastChild() && (!preCategoryTreeItem.isHasChildren())) {
				preCategoryTreeItem.setNextDifference(preCategoryTreeItem.getLevel() - categorTreeItem.getLevel());
			}
			preCategoryTreeItem_t[0] = categorTreeItem;
			categoryTreeItemList.add(categorTreeItem);
			
			//添加到父目录
			if(parentCategoryTreeItem!=null){
				if(parentCategoryTreeItem.getCategorys().size()==0)parentCategoryTreeItem.setCategorys(new LinkedHashSet<Category>());
				parentCategoryTreeItem.getCategorys().add(categorTreeItem);
				categorTreeItem.setCategory(parentCategoryTreeItem);
			}
			parentCategoryTreeItem_t=categorTreeItem;
			
			getCatalogTreeItemList(catalog.getId(),categoryId, level, preCategoryTreeItem_t,parentCategoryTreeItem_t,categoryTreeItemList,statusWcl,false);
		}
		level--;
	}
	
	public List<CategoryTreeItem> getContentTreeItemList(){
		List<CategoryTreeItem> categoryTreeItemList=new ArrayList<CategoryTreeItem>();
		getContentTreeItemList(null,null,0,new CategoryTreeItem[]{null},null,categoryTreeItemList,null);
		return categoryTreeItemList;
	}
	
	
	
	@Override
	public List<CategoryTreeItem> getContentTreeItemList(Integer storeId,Short status) {
		List<CategoryTreeItem> categoryTreeItemList=new ArrayList<CategoryTreeItem>();
		String statusWcl=null;
		if(status!=null){
			if(StringUtils.isNotBlank(statusWcl)){
				statusWcl="and "+statusWcl;
			}
			statusWcl="c.status="+status.intValue()+" ";
		}
		Store store=null;
		if(storeId!=null){
			store=storeDao.getById(storeId);
		}
		if(store!=null){
			getContentTreeItemList(storeId,store.getCategoryId(),0,new CategoryTreeItem[]{null},null,categoryTreeItemList,statusWcl);
		}else{
			getContentTreeItemList(null,null,0,new CategoryTreeItem[]{null},null,categoryTreeItemList,statusWcl);
		}
		return categoryTreeItemList;
	}
	
	private void getContentTreeItemList(Integer storeId,Integer pCatId,Integer level,CategoryTreeItem[] preCategoryTreeItem_t,CategoryTreeItem parentCategoryTreeItem_t,List<CategoryTreeItem> categoryTreeItemList, String statusWcl){
		CategoryTreeItem preCategoryTreeItem = preCategoryTreeItem_t[0];
		CategoryTreeItem parentCategoryTreeItem=parentCategoryTreeItem_t;
		level++;
		StringBuffer hql = new StringBuffer("SELECT c.store,c.categoryId,c.categoryName,c.categoryCode,c.categoryPath,c.isLinkCategory,c.linkUrl FROM Category c WHERE c.categoryType=? ");
		if (StringUtils.isNotEmpty(statusWcl)) {
			hql.append(" and " + statusWcl);
		}
		List tempList =null;
		if(storeId==null){
			hql.append(" and c.category.categoryId is null order by c.store.storeId");
			tempList = findByHql(hql.toString(), new Object[] {Constants.CATEGORY_TYPE_CONTENT});
		}else{
			hql.append(" and c.store.storeId=? and c.category.categoryId=? ORDER BY c.sortOrder");
			tempList = findByHql(hql.toString(), new Object[] {Constants.CATEGORY_TYPE_CONTENT,storeId, pCatId });
		}
		Integer parentItemsCount = tempList.size();
		if (preCategoryTreeItem != null) {
			preCategoryTreeItem.setHasChildren(parentItemsCount > 0);
			preCategoryTreeItem.setItemCount(parentItemsCount);
			if (preCategoryTreeItem.isParentLastChild() && (!preCategoryTreeItem.isHasChildren())) {
				preCategoryTreeItem.setNextDifference(preCategoryTreeItem.getLevel() - 1);
			}
		}
		for (int i = 0; i < parentItemsCount; i++) {
			Object[] array = (Object[]) tempList.get(i);
			Store store = (Store) array[0];
			Integer categoryId = (Integer) array[1];
			String categoryName = (String) array[2];
			String categoryCode = (String) array[3];
			String categoryPath = (String) array[4];
			Short isLinkCategory=(Short)array[5];
			String linkUrl = (String) array[6];
			
			CategoryTreeItem categorTreeItem = new CategoryTreeItem();
			Store storeItem =new Store();
			store.setStoreId(store.getId());
			categorTreeItem.setStore(storeItem);
			categorTreeItem.setCategoryId(categoryId);
			categorTreeItem.setCategoryName(categoryName);
			categorTreeItem.setCategoryCode(categoryCode);
			categorTreeItem.setCategoryPath(categoryPath);
			categorTreeItem.setIsLinkCategory(isLinkCategory);
			categorTreeItem.setLinkUrl(linkUrl);
			categorTreeItem.setParentFirstChild(i == 0);
			categorTreeItem.setParentLastChild(i == parentItemsCount - 1);
			categorTreeItem.setLevel(level);
			categorTreeItem.setIsFalseEntity(store.getCategoryId()==categoryId.intValue()?Constants.FLAG_TRUE:Constants.FLAG_FALSE);
			if(storeId==null){
				categorTreeItem.setCategoryName(store.getName());
				categorTreeItem.setCategoryCode(store.getCode());
			}
			
			preCategoryTreeItem = preCategoryTreeItem_t[0];
			if (preCategoryTreeItem != null && preCategoryTreeItem.isParentLastChild() && (!preCategoryTreeItem.isHasChildren())) {
				preCategoryTreeItem.setNextDifference(preCategoryTreeItem.getLevel() - categorTreeItem.getLevel());
			}
			preCategoryTreeItem_t[0] = categorTreeItem;
			categoryTreeItemList.add(categorTreeItem);
			
			//添加到父目录
			if(parentCategoryTreeItem!=null){
				if(parentCategoryTreeItem.getCategorys().size()==0)parentCategoryTreeItem.setCategorys(new LinkedHashSet<Category>());
				parentCategoryTreeItem.getCategorys().add(categorTreeItem);
				categorTreeItem.setCategory(parentCategoryTreeItem);
			}
			parentCategoryTreeItem_t=categorTreeItem;
			getContentTreeItemList(store.getId(),categoryId, level, preCategoryTreeItem_t,parentCategoryTreeItem_t,categoryTreeItemList,statusWcl);
		}
		level--;
	}

	@Override
	public Category getProductCategoryByCode(Integer catalogId, String categoryCode) {
		return (Category) this.findUnique("from Category c where c.categoryType=? and c.catalog.catalogId=? and c.categoryCode=?", new Object[] { Constants.CATEGORY_TYPE_CATALOG, catalogId,categoryCode });
	}
	
	@Override
	public Category getContentCategoryByCode(Integer storeId, String categoryCode) {
		return (Category) this.findUnique("from Category c where c.categoryType=? and c.store.storeId=? and c.categoryCode=?", new Object[] { Constants.CATEGORY_TYPE_CONTENT, storeId,categoryCode });
	}

	@Override
	public Integer getLinkedCategoryId(Integer categoryId) {
		return (Integer)findUnique("select c.linkedCategory.categoryId from Category c where c.categoryType=? and c.categoryId=?", new Object[] { Constants.CATEGORY_TYPE_CATALOG, categoryId});
	}


	

}
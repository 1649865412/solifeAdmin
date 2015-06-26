package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Category Data Access Object (DAO) interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public interface CategoryDao extends GenericDao<Category> {
	
	/**
	 * 获取指定目录下的所有下级目录中的最大排序值
	 * @param categoryId  目录ID
	 * @return
	 * @since 3.0
	 */
	public int getMaxSortOrder(Integer categoryId);
	
	
	/**
	 * 统计指定目录下的子目录数
	 * @param categoryId 目录ID
	 * @since 3.0
	 */
	public Integer countSubCategoryById(Integer categoryId);
	

	/**
	 * 根据编码获取商品分类
	 * @param catalogId
	 * @param categoryCode
	 * @return
	 */
	public Category getProductCategoryByCode(Integer catalogId,String categoryCode);
	
	
	/**
	 * 根据编码获取内容目录
	 * @param storeId
	 * @param categoryCode
	 * @return
	 */
	public Category getContentCategoryByCode(Integer storeId,String categoryCode);
	/**
	 * 检查目录是否存在激活的子目录
	 * @param categoryId
	 * @return
	 */
	public boolean getIsHasActiveSubCategory(Integer categoryId);
	
	
	/**
	 * 在指定父目录下的查找x名的目录
	 * @return
	 */
	public List<Category> findCategoryByParentIdAndName(Integer parentCategoryId,String categoryName);
	
	
	/**
	 * 目录名称查找目录
	 * @param catalogId
	 * @param categoryName
	 * @return
	 */
	public List<Category> findProductCategoryByName(Integer catalogId,String categoryName);
	/**
	 * 目录名称查找目录
	 * @param catalogId
	 * @param categoryName
	 * @return
	 */
	public List<Category> findContentCategoryByName(Integer storeId,String categoryName);
	
	/**
	 * 获取商品目录树
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList();
	
	/**
	 * 获取商品目录树
	 * @param catalogId
	 * @param virtual
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId,Integer virtual,Short status);
	
	/**
	 * 获取商品目录树
	 * @param catalogId
	 * @param virtual
	 * @param status
	 * @param isIncludeRoot 是否包含跟目录,即指定的catalog
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId,Integer virtual,Short status,Boolean isIncludeRoot);
	
	/**
	 * 获取要链接到目录Id
	 * @param categoryId
	 * @return
	 */
	public Integer getLinkedCategoryId(Integer categoryId);
	
	
	/**
	 * 获取内容目录树
	 * @return
	 */
	public List<CategoryTreeItem> getContentTreeItemList();
	
	/**
	 * 获取内容目录树
	 * @return
	 */
	public List<CategoryTreeItem> getContentTreeItemList(Integer storeId,Short status);
	
	
}

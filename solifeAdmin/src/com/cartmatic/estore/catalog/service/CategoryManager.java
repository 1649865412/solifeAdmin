package com.cartmatic.estore.catalog.service;

import java.io.Serializable;
import java.util.List;

import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.core.service.GenericManager;


/**
 * 目录模块服务层接口，供产品目录模块与内容目录模块共用
 * 
 * 注：接口定义中，如果是内容目录模块的操作一般带有content前缀，产品目录不会带有product前缀，如不会出现productCategory字样。
 * 
 * @since 2.0
 */
public interface CategoryManager extends GenericManager<Category>{
	
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
	public Integer countSubCategoryById(Serializable categoryId);

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
	 * 按categoryIds的顺序更新目录sortOrder
	 * @param categoryIds
	 */
	public void saveCategorySortOrder(Integer categoryIds[]);
	
	/**
	 * 保存目录，新增是自动设置SortOrder为最大值，不变更父目录
	 * @param category
	 * @param attributeValues
	 */
	public void saveCategoryAction(Category category,List<AttributeValue> attributeValues);
	
	
	/**
	 * 获取激活的目录
	 * @param categoryId
	 * @return
	 */
	public Category getActiveCategoryById(Integer categoryId);
	
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
	 * 获取商品目录树(完整)
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList();
	
	
	/**
	 * 获取商品目录树
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList(Integer catalogId,Integer virtual);
	
	
	public List<CategoryTreeItem> getActionCatalogTreeItemList(Integer catalogId);
	
	
	/**
	 * 获取商品目录树
	 * (缓存)
	 * @param catalogId
	 * @return
	 */
	public List<CategoryTreeItem> getCatalogTreeItemList4Front(Integer catalogId);
	
	/**
	 * 获取内容目录树
	 * @return
	 */
	public List<CategoryTreeItem> getContentTreeItemList(Integer storeId);
	
	/**
	 * 获取内容目录树
	 * (缓存)
	 * @return
	 */
	public List<CategoryTreeItem> getContentTreeItemList4Front(Integer storeId);
	
	/**
	 * 检查指定目录是否存在子目录或存在直属产品
	 * @param categoryId
	 * @return
	 */
	public boolean isContainSubCategoriesOrProducts(Integer categoryId);
	
	public Integer getLinkedCategoryId(Integer categoryId);
	
	public List<Category> getCategoryListByLinkedCategory(Integer linkedCategoryId);
	
	/**
	 * 获取内容目录树
	 * @return
	 */
	public List<CategoryTreeItem> getContentTreeItemList();
	
}

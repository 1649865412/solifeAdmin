package com.cartmatic.estore.common.model.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.catalog.base.CategoryTbl;

/**
 * Model - Business object This file won't get overwritten.
 */
public class Category extends CategoryTbl {
	public final static short ISLINKCATEGORY_YES = 1;
	public final static short ISLINKCATEGORY_NO = 0;
	
    private Short isCreateSubLinkedCategory=0;
	
    /**
     * 前台浏览URL
     */
    private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Default Empty Constructor for class Category
	 * 
	 */
	public Category() {
		super();
	}

	
	/**
	 * @return 完整的目录名
	 */
	public String getCategoryPathName() {
		if(getCategoryName()==null)return "";
		StringBuffer categoryPathName=new StringBuffer(getCategoryName());
		Category parentCategory=this.getCategory();
		while (parentCategory!=null&&StringUtils.isNotEmpty(parentCategory.getCategoryPath())) {
			categoryPathName.insert(0, ">");
			categoryPathName.insert(0, parentCategory.getCategoryName());
			parentCategory=parentCategory.getCategory();
		}
		return categoryPathName.toString();
	}
	
	private List<Category> parentCategorys=null;
	
	/**
	 * @return 所有父目录
	 */
	public List<Category> getParentCategorys(){
		if(parentCategorys==null){
			parentCategorys=new ArrayList<Category>();
			Category parentCategory=this.getCategory();
			while (parentCategory!=null&&StringUtils.isNotEmpty(parentCategory.getCategoryPath())) {
				parentCategorys.add(0,parentCategory);
				parentCategory=parentCategory.getCategory();
			}
		}
		return parentCategorys;
	}
	
	public List<Category> getAllParentCategorys(){
		if(parentCategorys==null){
			parentCategorys=new ArrayList<Category>();
			Category parentCategory=this.getCategory();
			while (parentCategory!=null) {
				parentCategorys.add(0,parentCategory);
				parentCategory=parentCategory.getCategory();
			}
		}
		return parentCategorys;
	}
	
	private List<Category> navigatorCategorys=null;
	/**
	 * @return 导航目录（当前目录的所有父目录+当前目录）
	 */
	public List<Category> getNavigatorCategorys(){
		if(navigatorCategorys==null){
			navigatorCategorys=new ArrayList<Category>();
			Category parentCategory=this.getCategory();
			while (parentCategory!=null&&StringUtils.isNotEmpty(parentCategory.getCategoryPath())) {
				navigatorCategorys.add(0,parentCategory);
				parentCategory=parentCategory.getCategory();
			}
			navigatorCategorys.add(this);
		}
		return navigatorCategorys;
	}
	
	private List<Category> allChildren=null;
	
	/**
	 * @return 所有子目录，按递归查找顺序
	 */
	@SuppressWarnings("unchecked")
	public List<Category> getAllChildren(){
		if(allChildren==null){
			allChildren=new ArrayList<Category>();
			Set<Category> categorys=getCategorys();
			addChildrenToList(allChildren, categorys);
		}
		return allChildren;
	}
	
	private void addChildrenToList(List<Category> childten,Set<Category>categorys){
		for (Category category : categorys) {
			childten.add(category);
			addChildrenToList(childten, category.categorys);
		}
	}
	
	public String getFullCategoryPath(){
		if(getCategoryPath()==null){
			return null;
		}
		StringBuffer fullCategoryPath=new StringBuffer(getCategoryPath());
		fullCategoryPath.append(getId());
		fullCategoryPath.append(".");
		return fullCategoryPath.toString();
	}

	/**
	 * Default Key Fields Constructor for class Category
	 */
	public Category(Integer in_categoryId) {
		super(in_categoryId);
	}

	protected String oldCategoryCode;

	public String getOldCategoryCode() {
		return oldCategoryCode;
	}

	public void setOldCategoryCode(String oldCategoryCode) {
		this.oldCategoryCode = oldCategoryCode;
	}

	protected Integer oldLinkedCategoryId;

	public Integer getOldLinkedCategoryId() {
		return oldLinkedCategoryId;
	}

	public void setOldLinkedCategoryId(Integer oldLinkedCategoryId) {
		this.oldLinkedCategoryId = oldLinkedCategoryId;
	}

	public Short getIsCreateSubLinkedCategory() {
		return isCreateSubLinkedCategory;
	}

	public void setIsCreateSubLinkedCategory(Short isCreateSubLinkedCategory) {
		this.isCreateSubLinkedCategory = isCreateSubLinkedCategory;
	}
	
}

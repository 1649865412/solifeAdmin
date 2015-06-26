package com.cartmatic.estore.imports.handler.category;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class CategoryParentCategoryHandler2 extends ColumnBasicHandler implements ColumnHandler{
	private Logger logger = Logger.getLogger(CategoryParentCategoryHandler2.class);
	protected CategoryManager	categoryManager	= null;
	private Short categoryType;
	private boolean isName=true;
	
	public void setProperty(ImportModel importModel, Column column)throws Exception {
		Category category=(Category)importModel.getTarget();
		//当为更新时，父目录不做处理
		if(category.getId()!=null){
			importModel.setResult("0");
			if(StringUtils.isNotEmpty(column.getValue())){
				logger.warn("本条数据为更新目录，其父目录不能进行更新操作！");
			}
			return;
		}
		Category parentCategory=null;
		//没有指定父目录的，就直接为一级目录
		if(StringUtils.isEmpty(column.getValue())){
			parentCategory=getRootCategory(importModel);
		}else if(StringUtils.isNotEmpty(column.getValue())){
			if(isName()){
				parentCategory=getCategoryByName(importModel,column.getValue());
			}else{
				parentCategory=getCategoryByCode(importModel,column.getValue());
			}
		}
		if(parentCategory!=null){
			category.setParentId(parentCategory.getCategoryId());
			importModel.setResult("1");
		}else{
			logger.warn("没有找到相应的父目录["+column.getValue()+"]");
			importModel.setResult("-1");
		}
	}
	
	private Category getRootCategory(ImportModel importModel){
		Category tempCategory=null;
		if(categoryType.intValue()==2){
			tempCategory=importModel.getStore().getCategory();
		}else{
			tempCategory=importModel.getCatalog().getCategory();
		}
		if(tempCategory==null){
			logger.warn("没有超级根目录,可能未初始化目录数据");
		}
		return tempCategory;
	}
	
	private Category getCategoryByName(ImportModel importModel,String categoryName){
		Category tempCategory=null;
		List<Category> categoryList=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG){
			Integer catalogId=importModel.getCatalog().getCatalogId();
			categoryList=categoryManager.findProductCategoryByName(catalogId, categoryName);
		}else{
			Integer storeId=importModel.getStore().getStoreId();
			categoryList=categoryManager.findContentCategoryByName(storeId, categoryName);
		}
		if(categoryList!=null&&categoryList.size()==1){
			tempCategory=categoryList.get(0);
		}else if(categoryList!=null&&categoryList.size()>1){
			logger.warn("系统存在"+categoryList.size()+"个["+categoryName+"]目录，系统无法确定使用哪个目录。");
		}else{
			logger.warn("没有找到目录名为["+categoryName+"]的目录。");
		}
		return tempCategory; 
	}
	
	
	private Category getCategoryByCode(ImportModel importModel,String categoryCode){
//		Category tempCategory=categoryManager.getCategoryByCode(categoryType, categoryCode);
		//@TODO 多店调整后待完善
		Category tempCategory=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG){
			Integer catalogId=importModel.getCatalog().getCatalogId();
			tempCategory=categoryManager.getProductCategoryByCode(catalogId, categoryCode);
		}else{
			Integer storeId=importModel.getStore().getStoreId();
			tempCategory=categoryManager.getContentCategoryByCode(storeId, categoryCode);
		}
		if(tempCategory==null){
			logger.warn("没有找到目录编码为["+categoryCode+"]的目录。");
		}
		return tempCategory;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public Short getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Short categoryType) {
		this.categoryType = categoryType;
	}

	public boolean isName() {
		return isName;
	}

	public void setName(boolean isName) {
		this.isName = isName;
	}

}

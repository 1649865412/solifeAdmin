package com.cartmatic.estore.imports.handler.category;

import java.util.Arrays;
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

public class CategoryParentCategoryHandler extends ColumnBasicHandler implements ColumnHandler{
	private Logger logger = Logger.getLogger(CategoryParentCategoryHandler.class);
	protected CategoryManager	categoryManager	= null;
	private Short categoryType;
	
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
			parentCategory=getCategoryByPathName(importModel,column.getValues().toArray(new String[]{}));
		}
		if(parentCategory!=null){
			category.setParentId(parentCategory.getCategoryId());
			importModel.setResult("1");
		}else{
			logger.warn("没有找到相应的父目录,["+Arrays.toString(column.getValues().toArray(new String[]{}))+"]");
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
	
	public Category getCategoryByPathName(ImportModel importModel,String categoryNames[]){
		Category tempCategory=null;
		tempCategory=getRootCategory(importModel);
		if(tempCategory==null){
			return null;
		}
		Integer parentCategoryId=tempCategory.getCategoryId();
		for (String categoryName : categoryNames) {
			List<Category> categoryList=categoryManager.findCategoryByParentIdAndName(parentCategoryId,categoryName);
			if(categoryList!=null&&categoryList.size()==1){
				tempCategory=categoryList.get(0);
				parentCategoryId=tempCategory.getId();
			}else if(categoryList!=null&&categoryList.size()>1){
				//当指定父目录存在多个同名子目录时，不作处理
				logger.warn("父目录："+tempCategory.getCategoryPathName()+"存在"+categoryList.size()+"个同名的子目录["+categoryName+"]，系统不作处理。");
				return null;
			}else{
				tempCategory=null;
			}
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

}

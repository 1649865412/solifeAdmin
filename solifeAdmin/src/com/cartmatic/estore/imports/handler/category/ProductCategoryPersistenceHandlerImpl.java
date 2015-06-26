package com.cartmatic.estore.imports.handler.category;

import java.util.List;

import org.apache.log4j.Logger;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.imports.handler.PersistenceHandler;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductCategoryPersistenceHandlerImpl implements PersistenceHandler{
	private Logger logger=Logger.getLogger(ProductCategoryPersistenceHandlerImpl.class);
	private CategoryManager	categoryManager	= null;
	
	public void saveOrUpdate(ImportModel importModel) {
		if(logger.isInfoEnabled()){
			logger.info("保存产品目录");
		}
		Category category=(Category)importModel.getTarget();
		category.setCategoryType(Constants.CATEGORY_TYPE_CATALOG);
		if(category.getSortOrder()==null){
			Integer maxSortOrder=categoryManager.getMaxSortOrder(category.getParentId());
			maxSortOrder++;
			category.setSortOrder(maxSortOrder);
		}
		category.setCatalog(importModel.getCatalog());
		//保存、更新自定义属性
		List<AttributeValue>  attributeValueList=(List<AttributeValue>)importModel.getImportTargetData().get("attributeValueList");
		
		categoryManager.saveCategoryAction(category,attributeValueList);
		importModel.setResult("1");
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void validate(ImportModel importModel) {
		
	}
	
	

}

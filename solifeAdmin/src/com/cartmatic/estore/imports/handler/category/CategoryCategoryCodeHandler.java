package com.cartmatic.estore.imports.handler.category;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class CategoryCategoryCodeHandler extends ColumnBasicHandler implements ColumnHandler{
	private Logger logger = Logger.getLogger(CategoryCategoryCodeHandler.class);
	protected CategoryManager	categoryManager	= null;
	public void setProperty(ImportModel importModel, Column column)
			throws Exception {
		Category category=(Category)importModel.getTarget();
		String value = column.getValue();
		if(StringUtils.isNotEmpty(value)){
			//@TODO 多店调整后待完善
			Integer catalogId=importModel.getCatalog().getCatalogId();
			Category category2=categoryManager.getProductCategoryByCode(catalogId, value);
			if(category2!=null){
				category=category2;
				importModel.setTarget(category);
				importModel.setUpdate(true);
				if(logger.isInfoEnabled()){
					logger.info("本条数据为更新目录信息，目录具体数据："+category);
				}
			}else{
				category.setCategoryCode(value);
				if(logger.isInfoEnabled()){
					logger.info("本条数据为新增目录");
				}
			}
			importModel.setResult("1");
		}else{
			logger.warn("没有指定相应的目录编码");
			importModel.setResult("-1");
		}
	}
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

}

package com.cartmatic.estore.imports.handler.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

/**
 * 本Handler不支持同一目录下有相同的目录名称,如果存在的会导致数据不准确
 * columnHeader顺序:由真正的目录到其父目录.例如:Third Category,Second Category,First Category
 * value=目录1>目录2>目录3      目录3为该产品所在的真正目录
 * @author Administrator
 *
 */
public class ProductCategoryHandler extends ColumnBasicHandler implements ColumnHandler{
	private Logger logger = Logger.getLogger(ProductCategoryHandler.class);
	/**
	 * 目录关系分隔符（从高级到低级）
	 */
	private String delimiter;
	private CategoryManager categoryManager=null;
	
	
	public void setProperty(ImportModel importModel,Column column) throws Exception {
//		Product product=(Product)importModel.getTarget();
		List<String>values=column.getValues();
		
		List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
		for (int i = 0; i < values.size(); i++) {
			if(StringUtils.isEmpty(values.get(i))&&i>0){
				//当额外目录为空时，忽略该目录设置
				continue;
			}
			Category category=getCategoryByPathName(importModel,values.get(i).split(delimiter),i==0);
			ProductCategory productCategory=new ProductCategory();
			productCategory.setCategory(category);
			if(category==null){
				if(i==0){
					logger.warn("没有找到相应的主目录："+Arrays.toString(values.get(i).split(delimiter)));
					importModel.setResult("-1");
					return;
				}else{
					logger.warn("没有找到相应的额外目录："+Arrays.toString(values.get(i).split(delimiter)));
					continue;
				}
			}
			if(i==0){
				productCategory.setIsMainCategory(Constants.FLAG_TRUE);
			}else{
				productCategory.setIsMainCategory(Constants.FLAG_FALSE);
			}
			productCategoryList.add(productCategory);
		}
		//更新产品时，直接更新设置产品目录信息
		Integer mainCategoryId=null;
		Integer categoryIds[]=new Integer[productCategoryList.size()];
		for (int i = 0; i < productCategoryList.size(); i++) {
			ProductCategory productCategory=productCategoryList.get(i);
			categoryIds[i]=productCategory.getCategoryId();
			if(productCategory.getIsMainCategory().intValue()==Constants.FLAG_TRUE.intValue()){
				mainCategoryId=productCategory.getCategoryId();
			}
		}
		importModel.getImportTargetData().put("mainCategoryId", mainCategoryId);
		importModel.getImportTargetData().put("categoryIds", categoryIds);
		importModel.setResult("1");
	}
	
	public Category getCategoryByPathName(ImportModel importModel,String categoryNames[],boolean isMainCategory){
		Integer parentCategoryId=importModel.getCatalog().getCategoryId();
		Category tempCategory=null;
		for (String categoryName : categoryNames) {
			tempCategory=null;
			List<Category> categoryList=categoryManager.findCategoryByParentIdAndName(parentCategoryId,categoryName);
			if(categoryList!=null&&categoryList.size()>0){
				tempCategory=categoryList.get(0);
				parentCategoryId=tempCategory.getId();
				if(categoryList.size()>1){
					//Category tempParentCategory=categoryManager.getById(parentCategoryId);
					//logger.warn("目录["+tempParentCategory.getCategoryPathName()+"]存在两个同名的子目录，系统以["+tempCategory.getCategoryName()+"]作为指定目录处理！");
					String categoryTypeName=isMainCategory?"主目录":"额外目录";
					logger.warn(categoryTypeName+"。系统存在"+categoryList.size()+"个["+categoryName+"]目录，系统无法确定使用哪个目录。");
					tempCategory=null;
				}
			}
		}
		return tempCategory;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	
}

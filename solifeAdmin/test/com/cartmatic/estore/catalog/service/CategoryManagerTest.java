package com.cartmatic.estore.catalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class CategoryManagerTest  extends BaseTransactionalTestCase
{
	@Autowired
	private CategoryManager categoryManager;

	@Autowired
	private ProductCategoryManager productCategoryManager;
	
	@Autowired
	private ContentManager contentManager;
	
	@Test
	@Rollback(false)
	public void test(){
//		perfectCategoryPath();
		setCategoryTemplate();
	}
	
	public void setCategoryTemplate(){
		List<CategoryTreeItem>categoryTreeItemList=categoryManager.getCatalogTreeItemList4Front(1);
		for (CategoryTreeItem categoryTreeItem : categoryTreeItemList){
			System.out.println(categoryTreeItem.getCategoryCode()+"\t"+categoryTreeItem.getLevel()+"\t"+categoryTreeItem.isHasChildren());
		}
	}
	
	private void perfectCategoryPath(){
		List<Category> categoryList=categoryManager.getAll();
		if(categoryList!=null){
			for (Category category : categoryList)
			{
				if(category.getCategory()!=null){
					StringBuffer categoryPath=new StringBuffer();
					List<Category> allParentCategoryList=category.getAllParentCategorys();
					for (Category category2 : allParentCategoryList)
					{
						categoryPath.append(category2.getId()+".");
					}
					
					category.setCategoryPath(categoryPath.toString());
					System.out.println(category.getId()+"\t"+category.getCategory().getId()+"\t"+categoryPath.toString());
					categoryManager.save(category);
				}
			}
		}
	}
	
	private void perfectProductCategoryPath(){
		List<ProductCategory> productCategoryList=productCategoryManager.getAllOrdered("productCategoryId", false);
		if(productCategoryList!=null){
			for (ProductCategory productCategory : productCategoryList)
			{
				Category category=productCategory.getCategory();
				String categoryPath=(StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + productCategory.getProduct().getProductId();
				productCategory.setCategoryPath(categoryPath);
				System.out.println(productCategory.getProduct().getId()+"\t"+category.getId()+"\t"+category.getCategoryPath()+"\t"+categoryPath.toString());
				productCategoryManager.save(productCategory);
			}
		}
	}
}

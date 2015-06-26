package com.cartmatic.estore.catalog.dao.impl;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductCategoryDao;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductCategory.
*/
public class ProductCategoryDaoImpl extends HibernateGenericDaoImpl<ProductCategory> implements ProductCategoryDao {



	public Integer getMaxSortOrder(Integer categoryId) {
		String hql="select max(pc.sortOrder) from  ProductCategory pc where pc.category.categoryId=?";
		Integer maxSortOrder=(Integer)findUnique(hql, categoryId);
		return maxSortOrder;
	}


	public Integer getNotDeletedProductCountByCategory(Integer categoryId) { 
		String hql="select count(pc.productCategoryId) from ProductCategory pc where pc.isMainCategory=1 and pc.product.status!=3 and pc.category.categoryId=?";
		Long result=countByHql(hql, categoryId);
		return result.intValue();
	}

	public boolean isInCategory(Integer productId, String categoryPath) {
		String hql="select count(pc.productCategoryId) from ProductCategory pc where pc.product.productId=? and pc.categoryPath like ?";
		Long result=countByHql(hql, new Object[]{productId,categoryPath+"%"});
		if(result>0){
			return true;
		}
		return false;
	}

	public Category getCategoryByProductCategoryPath(String productCategoryPath) {
		String hql="select pc.category from ProductCategory pc where pc.categoryPath=?";
		Category category=(Category)findUnique(hql, productCategoryPath);
		return category;
	}


	@Override
	public List<ProductCategory> findProductCategories(Integer catalogId, Integer categoryId) {
		String hql="from ProductCategory pc where pc.category.categoryId=? and pc.category.catalog.catalogId=?";
		return findByHql(hql,categoryId,catalogId);
	}
}
package com.cartmatic.estore.catalog.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.dao.CatalogDashboardDao;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

@SuppressWarnings("unchecked")
public class CatalogDashboardDaoImpl extends HibernateGenericDaoImpl<Product> implements CatalogDashboardDao{

	
	public Long getActiveProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.status=?",new Object[]{Constants.STATUS_ACTIVE});
		return result.get(0);
		
	}

	public Long getProductCategoryTotal() {
		List<Long> result=getHibernateTemplate().find("select count(c) from Category c where  c.categoryType=?",new Object[]{Constants.CATEGORY_TYPE_CATALOG});
		return result.get(0);
	}

	public Long getDraftProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.status=?",new Object[]{Constants.STATUS_NOT_PUBLISHED});
		return result.get(0);
	}

	public Long getInActiveProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.status=?",new Object[]{Constants.STATUS_INACTIVE});
		return result.get(0);
	}
	

	public Long getAwaitingDeleteProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.status=?",new Object[]{Constants.STATUS_AWAITING_DELETE});
		return result.get(0);
	}

	public Long getProductSumTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.status<>?",new Object[]{Constants.STATUS_DELETED});
		return result.get(0);
	}

	public Long getConfirmProductReviewTotal() {
		List<Long> result=getHibernateTemplate().find("select count(pr) from ProductReview pr where pr.status=?",new Object[]{Constants.STATUS_INACTIVE});
		return result.get(0);
	}

	public Long getSettingProductImgTotal() { 
		List<Long> result=getHibernateTemplate().find("select count(distinct ps.product) from ProductSku ps where ps.product.status<>? and (ps.image is null or ps.image='')", new Object[]{Constants.STATUS_NOT_PUBLISHED});
		return result.get(0);
	}

	public SearchCriteria findLatestProductReview(SearchCriteria sc) {
		sc.setHql("select pr from ProductReview pr where pr.reviewId is null order by pr.createTime desc");
		return sc;
	}

	public Long getCommonProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=?",new Object[]{CatalogConstants.PRODUCT_KIND_COMMON});
		return result.get(0);
	}

	public Long getActiveCommonProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_COMMON,Constants.STATUS_ACTIVE});
		return result.get(0);
	}
	
	public Long getPackageProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=?",new Object[]{CatalogConstants.PRODUCT_KIND_PACKAGE});
		return result.get(0);
	}
	
	public Long getActivePackageProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_PACKAGE,Constants.STATUS_ACTIVE});
		return result.get(0);
	}

	public Long getVariationProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=?",new Object[]{CatalogConstants.PRODUCT_KIND_VARIATION});
		return result.get(0);
	}
	
	public Long getActiveVariationProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.productKind=? and p.status=?", new Object[]{CatalogConstants.PRODUCT_KIND_VARIATION,Constants.STATUS_ACTIVE});
		return result.get(0);
	}

	public Long getAlwaysSellProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.availabilityRule=?", new Object[]{CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL});
		return result.get(0);
	}

	
	public Long getBackOrderProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.availabilityRule=?", new Object[]{CatalogConstants.PRODUCT_AVAILABILITY_BACK_ORDER});
		return result.get(0);
	}

	
	public Long getInStockProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.availabilityRule=?", new Object[]{CatalogConstants.PRODUCT_AVAILABILITY_ONLY_IN_STOCK});
		return result.get(0);
	}

	
	public Long getPreOrderProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.availabilityRule=?", new Object[]{CatalogConstants.PRODUCT_AVAILABILITY_PRE_ORDER});
		return result.get(0);
	}

	public Long getAwaitingDeleteCommonProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_COMMON,Constants.STATUS_AWAITING_DELETE});
	}

	public Long getAwaitingDeletePackageProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_PACKAGE,Constants.STATUS_AWAITING_DELETE});
	}

	public Long getAwaitingDeleteVariationProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_VARIATION,Constants.STATUS_AWAITING_DELETE});
	}

	public Long getDraftCommonProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_COMMON,Constants.STATUS_NOT_PUBLISHED});
	}

	public Long getDraftPackageProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_PACKAGE,Constants.STATUS_NOT_PUBLISHED});
	}

	public Long getDraftVariationProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_VARIATION,Constants.STATUS_NOT_PUBLISHED});
	}

	public Long getInActiveCommonProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_COMMON,Constants.STATUS_INACTIVE});
	}

	public Long getInActivePackageProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_PACKAGE,Constants.STATUS_INACTIVE});
	}

	public Long getInActiveVariationProductTotal() {
		return countByHql("from Product p where p.productKind=? and p.status=?",new Object[]{CatalogConstants.PRODUCT_KIND_VARIATION,Constants.STATUS_INACTIVE});
	}

	@Override
	public Long getNotInStockSellProductTotal() {
		List<Long> result=getHibernateTemplate().find("select count(p) from Product p where p.availabilityRule=?", new Object[]{CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL});
		return result.get(0);
	}

	
}

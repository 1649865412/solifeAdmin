package com.cartmatic.estore.catalog.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.dao.CatalogDashboardDao;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.inventory.dao.InventoryDashboardDao;

public class CatalogDashboardDaoTest extends BaseTransactionalTestCase {
	
	@Autowired
	private CatalogDashboardDao catalogDashboardDao=null;
	
	@Autowired
	private InventoryDashboardDao inventoryDashboardDao=null;
	
	
	@Test
	public void test(){
		System.out.println("获取产品总数:"+catalogDashboardDao.getProductSumTotal());
		System.out.println("激活产品数量:"+catalogDashboardDao.getActiveProductTotal());
		System.out.println("非激活产品数量:"+catalogDashboardDao.getInActiveProductTotal());
		System.out.println("草稿状态的产品数量:"+catalogDashboardDao.getDraftProductTotal());
		System.out.println("待审核的产品评论数量:"+catalogDashboardDao.getConfirmProductReviewTotal());
		System.out.println("待设置产品图片的数量:"+catalogDashboardDao.getSettingProductImgTotal());
		System.out.println("目录总数:"+catalogDashboardDao.getProductCategoryTotal());
		
		System.out.println("低库存的SKU数量:"+inventoryDashboardDao.getLowStockProductSkuTotal());
		System.out.println("低库存的激活产品的SKU数量:"+inventoryDashboardDao.getLowStockActiveProductSkuTotal());
		System.out.println("已到再进货时间Sku 总数量:"+inventoryDashboardDao.getAlreadyExpectedRestockDateInventoryTotal());
	}
}

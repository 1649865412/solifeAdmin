package com.cartmatic.estore.catalog.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.catalog.dao.CatalogDashboardDao;
import com.cartmatic.estore.catalog.service.CatalogDashboardManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

public class CatalogDashboardManagerImpl extends GenericManagerImpl<Product> implements CatalogDashboardManager {

	private CatalogDashboardDao catalogDashboardDao=null;
	public SearchCriteria findLatestProductReview(SearchCriteria sc) {
		sc =catalogDashboardDao.findLatestProductReview(sc);
		return sc;
	}

	public void setCatalogDashboardDao(CatalogDashboardDao catalogDashboardDao) {
		this.catalogDashboardDao = catalogDashboardDao;
	}

	public Map<String, Long> getCatalogDashboardData() {
		Map<String,Long> catalogDashBoardData=new HashMap<String, Long>();
		
		//激活产品数量
		Long activeProductTotal= catalogDashboardDao.getActiveProductTotal();
		catalogDashBoardData.put("activeProductTotal",activeProductTotal);
		
		//非激活产品数量
		Long inActiveProductTotal=catalogDashboardDao.getInActiveProductTotal();
		catalogDashBoardData.put("inActiveProductTotal", inActiveProductTotal);
		
		//草稿状态的产品数量
		Long draftProductTotal=catalogDashboardDao.getDraftProductTotal();
		catalogDashBoardData.put("draftProductTotal", draftProductTotal);
		
		//待删除的产品数量
		Long awaitingDeleteProductTotal=catalogDashboardDao.getAwaitingDeleteProductTotal();
		catalogDashBoardData.put("awaitingDeleteProductTotal", awaitingDeleteProductTotal);
		
		//获取产品总数
		catalogDashBoardData.put("productSumTotal", activeProductTotal+inActiveProductTotal+draftProductTotal+awaitingDeleteProductTotal);
		
		
		
		//获取普通产品数量(激活)
		Long activeCommonProductTotal= catalogDashboardDao.getActiveCommonProductTotal();
		catalogDashBoardData.put("activeCommonProductTotal",activeCommonProductTotal);
		
		//获取普通产品数量(非激活)
		Long inActiveCommonProductTotal=catalogDashboardDao.getInActiveCommonProductTotal();
		catalogDashBoardData.put("inActiveCommonProductTotal", inActiveCommonProductTotal);
		
		//获取普通产品数量(草稿状态)
		Long draftCommonProductTotal=catalogDashboardDao.getDraftCommonProductTotal();
		catalogDashBoardData.put("draftCommonProductTotal", draftCommonProductTotal);
		
		//获取普通产品数量(待删除)
		Long awaitingCommonDeleteProductTotal=catalogDashboardDao.getAwaitingDeleteCommonProductTotal();
		catalogDashBoardData.put("awaitingCommonDeleteProductTotal", awaitingCommonDeleteProductTotal);
		
		//获取普通产品总数
		catalogDashBoardData.put("commonProductTotal", activeCommonProductTotal+inActiveCommonProductTotal+draftCommonProductTotal+awaitingCommonDeleteProductTotal);
		
		
		//获取变种产品数量(激活)
		Long activeVariationProductTotal= catalogDashboardDao.getActiveVariationProductTotal();
		catalogDashBoardData.put("activeVariationProductTotal",activeVariationProductTotal);
		
		//获取变种产品数量(非激活)
		Long inActiveVariationProductTotal=catalogDashboardDao.getInActiveVariationProductTotal();
		catalogDashBoardData.put("inActiveVariationProductTotal", inActiveVariationProductTotal);
		
		//获取变种产品数量(草稿状态)
		Long draftVariationProductTotal=catalogDashboardDao.getDraftVariationProductTotal();
		catalogDashBoardData.put("draftVariationProductTotal", draftVariationProductTotal);
		
		//获取变种产品数量(待删除)
		Long awaitingVariationDeleteProductTotal=catalogDashboardDao.getAwaitingDeleteVariationProductTotal();
		catalogDashBoardData.put("awaitingVariationDeleteProductTotal", awaitingVariationDeleteProductTotal);
		
		//获取变种产品总数
		catalogDashBoardData.put("variationProductTotal", activeVariationProductTotal+inActiveVariationProductTotal+draftVariationProductTotal+awaitingVariationDeleteProductTotal);
		
		
		
		//获取产品包产品数量(激活)
		Long activePackageProductTotal= catalogDashboardDao.getActivePackageProductTotal();
		catalogDashBoardData.put("activePackageProductTotal",activePackageProductTotal);
		
		//获取产品包产品数量(非激活)
		Long inActivePackageProductTotal=catalogDashboardDao.getInActivePackageProductTotal();
		catalogDashBoardData.put("inActivePackageProductTotal", inActivePackageProductTotal);
		
		//获取产品包产品数量(草稿状态)
		Long draftPackageProductTotal=catalogDashboardDao.getDraftPackageProductTotal();
		catalogDashBoardData.put("draftPackageProductTotal", draftPackageProductTotal);
		
		//获取产品包产品数量(待删除)
		Long awaitingPackageDeleteProductTotal=catalogDashboardDao.getAwaitingDeletePackageProductTotal();
		catalogDashBoardData.put("awaitingPackageDeleteProductTotal", awaitingPackageDeleteProductTotal);
		
		//获取产品包产品总数
		catalogDashBoardData.put("packageProductTotal", activePackageProductTotal+inActivePackageProductTotal+draftPackageProductTotal+awaitingPackageDeleteProductTotal);
		
		
		//销售规则
		//有库存才可以购买
		Long inStockProductTotal=catalogDashboardDao.getInStockProductTotal();
		catalogDashBoardData.put("inStockProductTotal", inStockProductTotal);
		
		//可预订产品数量
		Long preOrderProductTotal=catalogDashboardDao.getPreOrderProductTotal();
		catalogDashBoardData.put("preOrderProductTotal", preOrderProductTotal);
		
		//可缺货销售
		Long backOrderProductTotal=catalogDashboardDao.getBackOrderProductTotal();
		catalogDashBoardData.put("backOrderProductTotal", backOrderProductTotal);
		
		//永远可售产品
		Long alwaysSellProductTotal=catalogDashboardDao.getAlwaysSellProductTotal();
		catalogDashBoardData.put("alwaysSellProductTotal", alwaysSellProductTotal);
		
		//无库存销售产品
		Long notInStockSellProductTotal=catalogDashboardDao.getNotInStockSellProductTotal();
		catalogDashBoardData.put("notInStockSellProductTotal", notInStockSellProductTotal);
		
		//待设置产品图片的数量
//		catalogDashBoardData.put("settingProductImgTotal", catalogDashboardDao.getSettingProductImgTotal());
		
		
		//待审核的产品评论数量
//		catalogDashBoardData.put("confirmProductReviewTotal", catalogDashboardDao.getConfirmProductReviewTotal());
		return catalogDashBoardData;
	}

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		dao=catalogDashboardDao;
	}

	@Override
	protected void onDelete(Product entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(Product entity) {
		// TODO Auto-generated method stub
		
	}

	
}

package com.cartmatic.estore.catalog.dao;


import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;

public interface CatalogDashboardDao extends GenericDao<Product>{
	/**
	 * 获取产品总数；（非已删除状态，即后台可见的）
	 * （暂时没有用）
	 * @return
	 */
	public Long getProductSumTotal();
	
	/** 
	 * 激活产品数量（激活状态,上架产品数）
	 * @return
	 */
	public Long getActiveProductTotal();

	/**
	 * 非激活产品数量
	 * @return
	 */
	public Long getInActiveProductTotal();
	
	/**
	 * 草稿状态的产品数量，（有待完成编辑产品的数量）
	 * @return
	 */
	public Long getDraftProductTotal();
	
	/**
	 * 待删除的产品数量，（有待完成编辑产品的数量）
	 * @return
	 */
	public Long getAwaitingDeleteProductTotal();
	

	
	/**
	 * 待审核的产品评论数量
	 * @return
	 */
	public Long getConfirmProductReviewTotal();
	
	/**
	 * 待设置产品图片的数量
	 * @return
	 */
	public Long getSettingProductImgTotal();
	
	
	/**
	 * 目录总数
	 * @return
	 */
	public Long getProductCategoryTotal();
	
	
	/**
	 * 获取普通产品数量
	 * @return
	 */
	public Long getCommonProductTotal();
	
	/**
	 * 获取普通产品数量(激活)
	 * @return
	 */
	public Long getActiveCommonProductTotal();
	
	/**
	 * 获取普通产品数量(非激活)
	 * @return
	 */
	public Long getInActiveCommonProductTotal();
	
	/**
	 * 获取普通产品数量(草稿状态)，（有待完成编辑产品的数量）
	 * @return
	 */
	public Long getDraftCommonProductTotal();
	
	/**
	 * 获取普通产品数量(待删除)，（有待完成编辑产品的数量）
	 * @return
	 */
	public Long getAwaitingDeleteCommonProductTotal();
			
	
	/**
	 * 获取变种产品数量
	 * @return
	 */
	public Long getVariationProductTotal();
	
	/**
	 * 获取变种产品数量(激活)
	 * @return
	 */
	public Long getActiveVariationProductTotal();
	

	/**
	 * 获取变种产品数量(非激活)
	 * @return
	 */
	public Long getInActiveVariationProductTotal();
	
	/**
	 *  获取变种产品数量(草稿状态）
	 * @return
	 */
	public Long getDraftVariationProductTotal();
	
	/**
	 *  获取变种产品数量(待删除）
	 * @return
	 */
	public Long getAwaitingDeleteVariationProductTotal();
	
	
	
	/**
	 * 获取产品包数量
	 * @return
	 */
	public Long getPackageProductTotal();
	
	/**
	 * 获取产品包数量(激活)
	 * @return
	 */
	public Long getActivePackageProductTotal();
	
	
	/**
	 * 获取产品包数量(非激活)
	 * @return
	 */
	public Long getInActivePackageProductTotal();
	
	/**
	 * 获取产品包数量(草稿状态)
	 * @return
	 */
	public Long getDraftPackageProductTotal();
	
	/**
	 * 获取产品包数量(待删除)
	 * @return
	 */
	public Long getAwaitingDeletePackageProductTotal();
	
	
	
	/**
	 * 获取有库存才可以购买产品数量
	 * @return
	 */
	public Long getInStockProductTotal();
	
	
	/**
	 * 获取可预订产品数量
	 * @return
	 */
	public Long getPreOrderProductTotal();
	
	
	/**
	 * 获取可缺货销售产品数量
	 * @return
	 */
	public Long getBackOrderProductTotal();
	
	/**
	 * 获取永远可售产品数量（无限库存）
	 * @return
	 */
	public Long getAlwaysSellProductTotal();
	
	/**
	 * 获取无库存销售产品数量
	 * @return
	 */
	public Long getNotInStockSellProductTotal();
	
	
	
	/**
	 * 获取最近的评论信息
	 * @return
	 */
	public SearchCriteria findLatestProductReview(SearchCriteria sc);
}

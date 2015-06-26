package com.cartmatic.estore.catalog.dao;

import java.util.List;


import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * Dao interface for Product.
 */
public interface ProductDao extends GenericDao<Product> {
	
	
	
	/**
	 * 查找某目录下的产品
	 * @param searchCriteria
	 * @param categoryId
	 * @param sorter
	 * @return
	 */
	public List<Product> findProductByCategoryIdForShow(SearchCriteria searchCriteria,Integer categoryId,String sorter);
	

	
	/**
	 * 前台搜索产品
	 * @param searchCriteria
	 * @param productSearchCriteria
	 * @return
	 */
	public SearchCriteria getProductSearchCriteria4Front(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria);
	
	/**
	 * 后台搜索产品
	 * @param searchCriteria
	 * @param productSearchCriteria
	 * @return
	 */
	public SearchCriteria getProductSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria);
	
	
	/**
	 * 后台搜索Sku
	 * @param searchCriteria
	 * @param productSearchCriteria
	 * @return
	 */
	public SearchCriteria getProductSkuSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria);
	
	/**
	 * 定时发布/结束产品
	 */
	public void updateStatusForPublish();
	
	/**
	 * 根据产品id集合获得产品实体
	 * @param ids
	 * @return
	 */
	public List<Product> getByIds(String[] ids);
	

	
	/**hqm
	*
	*/
	public void refresh(Object entity);
	
	public String getMaxAutoCode(String sample);
	
}
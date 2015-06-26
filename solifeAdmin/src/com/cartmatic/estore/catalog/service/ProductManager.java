package com.cartmatic.estore.catalog.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Product, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductManager extends GenericManager<Product> {
	
	public Product getProductByProductCode(String productCode);
	
	public Product getActiveProduct(Integer productId);


	/**
	 * 查找某目录下的产品
	 * @param searchCriteria
	 * @param categoryId
	 * @param sorter
	 * @return
	 */
	public List<Product> findProductByCategoryIdForShow(SearchCriteria searchCriteria,Integer categoryId,String sorter);
	
	
	
	public void delete(Product product);
	
	
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

	
	public void refresh(Object entity);

	/**
	 * 获取产品相应的附件组及附件项
	 * @param productId
	 * @return
	 */
	public List<Map.Entry<AccessoryGroup, List<Accessory>>> getProductAccessoryMap(Integer productId);


}


package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.core.dao.GenericDao;

/**
 * Dao interface for ProductSku.
 */
public interface ProductSkuDao extends GenericDao<ProductSku> {
	
	/**
	 * 获取激活的ProductSku
	 * @param productId
	 * @return
	 */
	public List<ProductSku> findActiveProductSkusByProductId(Integer productId);
	
	
	/**
	 * 根据productId 获取默认productSku
	 * @param productSkuId
	 * @return
	 */
	public ProductSku getDefaultProductSkuByProductId(Integer productId);
	
	/**
	 * 是否存在指定的图片引用 (主图)
	 * @param imageUrl
	 * @return
	 */
	public boolean existImageReference(String imageUrl);
}
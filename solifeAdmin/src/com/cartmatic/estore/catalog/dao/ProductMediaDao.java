package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductMedia.
 */
public interface ProductMediaDao extends GenericDao<ProductMedia> {
	/**查找产品更多图片或附件
	 * @param productId
	 * @param mediaType
	 * @return
	 */
	public List<ProductMedia> findProductMediaByProductIdAndType(Integer productId,Short...mediaType);
	
	/**
	 * 是否存在指定的图片引用
	 * @param imageUrl
	 * @return
	 */
	public boolean existImageReference(String imageUrl);
	
}
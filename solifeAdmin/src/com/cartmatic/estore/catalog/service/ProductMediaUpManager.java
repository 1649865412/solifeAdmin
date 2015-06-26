package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductMediaUp;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductMedia, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductMediaUpManager extends GenericManager<ProductMediaUp> {
	
	
	/**
	 * 保存产品所有产品媒体
	 * @param productId
	 * @param productMediaIds 产品媒体Id，如根据该Id找不到相应的数据的，则表示新增
	 * @param productMediaTypes 
	 * @param mediaUrls
	 * @param mediaDescription
	 * @param productMedia_deleteds 
	 * @return返回新增媒体的Id，原负id_新id
	 */
	public List<String> saveProductMedias(Integer productId,String[]productMediaIds,String[]productMediaTypes,String []mediaUrls, String[] mediaUrls_d, String []mediaDescription,String productMedia_deleteds[]);
	
	/**
	 * 查找产品更多图片或附件
	 * @param productId
	 * @param mediaType 要查找的产品类型
	 * @return
	 */
	public List<ProductMediaUp> findProductMediaByProductIdAndType(Integer productId,Short...mediaType);
	

	/**
	 * 是否存在指定的图片引用
	 * @param imageUrl
	 * @return
	 */
	public boolean existImageReference(String imageUrl);
}

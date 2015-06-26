package com.cartmatic.estore.catalog.service;

import java.util.List;
import java.util.Set;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.core.service.GenericManager;

public interface ProductSkuManager extends GenericManager<ProductSku> {
	public ProductSku getProductSkuByProductSkuCode(String productSkuCode);

	/**
	 * 获取激活的ProductSku
	 * @param productId
	 * @return
	 */
	public List<ProductSku> findActiveProductSkusByProductId(Integer productId);
	

	/**
	 * 保存ProductSku选项，保存前应检查是否已存在相应的sku
	 * @param productSkuId
	 * @param skuOptions 
	 */
	public void saveProductSkuOptions(Integer productSkuId,Set<Integer>skuOptionValueIds);
	


	/**
	 * 获取某产品指定optionValuesIds选项的Sku
	 * @param productId
	 * @param optionValuesIds
	 * @return
	 */
	public ProductSku getSkuInPorudctByOptionValueIds(Integer productId,Set<Integer>optionValuesIds);
	
	/**
	 * 设置产品默认的Sku
	 * @param productSkuId
	 */
	public void doSetDefaultProductSku(Integer productSkuId);
	
	
	
	/**
	 * 检查Sku选项属性是否与指定Sku一致
	 * @param productSkuId
	 * @param skuOptionValueIds
	 * @return
	 */
	public boolean isSkuOptionIsSameWithSku(Integer productSkuId,Set<Integer> skuOptionValueIds);
	
	/**
	 * 根据productId 获取默认productSku
	 * @param productSkuId
	 * @return
	 */
	public ProductSku getDefaultProductSkuByProductId(Integer productId);
	
	/**
	 * 比较两个Sku选项属性是否一致
	 * @param productSku1
	 * @param productSku2
	 * @return
	 */
	public boolean compare2SkuOptionIsSame(ProductSku productSku1,ProductSku productSku2);
	
	/**
	 * 是否存在指定的图片引用
	 * @param imageUrl
	 * @return
	 */
	public boolean existImageReference(String imageUrl);
}

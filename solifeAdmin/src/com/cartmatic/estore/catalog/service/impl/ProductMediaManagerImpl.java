package com.cartmatic.estore.catalog.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.catalog.dao.ProductMediaDao;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductMediaManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductMedia, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductMediaManagerImpl extends GenericManagerImpl<ProductMedia> implements ProductMediaManager {

	private ProductManager				productManager				= null;
	
	private ProductMediaDao productMediaDao = null;

	/**
	 * @param productMediaDao
	 *            the productMediaDao to set
	 */
	public void setProductMediaDao(ProductMediaDao productMediaDao) {
		this.productMediaDao = productMediaDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productMediaDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductMedia entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductMedia entity) {

	}

	/**
	 * 保存产品更多图片
	 */
	public List<String> saveProductMedias(Integer productId, String[] productMediaIds,
			String[]productMediaTypes,String[] mediaUrls, String[] mediaUrls_d, String[] mediaDescription, String[] mediaProductSkus ,String productMedia_deleteds[]) {
		Product product=productManager.getById(productId);
		List<String> productMediaIdList=Arrays.asList(productMediaIds);
		Set<ProductMedia> productMedias=product.getProductMedias();
		//删除没有关联的产品媒体
		for (ProductMedia productMedia : productMedias) {
			if(!productMediaIdList.contains(productMedia.getProductMediaId().toString())){
				deleteById(productMedia.getProductMediaId());
			}
		}
		//更新/新增产品媒体
		List<String>newProductMediaIds=new ArrayList<String>();
		for (int i = 0; i < productMediaIds.length; i++) {
			ProductMedia productMedia=null;
			Integer productMediaId=Integer.valueOf(productMediaIds[i]);
			//对指定要删除的媒体进行删除
			if(productMedia_deleteds[i].equals("1")){
				if(productMediaId>0){
					deleteById(productMediaId);
				}
				continue;
			}
			if(productMediaId>0){
				productMedia=getById(productMediaId);
			}
			if(productMedia==null){
				productMedia=new ProductMedia();
				productMedia.setProductId(productId);
			}
			productMedia.setMediaUrl(i < mediaUrls.length ? mediaUrls[i] : "");
			productMedia.setMediaUrlLarge(i < mediaUrls_d.length ? mediaUrls_d[i] : "");
			productMedia.setMediaType(Short.valueOf(productMediaTypes[i]));
			productMedia.setMediaDescription(mediaDescription[i]);
			productMedia.setProductSkuId(Integer.parseInt(mediaProductSkus[i]));
			productMedia.setSortOrder(i);
			save(productMedia);
			if(productMediaId<0){
				newProductMediaIds.add(productMediaId+"_"+productMedia.getProductMediaId());
			}
		}
		return newProductMediaIds;
	}
	
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public List<ProductMedia> findProductMediaByProductIdAndType(Integer productId, Short...mediaType) {
		return productMediaDao.findProductMediaByProductIdAndType(productId, mediaType);
	}

	@Override
	public boolean existImageReference(String imageUrl) {
		return productMediaDao.existImageReference(imageUrl);
	}
}

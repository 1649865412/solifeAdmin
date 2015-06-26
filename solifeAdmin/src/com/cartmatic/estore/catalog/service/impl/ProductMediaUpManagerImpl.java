package com.cartmatic.estore.catalog.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartmatic.estore.catalog.dao.ProductMediaUpDao;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductMediaUpManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductMediaUp;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductMedia, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductMediaUpManagerImpl extends GenericManagerImpl<ProductMediaUp> implements ProductMediaUpManager {

	private ProductManager				productManager				= null;
	
	private ProductMediaUpDao productMediaUpDao = null;

	/**
	 * @param productMediaDao
	 *            the productMediaDao to set
	 */
	public void setProductMediaUpDao(ProductMediaUpDao productMediaUpDao) {
		this.productMediaUpDao = productMediaUpDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productMediaUpDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductMediaUp entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductMediaUp entity) {

	}

	/**
	 * 保存产品更多图片
	 */
	public List<String> saveProductMedias(Integer productId, String[] productMediaIds,
			String[]productMediaTypes,String[] mediaUrls, String[] mediaUrls_d, String[] mediaDescription,String productMedia_deleteds[]) {
		Product product=productManager.getById(productId);
		List<String> productMediaIdList=Arrays.asList(productMediaIds);
		Set<ProductMediaUp> productMedias=product.getProductMediasUp();
		//删除没有关联的产品媒体
		for (ProductMediaUp productMedia : productMedias) {
			if(!productMediaIdList.contains(productMedia.getProductMediaUpId().toString())){
				deleteById(productMedia.getProductMediaUpId());
			}
		}
		//更新/新增产品媒体
		List<String>newProductMediaIds=new ArrayList<String>();
		for (int i = 0; i < productMediaIds.length; i++) {
			ProductMediaUp productMedia=null;
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
				productMedia=new ProductMediaUp();
				productMedia.setProductId(productId);
			}
			productMedia.setMediaUrl(i < mediaUrls.length ? mediaUrls[i] : "");
			productMedia.setMediaType(Short.valueOf(productMediaTypes[i]));
			productMedia.setMediaDescription(mediaDescription[i]);
			productMedia.setSortOrder(i);
			save(productMedia);
			if(productMediaId<0){
				newProductMediaIds.add(productMediaId+"_"+productMedia.getProductMediaUpId());
			}
		}
		return newProductMediaIds;
	}
	
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public List<ProductMediaUp> findProductMediaByProductIdAndType(Integer productId, Short...mediaType) {
		return productMediaUpDao.findProductMediaByProductIdAndType(productId, mediaType);
	}

	@Override
	public boolean existImageReference(String imageUrl) {
		return productMediaUpDao.existImageReference(imageUrl);
	}
}

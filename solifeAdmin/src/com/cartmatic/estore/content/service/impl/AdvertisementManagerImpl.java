package com.cartmatic.estore.content.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.content.Advertisement;
import com.cartmatic.estore.common.model.content.ProductAdvertisement;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.content.service.AdvertisementManager;
import com.cartmatic.estore.content.service.ProductAdvertisementManager;
import com.cartmatic.estore.content.dao.AdvertisementDao;


/**
 * Manager implementation for Advertisement, responsible for business processing, and communicate between web and persistence layer.
 */
public class AdvertisementManagerImpl extends GenericManagerImpl<Advertisement> implements AdvertisementManager {

	private AdvertisementDao advertisementDao = null;
	
	private ProductAdvertisementManager productAdvertisementManager=null;

	/**
	 * @param advertisementDao
	 *            the advertisementDao to set
	 */
	public void setAdvertisementDao(AdvertisementDao advertisementDao) {
		this.advertisementDao = advertisementDao;
	}

	public void setProductAdvertisementManager(
			ProductAdvertisementManager productAdvertisementManager) {
		this.productAdvertisementManager = productAdvertisementManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = advertisementDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Advertisement entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Advertisement entity) {

	}

	@Override
	public Advertisement getAdvertisementByName(String advertisementName) {
		return advertisementDao.findUniqueByProperty("advertisementName", advertisementName);
	}
	
	
	public List<Advertisement> getAllsForFront(Integer positionId) {
		return advertisementDao.getAllsForFront(positionId);
	}

	@Override
	public List<Advertisement> getAvalidAdvertisements(String categoryPath,Integer adPositionTypeId) {
		if(StringUtils.isNotBlank(categoryPath)){
			categoryPath="."+categoryPath;
		}
		List<Advertisement> returnList = new ArrayList<Advertisement>();
		List<Advertisement> advertisementList = getAllsForFront(adPositionTypeId);
		
		en1:for (Advertisement advertisement : advertisementList) {
			Set<ProductAdvertisement> productAdvertisements = advertisement.getProductAdvertisements();
			if(productAdvertisements==null||productAdvertisements.size()==0){
				returnList.add(advertisement);
				continue en1;
			}
			if(StringUtils.isBlank(categoryPath)){
				continue en1;
			}
			Object[] os = productAdvertisements.toArray();
			if(advertisement.getIsInclude() == null||advertisement.getIsInclude() == 0){
               for(int i=0;i<os.length;i++){
            	   ProductAdvertisement pa = (ProductAdvertisement)os[i];
            	   if(categoryPath.endsWith("."+pa.getCategoryId()+".")){
            		   returnList.add(advertisement);
						continue en1;
            	   }
               }
			}else{// 包含子目录
				for(int i=0;i<os.length;i++){
					ProductAdvertisement pa = (ProductAdvertisement)os[i];
					 if (categoryPath.indexOf("." + pa.getCategoryId()+".") != -1) {
						returnList.add(advertisement);
						continue en1;
					}
				}
			}
		}
		return returnList;
	}

	@Override
	public void saveAdvertisement(Advertisement advertisement,String[] categoryIds) {
		if(categoryIds==null){
			categoryIds=new String[]{};
		}
		save(advertisement);
		Set<ProductAdvertisement> productAdvertisements=advertisement.getProductAdvertisements();
		Set<ProductAdvertisement>removedProductAdvertisements=new HashSet<ProductAdvertisement>();
		//删除没有关联的目录
		for (ProductAdvertisement productAdvertisement : productAdvertisements) {
			int index=Arrays.binarySearch(categoryIds, productAdvertisement.getCategory().getCategoryId()+"");
			if(index>=0){
				categoryIds=(String[]) ArrayUtils.remove(categoryIds, index);
			}else {
				removedProductAdvertisements.add(productAdvertisement);
				productAdvertisementManager.deleteById(productAdvertisement.getProductAdvertisementId());
			}
		}
		productAdvertisements.removeAll(removedProductAdvertisements);
		//增加新关联的目录
		for (String categoryId : categoryIds) {
			ProductAdvertisement productAdvertisement = new ProductAdvertisement();
			productAdvertisement.setAdvertisement(advertisement);
			productAdvertisement.setCategoryId(new Integer(categoryId));
			//TODO，准备删除categoryPath,用categoryId替代
			productAdvertisementManager.save(productAdvertisement);
		}
	}
}
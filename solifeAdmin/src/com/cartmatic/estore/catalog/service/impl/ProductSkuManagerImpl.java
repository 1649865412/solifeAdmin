package com.cartmatic.estore.catalog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductSkuDao;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.ProductSkuOptionValueManager;
import com.cartmatic.estore.catalog.service.SkuOptionValueManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


public class ProductSkuManagerImpl extends GenericManagerImpl<ProductSku> implements ProductSkuManager {
	private ProductSkuOptionValueManager productSkuOptionValueManager=null;
	private ProductSkuDao productSkuDao = null;
	private ProductManager productManager=null; 
    private SkuOptionValueManager skuOptionValueManager = null;

	public void setSkuOptionValueManager(SkuOptionValueManager skuOptionValueManager) {
		this.skuOptionValueManager = skuOptionValueManager;
	}

	/**
	 * @param productSkuDao
	 *            the productSkuDao to set
	 */
	public void setProductSkuDao(ProductSkuDao productSkuDao) {
		this.productSkuDao = productSkuDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productSkuDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductSku entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductSku entity) {

	}
	
	public List<ProductSku> findActiveProductSkusByProductId(Integer productId) {
		return productSkuDao.findActiveProductSkusByProductId(productId);
	}

	public ProductSku getProductSkuByProductSkuCode(String productSkuCode) {
		return productSkuDao.findUniqueByProperty("productSkuCode", productSkuCode);
	}

	public void saveProductSkuOptions(Integer productSkuId, Set<Integer> skuOptionValueIds) {
		if (logger.isDebugEnabled()) {
            logger.debug("entering 'ProductSkuManagerImpl saveProductSkuOptions' method...");
        }
		//当Sku选项发生变化时，需更新该产品其他sku状态
		boolean updateStatus=false;
		Set<Integer> currSkuOptionValueIds=new HashSet<Integer>(skuOptionValueIds);
		//本方法不理会SkuOption和OptionValue的状态，以及是否与所属的产品类型是否有关联
		ProductSku productSku=getById(productSkuId);
		Set<ProductSkuOptionValue>productSkuOptionValues=productSku.getProductSkuOptionValues();
		//清除取消的选项
		for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
			if(skuOptionValueIds.contains(productSkuOptionValue.getSkuOptionValueId())){
				skuOptionValueIds.remove(productSkuOptionValue.getSkuOptionValueId());
			}else{
				updateStatus=true;
				if (logger.isDebugEnabled()) {
		            logger.debug("清除取消的选项："+productSkuOptionValue.getSkuOptionValue().getSkuOptionValueName());
		        }
				productSkuOptionValueManager.deleteById(productSkuOptionValue.getProductSkuOptionValueId());
			}
		}
		//添加新增选项
		for (Integer skuOptionValueId : skuOptionValueIds) {
			updateStatus=true;
			ProductSkuOptionValue productSkuOptionValue=new ProductSkuOptionValue();
			productSkuOptionValue.setProductSku(productSku);
			SkuOptionValue skuOptionValue=skuOptionValueManager.getById(skuOptionValueId);
			productSkuOptionValue.setSkuOptionValue(skuOptionValue);
			if (logger.isDebugEnabled()) {
	            logger.debug("添加新增选项："+productSkuOptionValue.getSkuOptionValue().getSkuOptionValueName());
	        }
			productSkuOptionValueManager.save(productSkuOptionValue);
		}
		
		//当更新的是产品的默认sku时，需要检查其他sku是否可激活
		Set<Integer> defaultSkuOptionIds=new HashSet<Integer>();
		ProductSku defaultProductSku=productManager.getById(productSku.getProductId()).getDefaultProductSku();
		if(defaultProductSku.getProductSkuId().intValue()==productSkuId.intValue()){
			for (Integer skuOptionValueId : currSkuOptionValueIds) {
				SkuOptionValue skuOptionValue=skuOptionValueManager.getById(skuOptionValueId);
				defaultSkuOptionIds.add(skuOptionValue.getSkuOptionId());
			}
		}
		if(updateStatus&&defaultProductSku.getProductSkuId().intValue()==productSkuId.intValue()){
			updateProductSkuStatusByDefaultSkuOptionIds(productSku.getProductId(), defaultSkuOptionIds,productSku.getProductSkuId());
		}
	}
	
	
	/**
	 * 更新产品其他Sku状态
	 * @param productId
	 * @param defaultSkuOptionIds
	 * @param currSkuId 
	 */
	private void updateProductSkuStatusByDefaultSkuOptionIds(Integer productId,Set<Integer> defaultSkuOptionIds,Integer currSkuId){
		List<ProductSku>productSkus=findActiveProductSkusByProductId(productId);
		for (ProductSku tempProductSku : productSkus) {
			if(currSkuId.intValue()==tempProductSku.getProductSkuId().intValue()||tempProductSku.getStatus()==Constants.STATUS_INACTIVE){
				continue;
			}
			boolean isSame=true;
			Set<ProductSkuOptionValue>productSkuOptionValues=tempProductSku.getProductSkuOptionValues();
			if(defaultSkuOptionIds.size()==productSkuOptionValues.size()){
				for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
					if(!defaultSkuOptionIds.contains(productSkuOptionValue.getSkuOptionValue().getSkuOptionId().intValue())){
						isSame=false;
						break;
					}
				}
			}else{
				isSame=false;
			}
			if(!isSame){
				if(logger.isInfoEnabled()){
					logger.info("修改Sku"+currSkuId+"导致其他Sku"+tempProductSku.getId()+" 与默认sku选项不一致，系统将其设置为非激活.");
				}
				tempProductSku.setStatus(Constants.STATUS_INACTIVE);
				productSkuDao.save(tempProductSku);
			}
		}
	}
	
	/**
	 * 更新产品Sku状态，将与默认sku不一致的，更新为非激活状态
	 * @param productId
	 */
	private void updateProductSkuStatusByProduct(Integer productId){
		Product product=productManager.getById(productId);
		List<ProductSku>productSkus=findActiveProductSkusByProductId(product.getProductId());
		for (ProductSku tempProductSku : productSkus) {
			if(tempProductSku.getStatus()==Constants.STATUS_INACTIVE){
				break;
			}
			boolean isSame=checkSkuOptionIsSameWithDefaultSku(tempProductSku.getId());
			if(!isSame){
				tempProductSku.setStatus(Constants.STATUS_INACTIVE);
				productSkuDao.save(tempProductSku);
			}
		}
	}
	
	/**
	 * 检查Sku选项属性是否与默认Sku一致
	 * @param productSkuId
	 * @return
	 */
	private boolean checkSkuOptionIsSameWithDefaultSku(Integer productSkuId){
		ProductSku productSku=getById(productSkuId);
		
		ProductSku defaultProductSku=productManager.getById(productSku.getProductId()).getDefaultProductSku();
		if(defaultProductSku.getProductSkuId().intValue()==productSkuId.intValue()){
			return true;
		}
		Set<ProductSkuOptionValue>defaultProductSkuOptionValues=defaultProductSku.getProductSkuOptionValues();
		Set<ProductSkuOptionValue>productSkuOptionValues=productSku.getProductSkuOptionValues();
		if(defaultProductSkuOptionValues==null||productSkuOptionValues==null||defaultProductSkuOptionValues.size()!=productSkuOptionValues.size()){
			return false;
		}
		Set<Integer> defaultSkuOptionIds=new HashSet<Integer>();
		for (ProductSkuOptionValue productSkuOptionValue : defaultProductSkuOptionValues) {
			defaultSkuOptionIds.add(productSkuOptionValue.getSkuOptionValue().getSkuOptionId());
		}
		boolean isSame=true;
		for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
			if(!defaultSkuOptionIds.contains(productSkuOptionValue.getSkuOptionValue().getSkuOptionId().intValue())){
				isSame=false;
				break;
			}
		}
		return isSame;
	}
	
	public ProductSku getSkuInPorudctByOptionValueIds(Integer productId,Set<Integer> skuOptionValueIds) {
		Product product=productManager.getById(productId);
		Set<ProductSku> productSkus=product.getProductSkus();
		for (ProductSku productSku : productSkus) {
			Set<ProductSkuOptionValue> productSkuOptionValues=productSku.getProductSkuOptionValues();
			boolean isSame=checkSkuOptionValuesIsSame(productSkuOptionValues, skuOptionValueIds);
			if(isSame){
				return productSku;
			}
		}
		return null;
	}
	
	/**
	 * 检查两集合id，是否一致
	 * @param productSkuOptionValues
	 * @param skuOptionValueIds
	 * @return
	 */
	private boolean checkSkuOptionValuesIsSame(Set<ProductSkuOptionValue> productSkuOptionValues,Set<Integer>skuOptionValueIds){
		//选项数量不一致的忽然
		if(productSkuOptionValues.size()!=skuOptionValueIds.size()){
			return false;
		}
		boolean isSame=true;
		for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
			if(!skuOptionValueIds.contains(productSkuOptionValue.getSkuOptionValue().getSkuOptionValueId().intValue())){
				isSame=false;
				break;
			}

		}
		return isSame;
	}
	
	
	
	public boolean isSkuOptionIsSameWithSku(Integer productSkuId,Set<Integer> skuOptionValueIds){
		ProductSku productSku=getById(productSkuId);
		
		Set<Integer> defaultSkuOptionIds=new HashSet<Integer>();
		Set<ProductSkuOptionValue>defaultProductSkuOptionValues=productSku.getProductSkuOptionValues();
		for (ProductSkuOptionValue productSkuOptionValue : defaultProductSkuOptionValues) {
			defaultSkuOptionIds.add(productSkuOptionValue.getSkuOptionValue().getSkuOptionId());
		}
		
		Set<Integer> currSkuOptionIds=new HashSet<Integer>();
		for (Integer skuOptionValueId : skuOptionValueIds) {
			SkuOptionValue skuOptionValue=skuOptionValueManager.getById(skuOptionValueId);
			currSkuOptionIds.add(skuOptionValue.getSkuOptionId());
		}
		
		boolean isSame=true;
		if(defaultSkuOptionIds.size()==currSkuOptionIds.size()){
			for (Integer defaultSkuOptionId : defaultSkuOptionIds) {
				if(!currSkuOptionIds.contains(defaultSkuOptionId.intValue())){
					isSame=false;
					break;
				}
			}
		}else{
			isSame=false;
		}
		return isSame;
	}
	
	public boolean compare2SkuOptionIsSame(ProductSku productSku1,ProductSku productSku2){
		boolean isSame=true;
		Set<ProductSkuOptionValue>productSkuOptionValues1=productSku1.getProductSkuOptionValues();
		Set<ProductSkuOptionValue>productSkuOptionValues2=productSku2.getProductSkuOptionValues();
		if(productSkuOptionValues1.size()!=productSkuOptionValues2.size()){
			return false;
		}
		for (ProductSkuOptionValue productSkuOptionValue1 : productSkuOptionValues1) {
			boolean exist=false;
			for (ProductSkuOptionValue productSkuOptionValue2 : productSkuOptionValues2) {
				if(productSkuOptionValue1.getSkuOptionValue().getSkuOptionId().intValue()==productSkuOptionValue2.getSkuOptionValue().getSkuOptionId().intValue()){
					exist=true;
					break;
				}
			}
			if(!exist){
				return false;
			}
		}
		return isSame;
	}

	public void doSetDefaultProductSku(Integer productSkuId) {
		if (logger.isDebugEnabled()) {
            logger.debug("entering 'ProductSkuManagerImpl doSetDefaultProductSku' method...");
        }
		ProductSku productSku=getById(productSkuId);
		Product product=productSku.getProduct();
		if(product.getDefaultProductSkuId()!=productSku.getProductSkuId()){
			product.setDefaultProductSku(productSku);
			productSku.setProduct(product);
			productManager.save(product);
			save(productSku);
			flush();
			updateProductSkuStatusByProduct(product.getProductId());
		}
	}
	
	
	public ProductSku getDefaultProductSkuByProductId(Integer productId){
		return productSkuDao.getDefaultProductSkuByProductId(productId);
	}

	public void setProductSkuOptionValueManager(
			ProductSkuOptionValueManager productSkuOptionValueManager) {
		this.productSkuOptionValueManager = productSkuOptionValueManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	@Override
	public boolean existImageReference(String imageUrl) {
		return productSkuDao.existImageReference(imageUrl);
	}
	
	

}

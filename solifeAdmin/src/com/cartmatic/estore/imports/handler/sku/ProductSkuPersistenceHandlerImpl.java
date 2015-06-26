package com.cartmatic.estore.imports.handler.sku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.imports.handler.PersistenceHandler;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductSkuPersistenceHandlerImpl implements PersistenceHandler{
	private Logger logger=Logger.getLogger(ProductSkuPersistenceHandlerImpl.class);
	private ProductSkuManager	productSkuManager	= null;
	private ProductMainManager productMainManager=null;
	private ProductManager					productManager	= null;
    private ProductStatManager productStatManager=null;

	@SuppressWarnings("unchecked")
	public void saveOrUpdate(ImportModel importModel) {
		if(logger.isInfoEnabled()){
			logger.info("保存更新SKU");
		}
		ProductSku productSku=(ProductSku)importModel.getTarget();
		Product product=productSku.getProduct();
		productManager.save(product);
		Set<Integer>skuOptionValueIds=(Set<Integer>)importModel.getImportTargetData().get("skuOptionValueIds");
		String []wholesalePrices=(String [])importModel.getImportTargetData().get("wholesalePrices");
		//保存更新Sku前检查数据
		if(product.getProductKind().intValue()!=2){
			//保存普通产品数据
			Short tempStatus=productSku.getStatus();
			productMainManager.saveSimpleProductSku(product, productSku, wholesalePrices);
			productSku.setStatus(tempStatus);
		}else{
			//保存更新变种产品Sku信息
			productMainManager.saveProductSkuAction(productSku, skuOptionValueIds, wholesalePrices);
		}
		//普通产品根据sku更新产品状态
		if(product.getProductKind().intValue()!=CatalogConstants.PRODUCT_KIND_VARIATION.intValue()){
			product.setStatus(productSku.getStatus());
			productManager.save(product);
		}else{
			//成功处理完Sku后，如果该产品是草稿状态的，要将其设置激活，其它状态的不作更新
			if(product.getStatus().intValue()==Constants.STATUS_NOT_PUBLISHED.intValue()){
				product.setStatus(Constants.STATUS_ACTIVE);
				productManager.save(product);
				logger.info("产品状态转为激活状态，"+product);
			}
		}
		importModel.setResult("1");
	}
	

	
	@SuppressWarnings("unchecked")
	public void validate(ImportModel importModel){
		ProductSku productSku=(ProductSku)importModel.getTarget();
		Product product=productSku.getProduct();
		
		//保存更新Sku前检查数据
		if(product.getProductKind().intValue()!=2){
			//保存普通产品数据
		}else{
			Set<Integer>skuOptionValueIds=(Set<Integer>)importModel.getImportTargetData().get("skuOptionValueIds");
			//当为变种产品时，并且导入的数据文件存在Sku属性选项的，需简单当前Sku设置sku选项是否已经存在其他的Sku
			if(skuOptionValueIds!=null&&skuOptionValueIds.size()!=0){
				ProductSku tempProductSku2=productSkuManager.getSkuInPorudctByOptionValueIds(productSku.getProductId(), skuOptionValueIds);
				if((tempProductSku2!=null&&productSku.getId()==null)||(tempProductSku2!=null&&productSku.getId()!=null&&productSku.getId().intValue()!=tempProductSku2.getProductSkuId().intValue())){
					importModel.setResult("-1");
					logger.warn("指定的Sku选项'"+Arrays.toString(skuOptionValueIds.toArray())+"'，系统已经存在相应的ProductSku:"+tempProductSku2);
					return;
				}
			}else if(skuOptionValueIds==null){
				//当为变种产品时,并且数据文件没有配置Sku选项时，就需要预先获取该Sku选项
				skuOptionValueIds=new HashSet<Integer>();
				Set<ProductSkuOptionValue> productSkuOptionValues=productSku.getProductSkuOptionValues();
				if(productSkuOptionValues!=null){
					for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
						skuOptionValueIds.add(productSkuOptionValue.getSkuOptionValueId());
					}
				}
			}
			if(skuOptionValueIds.size()==0){
				logger.warn("当前SKU属于变种产品，但没有指定相应的Sku选项，系统无法进行下一步处理。");
				importModel.setResult("-1");
			}else{
				//检查该sku是否可设置为激活
//				ProductSku defaultProductSku=productSkuManager.getDefaultProductSkuByProductId(product.getProductId());
				ProductSku defaultProductSku=product.getDefaultProductSku();
				if(defaultProductSku!=null&&defaultProductSku.getId()!=null&&(productSku.getId()==null||defaultProductSku.getId().intValue()!=productSku.getId().intValue())&&productSku.getStatus().intValue()==Constants.STATUS_ACTIVE.intValue()){
					boolean isSame=productSkuManager.isSkuOptionIsSameWithSku(defaultProductSku.getId(), skuOptionValueIds);
					if(!isSame){
						logger.warn("指定的Sku选项'"+Arrays.toString(skuOptionValueIds.toArray())+"'与当前产品默认SKU不一致，不能设置为激活状态"+productSku);
						productSku.setStatus(Constants.STATUS_INACTIVE);
					}
				}
			}
		}
		
		
	}


	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}


	public void setProductStatManager(ProductStatManager productStatManager) {
		this.productStatManager = productStatManager;
	}

}

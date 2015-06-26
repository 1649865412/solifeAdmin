package com.cartmatic.estore.imports.handler.product;

import java.util.List;

import org.apache.log4j.Logger;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.ProductDescriptionManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductMediaManager;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.imports.handler.PersistenceHandler;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductPersistenceHandlerImpl implements PersistenceHandler{
	private Logger logger=Logger.getLogger(ProductPersistenceHandlerImpl.class);
	private ProductMainManager productMainManager=null;
	private ProductDescriptionManager	productDescriptionManager	= null;
	private AttributeService attributeService=null;
	private ProductMediaManager			productMediaManager			= null;
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(ImportModel importModel) {
		if(logger.isInfoEnabled()){
			logger.info("保存更新产品");
		}
		Product  product=(Product)importModel.getTarget();
		//增加时统一为草稿状态,否则不更新其状态
		if(product.getId()==null)
			product.setStatus(Constants.STATUS_NOT_PUBLISHED);
		
		if(product.getProductDescription()==null){
			importModel.setResult("-1");
			logger.warn("没法创建产品描述");
			return ;
		}
		productDescriptionManager.save(product.getProductDescription());

		Integer mainCategoryId=(Integer)importModel.getImportTargetData().get("mainCategoryId");
		Integer categoryIds[]=(Integer[])importModel.getImportTargetData().get("categoryIds");
		productMainManager.saveProductAction(product, new Integer[]{mainCategoryId},categoryIds);
		//保存、更新自定义属性
		List<AttributeValue>  attributeValueList=(List<AttributeValue>)importModel.getImportTargetData().get("attributeValueList");
		if(attributeValueList!=null)
			attributeService.saveOrUpdateAttributeValue(attributeValueList, product);
		// 更新保存产品媒体
		String productMediaIds[]=(String[])importModel.getImportTargetData().get("productMediaIds");
		String mediaUrls[]=(String[])importModel.getImportTargetData().get("mediaUrls");
		String mediaUrls_d[]=(String[])importModel.getImportTargetData().get("mediaUrls_d");
		String mediaProductSkus[] = (String[])importModel.getImportTargetData().get("mediaProductSkus");
		String mediaDescriptions[]=(String[])importModel.getImportTargetData().get("mediaDescriptions");
		String productMediaTypes[]=(String[])importModel.getImportTargetData().get("productMediaTypes");
		if(mediaUrls!=null){
			String productMedia_deleteds[]=new String[mediaUrls.length];
			for (int i = 0; i < mediaUrls.length; i++) {
				productMedia_deleteds[i]="0";
			}
			productMediaManager.saveProductMedias(product.getProductId(), productMediaIds,productMediaTypes, mediaUrls, mediaUrls_d, mediaDescriptions,mediaProductSkus,productMedia_deleteds);
			
		}
		//保存供应商信息 @ TODO hardcode默认供应商
		productMainManager.saveProductSuppliersAction(product,-1,new Integer[]{-1});
		importModel.setResult("1"); 
	}
	
	public void validate(ImportModel importModel) {
		// 保存产品
		Integer mainCategoryId=(Integer)importModel.getImportTargetData().get("mainCategoryId");
		Integer categoryIds[]=(Integer[])importModel.getImportTargetData().get("categoryIds");
		if(mainCategoryId==null||categoryIds==null){
			importModel.setResult("-1"); 
			logger.warn("目录指定失败");
		}
	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	public void setProductDescriptionManager(
			ProductDescriptionManager productDescriptionManager) {
		this.productDescriptionManager = productDescriptionManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setProductMediaManager(ProductMediaManager productMediaManager) {
		this.productMediaManager = productMediaManager;
	}

	
}

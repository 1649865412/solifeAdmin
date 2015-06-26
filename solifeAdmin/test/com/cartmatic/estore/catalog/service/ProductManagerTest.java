package com.cartmatic.estore.catalog.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.attribute.dao.ProductAttrValueDao;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.model.catalog.ProductCompareModel;
import com.cartmatic.estore.common.model.catalog.ProductDescription;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.system.service.SiteMapManager;

public class ProductManagerTest extends BaseTransactionalTestCase{
	@Autowired
    private ProductAttGroupItemManager productAttGroupItemManager = null;
	@Autowired
	private ProductManager productManager=null;
	@Autowired
	private ProductMainManager productMainManager=null;
	@Autowired
	private ProductSkuManager	productSkuManager	= null;
	@Autowired
	private ProductAttrValueDao productAttrValueDao = null;
	@Autowired
	private SiteMapManager siteMapManager=null;
	@Autowired
	private ProductMediaManager productMediaManager=null;
	@Autowired
	private ContentManager contentManager=null;
	@Autowired
	private ProductDescriptionManager productDescriptionManager=null;
	@Autowired
	private SalesOrderManager salesOrderManager=null;
	

	
	@Test
	public void test() throws Exception{
		String msg=I18nUtil.getInstance().getMessage("salesOrder.shippingStatus_20");
		System.out.println(msg);
//		makeMapSite();
	}
	
	
	public void cancelExpireSalesOrder(){
		salesOrderManager.cancelExpireSalesOrder();
	}
	
	public void setOrderSkuAccQty(){
		OrderSku orderSku=salesOrderManager.getOrderSkuById(89);
		orderSku.setAllocatedQuantity(10);
		salesOrderManager.save(orderSku);
		
	}
	public void updateContentCode(){
		List<Content> contentList=contentManager.getAll();
		for (Content content : contentList) {
			if(StringUtils.isBlank(content.getContentCode())){
				content.setContentCode(Math.abs(UUID.randomUUID().getMostSignificantBits())+"");
				contentManager.save(content);
			}
		}
		 
	}
	
	public void updateSkuImage(){
		List<ProductSku>productSkuList=productSkuManager.getAll();
		for (ProductSku productSku : productSkuList) {
			Category category=productSku.getProduct().getMainCategory();
			if(category.getParentId().intValue()!=1){
				category=category.getParentCategorys().get(0);
			}
			
			StringBuffer image=new StringBuffer();
			image.append("product/");
			image.append(category.getCategoryCode());
			image.append(productSku.getImage());
			productSku.setImage(image.toString());
			System.out.println(productSku.getImage());
		}
		 
	}
	//src="http://image.lightinthebox.com/images/v/200812/1590056822495b199540e40.jpg" 
	public void updateProductDescription(){
		List<ProductDescription>productDescriptionList=productDescriptionManager.getAll();
		for (ProductDescription productDescription : productDescriptionList) {
			upDesc(productDescription);
		}
		
	}
	
	public void upDesc(ProductDescription productDescription){
		String fullDescription=productDescription.getFullDescription();
		if(StringUtils.isNotBlank(fullDescription)){
			fullDescription=fullDescription.replaceAll("src=\"http://image\\.lightinthebox\\.com[\\w:/\\.\\- \\\\(\\\\)]*\"","src=\"#\"");
			fullDescription=fullDescription.replaceAll("href=\"http://\\w+\\.lightinthebox\\.com[\\w:/\\.\\- \\\\(\\\\)\\?&;=]*\"","href=\"#\"");
			productDescription.setFullDescription(fullDescription);
		}
	}
	
	
	public void updateProductMediaImage(){
		List<ProductMedia>productMediaList=productMediaManager.getAll();
		for (ProductMedia productMedia : productMediaList) {
			Category category=productMedia.getProduct().getMainCategory();
			if(category.getParentId().intValue()!=1){
				category=category.getParentCategorys().get(0);
			}
			StringBuffer image=new StringBuffer();
			image.append("product/");
			image.append(category.getCategoryCode());
			image.append(productMedia.getMediaUrl());
			productMedia.setMediaUrl(image.toString());
			System.out.println(productMedia.getMediaUrl());
		}
		 
	}
	
	public void updateProductSkuCode(){
		List<Product> productList=productManager.getAll();
		for (Product product : productList) {
			if(product.getProductKind().intValue()==1){
				ProductSku productSku=product.getDefaultProductSku();
				productSku.setProductSkuCode(product.getProductCode());
				System.out.println(productSku.getProductSkuCode());
			}
		}
		 
	}
	
	public void updateProductUrl(){
		List<Product> productList=productManager.getAll();
		for (Product product : productList) {
			product.setUrl(uriReplace(product.getUrl()));
		}
		
	}
	
	private String uriReplace(String uri){
		uri=uri.replaceAll(" ","-");
		uri=uri.replaceAll("#","-");
		uri=uri.replaceAll("\\?","-");
		while(uri.indexOf("--")!=-1){
			uri=uri.replaceAll("--","-");
		}
		return uri;
	}
	
	public void makeMapSite(){
		siteMapManager.makeMapSite();
	}
	
	public void updateStatusForPublish(){
		productManager.updateStatusForPublish();
	}
	public void findSkuOptionsByProduct(){
		Map<SkuOption,List<SkuOptionValue>> productSkuOptionAndValue=productMainManager.findSkuOptionsByProduct(122);
		Set<SkuOption> skuOptions=productSkuOptionAndValue.keySet();
		for (SkuOption skuOption : skuOptions) {
			System.out.println(skuOption);
			List<SkuOptionValue>skuOptionValues =productSkuOptionAndValue.get(skuOption);
			for (SkuOptionValue skuOptionValue : skuOptionValues) {
				System.out.println(skuOptionValue);
			}
			
		}
	}
	
	public void copy() throws Exception{
		Product product=productManager.getById(107);
		Product product2=productMainManager.doCopyProduct(product);
		System.out.println(product2);
	}
	
	/**
	 * 清除没有关联的自定义属性值
	 */
	public void deleteInvalidProductAttrValue(){
		List<ProductAttrValue> productAttrValues=productAttrValueDao.getAll();
		for (ProductAttrValue productAttrValue : productAttrValues) {
			Integer attributeId=productAttrValue.getAttributeId();
			Integer productTypeId=productAttrValue.getProduct().getProductTypeId();
			ProductAttGroupItem productAttGroupItem=productAttGroupItemManager.getProductAttGroupItemByProductTypeAndAttribute(productTypeId, attributeId);
			if(productAttGroupItem==null){
				productAttrValueDao.delete(productAttrValue);
			}else{
				productAttrValue.setProductAttGroupItem(productAttGroupItem);
				productAttrValueDao.save(productAttrValue);
			}
		}
	}
	
	public void productCompare() throws Exception{
		System.out.println("________________________1_____________________");
		ProductCompareModel productCompareModel=productMainManager.getProductCompareModel(new Integer[]{1,2,107,106,119});
		System.out.println("_________________________2____________________");
		List<Product> productList=productCompareModel.getProductList();

		StringBuffer text=new StringBuffer("name______");
		for (Product product : productList) {
			text.append(product.getProductName());
			text.append("________");
		}

		logger.info(text);
		Map<Attribute, List<ProductAttrValue>> productAttribute=productCompareModel.getProductAttribute();
		logger.info(productAttribute.size());
		Set<Attribute>attributes=productAttribute.keySet();
		for (Attribute attribute : attributes) {
			StringBuffer attrtext=new StringBuffer(attribute.getId()+"_name______");
			attrtext.append(attribute.getAttributeName());
			attrtext.append("______value:");
			List<ProductAttrValue> productAttrValues=productAttribute.get(attribute);
			for (ProductAttrValue productAttrValue : productAttrValues) {
				if(productAttrValue!=null)
					attrtext.append(productAttrValue.getAttributeValue());
				attrtext.append("___");
			}
			logger.info(attrtext);
		}
		
		logger.info("______________123_________________");
		Map<SkuOption, List<List<SkuOptionValue>>>productSkuOptionValue=productCompareModel.getProductSkuOptionValue();
		Set<SkuOption>skuOptions=productSkuOptionValue.keySet();
		for (SkuOption skuOption : skuOptions) {
			StringBuffer skuOptiontext=new StringBuffer(skuOption.getId()+"_name______");
			skuOptiontext.append(skuOption.getSkuOptionName());
			skuOptiontext.append("___value:");
			List<List<SkuOptionValue>> productsSkuOptionValueList=productSkuOptionValue.get(skuOption);
			for (List<SkuOptionValue> list : productsSkuOptionValueList) {
				if(list!=null){
					for (SkuOptionValue skuOptionValue : list) {
						skuOptiontext.append(skuOptionValue.getSkuOptionValue());
						skuOptiontext.append(",");
					}
					skuOptiontext.append("_____");
				}
			}
			logger.info(skuOptiontext);
		}
	}
}

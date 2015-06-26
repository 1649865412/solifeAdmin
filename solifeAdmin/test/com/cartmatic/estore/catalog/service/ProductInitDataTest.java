package com.cartmatic.estore.catalog.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.attribute.dao.ProductAttrValueDao;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductInitDataTest  extends BaseTransactionalTestCase
{

	@Autowired
	private ProductSkuManager productSkuManager=null;
	@Autowired
	private InventoryService inventoryService=null;
	@Autowired
    private ProductAttGroupItemManager productAttGroupItemManager = null;
	@Autowired
	private ProductManager productManager=null;
	@Autowired
	private ProductAttrValueDao productAttrValueDao = null;
	@Autowired
    private ProductStatManager productStatManager = null;
	
	@Test
	@Rollback(false)
	public void testMethod(){
//		initSKUInitInventory();
//		deleteInvalidProductAttrValue();
	}
	
	/**
	 * 初始化库存信息
	 */
	public void initSKUInitInventory(){
		List<ProductSku>productSkus=productSkuManager.getAllOrdered("productSkuId",true);
		for (ProductSku productSku : productSkus) {
			inventoryService.doInitInventoryByCreateProduct(productSku);
		}
		
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
	
}

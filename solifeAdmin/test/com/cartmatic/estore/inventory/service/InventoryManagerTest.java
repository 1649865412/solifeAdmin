package com.cartmatic.estore.inventory.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.InventoryAudit;
import com.cartmatic.estore.common.model.inventory.InventoryModel;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class InventoryManagerTest extends BaseTransactionalTestCase
{
	@Autowired
    private InventoryAuditManager inventoryAuditManager = null;
	@Autowired
	private InventoryManager inventoryManager=null;
	@Autowired
	private ProductSkuManager productSkuManager=null;
	@Autowired
	private InventoryService inventoryService=null;
	
	
	@Test
	@Rollback(false)
	public void testMethod(){
		initSKUInitInventory();
//		this.setComplete();
	}
	
	public void getInventory(){
		Inventory inventory=inventoryManager.getInventoryBySkuCode("copy_test");
		Inventory inventory2=inventoryManager.getInventoryBySkuCode("copy_test");
		Inventory inventory3=inventoryManager.getInventoryBySkuCode("copy_test");
		inventory2.setCreateTime(new Date());
		inventoryManager.getInventoryBySkuCode("test");
		System.out.println(inventory==inventory2);
		System.out.println(inventory3==inventory2);
		Inventory inventory7=inventoryManager.getInventoryBySku(152);
		System.out.println(inventory==inventory7);
		
		Inventory inventory4=inventoryManager.loadForUpdate(38);
		Inventory inventory5=inventoryManager.loadForUpdate(38);
		Inventory inventory6=inventoryManager.loadForUpdate(38);
		System.out.println(inventory4==inventory5);
		System.out.println(inventory5==inventory6);
	}
	
	public void setProductSku(){
		List<InventoryAudit>inventoryAudits=inventoryAuditManager.getAll();
		for (InventoryAudit inventoryAudit : inventoryAudits) {
			inventoryAudit.setProductSku(inventoryAudit.getInventory().getProductSku());
		}
		
	}
	
	public void doAdjustStockQuantity(){
		InventoryModel inventoryModel=new InventoryModel();
		inventoryModel.setAdjustmentComment("aaaaaaaaaaaaaaaaaaaa");
		inventoryModel.setAdjustmentQuantity(10);
		inventoryModel.setAdjustmentReason("bbbbbbbbbbbbbbb");
		inventoryModel.setAdjustmentType(new Short("1"));
		inventoryModel.setExpectedRestockDate(new Date());
		inventoryModel.setProductSkuId(10);
		inventoryModel.setReorderMinimnm(5);
		inventoryModel.setReorderQuantity(15);
		inventoryModel.setReservedQuantity(8);
		
//		inventoryManager.saveInventoryAction(inventoryModel);
		
	}
	
	public void doAdjustStockQuantity2(){
		InventoryModel inventoryModel=new InventoryModel();
//		inventoryModel.setAdjustmentComment("aaaaaaaaaaaaaaaaaaaa");
//		inventoryModel.setAdjustmentQuantity(3);
//		inventoryModel.setAdjustmentReason("bbbbbbbbbbbbbbb");
//		inventoryModel.setAdjustmentType(new Short("2"));
		inventoryModel.setExpectedRestockDate(new Date());
		inventoryModel.setProductSkuId(10);
		inventoryModel.setReorderMinimnm(7);
		inventoryModel.setReorderQuantity(15);
		inventoryModel.setReservedQuantity(8);
		
//		inventoryManager.saveInventoryAction(inventoryModel);
		
	}
	
	
	public void selectForUpdate(){
//		InventoryThread it=new InventoryThread(38,inventoryManager);
//		 new Thread(it).start();
//		 new Thread(it).start();
//		 new Thread(it).start();
	}
	public static void main(String[] args) {
		InventoryManagerTest it=new InventoryManagerTest();	
		it.iii();
	}
	
	public void iii(){
		InventoryThread it=new InventoryThread(38,inventoryManager);
		 new Thread(it).start();
		 new Thread(it).start();
		 new Thread(it).start();
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
	public void setInventoryAuditManager(InventoryAuditManager inventoryAuditManager) {
		this.inventoryAuditManager = inventoryAuditManager;
	}
	
	
	private class InventoryThread implements Runnable {
		private Integer skuId;
		private InventoryManager inventoryManager=null;
		public InventoryThread(Integer skuId,InventoryManager inventoryManager){
			this.skuId=skuId;
			this.inventoryManager=inventoryManager;
		}
		
		public void run() {
		}
		
	}
}

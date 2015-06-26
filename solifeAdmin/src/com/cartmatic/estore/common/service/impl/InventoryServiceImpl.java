package com.cartmatic.estore.common.service.impl;

import java.util.Set;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.SkuInventoryVO;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.inventory.service.InventoryManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class InventoryServiceImpl implements InventoryService {
    private InventoryManager inventoryManager = null;


	public Integer[] doAllocateProductSku(SalesOrder salesOrder,ProductSku productSku, Integer quantity) throws OutOfStockException{
		AppUser appUser=(AppUser)RequestContext.getCurrentUser();
		StringBuffer handler;
		 if(appUser!=null&&appUser.getUserType().intValue()==AppUser.USER_TYPE_ADMIN.intValue()){
			 	handler=new StringBuffer("CM User/");
				handler.append(appUser.getAppuserId());
				handler.append("/");
				handler.append(appUser.getUsername());
		 }else{
			 handler=new StringBuffer("StoreFront");
		 }
		return inventoryManager.doAllocateProductSku(salesOrder, productSku, quantity, handler.toString());
	}
	
	public Integer doReAllocateStockForPreSKU(SalesOrder salesOrder,ProductSku productSku, Integer quantity) { 
		return inventoryManager.doReAllocateStockForPreSKU(salesOrder, productSku, quantity);
	}
	
	public void doCancelAllocate(SalesOrder salesOrder,
			ProductSku productSku, Integer quantity) {
		AppUser appUser=(AppUser)RequestContext.getCurrentUser();
		StringBuffer handler;
		if(appUser!=null&&appUser.getUserType().intValue()==AppUser.USER_TYPE_ADMIN.intValue()){
			handler=new StringBuffer("CM User/");
			handler.append(appUser.getAppuserId());
			handler.append("/");
			handler.append(appUser.getUsername());
		}else{
			handler=new StringBuffer("StoreFront");
		}
		inventoryManager.doCancelAllocate(salesOrder, productSku, quantity, handler.toString());
	}
	
	public void doCancelAllocatePreOrBackOrderedQty(ProductSku productSku,
			Integer quantity) {
		if(quantity!=null&&quantity>0)
			inventoryManager.doCancelAllocatePreOrBackOrderedQty(productSku, quantity);
	}

	public void doReleaseStock(SalesOrder salesOrder, ProductSku productSku,
			Integer quantity) {
		AppUser appUser=(AppUser)RequestContext.getCurrentUser();
		StringBuffer handler=new StringBuffer("CM User/");
		handler.append(appUser.getAppuserId());
		handler.append("/");
		handler.append(appUser.getUsername());
		inventoryManager.doReleaseStockt(salesOrder, productSku, quantity, handler.toString());
	}
	
	public void doInitInventoryByCreateProduct(ProductSku productSku) {
		//不管销售规则是什么，统一创建库存对象
		//int availabilityRule=productSku.getProduct().getAvailabilityRule().intValue();
		//if (availabilityRule!=CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()&&availabilityRule!=CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()) {
			if(getInventoryBySku(productSku.getId())==null){
				Inventory inventory=new Inventory();
				inventory.setProductSku(productSku);
				inventory.setQuantityOnHand(0);
				inventory.setAllocatedQuantity(0);
				inventory.setReservedQuantity(0);
				inventory.setReorderMinimnm(0);
				inventory.setReorderQuantity(0);
				inventory.setPreOrBackOrderedQty(0);
				inventoryManager.save(inventory);
			}
		//}
	}
	
	public boolean isHasStockOrOrderedPreOrBackOrderByProduct(Product product){
		Set<ProductSku> productSkus=product.getProductSkus();
		boolean isHasStock=false;
		for (ProductSku productSku : productSkus) {
			Inventory inventory=getInventoryBySku(productSku.getId());
			if(inventory!=null&&(inventory.getQuantityOnHand()>0||inventory.getPreOrBackOrderedQty()>0)){
				isHasStock=true;
				break;
			}
		}
		return isHasStock;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	

	public Inventory getInventoryBySku(Integer skuId) {
		return inventoryManager.getInventoryBySku(skuId);
	}

	public SkuInventoryVO getSkuInventoryVO(String productSkuCode) {
		//TODO 存在并发问题
		ProductSku productSku=CatalogHelper.getInstance().getProductSkuByCode(productSkuCode);
		SkuInventoryVO skuInventoryVO=inventoryManager.getSkuInventoryVO(productSku);
		return skuInventoryVO;
	}

	public Short checkInventoryInCart(String skuCode, Integer quantity) {
		//TODO 存在并发问题
		ProductSku productSku=CatalogHelper.getInstance().getProductSkuByCode(skuCode);
		return inventoryManager.checkInventoryInCart(productSku, quantity);
	}
}

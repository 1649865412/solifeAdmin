package com.cartmatic.estore.common.service.impl;

import java.util.Set;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.service.SupplierService;
import com.cartmatic.estore.supplier.service.PurchaseOrderItemManager;
import com.cartmatic.estore.supplier.service.SupplierProductManager;

public class SupplierServiceImpl implements SupplierService {

	private PurchaseOrderItemManager purchaseOrderItemManager;
	private SupplierProductManager supplierProductManager;
	
	
	public void createPurchaseOrderItem(OrderSku orderSku) {
		// TODO Auto-generated method stub
		Set<PurchaseOrderItem> poItems = orderSku.getPurchaseOrderItems();
		if (poItems.size() == 0)
		{
			PurchaseOrderItem item = new PurchaseOrderItem();
			item.setOrderSku(orderSku);
			item.setAccessories(orderSku.getAccessories());
			item.setProductName(orderSku.getProductName());		
			item.setPurchaseQuantity(orderSku.getQuantity());
			item.setSkuDisplay(orderSku.getDisplaySkuOptions());
			purchaseOrderItemManager.save(item);
		}
		else
		{
			int qty = 0;
			for (PurchaseOrderItem item: poItems)
			{
				qty += item.getPurchaseQuantity();
			}
			if (orderSku.getQuantity() > qty)
			{
				PurchaseOrderItem item = new PurchaseOrderItem();
				item.setOrderSku(orderSku);
				item.setAccessories(orderSku.getAccessories());
				item.setProductName(orderSku.getProductName());		
				item.setPurchaseQuantity(orderSku.getQuantity() - qty);
				item.setSkuDisplay(orderSku.getDisplaySkuOptions());
				purchaseOrderItemManager.save(item);
			}
		}
		//TODO 当有供应商产品对应时,应该采用
		/*if (orderSku.getProductSku().getProduct().getDefaultSupplierProductId() != null)
		{
			SupplierProduct sp = supplierProductManager.loadById(orderSku.getProductSku().getProduct().getDefaultSupplierProductId());
			item.setSupplierProductName(sp.getProductName());
			//TODO 设置成本价 item.setPurchasePrice(aValue);
		}*/
	}

	public void setSupplierProductManager(SupplierProductManager avalue)
	{
		supplierProductManager = avalue;
	}	
	
	public void setPurchaseOrderItemManager(PurchaseOrderItemManager avalue)
	{
		purchaseOrderItemManager = avalue;
	}
}

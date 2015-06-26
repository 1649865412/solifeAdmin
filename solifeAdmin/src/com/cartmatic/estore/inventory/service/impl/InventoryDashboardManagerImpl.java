package com.cartmatic.estore.inventory.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.inventory.dao.InventoryDashboardDao;
import com.cartmatic.estore.inventory.service.InventoryDashboardManager;

public class InventoryDashboardManagerImpl extends GenericManagerImpl<Inventory> implements InventoryDashboardManager {

	private InventoryDashboardDao inventoryDashboardDao=null;
	
	public Map<String,Long> getInventoryDashboardData() {
		Map<String,Long> inventoryDashBoardData=new HashMap<String, Long>();

		Long lackStockProductSkuTotal =inventoryDashboardDao.getLackStockProductSkuTotal();
		inventoryDashBoardData.put("lackStockProductSkuTotal", lackStockProductSkuTotal);
		Long lackStockActiveProductSkuTotal =inventoryDashboardDao.getLackStockActiveProductSkuTotal();
		inventoryDashBoardData.put("lackStockActiveProductSkuTotal", lackStockActiveProductSkuTotal);
		
		Long lowStockProductSkuTotal =inventoryDashboardDao.getLowStockProductSkuTotal();
		inventoryDashBoardData.put("lowStockProductSkuTotal", lowStockProductSkuTotal);
		Long lowStockActiveProductSkuTotal =inventoryDashboardDao.getLowStockActiveProductSkuTotal();
		inventoryDashBoardData.put("lowStockActiveProductSkuTotal", lowStockActiveProductSkuTotal);
		
		
		Long alreadyExpectedRestockDateInventoryTotal =inventoryDashboardDao.getAlreadyExpectedRestockDateInventoryTotal();
		inventoryDashBoardData.put("alreadyExpectedRestockDateInventoryTotal", alreadyExpectedRestockDateInventoryTotal);
		return inventoryDashBoardData;
	}

	public void setInventoryDashboardDao(InventoryDashboardDao inventoryDashboardDao) {
		this.inventoryDashboardDao = inventoryDashboardDao;
	}

	@Override
	protected void initManager() { 
		// TODO Auto-generated method stub
		dao=inventoryDashboardDao;
	}

	@Override
	protected void onDelete(Inventory entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(Inventory entity) {
		// TODO Auto-generated method stub
		
	}

}

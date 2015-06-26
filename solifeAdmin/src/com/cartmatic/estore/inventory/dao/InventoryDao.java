package com.cartmatic.estore.inventory.dao;

import java.util.List;

import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Inventory.
 */
public interface InventoryDao extends GenericDao<Inventory> {
	public Inventory getInventoryBySkuCode(String skuCode);
	public Integer getInventoryIdBySku(Integer skuId);
	public List<Inventory> getLackStockProductSkuLimit(Integer maxSize) ;
	public List<Inventory> getLackStockActiveProductSkuLimit(Integer maxSize) ;
	public List<Inventory> getLowStockProductSkuLimit(Integer maxSize) ;
	public List<Inventory> getLowStockActiveProductSkuLimit(Integer maxSize) ;
}
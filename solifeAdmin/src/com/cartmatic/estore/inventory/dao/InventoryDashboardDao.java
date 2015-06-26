package com.cartmatic.estore.inventory.dao;

import java.util.List;

import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.dao.GenericDao;

public interface InventoryDashboardDao extends GenericDao<Inventory>{
	/**
	 * 低库存的SKU数量 低库存：可售库存数量—待分配库存的预订数量 <  最小库存数量
	 * @return
	 */
	public Long getLowStockProductSkuTotal();
	
	/**
	 * 低库存的激活产品的SKU数量
	 * @return
	 */
	public Long getLowStockActiveProductSkuTotal();
	/**
	 * 缺货的SKU数量 缺货：可售库存数量—待分配库存的预订数量 <  0
	 * @return
	 */
	
	public Long getLackStockProductSkuTotal() ;
	
	/**
	 * 缺货的激活产品的SKU数量
	 * @return
	 */
	public Long getLackStockActiveProductSkuTotal() ;
	/**
	 * 已到再进货时间Sku 总数量
	 * @return
	 */
	public Long getAlreadyExpectedRestockDateInventoryTotal();
}

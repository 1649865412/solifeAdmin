package com.cartmatic.estore.common.service;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.SkuInventoryVO;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.exception.OutOfStockException;

public interface InventoryService {
	
	/**
	 * 请求分配库存
	 * @param salesOrder 应用订单
	 * @param productSku 请求分配库存的Sku
	 * @param quantity	请求分配的库存数量
	 * @return 返回两个数值，第一个是本次已分配的实际库存数量，第二个是本次已分配的预订数量
	 * @throws OutOfStockException 没有足够的实际库存和可分配给该Sku预订数量时抛出
	 */
	public Integer[] doAllocateProductSku(SalesOrder salesOrder,ProductSku productSku, Integer quantity)throws OutOfStockException;

	
	/**
	 * 请求重新分配实际库存给已预订的产品
	 * （进货、减少保留库存、取消实际库存分配时需主动分配实际库存给预订产品）
	 * @param salesOrder 应用订单
	 * @param productSku 请求分配实际库存的Sku
	 * @param quantity 请求分配的实际库存数量
	 * @return 本次已分配的实际库存数量
	 */
	public Integer doReAllocateStockForPreSKU(SalesOrder salesOrder,ProductSku productSku, Integer quantity);
	
	/**
	 * 取消某Sku的库存分配
	 * (真实库存分配，非预订库存)
	 * @param salesOrder
	 * @param productSku
	 * @param quantity
	 */
	public void doCancelAllocate(SalesOrder salesOrder,ProductSku productSku, Integer quantity);
	
	
	/**
	 * 取消分给Sku的预订数量
	 * @param productSku
	 * @param quantity
	 * @return
	 */
	public void doCancelAllocatePreOrBackOrderedQty(ProductSku productSku, Integer quantity);

	
	
	/**
	 * 对某sku发货，释放其库存（真正发货时调用）
	 * @param productSkuId
	 * @param quantity 对sku的发货数量
	 */
	public void doReleaseStock(SalesOrder salesOrder,ProductSku productSku, Integer quantity);
	

	
	/**
	 * 查找Sku库存
	 * @param skuId
	 * @return
	 */
	public Inventory getInventoryBySku(Integer skuId);
	
	/**
	 * 初始化Sku库存信息
	 * 当产品为永远可售时，不会创建库存数据
	 * 当产品修改销售规则，改变产品永远可售还是非永远可售时，会创建库存数据
	 * @param productSku
	 */
	public void doInitInventoryByCreateProduct(ProductSku productSku);
	
	
	/**
	 * 产品是否存在现有库存数量或存在已分配预订订单数量
	 * @param product
	 * @return
	 */
	public boolean isHasStockOrOrderedPreOrBackOrderByProduct(Product product);
	
	
	/**
	 * 获取Sku库存信息，只要是前台显示
	 * @param productSkuCode
	 * @return
	 */ 
	public SkuInventoryVO getSkuInventoryVO(String productSkuCode);
	
	/**
	 检查该Sku是否可添加到购物车内
	 * @param productSku
	 * @param quantity
	 * @return 1表示有足够的库存，可以添加到购物车；2.表示没有足够的库存，不能够添加购物车；3表示完全没有库存，不能添加到购物车
	 */
	public Short checkInventoryInCart(String skuCode,Integer quantity);
}

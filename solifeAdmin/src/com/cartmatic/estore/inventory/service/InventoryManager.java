package com.cartmatic.estore.inventory.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.InventoryModel;
import com.cartmatic.estore.common.model.inventory.SkuInventoryVO;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.exception.OutOfStockException;

/**
 * Manager interface for Inventory, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface InventoryManager extends GenericManager<Inventory> {
	
	
	/**
	 * 保存设置库存信息
	 * @param inventoryModel 
	 * @param inventory
	 */
	public void saveInventoryAction(InventoryModel inventoryModel,Inventory inventory);
	
	/**
	 * 后台调整库存
	 * @param inventoryModel 相应的数据数据
	 * @param inventory 要调整的Sku库存
	 */
	public void doAdjustStockQuantity(InventoryModel inventoryModel,Inventory inventory);
	
	/**
	 * 根据skuCode查找该Sku库存信息
	 * @param skuCode
	 * @return
	 */
	public Inventory getInventoryBySkuCode(String skuCode);
	
	/**
	 * 获取Sku库存信息
	 * @param skuId
	 * @return
	 */
	public Inventory getInventoryBySku(Integer skuId);
	
	
	/**
	 * 取消某Sku的实际库存分配（非预订库存）
	 * @param salesOrder 应用订单
	 * @param productSku 取消数量的Sku
	 * @param quantity	取消数量
	 * @param handler 操作者
	 */
	public void doCancelAllocate(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler);
	
	
	/**
	 * 对某sku发货，释放其库存（真正发发时调用）
	 * @param productSkuId
	 * @param quantity 对sku的发货数量
	 */
	public void doReleaseStockt(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler);
	
	/**
	 * 取消分给Sku的预订数量
	 * @param productSku 要取消的Sku
	 * @param quantity	取消数量
	 * @return
	 */
	public void doCancelAllocatePreOrBackOrderedQty(ProductSku productSku, Integer quantity);
	
	/**
	 * 请求分配库存
	 * @param salesOrder 应用订单
	 * @param productSku 请求分配库存的Sku
	 * @param quantity	请求分配的库存数量
	 * @param handler 操作者
	 * @return 返回两个数值，第一个是本次已分配的实际库存数量，第二个是本次已分配的预订数量
	 * @throws OutOfStockException 没有足够的实际库存和可分配给该Sku预订数量时抛出
	 */
	public Integer[] doAllocateProductSku(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler)throws OutOfStockException;
	
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
	 * 获取Sku库存信息，只要是前台显示.（只是提供相应的数据）
	 * 
	 * 当SkuInventoryVO.type为
	 * 1表示有库存才库存购买,即availableQuantity必须有相应数量存在
	 * 2表示预订，即availablePreOrBackOrderedQty必须有相应数量存在
	 * 4表示该产品是无限库存，不受库存管理，availableQuantity、availablePreOrBackOrderedQty等数据为空
	 * 5表示预订，但不可受预订数量限制
	 * 
	 * @param productSku 持久化状态的Sku
	 * @return
	 */ 
	public SkuInventoryVO getSkuInventoryVO(ProductSku productSku);
	
	/**
	 检查该Sku是否可添加到购物车内
	 * @param productSku
	 * @param quantity
	 * @return 1表示有足够的库存，可以添加到购物车；2.表示没有足够的库存，不能够添加购物车；3表示完全没有库存，不能添加到购物车
	 */
	public Short checkInventoryInCart(ProductSku productSku,Integer quantity);
	
	/**
	 * 获得指定数量的低库存的sku
	 * @return
	 */
	public List<Inventory> getLowStockProductSkuLimit(Integer maxSize) ;
	/**
	 * 获得指定数量的的激活的低库存sku
	 * @return
	 */
	public List<Inventory> getLowStockActiveProductSkuLimit(Integer maxSize) ;
	
	/**
	 * 获得指定数量的缺货的sku
	 * @return
	 */
	public List<Inventory> getLackStockProductSkuLimit(Integer maxSize) ;
	/**
	 * 获得指定数量的激活的缺货的sku
	 * @return
	 */
	public List<Inventory> getLackStockActiveProductSkuLimit(Integer maxSize) ;
	
	/**
	 * 向管理员发低库存邮件
	 */
	public void sendEmailOfLowStockProductSku();
	
}

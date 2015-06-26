package com.cartmatic.estore.inventory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.InventoryAudit;
import com.cartmatic.estore.common.model.inventory.InventoryModel;
import com.cartmatic.estore.common.model.inventory.SkuInventoryVO;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.inventory.InventoryConstants;
import com.cartmatic.estore.inventory.dao.InventoryDao;
import com.cartmatic.estore.inventory.dao.InventoryDashboardDao;
import com.cartmatic.estore.inventory.service.InventoryAuditManager;
import com.cartmatic.estore.inventory.service.InventoryManager;
import com.cartmatic.estore.webapp.util.RequestContext;




/**
 * Manager implementation for Inventory, responsible for business processing, and communicate between web and persistence layer.
 */
public class InventoryManagerImpl extends GenericManagerImpl<Inventory> implements InventoryManager {
    private InventoryAuditManager inventoryAuditManager = null;

	private InventoryDao inventoryDao = null;
	private InventoryDashboardDao inventoryDashboardDao = null;
	
	private ConfigUtil			configUtil			= ConfigUtil.getInstance();

	private MailEngine			mailEngine;
	/**
	 * @param inventoryDao
	 *            the inventoryDao to set
	 */
	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	public void setInventoryDashboardDao(InventoryDashboardDao inventoryDashboardDao) {
		this.inventoryDashboardDao = inventoryDashboardDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = inventoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(Inventory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(Inventory entity) {

	}
	
	public void saveInventoryAction(InventoryModel inventoryModel,Inventory inventory){
		//isNeedAllocationToOrder减少保留库存时需要分配库存给订单
		boolean isNeedAllocationToOrder=inventory.getReservedQuantity().compareTo(inventoryModel.getReservedQuantity())>0;
		inventory.setReservedQuantity(inventoryModel.getReservedQuantity());
		inventory.setReorderMinimnm(inventoryModel.getReorderMinimnm());
		inventory.setReorderQuantity(inventoryModel.getReorderQuantity());
		inventory.setExpectedRestockDate(inventoryModel.getExpectedRestockDate());
		//保留数量减少后，需自动分配库存给预订订单
		if(isNeedAllocationToOrder&&inventory.getAvailableQuantity().intValue()>0){
			//可销售数量增加了时，要马上分配数量给该sku预订订单的预订数量，调用订单接口，查找该sku需要分配的产品数量的预订订单，该订单sku获取分别数量
			OrderService orderService=(OrderService)ContextUtil.getSpringBeanById("orderService");
			orderService.doAllocation4PreOrBackOrder(inventory.getProductSkuId());
		}
	}
	
	
	public void doAdjustStockQuantity(InventoryModel inventoryModel,Inventory inventory){
		if(inventoryModel.getAdjustmentType()!=null&&inventoryModel.getAdjustmentQuantity()!=null&&inventoryModel.getAdjustmentQuantity().intValue()>0){
			//AdjustmentType等于1时，表示为增加库存，否则表示减少库存
			if(inventoryModel.getAdjustmentType()==1){
				inventory.setQuantityOnHand(inventory.getQuantityOnHand()+inventoryModel.getAdjustmentQuantity());
			}else{
				inventory.setQuantityOnHand(inventory.getQuantityOnHand()-inventoryModel.getAdjustmentQuantity());
			}
			InventoryAudit inventoryAudit=new InventoryAudit();
			inventoryAudit.setProductSku(inventory.getProductSku());
			inventoryAudit.setInventory(inventory);
			inventoryAudit.setComment(inventoryModel.getAdjustmentComment());
			inventoryAudit.setReason(inventoryModel.getAdjustmentReason());
			inventoryAudit.setEventType(InventoryConstants.INVENTORY_AUDIT_EVENT_TYPE_STOCK_ADJUSTMENT);
			if(inventoryModel.getAdjustmentType()==1){
				inventoryAudit.setQuantity(inventoryModel.getAdjustmentQuantity());
			}else{
				//当操作为减少库存时，数量以负数形式保存，这样直观
				inventoryAudit.setQuantity(inventoryModel.getAdjustmentQuantity()*-1);
			}
			inventoryAudit.setQuantityOnHand(inventory.getQuantityOnHand());
			inventoryAudit.setAllocatedQuantity(inventory.getAllocatedQuantity());
			AppUser appUser=RequestContext.getCurrentUser();
			StringBuffer handler=new StringBuffer("CM User/");
			handler.append(appUser.getAppuserId());
			handler.append("/");
			handler.append(appUser.getUsername());
			inventoryAudit.setEventHandler(handler.toString());
			inventoryAuditManager.save(inventoryAudit);
			if(inventoryModel.getAdjustmentType()==1&&inventoryModel.getAdjustmentQuantity().intValue()>0){
				//添加库存时要马上分配数量给该sku的预订数量，调用订单接口，查找该sku需要分配的产品数量的预订订单，该订单sku获取分别数量
				OrderService orderService=(OrderService)ContextUtil.getSpringBeanById("orderService");
				orderService.doAllocation4PreOrBackOrder(inventory.getProductSkuId());
			}
		}
	}

	
	public Integer[] doAllocateProductSku(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler)throws OutOfStockException{
		Integer result[]=new Integer[2];
		result[0]=0;
		result[1]=0;
		//无限库存时直接返回请求分配数量
		Short availabilityRule=productSku.getProduct().getAvailabilityRule();
		//无库存销售的直接返回0[无库存销售不能调用本方法]
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return result;
		}
		//无限库存的当直接分配处理
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()){
			result[0]=quantity;
			return result;
		}
		
		Inventory inventory=getInventoryForUpdateBySku(productSku.getId());
		//实际可售数量
		Integer availableQuantity=inventory.getAvailableQuantity();
		//可分配给该Sku的库存数量
		Integer canAllocateAvailableQuantity=0;
		if(availableQuantity>=quantity){
			canAllocateAvailableQuantity=quantity;
		}else{
			canAllocateAvailableQuantity=availableQuantity;
		}
		
		//请求预订数量
		Integer preOrBackQty=quantity-canAllocateAvailableQuantity;
		
		//可分配给该Sku预订数量
		Integer cancanAllocatePreOrBackOrderQty=0;
		if(preOrBackQty>0&&(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_PRE_ORDER||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_BACK_ORDER)){
			Integer preOrBackOrderedQty=inventory.getPreOrBackOrderedQty();
			Integer preOrBackOrderLimit=productSku.getProduct().getPreOrBackOrderLimit();
			if(preOrBackOrderLimit<=0){
				cancanAllocatePreOrBackOrderQty=preOrBackQty;
			}else{
				cancanAllocatePreOrBackOrderQty=preOrBackOrderLimit-preOrBackOrderedQty;
				if(cancanAllocatePreOrBackOrderQty>=preOrBackQty){
					cancanAllocatePreOrBackOrderQty=preOrBackQty;
				}
			}
		}
		//可下单数量（包含实际库存及预订库存）
		Integer canPlaceOrderQty=canAllocateAvailableQuantity+cancanAllocatePreOrBackOrderQty;
		//检查是否有足够的库存和可接受预订数量分配该Sku [库存分配触发调整]不管什么时候请求分配库存的,都返回当次分配的数量（没有库存可分配的就返回零）
//		if(canPlaceOrderQty>=quantity){
			result[0]=canAllocateAvailableQuantity;
			result[1]=cancanAllocatePreOrBackOrderQty;
			//更新库存数据
			//分配实际库存
			if(canAllocateAvailableQuantity>0){
				inventory.setAllocatedQuantity(inventory.getAllocatedQuantity()+canAllocateAvailableQuantity);
				//保存操作库存记录
				InventoryAudit inventoryAudit=new InventoryAudit();
				inventoryAudit.setProductSku(productSku);
				inventoryAudit.setInventory(inventory);
				inventoryAudit.setProductSku(productSku);
				inventoryAudit.setQuantity(canAllocateAvailableQuantity);
				inventoryAudit.setQuantityOnHand(inventory.getQuantityOnHand());
				inventoryAudit.setAllocatedQuantity(inventory.getAllocatedQuantity());
				inventoryAudit.setEventType(InventoryConstants.INVENTORY_AUDIT_EVENT_TYPE_STOCK_ALLOCATE);
				inventoryAudit.setSalesOrder(salesOrder);
				inventoryAudit.setEventHandler(handler);
				inventoryAuditManager.save(inventoryAudit);
			}
			//分配预订库存
			if(cancanAllocatePreOrBackOrderQty>0){
				inventory.setPreOrBackOrderedQty(inventory.getPreOrBackOrderedQty()+cancanAllocatePreOrBackOrderQty);
			}
			save(inventory);
			flush();
		/*}else{
			//没有足够的库存和可接受预订数量分配该Sku，直接抛出异常
			throw new OutOfStockException(canAllocateAvailableQuantity,cancanAllocatePreOrBackOrderQty);
		}*/
		return result;
	}
	
	public Integer doReAllocateStockForPreSKU(SalesOrder salesOrder,ProductSku productSku, Integer quantity){
		Short availabilityRule=productSku.getProduct().getAvailabilityRule();
		//无库存销售的直接返回0[无库存销售不能调用本方法]
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return 0;
		}
		//无限库存的当直接分配处理
		if(quantity.intValue()<=0||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()){
			return quantity;
		}
		Inventory inventory=getInventoryForUpdateBySku(productSku.getId());
		//实际可售数量
		Integer availableQuantity=inventory.getAvailableQuantity();
		//可分配给该Sku的实际库存数量
		Integer canAllocateAvailableQuantity=0;
		if(availableQuantity>=quantity){
			canAllocateAvailableQuantity=quantity;
		}else{
			canAllocateAvailableQuantity=availableQuantity;
		}
		//[库存分配触发调整]不管什么时候请求分配库存的,都返回当次分配的数量（没有库存可分配的就返回零）
//		if(canAllocateAvailableQuantity>0){
			//更新库存数据
			//分配实际库存
			inventory.setAllocatedQuantity(inventory.getAllocatedQuantity()+canAllocateAvailableQuantity);
			//分配了实际库存后待分配的预订库存（已分配的预订库存）应相应减少(系统自动触发的才需要)
			int tempPreOrBackOrderedQty=inventory.getPreOrBackOrderedQty()-canAllocateAvailableQuantity;
			if(tempPreOrBackOrderedQty<0){
				logger.warn("分配库存后，预订库存出现负数！");
				throw new RuntimeException("分配库存后，预订库存出现负数！");
			}
			//tempPreOrBackOrderedQty=tempPreOrBackOrderedQty<0?0:tempPreOrBackOrderedQty;
			inventory.setPreOrBackOrderedQty(tempPreOrBackOrderedQty);
			//保存操作库存记录
			InventoryAudit inventoryAudit=new InventoryAudit();
			inventoryAudit.setProductSku(productSku);
			inventoryAudit.setInventory(inventory);
			inventoryAudit.setProductSku(productSku);
			inventoryAudit.setQuantity(canAllocateAvailableQuantity);
			inventoryAudit.setQuantityOnHand(inventory.getQuantityOnHand());
			inventoryAudit.setAllocatedQuantity(inventory.getAllocatedQuantity());
			inventoryAudit.setEventType(InventoryConstants.INVENTORY_AUDIT_EVENT_TYPE_STOCK_ALLOCATE);
			inventoryAudit.setSalesOrder(salesOrder);
			//TODO
			inventoryAudit.setEventHandler("System");
			inventoryAuditManager.save(inventoryAudit);
			save(inventory);
			flush();
//		}
		return canAllocateAvailableQuantity;
	}
	

	public void doCancelAllocate(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler) {
		Short availabilityRule=productSku.getProduct().getAvailabilityRule();
		//无库存销售的直接不处理[无库存销售不能调用本方法]
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return ;
		}
		//当sku为无限库存的不需处理
		if(quantity.intValue()<=0||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()){
			return ;
		}
		Inventory inventory=getInventoryForUpdateBySku(productSku.getId());
		inventory.setAllocatedQuantity(inventory.getAllocatedQuantity()-quantity);
		save(inventory);
		InventoryAudit inventoryAudit=new InventoryAudit();
		inventoryAudit.setProductSku(productSku);
		inventoryAudit.setInventory(inventory);
		inventoryAudit.setQuantity(quantity);
		inventoryAudit.setQuantityOnHand(inventory.getQuantityOnHand());
		inventoryAudit.setAllocatedQuantity(inventory.getAllocatedQuantity());
		inventoryAudit.setEventType(InventoryConstants.INVENTORY_AUDIT_EVENT_TYPE_STOCK_DEALLOCATE);
		inventoryAudit.setSalesOrder(salesOrder);
		//TODO
		inventoryAudit.setEventHandler(handler);
		inventoryAuditManager.save(inventoryAudit);
		dao.flush();
		//取消真实库存分配时,实际可售数量增加了，要马上分配数量给该sku的预订数量
		OrderService orderService=(OrderService)ContextUtil.getSpringBeanById("orderService");
		orderService.doAllocation4PreOrBackOrder(inventory.getProductSkuId());
	}
	
	public void doReleaseStockt(SalesOrder salesOrder,ProductSku productSku, Integer quantity,String handler) {
		Short availabilityRule=productSku.getProduct().getAvailabilityRule();
		//无库存销售的不需处理[无库存销售不能调用本方法]
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return ;
		}
		//当sku为无限库存的不需处理
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()){
			return ;
		}
		Inventory inventory=getInventoryForUpdateBySku(productSku.getId());
		inventory.setQuantityOnHand(inventory.getQuantityOnHand()-quantity);
		inventory.setAllocatedQuantity(inventory.getAllocatedQuantity()-quantity);
		save(inventory);
		InventoryAudit inventoryAudit=new InventoryAudit();
		inventoryAudit.setProductSku(productSku);
		inventoryAudit.setInventory(inventory);
		inventoryAudit.setQuantity(quantity*-1);
		inventoryAudit.setQuantityOnHand(inventory.getQuantityOnHand());
		inventoryAudit.setAllocatedQuantity(inventory.getAllocatedQuantity());
		inventoryAudit.setEventType(InventoryConstants.INVENTORY_AUDIT_EVENT_TYPE_STOCK_RELEASE);
		inventoryAudit.setSalesOrder(salesOrder);
		//TODO
		inventoryAudit.setEventHandler(handler);
		inventoryAuditManager.save(inventoryAudit);
		flush();
	}

	public Inventory getInventoryBySkuCode(String skuCode) {
		return inventoryDao.getInventoryBySkuCode(skuCode);
	}

	

	public Inventory getInventoryBySku(Integer skuId) {
		//@TODO 延迟加载对象失败 (编辑Sku时)
//		ProductSku productSku=CatalogHelper.getInstance().getProductSkuById(skuId);
//		return productSku.getInventory();
		Inventory inventory=dao.findUniqueByProperty("productSku.productSkuId", skuId);
		return inventory;
	}
	
	
	private Inventory getInventoryForUpdateBySku(Integer skuId) {
		Integer inventoryId=inventoryDao.getInventoryIdBySku(skuId);
		Inventory inventory=inventoryDao.loadForUpdate(inventoryId);
		return inventory;
	}

	public void setInventoryAuditManager(InventoryAuditManager inventoryAuditManager) {
		this.inventoryAuditManager = inventoryAuditManager;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public void doCancelAllocatePreOrBackOrderedQty(ProductSku productSku,Integer quantity) {
		Short availabilityRule=productSku.getProduct().getAvailabilityRule();
		//无库存销售的不需处理[无库存销售不能调用本方法]
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return ;
		}
		//当sku为无限库存的不需处理
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()){
			return ;
		}
		Inventory inventory=getInventoryForUpdateBySku(productSku.getId());
		inventory.setPreOrBackOrderedQty(inventory.getPreOrBackOrderedQty()-quantity);
		save(inventory);
		flush();
	}

	public SkuInventoryVO getSkuInventoryVO(ProductSku productSku) {
		SkuInventoryVO skuInventoryVO=new SkuInventoryVO();
		Product product=productSku.getProduct();
		//获取该Sku的销售规则
		Short availabilityRule=product.getAvailabilityRule();
		skuInventoryVO.setAvailabilityRule(availabilityRule);
		//TODO 新增无库存销售后，未完善
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL){
			skuInventoryVO.setType(new Short("4"));
		}else{
			Inventory inventory=getInventoryBySku(productSku.getId());
			skuInventoryVO.setAvailableQuantity(inventory.getAvailableQuantity());
			if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ONLY_IN_STOCK.intValue()){
				skuInventoryVO.setType(new Short("1"));
			}else{
				Integer availablePreOrBackOrderedQty=product.getPreOrBackOrderLimit();
				//当可接受的最大预订数量为零时，可添加到购物车的数量无限制
				if(availablePreOrBackOrderedQty==null||availablePreOrBackOrderedQty.intValue()==0){
					skuInventoryVO.setType(new Short("5"));
				}else{
					skuInventoryVO.setAvailablePreOrBackOrderedQty(availablePreOrBackOrderedQty-inventory.getPreOrBackOrderedQty());
					skuInventoryVO.setType(new Short("2"));
				}
				if(availabilityRule.intValue()==2){
					skuInventoryVO.setExpectedRestockDate(product.getExpectedReleaseDate());
				}else if(availabilityRule.intValue()==3){
					skuInventoryVO.setExpectedRestockDate(inventory.getExpectedRestockDate());
				}
			}
			//availableToCartQty表示可以添加到购物车的数量
			skuInventoryVO.setAvailableToCartQty(skuInventoryVO.getAvailablePreOrBackOrderedQty()+skuInventoryVO.getAvailableQuantity());
		}
		return skuInventoryVO;
	}
	
	public Short checkInventoryInCart(ProductSku productSku,Integer quantity) {
		/**
		 * 1表示有足够的库存，可以添加到购物车；
		 * 2表示没有足够的库存，不能够添加购物车(即有库存，但不能满足该请求购买的数量)；
		 * 3表示完全没有库存，不能添加到购物车
		 */
		Short result=1;
		Product product=productSku.getProduct();
		//获取该Sku的销售规则
		Short availabilityRule=product.getAvailabilityRule();
		//TODO 新增无库存销售后，未完善
		//无限库存
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()){
			return result;
		}
		Inventory inventory=getInventoryBySku(productSku.getId());
		
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ONLY_IN_STOCK.intValue()){
			if(inventory.getAvailableQuantity().intValue()==0){
				result=3;
			}else if(inventory.getAvailableQuantity().intValue()>=quantity.intValue()){
				result=1;
			}else{
				result=2;
			}
		}else{
			Integer availablePreOrBackOrderedQty=product.getPreOrBackOrderLimit();
			//当可接受的最大预订数量为零时，可添加到购物车的数量无限制
			if(availablePreOrBackOrderedQty.intValue()==0){
				result=1;
			}else{
				int canInCartQuantity=availablePreOrBackOrderedQty-inventory.getPreOrBackOrderedQty()+inventory.getAvailableQuantity();
				if(canInCartQuantity==0){
					result=3;
				}else if(canInCartQuantity>=quantity.intValue()){
					result=1;
				}else{
					result=2;
				}
			}
		}
		return result;
	}
	
	public List<Inventory> getLowStockProductSkuLimit(Integer maxSize){
		return inventoryDao.getLowStockProductSkuLimit(maxSize);
	}
	public List<Inventory> getLowStockActiveProductSkuLimit(Integer maxSize){
		return inventoryDao.getLowStockActiveProductSkuLimit(maxSize);
	}
	
	public List<Inventory> getLackStockProductSkuLimit(Integer maxSize) {
		return inventoryDao.getLackStockProductSkuLimit(maxSize);
	}
	
	public List<Inventory> getLackStockActiveProductSkuLimit(Integer maxSize) {
		return inventoryDao.getLackStockActiveProductSkuLimit(maxSize);
	}
	
	public void sendEmailOfLowStockProductSku(){
		Map model = new HashMap();
		List<Inventory> lackStockProductSkuList = getLackStockActiveProductSkuLimit(50);
		List<Inventory> lowStockProductSkuList = getLowStockActiveProductSkuLimit(50);
		Long lackStockSize = inventoryDashboardDao.getLackStockProductSkuTotal();
		Long lackStockActiveSize = inventoryDashboardDao.getLackStockActiveProductSkuTotal();
		Long lowStockSize = inventoryDashboardDao.getLowStockProductSkuTotal();
		Long lowStockActiveSize = inventoryDashboardDao.getLowStockActiveProductSkuTotal();
		model.put("lowStockProductSkuList", lowStockProductSkuList);
		model.put("lackStockProductSkuList", lackStockProductSkuList);
		model.put("lackStockSize",lackStockSize);
		model.put("lackStockActiveSize",lackStockActiveSize);
		model.put("lowStockSize",lowStockSize);
		model.put("lowStockActiveSize",lowStockActiveSize);
		mailEngine.sendSimpleTemplateMail(configUtil
				.getLowProductSkuEmailTemplate(), model, null, null, configUtil.getStockAlertRecipientEmail());
	}
	
}

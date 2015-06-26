package com.cartmatic.estore.supplier.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.dao.PurchaseOrderDao;
import com.cartmatic.estore.supplier.service.PoNoGenerator;
import com.cartmatic.estore.supplier.service.PurchaseOrderItemManager;
import com.cartmatic.estore.supplier.service.PurchaseOrderManager;
import com.cartmatic.estore.supplier.service.SupplierManager;


/**
 * Manager implementation for PurchaseOrder, responsible for business processing, and communicate between web and persistence layer.
 */
public class PurchaseOrderManagerImpl extends GenericManagerImpl<PurchaseOrder> implements PurchaseOrderManager {

	private PurchaseOrderDao purchaseOrderDao = null;
	private PurchaseOrderItemManager purchaseOrderItemManager = null;
	private SupplierManager supplierManager = null;
	private PoNoGenerator poNoGenerator = null;
	
	public PurchaseOrder createPurchaseOrder(Integer supplierId)
	{
		Supplier supplier = supplierManager.loadById(supplierId);
		List<PurchaseOrderItem> rs = purchaseOrderItemManager.getAwaitingPoItemBySupplier(supplierId);
		PurchaseOrder order = new PurchaseOrder();
		order.setSupplier(supplier);
		order.setPurchaseOrderNo(poNoGenerator.generateOrderNo());
		order.setSupplierName(supplier.getSupplierName());
		for (PurchaseOrderItem item: rs)
		{
			item.setStatus(Constants.STATUS_ACTIVE);
			item.setPurchaseOrder(order);
			order.getPurchaseOrderItems().add(item);
			//purchaseOrderItemManager.save(item);
		}
		purchaseOrderDao.save(order);
		return order;
	}
	
	// setters
	public void setPoNoGenerator(PoNoGenerator avalue)
	{
		this.poNoGenerator = avalue;
	}
	public void setSupplierManager(SupplierManager avalue)
	{
		this.supplierManager = avalue;
	}
	public void setPurchaseOrderItemManager(PurchaseOrderItemManager avalue)
	{
		this.purchaseOrderItemManager = avalue;
	}
	/**
	 * @param purchaseOrderDao
	 *            the purchaseOrderDao to set
	 */
	public void setPurchaseOrderDao(PurchaseOrderDao purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = purchaseOrderDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(PurchaseOrder entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(PurchaseOrder entity) {

	}

}

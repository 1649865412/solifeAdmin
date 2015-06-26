package com.cartmatic.estoresa.supplier.web.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.supplier.SupplierConstants;
import com.cartmatic.estore.supplier.service.PurchaseOrderItemManager;
import com.cartmatic.estore.supplier.service.PurchaseOrderManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class PurchaseOrderController extends GenericController<PurchaseOrder> {
    private PurchaseOrderManager purchaseOrderManager = null;
    private PurchaseOrderItemManager purchaseOrderItemManager = null; 
    
    private SalesOrderManager salesOrderManager=null;
    
    private OrderService orderService=null;
    
    
    public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
     * 创建采购单
     * @param request
     * @param response
     * @return
     */
    public ModelAndView createPurchaseOrder(HttpServletRequest request, HttpServletResponse response)
    {
    	PurchaseOrder po = purchaseOrderManager.createPurchaseOrder(new Integer(request.getParameter("supplierId")));
    	saveMessage(Message.info("purchaseOrder.msg.create.purchase.succeed", po.getPurchaseOrderNo()));
    	return new ModelAndView(new RedirectView("/supplier/purchaseOrder.html?doAction=edit&purchaseOrderId="+po.getPurchaseOrderId(),true));
    }
    
    /**
     * 确认采购
     */
    public ModelAndView confirmOrder(HttpServletRequest request, HttpServletResponse response)
    {
    	String id = request.getParameter("purchaseOrderId");
    	PurchaseOrder po = purchaseOrderManager.loadById(new Integer(id));
    	po.setPoStatus(SupplierConstants.PO_STATUS_PROCESSING);
    	purchaseOrderManager.save(po);
    	saveMessage(Message.info("purchaseOrder.msg.confirm.purchase", po.getPurchaseOrderNo()));
    	return getRedirectToActionView("edit", id);
    }
    
    /**
     * 取消采购
     * @param request
     * @param response
     * @return
     */
    public ModelAndView cancelOrder(HttpServletRequest request, HttpServletResponse response)
    {
    	String id = request.getParameter("purchaseOrderId");
    	PurchaseOrder po = purchaseOrderManager.loadById(new Integer(id));
    	po.setPoStatus(SupplierConstants.PO_STATUS_CANCELED);
    	purchaseOrderManager.save(po);
    	saveMessage(Message.info("purchaseOrder.msg.cancel.purchase", po.getPurchaseOrderNo()));
    	return getRedirectToActionView("edit", id);
    }
    
    /**
     * 确认发货
     * @param request
     * @param response
     * @return
     */
    public ModelAndView confirmShiped(HttpServletRequest request, HttpServletResponse response)
    {
    	String id = request.getParameter("purchaseOrderId");
    	PurchaseOrder po = purchaseOrderManager.loadById(new Integer(id));
    	po.setPoStatus(SupplierConstants.PO_STATUS_SHIPED);
    	purchaseOrderManager.save(po);
    	saveMessage(Message.info("purchaseOrder.msg.confirm.ship", po.getPurchaseOrderNo()));
    	return getRedirectToActionView("edit", id);
    }
    
    /**
     * 确认完成
     * @param request
     * @param response
     * @return
     */
    public ModelAndView confirmCompleted(HttpServletRequest request, HttpServletResponse response)
    {
    	String id = request.getParameter("purchaseOrderId");
    	String poResust = request.getParameter("poResult");
    	PurchaseOrder po = purchaseOrderManager.loadById(new Integer(id));
    	po.setPoStatus(SupplierConstants.PO_STATUS_COMPLETED);
    	po.setPoResult(Short.valueOf(poResust));
    	purchaseOrderManager.save(po);
    	saveMessage(Message.info("purchaseOrder.msg.confirm.completed.purchase", po.getPurchaseOrderNo(),getMessage("purchaseOrder.poResult"+poResust)));
    	return getRedirectToActionView("edit", id);
    }
    
    /**
     * 删除某个指定的poItem
     * @param request
     * @param response
     * @return
     */
    public ModelAndView removePoItem(HttpServletRequest request, HttpServletResponse response)
    {
    	String poId = request.getParameter("purchaseOrderItemId");
    	PurchaseOrderItem item = purchaseOrderItemManager.loadById(new Integer(poId));
    	//采购的item还原到初始状态.(New)
    	item.setStatus(SupplierConstants.PO_ITEM_STATUS_NEW);
    	item.setPurchaseOrder(null);
    	purchaseOrderItemManager.save(item);
    	//PurchaseOrder po = purchaseOrderManager.loadById(id);
    	//po.setPoStatus(SupplierConstants.PO_STATUS_COMPLETED);
    	//purchaseOrderManager.save(po);
    	saveMessage(Message.info("purchaseOrder.msg.remove.purchase.item.succeed", item.getProductName()));
    	return getRedirectToActionView("edit", request.getParameter("purchaseOrderId"));
    }
    
    public void setPurchaseOrderManager(PurchaseOrderManager inMgr) {
        this.purchaseOrderManager = inMgr;
    }
    public void setPurchaseOrderItemManager(PurchaseOrderItemManager avalue)
    {
    	this.purchaseOrderItemManager = avalue;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(PurchaseOrder entity) {
		return entity.getPurchaseOrderName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = purchaseOrderManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, PurchaseOrder entity, BindException errors) {
	}
	
	/**
     * 增加注释
     * @param poId
     * @param comment
     * @return 增加后的注释
     */
	public ModelAndView doAppendPoComments(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer purchaseOrderId=new Integer(request.getParameter("purchaseOrderId"));
			String comment=request.getParameter("comment");
			PurchaseOrder po = purchaseOrderManager.loadById(purchaseOrderId);
	    	String idInfo = RequestContext.getCurrentUserName()+" "+DateUtil.getNowStr();
	    	//TODO \r\n换行无效,改为<br />
	    	if(po.getComments()==null){
	    		po.setComments(idInfo + "<br />" + comment );
	    	}else{
	    		po.setComments(idInfo + "<br />" + comment + "<br />" + po.getComments());
	    	}
	    	purchaseOrderManager.save(po);
	    	ajaxView.setData(po.getComments());
	    	ajaxView.setMsg(getMessage("purchaseOrder.msg.add.comment.succeed"));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
     * 保存采购价
     * @param id
     * @param price
     */
	public ModelAndView savePoItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer purchaseOrderItemId=new Integer(request.getParameter("purchaseOrderItemId"));
			BigDecimal price=new BigDecimal(request.getParameter("price"));
			Integer passQty=new Integer(request.getParameter("passQty"));
			PurchaseOrderItem poItem = purchaseOrderItemManager.loadById(purchaseOrderItemId);
	    	poItem.setPurchasePrice(price);
	    	poItem.setPassedQuantity(passQty);
	    	purchaseOrderItemManager.save(poItem);
	    	ajaxView.setMsg(getMessage("purchaseOrder.msg.edit.purchaseItem.succeed", poItem.getProductName()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
     * 调整orderSku上的item分配库存
     *
     */
	public ModelAndView doCompleted(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer purchaseOrderItemId=new Integer(request.getParameter("purchaseOrderItemId"));
			PurchaseOrderItem poItem = purchaseOrderItemManager.loadById(purchaseOrderItemId);
	    	OrderSku sku = poItem.getOrderSku();
	    	Integer newQty = sku.getAllocatedQuantity() + poItem.getPassedQuantity();
	    	if (newQty > sku.getQuantity())
	    		newQty = sku.getQuantity();
	    	sku.setAllocatedQuantity(newQty);
	    	salesOrderManager.save(sku);
	    	orderService.updateStatusForReallocated(sku);  //更新分配的状态
	    	poItem.setStatus(SupplierConstants.PO_ITEM_STATUS_COMPLETED);
	    	purchaseOrderItemManager.save(poItem);
	    	ajaxView.setMsg(getMessage("purchaseOrder.msg.purchase.item.succeed", poItem.getProductName()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

}

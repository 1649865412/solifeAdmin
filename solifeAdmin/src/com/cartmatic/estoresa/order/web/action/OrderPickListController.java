package com.cartmatic.estoresa.order.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.order.service.OrderPickListManager;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estoresa.order.print.PdfView4Picking;
import com.opensymphony.module.sitemesh.RequestConstants;

/**
 * 
 * @author Ryan
 *
 */
public class OrderPickListController extends GenericController<OrderPickList> {
    private OrderPickListManager orderPickListManager = null;    
    private SalesOrderManager salesOrderManager = null;    
    private OrderProcessFlowManager orderProcessFlowManager = null;    
    //private CompanyInfoManager companyInfoManager = null;
    

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(OrderPickList entity) {
		return entity.getOrderPickListName();
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
		mgr = orderPickListManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, OrderPickList entity, BindException errors) {
	}
	
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		//统计待备货的发货项数量
		request.setAttribute("availableAmount", salesOrderManager.countShipments4Picking());
		
		//获取当前用户创建的备货单列表
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		List<OrderPickList> myOrderPickList = orderPickListManager.getActivePickLists(appUser.getAppuserId());
		request.setAttribute("myOrderPickList", myOrderPickList);
		
		List<OrderPickList> myHistoryOrderPickList = orderPickListManager.getInActivePickLists(6, appUser.getAppuserId());
		request.setAttribute("myHistoryOrderPickList", myHistoryOrderPickList);
		
		String orderPickListId = request.getParameter("orderPickListId");
		if(StringUtils.isEmpty(orderPickListId)){
			orderPickListId = (String)request.getSession().getAttribute("orderPickListId");
			request.getSession().removeAttribute("orderPickListId");
		}
		if(StringUtils.isNotEmpty(orderPickListId)){
			OrderPickList orderPickList = orderPickListManager.getById(new Integer(orderPickListId));
			request.setAttribute("orderPickList", orderPickList);
		}
		
		removeNavFromSearchCriteria(request);
		
		return new ModelAndView(listView);
	}

	
	public ModelAndView createPickList(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("availableOrderShipments", salesOrderManager.getShipments4Picking());
		return new ModelAndView("/order/createPickList");
	}

	public ModelAndView completeCreatePickList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			String orderShipmentIdVesions [] = request.getParameterValues("multiIds");
			
			if (orderShipmentIdVesions==null){
				return ajaxView.setStatus(new Short("0"));
			}
			
			OrderPickList orderPickList = orderProcessFlowManager.createPickList(orderShipmentIdVesions, appUser);
			//统计待备货的发货项数量
			Integer availableAmount = salesOrderManager.countShipments4Picking();
			
			Map<String, String> jmap = new HashMap<String, String>();
			if(orderPickList!=null){
				jmap.put("orderPickListId", orderPickList.getOrderPickListId().toString());
				jmap.put("createTime", DateUtil.convertDateTimeToString(orderPickList.getCreateTime()));
			}
			jmap.put("availableAmount", availableAmount.toString());
			ajaxView.setData(jmap);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxView.setStatus(new Short("-500"));
		}
		
		return ajaxView;
	}
	
	public ModelAndView removeFromPickList(HttpServletRequest request,
			HttpServletResponse response){
		String orderShipmentId = request.getParameter("orderShipmentId");
		orderPickListManager.removeFromPickList(orderShipmentId);
		request.getSession().setAttribute("orderPickListId", request.getParameter("orderPickListId"));
		return getRedirectToActionView("defaultAction");
	}
	
	/**
	 * 校验订单发货项是否可以[完成发货]了
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView validateShipment(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> jmap = new HashMap<String, Object>();
		ajaxView.setData(jmap);
		String shipmentNo = request.getParameter("shipmentNo");
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentByShipmentNo(shipmentNo);
		if(orderShipment!=null){
			ajaxView.setStatus(new Short("1"));
			OrderAddress oa = orderShipment.getOrderAddress();
			jmap.put("firstName", oa.getFirstname());
			jmap.put("lastName", oa.getLastname());
			jmap.put("shippingAddress", oa.getState()+" " + oa.getCity() + " " + oa.getAddress1() + " " + (oa.getAddress2()==null?"":oa.getAddress2()));
			jmap.put("carrierName", orderShipment.getCarrierName());
			List<Map<String, String>> items = new ArrayList<Map<String, String>>();
			for (OrderSku sku : orderShipment.getOrderSkus())
			{
				Map<String, String> skuItem = new HashMap<String, String>();
				skuItem.put("id", sku.getOrderSkuId().toString());
				skuItem.put("productName", sku.getProductName());
				skuItem.put("skuCode", sku.getProductSkuCode());
				skuItem.put("accessories", empty(sku.getAccessories())?"":sku.getAccessories());
				skuItem.put("quantity", sku.getQuantity().toString());
				items.add(skuItem);
			}
			jmap.put("skuItems", items);
		}else{
			ajaxView.setStatus(new Short("0"));
		}
		return ajaxView;
	}
	
	/**
	 * 完成发货
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeShipping(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AjaxView ajaxView=new AjaxView(response);
		String shipmentNo = request.getParameter("shipmentNo");
		String trackingNo = request.getParameter("trackingNo");
		String carrierName = request.getParameter("carrierName");
		BigDecimal shippingCostPaid = null;
		if (!empty(request.getParameter("shippingCostPaid")))
		{
			shippingCostPaid = new BigDecimal(request.getParameter("shippingCostPaid"));
		}
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentByShipmentNo(shipmentNo);
		try
		{
			Set<OrderSku> skus = orderShipment.getOrderSkus();
			List<Map<String, Integer>> originalSkus = new ArrayList<Map<String, Integer>>();
			int qty_count = 0;
			for (OrderSku sku: skus)
			{
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("skuId", sku.getOrderSkuId());
				map.put("skuQty", sku.getQuantity());
				qty_count += new Integer(request.getParameter(sku.getOrderSkuId()+"_qty"));
				originalSkus.add(map);
			}
			if (qty_count == 0) //如果完成发货时,输入的qty全部为0.则取消本次发货
			{
				ajaxView.setStatus(new Short("0"));
				ajaxView.setMsg(getMessage("orderPickList.completeShipping.qty_all_zero"));
				return ajaxView;
			}
			for (Map<String, Integer> sku: originalSkus)
			{
				Integer qty = new Integer(request.getParameter(sku.get("skuId")+"_qty"));
				if (qty != sku.get("skuQty") && qty < sku.get("skuQty"))
				{
					//拆单
					Map<Integer, String> moveToMap = salesOrderManager.getShipments4MoveItemById(orderShipment.getSalesOrderId(), orderShipment.getOrderShipmentId());
					if (moveToMap.isEmpty())
					{
						salesOrderManager.doMoveItem(sku.get("skuId"), null, new Integer(sku.get("skuQty") - qty));
					}
					else
					{
						//拆单时,如果已经存在orderShipment,选第一个.
						Integer[] osIds = {};
						osIds = moveToMap.keySet().toArray(osIds);
						salesOrderManager.doMoveItem(sku.get("skuId"), osIds[0], new Integer(sku.get("skuQty") - qty));
					}
				}
				else if (qty > sku.get("skuQty")) //数量参数出错
				{
					ajaxView.setStatus(new Short("-1"));
					ajaxView.setMsg(getMessage("orderPickList.completeShipping.qty_error"));
					return ajaxView;
				}
			}
			orderProcessFlowManager.doCompletShipping(shipmentNo, 
					carrierName, 
					trackingNo, 
					(AppUser) RequestContext.getCurrentUser(), 
					shippingCostPaid);
			ajaxView.setStatus(new Short("1"));
			ajaxView.setMsg(getMessage("orderPickList.completeShipping.success",shipmentNo));
		}
		catch(Exception e)
		{
			ajaxView.setStatus(new Short("-2"));
			ajaxView.setMsg(getMessage("orderPickList.completeShipping.error"));
			return ajaxView;
		}
		return ajaxView;
	}
	
	public ModelAndView printPickList(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String, Object> model = getInitModel();
		updateModel(request, model);
		
		PdfView4Picking pdfView = new PdfView4Picking();
		
		return new ModelAndView(pdfView, model);
	}
	
/*	
 * 通过pdf打印
	public ModelAndView printPackingSlip(HttpServletRequest request,
			HttpServletResponse response){
		
		Map<String, Object> model = getInitModel();
		updateModel(request, model);
		
		PdfView4Packing pdfView = new PdfView4Packing();
		
		return new ModelAndView(pdfView, model);
	}*/
	
	
	 
	public ModelAndView printPackingSlip(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute(RequestConstants.DECORATOR, "printable");
		String orderShipmentId = request.getParameter("orderShipmentId");
		OrderShipment orderShipment=salesOrderManager.getOrderShipmentById(orderShipmentId);
		request.setAttribute("orderShipment", orderShipment);
		return new ModelAndView("/order/orderPrint_packingSlip"); 
	}
	
	public ModelAndView printReceipt(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute(RequestConstants.DECORATOR, "printable");
		String orderShipmentId = request.getParameter("orderShipmentId");
		OrderShipment orderShipment=salesOrderManager.getOrderShipmentById(orderShipmentId);
		request.setAttribute("orderShipment", orderShipment);
		return new ModelAndView("/order/orderPrint_receipt"); 
	}
	
	private void updateModel(HttpServletRequest request, Map<String, Object> model){
		String orderPickListId = request.getParameter("orderPickListId");
		String orderShipmentId = request.getParameter("orderShipmentId");
		Set<OrderShipment> orderShipments = null;
		if(orderShipmentId!=null && !"".equals(orderShipmentId.trim())){
			orderShipments = new HashSet<OrderShipment>();
			orderShipments.add(salesOrderManager.getOrderShipmentById(orderShipmentId));
		}else{
			OrderPickList orderPickList = orderPickListManager.getById(new Integer(orderPickListId));
			orderShipments = orderPickList.getOrderShipments();
		}
		model.put("orderShipments", orderShipments);
	}
	
	private Map<String, Object> getInitModel(){
		Map<String, Object> model = new HashMap<String, Object>();
		String siteUrl = ConfigUtil.getInstance().getStore().getSiteUrl();
		model.put(Constants.CONTEXT_PATH, siteUrl);
		model.put("storeFrontSiteUrl", siteUrl);
		return model;
	}
	
	public ModelAndView printEMS(HttpServletRequest request,HttpServletResponse response){
		ModelAndView ems = new ModelAndView("/order/orderPrint_EMS");
		String orderShipmentId = request.getParameter("orderShipmentId");
		OrderShipment os = salesOrderManager.getOrderShipmentById(orderShipmentId);
		
		request.setAttribute("ship", os);
		Set<OrderSku> skus = os.getOrderSkus();
		StringBuffer sb = new StringBuffer();
		int s = 0;
		for(OrderSku sku:skus){
			sb.append(sku.getProductName()+"\n");
			s += sku.getQuantity();
		}
		request.setAttribute("productNames", sb.toString());
		request.setAttribute("size", s);
		return ems;
	}

	
	public void setOrderProcessFlowManager(
			OrderProcessFlowManager orderProcessFlowManager) {
		this.orderProcessFlowManager = orderProcessFlowManager;
	}
    public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}
	public void setOrderPickListManager(OrderPickListManager inMgr) {
        this.orderPickListManager = inMgr;
    }
} 




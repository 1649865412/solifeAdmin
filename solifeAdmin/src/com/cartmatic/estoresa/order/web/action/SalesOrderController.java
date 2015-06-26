package com.cartmatic.estoresa.order.web.action;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderMessage;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.common.service.GiftCertificateService;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.PaymentMethodService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.common.service.SupplierService;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.OrderMessageManager;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.SalesOrderGeoipManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.system.service.ShippingRateManager;
import com.cartmatic.estore.system.service.WrapManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class SalesOrderController extends GenericController<SalesOrder> 
{
    private SalesOrderManager salesOrderManager = null;
    private MailEngine	mailEngine					= null;
    private AddressManager addressManager = null;    
    private ShippingRateManager shippingRateManager = null;    
    private OrderProcessFlowManager orderProcessFlowManager = null;
    private WrapManager wrapManager = null;
    private GiftCertificateService giftCertificateService = null;
    private CouponManager couponManager=null;
    private OrderMessageManager orderMessageMgr = null; 
    //private OrderAuditHelper orderAuditHelper=null;
    private SupplierService supplierService=null;
	private SalesOrderGeoipManager salesOrderGeoipManager=null;
	
	
	private ProductService productService=null;
	
	 private PaymentMethodService paymentMethodService=null;
	 
	 private OrderService orderService = null;
	 
	public void setOrderMessageMgr(OrderMessageManager orderMessageMgr) {
		this.orderMessageMgr = orderMessageMgr;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SalesOrder entity) {
		return entity.getSalesOrderName();
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
		mgr = salesOrderManager;
		//订单常量
		Field[] fs = OrderConstants.class.getFields();
		for(Field f: fs){
			ServletContext application = this.getServletContext();
			try {
				application.setAttribute(f.getName(), f.get(null));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SalesOrder entity, BindException errors) {
	}
	
	
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria sc = createSearchCriteria(request);
		request.setAttribute("paymentMethodList", this.paymentMethodService.getPaymentMethodByCart(false));
		return getModelAndView(listView, listModelName, salesOrderManager.searchByCriteria(sc));
	}
	
	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		//根据search参数来设置filter
		String search = request.getParameter("search");
		SearchCriteria sc =null;
		if(StringUtils.isNotEmpty(search))
		{
			request.setAttribute("search", search);
			request.getSession().removeAttribute("orderFilter");
			if("ask4Cancel".equals(search)){
				sc = createSearchCriteria(request,"ask4Cancel");
			}else if("awaitingPicking".equals(search)){
				sc = createSearchCriteria(request,"awaitingPicking");
			}else if("hasProblem".equals(search)){
				sc = createSearchCriteria(request,"hasProblem");
			}else if("picking".equals(search)){
				sc = createSearchCriteria(request,"picking");
			}else if("partiallyShipped".equals(search)){
				sc = createSearchCriteria(request,"partiallyShipped");
			}else if("partiallyPaid".equals(search)){
				sc = createSearchCriteria(request,"partiallyPaid");
			}else if("confirmedByRobot".equals(search)){
				sc = createSearchCriteria(request,"confirmedByRobot");
			}else if("newMessages".equals(search)){
				sc = createSearchCriteria(request,"newMessages");
			}else if("finished".equals(search)){
				sc = createSearchCriteria(request,"finished");
			}else if("payment".equals(search)){
				sc = createSearchCriteria(request,"byPaymentType");
				sc.addParamValue(new Short(request.getParameter("paymentType")));
				sc.lockParamValues();
			}
		}
		if(sc==null){
			sc = createSearchCriteria(request);
		}
		return getModelAndView("/order/include/salesOrderListContentBody", listModelName, salesOrderManager.searchByCriteria(sc));
	}

	protected SalesOrder formBackingObject(HttpServletRequest request) {
		markIsFormAction(request);
		
		SalesOrder entity = null;
		String salesOrderId = request.getParameter(entityIdName);
		String isEdit = request.getParameter("isEdit");
		if("true".equals(isEdit))
		{
			//加锁订单
			entity = salesOrderManager.doGetAndLockById(salesOrderId);
		}else{
			entity = salesOrderManager.getForViewById(salesOrderId);
		}
		setCustomerAddresses(request, entity.getCustomerId());		
		Set<OrderShipment> orderShipments  = entity.getOrderShipments();
		for(OrderShipment orderShipment: orderShipments){
			setShippingRates(orderShipment);
		}		
		
		if (entity == null) {
			try {
				entity = entityClass.newInstance();
				// TODO, move to showForm will be better?
				request.setAttribute(NEW_ENTITY_MARKER, Boolean.TRUE);
			} catch (Throwable e) {// should not happen
				new RuntimeException("Unexpected error!", e);
			}
		}
		if(entity.getGainedCouponTypeId()!=null){
			Coupon coupon=couponManager.getIdleCoupon(entity.getGainedCouponTypeId());
			request.setAttribute("gainedCoupon", coupon);
		}
		request.setAttribute("entityClassName", entityClassName);
		return entity;
	}
	
	
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//防止URL刷新
		if("1".equals(request.getParameter("formSubmit"))){
			StringBuilder exceptionMsg = new StringBuilder();
			int rtn = 0;
			try{
				rtn = salesOrderManager.saveAll(request.getParameterMap(), exceptionMsg);
				if(rtn==1){
					String orderNo = request.getParameter("orderNo");
					saveMessage(Message.info("salesOrder.save.order.succeed", orderNo));
				}
			}catch(OutOfStockException e){
				rtn=-2;
				saveMessage(Message.errorMsg(exceptionMsg.toString()));
			}
			request.setAttribute("rtn", rtn);
		}
		return getSalesOrderWithLock(request,false);
	}
	
	public ModelAndView lock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return getSalesOrderWithLock(request, true);
	}
	
	public ModelAndView unlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String salesOrderId = request.getParameter(entityIdName);
		SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId.toString()));
		unlock(salesOrder);
		saveMessage(Message.info("salesOrder.unlock.succeed",salesOrder.getOrderNo()));
		if("list".equals(request.getParameter("from"))){
			SearchCriteria sc = (SearchCriteria)request.getSession().getAttribute("sc");
			sc=getCurrentNavSearchCriteria(request);
			return getRedirectToActionView("defaultAction").addObject("search", request.getParameter("search"))
							.addObject(SearchCriteria.PRM_ITEMS_PER_PAGE, sc.getPageSize())
							.addObject(SearchCriteria.PRM_CURRENT_PAGE_NO, sc.getPageNo());
		}
		return getSalesOrderWithLock(request,false);
	}
	
	private void unlock(SalesOrder salesOrder){
		salesOrder.setIsLocked(Constants.FLAG_FALSE);
		salesOrder.setLockedBy(null);
		salesOrderManager.save(salesOrder);
	}
	
	/**
	 * 重新计算发货项款项
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView calculateShipmentTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> jmap = new HashMap<String, Object>();
		ajaxView.setData(jmap);
		try {
			String orderShipmentId = request.getParameter("orderShipmentId");
			OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(new Integer(orderShipmentId));

			Integer wrapId = new Integer(request.getParameter("wrapId"));
			if(wrapId>0){
				Wrap wrap = this.wrapManager.getById(wrapId);
				orderShipment.setWrapUnitPrice(wrap.getDefaultPrice());
			}
			
			Integer shippingAddressId = new Integer(request.getParameter("shippingAddressId"));
			List<String> shippingMethods = null;
			if(shippingAddressId!=0){
				Address address = addressManager.getById(shippingAddressId);
				shippingMethods = getShippingMethods(address.getCountryName(), address.getStateName(), address.getCityName());
			}else{
				OrderAddress orderAddress = orderShipment.getOrderAddress();
				if(orderAddress!=null)
					shippingMethods = getShippingMethods(orderAddress.getCountry(), orderAddress.getState(), orderAddress.getCity());
			}
			
			Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
			BigDecimal itemSubTotal = BigDecimal.ZERO;
			BigDecimal totalBeforeTax = BigDecimal.ZERO;
			BigDecimal totalAfterTax = BigDecimal.ZERO;
			while(i.hasNext()){
				OrderSku orderSku = i.next();
				orderSku.getProductSku().getProduct().getProductTypeId();
				Integer orderSkuId = orderSku.getOrderSkuId();
				//获取页面参数值
				Integer quantity = Integer.parseInt(request.getParameter("quantity" + orderShipmentId + orderSkuId) );
				BigDecimal discountPrice = new BigDecimal(request.getParameter("discountPrice" + orderShipmentId + orderSkuId));
				BigDecimal itemDiscountAmount = new BigDecimal(request.getParameter("itemDiscountAmount" + orderShipmentId + orderSkuId));
				//为税费、运输费计算设置
				orderSku.setQuantity(quantity);
				orderSku.setDiscountPrice(discountPrice);
				orderSku.setItemDiscountAmount(itemDiscountAmount);
				//款项重新汇总
				itemSubTotal = itemSubTotal.add(BigDecimal.valueOf(quantity).multiply(discountPrice).add(itemDiscountAmount.negate()));
			}
			String shippingRateId = request.getParameter("shippingRateId");
			if(StringUtils.isNotEmpty(shippingRateId))
				orderShipment.setShippingRateId(new Integer(shippingRateId));
			//税费、运输费计算
			salesOrderManager.caculateTaxesAndShippingCost(orderShipment, true);
			
			String discountAmount = request.getParameter("discountAmount");
			
			totalBeforeTax = itemSubTotal.add(orderShipment.getShippingCost()).add(new BigDecimal(discountAmount).negate()).add(orderShipment.getWrapUnitPrice());
			totalAfterTax = totalBeforeTax.add(orderShipment.getItemTax()).add(orderShipment.getShippingTax());
			
			
			jmap.put("orderShipmentId", orderShipmentId);
			jmap.put("itemSubTotal", getFormatedAmount(itemSubTotal));
			jmap.put("totalBeforeTax", getFormatedAmount(totalBeforeTax));
			jmap.put("itemTax", getFormatedAmount(orderShipment.getItemTax()));
			jmap.put("shippingTax", BigDecimal.ZERO);
			jmap.put("totalAfterTax", getFormatedAmount(totalAfterTax));
			jmap.put("wrapTotal", getFormatedAmount(orderShipment.getWrapUnitPrice()));
			jmap.put("shippingCost", getFormatedAmount(orderShipment.getShippingCost()));
			jmap.put("shippingRateId", orderShipment.getShippingRateId());
			if(shippingMethods!=null&&shippingMethods.size()>0)
				jmap.put("shippingMethods", shippingMethods);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	private String getFormatedAmount(BigDecimal amount){
		if(amount==null)
			return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 读取编辑发票信息、运输地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView editOrderAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String salesOrderId = request.getParameter("salesOrderId");
		String orderShipmentId = request.getParameter("orderShipmentId");
		
		if(StringUtils.isEmpty(orderShipmentId)){
			SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId));
			request.setAttribute("invoiceTitle", salesOrder.getInvoiceTitle());
			request.setAttribute("orderAddress", salesOrder.getOrderAddress());
		}else{
			String orderAddressId = request.getParameter("orderAddressId");
			OrderAddress orderAddress = null;
			// 0 代表原有发货地址
			if("0".equals(orderAddressId)){
				orderAddress = salesOrderManager.getOrderShipmentById(orderShipmentId).getOrderAddress();
				request.setAttribute("orderAddressId", orderAddressId);
			}else{
				//会员注册地址
				orderAddress = new OrderAddress();
				Address address = addressManager.getById(new Integer(orderAddressId));
				orderAddress.setFirstname(address.getFirstname());
				orderAddress.setLastname(address.getLastname());
				orderAddress.setCountry(address.getCountryName());
				orderAddress.setState(address.getStateName());
				orderAddress.setCity(address.getCityName());
				orderAddress.setAddress1(address.getAddress());
				orderAddress.setPostalcode(address.getZip());
				orderAddress.setPhoneNumber(address.getTelephone());
				request.setAttribute("orderAddressId", address.getAddressId().toString());
			}
			request.setAttribute("orderShipmentId", orderShipmentId);
			request.setAttribute("orderAddress", orderAddress);
		}
		
		request.setAttribute("salesOrderId", salesOrderId);
		
		return new ModelAndView("order/orderAddressForm");
	}
	/**
	 * 保存发票信息、运输地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveOrderAddress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> jmap = new HashMap<String, Object>();
		ajaxView.setData(jmap);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			String orderShipmentId = request.getParameter("orderShipmentId");
			
			if(orderShipmentId==null) orderShipmentId = "";
			request.setAttribute("salesOrderId", salesOrderId);
			//保存发票信息
			if(StringUtils.isEmpty(orderShipmentId)){
				String invoiceTitle = request.getParameter("invoiceTitle");
				
				SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId));
				salesOrder.setInvoiceTitle(invoiceTitle);
				
				OrderAddress orderAddress = salesOrder.getOrderAddress();
				setOrderAddressByParams(orderAddress, orderShipmentId, request);
				salesOrderManager.save(salesOrder);
				ajaxView.setMsg(getMessage("salesOrder.update.billingAddress.succeed",salesOrder.getOrderNo()));
			}else{
				//保存发货地址
				OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(orderShipmentId);
				OrderAddress orderAddress = orderShipment.getOrderAddress();
				setOrderAddressByParams(orderAddress, orderShipmentId, request);
				
				salesOrderManager.save(orderShipment);
				OrderAddress oa = orderShipment.getOrderAddress();
				
				jmap.put("orderShipmentId", orderShipmentId);
				jmap.put("shippingRateId", orderShipment.getShippingRateId());
				List<String> shippingMethods = getShippingMethods(oa.getCountry(), oa.getState(), oa.getCity());
				if(shippingMethods!=null&&shippingMethods.size()>0)
					jmap.put("shippingMethods", shippingMethods);
				ajaxView.setMsg(getMessage("salesOrder.update.shippingAddress.succeed",orderShipment.getShipmentNo()));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	private void setOrderAddressByParams(OrderAddress orderAddress, String orderShipmentId, HttpServletRequest request){
		String postalcode = request.getParameter("postalcode"+orderShipmentId);
		String country = request.getParameter("country"+orderShipmentId);
		String state = request.getParameter("state"+orderShipmentId);
		String city = request.getParameter("city"+orderShipmentId);
		String address1 = request.getParameter("address1"+orderShipmentId);
		String address2 = request.getParameter("address2"+orderShipmentId);
		
		orderAddress.setPostalcode(postalcode);
		orderAddress.setCountry(country);
		orderAddress.setState(state);
		orderAddress.setCity(city);
		orderAddress.setAddress1(address1);
		orderAddress.setAddress2(address2);
		
		if(!StringUtils.isEmpty(orderShipmentId)){
			String phoneNumber = request.getParameter("phoneNumber"+orderShipmentId);
			String firstname = request.getParameter("firstname"+orderShipmentId);
			String lastname = request.getParameter("lastname"+orderShipmentId);
			
			orderAddress.setFirstname(firstname);
			orderAddress.setLastname(lastname);
			orderAddress.setPhoneNumber(phoneNumber);
		}
	}
	
	private List<String> getShippingMethods(String countryName, String stateName, String cityName){
		List<ShippingRate> shippingRates = shippingRateManager.findShippingRateByRegionNames(countryName, stateName,cityName);
		List<String> shippingMethods = new ArrayList<String>();
		if(shippingRates!=null){
			for(ShippingRate shippingRate :shippingRates){
				shippingMethods.add(shippingRate.getShippingRateId().toString());
				shippingMethods.add(shippingRate.getShippingMethod().getShippingMethodName());
			}
		}
		
		return shippingMethods;
	}
	
	
	/**
	 * 添加商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderShipmentId = request.getParameter("orderShipmentId");
			String productSkuId = request.getParameter("productSkuId");
			int rtn = 0;
			try{
				rtn = salesOrderManager.addItem(orderShipmentId, productSkuId, (AppUser) RequestContext.getCurrentUser());
				ProductSku productSku=productService.getProductSku(new Integer(productSkuId));
				OrderShipment orderShipment=salesOrderManager.getOrderShipmentById(orderShipmentId);
				ajaxView.setMsg(getMessage("order.shipment.tip.addItem.succeed",orderShipment.getShipmentNo(),productSku.getProductSkuCode()));
			}catch(OutOfStockException e){
				rtn = -2;
				ajaxView.setMsg(getMessage("order.shipment.tip.outOfStock"));
				ajaxView.setStatus(new Short("-2"));
			}
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 移动商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView moveItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String salesOrderId = request.getParameter("salesOrderId");
		String orderShipmentId = request.getParameter("orderShipmentId");
		String orderSkuId = request.getParameter("orderSkuId");
		
		Map<Integer, String> moveToMap = salesOrderManager.getShipments4MoveItemById(salesOrderId, orderShipmentId);
		request.setAttribute("moveToMapSize", moveToMap.size());
		request.setAttribute("moveToMap", moveToMap);
		
		OrderSku orderSku = salesOrderManager.getOrderSkuById(orderSkuId);
		if(orderSku.getOrderShipment().getOrderSkus().size()>1)
			request.setAttribute("quantityMax", orderSku.getQuantity());
		else
			request.setAttribute("quantityMax", orderSku.getQuantity()-1);
		
		return new ModelAndView("order/moveItemForm");
	}
	
	/**
	 * 移除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String orderSkuId = request.getParameter("orderSkuId");
		OrderSku orderSku=salesOrderManager.getOrderSkuById(orderSkuId);
		Message message=Message.info("order.shipment.tip.removeItem.succeed", orderSku.getOrderShipment().getShipmentNo(),orderSku.getProductSkuCode());
		salesOrderManager.doRemoveItem(orderSkuId);
		saveMessage(message);
		return this.getSalesOrderWithLock(request, false);
	}
	
	/**
	 * 重新采购商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView repurchaseItem(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String orderSkuId = request.getParameter("orderSkuId");
		supplierService.createPurchaseOrderItem(salesOrderManager.getOrderSkuById(new Integer(orderSkuId)));
		return this.getSalesOrderWithLock(request, false);
	}
	
	
	public ModelAndView completeMoveItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderSkuId = request.getParameter("orderSkuId");
			String quantity = request.getParameter("quantity");
			
			String moveItemType = request.getParameter("moveItemType");
			if("move".equals(moveItemType)){
				String toOrderShipmentId = request.getParameter("toOrderShipmentId");
				OrderShipment orderShipment=salesOrderManager.doMoveItem(orderSkuId, toOrderShipmentId, new Integer(quantity));
				//提示信息
				if(orderShipment==null){
					orderShipment=salesOrderManager.getOrderShipmentById(new Integer(toOrderShipmentId));
				}
				OrderSku orderSku=salesOrderManager.getOrderSkuById(new Integer(orderSkuId));
				ajaxView.setMsg(getMessage("order.shipment.tip.moveItem.succeed", quantity,orderSku.getPrice(),orderShipment.getShipmentNo()));
			}else{
				//new
				OrderShipment orderShipment=salesOrderManager.doMoveItem(orderSkuId, null, new Integer(quantity));
				OrderSku orderSku=salesOrderManager.getOrderSkuById(new Integer(orderSkuId));
				ajaxView.setMsg(getMessage("order.shipment.tip.moveItem.succeed", quantity,orderSku.getPrice(),orderShipment.getShipmentNo()));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 添加备注
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addNote(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			String detail = request.getParameter("detail");
			String orderNo = request.getParameter("orderNo");
			salesOrderManager.addNote(salesOrderId, detail);
			ajaxView.setMsg(getMessage("salesOrder.addNote.succeed",orderNo));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView editNote(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderAuditId = request.getParameter("orderAuditId");
			String detail = request.getParameter("detail");
			String orderNo = request.getParameter("orderNo");
			salesOrderManager.editNote(orderAuditId, detail);
			ajaxView.setMsg(getMessage("salesOrder.editNote.succeed",orderNo));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 添加message
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			String orderNo = request.getParameter("orderNo");
			String customerId = request.getParameter("customerId");
			String subject = request.getParameter("subject");
			String message = request.getParameter("message");
			orderMessageMgr.addByAdmin(new Integer(salesOrderId), orderNo, new Integer(customerId), subject, message);
			//发邮件通知
			SalesOrder salesOrder=salesOrderManager.getById(new Integer(salesOrderId));
			Store store = salesOrder.getStore();
			String from=ConfigUtil.getInstance().getStore(store.getCode()).getEmailSender();
			Map<String,Object>dataMap=new HashMap<String, Object>();
			dataMap.put("orderNo",orderNo);
			dataMap.put("orderMessageSubject",subject);
			dataMap.put("orderMessageContent",message);
			mailEngine.sendSimpleTemplateMailByStoreCode("order/replyOrderMessage.vm", store.getCode() ,dataMap,null,from,salesOrder.getCustomerEmail());
			ajaxView.setMsg(getMessage("order.messageInfo.send.message.succeed",orderNo,salesOrder.getCustomerEmail()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView delMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String orderMessageId = request.getParameter("orderMessageId");
		String orderNo = request.getParameter("orderNo");	
		orderMessageMgr.deleteById(new Integer(orderMessageId));
		saveMessage(Message.info("orderMessage.delete.succeed", orderNo));
		String salesOrderId = request.getParameter("salesOrderId");
		//tabList方式显示订单表单的保存数据后需要知道保存的是哪个tab
		String tid="";
		if(StringUtils.isNotBlank(request.getParameter("tid"))){
			tid="&tid="+request.getParameter("tid");
		}
		return getRedirectView("/order/window.html?doAction=edit&salesOrderId="+salesOrderId+"&tabIndex=4"+tid);
	}
	
	public ModelAndView updateMessageStatus(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String orderMessageId = request.getParameter("orderMessageId");
		String status = request.getParameter("status");
		String salesOrderId = request.getParameter("salesOrderId");
		OrderMessage orderMessage=orderMessageMgr.getById(new Integer(orderMessageId));
		if(status.equals("read")){
			orderMessage.setStatus(Constants.STATUS_ACTIVE);
			saveMessage(Message.info("orderMessage.has.marked.read"));
		}else if(status.equals("unread")){
			orderMessage.setStatus(Constants.STATUS_NOT_PUBLISHED);
			saveMessage(Message.info("orderMessage.has.marked.unread"));
		}
		orderMessageMgr.save(orderMessage);
		//tabList方式显示订单表单的保存数据后需要知道保存的是哪个tab
		String tid="";
		if(StringUtils.isNotBlank(request.getParameter("tid"))){
			tid="&tid="+request.getParameter("tid");
		}
		return getRedirectView("/order/window.html?doAction=edit&salesOrderId="+salesOrderId+"&tabIndex=4"+tid);
	}
	
	
	public ModelAndView confirmShipment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderShipmentId = request.getParameter("orderShipmentId");
			int rtn = orderProcessFlowManager.doConfirmShipment(orderShipmentId, (AppUser) RequestContext.getCurrentUser());
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 重新发送确认email
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView sendReConfirmEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");		
			SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId));		
			salesOrderManager.sendNotificationEmail(OrderConstants.MAIL_TYPE_RECONFIRM, salesOrder);	
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 赠送礼券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView sendGcForSalesOrder(HttpServletRequest request,	HttpServletResponse response) throws Exception
	{
		AjaxView ajaxView=new AjaxView(response);
		String orderNo="";
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId));
			orderNo=salesOrder.getOrderNo();
			BigDecimal amt = new BigDecimal(request.getParameter("gcAmt"));
			GiftCertificate gc = giftCertificateService.createForSalesOrder(salesOrder, amt);
			Map model = new HashMap();
			model.put("giftCertificate", gc);
			model.put("salesOrder", salesOrder);
			
			Store store = salesOrder.getStore();
			String from=ConfigUtil.getInstance().getStore(store.getCode()).getEmailSender();
			mailEngine.sendSimpleTemplateMailByStoreCode("sales/giftcertificateForOrder.vm", store.getCode() , model, null,from, gc.getRecipientEmail());
			
			ajaxView.setMsg(getMessage("salesOrder.sendGcEmail.successfully",orderNo,amt,salesOrder.getCustomerEmail()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("salesOrder.sendGcEmail.failed",orderNo));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView cancelShipment(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderShipmentId = request.getParameter("orderShipmentId");
			int rtn = orderProcessFlowManager.doCancelShipment(orderShipmentId, (AppUser) RequestContext.getCurrentUser());
			OrderShipment orderShipment=salesOrderManager.getOrderShipmentById(orderShipmentId);
			ajaxView.setStatus(new Short(rtn+""));
			if(rtn==1){
				ajaxView.setMsg(getMessage("order.shipment.tip.cancelShipment.succeed", orderShipment.getShipmentNo()));
			}else{
				ajaxView.setMsg(getMessage("order.shipment.tip.cancelShipment.fail", orderShipment.getShipmentNo()));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
		
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView cancelOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			String orderNo = request.getParameter("orderNo");	
			int rtn = orderProcessFlowManager.doCancelOrder(salesOrderId, (AppUser) RequestContext.getCurrentUser());
			ajaxView.setStatus(new Short(rtn+""));
			ajaxView.setMsg(getMessage("salesOrder.cancelOrder.succeed",orderNo));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 取消有问题的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView markProblem(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			String flag = request.getParameter("flag");
			String orderNo = request.getParameter("orderNo");	
			SalesOrder so = salesOrderManager.getSalesOrderByOrderNo(orderNo);
			if ("0".equals(flag))
				so.setHasProblem(Constants.FLAG_FALSE);
			else
				so.setHasProblem(Constants.FLAG_TRUE);
			salesOrderManager.save(so);
			ajaxView.setMsg(getMessage("order.resumeOrder.succeed",orderNo));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 暂停订单处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView holdOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			int rtn = orderProcessFlowManager.doHoldOrder(salesOrderId, (AppUser) RequestContext.getCurrentUser());
			ajaxView.setStatus(new Short(rtn+""));
			SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId.toString()));
			ajaxView.setMsg(getMessage("order.holdOrder.succeed",salesOrder.getOrderNo()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 恢复订单处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView resumeOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			String salesOrderId = request.getParameter("salesOrderId");
			int rtn = orderProcessFlowManager.doResumeOrder(salesOrderId, (AppUser) RequestContext.getCurrentUser());
			ajaxView.setStatus(new Short(rtn+""));
			SalesOrder salesOrder = salesOrderManager.getById(new Integer(salesOrderId.toString()));
			ajaxView.setMsg(getMessage("order.resumeOrder.succeed",salesOrder.getOrderNo()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 手工支付
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView createPayment(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String orderNo = request.getParameter("orderNo");
		if(StringUtils.isBlank(orderNo)){
			saveMessage(Message.error("orderPayment.createPayment.errMsg.orderNo.required"));
			return new ModelAndView("order/createPayment");
		}
		orderNo = orderNo.trim();
		SalesOrder salesOrder =salesOrderManager.getSalesOrderByOrderNo(orderNo);
		if(salesOrder==null){
			saveMessage(Message.error("orderPayment.createPayment.errMsg.order.not.found"));
		}
		request.setAttribute("orderNo", orderNo);
		String transactionType = request.getParameter("transactionType");
		String paymentAmount = request.getParameter("paymentAmount");
		
		try {
			if(new BigDecimal(paymentAmount).equals(BigDecimal.ZERO)){
				saveMessage(Message.error("orderPayment.createPayment.errMsg.paymentAmount.required"));
			}
		} catch (Exception e) {
			saveMessage(Message.error("orderPayment.createPayment.errMsg.paymentAmount.required"));
		}
		
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setOrderNo(orderNo);
		orderPayment.setTransactionType(new Short(transactionType));
		orderPayment.setPaymentAmount(new BigDecimal(paymentAmount));
		orderPayment.setPaymentGatewayName(request.getParameter("paymentGatewayName"));
		String ip = request.getRemoteAddr();
		if(ip!=null) 
		{
			ip = ip.substring(0, ip.length()>=32?32:ip.length());
		}
		orderPayment.setIpAddress(ip);
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		orderPayment.setAddedBy(appUser.getUsername());
		int rtn = orderProcessFlowManager.createPaymentStoreManager(orderPayment);
		if(rtn==1){
			request.setAttribute("salesOrder", salesOrder);
			saveMessage(Message.info("orderPayment.tip.paySuccess"));
			this.orderService.sendPaymentResultToStore(null, salesOrder.getOrderNo());
		}else if(rtn==-1){
			saveMessage(Message.error("orderPayment.createPayment.errMsg"));
		}else if(rtn==-2){
			saveMessage(Message.error("orderPayment.tip.exchangeOrder"));
		}
		return new ModelAndView("order/createPayment");
	}
	
	/**
	 * 第三方银行的订单查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNo = request.getParameter("orderNo");
		String paymentMethodStr = request.getParameter("paymentMethodId");
		SalesOrder order = this.orderService.getSalesOrderByOrderNo(orderNo);
		PaymentMethod paymentMethod = this.paymentMethodService.getPaymentById(Integer.parseInt(paymentMethodStr));
		request.setAttribute("salesOrder", order);
		request.setAttribute("paymentMethod", paymentMethod);
		String returnStr = "order/bank/";
		returnStr += paymentMethod.getPaymentMethodCode();
		returnStr += "_orderSearch_request";
		return new ModelAndView(returnStr);
	}
	
	/**
	 * 下载交易对账单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downloadOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String paymentMethodStr = request.getParameter("paymentMethodId");
		PaymentMethod paymentMethod = this.paymentMethodService.getPaymentById(Integer.parseInt(paymentMethodStr));
		request.setAttribute("paymentMethod", paymentMethod);
		String returnStr = "order/bank/";
		returnStr += paymentMethod.getPaymentMethodCode();
		returnStr += "_download";
		return new ModelAndView(returnStr);
	}
	
	/**
	 * 退款请求
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView refundOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNo = request.getParameter("orderNo");
		SalesOrder order = this.orderService.getSalesOrderByOrderNo(orderNo);
		request.setAttribute("salesOrder", order);
		request.setAttribute("payAmount", request.getParameter("payAmount"));
		String paymentMethodCode = request.getParameter("paymentMethodCode");
		request.setAttribute("paymentMethodCode", paymentMethodCode);
		String returnStr = "order/bank/";
		returnStr += paymentMethodCode;
		returnStr += "_refund_request";
		return new ModelAndView(returnStr);
	}
	
	//@ TODO 没有使用,可以删除
	public ModelAndView autoCompleteShipmentNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			String prefix = request.getParameter("shipmentNo");
			List<String> shipmentNoList = salesOrderManager.autoCompleteShipmentNo(prefix, 8);
			ajaxView.setData(shipmentNoList);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView getPaymentDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String orderNo = request.getParameter("orderNo");
		if(StringUtils.isBlank(orderNo)){
			saveMessage(Message.error("orderPayment.createPayment.errMsg.orderNo.required"));
			return new ModelAndView("order/createPayment");
		}
		orderNo = orderNo.trim();
		request.setAttribute("orderNo", orderNo);
		
		SalesOrder salesOrder = salesOrderManager.getSalesOrderByOrderNo(orderNo);
		
		if(salesOrder!=null){
			request.setAttribute("salesOrder", salesOrder);
		}else{
			saveMessage(Message.error("orderPayment.createPayment.errMsg.order.not.found"));
		}
		
		return new ModelAndView("order/createPayment");
	}
		
	private ModelAndView getSalesOrderWithLock(HttpServletRequest request, boolean lock) throws Exception {
		SalesOrder entity = null;
		String salesOrderId = request.getParameter(entityIdName);
		
		if(lock){
			//加锁订单
			entity = salesOrderManager.doGetAndLockById(salesOrderId);
			saveMessage(Message.info("salesOrder.lock.succeed",entity.getOrderNo()));
		}else{
			entity = salesOrderManager.getForViewById(salesOrderId);
		}
		request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
		ServletRequestDataBinder binder = bindAndValidate(request, entity);
		BindException errors = new BindException(binder.getBindingResult());
		savedNavAndSearchCriteria(request, null, 
		            getMessage("common.message.nav.editing", new Object[]{getEntityName(entity)}));		
		
		setCustomerAddresses(request, entity.getCustomerId());
		
		Set<OrderShipment> orderShipments  = entity.getOrderShipments();
		for(OrderShipment orderShipment: orderShipments){
			setShippingRates(orderShipment);
		}
		
		
		//获取包装信息
		List<Wrap> wraps = wrapManager.getWrapsAllDesc();
		request.setAttribute("wraps", wraps);
		return showForm(request, errors);
	}
	
	
	/**
	 * 设置可用的运输方式
	 * @param orderShipment
	 */
	private void setShippingRates(OrderShipment orderShipment){
		OrderAddress oa = orderShipment.getOrderAddress();
		if(oa==null) return;
		List<ShippingRate> shippingRates = shippingRateManager.findShippingRateByRegionNames(oa.getCountry(), oa.getState(), oa.getCity());
		if(shippingRates!=null){
			for(ShippingRate shippingRate :shippingRates){
				orderShipment.addShippingMethod(shippingRate.getShippingRateId().toString(), 
					shippingRate.getShippingMethod().getShippingMethodName());
			}
		}
	}
	
	/**
	 * 如果是注册会员，设置可用运输地址
	 * @param request
	 * @param entity
	 */
	private void setCustomerAddresses(HttpServletRequest request, Integer customerId){		
		if(customerId!=null && customerId.intValue()!=-1){
			List<Address> shippingAddresses = addressManager.getAllShippingAddressByAppuserId(customerId);
			request.setAttribute("shippingAddresses", shippingAddresses);
		}
	}
	
	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		//对已全支付的订单，显示下单时的详细地址信息
		SalesOrder salesOrder = (SalesOrder) mv.getModel().get(formModelName);
		if(salesOrder.getPaymentStatus().intValue()==OrderConstants.PAYMENT_STATUS_PAID.intValue()){
			SalesOrderGeoip placeOrder_geoIP=salesOrderGeoipManager.getSalesOrderGeoipByOrderNoAndActionType(salesOrder.getOrderNo(),SalesOrderGeoip.PLACE_ORDER);
			mv.addObject("placeOrder_geoIP",placeOrder_geoIP);
		}
		PaymentMethod paymentMethod = null;
		if(salesOrder.getPaymentMethodId() != null){
			paymentMethod = this.paymentMethodService.getPaymentById(salesOrder.getPaymentMethodId());
			 mv.addObject("paymentMethod", paymentMethod);
		}
	}	
	
	public ModelAndView updateTrackingNoAndCostPaid(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		Integer orderShipmentId=ServletRequestUtils.getIntParameter(request,"orderShipmentId",0);
		try {
			String trackingNo = request.getParameter("trackingNo");
			BigDecimal shippingCostPaid = null;
			if (!empty(request.getParameter("shippingCostPaid")))
			{
				shippingCostPaid = new BigDecimal(request.getParameter("shippingCostPaid"));
			}
			OrderShipment orderShipment=salesOrderManager.updateTrackingNoAndCostPaid(orderShipmentId, trackingNo, shippingCostPaid);
			data.put("trackingNo", orderShipment.getTrackingNo());
			data.put("shippingCostPaid", shippingCostPaid==null?"":orderShipment.getShippingCostPaid());
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}
	
	public void setWrapManager(WrapManager wrapManager) {
		this.wrapManager = wrapManager;
	}

	public void setOrderProcessFlowManager(
			OrderProcessFlowManager orderProcessFlowManager) {
		this.orderProcessFlowManager = orderProcessFlowManager;
	}
	
	public void setShippingRateManager(ShippingRateManager shippingRateManager) {
		this.shippingRateManager = shippingRateManager;
	}

	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}

	public void setSalesOrderManager(SalesOrderManager inMgr) {
        this.salesOrderManager = inMgr;
    }
	
	public void setGiftCertificateService(GiftCertificateService avalue)
	{
		this.giftCertificateService = avalue;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}
	
	public void setOrderMessageManager(OrderMessageManager avalue)
	{
		this.orderMessageMgr = avalue;
	}

	public void setSalesOrderGeoipManager(
			SalesOrderGeoipManager salesOrderGeoipManager) {
		this.salesOrderGeoipManager = salesOrderGeoipManager;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public PaymentMethodService getPaymentMethodService() {
		return paymentMethodService;
	}

	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
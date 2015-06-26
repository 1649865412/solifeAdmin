/*
 * create date:2006-6-13
 */
package com.cartmatic.estore.cart.service.impl;


import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.CheckoutConstants;
import com.cartmatic.estore.cart.service.CheckoutManager;
import com.cartmatic.estore.cart.util.CheckoutUtil;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.common.service.CheckoutService;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.PaymentMethodService;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.common.service.ShoppingcartService;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.exception.GiftCertificateStateException;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author huang wenmin
 */
public class CheckoutManagerImpl extends GenericManagerImpl<SalesOrder> implements
		CheckoutManager {

	private ShoppingcartService shoppingcartService;
	private PromoService promoService;
	private CheckoutService checkoutService;
	private OrderService orderService;
	private PaymentMethodService paymentMethodService;
	private AddressManager addressManager;
	
	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}

	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}

	public void setShoppingcartService(ShoppingcartService shoppingcartService) {
		this.shoppingcartService = shoppingcartService;
	}

	public void setPromoService(PromoService promoService) {
		this.promoService = promoService;
	}

	public void setCheckoutService(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDelete(SalesOrder entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(SalesOrder entity) {
		// TODO Auto-generated method stub
		
	}


	public void doPlaceOrder(HttpServletRequest request,
			HttpServletResponse response)throws OutOfStockException,GiftCertificateStateException {
		String uuid = RequestUtil.getCookie(request, CheckoutConstants.SHOPPINGCART_COOKIE).getValue();
//		shoppingcartService.refreshShoppingcart(uuid, request, response);
		Shoppingcart cart = shoppingcartService.loadCookieCart(request);
		
		
		if(cart.getIsUsedCoupon()==Shoppingcart.ISUSECOUPON_YES)
		    promoService.useCoupon(cart.getUsedCouponNo());
		
		
		
		Map<String, String>paramData=RequestUtil.getRequestMap(request);
		paramData=CheckoutUtil.getInstance().paramDataCheckForPaging(paramData, request);
		if(!cart.getIsOnlyVirtualItem()){
//			 Integer cityId_int=StringUtils.isNotBlank(paramData.get("cityId"))?Integer.valueOf(paramData.get("cityId")):null;
//			 Integer stateId_int=StringUtils.isNotBlank(paramData.get("stateId"))?Integer.valueOf(paramData.get("stateId")):null;
//			checkoutService.caculateTaxes(cart, Integer.valueOf(paramData.get("countryId")), stateId_int, cityId_int);
		}
		   
		
		//Map<String , Object> m = CheckoutUtil.getInstance().parseReqeustString(request, response);
		
		String remoteAddr=request.getRemoteAddr();
		Map<String , Object> m = CheckoutUtil.getInstance().parseReqeustString(cart,paramData,remoteAddr);
		
		orderService.doPlaceOrder((OrderAddress)m.get(CheckoutUtil.BILL),(OrderAddress)m.get(CheckoutUtil.SHIPPING) ,
				(SalesOrder)m.get(CheckoutUtil.SALESORDER), (OrderShipment)m.get(CheckoutUtil.SHIPMENT), 
				(Set<OrderSku>)m.get(CheckoutUtil.SKUS), (Set<OrderPromotion>)m.get(CheckoutUtil.PROMOTIONS),
				(Set<OrderPayment>)m.get(CheckoutUtil.PAYMENT),(String)m.get(CheckoutUtil.NOTE));
		
		request.setAttribute("salesOrder", m.get(CheckoutUtil.SALESORDER));
		request.setAttribute("billaddress", m.get(CheckoutUtil.BILL));
		request.setAttribute("orderaddress", m.get(CheckoutUtil.SHIPPING));
		request.setAttribute("shipment", m.get(CheckoutUtil.SHIPMENT));
		request.setAttribute("orderskus", m.get(CheckoutUtil.SKUS));
		request.setAttribute("ps", m.get(CheckoutUtil.PROMOTIONS));
		request.setAttribute("ordernote", m.get(CheckoutUtil.NOTE));
		
/*		if(!request.getParameter("paymentId").equals("cod")){
			PaymentMethod pm = paymentMethodService.getPaymentById(Integer.valueOf(request.getParameter("paymentId")));
			request.setAttribute("payment", pm);
		}*/
		
		request.setAttribute("otherpayment", m.get(CheckoutUtil.PAYMENT));
		
		shoppingcartService.clearShoppingcart(cart.getUuid(), request, response);
	}
	
	
}
package com.cartmatic.estore.cart.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.model.CheckoutPagingModel;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.common.service.CheckoutService;
import com.cartmatic.estore.common.service.GiftCertificateService;
import com.cartmatic.estore.common.service.PaymentMethodService;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.common.service.ShoppingcartService;
import com.cartmatic.estore.common.util.NumberUtil;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.MembershipManager;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.exception.GiftCertificateStateException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.system.service.ShippingRateManager;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * 
 * @author huangwm210 2008-11-19
 *
 */
public class CheckoutUtil {

	private static CheckoutUtil checkoutUtil = new CheckoutUtil();
	private CheckoutService checkoutService;
	private PromoService promoService;  
	private ShoppingcartService shoppingcartService;	
	private PaymentMethodService paymentMethodService;
	private GiftCertificateService giftCertificateService;
	private AddressManager addressManager=null;
	public static final String BILL = "billad";
	public static final String SHIPPING = "shippingad";
	public static final String SALESORDER = "salesorder";
	public static final String SHIPMENT = "ordershipment";
	public static final String SKUS = "skus";
	public static final String PROMOTIONS = "promotions";
	public static final String PAYMENT = "payment";
	public static final String NOTE = "note";
	
	
	private CustomerManager customerManager;
	
	private ShopPointHistoryManager shopPointHistoryManager;
	
	private ShippingRateManager shippingRateManager;
	
	private MembershipManager membershipManager;
	
	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}

	public static synchronized CheckoutUtil getInstance() {
		return checkoutUtil;
	}
	
	/**
	 * 将request中的数据转换成订单模块对应的model(原来的不够灵活)
	 * @param request
	 * @param response
	 * @return
	 * @throws GiftCertificateStateException 
	 */
	public Map<String, Object> parseReqeustString(Shoppingcart cart,Map<String, String>paramData,String remoteAddr) throws GiftCertificateStateException{
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		OrderAddress billAd = getBillAd(paramData);
		OrderAddress shippingAd = cart.getIsOnlyVirtualItem()?null:getShippingAd(paramData);
		SalesOrder salesOrder = getSalesOrder(paramData, cart);
		OrderShipment orderShipment = getOrderShipment(paramData,cart);
		Set<OrderSku> skus = getSkus(paramData,cart);
		Set<OrderPromotion> orderPomotions = getPormotions(paramData, cart);
		Set<OrderPayment> payments = null;
		
		salesOrder.setIpAddress(remoteAddr);
		salesOrder.setPaymentStatus(OrderConstants.PAYMENT_STATUS_UNPAID);
		/*
		if(Boolean.valueOf(paramData.get("giftCertificate").toString())){
			payments = new HashSet<OrderPayment>();
			String[] nos = paramData.get("giftCertificateNos").toString().split("#");
			BigDecimal m = new BigDecimal(0);
			for(int i=0;i<nos.length;i++){
				String no = nos[i].split(":")[0];
				BigDecimal mm = BigDecimal.valueOf(Double.valueOf(nos[i].split(":")[1]));
				m = m.add(mm);
				giftCertificateService.doUseGiftCertificate(no, mm, cart.getBuyNowItemsCount());
			    OrderPayment op = new OrderPayment();
			    op.setTransactionType(OrderConstants.TRANSACTION_TYPE_GIFT_CERT);
			    op.setGiftCertificateNo(no);
			    op.setPaymentAmount(mm);
			    op.setCreateTime(new Date());
			    op.setVersion(0);
			    op.setAddedBy("");
			    op.setBalance(m);
			    payments.add(op);
			}
			salesOrder.setPaidAmount(m);
		}
		*/
		
		//订单 已支付
		BigDecimal amountPaid = new BigDecimal(0);
		
		//优惠劵 扣除
		if(cart.getCartDiscountAmount() != null){
			amountPaid = amountPaid.add(cart.getCartDiscountAmount());
			String couponNo = cart.getUsedCouponNo();
			if(couponNo != null && couponNo.length() == 8 && couponNo.startsWith("100")){
				Customer customer = (Customer) RequestContext.getCurrentUser();
				Membership member = membershipManager.getById(customer.getMembershipId());
				Membership temp = membershipManager.getMembershipByLevel(2);//白金会员
				if(member.getMembershipLevel() < temp.getMembershipLevel()){
					customer.setMembershipId(temp.getId());
					this.customerManager.save(customer);
				}
			}
		}
		
		//礼品卡 扣除
		if(cart.getGiftCertificateNos() != null){
			BigDecimal mm = BigDecimal.valueOf(Double.valueOf(cart.getGiftCertificateNos().split(":")[1]));
			amountPaid = amountPaid.add(mm);
			giftCertificateService.doUseGiftCertificate(cart.getGiftCertificateNos().split(":")[0], mm, cart.getBuyNowItemsCount());
			payments = new HashSet<OrderPayment>();
		    OrderPayment op = new OrderPayment();
		    op.setTransactionType(OrderConstants.TRANSACTION_TYPE_GIFT_CERT);
		    op.setGiftCertificateNo(cart.getGiftCertificateNos().split(":")[0]);
		    op.setPaymentAmount(mm);
		    op.setCreateTime(new Date());
		    op.setVersion(0);
		    op.setAddedBy("");
		    op.setBalance(mm);
		    payments.add(op);
		}
		//积分 扣除
		if(cart.getShopPoint() != null){
			BigDecimal shopPoint = new BigDecimal(cart.getShopPoint());
			BigDecimal mm =  ConfigUtil.getInstance().getShopPointUseGiftPercent().multiply(shopPoint);
			amountPaid = amountPaid.add(mm);
			Customer customer = customerManager.getById(cart.getCustomerId());
//			shopPointManager.saveChangeTotal(customer , -cart.getShopPoint());
			
			ShopPointHistory shopPointHistory = new ShopPointHistory();
			shopPointHistory.setShopPointType(ShopPointHistory.SHOPPOINT_TYPE_BUY);
			shopPointHistory.setAmount(-cart.getShopPoint());
			shopPointHistory.setCustomerId(customer.getAppuserId());
			shopPointHistory.setDescription("购买商品时使用积分："+ cart.getShopPoint());
			shopPointHistoryManager.saveShopPointHistoryAndUpdateTotal(shopPointHistory);
		}
		
		BigDecimal total = new BigDecimal(0);
		
		//订单 总额
		total = total.add(cart.getSubtotal());
//							.subtract(amountPaid);
		
		
/*		String isAbcDiscount = paramData.get("isAbcDiscount");
		BigDecimal abcDiscount = new BigDecimal(0);
		if(isAbcDiscount != null && isAbcDiscount.equals("true")){
			
			BigDecimal t1 = new BigDecimal("0.1");
			abcDiscount = total.subtract(amountPaid).multiply(t1);
			amountPaid = amountPaid.add(abcDiscount);
			
		}*/
		
//		.add(orderShipment.getItemTax())
//		.add(orderShipment.getShippingCost())
//		.add(orderShipment.getWrapUnitPrice());
		if(total.subtract(amountPaid).compareTo(new BigDecimal(0)) < 0){
			amountPaid = total;
		}
		if(total.subtract(amountPaid).compareTo(Constants.SHIPPING_COST_COMPARE) < 0){
//			total = total.add(new BigDecimal(12));
//			total = total.add(Constants.SHIPPING_COST);
			total = total.add(orderShipment.getShippingCost());
		}
		salesOrder.setPaidAmount(amountPaid);
		salesOrder.setTotalAmount(total);
		
		//setup storeId
		salesOrder.setStore(ConfigUtil.getInstance().getStore());
		
		if(salesOrder.getHasInvoice().shortValue()==Constants.FLAG_FALSE.shortValue()){
			billAd = shippingAd;
		}
		
		returnMap.put(CheckoutUtil.BILL, billAd);
		returnMap.put(CheckoutUtil.SHIPPING, shippingAd);
		returnMap.put(CheckoutUtil.PROMOTIONS, orderPomotions);
		returnMap.put(CheckoutUtil.SALESORDER, salesOrder);
		returnMap.put(CheckoutUtil.SKUS, skus);
		returnMap.put(CheckoutUtil.SHIPMENT, orderShipment);
		returnMap.put(CheckoutUtil.PAYMENT, payments);
		returnMap.put(CheckoutUtil.NOTE, paramData.get("orderNote").toString());
		return returnMap;
	}
	
	private OrderAddress getBillAd(Map<String, String> vm){
		OrderAddress ad = new OrderAddress();
		if(!Boolean.valueOf(vm.get("bill")))
			return null;
		else{
			ad.setAddress1(vm.get("bill_address"));
			ad.setAddress2(vm.get("bill_address2"));
			ad.setCity(vm.get("bill_cityName"));
			ad.setSection(vm.get("bill_sectionName"));
			ad.setCountry(vm.get("bill_countryName"));
			ad.setState(vm.get("bill_stateName"));
			ad.setFirstname(vm.get("bill_firstName"));
			ad.setLastname(vm.get("bill_lastName"));
			ad.setTitle(vm.get("bill_title"));
			ad.setPhoneNumber(vm.get("bill_telphone"));
			ad.setPostalcode(vm.get("bill_zip"));
			return ad;
		}
	}
	private OrderAddress getShippingAd(Map<String,String> vm){
		OrderAddress ad = new OrderAddress();
		ad.setAddress1(vm.get("address"));
		ad.setAddress2(vm.get("address2"));
		ad.setCity(vm.get("cityName"));
		ad.setSection(vm.get("sectionName"));
		ad.setCountry(vm.get("countryName"));
		ad.setState(vm.get("stateName"));
		ad.setFirstname(vm.get("firstName"));
		ad.setLastname(vm.get("lastName"));
		ad.setTitle(vm.get("title"));
		ad.setPhoneNumber(vm.get("telphone"));
		ad.setPostalcode(vm.get("zip"));
		return ad;
	}
	
	private SalesOrder getSalesOrder(Map<String, String> vm, Shoppingcart cart)
	{
		SalesOrder so = new SalesOrder();
		if(Boolean.valueOf(vm.get("bill"))){
			so.setHasInvoice(Constants.FLAG_TRUE);
			so.setInvoiceTitle(vm.get("invoiceTitle"));
		}
		else{
			so.setHasInvoice(Constants.FLAG_FALSE);
		}
		Date now = new Date(System.currentTimeMillis());
		so.setCreateTime(now);

		so.setCustomerEmail(vm.get("email"));
		if(vm.get("firstName").trim().length()<=0){
			if(!RequestContext.isAnonymousUser()){
				so.setCustomerTitle("");
				so.setCustomerFirstname(RequestContext.getCurrentUserName());
				so.setCustomerLastname("");
				so.setCustomerId(RequestContext.getCurrentUserId());
			}
			else{//匿名
				so.setCustomerTitle("");
				so.setCustomerFirstname(I18nUtil.getInstance().getMessage("salesOrder.anonymous"));
				so.setCustomerLastname("");
				so.setCustomerId(RequestContext.getCurrentUserId());
			}
		}
		else{
			so.setCustomerId(RequestContext.getCurrentUserId());
			so.setCustomerTitle(vm.get("title"));
			so.setCustomerFirstname(vm.get("firstName"));
			so.setCustomerLastname(vm.get("lastName"));	
		}

		
/*		String paymentId = vm.get("paymentId");
		
		//货到付款
		if(paymentId != null && paymentId.equals("cod")){
			so.setIsCod(new Short("1"));
		}
		//款到发货
		else{
			PaymentMethod pMethod = paymentMethodService.getPaymentById(Integer.valueOf(vm.get("paymentId")));
			so.setIsCod(pMethod.getIsCod());
			so.setPaymentMethodId(Integer.valueOf(vm.get("paymentId")));
		}*/
		
		String payType = vm.get("payType");	//支付方式 ： 0 online 在线支付；1 cod 货到付款；2 transfer 对公转账
		if(payType.equals("online")){
			so.setIsCod(new Short("0"));
		}else if(payType.equals("cod")){
			so.setIsCod(new Short("1"));
		}else if(payType.equals("transfer")){
			so.setIsCod(new Short("2"));
		}
		
		
		so.setGainedPoint(cart.getGainedPoint());
		so.setPaidAmount(new BigDecimal(0));
		so.setOrderStatus(OrderConstants.ORDER_STATUS_IN_PROGRESS);
		so.setIsOnHold(new Short("0"));
		so.setIsLocked(new Short("0"));
		so.setIsExchangeOrder(new Short("0"));
		so.setGainedCouponTypeId(cart.getGainedCouponTypeId());
		// not support now. 2013-03-20
		//if(Boolean.valueOf(vm.get("point"))){
		//	so.setShopPoint(Integer.valueOf(vm.get("xxxxp")));
		//}
		return so;
	}
	private OrderShipment getOrderShipment(Map<String, String> vm, Shoppingcart cart){
		OrderShipment os = new OrderShipment();
		os.setCreateTime(new Date());
		BigDecimal result = new BigDecimal(0);
		if(!cart.getIsOnlyVirtualItem()){
			String[] ids = vm.get("shippingMethodId").toString().split(",");//rateId,shippingMethodId
			ShippingRate sr = checkoutService.getShippingRateById(Integer.valueOf(ids[0]));
			Set<ShoppingcartItem> items = cart.getCartItems();
			BigDecimal w = new BigDecimal(0);
			Integer itemCount = 0;
			for(ShoppingcartItem item:items){
				if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
				itemCount += item.getQuantity();
			    BigDecimal w1 = NumberUtil.getBigDecimal(item.getProductSku().getWeight()).multiply(BigDecimal.valueOf(item.getQuantity()));
				w = w.add(w1);
			}
			BigDecimal temp = checkoutService.getShippingExpence(Integer.valueOf(ids[0]), w, itemCount);
			result = promoService.getShippingFee(cart.getShippingDiscountInfo(), Integer.valueOf(ids[1]),temp);
			
			os.setCarrierName(sr.getShippingMethod().getShippingMethodName());
			os.setShippingRateId(Integer.valueOf(ids[0]));
		}
		
		os.setShippingCost(result);
		os.setItemSubTotal(cart.getTotal());
		os.setVersion(1);
		os.setHasRobotReviewed(new Short("0"));
   		os.setItemTax(cart.getCartItemsTotalTax().setScale(2, BigDecimal.ROUND_HALF_UP));
   		os.setShippingTax(new BigDecimal(0));
   		os.setDiscountAmount(cart.getCartDiscountAmount());
        os.setHasRobotReviewed(new Short("0"));
        if(Boolean.valueOf(vm.get("wrap"))){
        	Wrap wrap = checkoutService.getWrapById(Integer.valueOf(vm.get("wrapId")));
        	os.setWrapName(wrap.getWrapName());
        	os.setWrapNote(vm.get("wrapNote"));
        	os.setWrapUnitPrice(wrap.getDefaultPrice());
        }
        else{
        	os.setWrapUnitPrice(new BigDecimal(0));
        }
		return os;
	}
	private Set<OrderSku> getSkus(Map<String, String> vm,Shoppingcart cart){
		Set<OrderSku> skus = new HashSet<OrderSku>();
		Set<ShoppingcartItem> items = cart.getCartItems();
		for(ShoppingcartItem item:items){
			OrderSku sku = new OrderSku();
			if(item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT)){
				sku.setProductSku(item.getProductSku());
				sku.setProductSkuCode(item.getProductSku().getProductSkuCode());
				sku.setProductName(item.getProductSku().getProduct().getProductName());
				sku.setProductId(item.getProductSku().getProductId());
				sku.setCostPrice(item.getProductSku().getCostPrice());//成本
				String displayOptions = item.getProductSku().getOrderSkuDisplayOption();
				sku.setDisplaySkuOptions(displayOptions);
				sku.setAccessories(item.getAccessories()); //附件功能
				sku.setWeight(item.getProductSku().getWeight());
			}
			else if(item.getItemType().equals(Constants.ITEM_TYPE_GC)){
				GiftCertificate gc = giftCertificateService.createGiftCertificate(item.getShoppingcartItemGc());
				sku.setGiftCertificate(gc);
				StringBuffer sb = new StringBuffer("");
				sb.append(item.getShoppingcartItemGc().getRecipient()+"###")
				.append(item.getShoppingcartItemGc().getRecipientEmail());
				sku.setDisplaySkuOptions(sb.toString());
			}
			sku.setCreateTime(new Date(System.currentTimeMillis()));
			sku.setPrice(item.getPrice());
			sku.setTax(item.getTax());
			sku.setTaxName(item.getTaxName());
			sku.setIsOnSale(item.getIsOnSale());
			sku.setIsWholesale(item.getIsWholesale());
			sku.setItemType(item.getItemType());   
			sku.setDiscountPrice(item.getDiscountPrice());
			sku.setQuantity(item.getQuantity());
			sku.setTotal(item.getTotal());
			sku.setItemDiscountAmount(item.getItemDiscountAmount());
			sku.setAllocatedQuantity(0);
			sku.setVersion(1);
			skus.add(sku);
		}
		return skus;
	}
	private Set<OrderPromotion> getPormotions(Map<String, String> vm, Shoppingcart cart){
		Set<OrderPromotion> returnSet = new HashSet<OrderPromotion>();
		Set<ShoppingcartPromotion> ps = cart.getShoppingCartPromotions();
		if(ps.size()!=0){
			for(ShoppingcartPromotion p:ps){
				OrderPromotion op = new OrderPromotion();
				op.setPromoRuleId(p.getPromoRuleId());
				op.setPromotionName(p.getPromotionName());
				op.setCreateTime(new Date());
				if(p.getIsUsedCoupon().equals(Shoppingcart.ISUSECOUPON_YES)){
					op.setCouponNo(p.getUsedCouponNo().toString());
					returnSet.add(op);
				}
				returnSet.add(op);
			}
		}
		if(returnSet.size()==0)
			return null;
		else
			return returnSet;
	}
	
	/**
	 * 检查购物车中的每一个Item的库存
	 * 返回的字符串的格式是：
	 * productName1:msg1###productName2:msg2
	 * @param request
	 * @param response
	 * @return
	 */
	public String checkInventoryForShoppingcart(HttpServletRequest request,HttpServletResponse response){
		Shoppingcart cart = shoppingcartService.loadCookieCart(request);
		String temp = "";		
		if(cart==null)
			return temp;
		else{
			Set<ShoppingcartItem> items = cart.getCartItems();
			for(ShoppingcartItem item:items){
				if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
				boolean stock = shoppingcartService.checkInventory(item, item.getProductSku().getProductSkuCode());
				if(!stock){
					temp += item.getProductSku().getProduct().getProductName()+":Out of Stock!###";
				}
			}
			if(temp.length()>0){
				temp = temp.substring(0,temp.length()-3);
			}
			else{
				temp = "ok";
			}
		}
		return temp;
	}
	
	public Map<String,String> paramDataCheckForPaging(Map<String,String> paramData2,HttpServletRequest request){
		Map<String,String> paramData=new HashMap<String, String>();
		paramData.putAll(paramData2);
		Customer customer=(Customer)RequestContext.getCurrentUser();
		//获取运输地址信息
		Integer shippingAddressId=	Integer.parseInt(paramData2.get("shippingAddressId"));	//getCheckoutPagingModel(request).getShippingAddressId();
		Address  shippingAddress=addressManager.getById(shippingAddressId);
		paramData.put("address", shippingAddress.getAddress());
		paramData.put("address2", shippingAddress.getAddress2());
		paramData.put("countryName", shippingAddress.getCountryName());
		paramData.put("stateName", shippingAddress.getStateName());
		paramData.put("cityName", shippingAddress.getCityName());
		paramData.put("sectionName", shippingAddress.getSectionName());
		paramData.put("firstName", shippingAddress.getFirstname());
		paramData.put("lastName", shippingAddress.getLastname());
		paramData.put("title", shippingAddress.getTitle());
		paramData.put("telphone", shippingAddress.getTelephone());
		paramData.put("zip", shippingAddress.getZip());
		paramData.put("countryId", shippingAddress.getCountryId()==null?null:shippingAddress.getCountryId().toString());
		paramData.put("stateId", shippingAddress.getStateId()==null?null:shippingAddress.getStateId().toString());
//		paramData.put("cityId", shippingAddress.getCityId().toString());
		//获取发票地址信息
		Address  billingAddress=addressManager.getDefBillingAddress(customer.getAppuserId());
		if(billingAddress == null){
			shippingAddress.setIsDefaultBillingAddress(Constants.FLAG_TRUE);
			addressManager.save(shippingAddress);
			billingAddress = shippingAddress;
		}
		paramData.put("bill", "true");
		paramData.put("invoiceTitle", "");
		paramData.put("bill_address", billingAddress.getAddress());
		paramData.put("bill_address2", billingAddress.getAddress2());
		paramData.put("bill_countryName", billingAddress.getCountryName());
		paramData.put("bill_stateName", billingAddress.getStateName());
		paramData.put("bill_cityName", billingAddress.getCityName());
		paramData.put("bill_sectionName", billingAddress.getSectionName());
		paramData.put("bill_firstName", billingAddress.getFirstname());
		paramData.put("bill_lastName", billingAddress.getLastname());
		paramData.put("bill_title", billingAddress.getTitle());
		paramData.put("bill_telphone", billingAddress.getTelephone());
		paramData.put("bill_zip", billingAddress.getZip());
		//当前用户email
		paramData.put("email", customer.getEmail());
		//是否使用包装
		String wrap_wrapId=request.getParameter("wrap_wrapId");
		String wrapNote=request.getParameter("wrapNote");
		wrap_wrapId=StringUtils.isBlank(wrap_wrapId)||wrap_wrapId.equals("0")?"":wrap_wrapId;
		paramData.put("wrapId",wrap_wrapId);
		paramData.put("wrap", wrap_wrapId.equals("")?"false":"true");
		paramData.put("wrapNote",wrapNote);
		//是否使用了积分
		paramData.put("point","false");
		//是否使用了礼券
		String giftCertificate=paramData2.get("giftCertificate");
		paramData.put("giftCertificate", (giftCertificate==null||!giftCertificate.equals("true"))?"false":"true");
		return paramData;
	}
	

	public CheckoutService getCheckoutService() {
		return checkoutService;
	}

	public void setCheckoutService(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	public void setPromoService(PromoService promoService) {
		this.promoService = promoService;
	}

	public void setShoppingcartService(ShoppingcartService shoppingcartService) {
		this.shoppingcartService = shoppingcartService;
	}

	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}

	public void setGiftCertificateService(
			GiftCertificateService giftCertificateService) {
		this.giftCertificateService = giftCertificateService;
	}
	
	public CheckoutPagingModel getCheckoutPagingModel(HttpServletRequest request){
		CheckoutPagingModel checkoutPagingModel=(CheckoutPagingModel)request.getSession().getAttribute(CheckoutPagingModel.SESSION_KEY);
		if(checkoutPagingModel==null){
			checkoutPagingModel=new CheckoutPagingModel();
			request.getSession().setAttribute(CheckoutPagingModel.SESSION_KEY,checkoutPagingModel);
		}
		return checkoutPagingModel;
	}
	 
	/**
	 * 列出购物车应用运输方式的运费
	 * @param shippingAddress
	 * @param cart
	 * @return
	 */
	public List<ShippingRate> statCartShipping(Address shippingAddress,Shoppingcart cart) 
	{
		List<ShippingRate> result = new ArrayList<ShippingRate>();
		Store store = ConfigUtil.getInstance().getStore();
		Set<ShippingMethod> methos = store.getShippingMethods();
		if (methos.isEmpty())
		{
			return result;
		}
		List<ShippingRate> shippingRateList = shippingRateManager.findAllShippingRate();	//checkoutService.getRegionShippingRates(shippingAddress.getCountryId(),shippingAddress.getStateId(), shippingAddress.getCityId());
		//计算运费
		Set<ShoppingcartItem> items = cart.getCartItems();
		BigDecimal w = new BigDecimal(0);
		Integer itemCount = 0;
		for(ShoppingcartItem item:items){
			if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
			itemCount += item.getQuantity();
			BigDecimal w0 = item.getProductSku().getWeight();
			BigDecimal w1 = w0==null?(new BigDecimal(0))
					:w0.multiply(BigDecimal.valueOf(item.getQuantity()));
			w = w.add(w1);
		}
		
		for(ShippingRate shippingRate : shippingRateList)
		{
			//判断是否在当前store中。
			boolean isBelongToStore = false;
			for (ShippingMethod method : methos)
			{
				if (method.getShippingMethodId().intValue() == shippingRate.getShippingMethodId().intValue())
				{
					isBelongToStore = true;
					break;
				}
			}
			if (!isBelongToStore)
				continue;
		    if (shippingRate.getMaxWeight()!= null) //过滤超重的运输shippingRate.  
            {
                if (w.compareTo(shippingRate.getMaxWeight()) == 1)
                {
                    continue;
                }
            }
			BigDecimal temp = checkoutService.getShippingExpence(shippingRate.getShippingRateId(), w, itemCount);
			BigDecimal cartShipping = promoService.getShippingFee(cart.getShippingDiscountInfo(), shippingRate.getShippingMethod().getShippingMethodId(),temp).setScale(2,BigDecimal.ROUND_HALF_UP); 
//			checkoutService.caculateTaxes(cart, Integer.valueOf(shippingAddress.getCountryId()), Integer.valueOf(shippingAddress.getStateId()), shippingAddress.getCityId());
			/*BigDecimal r = new BigDecimal(0);
			Set<ShoppingcartItem> items1 = cart.getCartItems();
			for(ShoppingcartItem i:items1){
				r = r.add(i.getTax());  
			}
			r = r.setScale(2,BigDecimal.ROUND_HALF_UP);*/
			shippingRate.setCartShipping(cartShipping);
			result.add(shippingRate);
		}
		//价格从低到高排序
		Collections.sort(result,new Comparator<ShippingRate>(){
			public int compare(ShippingRate shippingRate1, ShippingRate shippingRate2) {
				return shippingRate1.getCartShipping().compareTo(shippingRate2.getCartShipping());
			} 
			
		});
		return result;
	}

	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public ShopPointHistoryManager getShopPointHistoryManager() {
		return shopPointHistoryManager;
	}

	public void setShopPointHistoryManager(ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
	}

	public ShippingRateManager getShippingRateManager() {
		return shippingRateManager;
	}

	public void setShippingRateManager(ShippingRateManager shippingRateManager) {
		this.shippingRateManager = shippingRateManager;
	}

	public MembershipManager getMembershipManager() {
		return membershipManager;
	}

	public void setMembershipManager(MembershipManager membershipManager) {
		this.membershipManager = membershipManager;
	}

}

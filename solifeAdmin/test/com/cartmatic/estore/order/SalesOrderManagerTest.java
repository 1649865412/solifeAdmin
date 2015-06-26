package com.cartmatic.estore.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.order.service.OrderNoGenerator;
import com.cartmatic.estore.order.service.SalesOrderManager;

/**
 * 订单集成测试类
 * @author pengzhirong
 */
public class SalesOrderManagerTest extends BaseTransactionalTestCase {
	
	@Autowired	
	private SalesOrderManager salesOrderManager = null;
	@Autowired
	private OrderNoGenerator orderNoGenerator = null;
	@Autowired
	private ProductService productService = null;
	@Autowired
	private OrderService orderService = null;
	
	
	@Test		
	public void _testGetSalesOrderByUserId(){
		List<SalesOrder> list = orderService.getSalesOrderByUserId(29);
		if(list!=null)
			System.out.println(">>>>>>>>>>>>>>>>>"+list.size());
	}
	
	@Test
	public void _testCount(){
		long num = salesOrderManager.countCustomerOrder("2238");
		System.out.println("==============="+num);
	}
	
	@Test
	public void _testPlaceOrder4Shoppingcart() throws NumberFormatException, OutOfStockException{
		Set<OrderSku> orderSkus = new HashSet<OrderSku>();
		orderSkus.add(getOrderSku(null));
		orderService.doPlaceOrder( getOrderAddress(), getOrderAddress(), this.populateOrder(new Short("1")), getOrderShipment(null, null), orderSkus, null,null,null);
	}
	
	@Test
	public void _testAllocate(){
		orderService.doAllocation4PreOrBackOrder(2);
	}
	
	@Test
	public void _testPlaceOrder(){
		OrderAddress orderAddress  = getOrderAddress();
		salesOrderManager.save(orderAddress);
		
		OrderAddress shippingAddress  = getOrderAddress();
		salesOrderManager.save(shippingAddress);
		
		OrderSku orderSku  = this.getOrderSku(null);
		float totalAmount = orderSku.getDiscountPrice().floatValue()*orderSku.getQuantity();
		
		SalesOrder so = this.populateOrder(new Short("1"));
		so.setBillingAddressId(orderAddress.getOrderAddressId());
		so.setTotalAmount(BigDecimal.valueOf(totalAmount));
		salesOrderManager.save(so);
		
		OrderShipment orderShipment = this.getOrderShipment(so.getSalesOrderId(), so.getOrderNo());
		orderShipment.setItemSubTotalWithFloat(totalAmount);
		orderShipment.setOrderAddress(shippingAddress);
		salesOrderManager.save(orderShipment);
		
		orderSku.setOrderShipment(orderShipment);
		
		salesOrderManager.save(orderSku);
		
		
	}
	
	@Test
	public void _testPlacePayFirstOrder(){
		OrderAddress orderAddress  = getOrderAddress();
		salesOrderManager.save(orderAddress);
		
		OrderAddress shippingAddress  = getOrderAddress();
		salesOrderManager.save(shippingAddress);
		
		OrderSku orderSku  = this.getOrderSku(null);
		float totalAmount = orderSku.getDiscountPrice().floatValue()*orderSku.getQuantity();
		
		SalesOrder so = this.populateOrder(new Short("0"));
		so.setBillingAddressId(orderAddress.getOrderAddressId());
		so.setTotalAmount(BigDecimal.valueOf(totalAmount));
		salesOrderManager.save(so);
		
		OrderShipment orderShipment = this.getOrderShipment(so.getSalesOrderId(), so.getOrderNo());
		orderShipment.setItemSubTotalWithFloat(totalAmount);
		orderShipment.setOrderAddress(shippingAddress);
		salesOrderManager.save(orderShipment);
		
		orderSku.setOrderShipment(orderShipment);
		salesOrderManager.save(orderSku);
	}
	
	@Test
	public void _testPlaceGiftCertificationOrder(){
		OrderAddress orderAddress  = getOrderAddress();
		salesOrderManager.save(orderAddress);
		
		OrderAddress shippingAddress  = getOrderAddress();
		salesOrderManager.save(shippingAddress);
		
		SalesOrder so = this.populateOrder(new Short("0"));
		so.setBillingAddressId(orderAddress.getOrderAddressId());
		
		salesOrderManager.save(so);
		
		OrderShipment orderShipment = this.getOrderShipment(so.getSalesOrderId(), so.getOrderNo());
		orderShipment.setOrderAddress(shippingAddress);
		salesOrderManager.save(orderShipment);
		
		OrderSku orderSku  = this.getOrderSku(43);
		orderSku.setOrderShipment(orderShipment);
		
		salesOrderManager.save(orderSku);
		
		
	}
	
	private SalesOrder populateOrder(Short isCod){
		SalesOrder so = new SalesOrder();
//		if (!orderNoGenerator.isInit()) {
//			String maxOrderNo = salesOrderDao.getMaxOrderNo();
//			orderNoGenerator.init(maxOrderNo);
//		}
		so.setOrderNo(orderNoGenerator.generateOrderNo());
		so.setInvoiceTitle("彭志荣");
		so.setBillingAddressId(null);
		so.setCreateBy(2238);
		so.setCreateTime(new Date());
		so.setCustomerId(2238);
		so.setCustomerEmail("imroyce@hotmail.com");
		so.setCustomerFirstname("彭志荣");
		so.setCustomerLastname(" ");
		so.setCustomerTitle("先生");
		so.setHasInvoice(new Short("1"));
		so.setIpAddress("192.168.0.11");
		so.setIsCod(isCod);
		so.setIsExchangeOrder(new Short("0"));
		so.setOrderStatus(new Short("10"));
		so.setPaymentStatus(OrderConstants.PAYMENT_STATUS_UNPAID);
		so.setPaidAmount(new BigDecimal(0));
		so.setPaymentStatus(new Short("10"));
		so.setTotalAmount(new BigDecimal(300));
		so.setVersion(1);
		
		so.setIsOnHold(new Short("0"));
		so.setIsLocked(new Short("0"));
		
		return so;
	}
	
	private OrderShipment getOrderShipment(Integer orderId, String orderNo){
		OrderShipment orderShipment = new OrderShipment(); 
		orderShipment.setCarrierName("UPS快递");
		orderShipment.setCreateBy(2238);
		orderShipment.setCreateTime(new Date());
		orderShipment.setItemSubTotal(new BigDecimal(300));
		orderShipment.setOrderAddress(new OrderAddress(1));
		orderShipment.setStatus(new Short("20"));
		orderShipment.setShippingCost(new BigDecimal(0));
		orderShipment.setVersion(1);
		
		orderShipment.setShipmentNo(orderNo+"-01");
		orderShipment.setItemTax(new BigDecimal(0));
		orderShipment.setShippingTax(new BigDecimal(0));
		orderShipment.setDiscountAmount(new BigDecimal(0));
		orderShipment.setHasRobotReviewed(new Short("0"));
		orderShipment.setUpdateTime(orderShipment.getCreateTime());
		orderShipment.setSalesOrderId(orderId);
		return orderShipment;
	}
	
	private OrderSku getOrderSku(Integer giftCertificateId){
		ProductSku sku = productService.getProductSku(9);
		
		OrderSku orderSku = new OrderSku();
		orderSku.setQuantity(2);
		orderSku.setAllocatedQuantity(2);
		orderSku.setPrice(sku.getPrice());
		orderSku.setDiscountPrice(sku.getPrice());
		if(giftCertificateId!=null){
			orderSku.setItemType(CatalogConstants.SKU_KIND_SERVICE);
			orderSku.setGiftCertificateId(giftCertificateId);
		}
		else
			orderSku.setItemType(CatalogConstants.SKU_KIND_ENTITY);
		orderSku.setProductName(sku.getProductSkuName());
		orderSku.setProductSkuId(sku.getProductSkuId());
		orderSku.setProductId(sku.getProductId());
		orderSku.setProductSkuCode(sku.getProductSkuCode());
		orderSku.setVersion(1);
		orderSku.setIsOnSale(new Short("0"));
		orderSku.setIsWholesale(new Short("0"));
		orderSku.setItemDiscountAmount(new BigDecimal(0));
		return orderSku;
	}
	
	private OrderAddress getOrderAddress(){
		OrderAddress oa = new OrderAddress();
		oa.setAddress1("广州市天河区"+Math.random());
		oa.setAddress2("");
		oa.setCity("广州");
		oa.setCountry("中国");
		oa.setFaxNumber(" ");
		oa.setFirstname("Royce"+Math.random());
		oa.setLastname("");
		oa.setTitle("先生");
		oa.setPhoneNumber("2342343");
		oa.setPostalcode("323222");
		oa.setState("广东");
		return oa;
	}
	
}

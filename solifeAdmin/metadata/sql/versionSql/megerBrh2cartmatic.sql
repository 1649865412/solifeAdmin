-- run in the brh and make sure the pmm has brh storeId (3) 
--[insert into pmm.store (storeId,catalogId,name,code,siteUrl,status) values (3,1,'BRH','brh','http://brh',1);]
--appuserId + 8000, 
--orderAddressId + 12000, 
--salesorderid + 8000 ,
--orderReturnId + 1000, 
--orderPickListId + 2000
--orderShipmentId + 8000 
--orderSkuId + 10000
ALTER TABLE `order_sku` 
	CHANGE `accessories` `accessories` varchar(512)  COLLATE utf8_general_ci NULL after `allocatedQuantity`, 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `accessories`, 
	ADD COLUMN `allocatedPreQty` int(11)   NULL after `version`, COMMENT='';
	
-- mark the repeat userId 
create table app_user_temp (id int);
insert into app_user_temp select b.appuserId from pmm.app_user p ,estorebrh.app_user b where p.username=b.username or p.email=b.email;

-- inser app_user customer ; notice the storeID
insert into pmm.app_user (appuserId,memberShipId,storeId,username,password,
userType,title,firstname,lastname,email,fax,telephone,totalPoints,
status,isLocked,customerPosition,registerTime,deleted,createBy,updateBy,createTime,updateTime,version)
select appuserId+8000 as 'appuerId', memberShipId, 3 as 'storeId',username,password,
userType,title,firstname,lastname,email,fax,telephone,totalPoints,
status,isLocked,customerPosition,registerTime,deleted,createBy,updateBy,createTime,updateTime,version
from estorebrh.app_user
where userType=0 and appuserId not in(select id from app_user_temp);

-- shop_point
insert into pmm.SHOP_POINT (customerId,total,gainedTotal,usedTotal,updateTime)
select customerId+8000 as 'customerId',total,gainedTotal,usedTotal,updateTime
from estorebrh.SHOP_POINT
where customerId not in(select id from app_user_temp);

insert into pmm.SHOP_POINT_HISTORY (customerId,shopPointType,description,amount,createTime)
select customerId+8000 as 'customerId',shopPointType,description,amount,createTime
from estorebrh.SHOP_POINT_HISTORY
where customerId not in(select id from app_user_temp);

-- ADDRESS 
insert into pmm.ADDRESS (appuserId,addressType,title,firstname,lastname,telephone,zip,fax,companyName,
isDefaultShippingAddress,isDefaultBillingAddress,address,address2,countryName,stateName,cityName,countryId,stateId,cityId,userDefinedId,email)
select appuserId+8000 as 'appuserId',addressType,title,firstname,lastname,telephone,zip,fax,companyName,
isDefaultShippingAddress,isDefaultBillingAddress,address,address2,countryName,stateName,cityName,countryId,stateId,cityId,userDefinedId,email
from estorebrh.ADDRESS
where appuserId not in(select id from app_user_temp);

-- ORDER_ADDRESS
insert into pmm.ORDER_ADDRESS (orderAddressId,title,firstname,lastname,phoneNumber,faxNumber,postalcode,country,state,city,address1,address2)
select orderAddressId+12000 as 'orderAddressId',title,firstname,lastname,phoneNumber,faxNumber,postalcode,country,state,city,address1,address2
from estorebrh.ORDER_ADDRESS;

-- SALES_ORDER 
create table sales_order_temp (id int);
create table order_shipment_temp (id int);
insert into sales_order_temp select b.salesorderId from pmm.SALES_ORDER p ,estorebrh.SALES_ORDER b where p.orderNo=b.orderNo;
insert into sales_order_temp select b.salesorderId from estorebrh.SALES_ORDER b where customerId in(select id from app_user_temp);
insert into order_shipment_temp select orderShipmentId from estorebrh.order_shipment where salesorderId in(select id from sales_order_temp);

insert into pmm.SALES_ORDER (salesOrderId,billingAddressId,storeId,customerId,orderNo,membershipId,customerTitle,customerFirstname,
customerLastname,customerEmail,shopPoint,totalAmount,paidAmount,orderStatus,paymentStatus,isExchangeOrder,originalOrderId,
isCod,paymentMethodId,invoiceTitle,hasInvoice,gainedPoint,gainedCouponTypeId,ipAddress,isOnHold,isHoldByCustomer,isLocked,
lockedBy,createTime,updateTime,updateBy,version)
select salesOrderId+8000 as 'salesOrderId', billingAddressId+12000 as 'billingAddressId',3 as 'storeId',customerId+8000 as 'customerId',orderNo,membershipId,customerTitle,customerFirstname,
customerLastname,customerEmail,shopPoint,totalAmount,paidAmount,orderStatus,paymentStatus,isExchangeOrder,originalOrderId,
isCod,paymentMethodId,invoiceTitle,hasInvoice,gainedPoint,gainedCouponTypeId,ipAddress,isOnHold,isHoldByCustomer,isLocked,
lockedBy,createTime,updateTime,updateBy,version
from estorebrh.SALES_ORDER
where salesorderid not in (select id from sales_order_temp);

-- ORDER_PAYMENT
insert into pmm.ORDER_PAYMENT (salesOrderId,paymentAmount,balance,transactionType,paymentGatewayName,ipAddress,giftCertificateNo,addedBy,createTime,version)
select salesOrderId+8000 as 'salesOrderId',paymentAmount,balance,transactionType,paymentGatewayName,ipAddress,giftCertificateNo,addedBy,createTime,version
from estorebrh.ORDER_PAYMENT
where salesorderid not in (select id from sales_order_temp);

-- ORDER_RETURN 
insert into pmm.ORDER_RETURN (orderReturnId,rmaNo,salesOrderId,exchangeOrderId,receivedBy,returnType,status,itemSubTotal,shippingCost,discountAmount,
itemTax,shippingTax,isPhysicalReturn,lessRestockAmount,note,createBy,createTime,version)
select orderReturnId+1000 as 'orderReturnId',rmaNo,salesOrderId+8000 as 'salesOrderId',exchangeOrderId+8000 as 'exchangeOrderId',
1 as 'receivedBy',returnType,status,itemSubTotal,shippingCost,discountAmount,
itemTax,shippingTax,isPhysicalReturn,lessRestockAmount,note,1 as 'createBy',createTime,version
from estorebrh.ORDER_RETURN
where salesorderid not in (select id from sales_order_temp) and exchangeOrderId not in (select id from sales_order_temp);

-- ORDER_PICK_LIST
insert into pmm.ORDER_PICK_LIST (orderPickListId,createTime,updateTime,createdBy,isActive)
select orderPickListId+2000 as 'orderPickListId',createTime,updateTime,1 as 'createdBy',isActive
from estorebrh.ORDER_PICK_LIST;

-- ORDER_AUDIT 
insert into pmm.ORDER_AUDIT (salesOrderId,addedBy,createTime,transactionType,detail)
select salesOrderId+8000 as 'salesOrderId',addedBy,createTime,transactionType,detail
from estorebrh.ORDER_AUDIT
where salesorderid not in (select id from sales_order_temp);

-- ORDER_SHIPMENT 
insert into pmm.ORDER_SHIPMENT (orderShipmentId,salesOrderId,shippingAddressId,orderPickListId,shipmentNo,trackingNo,carrierName,
itemSubTotal,itemTax,shippingTax,shippingCost,shippingCostPaid,discountAmount,wrapName,wrapUnitPrice,wrapNote,hasRobotReviewed,
isConfirmedByRobot,status,deliverTime,createTime,updateTime,version)
select orderShipmentId+8000 as 'orderShipmentId',salesOrderId + 8000 as 'salesOrderId',shippingAddressId+12000 as 'shippingAddressId',
orderPickListId+2000 as 'orderPickListId',shipmentNo,trackingNo,carrierName,
itemSubTotal,itemTax,shippingTax,shippingCost,shippingCostPaid,discountAmount,wrapName,wrapUnitPrice,wrapNote,hasRobotReviewed,
isConfirmedByRobot,status,deliverTime,createTime,updateTime,version
from estorebrh.ORDER_SHIPMENT
where salesorderid not in (select id from sales_order_temp);

-- ORDER_SKU 
insert into pmm.ORDER_SKU (orderSkuId,orderShipmentId,productSkuId,productId,productName,productSkuCode,displaySkuOptions,itemType,quantity,costPrice,
price,discountPrice,isOnSale,isWholesale,tax,taxName,itemDiscountAmount,weight,allocatedQuantity,allocatedPreQty,accessories,version)
select orderSkuId+10000 as 'orderSkuId', orderShipmentId+8000 as 'orderShipmentId',435 as 'productSkuId',1303 as 'productId',productName,productSkuCode,displaySkuOptions,itemType,quantity,costPrice,
price,discountPrice,isOnSale,isWholesale,tax,taxName,itemDiscountAmount,weight,allocatedQuantity,allocatedPreQty,accessories,version
from estorebrh.ORDER_SKU
where orderShipmentId not in (select id from order_shipment_temp);

-- ORDER_RETURN_SKU 
insert into pmm.ORDER_RETURN_SKU (orderReturnId,orderSkuId,quantity,returnAmount,receivedQuantity,reasonType,receivedStatus)
select orderReturnId+1000 as 'orderReturnId',orderSkuId+10000 as 'orderSkuId',quantity,returnAmount,receivedQuantity,reasonType,receivedStatus
from estorebrh.ORDER_RETURN_SKU
where orderReturnId in (select orderReturnId from ORDER_RETURN where exchangeOrderId not in (select id from sales_order_temp));

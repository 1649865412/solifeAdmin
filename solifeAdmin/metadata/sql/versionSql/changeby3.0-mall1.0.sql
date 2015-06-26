/*==============================================================*/
/* Table: tax                                                   */
/*==============================================================*/
ALTER TABLE tax
	DROP COLUMN applyAddressType,
	DROP COLUMN priority,
	DROP COLUMN priceIncludesTax,
	DROP COLUMN displayTaxedPrice;
/*==============================================================*/
/* Table: tax_rate                                              */
/*==============================================================*/
 ALTER TABLE tax_rate  
 		DROP COLUMN applyType,
        DROP FOREIGN KEY FK_TAX_RATE_CA,
        DROP INDEX FK_TAX_RATE_CA;
 ALTER TABLE tax_rate
	CHANGE COLUMN categoryId productTypeId int not null;
ALTER TABLE tax_rate 
    ADD INDEX FK_TAX_RATE_PRODUCTTYPE(productTypeId), 
    ADD CONSTRAINT FK_TAX_RATE_PRODUCTTYPE 
    FOREIGN KEY (productTypeId) 
    REFERENCES product_type(productTypeId);

/*==============================================================*/
/* Table: product_type                                          */
/*==============================================================*/
ALTER TABLE product_type ADD version int default 0 not null;
ALTER TABLE product_type CHANGE typeDescription productTypeDescription varchar(128);
ALTER TABLE product_type ADD status smallint;

/*===========attribute category=============================*/
alter table attribute change attributeDateType attributeDataType int(11) NOT NULL;
alter table category_attr_value change attributeDateType attributeDataType int(11) NOT NULL;
alter table category_attr_value drop foreign key FK_ATTR_V__CATEGORY;
alter table category_attr_value add CONSTRAINT FK_ATTR_V__CATEGORY foreign key(categoryId) references category (categoryId) on delete cascade  on update cascade;
alter table category_attr_value drop foreign key FK_CATEGORY_V__ATTR; 
alter table category_attr_value add CONSTRAINT FK_CATEGORY_V__ATTR foreign key(attributeId) references attribute (attributeId) on delete cascade  on update cascade;

/*================attribute content==========================*/
alter table content_attr_value change attributeDateType attributeDataType int(11) NOT NULL;
alter table content_attr_value drop foreign key FK_ATTR_V_CONTENT;
alter table content_attr_value add CONSTRAINT FK_ATTR_V_CONTENT foreign key(contentId) references content (contentId) on delete cascade  on update cascade;
alter table content_attr_value drop foreign key FK_CONTENT_V_ATTR; 
alter table content_attr_value add CONSTRAINT FK_CONTENT_V_ATTR foreign key (attributeId) references attribute (attributeId) on delete cascade  on update cascade;

/*=================attribute customer==========================*/
alter table customer_attr_value change attributeDateType attributeDataType int(11) NOT NULL;
alter table customer_attr_value drop foreign key FK_ATTR_V__CUSTOMER;
alter table customer_attr_value add CONSTRAINT FK_ATTR_V__CUSTOMER foreign key(customerId) references app_user (appuserId) on delete cascade  on update cascade;
alter table customer_attr_value drop foreign key FK_CUSTOMER_V__ATTR; 
alter table customer_attr_value add CONSTRAINT FK_CUSTOMER_V__ATTR foreign key (attributeId) references attribute (attributeId) on delete cascade  on update cascade;

/*==================attribute product========================*/
alter table product_attr_value change attributeDateType attributeDataType int(11) NOT NULL;
alter table product_attr_value drop foreign key FK_Reference_PRODUCT_ATTR_VALUE__PRODUCT;
alter table product_attr_value add CONSTRAINT FK_Reference_PRODUCT_ATTR_VALUE__PRODUCT foreign key(productId) references product (productId) on delete cascade  on update cascade;
alter table product_attr_value drop foreign key FK_Reference_PRODUCT_ATTR_VALUE__ATTRIBUTE; 
alter table product_attr_value add CONSTRAINT FK_Reference_PRODUCT_ATTR_VALUE__ATTRIBUTE foreign key(attributeId) references attribute (attributeId) on delete cascade  on update cascade;

/*====================attribute sale========================*/
alter table salesorder_attr_value change attributeDateType attributeDataType int(11) NOT NULL;
alter table salesorder_attr_value drop foreign key FK_ATTR_V__ORDER;
alter table salesorder_attr_value add CONSTRAINT FK_ATTR_V__ORDER foreign key(orderId) references sales_order (orderId) on delete cascade  on update cascade;
alter table salesorder_attr_value drop foreign key FK_ORDER_V__ATTR; 
alter table salesorder_attr_value add CONSTRAINT FK_ORDER_V__ATTR foreign key(attributeId) references attribute (attributeId) on delete cascade  on update cascade;

/*====================attribute productgroup========================*/
alter table product_att_group_item drop foreign key FK_Reference_PRODUCT_TYPE_ATTRIBUTE__ATTRIBUTE; 
alter table product_att_group_item add CONSTRAINT FK_Reference_PRODUCT_TYPE_ATTRIBUTE__ATTRIBUTE foreign key(attributeId) references attribute (attributeId) on delete cascade  on update cascade;


/*====================attribute promotion_product========================*/
drop table promotion_product;

/*====================attribute coupon========================*/
drop table coupon;

/*====================attribute coupon_type========================*/
drop table coupon_type;

/*====================attribute order_coupon========================*/
drop table order_coupon;

/*====================attribute order_item_promotion========================*/
drop table order_item_promotion;

/*====================attribute promotion========================*/
drop table promotion;

/*==============================================================*/
/* Table:promo_rule                                                                                                                                        */
/*==============================================================*/
create table promo_rule  
(
   promoRuleId          int not null auto_increment,
   name                 varchar(64) not null ,
   description          varchar(256) ,
   priority             int not null ,
   enableDiscountAgain  smallint not null ,
   startTime            datetime not null,
   endTime              datetime,
   promoType            varchar(32) not null,
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (promoRuleId)
);

/*==============================================================*/
/* Table:promo_rule_element                                                                                                                         */
/*==============================================================*/
create table promo_rule_element
(
   promoRuleElementId   int not null auto_increment,
   promoRuleId          int not null,
   kind                 varchar(16) not null ,
   type                 varchar(64) not null,
   sort                 int ,
   primary key (promoRuleElementId)
);

alter table PROMO_RULE_ELEMENT add constraint FK_ELEMENT__RULE foreign key (promoRuleId)
      references PROMO_RULE (promoRuleId) on delete restrict on update restrict;
      
/*==============================================================*/
/* Table:promo_rule_parameter                                                                                                                         */
/*==============================================================*/
create table promo_rule_parameter
(
   promoRuleParameterId int not null auto_increment,
   promoRuleElementId   int not null,
   paramName            varchar(32) not null,
   paramValue           varchar(64) not null,
   isExcluded           smallint not null ,
   excludedType         varchar(16),
   primary key (promoRuleParameterId)
);

alter table PROMO_RULE_PARAMETER add constraint FK_PARAM__ELEMENT foreign key (promoRuleElementId)
      references PROMO_RULE_ELEMENT (promoRuleElementId) on delete restrict on update restrict;

/*==============================================================*/
/* Table:coupon                                                                                                                         */
/*==============================================================*/
create table coupon
(
   couponId             int not null auto_increment,
   promoRuleId          int,
   couponNo             varchar(16) not null,
   isSent               smallint,
   remainedTimes        int,
   status               smallint,
   version              int not null default 0,
   primary key (couponId),
   unique key AK_Key_2 (couponNo)
);


alter table coupon add constraint FK_COUPON__RULE foreign key (promoRuleId)
      references PROMO_RULE (promoRuleId) on delete restrict on update restrict;

---SkuOptionValue增加type区分值类型（文本，色块，图片）
ALTER TABLE SKU_OPTION_VALUE ADD skuOptionValueType smallint not null;



/* update shoppingCart by ryan 2008-10-21 */
drop table SHOPPINGCART_ITEM_PROMOTION;
drop table SHOPPINGCART_ITEM;
drop table SHOPPINGCART;
drop table SAVED_PRODUCT_LIST;

create table SHOPPINGCART
(
   shoppingCartId       int not null auto_increment,
   customerId           int,
   usedCouponTypeId     int,
   isUsedCoupon         smallint not null,
   cartDiscountAmount   numeric(12,2) default 0.0,
   shippingDiscountInfo varchar(256),
   gainedPoint          int,
   gainedCouponTypeId   int,
   subtotal             numeric(12,2) not null default 0.0,
   total                numeric(12,2) not null default 0.0,
   itemsCount           int,
   buyNowItemsCount     int,
   buyLaterItemsCount   int,
   createTime           datetime not null,
   updateTime           datetime not null,
   primary key (shoppingCartId)
);

create table SHOPPINGCART_ITEM
(
   shoppingCartItemId   int not null auto_increment,
   parentId             int,
   shoppingCartId       int not null,
   giftCertificateId    int,
   productSkuId         int,
   itemType             smallint not null,
   price                numeric(12,2) not null,
   discountPrice        numeric(12,2),
   quantity             int not null,
   itemDiscountAmount   numeric(12,2),
   isWholesale          smallint not null,
   isOnSale             smallint not null,
   isSaved              smallint not null,
   addTime              datetime not null,
   primary key (shoppingCartItemId)
);

alter table SHOPPINGCART_ITEM add constraint FK_CART_ITEM__CART foreign key (shoppingCartId)
      references SHOPPINGCART (shoppingCartId) on delete cascade on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_CART_ITEM__GIFT foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_CART_ITEM__SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete restrict on update restrict;
      
      
/** salesOrder, added by pengzhirong*/
drop table ORDER_COUPON;
drop table ORDER_PAYMENT;
drop table ORDER_SHIPMENT_ITEM;
drop table ORDER_SHIPMENT;
drop table ORDER_ITEM_ATTRIBUTE;
drop table ORDER_ITEM_PROMOTION;
drop table ORDER_ITEM;
drop table salesorder_attr_value;
drop table sales_order;

/*****购物车模块脚本*********/
drop table shoppingcart_item_promotion;
drop table shoppingcart_item;
drop table shoppingcart;

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM                                     */
/*==============================================================*/
create table SHOPPINGCART_ITEM
(
   shoppingCartItemId   int not null auto_increment,
   parentId             int comment ,
   shoppingCartId       int not null,
   giftCertificateId    int,
   productSkuId         int,
   itemType             smallint not null,
   price                numeric(12,2) not null,
   discountPrice        numeric(12,2) ,
   quantity             int not null,
   itemDiscountAmount   numeric(12,2) ,
   isWholesale          smallint not null ,
   isOnSale             smallint not null,
   isSaved              smallint not null ,
   addTime              datetime not null,
   primary key (shoppingCartItemId)
);
/*==============================================================*/
/* Table: SHOPPINGCART                                          */
/*==============================================================*/
create table SHOPPINGCART
(
   shoppingCartId       int not null auto_increment,
   Uuid                 varchar(40) not null,
   customerId           int,
   usedCouponTypeId     int,
   isUsedCoupon         smallint not null ,
   cartDiscountAmount   numeric(12,2) default 0.0 ,
   shippingDiscountInfo varchar(256),
   gainedPoint          int ,
   gainedCouponTypeId   int ,
   subtotal             numeric(12,2) not null default 0.0 ,
   total                numeric(12,2) not null default 0.0 ,
   itemsCount           int,
   buyNowItemsCount     int,
   buyLaterItemsCount   int,
   createTime           datetime not null,
   updateTime           datetime not null,
   primary key (shoppingCartId)
);

/*==============================================================*/
/* Index: I_UUID                                                */
/*==============================================================*/
create index I_UUID on SHOPPINGCART
(
   Uuid
);

/*==============================================================*/
/* Table: SHOPPINGCART_PROMOTION                                */
/*==============================================================*/
create table SHOPPINGCART_PROMOTION
(
   shoppingcartPromotionId int not null auto_increment,
   shoppingCartId       int,
   promoRuleId          int not null,
   couponNo             varchar(16),
   promotionName        varchar(64) not null,
   primary key (shoppingcartPromotionId)
);

alter table app_user drop column shoppingCartId;
alter table shoppingcart_item add constraint FK_shoppingcart_item foreign key(shoppingCartId) references shoppingcart(shoppingCartId) on delete cascade  on update cascade



/** sales order block, added by pengzhirong*/
create table ORDER_ADDRESS
(
   orderAddressId       int not null auto_increment,
   firstName            varchar(32) not null,
   lastName             varchar(32) not null,
   phoneNumber          varchar(32),
   faxNumber            varchar(32),
   postalcode           varchar(6),
   country              varchar(64) not null,
   state                varchar(64),
   city                 varchar(64),
   address1             varchar(128) not null,
   address2             varchar(128),
   primary key (orderAddressId)
);

create table SALES_ORDER
(
   salesOrderId         int not null auto_increment,
   billingAddressId     int,
   customerId           int,
   orderNo              varchar(20) not null ,
   membershipId         int,
   customerFirstName    varchar(32) not null,
   customerLastname     varchar(32),
   customerEmail        varchar(64) not null,
   shopPoint            int ,
   totalAmount          numeric(12,2) not null ,
   paidAmount           numeric(12,2) not null,
   orderStatus          smallint not null,
   paymentStatus        smallint not null,
   isExchangeOrder      smallint ,
   isCod                smallint not null,
   invoiceTitle         varchar(128) ,
   hasInvoice           smallint not null,
   gainedPoint          int,
   gainedCouponTypeId   int,
   ipAddress            varchar(64),
   isOnHold             smallint not null ,
   isHoldByCustomer     smallint ,
   isLocked             smallint not null,
   lockedBy             int,
   createTime           datetime not null,
   updateTime           datetime not null,
   updateBy             int,
   version              int not null default 0,
   primary key (salesOrderId),
   unique key AK_Key_2 (orderNo)
);

alter table SALES_ORDER add constraint FK_ORDER__BILLING_ADDR foreign key (billingAddressId)
      references ORDER_ADDRESS (orderAddressId) on delete restrict on update restrict;


create table ORDER_SHIPMENT
(
   orderShipmentId      int not null auto_increment,
   salesOrderId         int not null,
   shippingAddressId    int not null,
   orderPickListId      int,
   shipmentNo           varchar(32) not null,
   trackingNo           varchar(128),
   carrierName          varchar(128),
   itemSubTotal         numeric(12,2) not null,
   itemTax              numeric(12,2) not null,
   shippingTax          numeric(12,2) not null,
   shippingCost         numeric(12,2) not null,
   discountAmount       numeric(12,2) not null,
   wrapName             varchar(64),
   wrapUnitPrice        numeric(12,2),
   wrapNote             varchar(512),
   intelligentReview    smallint not null default 0,
   status               smallint,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null default 0,
   primary key (orderShipmentId),
   key AK_SHIP_NO (shipmentNo)
);

alter table ORDER_SHIPMENT add constraint FK_SHIPMENT__ADDRESS foreign key (shippingAddressId)
      references ORDER_ADDRESS (orderAddressId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_ORD_SHIPMENT__ORD foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_SHIPMENT__PICK_LIST foreign key (orderPickListId)
      references ORDER_PICK_LIST (orderPickListId) on delete restrict on update restrict;

create table ORDER_SKU
(
   orderSkuId           int not null auto_increment,
   giftCertificateId    int,
   productSkuId         int,
   orderShipmentId      int,
   productName          varchar(128) not null,
   productSkuCode       varchar(32),
   itemType          smallint not null ,
   quantity             int not null,
   price                numeric(12,2) not null,
   discountPrice        numeric(12,2) ,
   isOnSale             smallint not null ,
   isWholesale          smallint not null ,
   tax                  numeric(12,2),
   taxName              varchar(64),
   itemDiscountAmount   numeric(12,2),
   weight               numeric(12,2),
   allocatedQuantity    int,
   version              int not null default 0,
   primary key (orderSkuId)
);

alter table ORDER_SKU add constraint FK_O_SKU__GC foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId) on delete restrict on update restrict;

alter table ORDER_SKU add constraint FK_O_SKU__P_SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete restrict on update restrict;

alter table ORDER_SKU add constraint FK_O_SKU__SHIPMENT foreign key (orderShipmentId)
      references ORDER_SHIPMENT (orderShipmentId) on delete restrict on update restrict;
      
create table ORDER_AUDIT
(
   orderAuditId         int not null auto_increment,
   salesOrderId         int,
   addedBy              varchar(32) not null,
   createTime           datetime not null,
   transactionType      varchar(32) not null,
   detail               varchar(512),
   primary key (orderAuditId)
);

alter table ORDER_AUDIT add constraint FK_AUDIT__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;
      
create table ORDER_RETURN
(
   orderReturnId        int not null auto_increment,
   rmaNo                varchar(32) not null,
   salesOrderId         int not null,
   exchangeOrderId      int,
   receivedBy           int ,
   returnType           smallint not null ,
   status               smallint not null,
   isPhysicalReturn     smallint not null,
   lessRestockAmount    numeric(12,2) ,
   note                 varchar(1024),
   createBy             int not null,
   version              int not null default 0,
   primary key (orderReturnId),
   key AK_Key_2 (rmaNo)
);

alter table ORDER_RETURN add constraint FK_RETURN__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_RETURN add constraint FK_EXCHANGE__ORDER foreign key (exchangeOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_RETURN add constraint FK_RETURN__APP_USER foreign key (receivedBy)
      references APP_USER (appuserId) on delete restrict on update restrict;

create table ORDER_RETURN_SKU
(
   orderReturnSkuId     int not null auto_increment,
   orderReturnId        int not null,
   orderSkuId           int,
   quantity             int not null,
   returnAmount         numeric(12,2) not null,
   receivedQuantity     int not null,
   reasonType           smallint not null,
   primary key (orderReturnSkuId)
);

alter table ORDER_RETURN_SKU add constraint FK_RETURN_SKU__RETURN foreign key (orderReturnId)
      references ORDER_RETURN (orderReturnId) on delete restrict on update restrict;

alter table ORDER_RETURN_SKU add constraint FK_RETURN_SKU__O_SKU foreign key (orderSkuId)
      references ORDER_SKU (orderSkuId) on delete restrict on update restrict;
      
create table ORDER_PAYMENT
(
   orderPaymentId       int not null auto_increment,
   salesOrderId         int not null,
   paymentAmount        numeric(12,2) not null,
   balance              numeric(12,2) not null,
   transactionType      smallint not null,
   paymentGatewayName   varchar(256),
   ipAddress            varchar(64),
   giftCertificateNo    varchar(32),
   addedBy              varchar(32) not null,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (orderPaymentId)
);

alter table ORDER_PAYMENT add constraint FK_OR_PA_SALES_OR foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

     
create table ORDER_PROMOTION
(
   orderPromotionId     int not null auto_increment,
   salesOrderId         int,
   promoRuleId          int not null,
   couponNo             varchar(16),
   promotionName        varchar(64) not null,
   primary key (orderPromotionId)
);

alter table ORDER_PROMOTION add constraint FK_O_PROMO__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;
      
create table ORDER_SETTLEMENT
(
   orderSettlementId    int not null auto_increment,
   shipmentNo           varchar(32) not null,
   orderId              int not null,
   orderNo              varchar(20) not null,
   carrierName          varchar(128) not null ,
   trackingNo           varchar(128) not null,
   originalTotal        numeric(12,2) not null ,
   settlementAmount     numeric(12,2) not null,
   isComplete           smallint not null default 0,
   addedBy              varchar(32),
   primary key (orderSettlementId),
   key AK_SHIPMENT_NO (shipmentNo)
);

alter table SALES_ORDER CHANGE column customerFirstName customerFirstname varchar(32) not null;
alter table ORDER_ADDRESS CHANGE column firstName firstname varchar(32) not null;
alter table ORDER_ADDRESS CHANGE column lastName lastname varchar(32) null;

-- build 081110
alter table promo_rule add eligibilityOperator  smallint  default 1;
alter table promo_rule add conditionOperator  smallint not null default 1;

alter table product modify url varchar(255);
alter table product modify title varchar(255);

alter table order_sku add column productId int null after orderShipmentId;

ALTER TABLE product DROP COLUMN skuKind;
ALTER TABLE product DROP COLUMN isPackage;
ALTER TABLE product_Sku DROP COLUMN isPackage,DROP COLUMN productKind;

alter table product add productKind  smallint not null default 1 ;

alter table product_Sku add skuKind  smallint not null default 1 ;

ALTER TABLE `product` MODIFY COLUMN `planStartTime` DATETIME ;
ALTER TABLE `product` MODIFY COLUMN `membershipId` INTEGER;

-- 调整shop_point_history的外键名。
drop table shop_point_history;
create table SHOP_POINT_HISTORY
(
   shopPointHistoryId   int not null auto_increment,
   customerId           int,
   shopPointType        smallint not null,
   description          varchar(128),
   amount               int not null,
   createTime           datetime,
   primary key (shopPointHistoryId)
);

alter table SHOP_POINT_HISTORY add constraint FK_SHOP_CUST2 foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

-- build 081117
alter table promo_rule add availableCount INTEGER(11);

alter table PRODUCT drop FOREIGN KEY FK_PRO__BRAND;
alter table PRODUCT add constraint FK_PRO__BRAND foreign key (brandId)
      references BRAND (brandId) on delete set null on update restrict;
      
alter table PRODUCT_CATEGORY drop FOREIGN KEY FK_P_CAT__P;
alter table PRODUCT_CATEGORY add constraint FK_P_CAT__P foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;
      
alter table shoppingcart_promotion drop couponNo;

alter table shoppingcart drop usedCouponTypeId;
alter table shoppingcart add usedCouponNo varchar(16) null;      

-- build 081202
/*--huang wenmim 增加外键--*/
alter table shoppingcart_promotion add constraint FK_S_P foreign key(shoppingCartId) references shoppingcart (shoppingCartId) on delete cascade  on update cascade; 

/*--added by pengzhirong--*/
alter table order_settlement add column shipmentId int not null after orderSettlementId;
alter table order_shipment change column intelligentReview hasRobotReviewed smallint not null default 0;
alter table order_shipment add column isConfirmedByRobot smallint after hasRobotReviewed;

alter table sales_order add column customerTitle varchar(8) not null after customerLastname;
alter table order_address add column title varchar(8) not null after lastname;

/*产品级联删除*/
alter table PRODUCT_ATTR_VALUE drop FOREIGN KEY FK_ATTR_V__PRODUCT;
alter table PRODUCT_ATTR_VALUE add constraint FK_ATTR_V__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_SKU drop FOREIGN KEY FK_SKU__PRODUCT;
alter table PRODUCT_SKU add constraint FK_SKU__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;
      
alter table PRODUCT drop FOREIGN KEY FK_PRODUCT__DEF_SKU;


alter table WHOLESALE_PRICE drop FOREIGN KEY FK_WHOLESALE__SKU;
alter table WHOLESALE_PRICE add constraint FK_WHOLESALE__SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

alter table PRODUCT_SKU_OPTION_VALUE drop FOREIGN KEY FK_SKU__OPT_VALUE;
alter table PRODUCT_SKU_OPTION_VALUE add constraint FK_SKU__OPT_VALUE foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

delete from  PRODUCT_MEDIA where productId is null;

alter table PRODUCT_MEDIA drop FOREIGN KEY FK_MEDIA__P;
alter table PRODUCT_MEDIA add constraint FK_MEDIA__P foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;
      
alter table PRODUCT_STAT drop FOREIGN KEY FK_STAT__PRODUCT;
alter table PRODUCT_STAT add constraint FK_STAT__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT drop FOREIGN KEY FK_PRODUCT__DESC;
alter table PRODUCT add constraint FK_PRODUCT__DESC foreign key (productDescriptionId)
      references PRODUCT_DESCRIPTION (productDescriptionId) on delete set null on update restrict;

alter table PRODUCT_PACKAGE_ITEM drop FOREIGN KEY FK_PACKAGE_SKU;
alter table PRODUCT_PACKAGE_ITEM add constraint FK_PACKAGE_SKU foreign key (packageSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;
      


-- for recommended
drop table recommended_type_location;
drop table recommended_product;
/*==============================================================*/
/* Table: RECOMMENDED_PRODUCT                                   */
/*==============================================================*/
create table RECOMMENDED_PRODUCT
(
   recommendedProductId int not null auto_increment,
   productId            int not null,
   recommendedTypeId    int not null,
   sourceId      int,
   status               smallint,
   sortOrder            int,
   locationType         smallint,
   startTime            datetime,
   expireTime           datetime,
   version              int not null default 0,
   primary key (recommendedProductId)
);

alter table RECOMMENDED_PRODUCT add constraint FK_REC_PRO foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table RECOMMENDED_PRODUCT add constraint FK_RECO_RECOMMEN foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId) on delete restrict on update restrict;

delete from recommended_type;
INSERT INTO `recommended_type` (`recommendTitle`,`typeName`,`ruleCode`,`autoEval`,`status`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES 
 ('热卖产品','hot_sell',1,1,1,1,0,1,0),
 ('最新产品','new_arrival',3,1,1,1,0,1,0),
 ('购买此商品的顾客也购买了','also_buy',4,1,1,1,1,0,0),
 ('特色产品','feature_product',0,0,1,1,0,1,0),
 ('相似产品推荐','similar_product',5,0,1,1,1,0,0),
 ('同品牌的其他相似产品','same_brand',7,1,1,1,1,0,0),
 ('价格相近的其他同类产品','similar_price',6,1,1,1,1,0,0),
 ('可选配件','accessories',0,0,1,1,1,0,0);
 
/*PRODUCT_ATTR_VALUE增加与group_item的关联*/
alter table PRODUCT_ATTR_VALUE add column productAttGroupItemId int;
alter table PRODUCT_ATTR_VALUE add constraint FK_ATT_V_PRO_A_G_ITEM foreign key (productAttGroupItemId)
      references PRODUCT_ATT_GROUP_ITEM (productAttGroupItemId) on delete cascade on update restrict;
      
      
---------for content      
drop table content_attr_value;
drop table content_type;
alter table content drop FOREIGN KEY FK_CONTENT__CAT;
drop table content;

/*==============================================================*/
/* Table: CONTENT                                               */
/*==============================================================*/
create table CONTENT
(
   contentId            int not null auto_increment,
   categoryId           int,
   contentTitle         varchar(64) not null,
   contentBody          text,
   publishTime          datetime not null,
   expiredTime          datetime,
   metaKeyword          varchar(256),
   metaDescription      varchar(256),
   status               smallint not null,
   sortOrder            int not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (contentId)
);

alter table CONTENT add constraint FK_CONTENT__CAT foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

/*==============================================================*/
/* Table: CONTENT_ATTR_VALUE                                    */
/*==============================================================*/
create table CONTENT_ATTR_VALUE
(
   contentAttrValueId   int not null auto_increment,
   attributeId          int,
   contentId            int,
   shortTextValue       varchar(255),
   longTextValue        text,
   intValue             int,
   decimalValue         numeric(12,2),
   booleanValue         smallint,
   dateValue            datetime,
   attributeDataType    int not null,
   primary key (contentAttrValueId)
);

alter table CONTENT_ATTR_VALUE add constraint FK_CONTENT_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table CONTENT_ATTR_VALUE add constraint FK_ATTR_V__CON foreign key (contentId)
      references CONTENT (contentId) on delete restrict on update restrict;
     
      
-- clean system_config
delete from system_config;

alter table order_return add column createTime datetime not null after note;
alter table order_return_sku add column receivedStatus smallint after receivedQuantity;

drop table product_series_item;
drop table product_series;

--- build1208
ALTER TABLE product_review MODIFY COLUMN subject VARCHAR(128);

alter table PRODUCT_REVIEW add constraint FK_REVIEW__P_REVIEW foreign key (reviewId)
      references PRODUCT_REVIEW (productReviewId) on delete cascade on update restrict;
      
--原FK_R_VOTE__P_REVIEW没有设置级联删除，现在重新添加上
alter table REVIEW_VOTE drop FOREIGN KEY FK_R_VOTE__P_REVIEW;

alter table REVIEW_VOTE add constraint FK_R_VOTE__P_REVIEW foreign key (productReviewId)
      references PRODUCT_REVIEW (productReviewId) on delete cascade on update restrict;

alter table PRODUCT_REVIEW add column givenBy int after givenPoint;

ALTER TABLE tax_Rate
	DROP COLUMN `applyType`;
	
alter table sales_order add column originalOrderId int after isExchangeOrder;

-- Build1222
---price不能少于等于0
update product_sku set price=100 where price<=0;

alter table INVENTORY_AUDIT add column productSkuId int after inventoryId;
alter table INVENTORY_AUDIT add column quantityOnHand int not null;
alter table INVENTORY_AUDIT add column allocatedQuantity int not null;



alter table INVENTORY drop FOREIGN KEY FK_INVENTORY__SKU;
alter table INVENTORY add constraint FK_INVENTORY__SKU foreign key (productSkuId) references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;
alter table INVENTORY_AUDIT drop FOREIGN KEY FK_AUDIT__INVENTORY;   
alter table INVENTORY_AUDIT add constraint FK_AUDIT__INVENTORY foreign key (inventoryId) references INVENTORY (inventoryId) on delete cascade on update restrict;

----add system config value
INSERT INTO system_config(`configKey`,`description`,`category`,`configType`,`configValue`,`options`,`dataType`,`isSystem`,`createBy`,`updateBy`,`createTime`,`updateTime`,`version`) 
VALUE ("IsAllowSystemRulesWhenUseCoupon","","sales.coupon.rule",3,"false","",3,2,-1,-1,"2008-12-16 00:00:00","2008-12-16 00:00:00",0);

---删除没用的系统配置
delete from system_config where configKey='MultipleMediaExtendName';
delete from system_config where configKey='MoreImageExtendName';

ALTER TABLE product_review MODIFY COLUMN subject VARCHAR(128);

--Build 1229
alter table order_return add column itemSubTotal numeric(12,2) not null comment '需退还的商品总计金额' after isPhysicalReturn;
alter table order_return add column shippingCost numeric(12,2) not null comment '需退还的运输费' after itemSubTotal;
alter table order_return add column itemTax numeric(12,2) not null comment '需退还的商品税' after shippingCost;
alter table order_return add column shippingTax numeric(12,2) not null comment '需退还的运输税' after itemTax;


---目录表code和type组合唯一键
ALTER TABLE category DROP INDEX `AK_Key_3`,
 ADD UNIQUE `AK_Key_17` USING BTREE(`categoryCode`, `categoryType`);

 
 /*内容目录内的帮助中心及其四条基础数据(注意： 如果需要修改categoryId，那么也要注意修改parentId和categoryPath)*/
 INSERT INTO `category` ( `categoryId`, `parentId`, `categoryType`, `categoryName`, `categoryDescription`, `categoryCode`, `metaKeyword`, `metaDescription`, `templatePath`, `categoryPath`, `imageUrl`, `linkUrl`, `isLinkCategory`, `sortOrder`, `status`, `createBy`, `updateBy`, `createTime`, `updateTime`, `version`) VALUES 
  (3, 2, 2, '帮助中心', '帮助中心', 'help', '帮助中心', '帮助中心', '', '2.', '', '', 0, 1, 1, 1, 1, '2008-12-22 14:48:20', '2008-12-22 14:48:29', 1),
  (4, 3, 2, '订单帮助', '订单帮助', 'orderHelp', '订单帮助', '订单帮助', '', '2.3.', '', '', 0, 1, 1, 1, 1, '2008-12-22 13:26:53', '2008-12-22 14:48:29', 1),
  (5, 3, 2, '运输和退货', '运输和退货', 'shippingAndReturn', '运输和退货', '运输和退货', '', '2.3.', '', '', 0, 2, 1, 1, 1, '2008-12-22 13:39:36', '2008-12-22 14:48:29', 1),
  (6,3, 2, '产品帮助', '产品帮助', 'productHelp', '产品帮助', '产品帮助', '', '2.3.', '', '', 0, 3, 1, 1, 1, '2008-12-22 13:42:04', '2008-12-22 14:48:29', 1),
  (7, 3, 2, '关于网上商店', '关于网上商店', 'aboutEstore', '关于网上商店', '关于网上商店', '', '2.3.', '', '', 0,4, 1, 1, 1, '2008-12-22 13:43:47', '2008-12-22 14:48:29', 1);
  
  
  delete from promo_rule_parameter where paramName  like '%SUBTOTAL%';
  delete from promo_rule_element where type  like '%Subtotal%';

--Build0105
alter table order_return add column itemSubTotal numeric(12,2) not null comment '需退还的商品总计金额' after isPhysicalReturn;
alter table order_return add column shippingCost numeric(12,2) not null comment '需退还的运输费' after itemSubTotal;
alter table order_return add column itemTax numeric(12,2) not null comment '需退还的商品税' after shippingCost;
alter table order_return add column shippingTax numeric(12,2) not null comment '需退还的运输税' after itemTax;
/**huangwenmin**/
alter table shoppingcart_item change itemType skuType int(6) NOT NULL;
alter table shoppingcart change shoppingCartId shoppingcartId int NOT NULL auto_increment;
alter table shoppingcart_item change shoppingCartItemId shoppingcartItemId int NOT NULL auto_increment;
alter table shoppingcart_item change shoppingCartId shoppingcartId int NOT NULL;
alter table shoppingcart_promotion change shoppingCartId shoppingcartId int NOT NULL;
alter table shoppingcart_item add itemType  smallint  default 1;
/*==============================================================*/
/* Table: SHOPPINGCART_ITEM_GC                                  */
/*==============================================================*/
create table SHOPPINGCART_ITEM_GC  
(
   shoppingcartItemGcId int not null auto_increment,
   shoppingcartItemId   int,
   purchaser            varchar(64) not null,
   recipient            varchar(64) not null,
   recipientEmail       varchar(64) not null,
   message              varchar(512),
   giftCertAmt          numeric(12,2) not null,
   primary key (shoppingcartItemGcId)
);
alter table SHOPPINGCART_ITEM_GC add constraint FK_CARTITEM__GC foreign key (shoppingcartItemId)
      references SHOPPINGCART_ITEM (shoppingcartItemId) on delete cascade on update restrict;
      
alter table shoppingcart_item drop FOREIGN KEY FK_shoppingcart_item;
alter table shoppingcart_promotion drop FOREIGN KEY FK_S_P;
alter table shoppingcart_item add constraint FK_shoppingcart_item foreign key (shoppingcartId) references shoppingcart(shoppingcartId) on delete cascade  on update cascade
alter table shoppingcart_promotion add constraint FK_shoppingcart_promotion foreign key (shoppingcartId) references shoppingcart (shoppingcartId) on delete cascade  on update cascade;

/**huangwenmin**/
alter table shoppingcart_item drop column giftCertificateId;   

/*Build0112*/
/**added by pengzhirong**/
alter table order_sku modify column productName varchar(128) null;
alter table order_sku modify column itemType smallint not null comment 'types of items/goods:1:product (default) 2:gift certificate';

/* added by ljy */
alter table order_sku add displaySkuOptions varchar(255);

/**added by heqingming **/
delete from system_config where configKey = 'GiftCertificateCanPostMail';

/*Build0119*/
/**modified by heqingming**/
delete from gift_certificate where status=10 or status=4;

alter table ORDER_SHIPMENT add column shippingRateId int after orderPickListId;
alter table ORDER_RETURN add column discountAmount numeric(12,2) not null ;


ALTER TABLE `product_type` MODIFY COLUMN `minOrderQuantity` INTEGER NOT NULL ;

delete FROM system_config where configKey='SalesOrderPrefix';

alter table PRODUCT_CATEGORY drop FOREIGN KEY FK_P_CAT__CAT;

alter table PRODUCT_CATEGORY add constraint FK_P_CAT__CAT foreign key (categoryId) references CATEGORY (categoryId) on delete cascade on update restrict;

/*Build0202*/
alter table also_buy 
add column createTime  datetime not null,
add column updateTime  datetime not null;

/*Build0216*/
alter table ORDER_SHIPMENT modify column shippingAddressId int null;

/*Build0303*/
delete from system_config where configKey='IsAutoGeneratoreMediaFileName';
delete from system_config where configkey="ShopPointUsePercent" or configkey="IsShopPointPresentAfterPay"

/*Build0309*/
delete from system_config where configKey='FeedbackShopPointAmount';
delete from system_config where configKey='OrderAutoExpireDays';

INSERT INTO `app_resource` (`resourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES
 (8,'报表',0,'/report/*','报表模块下的所有页面');
INSERT INTO `role_res` (`resourceId`,`roleId`) VALUES
 (8,1);
 
/* Product Sku 增加成本价     默认值暂时为100 */
alter table product_sku add column costPrice numeric(12,2) not null;

/* 订单添加结算状态 */
alter table sales_order add column settlementStatus int not null;

/*==============================================================*/
/* Table: SALES_ORDER_BALANCE_HISTORY                           */
/*==============================================================*/
create table SALES_ORDER_BALANCE_HISTORY
(
   salesOrderBalanceHistoryId int not null auto_increment,
   gatewayCode          varchar(32) not null,
   gatewayBalanceNo     varchar(64) not null,
   balanceMoney         numeric(12,2) not null,
   totalCost            numeric(12,2) not null,
   exchangeRate         numeric(12,4) not null,
   orderStartTime       datetime not null,
   orderEndTime         datetime not null,
   comment              varchar(2048),
   existRefund          smallint not null default 0,
   orderNos             varchar(2048) not null,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   primary key (salesOrderBalanceHistoryId)
);

/* merger probizV3 */
alter table product_advertisement add column categoryId int not null;
update product_advertisement set categoryId=1;
/* 去掉属性的status */
alter table attribute drop column status;
/* 创建一个专为shoppingcart的统计的view */
CREATE OR REPLACE view V_SHOPPINGCART_ITEM_STAT 
AS select sum(s.quantity) as quantity,s.productSkuId,s.itemType,s.isSaved
   from shoppingcart_item s
   where s.productSkuId is not null and s.parentId is null
   group by s.productSkuId;
   
/* zipcode统一增长为12位 */
Alter table address modify zip varchar(12);
Alter table app_user modify zip varchar(12);
Alter table carrier modify zip  varchar(12);
Alter table company_info modify zip varchar(12);
Alter table gift_certificate modify recipientZip varchar(12);
Alter table order_address modify postalcode  varchar(12);
Alter table region modify zipCode  varchar(12);

Alter table ORDER_AUDIT modify detail varchar(1024);
Alter table ORDER_AUDIT modify addedBy varchar(64) not null;

alter table sales_order add column paymentMethodId int after isCod;
alter table order_pick_list add column updateTime datetime not null after createTime;
update order_pick_list set updateTime='2009-03-03';


/*去除地区前后空格*/
update region set regionName=trim(regionName) ;
update address set countryName=trim(countryName) ,stateName=trim(stateName),cityName=trim(cityName);



/* 修正app_user,使得username与usertype做唯一key */
alter table app_user drop key AK_USERNAME;
alter table app_user modify usertype smallint not null;
alter table app_user add unique key AK_USERNAME (username, userType);

/* 添加一个额外字段给product */
alter table product add extra1 varchar(255);

/* 成本价 */
alter table ORDER_SKU add costPrice numeric(12,2);
/* 发货时间 */
alter table ORDER_SHIPMENT add deliverTime datetime; 
/* 取消结算功能 */
drop table SALES_ORDER_BALANCE_HISTORY;

/* 新增其他颜色的推荐 */
INSERT INTO `recommended_type` (`recommendTitle`,`typeName`,`ruleCode`,`autoEval`,`status`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES
('Other Color','other_color',8,1,1,1,1,0,0);

alter table product_sku drop key ak_skucode;
alter table product_sku add unique key AK_SKUCODE (productSkuCode);

/* 去除IsServerSideProductPageCacheEnabled的配置,没有意义了 */
delete from system_config where configKey='IsServerSideProductPageCacheEnabled';

alter table sales_order drop column settlementStatus;

/* 新功能order message */
create table ORDER_MESSAGE
(
   orderMessageId       int not null auto_increment,
   subject              varchar(128) not null,
   message              text not null,
   customerId           int not null,
   salesOrderId         int not null,
   orderNo              varchar(20) not null,
   status               smallint not null,
   createTime           datetime not null,
   createBy             int not null,
   primary key (orderMessageId)
);
/*删除GeoIPCity配置*/
delete from system_config where configKey='GeoIPCity';

/* 调整region的code,因为国家的code有可能与state的code重复 */
alter table region modify column regionType SMALLINT not null;
alter table region drop key AK_Key_2;
alter table region add unique key AK_Key_2 (regionCode, regionType);

/*最少订购量非空*/
update product set minOrderQuantity=1 where minOrderQuantity is null;
ALTER TABLE product MODIFY COLUMN minOrderQuantity INT(11) NOT NULL;

/* 订单地理位置记录 */
/*==============================================================*/
/* Table: SALES_ORDER_GEOIP                                     */
/*==============================================================*/
create table SALES_ORDER_GEOIP
(
   salesOrderGeoipId    int not null auto_increment,
   orderNo              varchar(20) not null,
   customerIp           varchar(64),
   lon                  varchar(32),
   lat                  varchar(32),
   actionType           smallint not null,
   city                 varchar(64),
   state                varchar(64),
   country              varchar(64),
   createTime           datetime not null,
   primary key (salesOrderGeoipId)
);

/*==============================================================*/
/* Index: IX_SOG_ORDERNO                                        */
/*==============================================================*/
create index IX_SOG_ORDERNO on SALES_ORDER_GEOIP
(
   orderNo,
   actionType
);

/* 队列模块 */
create table SYSTEM_QUEUE
(
   systemQueueId        int not null auto_increment,
   title                varchar(256) not null,
   queueType            smallint not null,
   execTimes            smallint not null,
   targetEntiy          blob,
   errorMsg             varchar(1024),
   queueStatus          smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   nextRetryTime        datetime not null,
   primary key (systemQueueId)
);
/*删除匿名会员积分*/
delete from shop_point_history where customerId=-2;
delete from shop_point where customerId=-2;

/*增加组合唯一*/
alter table region_item add unique key AK_Key_REG_ITEM (regionId, itemId);

/*PAYMENT_METHOD与PAYMENT_GATEWAY合并*/
drop table PAYMENT_METHOD;
drop table PAYMENT_GATEWAY;

create table PAYMENT_METHOD
(
   paymentMethodId      int not null auto_increment,
   paymentMethodName    varchar(64) not null,
   paymentMethodCode    varchar(32) not null,
   paymentMethodDetail  varchar(1024),
   paymentMethodIcon    varchar(255),
   paymentMethodType    smallint comment '1=Online
            2=Offline',
   protocol             varchar(8),
   processorFile        varchar(256) comment 'processor the payment gateway request, such as "verisign.jsp"
            ',
   configFile           varchar(255) comment 'the name of the file which is provide the page to config the payment gateway',
   configData           blob comment 'store data for a paymentgateway configuration as a java serialize object
            (Java object : PaymentConfig)',
   testModel            char(1),
   isCod                smallint not null default 0 comment '0 must pay before shipping, 1 can shipping before pay',
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (paymentMethodId),
   unique key AK_Key_2 (paymentMethodCode)
);

INSERT INTO `payment_method` (`paymentMethodId`,`paymentMethodName`,`paymentMethodDetail`,`paymentMethodType`,`protocol`,`sortOrder`,`isCod`,`status`,`version`,`paymentMethodCode`,`testModel`,`paymentMethodIcon`,`configFile`,`processorFile`,`configData`) VALUES 
 (1,'COD','Cash on delivery',2,'HTTP',5,0,2,4,'cod','N',NULL,NULL,NULL,NULL),
 (2,'Western Union','<a href=\"http://www.replicaestore.com/mo/contents-code-WesternUnionForm/Western-Union.html\">Western Union</a>',2,'HTTP',4,0,2,8,'western_union','N',NULL,NULL,NULL,NULL),
 (3,'Credit Card','Visa Card Only',1,'HTTP',2,0,1,33,'ctopay','N','payment/icon/ctopay.gif','payment/ctopay_config','payment/ctopay_request',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000047400107061796D656E744761746577617949647400023139740008646F416374696F6E74001073617665436F6E666967416374696F6E7400064D44356B6579740008776361544460594C7400054D65724E6F7400043333313378),
 (4,'NPS','<a href=\"https://usa.visa.com/personal/security/vbv/index.html\">VISA Verified</a> Only',1,'HTTP',3,0,2,17,'nps_out','N','payment/icon/nps.gif','payment/nps_out_config','payment/nps_out_request',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000007740009746573744D6F64656C7400014E7400107061796D656E744761746577617949647400023135740008646F416374696F6E74001073617665436F6E666967416374696F6E7400074D65725F4B657974000A6A696168616F3230303874000A4D5F4C616E6775616765740001337400084D65725F436F646574000A3232323130323130363074000A4D4F43757272656E637974000355534478),
 (5,'MotoPay','Visa / MasterCard / American Express / Diners Club / JCB',1,'HTTPS',1,0,2,20,'china_bank_aim','N','payment/icon/CreditCard.gif','payment/chinabank_aim_config','payment/chinabank_request_aim',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000574000E765F65786368616E676572617465740006362E3835353074000A765F7465726D696E616C74000830303030303030317400107061796D656E744761746577617949647400023230740008646F416374696F6E74001073617665436F6E666967416374696F6E740005765F6D6964740008323038363434383278),
 (6,'authorizenet','authorizenet',1,'HTTPS',7,0,2,23,'authorizenet','N','payment/icon/authorizenet_logo.gif','payment/authorizenet_aim_config','payment/authorizenet_aim_request',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000008740007785F6C6F67696E74000937534C597239723261740009746573744D6F64656C74000130740006785F7479706574000C415554485F434150545552457400107061796D656E744761746577617949647400023231740008646F416374696F6E74001073617665436F6E666967416374696F6E74000A785F7472616E5F6B657974001036327134763733356533466B54364E3674000F785F63757272656E63795F636F646574000355535374000A785F6D64355F6861736874000768616E6462616778),
 (7,'Credit Card / Paypal (Recommended)','Visa Card / MasterCard / PayPal / American Express / Diners Club / JCB and eCheck',1,'HTTP',0,0,2,16,'paypal','N','payment/icon/paypal.gif','payment/paypal_config','payment/paypal_request',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000005740009746573744D6F64656C7400014E74000D63757272656E63795F636F64657400035553447400107061796D656E744761746577617949647400023133740008646F416374696F6E74001073617665436F6E666967416374696F6E740008627573696E65737374001773616C6573407265706C6963616573746F72652E636F6D78),
 (8,'Credit Card','Visa / MasterCard / American Express / JCB',1,'HTTP',8,0,1,12,'99bill','N','payment/icon/99bill.gif','payment/99bill_config','payment/99bill_request',0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000057400146D65726368616E744163637450617373776F726474000776706F733332317400107061796D656E744761746577617949647400023130740008646F416374696F6E74001073617665436F6E666967416374696F6E7400067465726D4964740008303230303030393274000E6D65726368616E7441636374496474000F39393934343030353231313030333278),
 (9,'IPS','IPS',1,'HTTP',9,0,2,0,'ips','Y','payment/icon/ips.gif','payment/ips_config','payment/ips_request',NULL),
 (10,'Checkout 2','Checkout 2',1,'HTTP',10,0,2,0,'checkout2','Y','payment/icon/checkout2.jpg','payment/checkout2_config','payment/checkout2_request',NULL),
 (11,'VeriSign Link','VeriSign Link',1,'HTTP',11,0,2,0,'verisign_link','N','payment/icon/verisign.gif','payment/verisign_payflow_link_config','payment/verisign_payflow_link_request',NULL),
 (12,'AliPay','AliPay',1,'HTTP',12,0,2,0,'alipay','N','payment/icon/alipay.gif','payment/alipay_config','payment/alipay_request',NULL),
 (13,'NPS(China)','NPS(China)',1,'HTTP',13,0,2,0,'nps','N','payment/icon/nps.gif','payment/nps_config','payment/nps_request',NULL),
 (14,'eWAY. Shared payment','eWAY. Shared payment',1,'HTTP',14,0,2,0,'eway_share','Y','payment/icon/eway.gif','payment/eway_config','payment/eway_sp_request',NULL),
 (15,'NoChex','NoChex',1,'HTTP',15,0,2,0,'nochex','Y','payment/icon/nochex.gif','payment/nochex_config','payment/nochex_request',NULL),
 (16,'WorldPay','WorldPay',1,'HTTP',16,0,2,0,'wordpay','N','payment/icon/worldpay.gif','payment/worldpay_config','payment/worldpay_request',NULL),
 (17,'Paypal China','Paypal China',1,'HTTP',17,0,2,0,'paypal_china','N','payment/icon/paypal_cn.gif','payment/paypal_cn_config','payment/paypal_cn_request',NULL),
 (18,'cncard','cncard',1,'HTTP',18,0,2,0,'cncard','N','payment/icon/cncard.gif','payment/cncard_config','payment/cncard_request',NULL),
 (19,'ChinaBank','ChinaBank',1,'HTTP',19,0,2,0,'china_bank','N','payment/icon/chinabank.gif','payment/chinabank_config','payment/chinabank_request',NULL),
 (20,'Money Bookers','Money Bookers',1,'HTTP',20,0,2,0,'money_bookers','N','payment/icon/moneybookers.gif','payment/money_bookers_config','payment/money_bookers_request',NULL),
 (21,'e-gold','e-gold',1,'HTTP',21,0,2,0,'egold','N','payment/icon/egold.gif','payment/egold_config','payment/egold_request',NULL),
 (22,'YeePay','YeePay',1,'HTTP',22,0,2,0,'yeepay','N','payment/icon/yeepay.png','payment/yeepay_config','payment/yeepay_request',NULL),
 (23,'XPay','XPay',1,'HTTP',23,0,2,0,'xpay','N','payment/icon/xpay.gif','payment/xpay_config','payment/xpay_request',NULL);
 
ALTER TABLE payment_history CHANGE COLUMN paymentGatewayId paymentMethodId INT(11) DEFAULT NULL;
/***删除目录时，广告与目录的关联同样删除***/
alter table PRODUCT_ADVERTISEMENT add constraint FK_P_AD__CAT foreign key (categoryId) references CATEGORY (categoryId) on delete cascade on update restrict;

/** 增加产品附件功能 **/
alter table ORDER_SKU add accessories varchar(512) null;
alter table SHOPPINGCART_ITEM add accessories varchar(512) null;

create table ACCESSORY_GROUP
(
   accessoryGroupId     int not null auto_increment,
   groupName            varchar(32) not null,
   groupDesc            varchar(64),
   primary key (accessoryGroupId)
);

create table ACCESSORY
(
   accessoryId          int not null auto_increment,
   accessoryGroupId     int,
   accessoryName        varchar(32) not null,
   price                numeric(12,2),
   sortOrder            int,
   version              int not null,
   primary key (accessoryId)
);

alter table ACCESSORY add constraint FK_ACC__ACC_GROUP foreign key (accessoryGroupId)
      references ACCESSORY_GROUP (accessoryGroupId) on delete cascade on update restrict;

create table PRODUCT_ACCESSORY
(
   accessoryId          int not null,
   productId            int not null,
   primary key (accessoryId, productId)
);

alter table PRODUCT_ACCESSORY add constraint FK_P_ACC__ACCESSORY foreign key (accessoryId)
      references ACCESSORY (accessoryId) on delete cascade on update restrict;

alter table PRODUCT_ACCESSORY add constraint FK_P_ACC__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;
      

alter table content add contentCode varchar(32);
alter table content modify column contentCode varchar(32) not null;
alter table content add unique key AK_CONTENTCODE (contentCode);

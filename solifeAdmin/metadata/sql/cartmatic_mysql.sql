/*==============================================================*/
/* Database name:  cartmatic                                    */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/12/18 11:49:40                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ACCESSORY                                             */
/*==============================================================*/
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

/*==============================================================*/
/* Table: ACCESSORY_GROUP                                       */
/*==============================================================*/
create table ACCESSORY_GROUP
(
   accessoryGroupId     int not null auto_increment,
   groupName            varchar(32) not null,
   groupCode            varchar(32) not null,
   groupDesc            varchar(64),
   primary key (accessoryGroupId),
   unique key AK_GROUPCODE (groupCode)
);

/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
create table ADDRESS
(
   addressId            int not null auto_increment,
   appuserId            int,
   addressType          smallint not null,
   title                varchar(8) not null,
   firstname            varchar(32) not null,
   lastname             varchar(32) not null,
   telephone            varchar(32) not null,
   zip                  varchar(12),
   fax                  varchar(32),
   companyName          varchar(128),
   isDefaultShippingAddress smallint,
   isDefaultBillingAddress smallint,
   address              varchar(128) not null,
   address2             varchar(128),
   countryName          varchar(64) not null,
   stateName            varchar(64),
   cityName             varchar(64),
   countryId            int not null,
   stateId              int default 0,
   cityId               int default 0,
   userDefinedId        int default 0,
   email                varchar(64),
   primary key (addressId)
);

/*==============================================================*/
/* Table: ADMIN_INFO                                            */
/*==============================================================*/
create table ADMIN_INFO
(
   adminInfoId          int not null auto_increment,
   department           varchar(64),
   pagingSetting        int,
   productViewSetting   smallint not null default 0,
   primary key (adminInfoId)
);

/*==============================================================*/
/* Table: ADVERTISEMENT                                         */
/*==============================================================*/
create table ADVERTISEMENT
(
   advertisementId      int not null auto_increment,
   adPositionTypeId     int,
   advertisementName    varchar(128) not null,
   contentType          smallint not null,
   advertisementDetail  varchar(512),
   mediaPath            varchar(128),
   url                  varchar(255) not null,
   redirectUrl          varchar(255),
   target               varchar(32),
   startPublishTime     datetime,
   endPublishTime       datetime,
   sortOrder            int,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   isInclude            smallint,
   version              int not null default 0,
   primary key (advertisementId)
);

/*==============================================================*/
/* Table: AD_POSITION_TYPE                                      */
/*==============================================================*/
create table AD_POSITION_TYPE
(
   adPositionTypeId     int not null auto_increment,
   storeId              int,
   positionName         varchar(128) not null,
   height               int not null,
   width                int not null,
   displayType          smallint not null,
   status               smallint,
   description          varchar(512),
   templatePath         varchar(128) not null,
   version              int not null default 0,
   primary key (adPositionTypeId),
   unique key AK_POSITIONNAME (positionName, storeId)
);

/*==============================================================*/
/* Table: ALSO_BUY                                              */
/*==============================================================*/
create table ALSO_BUY
(
   alsoBuyId            int not null auto_increment,
   productId            int not null,
   alsoProductId        int not null,
   times                int,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null default 0,
   primary key (alsoBuyId)
);

/*==============================================================*/
/* Table: APP_AUDIT                                             */
/*==============================================================*/
create table APP_AUDIT
(
   appAuditId           int not null auto_increment,
   procUserId           int,
   procObj              varchar(1024),
   actionName           varchar(64) not null,
   procResult           varchar(1024),
   requestUrl           varchar(255),
   procTime             datetime not null,
   primary key (appAuditId)
);

/*==============================================================*/
/* Table: APP_EVENT                                             */
/*==============================================================*/
create table APP_EVENT
(
   appEventId           int not null auto_increment,
   appEvent             blob not null,
   updateTime           datetime not null,
   primary key (appEventId)
);

/*==============================================================*/
/* Table: APP_RESOURCE                                          */
/*==============================================================*/
create table APP_RESOURCE
(
   appResourceId        int not null auto_increment,
   parentId             int,
   resourceName         varchar(32) not null,
   resourceType         smallint not null default 0,
   resourceString       varchar(255) not null,
   resourceDesc         varchar(512),
   resPermission        varchar(255),
   primary key (appResourceId)
);

/*==============================================================*/
/* Table: APP_ROLE                                              */
/*==============================================================*/
create table APP_ROLE
(
   appRoleId            int not null auto_increment,
   roleName             varchar(32) not null,
   roleDetail           varchar(255),
   createTime           datetime,
   updateTime           datetime,
   status               smallint,
   isSystem             smallint,
   version              int not null default 0,
   primary key (appRoleId)
);

/*==============================================================*/
/* Table: APP_USER                                              */
/*==============================================================*/
create table APP_USER
(
   appuserId            int not null auto_increment,
   membershipId         int,
   supplierId           int,
   storeId              int,
   adminInfoId          int,
   username             varchar(32) not null,
   password             varchar(128),
   userType             smallint not null,
   title                varchar(8),
   firstname            varchar(32),
   lastname             varchar(32),
   email                varchar(64) not null,
   fax                  varchar(32),
   telephone            varchar(32),
   preferredLanguage    varchar(8),
   lastLoginTime        datetime,
   status               smallint,
   isLocked             smallint,
   totalPoints          int,
   userPosition         varchar(128),
   passwordHint         varchar(128),
   passwordHintAnswer   varchar(256),
   customerPosition     varchar(128),
   registerTime         datetime,
   registerIpAddress    varchar(64),
   deleted              smallint not null default 0,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   note                 varchar(1024),
   version              int not null default 0,
   primary key (appuserId),
   unique key AK_USERNAME (username, userType),
   unique key AK_EMAIL (email)
);

/*==============================================================*/
/* Table: ATTRIBUTE                                             */
/*==============================================================*/
create table ATTRIBUTE
(
   attributeId          int not null auto_increment,
   attributeName        varchar(64) not null,
   attributeType        smallint not null,
   attributeDataType    int not null,
   attributeUnit        varchar(32),
   defaultValue         varchar(255),
   isRequired           smallint,
   attributeCode        varchar(32) not null,
   description          varchar(255),
   primary key (attributeId),
   key AK_ATT_CODE (attributeCode)
);

/*==============================================================*/
/* Table: BRAND                                                 */
/*==============================================================*/
create table BRAND
(
   brandId              int not null auto_increment,
   brandCode            varchar(32) not null,
   brandName            varchar(64) not null,
   sortOrder            int,
   website              varchar(255),
   logo                 varchar(255),
   version              int not null default 0,
   primary key (brandId),
   unique key AK_Key_2 (brandCode)
);

/*==============================================================*/
/* Table: CARRIER                                               */
/*==============================================================*/
create table CARRIER
(
   carrierId            int not null auto_increment,
   carrierName          varchar(128) not null,
   carrierAddress       varchar(128),
   carrierAddress2      varchar(128),
   linkman              varchar(32),
   telephone            varchar(32),
   fax                  varchar(32),
   email                varchar(64),
   zip                  varchar(12),
   status               smallint,
   deleted              smallint not null default 0,
   version              int not null default 0,
   logo                 varchar(128),
   primary key (carrierId)
);

/*==============================================================*/
/* Table: CATALOG                                               */
/*==============================================================*/
create table CATALOG
(
   catalogId            int not null auto_increment,
   categoryId           int not null,
   availabilityRule     smallint,
   name                 varchar(32) not null,
   code                 varchar(32) not null,
   description          text,
   isVirtual            smallint not null,
   status               smallint not null,
   version              int not null default 0,
   primary key (catalogId),
   unique key AK_Key_Code (code)
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   categoryId           int not null auto_increment,
   parentId             int,
   catalogId            int,
   storeId              int,
   categoryType         smallint not null default 1,
   categoryName         varchar(128) not null,
   categoryDescription  text,
   categoryCode         varchar(32) not null,
   metaKeyword          varchar(256),
   metaDescription      varchar(256),
   templatePath         varchar(128),
   categoryPath         varchar(32),
   imageUrl             varchar(255),
   linkUrl              varchar(255),
   isLinkCategory       smallint,
   linkedCategoryId     int,
   sortOrder            int not null,
   status               smallint not null,
   createBy             int,
   updateBy             int,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null default 0,
   primary key (categoryId),
   unique key AK_PRODUCT_CODE (categoryCode, catalogId, storeId)
);

/*==============================================================*/
/* Table: CATEGORY_ATTR_VALUE                                   */
/*==============================================================*/
create table CATEGORY_ATTR_VALUE
(
   categoryAttrValueId  int not null auto_increment,
   attributeId          int,
   categoryId           int,
   shortTextValue       varchar(255),
   longTextValue        text,
   intValue             int,
   decimalValue         numeric(12,2),
   booleanValue         smallint,
   dateValue            datetime,
   attributeDataType    int not null,
   primary key (categoryAttrValueId)
);

/*==============================================================*/
/* Table: CATEGORY_SHIPPING_RATE                                */
/*==============================================================*/
create table CATEGORY_SHIPPING_RATE
(
   categoryShippingMethodId int not null auto_increment,
   categoryProductPath  varchar(32) not null,
   isCategory           smallint,
   version              int not null default 0,
   primary key (categoryShippingMethodId)
);

/*==============================================================*/
/* Table: CONTENT                                               */
/*==============================================================*/
create table CONTENT
(
   contentId            int not null auto_increment,
   categoryId           int,
   storeId              int,
   contentTitle         varchar(128) not null,
   contentCode          varchar(32) not null,
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
   primary key (contentId),
   unique key AK_CONTENTCODE (contentCode, storeId)
);

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

/*==============================================================*/
/* Table: CONTENT_TYPE                                          */
/*==============================================================*/
create table CONTENT_TYPE
(
   contentTypeId        int not null auto_increment,
   contentTypeName      varchar(64) not null,
   contentTypeCode      varchar(32) not null,
   type                 smallint,
   isUse                smallint,
   url                  varchar(256),
   decoratorPath        varchar(256),
   sortOrder            int,
   isShowTree           smallint,
   parentId             int,
   primary key (contentTypeId)
);

/*==============================================================*/
/* Table: COOKIE                                                */
/*==============================================================*/
create table COOKIE
(
   cookieId             int not null auto_increment,
   cookieUid            varchar(64) not null,
   shoppingCartId       int,
   visitTime            datetime,
   version              int not null default 0,
   primary key (cookieId),
   unique key AK_Key_2 (cookieUid)
);

/*==============================================================*/
/* Table: COUPON                                                */
/*==============================================================*/
create table COUPON
(
   couponId             int not null auto_increment,
   promoRuleId          int,
   couponNo             varchar(16) not null,
   isSent               smallint,
   remainedTimes        int,
   status               smallint,
   version              int not null default 0,
   primary key (couponId),
   unique key AK_COUPONNO (couponNo)
);

/*==============================================================*/
/* Table: CURRENCY                                              */
/*==============================================================*/
create table CURRENCY
(
   currencyId           int not null auto_increment,
   currencyCode         varchar(8) not null,
   currencyName         varchar(32) not null,
   isDefault            smallint,
   status               smallint,
   sortOrder            int,
   exchangeRate         numeric(12,4),
   currencySymbol       varchar(16) not null,
   version              int not null default 0,
   primary key (currencyId)
);

/*==============================================================*/
/* Table: CUSTOMER_ATTR_VALUE                                   */
/*==============================================================*/
create table CUSTOMER_ATTR_VALUE
(
   customerAttrValueId  int not null auto_increment,
   attributeId          int,
   customerId           int,
   shortTextValue       varchar(255),
   longTextValue        text,
   intValue             int,
   decimalValue         numeric(12,2),
   booleanValue         smallint,
   dateValue            datetime,
   attributeDataType    int not null,
   primary key (customerAttrValueId)
);

/*==============================================================*/
/* Table: FAVORITE                                              */
/*==============================================================*/
create table FAVORITE
(
   favoriteId           int not null auto_increment,
   customerId           int,
   favoriteTitle        varchar(128) not null,
   url                  varchar(255) not null,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (favoriteId)
);

/*==============================================================*/
/* Table: FEEDBACK                                              */
/*==============================================================*/
create table FEEDBACK
(
   feedbackId           int not null auto_increment,
   productId            int,
   appuserId            int,
   title                varchar(8),
   firstName            varchar(32),
   lastName             varchar(32),
   subject              varchar(128),
   content              varchar(512),
   status               smallint,
   replyType            smallint,
   feedbackType         smallint,
   priority             int,
   email                varchar(64),
   telephone            varchar(32),
   fax                  varchar(32),
   threadId             int,
   updateTime           datetime,
   createTime           datetime,
   givenShopPointAction smallint,
   version              int not null default 0,
   primary key (feedbackId)
);

/*==============================================================*/
/* Table: GIFT_CERTIFICATE                                      */
/*==============================================================*/
create table GIFT_CERTIFICATE
(
   giftCertificateId    int not null auto_increment,
   giftCertificateNo    varchar(32) not null,
   orderNo              varchar(20),
   purchaser            varchar(64) not null,
   recipient            varchar(64) not null,
   isSentByEmail        smallint not null,
   recipientEmail       varchar(64),
   recipientFullname    varchar(32),
   recipientAddress     varchar(128),
   recipientContryId    int,
   recipientState       varchar(64),
   recipientCity        varchar(64),
   recipientZip         varchar(12),
   recipientPhone       varchar(32),
   message              varchar(512),
   giftCertAmt          numeric(12,2) not null,
   remainedAmt          numeric(12,2),
   expireTime           datetime,
   status               smallint not null,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (giftCertificateId),
   unique key AK_Key_2 (giftCertificateNo)
);

/*==============================================================*/
/* Table: INDEX_ITEM                                            */
/*==============================================================*/
create table INDEX_ITEM
(
   indexItemId          int not null auto_increment,
   contentUrl           varchar(255) not null,
   productId            int not null,
   subject              smallint,
   contentMark          varchar(64),
   requestType          smallint,
   createTime           datetime,
   updateTime           datetime,
   status               smallint,
   version              int not null default 0,
   primary key (indexItemId),
   unique key AK_Key_2 (contentUrl)
);

/*==============================================================*/
/* Table: INVENTORY                                             */
/*==============================================================*/
create table INVENTORY
(
   inventoryId          int not null auto_increment,
   productSkuId         int,
   quantityOnHand       int not null,
   allocatedQuantity    int not null,
   reservedQuantity     int not null,
   expectedRestockDate  datetime,
   reorderQuantity      int,
   preOrBackOrderedQty  int not null,
   reorderMinimnm       int not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null,
   primary key (inventoryId)
);

/*==============================================================*/
/* Table: INVENTORY_AUDIT                                       */
/*==============================================================*/
create table INVENTORY_AUDIT
(
   inventoryAuditId     int not null auto_increment,
   inventoryId          int,
   productSkuId         int not null,
   quantity             int not null,
   quantityOnHand       int not null,
   allocatedQuantity    int not null,
   eventType            smallint not null,
   reason               varchar(128),
   comment              varchar(1024),
   orderId              int,
   eventHandler         varchar(128) not null,
   createTime           datetime not null,
   primary key (inventoryAuditId)
);

/*==============================================================*/
/* Table: MEMBERSHIP                                            */
/*==============================================================*/
create table MEMBERSHIP
(
   membershipId         int not null auto_increment,
   membershipName       varchar(64) not null,
   membershipDetail     varchar(1024),
   membershipLevel      int not null,
   upgradeShopPoint     int,
   status               smallint,
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (membershipId),
   unique key AK_Key_2 (membershipName),
   unique key AK_Key_3 (membershipLevel)
);

/*==============================================================*/
/* Table: ORDER_ADDRESS                                         */
/*==============================================================*/
create table ORDER_ADDRESS
(
   orderAddressId       int not null auto_increment,
   title                varchar(8) not null,
   firstname            varchar(32) not null,
   lastname             varchar(32),
   phoneNumber          varchar(32),
   faxNumber            varchar(32),
   postalcode           varchar(12),
   country              varchar(64) not null,
   state                varchar(64),
   city                 varchar(64),
   address1             varchar(128) not null,
   address2             varchar(128),
   primary key (orderAddressId)
);

/*==============================================================*/
/* Table: ORDER_AUDIT                                           */
/*==============================================================*/
create table ORDER_AUDIT
(
   orderAuditId         int not null auto_increment,
   salesOrderId         int,
   addedBy              varchar(64) not null,
   createTime           datetime not null,
   transactionType      varchar(32) not null,
   detail               varchar(1024),
   primary key (orderAuditId)
);

/*==============================================================*/
/* Table: ORDER_ITEM_ATTRIBUTE                                  */
/*==============================================================*/
create table ORDER_ITEM_ATTRIBUTE
(
   orderItemAttributeId int not null auto_increment,
   attributeName        varchar(64) not null,
   attributeOptionName  varchar(64) not null,
   attributeValue       varchar(128),
   version              int not null default 0,
   primary key (orderItemAttributeId)
);

/*==============================================================*/
/* Table: ORDER_ITEM_PROMOTION                                  */
/*==============================================================*/
create table ORDER_ITEM_PROMOTION
(
   orderItemPromotionId int not null auto_increment,
   promotionId          int,
   promotionTitle       varchar(128),
   promotionDescription text,
   saveMoney            numeric(12,2),
   validFlag            smallint,
   isUnited             smallint,
   version              int not null default 0,
   primary key (orderItemPromotionId)
);

/*==============================================================*/
/* Table: ORDER_MESSAGE                                         */
/*==============================================================*/
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

/*==============================================================*/
/* Table: ORDER_PAYMENT                                         */
/*==============================================================*/
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

/*==============================================================*/
/* Table: ORDER_PICK_LIST                                       */
/*==============================================================*/
create table ORDER_PICK_LIST
(
   orderPickListId      int not null auto_increment,
   createTime           datetime not null,
   updateTime           datetime not null,
   createdBy            int not null,
   isActive             smallint not null default 0,
   primary key (orderPickListId)
);

/*==============================================================*/
/* Table: ORDER_PROMOTION                                       */
/*==============================================================*/
create table ORDER_PROMOTION
(
   orderPromotionId     int not null auto_increment,
   salesOrderId         int,
   promoRuleId          int not null,
   couponNo             varchar(16),
   promotionName        varchar(64) not null,
   primary key (orderPromotionId)
);

/*==============================================================*/
/* Table: ORDER_RETURN                                          */
/*==============================================================*/
create table ORDER_RETURN
(
   orderReturnId        int not null auto_increment,
   rmaNo                varchar(32) not null,
   salesOrderId         int not null,
   exchangeOrderId      int,
   receivedBy           int,
   returnType           smallint not null,
   status               smallint not null,
   itemSubTotal         numeric(12,2) not null,
   shippingCost         numeric(12,2) not null,
   discountAmount       numeric(12,2) not null,
   itemTax              numeric(12,2) not null,
   shippingTax          numeric(12,2) not null,
   isPhysicalReturn     smallint not null,
   lessRestockAmount    numeric(12,2),
   note                 varchar(1024),
   createBy             int not null,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (orderReturnId),
   key AK_Key_2 (rmaNo)
);

/*==============================================================*/
/* Table: ORDER_RETURN_SKU                                      */
/*==============================================================*/
create table ORDER_RETURN_SKU
(
   orderReturnSkuId     int not null auto_increment,
   orderReturnId        int not null,
   orderSkuId           int,
   quantity             int not null,
   returnAmount         numeric(12,2) not null,
   receivedQuantity     int not null,
   reasonType           smallint not null,
   receivedStatus       smallint,
   primary key (orderReturnSkuId)
);

/*==============================================================*/
/* Table: ORDER_SETTLEMENT                                      */
/*==============================================================*/
create table ORDER_SETTLEMENT
(
   orderSettlementId    int not null auto_increment,
   shipmentId           int not null,
   shipmentNo           varchar(32) not null,
   orderId              int not null,
   orderNo              varchar(20) not null,
   carrierName          varchar(128) not null,
   trackingNo           varchar(128) not null,
   originalTotal        numeric(12,2) not null,
   settlementAmount     numeric(12,2) not null,
   isComplete           smallint not null default 0,
   addedBy              varchar(32),
   primary key (orderSettlementId),
   key AK_SHIPMENT_NO (shipmentNo)
);

/*==============================================================*/
/* Table: ORDER_SHIPMENT                                        */
/*==============================================================*/
create table ORDER_SHIPMENT
(
   orderShipmentId      int not null auto_increment,
   salesOrderId         int not null,
   shippingAddressId    int,
   orderPickListId      int,
   shippingRateId       int,
   shipmentNo           varchar(32) not null,
   trackingNo           varchar(128),
   carrierName          varchar(128),
   itemSubTotal         numeric(12,2) not null,
   itemTax              numeric(12,2) not null,
   shippingTax          numeric(12,2) not null,
   shippingCost         numeric(12,2) not null,
   shippingCostPaid     numeric(12,2),
   discountAmount       numeric(12,2) not null,
   wrapName             varchar(64),
   wrapUnitPrice        numeric(12,2),
   wrapNote             varchar(512),
   hasRobotReviewed     smallint not null default 0,
   isConfirmedByRobot   smallint,
   status               smallint,
   deliverTime          datetime,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null default 0,
   primary key (orderShipmentId),
   key AK_SHIP_NO (shipmentNo)
);

/*==============================================================*/
/* Table: ORDER_SKU                                             */
/*==============================================================*/
create table ORDER_SKU
(
   orderSkuId           int not null auto_increment,
   giftCertificateId    int,
   productSkuId         int,
   orderShipmentId      int,
   productId            int,
   productName          varchar(128),
   productSkuCode       varchar(32),
   displaySkuOptions    varchar(255),
   itemType             smallint not null,
   quantity             int not null,
   costPrice            numeric(12,2),
   price                numeric(12,2) not null,
   discountPrice        numeric(12,2),
   isOnSale             smallint not null,
   isWholesale          smallint not null,
   tax                  numeric(12,2),
   taxName              varchar(64),
   itemDiscountAmount   numeric(12,2),
   weight               numeric(12,2),
   allocatedQuantity    int,
   allocatedPreQty      int,
   accessories          varchar(512),
   version              int not null default 0,
   primary key (orderSkuId)
);

/*==============================================================*/
/* Table: PATCH_EXECUTE_HISTORY                                 */
/*==============================================================*/
create table PATCH_EXECUTE_HISTORY
(
   patchExecuteId       int unsigned not null auto_increment,
   transactionUid       varchar(32),
   fileName             varchar(64) not null default '',
   fileType             varchar(64) not null default '',
   flag                 smallint unsigned not null default 0,
   resultDescription    varchar(256),
   fromVersion          varchar(32) not null default '',
   toVersion            varchar(32) not null default '',
   executeTime          datetime,
   version              int unsigned not null,
   primary key (patchExecuteId)
);

/*==============================================================*/
/* Table: PAYMENT_HISTORY                                       */
/*==============================================================*/
create table PAYMENT_HISTORY
(
   paymentHistoryId     int not null auto_increment,
   orderNo              varchar(20) not null,
   flag                 smallint not null,
   flowNo               varchar(64),
   amount               numeric(12,2),
   errorMessage         varchar(256),
   errorCode            varchar(32),
   remoteIp             varchar(32),
   receiveData          varchar(2048),
   paymentMethodId      int,
   isBrowsed            smallint default 0,
   createTime           datetime,
   version              int not null default 0,
   primary key (paymentHistoryId)
);

/*==============================================================*/
/* Table: PAYMENT_METHOD                                        */
/*==============================================================*/
create table PAYMENT_METHOD
(
   paymentMethodId      int not null auto_increment,
   paymentMethodName    varchar(64) not null,
   paymentMethodCode    varchar(32) not null,
   paymentMethodDetail  varchar(2048),
   paymentMethodType    smallint,
   paymentMethodIcon    varchar(255),
   protocol             varchar(8),
   processorFile        varchar(255),
   configFile           varchar(256),
   configData           blob,
   testModel            char(10),
   isCod                smallint not null default 0,
   sortOrder            int,
   status               smallint,
   version              int not null default 0,
   primary key (paymentMethodId),
   key AK_Key_2 (paymentMethodCode)
);

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
create table PRODUCT
(
   productId            int not null auto_increment,
   productTypeId        int,
   brandId              int,
   productDescriptionId int,
   defaultProductSkuId  int,
   membershipId         int,
   supplierId           int,
   defaultSupplierProductId int,
   productName          varchar(128) not null,
   productCode          varchar(32) not null,
   minOrderQuantity     int,
   url                  varchar(255),
   title                varchar(128),
   metaKeyword          varchar(256),
   metaDescription      varchar(256),
   templatePath         varchar(128),
   productKind          smallint not null default 1,
   expectedReleaseDate  datetime,
   availabilityRule     varchar(255) not null,
   preOrBackOrderLimit  int,
   planStartTime        datetime,
   planEndTime          datetime,
   publishTime          datetime,
   isAllowReview        smallint not null,
   extra1               varchar(255),
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (productId),
   unique key AK_Key_2 (productCode)
);

/*==============================================================*/
/* Index: IX_PROD_STATUS                                        */
/*==============================================================*/
create index IX_PROD_STATUS on PRODUCT
(
   status
);

/*==============================================================*/
/* Table: PRODUCT_ACCESSORY                                     */
/*==============================================================*/
create table PRODUCT_ACCESSORY
(
   accessoryId          int not null,
   productId            int not null,
   primary key (accessoryId, productId)
);

/*==============================================================*/
/* Table: PRODUCT_ADVERTISEMENT                                 */
/*==============================================================*/
create table PRODUCT_ADVERTISEMENT
(
   productAdvertisementId int not null auto_increment,
   advertisementId      int not null,
   categoryId           int not null,
   version              int not null default 0,
   primary key (productAdvertisementId)
);

/*==============================================================*/
/* Table: PRODUCT_ATTR_VALUE                                    */
/*==============================================================*/
create table PRODUCT_ATTR_VALUE
(
   productAttrValueId   int not null auto_increment,
   attributeId          int,
   productId            int,
   productAttGroupItemId int,
   shortTextValue       varchar(255),
   longTextValue        text,
   intValue             int,
   decimalValue         numeric(12,2),
   booleanValue         smallint,
   dateValue            datetime,
   attributeDataType    int not null,
   primary key (productAttrValueId)
);

/*==============================================================*/
/* Table: PRODUCT_ATT_GROUP                                     */
/*==============================================================*/
create table PRODUCT_ATT_GROUP
(
   productAttGroupId    int not null auto_increment,
   productTypeId        int,
   productAttGroupName  varchar(64) not null,
   sortOrder            int,
   primary key (productAttGroupId)
);

/*==============================================================*/
/* Table: PRODUCT_ATT_GROUP_ITEM                                */
/*==============================================================*/
create table PRODUCT_ATT_GROUP_ITEM
(
   productAttGroupItemId int not null auto_increment,
   attributeId          int not null,
   productAttGroupId    int not null,
   productTypeId        int not null,
   sortOrder            int,
   primary key (productAttGroupItemId)
);

/*==============================================================*/
/* Table: PRODUCT_CATEGORY                                      */
/*==============================================================*/
create table PRODUCT_CATEGORY
(
   productCategoryId    int not null auto_increment,
   categoryId           int not null,
   productId            int not null,
   isMainCategory       smallint,
   categoryPath         varchar(32) not null,
   sortOrder            int,
   primary key (productCategoryId),
   unique key AK_KEY_2_PRO_CATE_2 (categoryPath)
);

/*==============================================================*/
/* Table: PRODUCT_DESCRIPTION                                   */
/*==============================================================*/
create table PRODUCT_DESCRIPTION
(
   productDescriptionId int not null auto_increment,
   shortDescription     varchar(512),
   fullDescription      text,
   primary key (productDescriptionId)
);

/*==============================================================*/
/* Table: PRODUCT_MEDIA                                         */
/*==============================================================*/
create table PRODUCT_MEDIA
(
   productMediaId       int not null auto_increment,
   productId            int,
   mediaDescription     varchar(256),
   mediaType            smallint,
   mediaUrl             varchar(255),
   sortOrder            int,
   primary key (productMediaId)
);

/*==============================================================*/
/* Table: PRODUCT_PACKAGE_ITEM                                  */
/*==============================================================*/
create table PRODUCT_PACKAGE_ITEM
(
   productPackageItemId int not null auto_increment,
   packageSkuId         int,
   itemSkuId            int,
   sortOrder            int,
   quantity             int not null default 1,
   primary key (productPackageItemId)
);

/*==============================================================*/
/* Table: PRODUCT_RATE_ITEM                                     */
/*==============================================================*/
create table PRODUCT_RATE_ITEM
(
   productRateItemId    int not null auto_increment,
   productTypeId        int not null,
   rateName             varchar(64) not null,
   sortOrder            int,
   primary key (productRateItemId)
);

/*==============================================================*/
/* Table: PRODUCT_RATE_VALUE                                    */
/*==============================================================*/
create table PRODUCT_RATE_VALUE
(
   productRateValueId   int not null auto_increment,
   productRateItemId    int not null,
   productReviewId      int not null,
   rateValue            int not null,
   primary key (productRateValueId)
);

/*==============================================================*/
/* Table: PRODUCT_REVIEW                                        */
/*==============================================================*/
create table PRODUCT_REVIEW
(
   productReviewId      int not null auto_increment,
   productId            int,
   appuserId            int,
   reviewId             int,
   storeId              int,
   customerName         varchar(64),
   subject              varchar(128),
   message              varchar(2048) not null,
   remoteIp             varchar(32),
   givenPoint           int,
   givenTime            datetime,
   givenBy              int,
   rate                 int,
   usefulCount          int,
   unusefulCount        int,
   hasReply             smallint,
   createTime           datetime,
   updateTime           datetime,
   status               smallint not null,
   primary key (productReviewId)
);

/*==============================================================*/
/* Table: PRODUCT_SKU                                           */
/*==============================================================*/
create table PRODUCT_SKU
(
   productSkuId         int not null auto_increment,
   productId            int not null,
   productSkuCode       varchar(32) not null,
   weight               numeric(12,2),
   length               numeric(12,2),
   width                numeric(12,2),
   height               numeric(12,2),
   image                varchar(255),
   skuKind              smallint not null,
   price                numeric(12,2) not null,
   salePrice            numeric(12,2),
   listPrice            numeric(12,2),
   costPrice            numeric(12,2),
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   version              int not null default 0,
   primary key (productSkuId),
   unique key AK_SKUCODE (productSkuCode)
);

/*==============================================================*/
/* Table: PRODUCT_SKU_OPTION_VALUE                              */
/*==============================================================*/
create table PRODUCT_SKU_OPTION_VALUE
(
   productSkuOptionValueId int not null auto_increment,
   productSkuId         int,
   skuOptionValueId     int,
   primary key (productSkuOptionValueId)
);

/*==============================================================*/
/* Table: PRODUCT_STAT                                          */
/*==============================================================*/
create table PRODUCT_STAT
(
   productStatId        int not null auto_increment,
   productId            int,
   storeId              int,
   reviewCount          int not null default 0,
   averageRate          numeric(12,2) not null default 0,
   buyCount             int not null default 0,
   version              int not null default 0,
   primary key (productStatId)
);

/*==============================================================*/
/* Table: PRODUCT_TYPE                                          */
/*==============================================================*/
create table PRODUCT_TYPE
(
   productTypeId        int not null auto_increment,
   productTypeName      varchar(32) not null,
   productTypeDescription varchar(128),
   templatePath         varchar(128),
   minOrderQuantity     int,
   status               smallint not null,
   version              int not null default 0,
   primary key (productTypeId)
);

/*==============================================================*/
/* Table: PRODUCT_TYPE_SKU_OPTION                               */
/*==============================================================*/
create table PRODUCT_TYPE_SKU_OPTION
(
   productTypeSkuOptionId int not null auto_increment,
   skuOptionId          int,
   productTypeId        int,
   sortOrder            int,
   primary key (productTypeSkuOptionId)
);

/*==============================================================*/
/* Table: PROMO_RULE                                            */
/*==============================================================*/
create table PROMO_RULE
(
   promoRuleId          int not null auto_increment,
   catalogId            int,
   storeId              int,
   name                 varchar(64) not null,
   description          text,
   priority             int not null,
   enableDiscountAgain  smallint not null,
   eligibilityOperator  smallint default 1,
   conditionOperator    smallint not null default 1,
   startTime            datetime not null,
   endTime              datetime,
   promoType            varchar(32) not null,
   availableCount       int,
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (promoRuleId)
);

/*==============================================================*/
/* Table: PROMO_RULE_ELEMENT                                    */
/*==============================================================*/
create table PROMO_RULE_ELEMENT
(
   promoRuleElementId   int not null auto_increment,
   promoRuleId          int not null,
   kind                 varchar(16) not null,
   type                 varchar(64) not null,
   sort                 int,
   primary key (promoRuleElementId)
);

/*==============================================================*/
/* Table: PROMO_RULE_PARAMETER                                  */
/*==============================================================*/
create table PROMO_RULE_PARAMETER
(
   promoRuleParameterId int not null auto_increment,
   promoRuleElementId   int not null,
   paramName            varchar(32) not null,
   paramValue           varchar(64) not null,
   isExcluded           smallint not null,
   excludedType         varchar(16),
   primary key (promoRuleParameterId)
);

/*==============================================================*/
/* Table: PURCHASE_ORDER                                        */
/*==============================================================*/
create table PURCHASE_ORDER
(
   purchaseOrderId      int not null auto_increment,
   supplierId           int,
   supplierName         varchar(32) not null,
   countItems           int not null,
   purchaseOrderNo      varchar(20) not null,
   trackingNo           varchar(128),
   poStatus             smallint not null,
   poResult             smallint not null,
   createBy             int,
   updateBy             int,
   updateTime           datetime not null,
   createTime           datetime not null,
   comments             varchar(512),
   version              int not null default 0,
   primary key (purchaseOrderId)
);

/*==============================================================*/
/* Table: PURCHASE_ORDER_ITEM                                   */
/*==============================================================*/
create table PURCHASE_ORDER_ITEM
(
   purchaseOrderItemId  int not null auto_increment,
   purchaseOrderId      int,
   orderSkuId           int,
   productName          varchar(128) not null,
   supplierProductName  varchar(256),
   skuDisplay           varchar(128),
   accessories          varchar(512),
   purchaseQuantity     int not null,
   passedQuantity       int not null,
   purchasePrice        numeric(12,2) not null,
   status               smallint not null,
   primary key (purchaseOrderItemId)
);

/*==============================================================*/
/* Table: RECOMMENDED_PRODUCT                                   */
/*==============================================================*/
create table RECOMMENDED_PRODUCT
(
   recommendedProductId int not null auto_increment,
   productId            int not null,
   recommendedTypeId    int not null,
   sourceId             int,
   status               smallint,
   sortOrder            int,
   locationType         smallint,
   startTime            datetime,
   expireTime           datetime,
   version              int not null default 0,
   primary key (recommendedProductId)
);

/*==============================================================*/
/* Table: RECOMMENDED_TYPE                                      */
/*==============================================================*/
create table RECOMMENDED_TYPE
(
   recommendedTypeId    int not null auto_increment,
   recommendTitle       varchar(128),
   typeName             varchar(64) not null,
   ruleCode             int,
   autoEval             smallint not null,
   status               smallint not null,
   templatePath         varchar(128),
   isSystem             smallint not null,
   isApplyToProduct     smallint,
   isApplyToCategory    smallint,
   version              int not null default 0,
   primary key (recommendedTypeId),
   unique key AK_Key_2 (typeName)
);

/*==============================================================*/
/* Table: REGION                                                */
/*==============================================================*/
create table REGION
(
   regionId             int not null auto_increment,
   regionCode           varchar(32) not null,
   regionType           smallint not null,
   regionName           varchar(64) not null,
   zipCode              varchar(12),
   telephoneCode        varchar(8),
   regionIcon           varchar(256),
   priority             int,
   parentRegionId       int not null,
   description          varchar(1024),
   status               smallint not null,
   createBy             int,
   createTime           datetime,
   updateBy             int,
   updateTime           datetime,
   version              int not null default 0,
   primary key (regionId),
   unique key AK_Key_2 (regionCode, regionType)
);

/*==============================================================*/
/* Index: IX_REGION_PARENTID                                    */
/*==============================================================*/
create index IX_REGION_PARENTID on REGION
(
   parentRegionId,
   regionType
);

/*==============================================================*/
/* Table: REGION_ITEM                                           */
/*==============================================================*/
create table REGION_ITEM
(
   regionItemId         int not null auto_increment,
   regionId             int,
   version              int not null default 0,
   itemId               int,
   primary key (regionItemId),
   unique key AK_Key_REG_ITEM (regionId, itemId)
);

/*==============================================================*/
/* Table: REVIEW_VOTE                                           */
/*==============================================================*/
create table REVIEW_VOTE
(
   reviewVoteId         int not null auto_increment,
   productReviewId      int,
   customerId           int,
   primary key (reviewVoteId)
);

/*==============================================================*/
/* Table: ROLE_RES                                              */
/*==============================================================*/
create table ROLE_RES
(
   roleResId            int not null auto_increment,
   appResourceId        int not null,
   appRoleId            int not null,
   permissionMask       int,
   primary key (roleResId)
);

/*==============================================================*/
/* Table: SALESORDER_ATTR_VALUE                                 */
/*==============================================================*/
create table SALESORDER_ATTR_VALUE
(
   salesorderAttrValueId int not null auto_increment,
   attributeId          int,
   orderId              int,
   shortTextValue       varchar(255),
   longTextValue        text,
   intValue             int,
   decimalValue         numeric(12,2),
   booleanValue         smallint,
   dateValue            datetime,
   attributeDataType    int not null,
   primary key (salesorderAttrValueId)
);

/*==============================================================*/
/* Table: SALES_ORDER                                           */
/*==============================================================*/
create table SALES_ORDER
(
   salesOrderId         int not null auto_increment,
   billingAddressId     int,
   storeId              int,
   customerId           int,
   orderNo              varchar(20) not null,
   membershipId         int,
   customerTitle        varchar(8) not null,
   customerFirstname    varchar(32) not null,
   customerLastname     varchar(32),
   customerEmail        varchar(64) not null,
   shopPoint            int,
   totalAmount          numeric(12,2) not null,
   paidAmount           numeric(12,2) not null,
   orderStatus          smallint not null,
   paymentStatus        smallint not null,
   isExchangeOrder      smallint,
   originalOrderId      int,
   isCod                smallint not null,
   paymentMethodId      int,
   invoiceTitle         varchar(128),
   hasInvoice           smallint not null,
   gainedPoint          int,
   gainedCouponTypeId   int,
   ipAddress            varchar(64),
   isOnHold             smallint not null,
   isHoldByCustomer     smallint,
   isLocked             smallint not null,
   lockedBy             int,
   hasProblem           smallint not null default 0,
   createTime           datetime not null,
   updateTime           datetime not null,
   updateBy             int,
   version              int not null default 0,
   primary key (salesOrderId),
   unique key AK_Key_2 (orderNo)
);

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

/*==============================================================*/
/* Table: SEARCH_KEYWORD                                        */
/*==============================================================*/
create table SEARCH_KEYWORD
(
   searchKeywordId      int not null auto_increment,
   searchKeyword        varchar(128),
   times                int,
   version              int not null default 0,
   primary key (searchKeywordId)
);

/*==============================================================*/
/* Table: SEE_ALSO                                              */
/*==============================================================*/
create table SEE_ALSO
(
   seeAlsoId            int not null auto_increment,
   isSystem             smallint not null,
   title                varchar(64) not null,
   url                  varchar(255) not null,
   currentUrl           varchar(255) not null,
   version              int not null default 0,
   primary key (seeAlsoId)
);

/*==============================================================*/
/* Table: SHIPPING_METHOD                                       */
/*==============================================================*/
create table SHIPPING_METHOD
(
   shippingMethodId     int not null auto_increment,
   carrierId            int,
   shippingMethodName   varchar(64) not null,
   shippingMethodDetail varchar(1024),
   isDomestic           smallint,
   deliveryTime         varchar(256),
   status               smallint,
   isCod                smallint,
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (shippingMethodId)
);

/*==============================================================*/
/* Table: SHIPPING_RATE                                         */
/*==============================================================*/
create table SHIPPING_RATE
(
   shippingRateId       int not null auto_increment,
   shippingMethodId     int,
   regionId             int,
   baseOn               int not null,
   isFlat               smallint not null,
   isRoundUpValue       smallint,
   basePrice            numeric(12,2),
   maxWeight            numeric(12,2),
   maxVolume            numeric(12,2),
   baseWeight           numeric(12,2),
   baseVolume           numeric(12,2),
   weightPerRate        numeric(12,2),
   volumePerRate        numeric(12,2),
   volumePerFee         numeric(12,2),
   increaseUnit         numeric(12,2),
   metricUnitCode       varchar(32),
   maxItem              int,
   itemPerRate          numeric(12,2),
   description          varchar(1024),
   deleted              smallint not null default 0,
   version              int not null default 0,
   minWeight            numeric(12,2) default 0,
   primary key (shippingRateId)
);

/*==============================================================*/
/* Table: SHOPPINGCART                                          */
/*==============================================================*/
create table SHOPPINGCART
(
   shoppingcartId       int not null auto_increment,
   storeId              int,
   Uuid                 varchar(40) not null,
   customerId           int,
   usedCouponNo         varchar(16),
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
   primary key (shoppingcartId)
);

/*==============================================================*/
/* Index: I_UUID                                                */
/*==============================================================*/
create index I_UUID on SHOPPINGCART
(
   Uuid
);

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM                                     */
/*==============================================================*/
create table SHOPPINGCART_ITEM
(
   shoppingcartItemId   int not null auto_increment,
   parentId             int,
   shoppingcartId       int not null,
   productSkuId         int,
   skuType              smallint,
   itemType             smallint not null,
   price                numeric(12,2) not null,
   discountPrice        numeric(12,2),
   quantity             int not null,
   itemDiscountAmount   numeric(12,2),
   isWholesale          smallint not null,
   isOnSale             smallint not null,
   isSaved              smallint not null,
   addTime              datetime not null,
   accessories          varchar(512),
   primary key (shoppingcartItemId)
);

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

/*==============================================================*/
/* Table: SHOP_POINT                                            */
/*==============================================================*/
create table SHOP_POINT
(
   shopPointId          int not null auto_increment,
   customerId           int,
   total                int not null,
   gainedTotal          int not null,
   usedTotal            int not null,
   updateTime           datetime,
   primary key (shopPointId)
);

/*==============================================================*/
/* Table: SHOP_POINT_HISTORY                                    */
/*==============================================================*/
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

/*==============================================================*/
/* Table: SKU_OPTION                                            */
/*==============================================================*/
create table SKU_OPTION
(
   skuOptionId          int not null auto_increment,
   skuOptionName        varchar(64) not null,
   skuOptionCode        varchar(32) not null,
   status               smallint not null,
   version              int not null default 0,
   primary key (skuOptionId)
);

/*==============================================================*/
/* Table: SKU_OPTION_VALUE                                      */
/*==============================================================*/
create table SKU_OPTION_VALUE
(
   skuOptionValueId     int not null auto_increment,
   skuOptionId          int,
   skuOptionValue       varchar(128) not null,
   skuOptionValueName   varchar(64) not null,
   skuOptionValueType   smallint not null,
   sortOrder            int,
   status               smallint not null,
   version              int not null default 0,
   primary key (skuOptionValueId)
);

/*==============================================================*/
/* Table: SORT_ORDER                                            */
/*==============================================================*/
create table SORT_ORDER
(
   sortOrderId          int not null auto_increment,
   sortOrderCode        varchar(32) not null,
   maxOrderValue        int not null,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (sortOrderId),
   key AK_Key_2 (sortOrderCode)
);

/*==============================================================*/
/* Index: IX_SORT_ORDER_CODE                                    */
/*==============================================================*/
create index IX_SORT_ORDER_CODE on SORT_ORDER
(
   sortOrderCode
);

/*==============================================================*/
/* Table: STORE                                                 */
/*==============================================================*/
create table STORE
(
   storeId              int not null auto_increment,
   catalogId            int,
   categoryId           int not null,
   name                 varchar(32) not null,
   code                 varchar(32) not null,
   siteUrl              varchar(255) not null,
   description          text,
   status               smallint not null,
   version              int not null default 0,
   primary key (storeId),
   unique key AK_code (code)
);

/*==============================================================*/
/* Table: STORE_PAYMENT_METHOD                                  */
/*==============================================================*/
create table STORE_PAYMENT_METHOD
(
   storeId              int not null,
   paymentMethodId      int not null,
   primary key (storeId, paymentMethodId)
);

/*==============================================================*/
/* Table: STORE_SHIPPING_METHOD                                 */
/*==============================================================*/
create table STORE_SHIPPING_METHOD
(
   storeId              int not null,
   shippingMethodId     int not null,
   primary key (storeId, shippingMethodId)
);

/*==============================================================*/
/* Table: SUPPLIER                                              */
/*==============================================================*/
create table SUPPLIER
(
   supplierId           int not null auto_increment,
   supplierName         varchar(64) not null,
   supplierCode         varchar(32) not null,
   email                varchar(64) not null,
   address              varchar(256) not null,
   webSite              varchar(255),
   fax                  varchar(32),
   zip                  varchar(12),
   adminId              int,
   contacts             varchar(512),
   comments             varchar(512),
   bankAccount          varchar(512),
   prodDescConvert      text,
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (supplierId),
   unique key AK_Key_2 (supplierCode)
);

/*==============================================================*/
/* Table: SUPPLIER_PRODUCT                                      */
/*==============================================================*/
create table SUPPLIER_PRODUCT
(
   supplierProductId    int not null auto_increment,
   productId            int,
   supplierId           int,
   categoryId           int,
   tbCId                bigint,
   tbId                 bigint,
   tbCatProps           varchar(1024),
   tbSellCatProps       text,
   productName          varchar(256) not null,
   productDesc          text,
   wholesalePrice       varchar(256) not null,
   productCode          varchar(32) not null,
   status               smallint not null,
   mediaUrl             varchar(512),
   modifyLogs           text,
   updateLogs           varchar(512),
   createTime           datetime not null,
   updateTime           datetime not null,
   createBy             int,
   updateBy             int,
   version              int not null default 0,
   primary key (supplierProductId)
);

/*==============================================================*/
/* Table: SYSTEM_CONFIG                                         */
/*==============================================================*/
create table SYSTEM_CONFIG
(
   systemConfigId       int not null auto_increment,
   storeId              int,
   configKey            varchar(64) not null,
   description          varchar(512),
   category             varchar(64),
   configType           smallint,
   configValue          varchar(1024) not null,
   options              varchar(512),
   dataType             smallint,
   isSystem             smallint,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (systemConfigId),
   unique key AK_CONFIGKEY (configKey, storeId)
);

/*==============================================================*/
/* Table: SYSTEM_LANGUAGE                                       */
/*==============================================================*/
create table SYSTEM_LANGUAGE
(
   systemLanguageId     int not null auto_increment,
   languageNameKey      varchar(32) not null,
   localeCode           varchar(8) not null,
   status               smallint,
   isDefault            smallint,
   sortOrder            int,
   version              int not null default 0,
   primary key (systemLanguageId),
   unique key AK_Key_2 (localeCode)
);

/*==============================================================*/
/* Table: SYSTEM_MESSAGE                                        */
/*==============================================================*/
create table SYSTEM_MESSAGE
(
   systemMessageId      int not null auto_increment,
   messageHtml          varchar(512) not null,
   messageType          smallint,
   status               smallint,
   appuserId            int,
   createTime           datetime not null,
   primary key (systemMessageId)
);

/*==============================================================*/
/* Table: SYSTEM_QUEUE                                          */
/*==============================================================*/
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

/*==============================================================*/
/* Table: SYSTEM_VERSION                                        */
/*==============================================================*/
create table SYSTEM_VERSION
(
   systemVersionId      int not null auto_increment,
   sysVersion           varchar(32) not null,
   productCode          varchar(64) not null,
   domainName           varchar(256) not null,
   licenseKey           varchar(32) not null,
   createTime           datetime not null,
   updateTime           datetime,
   version              int not null default 0,
   primary key (systemVersionId)
);

/*==============================================================*/
/* Table: TAX                                                   */
/*==============================================================*/
create table TAX
(
   taxId                int not null auto_increment,
   taxName              varchar(64) not null,
   taxRegisterNo        varchar(64),
   status               smallint,
   createBy             int,
   updateBy             int,
   createTime           datetime not null,
   updateTime           datetime,
   version              int not null default 0,
   primary key (taxId),
   unique key AK_Key_2 (taxName)
);

/*==============================================================*/
/* Table: TAX_RATE                                              */
/*==============================================================*/
create table TAX_RATE
(
   taxRateId            int not null auto_increment,
   taxId                int,
   regionId             int,
   productTypeId        int,
   formula              varchar(255),
   rateValue            numeric(12,2),
   rateType             smallint,
   version              int not null default 0,
   primary key (taxRateId)
);

/*==============================================================*/
/* Table: TB_CATEGORY_REFER                                     */
/*==============================================================*/
create table TB_CATEGORY_REFER
(
   tbCategoryReferId    int not null auto_increment,
   categoryId           int,
   tbCategoryId         bigint not null,
   tbCategoryName       varchar(256),
   primary key (tbCategoryReferId),
   unique key AK_CATEGORYID (categoryId)
);

/*==============================================================*/
/* Table: TB_CAT_PROP_REFER                                     */
/*==============================================================*/
create table TB_CAT_PROP_REFER
(
   tbCatPropReferId     int not null auto_increment,
   accessoryGroupId     int,
   tbCatPropId          bigint not null,
   tbCatPropName        varchar(128),
   primary key (tbCatPropReferId),
   unique key AK_ACCESSORYGROUPID (accessoryGroupId)
);

/*==============================================================*/
/* Table: TB_CAT_PROP_VALUE_REFER                               */
/*==============================================================*/
create table TB_CAT_PROP_VALUE_REFER
(
   tbCatPropValueReferId int not null auto_increment,
   accessoryId          int,
   tbCatPropReferId     int,
   tbCatPropId          bigint not null,
   tbCatPropValueId     bigint not null,
   tbCatPropValueName   varchar(256),
   primary key (tbCatPropValueReferId),
   unique key AK_ACCESSORYID (accessoryId)
);

/*==============================================================*/
/* Table: TRANSFER_RECORD                                       */
/*==============================================================*/
create table TRANSFER_RECORD
(
   transferRecordId     int not null,
   type                 smallint not null,
   orderNo              varchar(20) not null,
   mtcnNo               varchar(16),
   firstname            varchar(32),
   middlename           varchar(32),
   lastname             varchar(32),
   address              varchar(256),
   taxCode              varchar(16),
   country              varchar(64),
   state                varchar(64),
   city                 varchar(64),
   zip                  varchar(16),
   phone                varchar(32),
   question             varchar(128),
   answer               varchar(128),
   amount               numeric(12,2),
   status               smallint not null,
   createTime           datetime not null,
   updateTime           datetime not null,
   updateBy             int not null,
   primary key (transferRecordId)
);

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE
(
   appUserId            int not null,
   appRoleId            int not null,
   version              int not null default 0,
   primary key (appUserId, appRoleId)
);

/*==============================================================*/
/* Table: VALIDATION_SESSION                                    */
/*==============================================================*/
create table VALIDATION_SESSION
(
   validationSessionId  int not null auto_increment,
   url                  varchar(255) not null,
   expiredDate          datetime not null,
   email                varchar(64) not null,
   vsType               smallint not null default 0,
   primary key (validationSessionId)
);

/*==============================================================*/
/* Table: WHOLESALE_PRICE                                       */
/*==============================================================*/
create table WHOLESALE_PRICE
(
   wholesalePriceId     int not null auto_increment,
   productSkuId         int,
   price                numeric(12,2) not null,
   minQuantity          int not null,
   primary key (wholesalePriceId)
);

/*==============================================================*/
/* Table: WISHLIST                                              */
/*==============================================================*/
create table WISHLIST
(
   wishListId           int not null auto_increment,
   shippingAddressId    int,
   customerId           int,
   wishListTitle        varchar(128) not null,
   wishListType         smallint not null,
   wishListDetail       varchar(512),
   shareLevel           int,
   isDefault            smallint,
   recipientEmailList   varchar(512),
   eventDate            datetime,
   priority             int,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (wishListId)
);

/*==============================================================*/
/* Table: WISHLIST_ITEM                                         */
/*==============================================================*/
create table WISHLIST_ITEM
(
   wishlistItemId       int not null auto_increment,
   wishListId           int not null,
   productId            int,
   quantity             int,
   purchasedQty         int,
   shortDescription     varchar(512),
   productAttributes    blob,
   createTime           datetime,
   version              int not null default 0,
   primary key (wishlistItemId)
);

/*==============================================================*/
/* Table: WRAP                                                  */
/*==============================================================*/
create table WRAP
(
   wrapId               int not null auto_increment,
   defaultPrice         numeric(12,2),
   wrapName             varchar(64),
   description          varchar(1024),
   imageSrc             varchar(255),
   version              int not null default 0,
   primary key (wrapId)
);

/*==============================================================*/
/* View: V_AWAITING_PURCHASE                                    */
/*==============================================================*/
CREATE view V_AWAITING_PURCHASE 
AS select p.supplierId, sum(poi.purchaseQuantity) as quantity
   from PURCHASE_ORDER_ITEM poi, ORDER_SKU os, Product_sku sku, Product p
   where poi.status=0
   and poi.orderSkuid=os.orderSkuid
   and os.productSkuId=sku.productSkuId
   and p.productId=sku.productId
   group by p.supplierId;

/*==============================================================*/
/* View: V_SHOPPINGCART_ITEM_STAT                               */
/*==============================================================*/
CREATE view V_SHOPPINGCART_ITEM_STAT 
AS select sum(s.quantity) as quantity,s.productSkuId,s.itemType,s.isSaved
   from shoppingcart_item s
   where s.productSkuId is not null and s.parentId is null
   group by s.productSkuId;

alter table ACCESSORY add constraint FK_ACC__ACC_GROUP foreign key (accessoryGroupId)
      references ACCESSORY_GROUP (accessoryGroupId) on delete cascade on update restrict;

alter table ADDRESS add constraint FK_ADD__USER foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table ADVERTISEMENT add constraint FK_AD_ADPOS foreign key (adPositionTypeId)
      references AD_POSITION_TYPE (adPositionTypeId) on delete restrict on update restrict;

alter table AD_POSITION_TYPE add constraint FK_AD_P__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table ALSO_BUY add constraint FK_ALSO_BUY_PR foreign key (alsoProductId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table APP_USER add constraint FK_CUT__MEMBERSHIP foreign key (membershipId)
      references MEMBERSHIP (membershipId) on delete restrict on update restrict;

alter table APP_USER add constraint FK_USER__SUPPLIER foreign key (supplierId)
      references SUPPLIER (supplierId) on delete restrict on update restrict;

alter table APP_USER add constraint FK_USER__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table APP_USER add constraint FK_USER__ADMIN_INFO foreign key (adminInfoId)
      references ADMIN_INFO (adminInfoId) on delete restrict on update restrict;

alter table CATEGORY add constraint FK_CAT__CAT foreign key (parentId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table CATEGORY add constraint FK_CATEGORY__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;

alter table CATEGORY add constraint FK_CATEGORY__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table CATEGORY_ATTR_VALUE add constraint FK_CATEGORY_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table CATEGORY_ATTR_VALUE add constraint FK_ATTR_V__CATEGORY foreign key (categoryId)
      references CATEGORY (categoryId) on delete cascade on update restrict;

alter table CONTENT add constraint FK_CONTENT__CAT foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table CONTENT add constraint FK_CONTENT__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table CONTENT_ATTR_VALUE add constraint FK_CONTENT_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table CONTENT_ATTR_VALUE add constraint FK_ATTR_V__CON foreign key (contentId)
      references CONTENT (contentId) on delete cascade on update restrict;

alter table COUPON add constraint FK_COUPON__RULE foreign key (promoRuleId)
      references PROMO_RULE (promoRuleId) on delete restrict on update restrict;

alter table CUSTOMER_ATTR_VALUE add constraint FK_CUSTOMER_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table CUSTOMER_ATTR_VALUE add constraint FK_ATTR_V__CUSTOMER foreign key (customerId)
      references APP_USER (appuserId) on delete cascade on update restrict;

alter table FAVORITE add constraint FK_FAVO_CUSTOMER foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table FEEDBACK add constraint FK_Reference_133 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table FEEDBACK add constraint FK_FEEDBACK__USER foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table INVENTORY add constraint FK_INVENTORY__SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

alter table INVENTORY_AUDIT add constraint FK_AUDIT__INVENTORY foreign key (inventoryId)
      references INVENTORY (inventoryId) on delete cascade on update restrict;

alter table ORDER_AUDIT add constraint FK_AUDIT__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_PAYMENT add constraint FK_OR_PA_SALES_OR foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_PROMOTION add constraint FK_O_PROMO__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_RETURN add constraint FK_RETURN__ORDER foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_RETURN add constraint FK_EXCHANGE__ORDER foreign key (exchangeOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_RETURN add constraint FK_RETURN__APP_USER foreign key (receivedBy)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table ORDER_RETURN_SKU add constraint FK_RETURN_SKU__RETURN foreign key (orderReturnId)
      references ORDER_RETURN (orderReturnId) on delete restrict on update restrict;

alter table ORDER_RETURN_SKU add constraint FK_RETURN_SKU__O_SKU foreign key (orderSkuId)
      references ORDER_SKU (orderSkuId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_SHIPMENT__ADDRESS foreign key (shippingAddressId)
      references ORDER_ADDRESS (orderAddressId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_ORD_SHIPMENT__ORD foreign key (salesOrderId)
      references SALES_ORDER (salesOrderId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_SHIPMENT__PICK_LIST foreign key (orderPickListId)
      references ORDER_PICK_LIST (orderPickListId) on delete restrict on update restrict;

alter table ORDER_SKU add constraint FK_O_SKU__GC foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId) on delete restrict on update restrict;

alter table ORDER_SKU add constraint FK_O_SKU__P_SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete restrict on update restrict;

alter table ORDER_SKU add constraint FK_O_SKU__SHIPMENT foreign key (orderShipmentId)
      references ORDER_SHIPMENT (orderShipmentId) on delete restrict on update restrict;

alter table PRODUCT add constraint FK_P__PT foreign key (productTypeId)
      references PRODUCT_TYPE (productTypeId) on delete restrict on update restrict;

alter table PRODUCT add constraint FK_PRODUCT__DESC foreign key (productDescriptionId)
      references PRODUCT_DESCRIPTION (productDescriptionId) on delete set null on update restrict;

alter table PRODUCT add constraint FK_PRO__BRAND foreign key (brandId)
      references BRAND (brandId) on delete set null on update restrict;

alter table PRODUCT_ACCESSORY add constraint FK_P_ACC__ACCESSORY foreign key (accessoryId)
      references ACCESSORY (accessoryId) on delete cascade on update restrict;

alter table PRODUCT_ACCESSORY add constraint FK_P_ACC__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_ADVERTISEMENT add constraint FK_PRO_ADVERTIS foreign key (advertisementId)
      references ADVERTISEMENT (advertisementId) on delete cascade on update cascade;

alter table PRODUCT_ADVERTISEMENT add constraint FK_P_AD__CAT foreign key (categoryId)
      references CATEGORY (categoryId) on delete cascade on update restrict;

alter table PRODUCT_ATTR_VALUE add constraint FK_PRODUCT_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table PRODUCT_ATTR_VALUE add constraint FK_ATTR_V__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_ATTR_VALUE add constraint FK_P_A_VALUE__GROUP_I foreign key (productAttGroupItemId)
      references PRODUCT_ATT_GROUP_ITEM (productAttGroupItemId) on delete cascade on update restrict;

alter table PRODUCT_ATT_GROUP add constraint FK_ATT_GROUP__P_TYPE foreign key (productTypeId)
      references PRODUCT_TYPE (productTypeId) on delete restrict on update restrict;

alter table PRODUCT_ATT_GROUP_ITEM add constraint FK_P_G_ITEM__ATT foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table PRODUCT_ATT_GROUP_ITEM add constraint FK_ATT_ITEM__ATT_G foreign key (productAttGroupId)
      references PRODUCT_ATT_GROUP (productAttGroupId) on delete restrict on update restrict;

alter table PRODUCT_CATEGORY add constraint FK_P_CAT__P foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_CATEGORY add constraint FK_P_CAT__CAT foreign key (categoryId)
      references CATEGORY (categoryId) on delete cascade on update restrict;

alter table PRODUCT_MEDIA add constraint FK_MEDIA__P foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_PACKAGE_ITEM add constraint FK_PACKAGE_SKU foreign key (packageSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

alter table PRODUCT_PACKAGE_ITEM add constraint FK_PACKAGE__ITEMS foreign key (itemSkuId)
      references PRODUCT_SKU (productSkuId) on delete restrict on update restrict;

alter table PRODUCT_RATE_ITEM add constraint FK_R_ITEM__TYPE foreign key (productTypeId)
      references PRODUCT_TYPE (productTypeId) on delete restrict on update restrict;

alter table PRODUCT_RATE_VALUE add constraint FK_RATE__R_ITEM foreign key (productRateItemId)
      references PRODUCT_RATE_ITEM (productRateItemId) on delete cascade on update restrict;

alter table PRODUCT_RATE_VALUE add constraint FK_RATE__REVIEW foreign key (productReviewId)
      references PRODUCT_REVIEW (productReviewId) on delete cascade on update restrict;

alter table PRODUCT_REVIEW add constraint FK_REVIEW__APPUSER foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table PRODUCT_REVIEW add constraint FK_REVIEW__P foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_REVIEW add constraint FK_REVIEW__P_REVIEW foreign key (reviewId)
      references PRODUCT_REVIEW (productReviewId) on delete cascade on update restrict;

alter table PRODUCT_REVIEW add constraint FK_P_REVIEW__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table PRODUCT_SKU add constraint FK_SKU__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_SKU_OPTION_VALUE add constraint FK_SKU__OPT_VALUE foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

alter table PRODUCT_SKU_OPTION_VALUE add constraint FK_OPT_VALUE__SKU foreign key (skuOptionValueId)
      references SKU_OPTION_VALUE (skuOptionValueId) on delete restrict on update restrict;

alter table PRODUCT_STAT add constraint FK_STAT__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete cascade on update restrict;

alter table PRODUCT_STAT add constraint FK_P_STAT__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table PRODUCT_TYPE_SKU_OPTION add constraint FK_P_TYPE__OPT foreign key (skuOptionId)
      references SKU_OPTION (skuOptionId) on delete restrict on update restrict;

alter table PRODUCT_TYPE_SKU_OPTION add constraint FK_OPT__P_TYPE foreign key (productTypeId)
      references PRODUCT_TYPE (productTypeId) on delete restrict on update restrict;

alter table PROMO_RULE add constraint FK_PROMO_RULE__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;

alter table PROMO_RULE add constraint FK_PROMO_RULE__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table PROMO_RULE_ELEMENT add constraint FK_ELEMENT__RULE foreign key (promoRuleId)
      references PROMO_RULE (promoRuleId) on delete restrict on update restrict;

alter table PROMO_RULE_PARAMETER add constraint FK_PARAM__ELEMENT foreign key (promoRuleElementId)
      references PROMO_RULE_ELEMENT (promoRuleElementId) on delete restrict on update restrict;

alter table PURCHASE_ORDER add constraint FK_PO__SUPPLIER foreign key (supplierId)
      references SUPPLIER (supplierId) on delete restrict on update restrict;

alter table PURCHASE_ORDER_ITEM add constraint FK_PO_ITEM__PO foreign key (purchaseOrderId)
      references PURCHASE_ORDER (purchaseOrderId) on delete restrict on update restrict;

alter table PURCHASE_ORDER_ITEM add constraint FK_PO_ITEM__O_SKU foreign key (orderSkuId)
      references ORDER_SKU (orderSkuId) on delete restrict on update restrict;

alter table RECOMMENDED_PRODUCT add constraint FK_REC_PRO foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table RECOMMENDED_PRODUCT add constraint FK_RECO_RECOMMEN foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId) on delete restrict on update restrict;

alter table REGION_ITEM add constraint FK_Reference_116 foreign key (regionId)
      references REGION (regionId) on delete cascade on update restrict;

alter table REVIEW_VOTE add constraint FK_R_VOTE__APP_USER foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table REVIEW_VOTE add constraint FK_R_VOTE__P_REVIEW foreign key (productReviewId)
      references PRODUCT_REVIEW (productReviewId) on delete cascade on update restrict;

alter table ROLE_RES add constraint FK_role_res_resource foreign key (appResourceId)
      references APP_RESOURCE (appResourceId) on delete cascade on update restrict;

alter table ROLE_RES add constraint FK_role_res_role foreign key (appRoleId)
      references APP_ROLE (appRoleId) on delete cascade on update restrict;

alter table SALESORDER_ATTR_VALUE add constraint FK_ORDER_V__ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete cascade on update restrict;

alter table SALESORDER_ATTR_VALUE add constraint FK_ATTR_V__ORDER foreign key (orderId)
      references SALES_ORDER (salesOrderId) on delete cascade on update restrict;

alter table SALES_ORDER add constraint FK_ORDER__BILLING_ADDR foreign key (billingAddressId)
      references ORDER_ADDRESS (orderAddressId) on delete restrict on update restrict;

alter table SALES_ORDER add constraint FK_SALES_ORDER__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table SHIPPING_METHOD add constraint FK_SHIP_CARRIER foreign key (carrierId)
      references CARRIER (carrierId) on delete restrict on update restrict;

alter table SHIPPING_RATE add constraint FK_SHIPPING__REGION foreign key (regionId)
      references REGION (regionId) on delete cascade on update restrict;

alter table SHIPPING_RATE add constraint FK_SHIP_SHIPPING foreign key (shippingMethodId)
      references SHIPPING_METHOD (shippingMethodId) on delete restrict on update restrict;

alter table SHOPPINGCART add constraint FK_SHOPPINGCART__STOARE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_CART_ITEM__CART foreign key (shoppingcartId)
      references SHOPPINGCART (shoppingcartId) on delete cascade on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_CART_ITEM__SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM_GC add constraint FK_CARTITEM__GC foreign key (shoppingcartItemId)
      references SHOPPINGCART_ITEM (shoppingcartItemId) on delete cascade on update restrict;

alter table SHOP_POINT add constraint FK_SHOP_POI_CUS foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table SHOP_POINT_HISTORY add constraint FK_SHOP_CUST2 foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table SKU_OPTION_VALUE add constraint FK_OPT_VALUE__OPT foreign key (skuOptionId)
      references SKU_OPTION (skuOptionId) on delete restrict on update restrict;

alter table STORE add constraint FK_STORE__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;

alter table STORE_PAYMENT_METHOD add constraint FK_SPM__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table STORE_PAYMENT_METHOD add constraint FK_SPM__PAYMENT_METHOD foreign key (paymentMethodId)
      references PAYMENT_METHOD (paymentMethodId) on delete restrict on update restrict;

alter table STORE_SHIPPING_METHOD add constraint FK_SHM__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table STORE_SHIPPING_METHOD add constraint FK_SPM__SHIPPING_METHOD foreign key (shippingMethodId)
      references SHIPPING_METHOD (shippingMethodId) on delete restrict on update restrict;

alter table SUPPLIER_PRODUCT add constraint FK_SUP_P__SUP foreign key (supplierId)
      references SUPPLIER (supplierId) on delete restrict on update restrict;

alter table SUPPLIER_PRODUCT add constraint FK_SP__PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table SYSTEM_CONFIG add constraint FK_SYSTEM_CONFIG__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table SYSTEM_MESSAGE add constraint FK_sm_app_user foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table TAX_RATE add constraint FK_TAX__REGION foreign key (regionId)
      references REGION (regionId) on delete cascade on update restrict;

alter table TAX_RATE add constraint FK_TAX__P_TYPE foreign key (productTypeId)
      references PRODUCT_TYPE (productTypeId) on delete restrict on update restrict;

alter table TAX_RATE add constraint FK_TAX_RATE_TAX foreign key (taxId)
      references TAX (taxId) on delete restrict on update restrict;

alter table TB_CATEGORY_REFER add constraint FK_TB_CAT_C__CAT foreign key (categoryId)
      references CATEGORY (categoryId) on delete cascade on update restrict;

alter table TB_CAT_PROP_REFER add constraint FK_CAT_PROP_C__ACC_GROUP foreign key (accessoryGroupId)
      references ACCESSORY_GROUP (accessoryGroupId) on delete cascade on update restrict;

alter table TB_CAT_PROP_VALUE_REFER add constraint FK_CAT_PROP_VAL_C__ACC foreign key (accessoryId)
      references ACCESSORY (accessoryId) on delete cascade on update restrict;

alter table TB_CAT_PROP_VALUE_REFER add constraint FK_CAT_PROP_VAL_C__CAT_PROP foreign key (tbCatPropReferId)
      references TB_CAT_PROP_REFER (tbCatPropReferId) on delete cascade on update restrict;

alter table USER_ROLE add constraint FK_APP_APPUSER foreign key (appUserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table USER_ROLE add constraint FK_APP_APP_ROLE foreign key (appRoleId)
      references APP_ROLE (appRoleId) on delete cascade on update restrict;

alter table WHOLESALE_PRICE add constraint FK_WHOLESALE__SKU foreign key (productSkuId)
      references PRODUCT_SKU (productSkuId) on delete cascade on update restrict;

alter table WISHLIST add constraint FK_WISH_CUSTOMER foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table WISHLIST add constraint FK_WISH_ADD foreign key (shippingAddressId)
      references ADDRESS (addressId) on delete set null on update restrict;

alter table WISHLIST_ITEM add constraint FK_WIS_PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table WISHLIST_ITEM add constraint FK_WIS_WISHLIST foreign key (wishListId)
      references WISHLIST (wishListId) on delete restrict on update restrict;


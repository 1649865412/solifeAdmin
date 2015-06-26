/* Foreign Keys must be dropped in the target to ensure that requires changes can be done*/

ALTER TABLE `advertisement` DROP FOREIGN KEY `FK_AD_ADPOS` ;

ALTER TABLE `app_user` DROP FOREIGN KEY `FK_CUT__MEMBERSHIP` ;

ALTER TABLE `app_user` DROP FOREIGN KEY `FK_USER__PROVIDER` ;

ALTER TABLE `content` DROP FOREIGN KEY `FK_CONTENT__CAT` ;

ALTER TABLE `order_sku` DROP FOREIGN KEY `FK_O_SKU__GC` ;

ALTER TABLE `order_sku` DROP FOREIGN KEY `FK_O_SKU__P_SKU` ;

ALTER TABLE `order_sku` DROP FOREIGN KEY `FK_O_SKU__SHIPMENT` ;

ALTER TABLE `payment_method` DROP FOREIGN KEY `FK_PAY_PAYMENT` ;

ALTER TABLE `product` DROP FOREIGN KEY `FK_P__PROVIDER` ;

ALTER TABLE `product` DROP FOREIGN KEY `FK_P__PT` ;

ALTER TABLE `product` DROP FOREIGN KEY `FK_PRO__BRAND` ;

ALTER TABLE `product` DROP FOREIGN KEY `FK_PRODUCT__DESC` ;

ALTER TABLE `product_advertisement` DROP FOREIGN KEY `FK_PRO_ADVERTIS` ;

ALTER TABLE `region_item` DROP FOREIGN KEY `FK_Reference_116` ;

ALTER TABLE `role_res` DROP FOREIGN KEY `FK_role_res_resource` ;

ALTER TABLE `role_res` DROP FOREIGN KEY `FK_role_res_role` ;

ALTER TABLE `shipping_rate` DROP FOREIGN KEY `FK_SHIPPING__REGION` ;

ALTER TABLE `user_role` DROP FOREIGN KEY `FK_APP_APP_ROLE` ;

ALTER TABLE `user_role` DROP FOREIGN KEY `FK_APP_APPUSER` ;


/* Alter table in target */
ALTER TABLE `accessory_group` 
	ADD COLUMN `groupCode` varchar(32)  COLLATE utf8_general_ci NOT NULL after `groupDesc`, 
	ADD UNIQUE KEY `AK_GROUPCODE`(`groupCode`), COMMENT='';

/* Alter table in target */
ALTER TABLE `ad_position_type` 
	CHANGE `positionName` `positionName` varchar(128)  COLLATE utf8_general_ci NOT NULL after `adPositionTypeId`, 
	CHANGE `height` `height` int(11)   NOT NULL after `positionName`, 
	CHANGE `width` `width` int(11)   NOT NULL after `height`, 
	CHANGE `displayType` `displayType` smallint(6)   NOT NULL after `width`, 
	CHANGE `templatePath` `templatePath` varchar(128)  COLLATE utf8_general_ci NOT NULL after `description`, 
	ADD UNIQUE KEY `AK_POSITIONNAME`(`positionName`), COMMENT='';

/* Alter table in target */
ALTER TABLE `advertisement` 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `isInclude`, 
	DROP COLUMN `status`, 
	ADD UNIQUE KEY `AK_ADVERTISEMENTNAME`(`advertisementName`), COMMENT='';

/* Alter table in target */
ALTER TABLE `app_resource` 
	ADD COLUMN `appResourceId` int(11)   NOT NULL auto_increment first, 
	CHANGE `parentId` `parentId` int(11)   NULL after `appResourceId`, 
	DROP COLUMN `resourceId`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`appResourceId`), COMMENT='';
INSERT INTO `app_resource` (`appResourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES (9,'供应商',0,'/supplier/**','供应商管理');

/* Alter table in target */
ALTER TABLE `app_role` 
	ADD COLUMN `appRoleId` int(11)   NOT NULL auto_increment first, 
	CHANGE `roleName` `roleName` varchar(32)  COLLATE utf8_general_ci NOT NULL after `appRoleId`, 
	DROP COLUMN `roleId`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`appRoleId`), COMMENT='';
INSERT INTO `app_role` (`appRoleId`,`roleName`,`roleDetail`,`isSystem`,`createTime`,`updateTime`,`status`) VALUES (9,'供应商管理员','供应商管理模块',1,NULL,NULL,1);

/* Alter table in target */
ALTER TABLE `app_user` 
	ADD COLUMN `supplierId` int(11)   NULL after `appuserId`, 
	CHANGE `membershipId` `membershipId` int(11)   NULL after `supplierId`, 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	DROP COLUMN `providerId`, 
	DROP KEY `FK_USER__PROVIDER`, add KEY `FK_USER__PROVIDER`(`supplierId`), 
	ADD KEY `FK_USER__STORE`(`storeId`), COMMENT='';

/* Create table in target */
CREATE TABLE `catalog`(
	`catalogId` int(11) NOT NULL  auto_increment , 
	`name` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`code` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`description` text COLLATE utf8_general_ci NULL  , 
	`isVirtual` smallint(6) NOT NULL  , 
	`status` smallint(6) NOT NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	`categoryId` int(11) NOT NULL  , 
	PRIMARY KEY (`catalogId`) , 
	UNIQUE KEY `AK_Key_Code`(`code`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Alter table in target */
ALTER TABLE `category` 
	ADD COLUMN `linkedCategoryId` int(11)   NULL after `version`, 
	ADD COLUMN `catalogId` int(11)   NULL after `linkedCategoryId`, 
	ADD COLUMN `storeId` int(11)   NULL after `catalogId`, 
	DROP KEY `AK_PRODUCT_CODE`, add UNIQUE KEY `AK_PRODUCT_CODE`(`categoryCode`,`catalogId`,`storeId`), 
	ADD KEY `FK_CATEGORY__CATALOG`(`catalogId`), 
	ADD KEY `FK_CATEGORY__STORE`(`storeId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `company_info` 
	ADD COLUMN `companyInfoId` int(11)   NOT NULL auto_increment first, 
	CHANGE `companyName` `companyName` varchar(64)  COLLATE utf8_general_ci NOT NULL after `companyInfoId`, 
	DROP COLUMN `companyId`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`companyInfoId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `content` 
	CHANGE `contentTitle` `contentTitle` varchar(128)  COLLATE utf8_general_ci NOT NULL after `categoryId`, 
	CHANGE `contentCode` `contentCode` varchar(32)  COLLATE utf8_general_ci NOT NULL after `contentTitle`, 
	CHANGE `contentBody` `contentBody` text  COLLATE utf8_general_ci NULL after `contentCode`, 
	CHANGE `publishTime` `publishTime` datetime   NOT NULL after `contentBody`, 
	CHANGE `expiredTime` `expiredTime` datetime   NULL after `publishTime`, 
	CHANGE `metaKeyword` `metaKeyword` varchar(256)  COLLATE utf8_general_ci NULL after `expiredTime`, 
	CHANGE `metaDescription` `metaDescription` varchar(256)  COLLATE utf8_general_ci NULL after `metaKeyword`, 
	CHANGE `status` `status` smallint(6)   NOT NULL after `metaDescription`, 
	CHANGE `sortOrder` `sortOrder` int(11)   NOT NULL after `status`, 
	CHANGE `createTime` `createTime` datetime   NOT NULL after `sortOrder`, 
	CHANGE `updateTime` `updateTime` datetime   NOT NULL after `createTime`, 
	CHANGE `createBy` `createBy` int(11)   NULL after `updateTime`, 
	CHANGE `updateBy` `updateBy` int(11)   NULL after `createBy`, 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `updateBy`, 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	ADD UNIQUE KEY `AK_CONTENTCODE`(`contentCode`,`storeId`), 
	ADD KEY `FK_CONTENT__STORE`(`storeId`), COMMENT='';

/* Create table in target */
CREATE TABLE `currency`(
	`currencyId` int(11) NOT NULL  auto_increment , 
	`currencyCode` varchar(8) COLLATE utf8_general_ci NOT NULL  , 
	`currencyName` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`isDefault` smallint(6) NULL  , 
	`status` smallint(6) NULL  , 
	`sortOrder` int(11) NULL  , 
	`exchangeRate` decimal(12,4) NULL  , 
	`currencySymbol` varchar(16) COLLATE utf8_general_ci NOT NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	PRIMARY KEY (`currencyId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Alter table in target */
ALTER TABLE `order_sku` 
	CHANGE `accessories` `accessories` varchar(512)  COLLATE utf8_general_ci NULL after `allocatedQuantity`, 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `accessories`, 
	ADD COLUMN `allocatedPreQty` int(11)   NULL after `version`, COMMENT='';

/* Alter table in target */
ALTER TABLE `patch_execute_history` 
	ADD COLUMN `patchExecuteHistoryId` int(10) unsigned   NOT NULL auto_increment first, 
	CHANGE `transactionUid` `transactionUid` varchar(32)  COLLATE utf8_general_ci NULL after `patchExecuteHistoryId`, 
	DROP COLUMN `executeId`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`patchExecuteHistoryId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `payment_history` 
	ADD COLUMN `paymentMethodId` int(11)   NULL after `receiveData`, 
	CHANGE `isBrowsed` `isBrowsed` smallint(6)   NULL DEFAULT '0' after `paymentMethodId`, 
	DROP COLUMN `paymentGatewayId`, COMMENT='';

/* Alter table in target */
ALTER TABLE `payment_method` 
	CHANGE `paymentMethodName` `paymentMethodName` varchar(64)  COLLATE utf8_general_ci NOT NULL after `paymentMethodId`, 
	ADD COLUMN `paymentMethodCode` varchar(32)  COLLATE utf8_general_ci NOT NULL after `paymentMethodName`, 
	CHANGE `paymentMethodDetail` `paymentMethodDetail` varchar(2048)  COLLATE utf8_general_ci NULL after `paymentMethodCode`, 
	ADD COLUMN `paymentMethodIcon` varchar(255)  COLLATE utf8_general_ci NULL after `paymentMethodType`, 
	CHANGE `protocol` `protocol` varchar(8)  COLLATE utf8_general_ci NULL after `paymentMethodIcon`, 
	ADD COLUMN `processorFile` varchar(255)  COLLATE utf8_general_ci NULL after `protocol`, 
	ADD COLUMN `configFile` varchar(256)  COLLATE utf8_general_ci NULL after `processorFile`, 
	ADD COLUMN `configData` blob   NULL after `configFile`, 
	ADD COLUMN `testModel` char(10)  COLLATE utf8_general_ci NULL after `configData`, 
	CHANGE `isCod` `isCod` smallint(6)   NOT NULL DEFAULT '0' after `testModel`, 
	CHANGE `sortOrder` `sortOrder` int(11)   NULL after `isCod`, 
	CHANGE `status` `status` smallint(6)   NULL after `sortOrder`, 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `status`, 
	DROP COLUMN `paymentGatewayId`, 
	ADD KEY `AK_Key_2`(`paymentMethodCode`), 
	DROP KEY `FK_PAY_PAYMENT`, COMMENT='';

/* Alter table in target */
ALTER TABLE `product` 
	ADD COLUMN `supplierId` int(11)   NULL after `productId`, 
	CHANGE `productTypeId` `productTypeId` int(11)   NULL after `supplierId`, 
	CHANGE `extra1` `extra1` varchar(255)  COLLATE utf8_general_ci NULL after `isAllowReview`, 
	ADD COLUMN `defaultSupplierProductId` int(11)   NULL after `version`, 
	DROP COLUMN `providerId`, 
	DROP COLUMN `extra2`, 
	DROP COLUMN `extra3`, 
	DROP KEY `FK_P__PROVIDER`, add KEY `FK_P__PROVIDER`(`supplierId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `product_advertisement` 
	CHANGE `categoryId` `categoryId` int(11)   NOT NULL after `advertisementId`, 
	CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `categoryId`, 
	DROP COLUMN `categoryPath`, 
	ADD KEY `FK_P_AD__CAT`(`categoryId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `product_review` 
	ADD COLUMN `storeId` int(11)   NULL after `status`, 
	ADD KEY `FK_P_REVIEW__STORE`(`storeId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `product_stat` 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	ADD KEY `FK_P_STAT__STORE`(`storeId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `promo_rule` 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	ADD COLUMN `catalogId` int(11)   NULL after `storeId`, 
	ADD KEY `FK_PROMO_RULE__CATALOG`(`catalogId`), 
	ADD KEY `FK_PROMO_RULE__STORE`(`storeId`), COMMENT='';

/* Create table in target */
CREATE TABLE `purchase_order`(
	`purchaseOrderId` int(11) NOT NULL  auto_increment , 
	`supplierId` int(11) NULL  , 
	`supplierName` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`purchaseOrderNo` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`trackingNo` varchar(128) COLLATE utf8_general_ci NULL  , 
	`poStatus` smallint(6) NOT NULL  , 
	`poResult` smallint(6) NOT NULL  , 
	`createBy` int(11) NULL  , 
	`updateBy` int(11) NULL  , 
	`updateTime` datetime NOT NULL  , 
	`createTime` datetime NOT NULL  , 
	`comments` varchar(512) COLLATE utf8_general_ci NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	PRIMARY KEY (`purchaseOrderId`) , 
	KEY `FK_PO__SUPPLIER`(`supplierId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Create table in target */
CREATE TABLE `purchase_order_item`(
	`purchaseOrderItemId` int(11) NOT NULL  auto_increment , 
	`purchaseOrderId` int(11) NULL  , 
	`productName` varchar(128) COLLATE utf8_general_ci NOT NULL  , 
	`supplierProductName` varchar(256) COLLATE utf8_general_ci NULL  , 
	`skuDisplay` varchar(128) COLLATE utf8_general_ci NULL  , 
	`purchaseQuantity` int(11) NOT NULL  , 
	`passedQuantity` int(11) NOT NULL  , 
	`purchasePrice` decimal(12,2) NOT NULL  , 
	`accessories` varchar(512) COLLATE utf8_general_ci NULL  , 
	`status` smallint(6) NOT NULL  , 
	`orderSkuId` int(11) NULL  , 
	PRIMARY KEY (`purchaseOrderItemId`) , 
	KEY `FK_PO_ITEM__PO`(`purchaseOrderId`) , 
	KEY `FK_PO_ITEM__O_SKU`(`orderSkuId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Alter table in target */
ALTER TABLE `region_item` COMMENT='';

/* Alter table in target */
ALTER TABLE `role_res`  ADD COLUMN `appResourceId` int(11)   NOT NULL after `roleResId`;
ALTER TABLE `role_res` ADD COLUMN `appRoleId` int(11)   NOT NULL after `appResourceId`;
ALTER TABLE `role_res` CHANGE `permissionMask` `permissionMask` int(11)   NULL after `appRoleId`;
update `role_res` set appResourceId=resourceId;
update `role_res` set appRoleId=roleId;
ALTER TABLE `role_res` DROP KEY `FK_role_res_resource`, add KEY `FK_role_res_resource`(`appResourceId`);
ALTER TABLE `role_res` DROP KEY `FK_role_res_role`, add KEY `FK_role_res_role`(`appRoleId`), COMMENT='';
ALTER TABLE `role_res` DROP COLUMN `resourceId`;
ALTER TABLE `role_res` DROP COLUMN `roleId`;

/* Alter table in target */
ALTER TABLE `sales_order` 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	ADD KEY `FK_SALES_ORDER__STORE`(`storeId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `shipping_rate` COMMENT='';

/* Alter table in target */
ALTER TABLE `shoppingcart` 
	ADD COLUMN `storeId` int(11)   NULL after `updateTime`, 
	ADD KEY `FK_SHOPPINGCART__STOARE`(`storeId`), COMMENT='';

/* Create table in target */
CREATE TABLE `store`(
	`storeId` int(11) NOT NULL  auto_increment , 
	`catalogId` int(11) NULL  , 
	`name` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`code` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`siteUrl` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`description` text COLLATE utf8_general_ci NULL  , 
	`status` smallint(6) NOT NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	`categoryId` int(11) NOT NULL  , 
	PRIMARY KEY (`storeId`) , 
	UNIQUE KEY `AK_code`(`code`) , 
	KEY `FK_STORE__CATALOG`(`catalogId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Create table in target */
CREATE TABLE `store_payment_method`(
	`storeId` int(11) NOT NULL  , 
	`paymentMethodId` int(11) NOT NULL  , 
	PRIMARY KEY (`storeId`,`paymentMethodId`) , 
	KEY `FK_SPM__PAYMENT_METHOD`(`paymentMethodId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Create table in target */
CREATE TABLE `store_shipping_method`(
	`storeId` int(11) NOT NULL  , 
	`shippingMethodId` int(11) NOT NULL  , 
	PRIMARY KEY (`storeId`,`shippingMethodId`) , 
	KEY `FK_SPM__SHIPPING_METHOD`(`shippingMethodId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Create table in target */
CREATE TABLE `supplier`(
	`supplierId` int(11) NOT NULL  auto_increment , 
	`supplierName` varchar(64) COLLATE utf8_general_ci NOT NULL  , 
	`supplierCode` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`email` varchar(64) COLLATE utf8_general_ci NOT NULL  , 
	`address` varchar(256) COLLATE utf8_general_ci NOT NULL  , 
	`fax` varchar(32) COLLATE utf8_general_ci NULL  , 
	`zip` varchar(12) COLLATE utf8_general_ci NULL  , 
	`adminId` int(11) NULL  , 
	`comments` varchar(512) COLLATE utf8_general_ci NULL  , 
	`status` smallint(6) NOT NULL  , 
	`createTime` datetime NOT NULL  , 
	`updateTime` datetime NOT NULL  , 
	`createBy` int(11) NULL  , 
	`updateBy` int(11) NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	`webSite` varchar(255) COLLATE utf8_general_ci NULL  , 
	`contacts` varchar(512) COLLATE utf8_general_ci NULL  , 
	`bankAccount` varchar(512) COLLATE utf8_general_ci NULL  , 
	`prodDescConvert` text COLLATE utf8_general_ci NULL  , 
	PRIMARY KEY (`supplierId`) , 
	UNIQUE KEY `AK_Key_2`(`supplierCode`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Create table in target */
CREATE TABLE `supplier_product`(
	`supplierProductId` int(11) NOT NULL  auto_increment , 
	`productId` int(11) NULL  , 
	`supplierId` int(11) NULL  , 
	`productName` varchar(256) COLLATE utf8_general_ci NOT NULL  , 
	`productDesc` text COLLATE utf8_general_ci NULL  , 
	`wholesalePrice` varchar(256) COLLATE utf8_general_ci NOT NULL  , 
	`productCode` varchar(32) COLLATE utf8_general_ci NOT NULL  , 
	`status` smallint(6) NOT NULL  , 
	`modifyLogs` text COLLATE utf8_general_ci NULL  , 
	`createTime` datetime NOT NULL  , 
	`updateTime` datetime NOT NULL  , 
	`createBy` int(11) NULL  , 
	`updateBy` int(11) NULL  , 
	`version` int(11) NOT NULL  DEFAULT '0' , 
	`mediaUrl` varchar(512) COLLATE utf8_general_ci NULL  , 
	`categoryId` int(11) NULL  , 
	`updateLogs` varchar(512) COLLATE utf8_general_ci NULL  , 
	`tbCId` bigint(20) NULL  , 
	`tbCatProps` varchar(1024) COLLATE utf8_general_ci NULL  , 
	`tbSellCatProps` text COLLATE utf8_general_ci NULL  , 
	`tbId` bigint(20) NULL  , 
	PRIMARY KEY (`supplierProductId`) , 
	KEY `FK_SUP_P__SUP`(`supplierId`) , 
	KEY `FK_SP__PRODUCT`(`productId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Alter table in target */
ALTER TABLE `system_config` 
	ADD COLUMN `systemConfigId` int(11)   NOT NULL auto_increment first, 
	CHANGE `configKey` `configKey` varchar(64)  COLLATE utf8_general_ci NOT NULL after `systemConfigId`, 
	ADD COLUMN `storeId` int(11)   NULL after `version`, 
	DROP COLUMN `configId`, 
	ADD UNIQUE KEY `AK_CONFIGKEY`(`configKey`,`storeId`), 
	DROP KEY `AK_Key_2`, 
	ADD KEY `FK_SYSTEM_CONFIG__STORE`(`storeId`), 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`systemConfigId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `system_language` 
	ADD COLUMN `systemLanguageId` int(11)   NOT NULL auto_increment first, 
	CHANGE `languageNameKey` `languageNameKey` varchar(32)  COLLATE utf8_general_ci NOT NULL after `systemLanguageId`, 
	DROP COLUMN `languageId`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`systemLanguageId`), COMMENT='';

/* Alter table in target */
ALTER TABLE `system_version` 
	ADD COLUMN `systemVersionId` int(11)   NOT NULL auto_increment first, 
	CHANGE `sysVersion` `sysVersion` varchar(32)  COLLATE utf8_general_ci NOT NULL after `systemVersionId`, 
	DROP COLUMN `id`, 
	DROP KEY `PRIMARY`, add PRIMARY KEY(`systemVersionId`), COMMENT='';

/* Create table in target */
CREATE TABLE `transfer_record`(
	`transferRecordId` int(11) NOT NULL  auto_increment , 
	`type` smallint(6) NOT NULL  , 
	`orderNo` varchar(20) COLLATE utf8_general_ci NOT NULL  , 
	`mtcnNo` varchar(16) COLLATE utf8_general_ci NULL  , 
	`firstname` varchar(32) COLLATE utf8_general_ci NULL  , 
	`middlename` varchar(32) COLLATE utf8_general_ci NULL  , 
	`lastname` varchar(32) COLLATE utf8_general_ci NULL  , 
	`address` varchar(256) COLLATE utf8_general_ci NULL  , 
	`country` varchar(64) COLLATE utf8_general_ci NULL  , 
	`state` varchar(64) COLLATE utf8_general_ci NULL  , 
	`city` varchar(64) COLLATE utf8_general_ci NULL  , 
	`taxCode` varchar(16) COLLATE utf8_general_ci NULL  , 
	`zip` varchar(16) COLLATE utf8_general_ci NULL  , 
	`phone` varchar(32) COLLATE utf8_general_ci NULL  , 
	`question` varchar(128) COLLATE utf8_general_ci NULL  , 
	`answer` varchar(128) COLLATE utf8_general_ci NULL  , 
	`status` smallint(6) NOT NULL  , 
	`createTime` datetime NOT NULL  , 
	`updateTime` datetime NOT NULL  , 
	`updateBy` int(11) NOT NULL  , 
	PRIMARY KEY (`transferRecordId`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


/* Alter table in target */
ALTER TABLE `user_role` ADD COLUMN `appRoleId` int(11)   NOT NULL after `appuserId`;
ALTER TABLE `user_role` CHANGE `version` `version` int(11)   NOT NULL DEFAULT '0' after `appRoleId`;
ALTER TABLE `user_role` DROP KEY `FK_APP_APP_ROLE`, add KEY `FK_APP_APP_ROLE`(`appRoleId`);
update user_role set appRoleId=roleId;
ALTER TABLE `user_role` DROP KEY `PRIMARY`, add PRIMARY KEY(`appuserId`,`appRoleId`), COMMENT='';
ALTER TABLE `user_role` DROP COLUMN `roleId`;

/* Alter ForeignKey(s)in target */

ALTER TABLE `app_user`
ADD CONSTRAINT `FK_USER__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);

ALTER TABLE `app_user`
ADD CONSTRAINT `FK_USER__SUPPLIER` 
FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `category`
ADD CONSTRAINT `FK_CATEGORY__CATALOG` 
FOREIGN KEY (`catalogId`) REFERENCES `catalog` (`catalogId`);

ALTER TABLE `category`
ADD CONSTRAINT `FK_CATEGORY__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `content`
ADD CONSTRAINT `FK_CONTENT__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `product_advertisement`
ADD CONSTRAINT `FK_P_AD__CAT` 
FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`) ON DELETE CASCADE;

ALTER TABLE `product_advertisement`
ADD CONSTRAINT `FK_PRO_ADVERTIS` 
FOREIGN KEY (`advertisementId`) REFERENCES `advertisement` (`advertisementId`) ON DELETE CASCADE;


/* Alter ForeignKey(s)in target */

ALTER TABLE `product_review`
ADD CONSTRAINT `FK_P_REVIEW__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `product_stat`
ADD CONSTRAINT `FK_P_STAT__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `promo_rule`
ADD CONSTRAINT `FK_PROMO_RULE__CATALOG` 
FOREIGN KEY (`catalogId`) REFERENCES `catalog` (`catalogId`);

ALTER TABLE `promo_rule`
ADD CONSTRAINT `FK_PROMO_RULE__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `purchase_order`
ADD CONSTRAINT `FK_PO__SUPPLIER` 
FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `purchase_order_item`
ADD CONSTRAINT `FK_PO_ITEM__O_SKU` 
FOREIGN KEY (`orderSkuId`) REFERENCES `order_sku` (`orderSkuId`);

ALTER TABLE `purchase_order_item`
ADD CONSTRAINT `FK_PO_ITEM__PO` 
FOREIGN KEY (`purchaseOrderId`) REFERENCES `purchase_order` (`purchaseOrderId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `region_item`
ADD CONSTRAINT `FK_REGION_ITEM__REGION` 
FOREIGN KEY (`regionId`) REFERENCES `region` (`regionId`) ON DELETE CASCADE;


/* Alter ForeignKey(s)in target */

ALTER TABLE `role_res`
ADD CONSTRAINT `FK_role_res_resource` 
FOREIGN KEY (`appResourceId`) REFERENCES `app_resource` (`appResourceId`) ON DELETE CASCADE;

ALTER TABLE `role_res`
ADD CONSTRAINT `FK_role_res_role` 
FOREIGN KEY (`appRoleId`) REFERENCES `app_role` (`appRoleId`) ON DELETE CASCADE;


/* Alter ForeignKey(s)in target */

ALTER TABLE `sales_order`
ADD CONSTRAINT `FK_SALES_ORDER__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `shipping_rate`
ADD CONSTRAINT `FK_SHIPPING__REGION` 
FOREIGN KEY (`regionId`) REFERENCES `region` (`regionId`) ON DELETE CASCADE;


/* Alter ForeignKey(s)in target */

ALTER TABLE `shoppingcart`
ADD CONSTRAINT `FK_SHOPPINGCART__STOARE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `store`
ADD CONSTRAINT `FK_STORE__CATALOG` 
FOREIGN KEY (`catalogId`) REFERENCES `catalog` (`catalogId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `store_payment_method`
ADD CONSTRAINT `FK_SPM__PAYMENT_METHOD` 
FOREIGN KEY (`paymentMethodId`) REFERENCES `payment_method` (`paymentMethodId`);

ALTER TABLE `store_payment_method`
ADD CONSTRAINT `FK_SPM__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `store_shipping_method`
ADD CONSTRAINT `FK_SPM__SHIPPING_METHOD` 
FOREIGN KEY (`shippingMethodId`) REFERENCES `shipping_method` (`shippingMethodId`);

ALTER TABLE `store_shipping_method`
ADD CONSTRAINT `FK_SHM__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);

/* Create ForeignKey(s)in Second database */ 

ALTER TABLE `supplier_product`
ADD CONSTRAINT `FK_SP__PRODUCT` 
FOREIGN KEY (`productId`) REFERENCES `product` (`productId`);

ALTER TABLE `supplier_product`
ADD CONSTRAINT `FK_SUP_P__SUP` 
FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `system_config`
ADD CONSTRAINT `FK_SYSTEM_CONFIG__STORE` 
FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`);


/* Alter ForeignKey(s)in target */

ALTER TABLE `user_role`
ADD CONSTRAINT `FK_APP_APP_ROLE` 
FOREIGN KEY (`appRoleId`) REFERENCES `app_role` (`appRoleId`) ON DELETE CASCADE;

/*  Create View in target  */

CREATE  VIEW `v_awaiting_purchase` AS select `p`.`supplierId` AS `supplierId`,sum(`poi`.`purchaseQuantity`) AS `quantity` from (((`purchase_order_item` `poi` join `order_sku` `os`) join `product_sku` `sku`) join `product` `p`) where ((`poi`.`status` = 0) and (`poi`.`orderSkuId` = `os`.`orderSkuId`) and (`os`.`productSkuId` = `sku`.`productSkuId`) and (`p`.`productId` = `sku`.`productId`)) group by `p`.`supplierId`;
 

/* The foreign keys that were dropped are now re-created*/

ALTER TABLE `advertisement`
ADD CONSTRAINT `FK_AD_ADPOS` 
FOREIGN KEY (`adPositionTypeId`) REFERENCES `ad_position_type` (`adPositionTypeId`);

ALTER TABLE `app_user`
ADD CONSTRAINT `FK_CUT__MEMBERSHIP` 
FOREIGN KEY (`membershipId`) REFERENCES `membership` (`membershipId`);

ALTER TABLE `content`
ADD CONSTRAINT `FK_CONTENT__CAT` 
FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`);

ALTER TABLE `order_sku`
ADD CONSTRAINT `FK_O_SKU__GC` 
FOREIGN KEY (`giftCertificateId`) REFERENCES `gift_certificate` (`giftCertificateId`);

ALTER TABLE `order_sku`
ADD CONSTRAINT `FK_O_SKU__P_SKU` 
FOREIGN KEY (`productSkuId`) REFERENCES `product_sku` (`productSkuId`);

ALTER TABLE `order_sku`
ADD CONSTRAINT `FK_O_SKU__SHIPMENT` 
FOREIGN KEY (`orderShipmentId`) REFERENCES `order_shipment` (`orderShipmentId`);

ALTER TABLE `product`
ADD CONSTRAINT `FK_P__PT` 
FOREIGN KEY (`productTypeId`) REFERENCES `product_type` (`productTypeId`);

ALTER TABLE `product`
ADD CONSTRAINT `FK_PRO__BRAND` 
FOREIGN KEY (`brandId`) REFERENCES `brand` (`brandId`) ON DELETE SET NULL;

ALTER TABLE `product`
ADD CONSTRAINT `FK_PRODUCT__DESC` 
FOREIGN KEY (`productDescriptionId`) REFERENCES `product_description` (`productDescriptionId`) ON DELETE SET NULL;

ALTER TABLE `user_role`
ADD CONSTRAINT `FK_APP_APPUSER` 
FOREIGN KEY (`appuserId`) REFERENCES `app_user` (`appuserId`);

/* init data */
insert into catalog (catalogId,name,categoryId,code,isVirtual,status,version) values (1,'Default',1,'default',0,1,0);
insert into store (storeId,catalogId,categoryId,name,code,siteUrl,status,version) values(1,1,2,'Default Store','default','http://localhost:8080',1,0);
update catalog set categoryId = 1;
update category set catalogId=1 where categoryPath like '1.%';
update category set catalogId=1 where categoryId =1;
update category set storeId=1 where categoryPath like '2.%';
update category set  storeId=1 where categoryId =2;
update content set storeId=1;
update shoppingcart set storeId=1;
update sales_order set storeId=1;
update app_user set storeId=1 where userType=0;
update product_review set storeId=1;
update promo_rule set storeId=1,catalogId=1;

delete from product_stat where reviewCount=0 and averageRate=0 and buyCount=0;
update product_stat set storeId=1;

update category set isLinkCategory=0 where categoryId=2;

/* system_config*/
update system_config set configValue = 'system,checkout,content,store,customer,order,catalog,sales,cart,sitemap' where configKey='ConfigCategorys';

/* drop table*/
drop table customer_message_list;
drop table message_list_item;
drop table message_list;
drop table message;
drop table payment_gateway;


/*附件调整*/
alter table ACCESSORY_GROUP add groupCode varchar(32) not null;
update ACCESSORY_GROUP set groupCode=LOWER(groupName); 
update ACCESSORY_GROUP set groupCode='availableColor' where ACCESSORYGROUPId=4; 
update ACCESSORY_GROUP set groupCode='hardwareColor' where ACCESSORYGROUPId=6; 
update ACCESSORY_GROUP set groupCode='furColor' where ACCESSORYGROUPId=8; 
update ACCESSORY_GROUP set groupCode='leatherMaterial' where ACCESSORYGROUPId=10; 
ALTER TABLE ACCESSORY_GROUP add unique key AK_GROUPCODE (groupCode);


/*sku 图片去除'"/"开始*/
update product_sku set image=CONCAT('cartmatic_image', image);
update product_sku set image=replace(image,'cartmatic_imagep/','');
update product_sku set image=replace(image,'cartmatic_image','');

/*productmedia 图片去除'"/"开始*/
update product_media set mediaurl=CONCAT('cartmatic_image', mediaurl);
update product_media set mediaurl=replace(mediaurl,'cartmatic_imagepm/','');
update product_media set mediaurl=replace(mediaurl,'cartmatic_image','');

/* 
初始化一个默认的供应商 
之后执行数据插入:http://192.168.1.123/StoreAdmin/supplier/supplierProduct.html?doAction=initSupplierProduct
*/
INSERT INTO `supplier` (`supplierId`,`supplierName`,`supplierCode`,`email`,`address`,`fax`,`zip`,`adminId`,`comments`,`status`,`createTime`,`updateTime`,`createBy`,`updateBy`,`version`,`webSite`,`contacts`,`bankAccount`) VALUES (-1,'N/A','NULL','null@null.com','N/A','0000',NULL,NULL,NULL,1,now(),now(),NULL,NULL,0,'','','');


/*支付数据完善,但具体的支付方式配置要直接在后台操作完善*/
update payment_method set paymentMethodCode='checkout2',processorFile='payment/checkout2_request',configFile='payment/checkout2_config',paymentMethodIcon='payment/icon/checkout2.jpg' where paymentMethodId=1;
update payment_method set paymentMethodCode='95epay',processorFile='payment/95epay_request',configFile='payment/95epay_config',paymentMethodIcon='payment/icon/95epay.gif' where paymentMethodId=2;
update payment_method set paymentMethodCode='westernUnion',processorFile='payment/westernUnion_request',configFile='payment/westernUnion_config',paymentMethodIcon='payment/icon/westernunion.gif' where paymentMethodId=3;
update payment_method set paymentMethodCode='95epay2',processorFile='payment/95epay2_request',configFile='payment/95epay2_config',paymentMethodIcon='payment/icon/95epay.gif' where paymentMethodId=4;
update payment_method set paymentMethodCode='cncard',processorFile='payment/cncard_request',configFile='payment/cncard_config',paymentMethodIcon='payment/icon/cncard.gif' where paymentMethodId=5;
update payment_method set paymentMethodCode='ips',processorFile='payment/ips_request',configFile='payment/ips_config',paymentMethodIcon='payment/icon/ips.gif' where paymentMethodId=6;
update payment_method set paymentMethodCode='realyPay-indir',processorFile='payment/realyPay_indir_request',configFile='payment/realyPay_indir_config',paymentMethodIcon='payment/icon/realyPay.gif' where paymentMethodId=7;
update payment_method set paymentMethodCode='realyPay',processorFile='payment/realyPay_request',configFile='payment/realyPay_config',paymentMethodIcon='payment/icon/realyPay.gif' where paymentMethodId=8;
update payment_method set paymentMethodCode='95epay-indir',processorFile='payment/95epay_indir_request',configFile='payment/95epay_indir_config',paymentMethodIcon='payment/icon/95epay.gif' where paymentMethodId=9;


update category set linkurl='/contactus.html' where linkUrl='/contactUs.html';
update category set linkurl=replace(linkurl,'/bagslist.html','_catalog.html');

truncate system_queue;
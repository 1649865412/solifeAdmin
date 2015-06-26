--广告模块数据库修改脚本 huangwenmin
drop table advertisement_position;
alter table ad_position_type change positionTypeId adpositiontypeid int(11) NOT NULL AUTO_INCREMENT ,drop column type;
alter table advertisement drop FOREIGN KEY FK_Reference_124,drop index FK_Reference_124,
drop column clickCount, drop column promotionId, drop column displayCount, add column isInclude SMALLINT(6), add column adpositiontypeid int(11) after advertisementId,
add constraint FK_AD_ADPOS foreign key (adpositiontypeid) references ad_position_type (adpositiontypeid) on delete restrict on update restrict;
--删除模板包
drop table template_set;

--删除目录与会员的关联
alter table CATEGORY drop FOREIGN KEY FK_Reference_121;
alter table CATEGORY drop column membershipId;




--属性模块
rename table attribute to attribute_old;

-- 2008-7-1 重构role_res
alter table APP_RESOURCE add column parentId int;
alter table APP_RESOURCE add column resPermission varchar(255);

drop table ROLE_RES;
create table ROLE_RES
(
   roleResId            int not null auto_increment,
   resourceId           int not null,
   roleId               int not null,
   permissionMask       int,
   primary key (roleResId)
);

alter table ROLE_RES add constraint FK_role_res_resource foreign key (resourceId)
      references APP_RESOURCE (resourceId) on delete cascade on update restrict;

alter table ROLE_RES add constraint FK_role_res_role foreign key (roleId)
      references APP_ROLE (roleId) on delete cascade on update restrict;
      
INSERT INTO `role_res` (`resourceId`,`roleId`) VALUES
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1),
 (6,1),
 (1,3),
 (2,4),
 (4,5),
 (3,6),
 (5,7),
 (6,8);
-- 删除没有用的角色 
delete from app_role where roleId=-2;
delete from app_role where roleId=2;
-- 删除没有用的资源
delete from app_resource where resourceid=-3;
delete from app_resource where resourceid=-2;
delete from app_resource where resourceid=-1;

alter table app_role drop column status; 
-- 统一resource的格式,全部加上'/'
update app_resource set resourceString = CONCAT('/', resourceString);


--属性模块数据库更新---
--建立V3属性模块表
DROP TABLE IF EXISTS `probiz`.`attribute`;
CREATE TABLE  `probiz`.`attribute` (
  `attributeId` int(11) NOT NULL auto_increment,
  `attributeName` varchar(64) NOT NULL,
  `attributeType` smallint(6) NOT NULL,
  `attributeDateType` int(11) NOT NULL,
  `attributeUnit` varchar(32) default NULL,
  `defaultValue` varchar(255) default NULL,
  `isRequired` tinyint(1) default NULL,
  `attributeCode` varchar(32) NOT NULL,
  `status` tinyint(1) default NULL ,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`attributeId`),
  UNIQUE KEY `AK_Key_2` USING BTREE (`attributeCode`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 COMMENT='All attributes used by product/category';

DROP TABLE IF EXISTS `probiz`.`category_attr_value`;
CREATE TABLE  `probiz`.`category_attr_value` (
  `categoryAttrValueId` int(11) NOT NULL auto_increment,
  `attributeId` int(11) default NULL,
  `categoryId` int(11) default NULL,
  `shortTextValue` varchar(255) default NULL,
  `longTextValue` text,
  `intValue` int(11) default NULL,
  `decimalValue` decimal(12,2) default NULL,
  `booleanValue` smallint(6) default NULL,
  `attributeDateType` int(11) NOT NULL,
  `dateValue` date default NULL,
  PRIMARY KEY  (`categoryAttrValueId`),
  KEY `FK_CATEGORY_V__ATTR` (`attributeId`),
  KEY `FK_ATTR_V__CATEGORY` (`categoryId`),
  CONSTRAINT `FK_ATTR_V__CATEGORY` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`),
  CONSTRAINT `FK_CATEGORY_V__ATTR` FOREIGN KEY (`attributeId`) REFERENCES `attribute` (`attributeId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `probiz`.`content_attr_value`;
CREATE TABLE  `probiz`.`content_attr_value` (
  `contentAttrValueId` int(11) NOT NULL auto_increment,
  `attributeId` int(11) default NULL,
  `contentId` int(11) default NULL,
  `shortTextValue` varchar(255) default NULL,
  `longTextValue` text,
  `intValue` int(11) default NULL,
  `decimalValue` decimal(12,2) default NULL,
  `booleanValue` smallint(6) default NULL,
  `attributeDateType` int(11) NOT NULL,
  `dateValue` date default NULL,
  PRIMARY KEY  (`contentAttrValueId`),
  KEY `FK_ATTR_V__CON` USING BTREE (`contentId`),
  KEY `FK_CONTENT_V_ATTR` (`attributeId`),
  CONSTRAINT `FK_ATTR_V_CONTENT` FOREIGN KEY (`contentId`) REFERENCES `content` (`contentId`),
  CONSTRAINT `FK_CONTENT_V_ATTR` FOREIGN KEY (`attributeId`) REFERENCES `attribute` (`attributeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `probiz`.`customer_attr_value`;
CREATE TABLE  `probiz`.`customer_attr_value` (
  `customerAttrValueId` int(11) NOT NULL auto_increment,
  `attributeId` int(11) default NULL,
  `customerId` int(11) default NULL,
  `shortTextValue` varchar(255) default NULL,
  `longTextValue` text,
  `intValue` int(11) default NULL,
  `decimalValue` decimal(12,2) default NULL,
  `booleanValue` smallint(6) default NULL,
  `attributeDateType` int(11) NOT NULL,
  `dateValue` date default NULL,
  PRIMARY KEY  (`customerAttrValueId`),
  KEY `FK_CUSTOMER_V__ATTR` (`attributeId`),
  KEY `FK_ATTR_V__CUSTOMER` (`customerId`),
  CONSTRAINT `FK_ATTR_V__CUSTOMER` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `FK_CUSTOMER_V__ATTR` FOREIGN KEY (`attributeId`) REFERENCES `attribute` (`attributeId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `probiz`.`product_attr_value`;
CREATE TABLE  `probiz`.`product_attr_value` (
  `productAttrValueId` int(11) NOT NULL auto_increment,
  `attributeId` int(11) default NULL,
  `productId` int(11) default NULL,
  `shortTextValue` varchar(255) default NULL,
  `longTextValue` text,
  `intValue` int(11) default NULL,
  `decimalValue` decimal(12,2) default NULL,
  `booleanValue` smallint(6) default NULL,
  `attributeDateType` int(11) NOT NULL,
  `dateValue` date default NULL,
  PRIMARY KEY  (`productAttrValueId`),
  KEY `FK_PRODUCT_V__ATTR` (`attributeId`),
  KEY `FK_ATTR_V__PRODUCT` (`productId`),
  CONSTRAINT `FK_ATTR_V__PRODUCT` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`),
  CONSTRAINT `FK_PRODUCT_V__ATTR` FOREIGN KEY (`attributeId`) REFERENCES `attribute` (`attributeId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=126924 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `probiz`.`salesorder_attr_value`;
CREATE TABLE  `probiz`.`salesorder_attr_value` (
  `salesordersAttrValueId` int(11) NOT NULL auto_increment,
  `attributeId` int(11) default NULL,
  `orderId` int(11) default NULL,
  `shortTextValue` varchar(255) default NULL,
  `longTextValue` text,
  `intValue` int(11) default NULL,
  `decimalValue` decimal(12,2) default NULL,
  `booleanValue` smallint(6) default NULL,
  `attributeDateType` int(11) NOT NULL,
  `dateValue` date default NULL,
  PRIMARY KEY  (`salesordersAttrValueId`),
  KEY `FK_ORDER_V__ATTR` (`attributeId`),
  KEY `FK_ATTR_V__ORDER` (`orderId`),
  CONSTRAINT `FK_ATTR_V__ORDER` FOREIGN KEY (`orderId`) REFERENCES `sales_order` (`orderId`),
  CONSTRAINT `FK_ORDER_V__ATTR` FOREIGN KEY (`attributeId`) REFERENCES `attribute` (`attributeId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--删除测试数据
delete from customer_attr_value;
delete from product_attr_value;
delete from content_attr_value;
delete from attribute;

--copy 原始数据到新的属性表中
insert into attribute(attributeId,attributeName,attributeType,defaultValue,isRequired,attributeCode,status,attributeDateType)select attributeId as attributeId,attributeName,1 as attributeType,defaultValue,isRequired,attributeCode,status,10 as attributeDateType from attribute_old;

insert into product_attr_value(productAttrValueId,attributeId,productId,longTextValue) select productAttributeValueId as productAttrValueId,attributeId,productId,inputValue as longTextValue from product_attribute_value;


update product_attr_value p set p.shortTextValue=p.longTextValue,attributeDateType=10 where length(p.longTextValue)<255;
update product_attr_value p set p.longTextValue=null where length(p.longTextValue)<255;
update product_attr_value p set p.attributeDateType=11 where p.longTextValue is not null;

--去掉现有的产品变种功能,将所有是变种产品的productkind设置为0
update product p set p.productKind=0 where p.productKind=2;

--开始删除已经废弃的旧属性模块的表及其相关表
alter table product_type_attribute drop foreign key FK_TYPE_ATT__ATT;
alter table attribute_option drop foreign key FK_ATT_ATTR;
alter table order_item_attribute drop foreign key FK_ORD_IT_ATTR;
alter table product_attribute_value drop foreign key FK_ATT_VAL__ATT;
drop table product_attribute_value;
drop table attribute_old;

--增加属性表和产品属性组之间的关联
alter table product_type_attribute add constraint FK_attribute_producttypeatt foreign key (attributeId) references attribute (attributeID) on delete restrict on update restrict;


/* added by pengzhirong*/
alter table category add column parentId int;
alter table category add constraint FK_CAT__CAT foreign key (parentId) references category (categoryId) on delete restrict on update restrict;
alter table category add column categoryType smallint not null default 1;
alter table category add column isLinkCategory smallint;
alter table category add column sortOrder int;
alter table category drop column deleted;
update category c set c.parentId=(select cc.categoryId from category_category cc where cc.subCategoryId=c.categoryId) where c.categoryId!=1;
update category c set c.sortOrder=(select cc.sortOrder from category_category cc where cc.subCategoryId=c.categoryId);

drop table category_category;

alter table category add column linkUrl varchar(255);
drop index AK_Key_Cate_2 on category; 
alter table category modify column categoryPath varchar(32);
INSERT INTO category (categoryType,categoryName,categoryCode,linkUrl,isLinkCategory,sortOrder,status,parentId) VALUES
 (2,'首页','mainPage','index.html',0,1,1,1),
 (2,'产品菜单','product','',0,10,1,1),
 (2,'自定义栏目','custom','',0,20,0,1),
 (2,'网站资讯','infos','content/contents=doActionshowContentByCode=columnCodeinfos=templateshowContents.html',0,30,1,1),
 (2,'优惠信息','promoInfo','content/promoInfo.html',1,50,1,1);
 
alter table content drop foreign key FK_fk_content_contentTypeID;
alter table content drop column contentTypeId;
alter table content add column categoryId int;
alter table content add constraint FK_CONTENT__CAT foreign key (categoryId) references category (categoryId) on delete restrict on update restrict;

drop table content_type;
/* above added by pengzhirong*/

drop table checkout_flow;
/* By Winston*/


--简化customer、appuser、appadmin模块

alter table app_user add column membershipId int(11)   NULL  after version, add column providerId int(11)   NULL  after membershipId, add column totalPoints int(11)   NULL  after providerId, add column userPosition varchar (128)   NULL  after totalPoints, add column department varchar (64)   NULL  after userPosition, add column passwordHint varchar (128)   NULL  after department, add column passwordHintAnswer varchar (255)   NULL  after passwordHint, add column customerPosition varchar (128)   NULL  after passwordHintAnswer, add column registerTime datetime   NULL  after customerPosition, add column shoppingCartId int(11)   NULL  after registerTime, add column lastVisitedTime datetime   NULL  after shoppingCartId, add column preferredCurrency varchar (8)   NULL  after lastVisitedTime, add column note varchar(1024)   NULL  after preferredCurrency;
alter table app_user add foreign key FK_app_user(providerId) references provider (providerId);
drop table app_admin;
--开始更新外键
alter table product_review drop foreign key FK_Reference_162;
alter table product_review add foreign key FK_product_review(customerId) references app_user (appuserId);

alter table product_rating drop foreign key FK_PROD_CUST2;
alter table product_rating add foreign key FK_product_rating(customerId) references app_user(appuserId);

alter table favorite drop foreign key FK_FAVO_CUSTOMER;
alter table favorite add foreign key FK_favorite(customerId) references app_user (appuserId);

alter table shop_point drop foreign key FK_SHOP_POI_CUS;
alter table shop_point add foreign key FK_shop_point(customerId) references app_user (appuserId);

alter table shop_point_history drop foreign key FK_SHOP_CUST2;
alter table shop_point_history add foreign key FK_shop_point_history(customerId) references app_user (appuserId);

alter table saved_product_list drop foreign key FK_SAVE_PRO_CUS;
alter table saved_product_list add foreign key FK_saved_product_list(customerId) references app_user (appuserId);

alter table wishlist drop foreign key FK_WISH_CUSTOMER;
alter table wishlist add foreign key FK_wishlist(customerId) references app_user (appuserId);

alter table customer_attr_value drop foreign key FK_ATTR_V__CUSTOMER;
alter table customer_attr_value add foreign key FK_customer_attr_value(customerId) references app_user(appuserId);

--删除customer表
drop table customer;

--简化customer、appuser、appadmin模块  完成--


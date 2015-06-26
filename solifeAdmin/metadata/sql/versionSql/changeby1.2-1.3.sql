--mysql:
alter table category_category add column parentCategoryPath varchar(32) null;
alter table category_category change column categoryPath subCategoryPath varchar(32);
--add by csx 2007-2-6
alter table `user_role` DROP FOREIGN KEY `FK_APP_APP_ROLE`;
ALTER TABLE `user_role` ADD CONSTRAINT `FK_APP_APP_ROLE` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`) on delete cascade;

alter table MEDIA_EFFECT change column mediaEffectId mediaEffectId int not null auto_increment;
alter table TEMPLATE_TYPE change column templateTypeID   templateTypeID   int not null auto_increment;

ALTER TABLE i18ntext_item ADD COLUMN fileName VARCHAR(64) NULL;
ALTER TABLE i18ntext_item ADD COLUMN type smallint NULL;
UPDATE i18ntext_item SET type=1;

ALTER TABLE recommended_type ADD templatePath varchar(128) NULL;

update  category set categoryCode='cat_code_1' ,categoryPath='cat_path_1' where categoryId=1;
ALTER TABLE system_config change COLUMN category category varchar(64) not null; 

---add by csx 2007-2-16 fro version 1.3   ---- start

DROP TABLE IF EXISTS `shop_point`;
CREATE TABLE `shop_point` (
  `shopPointId` int(11) NOT NULL auto_increment,
  `customerId` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `gainedTotal` int(11) NOT NULL,
  `usedTotal` int(11) NOT NULL,
  `updateTime` datetime default NULL,
  PRIMARY KEY  (`shopPointId`),
  UNIQUE KEY `customerId` (`customerId`)
);

alter table shop_point add constraint FK_Shop_Point_Customer foreign key (customerId)
      references customer (customerId) on delete restrict on update restrict;
      
DROP TABLE IF EXISTS `shop_point_history`;
CREATE TABLE `shop_point_history` (
  `shopPointHistoryId` int(11) NOT NULL auto_increment,
  `customerId` int(11) NOT NULL,
  `shopPointType` smallint(6) NOT NULL,
  `description` varchar(128) default NULL,
  `amount` int(11) NOT NULL,
  `createTime` datetime default NULL,
  PRIMARY KEY  (`shopPointHistoryId`),
  KEY `FK_Shop_Point_History_Customer` (`customerId`)
);

alter table shop_point_history add constraint FK_Shop_Point_History_Customer foreign key (customerId)
      references customer (customerId) on delete restrict on update restrict;
      
-----add by csx 2007-2-16 fro version 1.3   ---- end

ALTER TABLE i18ntext_item DROP COLUMN `createBy`,
 DROP COLUMN `createTime`,
 DROP COLUMN `updateBy`,
 DROP COLUMN `updateTime`,
 DROP COLUMN `version`;

--2007.2.25-2007.3.2
alter table feedback add column givenShopPointAction smallint default 0;

alter table content_Type add column parentId  int null;
alter table company_info add column logo varchar(128) null ;

create table STATIC_PAGE_LOG
(
   staticId             int not null auto_increment,
   startTime            datetime not null,
   endTime              datetime not null,
   staticType           smallint not null,
   primary key (staticId)
);

delete from system_config; 

drop table static_page;
drop table staticallize_history;
drop table staticallize_item;

----
update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000047400036B65797400206E77396C303873636474366C6A39303136653031646C633179346961306935647400086D65726368616E7474000F6D616E31393030403132362E636F6D7400107061796D656E7447617465776179496474000132740008646F416374696F6E74001073617665436F6E666967416374696F6E78
where paymentGatewayCode='alipay';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000674000C6D65726368616E745F75726C740033687474703A2F2F36312E3134342E36312E3133342F7061796D656E742F6765744461746146726F6D476174657761792E6A73707400036B65797400103031323334353637383931323334353674000B6D65726368616E745F69647400113838393930363035313439333335313335740008646F416374696F6E74001073617665436F6E666967416374696F6E7400107061796D656E74476174657761794964740002313174000863757272656E63797400013178
where paymentGatewayCode='99bill';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000005740009746573744D6F64656C7400015974000876656E646F724964740006363137313935740008646F416374696F6E74001073617665436F6E666967416374696F6E7400107061796D656E744761746577617949647400013774000A736563726574776F726474000C6D79736563726574776F726478
where paymentGatewayCode='checkout2';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000067400036B657974000A303132333435363738397400107061796D656E744761746577617949647400023133740008646F416374696F6E74001073617665436F6E666967416374696F6E74000B765F6D6F6E657974797065740003434E59740005765F6D69647400083230303330333131740005765F75726C740033687474703A2F2F36312E3134342E36312E3133342F7061796D656E742F6765744461746146726F6D476174657761792E6A737078
where paymentGatewayCode='china_bank';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000008740006635F7061737374000A32637578687966723964740009746573744D6F64656C7400014E740008646F416374696F6E74001073617665436F6E666967416374696F6E740005635F6D69647400073130303834333374000B635F6D6F6E657974797065740001307400107061796D656E744761746577617949647400023132740008635F72657475726C740033687474703A2F2F36312E3134342E36312E3133342F7061796D656E742F6765744461746146726F6D476174657761792E6A737074000A635F6C616E67756167657400013078
where paymentGatewayCode='cncard';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000874000D43757272656E63795F54797065740003524D42740009746573744D6F64656C740001597400084D65725F436572747400804744674C7777644B323730516A31773478686F386C79547052515A56394A6D3578344E77574F545468556134664D6845424B396A4F5846724B52543678686C4A75553246456138396F76307279796A664A7575506B63477A4F3543655678355A49726B6B743161426C5A563336795376484F4D634E7638726E635269793344517400044C616E677400024742740008646F416374696F6E74001073617665436F6E666967416374696F6E7400107061796D656E744761746577617949647400013474000752657454797065740001307400084D65725F436F646574000630303030313578
where paymentGatewayCode='ips';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C77080000001000000005740009746573744D6F64656C740001597400076C6F676F55726C740027687474703A2F2F36312E3134342E36312E3133342F7061796D656E742F70726F62697A2E6769667400107061796D656E7447617465776179496474000136740008646F416374696F6E74001073617665436F6E666967416374696F6E7400076163636F756E747400107465737431406E6F636865782E636F6D78
where paymentGatewayCode='nochex';

update payment_gateway set configData=0xACED0005737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000574000D63757272656E63795F636F6465740003434E597400107061796D656E7447617465776179496474000131740008646F416374696F6E74001073617665436F6E666967416374696F6E74001173656C5F63757272656E63795F636F6465740003434E59740008627573696E65737374000F6D616E31393030403132362E636F6D78
where paymentGatewayCode='paypal_china';

----
---- 2007.3.12
--sales_order for shop point pay
alter table sales_order add column shopPointPayCount int default 0;
alter table sales_order add column shopPointPayMoney DECIMAL(12,2) default 0;
alter table category add column(qtyUnitCode varchar(32),lengthUnitCode varchar(32),weightUnitCode varchar(32));
alter table category add column(departmentCode varchar(32));

-- sales_order for payment
alter table sales_order add column havePayedMoney DECIMAL(12,2) default 0;
alter table sales_order add column paymentTypesKey varchar(32) null;
----
---- 2007.3.19
create table SYSTEM_VERSION
(
   id                   int not null auto_increment,
   sysVersion           varchar(32) not null,
   productCode          varchar(64) not null,
   domainName           varchar(256) not null,
   licenseKey           varchar(32) not null,
   createTime           datetime not null,
   updateTime           datetime,
   version              int default 0,
   primary key (id)
);


insert into system_version(sysVersion,productCode,domainName,licenseKey,createTime) 
values('v1.3','ProBIZ eStore','localhost','yCebOVM-7552-6383324191','2007-03-16');

alter table PATCHEXECUTEHISTORY rename as PATCH_EXECUTE_HISTORY;
----
---- 2007.3.28
alter table sales_order add column paymentMethodNameKey varchar(32) null;

INSERT INTO `i18ntext` (`textKey`,`category`,`description`,`moduleId`,`createBy`,`createTime`,`updateTime`,`updateBy`,`version`) VALUES 
 ('salesOrder.giftpoint.description','SalesOrder',NULL,NULL,NULL,NULL,NULL,NULL,0);
 
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES 
 ('salesOrder.giftpoint.description','Get shop point for place order, the orderNo is: ','SalesOrder','en'),
 ('salesOrder.giftpoint.description','该积分是从下订单操作中取得，订单编号是：','SalesOrder','zh_CN');
 
alter table sales_order add column isPresentGift smallint default 0; 

INSERT INTO `i18ntext`(`textKey`,`category`,`description`,`moduleId`,`createBy`,`createTime`,`updateTime`,`updateBy`,`version`) VALUES
 ('Membership_Detail_0','Membership_Detail_',NULL,NULL,NULL,NULL,NULL,NULL,0),
 ('Membership_Name_0','Membership_Name_',NULL,NULL,NULL,NULL,NULL,NULL,0);

INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES
 ('Membership_Detail_0','Anonymous Membership Descrption..','Membership_Detail_','en'),
 ('Membership_Name_0','Anonymous Membership','Membership_Name_','en'),
 ('Membership_Detail_0','匿名会员','Membership_Detail_','zh_CN'),
 ('Membership_Name_0','匿名会员','Membership_Name_','zh_CN');

Update i18ntext_item set type=1 where type is null;

update membership set membershipLevel=membershipLevel+10 where membershipLevel!=-2;

update membership set membershipNameKey='Membership_Name_0',
membershipDetailKey='Membership_Detail_0' where membershipLevel=-2;

INSERT INTO `membership` (`membershipNameKey`,`membershipDetailKey`,`membershipLevel`,`status`,`deleted`,`version`) values
('Membership_Name_1','Membership_Detail_1',1,1,0,0);
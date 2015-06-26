alter table AD_POSITION_TYPE ADD COLUMN `storeId` int(11) ;
alter table AD_POSITION_TYPE add constraint FK_AD_P__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

ALTER TABLE ADVERTISEMENT DROP COLUMN width;
ALTER TABLE ADVERTISEMENT DROP COLUMN height;
ALTER TABLE ADVERTISEMENT DROP INDEX `AK_ADVERTISEMENTNAME`;

ALTER TABLE AD_POSITION_TYPE DROP INDEX `AK_POSITIONNAME`;
ALTER TABLE AD_POSITION_TYPE add unique key AK_POSITIONNAME (positionName, storeId);



create table ADMIN_INFO
(
   adminInfoId          int not null auto_increment,
   department           varchar(64),
   pagingSetting        int ,
   primary key (adminInfoId)
);

ALTER TABLE `app_user`
DROP COLUMN `zip`,
DROP COLUMN `department`;

alter table APP_USER add COLUMN adminInfoId int;
alter table APP_USER add constraint FK_USER__ADMIN_INFO foreign key (adminInfoId)
      references ADMIN_INFO (adminInfoId) on delete restrict on update restrict;
      

/* Build 20121113 */
INSERT INTO `payment_method` (`paymentMethodName`,`paymentMethodDetail`,`paymentMethodType`,`protocol`,`sortOrder`,`isCod`,`status`,`version`,`paymentMethodCode`,`testModel`,`paymentMethodIcon`,`configFile`,`processorFile`,`configData`) VALUES 
 ('GleePay','GleePay',1,'HTTP',22,0,2,0,'gleepay','N','payment/icon/gleepay.gif','payment/gleepay_config','payment/gleepay_request',NULL);
 
INSERT INTO `payment_method` (`paymentMethodName`,`paymentMethodDetail`,`paymentMethodType`,`protocol`,`sortOrder`,`isCod`,`status`,`version`,`paymentMethodCode`,`testModel`,`paymentMethodIcon`,`configFile`,`processorFile`,`configData`) VALUES 
 ('GleePay_indir','GleePay_indir',1,'HTTP',22,0,2,0,'gleepay_indir','N','payment/icon/gleepay.gif','payment/gleepay_indir_config','payment/gleepay_indir_request',NULL);
 
/*catalog 增加销售规则，作为添加产品默认值*/
ALTER TABLE `catalog` ADD COLUMN `availabilityRule`  smallint(6) NULL;

/* Build 20121127 删除无用字段 */
ALTER TABLE `app_user` DROP COLUMN `lastVisitedTime`;
ALTER TABLE `app_user` ADD COLUMN `registerIpAddress` varchar(64) NULL;

/* 2012-12-14 */
alter table TRANSFER_RECORD add amount numeric(12,2) null;

/* 2013-1-14 */
alter table ADMIN_INFO add productViewSetting   smallint not null default 0;
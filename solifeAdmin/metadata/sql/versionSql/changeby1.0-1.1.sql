--2006.12.4-2006.12.11 --
alter table system_message modify column messageHtml varchar(512);

update report set rptDesignFile='newsales_hotSale.rptdesign',url='report/sales_hotSale' where reportid=1;
update report set rptDesignFile='newsales_hotSaleCategory.rptdesign',url='report/sales_hotSaleCategory' where reportid=2;
update report set rptDesignFile='newsales_hotSaleManufacturer.rptdesign',url='report/sales_hotSaleManufacturer' where reportid=3;
update report set rptDesignFile='newsales_salesByMoonth.rptdesign' where reportid=7;
update report set rptDesignFile='newsales_SOByMoonth.rptdesign' where reportid=8;
update report set rptDesignFile='newnostock_product.rptdesign' where reportid=11;
update report set rptDesignFile='newsummary_report.rptdesign' where reportid=12;
update report set rptDesignFile='newsal_gather.rptdesign' where reportid=13;


alter table system_message drop index appuserId;
create index appuserId on system_message(appuserId);
update app_media set mediaTypeId=1 where mediaTypeId is null;

-- add by csx 2006-12-08 --
ALTER TABLE `payment_history` ADD COLUMN `paymentGatewayId` integer DEFAULT NULL;


--2006.12.11-2006.12.18 --
alter table sales_order modify column isCod integer null;

update report set rptDesignFile='newsales_sumGroup.rptdesign' where reportid=5;
insert into report(reportId,reportFolderId,title,description,rptDesignFile,url,isSystem)
	values(14,1,"Profit Report In Month","Profit List Report","newprofit_report.rptdesign","report/sales_salesByMonth",1);

/*2006-12-13*/	
/*faith*/
alter table shoppingcart DROP FOREIGN KEY FK_CART_REF_ADDR_1;
alter table shoppingcart DROP FOREIGN KEY FK_CART_REF_ADDR_2;
alter table shoppingcart drop column shippingAddressId;
alter table shoppingcart drop column billingAddressId;	

/* mfr 12-14*/
alter table product add( planStartTime datetime null,planEndTime datetime null,publishTime datetime null);


ALTER TABLE customer ADD COLUMN preferredCurrency varchar(8) DEFAULT NULL;
alter table app_user add column preferredLanguage varchar(8) default null;
alter table app_user add column lastLoginTime datetime default null;

--2006.12.25--
ALTER TABLE system_url modify COLUMN url varchar(512) DEFAULT NULL;
Insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,isSystem,version) values('content.front.leftMenu','/menu/defaultLeftMenu','','content',1,1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,isSystem,version) values('content.front.topMenu','/menu/defaultTopMenu','','content',1,1,1,0);

update system_config set isSystem = 2 where isSystem is null;
update system_config set category="content" where configKey="content.template.defaultType";


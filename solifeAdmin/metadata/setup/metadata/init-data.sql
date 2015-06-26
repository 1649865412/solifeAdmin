/*==============================================================*/
/* Database name:  probiz                                       */
/* DBMS name:      all                                          */
/* Created on:     2006-11-25 16:00:55                          */
/* Description:    setup init data                              */
/*==============================================================*/

INSERT INTO system_config (configKey,description,category,configType,configValue,options,dataType,isSystem,createBy,updateBy,version) VALUES
('StoreFrontSiteUrl',NULL,'system.general.url.',1,'@system-storefront-siteUrl@','',1,2,-1,1,1),
('StoreAdminSiteUrl',NULL,'system.general.url.',1,'@system-storeadmin-siteUrl@','',1,2,-1,1,1),
('StoreFrontInstallationPath',NULL,'system.general.path.',1,'@general-store-path@','',1,2,-1,1,1),
('CachableUrls',NULL,'system.cache.page.',1,'/mainMenu.html,/catalog/category,/sales/ProductSeries,sales/promotion','',1,2,-1,1,1);

UPDATE currency set status=1,isDefault=1,exChangeRate=1 WHERE currencyCode='@default-currency@';

insert into system_version(sysVersion,productCode,domainName,licenseKey,createTime)
values('v2.0','ProBIZ eStore','@webapp-domain@','@webapp-license@','@webapp-createtime@');
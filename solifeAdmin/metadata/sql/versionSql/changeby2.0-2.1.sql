--重命名权限表   
ALTER   TABLE resource  RENAME   TO   app_resource;

alter table user_role DROP COLUMN userRoleId;
alter table user_role add PRIMARY key (roleId,appuserId);

alter table role_res DROP COLUMN roleResId;
alter table role_res add PRIMARY key (roleId,resourceId);

alter table message_list_item drop column messageListItemId;
alter table message_list_item add primary key (messageListId,messageId);

--公司信息添加字段
alter table company_info add column qq varchar(128) null;
alter table company_info add column msn varchar(128) null;
alter table company_info add column telephoneHotLine varchar(128) null;


--订单sales_order增加发票抬头.
alter table sales_order add column invoiceTitle varchar(128) null;

--删除一个没有用的字段
alter table product_media drop column mediaEffectId;
--删除没有用的表
drop table product_tab;
drop table tab;
drop table media_effect;
drop table promotion_exception;
--email杂志功能
create table MAGAZINE_EMAIL
(
   magazineEmailId      int not null auto_increment,
   title                varchar(100) not null,
   context              text,
   sendTime             datetime,
   status               smallint,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   primary key (magazineEmailId)
);

truncate TABLE `report`;

INSERT INTO `report` (`reportId`,`reportFolderId`,`title`,`description`,`rptDesignFile`,`url`,`param`,`isSystem`,`version`) VALUES 
 (1,1,'热卖产品','某段时间内的最热卖的产品','newsales_hotSale.rptdesign','report/sales_hotSale',NULL,1,0),
 (2,1,'热卖类别','某段时间内的最热卖的产品类别','newsales_hotSaleCategory.rptdesign','report/sales_hotSaleCategory',NULL,1,0),
 (3,1,'最受欢迎的制品商','某段时间内最受欢迎的制品商','newsales_hotSaleManufacturer.rptdesign','report/sales_hotSaleManufacturer',NULL,1,0),
 (4,1,'客户购买力分析','列出最有购买力的客户和最有购买力的会员级别','sales_withCustomer.rptdesign','report/sales_with_a_period',NULL,1,0),
 (5,1,'售购日期报表','统计各时间内的售购情况','newsales_sumGroup.rptdesign','report/sales_salesOrderByTime',NULL,1,0),
 (6,1,'订单状态分析','订单状态分析','sales_orderStatus.rptdesign','report/sales_with_a_period',NULL,1,0),
 (7,1,'月订销售汇总报表','月订销售汇总报表','newsales_salesByMoonth.rptdesign','report/sales_salesByMonth',NULL,1,0),
 (8,1,'月订单明细报表','列出一个月的所有订单列表','newsales_SOByMoonth.rptdesign','report/sales_salesByMonth',NULL,1,0);
INSERT INTO `report` (`reportId`,`reportFolderId`,`title`,`description`,`rptDesignFile`,`url`,`param`,`isSystem`,`version`) VALUES 
 (9,1,'产品销售趋势分析','产品销售趋势分析','sales_productTrend.rptdesign','report/sales_productTrendChart',NULL,1,0),
 (10,1,'产品类别销售分析','产品类别销售分析','sales_catalogAnalyse.rptdesign','report/sales_analyseCatalog',NULL,1,0),
 (11,1,'无库存的产品','列出已经没有库存的产品','newnostock_product.rptdesign','report/newnostock_product',NULL,1,0),
 (12,1,'汇总报表','汇总报表','newsummary_report.rptdesign','report/newsummary_report',NULL,1,0),
 (13,1,'销售统计报表','汇总每日的销售情况','newsal_gather.rptdesign','report/newsales_GatherReport',NULL,1,0),
 (14,1,'月利润报表','月利润报表(根据成本价计算)','newprofit_report.rptdesign','report/sales_salesByMonth',NULL,1,0);

update system_version set sysVersion='2.1', productCode='Merchant One', updateTime=now();
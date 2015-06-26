truncate TABLE `ad_position_type`;
INSERT INTO `ad_position_type` (`positionTypeId`,`positionName`,`type`,`height`,`width`,`displayType`,`status`,`description`,`templatePath`,`version`) VALUES 
 (1,'mid',1,235,600,1,NULL,NULL,'advertisement/adViewMid',0),
 (2,'right1',1,265,222,1,NULL,NULL,'advertisement/adView',0),
 (3,'right2',1,265,222,1,NULL,NULL,'advertisement/adView',0),
 (4,'left1',1,180,150,1,NULL,NULL,'advertisement/adView',0),
 (5,'left2',1,180,150,1,NULL,NULL,'advertisement/adView',0); 


truncate TABLE `user_role`;
truncate TABLE `app_admin`;
truncate TABLE `app_user`;
truncate TABLE `role_res`;
truncate TABLE `app_role`;
truncate TABLE `app_resource`;

INSERT INTO `app_user` (`appuserId`,`username`,`password`,`userType`,`title`,`firstname`,`lastname`,`email`,`fax`,`zip`,`telephone`,`preferredLanguage`,`lastLoginTime`,`status`,`isLocked`,`deleted`,`createBy`,`updateBy`,`createTime`,`updateTime`,`version`) VALUES
 (-2,'ANONYMOUS_USER','PASS_user_ANONYMOUS',0,'Mr.','ANONYMOUS','USER','ANONYMOUS_USER@ANONYMOUS.com','88888888','111111','88888888',NULL,NULL,0,1,0,NULL,NULL,NULL,NULL,5),
 (1,'admin','d033e22ae348aeb5660fc2140aec35850c4da997',1,'Mr.','admin','Raible','admin@admin.com',NULL,NULL,NULL,NULL,NULL,1,0,0,NULL,NULL,NULL,NULL,1);

INSERT INTO `app_admin` (`appAdminId`,`department`,`userPosition`,`version`) VALUES
 (1,'admin','超级管理员',0);

INSERT INTO `app_resource` (`resourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES
 (-3,'登录',0,'login*',''),
 (-2,'主页',0,'*.html*','后台主页所有页面'),
 (-1,'所有',0,'**/*.html*','所有页面'),
 (1,'销售',0,'sales/*','销售模块下的所有页面'),
 (2,'产品',0,'catalog/*','产品模块下的所有页面'),
 (3,'内容',0,'content/*','内容模块下的所有页面'),
 (4,'顾客',0,'customer/*','顾客模块下的所有页面'),
 (5,'系统',0,'system/*','系统模块下的所有页面'),
 (6,'订单',0,'order/*','订单模块下的所有页面');

 INSERT INTO `app_role` (`roleId`,`roleName`,`roleDetail`,`isSystem`,`createTime`,`updateTime`,`status`,`version`) VALUES
 (-2,'forDataSync','Use by system only, not granted any access right except data synchronization with StoreFront.',1,NULL,NULL,1,1),
 (-1,'ROLE_ANONYMOUS','匿名角色，属于系统保留角色，不能把该角色授予任一用户。',1,NULL,NULL,2,1),
 (1,'admin','系统超级管理角色，具有访问系统所有资源的权限',1,NULL,NULL,1,1),
 (2,'customer','顾客角色，用于前台顾客',1,NULL,NULL,1,1),
 (3,'销售管理员','管理销售模块',1,NULL,NULL,1,1),
 (4,'产品管理员','管理产品模块',1,NULL,NULL,1,1),
 (5,'顾客管理员','管理顾客模块',1,NULL,NULL,1,1),
 (6,'内容管理员','管理内容模块',1,NULL,NULL,1,1),
 (7,'系统管理员','管理系统模块',1,NULL,NULL,1,1),
 (8,'订单管理员','管理订单模块',1,NULL,NULL,1,1);
 
INSERT INTO `app_role` (`roleName`,`roleDetail`,`isSystem`,`createTime`,`updateTime`,`status`,`version`) VALUES
 ('OrderCancel','Groups to cancel order',1,NULL,NULL,1,1),
 ('OrderConfirm','Groups to confirm order',1,NULL,NULL,1,1),
 ('Buyer','Groups to buy the product to warehouse',1,NULL,NULL,1,1),
 ('Warehouseman','Groups to packing and despatch',1,NULL,NULL,1,1),
 ('Cashier','Payer or get the cach',1,NULL,NULL,1,1);
 
 INSERT INTO `user_role` (`appuserId`,`roleId`,`version`) VALUES
 (1,1,1);

 INSERT INTO `role_res` (`resourceId`,`roleId`) VALUES
 (-3,-1),
 (-2,1),
 (-1,1),
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1),
 (6,1),
 (-2,3),
 (-1,3),
 (1,3),
 (-2,4),
 (-1,4),
 (2,4),
 (-2,5),
 (-1,5),
 (4,5),
 (-2,6),
 (-1,6),
 (3,6),
 (-2,7),
 (-1,7),
 (5,7),
 (-2,8),
 (-1,8),
 (6,8);



truncate TABLE `category`;
INSERT INTO `category` (`categoryId`,`categoryName`,`categoryDescription`,`categoryCode`,`productCount`,`subCategoryCount`,`metaKeyword`,`metaDescription`,`membershipId`,`status`,`createBy`,`updateBy`,`createTime`,`updateTime`,`templatePath`,`categoryPath`,`imageUrl`,`deleted`,`version`) VALUES 
 (1,'/',NULL,'cat_code_1',0,0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'1',NULL,0,1);
 
truncate TABLE `currency`;
INSERT INTO `currency` (`currencyId`,`currencyCode`,`currencyName`,`currencyUtilNameKey`,`isDefault`,`status`,`sortOrder`,`exchangeRate`,`currencySymbol`,`version`) VALUES 
 (1,'USD','美元',NULL,NULL,0,NULL,'1.0000','$',0),
 (2,'CNY','人民币',NULL,1,1,NULL,'1.0000','&yen;',0),
 (3,'JPY','日元',NULL,NULL,0,NULL,'102.2200','&yen;',0),
 (4,'EUR','欧元',NULL,NULL,0,NULL,'0.7340','&euro;',0);
 


truncate TABLE `membership`;
INSERT INTO `membership` (`membershipId`,`membershipName`,`membershipDetail`,`membershipLevel`,`status`,`deleted`,`version`,`upgradeShopPoint`) VALUES
 (1,'匿名顾客','匿名顾客',-2,1,0,0,-1);
 INSERT INTO `membership` (`membershipId`,`membershipName`,`membershipDetail`,`membershipLevel`,`status`,`deleted`,`version`,`upgradeShopPoint`) VALUES
 (2,'普通会员','普通会员',1,1,0,0,-1);


truncate TABLE `payment_gateway`;
INSERT INTO `payment_gateway` (`paymentGatewayId`,`paymentGatewayName`,`paymentGatewayDetail`,`processorFile`,`configFile`,`configData`,`gatewayIcon`,`isAdded`,`type`,`testModel`,`paymentGatewayCode`,`version`) VALUES 
 (1,'Paypal(贝宝)','Paypal China(贝宝)','payment/paypal_cn_request','payment/paypal_cn_config',NULL,'payment/icon/paypal_cn.gif',0,'C','N','paypal_china',60),
 (2,'AliPay(支付宝)','AliPay Description(支付宝)','payment/alipay_request','payment/alipay_config',NULL,'payment/icon/alipay.gif',0,'C','N','alipay',94),
 (3,'VeriSign Link','VeriSign Link','payment/verisign_payflow_link_request','payment/verisign_payflow_link_config',NULL,'payment/icon/verisign.gif',0,'C','N','verisign_link',79),
 (4,'IPS','IPS(环讯)','payment/ips_request','payment/ips_config',NULL,'payment/icon/ips.gif',0,'C','Y','ips',132),
 (5,'WorldPay','World Pay Description (UK)','payment/worldpay_request','payment/worldpay_config',NULL,'payment/icon/worldpay.gif',0,'C','N','wordpay',32),
 (6,'NoChex','NoChex Payment Gateway Description','payment/nochex_request','payment/nochex_config',NULL,'payment/icon/nochex.gif',0,'C','Y','nochex',36),
 (7,'Checkout 2','Checkout Version 2','payment/checkout2_request','payment/checkout2_config',NULL,'payment/icon/checkout2.jpg',0,'C','Y','checkout2',48);
INSERT INTO `payment_gateway` (`paymentGatewayId`,`paymentGatewayName`,`paymentGatewayDetail`,`processorFile`,`configFile`,`configData`,`gatewayIcon`,`isAdded`,`type`,`testModel`,`paymentGatewayCode`,`version`) VALUES 
 (8,'eWAY. Shared payment','eWay. Shared payment','payment/eway_sp_request','payment/eway_config',NULL,'payment/icon/eway.gif',0,'C','Y','eway_share',24),
 (9,'Money Bookers','Money Bookers','payment/money_bookers_request','payment/money_bookers_config',NULL,'payment/icon/moneybookers.gif',0,'C','N','money_bookers',20),
 (10,'99Bill Payment(快钱)','99Bill Payment(快钱)','payment/99bill_request','payment/99bill_config',NULL,'payment/icon/99bill.gif',0,'C','N','99bill',31),
 (11,'cncard(云网)','cncard(云网)','payment/cncard_request','payment/cncard_config',NULL,'payment/icon/cncard.gif',0,'C','N','cncard',65),
 (12,'ChinaBank(网银在线)','ChinaBank(网银在线)','payment/chinabank_request','payment/chinabank_config',NULL,'payment/icon/chinabank.gif',0,'C','N','china_bank',27),
 (13,'Paypal','Paypal','payment/paypal_request','payment/paypal_config',NULL,'payment/icon/paypal.gif',0,'C','N','paypal',0);
INSERT INTO `payment_gateway` (`paymentGatewayId`,`paymentGatewayName`,`paymentGatewayDetail`,`processorFile`,`configFile`,`configData`,`gatewayIcon`,`isAdded`,`type`,`testModel`,`paymentGatewayCode`,`version`) VALUES 
 (14,'NPS内卡','NPS','payment/nps_request','payment/nps_config',NULL,'payment/icon/nps.gif',0,'C','N','nps',0),
 (15,'NPS外卡','NPS','payment/nps_out_request','payment/nps_out_config',NULL,'payment/icon/nps.gif',0,'C','N','nps_out',0),
 (16,'YeePay(易宝支付)','YeePay(易宝支付)','payment/yeepay_request','payment/yeepay_config',NULL,'payment/icon/yeepay.png',0,'C','N','yeepay',0),
 (17,'e-gold','e-gold payment gateway','payment/egold_request','payment/egold_config',NULL,'payment/icon/egold.gif',0,'C','N','egold',0);
 
INSERT INTO `payment_gateway` (`paymentGatewayId`,`paymentGatewayName`,`paymentGatewayDetail`,`processorFile`,`configFile`,`configData`,`gatewayIcon`,`isAdded`,`type`,`testModel`,`paymentGatewayCode`,`version`) VALUES
(18,'易付通(xPay)','XPay','payment/xpay_request','payment/xpay_config',NULL,'payment/icon/xpay.gif',0,'C','N','xpay',0);

truncate TABLE `payment_method`;
INSERT INTO `payment_method` (`paymentMethodId`,`paymentGatewayId`,`paymentMethodName`,`paymentMethodDetail`,`paymentMethodType`,`protocol`,`sortOrder`,`isCod`,`status`,`version`) VALUES 
 (1,NULL,'现金支付','按照客户提交的订单内容，在承诺配送时限内送达顾客指定交货地点后，双方当时验收商品、当时交纳货款(现金形式)的一种结算支付方式。',2,'HTTP',1,0,1,1),
 (2,NULL,'邮局汇款','顾客将订单金额通过邮政部门汇到本网站的一种结算支付方式。',2,'HTTP',1,0,1,1);



truncate TABLE `recommended_type`;
INSERT INTO `recommended_type` (`recommendTitle`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES 
 ('热卖产品','hot_sell',1,1,1,NULL,1,1,1,0),
 ('最新产品','new_arrival',3,1,1,NULL,1,1,1,0),
 ('人气收藏','my_favorites',3,1,1,NULL,1,1,1,0),
 ('鞋品预售','xiepin_yushou',3,1,1,NULL,1,1,1,0),
 ('配饰预售','peishi_yushou',3,1,1,NULL,1,1,1,0), 
 ('购买此商品的顾客也购买了','also_buy',4,1,1,NULL,1,1,0,0),
 ('特色产品','feature_product',0,0,1,NULL,1,1,1,0),
 ('相似产品推荐','similar_product',5,0,1,NULL,1,1,0,0),
 ('同品牌的其他相似产品','same_brand',7,1,1,NULL,1,1,0,0),
 ('价格相近的其他同类产品','similar_price',6,1,1,NULL,1,1,0,0),
 ('可选配件','accessories',0,0,1,NULL,1,1,0,0);
 
truncate TABLE `report`;
truncate TABLE `report_folder`;
INSERT INTO `report_folder` (`reportFolderId`,`title`,`isSystem`,`version`) VALUES 
 (1,'Sales reports',1,0),
 (2,'System statistics',1,0);
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


truncate TABLE `carrier`;
INSERT INTO `carrier` (`carrierId`,`carrierName`,`carrierAddress`,`carrierAddress2`,`linkman`,`telephone`,`fax`,`email`,`zip`,`status`,`deleted`,`version`) VALUES 
 (1,'邮局EMS','','','','','','','',1,0,0);
truncate TABLE `menu`;
INSERT INTO `menu` (`menuId`,`menuName`,`url`,`categoryId`,`sortOrder`,`status`) VALUES 
 (1,'主页','index.html',1,0,NULL);
INSERT INTO `metric_type` (`metricTypeId`,`metricTypeCode`,`metricTypeName`,`description`,`version`) VALUES 
 (1,'w','重量单位','重量单位',0),
 (2,'l','长度单位','长度单位',0),
 (3,'q','数量单位','如:个,件,盒',0),
 (4,'v','容量单位','容量单位',0);
INSERT INTO `metric_unit` (`metricUnitId`,`metricTypeId`,`metricUnitCode`,`metricUnitName`,`isDefault`,`convertRate`,`version`) VALUES 
 (1,1,'kg','千克',1,'1.0000',4),
 (2,1,'g','克',0,'0.0010',4),
 (3,2,'cm','里米',1,'1.0000',4),
 (4,2,'mm','毫米',0,'0.1000',3),
 (5,2,'m','米',0,'100.0000',2),
 (6,3,'jian','件',0,'0.0000',0),
 (7,3,'he','盒',0,'0.0000',0),
 (8,4,'lit','升',1,'1.0000',0),
 (9,4,'ml','毫升',0,'0.0010',0);
truncate TABLE `shipping_method`;
INSERT INTO `shipping_method` (`shippingMethodId`,`carrierId`,`shippingMethodName`,`shippingMethodDetail`,`isDomestic`,`deliveryTime`,`status`,`isCod`,`deleted`,`version`) VALUES 
 (1,1,'邮局EMS','－国内统一资费：500g以内25元－500克以上邮件续重资费实行分区计费。',0,'3天左右',1,0,0,1);
truncate TABLE `shipping_rate`;
INSERT INTO `shipping_rate` (`shippingRateId`,`shippingMethodId`,`regionId`,`baseOn`,`isFlat`,`isRoundUpValue`,`basePrice`,`maxWeight`,`maxVolume`,`baseWeight`,`baseVolume`,`weightPerRate`,`volumePerRate`,`volumePerFee`,`increaseUnit`,`metricUnitCode`,`maxItem`,`itemPerRate`,`description`,`deleted`,`version`) VALUES 
 (1,1,NULL,0,1,NULL,'25.00','9999.00',NULL,'1.00',NULL,'6.00',NULL,NULL,'1.00','kg',NULL,NULL,'二区（1500公里以上至2500公里）为9元',0,1);

truncate TABLE `content`;
truncate TABLE `content_type`;
INSERT INTO content_type (contentTypeId,contentTypeName,contentTypeCode,url,decoratorPath,sortOrder,isShowTree,isUse,parentId,type) VALUES
 (1,'内容栏目','content_root',NULL,NULL,0,0,NULL,null,null),
 (2,'首页','mainPage',"index.html",NULL,1,0,1,1,3),
 (3,'产品菜单','product',"",NULL,10,0,1,1,1),
 (4,'自定义栏目','custom',"",NULL,20,0,1,1,1),
 (5,'网站资讯','infos',"content/contents=doActionshowContentByCode=columnCodeinfos=templateshowContents.html",NULL,30,0,1,1,1),
 (6,'最新消息','news',"",NULL,40,0,1,5,1),
 (7,'优惠信息','promoInfo',"content/promoInfo.html",NULL,50,0,1,1,3);


truncate TABLE `template_set`;
insert into template_set (templateSetName,templateSetPath,templateSetType,isDefault,description) values('标准模板包－综合类','/tss/general',0,1,'综合类模板包。集成常见的众多功能。对于要求较高、较专业的商家，可以以之作为基础进行定制修改。');
insert into template_set (templateSetName,templateSetPath,templateSetType,isDefault,description) values('服装模板包','/tss/fashion',1,0,'适用于服装类行业。');
 
insert into checkout_flow(checkoutFlowCode,checkoutFlowName,checkoutFlowDescription,status) values('express','快捷流程','一页结帐流程，一页输入所有结帐信息，方便快捷。',1);
 
insert into system_version(sysVersion,productCode,domainName,licenseKey,createTime) 
values('2.2','Mechant One','localhost','yCebOVM-7552-6383324191',now());
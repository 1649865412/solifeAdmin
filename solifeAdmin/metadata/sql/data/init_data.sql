truncate TABLE `user_role`;
truncate TABLE `app_user`;
truncate TABLE `role_res`;
truncate TABLE `app_role`;
truncate TABLE `app_resource`;

INSERT INTO `app_user` (`appuserId`,`username`,`password`,`userType`,`title`,`firstname`,`lastname`,`email`,`fax`,`telephone`,`preferredLanguage`,`lastLoginTime`,`status`,`isLocked`,`deleted`,`createBy`,`updateBy`,`createTime`,`updateTime`,`version`) VALUES
 (-2,'ANONYMOUS_USER','PASS_user_ANONYMOUS',0,'Mr.','ANONYMOUS','USER','ANONYMOUS_USER@ANONYMOUS.com','88888888','88888888',NULL,NULL,0,1,0,NULL,NULL,NULL,NULL,5),
 (1,'admin','d033e22ae348aeb5660fc2140aec35850c4da997',1,'Mr.','admin','Raible','admin@admin.com',NULL,NULL,NULL,NULL,1,0,0,NULL,NULL,NULL,NULL,1);

INSERT INTO `app_resource` (`appResourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES
 (1,'销售',0,'/sales/**','销售模块下的所有页面'),
 (2,'产品',0,'/catalog/**','产品模块下的所有页面'),
 (3,'内容',0,'/content/**','内容模块下的所有页面'),
 (4,'顾客',0,'/customer/**','顾客模块下的所有页面'),
 (5,'系统',0,'/system/**','系统模块下的所有页面'),
 (6,'库存',0,'/inventory/**','库存和物流'),
 (7,'订单',0,'/order/**','订单模块下的所有页面'),
 (8,'报表',0,'/report/**','报表模块下的所有页面'),
 (9,'供应商',0,'/supplier/**','供应商管理'),
 (10,'email',0,'GRANT_EMAIL','访问客户Email');


 INSERT INTO `app_role` (`appRoleId`,`roleName`,`roleDetail`,`isSystem`,`createTime`,`updateTime`,`status`) VALUES
 (1,'admin','系统超级管理角色，具有访问系统所有资源的权限',1,NULL,NULL,1),
 (2,'销售管理员','管理销售模块',1,NULL,NULL,1),
 (3,'产品管理员','管理产品模块',1,NULL,NULL,1),
 (4,'顾客管理员','管理顾客模块',1,NULL,NULL,1),
 (5,'内容管理员','管理内容模块',1,NULL,NULL,1),
 (6,'系统管理员','管理系统模块',1,NULL,NULL,1),
 (7,'订单管理员','管理订单模块',1,NULL,NULL,1),
 (8,'仓库管理员','仓库管理模块',1,NULL,NULL,1),
 (9,'供应商管理员','供应商管理模块',1,NULL,NULL,1);

 INSERT INTO `user_role` (`appuserId`,`appRoleId`,`version`) VALUES
 (1,1,1);

 INSERT INTO `role_res` (`appResourceId`,`appRoleId`) VALUES
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1),
 (6,1),
 (7,1),
 (8,1),
 (9,1),
 (10,1);
 
 
/* init one catalog and one store for default. */
insert into catalog (catalogId,categoryId,name,code,isVirtual,status,version) values (1,1,'Default','default',0,1,0);
insert into store (catalogId,categoryId,name,code,siteUrl,status,version) values(1,2,'Default Store','default','http://localhost:8080',1,0);
 
truncate TABLE category;
INSERT INTO category(categoryId,catalogId,categoryName,categoryCode,categoryType,status,sortOrder,createTime,updateTime,version) VALUES 
 (1,1,'产品根目录','cat_code_1',1,1,1,now(),now(),1);
INSERT INTO category(categoryId,storeId,categoryName,categoryCode,categoryType,status,sortOrder,isLinkCategory,createTime,updateTime,version) VALUES 
 (2,1,'内容根目录','content_cat_1',2,1,1,0,now(),now(),1);

/*内容目录内的帮助中心及其四条基础数据(注意： 如果需要修改categoryId，那么也要注意修改parentId和categoryPath)*/
 INSERT INTO `category` ( `categoryId`, `parentId`, `categoryType`, `categoryName`, `categoryDescription`, `categoryCode`, `metaKeyword`, `metaDescription`, `templatePath`, `categoryPath`, `imageUrl`, `linkUrl`, `isLinkCategory`, `sortOrder`, `status`, `createBy`, `updateBy`, `createTime`, `updateTime`, `version`) VALUES 
  (3, 2, 2, '帮助中心', '帮助中心', 'help', '帮助中心', '帮助中心', '', '2.', '', '', 0, 1, 1, 1, 1, '2008-12-22 14:48:20', '2008-12-22 14:48:29', 1),
  (4, 3, 2, '订单帮助', '订单帮助', 'orderHelp', '订单帮助', '订单帮助', '', '2.3.', '', '', 0, 1, 1, 1, 1, '2008-12-22 13:26:53', '2008-12-22 14:48:29', 1),
  (5, 3, 2, '运输和退货', '运输和退货', 'shippingAndReturn', '运输和退货', '运输和退货', '', '2.3.', '', '', 0, 2, 1, 1, 1, '2008-12-22 13:39:36', '2008-12-22 14:48:29', 1),
  (6, 3, 2, '产品帮助', '产品帮助', 'productHelp', '产品帮助', '产品帮助', '', '2.3.', '', '', 0, 3, 1, 1, 1, '2008-12-22 13:42:04', '2008-12-22 14:48:29', 1),
  (7, 3, 2, '关于网上商店', '关于网上商店', 'aboutEstore', '关于网上商店', '关于网上商店', '', '2.3.', '', '', 0,4, 1, 1, 1, '2008-12-22 13:43:47', '2008-12-22 14:48:29', 1);
  
truncate TABLE `membership`;
INSERT INTO `membership` (`membershipId`,`membershipName`,`membershipDetail`,`membershipLevel`,`status`,`deleted`,`version`,`upgradeShopPoint`) VALUES
 (1,'匿名顾客','匿名顾客',-2,1,0,0,-1);
 INSERT INTO `membership` (`membershipId`,`membershipName`,`membershipDetail`,`membershipLevel`,`status`,`deleted`,`version`,`upgradeShopPoint`) VALUES
 (2,'普通会员','普通会员',1,1,0,0,-1);


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
insert into payment_method (paymentMethodName,paymentMethodCode,paymentMethodDetail,paymentMethodType,paymentMethodIcon,protocol,processorFile,configFile,testModel,isCod,sortOrder,status,version)
values('Bank of China','boc','Bank of China',1,'payment/icon/boc.gif','HTTP','payment/boc_request','payment/boc_config','N',0,10,2,0); 

truncate TABLE `recommended_type`;
INSERT INTO `recommended_type` (`recommendTitle`,`typeName`,`ruleCode`,`autoEval`,`status`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES 
 ('热卖商品','hot_sell',1,1,1,1,0,1,0),
 ('最新商品','new_arrival',3,1,1,1,0,1,0),
 ('购买此商品的顾客也购买了','also_buy',4,1,1,1,1,0,0),
 ('特色商品','feature_product',0,2,1,1,0,1,0),
 ('相似商品推荐','similar_product',5,2,1,1,1,0,0),
 ('同品牌的其他相似商品','same_brand',7,1,1,1,1,0,0),
 ('其他颜色','other_color',8,1,1,1,1,0,0),
 ('价格相近的其他同类商品','similar_price',6,1,1,1,1,0,0),
 ('可选配件','accessories',0,2,1,1,1,0,0);
 
truncate TABLE `carrier`;
INSERT INTO `carrier` (`carrierId`,`carrierName`,`carrierAddress`,`carrierAddress2`,`linkman`,`telephone`,`fax`,`email`,`zip`,`status`,`deleted`,`version`) VALUES 
 (1,'邮局EMS','','','','','','','',1,0,0);

truncate TABLE `shipping_method`;
INSERT INTO `shipping_method` (`shippingMethodId`,`carrierId`,`shippingMethodName`,`shippingMethodDetail`,`isDomestic`,`deliveryTime`,`status`,`isCod`,`deleted`,`version`) VALUES 
 (1,1,'邮局EMS','－国内统一资费：500g以内25元－500克以上邮件续重资费实行分区计费。',0,'3天左右',1,0,0,1);
truncate TABLE `shipping_rate`;
INSERT INTO `shipping_rate` (`shippingRateId`,`shippingMethodId`,`regionId`,`baseOn`,`isFlat`,`isRoundUpValue`,`basePrice`,`maxWeight`,`maxVolume`,`baseWeight`,`baseVolume`,`weightPerRate`,`volumePerRate`,`volumePerFee`,`increaseUnit`,`metricUnitCode`,`maxItem`,`itemPerRate`,`description`,`deleted`,`version`) VALUES 
 (1,1,NULL,0,1,NULL,'25.00','9999.00',NULL,'1.00',NULL,'6.00',NULL,NULL,'1.00','kg',NULL,NULL,'二区（1500公里以上至2500公里）为9元',0,1);

insert into system_version(sysVersion,productCode,domainName,licenseKey,createTime) 
values('5.1','Merchant One','localhost','yCebOVM-7552-6383324191',now());

/* 初始化一个默认的供应商 */
insert into supplier (supplierid,supplierName,supplierCode,email,address,status,createTime,updateTime)
values(-1,'N/A','NULL','null@null.com','N/A',1,now(),now());

/* 增加多两个推荐 2010-3-8 */
INSERT INTO `recommended_type` (`recommendTitle`,`typeName`,`ruleCode`,`autoEval`,`status`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES 
('Best Wholesale Deals','best_wholesale_deals',0,2,1,1,0,1,0),
('Hot Wholesale Products','hot_wholesale_products',0,2,1,1,0,1,0);

insert into currency (currencyCode, currencyName, isDefault, status, sortOrder, exchangeRate, currencySymbol)
values ('USD', 'US Dollar', 1, 1, 1, 1, 'US$'),
('EUR', 'Euro', 0, 1, 2, 1, '$'),
('GBP', 'British Pound', 0, 1, 3, 1, '$'),
('CAD', 'Canadian Dollar', 0, 1, 4, 1, 'CA$'),
('AUD', 'Australian Dollar', 0, 1, 5, 1, 'AU$'),
('CHF', 'Switzerland Francs', 0, 1, 6, 1, 'CHF'),
('HKD', 'Hong Kong Dollar', 0, 1, 7, 1, 'HK$');

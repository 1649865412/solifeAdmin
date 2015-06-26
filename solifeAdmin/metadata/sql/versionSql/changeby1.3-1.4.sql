-- change version
update system_version set sysversion='1.4';

-- build 4-24 --
update menu set url=replace(url,'short','long') where locate('_',substring(url,28))>0  ;

alter table recommended_type add isApplyToProduct smallint null;
alter table recommended_type add isApplyToCategory smallint null;

INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES  ('recommended_t_hotsell','热卖产品','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_hotsell','Holt sell','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_new','最新产品','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_new','New arrival','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_alsobuy','购买此商品的顾客也购买了','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_alsobuy','Customers who bought items like this also bought','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_feature','特色产品','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_feature','Feature product','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_accessories','可选配件','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_accessories','Optional Accessories','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_brand','同品牌的其他相似产品','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_brand','Look for similar items by brand','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_product','相似产品推荐','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_product','Look for similar products','sales','en');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_price','价格相近的其他同类产品','sales','zh_CN');
INSERT INTO `i18ntext_item` (`textKey`,`textValue`,`category`,`localeCode`) VALUES   ('recommended_t_s_price','Look for similar items by price','sales','en');
 
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_hotsell','hot_sell',1,1,1,NULL,1,1,1,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_new','new_arrival',3,1,1,NULL,1,1,1,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_alsobuy','also_buy',4,1,1,NULL,1,1,0,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_feature','feature_product',0,0,1,NULL,1,1,1,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_s_product','similar_product',5,0,1,NULL,1,1,0,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_s_price','similar_price',6,1,1,NULL,1,1,0,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_s_brand','same_brand',7,1,1,NULL,1,1,0,0);
INSERT INTO `recommended_type` (`titleKey`,`typeName`,`ruleCode`,`autoEval`,`status`,`templatePath`,`isSystem`,`isApplyToProduct`,`isApplyToCategory`,`version`) VALUES  ('recommended_t_accessories','accessories',0,0,1,NULL,1,1,0,0);

update recommended_type set isApplyToProduct=1 where typeName in ('hot_sell','new_arrival','also_buy','feature_product');
update recommended_type set isApplyToCategory=1 where typeName in ('hot_sell','new_arrival','feature_product');
update recommended_type set isApplyToCategory=0 where typeName = 'also_buy';
-- build 4-24 end --
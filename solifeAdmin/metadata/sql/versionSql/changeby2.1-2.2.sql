ALTER TABLE `shipping_rate` MODIFY COLUMN `minWeight` DECIMAL(12,2);

delete from system_config where configkey='DefaultImage';

--去除"/media/"这个字串
update product set normalImageUrl = REPLACE(normalImageUrl,'/media/','');
update wrap set imageSrc = REPLACE(imageSrc,'/media/','');
update manufacturer set logoUrl = REPLACE(logoUrl,'/media/','');
update ADVERTISEMENT set url = REPLACE(url,'/media/','');
update product_media set mediaUrl = REPLACE(mediaUrl,'/media/','');

update system_config s set s.configValue='jpg' where s.configKey='MoreImageExtendName';
update system_config s set s.configValue=replace(s.configValue,';',',') where s.configKey='MultipleMediaExtendName';

update region set status=0;
update region set status=1 where regionCode='cn';

ALTER TABLE region_item
 DROP COLUMN regionItemType,
 DROP COLUMN status,
 DROP COLUMN addressList,
 DROP COLUMN zipList;
 
--增加会员等级升级积分
alter table membership add column upgradeShopPoint int null;

-- 去除没有用的表
drop table customer_preference;
drop table speed_bar;
drop table faq;
drop table faq_category;
drop table vote_result;
drop table vote_option;
drop table vote;
drop table survey_result;
drop table survey_option;
drop table survey;
drop table I18NTEXT_ITEM;
drop table I18NTEXT;
drop table credit_card;
drop table credit_card_type;
drop table PUBLISH_HISTORY;
drop table export_mapping_item;
drop table export_mapping;
drop table media_mapping_rule;
drop table SYSTEM_MODULE;
drop table IMPORT_EXPORT_ENTITY;
drop table URL_VISITED;

update membership set upgradeShopPoint = -1;

INSERT INTO content_type (contentTypeName,contentTypeCode,url,decoratorPath,sortOrder,isShowTree,isUse,parentId,type) VALUES
 ('优惠信息','promoInfo',"content/promoInfo.html",NULL,50,0,1,1,3);
 
--全部流程只采用onePageCheckout
update product_type set checkoutFlowCode='express';

delete from system_config where configKey='IsSupportWrapClassial';
delete from system_config where configKey='IsSupportWrapFashion';
create table `mail_queue`
(
   `mailQueueId` int unsigned not null auto_increment,
   `mailSubject` varchar(256)  not null,
   `mailFrom` varchar(128)  not null,
   `mailTos` varchar(1024)  not null,
   `sendStatus` smallint unsigned  not null,
   `sendTimes` smallint unsigned  not null,
   `errorMsg` varchar(1024)  not null,
   `mimeMessage` blob  not null,
   `createTime` datetime  not null,
   `updateTime` datetime  not null,
   `nextRetryTime` datetime,
   primary key (`mailQueueId`)
);

alter table region modify column regionCode varchar(32) not null;
create index IX_REGION_PARENTID on REGION
(
   parentRegionId,
   regionType
);

alter table attribute_option modify column attributeOptionValue varchar(128) not null;
update system_version set sysVersion='2.2', productCode='Merchant One', updateTime=now();
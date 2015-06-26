update department set departmentLevel=0;
ALTER TABLE currency ADD COLUMN currencyUtilNameKey varchar(32) DEFAULT NULL;

alter table `customer` DROP FOREIGN KEY `FK_Reference_164`;
ALTER TABLE `customer` ADD CONSTRAINT `FK_customer_membership` FOREIGN KEY (`membershipId`) REFERENCES `membership` (`membershipId`);

ALTER TABLE sales_order ADD COLUMN shippingMethodId int DEFAULT NULL;

--role
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `resourceId` int(11) NOT NULL auto_increment,
  `resourceName` varchar(32) NOT NULL,
  `resourceType` smallint(6) NOT NULL default '0' COMMENT '0=url\r\n1=function\r\n2=acl',
  `resourceString` varchar(255) NOT NULL,
  `resourceDesc` varchar(32) default NULL,
  PRIMARY KEY  (`resourceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `role_res`;
CREATE TABLE `role_res` (
  `roleResId` int(11) NOT NULL auto_increment,
  `resourceId` int(11) default NULL,
  `roleId` int(11) default NULL,
  PRIMARY KEY  (`roleResId`),
  KEY `resourceId` (`resourceId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `role_res_fk` FOREIGN KEY (`resourceId`) REFERENCES `resource` (`resourceId`) ON DELETE CASCADE,
  CONSTRAINT `role_res_fk1` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
delete from resource;
INSERT INTO `resource` (`resourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES
 (1,'customer',0,'customer/*','customerDesc'),
 (2,'sales',0,'sales/*','salesDesc'),
 (3,'index',0,'index.jsp*','indexDesc'),
 (4,'appAdmin',0,'system/appAdmins.html*','appAdminDesc'),
 (5,'catalog',0,'catalog/*','catalogDesc'),
 (6,'content',0,'content/*','contentDesc'),
 (7,'report',0,'report/*','reportDesc'),
 (8,'systemUrls',0,'system/systemUrls.html*','systemUrlsDesc'),
 (9,'system',0,'system/*','systemDesc'),
 (10,'dwr',0,'dwr/*','dwrDesc'),
 (11,'importProduct',0,'catalog/importProducts.html*','improtProductDesc'),
 (12,'All',0,'*/*.html*','all');
INSERT INTO `role_res` (`resourceId`,`roleId`) VALUES
 (1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1), (9,1), (10,1), (11,1), (12,1);


alter table department drop column version;
alter table manufacturer drop column version;
alter table attribute drop column version;

--1.15-1.22
ALTER TABLE resource modify COLUMN resourceDesc varchar(512) DEFAULT NULL;

update app_role set roleName='ROLE_ANONYMOUS' where roleId=-1;

delete from role_res;
delete from resource;

INSERT INTO `resource` (`resourceId`,`resourceName`,`resourceType`,`resourceString`,`resourceDesc`) VALUES
 (-3,'login',0,'login*',''),
 (-2,'mainMenu',0,'*.html*','the main menu'),
 (-1,'All',0,'**/*.html*','all'),
 (1,'sales',0,'sales/*','sales'),
 (2,'catalog',0,'catalog/*','catalog'),
 (3,'content',0,'content/*','content'),
 (4,'customer',0,'customer/*','customer'),
 (5,'system',0,'system/*','system');

INSERT INTO `role_res` (`resourceId`,`roleId`) VALUES
 (-3,-1),
 (-2,1),
 (-1,1),
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1);
 
 
 create table MEDIA_EFFECT
(
   mediaEffectId        int not null,
   mediaEffectFunction  varchar(1024),
   mediaEffectName      varchar(128) not null,
   isSystem             smallint not null comment '0=yes
            1=no',
   primary key (mediaEffectId)
);
alter table product add column(isMultipleMedia smallint,isMultipleAttachment  smallint);
alter table product drop attachmentName ;
alter table product drop attachmenturl;
alter table PRODUCT_MEDIA add column(mediaEffectId int,mediaDescritpion  varchar(256),mediaGroupType  smallint,sortOrder int,mediaUrl  varchar(255));

alter table PRODUCT_MEDIA add constraint FK_Reference_117 foreign key (mediaEffectId)
      references MEDIA_EFFECT (mediaEffectId) on delete set null;
alter table PRODUCT_MEDIA drop foreign key FK_Reference_140;
alter table PRODUCT_MEDIA drop mediaId;
 
--1.22-1.31
alter table product_media change mediaGroupType  mediaType smallint;

insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,isSystem,version) values('content.media.mediaEffectName','zoomIn,zoomOut,blindUp,blindDown','','content',1,1,2,0);

alter table product add mediaEffect varchar(64);

update product set membershipId=null 
where membershipId not in (select membershipId from membership);

alter table PRODUCT add constraint FK_Reference_120 foreign key (membershipId)
      references MEMBERSHIP (membershipId) on delete set null on update restrict;
      
update CATEGORY  set membershipId=null 
where membershipId not in (select membershipId from membership);

alter table CATEGORY add constraint FK_Reference_121 foreign key (membershipId)
      references MEMBERSHIP (membershipId) on delete set null on update restrict;

alter table product_media change mediaDescritpion  mediaDescription varchar(256);

alter table system_message modify column messageHtml varchar(512);

alter table address drop column version;

CREATE TABLE `patchexecutehistory` (

  `executeId` int(10) unsigned NOT NULL auto_increment,

  `transactionUid` varchar(32) default NULL,

  `fileName` varchar(45) NOT NULL default '',

  `fileType` varchar(45) NOT NULL default '',

  `flag` smallint(5) unsigned NOT NULL default '0',

  `resultDescription` varchar(256) default NULL,

  `fromVersion` varchar(32) NOT NULL default '',

  `toVersion` varchar(32) NOT NULL default '',

  `executeTime` datetime NOT NULL default '0000-00-00 00:00:00',

  `version` int(10) unsigned default NULL,

  PRIMARY KEY  (`executeId`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Alter table product add column firstTabNum int null;

/*==============================================================*/
/* Table: CONTENT_TYPE                                          */
/*==============================================================*/
create table CONTENT_TYPE
(
   contentTypeId        int not null auto_increment,
   contentTypeNameKey   varchar(32) not null,
   contentTypeCode      varchar(32) not null,
   primary key (contentTypeId)
);

/*==============================================================*/
/* Table: CONTENT                                               */
/*==============================================================*/
create table CONTENT
(
   contentId            int not null auto_increment,
   contentTypeId        int,
   titleKey             varchar(32) not null,
   display              smallint not null,
   contentKey           varchar(32),
   issueTime            datetime,
   expireTime           datetime,
   addTime              datetime,
   isTop                smallint,
   url                  varchar(512),
   primary key (contentId)
);

alter table CONTENT add constraint FK_fk_content_contentTypeID foreign key (contentTypeId)
      references CONTENT_TYPE (contentTypeId) on delete restrict on update restrict;
      
alter table product change firstTabNum  defaultTab int;
alter table product_package_item add quantity int default 1;
alter table metric_unit modify COLUMN convertRate DECIMAL(12,4) DEFAULT NULL;
alter table payment_history modify column receiveData varchar(2048);      

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

insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,isSystem,version) values
('System.gerneral.LongI18nMsg','d:/workspace1/eStore/web/WEB-INF/i18n/','Long I18n message file path ','system',2,1,2,0);

update system_config set isSystem=2 where isSystem is null;
      
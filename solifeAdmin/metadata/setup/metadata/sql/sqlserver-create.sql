CREATE DATABASE  @DB-NAME@;
USE @DB-NAME@;
/*==============================================================*/
/* Database name:  probiz                                       */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2006-10-30 9:58:50                           */
/*==============================================================*/


execute sp_unbindefault 'int_version';

if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'D_int_version'
   )
   drop default D_int_version;

execute sp_unbindefault 'small_deleted';

if exists (select 1
   from  sysobjects where type = 'D'
   and name = 'D_small_deleted'
   )
   drop default D_small_deleted;

/*==============================================================*/
/* Default: D_int_version                                       */
/*==============================================================*/
create default D_int_version
    as 0;

/*==============================================================*/
/* Default: D_small_deleted                                     */
/*==============================================================*/
create default D_small_deleted
    as 0;

/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
create table ADDRESS (
   addressId            int                  identity,
   appuserId            int                  null,
   addressType          smallint             not null,
   title                nvarchar(8)          not null,
   firstname            nvarchar(32)         not null,
   lastname             nvarchar(32)         not null,
   telephone            varchar(32)          not null,
   zip                  varchar(6)           null,
   fax                  varchar(32)          null,
   companyName          nvarchar(128)        null,
   isDefaultShippingAddress smallint             null,
   isDefaultBillingAddress smallint             null,
   address              nvarchar(128)        not null,
   address2             nvarchar(128)        null,
   countryName          nvarchar(64)         not null,
   stateName            nvarchar(64)         null,
   cityName             nvarchar(64)         null,
   countryId            int                  not null,
   stateId              int                  null default 0,
   cityId               int                  null default 0,
   userDefinedId        int                  null default 0,
   version              int                  not null,
   constraint PK_ADDRESS primary key nonclustered (addressId)
);

execute sp_bindefault D_int_version, 'ADDRESS.version';

declare @CmtADDRESS varchar(128)
select @CmtADDRESS = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Manage all customer address',
   'user', @CmtADDRESS, 'table', 'ADDRESS';

execute sp_addextendedproperty 'MS_Description', 
   '0=Personal address(reserved)
   1=shippinb address
   2=billing address
   3=company address',
   'user', '', 'table', 'ADDRESS', 'column', 'addressType';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'ADDRESS', 'column', 'isDefaultShippingAddress';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'ADDRESS', 'column', 'isDefaultBillingAddress';

execute sp_addextendedproperty 'MS_Description', 
   'Selected or Other state that not in our database',
   'user', '', 'table', 'ADDRESS', 'column', 'stateName';

execute sp_addextendedproperty 'MS_Description', 
   'Selected or Other city that not in our database',
   'user', '', 'table', 'ADDRESS', 'column', 'cityName';

/*==============================================================*/
/* Table: ADVERTISEMENT                                         */
/*==============================================================*/
create table ADVERTISEMENT (
   advertisementId      int                  identity,
   advertisementTypeId  int                  not null,
   promotionId          int                  null,
   advertisementName    nvarchar(128)        not null,
   contentType          smallint             not null,
   advertisementDetail  nvarchar(512)        null,
   mediaPath            varchar(128)         null,
   url                  varchar(255)         not null,
   redirectUrl          varchar(255)         null,
   target               varchar(32)          null,
   width                numeric(12,2)        null,
   height               numeric(12,2)        null,
   startPublishTime     datetime             null,
   endPublishTime       datetime             null,
   displayCount         int                  null,
   clickCount           int                  null,
   sortOrder            int                  null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_ADVERTISEMENT primary key nonclustered (advertisementId)
);

execute sp_bindefault D_int_version, 'ADVERTISEMENT.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=Text
   1=Image
   2=flash',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'contentType';

execute sp_addextendedproperty 'MS_Description', 
   'when will start publishing this ad',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'startPublishTime';

execute sp_addextendedproperty 'MS_Description', 
   'expire Publish Time',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'endPublishTime';

execute sp_addextendedproperty 'MS_Description', 
   'how many times this ad has been displayed to the customer',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'displayCount';

execute sp_addextendedproperty 'MS_Description', 
   'how many times this ad has been clicked',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'clickCount';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'ADVERTISEMENT', 'column', 'status';

/*==============================================================*/
/* Table: ADVERTISEMENT_POSITION                                */
/*==============================================================*/
create table ADVERTISEMENT_POSITION (
   adpositionId         int                  identity,
   advertisementId      int                  not null,
   positionTypeId       int                  not null,
   displayOrder         int                  null,
   version              int                  not null,
   constraint PK_ADVETION primary key nonclustered (adpositionId)
);

execute sp_bindefault D_int_version, 'ADVERTISEMENT_POSITION.version';

declare @CmtADVERTISEMENT_POSITION varchar(128)
select @CmtADVERTISEMENT_POSITION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define ad is used in which position type',
   'user', @CmtADVERTISEMENT_POSITION, 'table', 'ADVERTISEMENT_POSITION';

execute sp_addextendedproperty 'MS_Description', 
   'when in a sequential display mode, define the display order',
   'user', '', 'table', 'ADVERTISEMENT_POSITION', 'column', 'displayOrder';

/*==============================================================*/
/* Table: ADVERTISEMENT_TYPE                                    */
/*==============================================================*/
create table ADVERTISEMENT_TYPE (
   advertisementTypeId  int                  identity,
   typeName             nvarchar(64)         not null,
   typeCode             varchar(32)          not null,
   classpath            varchar(255)         null,
   version              int                  not null,
   constraint PK_ADVE_TYPE primary key nonclustered (advertisementTypeId),
   constraint AK_KEY_2_ADVERTIS unique (typeCode)
);

execute sp_bindefault D_int_version, 'ADVERTISEMENT_TYPE.version';

declare @CmtADVERTISEMENT_TYPE varchar(128)
select @CmtADVERTISEMENT_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define types of ad',
   'user', @CmtADVERTISEMENT_TYPE, 'table', 'ADVERTISEMENT_TYPE';

/*==============================================================*/
/* Table: AD_POSITION_TYPE                                      */
/*==============================================================*/
create table AD_POSITION_TYPE (
   positionTypeId       int                  identity,
   positionName         nvarchar(128)        null,
   type                 smallint             null,
   height               int                  null,
   width                int                  null,
   displayType          smallint             null,
   status               smallint             null,
   description          nvarchar(512)        null,
   templatePath         varchar(128)         null,
   version              int                  not null,
   constraint PK_ADVEION primary key nonclustered (positionTypeId)
);

execute sp_bindefault D_int_version, 'AD_POSITION_TYPE.version';

declare @CmtAD_POSITION_TYPE varchar(128)
select @CmtAD_POSITION_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define ad position type',
   'user', @CmtAD_POSITION_TYPE, 'table', 'AD_POSITION_TYPE';

execute sp_addextendedproperty 'MS_Description', 
   '0=Text
   1=Image
   2=flash
   3=other multimedia
   ',
   'user', '', 'table', 'AD_POSITION_TYPE', 'column', 'type';

execute sp_addextendedproperty 'MS_Description', 
   '0=Sequential
   1=Random',
   'user', '', 'table', 'AD_POSITION_TYPE', 'column', 'displayType';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'AD_POSITION_TYPE', 'column', 'status';

/*==============================================================*/
/* Table: ALSO_BUY                                              */
/*==============================================================*/
create table ALSO_BUY (
   alsoBuyId            int                  identity,
   productId            int                  not null,
   alsoProductId        int                  not null,
   times                int                  null,
   version              int                  not null,
   constraint PK_ALSO_BUY primary key nonclustered (alsoBuyId)
);

execute sp_bindefault D_int_version, 'ALSO_BUY.version';

declare @CmtALSO_BUY varchar(128)
select @CmtALSO_BUY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Customer buy this also buy these products',
   'user', @CmtALSO_BUY, 'table', 'ALSO_BUY';

execute sp_addextendedproperty 'MS_Description', 
   'how many times this product has been purchased',
   'user', '', 'table', 'ALSO_BUY', 'column', 'times';

/*==============================================================*/
/* Table: APP_ADMIN                                             */
/*==============================================================*/
create table APP_ADMIN (
   appAdminId           int                  not null,
   department           varchar(64)          null,
   userPosition         nvarchar(128)        null,
   version              int                  null,
   constraint PK_APP_ADMIN primary key nonclustered (appAdminId)
);

declare @CmtAPP_ADMIN varchar(128)
select @CmtAPP_ADMIN = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'System administrator',
   'user', @CmtAPP_ADMIN, 'table', 'APP_ADMIN';

execute sp_addextendedproperty 'MS_Description', 
   'user position, use '' to seperate several positions',
   'user', '', 'table', 'APP_ADMIN', 'column', 'userPosition';

/*==============================================================*/
/* Table: APP_AUDIT                                             */
/*==============================================================*/
create table APP_AUDIT (
   appAuditId           int                  identity,
   procUserId           int                  null,
   procObj              varchar(1024)        null,
   actionName           nvarchar(64)         not null,
   procResult           varchar(1024)        null,
   requestUrl           varchar(255)         null,
   procTime             datetime             not null,
   constraint PK_APP_AUDIT primary key nonclustered (appAuditId)
);

/*==============================================================*/
/* Table: APP_MEDIA                                             */
/*==============================================================*/
create table APP_MEDIA (
   mediaId              int                  identity,
   mediaTypeId          int                  null,
   mediaName            nvarchar(128)        not null,
   mediaDescription     nvarchar(512)        null,
   mediaWidth           int                  null,
   mediaHeight          int                  null,
   mediaPath            varchar(128)         null,
   mediaUrl             varchar(255)         null,
   alt                  nvarchar(128)        null,
   isMappinged          smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_APP_MEDIA primary key nonclustered (mediaId)
);

execute sp_bindefault D_int_version, 'APP_MEDIA.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'APP_MEDIA', 'column', 'status';

/*==============================================================*/
/* Table: APP_ROLE                                              */
/*==============================================================*/
create table APP_ROLE (
   roleId               int                  identity,
   roleName             nvarchar(32)         not null,
   roleDetail           nvarchar(255)        null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_APP_ROLE primary key nonclustered (roleId)
);

execute sp_bindefault D_int_version, 'APP_ROLE.version';

declare @CmtAPP_ROLE varchar(128)
select @CmtAPP_ROLE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Application used roles',
   'user', @CmtAPP_ROLE, 'table', 'APP_ROLE';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'APP_ROLE', 'column', 'status';

/*==============================================================*/
/* Table: APP_USER                                              */
/*==============================================================*/
create table APP_USER (
   appuserId            int                  identity,
   username             nvarchar(32)         not null,
   password             varchar(128)         null,
   userType             smallint             null,
   title                nvarchar(8)          null,
   firstname            nvarchar(32)         null,
   lastname             nvarchar(32)         null,
   email                varchar(32)          not null,
   fax                  varchar(32)          null,
   zip                  varchar(6)           null,
   telephone            varchar(32)          null,
   status               smallint             null,
   isLocked             smallint             null,
   deleted              smallint             not null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_APP_USER primary key nonclustered (appuserId),
   constraint AK_KEY_2_APP_USER unique (username),
   constraint AK_KEY_3_APP_USER unique (email)
);

execute sp_bindefault D_small_deleted, 'APP_USER.deleted';

execute sp_bindefault D_int_version, 'APP_USER.version';

declare @CmtAPP_USER varchar(128)
select @CmtAPP_USER = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Base use info, inherited by app_admin,customer,partner',
   'user', @CmtAPP_USER, 'table', 'APP_USER';

execute sp_addextendedproperty 'MS_Description', 
   '0=customer
   1=administrator,
   2=partener',
   'user', '', 'table', 'APP_USER', 'column', 'userType';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'APP_USER', 'column', 'status';

execute sp_addextendedproperty 'MS_Description', 
   '0=not locked
   1=is locked',
   'user', '', 'table', 'APP_USER', 'column', 'isLocked';

/*==============================================================*/
/* Table: ATTRIBUTE                                             */
/*==============================================================*/
create table ATTRIBUTE (
   attributeId          int                  identity,
   attributeNameKey     varchar(32)          not null,
   attributeType        smallint             not null,
   defaultValue         varchar(128)         null,
   validationExpression varchar(1024)        null,
   status               smallint             null,
   isRequired           smallint             null,
   inputType            smallint             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_ATTRIBUTE primary key nonclustered (attributeId)
);

execute sp_bindefault D_small_deleted, 'ATTRIBUTE.deleted';

execute sp_bindefault D_int_version, 'ATTRIBUTE.version';

declare @CmtATTRIBUTE varchar(128)
select @CmtATTRIBUTE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'All attributes used by product/category',
   'user', @CmtATTRIBUTE, 'table', 'ATTRIBUTE';

execute sp_addextendedproperty 'MS_Description', 
   'Define the attribute is used for what purpose
   0=product/category
   1=customer
   2=order
   3=other
   9=general',
   'user', '', 'table', 'ATTRIBUTE', 'column', 'attributeType';

execute sp_addextendedproperty 'MS_Description', 
   'used when attributeType=1(user input)
   use regular expression to validate user input',
   'user', '', 'table', 'ATTRIBUTE', 'column', 'validationExpression';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'ATTRIBUTE', 'column', 'status';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'ATTRIBUTE', 'column', 'isRequired';

execute sp_addextendedproperty 'MS_Description', 
   '0=list(drop down select box)
   1=input',
   'user', '', 'table', 'ATTRIBUTE', 'column', 'inputType';

/*==============================================================*/
/* Table: ATTRIBUTE_OPTION                                      */
/*==============================================================*/
create table ATTRIBUTE_OPTION (
   attributeOptionId    int                  identity,
   attributeId          int                  not null,
   attributeOptionNameKey varchar(32)          not null,
   attributeOptionValue varchar(32)          not null,
   isDefault            smallint             null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_ATT_OPT primary key nonclustered (attributeOptionId)
);

execute sp_bindefault D_int_version, 'ATTRIBUTE_OPTION.version';

declare @CmtATTRIBUTE_OPTION varchar(128)
select @CmtATTRIBUTE_OPTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Available option of a attribute',
   'user', @CmtATTRIBUTE_OPTION, 'table', 'ATTRIBUTE_OPTION';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'ATTRIBUTE_OPTION', 'column', 'isDefault';

/*==============================================================*/
/* Table: CARRIER                                               */
/*==============================================================*/
create table CARRIER (
   carrierId            int                  identity,
   carrierName          nvarchar(128)        not null,
   carrierAddress       nvarchar(128)        null,
   carrierAddress2      nvarchar(128)        null,
   linkman              nvarchar(32)         null,
   telephone            varchar(32)          null,
   fax                  varchar(32)          null,
   email                varchar(32)          null,
   zip                  varchar(6)           null,
   status               smallint             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_CARRIER primary key nonclustered (carrierId)
);

execute sp_bindefault D_small_deleted, 'CARRIER.deleted';

execute sp_bindefault D_int_version, 'CARRIER.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'CARRIER', 'column', 'status';

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY (
   categoryId           int                  identity,
   categoryNameKey      varchar(32)          not null,
   categoryDescriptionKey varchar(32)          null,
   categoryCode         varchar(32)          not null,
   productCount         int                  null,
   subCategoryCount     int                  null,
   metaKeywordKey       varchar(32)          null,
   metaDescriptionKey   varchar(32)          null,
   membershipId         int                  null,
   status               smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   templatePath         varchar(128)         null,
   categoryPath         varchar(255)         not null,
   imageUrl             varchar(255)         null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_CATEGORY primary key nonclustered (categoryId),
   constraint AK_KEY_3_CATEGORY unique (categoryCode),
   constraint AK_Key_Cate_2 unique (categoryPath)
);

execute sp_bindefault D_small_deleted, 'CATEGORY.deleted';

execute sp_bindefault D_int_version, 'CATEGORY.version';

declare @CmtCATEGORY varchar(128)
select @CmtCATEGORY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Product category. Id=0 is the root category. Each has a unique category path. Can have subcategory, parent category.',
   'user', @CmtCATEGORY, 'table', 'CATEGORY';

execute sp_addextendedproperty 'MS_Description', 
   'how many product under this category',
   'user', '', 'table', 'CATEGORY', 'column', 'productCount';

execute sp_addextendedproperty 'MS_Description', 
   'how many sub category under this category',
   'user', '', 'table', 'CATEGORY', 'column', 'subCategoryCount';

execute sp_addextendedproperty 'MS_Description', 
   'If defined, only customer having specified membership or above (determine by level) can see the category and products under it.',
   'user', '', 'table', 'CATEGORY', 'column', 'membershipId';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   
   ',
   'user', '', 'table', 'CATEGORY', 'column', 'status';

/*==============================================================*/
/* Table: CATEGORY_CATEGORY                                     */
/*==============================================================*/
create table CATEGORY_CATEGORY (
   categoryCategoryId   int                  identity,
   subCategoryId        int                  not null,
   categoryId           int                  not null,
   categoryPath         varchar(255)         not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_CAT_CAT primary key nonclustered (categoryCategoryId),
   constraint AK_KEY_2_CATEGORY unique (categoryPath)
);

execute sp_bindefault D_int_version, 'CATEGORY_CATEGORY.version';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)',
   'user', '', 'table', 'CATEGORY_CATEGORY', 'column', 'categoryPath';

/*==============================================================*/
/* Table: CATEGORY_SHIPPING_RATE                                */
/*==============================================================*/
create table CATEGORY_SHIPPING_RATE (
   categoryShippingMethodId int                  identity,
   shippingRateId       int                  null,
   categoryProductPath  varchar(255)         not null,
   isCategory           smallint             null,
   version              int                  not null,
   constraint PK_CATEG_SHIP_MET primary key nonclustered (categoryShippingMethodId)
);

execute sp_bindefault D_int_version, 'CATEGORY_SHIPPING_RATE.version';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)
   ',
   'user', '', 'table', 'CATEGORY_SHIPPING_RATE', 'column', 'categoryProductPath';

execute sp_addextendedproperty 'MS_Description', 
   '0=is product
   1=is category',
   'user', '', 'table', 'CATEGORY_SHIPPING_RATE', 'column', 'isCategory';

/*==============================================================*/
/* Table: COMPANY_INFO                                          */
/*==============================================================*/
create table COMPANY_INFO (
   companyId            int                  identity,
   companyNameKey       varchar(32)          not null,
   country              nvarchar(64)         null,
   state                nvarchar(64)         null,
   city                 nvarchar(64)         null,
   address              nvarchar(128)        null,
   phone                varchar(128)         null,
   email                varchar(32)          null,
   fax                  varchar(32)          null,
   zip                  varchar(6)           null,
   reserve1Key          varchar(32)          null,
   reserve2             varchar(32)          null,
   constraint PK_COMPANY_INFO primary key nonclustered (companyId)
);

/*==============================================================*/
/* Table: COOKIE                                                */
/*==============================================================*/
create table COOKIE (
   cookieId             int                  identity,
   cookieUid            varchar(64)          not null,
   shoppingCartId       int                  null,
   visitTime            datetime             null,
   version              int                  not null,
   constraint PK_COOKIE primary key nonclustered (cookieId),
   constraint AK_KEY_2_COOKIE unique (cookieUid)
);

execute sp_bindefault D_int_version, 'COOKIE.version';

/*==============================================================*/
/* Table: COUPON                                                */
/*==============================================================*/
create table COUPON (
   couponId             int                  identity,
   couponTypeId         int                  not null,
   couponNo             varchar(16)          not null,
   isSent               smallint             null,
   remainedTimes        int                  null,
   promotionId          int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_COUPON primary key nonclustered (couponId),
   constraint AK_KEY_2_COUPON unique (couponNo)
);

execute sp_bindefault D_int_version, 'COUPON.version';

execute sp_addextendedproperty 'MS_Description', 
   '0:Not sent to customer yet
   1:Sent',
   'user', '', 'table', 'COUPON', 'column', 'isSent';

execute sp_addextendedproperty 'MS_Description', 
   'how many remained times this coupon can be used',
   'user', '', 'table', 'COUPON', 'column', 'remainedTimes';

execute sp_addextendedproperty 'MS_Description', 
   'saved promotion id for better performance',
   'user', '', 'table', 'COUPON', 'column', 'promotionId';

/*==============================================================*/
/* Table: COUPON_TYPE                                           */
/*==============================================================*/
create table COUPON_TYPE (
   couponTypeId         int                  identity,
   promotionId          int                  null,
   title                nvarchar(128)        null,
   times                int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_COUPON_TYPE primary key nonclustered (couponTypeId)
);

execute sp_bindefault D_int_version, 'COUPON_TYPE.version';

declare @CmtCOUPON_TYPE varchar(128)
select @CmtCOUPON_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Types of coupon',
   'user', @CmtCOUPON_TYPE, 'table', 'COUPON_TYPE';

/*==============================================================*/
/* Table: CREDIT_CARD                                           */
/*==============================================================*/
create table CREDIT_CARD (
   creditCardId         int                  identity,
   creditCardTypeId     int                  not null,
   customerId           int                  not null,
   creditCardNo         varchar(32)          not null,
   cardHolderName       nvarchar(32)         null,
   cvv2                 smallint             null,
   expireDate           datetime             null,
   version              int                  not null,
   constraint PK_CREDIT_CARD primary key nonclustered (creditCardId)
);

execute sp_bindefault D_int_version, 'CREDIT_CARD.version';

declare @CmtCREDIT_CARD varchar(128)
select @CmtCREDIT_CARD = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Customer credit card info
   
   [Pending]',
   'user', @CmtCREDIT_CARD, 'table', 'CREDIT_CARD';

/*==============================================================*/
/* Table: CREDIT_CARD_TYPE                                      */
/*==============================================================*/
create table CREDIT_CARD_TYPE (
   creditCardTypeId     int                  identity,
   creditCardTypeCode   varchar(32)          not null,
   creditCardTypeName   nvarchar(32)         not null,
   hasCvv2              smallint             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_CRE_CA_TYPE primary key nonclustered (creditCardTypeId)
);

execute sp_bindefault D_int_version, 'CREDIT_CARD_TYPE.version';

declare @CmtCREDIT_CARD_TYPE varchar(128)
select @CmtCREDIT_CARD_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Types of credit card supported
   
   [Pending]',
   'user', @CmtCREDIT_CARD_TYPE, 'table', 'CREDIT_CARD_TYPE';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'CREDIT_CARD_TYPE', 'column', 'hasCvv2';

/*==============================================================*/
/* Table: CURRENCY                                              */
/*==============================================================*/
create table CURRENCY (
   currencyId           int                  identity,
   currencyCode         varchar(8)           not null,
   currencyName         nvarchar(32)         not null,
   isDefault            smallint             null,
   status               smallint             null,
   sortOrder            int                  null,
   exchangeRate         numeric(12,4)        null,
   currencySymbol       varchar(16)          not null,
   version              int                  not null,
   constraint PK_CURRENCY primary key nonclustered (currencyId)
);

execute sp_bindefault D_int_version, 'CURRENCY.version';

declare @CmtCURRENCY varchar(128)
select @CmtCURRENCY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Supported currency',
   'user', @CmtCURRENCY, 'table', 'CURRENCY';

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table CUSTOMER (
   customerId           int                  not null,
   passwordHint         varchar(128)         null,
   passwordHintAnswer   nvarchar(255)        null,
   customerPosition     nvarchar(128)        null,
   registerTime         datetime             null,
   membershipId         int                  null,
   totalPoints          int                  null,
   lastVisitedTime      datetime             null,
   note                 nvarchar(1024)       null,
   version              int                  null,
   constraint PK_CUSTOMER primary key nonclustered (customerId)
);

declare @CmtCUSTOMER varchar(128)
select @CmtCUSTOMER = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Registered Customer info. Link to cookie, has membership.',
   'user', @CmtCUSTOMER, 'table', 'CUSTOMER';

execute sp_addextendedproperty 'MS_Description', 
   'Customer position in his/her company, when several positions applied, use comma to seperate them',
   'user', '', 'table', 'CUSTOMER', 'column', 'customerPosition';

/*==============================================================*/
/* Table: CUSTOMER_MESSAGE_LIST                                 */
/*==============================================================*/
create table CUSTOMER_MESSAGE_LIST (
   customerMessageListId int                  identity,
   messageListId        int                  null,
   email                varchar(32)          not null,
   version              int                  not null,
   constraint PK_CUSM_MESE_LIST primary key nonclustered (customerMessageListId)
);

execute sp_bindefault D_int_version, 'CUSTOMER_MESSAGE_LIST.version';

declare @CmtCUSTOMER_MESSAGE_LIST varchar(128)
select @CmtCUSTOMER_MESSAGE_LIST = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Customre subscribe which message list',
   'user', @CmtCUSTOMER_MESSAGE_LIST, 'table', 'CUSTOMER_MESSAGE_LIST';

/*==============================================================*/
/* Table: CUSTOMER_PREFERENCE                                   */
/*==============================================================*/
create table CUSTOMER_PREFERENCE (
   customerPreferenceId int                  identity,
   customerId           int                  not null,
   preferenceKey        varchar(32)          null,
   preferenceValue      varchar(255)         null,
   version              int                  not null,
   constraint PK_CU_PRE primary key nonclustered (customerPreferenceId)
);

execute sp_bindefault D_int_version, 'CUSTOMER_PREFERENCE.version';

declare @CmtCUSTOMER_PREFERENCE varchar(128)
select @CmtCUSTOMER_PREFERENCE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Customer preference, e.g. personalization settings',
   'user', @CmtCUSTOMER_PREFERENCE, 'table', 'CUSTOMER_PREFERENCE';

execute sp_addextendedproperty 'MS_Description', 
   'value of customer preference, use comma to seperate if there are more than one value',
   'user', '', 'table', 'CUSTOMER_PREFERENCE', 'column', 'preferenceValue';

/*==============================================================*/
/* Table: DEPARTMENT                                            */
/*==============================================================*/
create table DEPARTMENT (
   departmentId         int                  identity,
   departmentCode       varchar(32)          not null,
   departmentNameKey    varchar(32)          not null,
   departmentDetailKey  varchar(32)          null,
   productCount         int                  null default 0,
   departmentLevel      int                  null,
   status               smallint             null,
   sortOrder            int                  null,
   parentId             int                  not null default 0,
   createBy             int                  null,
   updateBy             int                  null,
   updateTime           datetime             null,
   createTime           datetime             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_DEPARTMENT primary key nonclustered (departmentId),
   constraint AK_KEY_2_DEPARTME unique (departmentCode)
);

execute sp_bindefault D_small_deleted, 'DEPARTMENT.deleted';

execute sp_bindefault D_int_version, 'DEPARTMENT.version';

declare @CmtDEPARTMENT varchar(128)
select @CmtDEPARTMENT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Some store organize and sell goods by deparment, e.g. a department store',
   'user', @CmtDEPARTMENT, 'table', 'DEPARTMENT';

execute sp_addextendedproperty 'MS_Description', 
   'a saved calculated department level',
   'user', '', 'table', 'DEPARTMENT', 'column', 'departmentLevel';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'DEPARTMENT', 'column', 'status';

/*==============================================================*/
/* Table: EXPORT_MAPPING                                        */
/*==============================================================*/
create table EXPORT_MAPPING (
   exportMappingId      int                  identity,
   exportMappingName    nvarchar(32)         not null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             not null,
   updateTime           datetime             null,
   exportFileType       varchar(8)           null,
   description          nvarchar(512)        null,
   sourceEntityName     nvarchar(32)         not null,
   targetEntityName     nvarchar(32)         not null,
   version              int                  not null,
   constraint PK_EXPORT_MAPPING primary key nonclustered (exportMappingId)
);

execute sp_bindefault D_int_version, 'EXPORT_MAPPING.version';

/*==============================================================*/
/* Table: EXPORT_MAPPING_ITEM                                   */
/*==============================================================*/
create table EXPORT_MAPPING_ITEM (
   exportMappingItemId  int                  identity,
   exportMappingId      int                  null,
   sourcePropertyName   nvarchar(32)         null,
   sourcePropertyType   varchar(32)          null,
   targetPropertyName   nvarchar(32)         null,
   targetPropertyType   varchar(32)          null,
   isOverwrite          smallint             null,
   version              int                  not null,
   constraint PK_EX_MA_IT primary key nonclustered (exportMappingItemId)
);

execute sp_bindefault D_int_version, 'EXPORT_MAPPING_ITEM.version';

/*==============================================================*/
/* Table: FAQ                                                   */
/*==============================================================*/
create table FAQ (
   faqId                int                  identity,
   faqCategoryId        int                  not null,
   questionKey          varchar(32)          not null,
   answerKey            varchar(32)          not null,
   sortOrder            int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_FAQ primary key nonclustered (faqId)
);

execute sp_bindefault D_int_version, 'FAQ.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   
   ',
   'user', '', 'table', 'FAQ', 'column', 'status';

/*==============================================================*/
/* Table: FAQ_CATEGORY                                          */
/*==============================================================*/
create table FAQ_CATEGORY (
   faqCategoryId        int                  identity,
   faqCategoryTitleKey  varchar(32)          not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_FAQ_CATEGORY primary key nonclustered (faqCategoryId)
);

execute sp_bindefault D_int_version, 'FAQ_CATEGORY.version';

declare @CmtFAQ_CATEGORY varchar(128)
select @CmtFAQ_CATEGORY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Organize FAQ by category',
   'user', @CmtFAQ_CATEGORY, 'table', 'FAQ_CATEGORY';

/*==============================================================*/
/* Table: FAVORITE                                              */
/*==============================================================*/
create table FAVORITE (
   favoriteId           int                  identity,
   customerId           int                  not null,
   favoriteTitle        nvarchar(128)        not null,
   url                  varchar(255)         not null,
   createTime           datetime             not null,
   version              int                  not null,
   constraint PK_FAVORITE primary key nonclustered (favoriteId)
);

execute sp_bindefault D_int_version, 'FAVORITE.version';

/*==============================================================*/
/* Table: FEEDBACK                                              */
/*==============================================================*/
create table FEEDBACK (
   feedbackId           int                  identity,
   productId            int                  null,
   appuserId            int                  null,
   title                nvarchar(8)          null,
   firstName            nvarchar(32)         null,
   lastName             nvarchar(32)         null,
   subject              nvarchar(128)        null,
   content              nvarchar(512)        null,
   status               smallint             null,
   replyType            smallint             null,
   feedbackType         smallint             null,
   priority             int                  null,
   email                varchar(32)          null,
   telephone            varchar(32)          null,
   fax                  varchar(32)          null,
   threadId             int                  null,
   updateTime           datetime             null,
   createTime           datetime             null,
   version              int                  not null,
   constraint PK_FEEDBACK primary key nonclustered (feedbackId)
);

execute sp_bindefault D_int_version, 'FEEDBACK.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=Open
   1=Closed',
   'user', '', 'table', 'FEEDBACK', 'column', 'status';

execute sp_addextendedproperty 'MS_Description', 
   '0=online
   1=email
   2=fax 
   3=telephone',
   'user', '', 'table', 'FEEDBACK', 'column', 'replyType';

execute sp_addextendedproperty 'MS_Description', 
   '0=Complaint
   1=Suggestion',
   'user', '', 'table', 'FEEDBACK', 'column', 'feedbackType';

execute sp_addextendedproperty 'MS_Description', 
   'define the priority that should be processed',
   'user', '', 'table', 'FEEDBACK', 'column', 'priority';

/*==============================================================*/
/* Table: GIFT_CERTIFICATE                                      */
/*==============================================================*/
create table GIFT_CERTIFICATE (
   giftCertificateId    int                  identity,
   recipientContryId    int                  null,
   recipientCity        nvarchar(64)         null,
   recipientState       nvarchar(64)         null,
   giftCertificateNo    varchar(32)          not null,
   orderNo              varchar(12)          null,
   purchaser            nvarchar(64)         not null,
   recipient            nvarchar(64)         not null,
   isSentByEmail        smallint             not null,
   recipientEmail       varchar(32)          null,
   recipientFullname    nvarchar(32)         null,
   recipientAddress     nvarchar(128)        null,
   recipientZip         varchar(6)           null,
   recipientPhone       varchar(32)          null,
   message              nvarchar(512)        null,
   giftCertAmt          numeric(12,2)        not null,
   remainedAmt          numeric(12,2)        null,
   expireTime           datetime             null,
   status               smallint             not null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint P_GIFT_CERTIFICAT primary key nonclustered (giftCertificateId),
   constraint AK_KEY_2_GIFT_CER unique (giftCertificateNo)
);

execute sp_bindefault D_int_version, 'GIFT_CERTIFICATE.version';

execute sp_addextendedproperty 'MS_Description', 
   'whether gift Certificate should be sent by email
   0=No
   1=Yes',
   'user', '', 'table', 'GIFT_CERTIFICATE', 'column', 'isSentByEmail';

execute sp_addextendedproperty 'MS_Description', 
   'gift certificate total amount',
   'user', '', 'table', 'GIFT_CERTIFICATE', 'column', 'giftCertAmt';

execute sp_addextendedproperty 'MS_Description', 
   'remained gift certificate amount',
   'user', '', 'table', 'GIFT_CERTIFICATE', 'column', 'remainedAmt';

execute sp_addextendedproperty 'MS_Description', 
   'expire Time',
   'user', '', 'table', 'GIFT_CERTIFICATE', 'column', 'expireTime';

/*==============================================================*/
/* Table: HELP                                                  */
/*==============================================================*/
create table HELP (
   helpId               int                  identity,
   moduleName           nvarchar(32)         null,
   helpTitle            nvarchar(128)        not null,
   helpContent          nvarchar(1024)       not null,
   helpCode             varchar(32)          null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_HELP primary key nonclustered (helpId)
);

execute sp_bindefault D_int_version, 'HELP.version';

execute sp_addextendedproperty 'MS_Description', 
   'help comment id',
   'user', '', 'table', 'HELP', 'column', 'helpId';

/*==============================================================*/
/* Table: I18NTEXT                                              */
/*==============================================================*/
create table I18NTEXT (
   i18nTextId           int                  identity,
   textKey              varchar(32)          not null,
   category             varchar(64)          null,
   description          nvarchar(512)        null,
   moduleId             int                  null,
   createBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   updateBy             int                  null,
   version              int                  not null,
   constraint PK_I18NTEXT primary key nonclustered (i18nTextId),
   constraint AK_KEY_2_I18NTEXT_1 unique (textKey)
);

execute sp_bindefault D_int_version, 'I18NTEXT.version';

declare @CmtI18NTEXT varchar(128)
select @CmtI18NTEXT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define i18n text keys',
   'user', @CmtI18NTEXT, 'table', 'I18NTEXT';

/*==============================================================*/
/* Table: I18NTEXT_ITEM                                         */
/*==============================================================*/
create table I18NTEXT_ITEM (
   i18nTextItemId       int                  identity,
   textKey              varchar(32)          not null,
   textValue            nvarchar(1024)       not null,
   category             varchar(64)          null,
   createBy             int                  null,
   createTime           datetime             null,
   updateBy             int                  null,
   updateTime           datetime             null,
   localeCode           varchar(8)           not null,
   version              int                  not null,
   constraint PK_I18NTEXT_ITEM primary key nonclustered (i18nTextItemId),
   constraint AK_KEY_2_I18NTEXT unique (textKey, localeCode)
);

execute sp_bindefault D_int_version, 'I18NTEXT_ITEM.version';

declare @CmtI18NTEXT_ITEM varchar(128)
select @CmtI18NTEXT_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define a specific language text for a i18n text',
   'user', @CmtI18NTEXT_ITEM, 'table', 'I18NTEXT_ITEM';

execute sp_addextendedproperty 'MS_Description', 
   'locale Code',
   'user', '', 'table', 'I18NTEXT_ITEM', 'column', 'localeCode';

/*==============================================================*/
/* Table: IMPORT_EXPORT_ENTITY                                  */
/*==============================================================*/
create table IMPORT_EXPORT_ENTITY (
   entityId             int                  identity,
   operateType          smallint             not null,
   entityName           varchar(256)         not null,
   description          nvarchar(512)        null,
   version              int                  not null,
   constraint PK_IMPORT_EXPORT_ENTITY primary key nonclustered (entityId)
);

execute sp_bindefault D_int_version, 'IMPORT_EXPORT_ENTITY.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=import
   1=export',
   'user', '', 'table', 'IMPORT_EXPORT_ENTITY', 'column', 'operateType';

/*==============================================================*/
/* Table: IMPORT_MAPPING                                        */
/*==============================================================*/
create table IMPORT_MAPPING (
   importMappingId      int                  identity,
   importMappingName    nvarchar(32)         not null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             not null,
   updateTime           datetime             null,
   importFileType       varchar(8)           null,
   description          nvarchar(512)        null,
   version              int                  not null,
   constraint PK_IMPORT_MAPPING primary key nonclustered (importMappingId)
);

execute sp_bindefault D_int_version, 'IMPORT_MAPPING.version';

/*==============================================================*/
/* Table: IMPORT_MAPPING_ITEM                                   */
/*==============================================================*/
create table IMPORT_MAPPING_ITEM (
   importMappingItemId  int                  identity,
   importMappingId      int                  null,
   dbColumnName         nvarchar(32)         null,
   fileColumnIndex      int                  null,
   dataType             varchar(32)          null,
   isOverwrite          smallint             null,
   version              int                  not null,
   constraint PK_IMP_MA_ITEM primary key nonclustered (importMappingItemId)
);

execute sp_bindefault D_int_version, 'IMPORT_MAPPING_ITEM.version';

/*==============================================================*/
/* Table: INDEX_ITEM                                            */
/*==============================================================*/
create table INDEX_ITEM (
   indexItemId          int                  identity,
   contentUrl           varchar(255)         not null,
   subject              smallint             null,
   contentMark          nvarchar(64)         null,
   requestType          smallint             null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_INDEX_ITEM primary key nonclustered (indexItemId),
   constraint AK_KEY_2_INDEX_IT unique (contentUrl)
);

execute sp_bindefault D_int_version, 'INDEX_ITEM.version';

/*==============================================================*/
/* Table: MANUFACTURER                                          */
/*==============================================================*/
create table MANUFACTURER (
   manufacturerId       int                  identity,
   mediaId              int                  null,
   manufacturerCode     varchar(32)          not null,
   manufacturerNameKey  varchar(32)          not null,
   manufacturerUrl      varchar(255)         null,
   productCount         int                  null,
   status               smallint             null,
   sortOrder            int                  null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_MANUFACTURER primary key nonclustered (manufacturerId),
   constraint AK_KEY_2_MANUFACT unique (manufacturerCode)
);

execute sp_bindefault D_small_deleted, 'MANUFACTURER.deleted';

execute sp_bindefault D_int_version, 'MANUFACTURER.version';

declare @CmtMANUFACTURER varchar(128)
select @CmtMANUFACTURER = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Manufacturer that produce the product',
   'user', @CmtMANUFACTURER, 'table', 'MANUFACTURER';

execute sp_addextendedproperty 'MS_Description', 
   'site url of manufacturer',
   'user', '', 'table', 'MANUFACTURER', 'column', 'manufacturerUrl';

execute sp_addextendedproperty 'MS_Description', 
   'how many products of this manufacturer',
   'user', '', 'table', 'MANUFACTURER', 'column', 'productCount';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'MANUFACTURER', 'column', 'status';

/*==============================================================*/
/* Table: MEDIA_MAPPING_RULE                                    */
/*==============================================================*/
create table MEDIA_MAPPING_RULE (
   mappingId            int                  identity,
   mappingName          varchar(128)         not null,
   mappingRule          varchar(128)         null,
   mappingDescription   nvarchar(1024)       null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_MEDIA_MAPPING_RULE primary key nonclustered (mappingId)
);

execute sp_bindefault D_int_version, 'MEDIA_MAPPING_RULE.version';

/*==============================================================*/
/* Table: MEDIA_TYPE                                            */
/*==============================================================*/
create table MEDIA_TYPE (
   mediaTypeId          int                  identity,
   typeName             nvarchar(64)         not null,
   filenameRule         nvarchar(128)        null,
   mediaTypeKey         varchar(32)          null,
   version              int                  not null,
   constraint PK_MEDIA_TYPE primary key nonclustered (mediaTypeId)
);

execute sp_bindefault D_int_version, 'MEDIA_TYPE.version';

declare @CmtMEDIA_TYPE varchar(128)
select @CmtMEDIA_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Types of image supported/used.',
   'user', @CmtMEDIA_TYPE, 'table', 'MEDIA_TYPE';

execute sp_addextendedproperty 'MS_Description', 
   'the path/location this type of image are stored',
   'user', '', 'table', 'MEDIA_TYPE', 'column', 'filenameRule';

/*==============================================================*/
/* Table: MEMBERSHIP                                            */
/*==============================================================*/
create table MEMBERSHIP (
   membershipId         int                  identity,
   membershipNameKey    varchar(32)          not null,
   membershipDetailKey  varchar(32)          null,
   membershipLevel      int                  not null,
   status               smallint             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_MEMBERSHIP primary key nonclustered (membershipId),
   constraint AK_KEY_2_MEMBERSH unique (membershipNameKey),
   constraint AK_KEY_3_MEMBERSH unique (membershipLevel)
);

execute sp_bindefault D_small_deleted, 'MEMBERSHIP.deleted';

execute sp_bindefault D_int_version, 'MEMBERSHIP.version';

declare @CmtMEMBERSHIP varchar(128)
select @CmtMEMBERSHIP = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Customer membership, each has a unique level, thus a higher level membership auto get access to assets granted to a lower level membership.',
   'user', @CmtMEMBERSHIP, 'table', 'MEMBERSHIP';

execute sp_addextendedproperty 'MS_Description', 
   'A unique and sequential level, higher level auto get access to assets granted to lower level membership.',
   'user', '', 'table', 'MEMBERSHIP', 'column', 'membershipLevel';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'MEMBERSHIP', 'column', 'status';

/*==============================================================*/
/* Table: MENU                                                  */
/*==============================================================*/
create table MENU (
   menuId               int                  identity,
   menuNameKey          varchar(32)          not null,
   url                  varchar(255)         not null,
   categoryId           int                  null,
   sortOrder            int                  null,
   status               smallint             null,
   constraint PK_MENU primary key nonclustered (menuId)
);

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'MENU', 'column', 'status';

/*==============================================================*/
/* Table: MENU_MENU                                             */
/*==============================================================*/
create table MENU_MENU (
   menuMenuId           int                  identity,
   menuId               int                  not null,
   subMenuId            int                  not null,
   constraint PK_MENU_MENU primary key nonclustered (menuMenuId)
);

/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
create table MESSAGE (
   messageId            int                  identity,
   productId            int                  null,
   categoryId           int                  null,
   messageSubjectKey    varchar(32)          not null,
   messageBodyKey       varchar(32)          not null,
   isSupportedHtml      smallint             null,
   isShowInNews         smallint             null,
   isAvailableForSubscription smallint             null,
   testEmail            varchar(32)          null,
   templatePath         varchar(128)         null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_MESSAGE primary key nonclustered (messageId)
);

execute sp_bindefault D_int_version, 'MESSAGE.version';

declare @CmtMESSAGE varchar(128)
select @CmtMESSAGE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Message that composed by admin and will be sent to customer',
   'user', @CmtMESSAGE, 'table', 'MESSAGE';

execute sp_addextendedproperty 'MS_Description', 
   '0=not support html
   1=support html',
   'user', '', 'table', 'MESSAGE', 'column', 'isSupportedHtml';

execute sp_addextendedproperty 'MS_Description', 
   '0=not show in news
   1=show in news',
   'user', '', 'table', 'MESSAGE', 'column', 'isShowInNews';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes
   
   ',
   'user', '', 'table', 'MESSAGE', 'column', 'isAvailableForSubscription';

execute sp_addextendedproperty 'MS_Description', 
   'email address that use for test purpose',
   'user', '', 'table', 'MESSAGE', 'column', 'testEmail';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'MESSAGE', 'column', 'status';

/*==============================================================*/
/* Table: MESSAGE_LIST                                          */
/*==============================================================*/
create table MESSAGE_LIST (
   messageListId        int                  identity,
   messageListNameKey   varchar(32)          not null,
   messageListDetailKey varchar(32)          null,
   status               smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   deleted              smallint             not null,
   constraint PK_MESSAGE_LIST primary key nonclustered (messageListId),
   constraint AK_KEY_2_MESSAGE_ unique (messageListNameKey)
);

execute sp_bindefault D_int_version, 'MESSAGE_LIST.version';

execute sp_bindefault D_small_deleted, 'MESSAGE_LIST.deleted';

declare @CmtMESSAGE_LIST varchar(128)
select @CmtMESSAGE_LIST = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Message list that used to organize message and can be subscribed by customer',
   'user', @CmtMESSAGE_LIST, 'table', 'MESSAGE_LIST';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'MESSAGE_LIST', 'column', 'status';

/*==============================================================*/
/* Table: MESSAGE_LIST_ITEM                                     */
/*==============================================================*/
create table MESSAGE_LIST_ITEM (
   messageListItemId    int                  identity,
   messageListId        int                  not null,
   messageId            int                  not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_M_LIST_ITEM primary key nonclustered (messageListItemId)
);

execute sp_bindefault D_int_version, 'MESSAGE_LIST_ITEM.version';

declare @CmtMESSAGE_LIST_ITEM varchar(128)
select @CmtMESSAGE_LIST_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Message belongs to which message list',
   'user', @CmtMESSAGE_LIST_ITEM, 'table', 'MESSAGE_LIST_ITEM';

/*==============================================================*/
/* Table: METRIC_TYPE                                           */
/*==============================================================*/
create table METRIC_TYPE (
   metricTypeId         int                  identity,
   metricTypeCode       varchar(32)          not null,
   metricTypeNameKey    varchar(32)          not null,
   description          nvarchar(1024)       null,
   version              int                  not null,
   constraint PK_METRIC_TYPE primary key nonclustered (metricTypeId)
);

execute sp_bindefault D_int_version, 'METRIC_TYPE.version';

/*==============================================================*/
/* Table: METRIC_UNIT                                           */
/*==============================================================*/
create table METRIC_UNIT (
   metricUnitId         int                  identity,
   metricTypeId         int                  not null,
   metricUnitCode       varchar(32)          not null,
   metricUnitNameKey    varchar(32)          not null,
   isDefault            smallint             null,
   convertRate          numeric(12,2)        null,
   version              int                  not null,
   constraint PK_METRIC_UNIT primary key nonclustered (metricUnitId),
   constraint AK_KEY_2_METRIC_U unique (metricUnitCode)
);

execute sp_bindefault D_int_version, 'METRIC_UNIT.version';

execute sp_addextendedproperty 'MS_Description', 
   'length: start with"L_"
   weight: start with"W_"
   money: start with"M_"
   volume start with"V_"',
   'user', '', 'table', 'METRIC_UNIT', 'column', 'metricUnitCode';

execute sp_addextendedproperty 'MS_Description', 
   '0=not default
   1=is default',
   'user', '', 'table', 'METRIC_UNIT', 'column', 'isDefault';

/*==============================================================*/
/* Table: MODULE_HELP                                           */
/*==============================================================*/
create table MODULE_HELP (
   moduleHelpId         int                  identity,
   moduleUrl            varchar(255)         not null,
   helpContent          nvarchar(1024)       null,
   constraint PK_MODULE_HELP primary key nonclustered (moduleHelpId)
);

/*==============================================================*/
/* Table: ORDER_COUPON                                          */
/*==============================================================*/
create table ORDER_COUPON (
   orderCouponId        int                  identity,
   orderId              int                  not null,
   discountAmt          numeric(12,2)        null,
   couponId             int                  null,
   couponTypeId         int                  null,
   needToCalculate      smallint             null,
   version              int                  not null,
   constraint PK_ORDER_COUPON primary key nonclustered (orderCouponId)
);

execute sp_bindefault D_int_version, 'ORDER_COUPON.version';

declare @CmtORDER_COUPON varchar(128)
select @CmtORDER_COUPON = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Coupon used in an order',
   'user', @CmtORDER_COUPON, 'table', 'ORDER_COUPON';

execute sp_addextendedproperty 'MS_Description', 
   'total amount discounted using this coupon',
   'user', '', 'table', 'ORDER_COUPON', 'column', 'discountAmt';

execute sp_addextendedproperty 'MS_Description', 
   'for special coupon whick could''t to item saveMoney''s count.
   
   1:  need to calculate when stat. subtotal
   
   0:  not need
   
    
   ',
   'user', '', 'table', 'ORDER_COUPON', 'column', 'needToCalculate';

/*==============================================================*/
/* Table: ORDER_GIFT_CERTIFICATE                                */
/*==============================================================*/
create table ORDER_GIFT_CERTIFICATE (
   orderGiftCertificateId int                  identity,
   orderId              int                  not null,
   giftCertificateId    int                  not null,
   usedAmt              numeric(12,2)        null,
   version              int                  not null,
   constraint PK_OR_GI_CERT primary key nonclustered (orderGiftCertificateId)
);

execute sp_bindefault D_int_version, 'ORDER_GIFT_CERTIFICATE.version';

declare @CmtORDER_GIFT_CERTIFICATE varchar(128)
select @CmtORDER_GIFT_CERTIFICATE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Gift certificate used in an order',
   'user', @CmtORDER_GIFT_CERTIFICATE, 'table', 'ORDER_GIFT_CERTIFICATE';

execute sp_addextendedproperty 'MS_Description', 
   'amount of gift certificate used in this order',
   'user', '', 'table', 'ORDER_GIFT_CERTIFICATE', 'column', 'usedAmt';

/*==============================================================*/
/* Table: ORDER_ITEM                                            */
/*==============================================================*/
create table ORDER_ITEM (
   orderItemId          int                  identity,
   orderId              int                  not null,
   productId            int                  null,
   giftCertificateId    int                  null,
   itemType             smallint             null,
   quantity             int                  not null,
   price                numeric(12,2)        not null,
   addTime              datetime             not null,
   discountPrice        numeric(12,2)        null,
   discoutValidFlag     smallint             null,
   hasUnitedPmt         smallint             null,
   isGroup              smallint             null,
   isFreeShipping       smallint             null,
   isExempt             smallint             null,
   wrapId               int                  null,
   wrapCost             numeric(12,2)        null,
   wrapNote             nvarchar(1024)       null,
   isSpecialOffer       smallint             null,
   saveMoney            numeric(12,2)        null,
   productAttributes    varbinary(3000)      null,
   categoryPath         varchar(255)         not null,
   version              int                  not null,
   constraint PK_ORDER_ITEM primary key nonclustered (orderItemId)
);

execute sp_bindefault D_int_version, 'ORDER_ITEM.version';

execute sp_addextendedproperty 'MS_Description', 
   'types of items/goods:
   1:product (default)
   2:gift certificate
   3:service
   4:software
   5:document
   6:music
   7:video
   8:others',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'itemType';

execute sp_addextendedproperty 'MS_Description', 
   'price after discounted',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'discountPrice';

execute sp_addextendedproperty 'MS_Description', 
   'has united promotion',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'hasUnitedPmt';

execute sp_addextendedproperty 'MS_Description', 
   'Is this item free for shipping
   0=No
   1=Yes
   ',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'isFreeShipping';

execute sp_addextendedproperty 'MS_Description', 
   'Is tax free/exempt
   0=No
   1=Yes',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'isExempt';

execute sp_addextendedproperty 'MS_Description', 
   'wrap type id',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'wrapId';

execute sp_addextendedproperty 'MS_Description', 
   'wrapping cost  of this item',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'wrapCost';

execute sp_addextendedproperty 'MS_Description', 
   'notes that should be written on the wrap',
   'user', '', 'table', 'ORDER_ITEM', 'column', 'wrapNote';

/*==============================================================*/
/* Table: ORDER_ITEM_ATTRIBUTE                                  */
/*==============================================================*/
create table ORDER_ITEM_ATTRIBUTE (
   orderItemAttributeId int                  identity,
   attributeId          int                  not null,
   attributeNameKey     varchar(32)          null,
   orderItemId          int                  not null,
   attributeOptionNameKey varchar(32)          null,
   attributeValue       varchar(128)         null,
   version              int                  not null,
   constraint PK_ORD_ITEM_ATTR2 primary key nonclustered (orderItemAttributeId)
);

execute sp_bindefault D_int_version, 'ORDER_ITEM_ATTRIBUTE.version';

declare @CmtORDER_ITEM_ATTRIBUTE varchar(128)
select @CmtORDER_ITEM_ATTRIBUTE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Product attributes of an order item',
   'user', @CmtORDER_ITEM_ATTRIBUTE, 'table', 'ORDER_ITEM_ATTRIBUTE';

/*==============================================================*/
/* Table: ORDER_ITEM_PROMOTION                                  */
/*==============================================================*/
create table ORDER_ITEM_PROMOTION (
   orderItemPromotionId int                  identity,
   orderItemId          int                  null,
   promotionId          int                  null,
   titleKey             varchar(32)          null,
   descriptionKey       varchar(32)          null,
   saveMoney            numeric(12,2)        null,
   validFlag            smallint             null,
   isUnited             smallint             null,
   version              int                  not null,
   constraint PK_ORDER_ITEM_PROMOTION primary key nonclustered (orderItemPromotionId)
);

execute sp_bindefault D_int_version, 'ORDER_ITEM_PROMOTION.version';

declare @CmtORDER_ITEM_PROMOTION varchar(128)
select @CmtORDER_ITEM_PROMOTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '
   ',
   'user', @CmtORDER_ITEM_PROMOTION, 'table', 'ORDER_ITEM_PROMOTION';

execute sp_addextendedproperty 'MS_Description', 
   '0=is not valid
   1=is valid',
   'user', '', 'table', 'ORDER_ITEM_PROMOTION', 'column', 'validFlag';

execute sp_addextendedproperty 'MS_Description', 
   '0=is not united
   1=is united',
   'user', '', 'table', 'ORDER_ITEM_PROMOTION', 'column', 'isUnited';

/*==============================================================*/
/* Table: ORDER_PAYMENT                                         */
/*==============================================================*/
create table ORDER_PAYMENT (
   orderPaymentId       int                  identity,
   orderId              int                  not null,
   paymentMehodId       int                  not null,
   creditCardType       varchar(32)          null,
   creditCardNo         varchar(32)          null,
   cvv2                 varchar(8)           null,
   creditExpireDate     datetime             null,
   paymentAmt           numeric(12,2)        not null,
   currency             nvarchar(32)         null,
   createTime           datetime             not null,
   version              int                  not null,
   constraint PK_ORDER_PAYMENT primary key nonclustered (orderPaymentId)
);

execute sp_bindefault D_int_version, 'ORDER_PAYMENT.version';

declare @CmtORDER_PAYMENT varchar(128)
select @CmtORDER_PAYMENT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Payment info of an order',
   'user', @CmtORDER_PAYMENT, 'table', 'ORDER_PAYMENT';

/*==============================================================*/
/* Table: ORDER_SHIPMENT                                        */
/*==============================================================*/
create table ORDER_SHIPMENT (
   orderShipmentId      int                  identity,
   orderId              int                  not null,
   shippingRateId       int                  null,
   trackingNo           nvarchar(128)        null,
   shippingCost         numeric(12,2)        null,
   shippingStartTime    datetime             null,
   shippingArriveTime   datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_ORDER_SHIPMENT primary key nonclustered (orderShipmentId)
);

execute sp_bindefault D_int_version, 'ORDER_SHIPMENT.version';

declare @CmtORDER_SHIPMENT varchar(128)
select @CmtORDER_SHIPMENT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Shipment info of an order',
   'user', @CmtORDER_SHIPMENT, 'table', 'ORDER_SHIPMENT';

execute sp_addextendedproperty 'MS_Description', 
   'tracking no of this shipment',
   'user', '', 'table', 'ORDER_SHIPMENT', 'column', 'trackingNo';

execute sp_addextendedproperty 'MS_Description', 
   'start time of shipping',
   'user', '', 'table', 'ORDER_SHIPMENT', 'column', 'shippingStartTime';

execute sp_addextendedproperty 'MS_Description', 
   'when the shipping arrived and completed',
   'user', '', 'table', 'ORDER_SHIPMENT', 'column', 'shippingArriveTime';

execute sp_addextendedproperty 'MS_Description', 
   '0=Stockup
   1=Short
   2=Delivering
   3=Delivered',
   'user', '', 'table', 'ORDER_SHIPMENT', 'column', 'status';

/*==============================================================*/
/* Table: ORDER_SHIPMENT_ITEM                                   */
/*==============================================================*/
create table ORDER_SHIPMENT_ITEM (
   orderShipmentItemId  int                  identity,
   orderItemId          int                  not null,
   orderShipmentId      int                  not null,
   quantity             int                  not null,
   version              int                  not null,
   constraint PK_ORD_SHI_IT primary key nonclustered (orderShipmentItemId)
);

execute sp_bindefault D_int_version, 'ORDER_SHIPMENT_ITEM.version';

declare @CmtORDER_SHIPMENT_ITEM varchar(128)
select @CmtORDER_SHIPMENT_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Which items are delivered in each shipment',
   'user', @CmtORDER_SHIPMENT_ITEM, 'table', 'ORDER_SHIPMENT_ITEM';

execute sp_addextendedproperty 'MS_Description', 
   'how many items are shipped',
   'user', '', 'table', 'ORDER_SHIPMENT_ITEM', 'column', 'quantity';

/*==============================================================*/
/* Table: PAYMENT_GATEWAY                                       */
/*==============================================================*/
create table PAYMENT_GATEWAY (
   paymentGatewayId     int                  identity,
   paymentGatewayName   nvarchar(256)        not null,
   paymentGatewayDetail nvarchar(1024)       null,
   processorFile        varchar(256)         null,
   configFile           varchar(255)         null,
   configData           varbinary(3000)      null,
   gatewayIcon          varchar(255)         null,
   isAdded              smallint             null,
   type                 CHAR(1)              null default 'C',
   testModel            CHAR(1)              null default 'N',
   paymentGatewayCode   varchar(32)          not null,
   version              int                  not null,
   constraint PK_PAYMENT_GATEWAY primary key nonclustered (paymentGatewayId),
   constraint AK_KEY_2_PAYMENT_ unique (paymentGatewayCode)
);

execute sp_bindefault D_int_version, 'PAYMENT_GATEWAY.version';

execute sp_addextendedproperty 'MS_Description', 
   'processor the payment gateway request, such as "verisign.jsp"
   ',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'processorFile';

execute sp_addextendedproperty 'MS_Description', 
   'the name of the file which is provide the page to config the payment gateway',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'configFile';

execute sp_addextendedproperty 'MS_Description', 
   'store data for a paymentgateway configuration as a java serialize object
   (Java object : PaymentConfig)',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'configData';

execute sp_addextendedproperty 'MS_Description', 
   '0=Not yet added
   1=Already added to payment method
   ',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'isAdded';

execute sp_addextendedproperty 'MS_Description', 
   'payment gateway type (hold in future use)',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'type';

execute sp_addextendedproperty 'MS_Description', 
   'Y==test model
   N==live model',
   'user', '', 'table', 'PAYMENT_GATEWAY', 'column', 'testModel';

/*==============================================================*/
/* Table: PAYMENT_HISTORY                                       */
/*==============================================================*/
create table PAYMENT_HISTORY (
   paymentHistoryId     int                  identity,
   orderNo              varchar(12)          not null,
   flag                 smallint             not null,
   flowNo               varchar(64)          null,
   errorMessage         nvarchar(256)        null,
   errorCode            varchar(32)          null,
   remoteIp             varchar(32)          null,
   receiveData          nvarchar(1024)       null,
   isBrowsed            smallint             null default 0,
   createTime           datetime             null,
   version              int                  not null,
   constraint PK_PAYMENT_HISTORY primary key nonclustered (paymentHistoryId)
);

execute sp_bindefault D_int_version, 'PAYMENT_HISTORY.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=failure
   1=success',
   'user', '', 'table', 'PAYMENT_HISTORY', 'column', 'flag';

execute sp_addextendedproperty 'MS_Description', 
   '1=has browse
   0=not browse
   ',
   'user', '', 'table', 'PAYMENT_HISTORY', 'column', 'isBrowsed';

/*==============================================================*/
/* Table: PAYMENT_METHOD                                        */
/*==============================================================*/
create table PAYMENT_METHOD (
   paymentMethodId      int                  identity,
   paymentGatewayId     int                  null,
   paymentMethodNameKey varchar(32)          not null,
   paymentMethodDetailKey varchar(32)          null,
   paymentMethodType    smallint             null,
   protocol             varchar(8)           null,
   sortOrder            int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_PAYMENT_METHOD primary key nonclustered (paymentMethodId)
);

execute sp_bindefault D_int_version, 'PAYMENT_METHOD.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=Online
   2=Offline',
   'user', '', 'table', 'PAYMENT_METHOD', 'column', 'paymentMethodType';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'PAYMENT_METHOD', 'column', 'status';

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
create table PRODUCT (
   productId            int                  identity,
   departmentId         int                  null,
   manufacturerId       int                  null,
   productCode          varchar(32)          not null,
   productNameKey       varchar(32)          not null,
   customerRating       varchar(32)          null,
   merchantRating       varchar(32)          null,
   listPrice            numeric(12,2)        null,
   price                numeric(12,2)        not null,
   minOrderQuantity     int                  null,
   weight               numeric(12,2)        null,
   length               numeric(12,2)        null,
   width                numeric(12,2)        null,
   height               numeric(12,2)        null,
   shortDescriptionKey  varchar(32)          null,
   fullDescriptionKey   varchar(32)          null,
   manufacturerProductCode varchar(32)          null,
   status               smallint             null,
   membershipId         int                  null,
   metaKeywordKey       varchar(32)          null,
   metaDescriptionkey   varchar(32)          null,
   templatePath         varchar(128)         null,
   productType          smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   stockQty             int                  not null,
   stockMinQuantity     int                  null,
   buyCount             int                  not null,
   categoryPath         varchar(255)         not null,
   departmentNamekey    varchar(32)          null,
   manufacturerNameKey  varchar(32)          null,
   thumbnailImageUrl    varchar(255)         null,
   normalImageUrl       varchar(255)         null,
   isMultipleImage      smallint             null,
   qtyUnitCode          varchar(32)          null,
   qtyUnitNameKey       varchar(32)          null,
   lengthUnitCode       varchar(32)          null,
   lengthUnitNameKey    varchar(32)          null,
   weightUnitCode       varchar(32)          null,
   weightUnitNameKey    varchar(32)          null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_PRODUCT primary key nonclustered (productId),
   constraint AK_KEY_2_PRODUCT unique (productCode),
   constraint AK_Key_Pro_2 unique (categoryPath)
);

execute sp_bindefault D_small_deleted, 'PRODUCT.deleted';

execute sp_bindefault D_int_version, 'PRODUCT.version';

execute sp_addextendedproperty 'MS_Description', 
   'Product rating made by customer',
   'user', '', 'table', 'PRODUCT', 'column', 'customerRating';

execute sp_addextendedproperty 'MS_Description', 
   'rating by the merchant',
   'user', '', 'table', 'PRODUCT', 'column', 'merchantRating';

execute sp_addextendedproperty 'MS_Description', 
   'normal selling price, when in a promotion, this product can in sold in a lower price',
   'user', '', 'table', 'PRODUCT', 'column', 'price';

execute sp_addextendedproperty 'MS_Description', 
   'min quantity of products that must be purchased in one order',
   'user', '', 'table', 'PRODUCT', 'column', 'minOrderQuantity';

execute sp_addextendedproperty 'MS_Description', 
   'weight of product',
   'user', '', 'table', 'PRODUCT', 'column', 'weight';

execute sp_addextendedproperty 'MS_Description', 
   'length of product, optional',
   'user', '', 'table', 'PRODUCT', 'column', 'length';

execute sp_addextendedproperty 'MS_Description', 
   'width of product, optional',
   'user', '', 'table', 'PRODUCT', 'column', 'width';

execute sp_addextendedproperty 'MS_Description', 
   'height of product, optional',
   'user', '', 'table', 'PRODUCT', 'column', 'height';

execute sp_addextendedproperty 'MS_Description', 
   'brief/short product description, this is the i18n text key',
   'user', '', 'table', 'PRODUCT', 'column', 'shortDescriptionKey';

execute sp_addextendedproperty 'MS_Description', 
   'full product description, this is the i18n text key',
   'user', '', 'table', 'PRODUCT', 'column', 'fullDescriptionKey';

execute sp_addextendedproperty 'MS_Description', 
   '0=Not published(default status when created, can edit info, but won''t show to the customer and cannot accept order)
   1=Active, Ready for sale
   2=inactive
   3=deleted
   
   10=Preorder (can accept preorder only)',
   'user', '', 'table', 'PRODUCT', 'column', 'status';

execute sp_addextendedproperty 'MS_Description', 
   'If defined, only customer having specified membership or above (determine by level) can see the category and products under it.',
   'user', '', 'table', 'PRODUCT', 'column', 'membershipId';

execute sp_addextendedproperty 'MS_Description', 
   'keyword of product, will be output in page meta block, use comma to seperate',
   'user', '', 'table', 'PRODUCT', 'column', 'metaKeywordKey';

execute sp_addextendedproperty 'MS_Description', 
   '0=normal product
   1=product package
   2=product variation',
   'user', '', 'table', 'PRODUCT', 'column', 'productType';

execute sp_addextendedproperty 'MS_Description', 
   'how many items are there in the stock',
   'user', '', 'table', 'PRODUCT', 'column', 'stockQty';

execute sp_addextendedproperty 'MS_Description', 
   'min quantity of products in stock, alert when stock level go below this',
   'user', '', 'table', 'PRODUCT', 'column', 'stockMinQuantity';

execute sp_addextendedproperty 'MS_Description', 
   'this product has more than one image or not',
   'user', '', 'table', 'PRODUCT', 'column', 'isMultipleImage';

/*==============================================================*/
/* Index: IX_PROD_BUYCOUNT                                      */
/*==============================================================*/
create index IX_PROD_BUYCOUNT on PRODUCT (
buyCount  DESC
);

/*==============================================================*/
/* Index: IX_PROD_STATUS                                        */
/*==============================================================*/
create index IX_PROD_STATUS on PRODUCT (
status  ASC,
deleted  ASC,
productType  ASC
);

/*==============================================================*/
/* Table: PRODUCT_ADVERTISEMENT                                 */
/*==============================================================*/
create table PRODUCT_ADVERTISEMENT (
   productAdvertisementId int                  identity,
   advertisementId      int                  not null,
   categoryPath         varchar(255)         not null,
   version              int                  not null,
   constraint PK_PRODAD primary key nonclustered (productAdvertisementId)
);

execute sp_bindefault D_int_version, 'PRODUCT_ADVERTISEMENT.version';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)',
   'user', '', 'table', 'PRODUCT_ADVERTISEMENT', 'column', 'categoryPath';

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE                                     */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE (
   productAttributeId   int                  identity,
   attributeId          int                  null,
   categoryPath         varchar(255)         not null,
   isCategory           smallint             null,
   isRequired           smallint             null,
   defaultValue         varchar(128)         null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_PROD_ATTRI primary key nonclustered (productAttributeId)
);

execute sp_bindefault D_int_version, 'PRODUCT_ATTRIBUTE.version';

declare @CmtPRODUCT_ATTRIBUTE varchar(128)
select @CmtPRODUCT_ATTRIBUTE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Product/category attribute, attribute are defined using product/category path.',
   'user', @CmtPRODUCT_ATTRIBUTE, 'table', 'PRODUCT_ATTRIBUTE';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)
   can use product/category.categoryPath.startWith(PRODUCT_ATRIBUTE.categoryPath) and tell if this attribute is specified for this product/category
   ',
   'user', '', 'table', 'PRODUCT_ATTRIBUTE', 'column', 'categoryPath';

execute sp_addextendedproperty 'MS_Description', 
   '0=is product
   1=is category',
   'user', '', 'table', 'PRODUCT_ATTRIBUTE', 'column', 'isCategory';

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE_OPTION                              */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE_OPTION (
   productAttributeOptionId int                  identity,
   attributeOptionId    int                  null,
   productAttributeId   int                  null,
   categoryPath         varchar(255)         not null,
   version              int                  not null,
   constraint PK_PR_ATTRE_OPT primary key nonclustered (productAttributeOptionId)
);

execute sp_bindefault D_int_version, 'PRODUCT_ATTRIBUTE_OPTION.version';

declare @CmtPRODUCT_ATTRIBUTE_OPTION varchar(128)
select @CmtPRODUCT_ATTRIBUTE_OPTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Available attribute options for each product attribute',
   'user', @CmtPRODUCT_ATTRIBUTE_OPTION, 'table', 'PRODUCT_ATTRIBUTE_OPTION';

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE_VALUE                               */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE_VALUE (
   productAttributeValueId int                  identity,
   attributeId          int                  null,
   categoryPath         varchar(255)         not null,
   isCategory           smallint             null,
   inputValueKey        varchar(32)          not null,
   attributeNameKey     varchar(32)          null,
   constraint PK_PRODUCT_ATTRIBUTE_VALUE primary key nonclustered (productAttributeValueId)
);

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)
   can use product/category.categoryPath.startWith(PRODUCT_ATRIBUTE.categoryPath) and tell if this attribute is specified for this product/category
   ',
   'user', '', 'table', 'PRODUCT_ATTRIBUTE_VALUE', 'column', 'categoryPath';

execute sp_addextendedproperty 'MS_Description', 
   '0=is product
   1=is category',
   'user', '', 'table', 'PRODUCT_ATTRIBUTE_VALUE', 'column', 'isCategory';

/*==============================================================*/
/* Table: PRODUCT_CATEGORY                                      */
/*==============================================================*/
create table PRODUCT_CATEGORY (
   productCategoryId    int                  identity,
   categoryId           int                  not null,
   productId            int                  not null,
   isMainCategory       smallint             null,
   categoryPath         varchar(255)         not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_PROD_CATE primary key nonclustered (productCategoryId),
   constraint AK_KEY_2_PRO_CATE_2 unique (categoryPath)
);

execute sp_bindefault D_int_version, 'PRODUCT_CATEGORY.version';

declare @CmtPRODUCT_CATEGORY varchar(128)
select @CmtPRODUCT_CATEGORY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'A product belongs to which categories, one is master others are slave',
   'user', @CmtPRODUCT_CATEGORY, 'table', 'PRODUCT_CATEGORY';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'PRODUCT_CATEGORY', 'column', 'isMainCategory';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)',
   'user', '', 'table', 'PRODUCT_CATEGORY', 'column', 'categoryPath';

/*==============================================================*/
/* Table: PRODUCT_MEDIA                                         */
/*==============================================================*/
create table PRODUCT_MEDIA (
   productMediaId       int                  identity,
   mediaId              int                  null,
   productId            int                  null,
   constraint PK_PRODUCT_MEDIA primary key nonclustered (productMediaId)
);

declare @CmtPRODUCT_MEDIA varchar(128)
select @CmtPRODUCT_MEDIA = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Images used by each product',
   'user', @CmtPRODUCT_MEDIA, 'table', 'PRODUCT_MEDIA';

/*==============================================================*/
/* Table: PRODUCT_PACKAGE_ITEM                                  */
/*==============================================================*/
create table PRODUCT_PACKAGE_ITEM (
   productPackageItemId int                  identity,
   productId            int                  not null,
   itemId               int                  not null,
   version              int                  not null,
   constraint PK_PR_PA_IT primary key nonclustered (productPackageItemId)
);

execute sp_bindefault D_int_version, 'PRODUCT_PACKAGE_ITEM.version';

declare @CmtPRODUCT_PACKAGE_ITEM varchar(128)
select @CmtPRODUCT_PACKAGE_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'A package contains what products.',
   'user', @CmtPRODUCT_PACKAGE_ITEM, 'table', 'PRODUCT_PACKAGE_ITEM';

/*==============================================================*/
/* Table: PRODUCT_RATING                                        */
/*==============================================================*/
create table PRODUCT_RATING (
   productRatingId      int                  identity,
   productId            int                  not null,
   customerId           int                  not null,
   remoteIp             varchar(32)          not null,
   rateValue            int                  not null,
   ratingTime           datetime             not null,
   version              int                  not null,
   constraint PK_PRODUCT_RATING primary key nonclustered (productRatingId)
);

execute sp_bindefault D_int_version, 'PRODUCT_RATING.version';

/*==============================================================*/
/* Index: prod_rate_FK                                          */
/*==============================================================*/
create index prod_rate_FK on PRODUCT_RATING (
productId  DESC
);

/*==============================================================*/
/* Table: PRODUCT_REVIEW                                        */
/*==============================================================*/
create table PRODUCT_REVIEW (
   productReviewId      int                  identity,
   productId            int                  not null,
   customerId           int                  not null,
   subject              nvarchar(128)        not null,
   message              nvarchar(1024)       not null,
   remoteIp             varchar(32)          not null,
   createTime           datetime             null,
   updateTime           datetime             null,
   applyGrade           int                  null,
   applyGradeTime       datetime             null,
   version              int                  not null,
   constraint PK_PRODUCT_REVIEW primary key nonclustered (productReviewId)
);

execute sp_bindefault D_int_version, 'PRODUCT_REVIEW.version';

/*==============================================================*/
/* Index: prod_review_FK                                        */
/*==============================================================*/
create index prod_review_FK on PRODUCT_REVIEW (
productId  ASC
);

/*==============================================================*/
/* Table: PRODUCT_SERIES                                        */
/*==============================================================*/
create table PRODUCT_SERIES (
   productSeriesId      int                  identity,
   productSeriesNameKey varchar(32)          not null,
   seriesCode           varchar(32)          not null,
   productSeriesDetailKey varchar(32)          null,
   templatePath         varchar(128)         null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_PRODUCT_SERIES primary key nonclustered (productSeriesId),
   constraint AK_KEY_3_PRODUCT_ unique (seriesCode)
);

execute sp_bindefault D_int_version, 'PRODUCT_SERIES.version';

declare @CmtPRODUCT_SERIES varchar(128)
select @CmtPRODUCT_SERIES = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'A series is several products has something in common, e.g. they are produced by the same author.',
   'user', @CmtPRODUCT_SERIES, 'table', 'PRODUCT_SERIES';

/*==============================================================*/
/* Table: PRODUCT_SERIES_ITEM                                   */
/*==============================================================*/
create table PRODUCT_SERIES_ITEM (
   productSeriesItemId  int                  identity,
   productSeriesId      int                  not null,
   productId            int                  not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_PRO_SERIES_IT primary key nonclustered (productSeriesItemId)
);

execute sp_bindefault D_int_version, 'PRODUCT_SERIES_ITEM.version';

declare @CmtPRODUCT_SERIES_ITEM varchar(128)
select @CmtPRODUCT_SERIES_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'A series contains what products.',
   'user', @CmtPRODUCT_SERIES_ITEM, 'table', 'PRODUCT_SERIES_ITEM';

/*==============================================================*/
/* Table: PRODUCT_TAB                                           */
/*==============================================================*/
create table PRODUCT_TAB (
   productTabId         int                  identity,
   tabId                int                  not null,
   categoryPath         varchar(255)         not null,
   isCategory           smallint             null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_PRODUCT_TAB primary key nonclustered (productTabId)
);

execute sp_bindefault D_int_version, 'PRODUCT_TAB.version';

declare @CmtPRODUCT_TAB varchar(128)
select @CmtPRODUCT_TAB = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Tabs of each product',
   'user', @CmtPRODUCT_TAB, 'table', 'PRODUCT_TAB';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)
   can use product/category.categoryPath.startWith(PRODUCT_TAB.categoryPath) and tell if this tab is specified for this product/category
   
   ',
   'user', '', 'table', 'PRODUCT_TAB', 'column', 'categoryPath';

execute sp_addextendedproperty 'MS_Description', 
   '0=is product
   1=is category',
   'user', '', 'table', 'PRODUCT_TAB', 'column', 'isCategory';

/*==============================================================*/
/* Table: PRODUCT_VARIATION                                     */
/*==============================================================*/
create table PRODUCT_VARIATION (
   productVariationId   int                  identity,
   sortOrder            int                  null,
   productCode          varchar(32)          not null,
   categoryPath         varchar(255)         not null,
   price                numeric(12,2)        null,
   weight               numeric(12,2)        null,
   originCategoryPath   varchar(255)         not null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_PROD_VARIA primary key nonclustered (productVariationId),
   constraint AK_KEY_2_PRO_VAR_1 unique (productCode)
);

execute sp_bindefault D_int_version, 'PRODUCT_VARIATION.version';

declare @CmtPRODUCT_VARIATION varchar(128)
select @CmtPRODUCT_VARIATION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Variation of product, a variation has product code and selling price, thus also treated as a special product. Product variations has most attributes in common while a few are different.',
   'user', @CmtPRODUCT_VARIATION, 'table', 'PRODUCT_VARIATION';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   ',
   'user', '', 'table', 'PRODUCT_VARIATION', 'column', 'status';

/*==============================================================*/
/* Table: PROMOTION                                             */
/*==============================================================*/
create table PROMOTION (
   promotionId          int                  identity,
   couponTypeId         int                  null,
   titleKey             varchar(32)          null,
   descriptionKey       varchar(32)          null,
   startTime            datetime             null,
   expireTime           datetime             null,
   membershipId         int                  null,
   regionId             int                  null,
   promotionType        smallint             null,
   discountType         smallint             not null,
   minProductQty        int                  null,
   orderSubtotalAmt     numeric(12,2)        null,
   pointsPerAmt         numeric(12,4)        null,
   discount             numeric(12,2)        null,
   status               smallint             null,
   priority             int                  null,
   isApplyToAll         smallint             null,
   dblDiscount          smallint             null,
   createBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   updateBy             int                  null,
   version              int                  not null,
   constraint PK_PROMOTION primary key nonclustered (promotionId)
);

execute sp_bindefault D_int_version, 'PROMOTION.version';

declare @CmtPROMOTION varchar(128)
select @CmtPROMOTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Promotion activities.',
   'user', @CmtPROMOTION, 'table', 'PROMOTION';

execute sp_addextendedproperty 'MS_Description', 
   '0:Normal promotion
   1:only used in coupon',
   'user', '', 'table', 'PROMOTION', 'column', 'promotionType';

execute sp_addextendedproperty 'MS_Description', 
   '1=$ off
   2=% off
   3=give a fixed mount of rewarded point
   4=give rewarded points of % amount
   5=give coupon
   6=free shipping
   ',
   'user', '', 'table', 'PROMOTION', 'column', 'discountType';

execute sp_addextendedproperty 'MS_Description', 
   'reward how many points per amount of shopping amount
   Sample: Get Points x Per pointsPerAmt of subtotal.',
   'user', '', 'table', 'PROMOTION', 'column', 'pointsPerAmt';

execute sp_addextendedproperty 'MS_Description', 
   'will this rule apply to all product
   0:No
   1:Yes',
   'user', '', 'table', 'PROMOTION', 'column', 'isApplyToAll';

execute sp_addextendedproperty 'MS_Description', 
   'is Double Discount Allowed
   0=No
   1=Yes',
   'user', '', 'table', 'PROMOTION', 'column', 'dblDiscount';

/*==============================================================*/
/* Index: Promotion_Ref                                         */
/*==============================================================*/
create index Promotion_Ref on PROMOTION (
membershipId  ASC
);

/*==============================================================*/
/* Table: PROMOTION_PRODUCT                                     */
/*==============================================================*/
create table PROMOTION_PRODUCT (
   promotionProductId   int                  identity,
   promotionId          int                  null,
   sortOrder            int                  null,
   fixedPrice           numeric(12,2)        null,
   quantity             int                  null,
   categoryPath         varchar(255)         not null,
   isCategory           smallint             null,
   version              int                  not null,
   constraint PK_PRO_PRODUCT primary key nonclustered (promotionProductId)
);

execute sp_bindefault D_int_version, 'PROMOTION_PRODUCT.version';

declare @CmtPROMOTION_PRODUCT varchar(128)
select @CmtPROMOTION_PRODUCT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'What products are in a promotion',
   'user', @CmtPROMOTION_PRODUCT, 'table', 'PROMOTION_PRODUCT';

execute sp_addextendedproperty 'MS_Description', 
   'if defined, the product in this promotion will be sold using this fixed/specified price. this setting has the highest priority.',
   'user', '', 'table', 'PROMOTION_PRODUCT', 'column', 'fixedPrice';

execute sp_addextendedproperty 'MS_Description', 
   'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
   e.g. 001.002.4321P(however, the id will be used instead of the name)',
   'user', '', 'table', 'PROMOTION_PRODUCT', 'column', 'categoryPath';

execute sp_addextendedproperty 'MS_Description', 
   '0=not catagory
   1=is catagory',
   'user', '', 'table', 'PROMOTION_PRODUCT', 'column', 'isCategory';

/*==============================================================*/
/* Table: PUBLISH_HISTORY                                       */
/*==============================================================*/
create table PUBLISH_HISTORY (
   publishHistoryId     int                  identity,
   publishTime          datetime             null,
   publishMajorVerion   varchar(8)           null,
   publishMinorVersion  varchar(8)           null,
   version              int                  not null,
   constraint PK_PUBLISH_HISTORY primary key nonclustered (publishHistoryId)
);

execute sp_bindefault D_int_version, 'PUBLISH_HISTORY.version';

/*==============================================================*/
/* Table: RECOMMENDED_PRODUCT                                   */
/*==============================================================*/
create table RECOMMENDED_PRODUCT (
   recommendedProductId int                  identity,
   productId            int                  not null,
   recommendedTypeId    int                  not null,
   location             varchar(255)         null,
   status               smallint             null,
   sortOrder            int                  null,
   recommendLevel       int                  null,
   locationType         smallint             null,
   startTime            datetime             null,
   expireTime           datetime             null,
   version              int                  not null,
   constraint PK_REC_PRO primary key nonclustered (recommendedProductId)
);

execute sp_bindefault D_int_version, 'RECOMMENDED_PRODUCT.version';

declare @CmtRECOMMENDED_PRODUCT varchar(128)
select @CmtRECOMMENDED_PRODUCT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'When customer is browsing products, system will recommend other products to him. Can specify different types of recommended products.',
   'user', @CmtRECOMMENDED_PRODUCT, 'table', 'RECOMMENDED_PRODUCT';

execute sp_addextendedproperty 'MS_Description', 
   'where should this recommendation take place, i.e. home, cart, product page...;can use comma to seperate more than one location',
   'user', '', 'table', 'RECOMMENDED_PRODUCT', 'column', 'location';

execute sp_addextendedproperty 'MS_Description', 
   '1=1 star
   2=2 star
   3=3 star
   4=4 star
   5=5 star',
   'user', '', 'table', 'RECOMMENDED_PRODUCT', 'column', 'recommendLevel';

execute sp_addextendedproperty 'MS_Description', 
   'type of location:
   0:locationId is channelId
   1:locationId is categoryId',
   'user', '', 'table', 'RECOMMENDED_PRODUCT', 'column', 'locationType';

/*==============================================================*/
/* Table: RECOMMENDED_TYPE                                      */
/*==============================================================*/
create table RECOMMENDED_TYPE (
   recommendedTypeId    int                  identity,
   titleKey             varchar(32)          null,
   typeName             nvarchar(64)         not null,
   ruleCode             int                  null,
   autoEval             smallint             not null,
   status               smallint             not null,
   isSystem             smallint             not null,
   version              int                  not null,
   constraint PK_RECOYPE primary key nonclustered (recommendedTypeId),
   constraint AK_KEY_2_RECOMMEN unique (typeName)
);

execute sp_bindefault D_int_version, 'RECOMMENDED_TYPE.version';

declare @CmtRECOMMENDED_TYPE varchar(128)
select @CmtRECOMMENDED_TYPE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Types of recommend purpose',
   'user', @CmtRECOMMENDED_TYPE, 'table', 'RECOMMENDED_TYPE';

/*==============================================================*/
/* Table: RECOMMENDED_TYPE_LOCATION                             */
/*==============================================================*/
create table RECOMMENDED_TYPE_LOCATION (
   typeLocationId       int                  identity,
   recommendedTypeId    int                  not null,
   location             varchar(255)         not null,
   version              int                  not null,
   constraint PK_RECOMMENDED_TYPE_LOCATION primary key nonclustered (typeLocationId)
);

execute sp_bindefault D_int_version, 'RECOMMENDED_TYPE_LOCATION.version';

/*==============================================================*/
/* Table: REGION                                                */
/*==============================================================*/
create table REGION (
   regionId             int                  identity,
   regionCode           varchar(8)           not null,
   regionType           smallint             null,
   regionNameKey        varchar(32)          null,
   zipCode              varchar(6)           null,
   telephoneCode        varchar(8)           null,
   regionIcon           varchar(256)         null,
   priority             int                  null,
   parentRegionId       int                  not null,
   descriptionKey       varchar(32)          null,
   status               smallint             null,
   createBy             int                  null,
   createTime           datetime             null,
   updateBy             int                  null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_REGION primary key nonclustered (regionId),
   constraint AK_KEY_2_REGION unique (regionCode)
);

execute sp_bindefault D_int_version, 'REGION.version';

execute sp_addextendedproperty 'MS_Description', 
   'code for country, state, city, or input by admin when defining custom region',
   'user', '', 'table', 'REGION', 'column', 'regionCode';

execute sp_addextendedproperty 'MS_Description', 
   'Types of region:
   1:Country
   2:State/Province
   3:City
   4:Custom',
   'user', '', 'table', 'REGION', 'column', 'regionType';

execute sp_addextendedproperty 'MS_Description', 
   'i18n region name key',
   'user', '', 'table', 'REGION', 'column', 'regionNameKey';

execute sp_addextendedproperty 'MS_Description', 
   'some region/city can define zip code',
   'user', '', 'table', 'REGION', 'column', 'zipCode';

execute sp_addextendedproperty 'MS_Description', 
   'telephone zone code for country, state, city',
   'user', '', 'table', 'REGION', 'column', 'telephoneCode';

execute sp_addextendedproperty 'MS_Description', 
   'when more thant one region found to be match, higher priority will be choosed. custom region usually has higher priority.',
   'user', '', 'table', 'REGION', 'column', 'priority';

execute sp_addextendedproperty 'MS_Description', 
   'Indicate parent region',
   'user', '', 'table', 'REGION', 'column', 'parentRegionId';

execute sp_addextendedproperty 'MS_Description', 
   'i18n description Key',
   'user', '', 'table', 'REGION', 'column', 'descriptionKey';

/*==============================================================*/
/* Table: REGION_ITEM                                           */
/*==============================================================*/
create table REGION_ITEM (
   regionItemId         int                  identity,
   regionId             int                  null,
   regionItemType       smallint             null,
   status               smallint             null,
   version              int                  not null,
   addressList          nvarchar(512)        null,
   zipList              nvarchar(512)        null,
   itemId               int                  null,
   constraint PK_REGION_ITEM primary key nonclustered (regionItemId)
);

execute sp_bindefault D_int_version, 'REGION_ITEM.version';

execute sp_addextendedproperty 'MS_Description', 
   'referred region id',
   'user', '', 'table', 'REGION_ITEM', 'column', 'regionId';

execute sp_addextendedproperty 'MS_Description', 
   '1 address 2 zip 3 region',
   'user', '', 'table', 'REGION_ITEM', 'column', 'regionItemType';

/*==============================================================*/
/* Table: REPORT                                                */
/*==============================================================*/
create table REPORT (
   reportId             int                  identity,
   reportFolderId       int                  null,
   title                nvarchar(128)        not null,
   description          nvarchar(256)        null,
   rptDesignFile        varchar(128)         null,
   url                  varchar(255)         null,
   param                varbinary(3000)      null,
   isSystem             smallint             not null,
   version              int                  not null,
   constraint PK_REPORT primary key nonclustered (reportId)
);

execute sp_bindefault D_int_version, 'REPORT.version';

/*==============================================================*/
/* Table: REPORT_FOLDER                                         */
/*==============================================================*/
create table REPORT_FOLDER (
   reportFolderId       int                  identity,
   title                nvarchar(128)        not null,
   isSystem             smallint             not null,
   version              int                  not null,
   constraint PK_REPORT_FOLDER primary key nonclustered (reportFolderId)
);

execute sp_bindefault D_int_version, 'REPORT_FOLDER.version';

/*==============================================================*/
/* Table: SALES_ORDER                                           */
/*==============================================================*/
create table SALES_ORDER (
   orderId              int                  identity,
   customerId           int                  null,
   paymentMethodId      int                  not null,
   orderNo              varchar(12)          not null,
   membershipId         int                  null,
   customerTitle        nvarchar(8)          not null,
   customerFirstName    nvarchar(32)         not null,
   customerLastname     nvarchar(32)         not null,
   customerTelephone    varchar(32)          not null,
   customerFax          varchar(32)          null,
   customerEmail        varchar(32)          not null,
   customerZip          varchar(6)           null,
   customerCountry      nvarchar(64)         null,
   customerState        nvarchar(64)         null,
   customerCity         nvarchar(64)         null,
   customerAddress      nvarchar(128)        null,
   customerAddress1     nvarchar(128)        null,
   shippingTitle        nvarchar(8)          not null,
   shippingFirstname    nvarchar(32)         not null,
   shippingLastname     nvarchar(32)         not null,
   shippingTelephone    varchar(32)          not null,
   shippingFax          varchar(32)          null,
   shippingCountry      nvarchar(64)         not null,
   shippingState        nvarchar(64)         null,
   shippingCity         nvarchar(64)         null,
   shippingZip          varchar(6)           null,
   shippingAddress      nvarchar(128)        not null,
   shippingAddress1     nvarchar(128)        null,
   billingTitle         nvarchar(8)          null,
   billingFirstname     nvarchar(32)         null,
   billingLastname      nvarchar(32)         null,
   billingCountry       nvarchar(64)         null,
   billingState         nvarchar(64)         null,
   billingCity          nvarchar(64)         null,
   billingZip           varchar(6)           null,
   billingAddress       nvarchar(128)        null,
   billingAddress1      nvarchar(128)        null,
   companyName          nvarchar(128)        null,
   wrapTotalCost        numeric(12,2)        null,
   shippingTotalCost    numeric(12,2)        null,
   taxAmt               numeric(12,2)        null,
   taxApplied           varchar(256)         null,
   couponDiscountAmt    numeric(12,2)        null,
   giftCertficateAmt    numeric(12,2)        null,
   saveMoneyTotal       numeric(12,2)        null,
   subtotal             numeric(12,2)        null,
   standardTotalPrice   numeric(12,2)        not null,
   total                numeric(12,2)        not null,
   shouldPay            numeric(12,2)        not null,
   orderStatus          smallint             not null,
   paymentStatus        smallint             null,
   shippingStatus       smallint             null,
   itemsCount           int                  null,
   notes                nvarchar(1024)       null,
   shippingCountryId    int                  null,
   shippingStateId      int                  null,
   shippingCityId       int                  null,
   shippingCustomId     int                  null,
   needBilling          smallint             not null,
   createTime           datetime             not null,
   createBy             int                  null,
   updateTime           datetime             null,
   updateBy             int                  null,
   version              int                  not null,
   constraint PK_SALES_ORDER primary key nonclustered (orderId),
   constraint AK_KEY_2_SALES_OR unique (orderNo)
);

execute sp_bindefault D_int_version, 'SALES_ORDER.version';

execute sp_addextendedproperty 'MS_Description', 
   'OYYDDDNNNN
   1 bit is O
   2-6 bit is date string ,for example 06220
   7-10 bit is increased number,from 0001 to 9999
    
   ',
   'user', '', 'table', 'SALES_ORDER', 'column', 'orderNo';

execute sp_addextendedproperty 'MS_Description', 
   'total cost of all items'' wrapping',
   'user', '', 'table', 'SALES_ORDER', 'column', 'wrapTotalCost';

execute sp_addextendedproperty 'MS_Description', 
   'total shipping cost',
   'user', '', 'table', 'SALES_ORDER', 'column', 'shippingTotalCost';

execute sp_addextendedproperty 'MS_Description', 
   'total amount of tax',
   'user', '', 'table', 'SALES_ORDER', 'column', 'taxAmt';

execute sp_addextendedproperty 'MS_Description', 
   'all tax types that applied in this order, use comma to seperate',
   'user', '', 'table', 'SALES_ORDER', 'column', 'taxApplied';

execute sp_addextendedproperty 'MS_Description', 
   'Diccounted amount because of coupon',
   'user', '', 'table', 'SALES_ORDER', 'column', 'couponDiscountAmt';

execute sp_addextendedproperty 'MS_Description', 
   'gift Certficate Amount used in this order',
   'user', '', 'table', 'SALES_ORDER', 'column', 'giftCertficateAmt';

execute sp_addextendedproperty 'MS_Description', 
   'total discount Amount',
   'user', '', 'table', 'SALES_ORDER', 'column', 'saveMoneyTotal';

execute sp_addextendedproperty 'MS_Description', 
   'subtotal=standardTotalPrice-saveMoneyTotal-
   couponDiscountAmt
   ',
   'user', '', 'table', 'SALES_ORDER', 'column', 'subtotal';

execute sp_addextendedproperty 'MS_Description', 
   'total=subtotal+wrapTotalCost+shippingTotalCost+taxAmt
   ',
   'user', '', 'table', 'SALES_ORDER', 'column', 'total';

execute sp_addextendedproperty 'MS_Description', 
   '0=Placed, accepted and waiting for further processing(e.g credit card verification)
   1=Pending, auto verification passed and ready for manual verification/confirmation
   2=Verified and Confirmed, ready for stock up
   3=Stockuped, ready for shipping
   4=Shipped
   5=Completed
   8=Backed Order
   9=Cancelled',
   'user', '', 'table', 'SALES_ORDER', 'column', 'orderStatus';

execute sp_addextendedproperty 'MS_Description', 
   'Payment status
   0=No yet paid
   1=Partly paid
   2=All paid
   3=Customer has paid, but we hasn''t got it',
   'user', '', 'table', 'SALES_ORDER', 'column', 'paymentStatus';

execute sp_addextendedproperty 'MS_Description', 
   '0=Not shipped
   1=Partly shipped
   2=All shipped',
   'user', '', 'table', 'SALES_ORDER', 'column', 'shippingStatus';

execute sp_addextendedproperty 'MS_Description', 
   'items count',
   'user', '', 'table', 'SALES_ORDER', 'column', 'itemsCount';

execute sp_addextendedproperty 'MS_Description', 
   'user made extra notes telling how to process the order,etc.',
   'user', '', 'table', 'SALES_ORDER', 'column', 'notes';

execute sp_addextendedproperty 'MS_Description', 
   '1 need 0 not need not 
   
    
   ',
   'user', '', 'table', 'SALES_ORDER', 'column', 'needBilling';

/*==============================================================*/
/* Table: SAVED_PRODUCT_LIST                                    */
/*==============================================================*/
create table SAVED_PRODUCT_LIST (
   savedProductListId   int                  identity,
   customerId           int                  not null,
   productId            int                  not null,
   price                numeric(12,2)        not null,
   addTime              datetime             not null,
   version              int                  not null,
   constraint PK_SAVE_PR_L primary key nonclustered (savedProductListId)
);

execute sp_bindefault D_int_version, 'SAVED_PRODUCT_LIST.version';

execute sp_addextendedproperty 'MS_Description', 
   'the price when saved',
   'user', '', 'table', 'SAVED_PRODUCT_LIST', 'column', 'price';

/*==============================================================*/
/* Table: SEARCH_KEYWORD                                        */
/*==============================================================*/
create table SEARCH_KEYWORD (
   searchKeywordId      int                  identity,
   searchKeyword        nvarchar(128)        null,
   times                int                  null,
   version              int                  not null,
   constraint PK_SEARCH_KEYWORD primary key nonclustered (searchKeywordId)
);

execute sp_bindefault D_int_version, 'SEARCH_KEYWORD.version';

declare @CmtSEARCH_KEYWORD varchar(128)
select @CmtSEARCH_KEYWORD = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Collect user entered search keywords and make searching suggestion to user when possible',
   'user', @CmtSEARCH_KEYWORD, 'table', 'SEARCH_KEYWORD';

/*==============================================================*/
/* Table: SEARCH_WORD                                           */
/*==============================================================*/
create table SEARCH_WORD (
   searchWordId         int                  identity,
   searchWord           nvarchar(512)        not null,
   updateTime           datetime             null,
   searchTimes          int                  null,
   version              int                  not null,
   constraint PK_SEARCH_WORD primary key nonclustered (searchWordId)
);

execute sp_bindefault D_int_version, 'SEARCH_WORD.version';

/*==============================================================*/
/* Table: SEE_ALSO                                              */
/*==============================================================*/
create table SEE_ALSO (
   seeAlsoId            int                  identity,
   seeAlsoUrlId         int                  not null,
   systemUrlId          int                  not null,
   isSystem             smallint             not null,
   version              int                  not null,
   constraint PK_SEE_ALSO primary key nonclustered (seeAlsoId)
);

execute sp_bindefault D_int_version, 'SEE_ALSO.version';

declare @CmtSEE_ALSO varchar(128)
select @CmtSEE_ALSO = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Define see also links for each admin page',
   'user', @CmtSEE_ALSO, 'table', 'SEE_ALSO';

/*==============================================================*/
/* Table: SHIPPING_GATEWAY                                      */
/*==============================================================*/
create table SHIPPING_GATEWAY (
   shippingGatewayId    int                  identity,
   shippingGatewayName  nvarchar(32)         not null,
   templateName         nvarchar(32)         null,
   shippingGatewayDescription nvarchar(512)        null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_SHIP_GATE primary key nonclustered (shippingGatewayId)
);

execute sp_bindefault D_int_version, 'SHIPPING_GATEWAY.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   
   ',
   'user', '', 'table', 'SHIPPING_GATEWAY', 'column', 'status';

/*==============================================================*/
/* Table: SHIPPING_METHOD                                       */
/*==============================================================*/
create table SHIPPING_METHOD (
   shippingMethodId     int                  identity,
   carrierId            int                  null,
   shippingGatewayId    int                  null,
   shippingMethodNameKey varchar(32)          not null,
   shippingMethodDetailKey varchar(32)          null,
   isDomestic           smallint             null,
   deliveryTimeKey      varchar(32)          null,
   status               smallint             null,
   isCod                smallint             null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_SHIPPING_METHOD primary key nonclustered (shippingMethodId)
);

execute sp_bindefault D_small_deleted, 'SHIPPING_METHOD.deleted';

execute sp_bindefault D_int_version, 'SHIPPING_METHOD.version';

execute sp_addextendedproperty 'MS_Description', 
   'the id of the carrier company',
   'user', '', 'table', 'SHIPPING_METHOD', 'column', 'carrierId';

execute sp_addextendedproperty 'MS_Description', 
   '0=No (is international)
   1=Yes (is domestic)
   ',
   'user', '', 'table', 'SHIPPING_METHOD', 'column', 'isDomestic';

execute sp_addextendedproperty 'MS_Description', 
   'i18n delivery time description',
   'user', '', 'table', 'SHIPPING_METHOD', 'column', 'deliveryTimeKey';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'SHIPPING_METHOD', 'column', 'status';

/*==============================================================*/
/* Table: SHIPPING_RATE                                         */
/*==============================================================*/
create table SHIPPING_RATE (
   shippingRateId       int                  identity,
   shippingMethodId     int                  null,
   regionId             int                  null,
   baseOn               int                  not null,
   isFlat               smallint             not null,
   isRoundUpValue       smallint             null,
   basePrice            numeric(12,2)        null,
   maxWeight            numeric(12,2)        null,
   maxVolume            numeric(12,2)        null,
   baseWeight           numeric(12,2)        null,
   baseVolume           numeric(12,2)        null,
   weightPerRate        numeric(12,2)        null,
   volumePerRate        numeric(12,2)        null,
   volumePerFee         numeric(12,2)        null,
   increaseUnit         numeric(12,2)        null,
   metricUnitCode       varchar(32)          null,
   maxItem              int                  null,
   itemPerRate          numeric(12,2)        null,
   deleted              smallint             not null,
   version              int                  not null,
   constraint PK_SHIPPING_RATE primary key nonclustered (shippingRateId)
);

execute sp_bindefault D_small_deleted, 'SHIPPING_RATE.deleted';

execute sp_bindefault D_int_version, 'SHIPPING_RATE.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=weight
   1=volumn/size
   2=quantity
   3=.......',
   'user', '', 'table', 'SHIPPING_RATE', 'column', 'baseOn';

execute sp_addextendedproperty 'MS_Description', 
   '0=No(calc by %)
   1=Yes',
   'user', '', 'table', 'SHIPPING_RATE', 'column', 'isFlat';

execute sp_addextendedproperty 'MS_Description', 
   '0=No (No round up)
   1=Yes',
   'user', '', 'table', 'SHIPPING_RATE', 'column', 'isRoundUpValue';

execute sp_addextendedproperty 'MS_Description', 
   'base price of this shipping rate',
   'user', '', 'table', 'SHIPPING_RATE', 'column', 'basePrice';

execute sp_addextendedproperty 'MS_Description', 
   'quantity of counting unit that need to re-calc',
   'user', '', 'table', 'SHIPPING_RATE', 'column', 'increaseUnit';

/*==============================================================*/
/* Table: SHOPPINGCART                                          */
/*==============================================================*/
create table SHOPPINGCART (
   shoppingCartId       int                  identity,
   customerId           int                  null,
   shippingAddressId    int                  null,
   billingAddressId     int                  null,
   wrapTotalCost        numeric(12,2)        null default 0.0,
   shippingTotalCost    numeric(12,2)        null default 0.0,
   taxAmt               numeric(12,2)        null default 0.0,
   taxApplied           varchar(256)         null,
   couponDiscountAmt    numeric(12,2)        null default 0.0,
   saveMoneyTotal       numeric(12,2)        null default 0.0,
   subtotal             numeric(12,2)        null default 0.0,
   total                numeric(12,2)        null default 0.0,
   status               smallint             null default 1,
   notes                nvarchar(1024)       null,
   shippingCountryId    int                  null,
   shippingStateId      int                  null,
   shippingCityId       int                  null,
   shippingCustomId     int                  null,
   itemsCount           int                  null,
   buyNowItemsCount     int                  null,
   buyLaterItemsCount   int                  null,
   standardTotalPrice   numeric(12,2)        null default 0.0,
   hasUpdateCookie      smallint             null default 0,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_SHOPPINGCART primary key nonclustered (shoppingCartId)
);

execute sp_bindefault D_int_version, 'SHOPPINGCART.version';

execute sp_addextendedproperty 'MS_Description', 
   'total cost of all items'' wrapping',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'wrapTotalCost';

execute sp_addextendedproperty 'MS_Description', 
   'total shipping cost',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'shippingTotalCost';

execute sp_addextendedproperty 'MS_Description', 
   'total amount of tax',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'taxAmt';

execute sp_addextendedproperty 'MS_Description', 
   'all tax types that applied in this order, use comma to seperate',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'taxApplied';

execute sp_addextendedproperty 'MS_Description', 
   'Diccounted amount because of coupon',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'couponDiscountAmt';

execute sp_addextendedproperty 'MS_Description', 
   'total discounted amount',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'saveMoneyTotal';

execute sp_addextendedproperty 'MS_Description', 
   'subtotal=standardTotalPrice-saveMoneyTotal-
   couponDiscountAmt
   ',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'subtotal';

execute sp_addextendedproperty 'MS_Description', 
   'total=subtotal+wrapTotalCost+shippingTotalCost+taxAmt
   ',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'total';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'status';

execute sp_addextendedproperty 'MS_Description', 
   'Customer made extra notes about the order',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'notes';

execute sp_addextendedproperty 'MS_Description', 
   'items count',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'itemsCount';

execute sp_addextendedproperty 'MS_Description', 
   'total amount before any discount
   =sum(product.price*qty)',
   'user', '', 'table', 'SHOPPINGCART', 'column', 'standardTotalPrice';

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM                                     */
/*==============================================================*/
create table SHOPPINGCART_ITEM (
   shoppingCartItemId   int                  identity,
   productId            int                  null,
   shoppingCartId       int                  not null,
   quantity             int                  not null,
   price                numeric(12,2)        not null,
   addTime              datetime             not null,
   isSaved              smallint             null,
   discountPrice        numeric(12,2)        null,
   hasUnitedPmt         smallint             null,
   isFreeShipping       smallint             null,
   isExempt             smallint             null,
   wrapId               int                  null,
   wrapCost             numeric(12,2)        null,
   wrapNote             nvarchar(1024)       null,
   itemType             smallint             null,
   isSpecialOffer       smallint             null,
   saveMoney            numeric(12,2)        null,
   productAttributes    varbinary(3000)      null,
   categoryPath         varchar(255)         not null,
   version              int                  not null,
   constraint PK_SHO_ITEM primary key nonclustered (shoppingCartItemId)
);

execute sp_bindefault D_int_version, 'SHOPPINGCART_ITEM.version';

execute sp_addextendedproperty 'MS_Description', 
   'for certificate, it''s nullable',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'productId';

execute sp_addextendedproperty 'MS_Description', 
   'sales price of this product when saved',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'price';

execute sp_addextendedproperty 'MS_Description', 
   '0=not is save for later
   1=is save for later',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'isSaved';

execute sp_addextendedproperty 'MS_Description', 
   'price after discounted',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'discountPrice';

execute sp_addextendedproperty 'MS_Description', 
   'has united promotion',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'hasUnitedPmt';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'isFreeShipping';

execute sp_addextendedproperty 'MS_Description', 
   '0=No
   1=Yes',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'isExempt';

execute sp_addextendedproperty 'MS_Description', 
   'wrap type id',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'wrapId';

execute sp_addextendedproperty 'MS_Description', 
   'the price of this type of wrapping',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'wrapCost';

execute sp_addextendedproperty 'MS_Description', 
   'notes that should be written on the wrap',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'wrapNote';

execute sp_addextendedproperty 'MS_Description', 
   'types of items/goods:
   1:product (default)
   2:gift certificate
   3:service
   4:software
   5:document
   6:music
   7:video
   8:others',
   'user', '', 'table', 'SHOPPINGCART_ITEM', 'column', 'itemType';

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM_PROMOTION                           */
/*==============================================================*/
create table SHOPPINGCART_ITEM_PROMOTION (
   shoppingCartItemPromotionId int                  identity,
   shoppingCartItemId   int                  null,
   promotionId          int                  null,
   titleKey             varchar(32)          null,
   descriptionKey       varchar(32)          null,
   saveMoney            numeric(12,2)        null,
   validFlag            smallint             null,
   isUnited             smallint             null,
   version              int                  not null,
   constraint PK_SHOPPINGCART_ITEM_PROMOTION primary key nonclustered (shoppingCartItemPromotionId)
);

execute sp_bindefault D_int_version, 'SHOPPINGCART_ITEM_PROMOTION.version';

declare @CmtSHOPPINGCART_ITEM_PROMOTION varchar(128)
select @CmtSHOPPINGCART_ITEM_PROMOTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '
   ',
   'user', @CmtSHOPPINGCART_ITEM_PROMOTION, 'table', 'SHOPPINGCART_ITEM_PROMOTION';

execute sp_addextendedproperty 'MS_Description', 
   '0=is not valid
   1=is valid',
   'user', '', 'table', 'SHOPPINGCART_ITEM_PROMOTION', 'column', 'validFlag';

execute sp_addextendedproperty 'MS_Description', 
   '0=is not united
   1=is united',
   'user', '', 'table', 'SHOPPINGCART_ITEM_PROMOTION', 'column', 'isUnited';

/*==============================================================*/
/* Table: SHOP_POINT_HISTORY                                    */
/*==============================================================*/
create table SHOP_POINT_HISTORY (
   shopPointHistoryId   int                  identity,
   customerId           int                  null,
   points               int                  null,
   pointType            smallint             null,
   gainTime             datetime             null,
   version              int                  not null,
   constraint PK_SH_PO_HIS primary key nonclustered (shopPointHistoryId)
);

execute sp_bindefault D_int_version, 'SHOP_POINT_HISTORY.version';

declare @CmtSHOP_POINT_HISTORY varchar(128)
select @CmtSHOP_POINT_HISTORY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'shop points gain history',
   'user', @CmtSHOP_POINT_HISTORY, 'table', 'SHOP_POINT_HISTORY';

execute sp_addextendedproperty 'MS_Description', 
   'how many rewarded points',
   'user', '', 'table', 'SHOP_POINT_HISTORY', 'column', 'points';

execute sp_addextendedproperty 'MS_Description', 
   '0=Shopping
   1=Promotion
   2=Remarks',
   'user', '', 'table', 'SHOP_POINT_HISTORY', 'column', 'pointType';

execute sp_addextendedproperty 'MS_Description', 
   'the time that the points are gained/received',
   'user', '', 'table', 'SHOP_POINT_HISTORY', 'column', 'gainTime';

/*==============================================================*/
/* Table: SHOP_POINT_USED_HISTORY                               */
/*==============================================================*/
create table SHOP_POINT_USED_HISTORY (
   shopPointUsedHistoryId int                  identity,
   customerId           int                  null,
   points               int                  null,
   usedTime             datetime             null,
   description          nvarchar(512)        null,
   version              int                  not null,
   constraint PK_SH_PO_US_HIS primary key nonclustered (shopPointUsedHistoryId)
);

execute sp_bindefault D_int_version, 'SHOP_POINT_USED_HISTORY.version';

declare @CmtSHOP_POINT_USED_HISTORY varchar(128)
select @CmtSHOP_POINT_USED_HISTORY = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'shop points use history',
   'user', @CmtSHOP_POINT_USED_HISTORY, 'table', 'SHOP_POINT_USED_HISTORY';

/*==============================================================*/
/* Table: SORT_ORDER                                            */
/*==============================================================*/
create table SORT_ORDER (
   sortOrderId          int                  identity,
   sortOrderCode        varchar(32)          not null,
   maxOrderValue        int                  not null,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_SORT_ORDER primary key nonclustered (sortOrderId)
);

execute sp_bindefault D_int_version, 'SORT_ORDER.version';

/*==============================================================*/
/* Index: IX_SORT_ORDER_CODE                                    */
/*==============================================================*/
create index IX_SORT_ORDER_CODE on SORT_ORDER (
sortOrderCode  ASC
);

/*==============================================================*/
/* Table: SPEED_BAR                                             */
/*==============================================================*/
create table SPEED_BAR (
   speedBarId           int                  identity,
   mediaId              int                  null,
   speedBarImage        varchar(255)         null,
   url                  varchar(255)         not null,
   target               varchar(32)          null,
   sortOrder            int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_SPEED_BAR primary key nonclustered (speedBarId)
);

execute sp_bindefault D_int_version, 'SPEED_BAR.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'SPEED_BAR', 'column', 'status';

/*==============================================================*/
/* Table: STATICALLIZE_HISTORY                                  */
/*==============================================================*/
create table STATICALLIZE_HISTORY (
   staticallizeHistoryId int                  identity,
   version              int                  not null,
   constraint PK_STAE_HISTO primary key nonclustered (staticallizeHistoryId)
);

execute sp_bindefault D_int_version, 'STATICALLIZE_HISTORY.version';

/*==============================================================*/
/* Table: STATICALLIZE_ITEM                                     */
/*==============================================================*/
create table STATICALLIZE_ITEM (
   staticallizeItemId   int                  identity,
   version              int                  not null,
   constraint PK_STATIT primary key nonclustered (staticallizeItemId)
);

execute sp_bindefault D_int_version, 'STATICALLIZE_ITEM.version';

/*==============================================================*/
/* Table: STATIC_PAGE                                           */
/*==============================================================*/
create table STATIC_PAGE (
   staticPageId         int                  identity,
   staticPageTitleKey   varchar(32)          not null,
   staticPageContentKey varchar(32)          not null,
   staticPagePath       varchar(128)         not null,
   publishLocation      varchar(32)          not null,
   templateName         nvarchar(32)         null,
   sortOrder            int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_STATIC_PAGE primary key nonclustered (staticPageId)
);

execute sp_bindefault D_int_version, 'STATIC_PAGE.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'STATIC_PAGE', 'column', 'status';

/*==============================================================*/
/* Table: SURVEY                                                */
/*==============================================================*/
create table SURVEY (
   surveyId             int                  identity,
   surveyTitleKey       varchar(32)          not null,
   startTime            datetime             null,
   expireTime           datetime             null,
   times                int                  null,
   sortOrder            int                  null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_SURVEY primary key nonclustered (surveyId)
);

execute sp_bindefault D_int_version, 'SURVEY.version';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'SURVEY', 'column', 'status';

/*==============================================================*/
/* Table: SURVEY_OPTION                                         */
/*==============================================================*/
create table SURVEY_OPTION (
   surveyOptionId       int                  identity,
   surveyId             int                  not null,
   surveyOptionTitleKey varchar(32)          not null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_SURVEY_OPTION primary key nonclustered (surveyOptionId)
);

execute sp_bindefault D_int_version, 'SURVEY_OPTION.version';

/*==============================================================*/
/* Table: SURVEY_RESULT                                         */
/*==============================================================*/
create table SURVEY_RESULT (
   surveyResultId       int                  identity,
   surveyId             int                  not null,
   surveyOptionId       int                  not null,
   remoteIp             varchar(32)          not null,
   version              int                  not null,
   constraint PK_SURVEY_RESULT primary key nonclustered (surveyResultId)
);

execute sp_bindefault D_int_version, 'SURVEY_RESULT.version';

/*==============================================================*/
/* Table: SYSTEM_CONFIG                                         */
/*==============================================================*/
create table SYSTEM_CONFIG (
   configId             int                  identity,
   configKey            varchar(64)          not null,
   description          nvarchar(512)        null,
   category             varchar(32)          null,
   configType           smallint             null,
   configValue          nvarchar(1024)       not null,
   options              varchar(255)         null,
   dataType             smallint             null,
   isSystem             smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_SYSTEM_CONFIG primary key nonclustered (configId),
   constraint AK_KEY_2_SYSTEM_C unique (configKey)
);

execute sp_bindefault D_int_version, 'SYSTEM_CONFIG.version';

execute sp_addextendedproperty 'MS_Description', 
   'system parameter, use can not modify.',
   'user', '', 'table', 'SYSTEM_CONFIG', 'column', 'isSystem';

/*==============================================================*/
/* Table: SYSTEM_LANGUAGE                                       */
/*==============================================================*/
create table SYSTEM_LANGUAGE (
   languageId           int                  identity,
   languageNameKey      varchar(32)          not null,
   localeCode           varchar(8)           not null,
   status               smallint             null,
   isDefault            smallint             null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_SYSTEM_LANGUAGE primary key nonclustered (languageId),
   constraint AK_KEY_2_SYSTEM_L unique (localeCode)
);

execute sp_bindefault D_int_version, 'SYSTEM_LANGUAGE.version';

declare @CmtSYSTEM_LANGUAGE varchar(128)
select @CmtSYSTEM_LANGUAGE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Supported languages',
   'user', @CmtSYSTEM_LANGUAGE, 'table', 'SYSTEM_LANGUAGE';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'SYSTEM_LANGUAGE', 'column', 'status';

/*==============================================================*/
/* Table: SYSTEM_MESSAGE                                        */
/*==============================================================*/
create table SYSTEM_MESSAGE (
   systemMessageId      int                  identity,
   messageHtml          varchar(256)         not null,
   messageType          smallint             null,
   status               smallint             null,
   appuserId            int                  null,
   createTime           datetime             not null,
   constraint PK_SYSTEM_MESSAGE primary key nonclustered (systemMessageId)
);

execute sp_addextendedproperty 'MS_Description', 
   '0=all',
   'user', '', 'table', 'SYSTEM_MESSAGE', 'column', 'appuserId';

/*==============================================================*/
/* Table: SYSTEM_MODULE                                         */
/*==============================================================*/
create table SYSTEM_MODULE (
   moduleId             int                  identity,
   moduleName           nvarchar(32)         null,
   moduleDetail         nvarchar(255)        null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_SYSTEM_MODULE primary key nonclustered (moduleId)
);

execute sp_bindefault D_int_version, 'SYSTEM_MODULE.version';

declare @CmtSYSTEM_MODULE varchar(128)
select @CmtSYSTEM_MODULE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Installed moduled',
   'user', @CmtSYSTEM_MODULE, 'table', 'SYSTEM_MODULE';

/*==============================================================*/
/* Table: SYSTEM_URL                                            */
/*==============================================================*/
create table SYSTEM_URL (
   urlId                int                  identity,
   urlName              nvarchar(32)         not null,
   title                nvarchar(128)        not null,
   url                  varchar(255)         null,
   description          nvarchar(128)        null,
   menuLevel            smallint             not null,
   parentId             int                  null,
   version              int                  not null,
   constraint PK_SYSTEM_URL primary key nonclustered (urlId)
);

execute sp_bindefault D_int_version, 'SYSTEM_URL.version';

execute sp_addextendedproperty 'MS_Description', 
   'base from 1. 1 is topMenu.and the subItem is 2, 3 ...',
   'user', '', 'table', 'SYSTEM_URL', 'column', 'menuLevel';

/*==============================================================*/
/* Table: TAB                                                   */
/*==============================================================*/
create table TAB (
   tabId                int                  identity,
   tabImage             varchar(128)         null,
   tabNameKey           varchar(32)          not null,
   tabTipKey            varchar(32)          null,
   status               smallint             null,
   version              int                  not null,
   templateName         nvarchar(32)         null,
   constraint PK_TAB primary key nonclustered (tabId),
   constraint AK_KEY_2_TAB unique (tabNameKey)
);

execute sp_bindefault D_int_version, 'TAB.version';

declare @CmtTAB varchar(128)
select @CmtTAB = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Global Tabs used in the application',
   'user', @CmtTAB, 'table', 'TAB';

/*==============================================================*/
/* Table: TAX                                                   */
/*==============================================================*/
create table TAX (
   taxId                int                  identity,
   taxNameKey           varchar(32)          not null,
   applyAddressType     smallint             not null,
   priority             int                  null,
   priceIncludesTax     smallint             null,
   displayTaxedPrice    smallint             null,
   taxRegisterNo        varchar(64)          null,
   status               smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             not null,
   updateTime           datetime             null,
   version              int                  not null,
   constraint PK_TAX primary key nonclustered (taxId),
   constraint AK_KEY_2_TAX unique (taxNameKey)
);

execute sp_bindefault D_int_version, 'TAX.version';

declare @CmtTAX varchar(128)
select @CmtTAX = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Types of tax',
   'user', @CmtTAX, 'table', 'TAX';

execute sp_addextendedproperty 'MS_Description', 
   'apply on which type of address
   0=shipping address
   1=bill address',
   'user', '', 'table', 'TAX', 'column', 'applyAddressType';

execute sp_addextendedproperty 'MS_Description', 
   'does product price includes tax
   0=No
   1=Yes',
   'user', '', 'table', 'TAX', 'column', 'priceIncludesTax';

execute sp_addextendedproperty 'MS_Description', 
   '0=display price after taxed
   1=display price before taxed',
   'user', '', 'table', 'TAX', 'column', 'displayTaxedPrice';

execute sp_addextendedproperty 'MS_Description', 
   'registered no of tax',
   'user', '', 'table', 'TAX', 'column', 'taxRegisterNo';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'TAX', 'column', 'status';

/*==============================================================*/
/* Table: TAX_RATE                                              */
/*==============================================================*/
create table TAX_RATE (
   taxRateId            int                  identity,
   categoryId           int                  not null,
   taxId                int                  null,
   regionId             int                  null,
   formula              varchar(255)         null,
   rateValue            numeric(12,2)        null,
   rateType             smallint             null,
   version              int                  not null,
   constraint PK_TAX_RATE primary key nonclustered (taxRateId)
);

execute sp_bindefault D_int_version, 'TAX_RATE.version';

declare @CmtTAX_RATE varchar(128)
select @CmtTAX_RATE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Tax rate for each category or state',
   'user', @CmtTAX_RATE, 'table', 'TAX_RATE';

execute sp_addextendedproperty 'MS_Description', 
   '0=%
   1=Specified',
   'user', '', 'table', 'TAX_RATE', 'column', 'rateType';

/*==============================================================*/
/* Table: TEMPLATE                                              */
/*==============================================================*/
create table TEMPLATE (
   templateId           int                  identity,
   templateSetId        int                  not null,
   templateName         nvarchar(32)         not null,
   templateType         varchar(128)         not null,
   templatePath         varchar(128)         not null,
   isDefault            smallint             null,
   createBy             int                  null,
   updateBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   localeCode           varchar(32)          null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_TEMPLATE primary key nonclustered (templateId)
);

execute sp_bindefault D_int_version, 'TEMPLATE.version';

declare @CmtTEMPLATE varchar(128)
select @CmtTEMPLATE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Velocity templates',
   'user', @CmtTEMPLATE, 'table', 'TEMPLATE';

execute sp_addextendedproperty 'MS_Description', 
   'unique template name',
   'user', '', 'table', 'TEMPLATE', 'column', 'templateName';

execute sp_addextendedproperty 'MS_Description', 
   '0=is not default
   1=is default',
   'user', '', 'table', 'TEMPLATE', 'column', 'isDefault';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'TEMPLATE', 'column', 'status';

/*==============================================================*/
/* Table: TEMPLATE_SET                                          */
/*==============================================================*/
create table TEMPLATE_SET (
   templateSetId        int                  identity,
   templateSetNameKey   varchar(32)          not null,
   templateSetPath      varchar(128)         not null,
   isDefault            smallint             null,
   updateBy             int                  null,
   createBy             int                  null,
   createTime           datetime             null,
   updateTime           datetime             null,
   status               smallint             null,
   version              int                  not null,
   constraint PK_TEMPLATE_SET primary key nonclustered (templateSetId),
   constraint AK_KEY_2_TEMPLATE unique (templateSetNameKey)
);

execute sp_bindefault D_int_version, 'TEMPLATE_SET.version';

declare @CmtTEMPLATE_SET varchar(128)
select @CmtTEMPLATE_SET = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Treat a set of templates as a whole and can manage more conveniently',
   'user', @CmtTEMPLATE_SET, 'table', 'TEMPLATE_SET';

execute sp_addextendedproperty 'MS_Description', 
   '0=not is default
   1=is default',
   'user', '', 'table', 'TEMPLATE_SET', 'column', 'isDefault';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'TEMPLATE_SET', 'column', 'status';

/*==============================================================*/
/* Table: URL_VISITED                                           */
/*==============================================================*/
create table URL_VISITED (
   urlVisitedId         int                  identity,
   cookieId             varchar(128)         null,
   url                  varchar(255)         null,
   visitedTime          datetime             null,
   timeInterval         int                  null,
   clickCounts          int                  null,
   constraint PK_URL_VISITED primary key nonclustered (urlVisitedId)
);

execute sp_addextendedproperty 'MS_Description', 
   'Client Cookie Id ,which identify current client user',
   'user', '', 'table', 'URL_VISITED', 'column', 'cookieId';

execute sp_addextendedproperty 'MS_Description', 
   'Page url user visited',
   'user', '', 'table', 'URL_VISITED', 'column', 'url';

execute sp_addextendedproperty 'MS_Description', 
   'time interval user stay in this url page ,by seconds',
   'user', '', 'table', 'URL_VISITED', 'column', 'timeInterval';

execute sp_addextendedproperty 'MS_Description', 
   'times user click this page',
   'user', '', 'table', 'URL_VISITED', 'column', 'clickCounts';

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE (
   userRoleId           int                  identity,
   appuserId            int                  null,
   roleId               int                  null,
   version              int                  not null,
   constraint PK_USER_ROLE primary key nonclustered (userRoleId)
);

execute sp_bindefault D_int_version, 'USER_ROLE.version';

/*==============================================================*/
/* Table: VALIDATION_SESSION                                    */
/*==============================================================*/
create table VALIDATION_SESSION (
   validationSessionId  int                  identity,
   url                varchar(255)         not null,
   expiredDate          datetime             not null,
   email                varchar(32)          not null,
   vsType               smallint             not null default 0,
   constraint PK_VALIDATION_SESSION primary key nonclustered (validationSessionId)
);

execute sp_addextendedproperty 'MS_Description', 
   '0=for password recorver 
   1=for account activate',
   'user', '', 'table', 'VALIDATION_SESSION', 'column', 'vsType';

/*==============================================================*/
/* Table: VARIATION_ITEM                                        */
/*==============================================================*/
create table VARIATION_ITEM (
   variationItemId      int                  identity,
   productVariationId   int                  not null,
   attributeOptionId    int                  not null,
   version              int                  not null,
   constraint PK_VARIATION_ITEM primary key nonclustered (variationItemId)
);

execute sp_bindefault D_int_version, 'VARIATION_ITEM.version';

declare @CmtVARIATION_ITEM varchar(128)
select @CmtVARIATION_ITEM = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'What attributes combination make up a variation',
   'user', @CmtVARIATION_ITEM, 'table', 'VARIATION_ITEM';

/*==============================================================*/
/* Table: VOTE                                                  */
/*==============================================================*/
create table VOTE (
   voteId               int                  identity,
   voteTitleKey         varchar(32)          not null,
   beginTime            datetime             not null,
   expireTime           datetime             null,
   status               smallint             null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_VOTE primary key nonclustered (voteId)
);

execute sp_bindefault D_int_version, 'VOTE.version';

declare @CmtVOTE varchar(128)
select @CmtVOTE = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Pending Develop for requirement not confirm',
   'user', @CmtVOTE, 'table', 'VOTE';

execute sp_addextendedproperty 'MS_Description', 
   '1=active
   2=inactive
   3=deleted
   ',
   'user', '', 'table', 'VOTE', 'column', 'status';

/*==============================================================*/
/* Table: VOTE_OPTION                                           */
/*==============================================================*/
create table VOTE_OPTION (
   voteOptionId         int                  identity,
   voteId               int                  not null,
   voteOptionTitleKey   varchar(32)          not null,
   times                int                  null,
   sortOrder            int                  null,
   version              int                  not null,
   constraint PK_VOTE_OPTION primary key nonclustered (voteOptionId)
);

execute sp_bindefault D_int_version, 'VOTE_OPTION.version';

declare @CmtVOTE_OPTION varchar(128)
select @CmtVOTE_OPTION = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Pending Develop for requirement not confirm',
   'user', @CmtVOTE_OPTION, 'table', 'VOTE_OPTION';

/*==============================================================*/
/* Table: VOTE_RESULT                                           */
/*==============================================================*/
create table VOTE_RESULT (
   voteResultId         int                  identity,
   voteId               int                  not null,
   voteOptionId         int                  not null,
   remoteIp             varchar(32)          not null,
   version              int                  not null,
   constraint PK_VOTE_RESULT primary key nonclustered (voteResultId)
);

execute sp_bindefault D_int_version, 'VOTE_RESULT.version';

declare @CmtVOTE_RESULT varchar(128)
select @CmtVOTE_RESULT = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Pending Develop for requirement not confirm',
   'user', @CmtVOTE_RESULT, 'table', 'VOTE_RESULT';

/*==============================================================*/
/* Table: WISHLIST                                              */
/*==============================================================*/
create table WISHLIST (
   wishListId           int                  identity,
   customerId           int                  not null,
   shippingAddressId    int                  null,
   wishListTitle        nvarchar(128)        not null,
   wishListType         smallint             not null,
   wishListDetail       nvarchar(512)        null,
   shareLevel           int                  null,
   isDefault            smallint             null,
   recipientEmailList   nvarchar(512)        null,
   eventDate            datetime             null,
   priority             int                  null,
   createTime           datetime             not null,
   version              int                  not null,
   constraint PK_WISHLIST primary key nonclustered (wishListId)
);

execute sp_bindefault D_int_version, 'WISHLIST.version';

execute sp_addextendedproperty 'MS_Description', 
   '0=Normal
   1=Event base',
   'user', '', 'table', 'WISHLIST', 'column', 'wishListType';

execute sp_addextendedproperty 'MS_Description', 
   'who can read this whishlist
   0=any
   1=owner
   2=visited some person???
   ',
   'user', '', 'table', 'WISHLIST', 'column', 'shareLevel';

execute sp_addextendedproperty 'MS_Description', 
   '0=not default
   1=is default',
   'user', '', 'table', 'WISHLIST', 'column', 'isDefault';

execute sp_addextendedproperty 'MS_Description', 
   'define who can view this wish list, use comma to seperate the email',
   'user', '', 'table', 'WISHLIST', 'column', 'recipientEmailList';

/*==============================================================*/
/* Table: WISHLIST_ITEM                                         */
/*==============================================================*/
create table WISHLIST_ITEM (
   wishlistItemId       int                  identity,
   wishListId           int                  not null,
   productId            int                  null,
   quantity             int                  null,
   purchasedQty         int                  null,
   shortDescription     nvarchar(512)        null,
   productAttributes    varbinary(3000)      null,
   createTime           datetime             null,
   version              int                  not null,
   constraint PK_WISHLIST_ITEM primary key nonclustered (wishlistItemId)
);

execute sp_bindefault D_int_version, 'WISHLIST_ITEM.version';

execute sp_addextendedproperty 'MS_Description', 
   'how many items wished to have',
   'user', '', 'table', 'WISHLIST_ITEM', 'column', 'quantity';

execute sp_addextendedproperty 'MS_Description', 
   'how many this type of item already purchased',
   'user', '', 'table', 'WISHLIST_ITEM', 'column', 'purchasedQty';

/*==============================================================*/
/* Table: WISHLIST_ITEM_ATTRIBUTE                               */
/*==============================================================*/
create table WISHLIST_ITEM_ATTRIBUTE (
   wishListItemAttributeId int                  identity,
   wishlistItemId       int                  not null,
   attributeId          int                  not null,
   attributeValue       varchar(255)         null,
   version              int                  not null,
   constraint PK_WISH_ITEM_ATT primary key nonclustered (wishListItemAttributeId)
);

execute sp_bindefault D_int_version, 'WISHLIST_ITEM_ATTRIBUTE.version';

/*==============================================================*/
/* Table: WRAP                                                  */
/*==============================================================*/
create table WRAP (
   wrapId               int                  identity,
   defaultPrice         numeric(12,2)        null,
   descriptionKey       varchar(32)          null,
   version              int                  not null,
   constraint PK_WRAP primary key nonclustered (wrapId)
);

execute sp_bindefault D_int_version, 'WRAP.version';

declare @CmtWRAP varchar(128)
select @CmtWRAP = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Gift wrapping options',
   'user', @CmtWRAP, 'table', 'WRAP';

alter table ADDRESS
   add constraint FK_ADDRESS_REFERENCE_APP_USER foreign key (appuserId)
      references APP_USER (appuserId);

alter table ADVERTISEMENT
   add constraint FK_ADVERTIS_REFERENCE_PROMOTIO foreign key (promotionId)
      references PROMOTION (promotionId);

alter table ADVERTISEMENT
   add constraint FK_AD_ADVERTIS foreign key (advertisementTypeId)
      references ADVERTISEMENT_TYPE (advertisementTypeId);

alter table ADVERTISEMENT_POSITION
   add constraint FK_ADV_ADVERTIS foreign key (advertisementId)
      references ADVERTISEMENT (advertisementId);

alter table ADVERTISEMENT_POSITION
   add constraint FK_ADVE_ADVERTIZ foreign key (positionTypeId)
      references AD_POSITION_TYPE (positionTypeId);

alter table ALSO_BUY
   add constraint FK_ALSO_BUY_PR foreign key (alsoProductId)
      references PRODUCT (productId);

alter table APP_ADMIN
   add constraint FK_APP_ADMI_REFERENCE_APP_USER foreign key (appAdminId)
      references APP_USER (appuserId);

alter table APP_MEDIA
   add constraint FK_APP_MEDI_REFERENCE_MEDIA_TY foreign key (mediaTypeId)
      references MEDIA_TYPE (mediaTypeId);

alter table ATTRIBUTE_OPTION
   add constraint FK_ATT_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId);

alter table CATEGORY_CATEGORY
   add constraint CATEGORY_CATEGORY6 foreign key (subCategoryId)
      references CATEGORY (categoryId);

alter table CATEGORY_CATEGORY
   add constraint FK_CA_CATEG foreign key (categoryId)
      references CATEGORY (categoryId);

alter table CATEGORY_SHIPPING_RATE
   add constraint FK_CA_SHIPPING foreign key (shippingRateId)
      references SHIPPING_RATE (shippingRateId);

alter table COUPON
   add constraint FK_COUPON_REFERENCE_COUPON_T foreign key (couponTypeId)
      references COUPON_TYPE (couponTypeId);

alter table COUPON_TYPE
   add constraint FK_COUPON_T_REFERENCE_PROMOTIO foreign key (promotionId)
      references PROMOTION (promotionId);

alter table CREDIT_CARD
   add constraint FK_CR_C_REF_CR_C foreign key (creditCardTypeId)
      references CREDIT_CARD_TYPE (creditCardTypeId);

alter table CREDIT_CARD
   add constraint FK_CRE_C_RE_CUST foreign key (customerId)
      references CUSTOMER (customerId);

alter table CUSTOMER
   add constraint FK_CUSTOMER_REFERENCE_APP_USER foreign key (customerId)
      references APP_USER (appuserId);

alter table CUSTOMER
   add constraint FK_CUSTOMER_REFERENCE_MEMBERSH foreign key (membershipId)
      references MEMBERSHIP (membershipId);

alter table CUSTOMER_MESSAGE_LIST
   add constraint FK_CUST_MESSAGE_ foreign key (messageListId)
      references MESSAGE_LIST (messageListId);

alter table CUSTOMER_PREFERENCE
   add constraint FK_CUST_CUST foreign key (customerId)
      references CUSTOMER (customerId);

alter table EXPORT_MAPPING_ITEM
   add constraint FK_EXP_M_REF_EX_M foreign key (exportMappingId)
      references EXPORT_MAPPING (exportMappingId);

alter table FAQ
   add constraint FK_FAQ_FAQ_CATE foreign key (faqCategoryId)
      references FAQ_CATEGORY (faqCategoryId);

alter table FAVORITE
   add constraint FK_FAVO_CUSTOMER foreign key (customerId)
      references CUSTOMER (customerId);

alter table FEEDBACK
   add constraint FK_FEEDBACK_REFERENCE_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table FEEDBACK
   add constraint FK_FEEDBACK_REFERENCE_APP_USER foreign key (appuserId)
      references APP_USER (appuserId);

alter table IMPORT_MAPPING_ITEM
   add constraint FK_IMP_M_REF_IMP_M foreign key (importMappingId)
      references IMPORT_MAPPING (importMappingId);

alter table MANUFACTURER
   add constraint FK_MANUFACT_REFERENCE_APP_MEDI foreign key (mediaId)
      references APP_MEDIA (mediaId);

alter table MENU_MENU
   add constraint FK_MENU_ME2 foreign key (menuId)
      references MENU (menuId);

alter table MENU_MENU
   add constraint FK_MENU_MENU foreign key (subMenuId)
      references MENU (menuId);

alter table MESSAGE
   add constraint FK_MES_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table MESSAGE
   add constraint FK_MES_CATEGORY foreign key (categoryId)
      references CATEGORY (categoryId);

alter table MESSAGE_LIST_ITEM
   add constraint FK_ME_MESS foreign key (messageId)
      references MESSAGE (messageId);

alter table MESSAGE_LIST_ITEM
   add constraint FK_MES_MESSAGE_ foreign key (messageListId)
      references MESSAGE_LIST (messageListId);

alter table METRIC_UNIT
   add constraint FK_Ref_Metric_Type_Unit foreign key (metricTypeId)
      references METRIC_TYPE (metricTypeId);

alter table ORDER_COUPON
   add constraint FK_ORD_CO_RE_O foreign key (orderId)
      references SALES_ORDER (orderId);

alter table ORDER_GIFT_CERTIFICATE
   add constraint FK_OR_GI_SAL_OR foreign key (orderId)
      references SALES_ORDER (orderId);

alter table ORDER_GIFT_CERTIFICATE
   add constraint FK_OR_GI_GIFT_CER foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId);

alter table ORDER_ITEM
   add constraint FK_ORDER_IT_REFERENCE_GIFT_CER foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId);

alter table ORDER_ITEM
   add constraint FK_OR_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table ORDER_ITEM
   add constraint FK_ORD_SALES_OR2 foreign key (orderId)
      references SALES_ORDER (orderId);

alter table ORDER_ITEM_ATTRIBUTE
   add constraint FK_ORD_IT_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId);

alter table ORDER_ITEM_ATTRIBUTE
   add constraint FK_ORD_IT_R_OR_IT foreign key (orderItemId)
      references ORDER_ITEM (orderItemId);

alter table ORDER_ITEM_PROMOTION
   add constraint FK_ORDER_IT_REFERENCE_ORDER_IT foreign key (orderItemId)
      references ORDER_ITEM (orderItemId);

alter table ORDER_PAYMENT
   add constraint FK_OR_PA_SALES_OR foreign key (orderId)
      references SALES_ORDER (orderId);

alter table ORDER_PAYMENT
   add constraint FK_OR_PA_REE_PAY foreign key (paymentMehodId)
      references PAYMENT_METHOD (paymentMethodId);

alter table ORDER_SHIPMENT
   add constraint FK_ORDER_SH_REFERENCE_SHIPPING foreign key (shippingRateId)
      references SHIPPING_RATE (shippingRateId);

alter table ORDER_SHIPMENT
   add constraint FK_ORD_SALES_OR foreign key (orderId)
      references SALES_ORDER (orderId);

alter table ORDER_SHIPMENT_ITEM
   add constraint FK_OR_ORDER_SH foreign key (orderShipmentId)
      references ORDER_SHIPMENT (orderShipmentId);

alter table ORDER_SHIPMENT_ITEM
   add constraint FK_OR_SH_OR_IT foreign key (orderItemId)
      references ORDER_ITEM (orderItemId);

alter table PAYMENT_METHOD
   add constraint FK_PAY_PAYMENT_2 foreign key (paymentGatewayId)
      references PAYMENT_GATEWAY (paymentGatewayId);

alter table PRODUCT
   add constraint FK_PRO_MANUFACT foreign key (manufacturerId)
      references MANUFACTURER (manufacturerId);

alter table PRODUCT
   add constraint FK_PRO_DEPARTME foreign key (departmentId)
      references DEPARTMENT (departmentId);

alter table PRODUCT_ADVERTISEMENT
   add constraint FK_PRO_ADVERTIS foreign key (advertisementId)
      references ADVERTISEMENT (advertisementId);

alter table PRODUCT_ATTRIBUTE
   add constraint FK_PR_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId);

alter table PRODUCT_ATTRIBUTE_OPTION
   add constraint FK_PRO_ATTRI foreign key (attributeOptionId)
      references ATTRIBUTE_OPTION (attributeOptionId);

alter table PRODUCT_ATTRIBUTE_OPTION
   add constraint FK_PRO_PRODUCT_ foreign key (productAttributeId)
      references PRODUCT_ATTRIBUTE (productAttributeId);

alter table PRODUCT_ATTRIBUTE_VALUE
   add constraint FK_PRODUCT__REFERENCE_ATTRIBUT foreign key (attributeId)
      references ATTRIBUTE (attributeId);

alter table PRODUCT_CATEGORY
   add constraint FK_PROD_PRO foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_CATEGORY
   add constraint FK_PRO_CATEG foreign key (categoryId)
      references CATEGORY (categoryId);

alter table PRODUCT_MEDIA
   add constraint FK_PRODUCT__REFERENCE_APP_MEDI foreign key (mediaId)
      references APP_MEDIA (mediaId);

alter table PRODUCT_MEDIA
   add constraint FK_PRODUCT__REFERENCE_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_PACKAGE_ITEM
   add constraint FK_REF_PRO_PACKAGE_ITEM foreign key (itemId)
      references PRODUCT (productId);

alter table PRODUCT_PACKAGE_ITEM
   add constraint FK_PR_PROD foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_RATING
   add constraint FK_PRO__PROD4 foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_RATING
   add constraint FK_PROD_CUST2 foreign key (customerId)
      references CUSTOMER (customerId);

alter table PRODUCT_REVIEW
   add constraint FK_PROD_PROD5 foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_REVIEW
   add constraint FK_PRO_CUSTO foreign key (customerId)
      references CUSTOMER (customerId);

alter table PRODUCT_SERIES_ITEM
   add constraint FK_PROD_prod2 foreign key (productSeriesId)
      references PRODUCT_SERIES (productSeriesId);

alter table PRODUCT_SERIES_ITEM
   add constraint FK_PROD_PROD10 foreign key (productId)
      references PRODUCT (productId);

alter table PRODUCT_TAB
   add constraint FK_PROD_TAB foreign key (tabId)
      references TAB (tabId);

alter table PROMOTION
   add constraint FK_PROMOTIO_REFERENCE_REGION foreign key (regionId)
      references REGION (regionId);

alter table PROMOTION
   add constraint FK_PROMOTIO_REFERENCE_MEMBERSH foreign key (membershipId)
      references MEMBERSHIP (membershipId);

alter table PROMOTION_PRODUCT
   add constraint FK_PROMOTIO_REFERENCE_PROMOTIO foreign key (promotionId)
      references PROMOTION (promotionId);

alter table RECOMMENDED_PRODUCT
   add constraint FK_REC_PRO foreign key (productId)
      references PRODUCT (productId);

alter table RECOMMENDED_PRODUCT
   add constraint FK_RECO_RECOMMEN foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId);

alter table RECOMMENDED_TYPE_LOCATION
   add constraint FK_RECOMMEN_REFERENCE_RECOMMEN foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId);

alter table REGION_ITEM
   add constraint FK_REGION_I_REFERENCE_REGION foreign key (regionId)
      references REGION (regionId);

alter table REPORT
   add constraint FK_REPORT_REFERENCE_REPORT_F foreign key (reportFolderId)
      references REPORT_FOLDER (reportFolderId);

alter table SALES_ORDER
   add constraint FK_SALES_OR_REFERENCE_PAYMENT_ foreign key (paymentMethodId)
      references PAYMENT_METHOD (paymentMethodId);

alter table SAVED_PRODUCT_LIST
   add constraint FK_SAVE_PRO_CUS foreign key (customerId)
      references CUSTOMER (customerId);

alter table SAVED_PRODUCT_LIST
   add constraint FK_SAVE_PRO_PRO foreign key (productId)
      references PRODUCT (productId);

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_REF_SYSTEM_1 foreign key (seeAlsoUrlId)
      references SYSTEM_URL (urlId);

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_REFERENCE_SYSTEM_U foreign key (systemUrlId)
      references SYSTEM_URL (urlId);

alter table SHIPPING_METHOD
   add constraint FK_SHIP_CARRIER foreign key (carrierId)
      references CARRIER (carrierId);

alter table SHIPPING_METHOD
   add constraint FK_SHIPE_SHIPPING foreign key (shippingGatewayId)
      references SHIPPING_GATEWAY (shippingGatewayId);

alter table SHIPPING_RATE
   add constraint FK_SHIPPING_REFERENCE_REGION foreign key (regionId)
      references REGION (regionId);

alter table SHIPPING_RATE
   add constraint FK_SHIP_SHIPPING foreign key (shippingMethodId)
      references SHIPPING_METHOD (shippingMethodId);

alter table SHOPPINGCART
   add constraint FK_CART_REF_ADDR_1 foreign key (shippingAddressId)
      references ADDRESS (addressId);

alter table SHOPPINGCART
   add constraint FK_CART_REF_ADDR_2 foreign key (billingAddressId)
      references ADDRESS (addressId);

alter table SHOPPINGCART_ITEM
   add constraint FK_SHO_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table SHOPPINGCART_ITEM
   add constraint FK_SHO_SHO foreign key (shoppingCartId)
      references SHOPPINGCART (shoppingCartId);

alter table SHOPPINGCART_ITEM_PROMOTION
   add constraint FK_SHOPPING_REFERENCE_SHOPPING foreign key (shoppingCartItemId)
      references SHOPPINGCART_ITEM (shoppingCartItemId);

alter table SHOP_POINT_HISTORY
   add constraint FK_SHOP_CUST2 foreign key (customerId)
      references CUSTOMER (customerId);

alter table SHOP_POINT_USED_HISTORY
   add constraint FK_SHOP_POI_CUS foreign key (customerId)
      references CUSTOMER (customerId);

alter table SPEED_BAR
   add constraint FK_SPEED_BA_REFERENCE_APP_MEDI foreign key (mediaId)
      references APP_MEDIA (mediaId);

alter table SURVEY_OPTION
   add constraint FK_SUR_SURVEY foreign key (surveyId)
      references SURVEY (surveyId);

alter table SURVEY_RESULT
   add constraint FK_SU_R_SURVEY foreign key (surveyId)
      references SURVEY (surveyId);

alter table SURVEY_RESULT
   add constraint FK_SURY_R__SUR_O foreign key (surveyOptionId)
      references SURVEY_OPTION (surveyOptionId);

alter table SYSTEM_MESSAGE
   add constraint FK_SYSTEM_M_FK_SM_APP_APP_USER foreign key (appuserId)
      references APP_USER (appuserId);

alter table SYSTEM_URL
   add constraint FK_SYSTEM_U_REFERENCE_SYSTEM_U foreign key (parentId)
      references SYSTEM_URL (urlId);

alter table TAX_RATE
   add constraint FK_TAX_RATE_CA foreign key (categoryId)
      references CATEGORY (categoryId);

alter table TAX_RATE
   add constraint FK_TAX_RATE_REFERENCE_REGION foreign key (regionId)
      references REGION (regionId);

alter table TAX_RATE
   add constraint FK_TAX_RATE_TAX foreign key (taxId)
      references TAX (taxId);

alter table TEMPLATE
   add constraint FK_TEMPL_TEMPLATE foreign key (templateSetId)
      references TEMPLATE_SET (templateSetId);

alter table USER_ROLE
   add constraint FK_APP_APPUSER foreign key (appuserId)
      references APP_USER (appuserId);

alter table USER_ROLE
   add constraint FK_APP_APP_ROLE foreign key (roleId)
      references APP_ROLE (roleId);

alter table VARIATION_ITEM
   add constraint FK_VAR_PRODUCT_ foreign key (productVariationId)
      references PRODUCT_VARIATION (productVariationId);

alter table VARIATION_ITEM
   add constraint FK_VAR_ATTRIBUT foreign key (attributeOptionId)
      references ATTRIBUTE_OPTION (attributeOptionId);

alter table VOTE_OPTION
   add constraint FK_VOTE_OPT_VOTE foreign key (voteId)
      references VOTE (voteId);

alter table VOTE_RESULT
   add constraint FK_VOTE_RES_VOTE foreign key (voteId)
      references VOTE (voteId);

alter table VOTE_RESULT
   add constraint FK_VOTE_VOTE_OPT foreign key (voteOptionId)
      references VOTE_OPTION (voteOptionId);

alter table WISHLIST
   add constraint FK_WISH_CUSTOMER foreign key (customerId)
      references CUSTOMER (customerId);

alter table WISHLIST
   add constraint FK_WISH_ADD foreign key (shippingAddressId)
      references ADDRESS (addressId);

alter table WISHLIST_ITEM
   add constraint FK_WIS_PRODUCT foreign key (productId)
      references PRODUCT (productId);

alter table WISHLIST_ITEM
   add constraint FK_WIS_WISHLIST foreign key (wishListId)
      references WISHLIST (wishListId);

alter table WISHLIST_ITEM_ATTRIBUTE
   add constraint FK_WI_WIS foreign key (wishlistItemId)
      references WISHLIST_ITEM (wishlistItemId);

alter table WISHLIST_ITEM_ATTRIBUTE
   add constraint FK_WISH_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId);


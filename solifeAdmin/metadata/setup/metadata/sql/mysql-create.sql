/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2006-11-13 9:48:25                           */
/*==============================================================*/


/*==============================================================*/
/* Table: ADDRESS                                               */
/*==============================================================*/
create table ADDRESS
(
   addressId            int not null auto_increment,
   appuserId            int,
   addressType          smallint not null comment '0=Personal address(reserved)
            1=shippinb address
            2=billing address
            3=company address',
   title                varchar(8) not null,
   firstname            varchar(32) not null,
   lastname             varchar(32) not null,
   telephone            varchar(32) not null,
   zip                  varchar(6),
   fax                  varchar(32),
   companyName          varchar(128),
   isDefaultShippingAddress smallint comment '0=No
            1=Yes',
   isDefaultBillingAddress smallint comment '0=No
            1=Yes',
   address              varchar(128) not null,
   address2             varchar(128),
   countryName          varchar(64) not null,
   stateName            varchar(64) comment 'Selected or Other state that not in our database',
   cityName             varchar(64) comment 'Selected or Other city that not in our database',
   countryId            int not null,
   stateId              int default 0,
   cityId               int default 0,
   userDefinedId        int default 0,
   version              int not null default 0,
   primary key (addressId)
)
comment = "Manage all customer address";

/*==============================================================*/
/* Table: ADVERTISEMENT                                         */
/*==============================================================*/
create table ADVERTISEMENT
(
   advertisementId      int not null auto_increment,
   advertisementTypeId  int not null,
   promotionId          int,
   advertisementName    varchar(128) not null,
   contentType          smallint not null comment '0=Text
            1=Image
            2=flash',
   advertisementDetail  varchar(512),
   mediaPath            varchar(128),
   url                  varchar(255) not null,
   redirectUrl          varchar(255),
   target               varchar(32),
   width                numeric(12,2),
   height               numeric(12,2),
   startPublishTime     datetime comment 'when will start publishing this ad',
   endPublishTime       datetime comment 'expire Publish Time',
   displayCount         int comment 'how many times this ad has been displayed to the customer',
   clickCount           int comment 'how many times this ad has been clicked',
   sortOrder            int,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (advertisementId)
);

/*==============================================================*/
/* Table: ADVERTISEMENT_POSITION                                */
/*==============================================================*/
create table ADVERTISEMENT_POSITION
(
   adpositionId         int not null auto_increment,
   advertisementId      int not null,
   positionTypeId       int not null,
   displayOrder         int comment 'when in a sequential display mode, define the display order',
   version              int not null default 0,
   primary key (adpositionId)
)
comment = "Define ad is used in which position type";

/*==============================================================*/
/* Table: ADVERTISEMENT_TYPE                                    */
/*==============================================================*/
create table ADVERTISEMENT_TYPE
(
   advertisementTypeId  int not null auto_increment,
   typeName             varchar(64) not null,
   typeCode             varchar(32) not null,
   classpath            varchar(255),
   version              int not null default 0,
   primary key (advertisementTypeId),
   unique key AK_Key_2 (typeCode)
)
comment = "Define types of ad";

/*==============================================================*/
/* Table: AD_POSITION_TYPE                                      */
/*==============================================================*/
create table AD_POSITION_TYPE
(
   positionTypeId       int not null auto_increment,
   positionName         varchar(128),
   type                 smallint comment '0=Text
            1=Image
            2=flash
            3=other multimedia
            ',
   height               int,
   width                int,
   displayType          smallint comment '0=Sequential
            1=Random',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   description          varchar(512),
   templatePath         varchar(128),
   version              int not null default 0,
   primary key (positionTypeId)
)
comment = "Define ad position type";

/*==============================================================*/
/* Table: ALSO_BUY                                              */
/*==============================================================*/
create table ALSO_BUY
(
   alsoBuyId            int not null auto_increment,
   productId            int not null,
   alsoProductId        int not null,
   times                int comment 'how many times this product has been purchased',
   version              int not null default 0,
   primary key (alsoBuyId)
)
comment = "Customer buy this also buy these products";

/*==============================================================*/
/* Table: APP_ADMIN                                             */
/*==============================================================*/
create table APP_ADMIN
(
   appAdminId           int not null,
   department           varchar(64),
   userPosition         varchar(128) comment 'user position, use '' to seperate several positions',
   version              int,
   primary key (appAdminId)
)
comment = "System administrator";

/*==============================================================*/
/* Table: APP_AUDIT                                             */
/*==============================================================*/
create table APP_AUDIT
(
   appAuditId           int not null auto_increment,
   procUserId           int,
   procObj              varchar(1024),
   actionName           varchar(64) not null,
   procResult           varchar(1024),
   requestUrl           varchar(255),
   procTime             datetime not null,
   primary key (appAuditId)
);

/*==============================================================*/
/* Table: APP_MEDIA                                             */
/*==============================================================*/
create table APP_MEDIA
(
   mediaId              int not null auto_increment,
   mediaTypeId          int,
   mediaName            varchar(128) not null,
   mediaDescription     varchar(512),
   mediaWidth           int,
   mediaHeight          int,
   mediaPath            varchar(128),
   mediaUrl             varchar(255),
   alt                  varchar(128),
   isMappinged          smallint,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (mediaId)
);

/*==============================================================*/
/* Table: APP_ROLE                                              */
/*==============================================================*/
create table APP_ROLE
(
   roleId               int not null auto_increment,
   roleName             varchar(32) not null,
   roleDetail           varchar(255),
   createTime           datetime,
   updateTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (roleId)
)
comment = "Application used roles";

/*==============================================================*/
/* Table: APP_USER                                              */
/*==============================================================*/
create table APP_USER
(
   appuserId            int not null auto_increment,
   username             varchar(32) not null,
   password             varchar(128),
   userType             smallint comment '0=customer
            1=administrator,
            2=partener',
   title                varchar(8),
   firstname            varchar(32),
   lastname             varchar(32),
   email                varchar(64) not null,
   fax                  varchar(32),
   zip                  varchar(6),
   telephone            varchar(32),
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   isLocked             smallint comment '0=not locked
            1=is locked',
   deleted              smallint not null default 0,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (appuserId),
   unique key AK_Key_2 (username),
   unique key AK_Key_3 (email)
)
comment = "Base use info, inherited by app_admin,customer,partner";

/*==============================================================*/
/* Table: ATTRIBUTE                                             */
/*==============================================================*/
create table ATTRIBUTE
(
   attributeId          int not null auto_increment,
   attributeNameKey     varchar(32) not null,
   attributeType        smallint not null comment 'Define the attribute is used for what purpose
            0=product/category
            1=customer
            2=order
            3=other
            9=general',
   defaultValue         varchar(128),
   validationExpression varchar(1024) comment 'used when attributeType=1(user input)
            use regular expression to validate user input',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   isRequired           smallint comment '0=No
            1=Yes',
   inputType            smallint comment '0=list(drop down select box)
            1=input',
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (attributeId)
)
comment = "All attributes used by product/category";

/*==============================================================*/
/* Table: ATTRIBUTE_OPTION                                      */
/*==============================================================*/
create table ATTRIBUTE_OPTION
(
   attributeOptionId    int not null auto_increment,
   attributeId          int not null,
   attributeOptionNameKey varchar(32) not null,
   attributeOptionValue varchar(32) not null,
   isDefault            smallint comment '0=No
            1=Yes',
   sortOrder            int,
   version              int not null default 0,
   primary key (attributeOptionId)
)
comment = "Available option of a attribute";

/*==============================================================*/
/* Table: CARRIER                                               */
/*==============================================================*/
create table CARRIER
(
   carrierId            int not null auto_increment,
   carrierName          varchar(128) not null,
   carrierAddress       varchar(128),
   carrierAddress2      varchar(128),
   linkman              varchar(32),
   telephone            varchar(32),
   fax                  varchar(32),
   email                varchar(64),
   zip                  varchar(6),
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (carrierId)
);

/*==============================================================*/
/* Table: CATEGORY                                              */
/*==============================================================*/
create table CATEGORY
(
   categoryId           int not null auto_increment,
   categoryNameKey      varchar(32) not null,
   categoryDescriptionKey varchar(32),
   categoryCode         varchar(32) not null,
   productCount         int comment 'how many product under this category',
   subCategoryCount     int comment 'how many sub category under this category',
   metaKeywordKey       varchar(32),
   metaDescriptionKey   varchar(32),
   membershipId         int comment 'If defined, only customer having specified membership or above (determine by level) can see the category and products under it.',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            
            ',
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   templatePath         varchar(128),
   categoryPath         varchar(255) not null,
   imageUrl             varchar(255),
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (categoryId),
   unique key AK_Key_3 (categoryCode),
   unique key AK_Key_Cate_2 (categoryPath)
)
comment = "Product category. Id=0 is the root category. Each has a unique category path. Can have subcategory, parent category.";

/*==============================================================*/
/* Table: CATEGORY_CATEGORY                                     */
/*==============================================================*/
create table CATEGORY_CATEGORY
(
   categoryCategoryId   int not null auto_increment,
   subCategoryId        int not null,
   categoryId           int not null,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)',
   sortOrder            int,
   version              int not null default 0,
   primary key (categoryCategoryId),
   unique key AK_Key_2 (categoryPath)
);

/*==============================================================*/
/* Table: CATEGORY_SHIPPING_RATE                                */
/*==============================================================*/
create table CATEGORY_SHIPPING_RATE
(
   categoryShippingMethodId int not null auto_increment,
   shippingRateId       int,
   categoryProductPath  varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)
            ',
   isCategory           smallint comment '0=is product
            1=is category',
   version              int not null default 0,
   primary key (categoryShippingMethodId)
);

/*==============================================================*/
/* Table: COMPANY_INFO                                          */
/*==============================================================*/
create table COMPANY_INFO
(
   companyId            int not null auto_increment,
   companyNameKey       varchar(32) not null,
   country              varchar(64),
   state                varchar(64),
   city                 varchar(64),
   address              varchar(128),
   phone                varchar(128),
   email                varchar(64),
   fax                  varchar(32),
   zip                  varchar(6),
   reserve1Key          varchar(32),
   reserve2             varchar(32),
   primary key (companyId)
);

/*==============================================================*/
/* Table: COOKIE                                                */
/*==============================================================*/
create table COOKIE
(
   cookieId             int not null auto_increment,
   cookieUid            varchar(64) not null,
   shoppingCartId       int,
   visitTime            datetime,
   version              int not null default 0,
   primary key (cookieId),
   unique key AK_Key_2 (cookieUid)
);

/*==============================================================*/
/* Table: COUPON                                                */
/*==============================================================*/
create table COUPON
(
   couponId             int not null auto_increment,
   couponTypeId         int not null,
   couponNo             varchar(16) not null,
   isSent               smallint comment '0:Not sent to customer yet
            1:Sent',
   remainedTimes        int comment 'how many remained times this coupon can be used',
   promotionId          int comment 'saved promotion id for better performance',
   status               smallint,
   version              int not null default 0,
   primary key (couponId),
   unique key AK_Key_2 (couponNo)
);

/*==============================================================*/
/* Table: COUPON_TYPE                                           */
/*==============================================================*/
create table COUPON_TYPE
(
   couponTypeId         int not null auto_increment,
   promotionId          int,
   title                varchar(128),
   times                int,
   status               smallint,
   version              int not null default 0,
   primary key (couponTypeId)
)
comment = "Types of coupon";

/*==============================================================*/
/* Table: CREDIT_CARD                                           */
/*==============================================================*/
create table CREDIT_CARD
(
   creditCardId         int not null auto_increment,
   creditCardTypeId     int not null,
   customerId           int not null,
   creditCardNo         varchar(32) not null,
   cardHolderName       varchar(32),
   cvv2                 smallint,
   expireDate           datetime,
   version              int not null default 0,
   primary key (creditCardId)
)
comment = "Customer credit card info
           
           [Pending]";

/*==============================================================*/
/* Table: CREDIT_CARD_TYPE                                      */
/*==============================================================*/
create table CREDIT_CARD_TYPE
(
   creditCardTypeId     int not null auto_increment,
   creditCardTypeCode   varchar(32) not null,
   creditCardTypeName   varchar(32) not null,
   hasCvv2              smallint comment '0=No
            1=Yes',
   status               smallint,
   version              int not null default 0,
   primary key (creditCardTypeId)
)
comment = "Types of credit card supported
           
           [Pending]";

/*==============================================================*/
/* Table: CURRENCY                                              */
/*==============================================================*/
create table CURRENCY
(
   currencyId           int not null auto_increment,
   currencyCode         varchar(8) not null,
   currencyName         varchar(32) not null,
   isDefault            smallint,
   status               smallint,
   sortOrder            int,
   exchangeRate         numeric(12,4),
   currencySymbol       varchar(16) not null,
   version              int not null default 0,
   primary key (currencyId)
)
comment = "Supported currency";

/*==============================================================*/
/* Table: CUSTOMER                                              */
/*==============================================================*/
create table CUSTOMER
(
   customerId           int not null,
   passwordHint         varchar(128),
   passwordHintAnswer   varchar(255),
   customerPosition     varchar(128) comment 'Customer position in his/her company, when several positions applied, use comma to seperate them',
   registerTime         datetime,
   membershipId         int,
   totalPoints          int,
   lastVisitedTime      datetime,
   note                 varchar(1024),
   version              int,
   primary key (customerId)
)
comment = "Registered Customer info. Link to cookie, has membership.";

/*==============================================================*/
/* Table: CUSTOMER_MESSAGE_LIST                                 */
/*==============================================================*/
create table CUSTOMER_MESSAGE_LIST
(
   customerMessageListId int not null auto_increment,
   messageListId        int,
   email                varchar(64) not null,
   version              int not null default 0,
   primary key (customerMessageListId)
)
comment = "Customre subscribe which message list";

/*==============================================================*/
/* Table: CUSTOMER_PREFERENCE                                   */
/*==============================================================*/
create table CUSTOMER_PREFERENCE
(
   customerPreferenceId int not null auto_increment,
   customerId           int not null,
   preferenceKey        varchar(32),
   preferenceValue      varchar(255) comment 'value of customer preference, use comma to seperate if there are more than one value',
   version              int not null default 0,
   primary key (customerPreferenceId)
)
comment = "Customer preference, e.g. personalization settings";

/*==============================================================*/
/* Table: DEPARTMENT                                            */
/*==============================================================*/
create table DEPARTMENT
(
   departmentId         int not null auto_increment,
   departmentCode       varchar(32) not null,
   departmentNameKey    varchar(32) not null,
   departmentDetailKey  varchar(32),
   productCount         int default 0,
   departmentLevel      int comment 'a saved calculated department level',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   sortOrder            int,
   parentId             int not null default 0,
   createBy             int,
   updateBy             int,
   updateTime           datetime,
   createTime           datetime,
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (departmentId),
   unique key AK_Key_2 (departmentCode)
)
comment = "Some store organize and sell goods by deparment, e.g. a department store";

/*==============================================================*/
/* Table: EXPORT_MAPPING                                        */
/*==============================================================*/
create table EXPORT_MAPPING
(
   exportMappingId      int not null auto_increment,
   exportMappingName    varchar(32) not null,
   createBy             int,
   updateBy             int,
   createTime           datetime not null,
   updateTime           datetime,
   exportFileType       varchar(8),
   description          varchar(512),
   sourceEntityName     varchar(32) not null,
   targetEntityName     varchar(32) not null,
   version              int not null default 0,
   primary key (exportMappingId)
);

/*==============================================================*/
/* Table: EXPORT_MAPPING_ITEM                                   */
/*==============================================================*/
create table EXPORT_MAPPING_ITEM
(
   exportMappingItemId  int not null auto_increment,
   exportMappingId      int,
   sourcePropertyName   varchar(32),
   sourcePropertyType   varchar(32),
   targetPropertyName   varchar(32),
   targetPropertyType   varchar(32),
   isOverwrite          smallint,
   version              int not null default 0,
   primary key (exportMappingItemId)
);

/*==============================================================*/
/* Table: FAQ                                                   */
/*==============================================================*/
create table FAQ
(
   faqId                int not null auto_increment,
   faqCategoryId        int not null,
   questionKey          varchar(32) not null,
   answerKey            varchar(32) not null,
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            
            ',
   version              int not null default 0,
   primary key (faqId)
);

/*==============================================================*/
/* Table: FAQ_CATEGORY                                          */
/*==============================================================*/
create table FAQ_CATEGORY
(
   faqCategoryId        int not null auto_increment,
   faqCategoryTitleKey  varchar(32) not null,
   sortOrder            int,
   version              int not null default 0,
   primary key (faqCategoryId)
)
comment = "Organize FAQ by category";

/*==============================================================*/
/* Table: FAVORITE                                              */
/*==============================================================*/
create table FAVORITE
(
   favoriteId           int not null auto_increment,
   customerId           int not null,
   favoriteTitle        varchar(128) not null,
   url                  varchar(255) not null,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (favoriteId)
);

/*==============================================================*/
/* Table: FEEDBACK                                              */
/*==============================================================*/
create table FEEDBACK
(
   feedbackId           int not null auto_increment,
   productId            int,
   appuserId            int,
   title                varchar(8),
   firstName            varchar(32),
   lastName             varchar(32),
   subject              varchar(128),
   content              varchar(512),
   status               smallint comment '0=Open
            1=Closed',
   replyType            smallint comment '0=online
            1=email
            2=fax 
            3=telephone',
   feedbackType         smallint comment '0=Complaint
            1=Suggestion',
   priority             int comment 'define the priority that should be processed',
   email                varchar(64),
   telephone            varchar(32),
   fax                  varchar(32),
   threadId             int,
   updateTime           datetime,
   createTime           datetime,
   version              int not null default 0,
   primary key (feedbackId)
);

/*==============================================================*/
/* Table: GIFT_CERTIFICATE                                      */
/*==============================================================*/
create table GIFT_CERTIFICATE
(
   giftCertificateId    int not null auto_increment,
   recipientContryId    int,
   recipientCity        varchar(64),
   recipientState       varchar(64),
   giftCertificateNo    varchar(32) not null,
   orderNo              varchar(12),
   purchaser            varchar(64) not null,
   recipient            varchar(64) not null,
   isSentByEmail        smallint not null comment 'whether gift Certificate should be sent by email
            0=No
            1=Yes',
   recipientEmail       varchar(64),
   recipientFullname    varchar(32),
   recipientAddress     varchar(128),
   recipientZip         varchar(6),
   recipientPhone       varchar(32),
   message              varchar(512),
   giftCertAmt          numeric(12,2) not null comment 'gift certificate total amount',
   remainedAmt          numeric(12,2) comment 'remained gift certificate amount',
   expireTime           datetime comment 'expire Time',
   status               smallint not null,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (giftCertificateId),
   unique key AK_Key_2 (giftCertificateNo)
);

/*==============================================================*/
/* Table: HELP                                                  */
/*==============================================================*/
create table HELP
(
   helpId               int not null auto_increment comment 'help comment id',
   moduleName           varchar(32),
   helpTitle            varchar(128) not null,
   helpContent          varchar(1024) not null,
   helpCode             varchar(32),
   sortOrder            int,
   version              int not null default 0,
   primary key (helpId)
);

/*==============================================================*/
/* Table: I18NTEXT                                              */
/*==============================================================*/
create table I18NTEXT
(
   i18nTextId           int not null auto_increment,
   textKey              varchar(32) not null,
   category             varchar(64),
   description          varchar(512),
   moduleId             int,
   createBy             int,
   createTime           datetime,
   updateTime           datetime,
   updateBy             int,
   version              int not null default 0,
   primary key (i18nTextId),
   unique key AK_KEY_2_I18NTEXT_1 (textKey)
)
comment = "Define i18n text keys";

/*==============================================================*/
/* Table: I18NTEXT_ITEM                                         */
/*==============================================================*/
create table I18NTEXT_ITEM
(
   i18nTextItemId       int not null auto_increment,
   textKey              varchar(32) not null,
   textValue            varchar(1024),
   category             varchar(64),
   createBy             int,
   createTime           datetime,
   updateBy             int,
   updateTime           datetime,
   localeCode           varchar(8) not null comment 'locale Code',
   version              int not null default 0,
   primary key (i18nTextItemId),
   unique key AK_Key_2 (textKey, localeCode)
)
comment = "Define a specific language text for a i18n text";

/*==============================================================*/
/* Table: IMPORT_EXPORT_ENTITY                                  */
/*==============================================================*/
create table IMPORT_EXPORT_ENTITY
(
   entityId             int not null auto_increment,
   operateType          smallint not null comment '0=import
            1=export',
   entityName           varchar(256) not null,
   description          varchar(512),
   version              int not null default 0,
   primary key (entityId)
);

/*==============================================================*/
/* Table: IMPORT_MAPPING                                        */
/*==============================================================*/
create table IMPORT_MAPPING
(
   importMappingId      int not null auto_increment,
   importMappingName    varchar(32) not null,
   createBy             int,
   updateBy             int,
   createTime           datetime not null,
   updateTime           datetime,
   importFileType       varchar(8),
   description          varchar(512),
   version              int not null default 0,
   primary key (importMappingId)
);

/*==============================================================*/
/* Table: IMPORT_MAPPING_ITEM                                   */
/*==============================================================*/
create table IMPORT_MAPPING_ITEM
(
   importMappingItemId  int not null auto_increment,
   importMappingId      int,
   dbColumnName         varchar(32),
   fileColumnIndex      int,
   dataType             varchar(32),
   isOverwrite          smallint,
   version              int not null default 0,
   primary key (importMappingItemId)
);

/*==============================================================*/
/* Table: INDEX_ITEM                                            */
/*==============================================================*/
create table INDEX_ITEM
(
   indexItemId          int not null auto_increment,
   contentUrl           varchar(255) not null,
   subject              smallint,
   contentMark          varchar(64),
   requestType          smallint,
   createTime           datetime,
   updateTime           datetime,
   status               smallint,
   version              int not null default 0,
   primary key (indexItemId),
   unique key AK_Key_2 (contentUrl)
);

/*==============================================================*/
/* Table: MANUFACTURER                                          */
/*==============================================================*/
create table MANUFACTURER
(
   manufacturerId       int not null auto_increment,
   mediaId              int,
   manufacturerCode     varchar(32) not null,
   manufacturerNameKey  varchar(32) not null,
   manufacturerUrl      varchar(255) comment 'site url of manufacturer',
   productCount         int comment 'how many products of this manufacturer',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   sortOrder            int,
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (manufacturerId),
   unique key AK_Key_2 (manufacturerCode)
)
comment = "Manufacturer that produce the product";

/*==============================================================*/
/* Table: MEDIA_MAPPING_RULE                                    */
/*==============================================================*/
create table MEDIA_MAPPING_RULE
(
   mappingId            int not null auto_increment,
   mappingName          varchar(128) not null,
   mappingRule          varchar(128),
   mappingDescription   varchar(1024),
   status               smallint,
   version              int not null default 0,
   primary key (mappingId)
);

/*==============================================================*/
/* Table: MEDIA_TYPE                                            */
/*==============================================================*/
create table MEDIA_TYPE
(
   mediaTypeId          int not null auto_increment,
   typeName             varchar(64) not null,
   filenameRule         varchar(128) comment 'the path/location this type of image are stored',
   mediaTypeKey         varchar(32),
   version              int not null default 0,
   primary key (mediaTypeId)
)
comment = "Types of image supported/used.";

/*==============================================================*/
/* Table: MEMBERSHIP                                            */
/*==============================================================*/
create table MEMBERSHIP
(
   membershipId         int not null auto_increment,
   membershipNameKey    varchar(32) not null,
   membershipDetailKey  varchar(32),
   membershipLevel      int not null comment 'A unique and sequential level, higher level auto get access to assets granted to lower level membership.',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (membershipId),
   unique key AK_Key_2 (membershipNameKey),
   unique key AK_Key_3 (membershipLevel)
)
comment = "Customer membership, each has a unique level, thus a higher level membership auto get access to assets granted to a lower level membership.";

/*==============================================================*/
/* Table: MENU                                                  */
/*==============================================================*/
create table MENU
(
   menuId               int not null auto_increment,
   menuNameKey          varchar(32) not null,
   url                  varchar(255) not null,
   categoryId           int,
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   primary key (menuId)
);

/*==============================================================*/
/* Table: MENU_MENU                                             */
/*==============================================================*/
create table MENU_MENU
(
   menuMenuId           int not null auto_increment,
   menuId               int not null,
   subMenuId            int not null,
   primary key (menuMenuId)
);

/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
create table MESSAGE
(
   messageId            int not null auto_increment,
   productId            int,
   categoryId           int,
   messageSubjectKey    varchar(32) not null,
   messageBodyKey       varchar(32) not null,
   isSupportedHtml      smallint comment '0=not support html
            1=support html',
   isShowInNews         smallint comment '0=not show in news
            1=show in news',
   isAvailableForSubscription smallint comment '0=No
            1=Yes
            
            ',
   testEmail            varchar(64) comment 'email address that use for test purpose',
   templatePath         varchar(128),
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (messageId)
)
comment = "Message that composed by admin and will be sent to customer";

/*==============================================================*/
/* Table: MESSAGE_LIST                                          */
/*==============================================================*/
create table MESSAGE_LIST
(
   messageListId        int not null auto_increment,
   messageListNameKey   varchar(32) not null,
   messageListDetailKey varchar(32),
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   deleted              smallint not null default 0,
   primary key (messageListId),
   unique key AK_Key_2 (messageListNameKey)
)
comment = "Message list that used to organize message and can be subscribed by customer";

/*==============================================================*/
/* Table: MESSAGE_LIST_ITEM                                     */
/*==============================================================*/
create table MESSAGE_LIST_ITEM
(
   messageListItemId    int not null auto_increment,
   messageListId        int not null,
   messageId            int not null,
   sortOrder            int,
   version              int not null default 0,
   primary key (messageListItemId)
)
comment = "Message belongs to which message list";

/*==============================================================*/
/* Table: METRIC_TYPE                                           */
/*==============================================================*/
create table METRIC_TYPE
(
   metricTypeId         int not null auto_increment,
   metricTypeCode       varchar(32) not null,
   metricTypeNameKey    varchar(32) not null,
   description          varchar(1024),
   version              int not null default 0,
   primary key (metricTypeId)
);

/*==============================================================*/
/* Table: METRIC_UNIT                                           */
/*==============================================================*/
create table METRIC_UNIT
(
   metricUnitId         int not null auto_increment,
   metricTypeId         int not null,
   metricUnitCode       varchar(32) not null comment 'length: start with"L_"
            weight: start with"W_"
            money: start with"M_"
            volume start with"V_"',
   metricUnitNameKey    varchar(32) not null,
   isDefault            smallint comment '0=not default
            1=is default',
   convertRate          numeric(12,2),
   version              int not null default 0,
   primary key (metricUnitId),
   unique key AK_Key_2 (metricUnitCode)
);

/*==============================================================*/
/* Table: MODULE_HELP                                           */
/*==============================================================*/
create table MODULE_HELP
(
   moduleHelpId         int not null auto_increment,
   moduleUrl            varchar(255) not null,
   helpContent          varchar(1024),
   primary key (moduleHelpId)
);

/*==============================================================*/
/* Table: ORDER_COUPON                                          */
/*==============================================================*/
create table ORDER_COUPON
(
   orderCouponId        int not null auto_increment,
   orderId              int not null,
   discountAmt          numeric(12,2) comment 'total amount discounted using this coupon',
   couponId             int,
   couponTypeId         int,
   needToCalculate      smallint comment 'for special coupon whick could''t to item saveMoney''s count.
            
            1:  need to calculate when stat. subtotal
            
            0:  not need
            
             
            ',
   version              int not null default 0,
   primary key (orderCouponId)
)
comment = "Coupon used in an order";

/*==============================================================*/
/* Table: ORDER_GIFT_CERTIFICATE                                */
/*==============================================================*/
create table ORDER_GIFT_CERTIFICATE
(
   orderGiftCertificateId int not null auto_increment,
   orderId              int not null,
   giftCertificateId    int not null,
   usedAmt              numeric(12,2) comment 'amount of gift certificate used in this order',
   version              int not null default 0,
   primary key (orderGiftCertificateId)
)
comment = "Gift certificate used in an order";

/*==============================================================*/
/* Table: ORDER_ITEM                                            */
/*==============================================================*/
create table ORDER_ITEM
(
   orderItemId          int not null auto_increment,
   orderId              int not null,
   productId            int,
   giftCertificateId    int,
   itemType             smallint comment 'types of items/goods:
            1:product (default)
            2:gift certificate
            3:service
            4:software
            5:document
            6:music
            7:video
            8:others',
   quantity             int not null,
   price                numeric(12,2) not null,
   addTime              datetime not null,
   discountPrice        numeric(12,2) comment 'price after discounted',
   discoutValidFlag     smallint,
   hasUnitedPmt         smallint comment 'has united promotion',
   isGroup              smallint,
   isFreeShipping       smallint comment 'Is this item free for shipping
            0=No
            1=Yes
            ',
   isExempt             smallint comment 'Is tax free/exempt
            0=No
            1=Yes',
   wrapId               int comment 'wrap type id',
   wrapCost             numeric(12,2) comment 'wrapping cost  of this item',
   wrapNote             varchar(1024) comment 'notes that should be written on the wrap',
   isSpecialOffer       smallint,
   saveMoney            numeric(12,2),
   productAttributes    blob,
   categoryPath         varchar(255) not null,
   version              int not null default 0,
   primary key (orderItemId)
);

/*==============================================================*/
/* Table: ORDER_ITEM_ATTRIBUTE                                  */
/*==============================================================*/
create table ORDER_ITEM_ATTRIBUTE
(
   orderItemAttributeId int not null auto_increment,
   attributeId          int not null,
   attributeNameKey     varchar(32),
   orderItemId          int not null,
   attributeOptionNameKey varchar(32),
   attributeValue       varchar(128),
   version              int not null default 0,
   primary key (orderItemAttributeId)
)
comment = "Product attributes of an order item";

/*==============================================================*/
/* Table: ORDER_ITEM_PROMOTION                                  */
/*==============================================================*/
create table ORDER_ITEM_PROMOTION
(
   orderItemPromotionId int not null auto_increment,
   orderItemId          int,
   promotionId          int,
   titleKey             varchar(32),
   descriptionKey       varchar(32),
   saveMoney            numeric(12,2),
   validFlag            smallint comment '0=is not valid
            1=is valid',
   isUnited             smallint comment '0=is not united
            1=is united',
   version              int not null default 0,
   primary key (orderItemPromotionId)
)
comment = "
           ";

/*==============================================================*/
/* Table: ORDER_PAYMENT                                         */
/*==============================================================*/
create table ORDER_PAYMENT
(
   orderPaymentId       int not null auto_increment,
   orderId              int not null,
   paymentMehodId       int not null,
   creditCardType       varchar(32),
   creditCardNo         varchar(32),
   cvv2                 varchar(8),
   creditExpireDate     datetime,
   paymentAmt           numeric(12,2) not null,
   currency             varchar(32),
   createTime           datetime not null,
   version              int not null default 0,
   primary key (orderPaymentId)
)
comment = "Payment info of an order";

/*==============================================================*/
/* Table: ORDER_SHIPMENT                                        */
/*==============================================================*/
create table ORDER_SHIPMENT
(
   orderShipmentId      int not null auto_increment,
   orderId              int not null,
   shippingRateId       int,
   trackingNo           varchar(128) comment 'tracking no of this shipment',
   shippingCost         numeric(12,2),
   shippingStartTime    datetime comment 'start time of shipping',
   shippingArriveTime   datetime comment 'when the shipping arrived and completed',
   status               smallint comment '0=Stockup
            1=Short
            2=Delivering
            3=Delivered',
   version              int not null default 0,
   primary key (orderShipmentId)
)
comment = "Shipment info of an order";

/*==============================================================*/
/* Table: ORDER_SHIPMENT_ITEM                                   */
/*==============================================================*/
create table ORDER_SHIPMENT_ITEM
(
   orderShipmentItemId  int not null auto_increment,
   orderItemId          int not null,
   orderShipmentId      int not null,
   quantity             int not null comment 'how many items are shipped',
   version              int not null default 0,
   primary key (orderShipmentItemId)
)
comment = "Which items are delivered in each shipment";

/*==============================================================*/
/* Table: PAYMENT_GATEWAY                                       */
/*==============================================================*/
create table PAYMENT_GATEWAY
(
   paymentGatewayId     int not null auto_increment,
   paymentGatewayName   varchar(256) not null,
   paymentGatewayDetail varchar(1024),
   processorFile        varchar(256) comment 'processor the payment gateway request, such as "verisign.jsp"
            ',
   configFile           varchar(255) comment 'the name of the file which is provide the page to config the payment gateway',
   configData           blob comment 'store data for a paymentgateway configuration as a java serialize object
            (Java object : PaymentConfig)',
   gatewayIcon          varchar(255),
   isAdded              smallint comment '0=Not yet added
            1=Already added to payment method
            ',
   type                 char(1) default 'C' comment 'payment gateway type (hold in future use)',
   testModel            char(1) default 'N' comment 'Y==test model
            N==live model',
   paymentGatewayCode   varchar(32) not null,
   version              int not null default 0,
   primary key (paymentGatewayId),
   unique key AK_Key_2 (paymentGatewayCode)
);

/*==============================================================*/
/* Table: PAYMENT_HISTORY                                       */
/*==============================================================*/
create table PAYMENT_HISTORY
(
   paymentHistoryId     int not null auto_increment,
   orderNo              varchar(12) not null,
   flag                 smallint not null comment '0=failure
            1=success',
   flowNo               varchar(64),
   errorMessage         varchar(256),
   errorCode            varchar(32),
   remoteIp             varchar(32),
   receiveData          varchar(1024),
   isBrowsed            smallint default 0 comment '1=has browse
            0=not browse
            ',
   createTime           datetime,
   version              int not null default 0,
   primary key (paymentHistoryId)
);

/*==============================================================*/
/* Table: PAYMENT_METHOD                                        */
/*==============================================================*/
create table PAYMENT_METHOD
(
   paymentMethodId      int not null auto_increment,
   paymentGatewayId     int,
   paymentMethodNameKey varchar(32) not null,
   paymentMethodDetailKey varchar(32),
   paymentMethodType    smallint comment '1=Online
            2=Offline',
   protocol             varchar(8),
   sortOrder            int,
   isCod                smallint not null default 0 comment '0 must pay before shipping, 1 can shipping before pay',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (paymentMethodId)
);

/*==============================================================*/
/* Table: PRODUCT                                               */
/*==============================================================*/
create table PRODUCT
(
   productId            int not null auto_increment,
   departmentId         int,
   manufacturerId       int,
   productCode          varchar(32) not null,
   productNameKey       varchar(32) not null,
   customerRating       varchar(32) comment 'Product rating made by customer',
   merchantRating       varchar(32) comment 'rating by the merchant',
   listPrice            numeric(12,2),
   price                numeric(12,2) not null comment 'normal selling price, when in a promotion, this product can in sold in a lower price',
   minOrderQuantity     int comment 'min quantity of products that must be purchased in one order',
   weight               numeric(12,2) comment 'weight of product',
   length               numeric(12,2) comment 'length of product, optional',
   width                numeric(12,2) comment 'width of product, optional',
   height               numeric(12,2) comment 'height of product, optional',
   shortDescriptionKey  varchar(32) comment 'brief/short product description, this is the i18n text key',
   fullDescriptionKey   varchar(32) comment 'full product description, this is the i18n text key',
   manufacturerProductCode varchar(32),
   status               smallint comment '0=Not published(default status when created, can edit info, but won''t show to the customer and cannot accept order)
            1=Active, Ready for sale
            2=inactive
            3=deleted
            
            10=Preorder (can accept preorder only)',
   membershipId         int comment 'If defined, only customer having specified membership or above (determine by level) can see the category and products under it.',
   metaKeywordKey       varchar(32) comment 'keyword of product, will be output in page meta block, use comma to seperate',
   metaDescriptionkey   varchar(32),
   templatePath         varchar(128),
   productType          smallint comment '0=normal product
            1=product package
            2=product variation',
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   stockQty             int not null comment 'how many items are there in the stock',
   stockMinQuantity     int comment 'min quantity of products in stock, alert when stock level go below this',
   buyCount             int not null,
   categoryPath         varchar(255) not null,
   departmentNamekey    varchar(32),
   manufacturerNameKey  varchar(32),
   thumbnailImageUrl    varchar(255),
   normalImageUrl       varchar(255),
   isMultipleImage      smallint comment 'this product has more than one image or not',
   qtyUnitCode          varchar(32),
   qtyUnitNameKey       varchar(32),
   lengthUnitCode       varchar(32),
   lengthUnitNameKey    varchar(32),
   weightUnitCode       varchar(32),
   weightUnitNameKey    varchar(32),
   attachmentName       varchar(128),
   attachmentUrl        varchar(255),
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (productId),
   unique key AK_Key_2 (productCode),
   unique key AK_Key_Pro_2 (categoryPath)
);

/*==============================================================*/
/* Index: IX_PROD_BUYCOUNT                                      */
/*==============================================================*/
create index IX_PROD_BUYCOUNT on PRODUCT
(
   buyCount
);

/*==============================================================*/
/* Index: IX_PROD_STATUS                                        */
/*==============================================================*/
create index IX_PROD_STATUS on PRODUCT
(
   status,
   deleted,
   productType
);

/*==============================================================*/
/* Table: PRODUCT_ADVERTISEMENT                                 */
/*==============================================================*/
create table PRODUCT_ADVERTISEMENT
(
   productAdvertisementId int not null auto_increment,
   advertisementId      int not null,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)',
   version              int not null default 0,
   primary key (productAdvertisementId)
);

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE                                     */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE
(
   productAttributeId   int not null auto_increment,
   attributeId          int,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)
            can use product/category.categoryPath.startWith(PRODUCT_ATRIBUTE.categoryPath) and tell if this attribute is specified for this product/category
            ',
   isCategory           smallint comment '0=is product
            1=is category',
   isRequired           smallint,
   defaultValue         varchar(128),
   sortOrder            int,
   version              int not null default 0,
   primary key (productAttributeId)
)
comment = "Product/category attribute, attribute are defined using product/category path.";

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE_OPTION                              */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE_OPTION
(
   productAttributeOptionId int not null auto_increment,
   attributeOptionId    int,
   productAttributeId   int,
   categoryPath         varchar(255) not null,
   version              int not null default 0,
   primary key (productAttributeOptionId)
)
comment = "Available attribute options for each product attribute";

/*==============================================================*/
/* Table: PRODUCT_ATTRIBUTE_VALUE                               */
/*==============================================================*/
create table PRODUCT_ATTRIBUTE_VALUE
(
   productAttributeValueId int not null auto_increment,
   attributeId          int,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)
            can use product/category.categoryPath.startWith(PRODUCT_ATRIBUTE.categoryPath) and tell if this attribute is specified for this product/category
            ',
   isCategory           smallint comment '0=is product
            1=is category',
   inputValueKey        varchar(32) not null,
   attributeNameKey     varchar(32),
   primary key (productAttributeValueId)
);

/*==============================================================*/
/* Table: PRODUCT_CATEGORY                                      */
/*==============================================================*/
create table PRODUCT_CATEGORY
(
   productCategoryId    int not null auto_increment,
   categoryId           int not null,
   productId            int not null,
   isMainCategory       smallint comment '0=No
            1=Yes',
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)',
   sortOrder            int,
   version              int not null default 0,
   primary key (productCategoryId),
   unique key AK_KEY_2_PRO_CATE_2 (categoryPath)
)
comment = "A product belongs to which categories, one is master others are slave";

/*==============================================================*/
/* Table: PRODUCT_MEDIA                                         */
/*==============================================================*/
create table PRODUCT_MEDIA
(
   productMediaId       int not null auto_increment,
   mediaId              int,
   productId            int,
   primary key (productMediaId)
)
comment = "Images used by each product";

/*==============================================================*/
/* Table: PRODUCT_PACKAGE_ITEM                                  */
/*==============================================================*/
create table PRODUCT_PACKAGE_ITEM
(
   productPackageItemId int not null auto_increment,
   productId            int not null,
   itemId               int not null,
   version              int not null default 0,
   primary key (productPackageItemId)
)
comment = "A package contains what products.";

/*==============================================================*/
/* Table: PRODUCT_RATING                                        */
/*==============================================================*/
create table PRODUCT_RATING
(
   productRatingId      int not null auto_increment,
   productId            int not null,
   customerId           int not null,
   remoteIp             varchar(32) not null,
   rateValue            int not null,
   ratingTime           datetime not null,
   version              int not null default 0,
   primary key (productRatingId)
);

/*==============================================================*/
/* Index: prod_rate_FK                                          */
/*==============================================================*/
create index prod_rate_FK on PRODUCT_RATING
(
   productId
);

/*==============================================================*/
/* Table: PRODUCT_REVIEW                                        */
/*==============================================================*/
create table PRODUCT_REVIEW
(
   productReviewId      int not null auto_increment,
   productId            int not null,
   customerId           int not null,
   subject              varchar(128) not null,
   message              varchar(1024) not null,
   remoteIp             varchar(32) not null,
   createTime           datetime,
   updateTime           datetime,
   applyGrade           int,
   applyGradeTime       datetime,
   version              int not null default 0,
   primary key (productReviewId)
);

/*==============================================================*/
/* Index: prod_review_FK                                        */
/*==============================================================*/
create index prod_review_FK on PRODUCT_REVIEW
(
   productId
);

/*==============================================================*/
/* Table: PRODUCT_SERIES                                        */
/*==============================================================*/
create table PRODUCT_SERIES
(
   productSeriesId      int not null auto_increment,
   productSeriesNameKey varchar(32) not null,
   seriesCode           varchar(32) not null,
   productSeriesDetailKey varchar(32),
   templatePath         varchar(128),
   status               smallint,
   version              int not null default 0,
   primary key (productSeriesId),
   unique key AK_Key_3 (seriesCode)
)
comment = "A series is several products has something in common, e.g. they are produced by the same author.";

/*==============================================================*/
/* Table: PRODUCT_SERIES_ITEM                                   */
/*==============================================================*/
create table PRODUCT_SERIES_ITEM
(
   productSeriesItemId  int not null auto_increment,
   productSeriesId      int not null,
   productId            int not null,
   categoryPath         varchar(255) not null,
   sortOrder            int,
   version              int not null default 0,
   primary key (productSeriesItemId)
)
comment = "A series contains what products.";

/*==============================================================*/
/* Table: PRODUCT_TAB                                           */
/*==============================================================*/
create table PRODUCT_TAB
(
   productTabId         int not null auto_increment,
   tabId                int not null,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)
            can use product/category.categoryPath.startWith(PRODUCT_TAB.categoryPath) and tell if this tab is specified for this product/category
            
            ',
   isCategory           smallint comment '0=is product
            1=is category',
   sortOrder            int,
   version              int not null default 0,
   primary key (productTabId)
)
comment = "Tabs of each product";

/*==============================================================*/
/* Table: PRODUCT_VARIATION                                     */
/*==============================================================*/
create table PRODUCT_VARIATION
(
   productVariationId   int not null auto_increment,
   sortOrder            int,
   productCode          varchar(32) not null,
   categoryPath         varchar(255) not null,
   price                numeric(12,2),
   weight               numeric(12,2),
   originCategoryPath   varchar(255) not null,
   status               smallint comment '1=active
            2=inactive
            ',
   version              int not null default 0,
   primary key (productVariationId),
   unique key AK_KEY_2_PRO_VAR_1 (productCode)
)
comment = "Variation of product, a variation has product code and selling price, thus also treated as a special product. Product variations has most attributes in common while a few are different.";

/*==============================================================*/
/* Table: PROMOTION                                             */
/*==============================================================*/
create table PROMOTION
(
   promotionId          int not null auto_increment,
   couponTypeId         int,
   titleKey             varchar(32),
   descriptionKey       varchar(32),
   startTime            datetime,
   expireTime           datetime,
   membershipId         int,
   regionId             int,
   promotionType        smallint comment '0:Normal promotion
            1:only used in coupon',
   discountType         smallint not null comment '1=$ off
            2=% off
            3=give a fixed mount of rewarded point
            4=give rewarded points of % amount
            5=give coupon
            6=free shipping
            ',
   minProductQty        int,
   orderSubtotalAmt     numeric(12,2),
   pointsPerAmt         numeric(12,4) comment 'reward how many points per amount of shopping amount
            Sample: Get Points x Per pointsPerAmt of subtotal.',
   discount             numeric(12,2),
   status               smallint,
   priority             int,
   isApplyToAll         smallint comment 'will this rule apply to all product
            0:No
            1:Yes',
   dblDiscount          smallint comment 'is Double Discount Allowed
            0=No
            1=Yes',
   templatePath         varchar(128),
   createBy             int,
   createTime           datetime,
   updateTime           datetime,
   updateBy             int,
   version              int not null default 0,
   primary key (promotionId)
)
comment = "Promotion activities.";

/*==============================================================*/
/* Index: Promotion_Ref                                         */
/*==============================================================*/
create index Promotion_Ref on PROMOTION
(
   membershipId
);

/*==============================================================*/
/* Table: PROMOTION_PRODUCT                                     */
/*==============================================================*/
create table PROMOTION_PRODUCT
(
   promotionProductId   int not null auto_increment,
   promotionId          int,
   sortOrder            int,
   fixedPrice           numeric(12,2) comment 'if defined, the product in this promotion will be sold using this fixed/specified price. this setting has the highest priority.',
   quantity             int,
   categoryPath         varchar(255) not null comment 'unique category/product path, id will be used, and use . to seperate. Product will ends with a P char.
            e.g. 001.002.4321P(however, the id will be used instead of the name)',
   isCategory           smallint comment '0=not catagory
            1=is catagory',
   version              int not null default 0,
   primary key (promotionProductId)
)
comment = "What products are in a promotion";

/*==============================================================*/
/* Table: PUBLISH_HISTORY                                       */
/*==============================================================*/
create table PUBLISH_HISTORY
(
   publishHistoryId     int not null auto_increment,
   publishTime          datetime,
   publishMajorVerion   varchar(8),
   publishMinorVersion  varchar(8),
   version              int not null default 0,
   primary key (publishHistoryId)
);

/*==============================================================*/
/* Table: RECOMMENDED_PRODUCT                                   */
/*==============================================================*/
create table RECOMMENDED_PRODUCT
(
   recommendedProductId int not null auto_increment,
   productId            int not null,
   recommendedTypeId    int not null,
   location             varchar(255) comment 'where should this recommendation take place, i.e. home, cart, product page...;can use comma to seperate more than one location',
   status               smallint,
   sortOrder            int,
   recommendLevel       int comment '1=1 star
            2=2 star
            3=3 star
            4=4 star
            5=5 star',
   locationType         smallint comment 'type of location:
            0:locationId is channelId
            1:locationId is categoryId',
   categoryPath         varchar(255) not null,
   startTime            datetime,
   expireTime           datetime,
   version              int not null default 0,
   primary key (recommendedProductId)
)
comment = "When customer is browsing products, system will recommend other products to him. Can specify different types of recommended products.";

/*==============================================================*/
/* Table: RECOMMENDED_TYPE                                      */
/*==============================================================*/
create table RECOMMENDED_TYPE
(
   recommendedTypeId    int not null auto_increment,
   titleKey             varchar(32),
   typeName             varchar(64) not null,
   ruleCode             int,
   autoEval             smallint not null,
   status               smallint not null,
   isSystem             smallint not null,
   version              int not null default 0,
   primary key (recommendedTypeId),
   unique key AK_Key_2 (typeName)
)
comment = "Types of recommend purpose";

/*==============================================================*/
/* Table: RECOMMENDED_TYPE_LOCATION                             */
/*==============================================================*/
create table RECOMMENDED_TYPE_LOCATION
(
   typeLocationId       int not null auto_increment,
   recommendedTypeId    int not null,
   location             varchar(255) not null,
   version              int not null default 0,
   primary key (typeLocationId)
);

/*==============================================================*/
/* Table: REGION                                                */
/*==============================================================*/
create table REGION
(
   regionId             int not null auto_increment,
   regionCode           varchar(8) not null comment 'code for country, state, city, or input by admin when defining custom region',
   regionType           smallint comment 'Types of region:
            1:Country
            2:State/Province
            3:City
            4:Custom',
   regionNameKey        varchar(32) comment 'i18n region name key',
   zipCode              varchar(6) comment 'some region/city can define zip code',
   telephoneCode        varchar(8) comment 'telephone zone code for country, state, city',
   regionIcon           varchar(256),
   priority             int comment 'when more thant one region found to be match, higher priority will be choosed. custom region usually has higher priority.',
   parentRegionId       int not null comment 'Indicate parent region',
   descriptionKey       varchar(32) comment 'i18n description Key',
   status               smallint,
   createBy             int,
   createTime           datetime,
   updateBy             int,
   updateTime           datetime,
   version              int not null default 0,
   primary key (regionId),
   unique key AK_Key_2 (regionCode)
);

/*==============================================================*/
/* Table: REGION_ITEM                                           */
/*==============================================================*/
create table REGION_ITEM
(
   regionItemId         int not null auto_increment,
   regionId             int comment 'referred region id',
   regionItemType       smallint comment '1 address 2 zip 3 region',
   status               smallint,
   version              int not null default 0,
   addressList          varchar(512),
   zipList              varchar(512),
   itemId               int,
   primary key (regionItemId)
);

/*==============================================================*/
/* Table: REPORT                                                */
/*==============================================================*/
create table REPORT
(
   reportId             int not null auto_increment,
   reportFolderId       int,
   title                varchar(128) not null,
   description          varchar(256),
   rptDesignFile        varchar(128),
   url                  varchar(255),
   param                blob,
   isSystem             smallint not null,
   version              int not null default 0,
   primary key (reportId)
);

/*==============================================================*/
/* Table: REPORT_FOLDER                                         */
/*==============================================================*/
create table REPORT_FOLDER
(
   reportFolderId       int not null auto_increment,
   title                varchar(128) not null,
   isSystem             smallint not null,
   version              int not null default 0,
   primary key (reportFolderId)
);

/*==============================================================*/
/* Table: SALES_ORDER                                           */
/*==============================================================*/
create table SALES_ORDER
(
   orderId              int not null auto_increment,
   customerId           int,
   paymentMethodId      int,
   orderNo              varchar(12) not null comment 'OYYDDDNNNN
            1 bit is O
            2-6 bit is date string ,for example 06220
            7-10 bit is increased number,from 0001 to 9999
             
            ',
   membershipId         int,
   customerTitle        varchar(8) not null,
   customerFirstName    varchar(32) not null,
   customerLastname     varchar(32) not null,
   customerTelephone    varchar(32) not null,
   customerFax          varchar(32),
   customerEmail        varchar(64) not null,
   customerZip          varchar(6),
   customerCountry      varchar(64),
   customerState        varchar(64),
   customerCity         varchar(64),
   customerAddress      varchar(128),
   customerAddress1     varchar(128),
   shippingTitle        varchar(8) not null,
   shippingFirstname    varchar(32) not null,
   shippingLastname     varchar(32) not null,
   shippingTelephone    varchar(32) not null,
   shippingFax          varchar(32),
   shippingCountry      varchar(64) not null,
   shippingState        varchar(64),
   shippingCity         varchar(64),
   shippingZip          varchar(6),
   shippingAddress      varchar(128) not null,
   shippingAddress1     varchar(128),
   billingTitle         varchar(8),
   billingFirstname     varchar(32),
   billingLastname      varchar(32),
   billingCountry       varchar(64),
   billingState         varchar(64),
   billingCity          varchar(64),
   billingZip           varchar(6),
   billingAddress       varchar(128),
   billingAddress1      varchar(128),
   companyName          varchar(128),
   wrapTotalCost        numeric(12,2) comment 'total cost of all items'' wrapping',
   shippingTotalCost    numeric(12,2) comment 'total shipping cost',
   taxAmt               numeric(12,2) comment 'total amount of tax',
   taxApplied           varchar(256) comment 'all tax types that applied in this order, use comma to seperate',
   couponDiscountAmt    numeric(12,2) comment 'Diccounted amount because of coupon',
   giftCertficateAmt    numeric(12,2) comment 'gift Certficate Amount used in this order',
   saveMoneyTotal       numeric(12,2) comment 'total discount Amount',
   subtotal             numeric(12,2) comment 'subtotal=standardTotalPrice-saveMoneyTotal-
            couponDiscountAmt
            ',
   standardTotalPrice   numeric(12,2) not null,
   total                numeric(12,2) not null comment 'total=subtotal+wrapTotalCost+shippingTotalCost+taxAmt
            ',
   shouldPay            numeric(12,2) not null,
   orderStatus          smallint not null comment '10 placed,20 in progress,30 complete,40 cancel',
   paymentStatus        smallint comment '10 not pay,20 all pay',
   shippingStatus       smallint comment '10 unsettled,20 stock up,30 shipping,40 received',
   itemsCount           int comment 'items count',
   notes                varchar(1024) comment 'user made extra notes telling how to process the order,etc.',
   shippingCountryId    int,
   shippingStateId      int,
   shippingCityId       int,
   shippingCustomId     int,
   needBilling          smallint not null comment '1 need 0 not need not 
            
             
            ',
   createTime           datetime not null,
   createBy             int,
   updateTime           datetime,
   updateBy             int,
   version              int not null default 0,
   isCod                smallint not null default 0 comment '0 must pay before shipping, 1 can shipping before pay',
   paymentType          smallint default 0 comment '1=Online
            2=Offline
            3=Gift Certificate',
   primary key (orderId),
   unique key AK_Key_2 (orderNo)
);

/*==============================================================*/
/* Table: SAVED_PRODUCT_LIST                                    */
/*==============================================================*/
create table SAVED_PRODUCT_LIST
(
   savedProductListId   int not null auto_increment,
   customerId           int not null,
   productId            int not null,
   price                numeric(12,2) not null comment 'the price when saved',
   addTime              datetime not null,
   version              int not null default 0,
   primary key (savedProductListId)
);

/*==============================================================*/
/* Table: SEARCH_KEYWORD                                        */
/*==============================================================*/
create table SEARCH_KEYWORD
(
   searchKeywordId      int not null auto_increment,
   searchKeyword        varchar(128),
   times                int,
   version              int not null default 0,
   primary key (searchKeywordId)
)
comment = "Collect user entered search keywords and make searching suggestion to user when possible";

/*==============================================================*/
/* Table: SEARCH_WORD                                           */
/*==============================================================*/
create table SEARCH_WORD
(
   searchWordId         int not null auto_increment,
   searchWord           varchar(512) not null,
   updateTime           datetime,
   searchTimes          int,
   version              int not null default 0,
   primary key (searchWordId)
);

/*==============================================================*/
/* Table: SEE_ALSO                                              */
/*==============================================================*/
create table SEE_ALSO
(
   seeAlsoId            int not null auto_increment,
   seeAlsoUrlId         int not null,
   systemUrlId          int not null,
   isSystem             smallint not null,
   version              int not null default 0,
   primary key (seeAlsoId)
)
comment = "Define see also links for each admin page";

/*==============================================================*/
/* Table: SHIPPING_GATEWAY                                      */
/*==============================================================*/
create table SHIPPING_GATEWAY
(
   shippingGatewayId    int not null auto_increment,
   shippingGatewayName  varchar(32) not null,
   templateName         varchar(32),
   shippingGatewayDescription varchar(512),
   status               smallint comment '1=active
            2=inactive
            
            ',
   version              int not null default 0,
   primary key (shippingGatewayId)
);

/*==============================================================*/
/* Table: SHIPPING_METHOD                                       */
/*==============================================================*/
create table SHIPPING_METHOD
(
   shippingMethodId     int not null auto_increment,
   carrierId            int comment 'the id of the carrier company',
   shippingGatewayId    int,
   shippingMethodNameKey varchar(32) not null,
   shippingMethodDetailKey varchar(32),
   isDomestic           smallint comment '0=No (is international)
            1=Yes (is domestic)
            ',
   deliveryTimeKey      varchar(32) comment 'i18n delivery time description',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   isCod                smallint,
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (shippingMethodId)
);

/*==============================================================*/
/* Table: SHIPPING_RATE                                         */
/*==============================================================*/
create table SHIPPING_RATE
(
   shippingRateId       int not null auto_increment,
   shippingMethodId     int,
   regionId             int,
   baseOn               int not null comment '0=weight
            1=volumn/size
            2=quantity
            3=.......',
   isFlat               smallint not null comment '0=No(calc by %)
            1=Yes',
   isRoundUpValue       smallint comment '0=No (No round up)
            1=Yes',
   basePrice            numeric(12,2) comment 'base price of this shipping rate',
   maxWeight            numeric(12,2),
   maxVolume            numeric(12,2),
   baseWeight           numeric(12,2),
   baseVolume           numeric(12,2),
   weightPerRate        numeric(12,2),
   volumePerRate        numeric(12,2),
   volumePerFee         numeric(12,2),
   increaseUnit         numeric(12,2) comment 'quantity of counting unit that need to re-calc',
   metricUnitCode       varchar(32),
   maxItem              int,
   itemPerRate          numeric(12,2),
   deleted              smallint not null default 0,
   version              int not null default 0,
   primary key (shippingRateId)
);

/*==============================================================*/
/* Table: SHOPPINGCART                                          */
/*==============================================================*/
create table SHOPPINGCART
(
   shoppingCartId       int not null auto_increment,
   customerId           int,
   shippingAddressId    int,
   billingAddressId     int,
   wrapTotalCost        numeric(12,2) default 0.0 comment 'total cost of all items'' wrapping',
   shippingTotalCost    numeric(12,2) default 0.0 comment 'total shipping cost',
   taxAmt               numeric(12,2) default 0.0 comment 'total amount of tax',
   taxApplied           varchar(256) comment 'all tax types that applied in this order, use comma to seperate',
   couponDiscountAmt    numeric(12,2) default 0.0 comment 'Diccounted amount because of coupon',
   saveMoneyTotal       numeric(12,2) default 0.0 comment 'total discounted amount',
   subtotal             numeric(12,2) default 0.0 comment 'subtotal=standardTotalPrice-saveMoneyTotal-
            couponDiscountAmt
            ',
   total                numeric(12,2) default 0.0 comment 'total=subtotal+wrapTotalCost+shippingTotalCost+taxAmt
            ',
   status               smallint default 1 comment '1=active
            2=inactive
            3=deleted
            ',
   notes                varchar(1024) comment 'Customer made extra notes about the order',
   shippingCountryId    int,
   shippingStateId      int,
   shippingCityId       int,
   shippingCustomId     int,
   itemsCount           int comment 'items count',
   buyNowItemsCount     int,
   buyLaterItemsCount   int,
   standardTotalPrice   numeric(12,2) default 0.0 comment 'total amount before any discount
            =sum(product.price*qty)',
   hasUpdateCookie      smallint default 0,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (shoppingCartId)
);

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM                                     */
/*==============================================================*/
create table SHOPPINGCART_ITEM
(
   shoppingCartItemId   int not null auto_increment,
   productId            int comment 'for certificate, it''s nullable',
   shoppingCartId       int not null,
   quantity             int not null,
   price                numeric(12,2) not null comment 'sales price of this product when saved',
   addTime              datetime not null,
   isSaved              smallint comment '0=not is save for later
            1=is save for later',
   discountPrice        numeric(12,2) comment 'price after discounted',
   hasUnitedPmt         smallint comment 'has united promotion',
   isFreeShipping       smallint comment '0=No
            1=Yes',
   isExempt             smallint comment '0=No
            1=Yes',
   wrapId               int comment 'wrap type id',
   wrapCost             numeric(12,2) comment 'the price of this type of wrapping',
   wrapNote             varchar(1024) comment 'notes that should be written on the wrap',
   itemType             smallint comment 'types of items/goods:
            1:product (default)
            2:gift certificate
            3:service
            4:software
            5:document
            6:music
            7:video
            8:others',
   isSpecialOffer       smallint,
   saveMoney            numeric(12,2),
   productAttributes    blob,
   categoryPath         varchar(255) not null,
   version              int not null default 0,
   primary key (shoppingCartItemId)
);

/*==============================================================*/
/* Table: SHOPPINGCART_ITEM_PROMOTION                           */
/*==============================================================*/
create table SHOPPINGCART_ITEM_PROMOTION
(
   shoppingCartItemPromotionId int not null auto_increment,
   shoppingCartItemId   int,
   promotionId          int,
   titleKey             varchar(32),
   descriptionKey       varchar(32),
   saveMoney            numeric(12,2),
   validFlag            smallint comment '0=is not valid
            1=is valid',
   isUnited             smallint comment '0=is not united
            1=is united',
   version              int not null default 0,
   primary key (shoppingCartItemPromotionId)
)
comment = "
           ";

/*==============================================================*/
/* Table: SHOP_POINT_HISTORY                                    */
/*==============================================================*/
create table SHOP_POINT_HISTORY
(
   shopPointHistoryId   int not null auto_increment,
   customerId           int,
   points               int comment 'how many rewarded points',
   pointType            smallint comment '0=Shopping
            1=Promotion
            2=Remarks',
   gainTime             datetime comment 'the time that the points are gained/received',
   version              int not null default 0,
   primary key (shopPointHistoryId)
)
comment = "shop points gain history";

/*==============================================================*/
/* Table: SHOP_POINT_USED_HISTORY                               */
/*==============================================================*/
create table SHOP_POINT_USED_HISTORY
(
   shopPointUsedHistoryId int not null auto_increment,
   customerId           int,
   points               int,
   usedTime             datetime,
   description          varchar(512),
   version              int not null default 0,
   primary key (shopPointUsedHistoryId)
)
comment = "shop points use history";

/*==============================================================*/
/* Table: SORT_ORDER                                            */
/*==============================================================*/
create table SORT_ORDER
(
   sortOrderId          int not null auto_increment,
   sortOrderCode        varchar(32) not null,
   maxOrderValue        int not null,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (sortOrderId)
);

/*==============================================================*/
/* Index: IX_SORT_ORDER_CODE                                    */
/*==============================================================*/
create index IX_SORT_ORDER_CODE on SORT_ORDER
(
   sortOrderCode
);

/*==============================================================*/
/* Table: SPEED_BAR                                             */
/*==============================================================*/
create table SPEED_BAR
(
   speedBarId           int not null auto_increment,
   mediaId              int,
   speedBarImage        varchar(255),
   url                  varchar(255) not null,
   target               varchar(32),
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (speedBarId)
);

/*==============================================================*/
/* Table: STATICALLIZE_HISTORY                                  */
/*==============================================================*/
create table STATICALLIZE_HISTORY
(
   staticallizeHistoryId int not null auto_increment,
   version              int not null default 0,
   primary key (staticallizeHistoryId)
);

/*==============================================================*/
/* Table: STATICALLIZE_ITEM                                     */
/*==============================================================*/
create table STATICALLIZE_ITEM
(
   staticallizeItemId   int not null auto_increment,
   version              int not null default 0,
   primary key (staticallizeItemId)
);

/*==============================================================*/
/* Table: STATIC_PAGE                                           */
/*==============================================================*/
create table STATIC_PAGE
(
   staticPageId         int not null auto_increment,
   staticPageTitleKey   varchar(32) not null,
   staticPageContentKey varchar(32) not null,
   staticPagePath       varchar(128) not null,
   publishLocation      varchar(32) not null,
   templateName         varchar(32),
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (staticPageId)
);

/*==============================================================*/
/* Table: SURVEY                                                */
/*==============================================================*/
create table SURVEY
(
   surveyId             int not null auto_increment,
   surveyTitleKey       varchar(32) not null,
   startTime            datetime,
   expireTime           datetime,
   times                int,
   sortOrder            int,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (surveyId)
);

/*==============================================================*/
/* Table: SURVEY_OPTION                                         */
/*==============================================================*/
create table SURVEY_OPTION
(
   surveyOptionId       int not null auto_increment,
   surveyId             int not null,
   surveyOptionTitleKey varchar(32) not null,
   sortOrder            int,
   version              int not null default 0,
   primary key (surveyOptionId)
);

/*==============================================================*/
/* Table: SURVEY_RESULT                                         */
/*==============================================================*/
create table SURVEY_RESULT
(
   surveyResultId       int not null auto_increment,
   surveyId             int not null,
   surveyOptionId       int not null,
   remoteIp             varchar(32) not null,
   version              int not null default 0,
   primary key (surveyResultId)
);

/*==============================================================*/
/* Table: SYSTEM_CONFIG                                         */
/*==============================================================*/
create table SYSTEM_CONFIG
(
   configId             int not null auto_increment,
   configKey            varchar(64) not null,
   description          varchar(512),
   category             varchar(32),
   configType           smallint,
   configValue          varchar(1024) not null,
   options              varchar(255),
   dataType             smallint,
   isSystem             smallint comment 'system parameter, use can not modify.',
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   version              int not null default 0,
   primary key (configId),
   unique key AK_Key_2 (configKey)
);

/*==============================================================*/
/* Table: SYSTEM_LANGUAGE                                       */
/*==============================================================*/
create table SYSTEM_LANGUAGE
(
   languageId           int not null auto_increment,
   languageNameKey      varchar(32) not null,
   localeCode           varchar(8) not null,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   isDefault            smallint,
   sortOrder            int,
   version              int not null default 0,
   primary key (languageId),
   unique key AK_Key_2 (localeCode)
)
comment = "Supported languages";

/*==============================================================*/
/* Table: SYSTEM_MESSAGE                                        */
/*==============================================================*/
create table SYSTEM_MESSAGE
(
   systemMessageId      int not null auto_increment,
   messageHtml          varchar(256) not null,
   messageType          smallint,
   status               smallint,
   appuserId            int comment '0=all',
   createTime           datetime not null,
   primary key (systemMessageId)
);

/*==============================================================*/
/* Table: SYSTEM_MODULE                                         */
/*==============================================================*/
create table SYSTEM_MODULE
(
   moduleId             int not null auto_increment,
   moduleName           varchar(32),
   moduleDetail         varchar(255),
   status               smallint,
   version              int not null default 0,
   primary key (moduleId)
)
comment = "Installed moduled";

/*==============================================================*/
/* Table: SYSTEM_URL                                            */
/*==============================================================*/
create table SYSTEM_URL
(
   urlId                int not null auto_increment,
   urlName              varchar(32) not null,
   title                varchar(128) not null,
   url                  varchar(255),
   description          varchar(128),
   menuLevel            smallint not null comment 'base from 1. 1 is topMenu.and the subItem is 2, 3 ...',
   parentId             int,
   version              int not null default 0,
   primary key (urlId)
);

/*==============================================================*/
/* Table: TAB                                                   */
/*==============================================================*/
create table TAB
(
   tabId                int not null auto_increment,
   tabImage             varchar(128),
   tabNameKey           varchar(32) not null,
   tabTipKey            varchar(32),
   status               smallint,
   version              int not null default 0,
   templateName         varchar(32),
   primary key (tabId),
   unique key AK_Key_2 (tabNameKey)
)
comment = "Global Tabs used in the application";

/*==============================================================*/
/* Table: TAX                                                   */
/*==============================================================*/
create table TAX
(
   taxId                int not null auto_increment,
   taxNameKey           varchar(32) not null,
   applyAddressType     smallint not null comment 'apply on which type of address
            0=shipping address
            1=bill address',
   priority             int,
   priceIncludesTax     smallint comment 'does product price includes tax
            0=No
            1=Yes',
   displayTaxedPrice    smallint comment '0=display price after taxed
            1=display price before taxed',
   taxRegisterNo        varchar(64) comment 'registered no of tax',
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   createBy             int,
   updateBy             int,
   createTime           datetime not null,
   updateTime           datetime,
   version              int not null default 0,
   primary key (taxId),
   unique key AK_Key_2 (taxNameKey)
)
comment = "Types of tax";

/*==============================================================*/
/* Table: TAX_RATE                                              */
/*==============================================================*/
create table TAX_RATE
(
   taxRateId            int not null auto_increment,
   categoryId           int not null,
   taxId                int,
   regionId             int,
   formula              varchar(255),
   rateValue            numeric(12,2),
   rateType             smallint comment '0=%
            1=Specified',
   version              int not null default 0,
   primary key (taxRateId)
)
comment = "Tax rate for each category or state";

/*==============================================================*/
/* Table: TEMPLATE                                              */
/*==============================================================*/
create table TEMPLATE
(
   templateId           int not null auto_increment,
   templateSetId        int not null,
   templateName         varchar(64) not null comment 'unique template name',
   templateType         varchar(128) not null,
   templatePath         varchar(512) not null,
   isDefault            smallint comment '0=is not default
            1=is default',
   createBy             int,
   updateBy             int,
   createTime           datetime,
   updateTime           datetime,
   localeCode           varchar(32),
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (templateId)
)
comment = "Velocity templates";

/*==============================================================*/
/* Table: TEMPLATE_SET                                          */
/*==============================================================*/
create table TEMPLATE_SET
(
   templateSetId        int not null auto_increment,
   templateSetNameKey   varchar(32) not null,
   templateSetPath      varchar(128) not null,
   isDefault            smallint comment '0=not is default
            1=is default',
   updateBy             int,
   createBy             int,
   createTime           datetime,
   updateTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   version              int not null default 0,
   primary key (templateSetId),
   unique key AK_Key_2 (templateSetNameKey)
)
comment = "Treat a set of templates as a whole and can manage more conveniently";

/*==============================================================*/
/* Table: URL_VISITED                                           */
/*==============================================================*/
create table URL_VISITED
(
   urlVisitedId         int not null auto_increment,
   cookieId             varchar(128) comment 'Client Cookie Id ,which identify current client user',
   url                  varchar(255) comment 'Page url user visited',
   visitedTime          datetime,
   timeInterval         int comment 'time interval user stay in this url page ,by seconds',
   clickCounts          int comment 'times user click this page',
   primary key (urlVisitedId)
);

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE
(
   userRoleId           int not null auto_increment,
   appuserId            int,
   roleId               int,
   version              int not null default 0,
   primary key (userRoleId)
);

/*==============================================================*/
/* Table: VALIDATION_SESSION                                    */
/*==============================================================*/
create table VALIDATION_SESSION
(
   validationSessionId  int not null auto_increment,
   url                  varchar(255) not null,
   expiredDate          datetime not null,
   email                varchar(64) not null,
   vsType               smallint not null default 0 comment '0=for password recorver 
            1=for account activate',
   primary key (validationSessionId)
);

/*==============================================================*/
/* Table: VARIATION_ITEM                                        */
/*==============================================================*/
create table VARIATION_ITEM
(
   variationItemId      int not null auto_increment,
   productVariationId   int not null,
   attributeOptionId    int not null,
   version              int not null default 0,
   primary key (variationItemId)
)
comment = "What attributes combination make up a variation";

/*==============================================================*/
/* Table: VOTE                                                  */
/*==============================================================*/
create table VOTE
(
   voteId               int not null auto_increment,
   voteTitleKey         varchar(32) not null,
   beginTime            datetime not null,
   expireTime           datetime,
   status               smallint comment '1=active
            2=inactive
            3=deleted
            ',
   sortOrder            int,
   version              int not null default 0,
   primary key (voteId)
)
comment = "Pending Develop for requirement not confirm";

/*==============================================================*/
/* Table: VOTE_OPTION                                           */
/*==============================================================*/
create table VOTE_OPTION
(
   voteOptionId         int not null auto_increment,
   voteId               int not null,
   voteOptionTitleKey   varchar(32) not null,
   times                int,
   sortOrder            int,
   version              int not null default 0,
   primary key (voteOptionId)
)
comment = "Pending Develop for requirement not confirm";

/*==============================================================*/
/* Table: VOTE_RESULT                                           */
/*==============================================================*/
create table VOTE_RESULT
(
   voteResultId         int not null auto_increment,
   voteId               int not null,
   voteOptionId         int not null,
   remoteIp             varchar(32) not null,
   version              int not null default 0,
   primary key (voteResultId)
)
comment = "Pending Develop for requirement not confirm";

/*==============================================================*/
/* Table: WISHLIST                                              */
/*==============================================================*/
create table WISHLIST
(
   wishListId           int not null auto_increment,
   customerId           int not null,
   shippingAddressId    int,
   wishListTitle        varchar(128) not null,
   wishListType         smallint not null comment '0=Normal
            1=Event base',
   wishListDetail       varchar(512),
   shareLevel           int comment 'who can read this whishlist
            0=any
            1=owner
            2=visited some person???
            ',
   isDefault            smallint comment '0=not default
            1=is default',
   recipientEmailList   varchar(512) comment 'define who can view this wish list, use comma to seperate the email',
   eventDate            datetime,
   priority             int,
   createTime           datetime not null,
   version              int not null default 0,
   primary key (wishListId)
);

/*==============================================================*/
/* Table: WISHLIST_ITEM                                         */
/*==============================================================*/
create table WISHLIST_ITEM
(
   wishlistItemId       int not null auto_increment,
   wishListId           int not null,
   productId            int,
   quantity             int comment 'how many items wished to have',
   purchasedQty         int comment 'how many this type of item already purchased',
   shortDescription     varchar(512),
   productAttributes    blob,
   createTime           datetime,
   version              int not null default 0,
   primary key (wishlistItemId)
);

/*==============================================================*/
/* Table: WRAP                                                  */
/*==============================================================*/
create table WRAP
(
   wrapId               int not null auto_increment,
   defaultPrice         numeric(12,2),
   descriptionKey       varchar(32),
   version              int not null default 0,
   primary key (wrapId)
)
comment = "Gift wrapping options";

alter table ADDRESS add constraint FK_Reference_156 foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table ADVERTISEMENT add constraint FK_Reference_124 foreign key (promotionId)
      references PROMOTION (promotionId) on delete restrict on update restrict;

alter table ADVERTISEMENT add constraint FK_AD_ADVERTIS foreign key (advertisementTypeId)
      references ADVERTISEMENT_TYPE (advertisementTypeId) on delete restrict on update restrict;

alter table ADVERTISEMENT_POSITION add constraint FK_ADV_ADVERTIS foreign key (advertisementId)
      references ADVERTISEMENT (advertisementId) on delete restrict on update restrict;

alter table ADVERTISEMENT_POSITION add constraint FK_ADVE_ADVERTIZ foreign key (positionTypeId)
      references AD_POSITION_TYPE (positionTypeId) on delete restrict on update restrict;

alter table ALSO_BUY add constraint FK_ALSO_BUY_PR foreign key (alsoProductId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table APP_ADMIN add constraint FK_Reference_174 foreign key (appAdminId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table APP_MEDIA add constraint FK_Reference_139 foreign key (mediaTypeId)
      references MEDIA_TYPE (mediaTypeId) on delete restrict on update restrict;

alter table ATTRIBUTE_OPTION add constraint FK_ATT_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete restrict on update restrict;

alter table CATEGORY_CATEGORY add constraint CATEGORY_CATEGORY6 foreign key (subCategoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table CATEGORY_CATEGORY add constraint FK_CA_CATEG foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table CATEGORY_SHIPPING_RATE add constraint FK_CA_SHIPPING foreign key (shippingRateId)
      references SHIPPING_RATE (shippingRateId) on delete restrict on update restrict;

alter table COUPON add constraint FK_Reference_125 foreign key (couponTypeId)
      references COUPON_TYPE (couponTypeId) on delete restrict on update restrict;

alter table COUPON_TYPE add constraint FK_Reference_127 foreign key (promotionId)
      references PROMOTION (promotionId) on delete restrict on update restrict;

alter table CREDIT_CARD add constraint FK_CR_C_REF_CR_C foreign key (creditCardTypeId)
      references CREDIT_CARD_TYPE (creditCardTypeId) on delete restrict on update restrict;

alter table CREDIT_CARD add constraint FK_CRE_C_RE_CUST foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table CUSTOMER add constraint FK_Reference_138 foreign key (customerId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table CUSTOMER add constraint FK_Reference_164 foreign key (membershipId)
      references MEMBERSHIP (membershipId) on delete restrict on update restrict;

alter table CUSTOMER_MESSAGE_LIST add constraint FK_CUST_MESSAGE_ foreign key (messageListId)
      references MESSAGE_LIST (messageListId) on delete restrict on update restrict;

alter table CUSTOMER_PREFERENCE add constraint FK_CUST_CUST foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table EXPORT_MAPPING_ITEM add constraint FK_EXP_M_REF_EX_M foreign key (exportMappingId)
      references EXPORT_MAPPING (exportMappingId) on delete restrict on update restrict;

alter table FAQ add constraint FK_FAQ_FAQ_CATE foreign key (faqCategoryId)
      references FAQ_CATEGORY (faqCategoryId) on delete restrict on update restrict;

alter table FAVORITE add constraint FK_FAVO_CUSTOMER foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table FEEDBACK add constraint FK_Reference_133 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table FEEDBACK add constraint FK_Reference_167 foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table IMPORT_MAPPING_ITEM add constraint FK_IMP_M_REF_IMP_M foreign key (importMappingId)
      references IMPORT_MAPPING (importMappingId) on delete restrict on update restrict;

alter table MANUFACTURER add constraint FK_Reference_150 foreign key (mediaId)
      references APP_MEDIA (mediaId) on delete restrict on update restrict;

alter table MENU_MENU add constraint FK_MENU_ME2 foreign key (menuId)
      references MENU (menuId) on delete restrict on update restrict;

alter table MENU_MENU add constraint FK_MENU_MENU foreign key (subMenuId)
      references MENU (menuId) on delete restrict on update restrict;

alter table MESSAGE add constraint FK_MES_PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table MESSAGE add constraint FK_MES_CATEGORY foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table MESSAGE_LIST_ITEM add constraint FK_ME_MESS foreign key (messageId)
      references MESSAGE (messageId) on delete restrict on update restrict;

alter table MESSAGE_LIST_ITEM add constraint FK_MES_MESSAGE_ foreign key (messageListId)
      references MESSAGE_LIST (messageListId) on delete restrict on update restrict;

alter table METRIC_UNIT add constraint FK_Ref_Metric_Type_Unit foreign key (metricTypeId)
      references METRIC_TYPE (metricTypeId) on delete restrict on update restrict;

alter table ORDER_COUPON add constraint FK_ORD_CO_RE_O foreign key (orderId)
      references SALES_ORDER (orderId) on delete restrict on update restrict;

alter table ORDER_GIFT_CERTIFICATE add constraint FK_OR_GI_SAL_OR foreign key (orderId)
      references SALES_ORDER (orderId) on delete restrict on update restrict;

alter table ORDER_GIFT_CERTIFICATE add constraint FK_OR_GI_GIFT_CER foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId) on delete restrict on update restrict;

alter table ORDER_ITEM add constraint FK_Reference_122 foreign key (giftCertificateId)
      references GIFT_CERTIFICATE (giftCertificateId) on delete restrict on update restrict;

alter table ORDER_ITEM add constraint FK_OR_PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table ORDER_ITEM add constraint FK_ORD_SALES_OR2 foreign key (orderId)
      references SALES_ORDER (orderId) on delete restrict on update restrict;

alter table ORDER_ITEM_ATTRIBUTE add constraint FK_ORD_IT_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete restrict on update restrict;

alter table ORDER_ITEM_ATTRIBUTE add constraint FK_ORD_IT_R_OR_IT foreign key (orderItemId)
      references ORDER_ITEM (orderItemId) on delete restrict on update restrict;

alter table ORDER_ITEM_PROMOTION add constraint FK_Reference_134 foreign key (orderItemId)
      references ORDER_ITEM (orderItemId) on delete restrict on update restrict;

alter table ORDER_PAYMENT add constraint FK_OR_PA_SALES_OR foreign key (orderId)
      references SALES_ORDER (orderId) on delete restrict on update restrict;

alter table ORDER_PAYMENT add constraint FK_OR_PA_REE_PAY foreign key (paymentMehodId)
      references PAYMENT_METHOD (paymentMethodId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_Reference_172 foreign key (shippingRateId)
      references SHIPPING_RATE (shippingRateId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT add constraint FK_ORD_SALES_OR foreign key (orderId)
      references SALES_ORDER (orderId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT_ITEM add constraint FK_OR_ORDER_SH foreign key (orderShipmentId)
      references ORDER_SHIPMENT (orderShipmentId) on delete restrict on update restrict;

alter table ORDER_SHIPMENT_ITEM add constraint FK_OR_SH_OR_IT foreign key (orderItemId)
      references ORDER_ITEM (orderItemId) on delete restrict on update restrict;

alter table PAYMENT_METHOD add constraint FK_PAY_PAYMENT_2 foreign key (paymentGatewayId)
      references PAYMENT_GATEWAY (paymentGatewayId) on delete restrict on update restrict;

alter table PRODUCT add constraint FK_PRO_MANUFACT foreign key (manufacturerId)
      references MANUFACTURER (manufacturerId) on delete restrict on update restrict;

alter table PRODUCT add constraint FK_PRO_DEPARTME foreign key (departmentId)
      references DEPARTMENT (departmentId) on delete restrict on update restrict;

alter table PRODUCT_ADVERTISEMENT add constraint FK_PRO_ADVERTIS foreign key (advertisementId)
      references ADVERTISEMENT (advertisementId) on delete restrict on update restrict;

alter table PRODUCT_ATTRIBUTE add constraint FK_PR_ATTR foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete restrict on update restrict;

alter table PRODUCT_ATTRIBUTE_OPTION add constraint FK_PRO_ATTRI foreign key (attributeOptionId)
      references ATTRIBUTE_OPTION (attributeOptionId) on delete restrict on update restrict;

alter table PRODUCT_ATTRIBUTE_OPTION add constraint FK_PRO_PRODUCT_ foreign key (productAttributeId)
      references PRODUCT_ATTRIBUTE (productAttributeId) on delete restrict on update restrict;

alter table PRODUCT_ATTRIBUTE_VALUE add constraint FK_Reference_175 foreign key (attributeId)
      references ATTRIBUTE (attributeId) on delete restrict on update restrict;

alter table PRODUCT_CATEGORY add constraint FK_PROD_PRO foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_CATEGORY add constraint FK_PRO_CATEG foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table PRODUCT_MEDIA add constraint FK_Reference_140 foreign key (mediaId)
      references APP_MEDIA (mediaId) on delete restrict on update restrict;

alter table PRODUCT_MEDIA add constraint FK_Reference_151 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_PACKAGE_ITEM add constraint FK_REF_PRO_PACKAGE_ITEM foreign key (itemId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_PACKAGE_ITEM add constraint FK_PR_PROD foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_RATING add constraint FK_PRO__PROD4 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_RATING add constraint FK_PROD_CUST2 foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table PRODUCT_REVIEW add constraint FK_PROD_PROD5 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_REVIEW add constraint FK_PRO_CUSTO foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table PRODUCT_SERIES_ITEM add constraint FK_PROD_prod2 foreign key (productSeriesId)
      references PRODUCT_SERIES (productSeriesId) on delete restrict on update restrict;

alter table PRODUCT_SERIES_ITEM add constraint FK_PROD_PROD10 foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table PRODUCT_TAB add constraint FK_PROD_TAB foreign key (tabId)
      references TAB (tabId) on delete restrict on update restrict;

alter table PROMOTION add constraint FK_Reference_118 foreign key (regionId)
      references REGION (regionId) on delete restrict on update restrict;

alter table PROMOTION add constraint FK_Reference_128 foreign key (membershipId)
      references MEMBERSHIP (membershipId) on delete restrict on update restrict;

alter table PROMOTION_PRODUCT add constraint FK_Reference_123 foreign key (promotionId)
      references PROMOTION (promotionId) on delete restrict on update restrict;

alter table RECOMMENDED_PRODUCT add constraint FK_REC_PRO foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table RECOMMENDED_PRODUCT add constraint FK_RECO_RECOMMEN foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId) on delete restrict on update restrict;

alter table RECOMMENDED_TYPE_LOCATION add constraint FK_Reference_176 foreign key (recommendedTypeId)
      references RECOMMENDED_TYPE (recommendedTypeId) on delete restrict on update restrict;

alter table REGION_ITEM add constraint FK_Reference_116 foreign key (regionId)
      references REGION (regionId) on delete restrict on update restrict;

alter table REPORT add constraint FK_Reference_1 foreign key (reportFolderId)
      references REPORT_FOLDER (reportFolderId) on delete restrict on update restrict;

alter table SALES_ORDER add constraint FK_Reference_154 foreign key (paymentMethodId)
      references PAYMENT_METHOD (paymentMethodId) on delete restrict on update restrict;

alter table SAVED_PRODUCT_LIST add constraint FK_SAVE_PRO_CUS foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table SAVED_PRODUCT_LIST add constraint FK_SAVE_PRO_PRO foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_SEE_ALSO_REF_SYSTEM_1 foreign key (seeAlsoUrlId)
      references SYSTEM_URL (urlId) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_Reference_129 foreign key (systemUrlId)
      references SYSTEM_URL (urlId) on delete restrict on update restrict;

alter table SHIPPING_METHOD add constraint FK_SHIP_CARRIER foreign key (carrierId)
      references CARRIER (carrierId) on delete restrict on update restrict;

alter table SHIPPING_METHOD add constraint FK_SHIPE_SHIPPING foreign key (shippingGatewayId)
      references SHIPPING_GATEWAY (shippingGatewayId) on delete restrict on update restrict;

alter table SHIPPING_RATE add constraint FK_Reference_132 foreign key (regionId)
      references REGION (regionId) on delete restrict on update restrict;

alter table SHIPPING_RATE add constraint FK_SHIP_SHIPPING foreign key (shippingMethodId)
      references SHIPPING_METHOD (shippingMethodId) on delete restrict on update restrict;

alter table SHOPPINGCART add constraint FK_CART_REF_ADDR_1 foreign key (shippingAddressId)
      references ADDRESS (addressId) on delete restrict on update restrict;

alter table SHOPPINGCART add constraint FK_CART_REF_ADDR_2 foreign key (billingAddressId)
      references ADDRESS (addressId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_SHO_PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM add constraint FK_SHO_SHO foreign key (shoppingCartId)
      references SHOPPINGCART (shoppingCartId) on delete restrict on update restrict;

alter table SHOPPINGCART_ITEM_PROMOTION add constraint FK_Reference_170 foreign key (shoppingCartItemId)
      references SHOPPINGCART_ITEM (shoppingCartItemId) on delete restrict on update restrict;

alter table SHOP_POINT_HISTORY add constraint FK_SHOP_CUST2 foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table SHOP_POINT_USED_HISTORY add constraint FK_SHOP_POI_CUS foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table SPEED_BAR add constraint FK_Reference_152 foreign key (mediaId)
      references APP_MEDIA (mediaId) on delete restrict on update restrict;

alter table SURVEY_OPTION add constraint FK_SUR_SURVEY foreign key (surveyId)
      references SURVEY (surveyId) on delete restrict on update restrict;

alter table SURVEY_RESULT add constraint FK_SU_R_SURVEY foreign key (surveyId)
      references SURVEY (surveyId) on delete restrict on update restrict;

alter table SURVEY_RESULT add constraint FK_SURY_R__SUR_O foreign key (surveyOptionId)
      references SURVEY_OPTION (surveyOptionId) on delete restrict on update restrict;

alter table SYSTEM_MESSAGE add constraint FK_FK_sm_app_user foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table SYSTEM_URL add constraint FK_Reference_153 foreign key (parentId)
      references SYSTEM_URL (urlId) on delete restrict on update restrict;

alter table TAX_RATE add constraint FK_TAX_RATE_CA foreign key (categoryId)
      references CATEGORY (categoryId) on delete restrict on update restrict;

alter table TAX_RATE add constraint FK_Reference_131 foreign key (regionId)
      references REGION (regionId) on delete restrict on update restrict;

alter table TAX_RATE add constraint FK_TAX_RATE_TAX foreign key (taxId)
      references TAX (taxId) on delete restrict on update restrict;

alter table TEMPLATE add constraint FK_TEMPL_TEMPLATE foreign key (templateSetId)
      references TEMPLATE_SET (templateSetId) on delete restrict on update restrict;

alter table USER_ROLE add constraint FK_APP_APPUSER foreign key (appuserId)
      references APP_USER (appuserId) on delete restrict on update restrict;

alter table USER_ROLE add constraint FK_APP_APP_ROLE foreign key (roleId)
      references APP_ROLE (roleId) on delete restrict on update restrict;

alter table VARIATION_ITEM add constraint FK_VAR_PRODUCT_ foreign key (productVariationId)
      references PRODUCT_VARIATION (productVariationId) on delete restrict on update restrict;

alter table VARIATION_ITEM add constraint FK_VAR_ATTRIBUT foreign key (attributeOptionId)
      references ATTRIBUTE_OPTION (attributeOptionId) on delete restrict on update restrict;

alter table VOTE_OPTION add constraint FK_VOTE_OPT_VOTE foreign key (voteId)
      references VOTE (voteId) on delete restrict on update restrict;

alter table VOTE_RESULT add constraint FK_VOTE_RES_VOTE foreign key (voteId)
      references VOTE (voteId) on delete restrict on update restrict;

alter table VOTE_RESULT add constraint FK_VOTE_VOTE_OPT foreign key (voteOptionId)
      references VOTE_OPTION (voteOptionId) on delete restrict on update restrict;

alter table WISHLIST add constraint FK_WISH_CUSTOMER foreign key (customerId)
      references CUSTOMER (customerId) on delete restrict on update restrict;

alter table WISHLIST add constraint FK_WISH_ADD foreign key (shippingAddressId)
      references ADDRESS (addressId) on delete set null on update restrict;

alter table WISHLIST_ITEM add constraint FK_WIS_PRODUCT foreign key (productId)
      references PRODUCT (productId) on delete restrict on update restrict;

alter table WISHLIST_ITEM add constraint FK_WIS_WISHLIST foreign key (wishListId)
      references WISHLIST (wishListId) on delete restrict on update restrict;


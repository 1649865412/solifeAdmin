/*==============================================================*/
/* Table: CATALOG                                               */
/*==============================================================*/

create table CATALOG
(
   catalogId            int not null auto_increment,
   name                 varchar(32) not null,
   code                 varchar(32) not null,
   description          text,
   isVirtual            smallint not null,
   status               smallint not null,
   version              int not null default 0,
   primary key (catalogId),
   unique key AK_Key_Code (code)
);


/*==============================================================*/
/* Table: STORE                                                 */
/*==============================================================*/
/*==============================================================*/
/* Table: STORE                                                 */
/*==============================================================*/
create table STORE
(
   storeId              int not null auto_increment,
   catalogId            int,
   name                 varchar(32) not null,
   code                 varchar(32) not null,
   siteUrl              varchar(255) not null,
   description          text,
   status               smallint not null,
   version              int not null default 0,
   primary key (storeId),
   unique key AK_code (code)
);

alter table STORE add constraint FK_STORE__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;

/* app_user */
alter table APP_USER add COLUMN storeId int;
alter table APP_USER add constraint FK_USER__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;


/* category */
alter table CATEGORY add linkedCategoryId int;
alter table CATEGORY add catalogId int;
alter table CATEGORY add constraint FK_CATEGORY__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;

/* sales_order */
alter table SALES_ORDER add storeId int;
alter table SALES_ORDER add constraint FK_SALES_ORDER__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;
      
/* SHOPPINGCART */
alter table SHOPPINGCART add storeId int;
alter table SHOPPINGCART add constraint FK_SHOPPINGCART__STOARE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

/* PROMO_RULE */
alter table PROMO_RULE add  storeId int;
alter table PROMO_RULE add  catalogId int;
alter table PROMO_RULE add constraint FK_PROMO_RULE__CATALOG foreign key (catalogId)
      references CATALOG (catalogId) on delete restrict on update restrict;
alter table PROMO_RULE add constraint FK_PROMO_RULE__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

/* PRODUCT_REVIEW */
alter table PRODUCT_REVIEW add storeId int;
alter table PRODUCT_REVIEW add constraint FK_P_REVIEW__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;
/* PRODUCT_STAT */
alter table PRODUCT_STAT add storeId int;
alter table PRODUCT_STAT add constraint FK_P_STAT__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

/* category */
alter table CATEGORY add storeId int;
alter table CATEGORY add constraint FK_CATEGORY__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;
ALTER TABLE category DROP INDEX `AK_PRODUCT_CODE`;
ALTER TABLE category add unique key AK_PRODUCT_CODE (categoryCode, catalogId, storeId);

/* CONTENT */
alter table CONTENT add storeId int;
alter table CONTENT add constraint FK_CONTENT__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;
ALTER TABLE CONTENT DROP INDEX `AK_CONTENTCODE`;
ALTER TABLE CONTENT add unique key AK_CONTENTCODE (contentCode, storeId);

/* PAYMENT AND SHIPPING */
create table STORE_PAYMENT_METHOD
(
   storeId              int not null,
   paymentMethodId      int not null,
   primary key (storeId, paymentMethodId)
);

alter table STORE_PAYMENT_METHOD add constraint FK_SPM__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table STORE_PAYMENT_METHOD add constraint FK_SPM__PAYMENT_METHOD foreign key (paymentMethodId)
      references PAYMENT_METHOD (paymentMethodId) on delete restrict on update restrict;

create table STORE_SHIPPING_METHOD
(
   storeId              int not null,
   shippingMethodId     int not null,
   primary key (storeId, shippingMethodId)
);

alter table STORE_SHIPPING_METHOD add constraint FK_SHM__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;

alter table STORE_SHIPPING_METHOD add constraint FK_SPM__SHIPPING_METHOD foreign key (shippingMethodId)
      references SHIPPING_METHOD (shippingMethodId) on delete restrict on update restrict;

/* catalog add categoryId*/
alter table catalog add categoryId int;
/* init data */
insert into catalog (catalogId,name,categoryId,code,isVirtual,status,version) values (1,'Default',1,'default',0,1,0);
insert into store (storeId,catalogId,name,code,siteUrl,status,version) values(1,1,'Default Store','default','http://localhost:8080',1,0);

/* system_config */
alter table system_config DROP INDEX `AK_Key_2`;
alter table system_config add storeId int;
ALTER TABLE system_config add unique key AK_CONFIGKEY (configKey, storeId);
alter table SYSTEM_CONFIG add constraint FK_SYSTEM_CONFIG__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;


/*categoryId=1 为产品根目录*/
update catalog set categoryId = 1;
alter table catalog modify categoryId int not null;


/* store add categoryId*/
alter table store add categoryId int;
/*categoryId=2 为内容根目录*/
update store set categoryId = 2;
alter table store modify categoryId int not null;


/*catalogId=1为默认的catalog*/
update category set catalogId=1 where categoryPath like '1.%';
update category set catalogId=1 where categoryId =1;

/*storeId=1为默认的商店*/
update category set storeId=1 where categoryPath like '2.%';
update category set  storeId=1 where categoryId =2;
update content set storeId=1;
update shoppingcart set storeId=1;
update sales_order set storeId=1;
update app_user set storeId=1 where userType=0;
update product_review set storeId=1;
update promo_rule set storeId=1,catalogId=1;

delete from product_stat where reviewCount=0 and averageRate=0 and buyCount=0;
update product_stat set storeId=1;

update category set isLinkCategory=0 where categoryId=2;

/* system_config*/
update system_config set configValue = 'system,checkout,content,store,customer,order,catalog,sales,cart,sitemap' where configKey='ConfigCategorys';
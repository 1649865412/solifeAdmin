/* 20140113 */
alter table brand add pic2 varchar(256);
alter table brand add designer varchar(64);
alter table PRODUCT_DESCRIPTION add imageDescription text;

alter table GIFT_CERTIFICATE modify m1Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m2Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m3Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m4Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m5Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m6Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m7Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m8Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m91Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m10Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m11Amt numeric(12,2) default 0;
alter table GIFT_CERTIFICATE modify m12Amt numeric(12,2) default 0;



alter table Favorite add storeId int;
alter table Favorite add constraint FK_FAVORITE__STORE foreign key (storeId)
      references STORE (storeId) on delete restrict on update restrict;
update Favorite set storeId = 1;
/*20140115*/
insert into recommended_type(recommendTitle, typeName, ruleCode, autoEval, status, isSystem, isApplyToProduct, isApplyToCategory,version)
values('Favorite','favorite',2,1,1,1,0,1,0);

alter table purchase_order drop countItems;
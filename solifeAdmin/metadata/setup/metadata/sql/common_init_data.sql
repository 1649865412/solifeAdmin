



insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.storefront.protocol','@system-storefront-protocol@','','system',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.storefront.host','@system-storefront-host@','','system',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.storefront.ctxPath','@system-storefront-ctxPath@','','system',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.storefront.port','@system-storefront-port@','','system',2,1,0);



insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.general.storePath','@system-general-storePath@','eStore Install path','system',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('system.general.downLoadPath','@system-general-downLoadPath@','define your download path.','system',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('content.template.path','@content-template-path@','Templates Path','content',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('content.indexPath','@content-indexPath@','Index Path','content',1,1,0);
insert into SYSTEM_CONFIG(configKey,configValue,description,category,dataType,configType,version) values('content.media.upLoadPath','@content-media-upLoadPath@','Media path.Notice:If you change this path will lost old media.','content',1,1,0);


UPDATE system_language set status=1,isDefault=1 WHERE localeCode='@default-language@'; 
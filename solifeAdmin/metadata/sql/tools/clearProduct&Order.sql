truncate table wishlist_item;
truncate table wishlist;
truncate wholesale_price;
truncate table transfer_record;
truncate table system_queue;
truncate table system_message;
truncate table supplier_product;
truncate table system_message;
truncate table shoppingcart_item_gc;
truncate table shoppingcart_item;
truncate table shoppingcart;
truncate table shop_point_history;
truncate table shop_point;
truncate table see_also;
truncate table search_word;
truncate table search_keyword;
truncate table salesorder_attr_value;
truncate table sales_order_geoip;
truncate table order_audit;
truncate table purchase_order_item;
truncate table purchase_order;
truncate table order_sku;
truncate table order_shipment;
truncate table order_payment;
truncate table order_promotion;
truncate table sales_order;
truncate table review_vote;
truncate table provider;
truncate table product_stat;
truncate table product_sku;
truncate table product_review;
truncate table product_rate_value;

truncate table product_media;
truncate table product_description;
truncate table product_attr_value;
truncate table product_accessory;

truncate table also_buy;
truncate table recommended_product;
truncate table product;
truncate table payment_history;
truncate table patch_execute_history;
truncate table order_settlement;
truncate table order_return_sku;
truncate table order_return;
truncate table order_pick_list;
truncate table order_message;
truncate table order_item_promotion;
truncate table order_item_attribute;
truncate table order_address;
truncate table newsletter_group_newsletter;
truncate table newsletter_group;
truncate table newsletter;
truncate table inventory_audit;
truncate table gift_certificate;
truncate table feedback;
truncate table favorite;
truncate table customer_attr_value;
truncate table coupon;
truncate table cookie;
truncate table app_event;
truncate table app_audit;


--删除供应商
truncate table supplier;
truncate table supplier_product;
truncate table purchase_order;
truncate table purchase_order_item;

/* 初始化一个默认的供应商 */
INSERT INTO `supplier` (`supplierId`,`supplierName`,`supplierCode`,`email`,`address`,`fax`,`zip`,`adminId`,`comments`,`status`,`createTime`,`updateTime`,`createBy`,`updateBy`,`version`,`webSite`,`contacts`,`bankAccount`) VALUES (-1,'N/A','NULL','null@null.com','N/A','0000',NULL,NULL,NULL,1,now(),now(),NULL,NULL,0,'','','');
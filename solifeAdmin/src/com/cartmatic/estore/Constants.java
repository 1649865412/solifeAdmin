
package com.cartmatic.estore;

import java.math.BigDecimal;

/**
 * Constant values used throughout the application.
 * 
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants {
	
	//supportedSystemModes	= new String[] {"dev", "demo", "production", "test"	};
	public static final String	SYSTEM_MOED_DEV	= "dev";
	public static final String	SYSTEM_MOED_DEMO	= "demo";
	public static final String	SYSTEM_MOED_PRODUCTION	= "production";
	public static final String	SYSTEM_MOED_TEST	= "test";

	public static final String	ACTION_TYPE_ADD	= "ADD";

	public static final String	ACTION_TYPE_CANCEL	= "CANCEL";

	public static final String	ACTION_TYPE_DELETE	= "DELETE";

	public static final String	ACTION_TYPE_EDIT	= "EDIT";

	public static final String	ACTION_TYPE_PRINT	= "PRINT";

	public static final String	ACTION_TYPE_SEARCH	= "SEARCH";

	public static final String	ACTION_TYPE_VIEW	= "VIEW";

	// end: Generated for City From Template: Constants.java.vm
	// start: Generated for Address From Template: Constants.java.vm
	public static final Short	ADDRESS_TYPE_BILLING	= new Short((short) 2);

	public static final Short	ADDRESS_TYPE_COMPANY	= new Short((short) 3);

	public static final Short	ADDRESS_TYPE_PERSONAL	= new Short((short) 0);

	public static final Short	ADDRESS_TYPE_SHIPPING	= new Short((short) 1);

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String	ADMIN_ROLE	= "admin";
	// end: Generated for MediaMappingRule From Template: Constants.java.vm
	public static final String	FROM_CHECKOUT	= "fromCheckout";
	public static final String	CHECKOUT_TARGET_URL	= "checkoutTargetUrl";
	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 *	
	public static final String	AVAILABLE_ROLES	= "availableRoles";
	public static final String	BUTTON_NAME_CANCEL	= "cancel";
	/** Button name */
	public static final String	BUTTON_NAME_DELETE	= "delete";
	public static final String	BUTTON_NAME_SAVE	= "save";

	// end: Generated for MetricUnit From Template: Constants.java.vm

	

	//public static final String	CHECKOUTMODEL_DISPLAY	= "checkoutModel";
	// end: Generated for State From Template: Constants.java.vm
	// start: Generated for City From Template: Constants.java.vm
	// 对应货到付款的订单处理流程
	//public static final Short	CODTYPE_PAYAFTER	= new Short((short) 1);

	// 对应款到发货的订单处理流程
	//public static final Short	CODTYPE_PAYFIRST	= new Short((short) 0);
	// 对应付款和发货同时进行的订单处理流程
	//public static final Short	CODTYPE_PAYSYNCH	= new Short((short) 2);

	// end: Generated for MetricType From Template: Constants.java.vm

	/** The name of the configuration hashmap stored in application scope. */
	public static final String	CONFIG	= "appConfig";
	// end: Generated for Resource From Template: Constants.java.vm
	public static final String	CONTEXT_PATH	= "ctxPath";
	public static final String	CONTEXT_RES_PATH	= "resPath";
	public static final String	STORE_DEFAULT_CODE	= "default";
	// -- client cookie 's maxage
	public static int	COOKIE_DEFAULT_MAXAGE	= 30;	// 30
	// days
	// end: Generated for ShoppingcartItemPromotion From Template:
	/** Cookie Name */
	public static final String	COOKIE_NAME	= "eStore";

	//public static final String	COOKIE_NAME_LOADED_SHOPPINGCART	= "cookie_loaded_shoppingcart";
	//public static final String	COOKIE_NAME_SHOPPINGCART	= "cookie_shoppingcart";

	//public static final String	CUR_LOCALE_CODE	= "CUR_LOCALE_CODE";

	public static final String	CURRENT_GROUP_KEY	= "currentGroupKey";
	// end: Shoppingcart
	public final static String	KEY_MENU_CONTEXT	= "CartmaticMenuContext";
	public final static String	KEY_MENU_CONTEXT_LIST	= "CartmaticMenuContextList";
	public final static String	KEY_MENU_NAV	= "CartmaticMenuNav";
	public final static String	KEY_CURRENT_MENU	= "CartmaticCurrentMenu";

	//public static final String	Customer_COOKIE_USERNAME	= "customerUsername";
	// end: Generated for MediaType From Template: Constants.java.vm

	// default role name for administrator role
	public static final String	DEFAULT_ADMIN_ROLE_NAME	= "admin";

	// default user name for administrator user.
	public static final String	DEFAULT_ADMIN_USER_NAME	= "admin";
	public static final String	DEFAULT_ENCODING	= "UTF-8";

	// start: Generated for Feedback From Template: Constants.java.vm
	//public static final Short	Feedback_GivenShopPoint_Donate	= new Short((short) 1);
	//public static final Short	Feedback_GivenShopPoint_Inital	= new Short((short) 0);
	//public static final Short	Feedback_GivenShopPoint_Not_Donate	= new Short((short) 2);
	/** File separator from System properties */
	public static final String	FILE_SEP	= System.getProperty("file.separator");
	/** added by pengzhirong */
	public static final String	CATEGORY_PATH_NAME_SEP	= "/";

	public final static Short	FLAG_FALSE	= new Short((short) 0);
	public final static Short	FLAG_TRUE	= new Short((short) 1);
	
	public final static Short	PAY_TRANSFER	= new Short((short) 2);


	// start: Generated for Inventory From Template: Constants.java.vm
	public static final String	LOG4J_CONFIG_LOCATION	= "/WEB-INF/classes/conf/log4j.properties";
	// end: Generated for Coupon From Template: Constants.java.vm
	// start: Generated for Manufacturer From Template: Constants.java.vm
	public static final Short	MARK_DELETED	= new Short("1");

	public static final Short	MARK_NOT_DELETED	= new Short("0");

	// Constants.java.vm
	// start: mfr add Constants.java.vm
	//public static final String	MAX_ORDER	= "maxOrder";

	// end: Generated for MenuMenu From Template: Constants.java.vm

	public static final String	MEMBERSHIP_ANONYMOUS	= "anonymousMembership";
	public static final String	ENABLE_CURRENCYS	= "EnableCurrencys";
	/**
	 * current appuser's membership is requestContext
	 */
	// public static final String MEMBERSHIP_CURRENT = "currentMembership";
	// start: Generated for Membership From Template: Constants.java.vm
	
	

	/** Metric type */
	public final static String	METRIC_TYPE_LENGTH	= "l";

	public final static String	METRIC_TYPE_QUANTITY	= "q";
	public final static String	METRIC_TYPE_WEIGHT	= "w";

	// start: Generated for OrderShipmentItem From Template: Constants.java.vm
	public static final String	PAGES_PREFIX	= "/pages/";
	// start: Generated for PaymentHistory From Template: Constants.java.vm
	public static final Short	PaymentHistory_FLAG_FAILURE	= new Short((short) 0);

	public static final Short	PaymentHistory_FLAG_SUCCESS	= new Short((short) 1);
	public static final Short	PaymentHistory_FLAG_WAIT	= new Short((short) -1);
	
	public static final int	PaymentHistory_RESPONSE_FAILURE	= 2;
	public static final int	PaymentHistory_RESPONSE_NEVER	= 0;
	public static final int	PaymentHistory_RESPONSE_SUCCESS	= 1;
	// end: Generated for PaymentHistory From Template: Constants.java.vm
	// start: Generated for PaymentMethod From Template: Constants.java.vm
	/**
	 * Session scope attribute that holds the locale set by the user. By setting
	 * this key to the same one that JSTL uses, we get synchronization in JSTL
	 * w/o having to do extra work or have two session-level variables. Append
	 * .session to Config.FMT_LOCALE
	 */
	//public static final String	PREFERRED_LOCALE_KEY	= "javax.servlet.jsp.jstl.fmt.locale.session";
	public static final String	PRM_ACTION_TYPE	= "ACTION_TYPE";

	// start: Generated for PromotionProduct From Template: Constants.java.vm

	public static final String	QUERY_BEGINWITH	= "beginWith";

	public static final String	QUERY_BETWEEN	= "between";
	public static final String	QUERY_ENDWITH	= "endWith";
	public static final String	QUERY_EQUALS	= "eq";
	public static final String	QUERY_GREATTHAN	= "gt";
	public static final String	QUERY_IN	= "in";
	public static final String	QUERY_LESSTHAN	= "lt";
	public static final String	QUERY_LIKE	= "like";

	// start: Generated for RegionItem From Template: Constants.java.vm
	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	//public static final String	REGISTERED	= "registered";
	// end: Generated for ReportFolder From Template: Constants.java.vm

	public static final String	REQUEST_NAME_PRINT	= "printable";

	// end: Generated for CompanyInfo From Template: Constants.java.vm
	/*
	 * Status:Not published(default status when created, can edit info, but
	 * won't show to the customer and cannot accept order)
	 * 
	 */
	/**
	 * 未发布，或未完成，也可理解为草稿状态
	 */
	public static final Short	STATUS_NOT_PUBLISHED	= new Short("0");
	/** Status:Active */
	public static final Short	STATUS_ACTIVE	= new Short("1");
	/** Status:Inactive */
	public static final Short	STATUS_INACTIVE	= new Short("2");
	/** Status:Deleted */
	public static final Short	STATUS_DELETED	= new Short("3");
	/** Status:Expired */
	public static final Short	STATUS_EXPIRED	= new Short("4");
	/** Status:Upcoming */
	public static final Short	STATUS_UPCOMING	= new Short("5");
	/** Status:等待删除 */
	public static final Short	STATUS_AWAITING_DELETE	= new Short("6");
	/** Status:隐藏 */
	public static final Short	STATUS_HIDDEN	= new Short("7");
	/** not sent 0 */
	public static final Short	STATUS_NOT_SENT	= new Short("0");
	/** sent 1 */
	public static final Short	STATUS_SENT	= new Short("1");

	public static final Short	STATUS_PENDING	= new Short("10");
	/**
	 * Default locale used in this application, define in database, cache here
	 * only.
	 */
	//public static String	SYSTEM_LOCALE_CODE	= "zh_CN";

	// end: Generated for I18ntext From Template: Constants.java.vm

	public static final String	USER_HOME	= System
	.getProperty("user.home")
	+ FILE_SEP;

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String	USER_ROLE	= "user";
	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String	USER_ROLES	= "userRoles";

	public final static Integer	USERID_ANONYMOUS	= new Integer(-2);
	public final static Integer	USERID_SYSTEM	= new Integer(-1);
	// end: Generated for SortOrder From Template: Constants.java.vm

	public static final String	VELOCITY_PREFIX	= "/velocity/";

	public static final String	VIEW_PREFIX	= "VIEW_PREFIX";
	// end: Generated for Favorite From Template: Constants.java.vm
	public static final Short	CATEGORY_STATUS_DELETED	= new Short("3");

	/**
	 * 目录类型：产品目录
	 */
	public static final Short	CATEGORY_TYPE_CATALOG	= new Short("1");
	/**
	 * 目录类型：内容目录
	 */
	public static final Short	CATEGORY_TYPE_CONTENT	= new Short("2");
	
	/**
	 * 产品根目录ID
	 */
	public static final Integer	ROOT_CATEGORY_CATALOG	= new Integer("1");
	/**
	 * 内容跟目录ID
	 */
	public static final Integer	ROOT_CATEGORY_CONTENT	= new Integer("2");

	/** 内容目录－－首页栏目编码*/
	//public static final String	CONTENT_CATEGORY_CODE_INDEX	= "mainPage";
	/** 内容目录－－产品栏目编码*/
	//public static final String	CONTENT_CATEGORY_CODE_PRODUCT	= "product";
	
	/**商品类型 产品 1*/
	public static final Short ITEM_TYPE_PRODUCT = new Short("1");
	/**商品类型 礼券 2*/
	public static final Short ITEM_TYPE_GC = new Short("2");
	
	/**
	 * 客户激活模式：立即
	 */
	//public static final Short CUSTOMER_ACTIVE_NOW = new Short("0");
	/**
	 * 客户激活模式：发送确认邮件
	 */
	//public static final Short CUSTOMER_ACTIVE_EMAIL = new Short("1");
	
	/**
	 * 客户激活模式：后台人工激活
	 */
	//public static final Short CUSTOMER_ACTIVE_MANUAL = new Short("2");
	
	public final static String				KEY_SAVED_SEARCH_CRITERIA	= "CartmaticSavedSearchCriteria";
	
	public final static String				KEY_SAVED_RETURN_URI	= "CartmaticSavedReturnUri";
	
	public final static String				KEY_NAVIGATION_LIST	= "navigationList";
	
	public final static String				KEY_ADMIN_INFO	= "AdminInfo";
	
	/**
	 * 固定运费
	 */
	public final static BigDecimal SHIPPING_COST = new BigDecimal(22);
	
	/**
	 * 固定比较值，少于此值则计算运费
	 */
	public final static BigDecimal SHIPPING_COST_COMPARE = new BigDecimal(100);
}
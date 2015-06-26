/*
 * Created on Apr 30, 2006
 * 
 */

package com.cartmatic.estore.common.helper;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.model.system.SystemConfig;
import com.cartmatic.estore.core.util.ConfigRegistry;
import com.cartmatic.estore.system.service.StoreManager;
import com.cartmatic.estore.system.service.SystemConfigManager;

/**
 * 作用：统一注册系统所有的配置，包括Key，缺省值，配置的输入类型和数据类型等。<BR>
 * 如果配置原来不存在，在系统启动时候自动创建缺省的配置并保存到数据库，如果已经存在，则会更新除Value以外的配置信息，并即时生效。<BR>
 * 解释：INPUT_TYPE－控制输入模式，例如下拉框、Checkbox、模版选择器；DATA_TYPE控制输入内容的数据类型，以及输入验证规则；
 * CONF_MODE控制用户是否可见、编辑。<BR>
 * 用法：在registerConfigs注册所需的配置；尽量使用合适的方法，例如registerBoolConfig，
 * registerSimpleConfig等， 不够用才考虑用registerNormalConfig甚至registerConfig。<BR>
 * 
 * @author Ryan
 * 
 */
public class ConfigRegistryImpl implements ConfigRegistry
{
	public final static Short CONF_DATA_TYPE_BOOLEAN = new Short("3");

	public final static Short CONF_DATA_TYPE_MEDIA = new Short("5");

	public final static Short CONF_DATA_TYPE_NUMBER = new Short("2");

	public final static Short CONF_DATA_TYPE_STRING = new Short("1");

	// system can add control and validation
	public final static Short CONF_INPUT_TYPE_ARRAY = new Short("4");

	public final static Short CONF_INPUT_TYPE_CHECKBOX = new Short("3");

	public final static Short CONF_INPUT_TYPE_EMAIL = new Short("6");
	
	public final static Short CONF_INPUT_TYPE_PASSWORD = new Short("7");

	public final static Short CONF_INPUT_TYPE_FILEPATH = new Short("104");

	public final static Short CONF_INPUT_TYPE_IMAGE = new Short("102");

	// system can add control and validation
	public final static Short CONF_INPUT_TYPE_MAP = new Short("5");

	public final static Short CONF_INPUT_TYPE_SELECT = new Short("2");

	public final static Short CONF_INPUT_TYPE_TEMPLATE = new Short("101");

	public final static Short CONF_INPUT_TYPE_TEXT = new Short("1");

	public final static Short CONF_INPUT_TYPE_TEXT_I18N = new Short("103");

	public final static Short CONF_MODE_APPLICATION = new Short("2");

	public final static Short CONF_MODE_READONLY = new Short("4");

	public final static Short CONF_MODE_SYSTEM_HIDDEN = new Short("1");

	public final static Short CONF_MODE_USER = new Short("3");

	private final static ConfigRegistryImpl configRegistry = new ConfigRegistryImpl();

	private static final Log logger = LogFactory.getLog(ConfigRegistryImpl.class);

	/**
	 * 注意，里面的类型必须是String或者Integer等简单的类型
	 * 
	 * @param values
	 * @return
	 */
	private static String arrayToString(Object[] values) {
		return StringUtils.join(values, ',');
	}

	public static ConfigRegistryImpl getInstance() {
		return configRegistry;
	}

	/**
	 * 注意，里面的类型必须是String或者Integer等简单的类型
	 * 
	 * @param values
	 * @return
	 */
	private static String listToString(List<Object> values) {
		return StringUtils.join(values.iterator(), ',');
	}

	private SystemConfigManager systemConfigManager;
	private StoreManager storeManager = null;

	private ConfigRegistryImpl()
	{
	}

	private void registerArrayConfig(String confKey, String confValue, String confCategory, Short dataType) {
		registerNormalConfig(confKey, confValue, confCategory, CONF_INPUT_TYPE_ARRAY, dataType);
	}

	private void registerBoolConfig(String confKey, boolean confValue, String confCategory) {
		registerNormalConfig(confKey, "" + confValue, confCategory, CONF_INPUT_TYPE_CHECKBOX, CONF_DATA_TYPE_BOOLEAN);
	}

	/**
	 * 注册一个系统配置。建议首先使用其他方便的方法，不行再使用这个自行定制。<BR>
	 * 输入类型、数据类型等可以自由组合。<BR>
	 * Value和Options里面可以是I18n信息，但是使用者需要知道怎么去使用他们，系统配置模块只是负责配置。<BR>
	 * 注册一个系统配置后，其对应的配置名称、描述、类别的名称等的I18n信息在properties文件里面配置，约定使用下面的规则：<BR>
	 * 系统配置的名称的I18nKey=conf.name.${confKey}<BR>
	 * 系统配置的描述的I18nKey=conf.desc.${confKey}<BR>
	 * 配置类别的名称的I18nKey=conf.cate.${confCategory}<BR>
	 * 配置类别的描述的I18nKey=conf.cateDesc.${confCategory}<BR>
	 * 其中confCategory是多级结构，每段都是一个关键字，应分段定义I18n信息，如conf.cateName.system，conf.
	 * cateName.cache<BR>
	 * 
	 * @param confKey
	 *            配置的唯一的key，应该长一些，可以描述清楚其作用，例如isProductPageCacheEnabled
	 * @param confValue
	 *            配置的缺省值，只是支持字符串，如果是其他类型必须先转换为字符串
	 * @param confCategory
	 *            约定是多级结构，例如sales.certificate.email，第一段约定是模块，第二段约定是子模块/分组，
	 *            所有段都是关键字
	 * @param confInputType
	 *            控制输入类型，例如下拉框，模版选择器等
	 * @param dataType
	 *            数据的实际类型，也可以是数组里面元素的数据类型
	 * @param selectOptions
	 *            下拉框的可选项
	 * @param confMode
	 *            控制用户是否查看、编辑配置
	 */
	private synchronized void registerConfig(String confKey, String confValue, String confCategory, Short confInputType, Short dataType,
			String selectOptions, Short confMode) {
		if (confCategory == null || confCategory.indexOf(".") == -1)
		{
			throw new RuntimeException("Invalid config category, required format: A.B[.C]. Error config key is:" + confKey);
		}
		// Format arguments, apply default settings
		String fmtCategory = confCategory;
		if (!confCategory.endsWith("."))
		{
			fmtCategory = fmtCategory + ".";
		}
		Short fmtConfigType = confInputType == null ? CONF_INPUT_TYPE_TEXT : confInputType;
		Short fmtDataType = dataType == null ? CONF_DATA_TYPE_STRING : dataType;
		String fmtOptions = selectOptions == null ? "" : selectOptions;
		Short fmtConfigMode = confMode == null ? CONF_MODE_APPLICATION : confMode;

		if (fmtCategory.startsWith("store."))
		{
			List<Store> stores = storeManager.getAll();
			for (Store store: stores)
			{
				SystemConfig conf = systemConfigManager.getConfigByKey(confKey, store.getStoreId());
				if (conf == null)
				{
					SystemConfig config = new SystemConfig();
					config.setConfigKey(confKey);
					config.setConfigValue(confValue);
					config.setCategory(fmtCategory);
					config.setConfigType(fmtConfigType);
					config.setDataType(fmtDataType);
					config.setOptions(fmtOptions);
					config.setIsSystem(fmtConfigMode);
					config.setStore(store);
					systemConfigManager.save(config);
					logger.warn("System store config not found, auto create default config: " + config);
				}
				else
				{
					checkupdateConf(conf, fmtCategory, fmtConfigType, fmtDataType, fmtOptions, fmtConfigMode);
				}
			}
			//List<SystemConfig> confs = systemConfigManager.getStoreConfigsByKey(confKey);
			//if (confs == null || confs.isEmpty())
			//{
				
			//}
			//else
			//{
				//for (SystemConfig conf: confs)
				//{
					
				//}
			//}
		}
		else
		{
			SystemConfig curConf = systemConfigManager.getConfigByKey(confKey);
			if (curConf == null)
			{
				SystemConfig config = new SystemConfig();
				config.setConfigKey(confKey);
				config.setConfigValue(confValue);
				config.setCategory(fmtCategory);
				config.setConfigType(fmtConfigType);
				config.setDataType(fmtDataType);
				config.setOptions(fmtOptions);
				config.setIsSystem(fmtConfigMode);

				systemConfigManager.save(config);
				logger.warn("System config not found, auto create default config: " + config);
			}
			else 
			{
				checkupdateConf(curConf, fmtCategory, fmtConfigType, fmtDataType, fmtOptions, fmtConfigMode);
			}
		}
	}
	
	private void checkupdateConf(SystemConfig curConf, String fmtCategory, Short fmtConfigType, Short fmtDataType, String fmtOptions, Short fmtConfigMode)
	{
		if (!new EqualsBuilder().append(fmtCategory, curConf.getCategory()).append(fmtConfigType, curConf.getConfigType())
				.append(fmtDataType, curConf.getDataType()).append(fmtOptions, curConf.getOptions())
				.append(fmtConfigMode, curConf.getIsSystem()).isEquals())
		{
			// 更新除configKey，configValue以外的设置
			curConf.setCategory(fmtCategory);
			curConf.setConfigType(fmtConfigType);
			curConf.setDataType(fmtDataType);
			curConf.setOptions(fmtOptions);
			curConf.setIsSystem(fmtConfigMode);
			systemConfigManager.save(curConf);
			logger.warn("Updated system config settings: " + curConf);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.common.helper.ConfigRegistryT#registerConfigs()
	 */
	public final void registerConfigs() {
		// system 的目录类别
		registerHiddenConfig("ConfigCategorys", "system,checkout,content,store,customer,order,catalog,sales,cart,sitemap", "system.others");

		registerBoolConfig("IsPageGzipEnabled", true, "system.others");
		// registerBoolConfig("IsServerSideProductPageCacheEnabled", false,
		// "system.cache.page");
		// registerBoolConfig("IsServerSideDataCacheEnabled", true,
		// "system.cache");
		registerBoolConfig("IsRecentViewEnabled", false, "system.others.features");
		registerBoolConfig("IsValidationCodeEnabled", true, "system.security.features");
		registerBoolConfig("IsRememberMeEnabled", true, "system.security.features");
		registerNumberConfig("MaxLoginFailTimes", 3, "system.security");
		registerNumberConfig("ValidationSessionHours", 2, "system.security");
		// 只是供系统配置模块内部使用，不提供设置
		registerHiddenConfig("system.config.category", "system,catalog,content,customer,sales,order,other", "system.others");
		// registerReadOnlyConfig("TemplateType", "jsp", "system.page");
		//registerSimpleConfig("StoreFrontSiteUrl", "http://localhost:8080/eStore", "system.path");
		registerSimpleConfig("StoreAdminSiteUrl", "http://localhost:8080/StoreAdmin", "system.path");
		registerSimpleConfig("CachableUrls", "/index.html,/,*_catalog.html", "system.cache.page");
		registerHiddenConfig("IsAlertLicense", "false", "system.others");
		registerNumberConfig("BrowserSidePageCacheSeconds", 3600, "system.cache.page");
		registerNumberConfig("ServerSidePageCacheSeconds", 600, "system.cache.page");
		// registerSimpleConfig("PasswordEncryptionAlgorithm", "SHA",
		// "system.security");
		// registerSimpleConfig("StoreFrontInstallationPath",getContextRootParentPath()
		// + "/eStore", "system.path");
		registerHiddenConfig("SearchServerUrl", "http://localhost:8080/searchServer", "system.search.path");
		registerSimpleConfig("DefaultSystemEmail", "Cartmatic <sales@cartmatic.com>", "system.email");
		registerSimpleConfig("BugReportEmail", "Cartmatic <sales@cartmatic.com>", "system.email");
		
		registerSimpleConfig("MailHost", "", "system.email");
		registerSimpleConfig("MailProtocol", "smtp", "system.email");
		registerNumberConfig("MailPort", 0, "system.email");
		registerSimpleConfig("MailUsername", "", "system.email");
		registerNormalConfig("MailPassword", "", "system.email", CONF_INPUT_TYPE_PASSWORD, CONF_DATA_TYPE_STRING);		
		registerSimpleConfig("MailDefaultEncoding", "UTF-8", "system.email");
		registerBoolConfig("IsMailAuth", true, "system.email");
		registerSimpleConfig("MailTimeout", "300000", "system.email");
		registerBoolConfig("IsMailTlsEnable", true, "system.email");
		
		// registerSimpleConfig("BugReportEmailTemplate",
		// "system/bugReportEmail.vm", "system.email");

		// sales
		registerNumberConfig("ShopPointUseGiftPercent", 10, "sales.certificate");
		registerNumberConfig("GiftCertificateExpireYears", 2, "sales.certificate.time");
		registerNumberConfig("RecommendedProductExpireYears", 2, "sales.recommended");
		registerNumberConfig("GiftCertificateMinAmt", 10, "sales.certificate");
		registerNumberConfig("GiftCertificateMaxAmt", 1000, "sales.certificate");
		registerSimpleConfig("GiftCertificateEmailTemplate", "sales/giftcertificateMail.vm", "sales.certificate");
		registerSimpleConfig("CouponEmailTemplate", "sales/couponMail.vm", "sales.coupon");
		registerBoolConfig("IsAllowSystemRulesWhenUseCoupon", false, "sales.coupon");
		// customer
		// registerNumberConfig("IsCustomerRegisterConfirmEnabled", 2,
		// "customer.security.features");
		registerSelectConfig("IsCustomerRegisterConfirmEnabled", "0", "customer.security.features", CONF_DATA_TYPE_NUMBER,
				"[customer.active.now=0,customer.active.email=1,customer.active.manual=2]");

		registerNumberConfig("RegistrationShopPointAmount", 10, "customer.shoppoint");
		registerNumberConfig("LoginShopPointAmount", 1, "customer.shoppoint");
		registerSimpleConfig("FeedbackGivenPoints", "1,2,3,4,5", "customer.shoppoint");
		//registerDoubleConfig("ShopPointExchangeRate", 1, "customer.shoppoint");

		// catalog
		registerSimpleConfig("StockAlertRecipientEmail", "sales@cartmatic.com", "catalog.stock.email");
		// content
		registerReadOnlyConfig("DownloadPath", "/download", "content.path");
		registerReadOnlyConfig("MediaStorePath", "/media", "content.path");
		registerSimpleConfig("SiteMapPostUrl", "http://www.google.com/webmasters/tools/ping?sitemap=", "sitemap.");
		registerBoolConfig("IsAutoPostSiteMap", true, "sitemap.");

		// salesOrder and checkout
		/*
		 * 去除积分支付功能 registerNumberConfig("ShopPointUsePercent", 30,
		 * "checkout.shoppoint");
		 * registerBoolConfig("IsShopPointPresentAfterPay", true,
		 * "checkout.shoppoint");
		 */
		registerBoolConfig("IsAnonymousCheckoutEnabled", true, "checkout.security.features");
		registerBoolConfig("IsCancelOrderEnabled", true, "order.features");
		registerBoolConfig("IsRecalculateShippingAndTaxAfterModified", false, "order.features");
		registerNumberConfig("OrderRecentDays", 7, "order.time");
		registerNumberConfig("OrderRobotReviewDelayMinutes", 45, "order.time");
		registerNumberConfig("CartValidDays", 15, "cart.time");
		registerBoolConfig("anonymousAddFavorite", true, "cart.other");
		registerNumberConfig("CookieMaxAge", Constants.COOKIE_DEFAULT_MAXAGE, "cart.time");

		// system locale and language settings
		registerHiddenConfig("StoreFrontLocale", "en_US", "system.others");
		registerHiddenConfig("StoreAdminLocale", "en_US", "system.others");

		// 暂时只是加入两个流程
		// registerHiddenConfig(OrderService.PROCESS_PAY_FIRST + "_codType",
		// Constants.CODTYPE_PAYFIRST.toString(), "system.others");
		// registerHiddenConfig(OrderService.PROCESS_PAY_AFTER + "_codType",
		// Constants.CODTYPE_PAYAFTER.toString(), "system.others");

		// registerBoolConfig("IsSupportWrapFashion", false, "checkout.flow");
		registerBoolConfig("IsSupportWrapExpress", false, "checkout.flow");

		// seeAlso
		// registerBoolConfig("IsDeleteSystemSeeAlso", false, "system.others");
		registerNumberConfig("ContinuouslyFailCountStopImport", 1000, "system.others");

		registerBoolConfig("IsProductReviewConfirmEnabled", true, "catalog.others");
		registerBoolConfig("IsAllowAnonymousReview", false, "catalog.others");
		registerBoolConfig("IsCreateProductDefaultAllowReviewEnabled", true, "catalog.others");
		registerSimpleConfig("ProductReviewGivenPoints", "1,2,3,4,5", "catalog.others");

		registerSimpleConfig("WeightUnit", "g", "catalog.others");
		registerSimpleConfig("LengthUnit", "cm", "catalog.others");

		registerSimpleConfig("DefaultCurrencySymbol", "$", "system.others");

		registerSimpleConfig("SalesOrderPrefix", "S", "order.others");
		registerNumberConfig("precentOfProductCost", 80, "catalog.others");

		//For Stores
		registerSimpleConfig("StoreDomain", "", "store.seo");
		registerSimpleConfig("StoreTitle", "", "store.seo");
		registerSimpleConfig("StoreKeyWords", "", "store.seo");
		registerSimpleConfig("StoreDescription", "", "store.seo");
		registerSimpleConfig("StoreExtraMeta1", "", "store.seo");
		registerSimpleConfig("StoreExtraMeta2", "", "store.seo");
		registerSimpleConfig("StoreExtraMeta3", "", "store.seo");
		

		registerSimpleConfig("StoreEmailSiteUrl", "", "store.seo");
		registerSimpleConfig("StoreEmailSiteName", "", "store.seo");
		// Google Analytics是否跟踪订单
		registerBoolConfig("StoreIsTrackOrder", true, "store.seo");
		// Google Analytics是否跟踪结账过程
		registerBoolConfig("StoreIsTrackCheckout", true, "store.seo");
		// Google Analytics默认帐号
		registerSimpleConfig("StoreDefaultAnalytics", "", "store.seo");
		// Google Analytics备用帐号
		registerSimpleConfig("StoreSpareAnalytics", "", "store.seo");
		registerSimpleConfig("StoreFooterDescription", "", "store.seo");
		registerSimpleConfig("StoreEmail", "", "store.info");
		registerSimpleConfig("StorePhone", "", "store.info");
		registerSimpleConfig("StoreEmailSender", "", "store.info");
		registerNumberConfig("StoreCategoryListPerSize", 20, "store.info");
		registerSimpleConfig("StoreSearchAttribute", "", "store.info");
		registerSimpleConfig("StoreSearchSkuOption", "", "store.info");
		registerSimpleConfig("StoreMediaUrlPath", "/media/", "store.seo");
		

		registerSimpleConfig("StoreProductCategoryDisMaxRequestHandler", "category_edismax_default", "store.seo");
		registerSimpleConfig("StoreSearchProductDisMaxRequestHandler", "search_product_edismax_default", "store.seo");
		
		registerSimpleConfig("StoreUrlBuilderClass", "com.cartmatic.estore.catalog.util.DefaultUrlBuilder", "store.seo");
		
		
		// 产品批量修改属性
		// 产品普通属性
		registerReadOnlyConfig("BulkProdCommAttrs",
				"productName,productCode,status,extra1,url,metaKeyword,metaDescription,productDescription.fullDescription",
				"catalog.others");
		// Sku普通属性
		registerReadOnlyConfig("BulkSkuCommAttrs", "productSkuCode,costPrice,price,salePrice,listPrice,status", "catalog.others");
		// 产品自定义属性
		registerSimpleConfig("BulkProdAttrs", "shopByStyle,shopByColor,shopByGender", "catalog.others");
		//
		registerSimpleConfig("GoogleMapAPIKey", "", "order.time");
		registerSimpleConfig("IpAdressUserInfo", "", "order.time");
		// 本站rss链接
		registerSimpleConfig("RssUrl", "", "system.other");
		// 订单超过X天未支付的，自动取消
		registerNumberConfig("OrderCancelDays", 7, "order.time");

		registerBoolConfig("SitemapIncludeHomepage", true, "sitemap.");
		registerBoolConfig("SitemapIncludeProductCategories", true, "sitemap.");
		registerBoolConfig("SitemapIncludeProducts", true, "sitemap.");
		registerBoolConfig("SitemapIncludeContents", false, "sitemap.");

		registerSelectConfig(
				"SitemapHomepageFrequency",
				"daily",
				"sitemap.frequencies",
				CONF_DATA_TYPE_STRING,
				"[sitemap.frequencies.always=always,sitemap.frequencies.hourly=hourly,sitemap.frequencies.daily=daily,sitemap.frequencies.weekly=weekly,sitemap.frequencies.monthly=monthly,sitemap.frequencies.yearly=yearly,sitemap.frequencies.never=never]");
		registerSelectConfig(
				"SitemapProductCategoriesFrequency",
				"daily",
				"sitemap.frequencies",
				CONF_DATA_TYPE_STRING,
				"[sitemap.frequencies.always=always,sitemap.frequencies.hourly=hourly,sitemap.frequencies.daily=daily,sitemap.frequencies.weekly=weekly,sitemap.frequencies.monthly=monthly,sitemap.frequencies.yearly=yearly,sitemap.frequencies.never=never]");
		registerSelectConfig(
				"SitemapProductsFrequency",
				"weekly",
				"sitemap.frequencies",
				CONF_DATA_TYPE_STRING,
				"[sitemap.frequencies.always=always,sitemap.frequencies.hourly=hourly,sitemap.frequencies.daily=daily,sitemap.frequencies.weekly=weekly,sitemap.frequencies.monthly=monthly,sitemap.frequencies.yearly=yearly,sitemap.frequencies.never=never]");
		registerSelectConfig(
				"SitemapContentsFrequency",
				"monthly",
				"sitemap.frequencies",
				CONF_DATA_TYPE_STRING,
				"[sitemap.frequencies.always=always,sitemap.frequencies.hourly=hourly,sitemap.frequencies.daily=daily,sitemap.frequencies.weekly=weekly,sitemap.frequencies.monthly=monthly,sitemap.frequencies.yearly=yearly,sitemap.frequencies.never=never]");

		registerSelectConfig(
				"SitemapHomepagePriority",
				"1.0",
				"sitemap.priorities",
				CONF_DATA_TYPE_STRING,
				"[sitemap.priorities.0.1=0.1,sitemap.priorities.0.2=0.2,sitemap.priorities.0.3=0.3,sitemap.priorities.0.4=0.4,sitemap.priorities.0.5=0.5,sitemap.priorities.0.6=0.6,sitemap.priorities.0.7=0.7,sitemap.priorities.0.8=0.8,sitemap.priorities.0.9=0.9,sitemap.priorities.1.0=1.0]");
		registerSelectConfig(
				"SitemapProductCategoriesPriority",
				"0.6",
				"sitemap.priorities",
				CONF_DATA_TYPE_STRING,
				"[sitemap.priorities.0.1=0.1,sitemap.priorities.0.2=0.2,sitemap.priorities.0.3=0.3,sitemap.priorities.0.4=0.4,sitemap.priorities.0.5=0.5,sitemap.priorities.0.6=0.6,sitemap.priorities.0.7=0.7,sitemap.priorities.0.8=0.8,sitemap.priorities.0.9=0.9,sitemap.priorities.1.0=1.0]");
		registerSelectConfig(
				"SitemapProductsPriority",
				"0.3",
				"sitemap.priorities",
				CONF_DATA_TYPE_STRING,
				"[sitemap.priorities.0.1=0.1,sitemap.priorities.0.2=0.2,sitemap.priorities.0.3=0.3,sitemap.priorities.0.4=0.4,sitemap.priorities.0.5=0.5,sitemap.priorities.0.6=0.6,sitemap.priorities.0.7=0.7,sitemap.priorities.0.8=0.8,sitemap.priorities.0.9=0.9,sitemap.priorities.1.0=1.0]");
		registerSelectConfig(
				"SitemapContentsPriority",
				"0.1",
				"sitemap.priorities",
				CONF_DATA_TYPE_STRING,
				"[sitemap.priorities.0.1=0.1,sitemap.priorities.0.2=0.2,sitemap.priorities.0.3=0.3,sitemap.priorities.0.4=0.4,sitemap.priorities.0.5=0.5,sitemap.priorities.0.6=0.6,sitemap.priorities.0.7=0.7,sitemap.priorities.0.8=0.8,sitemap.priorities.0.9=0.9,sitemap.priorities.1.0=1.0]");

		registerSimpleConfig("SitemapContentCategories", "", "sitemap.");

		registerBoolConfig("SitemapProductIncludeImages", false, "sitemap.");

		registerSimpleConfig("TaoBaoAppKey", "12432320", "catalog.");
		registerSimpleConfig("TaoBaoAppSecret", "cb65357a99726dcab15deb3fb58a6200", "catalog.");
	}

	private void registerDoubleConfig(String confKey, double confValue, String confCategory) {
		registerNormalConfig(confKey, "" + confValue, confCategory, CONF_INPUT_TYPE_TEXT, CONF_DATA_TYPE_NUMBER);
	}

	/**
	 * 隐藏的系统配置一般不需要进行数据类型检查等,所以全部使用字符串;在ConfigUtil提供的方法处理数据转换
	 * 
	 * @param confKey
	 * @param confValue
	 * @param confCategory
	 */
	private void registerHiddenConfig(String confKey, String confValue, String confCategory) {
		registerConfig(confKey, confValue, confCategory, null, null, null, CONF_MODE_SYSTEM_HIDDEN);
	}

	private void registerMapConfig(String confKey, String confValue, String confCategory, Short dataType) {
		registerNormalConfig(confKey, confValue, confCategory, CONF_INPUT_TYPE_MAP, dataType);
	}

	private void registerNormalConfig(String confKey, String confValue, String confCategory, Short confInputType, Short dataType) {
		registerConfig(confKey, confValue, confCategory, confInputType, dataType, null, null);
	}

	/**
	 * 说明：如果想用其他输入模式，例如下拉框，也可以的，直接使用registerSelectConfig吧
	 * 
	 * @param confKey
	 * @param confValue
	 * @param confCategory
	 * @return
	 */
	private void registerNumberConfig(String confKey, int confValue, String confCategory) {
		registerNormalConfig(confKey, "" + confValue, confCategory, CONF_INPUT_TYPE_TEXT, CONF_DATA_TYPE_NUMBER);
	}

	/**
	 * 只读的系统配置一般不需要进行数据类型检查等,所以全部使用字符串;在ConfigUtil提供的方法处理数据转换
	 * 
	 * @param confKey
	 * @param confValue
	 * @param confCategory
	 */
	private void registerReadOnlyConfig(String confKey, String confValue, String confCategory) {
		registerConfig(confKey, confValue, confCategory, null, null, null, CONF_MODE_READONLY);
	}

	private void registerSelectConfig(String confKey, String confValue, String confCategory, Short dataType, String selectOptions) {
		registerConfig(confKey, confValue, confCategory, CONF_INPUT_TYPE_SELECT, dataType, selectOptions, null);
	}

	// private static SystemConfig changeConfigMode(SystemConfig config, Short
	// dataType) {
	// config.setIsSystem(dataType);
	// return config;
	// }
	//
	// private static SystemConfig changeDataType(SystemConfig config, Short
	// confMode) {
	// config.setDataType(confMode);
	// return config;
	// }

	private void registerSimpleConfig(String confKey, String confValue, String confCategory) {
		registerNormalConfig(confKey, confValue, confCategory, null, null);
	}

	private void registerTemplateConfig(String confKey, String confValue, String confCategory, Short dataType) {
		registerNormalConfig(confKey, confValue, confCategory, CONF_INPUT_TYPE_TEMPLATE, CONF_DATA_TYPE_STRING);
	}

	/**
	 * @param systemConfigManager
	 *            the systemConfigManager to set
	 */
	public void setSystemConfigManager(SystemConfigManager systemConfigManager) {
		this.systemConfigManager = systemConfigManager;
	}

	public void setStoreManager(StoreManager avalue) {
		this.storeManager = avalue;
	}
}

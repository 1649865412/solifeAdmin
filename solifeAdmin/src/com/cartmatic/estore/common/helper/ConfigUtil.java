/*
 * Created on Apr 30, 2006
 * 
 */

package com.cartmatic.estore.common.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.system.service.StoreManager;
import com.cartmatic.estore.system.service.SystemConfigManager;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * 提供系统配置的统一的使用接口。Java程序内直接调用各个API。JSP内用${appConfig.isDemoMode}这样的EL使用。<BR>
 * 所有设置应该先在ConfigRegistry注册<BR>
 * 
 * @author Ryan
 */
public class ConfigUtil {
	private final static ConfigUtil	configUtil = new ConfigUtil();
	/**
	 * 当配置文件无法解释某个属性时,会注入${xxxx}的属性值,这时应该忽略这些注入的setter.
	 */
	private Pattern unresolvablePlaceholders = Pattern.compile("^\\$\\{(.)*\\}$");

	private static final Log logger = LogFactory.getLog(ConfigUtil.class);

	private final static String[] supportedSystemModes	= new String[] {"dev", "demo", "production", "test"	};

	public static ConfigUtil getInstance() {
		return configUtil;
	}

	private String				searchServerUrl			= null;
	private boolean				autoStartScheduler			= false;
	private String				ctxPath						= "Not Initialized";
	private boolean				isStoreFront				= false;
	private boolean				rememberMeEnabled;
	private boolean				serverSideDataCacheEnabled	= true;
	//private String				storeFrontInstallationPath	= null;
	private String				assetsPath	= null;
	private SystemConfigManager	systemConfigManager;
	//private CompanyInfoManager companyInfoManager;
	private StoreManager storeManager;
	private String systemMode = "production";
	private String webAppRootPath = null;	
	private String orderNoPrefix = "";
	private String orderNoSuffix = "";
	//private String storeFrontSiteUrl = null;
	private String mailMarketerUrl=null; 
	private String poNoPrefix = "";
	/**
	 * 查询服务器的安装路径,通常在后台的配置文件上配置的.
	 */
	//private String storeSearchPath = null;

	private String cmsAccessCategories = "";
	
	//private String mediaUrlPath;
	
	/**
	 * key = Host
	 * value = Code
	 */
	private HashMap<String,String> storeHostAndCodeMap = new HashMap<String,String>();
	/**
	 * key = Code
	 * value = Store
	 */
	private HashMap<String,Store> storeMap = new HashMap<String,Store>();
	
	/**
	 * 模板配置
	 */
	private Properties templateConfig=null;
	
	private ConfigUtil() {
	}

	/**
	 * 从数据库读取并设置网上商店和商店管理系统的网站URL。注意，第一次启动，其数据可能是错误的，在登录时候会检查并自动跳转到系统配置页面。
	 * 
	 */
	public void checkConfigAtStartup() {
		logger.info("Initializing servlet context path...");
		//logger.info("Using config [StoreAdminSiteUrl]: " + ConfigUtil.getInstance().getStoreAdminSiteUrl());
		//logger.info("Using config [StoreFrontSiteUrl]:" + ConfigUtil.getInstance().getStoreFrontSiteUrl());
		String tmpCtxPath = "";
		//init storeMap
		List<Store> stores = storeManager.getAllActiveStores();
		for (Store store : stores)
		{
			
			store.setDomain(this.getConfig(store.getCode()+"_StoreDomain"));
			store.setKeyWords(this.getConfig(store.getCode()+"_StoreKeyWords"));
			store.setTitle(this.getConfig(store.getCode()+"_StoreTitle"));
			store.setDescription(this.getConfig(store.getCode()+"_StoreDescription"));
			store.setFooterDescription(this.getConfig(store.getCode()+"_StoreFooterDescription"));
			store.setDefaultAnalytics(this.getConfig(store.getCode()+"_StoreDefaultAnalytics", ""));
			store.setSpareAnalytics(this.getConfig(store.getCode()+"_StoreSpareAnalytics", ""));
			store.setIsTrackCheckout(this.getConfigAsBool(store.getCode()+"_StoreIsTrackCheckout", true));
			store.setIsTrackOrder(this.getConfigAsBool(store.getCode()+"_StoreIsTrackOrder", true));
			store.setExtraMeta1(this.getConfig(store.getCode()+"_StoreExtraMeta1"));
			store.setExtraMeta2(this.getConfig(store.getCode()+"_StoreExtraMeta2"));
			store.setExtraMeta3(this.getConfig(store.getCode()+"_StoreExtraMeta3"));
			store.setEmail(this.getConfig(store.getCode()+"_StoreEmail"));
			store.setPhone(this.getConfig(store.getCode()+"_StorePhone"));
			store.setEmailSender(this.getConfig(store.getCode()+"_StoreEmailSender", getDefaultSystemEmail()));
			store.setCategoryListPerSize(this.getConfig(store.getCode()+"_StoreCategoryListPerSize",20));
			store.setSearchAttribute(this.getConfigAsMap(store.getCode()+"_StoreSearchAttribute", ""));
			store.setSearchSkuOption(this.getConfigAsMap(store.getCode()+"_StoreSearchSkuOption", ""));
			store.setMediaUrlPath(this.getConfig(store.getCode()+"_StoreMediaUrlPath", "/media/"));
			
			String emailSiteUrl=this.getConfig(store.getCode()+"_StoreEmailSiteUrl","" );
			if(StringUtils.isBlank(emailSiteUrl)){
				emailSiteUrl=store.getSiteUrl();
			}
			store.setEmailSiteUrl(emailSiteUrl);
			
			String emailSiteName=this.getConfig(store.getCode()+"_StoreEmailSiteName","" );
			if(StringUtils.isBlank(emailSiteName)){
				emailSiteName=store.getName();
			}
			store.setEmailSiteName(emailSiteName);

			store.setProductCategoryDisMaxRequestHandler(this.getConfig(store.getCode()+"_StoreProductCategoryDisMaxRequestHandler", "category_edismax_default"));
			store.setSearchProductDisMaxRequestHandler(this.getConfig(store.getCode()+"_StoreSearchProductDisMaxRequestHandler", "search_product_edismax_default"));
			store.setUrlBuilderClass(this.getConfig(store.getCode()+"_StoreUrlBuilderClass", "com.cartmatic.estore.catalog.util.DefaultUrlBuilder"));
//			store.setCatalogId(store.getCatalogId());
			storeMap.put(store.getCode(), store);
		}
		
		if (getIsStoreFront()) 
		{
			try 
			{
				for (Store store : stores)
				{
					//storeMap.put(store.getCode(), store);
					URL site = new URL(store.getSiteUrl());
					if (store.getCode().equals(Constants.STORE_DEFAULT_CODE))
					{
						tmpCtxPath = site.getPath();
					}					
					storeHostAndCodeMap.put(site.getHost(), store.getCode());
				}				
			}
			catch (MalformedURLException e) 
			{
				logger.error("StoreFrontSiteUrl is not configured correctly. Start StoreAdmin and correct it, then retry.", e);
			}
			
		} else {
			try {
				URL storeAdminUrl = new URL(ConfigUtil.getInstance().getStoreAdminSiteUrl());
				tmpCtxPath = storeAdminUrl.getPath();
			} catch (MalformedURLException e) {
				logger.error("Store Admin site url is not configured correctly!"+ConfigUtil.getInstance().getStoreAdminSiteUrl(), e);
			}
		}	

		initContextPath(tmpCtxPath);
		
		/**
		 * 加载模板配置
		 */
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(getAssetsPath()+"/templates/templates.properties"));
			templateConfig=new Properties();
			templateConfig.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Store getStore()
	{
		String code = RequestContext.getCurrentStoreCode();
		return storeMap.get(code);
	}
	
	public Store getStore(String code)
	{
		return storeMap.get(code);
	}
	
	public HashMap getStoreMap()
	{
		return storeMap;
	}

	public final boolean checkInvalidSystemConfigs() {
		if (systemMode.equals("dev")) {
			return false;
		}
		// TODO,检查IP是否符合。检查download等路径是否可读写。
		int oriMsgSize = RequestContext.getSessionMessageCount();
//		if (getStoreFrontSiteUrl().indexOf("localhost") > 0
//				|| !UrlUtil.isUrlValid(getStoreFrontSiteUrl())) {
//			saveInvalidConfigMessage("StoreFrontSiteUrl");
//		}
//		if (getStoreAdminSiteUrl().indexOf("localhost") > 0
//				|| !UrlUtil.isUrlValid(getStoreAdminSiteUrl())) {
//			saveInvalidConfigMessage("StoreAdminSiteUrl");
//		}
		File tmpFile = new File(getAssetsPath());
		if (!tmpFile.exists()) {
			saveInvalidConfigMessage("AssetsPath");
		}
		return (RequestContext.getSessionMessageCount() - oriMsgSize > 0);
	}
	
	public Collection<String> getAllStoreCodes()
	{
		return storeHostAndCodeMap.values();
	}
	/**
	 * 
	 * @param host
	 * @return
	 */
	public String getStoreCode(String host)
	{
		return storeHostAndCodeMap.get(host);
	}

	public String getConfigCategorys()
	{
		return getConfig("ConfigCategorys");
	}
	
	public int getBrowserSidePageCacheSeconds() {
		return getConfigAsInt("BrowserSidePageCacheSeconds", 600);
	}

	public String getBugReportEmail() {
		return getConfig("BugReportEmail");
	}

	public String getDefaultSystemEmail() {
		return getConfig("DefaultSystemEmail","CartMatic <noreply@cartmatic.com>");
	}
	
	public String getMailHost() {
		return getConfig("MailHost","");
	}
	public String getMailProtocol() {
		return getConfig("MailProtocol","");
	}
	public int getMailPort() {
		return getConfigAsInt("MailPort",0);
	}
	public String getMailUsername() {
		return getConfig("MailUsername","");
	}
	/**
	 * 客户email
	 * @return
	 */
	public String getMailCustomer() {
		return getConfig("MailCustomer","");
	}
	/**
	 * 周年庆活动开始日期
	 * @return
	 */
	public String getAnniverysaryStartDate() {
		return getConfig("AnniverysaryStartDate","09/30/2014");
	}
	/**
	 * 周年庆活动结束日期
	 * @return
	 */
	public String getAnniverysaryEndDate() {
		return getConfig("AnniverysaryEndDate","11/30/2014");
	}
	
	/**
	 * 注册时宣传语，默认为“”
	 * @return
	 */
	public String getStoreSlogan() {
		return getConfig("StoreSlogan","");
	}
	
	public String getMailPassword() {
		return getConfig("MailPassword","");
	}
	public String getMailDefaultEncoding() {
		return getConfig("MailDefaultEncoding","");
	}
	public boolean getIsMailAuth() {
		return getConfigAsBool("IsMailAuth",true);
	}
	public String getMailTimeout() {
		return getConfig("MailTimeout","");
	}
	public boolean getIsMailTlsEnable() {
		return getConfigAsBool("IsMailTlsEnable",true);
	}
	
//	public String getBugReportEmailTemplate() {
//		return getConfig("BugReportEmailTemplate", null);
//	}

	public String getCachableUrls() {
		return getConfig("CachableUrls", "");
	}

	public int getCartValidDays() {
		return getConfigAsInt("CartValidDays", 15);
	}
	
	public boolean getIsAnonymousAddFavorite(){
		return getConfigAsBool("anonymousAddFavorite", true);
	}
	
	public boolean getIsAllowAnonymousReview(){ 
		return getConfigAsBool("IsAllowAnonymousReview", false);
	}
	
	public boolean getIsProductReviewConfirmEnabled(){
		return getConfigAsBool("IsProductReviewConfirmEnabled", true);
	}
	
	public boolean getIsCreateProductDefaultAllowReviewEnabled(){
		return getConfigAsBool("IsCreateProductDefaultAllowReviewEnabled", true);
	}
	
	public int[] getProductReviewGivenPoints() {
		String temp_productReviewGivenPoints=getConfig("ProductReviewGivenPoints", "1,2,3,4,5");
		int []productReviewGivenPoints=(int[])ConvertUtils.convert(temp_productReviewGivenPoints.split(","), int.class);
		return productReviewGivenPoints;
	}
	public int[] getFeedbackGivenPoints() {
		String temp_feedbackGivenPoints=getConfig("FeedbackGivenPoints", "1,2,3,4,5");
		int [] feedbackGivenPoints=(int[])ConvertUtils.convert(temp_feedbackGivenPoints.split(","), int.class);
		return  feedbackGivenPoints;
	}
	//public CompanyInfo getCompanyInfo() {
	//	return this.companyInfoManager.getDefaultCompany();
	//}

	protected final String getConfig(String key) {
		String confValue = getConfigMap().get(key);
		if (confValue == null) {
			logger
					.warn("Requesting config not found, register a default config. Config key is:["
							+ key + "].");
		}
		return confValue;
	}

	protected final Integer getConfig(String key, int defValue) {
		return new Integer(getConfig(key, "" + defValue));
	}

	protected final String getConfig(String key, String defValue) {
		String configValue = getConfig(key);

		return configValue == null ? defValue : configValue;
	}

	protected final String[] getConfigAsArray(String key) {
		return StringUtil.toArray(getConfig(key));
	}

	protected final String[] getConfigAsArray(String key, String delim) {
		return StringUtil.toArrayByDel(getConfig(key), delim);
	}

	protected final boolean getConfigAsBool(String key, boolean defValue) {
		return Boolean.parseBoolean(getConfig(key, "" + defValue));
	}

	protected final double getConfigAsDouble(String key, double defValue) {
		return Double.parseDouble(getConfig(key, "" + defValue));
	}

	protected final int getConfigAsInt(String key, int defValue) {
		return Integer.parseInt(getConfig(key, "" + defValue));
	}
	
	protected final Map<String, String> getConfigAsMap(String key, String defValue) {
		Map<String, String> map=new HashMap<String, String>();
		String configValue = getConfig(key);
		if(StringUtils.isBlank(configValue)){
			configValue=defValue;
		}
		if(StringUtils.isNotBlank(configValue)){
			String values[]=configValue.split(";");
			for (String value : values)
			{
				String val[]=value.split("=");
				if(val.length==2){
					if(StringUtils.isNotBlank(val[0])&&StringUtils.isNotBlank(val[1])){
						map.put(val[0].trim(), val[1].trim());
					}
				}
			}
		}
		return map;
	}

	protected Map<String, String> getConfigMap() {
		return systemConfigManager.getConfigAsMap();
	}

	public int getContinuouslyFailCountStopImport() {
		return getConfigAsInt("ContinuouslyFailCountStopImport", 50);
	}

	public int getCookieMaxAge() {
		return getConfig("CookieMaxAge", Constants.COOKIE_DEFAULT_MAXAGE);
	}

	public String getCouponEmailTemplate() {
		return getConfig("CouponEmailTemplate", null);
	}

	/**
	 * has leading / (when is not root web application), but no ending /.
	 * 
	 * @return
	 */
	public String getCtxPath() {
		return ctxPath;
	}

	public String getDownloadPath() {
		return getWebAppRootPath() + getConfig("DownloadPath", "/download");
	}

	public String getLowProductSkuEmailTemplate() {
		return "product/stockwarn.vm";
	}

	public String getGiftCertificateEmailTemplate() {
		return getConfig("GiftCertificateEmailTemplate", null);
	}

	public int getGiftCertificateExpireYears() {
		return getConfig("GiftCertificateExpireYears", 2);
	}

	public int getGiftCertificateMaxAmt() {
		return getConfig("GiftCertificateMaxAmt", 1000);
	}

	public int getGiftCertificateMinAmt() {
		return getConfig("GiftCertificateMinAmt", 10);
	}

	public int getRecommendedProductExpireYears() {
		return getConfig("RecommendedProductExpireYears", 2);
	}
	
	public boolean getIsAlertLicense() {
		return getConfigAsBool("IsAlertLicense", false);
	}

	public boolean getIsAnonymousCheckoutEnabled() {
		return getConfigAsBool("IsAnonymousCheckoutEnabled", true);
	}

	public boolean getIsAutoPostSiteMap() {
		return getConfigAsBool("IsAutoPostSiteMap", true);
	}

	/**
	 * @return the autoStartScheduler
	 */
	public boolean getIsAutoStartScheduler() {
		return this.autoStartScheduler;
	}

	public boolean getIsCancelOrderEnabled() {
		return getConfigAsBool("IsCancelOrderEnabled", true);
	}
	
	public boolean getIsRecalculateShippingAndTaxAfterModified() {
		return getConfigAsBool("IsRecalculateShippingAndTaxAfterModified", false);
	}

	// 是否允许删除系统的相关链接
//	public boolean getIsDeleteSystemSeeAlso() {
//		return this.getConfigAsBool("IsDeleteSystemSeeAlso", false);
//	}

	public boolean getIsDemoMode() {
		return "demo".equals(systemMode);
	}

	public boolean getIsDevMode() {
		return "dev".equals(systemMode);
	}
	
	public boolean getIsProductionMode() {
		return "production".equals(systemMode);
	}

	/**
	 * 根据约定的规则，根据emailCode读取相应的参数以判断该邮件是否启用。在ConfigRegistry应该有相应的缺省配置。
	 * 
	 * @param emailCode
	 * @return
	 */
	public boolean getIsEmailEnabled(final String emailCode) {
		return this.getConfigAsBool("Is" + StringUtils.capitalize(emailCode)
				+ "EmailEnabled", true);
	}

	public boolean getIsGzipEnabled() {
		return getConfigAsBool("IsPageGzipEnabled", true);
	}

	public boolean getIsRecentViewEnabled() {
		return getConfigAsBool("IsRecentViewEnabled", false);
	}

	/**
	 * customer is needed to confirm to activate, true or false return.
	 * 
	 * @return
	 */
	public Short getIsRegisterCustomerConfirmEnabled() {
		return Short.valueOf(getConfig("IsCustomerRegisterConfirmEnabled", "0"));
	}

	public boolean getIsRememberMeEnabled() {
		return rememberMeEnabled;
	}

	/**
	 * TODO, remove this method, not in use now
	 * 
	 * @return
	 */
	public boolean getIsServerSideDataCacheEnabled() {
		return serverSideDataCacheEnabled;
	}

	public boolean getIsShopPointPresentAfterPay() {
		return this.getConfigAsBool("IsShopPointPresentAfterPay", true);
	}

	public boolean getIsStoreFront() {
		return isStoreFront;
	}

	public boolean getIsSupportWrapExpress() {
		return this.getConfigAsBool("IsSupportWrapExpress", false);
	}

	public boolean getIsValidationCodeEnabled() {
		return getConfigAsBool("IsValidationCodeEnabled", false);
	}

	public int getLoginShopPointAmount() {
		return getConfigAsInt("LoginShopPointAmount", 1);
	}

	public int getMaxLoginFailTimes() {
		return getConfig("MaxLoginFailTimes", 3);
	}

	public String getMediaPath() {
		return getConfig("MediaStorePath", "/media");
	}

	public String getMediaStorePath() {
		return getAssetsPath() + getConfig("MediaStorePath", "/media");
	}

	public int getOrderRecentDays() {
		return getConfig("OrderRecentDays", 7);
	}

	//public String getPasswordEncryptionAlgorithm() {
	//	return getConfig("PasswordEncryptionAlgorithm", "SHA");
	//}

	public int getRegistrationShopPointAmount() {
		return getConfigAsInt("RegistrationShopPointAmount", 10);
	}

	public String getSearchServerUrl() {
		return searchServerUrl!= null ? searchServerUrl :
		    getConfig("SearchServerUrl", "http://localhost:8080/searchServer");
	}

	public int getServerSidePageCacheSeconds() {
		return getConfigAsInt("ServerSidePageCacheSeconds", 600);
	}

	public String getSiteMapPostUrl() {
		return getConfig("SiteMapPostUrl", "");
	}

	public String getStockAlertRecipientEmail() {
		return getConfig("StockAlertRecipientEmail");
	}

	public String getStoreAdminSiteUrl() {
		return getConfig("StoreAdminSiteUrl", "http://localhost:8080/StoreAdmin");
	}

	//public String getStoreFrontInstallationPath() {
	//	return storeFrontInstallationPath != null ? storeFrontInstallationPath
	//			: FileUtil.formatPath(getConfig("StoreFrontInstallationPath"));
	//}
	public String getAssetsPath() {
		return assetsPath != null ? assetsPath
				: FileUtil.formatPath(getConfig("AssetsPath"));
	}

	//public String getStoreFrontSiteUrl() {
	//	if (storeFrontSiteUrl == null)
	//		return getConfig("StoreFrontSiteUrl", "ERROR: StoreFrontSiteUrl_NOT_Configured");
	//	else
	//		return storeFrontSiteUrl;
	//}
	
	public Locale getSystemLocale() {		
		return I18nUtil.getLocaleByCode(getSystemLocaleCode());
	}

	public String getSystemLocaleCode() {
		return getIsStoreFront() ? getConfig("StoreFrontLocale", "zh_CN") : getConfig("StoreAdminLocale", "zh_CN");
	}

	public String getSystemMode() {
		return systemMode;
	}

	public int getValidationSessionTime() {
		return getConfig("ValidationSessionHours", 2);
	}

	public String getWebAppRootPath() {
		return this.webAppRootPath;
	}
	
	public String getWeightUnit() {
		return getConfig("WeightUnit", "g");
	}
	
	public String getLengthUnit() {
		return getConfig("LengthUnit", "cm");
	}
	
	public String getDefaultCurrencySymbol() {
		return getConfig("DefaultCurrencySymbol", "¥");
	}

	private void initContextPath(String in_ctxPath) {
		if ("/".equals(ctxPath)) {
			ctxPath = "";
		} else {
			ctxPath = in_ctxPath;
		}
		// use be used in EL like: ${applicationScope.ctxPath}
		RequestContext.getServletContext().setAttribute(Constants.CONTEXT_PATH, ctxPath);
		RequestContext.getServletContext().setAttribute(Constants.CONTEXT_RES_PATH, ctxPath+"/resources");
	}

	/**
	 * 包括更新数据库和缓存（Map）
	 * 
	 * @param configKey
	 * @param configValue
	 */
	public final void onConfigChanged(String configKey, String configValue) 
	{
		logger.info("Updating system all config by change configKey="+configKey);
		checkConfigAtStartup();
	}

	private final void saveInvalidConfigMessage(String configKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(I18nUtil.getInstance().getMessage("conf.name." + configKey));
		sb.append("]");
		sb.append(I18nUtil.getInstance().getMessage("conf.invalid.prompt"));
		sb.append("[");
		sb.append(I18nUtil.getInstance().getMessage("conf.err." + configKey));
		sb.append("]");
		String msg = sb.toString();
		RequestContext.saveSessionMessage(Message.infoMsg(msg));
		logger.warn(msg);
	}

	/**
	 * @param autoStartScheduler
	 *            the autoStartScheduler to set
	 */
	public void setAutoStartScheduler(boolean autoStartScheduler) {
		this.autoStartScheduler = autoStartScheduler;
	}

	/**
	 * @param rememberMeEnabled
	 *            the rememberMeEnabled to set
	 */
	public void setRememberMeEnabled(boolean rememberMeEnabled) {
		this.rememberMeEnabled = rememberMeEnabled;
	}

	public void setServerSideDataCacheEnabled(boolean avalue) {
		this.serverSideDataCacheEnabled = avalue;
	}

	public void setIsStoreFront(boolean isStoreFront) {
		this.isStoreFront = isStoreFront;
	}

	public void setAssetsPath(String path) {
		if (!StringUtil.isEmpty(path) && !unresolvablePlaceholders.matcher(path).matches()) {
			assetsPath = FileUtil.formatPath(path);
		}
	}
	
	//public void setStoreFrontSiteUrl(String url)
	//{
	//	if (!StringUtil.isEmpty(url) && !unresolvablePlaceholders.matcher(url).matches()) {
	//		storeFrontSiteUrl = url;
	//	}
	//}

	public void setSystemConfigManager(SystemConfigManager systemConfigManager) {
		this.systemConfigManager = systemConfigManager;
	}

	//public void setCompanyInfoManager(CompanyInfoManager companyInfoManager) {
	//	this.companyInfoManager = companyInfoManager;
	//}
	
	public void setStoreManager(StoreManager avalue)
	{
		this.storeManager = avalue;
	}

	public void setSystemMode(String systemMode) {
		Assert.isTrue(ArrayUtils.contains(supportedSystemModes, systemMode),
				"System mode [" + systemMode + "] is not supported.");
		this.systemMode = systemMode;
		logger.info("Using system mode:" + this.systemMode);
	}

	public void setWebAppRootPath(String webAppRootPath) {
		this.webAppRootPath = webAppRootPath;
	}
	
	public void setSearchServerUrl(String avalue)
	{
		if (!StringUtil.isEmpty(avalue) && !unresolvablePlaceholders.matcher(avalue).matches())
			searchServerUrl = avalue;
	}
	
	public int getOrderRobotReviewDelayMinutes(){
		return getConfigAsInt("OrderRobotReviewDelayMinutes", 45);
	}

	public String getOrderNoPrefix() {
		return orderNoPrefix;
	}

	public void setOrderNoPrefix(String avalue) {
		if (!StringUtil.isEmpty(avalue) && !unresolvablePlaceholders.matcher(avalue).matches())
		{
			if(avalue.length()>5)  //长度不能超过5位
				avalue = avalue.substring(0, 5);
			this.orderNoPrefix = avalue;
		}
	}
	
	/**
	 * 订单后缀
	 * @return
	 */
	public String getOrderNoSuffix() {
		return orderNoSuffix;
	}
	public void setOrderNoSuffix(String avalue) {
		if (!StringUtil.isEmpty(avalue) && !unresolvablePlaceholders.matcher(avalue).matches())
		{
			if(avalue.length()>5) //长度不能超过5位
				avalue = avalue.substring(0, 5);		
			this.orderNoSuffix = avalue;
		}
	}
	
	/**
	 * PO订单的前缀
	 * @return
	 */
	public String getPoNoPrefix() {
		return poNoPrefix;
	}
	public void setPoNoPrefix(String avalue) {
		if (!StringUtil.isEmpty(avalue) && !unresolvablePlaceholders.matcher(avalue).matches())
		{
			if(avalue.length()>5) //长度不能超过5位
				avalue = avalue.substring(0, 5);		
			this.poNoPrefix = avalue;
		}
	}
	
	public String getStoreSearchPath() {
		return this.getAssetsPath()+"/solrHome";
	}

	public String getSalesOrderPrefix() {
		return getConfig("SalesOrderPrefix", "S");
	}
	
	public Integer getPrecentOfProductCost(){
		return this.getConfigAsInt("precentOfProductCost", 80);
	}
	
	
		
	
	public String[] getBulkProdCommAttrs(){
		String temp[]=this.getConfig("BulkProdCommAttrs").split(",");
		while (ArrayUtils.contains(temp,"")) {
			temp=(String[]) ArrayUtils.removeElement(temp, "");
		}
		return temp;
	}
	
	public String[] getBulkSkuCommAttrs(){
		String temp[]=this.getConfig("BulkSkuCommAttrs").split(",");
		while (ArrayUtils.contains(temp,"")) {
			temp=(String[]) ArrayUtils.removeElement(temp, "");
		}
		return temp;
	}
	
	public String[] getBulkProdAttrs(){
		String temp[]=this.getConfig("BulkProdAttrs").split(",");
		while (ArrayUtils.contains(temp,"")) {
			temp=(String[]) ArrayUtils.removeElement(temp, "");
		}
		return temp;
	}
	
	
	public BigDecimal getShopPointUseGiftPercent(){
		BigDecimal shopPointUseGiftPercent=new BigDecimal(getConfigAsInt("ShopPointUseGiftPercent",10));
		shopPointUseGiftPercent=shopPointUseGiftPercent.divide(new BigDecimal(100));
		return shopPointUseGiftPercent;
	}
	
	public boolean getIsAllowSystemRulesWhenUseCoupon()
	{
		return this.getConfigAsBool("IsAllowSystemRulesWhenUseCoupon", false);
	}
	
	
	
	
	public String getGoogleMapAPIKey() {
		return this.getConfig("GoogleMapAPIKey", "");
	}
	
	//wholesale news 博客rss链接 
	public String getRssURL() {
		return this.getConfig("RssUrl", "");
	}
	
	public String getMailMarketerUrl() {
		return mailMarketerUrl;
	}

	public void setMailMarketerUrl(String mailMarketerUrl) {
		this.mailMarketerUrl = mailMarketerUrl;
	}

	public List<String[]> getIpAdressUserInfo() {
		List<String[]> users=new ArrayList<String[]>();
		String ipAdressUserInfo=this.getConfig("IpAdressUserInfo", "");
		if(StringUtils.isNotBlank(ipAdressUserInfo)){
			String temp_users[]=ipAdressUserInfo.split(";");
			for (String temp_user : temp_users) {
				if(StringUtils.isNotBlank(temp_user)){
					String temp_name_pwd[]=temp_user.split(",");
					if(temp_name_pwd.length>1){
						if(StringUtils.isNotBlank(temp_name_pwd[0])&&StringUtils.isNotBlank(temp_name_pwd[1]))
						users.add(new String[]{temp_name_pwd[0],temp_name_pwd[1]});
					}else if(temp_name_pwd.length==1){
						users.add(new String[]{temp_name_pwd[0],""});
					}
				}
			}
		}
		return users;
	}
	
	public int getOrderCancelDays() {
		return getConfig("OrderCancelDays", 7);
	}
	
	//订单自动取消时是否取消货到付款
	public boolean getOrderCancelIncludeCod() {
		return getConfigAsBool("OrderCancelIncludeCod",false);
	}
	
	public boolean getSitemapIncludeHomepage() {
		return this.getConfigAsBool("SitemapIncludeHomepage", true);
	}
	
	public boolean getSitemapIncludeProductCategories() {
		return this.getConfigAsBool("SitemapIncludeProductCategories", true);
	}
	public boolean getSitemapIncludeProducts() {
		return this.getConfigAsBool("SitemapIncludeProducts", true);
	}
	public boolean getSitemapIncludeContents() {
		return this.getConfigAsBool("SitemapIncludeContents", true);
	}
	
	public String getSitemapHomepageFrequency() {
		return this.getConfig("SitemapHomepageFrequency", "daily");
	}
	
	public String getSitemapProductCategoriesFrequency() {
		return this.getConfig("SitemapProductCategoriesFrequency", "daily");
	}
	
	public String getSitemapProductsFrequency() {
		return this.getConfig("SitemapProductsFrequency", "weekly");
	}
	
	public String getSitemapContentsFrequency() {
		return this.getConfig("SitemapContentsFrequency", "monthly");
	}
	
	public String getSitemapHomepagePriority() {
		return this.getConfig("SitemapHomepagePriority", "1.0");
	}
	
	public String getSitemapProductCategoriesPriority() {
		return this.getConfig("SitemapProductCategoriesPriority", "0.6");
	}
	
	public String getSitemapProductsPriority() {
		return this.getConfig("SitemapProductsPriority", "0.3");
	}
	
	public String getSitemapContentsPriority() {
		return this.getConfig("SitemapContentsPriority", "0.1");
	}
	
	public boolean getSitemapProductIncludeImages() {
		return this.getConfigAsBool("SitemapProductIncludeImages", false);
	}
	
	public String[] getSitemapContentCategories(){
		List<String>contentCategories=new ArrayList<String>();
		String temp[]=this.getConfig("SitemapContentCategories","").split(",");
		for (String string : temp) {
			if(StringUtils.isNotBlank(string)){
				contentCategories.add(string);
			}
		}
		return contentCategories.toArray(new String[]{});
	}
	
	public String getCmsAccessCategories() {
		return cmsAccessCategories;
	}

	public void setCmsAccessCategories(String cmsAccessCategories) {
		this.cmsAccessCategories = ","+cmsAccessCategories+",";
	}

//	public String getMediaUrlPath() {
//		return mediaUrlPath;
//	}
//
//	public void setMediaUrlPath(String mediaUrlPath) {
//		this.mediaUrlPath = mediaUrlPath;
//	}
	
	public String getTaoBaoAppKey() {
		return getConfig("TaoBaoAppKey", "");
	}
	
	public String getTaoBaoAppSecret() {
		return getConfig("TaoBaoAppSecret", "");
	}
	
	public List<String> getProductTemplates(){
		List<String> templateList=new ArrayList<String>();
		String templates[]=templateConfig.getProperty("product.templates").split(",");
		for (String template : templates) {
			if(StringUtils.isNotBlank(template)){
				templateList.add(template.trim());
			}
		}
		return templateList;
	}
	
	public List<String> getCategoryTemplates(){
		List<String> templateList=new ArrayList<String>();
		String templates[]=templateConfig.getProperty("category.templates").split(",");
		for (String template : templates) {
			if(StringUtils.isNotBlank(template)){
				templateList.add(template.trim());
			}
		}
		return templateList;
	}
	
	public List<String> getContentTemplates(){
		List<String> templateList=new ArrayList<String>();
		String templates[]=templateConfig.getProperty("content.templates").split(",");
		for (String template : templates) {
			if(StringUtils.isNotBlank(template)){
				templateList.add(template.trim());
			}
		}
		return templateList;
	}
	
	public List<String> getAdvertisementTemplates(){
		List<String> templateList=new ArrayList<String>();
		String templates[]=templateConfig.getProperty("advertisement.templates").split(",");
		for (String template : templates) {
			if(StringUtils.isNotBlank(template)){
				templateList.add(template.trim());
			}
		}
		return templateList;
	}
	
	public List<String> getSaleTemplates(){
		List<String> templateList=new ArrayList<String>();
		String templates[]=templateConfig.getProperty("sale.templates").split(",");
		for (String template : templates) {
			if(StringUtils.isNotBlank(template)){
				templateList.add(template.trim());
			}
		}
		return templateList;
	}
}

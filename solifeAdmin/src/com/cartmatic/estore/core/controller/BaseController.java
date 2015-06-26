/**
 * 
 */

package com.cartmatic.estore.core.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.event.ConfigChangedEvent;
import com.cartmatic.estore.core.event.ConfigChangedEventListener;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.core.util.UrlUtil;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 在MultiActionController的基础上提供一些与范型无关的常用方法。
 * <P>
 * 控制是否使用客户端页面缓存，在配置文件设置cacheSeconds即可。
 * 
 * @author Ryan
 * 
 */
public abstract class BaseController extends MultiActionController implements
		InitializingBean, ConfigChangedEventListener {

	protected static final String	FROM_ACTION_MARKER	= "IS_FORM_ACTION";

	protected static final String	NEW_ENTITY_MARKER	= "ENTITY_IS_NEW";
	
	protected static final String  EXPORT_FLAG   = "6578706f7274";

	public final static StringBuilder formatMsg(String msgWithFormat,
			Object... args) {
		return StringUtil.format(msgWithFormat, args);
	}

	private boolean		cacheSecondsConfigurable	= false;

	/**
	 * 在表单页面，取消编辑以及删除成功的时候转向的View，缺省是列表页。
	 */
	protected String	cancelFormView;

	private ConfigUtil	config						= null;

	

	/**
	 * Controller主要操作的实体的类名，框架会自动使用范型判断，一般不需要设置；如果自动判断失败，可以配置或在初始化设置。
	 */
	protected String	entityClassName;

	/**
	 * Controller主要操作的实体id（数据库和hibernate主键）的字段名，缺省是：{entityClassName}Id。
	 */
	protected String	entityIdName;

	/**
	 * 表单页面的model在request里面映射的参数名，框架缺省设置为{entityClassName}
	 */
	protected String	formModelName;

	/**
	 * 编辑页面使用的View，缺省是{lastPackageName}/{entityClassName}Form
	 */
	protected String	formView;

	/**
	 * 是否允许保存上次搜索条件和结果（只是id）。注意：允许会导致session里面保存很多数据，所以对于前台应用应该禁用。缺省是启用。
	 */
	protected boolean	lastSearchViewAllowed		= true;

	/**
	 * 列表页面的model在request里面映射的参数名，框架缺省设置为{entityClassName}List;其数据类型一般是List。
	 */
	protected String	listModelName;

	/**
	 * 列表页面使用的View，缺省是{lastPackageName}/{entityClassName}List
	 */
	protected String	listView;

	/**
	 * 在urlMapping映射的url，缺省是/{lastPackageName}/{entityClassName}.html
	 */
	protected String	mappedUrl;

	/**
	 * 在列表页面取消时返回的上级模块的view，缺省是重定向到/{lastPackageName}/index.html
	 */
	protected String	parentView;

	/**
	 * 在表单页面，保存成功后转向的View。缺省是{formView}，即保存后留在表单页。注意：saveAndNext即使成功还是会使用formView。
	 */
	protected String	successView;

	/**
	 * 记录导航的信息，包括SearchCriteria.
	 */
	//private List<NavigationModel> navigationList = new ArrayList<NavigationModel>();
	
	/**
	 * 
	 */
	public BaseController() {
		super();
	}

	/**
	 * 令到Spring在完成设置后调用，并进一步调用各Controller的初始化方法
	 * 
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		initPageCaching();
		initController();
	}
	
	protected final ServletRequestDataBinder bindAndValidate(
			HttpServletRequest request, Object entity) throws Exception {
		return bindAndValidate(request, entity, formModelName);
	}

	/**
	 * 理论上可以支持多次、多个对象的绑定
	 * 
	 * @param request
	 *            current HTTP request
	 * @param entity
	 *            the command to bind onto
	 * @param entity
	 *            the binding name
	 * @return the ServletRequestDataBinder instance for additional custom
	 *         validation
	 * @throws Exception
	 *             in case of invalid state or arguments
	 */
	protected final ServletRequestDataBinder bindAndValidate(HttpServletRequest request, Object entity, String bindName) throws Exception 
	{
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(entity, bindName);
		initBinder(request, binder);
		BindException errors = new BindException(binder.getBindingResult());
		if (!suppressBinding(request)) {
			binder.bind(request);
			if (getValidators() != null
					&& !suppressValidation(request, entity, errors)) 
			{
				for (int i = 0; i < getValidators().length; i++) 
				{
				    org.springframework.validation.Validator v = getValidators()[i];
				    //如果validator不支持当前的entity，则跳过不校验。
				   if (v.supports(entity.getClass()))
				        ValidationUtils.invokeValidator(v, entity, errors);
				}
			}
			onBindAndValidate(request, entity, errors);
		}
		return binder;
	}

	/**
	 * 说明：考虑不要保存浏览历史并依次返回的功能，比较复杂和难做好，而即使不提供这功能，用户也不会有明显的不便。返回到搜索列表通过指定的backToSearch功能实现。
	 * 所以对于Form，就返回列表；对于列表，就返回上层。如果想实现其他策略，重载本方法。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView cancelForm(HttpServletRequest request,
			HttpServletResponse response) {
		return getModelAndView(cancelFormView, null);
	}

	protected void debugRequest(HttpServletRequest request) {
		logger.debug(RequestUtil.getRequestInfo(request));
	}

	/**
	 * util function reference EL{ empty }
	 * 
	 * @param value
	 * @return
	 */
	protected final boolean empty(String value) {
		return (value == null || value.equals("")) ? true : false;
	}
	
	protected final void removeNavFromSearchCriteria(HttpServletRequest request)
	{
		request.getSession().removeAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST);
	}
	
	public Object getContextAttribute(String attrName) {
		return RequestContext.getCurrentHttpRequest().getSession()
				.getServletContext().getAttribute(attrName);
	}

	protected String getEntityTypeMessage() {
		return getMessage(entityClassName + "Detail.heading");
	}

	@Override
	public long getLastModified(HttpServletRequest request) {
		return getCacheSeconds() > 0 ? System.currentTimeMillis() : -1L;
	}


	/**
	 * Convenience method for getting a i18n key's value with arguments. User
	 * preferred locale will be tried first, then system default locale. If no
	 * text found, return ???msgKey???.
	 * 
	 * @param msgKey
	 * @param args
	 * @return
	 */
	protected final String getMessage(String msgKey,Object...args) {
		return super.getMessageSourceAccessor().getMessage(msgKey, args);
	}

	/**
	 * 方便的创建ModelAndView，适合Model里面有对个对象的用法，所以Model的填充应在传入之前完成。
	 * 
	 * @param viewName
	 * @param model
	 * @return
	 */
	protected final ModelAndView getModelAndView(final String viewName,
			final Map model) {
		return new ModelAndView(viewName, model);
	}

	/**
	 * 方便的创建ModelAndView，适合Model里面只有一个对象的用法。当然在创建后还是可以继续添加modelObject。
	 * 
	 * @param viewName
	 * @param modelName
	 * @param modelObject
	 * @return
	 */
	protected final ModelAndView getModelAndView(final String viewName,
			String modelName, Object modelObject) {
		return new ModelAndView(viewName, modelName, modelObject);
	}

	/**
	 * 需要支持批量更新的时候需要重载此方法。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	protected abstract Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request);

	protected final String getParameter(ServletRequest request, String name,
			String defaultVal) {
		String val = request.getParameter(name);
		return (val != null ? val : defaultVal);
	}


	/**
	 * 重定向指定的Action。
	 * <P>
	 * 说明：因为所有功能都基于action，而且在List页面和Form页面，form.action都是mappedUrl，不会带参数，所以重定向是个很好的方法，可以放心使用。
	 * 
	 * @param actionName
	 * @return
	 */
	protected ModelAndView getRedirectToActionView(String actionName) {
		return getRedirectView(UrlUtil.appendParamToUrl(mappedUrl, "doAction",
				actionName));
	}

	protected ModelAndView getRedirectToActionView(String actionName,
			String entityId) {
		return getRedirectView(UrlUtil.appendParamToUrl(UrlUtil
				.appendParamToUrl(mappedUrl, "doAction", actionName),
				entityIdName, entityId));
	}

	/**
	 * 转向指定的uri（框架自动加上Context Path）
	 * 
	 * @param uri
	 *            举例：/catalog/categorys_dy.html?doAction=doNotFoundAction
	 * @return
	 */
	protected final ModelAndView getRedirectView(final String uri) {
		return new ModelAndView(new RedirectView(uri, true));
	}


	/**
	 * 处理可恢复、可处理的（一般是用户或应用级别的，而不是系统级别的），这种错误需要在当前页面显示友好的错误信息。
	 * <P>
	 * 注意：如果是输入验证等的错误，应该用errors.reject等处理方法，而不要抛出这样的错误。这里主要是Mgr层处理（已经不能使用errors.reject）出错并需要显示友好错误信息的时候使用。
	 * <P>
	 * 说明：本来想用ExceptionHandler机制，但是那样很难设置errors和调用showForm了。
	 * 
	 * @param errors
	 *            会把具体错误信息添加到这里面并回传
	 * @param exception
	 *            具体的错误
	 */
	protected void handleApplicationException(BindException errors,
			ApplicationException exception) {
		if (exception.getErrorCode() != null) {
			errors.reject(exception.getErrorCode(), exception.getArgs(),
					exception.getMessage());
		}
		logger.warn("An application exception is being logged. "
				+ exception.getMessage());
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {

		NumberFormat nf = NumberFormat.getNumberInstance();
		SimpleDateFormat dtf = new SimpleDateFormat(DateUtil.getDatePattern());
		dtf.setLenient(false);
		// NumberFormat dcf = DecimalFormat.getNumberInstance();
		NumberFormat dcf = new DecimalFormat(
				BaseBinder.DEFAULT_BINDER_NUMBERFORMAT);

		binder.registerCustomEditor(BigDecimal.class, null,
				new CustomNumberEditor(BigDecimal.class, dcf, true));
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dtf,
				true));

		binder.registerCustomEditor(Integer.class, null,
				new CustomNumberEditor(Integer.class, nf, true));

		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, nf, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	/**
	 * 子类需要重载这个方法来实现初始化，包括设置范型mgr
	 * 
	 */
	protected abstract void initController() throws Exception;

	/**
	 * 缓存及时间控制策略：如果不允许缓存，设置为0来生成禁止缓存；如果允许缓存，优先使用XML配置的缓存时间（用来覆盖），否则使用系统统一的配置
	 * <ul>
	 * <li>cacheSecondsConfigurable=false,cacheSeconds：没有设置(-1):禁止缓存</li>
	 * <li>cacheSecondsConfigurable=false,cacheSeconds>0：使用xml设置的cacheSeconds而不是系统统一的缓存时间设置</li>
	 * <li>cacheSecondsConfigurable=true:使用系统统一的缓存时间设置</li>
	 * </ul>
	 */
	protected final void initPageCaching() {
		if (cacheSecondsConfigurable) {
			setCacheSeconds(config.getBrowserSidePageCacheSeconds());
		} else if (getCacheSeconds() == -1) {// 不使用系统统一配置，也没有在XML进行了显式配置，视为不允许缓存
			setCacheSeconds(0);
		} // 否则使用xml设置的cacheSeconds而不是系统统一的缓存时间设置，所以不需要修改什么
	}

	/**
	 * 用来区分新增还是更新的方法。
	 * 
	 * @param request
	 * @return
	 */
	protected final boolean isEntityNew(HttpServletRequest request) {
		return StringUtils.isEmpty(request.getParameter(entityIdName));
	}

	protected final boolean isFromFormAction(HttpServletRequest request) {
		return request.getAttribute(FROM_ACTION_MARKER) != null;
	}

	protected final void markIsFormAction(HttpServletRequest request) {
		request.setAttribute(FROM_ACTION_MARKER, Boolean.TRUE);
	}	

	/**
	 * Callback for custom post-processing in terms of binding and validation.
	 * Called on each submit, after standard binding and validation, but before
	 * error evaluation.
	 * <p>
	 * The default implementation is empty.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param command
	 *            the command object, still allowing for further binding
	 * @param errors
	 *            validation errors holder, allowing for additional custom
	 *            validation
	 * @throws Exception
	 *             in case of invalid state or arguments
	 * @see #bindAndValidate
	 * @see org.springframework.validation.Errors
	 */
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onConfigChanged(ConfigChangedEvent event) {
		if (cacheSecondsConfigurable) {
			logger.info("Updating browser side page cache time settings from: "
					+ getCacheSeconds() + " to: "
					+ config.getBrowserSidePageCacheSeconds());
			setCacheSeconds(config.getBrowserSidePageCacheSeconds());
		}
	}
	
	/**
	 * save message after operation
	 * 
	 * @param request
	 * @param msg
	 */
	protected final void saveMessage(HttpServletRequest request, Message message) {
		RequestContext.saveSessionMessage(request, message);
	}
	
	protected final void saveMessage(Message message) {
		RequestContext.saveSessionMessage(message);
	}

	/**
	 * Prepare model and view for the given form, including reference and
	 * errors, adding a controller-specific control model.
	 * <p>
	 * Can be used in subclasses to redirect back to a specific form page.
	 * 
	 * @param errors
	 *            validation errors holder
	 * @return the prepared form view
	 */
	protected ModelAndView showForm(HttpServletRequest request,
			BindException errors) {
		// Fetch errors model as starting point, containing form object under
		// "commandName", and corresponding Errors instance under internal key.
		Map model = errors.getModel();
		if (errors.hasErrors()) {
			if (errors.hasErrors()) {
				request.setAttribute("HAS_ERRORS", Boolean.TRUE);
			}
			markIsFormAction(request);			
		}		
		ModelAndView mv = getModelAndView(formView, model);
		onShowForm(request, mv);
		return mv;
	}
	
	/**
	 * showFrom时调用,可以重载这个方法在mv上加入一些新的元素
	 * @param request
	 * @param mv
	 */
	protected void onShowForm(HttpServletRequest request, ModelAndView mv)
	{}

	protected final boolean suppressBinding(HttpServletRequest request) {
		return request.getAttribute("SUPPRESS_BINDING") != null;
	}

	/**
	 * 缺省实现只是支持一个Request控制一次。
	 * 
	 * @param request
	 * @param command
	 * @param errors
	 * @return
	 */
	protected final boolean suppressValidation(HttpServletRequest request,
			Object command, BindException errors) {
		return request.getAttribute("SUPPRESS_VALIDATION") != null;
	}

	/**
	 * 返回上一层模块，由parentView指定
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView upToParent(HttpServletRequest request,
			HttpServletResponse response) {
		return getModelAndView(parentView, null);
	}
	
	/**
	 * @param cacheSecondsConfigurable
	 *            the cacheSecondsConfigurable to set
	 */
	public void setCacheSecondsConfigurable(boolean cacheSecondsConfigurable) {
		this.cacheSecondsConfigurable = cacheSecondsConfigurable;
	}

	/**
	 * @param cancelFormView
	 *            指定在Form页面，执行服务器端Cancel的时候用的View。注意：Delete操作也使用这个View。
	 */
	public void setCancelFormView(String cancelFormView) {
		this.cancelFormView = cancelFormView;
	}

	public void setConfigUtil(ConfigUtil configUtil) {
		this.config = configUtil;
	}	

	/**
	 * 指定在Form页面的View。
	 * 
	 * @param formView
	 *            the formView to set
	 */
	public void setFormView(String formView) {
		this.formView = formView;
	}

	public void setEntityClassName(String avalue)
	{
		this.entityClassName = avalue;
	}
	/**
	 * enable reuse of last search view or not. Should disable for StoreFront
	 * 
	 * @param lastSearchViewAllowed
	 */
	public void setLastSearchViewAllowed(boolean lastSearchViewAllowed) {
		this.lastSearchViewAllowed = lastSearchViewAllowed;
	}

	/**
	 * 指定列表、搜索页面的View。
	 * 
	 * @param listView
	 *            the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}

	/**
	 * 当自动生成的url映射不适用的时候，可以指定一个映射的url；一般是用于操作完成后转向用。
	 * 
	 * @param mappedUrl
	 */
	public void setMappedUrl(String mappedUrl) {
		this.mappedUrl = mappedUrl;
	}

	/**
	 * @param parentView
	 *            the parentView to set
	 */
	public void setParentView(String parentView) {
		this.parentView = parentView;
	}

	/**
	 * 指定在Form页面，成功（没有验证错误等）执行保存或其他更新类的操作后的View。缺省是直接用formView。
	 * 
	 * @param successView
	 *            the successView to set
	 */
	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setFormModelName(String avalue)
	{
		this.formModelName = avalue;
	}
	
	public final void setValidator(Validator validator) {
		super.setValidators(new Validator[] { validator });
	}
	
}
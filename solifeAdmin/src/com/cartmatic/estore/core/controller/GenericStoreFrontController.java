package com.cartmatic.estore.core.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.core.event.ConfigChangedEvent;
import com.cartmatic.estore.core.event.ConfigChangedEventListener;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public abstract class GenericStoreFrontController<T> extends MultiActionController implements InitializingBean,ConfigChangedEventListener
{

    /**
     * 缺省每页显示多少条记录，子类可在配置文件根据需要设置。缺省10。
     */
    protected int defaultPageSize = 10;

    protected GenericManager<T> mgr;

    private boolean cacheSecondsConfigurable = false;

    

    /**
     * When action parameter is not used/presented. decide view by parsing the
     * page name from URL.
     * 
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     */
    public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp)
    {
        String page = RequestUtil.getRequestedPageName(req, getDefaultViewName());
        return getModelAndView(page, null);
    }
    
    /**
     * 返回查询的list对象。
     * @param sc
     * @return
     */
    protected List<T> searchByCriteria(SearchCriteria sc) {
        if (sc.getPageNo() < 1)  //如果是翻页过前，就拿第一页就可以。
        {
            sc.changePaging(1, sc.getPageSize());
        }
        List<T> result = mgr.searchByCriteria(sc);
        if (result == null|| result.size() == 0 )
        {
            if (sc.getPageNo() > sc.getTotalPageCount()) //如果是翻页前，就拿最后一页就可以。
            {
                sc.changePaging(sc.getTotalPageCount(), sc.getPageSize());
                result = mgr.searchByCriteria(sc);
            }
        }
        return result;
    }

    /**
     * util function reference EL{ empty }
     * 
     * @param value
     * @return
     */
    protected boolean empty(String value)
    {
        return (value == null || value.equals("")) ? true : false;
    }

    /**
     * Get a baseBinder.
     * 
     * @return BaseBinder
     */
    protected BaseBinder getBinder()
    {
        return new BaseBinder();
    }

    /**
     * can override this
     * 
     * @return
     */
    protected String getDefaultViewName()
    {
        return "index";
    }

    /*
     * <p> Default implementation of getLastModified to make client browser sent
     * If-Modified-Since header. Subclass should overide this method to use
     * custom algorithm. Current system time will be used. </p>
     * 
     * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController#getLastModified(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public long getLastModified(HttpServletRequest request)
    {
        return getCacheSeconds() > 0 ? System.currentTimeMillis() : -1L;
    }

    /**
     * 创建SearchCriteria
     * @param request
     * @param in_filterName
     * @return
     */
    protected SearchCriteria createSearchCriteria(HttpServletRequest request, String in_filterName)
    {
        String filterName = in_filterName == null ? getParameter(request, SearchCriteria.PRM_FILTER_NAME, "default")
                            : in_filterName;
        SearchCriteria sc = mgr.getSearchCriteriaBuilder(filterName).buildSearchCriteria(request, defaultPageSize);
        // storeFront的是不会被保存
        sc.setSaveSearchResultsAllowed(false);
        sc.setSavedUri(request.getRequestURI());
        // 页面层需要用request（而不是session）里面的sc进行绑定（需要绑定column，filter，order等）
        request.setAttribute("sc", sc);
        return sc;
    }

    
    protected final String getParameter(ServletRequest request, String name, String defaultVal)
    {
        String val = request.getParameter(name);
        return (val != null ? val : defaultVal);
    }

    
    /**
     * 转向指定的uri（框架自动加上Context Path）
     * 
     * @param uri
     *            举例：/catalog/categorys_dy.html?doAction=doNotFoundAction
     * @return
     */
    protected ModelAndView getRedirectView(final String uri)
    {
        return new ModelAndView(new RedirectView(uri, true));
    }    

    /**
     * Convenience method for getting a i18n key's value. Calling
     * getMessageSourceAccessor() is used because the RequestContext variable is
     * not set in unit tests b/c there's no DispatchServlet Request.
     * 
     * @param msgKey
     * @return
     */
    protected String getText(String msgKey)
    {
        return getText(msgKey, null);
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
    protected String getText(String msgKey, Object[] args)
    {
        return getMessageSourceAccessor().getMessage(msgKey, args);
    }

    /**
     * 
     * @param msgKey
     * @param locale
     * @param defaultMsg
     * @return
     */
    protected String getTextOfLocale(String msgKey, Locale locale)
    {
        return getMessageSourceAccessor().getMessage(msgKey, locale);
    }

    public void init()
    {
        initPageCaching();
    }

    /**
     * 缓存及时间控制策略：如果不允许缓存，设置为0来生成禁止缓存；如果允许缓存，优先使用XML配置的缓存时间（用来覆盖），否则使用系统统一的配置
     * <ul>
     * <li>cacheSecondsConfigurable=false,cacheSeconds：没有设置(-1):禁止缓存</li>
     * <li>cacheSecondsConfigurable=false,cacheSeconds>0：使用xml设置的cacheSeconds而不是系统统一的缓存时间设置</li>
     * <li>cacheSecondsConfigurable=true:使用系统统一的缓存时间设置</li>
     * </ul>
     */
    protected final void initPageCaching()
    {
        if (cacheSecondsConfigurable)
        {
            setCacheSeconds(ConfigUtil.getInstance().getBrowserSidePageCacheSeconds());
        }
        else if (getCacheSeconds() == -1)
        {// 不使用系统统一配置，也没有在XML进行了显式配置，视为不允许缓存
            setCacheSeconds(0);
        }// 否则使用xml设置的cacheSeconds而不是系统统一的缓存时间设置，所以不需要修改什么
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if (cacheSecondsConfigurable)
        {
            logger.info("Updating browser side page cache time settings from: " + getCacheSeconds() + " to: "
                            + ConfigUtil.getInstance().getBrowserSidePageCacheSeconds());
            setCacheSeconds(ConfigUtil.getInstance().getBrowserSidePageCacheSeconds());
        }
    }
    
    protected void saveMessage(HttpServletRequest request, Message message) {
		RequestContext.saveSessionMessage(request, message);
	}

    /**
     * save message after operation
     * 
     * @param request
     * @param msg
     */
    protected void saveMessage(Message message)
    {
        RequestContext.saveSessionMessage(message);
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
     * 简化版本， 适用于不支持动态配置模版并且不需要传入Model的时候用。需要保证templatePath存在。
     * 
     * @param templatePath
     * @return
     */
    protected ModelAndView getModelAndView(final String templatePath)
    {
        return getModelAndView(templatePath, null, null);
    }

    /**
     * 适用于不支持动态配置模版的功能点，直接传入固定的模版路径，最终用户不应该修改该模版的路径。
     * 
     * @param templatePath
     * @param model
     * @return
     */
    protected ModelAndView getModelAndView(final String templatePath, final Map model)
    {
        return getModelAndView(templatePath, null, model);
    }

    /**
     * TODO,未完善，未测试，性能优化（如缓存）
     * 
     * @param templatePath
     *            不能用/开头
     * @param defaultTemplatePath
     *            没有指定模版，或指定的模版找不到的时候，使用的缺省模版
     * @param model
     * @return
     */
    protected ModelAndView getModelAndView(final String in_templatePath, final String defaultTemplatePath,
                    final Map model)
    {
        String templatePath = getTemplatePath(defaultTemplatePath, in_templatePath);
        return templatePath == null ? null : new ModelAndView(templatePath, model);
    }

    /**
     * TODO,未完善，未测试，性能优化（如缓存）
     * 
     * @param defaultTemplate
     *            没有指定模版，或指定的模版找不到的时候，使用的缺省模版
     * @param templatePath
     * @return
     */
    protected String getTemplatePath(String defaultTemplate, String templatePath)
    {
        // TODO 这个是否存在的检查应该缓存；另外，这样ViewResolver不再用检查是否存在了？
        if (empty(templatePath) || !isTemplateExists(templatePath))
        {
            if (logger.isInfoEnabled())
            {
                logger.info("Template not found for page: [" + templatePath + "].");
            }
            return defaultTemplate;
        }
        return templatePath;
    }

    protected boolean isTemplateExists(String templatePath)
    {
        return ContextUtil.getInstance().isFileExists(RequestContext.getRelativeTemplatePath(templatePath));
    }

    protected final String getMessage(String msgKey) {
		return super.getMessageSourceAccessor().getMessage(msgKey);
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
    
    /**
     * 子类需要重载这个方法来实现初始化，包括设置范型mgr
     * 
     */
    protected abstract void initController() throws Exception;
    
    /**
     * @param cacheSecondsConfigurable
     *            the cacheSecondsConfigurable to set
     */
    public void setCacheSecondsConfigurable(boolean cacheSecondsConfigurable)
    {
        this.cacheSecondsConfigurable = cacheSecondsConfigurable;
    }


    

    public void setDefaultPageSize(int avalue)
    {
        defaultPageSize = avalue;
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
	protected void handleApplicationException(BindingResult result,
			ApplicationException exception) {
		if (exception.getErrorCode() != null) {
			result.reject(exception.getErrorCode(), exception.getArgs(),
					exception.getMessage());
		}
		logger.warn("An application exception is being logged. "
				+ exception.getMessage());
	}
}

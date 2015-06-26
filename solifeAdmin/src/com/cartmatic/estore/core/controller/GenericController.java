/**
 * 
 */

package com.cartmatic.estore.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.AdminInfo;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.core.util.GenericsUtils;
import com.cartmatic.estore.core.util.UrlUtil;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * 支持范型的SpringMVC
 * Controller基类。能自动处理一些简单、基本的操作，例如CRUD，简单的批量更新、删除等，统一使用doAction参数控制。
 * <P>
 * 基于MultiActionController但是也支持大部分SimpleFormController的特性，并且用法类似。
 * <P>
 * 基本解决表单重复提交问题；包括浏览器退后并重新提交。
 * <P>
 * Controller与Manager不同，不需要基于接口编程。
 * <P>
 * 依赖：message必须有entityClassName的描述。
 * <P>
 * TODO:支持Export TODO:支持readOnlyView，printView TODO:
 * 
 * @author Ryan
 * 
 */
public abstract class GenericController<T> extends BaseController {

	private static final String	SEARCH_CRITERIA_IS_NEW	= "SearchCriteriaIsNew";
	/**
	 * 缺省每页显示多少条记录，子类可在配置文件根据需要设置。缺省10。
	 */
	protected int		defaultPageSize				= 0;
	
	protected Class<T>			entityClass;	// DAO所管理的Entity类型.

	protected GenericManager<T>	mgr;

	//private 
	/**
	 * 这里进行数据的初始化，根据约定的规则。如果要用自定义的规则，可以通过子类构造函数、init方法、setter方法等进行修改。
	 */
	public GenericController() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
     * 令到Spring在完成设置后调用，并进一步调用各Controller的初始化方法
     * 这里会初始化所有常用的变量值。
     * @throws Exception
     */
    public final void afterPropertiesSet() throws Exception {
        if (empty(entityClassName))
            entityClassName = entityClass.getSimpleName().substring(0, 1).toLowerCase() 
                              + entityClass.getSimpleName().substring(1);
        // 缺省的View设置，注意，页面首字母小写，如system/SystemLanguageList。可以在Bean设置以覆盖。
        String viewPrefix = new StringBuilder(
                        entityClass.getPackage().getName()
                        .substring(entityClass.getPackage().getName().lastIndexOf(".") + 1))
                        .append("/").append(entityClassName).toString();
        if (empty(listView))
            listView = viewPrefix + "List";
        if (empty(formView))
            formView = viewPrefix + "Form";
        if (empty(listModelName))
            listModelName = entityClassName + "List";
        if (empty(formModelName))
            formModelName = entityClassName;
        if (empty(entityIdName))
            entityIdName = entityClassName + "Id";
        if (empty(mappedUrl))
            mappedUrl = "/" + viewPrefix + ".html";
        if (empty(cancelFormView))
            cancelFormView = "redirect:" + mappedUrl;
        if (empty(parentView))
            parentView = "redirect:" + UrlUtil.getParentUrl(mappedUrl);
        super.afterPropertiesSet();
    }
	/**
	 * 新增。
	 * 主要是显示添"请录入新的XXXX详细信息"的信息提示。
	 * 会利用到 fooDetail.heading 的key。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
	    T entity = formBackingObject(request);
        BindException errors = null;
        // 会使用formModelName的名称进行绑定
        request.setAttribute("SUPPRESS_VALIDATION", Boolean.FALSE);
        ServletRequestDataBinder binder = bindAndValidate(request, entity);
        errors = new BindException(binder.getBindingResult());
        savedNavAndSearchCriteria(request, null, 
                        getMessage("common.message.addNew", new Object[]{getMessage(formModelName+"Detail.heading")}));
        return showForm(request, errors);
		//return edit(request, response);
	}

	/**
	 * 缺省实现：重用Edit，只是在session做标记，其他地方，例如保存、删除，以及页面层，应该根据编辑模式进行相应检查和优化。扩展：可增加权限控制功能。
	 * <P>
	 * 未测试
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView changeEditMode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String requestedMode = request.getParameter("EDIT_MODE");
		request.getSession().setAttribute("EDIT_MODE", requestedMode);
		return edit(request, response);
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			BindException errors) {
		// Fetch errors model as starting point, containing form object under
		// "commandName", and corresponding Errors instance under internal key.
		Map model = errors.getModel();
		if (isEntityNew(request) || errors.hasErrors()) {
			if (errors.hasErrors()) {
				request.setAttribute("HAS_ERRORS", Boolean.TRUE);
			}
			markIsFormAction(request);
			request.setAttribute("entityClassName", entityClassName);
		}
		request.setAttribute("SwitchItemEnabled", Boolean.TRUE);
		ModelAndView mv = getModelAndView(formView, model);
		onShowForm(request, mv);
		return mv;
	}
	/**
	 * 缺省的SC创建方法。如果需要指定（限定）Filter，可在search前通过在request里面指定。
	 * <P>
	 * 如果不需要限定，框架会自动从parameter里面分析用户的选择（如果没有安全性要求且页面上有输出的话），如果也找不到，缺省为default
	 * 
	 * @param request
	 * @return
	 */
	protected SearchCriteria createSearchCriteria(HttpServletRequest request) {
		return createSearchCriteria(request, (String) request
				.getAttribute(SearchCriteria.PRM_FILTER_NAME));
	}

	/**
	 * 根据Request自动创建SC，并可保存在Session，以便重用。SC里面也会保存Request原来的参数名和值（包括Filter和Column），页面必须使用SC里面保存的参数来绑定，而不是request里面的参数。
	 * <P>
	 * 一般有以下几种情况：
	 * <ol>
	 * <li>第一次请求，没有用户输入的搜索条件，使用缺省的搜索条件（使用名为default的filter对应的hql）</li>
	 * <li>对于只是翻页，不会创建新的SearchCriteria，只是更换页码</li>
	 * <li>对于新的搜索（不是returnToSearch或翻页），生成新的SC，不理是否与旧的SC相同，根据Filter，Column，Order等创建动态的SC</li>
	 * <li>每次会在session保存上一个搜索用的SC。注意：只是保存搜索条件，以及搜索结果的ID，实际的结果并不会保存，避免出现脏数据。</li>
	 * <li>对于在页面通过点击“返回上次搜索”，将重用session里面的SC</li>
	 * <li>对于点击连接等，也是当新的搜索处理，因为没有用户搜索条件，所以用缺省搜索条件</li>
	 * <li>通过浏览历史返回某搜索结果页面，由于搜索条件已经被更新，所以使用的是缺省搜索</li>
	 * </ol>
	 * <P>
	 * 注意：View层应保证所有条件，包括缺省条件可以正确绑定，并且如果该缺省条件是用户不能修改的，应该用隐藏域绑定。
	 * 
	 * @param request
	 * @param in_filterName
	 *            可指定使用的filter，从而不同的action可以使用不同的filter；缺省是从request里面自动分析或default
	 * @return
	 */
	protected SearchCriteria createSearchCriteria(HttpServletRequest request,
			String in_filterName) {
		SearchCriteria sc = null;
		// 如果是一个用户发起的ReturnToSearch请求，优先用保存的搜索条件。
		if (lastSearchViewAllowed && request.getAttribute("ReturnToSearch") != null) 
		{
		    sc = goPreviousNavSearchCriteria(request);
		}
		else if (lastSearchViewAllowed)
		{
		    // 一般的search，获得当前nav的sc就可以，翻页其实也用这个逻辑，但要更新页码。
		    sc = getCurrentNavSearchCriteria(request);
            if (sc != null && request.getParameter("PrmPageNo") != null) 
            {
                // 修改会自动反映到Session里面保存的SearchCriteria
                sc.changePaging(Integer.parseInt(request
                        .getParameter("PrmPageNo")), Integer.parseInt(request
                        .getParameter("PrmItemsPerPage")));
            }
		}

		if (sc == null || request.getParameter("btnSearch") != null) {// 如果没有允许或找不到（例如新的搜索），会构建新的SC
			String filterName = in_filterName == null ? getParameter(request,
					SearchCriteria.PRM_FILTER_NAME, "default") : in_filterName;
			sc = mgr.getSearchCriteriaBuilder(filterName).buildSearchCriteria(request, getPageSize());
			sc.setSaveSearchResultsAllowed(true);
			sc.setSavedUri(request.getRequestURI());
			//保存searchCriteria
			//this.savedNavAndSearchCriteria(request, sc, getMessage(listModelName+".heading"));
			this.savedNavAndSearchCriteria(request, sc, getMessage("menu.view" + entityClass.getSimpleName() + "s"));
			request.setAttribute(SEARCH_CRITERIA_IS_NEW, Boolean.TRUE);
		} else {
			logger.info("Reusing a saved SearchCriteria.");
		}
		if (request.getParameter(EXPORT_FLAG) != null) {// export
            if (request.getParameter("export-all") != null) {
                // export all result.
                if (request.getParameter("PrmPagePart") != null) {
                    // partition export
                    sc.changePaging(new Integer(request.getParameter("PrmPagePart")),
                                    new Integer(request.getParameter("PrmPageMaxResults")));
                } else {
                    sc.changePaging(1,0);
                }
            }
        }
		// 页面层需要用request（而不是session）里面的sc进行绑定（需要绑定column，filter，order等）
		request.setAttribute("sc", sc);
		return sc;
	}

	/**
	 * 缺省Action,列出缺省搜索条件的搜索结果列表。必须转给search处理。
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		return search(request, response);
	}

	/**
	 * 删除一条记录。并在应用级出错的时候显示出错信息。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model。因为要先保存其名称，否则可能会连I18N数据也已经删除了；另外可以用于出错的时候恢复显示Form页面
		T entity = formBackingObject(request);
		try {
			// 删除并设置信息
			String entityId = request.getParameter(entityIdName);
			if (!StringUtils.isEmpty(entityId)) {
				// 先构造提示信息，否则信息对应的记录可能也会被删除。
				String entityName = getEntityName(entity);
				String message = getMessage("common.deleted", new Object[] {
						getEntityTypeMessage(), entityName });
				entity = mgr.deleteById(new Integer(entityId));
				saveMessage(Message.info("common.deleted", getEntityTypeMessage(), entityName ));
			} else {
				saveMessage(Message.error("errors.invalid.delete.id", entityId));
			}
		} catch (ApplicationException e) {
			// 为了出错的时候可以恢复表单显示，构造errors，但是设置标记跳过绑定和验证
			request.setAttribute("SUPPRESS_BINDING", Boolean.TRUE);
			request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
			BindException errors = null;
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			handleApplicationException(errors, e);
			return showForm(request, errors);
		}

		return getModelAndView(cancelFormView, null);
	}

	/**
	 * 
	 * @param request
	 * @param sc
	 */
	protected final void savedNavAndSearchCriteria(HttpServletRequest request, 
	                SearchCriteria sc, String navName)
	{
	    //如果是导出的查询，跳过不用保存
	    if (request.getParameter(EXPORT_FLAG) != null)
	        return;
	    if (!lastSearchViewAllowed)
	    {
	        //request.getSession().setAttribute("navigationList", null);
	        return;
	    }
	    NavigationModel model = new NavigationModel();
	    model.setNavName(navName);
	    if (sc != null)
	    {
	        model.setIsFrom(false);
	        model.setUri(sc.getSavedUri());
	        model.setSearchCriteria(sc);
	    }
	    else
	    {
	        model.setIsFrom(true);
	        //如果不是edit的action就不要记录。
	        if ("edit".equals(request.getParameter("doAction")))
	        {
	            model.setUri(request.getRequestURI() + 
	                            "?doAction=edit&" + 
	                            entityIdName+"=" + request.getParameter(entityIdName));
	        }
	        else if ("add".equals(request.getParameter("doAction")))
	        {
	            model.setUri(request.getRequestURI() + "?doAction=add");
	        }
	        else
	        {
	            return;
	        }
	    }	    
	    
	    List<NavigationModel> navigationList = (List) request.getSession().getAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST);
	    if (navigationList == null)
	    {
	        navigationList = new ArrayList<NavigationModel>();
	    }
	    if (navigationList.isEmpty())
	    {
	        navigationList.add(model);
	    }
	    else
	    {
	        NavigationModel lastNav = navigationList.get(navigationList.size() - 1);
	        String lastUri = lastNav.getUri();
	        String lastUriPrefix = lastUri.substring(0, lastUri.indexOf(".html"));
	        if (logger.isDebugEnabled())
	            logger.debug("Nav's last uri="+lastNav.getUri()+" lastUriPrefix ="+lastUriPrefix );
	        while (!navigationList.isEmpty())
	        {
	            //当前的uri与nav中最后的uri不同
	            if (!lastUri.equals(model.getUri()))
	            {
	                if (model.getUri().startsWith(lastUriPrefix + "/"))
	                {
	                    navigationList.add(model);
	                    break;
	                }
	                //当前是from，上一个nav是list，由list页进入from页
	                else if (model.getIsFrom() && !lastNav.getIsFrom()
	                                && model.getUri().startsWith(lastUriPrefix))
	                {
	                    navigationList.add(model);
                        break;
	                }
	                //当前是from，上一个nav也是from，就是from页面之间的跳转，例如：前进或后退
	                else if (model.getIsFrom() && lastNav.getIsFrom() 
	                                && model.getUri().startsWith(lastUriPrefix))
	                {
	                    navigationList.set(navigationList.size() - 1, model);
	                    break;
	                }
	                else
	                    navigationList.remove(navigationList.size() - 1);
	            }
	            //当前的uri与nav中最后的uri相同，直接保存当前的sc代替原来的。
	            else
	            {
	                navigationList.set(navigationList.size() - 1, model);
	                break;
	            }
	        }
	        if (navigationList.isEmpty())
            {
                navigationList.add(model);
            }
	    }
	    if (logger.isDebugEnabled())
	        logger.debug("Saved a SearchCriteria");
	    request.getSession().setAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST, navigationList);
	}
	
	/**
	 * 编辑一条记录，是其他一些操作的基础。主要是读取数据，绑定（不验证），显示数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		T entity = formBackingObject(request);

		BindException errors = null;
		// Bind without validation, to allow for prepopulating a form, and for
		// convenient error evaluation in views (on both first attempt and
		// resubmit).
		// 会使用formModelName的名称进行绑定
		request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
		ServletRequestDataBinder binder = bindAndValidate(request, entity);
		errors = new BindException(binder.getBindingResult());
		savedNavAndSearchCriteria(request, null, 
		            getMessage("common.message.nav.editing", new Object[]{getEntityName(entity)}));
		//this.savedNavAndSearchCriteria(request, sc, getMessage(listModelName+".heading"));
		return showForm(request, errors);
	}

	/**
	 * 和Spring的SimpleFormController里面的方法的作用类似，取得当前form里面的command，在编辑之前应该先调用。
	 * <P>
	 * 此外，会判断是否新的表单，并在request设置。为了方便进行版本控制，牺牲一点性能，统一在第一次读的时候加锁。
	 * 
	 * @param request
	 * @return
	 */
	protected T formBackingObject(HttpServletRequest request) {
		markIsFormAction(request);
		String entityId = request.getParameter(entityIdName);
		T entity = null;

		if (!StringUtils.isEmpty(entityId)) {
			try {
				entity = mgr.loadById(new Integer(entityId));
			} catch (Exception ex) {
				logger
						.info(formatMsg(
								"Error retrieving entity [%1 : %2]. So assume it is new.",
								entityClassName, entityId, ex));
				saveMessage(Message.error("errors.invalid.edit.id", entityId));
			}
		}
		if (entity == null) {
			try {
				entity = entityClass.newInstance();
				// TODO, move to showForm will be better?
				request.setAttribute(NEW_ENTITY_MARKER, Boolean.TRUE);
			} catch (Throwable e) {// should not happen
				new RuntimeException("Unexpected error!", e);
			}
		}
		request.setAttribute("entityClassName", entityClassName);
		return entity;
	}

	/**
	 * 提供一个先处理，后储存formBackingObject的方法，这种模式可以较好的减少事务、死锁的问题，因为不用先读一次。而处理后又可以很好的兼容原来的的模式。
	 * 
	 * @param request
	 * @param in_entity
	 * @return
	 */
	protected T formBackingObject(HttpServletRequest request, T in_entity) {
		markIsFormAction(request);
		T entity = in_entity;
		if (entity == null) {
			try {
				entity = entityClass.newInstance();
				// TODO, move to showForm will be better?
				request.setAttribute(NEW_ENTITY_MARKER, Boolean.TRUE);
			} catch (Throwable e) {// should not happen
				new RuntimeException("Unexpected error!", e);
			}
		}
		request.setAttribute("entityClassName", entityClassName);
		return entity;
	}

	/**
	 * 子类应该告诉框架，Entity实例的名称（可支持I18n）是什么
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract String getEntityName(T entity);

	public ModelAndView handleApplicationException(HttpServletRequest request,
			HttpServletResponse response, ApplicationException exception) {
		logger
				.error("Unhandled exception in controller processing.",
						exception);
		if (exception.getErrorCode() != null) {
//			saveMessage(request, getMessage(exception.getErrorCode(), exception.getArgs()));
			saveMessage(Message.error(exception.getErrorCode(), exception.getArgs()));
		} else {
			//@ TODO 可能是直接的提示信息,不需要读取资源文件
//			saveMessage(request, exception.getMessage());
			saveMessage(Message.errorMsg(exception.getMessage()));
		}
		return defaultAction(request, response);
	}

	protected final boolean isSearchCriteriaNew(HttpServletRequest request) {
		return request.getAttribute(SEARCH_CRITERIA_IS_NEW) != null;
	}

	/**
	 * 批量删除记录。批量处理的模式：应该重用单个实体处理的方法，否则，就会出现处理不一致的情况。本模式能保证事务完整性。成功后重定向到列表页面，以消除重复提交问题。
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		String[] ids = request.getParameterValues("multiIds");
		if (ids != null && ids.length > 0) {
			mgr.deleteAllByIds(ids);
			saveMessage(Message.info("common.deleted.multi", new Object[] {getEntityTypeMessage(), ids.length }));
		}
		return getRedirectToActionView("search");
	}

	/**
	 * 多条记录的批量更新，本模式并能保证事务完整性。Controller应该构造更新的model，然后传给Mgr更新，整个更新过程应该放在事务的一个方法进行。
	 * <P>
	 * Controller不应该直接操作Entity。Mgr对Controller传入的Model采用Copy的方式。
	 * <P>
	 * 对于复杂、多个实体的情况，应该采用类似模式，但要实现自己的方法（重载）。
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView multiSave(HttpServletRequest request,
			HttpServletResponse response) {
		Map<Integer, Map<String, Object>> multiUpdateModel = getMultiSaveModel(request);
		if (multiUpdateModel != null && multiUpdateModel.size() > 0) {
			mgr.saveOrUpdateAll(multiUpdateModel);
//			String message = getMessage("common.updated.multi", new Object[] {
//					getEntityTypeMessage(), multiUpdateModel.size() });
			saveMessage(Message.info("common.updated.multi"));
		}
		return getRedirectToActionView("search");
	}

	/**
	 * 子类需要实现此方法，进行保存之前的绑定、验证、转换等处理。
	 * 
	 * @param request
	 * @param entity
	 * @param errors
	 */
	protected abstract void onSave(HttpServletRequest request, T entity,
			BindException errors);

	/**
	 * 缺省实现：简单重用View（不可编辑）。可以支持对doAction=print进行单独的权限控制，应设置使用不同的装饰器。注意：打印是临时操作，所以不应作为一种新的mode处理。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView print(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return view(request, response);
	}

	/**
	 * 返回上次保存的搜索结果列表。
	 * <P>
	 * 存在问题：只能返回defaultAction，而不能返回之前的action（因为不同的action其实都可以进行搜索）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView returnToSearch(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Restoring from a saved SearchCriteria");
		request.setAttribute("ReturnToSearch", Boolean.TRUE);
		return search(request, response);
	}

	/**
	 * 保存单个记录的数据，并可以处理应用级的错误信息。在formBackingObject读数据的时候已经加锁，所以可以保证事务和版本控制。子类需要实现onSave。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		T entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				mgr.save(entity);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, new Object[] {getEntityTypeMessage(), getEntityName(entity)}));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		ModelAndView mav;
		if (errors.hasErrors()) {
			mav = showForm(request, errors);
		} else if (successView != null) {
			mav = getModelAndView(successView, errors.getModel());
		} else {
			mav = getRedirectToActionView("edit", ((BaseObject) entity).getId()
					.toString());
		}
		return mav;
	}

	/**
	 * 成功保存或新增后，直接转到下一个工作件。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveAndNext(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = save(request, response);
		// 只有在成功（不出错）的时候才会转到下个工作件，否则直接用save传回来的mav。
		if (request.getAttribute("HAS_ERRORS") == null) {
			if (isEntityNew(request)) {
				return getRedirectToActionView("add");
			} else {
				return nextItem(request, response);
			}
		}

		return mav;
	}

	/**
	 * 编辑下一个工作件。比较好的支持GET的方式（推荐），对于需要比较多的参数的POST的方式需要自己处理。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView nextItem(HttpServletRequest request,HttpServletResponse response) 
	{
//		SearchCriteria sc = (SearchCriteria) request.getSession().getAttribute(
//				"SavedSearchCriteria");
	    // 从session中，得到navigation来进行查询。
	    SearchCriteria sc = getPreviousNavSearchCriteria(request);		
		Serializable nextItemId = null;
		Integer workingItemId = new Integer(request.getParameter(entityIdName));
		if (sc != null && workingItemId != null)
		{
		    List results = searchByCriteria(sc);
		    for (int i = 0; i < results.size(); i++ ) 
		    {
	            BaseObject obj = (BaseObject) results.get(i);
	            if (obj.getId().equals(workingItemId))//找到id
	            {
	                if ( (i + 1) < results.size())
	                    nextItemId = ((BaseObject) results.get(i + 1)).getId();
	                else if (sc.getTotalPageCount() > sc.getPageNo())//翻下一页
	                {
	                    sc.changePaging(sc.getPageNo() + 1, sc.getPageSize());
	                    results = searchByCriteria(sc);
	                    if (results.size() > 0)
	                    {
	                        //取第一个记录的id
	                        nextItemId = ((BaseObject) results.get(0)).getId();
	                    }
	                }
	                break;
	            }
	        }		    
			/*List<Serializable> searchResultIds = sc.getSearchResultIds();
			if (workingItemId != null && searchResultIds != null) {
				int idx = searchResultIds.indexOf(workingItemId);
				if (idx != -1) {
					int nextIdx = idx + 1;
					if (nextIdx < searchResultIds.size()) {
						nextItemId = searchResultIds.get(nextIdx);
					} else if (sc.getPageNo() < sc.getTotalPageCount()) {
						// 查询下一页的工作件
						sc.changePaging(sc.getPageNo() + 1, sc.getPageSize());
						List results = searchByCriteria(sc);
						if (results.size() > 0) {
							nextItemId = sc.getSearchResultIds().get(0);
						}
					}
				}// else WORKING_ITEM_NOT_FOUND
			}*/
		}
		if (nextItemId == null) {// not found, not available or unexpected
			// situations
			saveMessage(Message.error("common.message.next.item.not.found", getEntityTypeMessage()));
			nextItemId = workingItemId;
		}
		return getRedirectToActionView("edit", nextItemId.toString());
	}
	
	/**
	 * 编辑上一个工作件。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView prevItem(HttpServletRequest request,
			HttpServletResponse response) {
//		SearchCriteria sc = (SearchCriteria) request.getSession().getAttribute(
//				"SavedSearchCriteria");
	    SearchCriteria sc = getPreviousNavSearchCriteria(request);
		//TODO  将使用navigation代替
		Serializable nextItemId = null;
		Integer workingItemId = new Integer(request.getParameter(entityIdName));
		if (sc != null  && workingItemId != null)
		{
		    List results = searchByCriteria(sc);
            for (int i = 0; i < results.size(); i++ ) 
            {
                BaseObject obj = (BaseObject) results.get(i);
                if (obj.getId().equals(workingItemId))//找到id
                {
                    if ( i > 0 )
                        nextItemId = ((BaseObject) results.get(i - 1)).getId();
                    else if (sc.getPageNo() > 1)//翻上一页
                    {
                        sc.changePaging(sc.getPageNo() - 1, sc.getPageSize());
                        results = searchByCriteria(sc);
                        if (results.size() > 0)
                        {
                            //取最后一个id
                            nextItemId = ((BaseObject) results.get(results.size() - 1)).getId();
                        }
                    }
                    break;
                }
            }
		    
/*			List<Serializable> searchResultIds = sc.getSearchResultIds();
			if (workingItemId != null && searchResultIds != null) {
				int idx = searchResultIds.indexOf(workingItemId);
				if (idx != -1) {
					int nextIdx = idx - 1;
					if (nextIdx >= 0) {
						nextItemId = searchResultIds.get(nextIdx);
					} else if (sc.getPageNo() > 1) {// start of this page, try
						// prev page
						// 查询上一页的工作件
						sc.changePaging(sc.getPageNo() - 1, sc.getPageSize());
						List results = searchByCriteria(sc);
						if (results.size() > 0) {
							nextItemId = sc.getSearchResultIds().get(
									sc.getSearchResultIds().size() - 1);
						}
					}
				}// else WORKING_ITEM_NOT_FOUND
			}*/
		}
		if (nextItemId == null) {// not found, not available or unexpected
			// situations
			saveMessage(Message.error("common.message.prev.item.not.found", getEntityTypeMessage()));
			nextItemId = workingItemId;
		}
		return getRedirectToActionView("edit", nextItemId.toString());
	}
	/**
	 * 缺省、统一、规范的搜索入口。SearchCriteria可以继续扩展支持Hql、Hibernate
	 * Criteria、JDBC等不同的实现。但Hql无疑最简单，推荐。
	 * <P>
	 * 一般来说所有的搜索最终都应该调用这个方法。对于复杂列表页面，很可能还需要其他Model，可以重载或实现自己的Action，调用search得到mv，然后处理并添加新的model。
	 * <P>
	 * returnToSearch的时候，只会返回此方法（除非覆盖了）；但因为使用的是保存的SC，所以不传入filterName也可正常工作。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		SearchCriteria sc = createSearchCriteria(request);
		List results = searchByCriteria(sc);

		return getModelAndView(listView, listModelName, results);
	}

	/**
	 * 根据SearchCriteria进行搜索。可以使用createSearchCriteria等预建的方法快速、自动构造SC，也可以用自己定制的SC。
	 * 
	 * @param sc
	 * @return
	 */
	
	protected List searchByCriteria(SearchCriteria sc) {
	    if (sc.getPageNo() < 1)  //如果是翻页过前，就拿第一页就可以。
        {
            sc.changePaging(1, sc.getPageSize());
        }
	    List result = mgr.searchByCriteria(sc);
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
     * 返回上一步的searchCriteria,nav会进行后退的操作，删除当前的nav记录
     * 主要是for returnForSearch
     * @param request
     * @return
     */
    protected final SearchCriteria goPreviousNavSearchCriteria(HttpServletRequest request)
    {
        SearchCriteria result = null;
        List<NavigationModel> navigationList = (List) request.getSession().getAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST);
        if (navigationList != null && !navigationList.isEmpty())
        {
            navigationList.remove(navigationList.size()-1);
            if (navigationList.isEmpty())
                result = null;
            else
            {
                NavigationModel lastNav = navigationList.get(navigationList.size() - 1);
                if (request.getRequestURI().startsWith(lastNav.getUri()))
                {
                    result = lastNav.getSearchCriteria();
                }
            }
        }
        return result;
    }
	
	/**
	 * 获得返回上一步的searchCriteria
	 * 主要是for preItem和nextItem
	 * @param request
	 * @return
	 */
	protected final SearchCriteria getPreviousNavSearchCriteria(HttpServletRequest request)
	{
	    SearchCriteria result = null;
	    List<NavigationModel> navigationList = (List) request.getSession().getAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST);
	    if (navigationList != null && navigationList.size() >= 2)
	    {
            NavigationModel lastNav = navigationList.get(navigationList.size() - 2);
            if (request.getRequestURI().startsWith(lastNav.getUri()))
            {
                result = lastNav.getSearchCriteria();
            }
	    }
	    return result;
	}
	
	/**
     * 获得当前的searchCriteria，如果uri不对的话就会清空nav的session
     * @param request
     * @return
     */
	protected final SearchCriteria getCurrentNavSearchCriteria(HttpServletRequest request)
	{
	    SearchCriteria result = null;
        List<NavigationModel> navigationList = (List) request.getSession().getAttribute(com.cartmatic.estore.Constants.KEY_NAVIGATION_LIST);
        if (navigationList != null && !navigationList.isEmpty()&&navigationList.size()>0)
        {
            NavigationModel lastNav = navigationList.get(navigationList.size() - 1);
            if (request.getRequestURI().startsWith(lastNav.getUri()))
            {
                result = lastNav.getSearchCriteria();
            }
        }
        return result;
	}
	
	protected void setSearchFilterName(HttpServletRequest request,
			String filterName) {
		request.setAttribute(SearchCriteria.PRM_FILTER_NAME, filterName);
	}

	/**
	 * 查看一条记录，只读，不能编辑。缺省实现：重用Edit。参考changeEditMode。可能可以简化到不需要此方法，纯粹用changeEditMode控制session里面的Mode。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return edit(request, response);
	}
	
	/**
	 * @param defaultPageSize
	 *            the defaultPageSize to set
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	
	public int getPageSize(){
		if(defaultPageSize>0){
			return defaultPageSize;
		}
		AdminInfo adminInfo = RequestContext.getAdminInfo();
		if (adminInfo.getPagingSetting() != null)
			return adminInfo.getPagingSetting();
		else
			return 10;
	}
}

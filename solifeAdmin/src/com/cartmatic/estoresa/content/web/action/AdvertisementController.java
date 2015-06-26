package com.cartmatic.estoresa.content.web.action;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.content.Advertisement;
import com.cartmatic.estore.common.model.content.ProductAdvertisement;
import com.cartmatic.estore.content.service.AdPositionTypeManager;
import com.cartmatic.estore.content.service.AdvertisementManager;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;

public class AdvertisementController extends GenericController<Advertisement> {
    private AdvertisementManager advertisementManager = null;
    
    private AdPositionTypeManager adPositionTypeManager=null;

    public void setAdPositionTypeManager(AdPositionTypeManager adPositionTypeManager) {
		this.adPositionTypeManager = adPositionTypeManager;
	}

	public void setAdvertisementManager(AdvertisementManager inMgr) {
        this.advertisementManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Advertisement entity) {
		return entity.getAdvertisementName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = advertisementManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Advertisement advertisement, BindException errors) {
		Advertisement advertisement2=advertisementManager.getAdvertisementByName(advertisement.getAdvertisementName());
		if(advertisement2!=null&&(advertisement.getAdvertisementId()==null||advertisement.getAdvertisementId().intValue()!=advertisement2.getAdvertisementId())){
			errors.rejectValue("advertisementName","advertisement.name.exists");
		}
		String isInclude=request.getParameter("isInclude");
		if(isInclude!=null&&isInclude.equals("1")){
        	advertisement.setIsInclude(Short.valueOf("1"));
        }
        else{
        	advertisement.setIsInclude(Short.valueOf("0"));
        }
		//关联目录可以为空
		/*String categoryIds[]=request.getParameterValues("categoryIds");
		if(categoryIds==null||categoryIds.length==0){
			errors.reject("advertisement.error.categoryIds.empty");
		}*/
	}
	
	
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		Advertisement entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				String categoryIds[]=request.getParameterValues("categoryIds");
				advertisementManager.saveAdvertisement(entity, categoryIds);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(), getEntityName(entity)));
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

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Advertisement advertisement = (Advertisement) mv.getModel().get(formModelName);
		Set<ProductAdvertisement> productAdvertisements=advertisement.getProductAdvertisements();
		if (productAdvertisements != null && productAdvertisements.size() > 0) {
			CatalogHelper catalogHelper = CatalogHelper.getInstance();
			for (ProductAdvertisement productAdvertisement : productAdvertisements) {
				Category cat = catalogHelper.getCategoryById(productAdvertisement.getCategoryId());
				productAdvertisement.setCategoryPathName(cat != null ? cat.getCategoryPathName() : productAdvertisement.getCategoryId()+"");
			}
		}
		request.setAttribute("positions",adPositionTypeManager.getAll());
	}
}
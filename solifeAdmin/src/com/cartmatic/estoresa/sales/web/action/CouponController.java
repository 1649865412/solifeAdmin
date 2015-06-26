package com.cartmatic.estoresa.sales.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.service.CouponManager;
/**
 * 后台优惠券显示controller
 * @author CartMatic
 *
 */
public class CouponController extends GenericController<Coupon> {
    private CouponManager couponManager = null;

    public void setCouponManager(CouponManager inMgr) {
        this.couponManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Coupon entity) {
		return entity.getCouponName();
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
		mgr = couponManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Coupon entity, BindException errors) {
	}
	
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		SearchCriteria searchCriteria=createSearchCriteria(request);
		String promoRule = request.getParameter("promoRule");
		List results = new ArrayList();
		if(promoRule != null){
			if(!promoRule.equals("")){
				results =couponManager.searchCoupons(searchCriteria, promoRule);
			}
		}else{
			results=searchByCriteria(searchCriteria);
		}
	 
		return getModelAndView(listView, listModelName, results);
	}
}

package com.cartmatic.estoresa.customer.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.service.MembershipManager;

public class MembershipController extends GenericController<Membership> {
    private MembershipManager membershipManager = null;

    public void setMembershipManager(MembershipManager inMgr) {
        this.membershipManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Membership entity) {
		return entity.getMembershipName();
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
		mgr = membershipManager;
	}
	@Override
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}
	
	@Override
	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		String[] ids = request.getParameterValues("multiIds");
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
                if (StringUtils.isNotEmpty(ids[i])) {
                    Membership membership = membershipManager.getById(new Integer(ids[i]));
                    String membershipName=membership.getMembershipName();
                    if (CustomerConstants.MEMBERSHIP_LEVEL_ANONYMOUS.equals(membership.getMembershipLevel())||CustomerConstants.MEMBERSHIP_LEVEL_BASE.equals(membership.getMembershipLevel())) {
                    	saveMessage(Message.error("membership.cannotDeleted", membershipName));
                    } else if(membership.getAppUsers().size()>0){
                    	//getAppUsers().size()考虑直接count
                        saveMessage(Message.error("membership.canntDelete", new Object[] {membershipName,String.valueOf(membership.getAppUsers().size())}));
                    }else {
                        mgr.deleteById(new Integer(ids[i]));
                        saveMessage(Message.info("membership.deleted", membershipName));
                    }
                }
            }
		}
		return getRedirectToActionView("search");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Membership membership, BindException errors) {
		//新增的默认设置非删除状态
		if(membership.getMembershipId()==null){
            membership.setDeleted(new Short((short)0));
        }
		boolean isLevelExist=membershipManager.isExistLevel(membership);
		if(isLevelExist){
    	    errors.rejectValue("membershipLevel","membership.errors.LevelRepeat","Membership Level Has Repeat!");
    	}
		boolean isUpgradeExist = membershipManager.isExistUpgradePoint(membership);
    	if(isUpgradeExist){
    		errors.rejectValue("upgradeShopPoint", "membership.errors.UpgradeShopPoint","UpgradeShopPoint has existed!");
    	}
    	/***
    	 * 保证会员资格等级越高升级积分越多的规则.
    	 * 只有0或1条记录满足时是可以修改的.
    	 * 例如会员资格等级和升级积分分别有普通会员:1,500,高级VIP:3,3000.此时要插入VIP:2,1000的话,
    	 * 要判断2>1并升级积分1000>500.
    	 */
    	boolean isBigToFront = membershipManager.isBigToFront(membership);
    	if(!isBigToFront){
    		errors.rejectValue("upgradeShopPoint", "membership.errors.isBigToFront","not big to front");
    	}
	}
	
	public ModelAndView getMemberships(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		List<Map<String,Object>>data=new ArrayList<Map<String,Object>>();
		ajaxView.setData(data);
		List<Membership> shippingMethods = membershipManager.getMembershipsOrderLevel(new Membership());
		for (Membership membership : shippingMethods) {
			Map<String,Object> ct=new HashMap<String, Object>();
			ct.put("membershipId", membership.getMembershipId());
			ct.put("membershipName", membership.getMembershipName());
			data.add(ct);
		}
		return ajaxView;
	}
}

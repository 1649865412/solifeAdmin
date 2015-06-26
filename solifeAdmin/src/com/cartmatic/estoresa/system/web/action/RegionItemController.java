package com.cartmatic.estoresa.system.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.service.RegionItemManager;
import com.cartmatic.estore.system.service.RegionManager;

public class RegionItemController extends GenericController<RegionItem> {
    private RegionItemManager regionItemManager = null;
    private RegionManager regionManager=null;
    
    

    public void setRegionManager(RegionManager regionManager) {
		this.regionManager = regionManager;
	}

	public void setRegionItemManager(RegionItemManager inMgr) {
        this.regionItemManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(RegionItem entity) {
		return entity.getRegionItemName();
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
		mgr = regionItemManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, RegionItem entity, BindException errors) {
	}

	public ModelAndView getRegionItemsByRegionId(HttpServletRequest request,HttpServletResponse response) {
		Integer regionId=new Integer(request.getParameter("regionId"));
		List<RegionItem>regionItemList=regionItemManager.getRegionItemsByRegionId(regionId);
		return new ModelAndView("/system/include/subRegionList","regionItemList",regionItemList);
	}

	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer regionItemId=new Integer(request.getParameter("regionItemId"));
			Integer itemId=new Integer(request.getParameter("itemId"));
			Region item=regionManager.getById(itemId);
			regionItemManager.deleteById(regionItemId);
			ajaxView.setMsg(getMessage("region.remove.item.successed",item.getRegionName()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer regionId=new Integer(request.getParameter("regionId"));
			Integer itemId=new Integer(request.getParameter("itemId"));
			Region item=regionManager.getById(itemId);
			RegionItem regionItem=new RegionItem();
			regionItem.setRegionId(regionId);
			regionItem.setItemId(itemId);
			boolean exist=regionItemManager.isExistRegionItemForRegionAndItem(regionId, itemId);
			if(!exist){
				regionItemManager.save(regionItem);
				ajaxView.setStatus(new Short("1"));
				ajaxView.setMsg(getMessage("region.add.item.successed",item.getRegionName()));
			}else{
				ajaxView.setStatus(new Short("0"));
				ajaxView.setMsg(getMessage("region.error.add.item.exists",item.getRegionName()));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("region.error.add.item"));
			e.printStackTrace();
		}
		return ajaxView;
	}
}
package com.cartmatic.estore.system.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.service.RegionManager;

public class RegionSelectorController extends GenericController<Region> {
	private RegionManager	regionManager	= null;

	public void setRegionManager(RegionManager inMgr) {
		this.regionManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController
	 * getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Region entity) {
		return entity.getRegionName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现
	 * 。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = regionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController
	 * onSave(javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Region region,
			BindException errors) {
		
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		
	}

	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model。因为要先保存其名称，否则可能会连I18N数据也已经删除了；另外可以用于出错的时候恢复显示Form页面
		return null;
	}

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response)  {
		String uri=request.getRequestURI();
		try {
			if(uri.indexOf("/byType.html")!=-1){
				return getActiveRegionsByType(request, response);
			}else if(uri.indexOf("/byParentIdAndType.html")!=-1){
				return getRegionByParentIdAndType(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	@Override
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	@Override
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		return null;
	}
	
	public ModelAndView getActiveRegionsByType(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer regionType=new Integer(request.getParameter("regionType"));
			List<Region>regionList=regionManager.getActiveRegionByType(regionType);
			JSONArray jsonRegionList=regionListToJson(regionList);
			ajaxView.setData(jsonRegionList);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	public ModelAndView getRegionByParentIdAndType(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer pid=new Integer(request.getParameter("pid"));
			Integer regionType=new Integer(request.getParameter("regionType"));
			List<Region>regionList=regionManager.getRegionByParentId(pid, regionType);
			JSONArray jsonRegionList=regionListToJson(regionList);
			ajaxView.setData(jsonRegionList);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	private JSONArray regionListToJson(List<Region>regionList){
		List<Map<String,Object>> jsonRegionList=new ArrayList<Map<String,Object>>();
		for (Region region : regionList) {
			Map<String,Object> jsonRegion=new HashMap<String, Object>();
			jsonRegion.put("id",region.getId());
			jsonRegion.put("name", region.getRegionName());
			jsonRegionList.add(jsonRegion);
		}
		JSONArray jsonArray=JSONArray.fromObject(jsonRegionList);
		return jsonArray;
	}
}

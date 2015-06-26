
package com.cartmatic.estoresa.system.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.content.JTreeNode;
import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.common.model.system.RegionItem;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.system.service.RegionManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class RegionController extends GenericController<Region> {
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
		Region temp_region = regionManager.getRegionByCode(region.getRegionCode());
		if (temp_region != null && region.getRegionId() != null
				&& region.getRegionId().intValue() != temp_region.getRegionId()) {
			errors.rejectValue("regionCode", "region.code.exists");
		}
		if(isEntityNew(request)){
			region.setStatus(new Integer(1));
		}
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Region region = (Region) mv.getModel().get(formModelName);
		if (isEntityNew(request)) {
			String regionType = request.getParameter("regionType");
			if (StringUtils.isNotEmpty(regionType)) {
				region.setRegionType(new Integer(regionType));
			} else {
				region.setRegionType(new Integer(4));
			}
		}
		if (region.getParentRegionId() != null) {
			try {
				Region parentRegion = regionManager.getById(region
						.getParentRegionId());
				if (parentRegion != null)
					region.setParentRegionName(parentRegion.getRegionName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		//return a paging list
        if (RequestUtil.isSelectorUri(request))
	        return regionSelector(request, response);
        else{
        	return search(request, response);
        }
	}

	private ModelAndView regionSelector(HttpServletRequest request,HttpServletResponse response) {
		String regionId=request.getParameter("regionId");
		List<JTreeNode> childJTreeNodes = new ArrayList<JTreeNode>();
		if(StringUtils.isNotBlank(regionId)){
			boolean isRoot="0".equals(regionId);
			try {
				List childRegions=new ArrayList();
				String regionType=request.getParameter("regionType");//地区类型:all-->所有地区（默认），system-->系统内置，custom-->客户订制的，默认是all
				if("system".equalsIgnoreCase(regionType)){
					childRegions=regionManager.getChildRegions(Integer.parseInt(regionId),isRoot,"system");
				}else if("custom".equalsIgnoreCase(regionType)){
					childRegions=regionManager.getChildRegions(Integer.parseInt(regionId),isRoot,"custom");
				}else{//all
					childRegions=regionManager.getChildRegions(Integer.parseInt(regionId),isRoot,"all");
				}
				if(childRegions.size()>0){
					for (Iterator it = childRegions.iterator(); it.hasNext();){
						Region region=(Region)it.next();
						JTreeNode treeNode=new JTreeNode();
						treeNode.setText(region.getRegionName());
						treeNode.setId(region.getRegionId().toString());
						if(regionManager.haveChildRegion(region.getRegionId())){
							treeNode.setLeaf(false);
						}else{
							treeNode.setLeaf(true);
						}
						childJTreeNodes.add(treeNode);
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		System.out.println("childJTreeNodes.size()[" + childJTreeNodes.size()+"],regionId = "+regionId);
		request.setAttribute("childJTreeNodes", childJTreeNodes);
		return new ModelAndView("/system/regionSelector");
	}

	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		String regionType=request.getParameter("regionType");
		//@ TODO 不懂原来为什么要放到session
        /*HttpSession session=request.getSession();
        if(regionType==null){
        	if(session.getAttribute("regionType")!=null)
              regionType=session.getAttribute("regionType").toString();	
        }
        if (regionType == null || "".equals(regionType))
			regionType = "4";
        session.setAttribute("regionType",regionType);*/
		if(StringUtils.isBlank(regionType)){
			regionType="4";
		}
        
    	SearchCriteria sc =regionManager.getSearchCriteriaBuilder("selectByTypeAndStatus").buildSearchCriteria(request, 10);
		request.setAttribute("sc", sc);
		sc.addParamValue(new Integer(regionType));
		sc.addParamValue(new Integer(1));//@ TODO modified by liangyuandong,2008-10-24 14:25,原来是0
		List<Region> results=regionManager.searchByCriteria(sc);
		if(results!=null){//设置自定义区域的子区域名称
        	Map<Integer,String> hm=new HashMap<Integer,String>();
			for (Region region : results) {
				Set<RegionItem> regionItems=(Set<RegionItem>)region.getRegionItems();
        		if(regionItems.size()>0){
        			Set<Integer> regionIds=new HashSet<Integer>();
            		for (RegionItem regionItem : regionItems) {
            			regionIds.add(regionItem.getItemId());
            		}
            		List<Region> itemRegions=regionManager.findRegionByIds(regionIds);
            		for (Region itemRegion : itemRegions) {
						hm.put(itemRegion.getRegionId(), itemRegion.getRegionName());
					}
            		for (RegionItem regionItem : regionItems) {
            			if(hm.containsKey(regionItem.getItemId())) regionItem.setItemName(hm.get(regionItem.getItemId()));
            			else regionItem.setItemName(String.valueOf(regionItem.getItemId()));
            		}
            		hm.clear();
        		}
			}
		}
		
		return getModelAndView(listView, listModelName, results);
	}
}

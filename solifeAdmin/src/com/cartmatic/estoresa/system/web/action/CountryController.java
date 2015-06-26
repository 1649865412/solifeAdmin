
package com.cartmatic.estoresa.system.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.system.service.RegionManager;

public class CountryController extends GenericController<Region> {
	private RegionManager		regionManager	= null;
	private String				cityView;
	private static final String	Country_LIST	= "countryList";

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response){
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'CountryController defaultAction' method...");
		}
		if (request.getRequestURI().endsWith("city/dialog.html")) {
			return this.showCity(request, response);
		} else {
			return search(request, response);
		}
	}
	
	

	
	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria sc =regionManager.getSearchCriteriaBuilder("selectByType").buildSearchCriteria(request, getPageSize());
		request.setAttribute("sc", sc);
		sc.addParamValue(1);
		List<Region> results=regionManager.searchByCriteria(sc);
		for (Region region : results) {
			List<Region>  subRegions = regionManager.getSubRegion(region.getRegionId());
			if (subRegions.size() > 0) {
				region.setSubRegion(subRegions);
			}
		}
		return new ModelAndView("system/countryList", Country_LIST, results);
	}

	private ModelAndView showCity(HttpServletRequest request,HttpServletResponse response) {
		String stateId = request.getParameter("stateId");
		if (StringUtils.isNotEmpty(stateId)) {
			try {
				List<Region> cityes = regionManager.getSubRegion(Integer.parseInt(stateId));
				request.setAttribute("cities", cityes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ModelAndView mv = new ModelAndView(this.getCityView());
		mv.addObject("state",regionManager.getById(Integer.parseInt(stateId)));
		return mv;
	}


	public void setRegionManager(RegionManager manager) {
		this.regionManager = manager;
	}

	public String getCityView() {
		return cityView;
	}

	public void setCityView(String cityView) {
		this.cityView = cityView;
	}

	@Override
	protected String getEntityName(Region entity) {
		return entity.getRegionName();
	}

	@Override
	protected void onSave(HttpServletRequest request, Region entity,
			BindException errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		String[] multiIds = request.getParameterValues("multiIds");
		Map<Integer, Map<String , Object>> map = new HashMap<Integer, Map<String, Object>>();
		if (multiIds != null && multiIds.length != 0) {
			for (int i = 0; i < multiIds.length; i++) {
				Integer id = Integer.valueOf(multiIds[i]);
				String status = request.getParameter("status_" + id);
				if (StringUtils.isBlank(status)||!status.equals("1")) {
					status = "0";
				}
				Map<String, Object> entityModel = new HashMap<String, Object>();
				entityModel.put("status", Integer.valueOf(status));
				map.put(id, entityModel);
			}
		}
	    return map;
	}

	@Override
	protected void initController() throws Exception {
		mgr = regionManager;
	}
}

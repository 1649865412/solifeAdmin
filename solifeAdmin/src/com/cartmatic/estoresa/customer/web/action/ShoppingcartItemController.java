package com.cartmatic.estoresa.customer.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.cart.service.ShoppingcartItemManager;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;

public class ShoppingcartItemController extends GenericController<ShoppingcartItem>{

	private ShoppingcartItemManager shoppingcartItemManager = null;
	private String statView = null;
	/**
	 * 根据url来决定采用什么样的查询.
	 * 
	 */
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) 
	{
		String uri = request.getRequestURI();
		if (uri.endsWith("shoppingcartItemStat.html"))
		{
			return searchStat(request, response);
		}
		
		return search(request, response);
	}
	
	/**
	 * 统计shoppingcartItem
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView searchStat(HttpServletRequest request, HttpServletResponse response) 
	{
		SearchCriteria sc = null;
		if (empty(request.getParameter("SRH@filter")))
		{
			sc = createSearchCriteria(request,"statItem");
		}
		else sc = createSearchCriteria(request);
		//List results = shoppingcartItemManager.searchInShoppingcartStat(sc);
		List results = searchByCriteria(sc);
		//去掉导航信息
		removeNavFromSearchCriteria(request);
		return getModelAndView(statView, listModelName, results);
	}
	
	
	
	@Override
	protected String getEntityName(ShoppingcartItem entity) {
		
		return entity.getShoppingcartItemId().toString();
	}
	
	@Override
	protected void initController() throws Exception {
		mgr = shoppingcartItemManager;
	}
	
	@Override
	protected void onSave(HttpServletRequest request, ShoppingcartItem entity,
			BindException errors) {
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setShoppingcartItemManager(ShoppingcartItemManager avalue)
	{
		shoppingcartItemManager = avalue;
	}
	
	public void setStatView(String avalue)
	{
		statView = avalue;
	}
}

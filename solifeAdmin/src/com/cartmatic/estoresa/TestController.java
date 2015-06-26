/**
 * 
 */
package com.cartmatic.estoresa;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * @author Ryan
 * 
 */
public class TestController extends BaseController {
	private CustomerManager customerManager;
	private ContentManager contentManager;
	private AttributeService attributeService;
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.webapp.action.BaseListController#defaultAction(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)  
	 */
	
	public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// TODO Auto-generated method stub
		return getModelAndView(RequestUtil.getRequestedPageName(req, "index"), null);
	}

	


	
	public ModelAndView formplugin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String[]>paramMap=request.getParameterMap();
		Set<Entry<String, String[]>>paramMapEntrys=paramMap.entrySet();
		Map m=new HashMap();
		List l=new ArrayList();
		for (Entry<String, String[]> paramMapEntry : paramMapEntrys) {
			System.out.println(paramMapEntry.getKey()+Arrays.toString((String[])paramMapEntry.getValue()));
			m.put(paramMapEntry.getKey(),paramMapEntry.getValue());
			l.add(paramMapEntry.getKey());
		}
		Product p=new Product();
		p.setProductName("N82");
		ProductSku sku=new ProductSku();
		sku.setProductSkuCode("skuCode__n82");
		p.setDefaultProductSku(sku);
		Set<ProductSku> skuSet=new HashSet<ProductSku>();
		skuSet.add(sku);
		p.setProductSkus(skuSet);
		m.put("product",p);
		JSONObject jsonMap=JSONObject.fromObject(m);
		JSONArray jsonList=JSONArray.fromObject(l);
;        PrintWriter out = response.getWriter();
        System.out.println(jsonList.toString());
//        out.println(jsonList.toString());
        
		return null;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setContentManager(ContentManager contentManager) {
		this.contentManager = contentManager;
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}

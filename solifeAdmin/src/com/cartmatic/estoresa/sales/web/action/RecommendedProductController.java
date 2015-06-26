
package com.cartmatic.estoresa.sales.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.sales.service.RecommendedProductManager;
import com.cartmatic.estore.sales.service.RecommendedTypeManager;
/**
 * 后台关联推荐产品controller
 * @author CartMatic
 *
 */
public class RecommendedProductController extends
		GenericController<RecommendedProduct> {
	private RecommendedProductManager	recommendedProductManager	= null;
	private RecommendedTypeManager		recommendedTypeManager;

	public void setRecommendedProductManager(RecommendedProductManager manager) {
		this.recommendedProductManager = manager;
	}

	public void setRecommendedTypeManager(
			RecommendedTypeManager recommendedTypeManager) {
		this.recommendedTypeManager = recommendedTypeManager;
	}

	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sales/recommendedProduct");
		List<RecommendedProduct> results = recommendedProductManager
				.getRecommendedProductsByRecommendedTypeIdBySourceId(
						new Integer(request.getParameter("recommendedTypeId")),
						new Integer(request.getParameter("sourceId")), 0, -1);
		formatDateToDisplay(results);
		setState(results);
		mv.addObject("recommendedProductList", results);
		return mv;
	}

	

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String recommendedProductId = request.getParameter("recommendedProductId");
			RecommendedProduct recommendedProduct = recommendedProductManager.getById(new Integer(recommendedProductId));
			BaseBinder binder = new BaseBinder();
			binder.bind(request, recommendedProduct, "recommendedProduct");
			recommendedProductManager.save(recommendedProduct);
			
			String msgKey = "common.updated";
			String message = getMessage(msgKey, new Object[] { getEntityTypeMessage(),
					getEntityName(recommendedProduct) });
			Map<String, Object> data = new HashMap<String, Object>();
			ajaxView.setMsg(message);
			data.put("recommendedTypeId", recommendedProduct.getRecommendedTypeId());
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String bind_pix = request.getParameter("bind_pix");
			RecommendedProduct recommendedProduct = new RecommendedProduct();
			BaseBinder binder = new BaseBinder();
			binder.bind(request, recommendedProduct, "recommendedProduct",bind_pix);
			RecommendedProduct recommendedProductInDB = recommendedProductManager
					.getRecommendedProduct(recommendedProduct
							.getRecommendedTypeId(), recommendedProduct
							.getSourceId(), recommendedProduct.getProductId());
			String message;
			String msgKey;
			Map<String, Object> data = new HashMap<String, Object>();
			if (recommendedProductInDB == null) {
				recommendedProduct.setSortOrder(10);
				recommendedProduct.setStatus(Constants.FLAG_TRUE);
				recommendedProduct.setStartTime(new Date());
				recommendedProduct.setExpireTime(recommendedProductManager.getDefaultRecommendedProductExpireTime());
				recommendedProductManager.save(recommendedProduct);
				recommendedProductManager.evict(recommendedProduct);//为了显示提示信息
				recommendedProduct = recommendedProductManager.loadById(recommendedProduct.getRecommendedProductId());
				
				msgKey = "common.added";
				message = getMessage(msgKey, new Object[] { getEntityTypeMessage(),	getEntityName(recommendedProduct) });
				
				
//				msgKey = "common.added";
				ajaxView.setMsg(message);
				ajaxView.setStatus(new Short("1"));
			} else {
				ajaxView.setMsg(getMessage("recommendedProduct.repeat"));
				ajaxView.setStatus(new Short("2"));
			}
			data.put("recommendedTypeId", recommendedProduct.getRecommendedTypeId());
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
		
	}

	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String recommendedProductId = request.getParameter("recommendedProductId");
			Integer recommendedTypeId = new Integer(request.getParameter("recommendedTypeId"));
			RecommendedType recommendedType = recommendedTypeManager.getById(new Integer(recommendedTypeId));
			RecommendedProduct recommendedProduct = recommendedProductManager.getById(new Integer(recommendedProductId));
			recommendedProductManager.delete(recommendedProduct);
			String msgKey = "common.deleted";
			String message = getMessage(msgKey, new Object[] { getEntityTypeMessage(),getEntityName(recommendedProduct) });
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("recommendedTypeId", recommendedType.getRecommendedTypeId());
			ajaxView.setMsg(message);
			ajaxView.setStatus(new Short("1"));
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	protected String getEntityName(RecommendedProduct entity) {
		return entity.getProduct().getProductName();
	}

	protected void onSave(HttpServletRequest request,
			RecommendedProduct entity, BindException errors) {

	}

	protected void initController() throws Exception {

	}

	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		throw new RuntimeException("Not implemented yet!");
	}
	
	private void formatDateToDisplay(List rs)
    {
        for (int i = 0; i < rs.size(); i++)
        {
            RecommendedProduct rp = (RecommendedProduct) rs.get(i);
            if (rp.getRecommendedProductId() != null)
            {
            	if (rp.getExpireTime() != null)
                rp.setDisplayExpireTime(DateUtil.convertDateToString(rp.getExpireTime()));
            	if (rp.getStartTime() != null)
                rp.setDisplayStartTime(DateUtil.convertDateToString(rp.getStartTime()));
            }
        }
    }
	
	private void setState(List<RecommendedProduct> results) {
		for(RecommendedProduct recommendedProduct : results){
			recommendedProductManager.setState(recommendedProduct);
		}
		
	}

}

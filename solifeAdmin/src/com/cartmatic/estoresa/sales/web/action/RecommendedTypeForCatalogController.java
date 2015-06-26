
package com.cartmatic.estoresa.sales.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.model.RecommendedTypeSearchCriteria;
import com.cartmatic.estore.sales.service.RecommendedTypeManager;
import com.cartmatic.estore.webapp.util.RequestUtil;
/**
 * 后台关联推荐类型列表显示controller
 * @author CartMatic
 *
 */
public class RecommendedTypeForCatalogController extends
		GenericController<RecommendedType> {
	private RecommendedTypeManager	recommendedTypeManager	= null;

	public void setRecommendedTypeManager(
			RecommendedTypeManager recommendedTypeManager) {
		this.recommendedTypeManager = recommendedTypeManager;
	}

	protected String getEntityName(RecommendedType entity) {
		return entity.getRecommendTitle();
	}

	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		throw new RuntimeException("Not implemented yet!");
	}

	protected void initController() throws Exception {
		mgr = recommendedTypeManager;
	}

	protected void onSave(HttpServletRequest request, RecommendedType entity,
			BindException errors) {
	}

	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {

	}
	
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		String sourceType = RequestUtil.getParameterNullSafe(request, "sourceType");
		RecommendedTypeSearchCriteria recommendedTypeSearchCriteria = new RecommendedTypeSearchCriteria();
		if(SalesConstants.RECOMMENDED_TYPE_SOURCE_CATEGORY.equals(sourceType)){
			recommendedTypeSearchCriteria.setIsApplyToCategory(Constants.FLAG_TRUE);
			recommendedTypeSearchCriteria.setIsApplyToProduct(Constants.FLAG_FALSE);
		}else{
			recommendedTypeSearchCriteria.setIsApplyToCategory(Constants.FLAG_FALSE);
			recommendedTypeSearchCriteria.setIsApplyToProduct(Constants.FLAG_TRUE);
		}
		recommendedTypeSearchCriteria.setStatus(Constants.FLAG_TRUE);
		
		List results = recommendedTypeManager.getRecommendedTypesBySearchCriteria(recommendedTypeSearchCriteria);
		Map model = new HashMap();
		model.put("recommendedTypeList", results);
		model.put("sourceType", sourceType);
		return getModelAndView("sales/recommendedTypeForCatalog",model);
	} 
}

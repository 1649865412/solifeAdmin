package com.cartmatic.estoresa.catalog.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductPromotionController extends GenericController<Product> {
    private PromoService promoService ;
    private ProductManager productManager;

	
	public void setPromoService(PromoService promoService) {
		this.promoService = promoService;
	}
	
	

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Product entity) {
		return entity.getProductName();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Product entity, BindException errors) {
	}

	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = getModelAndView("/catalog/include/productPromotion",null);
		String productId = RequestUtil.getParameterNullSafe(request, "productId");
		if(!productId.trim().equals("")){
			Product product = productManager.getById(Integer.parseInt(productId));
			Collection<ProductSku> catalogPromotionResults = new ArrayList<ProductSku>();
			Collection<ProductSku> shoppingcartPromotionResults = new ArrayList<ProductSku>();
			Set<ProductSku> skus = product.getProductSkus();
			for(ProductSku sku : skus){
				Collection<ProductSku> result = promoService.getPromoInfoUsedInProductModule(sku);
				for(ProductSku item : result){
					if(PromoRule.PROMOTION_TYPE_CATALOGPROMOTION.equals(item.getPrule().getRuleType())){
						catalogPromotionResults.add(item);
					}else{
						shoppingcartPromotionResults.add(item);
					}
				}
			}
			mv.addObject("catalogPromotionResults", catalogPromotionResults);
			mv.addObject("shoppingcartPromotionResults", shoppingcartPromotionResults);
		}
		return mv;
	}
}

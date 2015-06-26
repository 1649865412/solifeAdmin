package com.cartmatic.estoresa.supplier.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductPriceController extends GenericController<ProductSku> {
	private ProductSkuManager productSkuManager = null;
	private ProductMainManager productMainManager = null;

	public void setProductSkuManager(ProductSkuManager inMgr) {
		this.productSkuManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ProductSku entity) {
		return entity.getProductSkuName();
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
		mgr = productSkuManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ProductSku productSku, BindException errors) {
	}

	/**
	 * 保存单个记录的数据，并可以处理应用级的错误信息。在formBackingObject读数据的时候已经加锁，所以可以保证事务和版本控制。子类需要实现onSave。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		ajaxView.setStatus(new Short("-500"));
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("entering 'ProductPriceController save' method...");
			}
			Integer skuId=ServletRequestUtils.getIntParameter(request,"skuId",0);
			Map<String, Object> data = new HashMap<String, Object>();
			ajaxView.setData(data);
			data.put("skuId",skuId);
			if(skuId>0){
				ProductSku productSku=productSkuManager.getById(skuId);
				BigDecimal price=new BigDecimal(request.getParameter("input_price_"+skuId));
				productSku.setPrice(price);
				data.put("price", price);
				BigDecimal salePrice=null;
				try {
					salePrice=new BigDecimal(request.getParameter("input_salePrice_"+skuId));
				} catch (Exception e) {
				}
				productSku.setSalePrice(salePrice);
				data.put("salePrice", salePrice==null?"":salePrice);
				
				BigDecimal costPrice=null;
				costPrice=new BigDecimal(request.getParameter("input_costPrice_"+skuId));
				productSku.setCostPrice(costPrice);
				data.put("costPrice", costPrice==null?"":costPrice);
				
				BigDecimal listPrice=null;
				try {
					listPrice=new BigDecimal(request.getParameter("input_listPrice_"+skuId));
				} catch (Exception e) {
				}
				productSku.setListPrice(listPrice);
				data.put("listPrice", listPrice==null?"":listPrice);
				
				//准备批发价数据
				String wholesalePrice_prices[] = RequestUtil.getParameterValuesNullSafe(request, "input_wholesalePrice_price_"+skuId);
				String wholesalePrice_minQuantitys[] = RequestUtil.getParameterValuesNullSafe(request, "input_wholesalePrice_minQuantity_"+skuId);
				String wholesalePrices[] = new String[wholesalePrice_prices.length];
				for (int i = 0; i < wholesalePrice_prices.length; i++) {
					String wholesalePrice_price = wholesalePrice_prices[i];
					String wholesalePrice_minQuantity = wholesalePrice_minQuantitys[i];
					wholesalePrices[i] = wholesalePrice_minQuantity + "-" + wholesalePrice_price;
				}
				productMainManager.saveProductSkuPrices(productSku, wholesalePrices);
				ajaxView.setStatus(new Short("1"));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria sc = createSearchCriteria(request,"default_product_price");
		List results = searchByCriteria(sc); 
		return getModelAndView(listView, "productPriceList", results);
	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}
}

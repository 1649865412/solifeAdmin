package com.cartmatic.estoresa.catalog.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.inventory.service.InventoryManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductSkuController extends GenericController<ProductSku> {
	private ProductSkuManager productSkuManager = null;
	private ProductMainManager productMainManager = null;
	private ProductManager productManager = null;
	private InventoryManager inventoryManager = null;

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
		//检查设置的sku编码是否已被使用
		ProductSku tempProductSku = productSkuManager.getProductSkuByProductSkuCode(productSku.getProductSkuCode());
		if (tempProductSku != null && (productSku.getId() == null || tempProductSku.getId().intValue() != productSku.getId().intValue())) {
			String msgKey = "productSku.productSkuCode.repeated";
			errors.rejectValue("productSkuCode", msgKey);
		}

		//检查skuOptionValueIds是否存在相应的Sku
		String[] skuOptionValueIds = RequestUtil.getParameterValuesNullSafe(request, "skuOptionValueIds");
		Set<Integer> skuOptionValueIdsI = new HashSet<Integer>();
		for (String integer : skuOptionValueIds) {
			if (StringUtils.isNotEmpty(integer) && StringUtils.isNumeric(integer)) {
				skuOptionValueIdsI.add(Integer.valueOf(integer));
			}
		}
		if (skuOptionValueIdsI.size() > 0) {
			ProductSku tempProductSku2 = productSkuManager.getSkuInPorudctByOptionValueIds(productSku.getProductId(), skuOptionValueIdsI);
			if ((tempProductSku2 != null && productSku.getId() == null) || (tempProductSku2 != null && productSku.getId() != null && productSku.getId().intValue() != tempProductSku2.getProductSkuId().intValue())) {
				String msgKey = "productDetail.productSku.existed.option";
				errors.reject(msgKey, "productSkuExisted_alert");
			}
		} else {
			//变种产品必须设置选择相应的Sku选项
			String msgKey = "productDetail.productSku.options.required";
			errors.reject(msgKey, "productSkuExisted_alert");
		}

		//检查是否可设置为激活
		ProductSku defaultProductSku=productManager.getById(productSku.getProductId()).getDefaultProductSku();
		if (defaultProductSku!=null&&defaultProductSku.getId()!=null&&(productSku.getId()==null||defaultProductSku.getId().intValue()!=productSku.getId().intValue())&&productSku.getStatus().intValue() == Constants.STATUS_ACTIVE.intValue()) {
			boolean isSame = productSkuManager.isSkuOptionIsSameWithSku(defaultProductSku.getId(), skuOptionValueIdsI);
			if (!isSame) {
				String msgKey = "productDetail.productSku.options.is.not.same";
				errors.reject(msgKey, "productSkuOptionNotSame_alert");
			}
		}
	}

	/**
	 * 保存单个记录的数据，并可以处理应用级的错误信息。在formBackingObject读数据的时候已经加锁，所以可以保证事务和版本控制。子类需要实现onSave。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'ProductSkuController save' method...");
		}
		// 取得Form对应的Model
		ProductSku entity = formBackingObject(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				//准备选项属性数据
				String[] skuOptionValueIds = RequestUtil.getParameterValuesNullSafe(request, "skuOptionValueIds");
				Set<Integer> skuOptionValueIdsI = new HashSet<Integer>();
				for (String integer : skuOptionValueIds) {
					if (StringUtils.isNotEmpty(integer) && StringUtils.isNumeric(integer)) {
						skuOptionValueIdsI.add(Integer.valueOf(integer));
					}
				}
				//准备批发价数据
				String wholesalePrice_prices[] = RequestUtil.getParameterValuesNullSafe(request, "wholesalePrice_price");
				String wholesalePrice_minQuantitys[] = RequestUtil.getParameterValuesNullSafe(request, "wholesalePrice_minQuantity");
				String wholesalePrices[] = new String[wholesalePrice_prices.length];
				for (int i = 0; i < wholesalePrice_prices.length; i++) {
					String wholesalePrice_price = wholesalePrice_prices[i];
					String wholesalePrice_minQuantity = wholesalePrice_minQuantitys[i];
					wholesalePrices[i] = wholesalePrice_minQuantity + "-" + wholesalePrice_price;
				}
				//保存Sku信息
				productMainManager.saveProductSkuAction(entity, skuOptionValueIdsI, wholesalePrices);
				//更新索引
				CatalogHelper.getInstance().indexNotifyUpdateEvent(entity.getProductId());
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}
		/**
		 * 保存后返回相应的提示信息，action=1表示保存更新成功，action=2表示保存更新失败； jFiledErrors为错误提示信息;
		 * 提示信息最好以json方式返回
		 * */
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors.hasErrors()) {
			List<JFieldError> jFiledErrors = new ArrayList<JFieldError>();
			List<ObjectError> ObjectErrors = errors.getAllErrors();
			for (ObjectError objectError : ObjectErrors) {
				JFieldError jFieldError = new JFieldError();
				String message = "";
				if (objectError instanceof FieldError) {
					FieldError fieldError = (FieldError) objectError;
					jFieldError.setObjectName(fieldError.getObjectName());
					jFieldError.setField(fieldError.getField());
					String key = fieldError.getCodes()[3];
					jFieldError.setKey(key);
					if (StringUtils.isEmpty(fieldError.getDefaultMessage())) {
						message = getMessage(key);
					} else {
						message = fieldError.getDefaultMessage();
					}
				} else {
					jFieldError.setField(objectError.getDefaultMessage());
					message = getMessage(objectError.getCodes()[1]);
				}

				jFieldError.setMessage(message);
				jFiledErrors.add(jFieldError);
			}
			ajaxView.setStatus(new Short("2"));
			data.put("jFiledErrors", jFiledErrors);
		} else {
			ajaxView.setStatus(new Short("1"));
			data.put("productSkuId", entity.getProductSkuId());
		}
		return ajaxView;
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		ProductSku productSku = (ProductSku) mv.getModel().get(formModelName);

		Integer productId = RequestUtil.getInteger(request, "productId");
		Product product = productManager.getById(productId);
		mv.addObject("product", product);
		
		//获取当前sku的选项属性及值
		if (productSku.getProductSkuId() != null) {
			Map<SkuOption, SkuOptionValue> skuOptionAndValues = productMainManager.findSkuOptionAndValuesByProductSku(productSku.getProductSkuId());
			mv.addObject("skuOptionAndValues", skuOptionAndValues);
		}
		
		//获取当前sku的选项可增加的选项
		List<SkuOption> otherSkuOptions = null;
		//判断当前编辑的Sku是否为产品默认Sku，如果是的，可编辑Sku选项范围为产品类型关联的选项属性；否则可编辑Sku选项范围为以该产品的默认Sku选项属性为基础
		if (product.getDefaultProductSkuId() == null || (productSku.getProductSkuId() != null && product.getDefaultProductSkuId().intValue() == productSku.getProductSkuId().intValue())) {
			otherSkuOptions = productMainManager.findActiveSkuOptionsOfProductTypeExcludeRefProductSku(product.getProductTypeId(), productSku.getProductSkuId());
		} else {
			//当为新增加Sku时，要带上默认sku的相关属性值
			if (productSku.getId() == null && product.getDefaultProductSku() != null) {
				ProductSku defaultProductSku = product.getDefaultProductSku();
				productSku.setCostPrice(defaultProductSku.getCostPrice());
				productSku.setPrice(defaultProductSku.getPrice());
				productSku.setSalePrice(defaultProductSku.getSalePrice());
				productSku.setListPrice(defaultProductSku.getListPrice());
				productSku.setWholesalePrices(defaultProductSku.getWholesalePrices());
				productSku.setWeight(defaultProductSku.getWeight());
				productSku.setWidth(defaultProductSku.getWidth());
				productSku.setLength(defaultProductSku.getLength());
				productSku.setHeight(defaultProductSku.getHeight());
			}
			otherSkuOptions = productMainManager.findSkuOptionsOfDefaultProductSkuExcludeRefProductSku(product.getDefaultProductSkuId(), productSku.getProductSkuId());
		}
		mv.addObject("otherSkuOptions", otherSkuOptions);
		//获取Sku库存信息
		if (productSku.getId() != null) {
			Inventory inventory = inventoryManager.getInventoryBySku(productSku.getProductSkuId());
			mv.addObject("inventory", inventory);
		}

		//获取其他Sku及其选项
		/*
		Map<ProductSku,Map<SkuOption,SkuOptionValue>>otherProductSkuAndOptionValues=new HashMap<ProductSku, Map<SkuOption,SkuOptionValue>>();
		mv.addObject("otherProductSkuAndOptionValues", otherProductSkuAndOptionValues);
		Set<ProductSku>productSkus=product.getProductSkus();
		for (ProductSku tempProductSku : productSkus) {
			if(productSku.getProductSkuId()==null||tempProductSku.getProductSkuId()!=productSku.getProductSkuId()){
		        Map<SkuOption,SkuOptionValue>tempSkuOptionAndValues=productMainManager.findSkuOptionAndValuesByProductSku(tempProductSku.getProductSkuId());
				otherProductSkuAndOptionValues.put(tempProductSku, tempSkuOptionAndValues);
			}
		}*/

	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	public ModelAndView setProductDefaultSku(HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'ProductSkuController setProductDefaultSku' method...");
		}
		AjaxView ajaxView=new AjaxView(response);
		Integer productSkuId = RequestUtil.getInteger(request, "productSkuId");
		productSkuManager.doSetDefaultProductSku(productSkuId);
		return ajaxView;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	/**
	 * 检查Sku编码是否已经被使用了，返回json数据，
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView isExistSkuCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		String productSkuCode = request.getParameter("productSkuCode");
		Integer productSkuId = ServletRequestUtils.getIntParameter(request, "productSkuId", -1);
		ProductSku productSku = null;
		if (productSkuId.intValue() != -1) {
			productSku = productSkuManager.getById(productSkuId);
		}
		ProductSku tempProductSku = productSkuManager.getProductSkuByProductSkuCode(productSkuCode);
		if (tempProductSku != null && (productSku == null || productSku.getId().intValue() != tempProductSku.getId().intValue())) {
			String msgKey = "productSku.productSkuCode.repeated";
			String msg = getMessage(msgKey);
			ajaxView.setMsg(msg);
			ajaxView.setStatus(new Short("2"));
		} else {
			ajaxView.setStatus(new Short("1"));
		}
		return ajaxView;
	}
	
	public ModelAndView printBarcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		Integer productSkuId = ServletRequestUtils.getIntParameter(request, "productSkuId", -1);
		ProductSku productSku = null;
		if (productSkuId.intValue() != -1) {
			productSku = productSkuManager.getById(productSkuId);
			data.put("sku", productSku.getJsonObject());
			data.put("dateTime", DateUtil.fmtTodayToFour());
			ajaxView.setData(data);
			ajaxView.setStatus(new Short("1"));
		}
		return ajaxView;
	}

}

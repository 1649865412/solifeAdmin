
package com.cartmatic.estoresa.inventory.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.inventory.InventoryAudit;
import com.cartmatic.estore.common.model.inventory.InventoryModel;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.inventory.service.InventoryAuditManager;
import com.cartmatic.estore.inventory.service.InventoryManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class InventoryController extends GenericController<Inventory> {
	private InventoryManager	inventoryManager	= null;

	private ProductService		productService		= null;
    private InventoryAuditManager inventoryAuditManager = null;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setInventoryManager(InventoryManager inMgr) {
		this.inventoryManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Inventory entity) {
		return entity.getInventoryName();
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
		mgr = inventoryManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Inventory entity,
			BindException errors) {
	}

	/**
	 * @param request
	 * @param response
	 * @return action -2表示输入SkuCode为空，-1输入SkuCode没有对应的sku，0表示产品为草稿状态，1表示存在相应的库存数据,-3该产品没有库存管理
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView getInventory(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("inventory/inventoryForm");
		String productSkuCode = request.getParameter("productSkuCode");
		//带skuId的表示又产品编辑中跳转过来的,直接通过Sku Id查找库存
		Integer productSkuId = RequestUtil.getInteger(request,"productSkuId");
		InventoryModel inventoryModel = new InventoryModel();
		mv.addObject("inventoryModel", inventoryModel);
		Inventory inventory=null;
		if(productSkuId==null){
			if(productSkuCode!=null)productSkuCode=productSkuCode.trim();
			if (StringUtils.isEmpty(productSkuCode)) {
				mv.addObject("action", -2);
				return mv;
			}
			ProductSku productSku = productService.getProductSkuByProductSkuCode(productSkuCode);
			if (productSku == null) {
				mv.addObject("action", -1);
				return mv;
			}
			if (productSku.getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL.intValue()) {
				mv.addObject("action", -3);
				return mv;
			}
			if (productSku.getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL.intValue()) {
				mv.addObject("action", -4);
				return mv;
			}
			if (productSku.getProduct().getStatus().intValue() == Constants.STATUS_NOT_PUBLISHED.intValue()) {
				mv.addObject("action", 0);
				return mv;
			}
			inventory=productSku.getInventory();
			//inventory = inventoryManager.getInventoryBySkuCode(productSkuCode);
		}else{
			inventory = inventoryManager.getInventoryBySku(productSkuId);
		}
		mv.addObject("inventory", inventory);
		if (inventory != null) {
			BeanUtils.copyProperties(inventory, inventoryModel);
		}
		mv.addObject("action", 1);
		SearchCriteria sc =inventoryAuditManager.getSearchCriteriaBuilder("inventoryInventoryAudit").buildSearchCriteria(request, getPageSize());
		mv.addObject("sc", sc);
		sc.addParamValue(inventory.getInventoryId());
		List<InventoryAudit> inventoryAuditList=inventoryAuditManager.searchByCriteria(sc);
		mv.addObject("inventoryAuditList", inventoryAuditList);
		return mv;
	}
	
	

	@SuppressWarnings("unchecked")
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		InventoryModel inventoryModel = new InventoryModel();
		Inventory inventory = inventoryManager.loadForUpdate(RequestUtil.getInteger(request, "inventoryId"));
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request,
					inventoryModel, "inventoryModel");
			errors = new BindException(binder.getBindingResult());
			// 检查设置保留数量是否少于(可售数量+原来的保留数量)
			Integer canReservedQuantity=inventory.getAvailableQuantity()+inventory.getReservedQuantity();
			if (canReservedQuantity- inventoryModel.getReservedQuantity() < 0) {
				String msgKey = "nventoryModel.reservedQuantity.error";
				errors.rejectValue("reservedQuantity", msgKey,new Object[]{canReservedQuantity},"必须少于{0}(原来的保留数量+可售数量)！");
			}
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				inventoryManager.saveInventoryAction(inventoryModel, inventory);
				saveMessage(Message.info("inventoryDetail.update.successd", inventory.getProductSku().getProductSkuCode()));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}
		ModelAndView mav;
		if (errors.hasErrors()) {
			Map model = errors.getModel();
			if (isEntityNew(request) || errors.hasErrors()) {
				if (errors.hasErrors()) {
					request.setAttribute("HAS_ERRORS", Boolean.TRUE);
				}
				markIsFormAction(request);
				request.setAttribute("entityClassName", "inventoryModel");
			}
			request.setAttribute("SwitchItemEnabled", Boolean.TRUE);
			mav = new ModelAndView("inventory/inventoryForm", model);
		} else {
			String redirectView=null;
			Integer productId=RequestUtil.getInteger(request, "productId");
			if(productId!=null){
				redirectView+="&productId="+productId;
				redirectView="/inventory/inventory/window.html?productId="+productId+"&productSkuId="+inventory.getProductSku().getId();
			}else{
				redirectView="inventory.html?productSkuCode="+ inventory.getProductSku().getProductSkuCode();
			}
			mav = getRedirectView(redirectView);
		}
		mav.addObject("inventory", inventory);
		mav.addObject("inventoryModel", inventoryModel);
		return mav;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView adjustStockQuantity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		InventoryModel inventoryModel = new InventoryModel();
		Inventory inventory = inventoryManager.loadForUpdate(RequestUtil.getInteger(
				request, "inventoryId"));
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request,inventoryModel, "inventoryModel");
			errors = new BindException(inventoryModel,"inventoryModel");
			if(inventoryModel.getAdjustmentType()==null){
				String msgKey = "inventoryModel.adjustmentType.select";
				errors.rejectValue("adjustmentType", msgKey);
			}
			if(inventoryModel.getAdjustmentQuantity()==null||inventoryModel.getAdjustmentQuantity().intValue()<=0){
				String msgKey = "inventoryModel.adjustmentQuantity.must.positiveInteger";
				errors.rejectValue("adjustmentQuantity", msgKey);
			}
			// 验证是否可以减少相应数量,可减少数量不能大于可售数量
			if (inventoryModel.getAdjustmentType() != null&& inventoryModel.getAdjustmentType() == 2) {
				if (inventory.getAvailableQuantity()- inventoryModel.getAdjustmentQuantity() < 0) {
					String msgKey = "inventoryModel.must.less.availableQuantity";
					errors.rejectValue("adjustmentQuantity", msgKey);
				}
			}
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				inventoryManager.doAdjustStockQuantity(inventoryModel,inventory);
				saveMessage(Message.info("inventoryDetail.update.successd",inventory.getProductSku().getProductSkuCode()));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}
		ModelAndView mav;
		if (errors.hasErrors()) {
			Map model = errors.getModel();
			if (isEntityNew(request) || errors.hasErrors()) {
				if (errors.hasErrors()) {
					request.setAttribute("HAS_ERRORS", Boolean.TRUE);
				}
				markIsFormAction(request);
				request.setAttribute("entityClassName", "inventoryModel");
			}
			request.setAttribute("SwitchItemEnabled", Boolean.TRUE);
			mav = new ModelAndView("inventory/inventoryForm", model);
		} else {
			String redirectView=null;
			Integer productId=RequestUtil.getInteger(request, "productId");
			if(productId!=null){
				redirectView+="&productId="+productId;
				redirectView="/inventory/inventory/window.html?productId="+productId+"&productSkuId="+inventory.getProductSku().getId();
			}else{
				redirectView="inventory.html?productSkuCode="+ inventory.getProductSku().getProductSkuCode();
			}
			mav = getRedirectView(redirectView);
		}
		mav.addObject("inventory", inventory);
		mav.addObject("inventoryModel", inventoryModel);
		return mav;
	}

	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		removeNavFromSearchCriteria(request);
		return getInventory(request, response);
	}

	public void setInventoryAuditManager(InventoryAuditManager inventoryAuditManager) {
		this.inventoryAuditManager = inventoryAuditManager;
	}
}

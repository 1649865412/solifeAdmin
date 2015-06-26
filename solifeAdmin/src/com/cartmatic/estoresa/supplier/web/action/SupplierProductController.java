package com.cartmatic.estoresa.supplier.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.service.AccessoryGroupManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductDataModel;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.common.model.supplier.TbCategoryRefer;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.supplier.service.SupplierProductManager;
import com.cartmatic.estore.supplier.service.TbCatPropValueReferManager;
import com.cartmatic.estore.supplier.service.TbCategoryReferManager;
import com.cartmatic.estore.webapp.util.RequestUtil;
import com.cartmatic.extend.supplier.util.TaoBaoApiHelper;

public class SupplierProductController extends GenericController<SupplierProduct> {
    private SupplierProductManager supplierProductManager = null;
    private ProductTypeManager productTypeManager=null;
    
    private ProductMainManager productMainManager=null;
    
    private AccessoryGroupManager accessoryGroupManager;
    
    private TbCategoryReferManager tbCategoryReferManager=null;
    
    private TbCatPropValueReferManager tbCatPropValueReferManager=null;

	private ProductManager productManager=null;


	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setTbCategoryReferManager(
			TbCategoryReferManager tbCategoryReferManager) {
		this.tbCategoryReferManager = tbCategoryReferManager;
	}

	public void setTbCatPropValueReferManager(
			TbCatPropValueReferManager tbCatPropValueReferManager) {
		this.tbCatPropValueReferManager = tbCatPropValueReferManager;
	}

	public void setAccessoryGroupManager(AccessoryGroupManager accessoryGroupManager) {
		this.accessoryGroupManager = accessoryGroupManager;
	}

	public void setSupplierProductManager(SupplierProductManager inMgr) {
        this.supplierProductManager = inMgr;
    }
    
	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SupplierProduct entity) {
		return entity.getSupplierProductName();
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
		mgr = supplierProductManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SupplierProduct entity, BindException errors) {
	}

	
	public ModelAndView createProduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		try {
			Integer supplierProductId = RequestUtil.getInteger(request, "supplierProductId");
			SupplierProduct supplierProduct=supplierProductManager.getById(supplierProductId);
			ProductDataModel productDataModel=new ProductDataModel();
			onCreateProduct(request, supplierProduct, productDataModel, ajaxView);
			if(ajaxView.getStatus()==1){
				Product product=productMainManager.createProduct4SupplierProdcut(supplierProduct,productDataModel);
				data.put("productId", product.getProductId());
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	private void onCreateProduct(HttpServletRequest request,SupplierProduct supplierProduct,ProductDataModel productDataModel,AjaxView ajaxView){
		Integer productTypeId=new Integer(request.getParameter("productTypeId"));
		productDataModel.setProductTypeId(productTypeId);
		boolean isVariation = ServletRequestUtils.getBooleanParameter(request, "isVariation", false);
		productDataModel.setProductKind(isVariation?CatalogConstants.PRODUCT_KIND_VARIATION:CatalogConstants.PRODUCT_KIND_COMMON);
		
		//检查目录设置
		Integer categoryId=supplierProduct.getCategoryId();
		Long tbCid=supplierProduct.getTbCId();
		if(categoryId==null&&tbCid!=null){
			TbCategoryRefer tbCategoryRefer =tbCategoryReferManager.getTbCategoryReferByTbCId(tbCid);
			if(tbCategoryRefer.getCategory()==null){
				ajaxView.setStatus(new Short("3"));
				ajaxView.setMsg("目录配置未完善!["+tbCategoryRefer.getTbCategoryName()+"]");
			}else{
				supplierProduct.setCategory(tbCategoryRefer.getCategory());
			}
		}
		
		//检查附件数据设置
		if(ajaxView.getStatus()==1&&supplierProduct.getTbSellCatProps()!=null){
			List<Integer> accessoryIds=new ArrayList<Integer>();
			List<TbCatPropValueRefer> tbCatPropValueReferList=TaoBaoApiHelper.getInstance().strToTbCatPropValueRefer(supplierProduct.getTbSellCatProps());
			Map<Integer, TbCatPropRefer>needConfigTbCatPropReferMap=new HashMap<Integer, TbCatPropRefer>();
			String accessoryConfigError="";
			for (TbCatPropValueRefer tbCatPropValueRefer : tbCatPropValueReferList) {
				TbCatPropValueRefer temp_tbCatPropValueRefer=tbCatPropValueReferManager.getTbCatPropValueReferByCatePropValueId(tbCatPropValueRefer.getTbCatPropValueId());
				if(temp_tbCatPropValueRefer==null){
					accessoryConfigError+="TbCatPropValueRefer id="+tbCatPropValueRefer.getTbCatPropValueId()+" is null;";
				}else{
					TbCatPropRefer tbCatPropRefer=needConfigTbCatPropReferMap.get(temp_tbCatPropValueRefer.getTbCatPropReferId());
					if(temp_tbCatPropValueRefer.getAccessory()==null){
						if(tbCatPropRefer==null){
							tbCatPropRefer=new TbCatPropRefer();
							needConfigTbCatPropReferMap.put(temp_tbCatPropValueRefer.getTbCatPropReferId(), tbCatPropRefer);
							tbCatPropRefer.setTbCatPropName(temp_tbCatPropValueRefer.getTbCatPropRefer().getTbCatPropName());
							tbCatPropRefer.setTbCatPropValueRefers(new HashSet<TbCatPropValueRefer>());
						}
						TbCatPropValueRefer error_tbCatPropValueRefer=new TbCatPropValueRefer();
						error_tbCatPropValueRefer.setTbCatPropValueName(temp_tbCatPropValueRefer.getTbCatPropValueName());
						tbCatPropRefer.getTbCatPropValueRefers().add(error_tbCatPropValueRefer);
					}else{
						accessoryIds.add(temp_tbCatPropValueRefer.getAccessoryId());
					}
				}
			}
			if(needConfigTbCatPropReferMap.size()>0){
				accessoryConfigError="附件配置未完善,[";
				List<Integer> accessoryIdList=new ArrayList<Integer>(); 
//				Set<Integer> tbCatPropReferIds=needConfigTbCatPropReferMap.keySet();
				List<Integer> tbCatPropReferIds=new ArrayList<Integer>(needConfigTbCatPropReferMap.keySet());
				for (int i = 0; i < tbCatPropReferIds.size(); i++) {
					TbCatPropRefer tbCatPropRefer=needConfigTbCatPropReferMap.get(tbCatPropReferIds.get(i));
					accessoryConfigError+=tbCatPropRefer.getTbCatPropName()+"--";
					List<TbCatPropValueRefer>tbCatPropValueRefersSet=new ArrayList<TbCatPropValueRefer>(tbCatPropRefer.getTbCatPropValueRefers());
					for (int j = 0; j < tbCatPropValueRefersSet.size(); j++) {
						TbCatPropValueRefer tbCatPropValueRefer =tbCatPropValueRefersSet.get(j);
						accessoryConfigError+=tbCatPropValueRefer.getTbCatPropValueName();
						if(j!=tbCatPropValueRefersSet.size()-1){
							accessoryConfigError+=",";
						}
					}
					if(i!=tbCatPropReferIds.size()-1){
						accessoryConfigError+=";";
					}
				}
				accessoryConfigError+="]";
				productDataModel.setAccessoryIds(accessoryIdList.toArray(new Integer[]{}));
				ajaxView.setStatus(new Short("2"));
				ajaxView.setMsg(accessoryConfigError);
			}else{
				productDataModel.setAccessoryIds(accessoryIds.toArray(new Integer[]{}));
			}
		}
		if(ajaxView.getStatus()==1){
			productDataModel.setFullDescription(supplierProduct.getProductDesc());
			// 保存更新批发价wholesalePrice_minQuantity
			if(!isVariation){
				String wholesalePrice_prices[] = RequestUtil.getParameterValuesNullSafe(request,"wholesalePrice_price");
				String wholesalePrice_minQuantitys[] = RequestUtil.getParameterValuesNullSafe(request,"wholesalePrice_minQuantity");
				String wholesalePrices[] = new String[wholesalePrice_prices.length];
				for (int i = 0; i < wholesalePrice_prices.length; i++) {
					String wholesalePrice_price = wholesalePrice_prices[i];
					String wholesalePrice_minQuantity = wholesalePrice_minQuantitys[i];
					wholesalePrices[i] = wholesalePrice_minQuantity + "-"+ wholesalePrice_price;
				}
				productDataModel.setWholesalePrices(wholesalePrices);
			}
		}
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		SupplierProduct supplierProduct = (SupplierProduct) mv.getModel().get(formModelName);
		List<ProductType> productTypeList = productTypeManager.findActiveProductTypes();
		request.setAttribute("productTypeList",productTypeList);
		// 新增产品并且不为产品包时，指定哪些产品类型存在SkuOption？用于确定选择该产品是否变种
		List<ProductType> hasActiveSkuOptionProductTypes = productTypeManager.findActiveSkuOptionsProductType();
		String hasActiveSkuOptionProductTypeIds = ",";
		for (ProductType productType : hasActiveSkuOptionProductTypes) {
			hasActiveSkuOptionProductTypeIds += productType.getProductTypeId()+ ",";
		}
		mv.addObject("hasActiveSkuOptionProductTypeIds",hasActiveSkuOptionProductTypeIds);
		
		//修过历史
		if(StringUtils.isNotBlank(supplierProduct.getUpdateLogs())){
			JSONArray updateLogs=JSONArray.fromObject(supplierProduct.getUpdateLogs());
			request.setAttribute("updateLogs", updateLogs);
		}
	}

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}
	
	public ModelAndView initSupplierProduct(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Product> productList=productManager.getAll();
		for (Product product : productList) {
			Supplier supplier=product.getSupplier();
			if(supplier==null){
				productMainManager.saveProductSuppliersAction(product, -1, new Integer[]{-1});
				System.out.println(product);
			}else{
				//productMainManager.saveProductSuppliersAction(product, supplier.getSupplierId(), new Integer[]{supplier.getSupplierId()});
			}
		}		
		return null;
	}

}


package com.cartmatic.estoresa.catalog.web.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.helper.BulkHelper;
import com.cartmatic.estore.catalog.model.BulkProduct;
import com.cartmatic.estore.catalog.model.BulkProductModel;
import com.cartmatic.estore.catalog.model.ProductBulkEditCheckModel;
import com.cartmatic.estore.catalog.service.AccessoryGroupManager;
import com.cartmatic.estore.catalog.service.BrandManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductAttGroupItemManager;
import com.cartmatic.estore.catalog.service.ProductAttGroupManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.catalog.service.ProductDescriptionManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductMediaManager;
import com.cartmatic.estore.catalog.service.ProductMediaUpManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.catalog.service.WholesalePriceManager;
import com.cartmatic.estore.common.helper.AttributeUtil;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.model.catalog.ProductDataModel;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductMediaUp;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.exception.BulkUpdateException;
import com.cartmatic.estore.inventory.service.InventoryManager;
import com.cartmatic.estore.supplier.service.SupplierManager;
import com.cartmatic.estore.supplier.service.SupplierProductManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductController extends GenericController<Product> {
	private ProductManager				productManager				= null;
	private ProductTypeManager			productTypeManager			= null;
	private BrandManager				brandManager				= null;
	private ProductSkuManager			productSkuManager			= null;
	private ProductMainManager			productMainManager			= null;
	private ProductAttGroupManager		productAttGroupManager		= null;
	private CategoryManager				categoryManager				= null;
	private ProductMediaManager			productMediaManager			= null;
	private ProductMediaUpManager			productMediaUpManager			= null;
	private InventoryManager			inventoryManager			= null;
	private ProductAttGroupItemManager	productAttGroupItemManager	= null;
	private ProductDescriptionManager	productDescriptionManager	= null;
	private ProductCategoryManager		productCategoryManager		= null;
	private WholesalePriceManager		wholesalePriceManager		= null;
	private AttributeService attributeService=null;
	private SupplierManager supplierManager=null;
	private SupplierProductManager supplierProductManager=null;
	
	private AccessoryGroupManager accessoryGroupManager=null;
	

	public void setSupplierProductManager(
			SupplierProductManager supplierProductManager) {
		this.supplierProductManager = supplierProductManager;
	}

	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	public void setAccessoryGroupManager(AccessoryGroupManager accessoryGroupManager) {
		this.accessoryGroupManager = accessoryGroupManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setProductAttGroupManager(
			ProductAttGroupManager productAttGroupManager) {
		this.productAttGroupManager = productAttGroupManager;
	}

	public void setProductManager(ProductManager inMgr) {
		this.productManager = inMgr;
	}

	public void setWholesalePriceManager(
			WholesalePriceManager wholesalePriceManager) {
		this.wholesalePriceManager = wholesalePriceManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java
	 *      .lang.Object)
	 */
	@Override
	protected String getEntityName(Product entity) {
		return entity.getProductName();
	}

	/**
	 * 重载edit方法，主要是去掉设置导航信息，（savedNavAndSearchCriteria(....)），其他处理与框架一致，
	 */
	@Override
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Product entity = formBackingObject(request);
		BindException errors = null;
		request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
		ServletRequestDataBinder binder = bindAndValidate(request, entity);
		errors = new BindException(binder.getBindingResult());
		return showForm(request, errors);
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现
	 * 。要求数据要先转换为正确的类型。
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
		mgr = productManager;
	}

	/**
	 * 保存产品前，对产品更新数据进行检查以及设置默认值等
	 */
	@Override
	protected void onSave(HttpServletRequest request, Product product,
			BindException errors) {
		// 新建产品时确定是普通产品、变种产品、还是产品包
		if (product.getProductId() == null) {
			boolean isVariation = ServletRequestUtils.getBooleanParameter(
					request, "isVariation", false);
			boolean isPackage = ServletRequestUtils.getBooleanParameter(
					request, "isPackage", false);
			if (isPackage) {
				product.setProductKind(CatalogConstants.PRODUCT_KIND_PACKAGE);
			} else if (isVariation) {
				product.setProductKind(CatalogConstants.PRODUCT_KIND_VARIATION);
			} else {
				product.setProductKind(CatalogConstants.PRODUCT_KIND_COMMON);
			}
		}
		// 当为产品包并且为草稿状态时，必须设置两个或两个以上的items,
		if (product.getProductId() != null
				&& product.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_PACKAGE
				&& product.getOrigStatus().intValue() == Constants.STATUS_NOT_PUBLISHED) {
			String[] productPackageItemIds = RequestUtil
					.getParameterValuesNullSafe(request,
							"productPackage_productPackageItemId");
			if (productPackageItemIds.length < 2) {
				// 少于两个，必须添加相应的item
				String msgKey = "productDetail.productPackage.item.less2";
				errors.reject(msgKey, "productPackageItem");
			}
		}

		// 设置产品相应的状态
		if (product.getProductId() == null
				|| product.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_VARIATION
				&& product.getDefaultProductSkuId() == null) {
			// 新增产品或该产品设置添加相应的Sku时，产品为草稿状态
			product.setStatus(Constants.STATUS_NOT_PUBLISHED);
		} else if (product.getProductId() != null
				&& product.getStatus() != null
				&& product.getStatus().intValue() == Constants.STATUS_NOT_PUBLISHED
						.intValue()) {
			// 新增产品最后一步保存（finish）时，设置为激活状态（原来是草稿的转为非激活）
			product.setStatus(Constants.STATUS_ACTIVE);
		}

		// 只检查产品code是否已经已被使用，
		Product tempProduct = productManager.getProductByProductCode(product.getProductCode());
		if (tempProduct != null
				&& (product.getId() == null || tempProduct.getId().intValue() != product
						.getId().intValue())) {
			String msgKey = "product.productCode.repeated";
			errors.rejectValue("productCode", msgKey);
		}
		// TODO 产品类型，品牌，目录是否存在或激活等，保存更新产品时没有对这些数据进行检查（暂不需要，目前编辑页面只会加载显示激活状态的实体）

		// 检查是否设置了主目录
		Integer mainCategoryId = RequestUtil.getInteger(request,
				"mainCategoryId");
		if (mainCategoryId == null) {
			String msgKey = "productDetail.required.mainCategory";
			errors.reject(msgKey, "mainCategoryId");
		}

		// 检查是否设置了相应的目录
		Integer categoryIds[] = RequestUtil.getIntegerArray(request,
				"categoryIds");
		if (categoryIds == null || categoryIds.length < 1) {
			String msgKey = "productDetail.required.categorys";
			errors.reject(msgKey, "categoryIds");
		}
	}

	/**
	 * 对编辑普通产品或产品包时，对相应的Sku设置的数据进行验证
	 * 
	 * @param request
	 * @param productSku
	 * @param errors
	 */
	private void onSaveProductSku(HttpServletRequest request,
			ProductSku productSku, BindException errors) {
		// 检查Sku编码是否已使用
		ProductSku tempProductSku = productSkuManager
				.getProductSkuByProductSkuCode(productSku.getProductSkuCode());
		if (tempProductSku != null&& (productSku.getId() == null || tempProductSku.getId().intValue() != productSku.getId().intValue())) {
			String msgKey = "productSku.productSkuCode.repeated";
			errors.rejectValue("productSkuCode", msgKey);
		}
	}

	@Override
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering ProductController 'save' method...");
		}
		// 首次保存只是保存productInfo
		boolean onlySaveProductInfo = ServletRequestUtils.getBooleanParameter(
				request, "onlySaveProductInfo", false);
		// 取得Form对应的Model
		Product product = formBackingObject(request);

		Integer mainCategoryIds[] = RequestUtil.getIntegerArray(request,"mainCategoryId");
		Integer categoryIds[] = RequestUtil.getIntegerArray(request,"categoryIds");
		Integer defaultSupplierId = RequestUtil.getInteger(request,"defaultSupplierId");
		Integer supplierIds[] = RequestUtil.getIntegerArrayNullSafe(request,"supplierIds");
		BindException errors = null;
		BindException productSkuErrors = null;
		ProductSku productSku = null;
		List<String> newProductMediaIds = null;
		try {
			ServletRequestDataBinder productBinder = bindAndValidate(request,product);
			errors = new BindException(productBinder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, product, errors);
			// 当产品为普通产品时，保存更新产品信息时，同时保存Sku的数据信息
			if (product.getProductId() != null&& product.getProductKind().intValue() != CatalogConstants.PRODUCT_KIND_VARIATION) {
				// 对默认Sku进行数据绑定
				productSku = product.getDefaultProductSku();
				if (productSku == null)
					productSku = new ProductSku();
				ServletRequestDataBinder productSkuBinder = bindAndValidate(request, productSku, "productSku");
				productSkuErrors = new BindException(productSkuBinder.getBindingResult());
				// 检查Sku数据
				onSaveProductSku(request, productSku, productSkuErrors);
				List<FieldError> felist = productSkuErrors.getAllErrors();
				// 对Sku检查的错误信息合并到产品BindException中去
				for (FieldError fieldError : felist) {
					errors.addError(fieldError);
				}
			}
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				// 组装productDataModel数据
				ProductDataModel productDataModel = new ProductDataModel();
				// 编辑产品时，同时保存描述信息，productDescription
				productDataModel.setOnlySaveProductInfo(onlySaveProductInfo);
				if (!onlySaveProductInfo) {
					productDataModel.setFullDescription(request.getParameter("fullDescription"));
					productDataModel.setShortDescription(request.getParameter("shortDescription"));
					productDataModel.setImageDescription(request.getParameter("imageDescription"));
					//普通产品时才存在附件
					if(product.getProductKind().intValue()==CatalogConstants.PRODUCT_KIND_COMMON.intValue()){
						//保存产品附件
						Integer[] accessoryIds = RequestUtil.getIntegerArrayNullSafe(request,"accessoryIds");
						productDataModel.setAccessoryIds(accessoryIds);
					}
				}
				// 保存产品
				productDataModel.setProduct(product);
				productDataModel.setMainCategoryIds(mainCategoryIds);
				productDataModel.setCategoryIds(categoryIds);
				//设置供应商数据
				productDataModel.setDefaultSupplierId(defaultSupplierId);
				productDataModel.setSupplierIds(supplierIds);
				if (!onlySaveProductInfo) {
					// 保存、更新自定义属性
					List<AttributeValue> attributeValues = AttributeUtil
							.getInstance().getAttributeFromRequest(request);
					// 为新增的自定义属性指定ProductAttGroupItem关联
					for (AttributeValue attributeValue : attributeValues) {
						ProductAttrValue productAttrValue = (ProductAttrValue) attributeValue;
						if (productAttrValue.getId() == null) {
							ProductAttGroupItem productAttGroupItem = productAttGroupItemManager
									.getProductAttGroupItemByProductTypeAndAttribute(
											product.getProductTypeId(),
											productAttrValue.getAttributeId());
							productAttrValue
									.setProductAttGroupItem(productAttGroupItem);
						}
					}
					productDataModel.setAttributeValues(attributeValues);
					
					// 更新保存产品媒体（产品大图）
					String productMediaIds[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMediaIds");
					String mediaUrls[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_urls");
					String mediaUrls_d[] = RequestUtil
							.getParameterValuesNullSafe(request,
								"productMedia_urls_d");
					String productMediaTypes[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMediaTypes");
					String mediaDescription[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_descs");
					String mediaProductSkus[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_productSkus");
					String productMedia_deleteds[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_deleteds");
					productDataModel.setProductMediaIds(productMediaIds);
					productDataModel.setMediaUrls(mediaUrls);
					productDataModel.setMediaUrls_d(mediaUrls_d);
					productDataModel.setProductMediaTypes(productMediaTypes);
					productDataModel.setMediaDescription(mediaDescription);
					productDataModel.setProductMedia_deleteds(productMedia_deleteds);
					productDataModel.setMediaProductSkus(mediaProductSkus);
					
					
					//更新保存产品媒体（产品额外图）
					String productMediaUpIds[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMediaUpIds");
					String mediaUrlsUp[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_urlsUp");
					String productMediaTypesUp[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMediaTypesUp");
					String mediaDescriptionUp[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_descsUp");
					String productMedia_deletedsUp[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"productMedia_deletedsUp");
					
					productDataModel.setProductMediaUpIds(productMediaUpIds);
					productDataModel.setMediaUrlsUp(mediaUrlsUp);
					productDataModel.setProductMediaTypesUp(productMediaTypesUp);
					productDataModel.setMediaDescriptionUp(mediaDescriptionUp);
					productDataModel.setProductMedia_deletedsUp(productMedia_deletedsUp);
					
				}
				if (productSku != null) {
					// 保存sku信息
					productDataModel.setDefaultProductSku(productSku);
					// 保存更新批发价wholesalePrice_minQuantity
					String wholesalePrice_prices[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"wholesalePrice_price");
					String wholesalePrice_minQuantitys[] = RequestUtil
							.getParameterValuesNullSafe(request,
									"wholesalePrice_minQuantity");
					String wholesalePrices[] = new String[wholesalePrice_prices.length];
					for (int i = 0; i < wholesalePrice_prices.length; i++) {
						String wholesalePrice_price = wholesalePrice_prices[i];
						String wholesalePrice_minQuantity = wholesalePrice_minQuantitys[i];
						wholesalePrices[i] = wholesalePrice_minQuantity + "-"
								+ wholesalePrice_price;
					}
					productDataModel.setWholesalePrices(wholesalePrices);
					// 当为产品包时，保存被包含的items,草稿状态才可以更新保存
					// TODO己激活过的产品不能更新产品包Items
					if (product.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_PACKAGE
							&& product.getOrigStatus() != null
							&& product.getOrigStatus().intValue() == Constants.STATUS_NOT_PUBLISHED
									.intValue()) {
						Integer[] productPackageItemIds = RequestUtil
								.getIntegerArray(request,
										"productPackage_productPackageItemId");
						Integer[] productPackageQuantitys = RequestUtil
								.getIntegerArrayNullSafe(request,
										"productPackage_quantity");
						productDataModel
								.setProductPackageItemIds(productPackageItemIds);
						productDataModel
								.setProductPackageQuantity(productPackageQuantitys);
					}
				}
				
				String handDrawId = request.getParameter("handDrawId");
				String handDrawUrl = request.getParameter("handDrawUrl");
				productDataModel.setHandDrawId(handDrawId);
				productDataModel.setHandDrawUrl(handDrawUrl);
				
				// 保存更新产品数据
				Map<String, Object> saveProductMsg = productMainManager.saveProductAction(productDataModel);
				
				if(product.getStatus()!=null&&product.getStatus().intValue()!=Constants.STATUS_NOT_PUBLISHED){
					//更新索引
					CatalogHelper.getInstance().indexNotifyUpdateEvent(product.getProductId());
				}
				
				// 保存为ajax处理，因此保存后续更新页面新增的产品媒体的ID
				newProductMediaIds = (List<String>) saveProductMsg.get("newProductMediaIds");
			}
		} catch (ApplicationException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("entering ProductController 'save product error'...");
			}
			handleApplicationException(errors, e);
		}
		/**
		 * 保存后返回相应的提示信息，action=1表示保存更新成功，action=2表示保存更新失败； jFiledErrors为错误提示信息;
		 * 提示信息最好以json方式返回
		 */
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors.hasErrors()) {
			List<JFieldError> jFiledErrors = JFieldErrorUtil.getFiledErrors(errors);
			ajaxView.setStatus(new Short("2"));
			data.put("jFiledErrors", jFiledErrors);
		} else {
			String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
			ajaxView.setMsg(getMessage(msgKey, new Object[] {getEntityTypeMessage(), product.getProductCode()}));
			ajaxView.setStatus(new Short("1"));
			data.put("productId", product.getProductId());
			data.put("productCode", product.getProductCode());
			data.put("newProductMediaIds", newProductMediaIds);
			if (productSku != null) {
				data.put("productSkuId", productSku.getProductSkuId());
			}
		}
		return ajaxView;
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering ProductController 'onShowForm' method...");
		}
		Product product = (Product) mv.getModel().get(formModelName);
	
		// 是否进入导向编辑页面，（目前只有为草稿状态就进入导航页面）
		boolean isOriented = false;
		if (product.getProductId() == null
				|| product.getStatus().intValue() == Constants.STATUS_NOT_PUBLISHED) {
			isOriented = true;
		}
		mv.addObject("isOriented", isOriented);

		// 是否产品包，（产品包没有特性tab)
		boolean isPackage = false;
		if (product.getProductId() == null) {
			isPackage = ServletRequestUtils.getBooleanParameter(request,
					"isPackage", false);
		} else {
			isPackage = product.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_PACKAGE
					.intValue();
		}
		mv.addObject("isPackage", isPackage);
		
		// 加载产品类型、品牌等数据
		List<ProductType> productTypeList = productTypeManager.findActiveProductTypes();
		List<Brand> brandList = brandManager.getAllOrdered("sortOrder", true);
		String productTypeJSONList=productTypeJSONData(productTypeList);
		mv.addObject("productTypeList", productTypeList);
		mv.addObject("productTypeJSONList", productTypeJSONList);
		mv.addObject("brandList", brandList);

		if (product != null) {
			ProductSku productSku = getOnlyOneProductSku(product);
			// 当该产品只有一个Sku,并且没有skuOption时，Sku Tab直接显示Sku的编辑页面，加载sku编辑页面的数据
			if (productSku != null && productSku.getProductSkuId() != null) {
				// 获取库存信息
				if (product.getStatus().intValue() != Constants.STATUS_NOT_PUBLISHED
						&& product.getAvailabilityRule().intValue() != CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL
								.intValue()&&product.getAvailabilityRule().intValue() != CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL
								.intValue()) {
					Inventory inventory = inventoryManager
							.getInventoryBySku(productSku.getProductSkuId());
					mv.addObject("inventory", inventory);
				}
			}
			mv.addObject("productSku", productSku);
		}

		//产品实体目录
		Catalog catalogEntity=null;
		
		if (product.getProductId() != null) {
			// 加载产品类型关联的相关自定义属性
			List<ProductAttGroup> productAttGroups = productAttGroupManager
					.findProductAttGroupsByProductType(product
							.getProductTypeId());
			mv.addObject("productAttGroups", productAttGroups);
			// 获取产品媒体信息（包括产品更多图片及产品附件）
			List<ProductMedia> productMoreImages = productMediaManager
					.findProductMediaByProductIdAndType(product.getProductId(),
							CatalogConstants.PRODUCT_MEDIA_TYPE_MORE_IMAGE);
			List<ProductMedia> productAccessoriesAndMultdMedia = productMediaManager
					.findProductMediaByProductIdAndType(
							product.getProductId(),
							new Short[] {
									CatalogConstants.PRODUCT_MEDIA_TYPE_ACCESSORIES,
									CatalogConstants.PRODUCT_MEDIA_TYPE_MULTIMEDIA });
			mv.addObject("productMoreImages", productMoreImages);
			mv.addObject("productAccessoriesAndMultdMedias",
					productAccessoriesAndMultdMedia);
			
			List<ProductMediaUp> productMoreImagesUp = productMediaUpManager.findProductMediaByProductIdAndType(product.getProductId(), CatalogConstants.PRODUCT_MEDIA_TYPE_MORE_IMAGE);
			mv.addObject("productMoreImagesUp", productMoreImagesUp);
			
		} else {
			// 新增产品时指定产品目录(通过左侧的目录树确定该产品的主目录)
			Integer categoryId = RequestUtil.getInteger(request, "categoryId");
			Category category = categoryManager.getById(categoryId);
			mv.addObject("category", category);
			catalogEntity=category.getCatalog();

			// 新增产品并且不为产品包时，指定哪些产品类型存在SkuOption？用于确定选择该产品是否变种
			if (!isPackage) {
				List<ProductType> hasActiveSkuOptionProductTypes = productTypeManager.findActiveSkuOptionsProductType();
				String hasActiveSkuOptionProductTypeIds = ",";
				for (ProductType productType : hasActiveSkuOptionProductTypes) {
					hasActiveSkuOptionProductTypeIds += productType.getProductTypeId()+ ",";
				}
				mv.addObject("hasActiveSkuOptionProductTypeIds",hasActiveSkuOptionProductTypeIds);
			}
		}
		
		if(product.getProductId()!=null){
			//加载所有附件项
			List<AccessoryGroup> allAccessoryGroupList=accessoryGroupManager.findAllAccessoryGroup();
			JSONArray accessoryGroupJSONList=accessoryGroupListToJson(allAccessoryGroupList);
			mv.addObject("accessoryGroupJSONList",accessoryGroupJSONList);
			
			List<Map.Entry<AccessoryGroup, List<Accessory>>> productAccessoryList=productManager.getProductAccessoryMap(product.getProductId());
			mv.addObject("productAccessoryList",productAccessoryList);
			//产品拥有的附件Id串
			StringBuffer productAccessoryIds=new StringBuffer(",");
			Set<Accessory> accessoryList=product.getAccessories();
			if(accessoryList!=null){
				for (Accessory accessory : accessoryList) {
					productAccessoryIds.append(accessory.getAccessoryId().intValue());
					productAccessoryIds.append(",");
				}
			}
			mv.addObject("productAccessoryIds", productAccessoryIds);
		}
		//获取当前产品供应商信息
		if(product.getProductId()!=null){
			Supplier defaultSupplier=product.getSupplier();
			List<Supplier>supplierList=supplierProductManager.findSuppliersByProductId(product.getProductId());
			Collections.sort(supplierList);
			request.setAttribute("supplierList",supplierList);
			request.setAttribute("defaultSupplier",defaultSupplier);
		}
		
		
		//获取当前产品关联的Catalog
		List<Catalog>catalogList=new ArrayList<Catalog>();
		List<Integer>catalogIdList=new ArrayList<Integer>();
		Set<ProductCategory> productCategoryList=product.getProductCategorys();
		for (ProductCategory productCategory : productCategoryList){
			if(!catalogIdList.contains(productCategory.getCategory().getCatalogId())){
				catalogIdList.add(productCategory.getCategory().getCatalogId());
				catalogList.add(productCategory.getCategory().getCatalog());
			}
			/*if(catalogEntity==null&&productCategory.getCategory().getCatalog().getIsVirtual()==Constants.FLAG_FALSE){
				catalogEntity=productCategory.getCategory().getCatalog();
			}*/
		}
		request.setAttribute("catalogList", catalogList);
		request.setAttribute("catalogEntity", catalogEntity);
	}
	
	private JSONArray accessoryGroupListToJson(List<AccessoryGroup> allAccessoryGroupList){
		JSONArray accessoryGroupJSONList=new JSONArray();
		for (AccessoryGroup accessoryGroup : allAccessoryGroupList) {
			JSONObject jsonAccessoryGroup=new JSONObject();
			jsonAccessoryGroup.put("groupName", accessoryGroup.getGroupName());
			jsonAccessoryGroup.put("groupDesc", accessoryGroup.getGroupDesc().length()<35?accessoryGroup.getGroupDesc():accessoryGroup.getGroupDesc().substring(0,24));
			jsonAccessoryGroup.put("accessoryGroupId", accessoryGroup.getAccessoryGroupId());
			JSONArray jsonAccessorys=new JSONArray();
			Set<Accessory>accessorys=accessoryGroup.getAccessorys();
			for (Accessory accessory : accessorys) {
				JSONObject jsonAccessory=new JSONObject();
				jsonAccessory.put("accessoryId", accessory.getAccessoryId());
				jsonAccessory.put("accessoryName", accessory.getAccessoryName());
				jsonAccessory.put("price", accessory.getPrice()==null?0:accessory.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				jsonAccessorys.add(jsonAccessory);
			}
			
			jsonAccessoryGroup.put("accessorys", jsonAccessorys);
			accessoryGroupJSONList.add(jsonAccessoryGroup);
		}
		return accessoryGroupJSONList;
	}
	
	private String productTypeJSONData(List<ProductType> productTypeList){
		JSONArray productTypeJsonList=new JSONArray();
		for (ProductType productType : productTypeList) {
			JSONObject productTypeJson=new JSONObject();
			productTypeJson.put("productTypeId",productType.getProductTypeId());
			productTypeJson.put("minOrderQuantity",productType.getMinOrderQuantity()==null?"":productType.getMinOrderQuantity());
			productTypeJson.put("templatePath",productType.getTemplatePath()==null?"":productType.getTemplatePath());
			productTypeJsonList.add(productTypeJson);
		} 
		return productTypeJsonList.toString();
	}

	/**
	 * 当该产品有多个sku,或sku有skuOption选项时，返回null否则返回一个productSku或new productSku
	 * 
	 * @param product
	 * @return
	 */
	private ProductSku getOnlyOneProductSku(Product product) {
		Set<ProductSku> productSkus = product.getProductSkus();
		ProductSku productSku = null;
		if (productSkus == null || productSkus.size() == 0) {
			productSku = new ProductSku();
		} else if (productSkus.size() == 1) {
			productSku = (ProductSku) productSkus.iterator().next();
			Set<ProductSkuOptionValue> productSkuOptionValues = productSku
					.getProductSkuOptionValues();
			if (productSkuOptionValues != null
					&& productSkuOptionValues.size() > 0) {
				// productSku = null;
			}
		}
		return productSku;
	}

	/**
	 * 刷新SkuTab,当为变种产品时，Sku Tab显示为Sku列表，编辑保存Sku或在列表切换产品默认Sku时，调用本方法刷新Sku列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView refurbishSkuList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger
					.debug("entering ProductController 'refurbishSkuTab' method...");
		}
		ModelAndView mv = new ModelAndView("catalog/include/productSkuList");
		// 直接查找产品，通过产品获取该产品相应的Sku来刷新Sku列表;使productSkuList。jsp能重用上
		Integer productId = RequestUtil.getInteger(request, "productId");
		Product product = productManager.getById(productId);
		mv.addObject("product", product);
		return mv;
	}
	
	/**
	 * 为产品media提供sku列表选择
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView reloadSkuSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("catalog/include/productSkuSelect");
		Integer productId = RequestUtil.getInteger(request, "productId");
		Product product = productManager.getById(productId);
		mv.addObject("product", product);
		return mv;
	}

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}


	/**
	 * 按所点击的目录或搜索表单列出产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView getProductList(HttpServletRequest request,
			HttpServletResponse response) {
		return search(request, response);
	}

	/**
	 * 根据所选择的目录或搜索表单获取相应的产品
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#search(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		SearchCriteria searchCriteria = createSearchCriteria(request);
		ProductSearchCriteria productSearchCriteria = new ProductSearchCriteria();
		BaseBinder binder = new BaseBinder();
		binder.bind(request, productSearchCriteria, "productSearchCriteria");
		request.setAttribute("productSearchCriteria", productSearchCriteria);
		// 去除已删除状态
		productSearchCriteria.setExcludeProductStatus("3");
		searchCriteria = productManager.getProductSearchCriteria(
				searchCriteria, productSearchCriteria);
		List results = searchByCriteria(searchCriteria);
		
		return getModelAndView(getListView(), listModelName, results);
	}
	
	private String getListView()
	{
		return listView + RequestContext.getAdminInfo().getProductViewSetting();
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setProductMediaManager(ProductMediaManager productMediaManager) {
		this.productMediaManager = productMediaManager;
	}

	/**
	 * 简化GenericController的multiDelete，ajax处理，返回空数据
	 * 
	 */
	@Override
	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		try {
			String[] ids = request.getParameterValues("multiIds");
			if (ids != null && ids.length > 0) {
				List<Integer> productIds=new ArrayList<Integer>();
				for (String id : ids) {
					productMainManager.delete(Integer.valueOf(id));
					productIds.add(Integer.valueOf(id));
				}
				String message = getMessage("common.deleted.multi", new Object[]{ getEntityTypeMessage(), ids.length });
				//更新索引
				CatalogHelper.getInstance().indexNotifyDeleteEvent(productIds.toArray(new Integer[]{}));
				ajaxView.setMsg(message);
				ajaxView.setStatus(new Short("1"));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		
		return ajaxView;
	}

	/**
	 * 简化GenericController的delete，ajax处理，返回空数据
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#delete(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String entityId = request.getParameter(entityIdName);
			Product product=productManager.getById(new Integer(entityId));
			String entityName = getEntityName(product);
			productMainManager.delete(Integer.valueOf(entityId));
			//更新索引
			CatalogHelper.getInstance().indexNotifyDeleteEvent(Integer.valueOf(entityId));
			String message = getMessage("common.deleted", new Object[] {getEntityTypeMessage(), entityName });
			ajaxView.setMsg(message);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	/**
	 * 复制产品，处理方式类似window下的复制文件，复制后实体已存在，只是状态为草稿而已；ajax方式请求复制，成功后js再做页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return 返回action=1表示复制产品成功，否则为失败；productId为复制后创建的产品Id
	 * @throws Exception
	 */
	public ModelAndView copyProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Integer productId = RequestUtil.getInteger(request, "productId");
			Product origProduct = productManager.getById(productId);
			if(origProduct.getStatus().intValue()==Constants.STATUS_NOT_PUBLISHED){
				return ajaxView.setStatus(new Short("-1")).setMsg(getMessage("productLis.copyProduct.draft.fail"));
			}
			Product destProduct = productMainManager.doCopyProduct(origProduct);
			ajaxView.setData(data);
			data.put("productId", destProduct.getProductId());
			data.put("productCode", destProduct.getProductCode());
			ajaxView.setMsg(getMessage("productLis.copyProduct.successed",origProduct.getProductName()));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("productLis.copyProduct.fail"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	// 用于判断商品是否可修改，只有状态是草稿、激活和非激活的产品才能修改
	private boolean checkProductChangeAvailable(Product product) {
		if (Product.STATUS_DRAFT.equals(product.getStatus())
				|| Product.STATUS_ACTIVE.equals(product.getStatus())
				|| Product.STATUS_NOTACTIVE.equals(product.getStatus()))
			return true;
		return false;
	}

	// 用于判断商品的某些属性是否可修改，例如草稿状态下的产品就没有sku，所以sku的属性都不可以修改
	private boolean checkProductFieldChangeAvailable(Product product) {
		if (Product.STATUS_ACTIVE.equals(product.getStatus())
				|| Product.STATUS_NOTACTIVE.equals(product.getStatus()))
			return true;
		return false;
	}

	/**
	 * 联合批修改頁面显示
	 * xxxxx
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView associatedBulkEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String productIds = RequestUtil.getParameterNullSafe(request,"productIds");
		List<Product> productList = productManager.getByIds(productIds.split(","));
		String productSkuIds = "";
		for (Product product : productList) {
			if (checkProductFieldChangeAvailable(product)) {
				for (ProductSku sku : product.getProductSkus()) {
					productSkuIds += sku.getProductSkuId().toString() + ",";
				}
			}
		}
		if (productSkuIds.length() != 0) {
			productSkuIds = productSkuIds.substring(0,
					productSkuIds.length() - 1);
		}

		ModelAndView mv = new ModelAndView("catalog/associatedBulkEditForm");
		mv.addObject("productIds", productIds);
		mv.addObject("productSkuIds", productSkuIds);
		mv.addObject("productList", productList);
		return mv;
	}

	

	/**
	 * 统一批修改頁面显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView unitiveBulkEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String productIds = RequestUtil.getParameterNullSafe(request,
				"productIds");
		List<Product> productList = productManager.getByIds(productIds.split(","));
		String productSkuIds = "";
		for (Product product : productList) {
			if (checkProductFieldChangeAvailable(product)) {
				for (ProductSku sku : product.getProductSkus()) {
					productSkuIds += sku.getProductSkuId().toString() + ",";
				}
			}
		}
		if (productSkuIds.length() != 0) {
			productSkuIds = productSkuIds.substring(0,
					productSkuIds.length() - 1);
		}
		List<Brand> brandList = brandManager.getAll();
		ModelAndView mv = new ModelAndView("catalog/unitiveBulkEditForm");
		mv.addObject("productIds", productIds);
		mv.addObject("productSkuIds", productSkuIds);
		mv.addObject("brandList", brandList);
		mv.addObject("productList", productList);
		
		//加载目录树数据
		List<CategoryTreeItem> catalogTreeItems = categoryManager.getCatalogTreeItemList();
		
		request.setAttribute("categoryTreeItems", catalogTreeItems);
		return mv;
	}

	/**
	 * 统一批修改頁面保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView unitiveBulkEditSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			//检查有没有选择统一修改的属性
			Map<String, String[]> paramMap=request.getParameterMap();
			Set<String> paramNames=paramMap.keySet();
			boolean selectedProperty=false;
			for (String paramName : paramNames) {
				if(paramName.endsWith("Check")){
					selectedProperty=true;
					break;
				}
			}
			if(!selectedProperty){
				return ajaxView.setStatus(new Short("-1")).setMsg(getMessage("unitiveBulkEdit.product.select.property"));
			}
			String productIdsString = RequestUtil.getParameterNullSafe(request,"productIds");
			String productSkuIdsString = RequestUtil.getParameterNullSafe(request, "productSkuIds");
			String[] productIds = StringUtil.toArrayByDel(productIdsString, ",");
			String[] productSkuIds = StringUtil.toArrayByDel(productSkuIdsString, ",");
			Integer[] catalogIds = RequestUtil.getIntegerArrayNullSafe(request, "catalogId");

			ProductBulkEditCheckModel productBulkEditCheckModel= new ProductBulkEditCheckModel();
			try {
				ServletRequestDataBinder binder = bindAndValidate(request, productBulkEditCheckModel);
			}catch(Exception e){
				e.printStackTrace();
			}
			for (String productId : productIds) {
				try {
					Product product = productManager.getById(Integer.parseInt(productId));
					handleProductInUniBulkEdit(request,productBulkEditCheckModel, product,catalogIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != productSkuIds) {
				for (String productSkuId : productSkuIds) {
					try {
						ProductSku productSku = productSkuManager.getById(Integer.parseInt(productSkuId));
						handleProductSkuInUniBulkEdit(request,productBulkEditCheckModel, productSku);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			
			List<Integer> productIdList=new ArrayList<Integer>();
			for (String productId : productIds) {
				productIdList.add(Integer.valueOf(productId));
			}
			//更新索引
			CatalogHelper.getInstance().indexNotifyUpdateEvent(productIdList.toArray(new Integer[]{}));
			ajaxView.setMsg(getMessage("bulkEdit.commit_success"));
		} catch (ApplicationException e) {
			e.printStackTrace();
			ajaxView.setMsg(getMessage("bulkEdit.commit_fail"));
			ajaxView.setStatus(new Short("-500"));
		}
		return ajaxView;
	}

	private void handleProductInUniBulkEdit(HttpServletRequest request,
			ProductBulkEditCheckModel productBulkEditCheckModel, Product product,Integer[] catalogIds)
			throws ParseException {
		if (checkProductChangeAvailable(product)) {
			boolean flag = false;
			boolean productFieldFlag = checkProductFieldChangeAvailable(product);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getNameCheck()))
				flag |= handleProductNameInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getStatusCheck())
					&& productFieldFlag)
				flag |= handleProductStatusInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getBrandCheck()))
				flag |= handleProductBrandInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getMinOrderQuantityCheck()))
				flag |= handleProductMinOrderQuantityInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getIsAllowReviewCheck()))
				flag |= handleProductIsAllowReviewInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getTemplatePathCheck()))
				flag |= handleProductTemplatePathInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getPlanStartTimeCheck()))
				flag |= handleProductPlanStartTimeInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getPlanEndTimeCheck()))
				flag |= handleProductPlanEndTimeInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getShortDescriptionCheck())
					&& productFieldFlag)
				flag |= handleProductShortDescriptionInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getFullDescriptionCheck())
					&& productFieldFlag)
				flag |= handleProductFullDescriptionInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getTitleCheck())
					&& productFieldFlag)
				flag |= handleProductTitleInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getUrlCheck())
					&& productFieldFlag)
				flag |= handleProductUrlInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getMetaKeywordCheck())
					&& productFieldFlag)
				flag |= handleProductMetaKeywordInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getMetaDescriptionCheck())
					&& productFieldFlag)
				flag |= handleProductMetaDescriptionInUniBulkEdit(
						product, request);
			if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getMediaCheck())
					&& productFieldFlag)
				flag |= handleProductMediaInUniBulkEdit(product,
						request);
			if (flag) {
				productManager.save(product);
				productDescriptionManager.save(product
						.getProductDescription());
			}
			
			for (Integer catalogId : catalogIds){
				// 主目录和额外目录必须后面处理
				String mainCategoryCheck=request.getParameter("mainCategory_"+catalogId+"Check");
				String otherCategoryCheck=request.getParameter("otherCategory_"+catalogId+"Check");
				if (mainCategoryCheck!=null&&Constants.FLAG_TRUE.toString().equals(mainCategoryCheck))
					handleProductMainCategoryInUniBulkEdit(catalogId,product,request);
				if (otherCategoryCheck!=null&&Constants.FLAG_TRUE.toString().equals(otherCategoryCheck))
					handleProductOtherCategoryInUniBulkEdit(catalogId,product,request);
			}
			
			
			// 主供应商和额外供应商处理
			
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getMainSupplierCheck()))
				handleProductMainSupplierInUniBulkEdit(product,
						request);
			if (Constants.FLAG_TRUE.toString().equals(
					productBulkEditCheckModel.getOtherSupplierCheck()))
				handleProductOtherSupplierInUniBulkEdit(product,request);
		}
	}

	private void handleProductSkuInUniBulkEdit(HttpServletRequest request,
			ProductBulkEditCheckModel productBulkEditCheckModel,
			ProductSku productSku) throws ParseException {
		boolean flag = false;
		boolean productFieldFlag = checkProductFieldChangeAvailable(productSku
				.getProduct());
		if (Constants.FLAG_TRUE.toString().equals(
				productBulkEditCheckModel.getProductSkuStatusCheck())
				&& productFieldFlag)
			flag |= handleProductSkuStatusInUniBulkEdit(
					productSku, request);

		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getImageCheck())
				&& productFieldFlag)
			flag |= handleProductSkuImageInUniBulkEdit(
					productSku, request);

		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getPriceCheck())
				&& productFieldFlag)
			flag |= handleProductSkuPriceInUniBulkEdit(
					productSku, request);
		if (Constants.FLAG_TRUE.toString().equals(
				productBulkEditCheckModel.getSalePriceCheck())
				&& productFieldFlag)
			flag |= handleProductSkuSalePriceInUniBulkEdit(
					productSku, request);
		if (Constants.FLAG_TRUE.toString().equals(
				productBulkEditCheckModel.getListPriceCheck())
				&& productFieldFlag)
			flag |= handleProductSkuListPriceInUniBulkEdit(
					productSku, request);

		if (Constants.FLAG_TRUE.toString().equals(
				productBulkEditCheckModel.getWholeSalePriceCheck())
				&& productFieldFlag)
			flag |= handleProductSkuWholeSalePriceInUniBulkEdit(
					productSku, request);

		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getWeightCheck())
				&& productFieldFlag)
			flag |= handleProductSkuWeightInUniBulkEdit(
					productSku, request);
		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getLengthCheck())
				&& productFieldFlag)
			flag |= handleProductSkuLengthInUniBulkEdit(
					productSku, request);
		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getWidthCheck())
				&& productFieldFlag)
			flag |= handleProductSkuWidthInUniBulkEdit(
					productSku, request);
		if (Constants.FLAG_TRUE.toString().equals(productBulkEditCheckModel.getHeightCheck())
				&& productFieldFlag)
			flag |= handleProductSkuHeightInUniBulkEdit(
					productSku, request);

		if (flag) {
			productSkuManager.save(productSku);
		}
	}

	/**
	 * 设置产品状态
	 * 
	 * @param _product
	 * @param request
	 */
	private boolean handleProductStatusInUniBulkEdit(Product _product,
			HttpServletRequest request) {
		Product product = _product;

		String status = RequestUtil.getParameterNullSafe(request, "status");
		if (!StringUtils.isBlank(status)) {
			if (!product.getStatus().toString().equals(status)) {
				// 状态改变了
				product.setStatus(Short.parseShort(status));
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置产品名称
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductNameInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String name_method = RequestUtil.getParameterNullSafe(request,
				"name_method");
		if (name_method.equals("2")) {
			String namePrefix = RequestUtil.getParameterNullSafe(request,
					"name_prefix");
			String nameSuffix = RequestUtil.getParameterNullSafe(request,
					"name_suffix");
			if (!StringUtils.isBlank(namePrefix)) {
				if ((namePrefix + product.getProductName()).length() <= 128) {
					product.setProductName(namePrefix
							+ product.getProductName());
				}
			}
			if (!StringUtils.isBlank(nameSuffix)) {
				if ((nameSuffix + product.getProductName()).length() <= 128) {
					product.setProductName(product.getProductName()
							+ nameSuffix);
				}
			}
		} else if (name_method.equals("3")) {
			String name_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"name_findAndReplaceOldString");
			String name_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"name_findAndReplaceNewString");
			if (!StringUtils.isBlank(name_findAndReplaceOldString)) {
				String newname = product.getProductName().replace(
						name_findAndReplaceOldString,
						name_findAndReplaceNewString);
				if (newname.length() <= 128)
					product.setProductName(newname);
			}
		}
		return true;
	}

	/**
	 * 设置产品品牌
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductBrandInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String brand = RequestUtil.getParameterNullSafe(request, "brand");
		if (!StringUtils.isBlank(brand)) {
			product.setBrandId(Integer.parseInt(brand));
		} else {
			product.setBrand(null);
		}
		return true;
	}
	
	/**
	 * 设置产品最少订购量
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductMinOrderQuantityInUniBulkEdit(Product _product,HttpServletRequest request) throws ParseException {
		Product product = _product;
		String minOrderQuantity = RequestUtil.getParameterNullSafe(request,"minOrderQuantity");
		if (!StringUtils.isBlank(minOrderQuantity)) {
			product.setMinOrderQuantity(new Integer(minOrderQuantity));
		} else {
			
		}
		return true;
	}

	/**
	 * 设置是否接受评论
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductIsAllowReviewInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String isAllowReview = RequestUtil.getParameterNullSafe(request,
				"isAllowReview");
		if (!StringUtils.isBlank(isAllowReview)
				&& Constants.FLAG_TRUE.toString().equals(isAllowReview)) {
			product.setIsAllowReview(Constants.FLAG_TRUE);
		} else {
			product.setIsAllowReview(Constants.FLAG_FALSE);
		}
		return true;
	}

	/**
	 * 设置模板路径
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductTemplatePathInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String templatePath = RequestUtil.getParameterNullSafe(request,
				"templatePath");
		if (!StringUtils.isBlank(templatePath)) {
			product.setTemplatePath(templatePath);
		} else {
			product.setTemplatePath("");
		}
		return true;
	}

	/**
	 * 设置产品上架时间
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductPlanStartTimeInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String planStartTime = RequestUtil.getParameterNullSafe(request,
				"planStartTime");
		if (!StringUtils.isBlank(planStartTime)) {
			product.setPlanStartTime(DateUtil.convertStringToDate("yyyy-MM-dd",
					planStartTime));
		} else {
			product.setPlanStartTime(null);
		}
		return true;
	}

	/**
	 * 设置产品下架时间
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductPlanEndTimeInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String planEndTime = RequestUtil.getParameterNullSafe(request,
				"planEndTime");
		if (!StringUtils.isBlank(planEndTime)) {
			product.setPlanEndTime(DateUtil.convertStringToDate("yyyy-MM-dd",
					planEndTime));
		} else {
			product.setPlanEndTime(null);
		}
		return true;
	}

	/**
	 * 设置产品主目录
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductMainCategoryInUniBulkEdit(Integer catalogId,Product _product,HttpServletRequest request) throws ParseException {
		Product product = _product;
		Integer mainCategoryId =ServletRequestUtils.getIntParameter(request, "mainCategoryId_"+catalogId, -1) ;
		if (mainCategoryId>0) {
			Category mainCategory= product.getMainCategory(catalogId);
			if(mainCategory==null||mainCategory.getCategoryId().intValue()!=mainCategoryId){
				List<Integer> categoryIds = new ArrayList<Integer>();
				for (ProductCategory productCategory : product.getProductCategorys()) {
					if(productCategory.getCategory().getCatalogId().intValue()==catalogId&&productCategory.getIsMainCategory()!=Constants.FLAG_TRUE.intValue()){
						categoryIds.add(productCategory.getCategoryId());
					}
				}
				categoryIds.add(mainCategoryId);
				productMainManager.saveProductCategories(catalogId,product, mainCategoryId, categoryIds.toArray(new Integer[0]));
				productManager.refresh(product);// 必须更新
			}
		}
		return true;
	}

	/**
	 * 设置产品额外目录
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductOtherCategoryInUniBulkEdit(Integer catalogId,Product _product,HttpServletRequest request) throws ParseException {
		Product product = _product;
		String[] otherCategory = RequestUtil.getParameterValuesNullSafe(request, "otherCategory_"+catalogId);
		if (otherCategory!=null) {
			String otherCategory_method = RequestUtil.getParameterNullSafe(request,"otherCategory_method_"+catalogId);
			Integer mainCategoryId=product.getMainCategory(catalogId).getCategoryId();
			List<Integer> categoryIds = new ArrayList<Integer>();
			//全替换
			if (otherCategory_method.equals("1")) {
				for (String categoryId : otherCategory) {
					categoryIds.add(Integer.parseInt(categoryId));
				}
			}
			//在原来基础上增加
			else if(otherCategory_method.equals("2")){
				for (String categoryId : otherCategory) {
					categoryIds.add(Integer.parseInt(categoryId));
				}
				for (ProductCategory productCategory : product.getProductCategorys()) {
					if(productCategory.getCategory().getCatalogId().intValue()==catalogId){
						categoryIds.add(productCategory.getCategoryId());
					}
				}
				
			}
			//在原来基础上删除（减少）
			else if(otherCategory_method.equals("3")){
				for (ProductCategory productCategory : product.getProductCategorys()) {
					if(productCategory.getCategory().getCatalogId().intValue()==catalogId){
						if(ArrayUtils.indexOf(otherCategory,productCategory.getCategoryId()+"")==-1){
							categoryIds.add(productCategory.getCategoryId());
						}
					}
				}
			}
			if(otherCategory_method.equals("1")||otherCategory_method.equals("2")||otherCategory_method.equals("3")){
				categoryIds.add(mainCategoryId);
				productMainManager.saveProductCategories(catalogId,product, mainCategoryId, categoryIds.toArray(new Integer[0]));
				productManager.refresh(product);// 必须更新
			}
		}
		return true;
	}
	
	/**
	 * 设置产品主供应商（默认）
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductMainSupplierInUniBulkEdit(Product _product,HttpServletRequest request) throws ParseException {
		Product product = _product;
		String mainSupplierId = RequestUtil.getParameterNullSafe(request,"mainSupplierId");
		if (!StringUtils.isBlank(mainSupplierId)) {
			List<Integer> supplierIds = new ArrayList<Integer>();
			if(product.getSupplier()==null||(!product.getSupplier().getSupplierId().equals(mainSupplierId))){
				List<SupplierProduct> supplierProductList=supplierProductManager.findSupplierProductByProductId(product.getProductId());
				for (SupplierProduct supplierProduct : supplierProductList) {
					supplierIds.add(supplierProduct.getSupplierId());
				}
				//删除原来的主供应商（默认）
				if(product.getSupplier()!=null){
					supplierIds.remove(product.getSupplier().getSupplierId());
				}
				supplierIds.add(new Integer(mainSupplierId));
				productMainManager.saveProductSuppliersAction(product, new Integer(mainSupplierId), supplierIds.toArray(new Integer[0]));
				productManager.refresh(product);// 必须更新
			}
		}
		return true;
	}
	
	/**
	 * 设置产品额外供应商
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductOtherSupplierInUniBulkEdit(Product _product,HttpServletRequest request) throws ParseException {
		Product product = _product;
		String[] otherSupplier = RequestUtil.getParameterValuesNullSafe(request, "otherSupplier");
		if (otherSupplier!=null) {
			List<Integer> supplierIds = new ArrayList<Integer>();
			for (String supplierId : otherSupplier) {
				supplierIds.add(Integer.parseInt(supplierId));
			}
			Integer defaultSupplierId=null;
			if(product.getSupplier()!=null){
				defaultSupplierId=product.getSupplier().getSupplierId();
				supplierIds.add(defaultSupplierId);
			}else{
				defaultSupplierId=supplierIds.get(0);
			}
			productMainManager.saveProductSuppliersAction(product, defaultSupplierId, supplierIds.toArray(new Integer[0]));
			productManager.refresh(product);// 必须更新
		}
		return true;
	}

	/**
	 * 设置产品更多图片、更多附件
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductMediaInUniBulkEdit(Product _product,
			HttpServletRequest request) {
		Product product = _product;
		String mediaUrls[] = RequestUtil.getParameterValuesNullSafe(request,
				"productMedia_urls");
		String mediaTypes[] = RequestUtil.getParameterValuesNullSafe(request,
				"productMedia_types");
		String mediaDescriptions[] = RequestUtil.getParameterValuesNullSafe(
				request, "productMedia_descs");
		for (int i = 0; i < mediaUrls.length; i++) {
			ProductMedia productMedia = new ProductMedia();
			productMedia.setProduct(product);
			productMedia.setMediaUrl(mediaUrls[i]);
			productMedia.setMediaType(Short.parseShort(mediaTypes[i]));
			productMedia.setMediaDescription(mediaDescriptions[i]);
			productMediaManager.save(productMedia);
		}
		return true;

	}

	/**
	 * 设置产品简单描述
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductShortDescriptionInUniBulkEdit(
			Product _product, HttpServletRequest request) throws ParseException {
		Product product = _product;
		String shortDescription_method = RequestUtil.getParameterNullSafe(
				request, "shortDescription_method");
		if (shortDescription_method.equals("1")) {
			String shortDescritpion = RequestUtil.getParameterNullSafe(request,
					"shortDescription");
			product.getProductDescription().setShortDescription(
					shortDescritpion);
		} else if (shortDescription_method.equals("2")) {
			String shortDescritpionPrefix = RequestUtil.getParameterNullSafe(
					request, "shortDescription_prefix");
			String shortDescritpionSuffix = RequestUtil.getParameterNullSafe(
					request, "shortDescription_suffix");
			if (!StringUtils.isBlank(shortDescritpionPrefix)) {
				if ((shortDescritpionPrefix + product.getProductDescription()
						.getShortDescription()).length() <= 512) {
					product.getProductDescription().setShortDescription(
							shortDescritpionPrefix
									+ product.getProductDescription()
											.getShortDescription());
				}

			}
			if (!StringUtils.isBlank(shortDescritpionSuffix)) {
				if ((shortDescritpionSuffix + product.getProductDescription()
						.getShortDescription()).length() <= 512) {
					product.getProductDescription().setShortDescription(
							product.getProductDescription()
									.getShortDescription()
									+ shortDescritpionSuffix);
				}
			}
		} else if (shortDescription_method.equals("3")) {
			String shortDescription_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"shortDescription_findAndReplaceOldString");
			String shortDescription_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"shortDescription_findAndReplaceNewString");
			if (!StringUtils.isBlank(shortDescription_findAndReplaceOldString)
					) {
				String newShortDescription = product.getProductDescription()
						.getShortDescription().replace(
								shortDescription_findAndReplaceOldString,
								shortDescription_findAndReplaceNewString);
				if (newShortDescription.length() <= 512) {
					product.getProductDescription().setShortDescription(
							newShortDescription);
				}
			}
		}
		return true;
	}

	/**
	 * 设置产品详细描述
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductFullDescriptionInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String fullDescription_method = RequestUtil.getParameterNullSafe(
				request, "fullDescription_method");
		if (fullDescription_method.equals("1")) {
			String fullDescritpion = RequestUtil.getParameterNullSafe(request,
					"fullDescription");
			product.getProductDescription().setFullDescription(fullDescritpion);
		} else if (fullDescription_method.equals("2")) {
			String fullDescritpionPrefix = RequestUtil.getParameterNullSafe(
					request, "fullDescription_prefix");
			String fullDescritpionSuffix = RequestUtil.getParameterNullSafe(
					request, "fullDescription_suffix");
			if (!StringUtils.isBlank(fullDescritpionPrefix)) {
				if ((fullDescritpionPrefix + product.getProductDescription()
						.getFullDescription()).length() <= 20000) {
					product.getProductDescription().setFullDescription(
							fullDescritpionPrefix
									+ product.getProductDescription()
											.getFullDescription());
				}
			}
			if (!StringUtils.isBlank(fullDescritpionSuffix)) {
				if ((fullDescritpionSuffix + product.getProductDescription()
						.getFullDescription()).length() <= 20000) {
					product.getProductDescription().setFullDescription(
							product.getProductDescription()
									.getFullDescription()
									+ fullDescritpionSuffix);
				}
			}
		} else if (fullDescription_method.equals("3")) {
			String fullDescription_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"fullDescription_findAndReplaceOldString");
			String fullDescription_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"fullDescription_findAndReplaceNewString");
			if (!StringUtils.isBlank(fullDescription_findAndReplaceOldString)
					) {
				String newFullDescription = product.getProductDescription()
						.getFullDescription().replace(
								fullDescription_findAndReplaceOldString,
								fullDescription_findAndReplaceNewString);
				if (newFullDescription.length() <= 20000) {
					product.getProductDescription().setFullDescription(
							newFullDescription);
				}
			}
		}
		return true;
	}

	/**
	 * 设置产品标题
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductTitleInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String title_method = RequestUtil.getParameterNullSafe(request,
				"title_method");
		if (title_method.equals("1")) {
			String title = RequestUtil.getParameterNullSafe(request, "title");
			product.setTitle(title);
		} else if (title_method.equals("2")) {
			String titlePrefix = RequestUtil.getParameterNullSafe(request,
					"title_prefix");
			String titleSuffix = RequestUtil.getParameterNullSafe(request,
					"title_suffix");
			if (!StringUtils.isBlank(titlePrefix)) {
				if ((titlePrefix + product.getTitle()).length() <= 128) {
					product.setTitle(titlePrefix + product.getTitle());
				}
			}
			if (!StringUtils.isBlank(titleSuffix)) {
				if ((titleSuffix + product.getTitle()).length() <= 128) {
					product.setTitle(product.getTitle() + titleSuffix);
				}
			}
		} else if (title_method.equals("3")) {
			String title_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"title_findAndReplaceOldString");
			String title_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"title_findAndReplaceNewString");
			if (!StringUtils.isBlank(title_findAndReplaceOldString)
					) {
				String newTitle = product.getTitle().replace(
						title_findAndReplaceOldString,
						title_findAndReplaceNewString);
				if (newTitle.length() <= 128)
					product.setTitle(newTitle);
			}
		}
		return true;
	}

	/**
	 * 设置产品url
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductUrlInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String url_method = RequestUtil.getParameterNullSafe(request,
				"url_method");
		if (url_method.equals("1")) {
			String url = RequestUtil.getParameterNullSafe(request, "url");
			product.setUrl(url);
		} else if (url_method.equals("2")) {
			String urlPrefix = RequestUtil.getParameterNullSafe(request,
					"url_prefix");
			String urlSuffix = RequestUtil.getParameterNullSafe(request,
					"url_suffix");
			if (!StringUtils.isBlank(urlPrefix)) {
				if ((urlPrefix + product.getUrl()).length() <= 255) {
					product.setUrl(urlPrefix + product.getUrl());
				}
			}
			if (!StringUtils.isBlank(urlSuffix)) {
				if ((urlSuffix + product.getUrl()).length() <= 255) {
					product.setUrl(product.getUrl() + urlSuffix);
				}
			}
		} else if (url_method.equals("3")) {
			String url_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"url_findAndReplaceOldString");
			String url_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"url_findAndReplaceNewString");
			if (!StringUtils.isBlank(url_findAndReplaceOldString)
					) {
				String newUrl = product.getUrl().replace(
						url_findAndReplaceOldString,
						url_findAndReplaceNewString);
				if (newUrl.length() <= 255)
					product.setUrl(newUrl);
			}
		}
		return true;
	}

	/**
	 * 设置产品metaKeyword
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductMetaKeywordInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String metaKeyword_method = RequestUtil.getParameterNullSafe(request,
				"metaKeyword_method");
		if (metaKeyword_method.equals("1")) {
			String metaKeyword = RequestUtil.getParameterNullSafe(request,
					"metaKeyword");
			product.setMetaKeyword(metaKeyword);
		} else if (metaKeyword_method.equals("2")) {
			String metaKeywordPrefix = RequestUtil.getParameterNullSafe(
					request, "metaKeyword_prefix");
			String metaKeywordSuffix = RequestUtil.getParameterNullSafe(
					request, "metaKeyword_suffix");
			if (!StringUtils.isBlank(metaKeywordPrefix)) {
				if ((metaKeywordPrefix + product.getMetaKeyword()).length() <= 256) {
					product.setMetaKeyword(metaKeywordPrefix
							+ product.getMetaKeyword());
				}
			}
			if (!StringUtils.isBlank(metaKeywordSuffix)) {
				if ((metaKeywordSuffix + product.getMetaKeyword()).length() <= 256) {
					product.setMetaKeyword(product.getMetaKeyword()
							+ metaKeywordSuffix);
				}
			}
		} else if (metaKeyword_method.equals("3")) {
			String metaKeyword_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"metaKeyword_findAndReplaceOldString");
			String metaKeyword_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"metaKeyword_findAndReplaceNewString");
			if (!StringUtils.isBlank(metaKeyword_findAndReplaceOldString)
					) {
				String newMetaKeyword = product.getMetaKeyword().replace(
						metaKeyword_findAndReplaceOldString,
						metaKeyword_findAndReplaceNewString);
				if (newMetaKeyword.length() <= 256)
					product.setMetaKeyword(newMetaKeyword);
			}
		}
		return true;
	}

	/**
	 * 设置产品metaKeyDescription
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductMetaDescriptionInUniBulkEdit(Product _product,
			HttpServletRequest request) throws ParseException {
		Product product = _product;
		String metaDescription_method = RequestUtil.getParameterNullSafe(
				request, "metaDescription_method");
		if (metaDescription_method.equals("1")) {
			String metaDescription = RequestUtil.getParameterNullSafe(request,
					"metaDescription");
			product.setMetaDescription(metaDescription);
		} else if (metaDescription_method.equals("2")) {
			String metaDescriptionPrefix = RequestUtil.getParameterNullSafe(
					request, "metaDescription_prefix");
			String metaDescriptionSuffix = RequestUtil.getParameterNullSafe(
					request, "metaDescription_suffix");
			if (!StringUtils.isBlank(metaDescriptionPrefix)) {
				if ((metaDescriptionPrefix + product.getMetaDescription())
						.length() <= 256) {
					product.setMetaDescription(metaDescriptionPrefix
							+ product.getMetaDescription());
				}
			}
			if (!StringUtils.isBlank(metaDescriptionSuffix)) {
				if ((metaDescriptionSuffix + product.getMetaDescription())
						.length() <= 256) {
					product.setMetaDescription(product.getMetaDescription()
							+ metaDescriptionSuffix);
				}
			}
		} else if (metaDescription_method.equals("3")) {
			String metaDescription_findAndReplaceOldString = RequestUtil
					.getParameterNullSafe(request,
							"metaDescription_findAndReplaceOldString");
			String metaDescription_findAndReplaceNewString = RequestUtil
					.getParameterNullSafe(request,
							"metaDescription_findAndReplaceNewString");
			if (!StringUtils.isBlank(metaDescription_findAndReplaceOldString)
					) {
				String newMetaDescription = product.getMetaDescription()
						.replace(metaDescription_findAndReplaceOldString,
								metaDescription_findAndReplaceNewString);
				if (newMetaDescription.length() <= 256)
					product.setMetaDescription(newMetaDescription);
			}
		}
		return true;
	}

	/**
	 * 设置sku状态
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductSkuStatusInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) {
		ProductSku productSku = _productSku;
		String status = RequestUtil.getParameterNullSafe(request,
				"productSkuStatus");
		if (!StringUtils.isBlank(status)) {
			if (!productSku.getStatus().toString().equals(status)) {
				// 状态改变了
				productSku.setStatus(Short.parseShort(status));
				return true;
			}
		}
		return false;

	}

	/**
	 * 设置sku图片
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductSkuImageInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) {
		ProductSku productSku = _productSku;
		String image = RequestUtil.getParameterNullSafe(request, "image");
		if (!StringUtils.isBlank(image)) {
			productSku.setImage(image);
		}
		return true;

	}

	/**
	 * 设置sku售价
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductSkuPriceInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) {
		ProductSku productSku = _productSku;
		String price_method = RequestUtil.getParameterNullSafe(request,
				"price_method");

		if (price_method.equals("1")) {
			String price = RequestUtil.getParameterNullSafe(request, "price");
			if (!StringUtils.isBlank(price)) {
				productSku.setPrice(new BigDecimal(price));
				return true;
			}
		} else if (price_method.equals("4")) {
			String price_method_operator = RequestUtil.getParameterNullSafe(
					request, "price_method_operator");
			String price_numeric = RequestUtil.getParameterNullSafe(request,
					"price_numeric");
			BigDecimal numeric = new BigDecimal(price_numeric);
			if (price_method_operator.equals("1")) {
				// 加法
				productSku.setPrice(productSku.getPrice().add(numeric));
				return true;
			} else if (price_method_operator.equals("2")) {
				// 减法
				if (numeric.compareTo(productSku.getPrice()) <= 0) {
					productSku.setPrice(productSku.getPrice().add(
							numeric.negate()));
					return true;
				}
			} else if (price_method_operator.equals("3")) {
				// 乘法
				productSku.setPrice(productSku.getPrice().multiply(numeric));
				return true;
			}

		}
		return false;

	}

	/**
	 * 设置sku特价
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductSkuSalePriceInUniBulkEdit(
			ProductSku _productSku, HttpServletRequest request) {
		ProductSku productSku = _productSku;
		String salePrice_method = RequestUtil.getParameterNullSafe(request,
				"salePrice_method");

		if (salePrice_method.equals("1")) {
			String salePrice = RequestUtil.getParameterNullSafe(request,
					"salePrice");
			if (!StringUtils.isBlank(salePrice)) {
				productSku.setSalePrice(new BigDecimal(salePrice));
				return true;
			} else {
				productSku.setSalePrice(null);
				return true;
			}
		} else if (salePrice_method.equals("4")) {
			String salePrice_method_operator = RequestUtil
					.getParameterNullSafe(request, "salePrice_method_operator");
			String salePrice_numeric = RequestUtil.getParameterNullSafe(
					request, "salePrice_numeric");
			BigDecimal numeric = new BigDecimal(salePrice_numeric);
			if (null == productSku.getSalePrice()) {
				// 如果没有特价，必须先给一个默认值
				productSku.setSalePrice(new BigDecimal("0"));
			}
			if (salePrice_method_operator.equals("1")) {
				// 加法
				productSku.setSalePrice(productSku.getSalePrice().add(numeric));
				return true;
			} else if (salePrice_method_operator.equals("2")) {
				// 减法
				if (numeric.compareTo(productSku.getSalePrice()) <= 0) {
					productSku.setSalePrice(productSku.getSalePrice().add(
							numeric.negate()));
					return true;
				}
			} else if (salePrice_method_operator.equals("3")) {
				// 乘法
				productSku.setSalePrice(productSku.getSalePrice().multiply(
						numeric));
				return true;
			}

		}
		return false;

	}

	/**
	 * 设置sku市场价
	 * 
	 * @param _productSku
	 * @param request
	 */
	private boolean handleProductSkuListPriceInUniBulkEdit(
			ProductSku _productSku, HttpServletRequest request) {
		ProductSku productSku = _productSku;
		String listPrice_method = RequestUtil.getParameterNullSafe(request,
				"listPrice_method");

		if (listPrice_method.equals("1")) {
			String listPrice = RequestUtil.getParameterNullSafe(request,
					"listPrice");
			if (!StringUtils.isBlank(listPrice)) {
				productSku.setListPrice(new BigDecimal(listPrice));
				return true;
			} else {
				productSku.setListPrice(null);
				return true;
			}
		} else if (listPrice_method.equals("4")) {
			String listPrice_method_operator = RequestUtil
					.getParameterNullSafe(request, "listPrice_method_operator");
			String listPrice_numeric = RequestUtil.getParameterNullSafe(
					request, "listPrice_numeric");
			BigDecimal numeric = new BigDecimal(listPrice_numeric);
			if (null == productSku.getListPrice()) {
				// 如果没有市场价，必须先给一个默认值
				productSku.setListPrice(new BigDecimal("0"));
			}
			if (listPrice_method_operator.equals("1")) {
				// 加法
				productSku.setListPrice(productSku.getListPrice().add(numeric));
				return true;
			} else if (listPrice_method_operator.equals("2")) {
				// 减法
				if (numeric.compareTo(productSku.getListPrice()) <= 0) {
					productSku.setListPrice(productSku.getListPrice().add(
							numeric.negate()));
					return true;
				}
			} else if (listPrice_method_operator.equals("3")) {
				// 乘法
				productSku.setListPrice(productSku.getListPrice().multiply(
						numeric));
				return true;
			}

		}
		return false;

	}

	/**
	 * 设置sku批发价
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductSkuWholeSalePriceInUniBulkEdit(
			ProductSku _productSku, HttpServletRequest request)
			throws ParseException {

		String wholesalePrice_prices[] = RequestUtil
				.getParameterValuesNullSafe(request, "wholesalePrice_price");
		String wholesalePrice_minQuantitys[] = RequestUtil
				.getParameterValuesNullSafe(request,
						"wholesalePrice_minQuantity");
		if (wholesalePrice_prices != null) {
			for (int i = 0; i < wholesalePrice_prices.length; i++) {
				WholesalePrice wholeSalePrice = wholesalePriceManager
						.getSalePriceBySkuIdByMinQuantity(_productSku
								.getProductSkuId(), Integer
								.parseInt(wholesalePrice_minQuantitys[i]));
				if (null != wholeSalePrice) {
					// 修改批发价
					wholeSalePrice.setPrice(new BigDecimal(
							wholesalePrice_prices[i]));
					wholesalePriceManager.save(wholeSalePrice);
				} else {
					// 新增批发价
					wholeSalePrice = new WholesalePrice();
					wholeSalePrice.setProductSku(_productSku);
					wholeSalePrice.setPrice(new BigDecimal(
							wholesalePrice_prices[i]));
					wholeSalePrice.setMinQuantity(Integer
							.parseInt(wholesalePrice_minQuantitys[i]));
					wholesalePriceManager.save(wholeSalePrice);
				}
			}
		}
		return true;
	}

	/**
	 * 设置sku重量
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductSkuWeightInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) throws ParseException {
		ProductSku productSku = _productSku;
		String weight = RequestUtil.getParameterNullSafe(request, "weight");
		if (!StringUtils.isBlank(weight)) {
			productSku.setWeight(new BigDecimal(weight));
		} else {
			productSku.setWeight(null);
		}
		return true;
	}

	/**
	 * 设置sku长度
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductSkuLengthInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) throws ParseException {
		ProductSku productSku = _productSku;
		String length = RequestUtil.getParameterNullSafe(request, "length");
		if (!StringUtils.isBlank(length)) {
			productSku.setLength(new BigDecimal(length));
		} else {
			productSku.setLength(null);
		}
		return true;
	}

	/**
	 * 设置sku宽度
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductSkuWidthInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) throws ParseException {
		ProductSku productSku = _productSku;
		String width = RequestUtil.getParameterNullSafe(request, "width");
		if (!StringUtils.isBlank(width)) {
			productSku.setWidth(new BigDecimal(width));
		} else {
			productSku.setWidth(null);
		}
		return true;
	}

	/**
	 * 设置sku高度
	 * 
	 * @param _product
	 * @param request
	 * @throws ParseException
	 */
	private boolean handleProductSkuHeightInUniBulkEdit(ProductSku _productSku,
			HttpServletRequest request) throws ParseException {
		ProductSku productSku = _productSku;
		String height = RequestUtil.getParameterNullSafe(request, "height");
		if (!StringUtils.isBlank(height)) {
			productSku.setHeight(new BigDecimal(height));
		} else {
			productSku.setHeight(null);
		}
		return true;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	public void setProductAttGroupItemManager(
			ProductAttGroupItemManager productAttGroupItemManager) {
		this.productAttGroupItemManager = productAttGroupItemManager;
	}

	public void setProductDescriptionManager(
			ProductDescriptionManager productDescriptionManager) {
		this.productDescriptionManager = productDescriptionManager;
	}

	public void setProductCategoryManager(
			ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}
	
	public ModelAndView eachEditSelect(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mv=new ModelAndView("/catalog/productEachEditSelect");
		String productIds = request.getParameter("productIds");
		List<Product> productList = productManager.getByIds(productIds.split(","));
		ConfigUtil configUtil=ConfigUtil.getInstance();
		mv.addObject("prodCommAttrs",configUtil.getBulkProdCommAttrs());
		mv.addObject("skuCommAttrs", configUtil.getBulkSkuCommAttrs());
		String prodAttrs[]=configUtil.getBulkProdAttrs();
		List<Attribute>productAttributeList=new ArrayList<Attribute>();
		for (String prodAttr : prodAttrs) {
			Attribute attribute=attributeService.getAttribute(prodAttr);
			if(attribute!=null)
				productAttributeList.add(attribute);
		}
		mv.addObject("prodAttrs", productAttributeList);
		mv.addObject("productList",productList);
		mv.addObject("productIds", productIds);
		return mv;
	}
	
	public ModelAndView productEachEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("/catalog/productEachEdit");
		String productIds=request.getParameter("productIds");
		String []sel_bulk_pro_comm_attrs=request.getParameterValues("sel_bulk_pro_comm_attrs");
		BulkProductModel bulkProductModel=new BulkProductModel();
		bulkProductModel.setProductIds(productIds);
		//TODO 暂时跳过选择批修改属性
		sel_bulk_pro_comm_attrs=ConfigUtil.getInstance().getBulkProdCommAttrs();
		bulkProductModel.setProdCommAttrs(sel_bulk_pro_comm_attrs);
		String []sel_bulk_sku_attrs=request.getParameterValues("sel_bulk_sku_attrs");
		sel_bulk_sku_attrs=ConfigUtil.getInstance().getBulkSkuCommAttrs();
		bulkProductModel.setSkuCommAttrs(sel_bulk_sku_attrs);
		String []sel_bulk_pro_attrs=request.getParameterValues("sel_bulk_pro_attrs");
		sel_bulk_pro_attrs=ConfigUtil.getInstance().getBulkProdAttrs();
		bulkProductModel.setProdAttrs(sel_bulk_pro_attrs);
		List<BulkProduct> bulkProductList=BulkHelper.getInstance().builderBulkProduct(bulkProductModel);
		mv.addObject("bulkProductList",bulkProductList);
		mv.addObject("prodCommAttrs",bulkProductModel.getProdCommAttrs());
		mv.addObject("skuCommAttrs",bulkProductModel.getSkuCommAttrs());
		mv.addObject("prodAttrs",bulkProductModel.getProdAttrs());
		mv.addObject("productIds",productIds);
		return mv;
	}
	
	public ModelAndView productEachEditUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		Map<String,String[]>postData=request.getParameterMap();
		
		try {
			List<BulkProduct>bulkProductList=BulkHelper.getInstance().builderBulkProductByMap(postData);
			try {
				ajaxView.setMsg(getMessage("eachEditUpdate.commit.success"));
				productMainManager.doBulkProduct(bulkProductList);
			} catch (BulkUpdateException e) {
				ajaxView.setMsg(getMessage("eachEditUpdate.commit.fail",e.getMsg()));
				ajaxView.setStatus(new Short("-1"));
				data.put("bulkUpdateException", new JSONObject().fromObject(e));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView updateProductPublishTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		Integer productId=ServletRequestUtils.getIntParameter(request,"productId");
		String publishTime=request.getParameter("publishTime");
		try {
			publishTime=publishTime.trim();
			Date publishTimeD=null;
			try {
				publishTimeD=DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", publishTime);
			} catch (Exception e) {
				return ajaxView.setStatus(new Short("0"));
			}
			Product product=productManager.getById(productId);
			product.setPublishTime(publishTimeD);
			productManager.save(product);
			
			//更新索引
			CatalogHelper.getInstance().indexNotifyUpdateEvent(product.getProductId());
			
			data.put("productId", productId);
			data.put("publishTime", DateUtil.getDate(publishTimeD, "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			// TODO: handle exception
			ajaxView.setStatus(new Short("-500"));
		}
		return ajaxView;
	}

	public ProductMediaUpManager getProductMediaUpManager() {
		return productMediaUpManager;
	}

	public void setProductMediaUpManager(ProductMediaUpManager productMediaUpManager) {
		this.productMediaUpManager = productMediaUpManager;
	}

}

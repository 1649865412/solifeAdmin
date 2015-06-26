package com.cartmatic.estore.catalog.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.attribute.service.AttributeValueManager;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.helper.BulkHelper;
import com.cartmatic.estore.catalog.model.BulkField;
import com.cartmatic.estore.catalog.model.BulkProduct;
import com.cartmatic.estore.catalog.model.BulkProductSku;
import com.cartmatic.estore.catalog.service.AccessoryManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductAttGroupManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.catalog.service.ProductDescriptionManager;
import com.cartmatic.estore.catalog.service.ProductHandDrawManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductMediaManager;
import com.cartmatic.estore.catalog.service.ProductMediaUpManager;
import com.cartmatic.estore.catalog.service.ProductPackageItemManager;
import com.cartmatic.estore.catalog.service.ProductRateItemManager;
import com.cartmatic.estore.catalog.service.ProductRateValueManager;
import com.cartmatic.estore.catalog.service.ProductReviewManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.ProductSkuOptionValueManager;
import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.catalog.service.SkuOptionManager;
import com.cartmatic.estore.catalog.service.SkuOptionValueManager;
import com.cartmatic.estore.catalog.service.WholesalePriceManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.model.catalog.ProductCompareModel;
import com.cartmatic.estore.common.model.catalog.ProductDataModel;
import com.cartmatic.estore.common.model.catalog.ProductDescription;
import com.cartmatic.estore.common.model.catalog.ProductHandDraw;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.common.model.catalog.ProductPackageItem;
import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.common.model.catalog.ProductRateValue;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductSkuOptionValue;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.common.service.RecommendedService;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.content.service.UploadManager;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.exception.BulkUpdateException;
import com.cartmatic.estore.supplier.SupplierConstants;
import com.cartmatic.estore.supplier.service.SupplierManager;
import com.cartmatic.estore.supplier.service.SupplierProductManager;

public class ProductMainManagerImpl implements ProductMainManager {
	private ProductSkuManager productSkuManager = null;
	private SkuOptionManager skuOptionManager = null;
	private CategoryManager categoryManager = null;
	private ProductCategoryManager productCategoryManager = null;
	private ProductManager productManager = null;
	private WholesalePriceManager wholesalePriceManager = null;
	private ProductDescriptionManager productDescriptionManager = null;
	
	private ProductHandDrawManager productHandDrawManager = null;
	
	private AttributeService attributeService = null;
	private ProductMediaManager productMediaManager = null;
	private ProductMediaUpManager productMediaUpManager = null;
	private ProductPackageItemManager productPackageItemManager = null;

	private InventoryService inventoryService = null;
	private ProductSkuOptionValueManager productSkuOptionValueManager = null;
	private AttributeValueManager attributeValueManager = null;
	private ProductAttGroupManager productAttGroupManager = null;
	private ProductReviewManager productReviewManager = null;
	private ProductRateItemManager productRateItemManager = null;
	private ProductRateValueManager productRateValueManager = null;
	private ProductStatManager productStatManager = null;
	private RecommendedService recommendedService = null;
	private AccessoryManager accessoryManager=null;
	private SupplierManager supplierManager=null;
	private SupplierProductManager supplierProductManager=null;
	private ProductTypeManager productTypeManager=null;
	private UploadManager uploadManager=null;
	private SkuOptionValueManager skuOptionValueManager=null;
	
	
	public void setSkuOptionValueManager(SkuOptionValueManager skuOptionValueManager) {
		this.skuOptionValueManager = skuOptionValueManager;
	}

	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}

	public void setSupplierProductManager(
			SupplierProductManager supplierProductManager) {
		this.supplierProductManager = supplierProductManager;
	}

	public void setAccessoryManager(AccessoryManager accessoryManager) {
		this.accessoryManager = accessoryManager;
	}
	private final transient Log logger = LogFactory.getLog(ProductMainManagerImpl.class);

	public void setRecommendedService(RecommendedService recommendedService) {
		this.recommendedService = recommendedService;
	}

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}

	public void setAttributeValueManager(AttributeValueManager attributeValueManager) {
		this.attributeValueManager = attributeValueManager;
	}

	public void setProductSkuOptionValueManager(ProductSkuOptionValueManager productSkuOptionValueManager) {
		this.productSkuOptionValueManager = productSkuOptionValueManager;
	}

	public void setWholesalePriceManager(WholesalePriceManager wholesalePriceManager) {
		this.wholesalePriceManager = wholesalePriceManager;
	}

	public void saveProductAction(Product product, Integer[] mainCategoryIds, Integer[] categoryIds) {
		if (product.getProductId() == null) {
			productManager.save(product);
		}
		// 设置产品目录
		saveProductCategories(product, mainCategoryIds, categoryIds);
		productManager.save(product);
	}

	/**
	 * 设置更新产品关联目录，
	 * @param product 
	 * @param mainCategoryId
	 * @param categoryIds
	 */
	public void saveProductCategories(Product product, Integer[] mainCategoryIds, Integer[] categoryIds) {
		//去除重复目录
		Set<Integer> tempCategoryIds=new HashSet<Integer>();
		CollectionUtils.addAll(tempCategoryIds, mainCategoryIds);
		CollectionUtils.addAll(tempCategoryIds, categoryIds);
		categoryIds=tempCategoryIds.toArray(new Integer[]{});
		
		//检查去除重复主目录
		Set<Integer> tempMainCategoryIds=new HashSet<Integer>();
		List<Integer>catalogList=new ArrayList<Integer>();
		for (Integer mainCategoryId : mainCategoryIds){
			Category category=categoryManager.getById(mainCategoryId);
			if(!catalogList.contains(category.getCatalogId())){
				catalogList.add(category.getCatalogId());
				tempMainCategoryIds.add(mainCategoryId);
			}
		}
		mainCategoryIds=tempMainCategoryIds.toArray(new Integer[]{});
		
		//检查catalog下的目录,防止出现没有主目录情况
		for (Integer categoryId : categoryIds){
			Category category=categoryManager.getById(categoryId);
			if(!catalogList.contains(category.getCatalogId())){
				throw new RuntimeException("有目录,没有指定主商品分类!");
			}
		}
		
		
		
		Set<ProductCategory> productCategories = product.getProductCategorys();
		// 保留的目录
		Set<ProductCategory> reserved_productCategories = new HashSet<ProductCategory>();
		// 清除没有关联的目录
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				boolean flag =ArrayUtils.contains(categoryIds, productCategory.getCategoryId());
				if (flag) {
					reserved_productCategories.add(productCategory);
				} else {
					productCategoryManager.deleteById(productCategory.getProductCategoryId());
				}
			}
		}
		// 添加目录
		for (Integer categoryId : categoryIds) {
			boolean flag = true;
			ProductCategory tempProductCategory = null;
			for (ProductCategory productCategory : reserved_productCategories) {
				if (productCategory.getCategoryId().equals(categoryId)) {
					flag = false;
					tempProductCategory = productCategory;
					break;
				}
			}
			if (flag) {
				//产品新增关联目录
				ProductCategory productCategory = new ProductCategory();
				Category category = categoryManager.getById(categoryId);
				productCategory.setCategory(category);
				productCategory.setProduct(product);
				//hqm **原CategoryPath不会为空。。。
				productCategory.setCategoryPath((StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + product.getProductId());
				if (ArrayUtils.contains(mainCategoryIds, categoryId)) {
					productCategory.setIsMainCategory(Constants.FLAG_TRUE);
				} else {
					productCategory.setIsMainCategory(Constants.FLAG_FALSE);
				}
				productCategory.setSortOrder(product.getSortOrder());
				productCategoryManager.save(productCategory);
				
				
				//增加链接目录关联
				if(product.getCreateTime().compareTo(product.getUpdateTime())==0){
					addProductCategory(productCategory, categoryIds, catalogList);
				}
			} else {
				// 变更主目录
				if (ArrayUtils.contains(mainCategoryIds, categoryId)) {
					tempProductCategory.setIsMainCategory(Constants.FLAG_TRUE);
					productCategoryManager.save(tempProductCategory);
				} else {
					tempProductCategory.setIsMainCategory(Constants.FLAG_FALSE);
					productCategoryManager.save(tempProductCategory);
				}
				
				//增加链接目录关联
				//addProductCategory(tempProductCategory, categoryIds, catalogList);
			}
		}
	}
	
	
	public void saveProductCategories(Integer catalogId,Product product, Integer mainCategoryId, Integer[] categoryIds) {
		//主商品分类没有指定的，不处理
		if(mainCategoryId==null){
			return ;
//			throw new RuntimeException("目录,没有指定主商品分类!");
		}

		Category tempMainCategory = categoryManager.getById(mainCategoryId);
		if(tempMainCategory==null||tempMainCategory.getCatalogId().intValue()!=catalogId){
			return ;
//			throw new RuntimeException("目录,没有指定主商品分类!");
		}
		//去除重复目录
		Set<Integer> tempCategoryIds=new HashSet<Integer>();
		tempCategoryIds.add(mainCategoryId);
		CollectionUtils.addAll(tempCategoryIds, categoryIds);
		categoryIds=tempCategoryIds.toArray(new Integer[]{});
		
	
		
		
		Set<ProductCategory> productCategories = product.getProductCategorys();
		// 保留的目录
		Set<ProductCategory> reserved_productCategories = new HashSet<ProductCategory>();
		// 清除没有关联的目录
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				if(productCategory.getCategory().getCatalogId().intValue()==catalogId){
					boolean flag =ArrayUtils.contains(categoryIds, productCategory.getCategoryId());
					if (flag) {
						reserved_productCategories.add(productCategory);
					} else {
						productCategoryManager.deleteById(productCategory.getProductCategoryId());
					}
				}
			}
		}
		// 添加目录
		for (Integer categoryId : categoryIds) {
			boolean flag = true;
			ProductCategory tempProductCategory = null;
			for (ProductCategory productCategory : reserved_productCategories) {
				if (productCategory.getCategoryId().equals(categoryId)) {
					flag = false;
					tempProductCategory = productCategory;
					break;
				}
			}
			if (flag) {
				//产品新增关联目录
				ProductCategory productCategory = new ProductCategory();
				Category category = categoryManager.getById(categoryId);
				//检查商品分类是否当前Catalog
				if(category.getCatalogId().intValue()!=catalogId){
					continue;
				}
				productCategory.setCategory(category);
				productCategory.setProduct(product);
				productCategory.setCategoryPath((StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + product.getProductId());
				if (mainCategoryId==mainCategoryId.intValue()) {
					productCategory.setIsMainCategory(Constants.FLAG_TRUE);
				} else {
					productCategory.setIsMainCategory(Constants.FLAG_FALSE);
				}
				productCategory.setSortOrder(product.getSortOrder());
				productCategoryManager.save(productCategory);
			
			} else {
				// 变更主目录
				if (mainCategoryId==mainCategoryId.intValue()) {
					tempProductCategory.setIsMainCategory(Constants.FLAG_TRUE);
					productCategoryManager.save(tempProductCategory);
				} else {
					tempProductCategory.setIsMainCategory(Constants.FLAG_FALSE);
					productCategoryManager.save(tempProductCategory);
				}
				
			}
		}
	}
	
	private void addProductCategory(ProductCategory productCategory,Integer []allCategoryIds,List<Integer> allCatalogIdList){
		if(productCategory.getCategory().getCatalog().getIsVirtual()==Constants.FLAG_FALSE.intValue()){
			List<Category>linkCategoryList=categoryManager.getCategoryListByLinkedCategory(productCategory.getCategory().getId());
			for (Category linkCategory : linkCategoryList){
				if((!ArrayUtils.contains(allCategoryIds, linkCategory.getId()))&&!allCatalogIdList.contains(linkCategory.getCatalogId())){
					ProductCategory newProductCategory=new ProductCategory();
					newProductCategory.setCategory(linkCategory);
					newProductCategory.setProduct(productCategory.getProduct());
					newProductCategory.setCategoryPath((StringUtils.isNotEmpty(linkCategory.getCategoryPath())?linkCategory.getCategoryPath():"") + linkCategory.getCategoryId() + "." + newProductCategory.getProduct().getProductId());
					newProductCategory.setSortOrder(productCategory.getSortOrder());
					newProductCategory.setIsMainCategory(Constants.FLAG_TRUE);
					
					//检查该目录是否已经存在该产品
					List<ProductCategory> tempProductCateogryList=productCategoryManager.findByProperty("categoryPath", newProductCategory.getCategoryPath());
					if(tempProductCateogryList==null||tempProductCateogryList.size()==0){
						productCategoryManager.save(newProductCategory);
					}
				}
			}
		}
	}

	public Map<SkuOption, SkuOptionValue> findSkuOptionAndValuesByProductSku(Integer productSkuId) {
		Map<SkuOption, SkuOptionValue> result = new LinkedHashMap<SkuOption, SkuOptionValue>();
		ProductSku productSku = productSkuManager.getById(productSkuId);
		Set<ProductSkuOptionValue> tempProductSkuOptionValues = productSku.getProductSkuOptionValues();
		// 获取产品产品类型关联的SkuOption,按ProductTypeSkuOption的sortOrder排序，
		//TODO 延迟加载失败，直接加载产品
		Product product=productManager.getById(productSku.getProductId());
		if(product.getProductKind().intValue()==CatalogConstants.PRODUCT_KIND_VARIATION.intValue()){
			List<SkuOption> ProductTypeSkuOptions = skuOptionManager.findSkuOptionsByProductType(product.getProductTypeId());
			//查找该Sku并且与该Sku所属的产品类型有关联的SkuOption选项及值，添加到result集合
			for (SkuOption skuOption : ProductTypeSkuOptions) {
				for (ProductSkuOptionValue productSkuOptionValue : tempProductSkuOptionValues) {
					if (productSkuOptionValue.getSkuOptionValue().getSkuOptionId().intValue() == skuOption.getId().intValue()) {
						result.put(productSkuOptionValue.getSkuOptionValue().getSkuOption(), productSkuOptionValue.getSkuOptionValue());
						// tempProductSkuOptionValues.remove(productSkuOptionValue);
						break;
					}
				}
			}
			//查找该Sku但与该Sku所属的产品类型没有关联的SkuOption选项及值，添加到result集合
			for (ProductSkuOptionValue productSkuOptionValue : tempProductSkuOptionValues) {
				SkuOption skuOption = productSkuOptionValue.getSkuOptionValue().getSkuOption();
				if (!result.containsKey(skuOption)) {
					skuOption.setRefProductType(false);
					result.put(skuOption, productSkuOptionValue.getSkuOptionValue());
				}
			}
		}
		return result;
	}


	public List<SkuOption> findActiveSkuOptionsOfProductTypeExcludeRefProductSku(Integer productTypeId, Integer productSkuId) {
		List<SkuOption> skuOptions = skuOptionManager.findActiveSkuOptionsByProductType(productTypeId);
		if (productSkuId != null) {
			ProductSku productSku = productSkuManager.getById(productSkuId);
			//获取产品关联的Sku选项
			Set<ProductSkuOptionValue> tempProductSkuOptionValues = productSku.getProductSkuOptionValues();
			for (ProductSkuOptionValue productSkuOptionValue : tempProductSkuOptionValues) {
				SkuOption skuOption = productSkuOptionValue.getSkuOptionValue().getSkuOption();
				for (int i = 0; i < skuOptions.size(); i++) {
					if (skuOption.getId() == skuOptions.get(i).getId()) {
						skuOptions.remove(i);
						break;
					}
				}
			}
		}
		return skuOptions;
	}


	public List<SkuOption> findSkuOptionsOfDefaultProductSkuExcludeRefProductSku(Integer defaultProductSkuId, Integer productSkuId) {
		List<SkuOption> result = new ArrayList<SkuOption>();
		ProductSku productSku = null;
		//获取指定Sku的选项
		Set<ProductSkuOptionValue> productSku_productSkuOptionValues = null;
		if (productSkuId != null) {
			productSku = productSkuManager.getById(productSkuId);
			productSku_productSkuOptionValues = productSku.getProductSkuOptionValues();
		}
		//获取默认Sku的选项
		ProductSku defaultProductSku = productSkuManager.getById(defaultProductSkuId);
		Set<ProductSkuOptionValue> defaultProductSku_productSkuOptionValues = defaultProductSku.getProductSkuOptionValues();
		
		//过滤掉与指定Sku有关联的Sku选项，将之与默认Sku关联的添加到result集合中去
		for (ProductSkuOptionValue defaultProductSku_productSkuOptionValue : defaultProductSku_productSkuOptionValues) {
			boolean flag = true;
			SkuOption skuOption = defaultProductSku_productSkuOptionValue.getSkuOptionValue().getSkuOption();
			if (productSku_productSkuOptionValues != null) {
				for (ProductSkuOptionValue productSkuOptionValue : productSku_productSkuOptionValues) {
					if (skuOption.getSkuOptionId().intValue() == productSkuOptionValue.getSkuOptionValue().getSkuOption().getSkuOptionId().intValue()) {
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				result.add(skuOption);
			}
		}
		return result;
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public void setSkuOptionManager(SkuOptionManager skuOptionManager) {
		this.skuOptionManager = skuOptionManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setProductCategoryManager(ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void saveProductSkuAction(ProductSku productSku, Set<Integer> skuOptionValueIds, String[] wholesalePrices) {
		// 更新保存Sku
		Product product = productManager.getById(productSku.getProductId());
		productSku.setProduct(product);
		autoSetProductSkuCode(productSku,skuOptionValueIds);
		//当产品还没有指定默认的Sku时，就直接将本Sku作为产品的默认sku
		if (product.getDefaultProductSkuId() == null) {
			product.setDefaultProductSku(productSku);
		}
		productSkuManager.save(productSku);
		//初始化库存信息
		inventoryService.doInitInventoryByCreateProduct(productSku);
		// 更新保存批发价
		wholesalePriceManager.saveWholesalePrices(productSku.getId(), wholesalePrices);

		// 更新选项属性
		productSkuManager.saveProductSkuOptions(productSku.getId(), skuOptionValueIds);
		
	}
	
	private void autoSetProductSkuCode(ProductSku productSku,Set<Integer> skuOptionValueIds) {
		if(productSku.getId()==null&&StringUtils.isBlank(productSku.getProductSkuCode())){
			List<SkuOptionValue> skuOptionValueList=new ArrayList<SkuOptionValue>();
			
			for (Integer skuOptionValueId : skuOptionValueIds) {
				SkuOptionValue skuOptionValue=skuOptionValueManager.getById(skuOptionValueId);
				skuOptionValueList.add(skuOptionValue);
			}
			Collections.sort(skuOptionValueList,new Comparator<SkuOptionValue>()
			{
				@Override
				public int compare(SkuOptionValue o1, SkuOptionValue o2) {
					return o1.getSkuOption().getId().compareTo(o2.getSkuOption().getId());
				}
			});
			String productCode=productSku.getProduct().getProductCode();
			StringBuffer skuCodeBuf=new StringBuffer(productCode);
			for (SkuOptionValue skuOptionValue : skuOptionValueList)
			{
				skuCodeBuf.append("-").append(skuOptionValue.getSkuOptionValue());
			}
			
			String code= skuCodeBuf.toString();
			ProductSku tempProductSku =productSkuManager.getProductSkuByProductSkuCode(code);
			skuCodeBuf.append("-");
			int suq=2;
			while (tempProductSku!=null)
			{
				code= skuCodeBuf.toString()+suq;
				tempProductSku = productSkuManager.getProductSkuByProductSkuCode(code);
				suq++;
			}
			productSku.setProductSkuCode(code);
		}
	}

	public Map<String, Object> saveProductAction(ProductDataModel productDataModel) {
		// 编辑产品时，同时保存产品信息
		Map<String, Object> saveProductMsg = new HashMap<String, Object>();
		Product product = productDataModel.getProduct();
		//保存产品描述信息
		ProductSku productSku = productDataModel.getDefaultProductSku();
		if (!productDataModel.isOnlySaveProductInfo()) {
			ProductDescription productDescription = product.getProductDescription();
			if (productDescription == null) {
				productDescription = new ProductDescription();
			}
			productDescription.setFullDescription(productDataModel.getFullDescription());
			productDescription.setShortDescription(productDataModel.getShortDescription());
			productDescription.setImageDescription(productDataModel.getImageDescription());
			productDescriptionManager.save(productDescription);
			product.setProductDescription(productDescription);
		}
		if (!productDataModel.isOnlySaveProductInfo()) {
			ProductHandDraw productHandDraw = product.getProductHandDraw();
			if (productHandDraw == null) {
				productHandDraw = new ProductHandDraw();
			}
			productHandDraw.setMediaUrl(productDataModel.getHandDrawUrl() == null ? "" : productDataModel.getHandDrawUrl());
			productHandDrawManager.save(productHandDraw);
			product.setProductHandDraw(productHandDraw);
		}
		// 保存更新产品主体信息
		saveProductAction(product, productDataModel.getMainCategoryIds(), productDataModel.getCategoryIds());

		if (!productDataModel.isOnlySaveProductInfo()) {
			// 保存、更新自定义属性
			attributeService.saveOrUpdateAttributeValue(productDataModel.getAttributeValues(), product);
			// 更新保存产品媒体
			String productMediaIds[] = productDataModel.getProductMediaIds();
			String mediaUrls[] = productDataModel.getMediaUrls();
			String mediaUrls_d[] = productDataModel.getMediaUrls_d();
			String productMediaTypes[] = productDataModel.getProductMediaTypes();
			String mediaDescription[] = productDataModel.getMediaDescription();
			String mediaProductSkus[] = productDataModel.getMediaProductSkus();
			String productMedia_deleteds[] = productDataModel.getProductMedia_deleteds();
			//newProductMediaIds主要是由于保存产品时是ajax直接保存，因此新增产品媒体时相应的Id数据信息应保留回在页面，以保证在当前页面再次保存数据时正确
			List<String> newProductMediaIds = productMediaManager.saveProductMedias(product.getProductId(), productMediaIds, productMediaTypes, mediaUrls, mediaUrls_d, mediaDescription, mediaProductSkus ,productMedia_deleteds);
			saveProductMsg.put("newProductMediaIds", newProductMediaIds);
			
			String productMediaUpIds[] = productDataModel.getProductMediaUpIds();
			String mediaUrlsUp[] = productDataModel.getMediaUrlsUp();
			String productMediaTypesUp[] = productDataModel.getProductMediaTypesUp();
			String mediaDescriptionUp[] = productDataModel.getMediaDescriptionUp();

			String productMedia_deletedsUp[] = productDataModel.getProductMedia_deletedsUp();
			//newProductMediaIds主要是由于保存产品时是ajax直接保存，因此新增产品媒体时相应的Id数据信息应保留回在页面，以保证在当前页面再次保存数据时正确
			List<String> newProductMediaUpIds = productMediaUpManager.saveProductMedias(product.getProductId(), productMediaUpIds, productMediaTypesUp, mediaUrlsUp, mediaUrls_d, mediaDescriptionUp, productMedia_deletedsUp);
			saveProductMsg.put("newProductMediaUpIds", newProductMediaUpIds);

			
			//保存更新产品附件信息
			if(product.getProductKind().intValue()==CatalogConstants.PRODUCT_KIND_COMMON.intValue()){
				saveProductAccessorys(product,productDataModel.getAccessoryIds());
			}
		}
		if (productSku != null) {
			//保存普通产品Sku信息
			String wholesalePrices[] = productDataModel.getWholesalePrices();
			saveSimpleProductSku(product, productSku, wholesalePrices);
			// 当为产品包时，保存被包含的items,草稿状态才可以更新保存
			//TODO己激活过的产品不能更新产品包Items
			if (product.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_PACKAGE && product.getOrigStatus() != null && product.getOrigStatus().intValue() == Constants.STATUS_NOT_PUBLISHED.intValue()) {
				Integer[] itemSkuIds = productDataModel.getProductPackageItemIds();
				Integer[] productPackageQuantitys = productDataModel.getProductPackageQuantity();
				productPackageItemManager.saveProductPackageItemAction(productSku.getId(), itemSkuIds, productPackageQuantitys);
			}
		}
		
		//保存供应商信息
		saveProductSuppliersAction(product,productDataModel.getDefaultSupplierId(),productDataModel.getSupplierIds());
		return saveProductMsg;
	}
	
	
	public void saveProductSuppliersAction(Product product,Integer defaultSupplierId,Integer supplierIds[]){
		//去除重复供应商
		Set<Integer> tempSuppliers=new HashSet<Integer>();
		CollectionUtils.addAll(tempSuppliers, supplierIds);
		supplierIds=tempSuppliers.toArray(new Integer[]{});
		
		Integer defaultSupplierProductId=null;
		
		//没有指定供应商的为其指定默认的供应商
		if(supplierIds.length==0){
			defaultSupplierId=SupplierConstants.DEFAULT_SUPPLIER_ID;
			supplierIds=(Integer[])ArrayUtils.add(supplierIds,defaultSupplierId);
		}
		
		//supplierManager
		List<SupplierProduct> supplierProductList=supplierProductManager.findSupplierProductByProductId(product.getProductId());
		
		Set<SupplierProduct> reserved_supplierProducts = new HashSet<SupplierProduct>();
		// 清除没有关联的供应商
		if (supplierProductList != null) {
			for (SupplierProduct supplierProduct : supplierProductList) {
				boolean flag =ArrayUtils.contains(supplierIds, supplierProduct.getSupplierId());
				if (flag) {
					reserved_supplierProducts.add(supplierProduct);
				} else {
					if(supplierProduct.getStatus().intValue()==Constants.STATUS_NOT_PUBLISHED){
						supplierProductManager.deleteById(supplierProduct.getSupplierProductId());
					}
				}
			}
		}
		// 添加供应商与产品关联
		for (Integer supplierId : supplierIds) {
			boolean flag = true;
			SupplierProduct tempSupplierProduct = null;
			for (SupplierProduct supplierProduct : reserved_supplierProducts) {
				if (supplierProduct.getSupplierId().intValue()==supplierId) {
					flag = false;
					tempSupplierProduct = supplierProduct;
					break;
				}
			}
			if (flag) {
				//新增关联供应商与产品
				Supplier supplier=supplierManager.getById(supplierId);
				SupplierProduct supplierProduct=new SupplierProduct();
				supplierProduct.setSupplier(supplier);
				supplierProduct.setProduct(product);
				supplierProduct.setProductCode("N/A");
				supplierProduct.setProductName("N/A");
				supplierProduct.setWholesalePrice("N/A");
				supplierProduct.setStatus(Constants.STATUS_NOT_PUBLISHED);
				supplierProduct.setProductDesc("");
				supplierProduct.setUpdateLogs("");
				supplierProduct.setMediaUrl("");
				supplierProduct.setTbSellCatProps("");
				supplierProduct.setMediaUrl("");
				
				supplierProductManager.save(supplierProduct);
				if(supplierId.intValue()==defaultSupplierId.intValue()){
					defaultSupplierProductId=supplierProduct.getSupplierProductId();
				}
			}else {
				if(tempSupplierProduct.getSupplierId().intValue()==defaultSupplierId.intValue()){
					defaultSupplierProductId=tempSupplierProduct.getSupplierProductId();
				}
			}
		}
		product.setSupplierId(defaultSupplierId);
		product.setDefaultSupplierProductId(defaultSupplierProductId);
	}
	
	public void saveProductAccessorys(Product product,Integer[]accessoryIds){
		Set<Accessory> accessorys=product.getAccessories();
		
		if(accessorys==null)
			accessorys=new HashSet<Accessory>();
		if(accessoryIds==null){
			accessoryIds=new Integer[]{};
		} 
		List<Accessory> tempDeleteAccessorys=new ArrayList<Accessory>();
		for (Accessory accessory : accessorys) {
			if(ArrayUtils.contains(accessoryIds, accessory.getAccessoryId())){
				ArrayUtils.removeElement(accessoryIds, accessory.getAccessoryId());
			}else{
				tempDeleteAccessorys.add(accessory);
			}
		}
		accessorys.removeAll(tempDeleteAccessorys);
		for (Integer accessoryId : accessoryIds) {
			accessorys.add(accessoryManager.getById(accessoryId));
		}
		
	}

	public void saveSimpleProductSku(Product product, ProductSku productSku, String wholesalePrices[]) {
		boolean isNew = productSku.getId() == null;
		// 保存sku信息
		productSku.setProduct(product);
		if (product.getStatus().intValue() == Constants.STATUS_ACTIVE) {
			productSku.setStatus(Constants.STATUS_ACTIVE);
		} else {
			productSku.setStatus(Constants.STATUS_INACTIVE);
		}
		productSkuManager.save(productSku);
		//初始化库存信息
		inventoryService.doInitInventoryByCreateProduct(productSku);
		product.setDefaultProductSku(productSku);
		// 保存更新批发价wholesalePrice_minQuantity
		wholesalePriceManager.saveWholesalePrices(productSku.getId(), wholesalePrices);
	}

	public void setProductDescriptionManager(ProductDescriptionManager productDescriptionManager) {
		this.productDescriptionManager = productDescriptionManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setProductMediaManager(ProductMediaManager productMediaManager) {
		this.productMediaManager = productMediaManager;
	}

	public void setProductPackageItemManager(ProductPackageItemManager productPackageItemManager) {
		this.productPackageItemManager = productPackageItemManager;
	}

	public Product doCopyProduct(Product origProduct) throws Exception {
		ConvertUtilsBean cub = new ConvertUtilsBean();
		cub.register(new BigDecimalConverter(null), BigDecimal.class);
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean(cub, new PropertyUtilsBean());
		//复制原产品基本信息
		Product destProduct = (Product) beanUtilsBean.cloneBean(origProduct);
		destProduct.setProductId(null);
		destProduct.setVersion(null);
		destProduct.setProductDescription(null);
		destProduct.setProductReviews(null);
		destProduct.setProductSkus(null);
		destProduct.setProductAttrValues(null);
		destProduct.setProductCategorys(null);
		destProduct.setProductMedias(null);
		destProduct.setProductReviews(null);
		destProduct.setProductStats(null);
		destProduct.setStatus(Constants.STATUS_NOT_PUBLISHED);
		//TODO设置code,name
		String destProductCode = generCopyProductCode(origProduct.getProductCode(), origProduct.getProductCode());
		destProduct.setProductCode(destProductCode);
		String productName = origProduct.getProductName();
		productName = "copy_" + productName;
		if (productName.length() > 128)
			productName = productName.substring(0, 127);
		destProduct.setProductName(productName);
		productManager.save(destProduct);
		//复制描述信息
		ProductDescription origProductDescription = origProduct.getProductDescription();
		ProductDescription destProductDescription = new ProductDescription();
		destProductDescription.setFullDescription(origProductDescription.getFullDescription());
		destProductDescription.setShortDescription(origProductDescription.getShortDescription());
		productDescriptionManager.save(destProductDescription);
		destProduct.setProductDescription(destProductDescription);
		//复制产品目录
		Set<ProductCategory> productCategorys = origProduct.getProductCategorys();
		for (ProductCategory origProductCategory : productCategorys) {
			ProductCategory destProductCategory = (ProductCategory) beanUtilsBean.cloneBean(origProductCategory);
			destProductCategory.setProductCategoryId(null);
			destProductCategory.setVersion(null);
			destProductCategory.setProduct(destProduct);
			destProductCategory.setCategoryPath(origProductCategory.getCategory().getCategoryPath() + origProductCategory.getCategory().getCategoryId() + "." + destProduct.getProductId());
			productCategoryManager.save(destProductCategory);
		}
		//复制产品媒体信息
		Set<ProductMedia> origProductMedias = origProduct.getProductMedias();
		for (ProductMedia origProductMedia : origProductMedias) {
			ProductMedia destProductMedia = (ProductMedia) beanUtilsBean.cloneBean(origProductMedia);
			destProductMedia.setProductMediaId(null);
			destProductMedia.setVersion(null);
			destProductMedia.setProduct(destProduct);
			productMediaManager.save(destProductMedia);
		}
		//复制产品自定义属性信息
		Set<ProductAttrValue> origProductAttrValues = origProduct.getProductAttrValues();
		for (ProductAttrValue origProductAttrValue : origProductAttrValues) {
			ProductAttrValue destProductAttrValue = (ProductAttrValue) beanUtilsBean.cloneBean(origProductAttrValue);
			destProductAttrValue.setProductAttrValueId(null);
			destProductAttrValue.setVersion(null);
			destProductAttrValue.setProduct(destProduct);
			attributeValueManager.saveOrUpdate(destProductAttrValue);
		}
		//负责产品附件信息
		if(origProduct.getProductKind().intValue()==CatalogConstants.PRODUCT_KIND_COMMON.intValue()){
			Set<Accessory> origProduct_accessories=origProduct.getAccessories();
			Set<Accessory> destProduct_accessories=new HashSet<Accessory>();
			for (Accessory accessory : origProduct_accessories) {
				destProduct_accessories.add(accessory);
			}
			destProduct.setAccessories(destProduct_accessories);
		}
		//复制Sku
		Set<ProductSku> origProductSkus = origProduct.getProductSkus();
		for (ProductSku origProductSku : origProductSkus) {
			ProductSku destProductSku = new ProductSku();
			beanUtilsBean.copyProperties(destProductSku, origProductSku);
			destProductSku.setProductId(null);
			destProductSku.setVersion(null);
			destProductSku.setWholesalePrices(null);
			destProductSku.setInventorys(null);
			destProductSku.setProductPackageItems(null);
			destProductSku.setProductSkuOptionValues(null);

			destProductSku.setProduct(destProduct);
			//TODO设置code
			String destProductSkuCode = generCopyProductSkuCode(origProductSku.getProductSkuCode(), origProductSku.getProductSkuCode());
			destProductSku.setProductSkuCode(destProductSkuCode);
			productSkuManager.save(destProductSku);
			//指定默认Sku
			if (origProduct.getDefaultProductSkuId().intValue() == origProductSku.getProductSkuId().intValue()) {
				destProduct.setDefaultProductSku(destProductSku);
			}
			//复制批发价 
			Set<WholesalePrice> origWholesalePrices = origProductSku.getWholesalePrices();
			for (WholesalePrice origWholesalePrice : origWholesalePrices) {
				WholesalePrice destWholesalePrice = (WholesalePrice) beanUtilsBean.cloneBean(origWholesalePrice);
				destWholesalePrice.setWholesalePriceId(null);
				destWholesalePrice.setVersion(null);
				destWholesalePrice.setProductSku(destProductSku);
				wholesalePriceManager.save(destWholesalePrice);
			}
			//复制产品包Item
			if (destProduct.getProductKind().intValue() == CatalogConstants.PRODUCT_KIND_PACKAGE.intValue()) {
				Set<ProductPackageItem> origProductPackageItems = origProductSku.getProductPackageItems();
				for (ProductPackageItem origProductPackageItem : origProductPackageItems) {
					ProductPackageItem destProductPackageItem = (ProductPackageItem) beanUtilsBean.cloneBean(origProductPackageItem);
					destProductPackageItem.setProductPackageItemId(null);
					destProductPackageItem.setVersion(null);
					destProductPackageItem.setProductSku(destProductSku);
					productPackageItemManager.save(destProductPackageItem);
				}
			}
			//复制Sku选项
			Set<ProductSkuOptionValue> origProductSkuOptionValues = origProductSku.getProductSkuOptionValues();
			for (ProductSkuOptionValue origProductSkuOptionValue : origProductSkuOptionValues) {
				ProductSkuOptionValue destProductSkuOptionValue = (ProductSkuOptionValue) beanUtilsBean.cloneBean(origProductSkuOptionValue);
				destProductSkuOptionValue.setProductSkuOptionValueId(null);
				destProductSkuOptionValue.setVersion(null);
				destProductSkuOptionValue.setProductSku(destProductSku);
				productSkuOptionValueManager.save(destProductSkuOptionValue);
			}
			//初始化库存数据
			inventoryService.doInitInventoryByCreateProduct(destProductSku);
		}
		return destProduct;
	}

	/**
	 * TODO 有待调整
	 * @param code
	 * @param origCode
	 * @return
	 */
	private String generCopyProductCode(String code, String origCode) {
		code = "copy_" + code;
		if (code.length() > 32) {
			code = "c" + new Date().getTime() + "_" + origCode;
			if (code.length() > 32) {
				code = code.substring(0, 31);
			}
		}
		Product tempProduct = productManager.getProductByProductCode(code);
		if (tempProduct == null) {
			return code;
		}
		return generCopyProductCode(code, origCode);
	}

	/**
	 * TODO 有待调整
	 * @param code
	 * @param origCode
	 * @return
	 */
	private String generCopyProductSkuCode(String code, String origCode) {
		code = "copy_" + code;
		if (code.length() > 32) {
			code = "c" + new Date().getTime() + "_" + origCode;
			if (code.length() > 32) {
				code = code.substring(0, 31);
			}
		}
		ProductSku tempProductSku = productSkuManager.getProductSkuByProductSkuCode(code);
		if (tempProductSku == null) {
			return code;
		}
		return generCopyProductSkuCode(code, origCode);
	}

	/**
	 * 删除产品，
	 * 当产品为草稿状态时为物理删除
	 * 当产品为非草稿状态时为逻辑删除，当删除产品的Sku存在相应库存的就进入待删除状态，否则进入已删除状态
	 * 
	 */
	public void delete(Integer productId) {
		//草稿状态时可物理删除产品
		Product product = productManager.getById(productId);
		if (product.getStatus().intValue() == Constants.STATUS_NOT_PUBLISHED.intValue()) {
			ProductDescription productDescription = product.getProductDescription();
			if (productDescription != null)
				productDescriptionManager.deleteById(productDescription.getId());
			supplierProductManager.deleteProductToSetNull(productId);
			productManager.delete(product);
		} else if(product.getStatus().intValue() != Constants.STATUS_AWAITING_DELETE.intValue()&&product.getStatus().intValue() != Constants.STATUS_DELETED.intValue()) {
			//TODO 不管是进入已删除还是待删除状态，产品的相关推荐一律删除(目前待删除只能转为已删除状态)
			recommendedService.removeRecommendedRelate(productId);
			//不存在库存时现有库存时可直接删除
			boolean isHasStock = inventoryService.isHasStockOrOrderedPreOrBackOrderByProduct(product);
			if (isHasStock) {
				product.setStatus(Constants.STATUS_AWAITING_DELETE);
			} else {
				removeProduct(product);
				return;
			}
			supplierProductManager.deleteProductToSetNull(productId);
			productManager.save(product);
		}
	}

	/**
	 * 逻辑删除产品
	 * 目前逻辑删除产品时，只是修改Code,及状态而已，其他的关联数据不处理
	 * @param product
	 */
	private void removeProduct(Product product) {
		Set<ProductSku> productSkus = product.getProductSkus();
		for (ProductSku productSku : productSkus) {
			String skuCode = productSku.getProductSkuCode();
			skuCode = UUID.randomUUID().getMostSignificantBits() + skuCode;
			skuCode = skuCode.length() > 32 ? skuCode.substring(0, 31) : skuCode;
			productSku.setProductSkuCode(skuCode);
		}
		String productCode = product.getProductCode();
		productCode = UUID.randomUUID().getMostSignificantBits() + productCode;
		productCode = productCode.length() > 32 ? productCode.substring(0, 31) : productCode;
		product.setProductCode(productCode);
		product.setStatus(Constants.STATUS_DELETED);
		productManager.save(product);
	}

	public void doClearAwaitingDeleteProductJob() {
		List<Product> productList = productManager.findByProperty("status", Constants.STATUS_AWAITING_DELETE);
		for (Product product : productList) {
			boolean isHasStock = inventoryService.isHasStockOrOrderedPreOrBackOrderByProduct(product);
			if (!isHasStock) {
				removeProduct(product);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ProductAttGroup> findAttGroupAndProductAttrValueByProduct(Product product) {
		//获取产品自定义值属性
		Set<ProductAttrValue> productAttrValues = new HashSet<ProductAttrValue>();
		productAttrValues.addAll(product.getProductAttrValues());
		//获取与产品类型关联的自定义属性组
		List<ProductAttGroup> productAttGroups = productAttGroupManager.findProductAttGroupsByProductType(product.getProductTypeId());
		//将产品自定义属性附加在自定义属性组的productAttrValues（辅助前台显示自定义属性）
		for (ProductAttGroup productAttGroup : productAttGroups) {
			Set<ProductAttGroupItem> productAttGroupItems = productAttGroup.getProductAttGroupItems();
			for (ProductAttGroupItem productAttGroupItem : productAttGroupItems) {
				ProductAttrValue tempProductAttrValue = null;
				for (ProductAttrValue productAttrValue : productAttrValues) {
					if (productAttGroupItem.getId().intValue() == productAttrValue.getProductAttGroupItemId().intValue()) {
						tempProductAttrValue = productAttrValue;
						break;
					}
				}
				if (tempProductAttrValue != null) {
					List<ProductAttrValue> productAttrValueList = productAttGroup.getProductAttrValues();
					if (productAttrValueList == null) {
						productAttrValueList = new ArrayList<ProductAttrValue>();
						productAttGroup.setProductAttrValues(productAttrValueList);
					}
					productAttrValueList.add(tempProductAttrValue);
					productAttrValues.remove(tempProductAttrValue);
				}
			}
		}
		return productAttGroups;
	}

	public void saveProductReviewAction(ProductReview productReview, String[] attrRates) {
		//获取系统参数，产品评论是否需审批
		boolean isProductReviewConfirm = ConfigUtil.getInstance().getIsProductReviewConfirmEnabled();
		if (isProductReviewConfirm) {
			productReview.setStatus(Constants.STATUS_INACTIVE);
		} else {
			productReview.setStatus(Constants.STATUS_ACTIVE);
		}
		productReview.setUnusefulCount(0);
		productReview.setUsefulCount(0);
		productReviewManager.save(productReview);
		//不需要审批的，评论得分马上加入产品中
		if(!isProductReviewConfirm){
			productReviewManager.updateProductReviewStat(productReview.getStoreId(),productReview.getProductId());
		}
		//增加保存评论项得分
		for (String attrRate : attrRates) {
			String rateValues[] = attrRate.split("_");
			ProductRateItem productRateItem = productRateItemManager.getById(Integer.valueOf(rateValues[0]));
			if (productRateItem != null) {
				ProductRateValue productRateValue = new ProductRateValue();
				productRateValue.setProductRateItem(productRateItem);
				productRateValue.setProductReview(productReview);
				productRateValue.setRateValue(Integer.valueOf(rateValues[1]));
				productRateValueManager.save(productRateValue);
			}
		}
	}

	public void setProductAttGroupManager(ProductAttGroupManager productAttGroupManager) {
		this.productAttGroupManager = productAttGroupManager;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public void setProductReviewManager(ProductReviewManager productReviewManager) {
		this.productReviewManager = productReviewManager;
	}

	public void setProductRateItemManager(ProductRateItemManager productRateItemManager) {
		this.productRateItemManager = productRateItemManager;
	}

	public void setProductRateValueManager(ProductRateValueManager productRateValueManager) {
		this.productRateValueManager = productRateValueManager;
	}

	public void setProductStatManager(ProductStatManager productStatManager) {
		this.productStatManager = productStatManager;
	}

	public Map<SkuOption, List<SkuOptionValue>> findSkuOptionsByProduct(Integer productId) {
		//TODO 结合实际使用优化
		LinkedHashMap<SkuOption, List<SkuOptionValue>> productSkuOptionAndValue = new LinkedHashMap<SkuOption, List<SkuOptionValue>>();
		//获取产品相关的SkuOption以及SkuOptionValue
		Product product = productManager.getById(productId);
		
		List<ProductSku> ProductSkuList = productSkuManager.findActiveProductSkusByProductId(productId);
		//temp_productSkuOptionAndValue 保存该产品的变种选项及值，以Map的形式
		Map<SkuOption, Set<SkuOptionValue>> temp_productSkuOptionAndValue = new HashMap<SkuOption, Set<SkuOptionValue>>();
		for (ProductSku productSku : ProductSkuList) {
			Set<ProductSkuOptionValue> productSkuOptionValues = productSku.getProductSkuOptionValues();
			for (ProductSkuOptionValue productSkuOptionValue : productSkuOptionValues) {
				SkuOptionValue skuOptionValue = productSkuOptionValue.getSkuOptionValue();
				SkuOption skuOption = skuOptionValue.getSkuOption();
				Set<SkuOptionValue> ProductSkuOptionValues = temp_productSkuOptionAndValue.get(skuOption);
				if (ProductSkuOptionValues == null) {
					ProductSkuOptionValues = new HashSet<SkuOptionValue>();
					temp_productSkuOptionAndValue.put(skuOption, ProductSkuOptionValues);
				}
				ProductSkuOptionValues.add(skuOptionValue);
				skuOptionValue.setSku(productSku);
			}
		}

		//处理排序
		
		List<SkuOption> ProductTypeSkuOptions = skuOptionManager.findSkuOptionsByProductType(product.getProductTypeId());

		Set<SkuOption> tempProductSkuOptions = temp_productSkuOptionAndValue.keySet();

		//按选项按与产品类型关联的排序
		for (SkuOption skuOption : ProductTypeSkuOptions) {
			for (SkuOption tempProductSkuOption : tempProductSkuOptions) {
				if (skuOption.getId().intValue() == tempProductSkuOption.getId().intValue()) {
					sortSkuOptionAndValue(productSkuOptionAndValue, temp_productSkuOptionAndValue, tempProductSkuOption);
					break;
				}
			}
		}
		//将没有与产品类型关联的Sku选项也添加到结果集合
		for (SkuOption tempProductSkuOption : tempProductSkuOptions) {
			if (!productSkuOptionAndValue.containsKey(tempProductSkuOption)) {
				sortSkuOptionAndValue(productSkuOptionAndValue, temp_productSkuOptionAndValue, tempProductSkuOption);
			}
		}
		return productSkuOptionAndValue;
	}

	@SuppressWarnings("unchecked")
	private void sortSkuOptionAndValue(Map<SkuOption, List<SkuOptionValue>> productSkuOptionAndValue, Map<SkuOption, Set<SkuOptionValue>> temp_productSkuOptionAndValue, SkuOption tempProductSkuOption) {

		List<SkuOptionValue> productSkuOptionValues = productSkuOptionAndValue.get(tempProductSkuOption);

		if (productSkuOptionAndValue.get(tempProductSkuOption) == null) {
			productSkuOptionValues = new ArrayList<SkuOptionValue>();
			productSkuOptionAndValue.put(tempProductSkuOption, productSkuOptionValues);
		}
		Set<SkuOptionValue> skuOptionValues = tempProductSkuOption.getSkuOptionValues();
		Set<SkuOptionValue> temp_skuOptionValues = temp_productSkuOptionAndValue.get(tempProductSkuOption);

		for (SkuOptionValue skuOptionValue : skuOptionValues) {
			for (SkuOptionValue temp_skuOptionValue : temp_skuOptionValues) {
				if (skuOptionValue.getId().intValue() == temp_skuOptionValue.getId().intValue()) {
					productSkuOptionValues.add(temp_skuOptionValue);
					break;
				}
			}
		}
		for (SkuOptionValue temp_skuOptionValue : temp_skuOptionValues) {
			SkuOptionValue skuOptionValue = null;
			for (SkuOptionValue productSkuOptionValue : productSkuOptionValues) {
				if (productSkuOptionValue.getId().intValue() == temp_skuOptionValue.getId().intValue()) {
					skuOptionValue = productSkuOptionValue;
					break;
				}
			}
			if (skuOptionValue == null) {
				productSkuOptionValues.add(temp_skuOptionValue);
			}
		}
	}

	
	public ProductCompareModel getProductCompareModelForFront(Integer productIds[]) {
		List<Product> productList = new ArrayList<Product>();
		//获取需比较的产品
		for (Integer productId : productIds) {
			Product product = productManager.getActiveProduct(productId);
			productList.add(product);
		}
		return getProductCompareModel(productList);
	}

	public ProductCompareModel getProductCompareModel(Integer productIds[]) {
		List<Product> productList = new ArrayList<Product>();
		//获取需比较的产品
		for (Integer productId : productIds) {
			Product product = productManager.getById(productId);
			productList.add(product);
		}
		return getProductCompareModel(productList);
	}

	private ProductCompareModel getProductCompareModel(List<Product> productList) {
		ProductCompareModel productCompareModel = new ProductCompareModel();
		productCompareModel.setProductList(productList);
		//tempAllAttribte记录保存比较产品中所有的普通自定义属性
		Set<Attribute> tempAllAttribte = new HashSet<Attribute>();
		//tempAllSkuOption记录保存比较产品中所有的变种自定义属性
		Set<SkuOption> tempAllSkuOption = new HashSet<SkuOption>();

		Map<Product, Map<SkuOption, List<SkuOptionValue>>> tempProductsOptionValue = new HashMap<Product, Map<SkuOption, List<SkuOptionValue>>>();
		//检查相应的非必填字段是否可显示
		for (Product product : productList) {
			//检查是否可以显示图片
			if ((!productCompareModel.isShowImage()) && StringUtils.isNotEmpty(product.getDefaultProductSku().getImage())) {
				productCompareModel.setShowImage(true);
			}
			//检查是否可以显示品牌
			if ((!productCompareModel.isShowBrand()) && product.getBrand() != null) {
				productCompareModel.setShowBrand(true);
			}
			//检查是否可以显示简单描述
			if ((!productCompareModel.isShowShortDescription()) && StringUtils.isNotEmpty(product.getProductDescription().getShortDescription())) {
				productCompareModel.setShowShortDescription(true);
			}
			//检查是否可以显示重量
			if ((!productCompareModel.isShowWeight()) && product.getDefaultProductSku().getWeight() != null) {
				productCompareModel.setShowWeight(true);
			}
			//检查是否可以显示批发价
			if ((!productCompareModel.isShowWholesalePrice()) && product.getDefaultProductSku().getWholesalePrices() != null && product.getDefaultProductSku().getWholesalePrices().size() > 0) {
				productCompareModel.setShowWholesalePrice(true);
			}
			//检查是否可以显示最少订购量
			if ((!productCompareModel.isShowMinOrderQuantity()) && product.getMinOrderQuantity() != null && product.getMinOrderQuantity().intValue() != 0) {
				productCompareModel.setShowMinOrderQuantity(true);
			}
			//检查是否可以显示供应商
			if ((!productCompareModel.isShowProviderName()) && product.getSupplier() != null) {
				productCompareModel.setShowProviderName(true);
			}
			Set<ProductAttrValue> productAttrValues = product.getProductAttrValues();
			for (ProductAttrValue productAttrValue : productAttrValues) {
				tempAllAttribte.add(productAttrValue.getAttribute());
			}
			Map<SkuOption, List<SkuOptionValue>> productSkuOptionAndValue = findSkuOptionsByProduct(product.getProductId());
			tempAllSkuOption.addAll(productSkuOptionAndValue.keySet());
			tempProductsOptionValue.put(product, productSkuOptionAndValue);
		}
		
		//比较产品的普通自定义属性
		productCompareModel.setProductAttribute(new HashMap<Attribute, List<ProductAttrValue>>());
		for (Attribute attribute : tempAllAttribte) {
			List<ProductAttrValue> productAttrValueList = productCompareModel.getProductAttribute().get(attribute);
			if (productAttrValueList == null) {
				productAttrValueList = new ArrayList<ProductAttrValue>();
				productCompareModel.getProductAttribute().put(attribute, productAttrValueList);
			}
			for (Product product : productList) {
				Set<ProductAttrValue> productAttrValues = product.getProductAttrValues();
				ProductAttrValue prodcutAttributeValue = null;
				for (ProductAttrValue productAttrValue : productAttrValues) {
					if (productAttrValue.getAttribute().getId().intValue() == attribute.getId().intValue()) {
						if (productAttrValue.getAttributeValue() != null && StringUtils.isNotEmpty(productAttrValue.getAttributeValue().toString())) {
							prodcutAttributeValue = productAttrValue;
						}
						break;
					}
				}
				productAttrValueList.add(prodcutAttributeValue);
			}
		}
		//去除null值..(普通自定义属性)
		for (Attribute attribute : tempAllAttribte) {
			List<ProductAttrValue> productAttrValues = productCompareModel.getProductAttribute().get(attribute);
			boolean isAllEmpty = true;
			for (ProductAttrValue productAttrValue : productAttrValues) {
				if (productAttrValue != null) {
					isAllEmpty = false;
					break;
				}
			}
			if (isAllEmpty) {
				productCompareModel.getProductAttribute().remove(attribute);
			}
		}
		//获取可比较的Sku选项
		productCompareModel.setProductSkuOptionValue(new HashMap<SkuOption, List<List<SkuOptionValue>>>());
		for (SkuOption skuOption : tempAllSkuOption) {
			List<List<SkuOptionValue>> productsOptionValueList = productCompareModel.getProductSkuOptionValue().get(skuOption);
			if (productsOptionValueList == null) {
				productsOptionValueList = new ArrayList<List<SkuOptionValue>>();
				productCompareModel.getProductSkuOptionValue().put(skuOption, productsOptionValueList);
			}
			for (Product product : productList) {
				Map<SkuOption, List<SkuOptionValue>> productSkuOptionAndValue = tempProductsOptionValue.get(product);
				List<SkuOptionValue> skuOptionValueList = productSkuOptionAndValue.get(skuOption);
				productsOptionValueList.add(skuOptionValueList);
			}
		}
		return productCompareModel;
	}

	public void doBulkProduct(List<BulkProduct>bulkProductList) throws BulkUpdateException{
		List<Integer>productIdList=new ArrayList<Integer>();
		for (BulkProduct bulkProduct : bulkProductList) {
			Product product=bulkProduct.getProduct();
			//更新保存产品普通属性
			Map<String, BulkField> prodCommAttrs=bulkProduct.getProdCommAttrs();
			bulkUpdateProdCommAttrs(product, prodCommAttrs);
			//更新保存产品自定义属性
			bulkUpdateProdAttrs(product, bulkProduct.getProdAttrs());
			//更新保存sku属性
			List<BulkProductSku>bulkProductSkuList=bulkProduct.getProductSkus();
			bulkUpdateSkuCommAttrs(bulkProductSkuList);
			productIdList.add(product.getProductId());
		}
		//更新索引
		CatalogHelper.getInstance().indexNotifyUpdateEvent(productIdList.toArray(new Integer[]{}));
		
	}
	private void bulkUpdateSkuCommAttrs(List<BulkProductSku>bulkProductSkuList)throws BulkUpdateException{
		BeanUtilsBean beanUtils=BulkHelper.getInstance().getBeanUtilsBean();
		for (BulkProductSku bulkProductSku : bulkProductSkuList) {
			ProductSku productSku=bulkProductSku.getProductSku();
			Map<String, BulkField> skuCommAttrs=bulkProductSku.getSkuCommAttrs();
			Set<String> skuKeys=skuCommAttrs.keySet();
			for (String skuKey : skuKeys) {
				BulkField bulkField=skuCommAttrs.get(skuKey); 
				if(bulkField.getName().equals("productSkuCode")){
					ProductSku tempProductSku=productSkuManager.getProductSkuByProductSkuCode(bulkField.getValue());
					if(tempProductSku!=null&&tempProductSku.getId().intValue()!=productSku.getId().intValue()){
						throw new BulkUpdateException(bulkField,"Sku编码重复");
					}
				}
				try {
					//TODO 产品描述，可能为null,状态是否可转换等
					beanUtils.setProperty(productSku, skuKey, bulkField.getValue());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new BulkUpdateException(bulkField,"保存出错，请刷新本页面后再试！");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new BulkUpdateException(bulkField,"保存出错，请刷新本页面后再试！");
				}catch (ConversionException e) {
					e.printStackTrace();
					throw new BulkUpdateException(bulkField,"保存出错，请刷新本页面后再试！");
				}
				//检查是否可设置为激活
				if(bulkField.getName().equals("status")&&productSku.getStatus().intValue()!=productSku.getOrigStatus().intValue()){
					ProductSku defaultProductSku=productSku.getProduct().getDefaultProductSku();
					if (defaultProductSku.getId().intValue()!=productSku.getId().intValue()&&productSku.getStatus().intValue() == Constants.STATUS_ACTIVE.intValue()) {
						boolean isSame = productSkuManager.compare2SkuOptionIsSame(defaultProductSku, productSku);
						if(!isSame){
							throw new BulkUpdateException(bulkField,"该Sku选项与默认Sku选项不一致，不能设置为激活，请修改！");
						}
					}
				}
			}
		}
	}
	private void bulkUpdateProdAttrs(Product product,Map<String, BulkField> prodAttrs)throws BulkUpdateException{
		if(prodAttrs.size()==0)return;
		Set<ProductAttrValue> productAttrValueSet=product.getProductAttrValues();
		for (ProductAttrValue productAttrValue : productAttrValueSet) {
			BulkField bulkField=prodAttrs.get(productAttrValue.getAttribute().getAttributeCode());
			if(bulkField!=null){
				try {
					productAttrValue.setAttributeValue(bulkField.getValue());
				} catch (Exception e) {
					throw new BulkUpdateException(bulkField,"该数据保存失败，请检查！");
				}
			}
		}
	}
	
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	private void bulkUpdateProdCommAttrs(Product product,Map<String, BulkField> prodCommAttrs)throws BulkUpdateException{
		BeanUtilsBean beanUtils=BulkHelper.getInstance().getBeanUtilsBean();
		Set<String> keys=prodCommAttrs.keySet();
		for (String key : keys) {
			BulkField bulkField=prodCommAttrs.get(key);
			if(bulkField.getName().equals("productCode")){
				Product tempProduct=productManager.getProductByProductCode(bulkField.getValue());
				if(tempProduct!=null&&tempProduct.getId().intValue()!=product.getId().intValue()){
					throw new BulkUpdateException(bulkField,"产品编码重复");
				}
			}
			if(bulkField.getName().equals("publishTime")){
				if(StringUtils.isBlank(bulkField.getValue())){
					return ;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					df.parse(bulkField.getValue());
				} catch (ParseException e) {
					df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						df.parse(bulkField.getValue());
					} catch (ParseException e1) {
						e1.printStackTrace();
						throw new BulkUpdateException(bulkField,"发布时间格式错误");
					}
				}
			}
			try {
				//TODO 产品描述，可能为null,状态是否可转换等
				beanUtils.setProperty(product, key, bulkField.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new BulkUpdateException(bulkField,"保存出错，请刷新本页面后再试！");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new BulkUpdateException(bulkField,"保存出错，请刷新本页面后再试！");
			}
			if(bulkField.getName().equals("status")){
				if(product.getStatus().intValue()!=product.getOrigStatus().intValue()){
					if(product.getOrigStatus().intValue()!=Product.STATUS_ACTIVE&&product.getStatus().intValue()==Product.STATUS_NOTACTIVE){
						throw new BulkUpdateException(bulkField,"产品不能设置为非激活");
					}else if(product.getOrigStatus().intValue()!=Product.STATUS_NOTACTIVE&&product.getStatus().intValue()==Product.STATUS_ACTIVE){
						throw new BulkUpdateException(bulkField,"产品不能设置为激活");
					}
				}
				if(product.getProductKind().intValue()!=CatalogConstants.PRODUCT_KIND_VARIATION.intValue()&&product.getDefaultProductSku()!=null){
					if (product.getStatus().intValue() == Constants.STATUS_ACTIVE) {
						product.getDefaultProductSku().setStatus(Constants.STATUS_ACTIVE);
					} else {
						product.getDefaultProductSku().setStatus(Constants.STATUS_INACTIVE);
					}
				}
			}
		}
	}
	
	public Product createProduct4SupplierProdcut(SupplierProduct supplierProduct,ProductDataModel productDataModel) {
		Product product=new Product();
		product.setProductName(supplierProduct.getProductName());
		product.setStatus(Constants.STATUS_NOT_PUBLISHED);
		String productCode=supplierProduct.getProductCode();
		while(true){
			Product tempProduct = productManager.getProductByProductCode(productCode);
			if(tempProduct==null)
				break;
			else{
				productCode=Math.abs(UUID.randomUUID().getLeastSignificantBits())+"";
			}
		}
		
		product.setProductCode(productCode);
		product.setAvailabilityRule(CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL);
		product.setProductKind(productDataModel.getProductKind());
		product.setIsAllowReview(new Short(ConfigUtil.getInstance().getIsCreateProductDefaultAllowReviewEnabled()?"1":"0"));
		
		ProductType productType=productTypeManager.getById(productDataModel.getProductTypeId());
		product.setProductType(productType);
		product.setMinOrderQuantity(productType.getMinOrderQuantity()==null?1:productType.getMinOrderQuantity());
		product.setTemplatePath(productType.getTemplatePath());
		
		
		ProductDescription productDescription = new ProductDescription();
		//替换描述(翻译)
		Supplier supplier=supplierProduct.getSupplier();
		String fullDescription=productDataModel.getFullDescription();
		JSONArray prodDescConvertList=JSONArray.fromObject(supplier.getProdDescConvert());
		
		for (Object object : prodDescConvertList) {
			JSONObject obj=(JSONObject)object;
			String regex=(String)obj.keys().next();
			String replacement=(String)obj.get(regex);
			fullDescription=fullDescription.replaceAll(regex, replacement);
		}
		productDescription.setFullDescription(fullDescription);
		productDescription.setShortDescription(productDataModel.getShortDescription());
		productDescription.setImageDescription(productDataModel.getImageDescription());
		productDescriptionManager.save(productDescription);
		product.setProductDescription(productDescription);
		
		
		saveProductAction(product,  new Integer[]{supplierProduct.getCategoryId()}, new Integer[]{supplierProduct.getCategoryId()});
		supplierProduct.setStatus(Constants.STATUS_ACTIVE);
		product.setSupplier(supplierProduct.getSupplier());
		product.setDefaultSupplierProductId(supplierProduct.getSupplierProductId());
		supplierProduct.setProduct(product);
		supplierProductManager.save(supplierProduct);
		
		boolean isVariation = product.getProductKind().intValue()==CatalogConstants.PRODUCT_KIND_VARIATION;
		//sku
		ProductSku productSku=null;
		if(!isVariation){
			productSku=new ProductSku();
			productSku.setProduct(product);
			String productSkuCode=productCode;
			while(true){
				ProductSku tempProductSku = productSkuManager.getProductSkuByProductSkuCode(productSkuCode);
				if(tempProductSku==null)
					break;
				else{
					productSkuCode=Math.abs(UUID.randomUUID().getLeastSignificantBits())+"";
				}
			}
			productSku.setProductSkuCode(productSkuCode);
			productSku.setStatus(Constants.STATUS_ACTIVE);
			productSku.setSkuKind(CatalogConstants.SKU_KIND_ENTITY);
		}
		
		List<String> mediaUrlList=supplierProduct.getMediaUrlList();
		
		//sku images
		for (int i = 0; i < mediaUrlList.size(); i++) {
			String mediaUrl=mediaUrlList.get(i);
			if(i==0){
				String mediaUrlPath=copyProductMediaFile(product, "product", mediaUrl);
				if(StringUtils.isNotBlank(mediaUrlPath)){
					try {
						uploadManager.processImage(mediaUrlPath, "product");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(!isVariation){
					productSku.setImage(mediaUrlPath);
				}
			}else{
				String mediaUrlPath=copyProductMediaFile(product, "productMedia", mediaUrl);
				if(StringUtils.isNotBlank(mediaUrlPath)){
					try {
						uploadManager.processImage(mediaUrlPath, "productMedia");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ProductMedia productMedia=new ProductMedia();
				productMedia.setProductId(product.getProductId());
				productMedia.setMediaUrl(mediaUrlPath);
				productMedia.setMediaType(Short.valueOf("0"));
				productMedia.setMediaDescription("");
				productMedia.setSortOrder(i);
				productMediaManager.save(productMedia);
			}
		}
		
		//设置价格信息
		/*List<String[]> wholesalePriceList=supplierProduct.getWholesalePriceList();
		String wholesalePrices[] =new String[wholesalePriceList.size()-1];
		for (int i = 0; i < wholesalePriceList.size(); i++) {
			if(i==0){
				productSku.setPrice(new BigDecimal(wholesalePriceList.get(i)[1]));
				productSku.setCostPrice(new BigDecimal(wholesalePriceList.get(i)[1]));
			}else{
				wholesalePrices[i-1]=wholesalePriceList.get(i)[0]+"-"+wholesalePriceList.get(i)[1];
			}
		}*/

		if(!isVariation){
			String temp_wholesalePrices[]=productDataModel.getWholesalePrices();
			String wholesalePrices[] = new String[temp_wholesalePrices.length-1];
			for (int i = 0; i < temp_wholesalePrices.length; i++) {
				if(i==0){
					String temp_qty_price[]=temp_wholesalePrices[i].split("-");
					productSku.setPrice(new BigDecimal(temp_qty_price[1]));
					productSku.setCostPrice(new BigDecimal(temp_qty_price[1]));
				}else{
					wholesalePrices[i-1]=temp_wholesalePrices[i];
				}
			}
			
			productSkuManager.save(productSku);
			product.setDefaultProductSku(productSku);
			wholesalePriceManager.saveWholesalePrices(productSku.getProductSkuId(), wholesalePrices);
		}
		
		saveProductAccessorys(product,productDataModel.getAccessoryIds());
		return product;
	}
	
	public String copyProductMediaFile(Product product,String mediaType,String mediaUrl){
		//String storeFrontInstallationPath=ConfigUtil.getInstance().getStoreFrontInstallationPath();
		String mediaPath=ConfigUtil.getInstance().getMediaStorePath();
		if(StringUtils.isNotBlank(mediaUrl)){
			File srcMediaFile=new File(mediaPath+"/supplierProduct/v/"+mediaUrl);
			if(srcMediaFile.exists()){
				//http://192.168.1.123/StoreAdmin/media/product/b/02/06/48/img0729_131436_000.jpg
				String hashPath=FileUtil.hashPath(product.getId().toString(), 6, 2, "0");
				String fileName=srcMediaFile.getName();
				String destMediaFilePath=mediaPath+"/"+mediaType+"/v/"+hashPath+fileName;
				File destFile=new File(destMediaFilePath);
				int i=1;
				while (destFile.exists()){
					i++;
					if(fileName.indexOf(".")!=-1){
						fileName=fileName.substring(0, fileName.lastIndexOf("."))+"-"+i+fileName.substring(fileName.lastIndexOf("."));
					}else{
						fileName=fileName+"-"+i;
					}
					destFile=new File(destMediaFilePath);
				}
				try {
					FileUtils.copyFile(srcMediaFile, destFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return hashPath+fileName;
			}
		}
		return "";
	}

	public void saveProductSkuPrices(ProductSku productSku,String[] wholesalePrices) {
		productSkuManager.save(productSku);
		// 更新保存批发价
		wholesalePriceManager.saveWholesalePrices(productSku.getId(), wholesalePrices);
	}

	@Override
	public Short addExistingProductToCate(Category category, Product product) {
		if(category.getCatalog().getIsVirtual()==Constants.FLAG_FALSE){
			return new Short("0");
		}
		ProductCategory newProductCategory=new ProductCategory();
		newProductCategory.setCategory(category);
		newProductCategory.setProduct(product);
		newProductCategory.setCategoryPath((StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + newProductCategory.getProduct().getProductId());
		newProductCategory.setIsMainCategory(Constants.FLAG_TRUE);
		//检查该目录是否已经存在该产品
		List<ProductCategory> tempProductCateogryList=productCategoryManager.findByProperty("categoryPath", newProductCategory.getCategoryPath());
		if(tempProductCateogryList==null||tempProductCateogryList.size()==0){
			Short isMainCategory=Constants.FLAG_TRUE;
			Set<ProductCategory> productCategories=product.getProductCategorys();
			for (ProductCategory productCategory : productCategories){
				if(productCategory.getCategory().getCatalog().getId().intValue()==category.getCatalogId().intValue()){
					isMainCategory=Constants.FLAG_FALSE;
					break;
				}
			}
			newProductCategory.setIsMainCategory(isMainCategory);
			productCategoryManager.save(newProductCategory);
			return new Short("1");
		}else{
			return new Short("2");
		}
	}

	@Override
	public Short remmoveExistingProduct4Cate(Category category, Product product) {
		if(category.getCatalog().getIsVirtual()==Constants.FLAG_FALSE){
			return new Short("0");
		}
		String productCategoryPath=(StringUtils.isNotEmpty(category.getCategoryPath())?category.getCategoryPath():"") + category.getCategoryId() + "." + product.getProductId();
		List<ProductCategory> tempProductCateogryList=productCategoryManager.findByProperty("categoryPath", productCategoryPath);
		if(tempProductCateogryList!=null&&tempProductCateogryList.size()>0){
			Set<ProductCategory> productCategories=product.getProductCategorys();
			ProductCategory newMainProductCategory=null;
			for (ProductCategory productCategory : productCategories){
				if(productCategory.getCategoryPath().equals(productCategoryPath)){
					if(productCategories.size()>1&&productCategory.getIsMainCategory().intValue()==Constants.FLAG_TRUE){
						for (ProductCategory productCategory2 : productCategories){
							if(productCategory.getCategory().getCatalogId().intValue()==productCategory2.getCategory().getCatalogId().intValue()&&!productCategory.getCategoryPath().equals(productCategoryPath)){
								newMainProductCategory=productCategory2;
							}
						}
					}
					productCategoryManager.deleteById(productCategory.getId());
					break;
				}
			}
			if(newMainProductCategory!=null){
				newMainProductCategory.setIsMainCategory(Constants.FLAG_TRUE);
				productCategoryManager.save(newMainProductCategory);
			}
			return new Short("1");
		}else{
			return new Short("2");
		}
	}

	@Override
	public void updateProductType(Category category, ProductType newProductType) {
		List<ProductCategory> productCategoryList=productCategoryManager.findByProperty("category", category);
		for (ProductCategory productCategory : productCategoryList){
			Product product=productCategory.getProduct();
			if(product.getProductType().getId().intValue()!=newProductType.getId().intValue()){
				//删除自定义属性
				attributeValueManager.deleteValuesByProductId(product.getId());
				//产品评论评分项
				productRateValueManager.deleteAllByProductId(product.getId());
				product.setProductType(newProductType);
				productManager.save(product);
			}
		}
	}

	@Override
	public Integer deleteProduct(Category category,boolean isIncludeSubCat) {
		int count=0;
		int itemsPerPage=500;
		int currentPage=1;
		List<Object> paramList=new ArrayList<Object>();
		String hql="select distinct(p) from Product p,ProductCategory pc where p.productId=pc.product.productId and p.status<>? and p.status<>? and";
		paramList.add(Constants.STATUS_DELETED);
		paramList.add(Constants.STATUS_AWAITING_DELETE);
		if(isIncludeSubCat){
			hql+=" pc.categoryPath like ?";
			paramList.add(category.getFullCategoryPath()+"%");
		}else{
			hql+=" pc.category.categoryId=?";
			paramList.add(category.getCategoryId());
		}
		SearchCriteria searchCriteria =SearchCriteria.getHqlPagingInstance(hql, paramList.toArray(new Object[]{}),currentPage, itemsPerPage, new HashMap());
		List<Product> pageProducts=productManager.searchByCriteria(searchCriteria);
		for (int i = 1; i <= searchCriteria.getTotalPageCount(); i++) {
			if(i!=1){
				searchCriteria.changePaging(i, itemsPerPage);
				pageProducts=productManager.searchByCriteria(searchCriteria);
			}
			if(pageProducts.size()==0){
				break;
			}
			for (Product product : pageProducts) {
				delete(product.getProductId());
				count++;
			}
		}
		return count;
	}

	public ProductHandDrawManager getProductHandDrawManager() {
		return productHandDrawManager;
	}

	public void setProductHandDrawManager(ProductHandDrawManager productHandDrawManager) {
		this.productHandDrawManager = productHandDrawManager;
	}

	public void setProductMediaUpManager(ProductMediaUpManager productMediaUpManager) {
		this.productMediaUpManager = productMediaUpManager;
	}
}

package com.cartmatic.estore.common.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.util.UrlBuilderUtil;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryMenu;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.textsearch.SearchConstants;
import com.cartmatic.estore.webapp.event.IndexNotifyEvent;

public class CatalogHelper {
	final static CatalogHelper	catalogHelper	= new CatalogHelper();
	private CategoryManager	categoryManager	= null;
	private ProductCategoryManager	productCategoryManager	= null;
	private ProductSkuManager			productSkuManager			= null;
	private ProductManager				productManager				= null;
	private final transient Log	logger	= LogFactory.getLog(CatalogHelper.class);

	private CatalogHelper() {
	}
	
	public static CatalogHelper getInstance(){
		return catalogHelper;
	}

	/**
	 * 通过完整的categoryPath获取相应的目录 
	 * （完整的Path，由所有目录id及产品id组成）
	 * @param productCategoryPath
	 * @return
	 */
	public Category getCategoryByProductCategoryPath(String productCategoryPath){
		Category category=productCategoryManager.getCategoryByProductCategoryPath(productCategoryPath);
		return category;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}


	public void setProductCategoryManager(ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}
	

	/**
	 * 
	 * @param category 要显示目录
	 * @param displayLevel 显示目录的级数（深度）,从1开始;-1代表显示该父目录的所有子目录
	 * @param excludeCode 要排除的目录
	 * @return
	 */
	public CategoryMenu getCategoryByLevel(CategoryTreeItem category,Integer displayLevel,String excludeCode){
		CategoryMenu categoryMenu=new CategoryMenu();
		List<CategoryTreeItem> result=new ArrayList<CategoryTreeItem>();
		categoryMenu.setCategoryTreeList(result);
		//获取实际要显示的目录的级别
		int fillCategoryLevel=displayLevel.intValue()+1;
		categoryMenu.setDisplayLevel(fillCategoryLevel);
		
		String []excludeCodes={};
		if(StringUtils.isNotBlank(excludeCode)){
			excludeCodes=StringUtils.split(excludeCode,",");
		}
		
		if(category.getCategoryPath()!=null){
			fillCategoryLevel+=category.getCategoryPath().split("\\.").length;
		}
		
		filterCategoryItem(result, category, fillCategoryLevel, excludeCodes);
		
		if(result.size()>0){
			//设置指定目录的实际级数
			categoryMenu.setDisplayLevel(fillCategoryLevel);
		}
		return categoryMenu;
	}
	
	private void filterCategoryItem(List<CategoryTreeItem> result,CategoryTreeItem category,Integer fillCategoryLevel,String excludeCodes[]){
		Set<CategoryTreeItem>categoryTreeItemList=category.getCategorys();
		for (CategoryTreeItem categoryTreeItem : categoryTreeItemList) {
			//设定实际显示的目录等级
			if(categoryTreeItem.getLevel()<=fillCategoryLevel&&!ArrayUtils.contains(excludeCodes,categoryTreeItem.getCategoryCode())){
				result.add(categoryTreeItem);
				filterCategoryItem(result, categoryTreeItem, fillCategoryLevel, excludeCodes);
			}
		}
	}

	/**
	 * 仅供CategoryManager使用，前台使用,(前台直接从category.url获取)
	 * @param categoryList
	 * @return
	 */
	public String getProductCategoryUrl(Category category){
		String url=UrlBuilderUtil.getInstance().getProductCategoryUrl(category);
		return url;
	}
	
	/**
	 * 仅供CategoryManager使用
	 * @param categoryList 目录的导航list
	 * @return
	 */
	public String getContentCategoryUri(Category category){
		String url=UrlBuilderUtil.getInstance().getContentCategoryUrl(category);
		return url;
	}
	
	/**
	 * 根据Id获取目录
	 * （存在缓存,仅供前台使用）
	 * @param categoryType 目录类型
	 * @param categoryId 目录Id
	 * @return
	 */
	public Category getCategoryById4Front(Short categoryType,Integer categoryId){
		List<CategoryTreeItem> categoryTreeItems=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG.intValue()){
			Integer catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
			categoryTreeItems=categoryManager.getCatalogTreeItemList4Front(catalogId);
		}else{
			Integer storeId=ConfigUtil.getInstance().getStore().getStoreId();
			categoryTreeItems=categoryManager.getContentTreeItemList4Front(storeId);
		}
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			if(categoryTreeItem.getId().intValue()==categoryId.intValue()){
				return categoryTreeItem;
			}
		}
		return null;
	}
	
	/**
	 * 根据目录编码获取目录
	 * （存在缓存,仅供前台使用）
	 * @param categoryType目录类型
	 * @param categoryCode 目录编码
	 * @return
	 */
	public Category getCategoryByCode4Front(Short categoryType,String categoryCode){
		List<CategoryTreeItem> categoryTreeItems=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG.intValue()){
			Integer catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
			categoryTreeItems=categoryManager.getCatalogTreeItemList4Front(catalogId);
		}else{
			Integer storeId=ConfigUtil.getInstance().getStore().getStoreId();
			categoryTreeItems=categoryManager.getContentTreeItemList4Front(storeId);
		}
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			if(categoryTreeItem.getCategoryCode().equals(categoryCode)){
				return categoryTreeItem;
			}
		}
		return null;
	}
	

	/**
	 * 
	 * （存在缓存,仅供前台使用）
	 * @param categoryType目录类型
	 * @param categoryIds 目录Id
	 * @return
	 */
	/*public List<Category> getCategoryById4Front(Short categoryType,Integer[]categoryIds){
		Category categorys[]=new Category[categoryIds.length];
		List<CategoryTreeItem> categoryTreeItems=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG.intValue()){
			Integer catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
			categoryTreeItems=categoryManager.getCatalogTreeItemList4Front(catalogId);
		}else{
			Integer storeId=ConfigUtil.getInstance().getStore().getStoreId();
			categoryTreeItems=categoryManager.getContentTreeItemList4Front(storeId);
		}
		int count=0;
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			int index=Arrays.binarySearch(categoryIds, categoryTreeItem.getId().intValue());
			if(index>=0){
				categorys[index]=categoryTreeItem;
				count++;
				if(categoryIds.length==count)break;
			}
		}
		return Arrays.asList(categorys);
	}*/
	
	/**
	 * 获取指定编码的目录
	 * （存在缓存,仅供前台使用）
	 * @param categoryType目录类型
	 * @param categoryCodes 目录编码
	 * @return
	 */
	/*public List<Category> getCategoryByCode4Front(Short categoryType,String[]categoryCodes){
		Category categorys[]=new Category[categoryCodes.length];
		List<CategoryTreeItem> categoryTreeItems=null;
		if(categoryType.intValue()==Constants.CATEGORY_TYPE_CATALOG.intValue()){
			Integer catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
			categoryTreeItems=categoryManager.getCatalogTreeItemList4Front(catalogId);
		}else{
			Integer storeId=ConfigUtil.getInstance().getStore().getStoreId();
			categoryTreeItems=categoryManager.getContentTreeItemList4Front(storeId);
		}
		int count=0;
		for (CategoryTreeItem categoryTreeItem : categoryTreeItems) {
			int index=Arrays.binarySearch(categoryCodes, categoryTreeItem.getCategoryCode());
			if(index>=0){
				categorys[index]=categoryTreeItem;
				count++;
				if(categoryCodes.length==count)break;
			}
		}
		return Arrays.asList(categorys);
	}*/
	
	/**
	 * 获取产品访问URI 
	 * @param product 产品
	 * @param categoryId 产品所在目录Id，可以为null，非null时明确指定目录，（主要是额为目录）
	 * @return
	 */
	public String getProductUrl(Product product,Integer catalogId,Integer categoryId){
		return UrlBuilderUtil.getInstance().getProductUrl(product,catalogId,categoryId);
	}

	/**
	 * 目前内容不存在额外目录，因此本方法可以前后台调用
	 * @param content
	 * @param navigatorCategorys
	 * @return
	 */
	public String getContentUrl(Content content){
		return UrlBuilderUtil.getInstance().getContentUrl(content);
	}
	
	
	public Category getProductCategoryByUri(String uri){
		Category category=UrlBuilderUtil.getInstance().getProductCategoryByUri(uri);
		return category;
	}
	
	public Category getContentCategoryByUri(String uri){
		Category category=UrlBuilderUtil.getInstance().getContentCategoryByUri(uri);
		return category;
	}
	
	/**
	 * 根据详细页面的URI获取产品Id,及产品所在目录Id
	 * @param uri /eStore/Piaget-Black-Tie-Emperador-Tourbillon-Skeleton_p398.html
	 * @return  20
	 */
	public Integer[] getProductIdByUri(String uri){
		return UrlBuilderUtil.getInstance().getIdsByProductUrl(uri);
	}
	
	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}
	
	public ProductSku getProductSkuById(Integer productSkuId){
		return productSkuManager.getById(productSkuId);
	}
	
	public Product getProductById(Integer productId){
		return productManager.getById(productId);
	}
	
	public ProductSku getProductSkuByCode(String productSkuCode){
		return productSkuManager.getProductSkuByProductSkuCode(productSkuCode);
	}
	
	public Product getProductByCode(String productCode){
		return productManager.getProductByProductCode(productCode);
	}
	
	public Category getCategoryById(Integer categoryId){
		return categoryManager.getById(categoryId);
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	
	public Integer getPageNoByUriForContent(String uri){
		if(uri.contains("_pn")){
			return Integer.parseInt(uri.substring(uri.indexOf("_pn")+"_pn".length(),uri.indexOf(".html")));
		}else{
			return 1;
		}
	}
	/**
	 * 永远只会见到两个价
	 * 1.市场价、售价
	 * 2.售价、特价
	 * 3.售价、折扣价
	 * 4.售价
	 * @param productSku
	 * @return
	 */
	public void setPriceViewType(ProductSku productSku){
		Short priceViewType=null;
		if(productSku.getPrice().compareTo(BigDecimal.ZERO)>0&&productSku.getSalePrice()!=null){
			priceViewType=2;
		}else if(productSku.getPrice().compareTo(BigDecimal.ZERO)>0&&productSku.getDiscountPrice().compareTo(productSku.getPrice())!=0){
			priceViewType= 3;
		}else if(productSku.getListPrice()!=null&&productSku.getListPrice().compareTo(BigDecimal.ZERO)>0){
			priceViewType= 1;
		}else{
			priceViewType= 4;
		}
		productSku.setPriceViewType(priceViewType);
	}
	
	/**
	 * 获取sku可以添加到购物车下拉列表显示的最小数量和最大数量
	 * @param productSku
	 * @param buyCount
	 * @return
	 */
	public Integer[]getMinAndMaxQuantityToCart(ProductSku productSku,Integer buyCount){
		if(buyCount==null||buyCount.intValue()==0){
			buyCount=1;
		}
		Integer minAndMaxQuantity[]=new Integer[2];
		Product product=productSku.getProduct();
		minAndMaxQuantity[0]=product.getMinOrderQuantity()==null?buyCount:product.getMinOrderQuantity();
		Short availabilityRule=product.getAvailabilityRule();
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL){
			minAndMaxQuantity[1]=buyCount+10;
			return minAndMaxQuantity;
		}
		Inventory inventory=productSku.getInventory();
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ONLY_IN_STOCK){
			if(buyCount>=inventory.getAvailableQuantity()){
				minAndMaxQuantity[1]=buyCount;
			}else if(inventory.getAvailableQuantity()>=(buyCount+10)){
				minAndMaxQuantity[1]=buyCount+10;
			}else{
				minAndMaxQuantity[1]=inventory.getAvailableQuantity();
			}
			if(minAndMaxQuantity[0]>minAndMaxQuantity[1])minAndMaxQuantity[1]=minAndMaxQuantity[0];
			return minAndMaxQuantity;
		}
		if(product.getPreOrBackOrderLimit()==null||product.getPreOrBackOrderLimit().intValue()==0){
			 minAndMaxQuantity[1]=buyCount+10;
		}else{
			Integer canAddToCartQuintity=inventory.getAvailableQuantity()+(product.getPreOrBackOrderLimit()-inventory.getPreOrBackOrderedQty());
			if(buyCount>=canAddToCartQuintity){
				minAndMaxQuantity[1]=buyCount;
			}else if(canAddToCartQuintity.intValue()>(buyCount+10)){
				 minAndMaxQuantity[1]=buyCount+10;
			}else{
				 minAndMaxQuantity[1]=canAddToCartQuintity;
			}
		}
		return minAndMaxQuantity;
	}
	
	/**
	 * 获取sku可以添加到购物车的最小数量和最大数量,text input非下拉
	 * @param productSku
	 * @param buyCount
	 * @return
	 */
	public Integer[]getMinAndMaxQuantityToCart(ProductSku productSku){
		Integer defaultMaxQuantity=10;
		Integer minAndMaxQuantity[]=new Integer[2];
		Product product=productSku.getProduct();
		minAndMaxQuantity[0]=product.getMinOrderQuantity()==null?1:product.getMinOrderQuantity();
		
		minAndMaxQuantity[1] = 0;
		int maxQuantity = 0;
		for(ProductSku sku : product.getProductSkus()){
			Inventory inv  = sku.getInventory();
			if(inv != null){
				Integer avQn = inv.getAvailableQuantity();
				maxQuantity += (avQn == null ? 0 : avQn.intValue());
			}
		}
		minAndMaxQuantity[1] = maxQuantity;
		
		Short availabilityRule=product.getAvailabilityRule();
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL||availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL){
			minAndMaxQuantity[1]=defaultMaxQuantity;
			return minAndMaxQuantity;
		}
		/*
		Inventory inventory=productSku.getInventory();
		if(availabilityRule.intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ONLY_IN_STOCK){
			minAndMaxQuantity[1]=inventory.getAvailableQuantity();
			return minAndMaxQuantity;
		}
		if(product.getPreOrBackOrderLimit()==null||product.getPreOrBackOrderLimit().intValue()==0){
			 minAndMaxQuantity[1]=defaultMaxQuantity;
		}else{
			Integer canAddToCartQuintity=inventory.getAvailableQuantity()+(product.getPreOrBackOrderLimit()-inventory.getPreOrBackOrderedQty());
			minAndMaxQuantity[1]=canAddToCartQuintity;
		}
		*/
		return minAndMaxQuantity;
	}
	
	/**
	 * 前台搜索排序hql转换
	 * @param sortOrder
	 * @return
	 */
	public String convertSortOrder(String sortOrder){
		if(StringUtils.isEmpty(sortOrder)){
			sortOrder="p.publishTime desc";
			return sortOrder;
		}else if(sortOrder.equals("price")){
			sortOrder="ifnull(p.defaultProductSku.salePrice,p.defaultProductSku.price) asc,p.productId desc";
		}else if(sortOrder.equals("dprice")){
			sortOrder="ifnull(p.defaultProductSku.salePrice,p.defaultProductSku.price)desc,p.productId desc";
		}else if(sortOrder.equals("productName")){
			sortOrder="p.productName asc,p.productId desc";
		}else if(sortOrder.equals("dproductName")){
			sortOrder="p.productName desc,p.productId desc";
		}else if(sortOrder.equals("dpublishTime")){
			sortOrder="p.publishTime desc,p.productId desc";
		}else if(sortOrder.equals("dbuyCount")){
			sortOrder="pstat.buyCount desc,p.productId desc";
		}else if(sortOrder.equals("drate")){
			sortOrder="pstat.averageRate desc,p.productId desc";
		}
		return sortOrder;
	}
	
	public void indexNotifyUpdateEvent(Integer...productIds){
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_PRODUCT, SearchConstants.UPDATE_TYPE.UPDATE); 
		event.setIds(Arrays.asList(productIds));
		ContextUtil.getInstance().fireApplicationEvent(event);
	}
	
	public void indexNotifyDeleteEvent(Integer...productIds){
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_PRODUCT, SearchConstants.UPDATE_TYPE.DEL); 
		event.setIds(Arrays.asList(productIds));
		ContextUtil.getInstance().fireApplicationEvent(event);
	}
}
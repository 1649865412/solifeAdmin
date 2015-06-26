package com.cartmatic.estore.catalog.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cartmatic.estore.catalog.model.BulkProduct;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductCompareModel;
import com.cartmatic.estore.common.model.catalog.ProductDataModel;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.exception.BulkUpdateException;

public interface ProductMainManager {

	
	/**
	 * 保存产品主体数据
	 * @param product
	 * @param mainCategoryId 产品主目录ID
	 * @param categoryIds产品目录ID（包括主目录和额外目录）
	 */
	public void saveProductAction(Product product,Integer[] mainCategoryIds,Integer[]categoryIds);
	
	/**
	 * 保存商品目录分类
	 * @param catalogId
	 * @param product
	 * @param mainCategoryId
	 * @param categoryIds
	 */
	public void saveProductCategories(Integer catalogId,Product product, Integer mainCategoryId, Integer[] categoryIds);
	
	
	/**
	 * 获取与指定产品类型有关联，但与指定sku没有关联的SkuOption
	 * 按ProductTypeSkuOption的sortOrder排序
	 * @param productTypeId
	 * @param productSkuId
	 * @return
	 */
	public List<SkuOption> findActiveSkuOptionsOfProductTypeExcludeRefProductSku(Integer productTypeId,Integer productSkuId);
	
	
	/**
	 * 获取与某Sku的产品的默认Sku有关联，但与本Sku没有的SkuOption（defaultProductSkuId与productSkuId必须属于同一个产品）
	 * @param defaultProductSkuId  
	 * @param productSkuId
	 * @return
	 */
	public List<SkuOption> findSkuOptionsOfDefaultProductSkuExcludeRefProductSku(Integer defaultProductSkuId,Integer productSkuId);
	
	/**
	 * 获取指定ProductSku的SkuOption及SkuOptionValue
	 * 本方法主要的作用是排序，如不需排序的建议直接通过ProductSku.getProductSkuValue()获取相应的ProductSkuValue
	 * @param productSkuId
	 * @return
	 */
	public Map<SkuOption,SkuOptionValue>findSkuOptionAndValuesByProductSku(Integer productSkuId);
	
	
	
	/**
	 * 保存变种产品Sku信息,本方法不检查选项是否存在相应的Sku
	 * @param productSku
	 * @param skuOptionValueIds
	 * @param wholesalePrices
	 */
	public void saveProductSkuAction(ProductSku productSku,Set<Integer> skuOptionValueIds,String wholesalePrices[]);
	 
	/**
	 * 保存产品信息
	 * @param productDataModel
	 * @return
	 */
	public Map<String, Object> saveProductAction(ProductDataModel productDataModel);
	
	/**
	 * 保存普通产品Sku信息
	 * @param product
	 * @param productSku
	 * @param wholesalePrices
	 */
	public void saveSimpleProductSku(Product product,ProductSku productSku,String wholesalePrices[]);
	
	/**
	 * 复制产品 
	 * @param orig
	 * @return
	 * @throws Exception
	 */
	public Product doCopyProduct(Product orig)throws Exception ;
	
	/**
	 * 查找产品自定义属性(自定义属性组)
	 * @param product
	 * @return
	 */
	public List<ProductAttGroup> findAttGroupAndProductAttrValueByProduct(Product product);
	
	public void delete(Integer productId);
	
	/**
	 * 清除待删除产品
	 */
	public void doClearAwaitingDeleteProductJob();
	
	/**
	 * 添加评论
	 * @param productReview
	 * @param attrRates自定义评分项得分 "id_value"分别代表"评分项Id_得分"
	 */
	public void saveProductReviewAction(ProductReview productReview,String attrRates[]);
	
	/**
	 * 获取某产品的Option(变种选项)，按产品类型排序
	 * （只包含激活的Sku）
	 * @param productSkuId
	 * @return
	 */
	public Map<SkuOption,List<SkuOptionValue>> findSkuOptionsByProduct(Integer productId);
	

	
	/**
	 * 产品比较(后台使用)
	 * TODO 目前后台没有这功能
	 * @param productIds
	 * @return
	 */
	public ProductCompareModel getProductCompareModel(Integer productIds[]);
	
	/**
	 * 产品比较(前台使用)
	 * @param productIds
	 * @return
	 */
	public ProductCompareModel getProductCompareModelForFront(Integer productIds[]);
	
	
	/**
	 * 设置更新产品关联目录，
	 * @param product 
	 * @param mainCategoryId
	 * @param categoryIds
	 */
	public void saveProductCategories(Product product, Integer[] mainCategoryIds, Integer[] categoryIds);
	
	/**
	 * 批量修改产品
	 * @param bulkProductList
	 */
	public void doBulkProduct(List<BulkProduct>bulkProductList)throws BulkUpdateException;
	
	/**
	 * 设置产品与供应商关联
	 * @param product
	 * @param defaultSupplierId
	 * @param supplierIds
	 */
	public void saveProductSuppliersAction(Product product,Integer defaultSupplierId,Integer supplierIds[]);
	
	/**
	 * 由供应商上传的产品数据创建在线销售的产品实体
	 * @param supplierProduc
	 * @param productDataModel 部分必填产品数据
	 * @return返回创建好的产品实体（草稿状态）
	 */
	public Product createProduct4SupplierProdcut(SupplierProduct supplierProduct,ProductDataModel productDataModel);
	
	/**
	 * 保存Sku 价格（售价，市场价，批发价，特价）
	 * @param productSku
	 * @param wholesalePrices
	 */
	public void saveProductSkuPrices(ProductSku productSku,String wholesalePrices[]);
	
	/**
	 * 为目录添加产品(已存在的)
	 * @param category
	 * @param product
	 * @return 1 成功添加，2产品已存在
	 */
	public Short addExistingProductToCate(Category category, Product product);
	
	/**
	 * 对目录移除产品(已存在的)
	 * @param category
	 * @param product
	 * @return 1 成功删除，2产品不存在
	 */
	public Short remmoveExistingProduct4Cate(Category category, Product product);

	/**
	 * 修改产品的产品类型
	 * @param category
	 * @param newProductType
	 */
	public void updateProductType(Category category,ProductType newProductType);
	
	
	/**
	 * 删除指定目录所有产品产品
	 * @param category 
	 * @param isIncludeSubCat 是否包含子目录
	 */
	public Integer deleteProduct(Category category,boolean isIncludeSubCat);
}

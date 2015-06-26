package com.cartmatic.estore.common.model.catalog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.cartmatic.estore.catalog.CatalogConstants;
import com.cartmatic.estore.common.model.catalog.base.ProductSkuTbl;
import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.sales.engine.PRule;

/**
 * Model class for ProductSku. Add not database mapped fileds in this class.
 */
public class ProductSku extends ProductSkuTbl implements Cloneable{
	
	private BigDecimal discountPrice ;
	//*RAM用于暂时保存促销规则模拟运行后的结果
	private BigDecimal discountPriceRAM = new BigDecimal(0);
	//是否已对该商品打折
	private boolean hasDiscount = false;
	//用于记录影响sku的单条促销规则
	private PRule prule = null;
	//用于记录售价(特价或者折扣价)比原价折扣了多少
	private BigDecimal discountPercent ;
	
	/**
	 * 永远只会见到两个价
	 * 1.市场价、售价
	 * 2.售价、特价
	 * 3.售价、折扣价
	 * 4.售价
	 */
	private Short priceViewType;

  	public Short getPriceViewType() {
		return priceViewType;
	}

	public void setPriceViewType(Short priceViewType) {
		this.priceViewType = priceViewType;
	}

	/**
	 * Default Empty Constructor for class ProductSku
	 */
	public ProductSku () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productSkuName
	 * 必须手工完成这个部分，否则编译不通过。
	 * 注意：本方法不能直接获取产品名称，如需获取产品名称应该通过getProduct().productName();获取
	 */
	public String getProductSkuName () {
		if (productSkuId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productSkuCode;
	}
	
	/**
	 * Default Key Fields Constructor for class ProductSku
	 */
	public ProductSku (
		 Integer in_productSkuId
		) {
		super (
		  in_productSkuId
		);
	}

	public void setProductSkuStatus(Short productSkuStatus) {
		this.status = productSkuStatus;
	}

	
	
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public BigDecimal getDiscountPriceRAM() {
		return discountPriceRAM;
	}

	public void setDiscountPriceRAM(BigDecimal discountPriceRAM) {
		this.discountPriceRAM = discountPriceRAM;
	}
	
	
	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public boolean isHasDiscount() {
		return hasDiscount;
	}

	public void setHasDiscount(boolean hasDiscount) {
		this.hasDiscount = hasDiscount;
	}

	/**
	 * 构建简单的Json对象主要用于选择器
	 * @return
	 */
	public JSONObject getJsonObject(){
		JSONObject jsonProductSku=new JSONObject();
		jsonProductSku.put("productSkuId",this.productSkuId);
		jsonProductSku.put("productSkuCode",this.productSkuCode);
		jsonProductSku.put("price",this.price);
		jsonProductSku.put("salePrice",this.salePrice);
		jsonProductSku.put("image",this.image);
		Map<String,Object> product=new HashMap<String, Object>();
		product.put("productId",this.getProduct().getProductId());
		product.put("productName",this.getProduct().getProductName());
		product.put("productCode", this.getProduct().getProductCode());
		Map<String,Object> brand=new HashMap<String, Object>();
		brand.put("brandId",this.getProduct().getBrand()==null?"":this.getProduct().getBrand().getBrandId());
		brand.put("brandName",this.getProduct().getBrand()==null?"":this.getProduct().getBrand().getBrandName());
		product.put("brand",brand);
		jsonProductSku.put("product",product);
		return jsonProductSku; 
	}
	
	/**
	 * 是否虚拟产品？
	 * 目前非实体的都算虚拟产品
	 * @return
	 */
	public boolean getIsFictitious(){
		return this.skuKind==null?false:this.skuKind!=1;
	}
	
	
	/**
	 * 本方法，当产品为无限库存时，直接返回null
	 * @return
	 */
	public Inventory getInventory(){
		if(getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_ALWAYS_SELL||getProduct().getAvailabilityRule().intValue()==CatalogConstants.PRODUCT_AVAILABILITY_NOT_IN_STOCK_SELL)
			return null;
		if(getInventorys()==null||getInventorys().size()==0)
			return null;
		else
			return getInventorys().iterator().next();
	}
	
	
	
	public PRule getPrule() {
		return prule;
	}

	public void setPrule(PRule prule) {
		this.prule = prule;
	}

	public Object clone(){
		ProductSku o=null;
        try
        {
            o=(ProductSku)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;

	}
	
	public String getOrderSkuDisplayOption(){
		Set<ProductSkuOptionValue> psos = this.getProductSkuOptionValues();
		String displayOptions = "";
		for(ProductSkuOptionValue pso:psos){
			displayOptions += pso.getSkuOptionValue().getSkuOption().getSkuOptionName()+":"+pso.getSkuOptionValue().getSkuOptionValueName()+"###";
		}
		if(displayOptions.length()!=0){
			displayOptions = displayOptions.substring(0,displayOptions.length()-3);
		}
		return displayOptions;
	}
}

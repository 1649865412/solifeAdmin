package com.cartmatic.estore.catalog.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.catalog.model.BulkField;
import com.cartmatic.estore.catalog.model.BulkProduct;
import com.cartmatic.estore.catalog.model.BulkProductModel;
import com.cartmatic.estore.catalog.model.BulkProductSku;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;


public class BulkHelper {
	final static BulkHelper bulkHelper=new BulkHelper();
	
	private ProductManager productManager=null;
	
	private BulkHelper(){}
	
	public static BulkHelper getInstance(){
		return bulkHelper;
	}
	
	public List<BulkProduct> builderBulkProductByMap(Map<String,String[]>postData){
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String productIds=postData.get("productIds")[0];
		List<Product> productList=productManager.getByIds(productIds.split(","));
		String prodCommAttrs[] =configUtil.getBulkProdCommAttrs();
		String bulkSkuCommAttrs[] =configUtil.getBulkSkuCommAttrs();
		List<BulkProduct> bulkProductList=new ArrayList<BulkProduct>();
		for (Product product : productList) {
			BulkProduct bulkProduct=new BulkProduct();
			bulkProduct.setProduct(product);
			String pId=product.getId().toString();
			//创建构造产品基本属性，批量修改对象
			for (String prodCommAttr : prodCommAttrs) {
				BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_PRODUCT,prodCommAttr,pId);
				String value[]=postData.get(bulkField.getInputName());
				if(value!=null){
					bulkField.setValue(value[0]);
					bulkProduct.getProdCommAttrs().put(prodCommAttr, bulkField);
				}
			}
			//创建构造产品自定义属性，批量修改对象
			Set<ProductAttrValue> productAttrValueSet=product.getProductAttrValues();
			for (ProductAttrValue productAttrValue : productAttrValueSet) {
				BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_PROD_ATTR,productAttrValue.getAttribute().getAttributeCode(),product.getId());
				String value[]=postData.get(bulkField.getInputName());
				if(value!=null){
					bulkField.setValue(value[0]);
					bulkField.setAttribute(productAttrValue.getAttribute());
					bulkProduct.getProdAttrs().put(productAttrValue.getAttribute().getAttributeCode(), bulkField);
				}
			}
			//创建构造Sku基本属性，批量修改对象
			Set<ProductSku>productSkus =product.getProductSkus();
			for (ProductSku productSku : productSkus) {
				BulkProductSku bulkProductSku=new BulkProductSku();
				bulkProductSku.setProductSku(productSku);
				String skuId=productSku.getId().toString();
				for (String bulkSkuCommAttr : bulkSkuCommAttrs) {
					BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_SKU,bulkSkuCommAttr,skuId);
					String value[]=postData.get(bulkField.getInputName());
					if(value!=null){
						bulkField.setValue(value[0]);
						bulkProductSku.getSkuCommAttrs().put(bulkSkuCommAttr, bulkField);
					}
				}
				if(bulkProductSku.getSkuCommAttrs().size()>0){
					bulkProduct.getProductSkus().add(bulkProductSku);
				}
			}
			bulkProductList.add(bulkProduct);
		}
		return bulkProductList;
	}
	
	public List<BulkProduct> builderBulkProduct(BulkProductModel bulkProductModel){
		List<Product> productList=productManager.getByIds(bulkProductModel.getProductIds().split(","));
		String[]prodCommAttrs=bulkProductModel.getProdCommAttrs();
		String[]skuCommAttrs=bulkProductModel.getSkuCommAttrs();
		String[]prodAttrs=bulkProductModel.getProdAttrs();
		List<BulkProduct>bulkProductList=new ArrayList<BulkProduct>();
		for (Product product : productList) {
			BulkProduct bulkProduct=new BulkProduct();
			bulkProduct.setProduct(product);
			fillProdCommAttrField(bulkProduct, prodCommAttrs, product);
			fillProdAttrField(bulkProduct, prodAttrs, product); 
			fillSku(bulkProduct, skuCommAttrs, product);
			bulkProductList.add(bulkProduct);
		}
		return bulkProductList;
	}
	
	private void fillProdAttrField(BulkProduct bulkProduct, String[] prodAttrs, Product product) {
		Set<ProductAttrValue> productAttrValueSet=product.getProductAttrValues();
		for (ProductAttrValue productAttrValue : productAttrValueSet) {
			if(ArrayUtils.contains(prodAttrs, productAttrValue.getAttribute().getAttributeCode())){
				BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_PROD_ATTR,productAttrValue.getAttribute().getAttributeCode(),product.getId());
				bulkField.setValue(productAttrValue.getAttributeValue().toString());
				bulkField.setAttribute(productAttrValue.getAttribute());
				bulkProduct.getProdAttrs().put(productAttrValue.getAttribute().getAttributeCode(), bulkField);
			}
		}
	}

	private void fillSku(BulkProduct bulkProduct, String[] skuCommAttrs, Product product) {
		if(skuCommAttrs==null||skuCommAttrs.length==0)return;
		Set<ProductSku> productSkus=product.getProductSkus();
		List<BulkProductSku>bulkProductSkuList=new ArrayList<BulkProductSku>();
		for (ProductSku productSku : productSkus) {
			BulkProductSku bulkProductSku=new BulkProductSku();
			bulkProductSku.setProductSku(productSku);
			fillSkuCommAttrField(bulkProductSku, skuCommAttrs, productSku);
			bulkProductSkuList.add(bulkProductSku);
		}
		bulkProduct.setProductSkus(bulkProductSkuList);
		
	}
	private void fillSkuCommAttrField(BulkProductSku bulkProductSku,String[]skuCommAttrs,ProductSku productSku){
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String bulkSkuCommAttrs[] =configUtil.getBulkSkuCommAttrs();
		BeanUtilsBean beanUtils=getBeanUtilsBean();
		for (String bulkSkuCommAttr : skuCommAttrs) {
			if(ArrayUtils.contains(bulkSkuCommAttrs, bulkSkuCommAttr)){
				BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_SKU,bulkSkuCommAttr,productSku.getId());
				try {
					String value=beanUtils.getProperty(productSku, bulkSkuCommAttr);
					bulkField.setValue(value);
				} catch (Exception e) {
					//TODO 获取数据出错
					e.printStackTrace();
					continue;
				}
				bulkProductSku.getSkuCommAttrs().put(bulkSkuCommAttr, bulkField);
			}
		}
	}

	private void fillProdCommAttrField(BulkProduct bulkProduct,String[]prodCommAttrs,Product product){
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String sys_prodCommAttrs[] =configUtil.getBulkProdCommAttrs();
		BeanUtilsBean beanUtils=getBeanUtilsBean();
		if(prodCommAttrs!=null){
			for (String prodCommAttr : prodCommAttrs) {
				if(ArrayUtils.contains(sys_prodCommAttrs, prodCommAttr)){
					BulkField bulkField=new BulkField(BulkField.OBJ_TYPE_PRODUCT,prodCommAttr,product.getId());
					try {
						String value=beanUtils.getProperty(product, prodCommAttr);
						bulkField.setValue(value);
					} catch (Exception e) {
						//TODO 获取数据出错
						e.printStackTrace();
						continue;
					}
					bulkProduct.getProdCommAttrs().put(prodCommAttr, bulkField);
				}
			}
		}
	}
	
	public BeanUtilsBean getBeanUtilsBean(){
		ConvertUtilsBean cub=new ConvertUtilsBean();
		cub.register(new Converter(){
		public Object convert(Class arg0, Object arg1) {
			Date date=null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date=df.parse(arg1.toString());
			} catch (ParseException e) {
				df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date=df.parse(arg1.toString());
				} catch (ParseException e1) {
					date=null;
				}
			}
			return date;
		}},Date.class);
		cub.register(new ShortConverter(null),Short.class);
		cub.register(new IntegerConverter(null),Integer.class);
		cub.register(new DoubleConverter(null),Double.class);
		cub.register(new LongConverter(null),Long.class);
		cub.register(new FloatConverter(null),Float.class);
		cub.register(new BooleanConverter(null),Boolean.class);
		cub.register(new BigIntegerConverter(null),BigInteger.class);
		cub.register(new BigDecimalConverter(null),BigDecimal.class);
		BeanUtilsBean bean=new BeanUtilsBean(cub,new PropertyUtilsBean());
		return bean;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	
	
}

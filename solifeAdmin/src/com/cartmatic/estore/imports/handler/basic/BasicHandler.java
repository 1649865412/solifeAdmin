
package com.cartmatic.estore.imports.handler.basic;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

/**
 * required为flase,value为空的取defaultValue,defaultValue都为空的,忽略该属性的设置
 * required为true,只取value值,空的直接忽略该条数据的导入
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class BasicHandler extends ColumnBasicHandler implements ColumnHandler {
	private Logger logger = Logger.getLogger(BasicHandler.class);
	/**
	 * 初始化对象
	 * @param target
	 * @param propertiesName
	 * @throws Exception
	 */
	private  void newInstanceProperties(Object target,String propertiesName) throws Exception{
		String properties[]=propertiesName.split("\\.");
		if(properties.length>1){
			for (int i = 0; i < properties.length-1; i++) {
				String property = properties[i];
				Object obj=BeanUtils.getProperty((Product)target, property);
				if(obj==null){
					Method method=MethodUtils.getAccessibleMethod(target.getClass(), "get"+StringUtils.capitalize(property),new Class[]{});
					obj=method.getReturnType().newInstance();
					BeanUtils.setProperty(target, property,obj);
				}
				target=obj;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new BigDecimal(112.33));
		System.out.println(BigDecimal.valueOf(112.33));
		NumberFormat nf=new DecimalFormat("##,###.##");
		Number n=nf.parse("23123.2432");
		System.out.println(n.doubleValue());
		BasicHandler basicHandler=new BasicHandler();
		Column column=new Column(); 
		column.setPattern("yyyy-MM-dd");
		/*	Product product3=new Product();
		basicHandler.newInstanceProperties(product3,  "defaultProductSku.status");
		beanUtils.setProperty(product3, "defaultProductSku.status", "aaa");
		System.out.println(beanUtils.getProperty(product3, "defaultProductSku.status"));
		*/

		BeanUtilsBean beanUtils=basicHandler.getBeanUtilsBean(column); 
		ProductSku productSku=new ProductSku();
		beanUtils.setProperty(productSku, "price","123456,789.33");
		System.out.println(productSku.getPrice());  
		System.out.println(beanUtils.getProperty(productSku, "price")); 
		System.out.println("------------------");
		
	}
	
	
	public void setProperty(ImportModel importModel,Column column) {
/*		if(column.getAttrName().equals("weight")){
			System.out.println();
		}
*/		Object target=importModel.getTarget();
		//初始化对象时，是通过反射现实，可能会发生错误，避免出错都catch处理
		try {
			newInstanceProperties(target, column.getAttrName());
		} catch (Exception e) {
			logger.warn("处理失败数据："+column);
			logger.error("错误信息："+e.getMessage()+">>>"+e);
			setDefaultResult(importModel, column);
			return;
		}
		BeanUtilsBean beanUtils=getBeanUtilsBean(column);
		//通过反射获取对象Id时，可能会发生错误，避免出错都catch处理
		try {
			String tempId=beanUtils.getProperty(target,"id");
			if((!column.isSupportUpdate())&&StringUtils.isNotEmpty(tempId)){
				String tempValue=beanUtils.getProperty(target,column.getAttrName());
				if(!tempValue.equals(column.getValue())){
					logger.warn("本条数据为更新，"+column.getColumnHeader()+"不支持更新操作。"+column);
				}
				importModel.setResult("0");
				return;
			}
		} catch (Exception e) {
			logger.warn("处理失败数据："+column);
			logger.error("错误信息："+e.getMessage()+">>>"+e);
			e.printStackTrace();
		}
		//为处理的对象设置相应值时，可能会发生错误，避免出错都catch处理，出错后设置上默认值
		try {
			beanUtils.setProperty(target, column.getAttrName(), column.getValue());
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				beanUtils.setProperty(target, column.getAttrName(), column.getDefaultValue());
			} catch (Exception e) {
				setDefaultResult(importModel, column);
				return;
			}
		} 
		//当值为空时，设置上默认值
		try {
			String tempValue=beanUtils.getProperty(target, column.getAttrName());
			if(StringUtils.isEmpty(tempValue)){
				setDefaultResult(importModel, column);
			}else{
				importModel.setResult("1");
			}
		} catch (Exception e) {
			logger.warn("处理失败数据："+column);
			logger.error("错误信息："+e.getMessage()+">>>"+e);
			setDefaultResult(importModel, column);
			e.printStackTrace();
		} 
		
	}
	
	public BeanUtilsBean getBeanUtilsBean(final Column column){
		ConvertUtilsBean cub=new ConvertUtilsBean();
		if(StringUtils.isNotEmpty(column.getPattern())){
			cub.register(new Converter(){
				public Object convert(Class arg0, Object arg1) {
					if(arg1!=null){
						SimpleDateFormat df = new SimpleDateFormat(column.getPattern());
						try {
							if(StringUtils.isEmpty(arg1.toString())){
								return null;
							}
							return df.parse(arg1.toString());
						} catch (ParseException e) {
							logger.warn("处理失败数据："+column);
							logger.error("错误信息："+e.getMessage()+">>>"+e);
							e.printStackTrace();
						}
					}
					return null;
				}},Date.class);
		}

		cub.register(new Converter(){
			public Object convert(Class arg0, Object arg1) {
				if(arg1!=null){
					NumberFormat nf=new DecimalFormat(column.getPattern());
					try {
						if(StringUtils.isEmpty(arg1.toString())){
							return null;
						}
						Number number=nf.parse(arg1.toString());
						return BigDecimal.valueOf(number.doubleValue());
					} catch (Exception e) {
						logger.warn("处理失败数据："+column+column.getPattern());
						logger.error("错误信息："+e.getMessage()+">>>"+e);
						e.printStackTrace();
					}
				}
				return null;
			}
		},BigDecimal.class);
		
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
}
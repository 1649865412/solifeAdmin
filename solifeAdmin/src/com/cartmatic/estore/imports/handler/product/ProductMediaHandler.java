package com.cartmatic.estore.imports.handler.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class ProductMediaHandler extends ColumnBasicHandler implements ColumnHandler{
	private Logger logger = Logger.getLogger(ProductMediaHandler.class);
	/**
	 * 媒体路径与描述之间的分隔符
	 */
	private String delimiter;
	
	/**
	 * 更多图片前缀
	 */
	private String imgUrlPrefix;

	public String getImgUrlPrefix() {
		return imgUrlPrefix;
	}

	public void setImgUrlPrefix(String imgUrlPrefix) {
		this.imgUrlPrefix = imgUrlPrefix;
	}

	public void setProperty(ImportModel importModel, Column column) throws Exception {
		Product product=(Product)importModel.getTarget();
		List<String> values=column.getValues();
		Set<ProductMedia> productMedias=product.getProductMedias();
		List<String> productMediaIds = new ArrayList<String>();
		List<String> mediaUrls =  new ArrayList<String>();
		List<String> mediaDescriptions =  new ArrayList<String>();
		List<String> productMediaTypes =  new ArrayList<String>();
		
		for (String value : values) {
			if(StringUtils.isEmpty(value)){
				continue;
			}
			String temp_productMedia[]=value.split(delimiter);
			if(temp_productMedia.length==1)
				temp_productMedia=(String [])ArrayUtils.add(temp_productMedia, "");
			String tempId="0";
			if(StringUtils.isNotEmpty(temp_productMedia[0])){
				StringBuffer tempValue=new StringBuffer(temp_productMedia[0]);
				if(StringUtils.isNotEmpty(imgUrlPrefix)){
					tempValue.insert(0,imgUrlPrefix);
				}
				temp_productMedia[0]=tempValue.toString();
			}
			for (ProductMedia productMedia : productMedias) {
				if(temp_productMedia[0].equals(productMedia.getMediaUrl())&&temp_productMedia[1].equals(productMedia.getMediaDescription())){
					if(!productMediaIds.contains(productMedia.getId().toString())){
						tempId=productMedia.getId().toString();
					}
				}
			}
			productMediaIds.add(tempId);
			mediaUrls.add(temp_productMedia[0]);
			mediaDescriptions.add(temp_productMedia[1]);
			productMediaTypes.add("0");
		}
		importModel.getImportTargetData().put("productMediaIds",productMediaIds.toArray(new String[]{}));
		importModel.getImportTargetData().put("mediaUrls",mediaUrls.toArray(new String[]{}));
		importModel.getImportTargetData().put("mediaDescriptions",mediaDescriptions.toArray(new String[]{}));
		importModel.getImportTargetData().put("productMediaTypes",productMediaTypes.toArray(new String[]{}));
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
}

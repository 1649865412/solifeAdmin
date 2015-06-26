
package com.cartmatic.estore.imports.handler.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import com.cartmatic.estore.catalog.service.ProductAttGroupItemManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class AttributeHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private Logger logger = Logger.getLogger(AttributeHandler.class);
	private ProductAttGroupItemManager productAttGroupItemManager=null;
	private AttributeService attributeService=null;

	
	@SuppressWarnings("unchecked")
	public void setProperty(ImportModel importModel, Column column) throws Exception {
		String attributeCode=column.getAttrName();
		Attribute attribute=attributeService.getAttribute(attributeCode);
		if(attribute==null){
			logger.warn("没有找到相应的自定义属性，code:"+attributeCode);
		}
		BaseAttributeValue baseAttributeValue=null;
		if(attribute!=null){
			if(attribute.getAttributeType().intValue()==1){
				Product product=(Product)importModel.getTarget();
				ProductAttrValue productAttrValue=attributeService.getProductAttributeValue(product.getId(), attribute.getId());
				if(productAttrValue==null){
					ProductAttGroupItem productAttGroupItem=productAttGroupItemManager.getProductAttGroupItemByProductTypeAndAttribute(product.getProductTypeId(), attribute.getId());
					if(productAttGroupItem!=null){
						productAttrValue = new ProductAttrValue();
						productAttrValue.setAttributeDataType(attribute.getAttributeDataType());
						productAttrValue.setAttribute(attribute);
						productAttrValue.setProductAttGroupItem(productAttGroupItem);
					}
				}
				baseAttributeValue=productAttrValue;
			}else if(attribute.getAttributeType().intValue()==5){
				Category category=(Category)importModel.getTarget();
				CategoryAttrValue categoryAttrValue=attributeService.getCategoryAttrValue(category.getId(), attribute.getId());
				if(categoryAttrValue==null){
					categoryAttrValue = new CategoryAttrValue();
					categoryAttrValue.setAttributeDataType(attribute.getAttributeDataType());
					categoryAttrValue.setAttribute(attribute);
				}
				baseAttributeValue=categoryAttrValue;
			}
		}
		String value=null;
		if(column.getValue()!=null){
			value=column.getValue();
		}else if(column.getDefaultValue()!=null){
			value=column.getDefaultValue();
		}
		if(value!=null&&baseAttributeValue!=null){
			baseAttributeValue.setAttributeValue(value);
			List<AttributeValue>  attributeValueList=(List<AttributeValue>)importModel.getImportTargetData().get("attributeValueList");
			if(attributeValueList==null){
				attributeValueList=new ArrayList<AttributeValue>();
				importModel.getImportTargetData().put("attributeValueList",attributeValueList);
			}
			attributeValueList.add(baseAttributeValue);
			importModel.setResult("1");
			return;
		}
		setDefaultResult(importModel, column);
	}


	public void setProductAttGroupItemManager(
			ProductAttGroupItemManager productAttGroupItemManager) {
		this.productAttGroupItemManager = productAttGroupItemManager;
	}


	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}


}

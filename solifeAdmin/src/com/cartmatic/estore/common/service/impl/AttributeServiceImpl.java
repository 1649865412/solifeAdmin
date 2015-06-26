
package com.cartmatic.estore.common.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.attribute.service.AttributeManager;
import com.cartmatic.estore.attribute.service.AttributeValueManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.service.AttributeService;

public class AttributeServiceImpl implements AttributeService {

	private AttributeManager		attributeManager;
	private AttributeValueManager	attributeValueManager;

	public List getAttributes(Short attributeType) {
		// TODO Auto-generated method stub
		List list = attributeManager.getAttributes(attributeType);
		return list;
	}

	public Attribute getAttribute(String attributeCode) {
		// TODO Auto-generated method stub
		Attribute attr = attributeManager.getAttribute(attributeCode);
		return attr;
	}

	public AttributeManager getAttributeManager() {
		return attributeManager;
	}

	public void setAttributeManager(AttributeManager attributeManager) {
		this.attributeManager = attributeManager;
	}

	public void saveOrUpdateAttributeValue(List<AttributeValue> list,
			Object object) {
		// TODO Auto-generated method stub
		Class clazz = object.getClass();
		StringBuffer methodName = new StringBuffer();
		methodName.append("set");
		methodName.append(clazz.getSimpleName());
		
		Class valueClazz = null;
		Method in = null;
		for (AttributeValue value : list) {
			if (valueClazz == null)
				valueClazz = value.getClass();
			try {
				if(in==null){
					Method[] methods = valueClazz.getMethods();
					for(Method m : methods){
						if(methodName.toString().startsWith(m.getName())){
							in = m;
							break;
						}
					}
				}
				in.invoke(value, object);
				attributeValueManager.saveOrUpdate(value);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setAttributeValueManager(
			AttributeValueManager attributeValueManager) {
		this.attributeValueManager = attributeValueManager;
	}

	public BaseAttributeValue getCategroryAttributeValueById(Integer id) {
		// TODO Auto-generated method stub
		CategoryAttrValue returnValue = (CategoryAttrValue) attributeValueManager.getAttributeValueById(id, Constants.MODULE_CATEGORY);
		return returnValue;
	}

	public BaseAttributeValue getContentAttributeValueById(Integer id) {
		// TODO Auto-generated method stub
		ContentAttrValue returnValue = (ContentAttrValue) attributeValueManager.getAttributeValueById(id, Constants.MODULE_CONTENT);
		return returnValue;
	}

	public BaseAttributeValue getCustomerAttributeValueById(Integer id) {
		// TODO Auto-generated method stub
		CustomerAttrValue returnValue = (CustomerAttrValue) attributeValueManager.getAttributeValueById(id, Constants.MODULE_CUSTOMER);
		return returnValue;
	}

	public BaseAttributeValue getProductAttributeValueById(Integer id) {
		// TODO Auto-generated method stub
		ProductAttrValue returnValue = (ProductAttrValue) attributeValueManager.getAttributeValueById(id, Constants.MODULE_PRODUCT);
		return returnValue;
	}


	public Attribute getAttribute(Integer attributeId) {
		// TODO Auto-generated method stub
		Attribute attribute = attributeManager.getById(attributeId);
		return attribute;
	}
	

	public ProductAttrValue getProductAttributeValue(Integer productId,
			Integer attrId) {
		// TODO Auto-generated method stub
		ProductAttrValue pav = attributeValueManager.getProductAttrValue(productId, attrId);
		return pav;
	}

	@Override
	public CategoryAttrValue getCategoryAttrValue(Integer categoryId, Integer attrId) {
		return attributeValueManager.getCategoryAttrValue(categoryId, attrId);
	}

}

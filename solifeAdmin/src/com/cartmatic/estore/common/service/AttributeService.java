package com.cartmatic.estore.common.service;

import java.util.List;

import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;

public interface AttributeService {
	
	/**
	 * 查找某个模块定义的所有属性
	 * @param attributeType
	 *        参考com.cartmatic.estore.attribute.Constants中定义的模块常量
	 * @return
	 */
	public List<Attribute> getAttributes(Short attributeType);
	
	
    /**
     * 通过属性唯一CODE来获取该属性PO
     * @param attributeCode
     * @return
     */
	public Attribute getAttribute(String attributeCode);
	
	/**
	 * 保存/更新自定义属性。<strong>参数list</strong>可以是使用AttributeUtil.getAttributeFromRequest(request)
	 * 获取。<strong>参数object</strong>可是是对应模块的PO实例，比如<strong>Customer、Content、Product、Category、SaleOrder</strong>其中之一,
	 * 否则，该方法将会报错
	 * @param list
	 * @param object
	 */
	public void saveOrUpdateAttributeValue(List<AttributeValue> list, Object object);
	
	public BaseAttributeValue getCustomerAttributeValueById(Integer id);

	public BaseAttributeValue getContentAttributeValueById(Integer id);
	
	public BaseAttributeValue getProductAttributeValueById(Integer id);
	
	public BaseAttributeValue getCategroryAttributeValueById(Integer id);
	
	
	/**
	 * 通过attributeId来获取Attribute实例
	 * @param attributeId
	 * @return
	 */
	public Attribute getAttribute(Integer attributeId);

	/**
	 * 提供给产品模块使用
	 * 返回某个产品某个自定义属性的值
	 * @param productId
	 *        产品ID
	 * @param attrId
	 *        自定义属性的id
	 * @return
	 */
	public ProductAttrValue getProductAttributeValue(Integer productId,Integer attrId);
	

	public CategoryAttrValue getCategoryAttrValue(Integer categoryId,Integer attrId);
}

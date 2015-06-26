
package com.cartmatic.estore.common.helper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.attribute.BaseAttributeValue;
import com.cartmatic.estore.common.model.attribute.CategoryAttrValue;
import com.cartmatic.estore.common.model.attribute.ContentAttrValue;
import com.cartmatic.estore.common.model.attribute.CustomerAttrValue;
import com.cartmatic.estore.common.model.attribute.ProductAttrValue;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.core.util.StringUtil;

/**
 * 
 * @author huangwenmin
 *
 */
public class AttributeUtil {
    private final static AttributeUtil attributeUtil = new AttributeUtil();
	private AttributeService	attributeService;
	private final String		attributeName_prefix		= "cartmaticAttr";

	/**
	 * 防止checkbox没有被选中就不会被提交的情况，默认的在checkbox类型的属性后加上一个hidden，名字以它开头
	 */
	private final String		attributeName_hidden_prefix	= "_cartmaticAttr";

	private final String		attributeName_split			= "--";
	private final String		param_split					= "__";
     
	/**
	 *    
	 * @return
	 */
	public static AttributeUtil getInstance() {
        return attributeUtil;
    }
	/**
	 * <p>
	 * 从request中自动将自定义的属性值封装成对应模块的PO
	 * </p>
	 * <h1>注意：返回的List中已经根据属性所属模块自动转换成对应模块的AttrValue实例</h1>
	 * 
	 * @param request
	 * @param type
	 *            参看Contants类
	 * @return
	 */
	public List<AttributeValue> getAttributeFromRequest(
			HttpServletRequest request) {
		List<AttributeValue> returnList = new ArrayList<AttributeValue>();

		Enumeration en = request.getParameterNames();

		List<String> paraNames = new ArrayList<String>();
		List<String> hiddenParaNames = new ArrayList<String>();

		while (en.hasMoreElements()) {
			String nameTest = (String) en.nextElement();
			if (nameTest.startsWith(attributeName_prefix))
				paraNames.add(nameTest);
			else if (nameTest.startsWith(attributeName_hidden_prefix))
				hiddenParaNames.add(nameTest);
		}

		Iterator it = paraNames.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			String name1 = name.split(attributeName_split)[1];
			/**
			 * 如果为新增，则name1=attributeCode__attributeType__attributeDateType
			 * 如果为修改，则name1=attributeCode__attributeType__attributeDateType__attributeValueId
			 */
			String[] param1 = name1.split(param_split);
			String value = null;
			if (param1[2].equals("1")) {// 多选型
				String[] strTemp = request.getParameterValues(name);
				value = StringUtil.arrayToString(strTemp, ",");
			} else
				value = request.getParameter(name);
			Integer attributeType = Integer.valueOf(param1[1]);
			BaseAttributeValue attrValue = initialize(attributeType, param1);
			Attribute attribute = attributeService.getAttribute(param1[0]);
			attrValue.setAttributeDataType(Integer.valueOf(param1[2]));
			attrValue.setAttributeValue(value);
			attrValue.setAttribute(attribute);
			returnList.add(attrValue);
		}

		// 处理checkbox没有被选中的情况。
		Iterator itHidden = hiddenParaNames.iterator();
		while (itHidden.hasNext()) {
			String name = itHidden.next().toString();

			String temp = name.substring(1, name.length());
			String value = request.getParameter(temp);
			if (value == null) {
				String name1 = name.split(attributeName_split)[1];

				/**
				 * 如果为新增，则name1=attributeCode###attributeType###attributeDateType
				 * 如果为修改，则name1=attributeCode###attributeType###attributeDateType###attributeValueId
				 */
				String[] param1 = name1.split(param_split);

				value = request.getParameter(name);
				Integer attributeType = Integer.valueOf(param1[1]);
				BaseAttributeValue attributeValue = initialize(attributeType,
						param1);
				Attribute attribute = attributeService.getAttribute(param1[0]);
				attributeValue.setAttributeDataType(Integer.valueOf(param1[2]));
				attributeValue.setAttributeValue(value);
				attributeValue.setAttribute(attribute);
				returnList.add(attributeValue);
			}
		}
		return returnList;
	}

	public AttributeService getAttributeService() {
		return attributeService;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	private BaseAttributeValue initialize(Integer attributeType, String[] parame) {
		BaseAttributeValue attrValue = null;
		int length = parame.length;
		if (attributeType.intValue() == Constants.MODULE_CATEGORY.intValue()) {
			if (length != 4) {
				attrValue = new CategoryAttrValue();
			} else {
				attrValue = attributeService.getCategroryAttributeValueById(Integer.valueOf(parame[3]));
			}
		} else if (attributeType.intValue() == Constants.MODULE_CONTENT.intValue()) {
			if (length != 4) {
				attrValue = new ContentAttrValue();
			} else {
				attrValue = attributeService.getContentAttributeValueById(Integer.valueOf(parame[3]));
			}
		} else if (attributeType.intValue() == Constants.MODULE_CUSTOMER.intValue()) {
			if(length != 4){
			    attrValue = new CustomerAttrValue();
			}
			else{
				attrValue = attributeService.getCustomerAttributeValueById(Integer.valueOf(parame[3]));
			}
		} else if (attributeType.intValue() == Constants.MODULE_PRODUCT.intValue()) {
			if(length != 4){
			    attrValue = new ProductAttrValue();
			}
			else{
				attrValue = attributeService.getProductAttributeValueById(Integer.valueOf(parame[3]));
			}
		}
		return attrValue;
	}
}

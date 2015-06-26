
package com.cartmatic.estore.sales.util;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.common.model.sales.PromoRuleParameter;

/**
 * target 1.
 * analyse the string which from web to controller
 * [element:id],[element:kind],[element.type]|[paramId1]:[paramName1]:[paramValue1],[paramId2]:[paramName2]:[paramValue2],
 * (the last ',' is required,if not it will throw exception)(if element.id == -1
 * then the element is new)
 * 
 * 
 * for example:
 * elementString=o417,condition,CartSubtotalCondition|700:SUBTOTAL_AMOUNT:,
 * elementString=o418,condition,CartAnySkuInCartCondition|701:NUMITEMS:,702:NUMITEMS_QUANTIFIER:1,
 * elementString=o419,condition,CartContainsItemsOfCategoryCondition|703:NUMITEMS:,704:CATEGORY_EXCLUDED:1,705:PRODUCT_EXCLUDED:2,706:SKU_EXCLUDED:3,711:CATEGORY_EXCLUDED:1,707:NUMITEMS_QUANTIFIER:1,
 * elementString=e0,eligibility,EveryoneEligibility|
 * elementString=e1,condition,CartAnySkuInCartCondition|p1:NUMITEMS:,p2:NUMITEMS_QUANTIFIER:1,
 * 
 * target 2.
 * construct the string which from controller to web
 * [id in page]~[id in database]
 * the meaning of 'e','p' and 'o' is introduced in [promoRuleForm.js].  please read it first.
 * for example:
 * e5~o23
 * p4~78
 * 
 * @author CartMatic
 * 
 */
public class PromoRuleUtil {
	
	private static final String	SEPARATOR_IN_ELEMENT			= ",";
	private static final String	SEPARATOR_BEW_ELEMENT_AND_PARAM	= "|";
	private static final String	SEPARATOR_IN_PARAM				= ":";
	private static final String	SEPARATOR_BEW_PARAM_AND_PARAM	= ",";
	
	private static final String	SEPARATOR_BEW_IDINPAGE_AND_IDINDB	= "~";
	//get the element bean from web
	public static PromoRuleElement getElement(String _elementString) {
		String elementAttributeString = _elementString.substring(0,
				_elementString.indexOf(SEPARATOR_BEW_ELEMENT_AND_PARAM));
		String[] elementFields = elementAttributeString
				.split(SEPARATOR_IN_ELEMENT);

		PromoRuleElement element = new PromoRuleElement();
		element.setKind(elementFields[1]);
		element.setType(elementFields[2]);
		element.setPromoRuleElementIdString(elementFields[0]);

		return element;
	}
	//get the parameter list from web
	public static List<PromoRuleParameter> getParameters(String _elementString) {
		List<PromoRuleParameter> paramList = new ArrayList<PromoRuleParameter>();
		String paramString = _elementString.substring(_elementString
				.indexOf(SEPARATOR_BEW_ELEMENT_AND_PARAM) + 1, _elementString
				.length());
		if (paramString.trim().length() != 0) {
			String[] params = paramString.split(SEPARATOR_BEW_PARAM_AND_PARAM);
			for (String param : params) {
				String[] paramFields = param.split(SEPARATOR_IN_PARAM);
				if (paramFields.length > 2) {
					paramList.add(new PromoRuleParameter(paramFields[0],
							paramFields[1], paramFields[2], null));
				} else {
					paramList.add(new PromoRuleParameter(paramFields[0],
							paramFields[1], "", null));
				}
			}
		}
		return paramList;
	}
	
	//get the update id format string
	public static String getUpdateFormatString(String idInPage, String idInDB){
		return new StringBuffer().append(idInPage).append(SEPARATOR_BEW_IDINPAGE_AND_IDINDB).append(idInDB).toString();
	}

}

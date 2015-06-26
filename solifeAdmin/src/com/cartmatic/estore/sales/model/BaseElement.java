
package com.cartmatic.estore.sales.model;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.sales.util.PromoDependServicesUtil;

/**
 * 资质、条件、动作的基础类,主要封装了一些基础方法
 * 
 * @author CartMatic
 * 
 */
public class BaseElement {
	private static final Log	logger							= LogFactory
																		.getLog(BaseElement.class);
	public final static String	SKU_EXCLUDED					= "SKU_EXCLUDED";
	public final static String	PRODUCT_EXCLUDED				= "PRODUCT_EXCLUDED";
	public final static String	CATEGORY_EXCLUDED				= "CATEGORY_EXCLUDED";
	public final static String	SEPERATOR_BEW_EXCLUDED_PARAM	= ",";

	/**
	 * 判断指定的SKU是不是被排除
	 * 
	 * @param _params
	 *            Map<String, String> 从数据库中得到的excluded元素
	 * @param _paramSku
	 *            String 指定的Sku id
	 * @return true 如果这是一个被排除的元素
	 */
	public boolean isSkuExcluded(Map<String, String> _params, String _paramSku) {

		if (_params.containsKey(SKU_EXCLUDED)) {
			String paramString = SEPERATOR_BEW_EXCLUDED_PARAM
					+ _params.get(SKU_EXCLUDED) + SEPERATOR_BEW_EXCLUDED_PARAM;

			logger.debug("[SKU_EXCLUDED|" + _params.get(SKU_EXCLUDED)
					+ "]~~(SKU|" + _paramSku + ")");
			if (paramString.indexOf(SEPERATOR_BEW_EXCLUDED_PARAM + _paramSku
					+ SEPERATOR_BEW_EXCLUDED_PARAM) != -1) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 判断指定的Product是不是被排除
	 * 
	 * @param _params
	 *            Map<String, String> 从数据库中得到的excluded元素
	 * @param _paramSku
	 *            String 指定的product id
	 * @return true 如果这是一个被排除的元素
	 */
	public boolean isProductExcluded(Map<String, String> _params,
			String _paramProduct) {

		if (_params.containsKey(PRODUCT_EXCLUDED)) {
			String paramString = SEPERATOR_BEW_EXCLUDED_PARAM
					+ _params.get(PRODUCT_EXCLUDED)
					+ SEPERATOR_BEW_EXCLUDED_PARAM;
			logger.debug("[PRODUCT_EXCLUDED|" + _params.get(PRODUCT_EXCLUDED)
					+ "]~~(PRODUCT|" + _paramProduct + ")");
			if (paramString.indexOf(SEPERATOR_BEW_EXCLUDED_PARAM
					+ _paramProduct + SEPERATOR_BEW_EXCLUDED_PARAM) != -1) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 判断指定的Category是不是被排除
	 * 
	 * @param _params
	 *            Map<String, String> 从数据库中得到的excluded元素
	 * @param _paramSku
	 *            String 指定的Category id
	 * @return true 如果这是一个被排除的元素
	 */
	public boolean isCategoryExcluded(Map<String, String> _params,
			String _paramSku) {
		if (_params.containsKey(CATEGORY_EXCLUDED)) {
			String paramStrings[] = _params.get(CATEGORY_EXCLUDED).split(
					SEPERATOR_BEW_EXCLUDED_PARAM);
			logger.debug("[CATEGORY_EXCLUDED|" + _params.get(CATEGORY_EXCLUDED)
					+ "]~~(SKU|" + _paramSku + ")");
			for (String paramString : paramStrings) {
				if (PromoDependServicesUtil.getProductService()
						.isInCategoryBySku(new Integer(_paramSku),
								new Integer(paramString))) {

					return true;
				}
			}

		}
		return false;
	}
}

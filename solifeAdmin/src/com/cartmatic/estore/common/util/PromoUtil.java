package com.cartmatic.estore.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.core.util.StringUtil;





public class PromoUtil {
	private static final String	SEPERATOR_BEW_SHIPPINGINFO			= ",";
	private static final String	SEPERATOR_BEW_LEVEL_AND_DISCOUNT	= "-";
	private static final String	FLAG_DISCOUNT_PERCENT				= "%";
	
	public static Map<Integer, BigDecimal[]> parseShippingDiscountInfo(
			String _shippingDiscountInfo) {
		Map<Integer, BigDecimal[]> infos = null;
		if (_shippingDiscountInfo != null
				&& !_shippingDiscountInfo.trim().equals("")) {
			infos = new HashMap<Integer, BigDecimal[]>();
			String[] items = _shippingDiscountInfo.substring(1).split(
					SEPERATOR_BEW_SHIPPINGINFO);
			for (String item : items) {
				String[] ld = item.split(SEPERATOR_BEW_LEVEL_AND_DISCOUNT);
				if (infos.containsKey(new Integer(ld[0]))) {
					BigDecimal[] discounts = infos.get(new Integer(ld[0]));
					if (ld[1].indexOf(FLAG_DISCOUNT_PERCENT) != -1) {
						discounts[0] = discounts[0].add(new BigDecimal(ld[1]
								.substring(1)));
					} else {
						discounts[1] = discounts[1].add(new BigDecimal(ld[1]
								.substring(1)));
					}
				} else {
					BigDecimal[] discounts = new BigDecimal[2];
					if (ld[1].indexOf(FLAG_DISCOUNT_PERCENT) != -1) {
						discounts[0] = new BigDecimal(ld[1].substring(1));
						discounts[1] = new BigDecimal("0");
					} else {
						discounts[0] = new BigDecimal("0");
						discounts[1] = new BigDecimal(ld[1].substring(1));
					}
					infos.put(new Integer(ld[0]), discounts);
				}
			}
		}
		return infos;
	}
	
	/**
	 * 检查两个promoInfo中是否有相同的shipmentMethodId
	 * @param info1
	 * @param info2
	 * @return
	 */
	public static boolean hasSameShipmentMethodId(String info1, String info2)
	{
		if (StringUtil.isEmpty(info1) || StringUtil.isEmpty(info2))
		{
			return false;
		}
		String[] items1 = info1.substring(1).split(SEPERATOR_BEW_SHIPPINGINFO);
		String[] items2 = info2.substring(1).split(SEPERATOR_BEW_SHIPPINGINFO);
		for (String item1 : items1) {
			for (String item2 : items2) {
				String ld1 = item1.substring(0, item1.indexOf(SEPERATOR_BEW_LEVEL_AND_DISCOUNT));//.split(SEPERATOR_BEW_LEVEL_AND_DISCOUNT);
				String ld2 = item2.substring(0, item2.indexOf(SEPERATOR_BEW_LEVEL_AND_DISCOUNT));
				if (ld1.equals(ld2))
					return true;
			}
		}
		return false;
	}
}

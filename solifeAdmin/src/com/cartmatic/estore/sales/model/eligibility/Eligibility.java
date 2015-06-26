package com.cartmatic.estore.sales.model.eligibility;

import java.util.Map;

import com.cartmatic.estore.core.model.AppUser;


public interface Eligibility {
	/**
	 * 获得该资质的参数
	 * @return Map<String, String>
	 */
	public abstract Map<String, String> getParams();
	/**
	 * 判断该顾客是否满足该资质
	 * @param _customer AppUser 顾客
	 * @return true 如果该顾客满足该资质
	 */
	public  boolean isMatch(AppUser _customer) ;
}

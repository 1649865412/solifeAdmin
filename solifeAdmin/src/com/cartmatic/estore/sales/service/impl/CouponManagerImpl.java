
package com.cartmatic.estore.sales.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.CouponDao;
import com.cartmatic.estore.sales.model.EmailModel;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.sales.util.GenerateCodeUtil;

/**
 * Manager implementation for Coupon, responsible for business processing, and
 * communicate between web and persistence layer.
 */
public class CouponManagerImpl extends GenericManagerImpl<Coupon> implements
		CouponManager {

	private CouponDao	couponDao	= null;
	private ConfigUtil	configUtil	= ConfigUtil.getInstance();
	private MailEngine	mailEngine;

	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	protected void initManager() {
		dao = couponDao;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	protected void onDelete(Coupon entity) {

	}

	protected void onSave(Coupon entity) {

	}

	public List<Coupon> createCoupons(Integer _couponType, Integer _availableCount,
			int _couponStyle, String _prefix,int _quantity){
		List<Coupon> couponList = new ArrayList<Coupon>();
		for (int i = 0; i < _quantity; i++) {
			Coupon coupon = createCoupon(_couponType, _availableCount, _couponStyle, _prefix);
			couponList.add(coupon);
		}
		return couponList;
	}

	public Coupon createCoupon(Integer _couponType, Integer _availableCount,
			int _couponStyle, String _prefix) {
		Coupon coupon = new Coupon();
		coupon.setStatus(Constants.STATUS_ACTIVE);
		coupon.setRemainedTimes(_availableCount);
		coupon.setIsSent(Constants.FLAG_FALSE);
		coupon.setPromoRuleId(_couponType);
		String no = GenerateCodeUtil
				.generateCouponNo(_couponStyle, _prefix);
		int j = 0;
		while (couponDao.existCoupon(no)) {
			j++;
			if (j > SalesConstants.MAX_GEN_TRY_TIMES) {
				logger.error("CouponNo is repeat. MAX_TRY_NUM["
						+ SalesConstants.MAX_GEN_TRY_TIMES + "]");
				throw new DataIntegrityViolationException(
						"CouponNo is repeat.");
			}
			no = GenerateCodeUtil.generateCouponNo(_couponStyle, _prefix);
		}
		coupon.setCouponNo(no);
		dao.save(coupon);
		return coupon;
	}
	
	public Coupon createCoupon(Integer couponType, Integer availableCount, String couponNo)
	{
		if(couponDao.existCoupon(couponNo))
		{
			return null;		
		}
		Coupon coupon = new Coupon();
		coupon.setStatus(Constants.STATUS_ACTIVE);
		coupon.setRemainedTimes(availableCount);
		coupon.setIsSent(Constants.FLAG_FALSE);
		coupon.setPromoRuleId(couponType);
		coupon.setCouponNo(couponNo);
		dao.save(coupon);
		return coupon;
	}

	public boolean existCoupon(String couponNo) {
		return couponDao.existCoupon(couponNo);
	}

	public Coupon getCouponByNo(String couponNo) {
		return couponDao.getCouponByNo(couponNo);
	}

	public List searchCoupons(SearchCriteria _searchCriteria, String _promoRule) {
		return couponDao.searchCoupons(_searchCriteria, _promoRule);
	}

	public void doSendCoupon(Coupon coupon, EmailModel emailModel) {

		// send mail
		Map model = new HashMap();
		model.put("coupon", coupon);
		model.put("email", emailModel);
		mailEngine.sendSimpleTemplateMail(configUtil.getCouponEmailTemplate(),model, null, null, emailModel.getEmail());
		//save
		coupon.setIsSent(Constants.FLAG_TRUE);
		couponDao.save(coupon);
	}
	
	public Coupon getIdleCoupon(Integer couponTypeId){
		return couponDao.getIdleCoupon(couponTypeId);
	}

}

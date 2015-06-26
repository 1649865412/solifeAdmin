package com.cartmatic.estore.customer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.ValidationSession;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.ValidationSessionDao;
import com.cartmatic.estore.customer.service.ValidationSessionManager;
import com.cartmatic.estore.system.service.AppUserManager;
import com.cartmatic.estore.system.util.TranslateService;


/**
 * Manager implementation for ValidationSession, responsible for business processing, and communicate between web and persistence layer.
 */
public class ValidationSessionManagerImpl extends GenericManagerImpl<ValidationSession> implements ValidationSessionManager {

	private ValidationSessionDao validationSessionDao = null;
	private AppUserManager appUserManager=null;
	

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	/**
	 * @param validationSessionDao
	 *            the validationSessionDao to set
	 */
	public void setValidationSessionDao(ValidationSessionDao validationSessionDao) {
		this.validationSessionDao = validationSessionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = validationSessionDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ValidationSession entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ValidationSession entity) {

	}

	public ValidationSession getByUrlType(String url, Short type) {
		return validationSessionDao.getByUrlType(url, type);
	}

	public ValidationSession doGenerateUrlByEmail(String email, Short type) {
		if(!appUserManager.isEmailExist(email))
	        return null;
			
	        String uuid = UUID.randomUUID().toString();
			
			Calendar calendar=Calendar.getInstance();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			
			String currentTime=sdf.format(calendar.getTime());
			
			String originalText=uuid+currentTime+email;
			//use md5 to genrate a unique uid 
			String uid=TranslateService.md5Translate(originalText);
			
			ValidationSession vs=new ValidationSession();
			//PasswordRecover pr=new PasswordRecover();
			
			vs.setEmail(email);
			//hourInterval 
			int hourInterval=2;
			try{
			    hourInterval= ConfigUtil.getInstance().getValidationSessionTime();
			}catch(Exception ex){
			    hourInterval=2;
			}
			
			//add a time intreval.
			calendar.add(Calendar.HOUR_OF_DAY,hourInterval);
			
			vs.setExpiredDate(calendar.getTime());
			
			vs.setUrl(uid);
			
			vs.setVsType(type);
			
			dao.save(vs);
			
	        return vs;
	}

	public boolean isUrlValid(String email, String url, Short type) {
		return validationSessionDao.isUrlValid(email, url, type);
	}

	public boolean isUrlValid(String url, Short type) {
		return validationSessionDao.isUrlValid(url, type);
	}

}

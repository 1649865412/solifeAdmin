
package com.cartmatic.estore.sales.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.I18nUtil;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.customer.service.ShopPointManager;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.GiftCertificateDao;
import com.cartmatic.estore.sales.service.GiftCertificateManager;
import com.cartmatic.estore.sales.util.GenerateCodeUtil;

/**
 * GiftCertificate Business Delegate (Proxy) implementation to handle
 * communication between web and persistence layer. Implementation of
 * GiftCertificateManager interface. Developer introduced interfaces should be
 * declared here. Won't get overwritten.
 */
public class GiftCertificateManagerImpl extends	GenericManagerImpl<GiftCertificate> implements	GiftCertificateManager {
	private ConfigUtil			configUtil			= ConfigUtil.getInstance();
	private MailEngine			mailEngine;

	private GiftCertificateDao	giftCertificateDao	= null;
	private ShopPointManager shopPointManager=null;
	private ShopPointHistoryManager shopPointHistoryManager=null;
	

	public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
		this.giftCertificateDao = giftCertificateDao;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	protected void initManager() {
		dao = giftCertificateDao;
	}

	protected void onDelete(GiftCertificate entity) {

	}

	protected void onSave(GiftCertificate entity) {

	}

	public GiftCertificate getGiftCertificate(String giftCertificateNo) {
		return giftCertificateDao.getGiftCertificate(giftCertificateNo);
	}

	public List<GiftCertificate> getAllActiveGiftCertificates() {
		return giftCertificateDao.getAllActiveGiftCertificates();
	}

	public void sendGiftCertificate(GiftCertificate gc, String orderNo) {
		if (null != orderNo && !orderNo.equals("")) {
			gc.setOrderNo(orderNo);
		}
		if (Constants.FLAG_TRUE.equals(gc.getIsSentByEmail())) {
			// Sends email at once.
			Map model = new HashMap();
			model.put("giftCertificate", gc);
			mailEngine.sendSimpleTemplateMail(configUtil
					.getGiftCertificateEmailTemplate(), model, null, null, gc
					.getRecipientEmail());
		}
	}

	public String generateGiftCertificateNo() {
		String no = GenerateCodeUtil.generateGiftCertificateNo();
		int j = 0;
		while (giftCertificateDao.existGiftCertificateNo(no)) {
			j++;
			if (j > SalesConstants.MAX_GEN_TRY_TIMES) {
				logger.error("GiftCertificateNo is repeat. MAX_TRY_NUM["
						+ SalesConstants.MAX_GEN_TRY_TIMES + "]");
				throw new DataIntegrityViolationException(
						"GiftCertificateNo is repeat.");
			}
			no = GenerateCodeUtil.generateGiftCertificateNo();
		}
		return no;
	}
	
	/**
	 * 
	 * @param salesOrder 订单号
	 * @param amt 赠送的礼券金额
	 * @return
	 */
	public GiftCertificate createForSalesOrder(SalesOrder salesOrder, BigDecimal amt)
	{
		GiftCertificate entity = new GiftCertificate();
		entity.setExpireTime(getDefaultGiftCertificateExpireTime());
		entity.setStatus(GiftCertificate.STATUS_ACTIVE);		
		entity.setGiftCertAmt(amt);
		entity.setRemainedAmt(amt);
		//entity.setMessage(I18nUtil.getInstance().getMessage("giftCertificateDetail.sendWithSalesorder"));
		entity.setGiftCertificateNo(GenerateCodeUtil.generateGiftCertificateNo(salesOrder.getOrderNo()));
		entity.setPurchaser(ConfigUtil.getInstance().getStore().getName());
		entity.setRecipient(salesOrder.getCustomerFirstname()+" "+ salesOrder.getCustomerLastname());
		entity.setRecipientEmail(salesOrder.getCustomerEmail());
		entity.setIsSentByEmail(Constants.FLAG_TRUE);
		this.save(entity);
		return entity;
	}
	
	
	public GiftCertificate exchangeGiftCertificateByRewardPoints(Customer customer, Integer point)
	{
		GiftCertificate certificate =null;
		ShopPoint shopPoint=shopPointManager.getByCustomerId(customer.getAppuserId());
		if(shopPoint!=null&&shopPoint.getTotal()>=point){
			BigDecimal giftCertAmt=configUtil.getShopPointUseGiftPercent().multiply(new BigDecimal(point));
			
			certificate = new GiftCertificate();
			certificate.setExpireTime(getDefaultGiftCertificateExpireTime());
			certificate.setStatus(GiftCertificate.STATUS_ACTIVE);
			certificate.setGiftCertAmt(giftCertAmt);
			certificate.setRemainedAmt(giftCertAmt);
			certificate.setMessage(I18nUtil.getInstance().getMessage("shopPoint.exchange.giftCertificate.message"));
			certificate.setGiftCertificateNo(generateGiftCertificateNo());
			certificate.setPurchaser(ConfigUtil.getInstance().getStore().getName());
			certificate.setRecipient(customer.getUsername());
			certificate.setRecipientEmail(customer.getEmail());
			certificate.setIsSentByEmail(Constants.FLAG_TRUE);
			this.save(certificate);
			
			//换取礼券
			ShopPointHistory shopPointHistory = new ShopPointHistory();
			shopPointHistory.setShopPointType(ShopPointHistory.SHOPPOINT_TYPE_GIFTCERTIFICATE);
			shopPointHistory.setAmount(point*-1);
			shopPointHistory.setCustomerId(customer.getAppuserId());
			shopPointHistory.setDescription("礼品卡优惠处填写"+certificate.getGiftCertificateNo());
			shopPointHistoryManager.saveShopPointHistoryAndUpdateTotal(shopPointHistory);

			String from=ConfigUtil.getInstance().getStore().getEmailSender();
			//发送积分换礼券邮件
			Map model = new HashMap();
			model.put("giftCertificate", certificate);
//			model.put("salesOrder", salesOrder);
			mailEngine.sendSimpleTemplateMail("sales/giftcertificateMail.vm", model, null, from, certificate.getRecipientEmail());
		}
		return certificate;
	}
	
	
		
	public Date getDefaultGiftCertificateExpireTime()
    {
        int conf = ConfigUtil.getInstance().getGiftCertificateExpireYears();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, new Integer(conf).intValue());
        return cal.getTime();
    }
	
	public void setState(GiftCertificate _giftCertificate){
		GiftCertificate giftCertificate = _giftCertificate;
		Calendar now=Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MINUTE, 0);
		if(giftCertificate.getExpireTime()!=null && now.getTime().compareTo(giftCertificate.getExpireTime()) >= 0){
			giftCertificate.setState(GiftCertificate.SA_STATE_PAST);
			return;
		}
		switch(giftCertificate.getStatus().shortValue()){
			case 1: giftCertificate.setState(GiftCertificate.SA_STATE_ACTIVE);break;
			case 0: giftCertificate.setState(GiftCertificate.SA_STATE_UNDEAL);break;
			default: giftCertificate.setState(GiftCertificate.SA_STATE_UNACTIVE);break;
				
		}
	}

	public short getState(GiftCertificate giftCertificate) 
	{
		if (null == giftCertificate) {
			return GiftCertificate.STATE_INVALID;
		}
		if (giftCertificate.getExpireTime() != null
				&& (new Date()).compareTo(giftCertificate.getExpireTime()) > 0) {
			return GiftCertificate.STATE_EXPIRE;
		}
		if (giftCertificate.getStatus().equals(GiftCertificate.STATUS_UNACTIVE)) {
			return GiftCertificate.STATE_UNACTIVE;
		}
		if (giftCertificate.getStatus().equals(GiftCertificate.STATUS_UNDEAL)) {
			return GiftCertificate.STATE_UNDEAL;
		}

		return GiftCertificate.STATE_AVAILABLE;
	}
	
	public List<GiftCertificate> getBindedGiftCard(Integer customerId)
	{
		return giftCertificateDao.getBindedGiftCard(customerId);
	}

	public void setShopPointManager(ShopPointManager shopPointManager) {
		this.shopPointManager = shopPointManager;
	}

	public void setShopPointHistoryManager(ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
	}

}

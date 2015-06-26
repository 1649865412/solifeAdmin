
package com.cartmatic.estore.common.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.service.GiftCertificateService;
import com.cartmatic.estore.exception.GiftCertificateStateException;
import com.cartmatic.estore.sales.service.GiftCertificateManager;

public class GiftCertificateServiceImpl implements GiftCertificateService {

	private GiftCertificateManager	giftCertificateManager;
	//private OrderProcessFlowManager orderProcessFlowManager = null;
	/**
	 * 正常的礼券号规则
	 */
	private final static Pattern PATTERN_FOR_GC = Pattern.compile("\\d{9}");
	/**
	 * 针对只能应用于订单上的礼券号
	 */
	private final static Pattern PATTERN_FOR_SALESORDER_GC = Pattern.compile("^\\w*-\\d{4}-\\d{6}");
	
	public GiftCertificate createGiftCertificate(ShoppingcartItemGc shoppingcartItemGc) 
	{
		GiftCertificate gc = new GiftCertificate();
		gc.setCreateTime(new Date());
		gc.setGiftCertAmt(shoppingcartItemGc.getGiftCertAmt());
		gc.setRemainedAmt(gc.getGiftCertAmt());
		gc.setPurchaser(shoppingcartItemGc.getPurchaser());
		gc.setRecipient(shoppingcartItemGc.getRecipient());
		gc.setRecipientEmail(shoppingcartItemGc.getRecipientEmail());
		gc.setMessage(shoppingcartItemGc.getMessage());
		gc.setIsSentByEmail(GiftCertificate.SENTBYEMAIL_YES);
		gc.setStatus(GiftCertificate.STATUS_UNDEAL);
		gc.setGiftCertificateNo(giftCertificateManager
				.generateGiftCertificateNo());
		gc.setExpireTime(giftCertificateManager
				.getDefaultGiftCertificateExpireTime());
		gc.setVersion(0);
		giftCertificateManager.save(gc);
		return gc;

	}

	public void doSendGiftCertificate(GiftCertificate giftCertificate,
			String orderNo) {
		// 将状态设为激活
		giftCertificate.setStatus(GiftCertificate.STATUS_ACTIVE);
		if (null != orderNo && !orderNo.equals("")) {
			giftCertificate.setOrderNo(orderNo);
		}
		giftCertificateManager.save(giftCertificate);
		giftCertificateManager.sendGiftCertificate(giftCertificate, orderNo);
	}
	
	/**
     * 产生一个只能用于订单的礼券
     * @param salesOrder
     * @param amt
     * @return
     */
    public GiftCertificate createForSalesOrder(SalesOrder salesOrder, BigDecimal amt)
    {
    	return giftCertificateManager.createForSalesOrder(salesOrder, amt);
    }

	public BigDecimal mockUseGiftCertificate(String giftCertificateNo, BigDecimal decMoney, int shoppingCartItems) 
	{
		GiftCertificate giftCertificate = giftCertificateManager.getGiftCertificate(giftCertificateNo);
		giftCertificate.setShoppingCartItems(shoppingCartItems);
		if (decMoney.compareTo(giftCertificate.getRemainedAmt()) > 0) {
			// 余额不足够支付
			return giftCertificate.getRemainedAmt();
		} else {
			// 余额足够支付
			return decMoney;
		}

	}
	
	public void doUseGiftCertificate(String giftCertificateNo, BigDecimal decMoney, int shoppingCartItems) throws GiftCertificateStateException 
	{
		short state = checkGiftcertificate(giftCertificateNo);
		if (state == GiftCertificate.STATE_AVAILABLE) {
			GiftCertificate giftCertificate = giftCertificateManager.getGiftCertificate(giftCertificateNo);
			giftCertificate.setShoppingCartItems(shoppingCartItems);
			if (decMoney.compareTo(giftCertificate.getRemainedAmt()) > 0) {
				// 余额不足够支付
				throw new GiftCertificateStateException(giftCertificateNo,
						GiftCertificate.STATE_NOT_ENOUGH_REMAINEDAMT);
			} else {
				// 余额足够支付
				giftCertificate.setRemainedAmt(giftCertificate.getRemainedAmt()
						.add(decMoney.negate()));
				giftCertificateManager.save(giftCertificate);
				return;
			}
		} else {
//			throw new GiftCertificateStateException(giftCertificateNo, state);
		}
	}

	public GiftCertificate QueryGiftCertificateByNo(String giftCertificateNo) 
	{
		GiftCertificate giftCertificate = giftCertificateManager.getGiftCertificate(giftCertificateNo);
		short state = giftCertificateManager.getState(giftCertificate);
		if (GiftCertificate.STATE_INVALID == state) {
			giftCertificate = new GiftCertificate();
		}
		giftCertificate.setState(state);
		return giftCertificate;
	}

	public short checkGiftcertificate(String giftCertificateNo) 
	{
/*		if (!PATTERN_FOR_GC.matcher(giftCertificateNo).matches())
		{
			return GiftCertificate.STATE_INVALID;
		}*/
		GiftCertificate giftCertificate = giftCertificateManager.getGiftCertificate(giftCertificateNo);
		short state = giftCertificateManager.getState(giftCertificate);
		if (GiftCertificate.STATE_AVAILABLE.equals(state)) {
			if (giftCertificate.getRemainedAmt().compareTo(new BigDecimal("0")) <= 0) {
				return GiftCertificate.STATE_REMAINEDAMT_IS_ZERO;
			}
		}
		return state;
	}

	public List<GiftCertificate> getBindedGiftCard(Integer customerId)
	{
		return giftCertificateManager.getBindedGiftCard(customerId);
	}

	public GiftCertificate getGiftCertificateByNo(String giftCertificateNo) {
		return giftCertificateManager.getGiftCertificate(giftCertificateNo);
	}
	
	
	public void setGiftCertificateManager(GiftCertificateManager giftCertificateManager) 
	{
		this.giftCertificateManager = giftCertificateManager;
	}
}

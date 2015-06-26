package com.cartmatic.estore.sales.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.core.service.GenericManager;


public interface GiftCertificateManager extends GenericManager<GiftCertificate>
{
	//根据礼券号码获得礼券实体
    public GiftCertificate getGiftCertificate(String giftCertificateNo);
    //获得所有激活状态的礼券
    public List<GiftCertificate> getAllActiveGiftCertificates();
    //发送礼券给用户
    public void sendGiftCertificate(GiftCertificate gc, String orderNo);
    
    /**
     * 产生一个只能用于订单的礼券
     * @param salesOrder
     * @param amt
     * @return
     */
    public GiftCertificate createForSalesOrder(SalesOrder salesOrder, BigDecimal amt);
    /**
     * 产生一个只能用于积分换取礼券
     * @param appUser
     * @param amt
     * @return
     */
    public GiftCertificate exchangeGiftCertificateByRewardPoints(Customer customer, Integer point);
    
    //产生礼券号码
    public String generateGiftCertificateNo();
    //获得默认礼券过期时间
    public Date getDefaultGiftCertificateExpireTime();
    
    public short getState(GiftCertificate giftCertificate);
    
    public List<GiftCertificate> getBindedGiftCard(Integer customerId);
}

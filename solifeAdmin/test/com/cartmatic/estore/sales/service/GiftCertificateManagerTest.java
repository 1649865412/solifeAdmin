package com.cartmatic.estore.sales.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.sales.service.GiftCertificateManager;


public class GiftCertificateManagerTest extends BaseTransactionalTestCase
{
	@Autowired
    private GiftCertificateManager mgr;
	@Autowired
    private CustomerManager customerManager;
    
   /* public void testSendByEmail()
    {
        GiftCertificate gc = new GiftCertificate();
        gc.setGiftCertificateNo("test_0000001");
        gc.setIsSentByEmail(new Short("1"));
        gc.setPurchaser("purchaser");
        gc.setRecipient("recipient");
        gc.setRecipientEmail("jcool@21cn.com");
        gc.setGiftCertAmt(new BigDecimal("100.99"));
        gc.setStatus(new Short("10"));
        mgr.sendGiftCertificate(gc,"orderNo_0001");
        
        assertEquals(gc.getStatus(), Constants.STATUS_ACTIVE);
    }
    
    
    public void testSendByPostMail()
    {
        GiftCertificate gc = new GiftCertificate();
        gc.setGiftCertificateNo("test_0000001");
        gc.setIsSentByEmail(new Short("0"));
        gc.setPurchaser("purchaser");
        gc.setRecipient("recipient");
        gc.setRecipientCity("guang zhou");
        gc.setRecipientAddress("address");
        gc.setRecipientFullname("Ryan Liu");
        gc.setGiftCertAmt(new BigDecimal("99.99"));
        gc.setStatus(new Short("10"));
        mgr.sendGiftCertificate(gc,"orderNo_0001");
        
        assertEquals(gc.getStatus(), Constants.STATUS_NOT_PUBLISHED);
    }*/
    
    @Test
    public void testGiftCertificate(){
    	//Customer customer=customerManager.getById(2528);
    	//GiftCertificate giftCertificate=mgr.exchangeGiftCertificateByRewardPoints(customer, 200);
    	//System.out.println(giftCertificate);
    	GiftCertificate gc = new GiftCertificate();
    	gc.setGiftType(new Short("1"));
    	gc.bindToCustomer(12);    	
    	System.out.println(gc.getExpireTime());
    	
    	System.out.println("1 item balance:"+gc.getRemainedAmt());
    	System.out.println("2 item balance:"+gc.getRemainedAmt());
    	System.out.println("3 item balance:"+gc.getRemainedAmt());
    	System.out.println("14 item balance:"+gc.getRemainedAmt());
    	//System.out.println("1 item balance:"+gc.getBalance(1));
    	gc.setRegisterTime(DateUtil.combineDateStart("2013", "4", "2"));
    	System.out.println("1 item balance:"+gc.getRemainedAmt());
    	System.out.println("2 item balance:"+gc.getRemainedAmt());
    	System.out.println("3 item balance:"+gc.getRemainedAmt());
    	System.out.println("14 item balance:"+gc.getRemainedAmt());
    	
    	
    	gc.setGiftType(new Short("2"));
    	gc.bindToCustomer(12);    	
    	System.out.println(gc.getExpireTime());
    	
    	System.out.println("1 item balance:"+gc.getRemainedAmt());
    	System.out.println("2 item balance:"+gc.getRemainedAmt());
    	System.out.println("3 item balance:"+gc.getRemainedAmt());
    	System.out.println("14 item balance:"+gc.getRemainedAmt());
    	//System.out.println("1 item balance:"+gc.getBalance(1));
    	
    	
    }
    
    
}

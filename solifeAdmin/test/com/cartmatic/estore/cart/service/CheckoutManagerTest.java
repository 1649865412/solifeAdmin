/*
 * create 2006-12-15
 */
package com.cartmatic.estore.cart.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

/**
 * @author Faith Yan
 */
public class CheckoutManagerTest  extends BaseTransactionalTestCase {
	
	@Autowired
    private CheckoutManager checkoutManager;
   
    private Shoppingcart shoppingcart_ret;
    
    private Membership membership_anony;
    
    private Membership membership_login;
    
    private Integer appuserId_annoy;
    private Integer appuserId_login;
    
    private Integer shippingRateId;
        
    private Integer shoppingCartId;
    
    private Customer customer=new Customer();
    
    private void initData(){
        membership_anony = new Membership();
        membership_anony.setMembershipId(new Integer(7));
        membership_anony.setVersion(new Integer(10));
        membership_anony.setMembershipLevel(new Integer(-2));
        membership_anony.setStatus(new Short((short) 1));
        membership_anony.setDeleted(new Short((short) 0));
        
        //checkoutModel_anony.setAnonymous(true);
        //checkoutModel_anony.setCheckoutAnonymous(true);
        //checkoutModel_anony.setNeedBilling(Constants.SalesOrder_NOT_NEED_BILLING);
        
        customer.setAppuserId(new Integer(26));
        customer.setTitle("title");
        customer.setTelephone("22376807");
        customer.setEmail("my@email.com");
        customer.setFirstname("firstname");
        customer.setLastname("lastname");
        customer.setVersion(new Integer(1));
        customer.setDeleted(new Short((short)0));
        //checkoutModel_anony.setPersonalAddress(new Address());
        
        Address address=new Address();
        address.setAppuserId(new Integer(6));
        address.setAddressType(new Short((short)1));
        address.setTitle("title");
        address.setAddress("address");
        address.setFirstname("firstname");
        address.setLastname("lastname");
        address.setTelephone("22376807");
        address.setFax("22376807");
        address.setZip("510600");
        address.setCompanyName("companyName");
        address.setIsDefaultShippingAddress(new Short((short)1));
        address.setCountryName("coname");
        address.setStateName("sname");
        address.setCityName("cityname");
        
        ShippingRate shippingRate=new ShippingRate();
        shippingRate.setShippingRateId(new Integer(24));
        shippingRate.setShippingMethodId(new Integer(228));
        shippingRate.setRegionId(new Integer(986));
        shippingRate.setBaseOn(new Integer(2));
        shippingRate.setIsFlat(new Short((short)1));
        shippingRate.setBasePrice(new BigDecimal(200));
        shippingRate.setVersion(new Integer(1));
        shippingRate.setMaxItem(new Integer(30));
        shippingRate.setItemPerRate(new BigDecimal(10));
        shippingRate.setDeleted(new Short((short)0));
        shippingRate.setDescription("Shipping_Name_12327");        
        
                
        
        

        
        membership_login = new Membership();
        membership_login.setMembershipId(new Integer(13));
        membership_login.setVersion(new Integer(10));
        membership_login.setMembershipLevel(new Integer(6));
        membership_login.setStatus(new Short((short) 1));
        membership_login.setDeleted(new Short((short) 0));
        
        Integer appuserId_annoy=Constants.USERID_ANONYMOUS;
        Integer appuserId_login=new Integer(26);
        
        shippingRateId=new Integer(24);
        
        shoppingCartId = new Integer(12512);
    }
    
    @Test
    public void testDoBeginCheckout() throws Exception{
        this.initData();        
        //checkoutModel_ret=this.checkoutManager.doBeginCheckout(checkoutModel_anony,appuserId_annoy,true,membership_anony,shoppingcart_ret,"");
    }
    @Test
    public void testSaveShipping() throws Exception{
        this.initData();
        //CalculateResult calculateResult=this.checkoutManager.saveShippingMoney(checkoutModel_anony,shoppingCartId,new HashMap(),shippingRateId);
        //assertTrue(calculateResult!=null);
        //assertTrue(calculateResult.getTotalCharge().doubleValue()>0);
        //assertTrue(calculateResult.getStatus()==ShippingConstants.STATUS_UNOVERITEM);
    }
    @Test
    public void testDoCheckShippingPass() throws Exception{
        this.initData();
        //HandleResult handleResult=this.checkoutManager.doCheckCanShipping(shoppingCartId);
        //assertTrue(handleResult.getStatus()==ShippingConstants.STATUS_CONSISTENT);
        
        //HandleResult handleResult2=this.checkoutManager.doCheckCanShipping(new Integer(8035));
        //assertTrue(handleResult2.getStatus()==ShippingConstants.STATUS_UNMATCH);
    }
    @Test
    public void testDoApplyCoupon() throws Exception{
        this.initData();
        String couponNo="1";
//        ValidateCodeModel validateCodeModel=this.checkoutPayment.doApplyCoupon(couponNo);
//        assertTrue(validateCodeModel.getCoupon()==null);
//        assertFalse(validateCodeModel.getErrorKey().equals(""));
//        
//        String couponNo2="nn25130732675618";
//        ValidateCodeModel validateCodeModel2=this.checkoutManager.doApplyCoupon(couponNo2);
//        assertTrue(validateCodeModel2.getCoupon()!=null);
//        assertTrue(validateCodeModel2.getErrorKey()==null);                
    }
    @Test
    public void testDoApplyGiftCertificate() throws Exception{
        this.initData();
//        String gcNo="1";
//        ValidateCodeModel validateCodeModel=this.checkoutManager.doApplyGiftCertificate(gcNo);
//        assertTrue(validateCodeModel!=null);
//        assertTrue(validateCodeModel.getGiftCertificate()==null);
//        assertTrue(validateCodeModel.getErrorKey()!=null);
//        
//        String gcNo2="8430-371711-2128-805931";
//        ValidateCodeModel validateCodeModel2=this.checkoutManager.doApplyGiftCertificate(gcNo2);
//        assertTrue(validateCodeModel2!=null);
//        assertTrue(validateCodeModel2.getGiftCertificate()!=null);
//        assertTrue(validateCodeModel2.getErrorKey()==null);      

//        String gcNo3="5314-024942-6676-877756";
//        ValidateCodeModel validateCodeModel3=this.checkoutManager.doApplyGiftCertificate(gcNo3);
//        assertTrue(validateCodeModel3!=null);
//        assertTrue(validateCodeModel3.getGiftCertificate()!=null);
//        assertTrue(validateCodeModel3.getErrorKey()==null);
//        log.debug("validateCodeModel3.getGiftCertificate().getRemainedAmt()="+validateCodeModel3.getGiftCertificate().getRemainedAmt());
//        assertTrue(validateCodeModel3.getGiftCertificate().getRemainedAmt().setScale(2,BigDecimal.ROUND_HALF_UP).equals(new BigDecimal(210.00).setScale(2,BigDecimal.ROUND_HALF_UP)));
//                
    }
    @Test
    public void testBigDemail() throws Exception{    	
    	BigDecimal b1=new BigDecimal(0);
    	BigDecimal b2=new BigDecimal(0);
    	BigDecimal b3=new BigDecimal(0.0);
    	Assert.assertTrue(b1.equals(b2));
    	Assert.assertTrue(b1.equals(b3));
    }
    
}

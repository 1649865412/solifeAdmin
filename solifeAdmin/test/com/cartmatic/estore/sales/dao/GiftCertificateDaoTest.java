package com.cartmatic.estore.sales.dao;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class GiftCertificateDaoTest extends BaseTransactionalTestCase {
	@Autowired
    private GiftCertificateDao dao = null;
	
    private GiftCertificate giftCertificate = null;
   
    @Test  
    public void testGetGiftCertificateInvalid() throws Exception {
        try {
            giftCertificate = dao.getById(new Integer(0));
            Assert.fail("'badgiftCertificatename' found in database, failing test...");
        } catch (DataAccessException d) {
            Assert.assertTrue(d != null);
        }
    }

    @Test
    public void testAddAndRemoveGiftCertificate() throws Exception {
        giftCertificate = new GiftCertificate();
        giftCertificate.setGiftCertificateNo("giftcertificateno9");
        giftCertificate.setPurchaser("purchaser9");
        giftCertificate.setRecipient("recipient9");
        giftCertificate.setIsSentByEmail((new Short((new Long(9)).shortValue())));
        giftCertificate.setRecipientEmail("recipientemail9");
        giftCertificate.setRecipientFullname("recipientfirstname9");
        giftCertificate.setRecipientAddress("recipientaddress9");
        giftCertificate.setRecipientZip("recip9");
        giftCertificate.setRecipientPhone("recipientphone9");
        giftCertificate.setMessage("message9");
        giftCertificate.setGiftCertAmt(new BigDecimal(9.0 ));
        giftCertificate.setRemainedAmt(new BigDecimal(9.0 ));
        giftCertificate.setExpireTime((new java.util.Date((new Long(9)).longValue())));
        giftCertificate.setStatus((new Short((new Long(9)).shortValue())));
        giftCertificate.setCreateTime((new java.util.Date((new Long(9)).longValue())));
        giftCertificate.setVersion(null);
        giftCertificate.setRecipientContryId(new Integer(9));
        giftCertificate.setRecipientCity("recipientcity9");
        giftCertificate.setRecipientState("recipientstate9");
        giftCertificate.setCreateBy(new Integer(9));
        giftCertificate.setUpdateBy(new Integer(9));
        giftCertificate.setUpdateTime((new java.util.Date((new Long(9)).longValue())));
        dao.save(giftCertificate);
        Assert.assertEquals("giftcertificateno9", giftCertificate.getGiftCertificateNo());
        dao.delete(giftCertificate);

        try {
            giftCertificate = dao.getById(giftCertificate.getGiftCertificateId());
            Assert.fail("saveGiftCertificate didn't throw DataAccessException");
        } catch (DataAccessException d) {
            Assert.assertNotNull(d);
        }
    }
    
    @Test
    public void testGetGiftCertificate() throws Exception {
        giftCertificate = dao.getById(new Integer(1));

        Assert.assertNotNull(giftCertificate);
        Assert.assertEquals("giftcertificateno1", giftCertificate.getGiftCertificateNo());
    }
    
    @Test
    public void testGetGiftCertificateList() throws Exception {
        List results = dao.getAll();

        Assert.assertTrue(results.size() > 0);
         
    }

    @Test
    public void testUpdateGiftCertificate() throws Exception {
        giftCertificate = dao.getById(new Integer(1));
        giftCertificate.setGiftCertificateNo("giftcertificateno8");

        dao.save(giftCertificate);

        Assert.assertEquals("giftcertificateno8", giftCertificate.getGiftCertificateNo());
        
        // verify that violation occurs when adding new giftCertificate
        // with same giftCertificategiftCertificateId
        
        giftCertificate.setVersion(null);

        try {
            dao.save(giftCertificate);
            Assert.fail("saveGiftCertificate didn't throw DataIntegrityViolationException");
        } catch (DataIntegrityViolationException e) {
        	Assert.assertNotNull(e);
            logger.debug("expected exception: " + e.getMessage());
        }
    }

}
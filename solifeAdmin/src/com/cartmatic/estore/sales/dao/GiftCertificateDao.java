package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
/**
 * GiftCertificate Data Access Object (DAO) interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public interface GiftCertificateDao extends GenericDao<GiftCertificate> {
	//根据礼券号码获得礼券实体
    public GiftCertificate getGiftCertificate(String giftCertificateNo);
    //获得所有激活状态的礼券
    public List<GiftCertificate> getAllActiveGiftCertificates();
    //判断是否有重复的giftCertificateNo
    public boolean existGiftCertificateNo(String giftCertificateNo);
    
    public List<GiftCertificate> getBindedGiftCard(Integer customerId);
}

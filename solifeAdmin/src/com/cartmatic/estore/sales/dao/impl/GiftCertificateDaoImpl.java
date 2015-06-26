
package com.cartmatic.estore.sales.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.dao.GiftCertificateDao;

/**
 * GiftCertificate Data Access Object (DAO) implementation. Developer introduced
 * interfaces should be declared here. Won't get overwritten.
 */
public class GiftCertificateDaoImpl extends
		HibernateGenericDaoImpl<GiftCertificate> implements GiftCertificateDao {

	public GiftCertificate getGiftCertificate(String giftCertificateNo) {
		List list = getHibernateTemplate().find(
				"from GiftCertificate gc where gc.giftCertificateNo='"
						+ StringEscapeUtils.escapeSql(giftCertificateNo) + "'");
		if (list.size() == 0) {
			return null;
		}

		return (GiftCertificate) list.get(0);
	}

	public List<GiftCertificate> getAllActiveGiftCertificates() {
		List<GiftCertificate> list = getHibernateTemplate().find(
				"from GiftCertificate gc where gc.status=1");
		return list;
	}
	
	public List<GiftCertificate> getBindedGiftCard(Integer customerId)
	{
		String hql = "from GiftCertificate gc where gc.status=1 and gc.customerId=?";
		List<GiftCertificate> list = this.findByHql(hql, new Object[]{customerId});
		return list;
	}

	public boolean existGiftCertificateNo(String giftCertificateNo) {
		List<GiftCertificate> list = getHibernateTemplate().find(
				"from GiftCertificate gc where gc.giftCertificateNo='"
						+ StringEscapeUtils.escapeSql(giftCertificateNo) + "'");
		return (list.size() != 0) ? true : false;
	}

}
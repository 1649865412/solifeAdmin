package com.cartmatic.estore.attribute.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.attribute.dao.AttributeDao;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

/**
 * Dao implementation for Attribute.
*/
public class AttributeDaoImpl extends HibernateGenericDaoImpl<Attribute> implements AttributeDao {

	public Attribute getAttribute(String attributeCode) {
		// TODO Auto-generated method stub
		String hql = "from Attribute a where a.attributeCode=?";
		Object ob = super.findUnique(hql, attributeCode);
		return (Attribute)ob;
	}

	public List<Attribute> getAttributes(Short attributeType) {
		// TODO Auto-generated method stub
		String hql = "from Attribute a where a.attributeType=?";
		List<Attribute> list = super.findByHql(hql, attributeType);
		return list;
	}

	public Attribute saveOrUpAttribute(Attribute attribute) {
		// TODO Auto-generated method stub
		super.save(attribute);
		return attribute;
	}

	public List getAttributeForPaging(SearchCriteria sc) {
		// TODO Auto-generated method stub
		List list = super.searchByCriteria(sc);
		return list;
	}

	public int bulkUpdateAttrValue(String fromValue, String toValue, Attribute attribute)
	{
		int result = 0;
		String attrValueObje = "";
		if(attribute.getAttributeType().intValue() == Constants.MODULE_CATEGORY.intValue()){
			attrValueObje = "CategoryAttrValue";
		}
		else if(attribute.getAttributeType().intValue() == Constants.MODULE_CONTENT.intValue()){
			attrValueObje = "ContentAttrValue";
		}
		else if(attribute.getAttributeType().intValue() == Constants.MODULE_CUSTOMER.intValue()){
			attrValueObje = "CustomerAttrValue";
		}
		else if(attribute.getAttributeType().intValue() == Constants.MODULE_PRODUCT.intValue()){
			attrValueObje = "ProductAttrValue";
		}

		if (attribute.getAttributeDataType() == Constants.DATETYPE_INT.intValue())
		{
			result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set intValue=? where intValue=? and attributeId=?", 
					new Object[]{Integer.parseInt(toValue), Integer.parseInt(fromValue), attribute.getAttributeId()});
		}
		else if (attribute.getAttributeDataType() == Constants.DATETYPE_FLOAT.intValue())
		{
			result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set decimalValue=? where decimalValue=? and attributeId=?", 
					new Object[]{new BigDecimal(toValue), new BigDecimal(fromValue), attribute.getAttributeId()});
		}
		else if (attribute.getAttributeDataType() == Constants.DATETYPE_BOOLEAN.intValue())
		{
			result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set booleanValue=? where booleanValue=? and attributeId=?", 
					new Object[]{Short.parseShort(toValue), Short.parseShort(fromValue), attribute.getAttributeId()});
		}
		else if (attribute.getAttributeDataType() == Constants.DATETYPE_DATE.intValue())
		{
			try {
				result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set dateValue=? where dateValue=? and attributeId=?", 
						new Object[]{DateUtil.convertStringToDate(toValue), DateUtil.convertStringToDate(fromValue), attribute.getAttributeId()});
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (attribute.getAttributeDataType() == Constants.DATETYPE_LONGTEXT.intValue())
		{
			result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set longTextValue=? where longTextValue=? and attributeId=?", 
					new Object[]{toValue, fromValue, attribute.getAttributeId()});
		}
		else
		{
			result = getHibernateTemplate().bulkUpdate("update "+attrValueObje+" set shortTextValue=? where shortTextValue=? and attributeId=?", 
					new Object[]{toValue, fromValue, attribute.getAttributeId()});
		}
		return result;
	}
}

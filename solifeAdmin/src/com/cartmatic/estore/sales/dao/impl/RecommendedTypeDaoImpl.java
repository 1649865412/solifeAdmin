package com.cartmatic.estore.sales.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.RecommendedTypeDao;
import com.cartmatic.estore.sales.model.RecommendedTypeSearchCriteria;


/**
 * RecommendedType Data Access Object (DAO) implementation.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
*/
public class RecommendedTypeDaoImpl extends HibernateGenericDaoImpl<RecommendedType> implements RecommendedTypeDao {

    public RecommendedType getRecommendedTypeByName(String typeName)
    {
        List list = getHibernateTemplate().find("from RecommendedType vo where vo.typeName='"+typeName+"'");
        if (list.size() > 0)
        {
            return (RecommendedType) list.get(0);
        }
        return null;
    }
    
    public List getRecommendedTypesBySearchCriteria(RecommendedTypeSearchCriteria recommendedTypeSearchCriteria){
    	StringBuffer sb = new StringBuffer(200);
    	sb.append("from RecommendedType where 1=1 ");
    	if(null != recommendedTypeSearchCriteria.getStatus()){
    		sb.append(" and status ="+recommendedTypeSearchCriteria.getStatus().shortValue());
    	}
    	if(null != recommendedTypeSearchCriteria.getIsApplyToCategory()){
    		sb.append(" and isApplyToCategory ="+recommendedTypeSearchCriteria.getIsApplyToCategory().shortValue());
    	}
    	if(null != recommendedTypeSearchCriteria.getIsApplyToProduct()){
    		sb.append(" and isApplyToProduct ="+recommendedTypeSearchCriteria.getIsApplyToProduct().shortValue());
    	}
    	sb.append(" order by recommendedTypeId asc");
    	
    	return getHibernateTemplate().find(sb.toString());
    	
    }
}
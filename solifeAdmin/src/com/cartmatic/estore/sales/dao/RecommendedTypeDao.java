package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.sales.model.RecommendedTypeSearchCriteria;
/**
 * RecommendedType Data Access Object (DAO) interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public interface RecommendedTypeDao extends GenericDao<RecommendedType>  {

    public RecommendedType getRecommendedTypeByName(String typeName);
    
    public List getRecommendedTypesBySearchCriteria(RecommendedTypeSearchCriteria recommendedTypeSearchCriteria);
}

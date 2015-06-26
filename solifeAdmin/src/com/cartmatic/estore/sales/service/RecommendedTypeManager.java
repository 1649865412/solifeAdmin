package com.cartmatic.estore.sales.service;

import java.util.List;

import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.sales.model.RecommendedTypeSearchCriteria;


/**
 * RecommendedType Business Delegate (Proxy) Interface to handle communication between web and
 * persistence layer.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 *
 */
public interface RecommendedTypeManager extends GenericManager<RecommendedType> {
	//根据typeName获得推荐类型
    public RecommendedType getRecommendedTypeByName(String typeName);
    //根据搜索条件获得推荐类型
    public List getRecommendedTypesBySearchCriteria(RecommendedTypeSearchCriteria recommendedTypeSearchCriteria);
}

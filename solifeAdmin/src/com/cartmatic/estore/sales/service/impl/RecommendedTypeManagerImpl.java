package com.cartmatic.estore.sales.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.SalesConstants;
import com.cartmatic.estore.sales.dao.RecommendedTypeDao;
import com.cartmatic.estore.sales.model.RecommendedTypeSearchCriteria;
import com.cartmatic.estore.sales.service.RecommendedTypeManager;


/**
 * RecommendedType Business Delegate (Proxy) implementation to handle communication between web and
 * persistence layer. Implementation of RecommendedTypeManager interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public class RecommendedTypeManagerImpl extends GenericManagerImpl<RecommendedType> implements RecommendedTypeManager 
{
	private RecommendedTypeDao recommendedTypeDao = null;


	public void setRecommendedTypeDao(RecommendedTypeDao recommendedTypeDao) {
		this.recommendedTypeDao = recommendedTypeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = recommendedTypeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(RecommendedType recommendedType) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(RecommendedType recommendedType) {

	}
   
    //重载了save方法
    public void save(RecommendedType recommendedType) 
    {
        //saveI18nContent(recommendedType);
    	if (recommendedType.getId() == null) //is new, set the apply rule
    	{
    		switch( recommendedType.getRuleCode().intValue() ){
    			case SalesConstants.RECOMMENDED_RULE_CODE_ALSO_BUY:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_FALSE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_TRUE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_BUY_COUNT:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_TRUE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_FALSE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_NEW:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_TRUE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_FALSE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_SAME_BRAND:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_FALSE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_TRUE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRICE:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_FALSE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_TRUE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_SIMILAR_PRODUCT:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_FALSE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_TRUE);
    				break;
    			case SalesConstants.RECOMMENDED_RULE_CODE_FAVORIATE:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_TRUE);
        			recommendedType.setIsApplyToProduct(Constants.FLAG_FALSE);
    				break;
    			default:
    				recommendedType.setIsApplyToCategory(Constants.FLAG_FALSE);
    				recommendedType.setIsApplyToProduct(Constants.FLAG_FALSE);
    				break;
    				
    		}
    	}
    	recommendedTypeDao.save(recommendedType);
    }
    public RecommendedType getRecommendedTypeByName(String typeName)
    {
        return recommendedTypeDao.getRecommendedTypeByName(typeName);
    }
    
    public List getRecommendedTypesBySearchCriteria(RecommendedTypeSearchCriteria recommendedTypeSearchCriteria){
    	return recommendedTypeDao.getRecommendedTypesBySearchCriteria(recommendedTypeSearchCriteria);
    }
   
    
}

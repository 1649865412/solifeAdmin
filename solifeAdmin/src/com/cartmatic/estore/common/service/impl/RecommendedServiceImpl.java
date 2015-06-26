package com.cartmatic.estore.common.service.impl;

import java.util.List;

import com.cartmatic.estore.common.service.RecommendedService;
import com.cartmatic.estore.sales.service.AlsoBuyManager;
import com.cartmatic.estore.sales.service.RecommendedProductManager;

public class RecommendedServiceImpl implements RecommendedService
{

    private AlsoBuyManager alsoBuyManager;
    private RecommendedProductManager recommendedProductManager;
       
    public void setAlsoBuyManager(AlsoBuyManager avalue)
    {
        alsoBuyManager = avalue;
    }
    
    public void setRecommendedProductManager(
			RecommendedProductManager recommendedProductManager) {
		this.recommendedProductManager = recommendedProductManager;
	}

	public void saveAlsoBuy(List<Integer> productIds){
    	alsoBuyManager.saveAlsoBuyProducts(productIds);
    }
	
	public void removeRecommendedRelate(Integer productId){
		alsoBuyManager.removeAlsoBuyProductsByProductId(productId);
		recommendedProductManager.removeRecommendedProductsByProductId(productId);
	}
}

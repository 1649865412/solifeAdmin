/*
 * create 2006-8-18
 *
 * 
 */
package com.cartmatic.estore.sales.model;

import java.util.List;

import com.cartmatic.estore.common.model.sales.RecommendedType;

/**
 * @author Ryan
 *就是一个推荐类型加一个productList
 * 
 */
public class RecommendedModel
{
    private List productList;
    private RecommendedType recommendedType;
    public List getProductList()
    {
        return productList;
    }
    public void setProductList(List productList)
    { 
        this.productList = productList;
    }
    public RecommendedType getRecommendedType()
    {
        return recommendedType;
    }
    public void setRecommendedType(RecommendedType recommendedType)
    {
        this.recommendedType = recommendedType;
    }
}

package com.cartmatic.estore.common.service;

import java.util.List;
/**
 * 关联推荐模块接口服务
 * @author CartMatic
 *
 */
public interface RecommendedService
{

    /**
     * Save also buy
     * 本次结帐有那些商品同时购买的.
     * 
     * @param pkeys list of productId.
     */
    public void saveAlsoBuy(List<Integer> productIds);
    /**
     * 移除关联推荐商品的关联，在删除商品前调用
     * @param productId 商品Id
     */
    public void removeRecommendedRelate(Integer productId);
}

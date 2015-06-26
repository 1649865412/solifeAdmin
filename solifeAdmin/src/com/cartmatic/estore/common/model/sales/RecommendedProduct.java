package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.RecommendedProductTbl;

/**
 * 
 * @author CartMatic
 *
 */
public class RecommendedProduct extends RecommendedProductTbl 
{
   
	private static final long	serialVersionUID	= 16018066836729388L;
	
	public static final int STATE_INVALID = 0; //非激活 
	public static final int STATE_DOING = 1;  //发布中
	public static final int STATE_FUTURE = 2; //将要发布
	public static final int STATE_PAST = 3; //已过期
	
	
	private Integer innerSortOrder;
    private String displayStartTime;
    private String displayExpireTime;
	private String state;
	
    
    public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDisplayExpireTime()
    {
        return displayExpireTime;
    }
    public void setDisplayExpireTime(String displayExpireTime)
    {
        this.displayExpireTime = displayExpireTime;
    }
    public String getDisplayStartTime()
    {
        return displayStartTime;
    }
    public void setDisplayStartTime(String displayStartTime)
    {
        this.displayStartTime = displayStartTime;
    }
    public Integer getInnerSortOrder()
    {
        return innerSortOrder;
    }
    public void setInnerSortOrder(Integer innerSortOrder)
    {
        this.innerSortOrder = innerSortOrder;
    }
  	/**
	 * Default Empty Constructor for class RecommendedProduct
	 */
	public RecommendedProduct () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RecommendedProduct
	 */
	public RecommendedProduct (
		 Integer in_recommendedProductId
		) {
		super (
		  in_recommendedProductId
		);
	}

}

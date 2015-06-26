package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.AlsoBuyTbl;

public class AlsoBuy extends AlsoBuyTbl implements Comparable<AlsoBuy>
{
	private static final long	serialVersionUID	= -4443624748861724993L;
	
	private String productName;
    private String alsoProductName;
    
    public String getAlsoProductName()
    {
        return alsoProductName;
    }
    public void setAlsoProductName(String alsoProductName)
    {
        this.alsoProductName = alsoProductName;
    }
    private int alsoBuyCount = 0;

    public int getAlsoBuyCount()
    {
        return alsoBuyCount;
    }
    public void setAlsoBuyCount(int alsoBuyCount)
    {
        this.alsoBuyCount = alsoBuyCount;
    }
  	/**
	 * Default Empty Constructor for class AlsoBuy
	 */
	public AlsoBuy () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AlsoBuy
	 */
	public AlsoBuy (
		 Integer in_alsoBuyId
		) {
		super (
		  in_alsoBuyId
		);
	}
	
	/**
	 * For Desc sort
	 */
	public int compareTo(AlsoBuy o)
	{
	    Integer i = o.getTimes();
	    return i.compareTo(times);
	}

    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }
}

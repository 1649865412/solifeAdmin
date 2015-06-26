package com.cartmatic.estore.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil
{

    public static BigDecimal getBigDecimal(BigDecimal avalue)
    {
        if (avalue == null) return BigDecimal.ZERO;
        return avalue;
    }
    
    public static Integer getInteger(Integer avalue)
    {
        if (avalue == null) return Integer.valueOf(0);
        return avalue;
    }
    
    public static Integer longCompare(Long l1,Long l2)
    {
        if(l1==null&&l2==null){
        	return 0;
        }
        if(l1!=null&&l2==null){
        	return 1;
        }
        if(l1==null&&l2!=null){
        	return -1;
        }
        return l1.compareTo(l2);
    }
    
}

package com.cartmatic.estore.sales.util;

import java.util.Date;
import java.util.Random;

import com.cartmatic.estore.sales.SalesConstants;


public class GenerateCodeUtil
{

    private static Random random = new Random(new Date().getTime());
    
    public static String generateCouponNo(int style, String prefix)
    {
        StringBuffer result = new StringBuffer();

        if (prefix != null)
        {
            result = result.append(prefix);
        }
        if (style == SalesConstants.COUPON_NO_STYLE_NUMBER)
        {
            while (result.length() < 16)
            {
                result = result.append(random.nextInt(10));
            }
        }
        else if (style == SalesConstants.COUPON_NO_STYLE_STRING)
        {

            while (result.length() < 16)
            {
                int ascii = random.nextInt(26) + 97;
                //ascii += 97;
                char aaa = (char) ascii;
                result = result.append(aaa);
            }
        }
        return result.toString();
    }

    public static String generateGiftCertificateNo()
    {
        StringBuffer result = new StringBuffer();
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));        
        return result.toString();
    }
    
    /**
     * 这种gc的格式是 orderNo-xxxx-xxxxxx
     * @param orderNo
     * @return
     */
    public static String generateGiftCertificateNo(String orderNo)
    {
        StringBuffer result = new StringBuffer();        
        result = result.append(orderNo);
        result = result.append("-");
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append("-");
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        result = result.append(random.nextInt(10));
        return result.toString();
    }
}

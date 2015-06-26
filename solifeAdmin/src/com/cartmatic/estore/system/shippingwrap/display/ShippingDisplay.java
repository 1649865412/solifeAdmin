package com.cartmatic.estore.system.shippingwrap.display;

import java.util.List;

import com.cartmatic.estore.common.model.system.Region;

/**
 * Created 2006-6-21--10:29:58
 * @author  fire
 * 
 */
public class ShippingDisplay extends Region{
    
    private List shippingMethods;
    

    /**
     * @return 返回 shippingMethods。
     */
    public List getShippingMethods() {
     return shippingMethods;
    }
    /**
     * @param shippingMethods 要设置的 shippingMethods。
     */
    public void setShippingMethods(List shippingMethods) {
        this.shippingMethods = shippingMethods;
    }
}

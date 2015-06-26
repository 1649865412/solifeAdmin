package com.cartmatic.estore.system.shippingwrap;


import java.util.List;
import java.util.Map;

/**
 * Created 2006-6-1--16:11:12
 * @author  fire
 * 
 */
public class HandleResult {
 
    private List conflictCartItems;          //发生冲突时，保存了发生冲突的 Item，否则为null; value: shoppingcartItem
    private List conflictCartItemIds;          //发生冲突时，保存了发生冲突的 Item，否则为null; value: shoppingcartItemId
    private List shippingRates;              //没有发生冲突时,保存了可选择的运输方式,否则为null  
    
    
    private short status=ShippingConstants.STATUS_UNHANDLED;  //处理状态
    private Map itemShippingRates;// key: shoppingcartItemId , value: shippingRate List
    private Map itemInfoCode;     // key: shoppingcartItemId , value: info code (i18n)
    private String infoCode;      //i18n 
    
    
    

  
    
	public List getConflictCartItemIds() {
		return conflictCartItemIds;
	}
	public void setConflictCartItemIds(List conflictCartItemIds) {
		this.conflictCartItemIds = conflictCartItemIds;
	}
    /**
     * @return 
     */
    public String getInfoCode() {
        return infoCode;
    }
    /**
     * @param infoCode 
     */
    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }
    /**
     * @return 
     */
    public Map getItemInfoCode() {
        return itemInfoCode;
    }
    /**
     * @param itemInfoCode 
     */
    public void setItemInfoCode(Map itemInfoCode) {
        this.itemInfoCode = itemInfoCode;
    }
    /**
     * @return 
     */
    public Map getItemShippingRates() {
        return itemShippingRates;
    }
    /**
     * @param itemShippingRates 
     */
    public void setItemShippingRates(Map itemShippingRates) {
        this.itemShippingRates = itemShippingRates;
    }

    /**
     * @param conflictProducts 
     */
    public void setConflictCartItems(List conflictProducts) {
        this.conflictCartItems = conflictProducts;
    }
    /**
     * @param isConflictive 
     */
    public void setStatus(short status) {
        this.status=status;
    }
    /**
     * @param shippingMethods 
     */
    public void setShippingRates(List shippingMethods) {
        this.shippingRates = shippingMethods;
    }
    public short getStatus(){
        
        return this.status;
    }
    
    public List getShippingRates(){
       return this.shippingRates;
    }
    
    public List getConflictCartItems(){
        
        return this.conflictCartItems;
    }
    
    
    
    

}

package com.cartmatic.estore.system.shippingwrap;

/**
 * Created 2006-6-7--9:55:01
 * @author  fire
 * 
 */
public class ShippingConstants {
    public static final short STATUS_UNHANDLED = 0;   //当shoppingcart没有经过运输费用处理
   // public static final short STATUS_CONFLICTIVE = 1; //各个商品的运输方式发生冲突
    
    
    public static final short STATUS_CONSISTENT = 1;  //各个商品的运输方式没有发生冲突
          //unconflictive  and there are contain valible shippingrates;
    public static final short STATUS_UNMATCH=2;
          //unconflictive  but there are not contain valible shippingrates;
    public static final short STATUS_SPECIAL_CONFLICTIVE=3;
          //conflictive    and there are all the special item in the shoppingcart
    public static final short STATUS_NORMAL_CONFLICTIVE=4;
          //conflictive    and there are conflictive between the normal items and the special items 
    
    
    
    public static final short STATUS_OVERWEIGHT = 5;   //超重，不能计算运输费用
    public static final short STATUS_UNOVERWEIGHT = 6; //重量没有超过限制 
    public static final short STATUS_OVERITEM = 7;     //产品数量过大，不能计算运输费用
    public static final short STATUS_UNOVERITEM = 8;     //产品数量没有超过限制
    
   //计费类型-----------------------------------
    public static final short RATINGTYPE_WEIGHT = 20;     //按重量计费
    public static final short RATINGTYPE_ITEM = 21;       //按数量计费
   //------------------------------------------
    
    
    public static final String CONFLICTIVE_HEADER_CODE="shipping.conflictive_header"; 
    public static final String NOMATCH_HEADER_CODE="shipping.nomatch_header";
    
    
    public static final String CONFLICTIVE_ITEM_CODE="shipping.conflictive_item_code";
    public static final String NOMATCH_ITEM_CODE="shipping.nomatch_item_code";
    
    public static final String OVERWEIGHT_CODE="shipping.overweight";
    public static final String OVERITEM_CODE="shipping.overitem";

}

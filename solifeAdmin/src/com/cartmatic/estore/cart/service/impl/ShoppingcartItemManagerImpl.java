package com.cartmatic.estore.cart.service.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.dao.ShoppingcartItemDao;
import com.cartmatic.estore.cart.service.ShoppingcartItemManager;
import com.cartmatic.estore.catalog.service.AccessoryManager;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemStat;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.catalog.WholesalePrice;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.opensymphony.oscache.util.StringUtil;


/**
 * Manager implementation for ShoppingcartItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShoppingcartItemManagerImpl extends GenericManagerImpl<ShoppingcartItem> implements ShoppingcartItemManager {

	private ShoppingcartItemDao shoppingcartItemDao = null;
	private AccessoryManager accessoryManager = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shoppingcartItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ShoppingcartItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ShoppingcartItem entity) {

	}

	public ShoppingcartItem updateQuantity(ShoppingcartItem item,
			Integer quantity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ShoppingcartItem newShoppingcartItem(ProductSku productsku, Integer quantity, String accessoryIds, short isSaved){
		
		ShoppingcartItem item = new ShoppingcartItem();
		item.setProductSku(productsku);
		item.setAddTime(new Date());
		item.setCreateTime(new Date());
		item.setPrice(productsku.getPrice());
		item.setIsSaved(isSaved);
		item.setSkuType(productsku.getSkuKind());  
		item.setQuantity(quantity);
		item.setItemType(Constants.ITEM_TYPE_PRODUCT);
		if (!StringUtil.isEmpty(accessoryIds))
		{
			String[] ids = accessoryIds.split(",");
			//存贮在shoppingcartItem的内容.
			JSONObject obj = new JSONObject();
			for (String id : ids)
			{
				Accessory accessory = accessoryManager.getById(new Integer(id));
				//TODO 如果有自定义size之类的功能,可以通过request得到?
				String groupName = accessory.getAccessoryGroup().getGroupName();
				obj.put(groupName, accessory.getAccessoryName());
				if (accessory.getPrice() != null)
					obj.put(groupName+"_price", accessory.getPrice().toString());
			}
			item.setAccessories(obj.toString());
		}
        this.updateItemPrice(productsku, item, quantity);
		
		return item;	
	}
	
	
public ShoppingcartItem newShoppingcartItem(ProductSku productsku, Integer quantity, String accessoryIds,String customMade, short isSaved){
		
		ShoppingcartItem item = new ShoppingcartItem();
		item.setProductSku(productsku);
		item.setAddTime(new Date());
		item.setCreateTime(new Date());
		item.setPrice(productsku.getPrice());
		item.setIsSaved(isSaved);
		item.setSkuType(productsku.getSkuKind());  
		item.setQuantity(quantity);
		item.setItemType(Constants.ITEM_TYPE_PRODUCT);
		if (!StringUtil.isEmpty(accessoryIds))
		{
			String[] ids = accessoryIds.split(",");
			//存贮在shoppingcartItem的内容.
			JSONObject obj = new JSONObject();
			for (String id : ids)
			{
				Accessory accessory = accessoryManager.getById(new Integer(id));
				//TODO 如果有自定义size之类的功能,可以通过request得到?
				String groupName = accessory.getAccessoryGroup().getGroupName();
				obj.put(groupName, accessory.getAccessoryName());
				if (accessory.getPrice() != null)
					obj.put(groupName+"_price", accessory.getPrice().toString());
			}
			JSONObject obj_customMade = net.sf.json.JSONObject.fromObject(customMade);
			JSONArray array = obj_customMade.names();
			for (Object name: array){
				obj.put(name, obj_customMade.get(name));
			}
			item.setAccessories(obj.toString());
		}
        this.updateItemPrice(productsku, item, quantity);
		
		return item;	
	}

	/**
	 * 计算item是否达到批发价的条件
	 * @param productsku
	 * @param item
	 * @param quantity
	 */
	public void updateItemPrice(ProductSku productsku, ShoppingcartItem item, Integer quantity ){
		if(productsku.getSalePrice()!=null&&productsku.getSalePrice().doubleValue()!=0.0){
			item.setIsOnSale((short)1);
			item.setDiscountPrice(productsku.getSalePrice());
		}
		else{
			item.setIsOnSale((short)0);
		}
		
		//满足特价的商品，不参与批发价
		if(item.getIsOnSale()!= (short)1)
		{
			Set<WholesalePrice> wholesalePrice = productsku.getWholesalePrices();
			//找出最满足条件的批发的价，当满足批发条件，这该item不参与促销活动
			SortedSet<WholesalePrice> sortPrice = new TreeSet<WholesalePrice>(new Comparator<WholesalePrice>(){
				public int compare(WholesalePrice o1, WholesalePrice o2) {
					// TODO Auto-generated method stub
					Integer q1 = o1.getMinQuantity();
					Integer q2 = o2.getMinQuantity();
					return q1.compareTo(q2);
				}
			});
			sortPrice.addAll(wholesalePrice);
			WholesalePrice w1 = new WholesalePrice();
			w1.setMinQuantity(quantity);
			if(sortPrice.contains(w1)){
				item.setIsWholesale((short)1);
				for(WholesalePrice p:wholesalePrice){
					if(p.getMinQuantity().intValue()==quantity){
						item.setDiscountPrice(p.getPrice());
						break;
					}
				}
			}
			else{
				WholesalePrice w2 = new WholesalePrice();
				w2.setMinQuantity(0);
				if(sortPrice.subSet(w2, w1).size()!=0){
					WholesalePrice who = sortPrice.subSet(w2, w1).last();
					item.setIsWholesale((short)1);
					item.setDiscountPrice(who.getPrice());
				}
				else{
					item.setIsWholesale((short)0);
					item.setDiscountPrice(item.getDiscountPrice() == null ? productsku.getPrice() : item.getDiscountPrice()); 	//当产品非特价和批发价时
//					item.setDiscountPrice(productsku.getPrice());
				}
			}
		}
		
		//最后计算附件的额外价格
		if (!StringUtil.isEmpty(item.getAccessories()))
		{
			JSONObject obj = JSONObject.fromObject(item.getAccessories());
			//obj.get
			JSONArray array = obj.names();
			for (Object name: array)
			{
				String strName = (String) name;
				if (strName.endsWith("_price"))
				{
					BigDecimal price = new BigDecimal((String) obj.get(name));
					item.setPrice(productsku.getPrice().add(price));
					item.setDiscountPrice(item.getDiscountPrice().add(price));
				}				
			}
		}
	}

	public ShoppingcartItem newShoppingcartItem(ShoppingcartItemGc gc) {
		ShoppingcartItem item = new ShoppingcartItem();
		item.setShoppingcartItemGc(gc);
		item.setAddTime(new Date());
		item.setCreateTime(new Date());
		item.setPrice(gc.getGiftCertAmt());
		item.setSkuType((short)0);  
		item.setQuantity(1);
		item.setIsSaved(ShoppingcartItem.ISSAVED_NO);
		item.setItemType(Constants.ITEM_TYPE_GC);
		item.setIsOnSale(ShoppingcartItem.ISONSALE_NO);
		item.setIsWholesale(ShoppingcartItem.ISWHOLESALE_NO);
		gc.setShoppingcartItem(item);
		return item;	
	}

	public ShoppingcartItem getShoppingcartItemByGcId(Integer gcId) {
		// TODO Auto-generated method stub
		if(gcId==null)return null;
		ShoppingcartItem item = shoppingcartItemDao.getItemByGcId(gcId);
		return item;
	}
	
	public List<ShoppingcartItemStat> searchInShoppingcartStat(SearchCriteria sc)
	{
		return shoppingcartItemDao.searchInShoppingcartStat(sc);
	}
	
	public List<ShoppingcartItemStat> searchInSavedStat(SearchCriteria sc)
	{
		return shoppingcartItemDao.searchInSavedStat(sc);
	}
	
	/**
	 * @param shoppingcartItemDao
	 *            the shoppingcartItemDao to set
	 */
	public void setShoppingcartItemDao(ShoppingcartItemDao shoppingcartItemDao) {
		this.shoppingcartItemDao = shoppingcartItemDao;
	}
	public void setAccessoryManager(AccessoryManager avalue)
	{
		accessoryManager = avalue;
	}
}

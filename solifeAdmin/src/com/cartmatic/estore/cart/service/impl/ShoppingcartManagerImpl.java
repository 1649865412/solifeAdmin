
package com.cartmatic.estore.cart.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.CheckoutConstants;
import com.cartmatic.estore.cart.dao.ShoppingcartDao;
import com.cartmatic.estore.cart.service.ShoppingcartItemGcManager;
import com.cartmatic.estore.cart.service.ShoppingcartItemManager;
import com.cartmatic.estore.cart.service.ShoppingcartPromotionManager;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.service.InventoryService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.search.SearchCriteriaBuilder;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 
 * @author huangwm 2008-10-28
 * 
 */

// TODO 购物车遗留任务：商品价格变化时在前台页面进行信息提示，需要整合SystemMessage；此功能放到下一步和界面整合之后。2008/11/11
public class ShoppingcartManagerImpl extends GenericManagerImpl<Shoppingcart>
		implements com.cartmatic.estore.cart.service.ShoppingcartManager {

	private ShoppingcartItemManager			shoppingcartItemManager;

	private ShoppingcartPromotionManager	shoppingcartPromotionManager;

	private PromoService					promoService;

	private ProductService					productService;

	private ShoppingcartDao					shoppingcartDao;

	private InventoryService				inventoryService;
	
	private ShoppingcartItemGcManager shoppingcartItemGcManager;
	
	private CouponManager couponManager;
	
    public void setShoppingcartItemGcManager(
			ShoppingcartItemGcManager shoppingcartItemGcManager) {
		this.shoppingcartItemGcManager = shoppingcartItemGcManager;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setShoppingcartDao(ShoppingcartDao shoppingcartDao) {
		this.shoppingcartDao = shoppingcartDao;
	}

	/*
	 * 删除过期的shoppingCart
	 */
	public void deleteExpirationShoppingcarts() {
		if (logger.isDebugEnabled()) {
			logger.debug("enter clearExpirationShoppingcarts method......");
		}
		//保留多少天的shoppingCart
		int validDays = ConfigUtil.getInstance().getCartValidDays();
		shoppingcartDao.cleanShoppingcartByUpdatetime(DateUtil.changeDate(0, 0, -1*validDays, 0, 0, 0).getTime());
	}

	/*private boolean isExpiration(Shoppingcart shoppingcart, int validDays) {
		if (logger.isDebugEnabled()) {
			logger.debug("enter isExpiration method......");
		}
		Date dateNow = new Date();
		Date expirationDate = DateUtil.changeDate(shoppingcart.getUpdateTime(),
				0, 0, validDays, 0, 0, 0).getTime();
		return dateNow.after(expirationDate);
	}*/

	/**
	 * 叠加购物车产品数量
	 */
	private ShoppingcartItem plusItemNo(Shoppingcart cart,
			ShoppingcartItem newItem, Integer quantity) throws Exception{
		short isSaved = newItem.getIsSaved().shortValue();
		Set<ShoppingcartItem> pset = new HashSet<ShoppingcartItem>();
		if (newItem.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_YES)
			pset = cart.getFavoriteItems();
		else
			pset = cart.getCartItems();
		for (ShoppingcartItem item : pset) {
			if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
			if (item.getProductSku().getProductSkuCode().equals(
					newItem.getProductSku().getProductSkuCode())
					&& ((item.getParent() == null && newItem.getParent() == null) || (item
							.getParent().getProductSku().getProductSkuCode()
							.equals(newItem.getParent().getProductSku()
									.getProductSkuCode())))) {
				if (isSaved == ShoppingcartItem.ISSAVED_YES) {
					item.setQuantity(item.getQuantity() + newItem.getQuantity());
					checkInventory(item, item.getProductSku().getProductSkuCode());
					shoppingcartItemManager.updateItemPrice(item.getProductSku(), item, item.getQuantity());
				} else {
					item.setQuantity(item.getQuantity() + newItem.getQuantity());
					boolean result = checkInventory(item, item.getProductSku().getProductSkuCode());
					if(!result && isSaved == ShoppingcartItem.ISSAVED_NO){
						throw new Exception("Out of stock!");
					}

					shoppingcartItemManager.updateItemPrice(item.getProductSku(), item, item.getQuantity());
				}
				newItem = item;
				break;
			}
		}
		return newItem;
	}

	public Shoppingcart addMultiProductToCart(String cartUuid,
			String parentSku, String[] childrenSkus, Integer quantity, String accessoryIds,
			short isSaved, HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		if (cart == null) {
			Customer customer = (Customer) RequestContext.getCurrentUser();
			cart = this.initShoppingcart(customer);
		}
		ProductSku sku = productService
				.getProductSkuByProductSkuCode(parentSku);
		ShoppingcartItem newCartItem_p = shoppingcartItemManager
				.newShoppingcartItem(sku, quantity, accessoryIds, isSaved);
		newCartItem_p = doAdd(cart, newCartItem_p, quantity);
		for (int i = 0; i < childrenSkus.length; i++) {
			ProductSku sku_p = productService
					.getProductSkuByProductSkuCode(childrenSkus[i]);
			if (sku_p.getIsFictitious()) {
				ShoppingcartItem newCartItem_c = shoppingcartItemManager
						.newShoppingcartItem(sku_p, quantity, accessoryIds, isSaved);
				newCartItem_c.setParent(newCartItem_p);
				newCartItem_c = doAdd(cart, newCartItem_c, 0);
			} else {
				ShoppingcartItem newCartItem_c = shoppingcartItemManager
						.newShoppingcartItem(sku_p, quantity, accessoryIds, isSaved);
				doAdd(cart, newCartItem_c, quantity);
			}
		}

		// refresh shoppingcart
		refreshAndUpdateCart(cart);
		// refresh cookie
		refreshAndUpdateCartCookie(cart, request, response);
		return cart;
	}

	private Shoppingcart addProduct (String cartUuid, String productsku,
			Integer quantity, String accessoryIds,String customMade, short isSaved, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		Customer customer = (Customer) RequestContext.getCurrentUser();
		if (cart == null) {
			cart = this.initShoppingcart(customer);
		}else{
			cart.setIsUsedCoupon((short)0);
			cart.setUsedCouponNo(null);
		}
		if (!RequestContext.isAnonymousUser()) {
			cart.setCustomerId(customer.getAppuserId());
		}
		ProductSku sku = productService
				.getProductSkuByProductSkuCode(productsku);
		ShoppingcartItem newCartItem;
		if(StringUtils.isBlank(customMade)){
			newCartItem = shoppingcartItemManager.newShoppingcartItem(sku, quantity, accessoryIds, isSaved);
		}else{
			newCartItem = shoppingcartItemManager.newShoppingcartItem(sku, quantity, accessoryIds,customMade, isSaved);
		}
		
		doAdd(cart, newCartItem, quantity);
		// refresh shoppingcart
		refreshAndUpdateCart(cart);
		// refresh cookie
		refreshAndUpdateCartCookie(cart, request, response);
		return cart;
	}
	
	@Override
	public Shoppingcart addProductToCart4WS(Shoppingcart cart, ProductSku sku,Integer quantity, String accessoryIds, String customMade)throws Exception {
		ShoppingcartItem newCartItem;
		if(StringUtils.isBlank(customMade)){
			newCartItem = shoppingcartItemManager.newShoppingcartItem(sku, quantity, accessoryIds, ShoppingcartItem.ISSAVED_NO);
		}else{
			newCartItem = shoppingcartItemManager.newShoppingcartItem(sku, quantity, accessoryIds,customMade, ShoppingcartItem.ISSAVED_NO);
		}
		doAdd(cart, newCartItem, quantity);
		// refresh shoppingcart
		refreshAndUpdateCart(cart);
		return cart;
	}

	public Shoppingcart addProductToCart(String cartUuid, String productsku,
			Integer quantity, String accessoryIds, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return addProduct(cartUuid, productsku, quantity, accessoryIds,"", ShoppingcartItem.ISSAVED_NO, request,response);
	}
	
	public Shoppingcart addProductToCart(String cartUuid, String productsku,Integer quantity, String accessoryIds, String customMade,HttpServletRequest request,HttpServletResponse response) throws Exception{
		return addProduct(cartUuid, productsku, quantity, accessoryIds,customMade, ShoppingcartItem.ISSAVED_NO, request,response);
	}
	
	public Shoppingcart addGcToCart(String cartUuid,ShoppingcartItemGc sgc,
			HttpServletRequest request, HttpServletResponse response) {
		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		Customer customer = (Customer) RequestContext.getCurrentUser();
		if (cart == null) {
			cart = this.initShoppingcart(customer);
		}
		if (!RequestContext.isAnonymousUser()) {
			cart.setCustomerId(customer.getAppuserId());
		}
		ShoppingcartItem item = null;
		if(sgc.getShoppingcartItemGcId()!=null){
			item = shoppingcartItemManager.getShoppingcartItemByGcId(sgc.getShoppingcartItemGcId());
			ShoppingcartItemGc gc = shoppingcartItemGcManager.getById(sgc.getShoppingcartItemGcId());
			gc.setGiftCertAmt(sgc.getGiftCertAmt());
			gc.setMessage(sgc.getMessage());
			gc.setPurchaser(sgc.getPurchaser());
			gc.setRecipient(sgc.getRecipient());
			gc.setRecipientEmail(sgc.getRecipientEmail());
			shoppingcartItemGcManager.save(gc);
			item.setPrice(gc.getGiftCertAmt());
		}
		else{
			item = shoppingcartItemManager.newShoppingcartItem(sgc);
			shoppingcartItemGcManager.save(sgc);
		}
		try{
		   doAdd(cart, item, 1);
		}catch(Exception e){
			//永远不会在这里发生异常
		}
		// refresh shoppingcart
		refreshAndUpdateCart(cart);
		// refresh cookie
		refreshAndUpdateCartCookie(cart, request, response);
		return cart;
	}

	public Shoppingcart addProductToFavorite(String cartUuid,
			String productsku, Integer quantity, String accessoryIds, HttpServletRequest request,
			HttpServletResponse response){
		try{
		return addProduct(cartUuid, productsku, quantity, accessoryIds,"", ShoppingcartItem.ISSAVED_YES, request, response);
		}catch(Exception e){
			return null;//添加到收藏夹时永远不会抛出此异常
		}
	}

	private ShoppingcartItem doAdd(Shoppingcart cart, ShoppingcartItem newItem,
			Integer quantity)throws Exception {
		if (newItem.getItemType().equals(Constants.ITEM_TYPE_PRODUCT)) {//增加产品类型的商品
			boolean isExit = false;
			if (newItem.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_YES)
				isExit = isExitsInFavorite(cart, newItem);
			else
				isExit = isExitsInCart(cart, newItem);
			if (isExit) {// 已经存在该商品，则叠加数量
				newItem = plusItemNo(cart, newItem, quantity);
			} else {// 该商品不存在，则直接放入购物车
				boolean result = checkInventory(newItem, newItem.getProductSku().getProductSkuCode());
				if(!result && newItem.getIsSaved().shortValue() == ShoppingcartItem.ISSAVED_NO){
					throw new Exception("Out of stock!");
				}
				newItem.setShoppingcart(cart);
				cart.addShoppingCartItem(newItem);
				shoppingcartItemManager.save(newItem);
			}
		} else if (newItem.getItemType().equals(Constants.ITEM_TYPE_GC)) {//礼券类型的商品
			if(newItem.getShoppingcartItemId()==null||
			   (newItem.getShoppingcart()!=null&&
			   !newItem.getShoppingcartItemId().equals(cart.getShoppingcartId()))){
				newItem.setShoppingcart(cart);
				cart.addShoppingCartItem(newItem);
			}
			shoppingcartItemManager.save(newItem);
		}
		return newItem;
	}

	public Shoppingcart doDeleteItem(String cartUuid,
			String shoppingcartItemId, HttpServletRequest request,
			HttpServletResponse response) {

		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);

		ShoppingcartItem toDeleteItem = shoppingcartItemManager.getById(Integer.valueOf(shoppingcartItemId));

		if (toDeleteItem != null) {
			cart.removeShoppingcartItem(toDeleteItem);
			if (toDeleteItem.getParent() != null)
				toDeleteItem.getParent().getShoppingcartItems().remove(
						toDeleteItem);

			shoppingcartItemManager.deleteById(toDeleteItem.getShoppingcartItemId());
			//removePromotions(cart);
			refreshAndUpdateCart(cart);
			refreshAndUpdateCartCookie(cart, request, response);
		}
		return cart;
	}

	public Shoppingcart doUniteShoppingcarts(String cartDBUuid,
			String cartCookieUuid, HttpServletRequest request,
			HttpServletResponse response) {

		if (cartDBUuid.equals(cartCookieUuid)) {
			Shoppingcart cart = loadShoppingcartByUuid(cartDBUuid);
			refreshAndUpdateCart(cart);
			refreshAndUpdateCartCookie(cart, request, response);
			return cart;
		} else {
			Shoppingcart cartDb = loadShoppingcartByUuid(cartDBUuid);
			Shoppingcart cartCookie = loadShoppingcartByUuid(cartCookieUuid);
			if (cartCookie == null && cartDb != null) {
				refreshAndUpdateCart(cartDb);
				refreshAndUpdateCartCookie(cartDb, request, response);
			} else if (cartCookie != null && cartDb == null) {
				Customer customer = (Customer) RequestContext.getCurrentUser();
				cartCookie.setCustomerId(customer.getAppuserId());
				refreshAndUpdateCart(cartCookie);
				refreshAndUpdateCartCookie(cartCookie, request, response);
			} else if (cartCookie != null & cartDb != null) {// 合并,将匿名购物车中的物品放入到客户原来数据库中的购物车里
				if (cartCookie.getCustomerId() == null) {
					Set<ShoppingcartItem> itemsC = cartCookie.getShoppingCartItems();
					//同一Store的才合并
					if(cartCookie.getStore().getId().intValue()==cartDb.getStore().getId().intValue()){
						for (ShoppingcartItem itemc : itemsC) {
							if (itemc.getParent() != null) {// 子类的产品
								try{
								    doAdd(cartDb, itemc, 0);
								}catch(Exception e){
									continue;
								}
							} else {
								try{
								    doAdd(cartDb, itemc, itemc.getQuantity());
								}catch(Exception e){
									continue;
								}
							}
						}
					}
					itemsC.clear();
					shoppingcartDao.removeShoppingcart(cartCookie);
					this.save(cartDb);
					//shoppingcartDao.saveShoppingcart(cartDb);
				} else {// 此时一定是timeout的情况下才出现，此时直接将登陆用户的购物车返回即可
					refreshAndUpdateCart(cartDb);
					refreshAndUpdateCartCookie(cartDb, request, response);
				}
			}
			return cartDb;
		}
	}

	public SearchCriteriaBuilder getSearchCriteriaBuilder(String filterName) {

		return null;
	}

	@Override
	protected void initManager() {
		// 此处不使用框架泛型DAO类，故不用在此处初始化DAO，原因是shoppingcartDao没有继承新框架中的GenericDao<T>接口
		dao = shoppingcartDao;
	}

	@Override
	protected void onDelete(Shoppingcart entity) {

	}

	@Override
	protected void onSave(Shoppingcart entity) {

	}

	public void doMoveCartItemToFavorite(String cartUuid, Integer itemId,
			HttpServletRequest request, HttpServletResponse response) {

		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		ShoppingcartItem itemc = cart.getItemFromCart(itemId);
		if (itemc == null)  //没有对应的item就结束.
			return ;

		String sku = itemc.getProductSku().getProductSkuCode();
		ShoppingcartItem itemf = cart.getItemFromFavorite(sku);
		
		
		// 该产品已经存在在收藏夹中,此时只要改变收藏夹中该产品的数量，然后在购物车中删除即可
		if (itemf != null  && isSameAccessories(itemf, itemc)) {
			Set<ShoppingcartItem> fItems = itemf.getShoppingcartItems();
			Set<ShoppingcartItem> cItems = itemc.getShoppingcartItems();
			en: for (ShoppingcartItem c : cItems) {// 改变该产品的子产品的数量
				for (ShoppingcartItem f : fItems) {
					if (f.getProductSku().getProductSkuCode().equals(c.getProductSku().getProductSkuCode())) 
					{
						f.setQuantity(f.getQuantity() + c.getQuantity());
						continue en;
					}
				}
				c.setIsSaved((short) 1);
				c.setParent(itemf);
				itemf.getShoppingcartItems().add(c);
				continue en;
			}
			itemf.setQuantity(itemf.getQuantity() + itemc.getQuantity());
			shoppingcartItemManager.save(itemf);

			cart.getShoppingCartItems().remove(itemc);
			itemc.getShoppingcartItems().clear();
			shoppingcartItemManager.deleteById(itemc.getShoppingcartItemId());
		} else {// 该产品没有存在在收藏夹中
			Set<ShoppingcartItem> cs = itemc.getShoppingcartItems();
			for (ShoppingcartItem i : cs) {
				i.setIsSaved((short) 1);
				shoppingcartItemManager.save(i);
			}
			itemc.setIsSaved((short) 1);
			shoppingcartItemManager.save(itemc);
		}

		refreshAndUpdateCart(cart);
		refreshAndUpdateCartCookie(cart, request, response);
	}

	public boolean doMoveFavoriteItemToCart(String cartUuid, Integer itemId,
			HttpServletRequest request, HttpServletResponse response) {
		boolean temp = true;
		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		ShoppingcartItem itemf = cart.getItemFromFavorite(itemId);
		if (itemf == null) //没有对应的item记录,就结束.
			return temp;
		
		String sku = itemf.getProductSku().getProductSkuCode();
		ShoppingcartItem itemc = cart.getItemFromCart(sku);

		
		// 该产品已经存在在购物车中,此时只要改变购物车中该产品的数量，然后在收藏夹中删除即可
   		if (itemc != null && isSameAccessories(itemf, itemc)) {
			Set<ShoppingcartItem> fItems = itemf.getShoppingcartItems();
			Set<ShoppingcartItem> cItems = itemc.getShoppingcartItems();

			en: for (ShoppingcartItem f : fItems) {// 改变该产品的子产品的数量(虚拟产品)
				for (ShoppingcartItem c : cItems) {
					if (c.getProductSku().getProductSkuCode().equals(f.getProductSku().getProductSkuCode())) 
					{
						c.setQuantity(c.getQuantity() + f.getQuantity());
						this.checkInventory(c, c.getProductSku().getProductSkuCode());
						continue en;
					}
				}
				f.setIsSaved((short) 0);
				f.setParent(itemc);
				itemc.getShoppingcartItems().add(f);
				continue en;
			}
			Integer oldQu = itemc.getQuantity();
			itemc.setQuantity(itemc.getQuantity() + itemf.getQuantity());
			temp = this.checkInventory(itemc, sku);
			if (!temp){
				itemc.setQuantity(oldQu);
				return temp;
			}
			
			shoppingcartItemManager.save(itemc);
			itemf.getShoppingcartItems().clear();
			cart.getShoppingCartItems().remove(itemf);
			shoppingcartItemManager.deleteById(itemf.getShoppingcartItemId());

		} else {
			temp = this.checkInventory(itemf, sku);
			if (!temp)
				return temp;

			Set<ShoppingcartItem> cs = itemf.getShoppingcartItems();
			for (ShoppingcartItem i : cs) {
				i.setIsSaved((short) 0);
				shoppingcartItemManager.save(i);
			}
			itemf.setIsSaved((short) 0);
			shoppingcartItemManager.save(itemf);
		}

		refreshAndUpdateCart(cart);
		refreshAndUpdateCartCookie(cart, request, response);
		return temp;
	}

	/**
	 * 初始化一辆新的购物车，并持久化
	 * 
	 * @param customer
	 * @return
	 */
	public Shoppingcart initShoppingcart(Customer customer) {

		Shoppingcart cart = new Shoppingcart();
		if (customer != null) {// 匿名用户浏览时customer可以为null
			cart.setCustomerId(customer.getAppuserId());
		}
		String uuid = UUID.randomUUID().toString();
		cart.setUuid(uuid);
		cart.setIsUsedCoupon((short) 0);
		cart.setCreateTime(new Date(System.currentTimeMillis()));
		cart.setSubtotal(new BigDecimal(0));
		cart.setTotal(new BigDecimal(0));
		cart.setStore(ConfigUtil.getInstance().getStore());
		cart.setGiftCertificateNos(null);
		cart.setShopPoint(null);
		this.save(cart);
		//shoppingcartDao.saveShoppingcart(cart);
		return cart;
	}

	/**
	 * 判断购物车中是否已经存在某个商品
	 * 
	 * @param cart
	 * @param item
	 * @return true 存在 false 不存在
	 */
	private boolean isExitsInCart(Shoppingcart cart, ShoppingcartItem testItem) {
		Set<ShoppingcartItem> cartItems = cart.getCartItems();
		if (cartItems == null)
			return false;
		for (ShoppingcartItem item : cartItems) {
			if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
			ProductSku product = item.getProductSku();
			ShoppingcartItem p = item.getParent();
			if (product.getProductSkuCode().equals(testItem.getProductSku().getProductSkuCode())) 
			{
				//检查相关的子item
				if ((p == null && testItem.getParent() == null)
						|| (p != null && 
							testItem.getParent() != null &&
							p.getProductSku().getProductSkuCode().equals(testItem.getParent().getProductSku().getProductSkuCode())
							))
				{
					//检查附件是否相同
					if (isSameAccessories(item, testItem))
						return true;
				}
			}
		}
		return false;
	}

	private boolean isSameAccessories(ShoppingcartItem item1, ShoppingcartItem item2)
	{
		if ((item1.getAccessories()!= null && item1.getAccessories().equals(item2.getAccessories())) ||
				(item1.getAccessories()== null && item2.getAccessories() == null))
		{
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 判断收藏夹中是否已经存在某个商品
	 * 
	 * @param cart
	 * @param testItem
	 * @return
	 */
	private boolean isExitsInFavorite(Shoppingcart cart,
			ShoppingcartItem testItem) {
		Set<ShoppingcartItem> fItems = cart.getFavoriteItems();
		for (ShoppingcartItem item : fItems) {
			ProductSku product = item.getProductSku();
			ShoppingcartItem p = item.getParent();
			if (product.getProductSkuCode().equals(
					testItem.getProductSku().getProductSkuCode())) {
				if ((p == null && testItem.getParent() == null)
						|| (p != null && testItem.getParent() != null && p
								.getProductSku().getProductSkuCode().equals(
										testItem.getParent().getProductSku()
												.getProductSkuCode())))
				{
					if (isSameAccessories(item, testItem))
						return true;
				}
			}
		}
		return false;
	}

	private void refreshAndUpdateCart(Shoppingcart cart) {
		// TODO 在此处使用计费引擎重新计算购物车中价格
		if (cart != null) {
//			removePromotions(cart);
//			cart.getShoppingCartPromotions().clear();
			this.checkItemWholesaleOrSales(cart);
			// do computer the cart's price
//			cart = promoService.appendPromoInfo(cart, RequestContext.getCurrentUser());
			cart = promoService.appendPromoNInCart(cart, RequestContext.getCurrentUser());
			// save
			//this.shoppingcartDao.saveShoppingcart(cart);
			if(cart.getShoppingCartPromotions() != null){
				this.shoppingcartPromotionManager.saveOrUpdateAll(cart.getShoppingCartPromotions());
			}
			this.save(cart);
		}
	}

	private void refreshAndUpdateCartCookie(Shoppingcart cart,
			HttpServletRequest request, HttpServletResponse response) {
		if (cart != null) {
			RequestUtil.setCookie(
							response,
							com.cartmatic.estore.cart.CheckoutConstants.SHOPPINGCART_COOKIE,
							cart.getUuid(), request.getContextPath());
			RequestUtil.setCookie(
							response,
							com.cartmatic.estore.cart.CheckoutConstants.SHOPPINGCART_PRICE_COOKIE,
							cart.getTotal().toString(), request.getContextPath());
			RequestUtil.setCookie(
							response,
							com.cartmatic.estore.cart.CheckoutConstants.C_ITEMCOUNT_COOKIE,
							String.valueOf(cart.getBuyNowItemsCount()), request.getContextPath());
			RequestUtil.setCookie(
							response,
							com.cartmatic.estore.cart.CheckoutConstants.F_ITEMCOUNT_COOKIE,
							String.valueOf(cart.getBuyLaterItemsCount()), request.getContextPath());
			RequestUtil.setCookie(
							response,
							com.cartmatic.estore.cart.CheckoutConstants.SHOPPINGCAT_ITEMCOUT_COOKIE,
							String.valueOf(cart.getItemsCount()), request.getContextPath());
		}

	}

	public void setShoppingcartItemManager(
			ShoppingcartItemManager shoppingcartItemManager) {
		this.shoppingcartItemManager = shoppingcartItemManager;
	}

	public Shoppingcart loadShoppingcartByUuid(String uuid) {
        if(uuid==null||uuid.equals(""))return null;
		Shoppingcart cart = this.shoppingcartDao.getShoppingcartByUuid(uuid);
		return cart;
	}

	// TODO 此方法虽然单一产品已经通过测试，但是当真正改变商品时，还未测试，产品模块做好之后，需要进行测试
	public boolean updateShoppingcartItem(String itemId, String skuNew,
			Integer newQuantity, HttpServletRequest request,
			HttpServletResponse response) {
		boolean temp = true;
		ShoppingcartItem item = shoppingcartItemManager.getById(Integer
				.valueOf(itemId));
		String uuid = RequestUtil.getCookie(request,
				CheckoutConstants.SHOPPINGCART_COOKIE).getValue();
		Shoppingcart cart = this.loadShoppingcartByUuid(uuid);
		boolean isSaved = item.getIsSaved() == 1 ? true : false;
		if (item.getProductSku().getProductSkuCode().equals(skuNew)) {// 更新数量
			Integer oldQu = item.getQuantity();
			item.setQuantity(newQuantity);
			temp = this.checkInventory(item, item.getProductSku()
					.getProductSkuCode());
			if (!temp){
				item.setQuantity(oldQu);
				return temp;
			}

			if (item.getShoppingcartItems() != null) {
				for (ShoppingcartItem is : item.getShoppingcartItems()) {
					is.setQuantity(item.getQuantity());
				}
			}
			shoppingcartItemManager.updateItemPrice(item.getProductSku(), item, item.getQuantity());
			//removePromotions(cart);
			refreshAndUpdateCart(cart);
			refreshAndUpdateCartCookie(cart, request, response);
			return temp;
		} else {// TODO 更换新的商品了。此种情况为测试，需要shoppingart新的UI出来之后再进行测试、设计、修改。
			ProductSku productSku = productService
					.getProductSkuByProductSkuCode(skuNew);
			item.setProductSku(productSku);
			item.setQuantity(newQuantity);
			if (item.getShoppingcartItems() != null) {
				for (ShoppingcartItem is : item.getShoppingcartItems()) {
					is.setQuantity(item.getQuantity());
				}
			}
			shoppingcartItemManager.updateItemPrice(item.getProductSku(), item, newQuantity);
			//removePromotions(cart);
			refreshAndUpdateCart(cart);
			refreshAndUpdateCartCookie(cart, request, response);
			return temp;
		}
	}

	public Shoppingcart doClearFavorite(String cartUuid,
			HttpServletRequest request, HttpServletResponse response) {

		Shoppingcart cart = doClear(cartUuid, true, request, response);
		return cart;
	}

	public Shoppingcart doClearShoppingcart(String cartUuid,HttpServletRequest request, HttpServletResponse response) {
		Shoppingcart cart = doClear(cartUuid, false, request, response);
		return cart;
	}

	private Shoppingcart doClear(String cartUuid, boolean isSaved,HttpServletRequest request, HttpServletResponse response) {
		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		Set<ShoppingcartItem> items = new HashSet<ShoppingcartItem>();
		if (!isSaved) {
			items.addAll(cart.getCartItems());
			cart.setTotal(new BigDecimal(0));
			cart.setSubtotal(new BigDecimal(0));
			cart.setGainedPoint(0);
			cart.setCartDiscountAmount(new BigDecimal(0));
			cart.setIsUsedCoupon(Shoppingcart.ISUSECOUPON_NO);
			cart.setUsedCouponNo("");
			//this.removePromotions(cart);
		} else {
			items.addAll(cart.getFavoriteItems());
		}
		for (ShoppingcartItem item : items) {
			cart.removeShoppingcartItem(item);
			item.setShoppingcart(null);
			this.shoppingcartItemManager.deleteById(item.getShoppingcartItemId());
		}
		refreshAndUpdateCart(cart);
		refreshAndUpdateCartCookie(cart, request, response);
		return cart;
	}

	public Shoppingcart refreshCart(String cartUuid,
			HttpServletRequest request, HttpServletResponse response) {

		Shoppingcart cart = loadShoppingcartByUuid(cartUuid);
		refreshAndUpdateCart(cart);
		refreshAndUpdateCartCookie(cart, request, response);
		return cart;
	}

	public void setPromoService(PromoService promoService) {
		this.promoService = promoService;
	}

	private void removePromotions(Shoppingcart cart) {
		if(cart.getIsUsedCoupon() != null && cart.getIsUsedCoupon() == Shoppingcart.ISUSECOUPON_YES){		//当购物车内使用coupon优惠劵时，去除该shoppingcart_promotion
			ShoppingcartPromotion promoRemove = null;
			for(ShoppingcartPromotion promo : cart.getShoppingCartPromotions()){
				if(PromoRule.PROMOTION_TYPE_COUPONPROMOTION.equals(promo.getType().trim())){
					promoRemove = promo;
					this.shoppingcartPromotionManager.deleteById(promo.getId());
					break;
				}
			}
			if(promoRemove != null){
				cart.getShoppingCartPromotions().remove(promoRemove);
			}
		}else{																																											//当购物车内 未 使用coupon优惠劵时，去除所有shoppingcart_promotion
			for(ShoppingcartPromotion promo : cart.getShoppingCartPromotions()){
				this.shoppingcartPromotionManager.deleteById(promo.getId());
			}
			cart.getShoppingCartPromotions().clear();
		}
		
	}

	/**
	 * 移除购物车内的所有促销信息
	 * @param cart
	 */
	public void doNotUsePromo(Shoppingcart cart){
		if(cart != null){
			//还原购物车内的商品的discountPrice、itemDiscountAmount
			for(ShoppingcartItem item : cart.getShoppingCartItems()){
				item.setDiscountPrice(item.getPrice());
				item.setItemDiscountAmount(null);
			}
			for(ShoppingcartPromotion promo : cart.getShoppingCartPromotions()){
				this.shoppingcartPromotionManager.deleteById(promo.getId());
			}
//			cart.setIsUsedCoupon((short)0);
//			cart.setUsedCouponNo(null);
			cart.notUseCoupon();
			cart.setCartDiscountAmount(BigDecimal.ZERO);
			cart.getShoppingCartPromotions().clear();
			this.save(cart);
		}
	}

	public short doUseCoupon(String no, String uuid) {
		// TODO Auto-generated method stub
		short r = promoService.checkCoupon(no);
		if (r == Coupon.STATE_VALID) {
			Shoppingcart cart = loadShoppingcartByUuid(uuid);
			String noPrev = cart.getUsedCouponNo();
			if(noPrev != null){
				Coupon couponPrev = couponManager.getCouponByNo(noPrev);
				Coupon couponNow = couponManager.getCouponByNo(no);
				if(couponPrev.getPromoRuleId().equals(couponNow.getPromoRuleId())){
					r = Coupon.STATE_SAME_PRULE;
					for(ShoppingcartPromotion promo : cart.getShoppingCartPromotions()){
						if(promo.getPromoRuleId().equals(couponNow.getPromoRuleId())){
							promo.setUsedCouponNo(no);
							this.shoppingcartPromotionManager.save(promo);
							break;
						}
					}
				}
			}
			cart.setIsUsedCoupon(Shoppingcart.ISUSECOUPON_YES);
			cart.setUsedCouponNo(no);
			this.save(cart);
			//shoppingcartDao.saveShoppingcart(cart);
		}
		return r;
	}

	/**
	 * 购物车里不使用优惠劵=
	 */
	public void doNotUseCoupon(String uuid) {
		Shoppingcart cart = loadShoppingcartByUuid(uuid);
		if(cart != null){
			cart.notUseCoupon();
			ShoppingcartPromotion promoRemove = null;
			for(ShoppingcartPromotion promo : cart.getShoppingCartPromotions()){
				if(PromoRule.PROMOTION_TYPE_COUPONPROMOTION.equals(promo.getType().trim())){
					promoRemove = promo;
					this.shoppingcartPromotionManager.deleteById(promo.getId());
					break;
				}
			}
			if(promoRemove != null){
				cart.getShoppingCartPromotions().remove(promoRemove);
			}
			//还原购物车内的商品的discountPrice、itemDiscountAmount
			for(ShoppingcartItem item : cart.getShoppingCartItems()){
				item.setDiscountPrice(item.getPrice());
				item.setItemDiscountAmount(null);
			}
			this.save(cart);
		}
	}

	/**
	 * 检查Item满足批发价或者特价 特价优先
	 * 
	 * @param cart
	 */
	private void checkItemWholesaleOrSales(Shoppingcart cart) {
		for (ShoppingcartItem item : cart.getShoppingCartItems()) {
			if(!item.getItemType().equals(Constants.ITEM_TYPE_PRODUCT))continue;
			shoppingcartItemManager.updateItemPrice(item.getProductSku(), item, item.getQuantity());
		}
	}

	/**
	 * 检查库存
	 * 
	 * @param skuCode
	 * @return 返回“ok”，表示库存正常，可以进行下一步操作，否则不行
	 */
	private boolean checkInventory(ShoppingcartItem item, String skuCode) {
		
		boolean temp = true;
		int qu = item.getQuantity();
		short type = inventoryService.checkInventoryInCart(skuCode, qu);
		if (type == 2 || type == 3) {
			temp = false;
		}
			//temp = CheckoutConstants.CANNOTMOVE_C_NOINVENTORY_NOMORE+":"+ item.getProductSku().getProduct().getProductName();
		//} else if (type == 3) {
			//temp = CheckoutConstants.CANNOTMOVE_C_NOINVENTORY+":"+ item.getProductSku().getProduct().getProductName();
		//}
		return temp;
	}

	public boolean checkItemInventory(ShoppingcartItem item, String skuCode) {
		return checkInventory(item, skuCode);
	}

	public boolean doCheckCartOutOfStock(Shoppingcart cart) {
		// TODO Auto-generated method stub
		if(cart == null){
			return false;
		}
		else{
			for(ShoppingcartItem item:cart.getCartItems()){
				if(item.getItemType().shortValue()!=Constants.ITEM_TYPE_PRODUCT)
					continue;
				if(!checkInventory(item, item.getProductSku().getProductSkuCode())){
					return true;
				}
			}
		}
		return false;
	}

	public ShoppingcartPromotionManager getShoppingcartPromotionManager() {
		return shoppingcartPromotionManager;
	}

	public void setShoppingcartPromotionManager(ShoppingcartPromotionManager shoppingcartPromotionManager) {
		this.shoppingcartPromotionManager = shoppingcartPromotionManager;
	}

	public CouponManager getCouponManager() {
		return couponManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}


}
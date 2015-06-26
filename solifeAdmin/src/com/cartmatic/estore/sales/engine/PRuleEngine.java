
package com.cartmatic.estore.sales.engine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.util.PromoUtil;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.engine.handler.ActionHandler;
import com.cartmatic.estore.sales.engine.handler.ConditionMaintainHandler;
import com.cartmatic.estore.sales.engine.handler.EligibilityHandler;
import com.cartmatic.estore.sales.model.condition.AlwaysTrueCondition;
import com.cartmatic.estore.sales.model.condition.Condition;

/**
 * RuleEngine organize the EligibilityManager,ConditionMaintainManager and
 * ActionManager, let them run as a system
 * 
 * @author CartMatic
 * 
 */
public class PRuleEngine {
	private static final Log	logger	= LogFactory.getLog(PRuleEngine.class);
	private Vector<PRule>		prules	= null;
	
	private final int priceScale = 2;  //价格精度
	private final int divideScale = 5;  //除法精度
	private final int roundingMode = BigDecimal.ROUND_HALF_UP; 

	public PRuleEngine(Vector<PRule> _prules) {
		prules = _prules;
	}

	/**
	 * 初始化Shoppingcart
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @return Shoppingcart
	 */
	public Shoppingcart init(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		cart.setCartDiscountAmount(cart.getCartDiscountAmount() == null ? new BigDecimal("0") : cart.getCartDiscountAmount());
		cart.setGainedCouponTypeId(null);
		cart.setGainedPoint(0);
		cart.setHasDiscountOfMoney(false);
		cart.setHasDiscountOfPoint(false);
		cart.setShippingDiscountInfo("");
		cart.setSubtotal(new BigDecimal("0"));
		cart.setTotal(new BigDecimal("0"));
		// 构建shoppingcartItemsForCalculation
		cart.clearShoppingCartItemsForCalculation();
		for (ShoppingcartItem o : cart.getShoppingCartItems()) {
			if (o.getIsSaved() == ShoppingcartItem.ISSAVED_NO) {
				cart.addShoppingCartItemForCalculation(o);
			}
		}
		
		// 构建shoppingcartItemsForPromo
		cart.clearShoppingCartItemsForPromo();
		for (ShoppingcartItem o : cart.getShoppingCartItemsForCalculation()) {
			if (Constants.ITEM_TYPE_PRODUCT.equals(o.getItemType())) {
				cart.addShoppingCartItemForPromo(o);
			}
		}
		// 构建shoppingcartItemsForPromoInCondition
		cart.clearShoppingCartItemsForPromoInCondition();
		for (ShoppingcartItem o : cart.getShoppingCartItemsForPromo()) {
			if (Constants.ITEM_TYPE_PRODUCT.equals(o.getItemType())) {
				cart.addShoppingCartItemForPromoInCondition(o);
			}
		}
		// 构建shoppingcartItemsForPromoInAction
		cart.clearShoppingCartItemsForPromoInAction();
		for (ShoppingcartItem o : cart.getShoppingCartItemsForPromo()) {
			if (Constants.ITEM_TYPE_PRODUCT.equals(o.getItemType())
					&& o.getIsOnSale() != ShoppingcartItem.ISONSALE_YES
					&& o.getIsWholesale() != ShoppingcartItem.ISWHOLESALE_YES) {
				cart.addShoppingCartItemForPromoInAction(o);
			}
		}
		for (ShoppingcartItem o : cart.getShoppingCartItemsForCalculation()) {
			o.setCatalogConditionRunInAllFlagRAM(true);
			o.setCatalogConditionRunInAnyFlagRAM(false);
			o.setCatalogConditonFlagRAM(false);
			o.setHasDiscount(false);
			o.setItemDiscountAmount(new BigDecimal("0"));
			if (o.getIsOnSale() != ShoppingcartItem.ISONSALE_YES
					&& o.getIsWholesale() != ShoppingcartItem.ISWHOLESALE_YES) {
//				o.setDiscountPrice(o.getPrice());
				o.setDiscountPrice(o.getDiscountPrice() == null ? o.getPrice() : o.getDiscountPrice());
			}
			o.setDiscountQuantity(o.getQuantity());
			o.setTotal(o.getDiscountPrice().multiply(
					new BigDecimal(o.getDiscountQuantity())));
			cart.setSubtotal(cart.getSubtotal().add(o.getTotal()));
			cart.setTotal(cart.getSubtotal());

		}

		return cart;
	}

	/**
	 * 在所有规则运行后做总结计算的工作
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @return Shoppingcart
	 */
	public Shoppingcart summary(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		BigDecimal itemsTotal = new BigDecimal("0");
		for (ShoppingcartItem o : cart.getShoppingCartItemsForCalculation()) {
			o.setTotal(o.getDiscountPrice().multiply(
					new BigDecimal(o.getDiscountQuantity())));
			o.setItemDiscountAmount(o.getPrice().subtract(o.getDiscountPrice()).multiply(
					new BigDecimal(o.getDiscountQuantity())));
			itemsTotal = itemsTotal.add(o.getTotal());
		}
		cart.setSubtotal(itemsTotal);	// subTotal = itemsTotal
		if (cart.getCartDiscountAmount().compareTo(itemsTotal) != 1) {		//cartDiscountAmount <= itemsTotal
			cart
					.setTotal(itemsTotal.add(cart.getCartDiscountAmount()
							.negate()));		// total = itemsTotal - cartDiscountAmount
		} else {
			cart.setTotal(itemsTotal);
		}

		return cart;
	}

	/**
	 * 对shoppingcart的结果进行格式化的处理
	 * 
	 * @param _cart
	 * @return
	 */
	public Shoppingcart format(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		
		for (ShoppingcartItem o : cart.getShoppingCartItemsForCalculation()) {
			o.setPrice(o.getPrice().setScale(priceScale, roundingMode));
			o.setDiscountPrice(o.getDiscountPrice().setScale(priceScale,
					roundingMode));
			o.setTotal(o.getTotal().setScale(priceScale, roundingMode));
			o.setItemDiscountAmount(o.getItemDiscountAmount().setScale(priceScale,
					roundingMode));
		}
		cart.setSubtotal(cart.getSubtotal().setScale(priceScale,
				roundingMode));
		cart.setTotal(cart.getTotal().setScale(priceScale, roundingMode));
		cart.setCartDiscountAmount(cart.getCartDiscountAmount().setScale(priceScale,
				roundingMode));
		return cart;
	}

	/**
	 * 最后对shoppingcart进行的处理
	 */
	public Shoppingcart finish(Shoppingcart _cart) {
		return format(_cart);
	}

	/**
	 * 运行规则
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @param _customer
	 *            AppUser
	 * @return Shoppingcart
	 * @throws Exception
	 */
	public Shoppingcart run(Shoppingcart _cart, AppUser _customer)
			throws Exception {
		logger.debug("runEngine: run>>>>>>>>>>>");
		logger.debug("runEngine: there are " + prules.size() + " rules");
		Shoppingcart cart = init(_cart);
		for (PRule prule : prules) {
			logger.debug("---" + prule.getRuleId() + ":" + prule.getRuleName()
					+ "---");
			EligibilityHandler eligibilityManager = new EligibilityHandler(
					prule.getEligibilityOperator(), prule.getEligibilitys());
			short conditionOperator = prule.getConditionOperator();
			// 这里主要是为了区分catalogPromotion和cartPromotion,他们两者在运行的时候
			// ConditionMaintainHandler的逻辑不一样，要注意。
			if (prule.getRuleType().equals(
					PromoRule.PROMOTION_TYPE_CATALOGPROMOTION)) {
				conditionOperator += 2;
			}
			ConditionMaintainHandler conditionMaintainManager = new ConditionMaintainHandler(
					conditionOperator, prule.getConditions());
			ActionHandler actionManager = new ActionHandler(prule.getActions());
			if (conditionOperator >= 2 || eligibilityManager.isMatch(_customer)) {
				if (conditionMaintainManager.run(cart)) {
					cart = actionManager.run(cart);
					if (!isInvalid(cart)) {
						cart = commitActions(cart, prule);
						cart = clearUpActions(cart);
					} else {
						cart = clearUpActions(cart);
					}
				}
			}
		}
		cart = summary(cart);
		return finish(cart);
	}

	/**
	 * 判断一条规则模拟运行完以后是否生效
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @return true 该规则是有效规则
	 */
	private boolean isInvalid(Shoppingcart _cart) {
		if (_cart.getCartDiscountAmountRAM().add(_cart.getCartDiscountAmount())
				.compareTo(_cart.getSubtotal()) == 1) {
			return true;
		}
		for (ShoppingcartItem cartItem : _cart
				.getShoppingCartItemsForPromoInAction()) {

			if (cartItem.getDiscountPriceRAM().compareTo(
					cartItem.getDiscountPrice()) == 1) {
				return true;
			}
			if (cartItem.getDiscountQuantityRAM().compareTo(
					cartItem.getDiscountQuantity()) == 1) {
				return true;
			}

		}

		return false;
	}

	/**
	 * 如果该规则是"有效规则",则"提交"动作。
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @param _prule
	 *            PRule
	 * @return
	 */
	public Shoppingcart commitActions(Shoppingcart _cart, PRule _prule) {
		Shoppingcart cart = _cart;
		boolean existCommitAction = false;
		if (cart.getCartDiscountAmountRAM().compareTo(new BigDecimal(0)) != 0) {
			if (_prule.isEnableDiscountAgain() || !cart.isHasDiscountOfMoney()) {

				cart.setCartDiscountAmount(cart.getCartDiscountAmount().add(
						cart.getCartDiscountAmountRAM()));
				cart.setHasDiscountOfMoney(true);
				existCommitAction = true;
			}
		}
		if (cart.getGainedPointRAM() != 0) {
			if (_prule.isEnableDiscountAgain() || !cart.isHasDiscountOfPoint()) {
				cart.setGainedPoint(cart.getGainedPoint()
						+ cart.getGainedPointRAM());
				cart.setHasDiscountOfPoint(true);
				existCommitAction = true;
			}
		}
		if (!cart.getShippingDiscountInfoRAM().equals("")) {
			//就算不是折上折时,不同的运输方式可以同时生效;但在非折上折的情况下,相同的运输方式只会取优先级最高的
			if (_prule.isEnableDiscountAgain() 
					|| !cart.isHasDiscountOfShipment()
					|| !PromoUtil.hasSameShipmentMethodId(cart.getShippingDiscountInfo(), cart.getShippingDiscountInfoRAM()))
			{
				cart.setShippingDiscountInfo(cart.getShippingDiscountInfo()
					+ cart.getShippingDiscountInfoRAM());
				cart.setHasDiscountOfShipment(true);
				existCommitAction = true;
			}
		}
		if ((null == cart.getGainedCouponTypeId()) && (null != cart.getGainedCouponTypeIdRAM())) {
			cart.setGainedCouponTypeId(cart.getGainedCouponTypeIdRAM());
			existCommitAction = true;
		}
		for (ShoppingcartItem cartItem : cart
				.getShoppingCartItemsForPromoInAction()) {
			if (cartItem.getDiscountPriceRAM().compareTo(new BigDecimal(0)) != 0
					|| cartItem.getDiscountQuantityRAM() != 0) {
				if (_prule.isEnableDiscountAgain() || !cartItem.isHasDiscount()) {
					cartItem.setDiscountPrice(cartItem.getDiscountPrice().add(
							cartItem.getDiscountPriceRAM().negate()));
					cartItem.setDiscountQuantity(cartItem.getDiscountQuantity()
							- cartItem.getDiscountQuantityRAM());
					cartItem.setHasDiscount(true);
					existCommitAction = true;
				}
			}
		}

		BigDecimal itemsTotal = new BigDecimal("0");
		for (ShoppingcartItem o : cart.getShoppingCartItemsForCalculation()) {
			itemsTotal = itemsTotal.add(o.getDiscountPrice().multiply(
					new BigDecimal(o.getDiscountQuantity())));
		}
		if (cart.getCartDiscountAmount().compareTo(itemsTotal) != 1) {
			cart
					.setTotal(itemsTotal.add(cart.getCartDiscountAmount()
							.negate()));
		} else {
			cart.setTotal(itemsTotal);
		}

		if (existCommitAction) {
			boolean exitThisPromotion = false;
			for (ShoppingcartPromotion epromo : cart
					.getShoppingCartPromotions()) {
				if (epromo.getPromoRuleId().equals(_prule.getRuleId())) {
					exitThisPromotion = true;
					break;
				}
			}
			if (!exitThisPromotion) {
				ShoppingcartPromotion promo = new ShoppingcartPromotion();
				promo.setPromoRuleId(_prule.getRuleId());
				promo.setPromotionName(_prule.getRuleName());
				promo.setType(_prule.getRuleType());
				promo.setShoppingcart(cart);
				cart.addShoppingCartPromotion(promo);
			}
		}
		return cart;
	}

	/**
	 * 在一条规则运行完以后做一些清理的工作。
	 * 
	 * @param _cart
	 *            Shoppingcart
	 * @return Shoppingcart
	 */
	public Shoppingcart clearUpActions(Shoppingcart _cart) {
		Shoppingcart cart = _cart;
		cart.setCartDiscountAmountRAM(new BigDecimal(0));
		cart.setShippingDiscountInfoRAM("");
		cart.setGainedPointRAM(0);
		cart.setGainedCouponTypeIdRAM(null);
		for (ShoppingcartItem cartItem : _cart.getShoppingCartItemsForPromo()) {
			cartItem.setDiscountPriceRAM(new BigDecimal(0));
			cartItem.setDiscountQuantityRAM(0);
			cartItem.setCatalogConditonFlagRAM(false);
			cartItem.setCatalogConditionRunInAllFlagRAM(true);
			cartItem.setCatalogConditionRunInAnyFlagRAM(false);
		}
		return cart;
	}

	/**
	 * 运行前做一些准备的工作
	 * 
	 * @param _sku
	 *            ProductSku
	 * @return ProductSku
	 */
	public ProductSku init(ProductSku _sku) {
		ProductSku sku = _sku;
		if (sku.getSalePrice() == null) {
			sku.setDiscountPrice(sku.getPrice());
		} else {
			sku.setDiscountPrice(sku.getSalePrice());
		}
		return sku;

	}

	/**
	 * 运行catalogPromotion类的规则
	 * 
	 * @param _sku
	 *            ProductSku
	 * @return ProductSku
	 * @throws Exception
	 */
	public ProductSku run(ProductSku _sku) throws Exception {
		logger.debug("runEngine: run>>>>>>>>>>>");
		logger.debug("runEngine: there are " + prules.size() + " rules");
		ProductSku sku = _sku;
		if (null == sku.getSalePrice()) {
			sku = init(sku);
			for (PRule prule : prules) {
				logger.debug("---" + prule.getRuleId() + ":"
						+ prule.getRuleName() + "---");
				ConditionMaintainHandler conditionMaintainManager = new ConditionMaintainHandler(
						prule.getConditionOperator(), prule.getConditions());
				ActionHandler actionManager = new ActionHandler(prule
						.getActions());

				if (conditionMaintainManager.run(sku)) {
					sku = actionManager.run(sku);
					if (!isInvalid(sku)) {
						sku = commitActions(sku, prule.isEnableDiscountAgain());
						sku = clearUpActions(sku);
					} else {
						sku = clearUpActions(sku);
					}
				}

			}
		} 
		return finish(sku);
	}

	/**
	 * 模拟运行规则（去除eligibility的判断）
	 * 
	 * @param _sku
	 *            ProductSku
	 * @return ProductSku
	 * @throws Exception
	 */
	public Collection<ProductSku> mockRun(ProductSku _sku) throws Exception {
		logger.debug("runEngine: mockRun>>>>>>>>>>>");
		logger.debug("runEngine: there are " + prules.size() + " rules");
		Collection<ProductSku> results = new ArrayList<ProductSku>();
		if (null == _sku.getSalePrice()) {
			for (PRule prule : prules) {
				logger.debug("---" + prule.getRuleId() + ":"
						+ prule.getRuleName() + "---");
				ProductSku sku = (ProductSku) _sku.clone();
				sku = init(sku);
				ConditionMaintainHandler conditionMaintainManager;
				if (!prule.getRuleType().equals(
						PromoRule.PROMOTION_TYPE_CATALOGPROMOTION)) {
					AlwaysTrueCondition alwaysTrueCondition = new AlwaysTrueCondition();
					List<Condition> conditions = new ArrayList<Condition>();
					conditions.add(alwaysTrueCondition);
					conditionMaintainManager = new ConditionMaintainHandler(
							ConditionMaintainHandler.CONDITION_OPERATOR_ANY,
							conditions);
				} else {
					conditionMaintainManager = new ConditionMaintainHandler(
							prule.getConditionOperator(), prule.getConditions());
				}
				ActionHandler actionManager = new ActionHandler(prule
						.getActions());
				if (conditionMaintainManager.run(sku)) {
					sku = actionManager.run(sku);
					if (!isInvalid(sku)) {
						sku = commitActions(sku, prule.isEnableDiscountAgain());
						sku = clearUpActions(sku);
					} else {
						sku = clearUpActions(sku);
					}
				}
				sku = finish(sku);
				if (!sku.getPrice().equals(sku.getDiscountPrice())) {
					//促销价与原价不同才加进结果集
					sku.setPrule(prule);
					results.add(sku);
				}

			}
		} else {
			//不处理特价
		}
		return results;
	}

	public Collection<ProductSku> run(Collection<ProductSku> _list)
			throws Exception {
		Collection<ProductSku> list = _list;
		for (ProductSku sku : list) {
			sku = run(sku);
		}
		return list;
	}

	/**
	 * 判断一条规则模拟运行后是否有效
	 * 
	 * @param _sku
	 *            ProductSku
	 * @return true 该规则有效
	 */
	private boolean isInvalid(ProductSku _sku) {
		if (_sku.getDiscountPriceRAM().compareTo(_sku.getDiscountPrice()) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 如果规则有效,则"提交"动作
	 * 
	 * @param _sku
	 *            ProductSku
	 * @param enableDiscountAgain
	 * @return
	 */
	public ProductSku commitActions(ProductSku _sku, boolean enableDiscountAgain) {
		ProductSku sku = _sku;
		if (enableDiscountAgain || !sku.isHasDiscount()) {
			sku.setDiscountPrice(sku.getDiscountPrice().add(
					sku.getDiscountPriceRAM().negate()));
			sku.setHasDiscount(true);
		}
		return sku;
	}

	/**
	 * 在一条规则运行完以后做一些清理的工作
	 * 
	 * @param _sku
	 *            ProductSku
	 * @return ProductSku
	 */
	public ProductSku clearUpActions(ProductSku _sku) {
		ProductSku sku = _sku;
		sku.setDiscountPriceRAM(new BigDecimal(0));
		return sku;
	}

	/**
	 * 最后对SKU的处理
	 * 
	 * @param _sku
	 * @return
	 */
	public ProductSku finish(ProductSku _sku) {
		ProductSku sku = calcDiscountPercent(_sku);
		return format(sku);
	}
	/**
	 * 计算折扣百分比
	 * 原价为0： 100
	 * 有特价或者促销价：= 特价or促销价/原价*100
	 * 无：0
	 * @param _sku
	 * @return ProductSku
	 */
	public ProductSku calcDiscountPercent(ProductSku _sku) {
		ProductSku sku = _sku;
		final BigDecimal zero = new BigDecimal("0");
		final BigDecimal oneHundred = new BigDecimal("100");
		//if(sku.getPrice().equals(zero)){
		if(sku.getPrice().compareTo(BigDecimal.ZERO)==0){
			sku.setDiscountPercent(oneHundred);
		} else {
			
			if (null != sku.getSalePrice()) {
				sku.setDiscountPercent(sku.getPrice().add(sku.getSalePrice().negate()).divide(sku.getPrice(),divideScale,roundingMode).multiply(oneHundred));
			}
			else if (null != sku.getDiscountPrice()) {
				sku.setDiscountPercent(sku.getPrice().add(sku.getDiscountPrice().negate()).divide(sku.getPrice(),divideScale,roundingMode).multiply(oneHundred));
			}else{
				sku.setDiscountPercent(zero);
			}
		}
		return sku;
		
	}

	/**
	 * 对sku进行格式化的处理
	 * 
	 * @param _sku
	 * @return
	 */
	public ProductSku format(ProductSku _sku) {
		ProductSku sku = _sku;
		
		sku.setPrice(sku.getPrice().setScale(priceScale, roundingMode));
		if (null != sku.getSalePrice()) {
			sku.setSalePrice(sku.getSalePrice().setScale(priceScale,
					roundingMode));
		}
		if (null != sku.getDiscountPrice()) {
			sku.setDiscountPrice(sku.getDiscountPrice().setScale(priceScale,
					roundingMode));
		}
		sku.setDiscountPercent(sku.getDiscountPercent().setScale(priceScale,roundingMode));
		return sku;
	}

}

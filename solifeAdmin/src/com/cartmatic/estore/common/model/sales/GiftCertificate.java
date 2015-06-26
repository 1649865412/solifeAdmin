package com.cartmatic.estore.common.model.sales;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.cartmatic.estore.common.model.sales.base.GiftCertificateTbl;
import com.cartmatic.estore.common.util.DateUtil;

/**
 * 礼券实体
 * 
 * @author CartMatic
 * 
 */
public class GiftCertificate extends GiftCertificateTbl
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -109720087278889035L;
	public static final Short STATUS_ACTIVE = 1;// 礼券激活
	public static final Short STATUS_UNACTIVE = 2;// 非激活
	public static final Short STATUS_UNDEAL = 0;// 未成交，刚下订单，但未支付
	public static final Short SENTBYEMAIL_YES = 1;// 电子礼券
	public static final Short SENTBYEMAIL_NO = 0;// 邮政礼券

	// 前台状态
	public static final Short STATE_INVALID = 0;// 无效,礼券号不存在或者其他原因
	public static final Short STATE_UNACTIVE = -1;// 无效，因为礼券号未激活
	public static final Short STATE_UNDEAL = -2;// 无效，因为礼券号在交易中，暂时不能使用
	public static final Short STATE_EXPIRE = -3;// 无效，因为礼券号已经超过可用时间
	public static final Short STATE_NOT_ENOUGH_REMAINEDAMT = -4;// 无效，因为余额不足以支付规定的金额
	public static final Short STATE_REMAINEDAMT_IS_ZERO = -5;// 无效，因为余额为零
	public static final Short STATE_AVAILABLE = 1;// 有效

	// 后台状态
	public static final Short SA_STATE_ACTIVE = 1;// 激活
	public static final Short SA_STATE_UNACTIVE = 2;// 非激活
	public static final Short SA_STATE_UNDEAL = 0;// 未成交，未支付
	public static final Short SA_STATE_PAST = 3;// 已过期

	// 0普通礼券　1=500元礼品卡 2=1000元礼品卡
	public static final Short GC_NORMAL = 0;
	public static final Short GC_500 = 1;
	public static final Short GC_1000 = 2;

	private short state;

	private int shoppingCartItems = 12;

	/**
	 * Default Empty Constructor for class GiftCertificate
	 */
	public GiftCertificate()
	{
		super();
	}

	/**
	 * Default Key Fields Constructor for class GiftCertificate
	 */
	public GiftCertificate(Integer in_giftCertificateId)
	{
		super(in_giftCertificateId);
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public int getShoppingCartItems() {
		return shoppingCartItems;
	}

	public void setShoppingCartItems(int shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	/**
	 * 业务规则 500 元激活时，可用12个月。1-11月40元／月，12月60元。 1000元，1-11月80元／月，12月120元。
	 * 
	 * @param customerId
	 */
	public void bindToCustomer(Integer customerId) {
		// 已经绑定了的，不能再次绑定；非激活的也不能进行绑定
		if (this.customerId != null || STATUS_ACTIVE.equals(this.status))
			return;
		this.customerId = customerId;
		this.registerTime = new Date();
		this.status = STATUS_ACTIVE;
		// 计算有效期
		Date start = DateUtil.getStartOfThisMonth();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.YEAR, 1);
		this.expireTime = DateUtil.getEndOfThisDay(cal.getTime());
		if (GC_500.equals(this.giftType))
		{
			this.m1Amt = new BigDecimal(40);
			this.m2Amt = new BigDecimal(40);
			this.m3Amt = new BigDecimal(40);
			this.m4Amt = new BigDecimal(40);
			this.m5Amt = new BigDecimal(40);
			this.m6Amt = new BigDecimal(40);
			this.m7Amt = new BigDecimal(40);
			this.m8Amt = new BigDecimal(40);
			this.m9Amt = new BigDecimal(40);
			this.m10Amt = new BigDecimal(40);
			this.m11Amt = new BigDecimal(40);
			this.m12Amt = new BigDecimal(60);

		}
		else if (GC_1000.equals(this.giftType))
		{
			this.m1Amt = new BigDecimal(80);
			this.m2Amt = new BigDecimal(80);
			this.m3Amt = new BigDecimal(80);
			this.m4Amt = new BigDecimal(80);
			this.m5Amt = new BigDecimal(80);
			this.m6Amt = new BigDecimal(80);
			this.m7Amt = new BigDecimal(80);
			this.m8Amt = new BigDecimal(80);
			this.m9Amt = new BigDecimal(80);
			this.m10Amt = new BigDecimal(80);
			this.m11Amt = new BigDecimal(80);
			this.m12Amt = new BigDecimal(120);
		}
	}

	/**
	 * 查询可以使用的金额
	 * 
	 * @param cartItem
	 * @return
	 */
	private BigDecimal getBalance(int cartItem) {
		BigDecimal[] amts = new BigDecimal[] { m1Amt, m2Amt, m3Amt, m4Amt, m5Amt, m6Amt, m7Amt, m8Amt, m9Amt, m10Amt, m11Amt, m12Amt };
		if (GC_NORMAL.equals(this.giftType))
		{
			return this.remainedAmt;
		}
		else if (GC_500.equals(this.giftType) || GC_1000.equals(this.giftType))
		{
			int currentM = getCurrentMonth();
			if (currentM == -1)
				return BigDecimal.ZERO;
			BigDecimal result = BigDecimal.ZERO;
			for (int i = currentM; i < currentM + cartItem && i < 12; i++)
			{
				// 如果当前月份已经用了，就没有了。
				if (i == currentM && amts[i].compareTo(BigDecimal.ZERO) == 0)
				{
					return result;
				}
				result = result.add(amts[i]);
			}
			return result;
		}
		return BigDecimal.ZERO;
	}

	private int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.registerTime == null? new Date(): registerTime);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date start = DateUtil.getStartOfThisDay(cal.getTime());
		long current = (new Date()).getTime();
		for (int i = 0; i < 12; i++)
		{
			Calendar c1 = Calendar.getInstance();
			c1.setTime(start);
			c1.add(Calendar.MONTH, i + 1);
			Date end = c1.getTime();
			if (current >= start.getTime() && end.getTime() > current)
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * 获得所有12个月的余额
	 * 
	 * @return
	 */
	// public BigDecimal getAllBalance()
	// {
	// return getBalance(12);
	// }

	@Override
	public java.math.BigDecimal getRemainedAmt() {
		if (GC_NORMAL.equals(this.getGiftType()) || this.giftCertificateId == null)
			return this.remainedAmt;
		else
			return getBalance(shoppingCartItems);
	}

	@Override
	public void setRemainedAmt(BigDecimal amt) {
		if (GC_NORMAL.equals(this.getGiftType()) || this.giftCertificateId == null)
			this.remainedAmt = amt;
		else
		{
			//BigDecimal[] amts = new BigDecimal[] { m1Amt, m2Amt, m3Amt, m4Amt, m5Amt, m6Amt, m7Amt, m8Amt, m9Amt, m10Amt, m11Amt, m12Amt };
			int currentM = getCurrentMonth();
			if (currentM < 0 || currentM > 11)
				return;
			// if (amt.compareTo(amts[currentM]) >= 0)
			// {
			//amts[currentM] = amt;
			switch (currentM)
			{
			case 0:
				m1Amt = amt;
				break;
			case 1:
				m2Amt = amt;
				break;
			case 2:
				m3Amt = amt;
				break;
			case 3:
				m4Amt = amt;
				break;
			case 4:
				m5Amt = amt;
				break;
			case 5:
				m6Amt = amt;
				break;
			case 6:
				m7Amt = amt;
				break;
			case 7:
				m8Amt = amt;
				break;
			case 8:
				m9Amt = amt;
				break;
			case 9:
				m10Amt = amt;
				break;
			case 10:
				m11Amt = amt;
				break;
			case 11:
				m12Amt = amt;
				break;
			default:
				break;
			}			
		}
	}
	// public BigDecimal getAvailableBalance()
	// {
	// return getBalance(shoppingCartItems);
	// }
}

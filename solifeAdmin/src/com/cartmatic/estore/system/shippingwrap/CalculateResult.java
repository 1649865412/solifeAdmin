package com.cartmatic.estore.system.shippingwrap;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created 2006-6-7--9:51:15
 * @author  fire
 * 
 */
public class CalculateResult {
    private short status=ShippingConstants.STATUS_UNHANDLED;                  //处理状态
   
    private BigDecimal totalWeight;          //购物车总重量
    private BigDecimal maxWeight;            //最大允许运输重量
    private String weightUnitName;
    
    private BigDecimal totalVolume;          //购物车总体积
    private BigDecimal maxVolume;            //最大允许运输体积
    private String volumeUnitCode;
    
    
    private Integer totalItem;          //购物车总重量
    private Integer maxItem;            //最大允许运输重量
    
    
    private BigDecimal totalCharge;          //总的运输费用
     
    private boolean isCod=false;    
    
    private short ratingType;
    
    
    
    
	public short getRatingType() {
		return ratingType;
	}
	public void setRatingType(short ratingType) {
		this.ratingType = ratingType;
	}
	/**
	 * @return 
	 */
	public boolean isCod() {
		return isCod;
	}
	/**
	 * @param isCod 
	 */
	public void setCod(boolean isCod) {
		this.isCod = isCod;
	}
    /**
     * @return 返回 maxVolume。
     */
    public BigDecimal getMaxVolume() {
        return maxVolume;
    }
    /**
     * @param maxVolume 要设置的 maxVolume。
     */
    public void setMaxVolume(BigDecimal maxVolume) {
        this.maxVolume = maxVolume;
    }
    /**
     * @return 返回 maxWeight。
     */
    public BigDecimal getMaxWeight() {
        return maxWeight;
    }
    /**
     * @param maxWeight 要设置的 maxWeight。
     */
    public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
    }
    /**
     * @return 返回 totalVolume。
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }
    /**
     * @param totalVolume 要设置的 totalVolume。
     */
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }
    /**
     * @return 返回 totalWeight。
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }
    /**
     * @param totalWeight 要设置的 totalWeight。
     */
    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }
    /**
     * @return 返回 volumeUnitCode。
     */
    public String getVolumeUnitCode() {
        return volumeUnitCode;
    }
    /**
     * @param volumeUnitCode 要设置的 volumeUnitCode。
     */
    public void setVolumeUnitCode(String volumeUnitCode) {
        this.volumeUnitCode = volumeUnitCode;
    }
    /**
     * @return 返回 weightUnitCode。
     */
    public String getWeightUnitName() {
        return weightUnitName;
    }
    /**
     * @param weightUnitCode 要设置的 weightUnitCode。
     */
    public void setWeightUnitName(String weightUnitName) {
        this.weightUnitName = weightUnitName;
    }
    
   
	public Integer getMaxItem() {
		return maxItem;
	}
	public void setMaxItem(Integer maxItem) {
		this.maxItem = maxItem;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	
	
    /**
     * @return 
     */
    public short getStatus() {
        return status;
    }
    /**
     * @param status 
     */
    public void setStatus(short status) {
        this.status = status;
    }
    /**
     * @return 
     */
    public BigDecimal getTotalCharge() {
        return totalCharge;
    }
    /**
     * @param totalCharge 
     */
    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }
  
    public String toString() {
		return new ToStringBuilder(this)
				.append("totalCharge", this.totalCharge) 
				.append("totalWeight", this.totalWeight) 
				.toString();
	}
}

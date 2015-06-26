package com.cartmatic.estore.common.model.inventory;

import java.util.Date;

/**
 * 
 * @author kedou
 *
 */
public class InventoryModel {
	private Integer inventoryId;
	
	private Integer productSkuId;
	
	private Integer reservedQuantity;
	
	private Integer reorderQuantity;
	
	private Integer reorderMinimnm;
	
	private Date expectedRestockDate;
	
	private String eventHandler;
	
	
	/**
	 * 调整库存类型，1增加、2减少
	 */
	private Short adjustmentType;
	
	/**
	 * 调整数量
	 */
	private Integer adjustmentQuantity;
	
	/**
	 * 调整理由
	 */
	private String adjustmentReason;
	
	/**
	 * 备注
	 */
	private String adjustmentComment;

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Integer productSkuId) {
		this.productSkuId = productSkuId;
	}

	public Integer getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(Integer reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public Integer getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(Integer reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public Integer getReorderMinimnm() {
		return reorderMinimnm;
	}

	public void setReorderMinimnm(Integer reorderMinimnm) {
		this.reorderMinimnm = reorderMinimnm;
	}

	public Date getExpectedRestockDate() {
		return expectedRestockDate;
	}

	public void setExpectedRestockDate(Date expectedRestockDate) {
		this.expectedRestockDate = expectedRestockDate;
	}

	public Short getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(Short adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public Integer getAdjustmentQuantity() {
		return adjustmentQuantity;
	}

	public void setAdjustmentQuantity(Integer adjustmentQuantity) {
		this.adjustmentQuantity = adjustmentQuantity;
	}

	public String getAdjustmentReason() {
		return adjustmentReason;
	}

	public void setAdjustmentReason(String adjustmentReason) {
		this.adjustmentReason = adjustmentReason;
	}

	public String getAdjustmentComment() {
		return adjustmentComment;
	}

	public void setAdjustmentComment(String adjustmentComment) {
		this.adjustmentComment = adjustmentComment;
	}

	public void setEventHandler(String eventHandler) {
		this.eventHandler = eventHandler;
	}

	public String getEventHandler() {
		return eventHandler;
	}
	
	
}

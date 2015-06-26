package com.cartmatic.estore.exception;

public class OutOfStockException extends Exception{
	
	private static final long	serialVersionUID	= 6445963130649662622L;
	
	private Integer canAllocateAvailableQty;
	private Integer canAllocatePreOrBackOrderQty;

	public OutOfStockException(Integer canAllocateAvailableQty,Integer canAllocatePreOrBackOrderQty) {
		super();
		this.canAllocateAvailableQty=canAllocateAvailableQty;
		this.canAllocatePreOrBackOrderQty=canAllocatePreOrBackOrderQty;
	}

	public Integer getCanAllocateAvailableQty() {
		return canAllocateAvailableQty;
	}

	public Integer getCanAllocatePreOrBackOrderQty() {
		return canAllocatePreOrBackOrderQty;
	}
}

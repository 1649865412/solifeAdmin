
package com.cartmatic.estore.exception;

import com.cartmatic.estore.common.model.sales.GiftCertificate;

public class GiftCertificateStateException extends Exception {

	private static final long	serialVersionUID	= 1L;
	private String				giftCertificateNo;
	private short				state				= GiftCertificate.STATE_INVALID;

	public GiftCertificateStateException() {
		super();
	}

	public GiftCertificateStateException(String giftCertificateNo, short state) {
		super();
		this.setGiftCertificateNo(giftCertificateNo);
		this.setState(state);
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public String getGiftCertificateNo() {
		return giftCertificateNo;
	}

	public void setGiftCertificateNo(String giftCertificateNo) {
		this.giftCertificateNo = giftCertificateNo;
	}

}

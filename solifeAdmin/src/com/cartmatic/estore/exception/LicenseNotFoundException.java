
package com.cartmatic.estore.exception;

public class LicenseNotFoundException extends RuntimeException {
	public LicenseNotFoundException() {
		super();
		System.out
				.println("--------------------------------------\r\n Merchant One License Key not found. Contact us please.\r\n Mailing Address \r\n CartMatic Software Technology Co., Ltd.\r\n 28HI, NO.80 XianLieZhong Rd.\r\n GuangZhou, GuangDong, 510035 \r\n China\r\n Toll free: 4006 786 687 (CHINA ONLY)\r\n Tel: +86 20 2376808 2376806\r\n Fax: +86 20 22376801 \r\n--------------------------------------");
	}
}

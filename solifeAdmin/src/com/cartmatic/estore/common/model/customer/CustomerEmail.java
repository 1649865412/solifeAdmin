package com.cartmatic.estore.common.model.customer;

public class CustomerEmail {
	private String title = "";//email ' title

	private String receivers = "";//receivers's emails

	private String accessories = "";//accessories to email

	private String content = "";

	public CustomerEmail() {
	};

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
package com.rewardomain.manager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dining {
	private long id;
	
	@JsonProperty("credit_card_number")
	private String creditCardNumber;
	
	@JsonProperty("merchant_number")
	private long merchantNumber;
	
	@JsonProperty("dining_amount")
	private double diningAmount;
	
	@JsonProperty("dining_date")
	private String diningDate;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public long getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(long merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public double getDiningAmount() {
		return diningAmount;
	}

	public void setDiningAmount(double diningAmount) {
		this.diningAmount = diningAmount;
	}

	public String getDiningDate() {
		return diningDate;
	}

	public void setDiningDate(String diningDate) {
		this.diningDate = diningDate;
	}

	public Dining(long id, String creditCardNumber, long merchantNumber, double diningAmount, String diningDate) {
		//super();
		this.id = id;
		this.creditCardNumber = creditCardNumber;
		this.merchantNumber = merchantNumber;
		this.diningAmount = diningAmount;
		this.diningDate = diningDate;
	}

	public Dining() {
	//	super();
	}




	
}

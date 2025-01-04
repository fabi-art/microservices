package com.rewardomain.manager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class BenefitRestaurant {

	@JsonProperty("benefit_amount")
	private double amount;
	
	
	@Transient
	private String executionChain;
	

	public BenefitRestaurant(double amount, String executionChain) {
		super();
		this.amount = amount;
		this.executionChain = executionChain;
	}



	public String getExecutionChain() {
		return executionChain;
	}



	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}
	

	public BenefitRestaurant(double amount) {
		this.setAmount(amount);
	}

	public BenefitRestaurant() {
	
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
}

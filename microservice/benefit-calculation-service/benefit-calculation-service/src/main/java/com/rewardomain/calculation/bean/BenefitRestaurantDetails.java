package com.rewardomain.calculation.bean;

import jakarta.persistence.Transient;

public class BenefitRestaurantDetails extends BenefitRestaurant {

	private int id;
	private int number;
	private String name;
	private double percentage;
	private String availability;
	 @Transient
	    private String executionChain;

	
	public String getExecutionChain() {
		return executionChain;
	}

	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}

	public BenefitRestaurantDetails(int id, int number, String name, double percentage, String availability) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.percentage = percentage;
		this.availability = availability;
	}

	public BenefitRestaurantDetails() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	
}

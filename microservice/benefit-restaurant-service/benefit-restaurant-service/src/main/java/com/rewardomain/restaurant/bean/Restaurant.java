package com.rewardomain.restaurant.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Restaurant {

    @Id
    private long id;

    @Column(name="merchant_number")
    private long number;
    private String name;
    @Column(name="benefit_percentage")
    private double percentage;
    @Column(name="benefit_availability_policy")
    private String availability;
    @Transient
    private String executionChain;
    
    
	public Restaurant(long id, long number, String name, double percentage, String availability,
			String executionChain) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.percentage = percentage;
		this.availability = availability;
		this.executionChain = executionChain;
	}

	public String getExecutionChain() {
		return executionChain;
	}

	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}

	public Restaurant(long id, long number, String name, double percentage, String availability) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.percentage = percentage;
		this.availability = availability;
	}
	
	public Restaurant() {
		super();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
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

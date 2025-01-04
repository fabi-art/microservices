package com.rewardomain.manager.bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reward {


	public Reward(long id, long confirmationNumber, double amount, long merchantNumber, LocalDateTime rewardDate) {
		super();
		this.id = id;
		this.confirmationNumber = confirmationNumber;
		this.amount = amount;
		this.merchantNumber = merchantNumber;
		this.rewardDate = rewardDate;
	}

	public Reward() {
	
	}

	
	public long getConfirmationNumber() {
		return confirmationNumber;
	}

	public Reward(long confirmationNumber, double amount, long merchantNumber, LocalDateTime rewardDate) {
		super();
		this.confirmationNumber = confirmationNumber;
		this.amount = amount;
		this.merchantNumber = merchantNumber;
		this.rewardDate = rewardDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public void setConfirmationNumber(long confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(long merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public LocalDateTime getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(LocalDateTime rewardDate) {
		this.rewardDate = rewardDate;
	}

	@Id
	private long id;
	
	@Column(name="confirmation_number")
	private long confirmationNumber;
	private double amount;
	
	@Column(name="merchant_number")
	private long merchantNumber;
	
	@Column(name="reward_date")
	private LocalDateTime rewardDate;



	

}

package com.rewardomain.manager.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class Confirmation {
	@JsonProperty("reward_confirmation_number")
	private long rewardConfirmation;
	@Transient
	private String executionChain;
	
	
	
	public String getExecutionChain() {
		return executionChain;
	}



	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}



	public Confirmation(long rewardConfirmation, String executionChain) {
		super();
		this.rewardConfirmation = rewardConfirmation;
		this.executionChain = executionChain;
	}





	public long getRewardConfirmation() {
		return rewardConfirmation;
	}

	public void setRewardConfirmation(long rewardConfirmation) {
		this.rewardConfirmation = rewardConfirmation;
	}

	public Confirmation(long rewardConfirmation) {
		super();
		this.rewardConfirmation = rewardConfirmation;
	}



	

	
}

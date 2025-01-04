package com.rewardomain.contribution.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class AccountContributionRequest {
		private String name;
		@JsonProperty("credit_card_number") 
		private String ccnumber;
		@JsonProperty("account_number") 
		private String anumber;
		@JsonProperty("allocation_percentage") 
		private double percentage;
		
		@Transient
		private String executionChain;
		
		public String getExecutionChain() {
			return executionChain;
		}
		public void setExecutionChain(String executionChain) {
			this.executionChain = executionChain;
		}
		
		
		public AccountContributionRequest(String name, String ccnumber, String anumber, double percentage,
				String executionChain) {
			super();
			this.name = name;
			this.ccnumber = ccnumber;
			this.anumber = anumber;
			this.percentage = percentage;
			this.executionChain = executionChain;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCcnumber() {
			return ccnumber;
		}
		public void setCcnumber(String ccnumber) {
			this.ccnumber = ccnumber;
		}
		public String getAnumber() {
			return anumber;
		}
		public void setAnumber(String anumber) {
			this.anumber = anumber;
		}
		public double getPercentage() {
			return percentage;
		}
		public void setPercentage(double percentage) {
			this.percentage = percentage;
		}
		public AccountContributionRequest(String name, String ccnumber, String anumber, double percentage) {
			super();
			this.name = name;
			this.ccnumber = ccnumber;
			this.anumber = anumber;
			this.percentage = percentage;
		}
		public AccountContributionRequest() {
		
		}
		


}
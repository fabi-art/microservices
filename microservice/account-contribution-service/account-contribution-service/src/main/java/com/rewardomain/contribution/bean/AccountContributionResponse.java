package com.rewardomain.contribution.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class AccountContributionResponse {
		@JsonProperty("status_code")
		private int code;
		private String message;
		@Transient
		private String executionChain;
		
		
		public AccountContributionResponse(int code, String message, String executionChain) {
			super();
			this.code = code;
			this.message = message;
			this.executionChain = executionChain;
		}
		public String getExecutionChain() {
			return executionChain;
		}
		public void setExecutionChain(String executionChain) {
			this.executionChain = executionChain;
		}
		public int getCode() {
			return code;

		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public AccountContributionResponse(int code, String message) {
			super();
			this.code = code;
			this.message = message;
		}
		public AccountContributionResponse() {
					}
       

}
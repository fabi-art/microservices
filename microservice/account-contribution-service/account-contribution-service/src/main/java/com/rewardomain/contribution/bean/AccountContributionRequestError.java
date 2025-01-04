package com.rewardomain.contribution.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"name", "ccnumber","anumber","percentage","executionChain"})
public class  AccountContributionRequestError extends  AccountContributionRequest {

    @JsonProperty("status_code")
    private Long statusCode;

    @JsonProperty("error_message")
    private String message;

	public Long getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Long statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AccountContributionRequestError(String name, String ccnumber, String anumber, double percentage,
			String executionChain, Long statusCode, String message) {
		super(name, ccnumber, anumber, percentage, executionChain);
		this.statusCode = statusCode;
		this.message = message;
	}
    
	public AccountContributionRequestError(Long statusCode, String message) {
		
		this.statusCode = statusCode;
		this.message = message;
	}
	
    
}
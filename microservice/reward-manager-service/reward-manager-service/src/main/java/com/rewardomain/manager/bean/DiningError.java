package com.rewardomain.manager.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "creditCardNumber", "merchantNumber", "executionChain"})
public class DiningError extends Dining {

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

	public DiningError(Long statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public DiningError() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	
	
}
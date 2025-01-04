package com.rewardomain.calculation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"amount", "executionChain"})
public class BenefitRestaurantError extends BenefitRestaurant {

    @JsonProperty("status_code")
    private Long statusCode;

    @JsonProperty("error_message")
    private String message;

    // Default constructor
    public BenefitRestaurantError() {
        super();
    }

    // Constructor with parameters
    public BenefitRestaurantError(double amount, double percentage, Long statusCode, String message) {
        super(amount, percentage);
        this.statusCode = statusCode;
        this.message = message;
    }
    
    public BenefitRestaurantError( Long statusCode, String message) {
       
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getters and setters
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
}

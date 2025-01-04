package com.rewardomain.restaurant.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "number", "name", "percentage", "availability", "executionChain"})
public class RestaurantError extends Restaurant {

    @JsonProperty("status_code")
    private Long statusCode;

    @JsonProperty("error_message")
    private String message;

    // Default constructor
    public RestaurantError() {
        super();
    }

    // Constructor with parameters
    public RestaurantError(long id, long number, String name, double percentage, String availability,
                           String executionChain, Long statusCode, String message) {
        super(id, number, name, percentage, availability, executionChain);
        this.statusCode = statusCode;
        this.message = message;
    }

  
    
    public RestaurantError(Long statusCode, String message) {

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

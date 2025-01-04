package com.rewardomain.manager.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "number", "name", "percentage", "availability", "executionChain"})
public class BenefitRestaurantError extends BenefitRestaurant {

    @JsonProperty("status_code")
    private Long statusCode;

    @JsonProperty("error_message")
    private String message;
}
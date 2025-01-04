package com.rewardomain.restaurant.controller;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rewardomain.restaurant.bean.Restaurant;
import com.rewardomain.restaurant.repository.RestaurantRepository;

public class RestaurantController {

	private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }
	
	@GetMapping("/benefit-restaurant/merchants/{merchant_number}")
	
public  ResponseEntity<Restaurant>  getRestaurant(@PathVariable long merchant_number)	{
	
		Restaurant restaurant = repository.findByNumber(merchant_number);
		if(restaurant == null) {
			 new ResponseEntity<>(HttpStatusCode.valueOf(404));
		}
		 return new ResponseEntity<>(restaurant, HttpStatusCode.valueOf(200));
	}
}

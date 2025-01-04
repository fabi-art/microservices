package com.rewardomain.restaurant.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.restaurant.bean.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	Restaurant findByNumber(long number);

	Restaurant save(Restaurant restaurant);
	
	List<Restaurant> findAll();
	
	
//	Restaurant updateAvailability(long number, String availability);




	
}

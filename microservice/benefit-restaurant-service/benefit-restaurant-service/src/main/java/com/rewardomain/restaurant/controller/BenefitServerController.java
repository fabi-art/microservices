package com.rewardomain.restaurant.controller;

import java.util.List;


import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.rewardomain.restaurant.configuration.Configuration;
import com.rewardomain.restaurant.bean.Restaurant;
import com.rewardomain.restaurant.bean.RestaurantError;
import com.rewardomain.restaurant.repository.RestaurantRepository;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class BenefitServerController {
	
	 @Autowired
	    private Environment environment;
	//@Autowired
	//private Configuration configuration;
	//@GetMapping("/benefitserver")
	
	//public BenefitServer getBenefitServer() {
	//	return new BenefitServer(configuration.getType(),configuration.getName());
		//return new BenefitServer("unique","SRV-LOCAL-UNK001");
	//}


	
	private final RestaurantRepository repository;
	

    public BenefitServerController(RestaurantRepository repository) {
        this.repository = repository;
    }
	
  //  private Logger logger = LoggerFactory.getLogger (BenefitServerController.class);
    private Logger logger = LoggerFactory.getLogger(BenefitServerController.class);

    @Retry(name = "benefit-restaurant", fallbackMethod = "defaultResponse")
	@GetMapping("/benefit-restaurant/merchants/{merchant_number}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable long merchant_number){

    	
        logger.info("Benefit Restaurant Service call received.");

        
        Restaurant restaurant = repository.findByNumber(merchant_number);
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found for the merchant number: " + merchant_number + ".");
        }


        String port = environment.getProperty("local.server.port");
        restaurant.setExecutionChain("restaurant-service instance:" + port);

     //   return ResponseEntity.ok(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatusCode.valueOf(200));
       
	}
	
    public ResponseEntity<Restaurant> defaultResponse (RuntimeException e) {
    
    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(403);
    	return new ResponseEntity<>(new RestaurantError(404L, e.getMessage()), httpStatusCode);
    	}
	
	@PostMapping("/benefit-restaurant/merchants/")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant savedRestaurant = repository.save(restaurant);

        if(savedRestaurant == null) {
			 new ResponseEntity<>(HttpStatusCode.valueOf(404));
		}
		 return new ResponseEntity<>(savedRestaurant, HttpStatusCode.valueOf(201));
    //    return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }
	
	 @GetMapping("/benefit-restaurant/merchants/")
	    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
	        List<Restaurant> allRestaurants = repository.findAll();
	        if (allRestaurants.isEmpty()) {
	            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
	        }
	        return new ResponseEntity<>(allRestaurants, HttpStatusCode.valueOf(200));
	    }
	 
	 
	 @PutMapping("/benefit-restaurant/merchants/{merchant_number}/{availability}")
	    public ResponseEntity<Restaurant> updateAvailability(
	            @PathVariable long merchant_number,
	            @PathVariable String availability) {

	
	        Restaurant restaurant = repository.findByNumber(merchant_number);

	        if (restaurant == null) {
	            throw new RuntimeException("Restaurant non trouvé !");
	        }

	        restaurant.setAvailability(availability);
	        repository.save(restaurant);

	        return new ResponseEntity<>(restaurant, HttpStatusCode.valueOf(200));
	
	    }
	 
	

}
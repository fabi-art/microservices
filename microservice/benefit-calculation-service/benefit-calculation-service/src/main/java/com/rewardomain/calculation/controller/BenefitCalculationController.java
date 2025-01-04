package com.rewardomain.calculation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rewardomain.calculation.bean.BenefitRestaurant;
import com.rewardomain.calculation.bean.BenefitRestaurantDetails;
import com.rewardomain.calculation.bean.BenefitRestaurantError;
import com.rewardomain.calculation.proxy.BenefitRestaurantProxy;

import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;





@RestController
public class BenefitCalculationController {

	@Autowired
	private BenefitRestaurantProxy proxy;
	
	@Autowired
    private Environment environment;
	
	
//	@GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
//	public ResponseEntity<BenefitRestaurant>
//	      getBenefitAmountFeign (@PathVariable("merchant_number") long number, @PathVariable("dining_amount") double amount){
//	      
////	      HashMap<String, String> uriVariables = new HashMap<> ();
////	       uriVariables.put("merchant_number", String.valueOf(number));
////	       
////	       ResponseEntity<BenefitRestaurantDetails> responseEntity = new RestTemplate().
////	    		   getForEntity("http://localhost:8100/benefit-restaurant/merchants/{merchant_number}",
////	       BenefitRestaurantDetails.class, uriVariables);
////	    		   
////	    		   BenefitRestaurantDetails benefitCalculation = responseEntity.getBody();
////	    		   return new ResponseEntity<BenefitRestaurant>
////	    		   (new BenefitRestaurant(amount, benefitCalculation.getPercentage()), HttpStatusCode.valueOf(200));
////	       
//	   
//		  BenefitRestaurantDetails benefitRestaurantDetails = proxy.getBenefitRestaurantDetails(number);
//		   return new ResponseEntity<BenefitRestaurant>
//		   (new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage()), HttpStatusCode.valueOf(200));
//  
//		
//}
	
	
//	@GetMapping("/benefit-calculation/{merchant number}/{dining_amount}")
//	public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign ( @PathVariable("merchant_number") long number,
//	@PathVariable("dining_amount") double amount) {
//	BenefitRestaurantDetails benefitRestaurantDetails proxy.getBenefitRestaurantDetails(number);
//	return new ResponseEntity<BenefitRestaurant> (new BenefitRestaurant (amount, benefitRestaurantDetails.getPercentage()), HttpStatusCode.valueOf(200));
//	}
	

	
	
	
	private Logger logger = LoggerFactory.getLogger(BenefitCalculationController.class);

	@Retry(name = "benefit-calculation", fallbackMethod = "defaultResponse")
	@GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
	public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign(@PathVariable("merchant_number") long number, @PathVariable("dining_amount") double amount) {
	    logger.info("Appel du service Benefit Calculation reçu.");

	    try {
	        BenefitRestaurantDetails benefitRestaurantDetails = proxy.getBenefitRestaurantDetails(number);

	        if (benefitRestaurantDetails == null) {
	            logger.error("BenefitRestaurantDetails not found for the merchant number: {}", number);
	            throw new RuntimeException("Benefit calculation not found for the merchant number:" + number + ".");
	        }

	        String port = environment.getProperty("local.server.port");
	        BenefitRestaurant benefitRestaurant = new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage());
	        benefitRestaurant.setExecutionChain("calculation-service instance:" +
	                port + "== invoked => " + benefitRestaurantDetails.getExecutionChain());

	        return new ResponseEntity<>(benefitRestaurant, HttpStatusCode.valueOf(200));
	    } catch (FeignException ex) {
	        if (ex.status() == 404) {
	            logger.error("Restaurant not found for the merchant number: {}", number);
	            throw new RuntimeException("Restaurant not found for the merchant number:" + number + ".");
	        } else {
	            logger.error("Error calling BenefitRestaurantProxy: {}", ex.getMessage());
	            throw new RuntimeException("Error calling BenefitRestaurantProxy.", ex);
	        }
	    }
	}
	
	
	
//	 private Logger logger = LoggerFactory.getLogger(BenefitCalculationController.class);
//
//	    @Retry(name = "benefit-calculation", fallbackMethod = "defaultResponse")
//	@GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
//	public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign ( @PathVariable("merchant_number") long number, @PathVariable("dining_amount") double amount) {
//	    	logger.info("Benefit Calculation Service call received.");
//	BenefitRestaurantDetails benefitRestaurantDetails = proxy.getBenefitRestaurantDetails (number); 
//
//	if(benefitRestaurantDetails == null)
//	 {
//		 throw new RuntimeException ("Benefit calculation  not found for the merchant number:" + number + ".");
//	 }
//
//	String port = environment.getProperty("local.server.port");
//	 BenefitRestaurant benefitRestaurant= new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage());
//			 benefitRestaurant.setExecutionChain("calculation-service instance:" +
//						
//	port + "== invoked => " + benefitRestaurantDetails.getExecutionChain());
//	 return new ResponseEntity<BenefitRestaurant>
//	   
//	(benefitRestaurant, HttpStatusCode.valueOf(200));
//	 
//	}

	    public ResponseEntity<BenefitRestaurant> defaultResponse (RuntimeException e) {
	        
	    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(403);
	    	return new ResponseEntity<>(new BenefitRestaurantError(404L, e.getMessage()), httpStatusCode);
	    	}
	
	@GetMapping("/benefit-calculation-rest-template/{merchant_number}/{dining_amount}")
	public ResponseEntity<BenefitRestaurant>
	getBenefitAmount (@PathVariable("merchant_number")long number, @PathVariable("dining_amount") double amount){
		return null;
		
	}
	}

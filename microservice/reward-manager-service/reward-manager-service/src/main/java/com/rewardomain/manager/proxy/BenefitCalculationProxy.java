package com.rewardomain.manager.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rewardomain.manager.bean.BenefitRestaurant;

//@FeignClient(name="benefit-calculation", url="localhost:8200")
@FeignClient(name="benefit-calculation-service")
public interface BenefitCalculationProxy {
	
	
	@GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
	
	public BenefitRestaurant getBenefitRestaurant(
			@PathVariable("merchant_number") long number,
			@PathVariable("dining_amount") double amount);
}

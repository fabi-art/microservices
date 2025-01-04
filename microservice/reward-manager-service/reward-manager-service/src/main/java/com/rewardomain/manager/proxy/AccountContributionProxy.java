package com.rewardomain.manager.proxy;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Distribution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.rewardomain.manager.bean.AccountContributionResponse;


@FeignClient(name = "account-contribution-service")


public interface AccountContributionProxy {
    
	@PutMapping("/account-contribution/{credit_card_number}/reward/{reward}") 

    ResponseEntity<AccountContributionResponse> distributeReward(
    			@PathVariable("credit_card_number") String number, 
    			@PathVariable("reward") double reward);
}
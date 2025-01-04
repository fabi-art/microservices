package com.rewardomain.manager.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//import com.rewardomain.calculation.bean.BenefitRestaurant;
//import com.rewardomain.calculation.bean.BenefitRestaurantDetails;
import com.rewardomain.manager.bean.AccountContributionResponse;
import com.rewardomain.manager.bean.BenefitRestaurant;
import com.rewardomain.manager.bean.Confirmation;
import com.rewardomain.manager.bean.Dining;
import com.rewardomain.manager.bean.DiningError;
import com.rewardomain.manager.bean.Reward;
import com.rewardomain.manager.proxy.AccountContributionProxy;
import com.rewardomain.manager.proxy.BenefitCalculationProxy;
import com.rewardomain.manager.repository.RewardRepository;

import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;



@RestController
public class RewardManagerController {

	@Autowired
	private BenefitCalculationProxy bcproxy;
	
	@Autowired
	private AccountContributionProxy acproxy;
	
	@Autowired
	private RewardRepository repository;
	
	@Autowired
    private Environment environment;
	
//	@PostMapping("/reward-manager/rewards")
//	public ResponseEntity<Confirmation> createReward(@RequestBody Dining dining){
//		long merchantNumber = dining.getMerchantNumber();
//		double diningAmount = dining.getDiningAmount();
//		
//		BenefitRestaurant benefitRestaurant = bcproxy.getBenefitRestaurant(merchantNumber, diningAmount);
//		Reward reward = new Reward (
//				dining.getId() + 100, dining.getId() + 1000, benefitRestaurant.getAmount(), merchantNumber,
//				LocalDateTime.parse(dining.getDiningDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
//		
//		repository.save(reward);
//		
//		return new ResponseEntity<Confirmation>
//		(new Confirmation(reward.getConfirmationNumber()), HttpStatusCode.valueOf(200));
//	}

	

	
	
	private Logger logger = LoggerFactory.getLogger(RewardManagerController.class);

    @Retry(name = "reward-manager", fallbackMethod = "defaultResponse")
	@PostMapping("/reward-manager/rewards")
	public Confirmation createReward(@RequestBody Dining dining) {
    	logger.info("Reward Manager Service call received.");
	    long merchantNumber = dining.getMerchantNumber();
	    double diningAmount = dining.getDiningAmount();

	    BenefitRestaurant benefitRestaurant = bcproxy.getBenefitRestaurant(merchantNumber, diningAmount);
	    if(benefitRestaurant == null)
		 {
			 throw new RuntimeException ("Reward Manager  not found for the merchant number:" + merchantNumber+".");
		 }
         String port = environment.getProperty("local.server.port");
	
	 benefitRestaurant.setExecutionChain("reward-service instance:" +
	
	port + "== invoked => " + benefitRestaurant.getExecutionChain());
	    Reward reward = new Reward(
	            dining.getId() + 100,
	            dining.getId() + 1000,
	            benefitRestaurant.getAmount(),
	            merchantNumber,
	            LocalDateTime.parse(dining.getDiningDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
	    );
	    repository.save(reward);
	    Confirmation confirmation = new Confirmation(reward.getConfirmationNumber(),benefitRestaurant.getExecutionChain());
	  //  return new ResponseEntity<>(confirmation, HttpStatusCode.valueOf(200));
	    
	    return confirmation;
	
	}
	
	


    
    
    public ResponseEntity<Dining> defaultResponse (RuntimeException e) {
        
    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(403);
    	return new ResponseEntity<>(new DiningError(404L, e.getMessage()), httpStatusCode);
    	}

	
	@GetMapping("/reward-manager/rewards/{confirmation_number}")
    public ResponseEntity<Reward> getReward(@PathVariable long confirmation_number) {
        // Utilisez votre repository pour rechercher la récompense par le numéro de confirmation
        Reward reward = repository.findByConfirmationNumber(confirmation_number);

        if (reward == null) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(reward, HttpStatusCode.valueOf(200));	
}
	

	
//	@PostMapping("/reward-manager/rewards/{credit_card_number}/{reward_confirmation_number}")
//    public ResponseEntity<Confirmation> distributeRewardWithoutCreatingReward(@PathVariable String credit_card_number, @PathVariable long reward_confirmation_number) {
//        // Récupérer les détails de la récompense à partir du confirmationNumber
//        Reward reward = repository.findByConfirmationNumber(reward_confirmation_number);
//
//        // Vérifier si la récompense existe
//        if (reward == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Récupérer le port sur lequel l'application s'exécute
//        String port = environment.getProperty("local.server.port");
//
//        // Appeler le microservice « account contribution service » pour distribuer la récompense
//        ResponseEntity<AccountContributionResponse> accountContributionResponse =
//                acproxy.distributeReward(credit_card_number, reward.getAmount());
//
//        if (accountContributionResponse.getStatusCode() == HttpStatusCode.valueOf(200)) {
//            // Si la récompense a été distribuée avec succès, retourner la confirmation
//            AccountContributionResponse contributionResponse = accountContributionResponse.getBody();
//
//            // Récupérer la chaîne d'exécution de la réponse de contribution
//            String accountContributionExecutionChain = contributionResponse.getExecutionChain();
//
//            // Construction de la nouvelle chaîne d'exécution avec les informations actuelles
//            String updatedExecutionChain = "reward_manager_instance:" + port + " invoked =>" + accountContributionExecutionChain;
//
//            Confirmation confirmation = new Confirmation(reward_confirmation_number);
//            confirmation.setExecutionChain(updatedExecutionChain);
//
//            return ResponseEntity.ok(confirmation);
//        } else {
//            // Gérer les erreurs de distribution de la récompense
//            return ResponseEntity.status(accountContributionResponse.getStatusCode()).build();
//        }
//    }	

	
	
	@PostMapping("/reward-manager/rewards/{credit_card_number}/{reward_confirmation_number}")
    public ResponseEntity<AccountContributionResponse> distributeRewardWithoutCreatingReward(@PathVariable String credit_card_number, @PathVariable long reward_confirmation_number) {
        
		logger.info("Reward manager service call received");
        Reward reward = repository.findByConfirmationNumber(reward_confirmation_number);

        
        if (reward == null) {
        	throw new RuntimeException ("Reward not found");
        }

        
        String port = environment.getProperty("local.server.port");

        
        ResponseEntity<AccountContributionResponse> accountContributionResponse =
        		acproxy.distributeReward(credit_card_number, reward.getAmount());

        if (accountContributionResponse.getStatusCode() == HttpStatus.OK) {
            
            AccountContributionResponse contributionResponse = accountContributionResponse.getBody();

            
            String accountContributionExecutionChain = contributionResponse.getExecutionChain();

            
           String updatedExecutionChain = "reward_manager_instance:" + port + " invoked =>" + accountContributionResponse.getBody().getExecutionChain();

            AccountContributionResponse accountContributionResponse1 = new AccountContributionResponse(updatedExecutionChain);
            accountContributionResponse1.setCode(accountContributionResponse.getBody().getCode());
            accountContributionResponse1.setMessage(accountContributionResponse.getBody().getMessage());
            
            return ResponseEntity.ok(accountContributionResponse1);
        } else {
            return ResponseEntity.status(accountContributionResponse.getStatusCode()).build();
        }
    }
	
}
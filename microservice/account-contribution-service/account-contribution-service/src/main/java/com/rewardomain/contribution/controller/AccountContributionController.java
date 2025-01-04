package com.rewardomain.contribution.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rewardomain.contribution.bean.AccountContributionRequest;
import com.rewardomain.contribution.bean.CreditCard;
import com.rewardomain.contribution.distribution.Distribution;
import com.rewardomain.contribution.repository.AccountRepository;
import com.rewardomain.contribution.repository.BeneficiaryRepository;
import com.rewardomain.contribution.repository.CreditCardRepository;

import io.github.resilience4j.retry.annotation.Retry;


import com.rewardomain.contribution.bean.*;
import org.springframework.http.ResponseEntity;

@RestController
public class AccountContributionController {
			@Autowired
			private CreditCardRepository creditCardRepository;
			@Autowired
			private AccountRepository accountRepository;
			@Autowired
			private BeneficiaryRepository beneficiaryRepository;
			@Autowired
			private Distribution distribution;
			@Autowired
			private Environment environment;
			
			
			 private Logger logger = LoggerFactory.getLogger(AccountContributionController.class);

		
			 
			@PostMapping("/account-contribution/accounts")
			public ResponseEntity<AccountContributionResponse>
			createAccount (@RequestBody AccountContributionRequest request) {
				
			CreditCard creditCard = new CreditCard(request.getCcnumber());
		
			Account account = new Account (request.getName(), request.getAnumber()); 
			account.setCreditCard(creditCard);
			creditCardRepository.save(creditCard); 
			accountRepository.save(account);
		
			
			return new ResponseEntity<>
			(new AccountContributionResponse (201, "Account created."), HttpStatusCode.valueOf(201));
			}
			
			
			
			@GetMapping("/account-contribution/accounts/{account_number}") 
			public ResponseEntity<Account> getAccount (@PathVariable("account_number") String number) {
				Account account = accountRepository.findByNumber(number); 
				
				 String port = environment.getProperty("local.server.port");
				    account.setExecutionChain("Account Contribution service "+ port);
				
				return new ResponseEntity<Account>(account, HttpStatusCode.valueOf(200));
				}
			
			@GetMapping("/account-contribution/accounts")
			public ResponseEntity<List<Account>> getAccounts () {
				  String port = environment.getProperty("local.server.port");
				    
				    // Assurez-vous que la variable account est initialisée ou déclarée
				    Account account = new Account();  // Assurez-vous d'initialiser correctement ou de récupérer la valeur appropriée

				    account.setExecutionChain("Account Contribution service " + port);

			return new ResponseEntity<List<Account>> 
			(accountRepository.findAll(), HttpStatusCode.valueOf(200));
			}
			
			
			 @Retry(name = "account-contribution", fallbackMethod = "defaultResponse")
			@PostMapping("/account-contribution/accounts/{account_number}/beneficiaries")
			public
			ResponseEntity<AccountContributionResponse> createBeneficiary ( @RequestBody AccountContributionRequest request, @PathVariable("account_number") String anumber) {
				 logger.info("Account Contribution Service call received.");
			Beneficiary beneficiary = new Beneficiary(request.getPercentage(), request.getName());
			Account account = accountRepository.findByNumber(anumber);
			if (account != null) {
			beneficiary.setAccount(account);
			beneficiaryRepository.save(beneficiary);
			return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse (201, "Beneficiary created and added."), HttpStatusCode.valueOf(201));
			}
			else
			 {
				 throw new RuntimeException ("Account Contribution  not found for the merchant number:" + anumber + ".");
			 }
		//	return new ResponseEntity<AccountContributionResponse>
		//	(new AccountContributionResponse(404, "Account not found."), HttpStatusCode.valueOf(404));
			}
			 public ResponseEntity<AccountContributionRequest> defaultResponse (RuntimeException e) {
			        
			    	HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(403);
			    	return new ResponseEntity<>(new AccountContributionRequestError(404L, e.getMessage()), httpStatusCode);
			    	}
			 
			 
			@PutMapping("/account-contribution/beneficiaries/{id}")
			public ResponseEntity<AccountContributionResponse> updateBeneficiary
			(@RequestBody AccountContributionRequest request, @PathVariable long id) {
			Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(id);
			if (beneficiary.isPresent()) {
			beneficiary.get().setPercentage(request.getPercentage());
			beneficiaryRepository.save(beneficiary.get()); 
			return new ResponseEntity<AccountContributionResponse>
			(new AccountContributionResponse(200, "Beneficiary updated."), HttpStatusCode.valueOf(200));
			} else return new ResponseEntity<AccountContributionResponse> 
			(new AccountContributionResponse(404, "Beneficiary not found."), HttpStatusCode.valueOf(404));
			}
			
			@GetMapping("/account-contribution/accounts/{account_number}/beneficiaries")
			public ResponseEntity<List<Beneficiary>>
			getAccounts (@PathVariable("account_number") String number) {
			Account account = accountRepository.findByNumber(number);
			if (account != null) {
			return new ResponseEntity<List<Beneficiary>> 
			(account.getBeneficiaries(), HttpStatusCode.valueOf(200));
			}
			return new ResponseEntity<List<Beneficiary>> (new ArrayList<>(), HttpStatusCode.valueOf(404));
			}
			
			@PutMapping("/account-contribution/{credit_card_number}/reward/{reward}")
			public ResponseEntity<AccountContributionResponse> distributeReward(
			        @PathVariable("credit_card_number") String number, 
			        @PathVariable double reward) {

			    Optional<Account> optional = accountRepository.findByCreditCard_Number(number);

			    if (optional.isEmpty()) {
			        return new ResponseEntity<>(
			                new AccountContributionResponse(404, "Account not found."),
			                HttpStatusCode.valueOf(404));
			    } else {
			        Account account = optional.get();
			        if (account.isValid()) {
			            account.setBenefits(account.getBenefits() + reward);
			            List<Beneficiary> beneficiaries = account.getBeneficiaries();
			            distribution.distribute(beneficiaries, reward);
			            accountRepository.save(account);

			            // Récupérer le port sur lequel l'application s'exécute
			            String port = environment.getProperty("local.server.port");

			            // Utiliser la méthode setExecutionChain pour mettre à jour la chaîne d'exécution
			            AccountContributionResponse accountContributionResponse = new AccountContributionResponse(200, "Reward distributed.");
			            accountContributionResponse.setExecutionChain("account-contribution-service instance:" + port);

			            return new ResponseEntity<>(accountContributionResponse, HttpStatusCode.valueOf(200));
			        } else {
			            return new ResponseEntity<>(
			                    new AccountContributionResponse(403, "Account is invalid."),
			                    HttpStatusCode.valueOf(403));
			        }
			    }
			}


			
//			@PutMapping("/account-contribution/{credit_card_number}/reward/{reward}")
//			public ResponseEntity<AccountContributionResponse> distributeReward(
//			        @PathVariable("credit_card_number") String number, @PathVariable double reward) {
//			    Optional<Account> optional = accountRepository.findByCreditCard_Number(number);
//			    if (optional.isEmpty()) {
//			        return new ResponseEntity<>(
//			                new AccountContributionResponse(404, "Account not found."),
//			                HttpStatusCode.valueOf(403));
//			    } else {
//			        Account account = optional.get();
//			        if (account.isValid()) {
//			            account.setBenefits(account.getBenefits() + reward);
//			            List<Beneficiary> beneficiaries = account.getBeneficiaries();
//			            distribution.distribute(beneficiaries, reward);
//			            accountRepository.save(account);
//
//			            // Récupérer le port sur lequel l'application s'exécute
//			            String port = environment.getProperty("local.server.port");
//
//			            // Construire la chaîne d'exécution avec le port de l'instance
//			            String executionChain = "account-service instance:" + port +
//			                    " => Reward distributed to account with credit card number: " + number;
//
//			            // Inclure la chaîne d'exécution dans la réponse
//			            return new ResponseEntity<>(
//			                    new AccountContributionResponse(200, executionChain),
//			                    HttpStatusCode.valueOf(200));
//			        } else {
//			            return new ResponseEntity<>(
//			                    new AccountContributionResponse(403, "Account is invalid."),
//			                    HttpStatusCode.valueOf(403));
//			        }
//			    }
//			}

			
			
			
//			@PutMapping("/account-contribution/{credit_card_number}/reward/{reward}") 
//			public ResponseEntity<AccountContributionResponse> distributeReward (
//					@PathVariable("credit_card_number") String number, @PathVariable double reward) {
//					Optional<Account> optional = accountRepository.findByCreditCard_Number(number);
//					if (optional.isEmpty())
//					return new ResponseEntity<AccountContributionResponse>
//					(new AccountContributionResponse(404, "Account not found."), HttpStatusCode.valueOf(404));
//					else {
//					Account account = optional.get();
//					if (account.isValid()) {
//					account.setBenefits(account.getBenefits() + reward);
//					List<Beneficiary> beneficiaries = account.getBeneficiaries();
//					distribution.distribute(beneficiaries, reward);
//					accountRepository.save(account);
//					return new ResponseEntity<AccountContributionResponse>
//				 
//					(new AccountContributionResponse(200, "Reward distributed."), HttpStatusCode.valueOf(200));
//					} else return new ResponseEntity<AccountContributionResponse>
//					(new AccountContributionResponse (403, "Account is invalid."), HttpStatusCode.valueOf(403));
//					}
//					}

}
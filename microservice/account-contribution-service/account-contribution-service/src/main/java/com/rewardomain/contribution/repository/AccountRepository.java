package com.rewardomain.contribution.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.contribution.bean.Account;

public interface AccountRepository extends JpaRepository <Account, Long> {
		public Account findByNumber (String number);
		Optional<Account> findByCreditCard_Number (String cardNumber);
}
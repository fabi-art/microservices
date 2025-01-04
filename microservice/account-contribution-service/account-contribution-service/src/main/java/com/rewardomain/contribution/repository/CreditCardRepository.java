package com.rewardomain.contribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.contribution.bean.CreditCard;

public interface CreditCardRepository extends JpaRepository <CreditCard, Long> {}
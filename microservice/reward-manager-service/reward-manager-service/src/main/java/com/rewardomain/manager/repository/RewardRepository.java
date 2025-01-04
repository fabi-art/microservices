package com.rewardomain.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.manager.bean.*;
public interface RewardRepository extends JpaRepository <Reward, Long>{


	Reward findByConfirmationNumber(long confirmationNumber);


}

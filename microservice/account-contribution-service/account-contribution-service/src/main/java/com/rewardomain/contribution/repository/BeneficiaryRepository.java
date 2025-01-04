package com.rewardomain.contribution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewardomain.contribution.bean.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository <Beneficiary, Long> {}
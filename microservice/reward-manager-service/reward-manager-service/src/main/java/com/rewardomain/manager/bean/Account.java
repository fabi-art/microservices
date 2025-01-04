package com.rewardomain.manager.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class Account {
	//@Id
	//@GeneratedValue(strategy = GenerationType. IDENTITY) private Long id;
	//@Column
	Long id;
	private String owner;
	@JsonProperty("account_number")
	//@Column(name = "account_number")
	private String number;
	//@Column(name = "total_benefits") 
	private double benefits;
	
	@JsonIgnore
	@OneToMany (mappedBy = "account")
	private List<Beneficiary> beneficiaries = new ArrayList<>();

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_card_id")
	private CreditCard creditCard;
	
	
	 @Transient
	    private String executionChain;
	 
	
	public Account(Long id, String owner, String number, double benefits, List<Beneficiary> beneficiaries,
			CreditCard creditCard, String executionChain) {
		super();
		this.id = id;
		this.owner = owner;
		this.number = number;
		this.benefits = benefits;
		this.beneficiaries = beneficiaries;
		this.creditCard = creditCard;
		this.executionChain = executionChain;
	}





	public String getExecutionChain() {
		return executionChain;
	}





	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}





	public Account() {}

	
	
	

public Account(String owner, String number) {
		super();
		this.owner = owner;
		this.number = number;
	}


	public boolean isValid() {
		double totalPercentage = 0.0;
		for (Beneficiary beneficiary: beneficiaries) {
		totalPercentage += beneficiary.getPercentage();
		}
		return totalPercentage == 100.0? true : false;
		}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public double getBenefits() {
		return benefits;
	}


	public void setBenefits(double benefits) {
		this.benefits = benefits;
	}


	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}


	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}


	public CreditCard getCreditCard() {
		return creditCard;
	}


	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	


	
}
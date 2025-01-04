package com.rewardomain.manager.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

public class Beneficiary { 
//			@Id
//		
//		@GeneratedValue(strategy = GenerationType. IDENTITY)
		private Long id;
//		@Column(name="allocation_percentage") 
		private double percentage;
	//	@Column
		private String name;
		//@Column
		private double savings;
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name = "account_id")
		private Account account;
		
		 @Transient
		    private String executionChain;
		 
		 
		
		
		public Beneficiary(Long id, double percentage, String name, double savings, Account account,
				String executionChain) {
			super();
			this.id = id;
			this.percentage = percentage;
			this.name = name;
			this.savings = savings;
			this.account = account;
			this.executionChain = executionChain;
		}
		public String getExecutionChain() {
			return executionChain;
		}
		public void setExecutionChain(String executionChain) {
			this.executionChain = executionChain;
		}
		public Beneficiary() {}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public double getPercentage() {
			return percentage;
		}
		public void setPercentage(double percentage) {
			this.percentage = percentage;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getSavings() {
			return savings;
		}
		public void setSavings(double savings) {
			this.savings = savings;
		}
		public Account getAccount() {
			return account;
		}
		public void setAccount(Account account) {
			this.account = account;
		}
		public Beneficiary(Long id, double percentage, String name, double savings, Account account) {
			super();
			this.id = id;
			this.percentage = percentage;
			this.name = name;
			this.savings = savings;
			this.account = account;
		}
		public Beneficiary(double percentage, String name) {
			super();
			this.percentage = percentage;
			this.name = name;
		}
	

		
}
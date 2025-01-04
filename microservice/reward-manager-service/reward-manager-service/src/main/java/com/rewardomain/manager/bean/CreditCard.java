package com.rewardomain.manager.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

//@Entity
public class CreditCard { 
//		@Id
//		@GeneratedValue(strategy = GenerationType.IDENTITY) 
		private Long id;
//		@Column(name="credit_card_number") 
		private String number;
		@OneToOne (mappedBy = "creditCard") 
		private Account account;
		 @Transient
		    private String executionChain;
		 
		
		
		public CreditCard(Long id, String number, Account account, String executionChain) {
			super();
			this.id = id;
			this.number = number;
			this.account = account;
			this.executionChain = executionChain;
		}
		public String getExecutionChain() {
			return executionChain;
		}
		public void setExecutionChain(String executionChain) {
			this.executionChain = executionChain;
		}
		public CreditCard() {}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public Account getAccount() {
			return account;
		}
		public void setAccount(Account account) {
			this.account = account;
		}
		public CreditCard(Long id, String number, Account account) {
			super();
			this.id = id;
			this.number = number;
			this.account = account;
		}
		public CreditCard(String number) {
			super();
			this.number = number;
		}
		
		

}
package com.rewardomain.restaurant.bean;

public class BenefitServer {

	private String name;
	private String type;
	
	
	public BenefitServer(String type, String name) {
          this.type=type;
          this.name=name;
		
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}

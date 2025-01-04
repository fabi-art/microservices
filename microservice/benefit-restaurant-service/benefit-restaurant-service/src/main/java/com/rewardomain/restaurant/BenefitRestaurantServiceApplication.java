package com.rewardomain.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class BenefitRestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenefitRestaurantServiceApplication.class, args);
	}
//	public static void main(String[] args) {
//        new SpringApplicationBuilder(BenefitRestaurantServiceApplication.class)
//            .web(WebApplicationType.REACTIVE)
//            .run(args);
//	}
}

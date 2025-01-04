package com.rewardomain.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringCloudGatewayApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(SpringCloudGatewayApplication.class, args);
//	}
//
//	
	public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudGatewayApplication.class)
            .web(WebApplicationType.REACTIVE)
            .run(args);
    }
}
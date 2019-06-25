package com.dri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.dri.filter.MyFilter;
import com.dri.filter.RateLimiterFilter;

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(GatewayApplication.class, args);
	}

	 @Bean public MyFilter myFilter() 
	 { return new MyFilter(); }
	
	/*
	 * @Bean public RateLimiterFilter rateLimiterFilter() { return new
	 * RateLimiterFilter(); }
	 */
}

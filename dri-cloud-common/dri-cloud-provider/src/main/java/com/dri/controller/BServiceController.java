package com.dri.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BServiceController {

	@Value("${server.port}")
	String port;
	
	@Value("${spring.application.name}")
	String serviceName;

	@GetMapping("/get")
	public String query(String param) throws Exception {	
		return "query B service";
	}
	
}

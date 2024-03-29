package com.dri.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * https://cloud.tencent.com/developer/article/1333796
 * 
 * 
 * @author 
 *
 */

@Component
public class ProducerFallback implements FallbackProvider {

	private final Logger logger = LoggerFactory.getLogger(ProducerFallback.class);
	
	@Override
	public String getRoute() {
		
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				
				HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				
				return new ByteArrayInputStream("The service is unavailable.".getBytes());
			}
			
			@Override
			public String getStatusText() throws IOException {
				
				return "OK";
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				
				return 200;
			}
			
			@Override
			public void close() {
				
				
			}
		};
		
		
		
	    
	}
	
	
	/*
	 * public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
	 * if (cause != null && cause.getCause() != null) { String reason =
	 * cause.getCause().getMessage(); logger.info("Excption {}", reason); } return
	 * fallbackResponse(); }
	 */

}

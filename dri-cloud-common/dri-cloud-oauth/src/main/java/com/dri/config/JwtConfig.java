package com.dri.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@ConditionalOnProperty(prefix = "dri.security.oauth2",name = "tokenStore",havingValue = "jwt",matchIfMissing = true )
public class JwtConfig {
	
	
	@Bean
	public TokenStore jwtTokenStore() {
		
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter =new JwtAccessTokenConverter();
		converter.setSigningKey("test");
		return converter;
		
	}

}

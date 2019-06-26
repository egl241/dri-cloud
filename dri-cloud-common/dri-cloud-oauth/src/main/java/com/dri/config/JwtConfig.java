package com.dri.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.dri.properties.SecurityProperties;

@Configuration
@ConditionalOnProperty(prefix = "dri.security.oauth2",name = "tokenStore",havingValue = "jwt",matchIfMissing = true )
public class JwtConfig {
	
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	public TokenStore jwtTokenStore() {
		
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter =new JwtAccessTokenConverter();
		//设置JWTtoken签名的秘钥
		converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
		return converter;
		
	}

}

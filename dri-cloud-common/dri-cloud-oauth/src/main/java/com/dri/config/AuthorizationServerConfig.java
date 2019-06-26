package com.dri.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.dri.properties.OAuth2ClientProperties;
import com.dri.properties.OAuth2Properties;
import com.dri.properties.SecurityProperties;


/**
* 授权服务器配置
* @ EnableAuthorizationServer 启用授权服务
*/

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;  // 认证管理器
	
	//@Autowired
	//private RedisConnectionFactory redisConnectionFactory; // redis连接工厂
	
	
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	 /**
     * 令牌存储
     * @return redis令牌存储对象
     */
	@Bean
	public TokenStore tokenStore() {
		//return new RedisTokenStore(redisConnectionFactory);
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		        .checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		//遍历数组
		for( OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
			builder.withClient(client.getClientId())
		       .secret(client.getClientSecret())
		       .authorizedGrantTypes("password", "authorization_code", "refresh_token")
		       .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
		       .scopes("all");
		       //.and()
		       //.withClient("webapp")
		       //.scopes("xx")
		       //.authorizedGrantTypes("implicit");
		}
		
		
		
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.tokenStore(tokenStore());
		
		if(jwtAccessTokenConverter!=null) {
			endpoints.accessTokenConverter(jwtAccessTokenConverter);
		}
		
	}
	
	
	
}

package com.dri.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(3)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/**
     * http安全配置
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();  // 禁用csrf
	}

	
	
}

package com.dri.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.dri.security.DomainUserDetailsService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	/**
	 * 注入用户信息服务
	 * 
	 * @return 用户信息服务对象
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new DomainUserDetailsService();
	}

	// @Autowired
	// private DomainUserDetailsService userDetailsService;

	/**
	 * 全局用户信息
	 * 
	 * @param auth 认证管理
	 * @throws Exception 用户认证异常信息
	 */

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService()).passwordEncoder(new
		// MyPasswordEncoder()); 
		System.out.println("MySecurityConfigMySecurityConfig configureGlobal(AuthenticationManagerBuilder auth)");
		auth.userDetailsService(userDetailsService());

		//auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
		//		.password(new BCryptPasswordEncoder().encode("admin")).roles("USER");
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	/**
	 * http安全配置
	 * 
	 * @param http http安全对象
	 * @throws Exception http安全异常信息
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		System.out.println("MySecurityConfig configure(HttpSecurity http)");
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and()
				.httpBasic().and().csrf().disable();
	}

}

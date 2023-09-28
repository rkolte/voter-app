package com.voter.conifg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.voter.serviceImp.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {

		logger.info("Security check has been started");
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/voter/**").permitAll()
				.antMatchers(HttpMethod.POST, "/voter/vote/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/candidate/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/candidate/**").authenticated()
				.antMatchers(HttpMethod.POST, "/candidate/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/candidate/**").hasRole("ADMIN").and().httpBasic();
		logger.info("Security check has been completed");

	}

}
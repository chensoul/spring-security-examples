package com.chensoul.security.xss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class XssSecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// Ignoring here is only for this example. Normally people would apply their own authentication/authorization policies
		return (web) -> web.ignoring()
			.antMatchers("/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().xssProtection()
			.and()
			.contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline';");
		return http.build();
	}
}

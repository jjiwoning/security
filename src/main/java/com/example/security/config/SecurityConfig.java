package com.example.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.security.auth.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final static String[] ALLOWED_URLS = new String[] {"/", "/sign-up", "/sign-in"};

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.headers(
				headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // h2 console use
			.authorizeHttpRequests(requests ->
				requests.requestMatchers(ALLOWED_URLS).permitAll() // main page permit
					.requestMatchers(PathRequest.toH2Console()).permitAll() // h2 console permit
					.anyRequest().authenticated() // need authenticated
			)
			.sessionManagement(sessionManagement ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // not use session -> disable
			.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

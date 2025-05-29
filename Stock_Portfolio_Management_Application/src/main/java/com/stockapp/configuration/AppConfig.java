package com.stockapp.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * General application-level beans like password encoder and HTTP client.
 */

@Configuration
public class AppConfig {
	
	// Used to securely hash passwords
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    // Used to call external APIs (e.g., for fetching stock prices)
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
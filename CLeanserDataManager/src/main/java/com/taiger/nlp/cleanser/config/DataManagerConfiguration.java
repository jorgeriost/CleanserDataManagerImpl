package com.taiger.nlp.cleanser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataManagerConfiguration {

	@Bean
	public Constants constants() {
		return new Constants();
	}
	
	
}

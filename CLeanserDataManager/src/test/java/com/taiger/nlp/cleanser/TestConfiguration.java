package com.taiger.nlp.cleanser;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taiger.nlp.cleanser.impl.OCRFailsDataAccessImpl;
import com.taiger.nlp.cleanser.interfaces.OCRFailsDataAccess;

@Configuration
public class TestConfiguration {

	@Bean
	public OCRFailsDataAccess rulesImpl () {
		return Mockito.mock(OCRFailsDataAccessImpl.class);
	}
	
}

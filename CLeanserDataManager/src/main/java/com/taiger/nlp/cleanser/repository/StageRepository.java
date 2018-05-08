package com.taiger.nlp.cleanser.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.taiger.nlp.cleanser.model.Stage;


public interface StageRepository extends PagingAndSortingRepository<Stage, String>{
	
	Stage findByTypeAndSubtypeAndCountryAndLanguage (String type, String subtype, String country, String language);

}

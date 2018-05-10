package com.taiger.nlp.cleanser.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.taiger.nlp.cleanser.model.Correction;
import com.taiger.nlp.cleanser.model.Stage;


public interface CorrectionRepository extends PagingAndSortingRepository<Correction, String>{
	
	List<Correction> findByKnownErrorAndStage (String knownError, Stage stage);
	
	List<Correction> findByWordAndStage (String word, Stage stage);
	
	List<Correction> findByStage (Stage stage);
	
	List<Correction> findByWordAndKnownErrorAndStage (String word, String knownError, Stage stage);

}

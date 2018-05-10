package com.taiger.nlp.cleanser.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiger.nlp.cleanser.model.OcrRule;


public interface RuleRepository extends PagingAndSortingRepository<OcrRule, String>{
	
	@Transactional
	List<OcrRule> removeByA(@Param("a") String a);
	
	@Transactional
	List<OcrRule> removeByB(@Param("b") String b);
	
	@Transactional
	List<OcrRule> removeByAAndB (@Param("a") String a, @Param("b") String b);
	
	List<OcrRule> findByA (String a);
	
	List<OcrRule> findByB (String b);
	
	List<OcrRule> findByAAndB (String a, String b);

}

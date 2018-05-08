package com.taiger.nlp.cleanser.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiger.nlp.cleanser.model.Rule;


public interface RuleRepository extends PagingAndSortingRepository<Rule, String>{
	
	@Transactional
	List<Rule> removeByA(@Param("a") String a);
	
	@Transactional
	List<Rule> removeByB(@Param("b") String b);
	
	@Transactional
	List<Rule> removeByAAndB (@Param("a") String a, @Param("b") String b);
	
	List<Rule> findByA (String a);
	
	List<Rule> findByB (String b);

}

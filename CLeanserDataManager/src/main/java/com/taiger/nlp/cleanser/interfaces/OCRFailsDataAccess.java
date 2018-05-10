package com.taiger.nlp.cleanser.interfaces;

import java.util.List;
import java.util.Map;

import com.taiger.nlp.cleanser.tmp.Rule;



public interface OCRFailsDataAccess {

	List<Rule> getRules ();
	
	Map<String, Double> applyRule (String left);
	
}

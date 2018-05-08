package com.taiger.nlp.cleanser.interfaces;

import java.util.List;

import com.taiger.nlp.cleanser.model.Rule;



public interface OCRFailsDataAccess {

	List<Rule> getRules ();
	
	int removeRule (Rule r);
	
	int setRule (Rule r);
	
	List<String> applyRule (String left);
	
}

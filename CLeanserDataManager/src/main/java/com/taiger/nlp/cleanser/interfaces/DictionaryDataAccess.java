package com.taiger.nlp.cleanser.interfaces;

import java.util.Map;
import java.util.Set;

import com.taiger.nlp.cleanser.model.Pair;



public interface DictionaryDataAccess {
	
	Set<String> getWords ();
	
	Map<String, Pair<String, Double>> getKnownFails ();
	
	Map<String, Set<String>> getAllKnownFails ();
	
	boolean checkWord (String word);
	
	Set<String> getCorrection (String word);
	
	int setCorrection (String word, String correction);
	
}

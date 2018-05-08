package com.taiger.nlp.cleanser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.taiger.nlp.cleanser.config.Constants;
import com.taiger.nlp.cleanser.interfaces.DictionaryDataAccess;
import com.taiger.nlp.cleanser.model.Correction;
import com.taiger.nlp.cleanser.model.Pair;
import com.taiger.nlp.cleanser.model.Stage;
import com.taiger.nlp.cleanser.repository.CorrectionRepository;
import com.taiger.nlp.cleanser.repository.StageRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DictionaryDataAccessImpl implements DictionaryDataAccess {

	private Stage stage;
	
	@Autowired
	private CorrectionRepository correctionRepo;
	
	@Autowired
	private StageRepository stageRepo;
	
	
	public DictionaryDataAccessImpl (Stage stage) {
		loadStage(stage);
	}
	
	public DictionaryDataAccessImpl loadStage (Stage stage) {
		this.stage = stageRepo.findByTypeAndSubtypeAndCountryAndLanguage(stage.getType(), stage.getSubtype(), stage.getCountry(), stage.getLanguage());
		return this;
	}
	
	@Override
	public Set<String> getWords() {
		Set<String> words = new HashSet<>();
		correctionRepo.listWordsByStage(stage.getId()).forEach(words::add);
		// correctionRepo.listKnownErrorsByStage(stage.getId()).forEach(words::add);
		return words;
	}

	@Override
	public boolean checkWord(String word) {
		return correctionRepo.checkWord(stage.getId(), word) > 0;
	}

	@Override
	public Set<String> getCorrection(String word) {
		Set<String> result = new HashSet<>();
		List<Correction> corrs = correctionRepo.findByKnownError(word);
		// List<Correction> corrs = correctionRepo.findByWord(word);
		corrs.forEach(c -> result.add(c.getWord()));
		return result;
	}

	@Override
	public int setCorrection(String word, String correction) {
		Correction correctionNew = new Correction();
		correctionNew.setKnownError(word);
		correctionNew.setWord(correction);
		if (correctionNew.getStages() == null) {
			correctionNew.setStages(new ArrayList<>());
		}
		correctionNew.getStages().add(this.stage);
		correctionNew = correctionRepo.save(correctionNew);
		if (correctionNew == null || correctionNew.getId().length() == 0) {
			return Constants.INSERT_ERROR;
		}
		return Constants.OK;
	}

	@Override
	public Map<String, Pair<String, Double>> getKnownFails() {
		Map<String, Pair<String, Double>> result = new HashMap<>();
		for (Correction c : correctionRepo.listByStage(stage.getId())) {
			if (!result.containsKey(c.getKnownError())) {
				result.put(c.getKnownError(), new Pair<String, Double>(c.getWord(), 0.0));
			}
		}
		return result;
	}

	@Override
	public Map<String, Set<String>> getAllKnownFails() {
		Map<String, Set<String>> result = new HashMap<>();
		for (Correction c : correctionRepo.listByStage(stage.getId())) {
			if (!result.containsKey(c.getKnownError())) {
				result.put(c.getKnownError(), new HashSet<>());
			}
			result.get(c.getKnownError()).add(c.getWord());
		}
		return result;
	}

}









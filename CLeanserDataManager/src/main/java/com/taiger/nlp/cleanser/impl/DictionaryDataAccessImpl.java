package com.taiger.nlp.cleanser.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import com.taiger.nlp.cleanser.interfaces.DictionaryDataAccess;
import com.taiger.nlp.cleanser.model.Correction;
import com.taiger.nlp.cleanser.model.Stage;
import com.taiger.nlp.cleanser.repository.CorrectionRepository;
import com.taiger.nlp.cleanser.repository.StageRepository;
import com.taiger.nlp.cleanser.tmp.DocType;
import com.taiger.nlp.cleanser.tmp.Pair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DictionaryDataAccessImpl implements DictionaryDataAccess {

	Stage stage;
	
	CorrectionRepository correctionRepo;
	
	StageRepository stageRepo;
	
	public DictionaryDataAccessImpl (CorrectionRepository correctionRepo, StageRepository stageRepo, Stage stage) {
		Assert.notNull(stageRepo,"'stageRepo' must not be null");
		Assert.notNull(correctionRepo,"'correctionRepo' must not be null");
		Assert.notNull(stage,"'stage' must not be null");
		this.correctionRepo = correctionRepo;
		this.stageRepo = stageRepo;
		loadStage(stage);
	}
	
	public DictionaryDataAccessImpl (CorrectionRepository correctionRepo, StageRepository stageRepo, DocType docType) {
		Assert.notNull(stageRepo,"'stageRepo' must not be null");
		Assert.notNull(correctionRepo,"'correctionRepo' must not be null");
		Assert.notNull(docType,"'docType' must not be null");
		this.correctionRepo = correctionRepo;
		this.stageRepo = stageRepo;
		loadStage(docType);
	}
	
	public DictionaryDataAccessImpl loadStage (Stage stage) {
		Assert.notNull(stageRepo,"'stageRepo' must not be null");
		this.stage = stageRepo.findByTypeAndSubtypeAndCountryAndLanguage(stage.getType(), stage.getSubtype(), stage.getCountry(), stage.getLanguage());
		Assert.notNull(this.stage,"'this.stage' must not be null");
		return this;
	}
	
	public DictionaryDataAccessImpl loadStage (DocType docType) {
		Assert.notNull(stageRepo,"'stageRepo' must not be null");
		this.stage = stageRepo.findByTypeAndSubtypeAndCountryAndLanguage(docType.getType(), docType.getSubtype(), docType.getCountry(), docType.getLanguage());
		Assert.notNull(this.stage,"'this.stage' must not be null");
		return this;
	}
	
	/**
	 * Gets all words in the dictionary for the used stage
	 */
	@Override
	public Set<String> getWords() {
		Set<String> words = new HashSet<>();
		correctionRepo.findByStage(stage).forEach(corr -> words.add(corr.getWord()));
		return words;
	}

	/**
	 * Checks if a word is in the dictionary for the used stage
	 */
	@Override
	public boolean checkWord(String word) {
		Assert.hasText(word, "'word' must have text");
		return !correctionRepo.findByWordAndStage(word, this.stage).isEmpty();
	}

	/**
	 * Gets all correct words associated with the known error for the used stage
	 */
	@Override
	public Set<String> getCorrection(String knownFail) {
		Assert.hasText(knownFail, "'knownFail' must have text");
		Set<String> result = new HashSet<>();
		correctionRepo.findByKnownErrorAndStage(knownFail, this.stage).forEach(c -> result.add(c.getWord()));
		return result;
	}

	/**
	 * Inserts a new correction (word/knownFail pair) for the used stage
	 */
	@Override
	public void setCorrection(String word, String knownFail) {
		Assert.hasText(word, "'word' must have text");
		Assert.hasText(knownFail, "'knownFail' must have text");
		if (!checkCorrectionExistence(word, knownFail)) {
			Correction corr = new Correction();
			corr.setKnownError(knownFail);
			corr.setWord(word);
			corr.setStage(this.stage);
			
			correctionRepo.save(corr);
		}
	}
	
	private boolean checkCorrectionExistence (String word, String knownFail) {
		Correction corr = getCorrection (word, knownFail);
		return corr != null;
	}
	
	private Correction getCorrection (String word, String knownFail) {
		List<Correction> result = correctionRepo.findByWordAndKnownErrorAndStage(word, knownFail, this.stage);
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Pair<String, Double>> getKnownFails() {
		Map<String, Pair<String, Double>> result = new HashMap<>();
		for (Correction c : correctionRepo.findByStage(this.stage)) {
			if (!result.containsKey(c.getKnownError())) {
				result.put(c.getKnownError(), new Pair<String, Double>(c.getWord(), 0.0));
			}
		}
		return result;
	}

	@Override
	public Map<String, Set<String>> getAllKnownFails() {
		Map<String, Set<String>> result = new HashMap<>();
		for (Correction c : correctionRepo.findByStage(this.stage)) {
			if (!result.containsKey(c.getKnownError())) {
				result.put(c.getKnownError(), new HashSet<>());
			}
			result.get(c.getKnownError()).add(c.getWord());
		}
		return result;
	}

}









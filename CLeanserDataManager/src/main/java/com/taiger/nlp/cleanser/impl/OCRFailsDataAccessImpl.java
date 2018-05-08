package com.taiger.nlp.cleanser.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.taiger.nlp.cleanser.config.Constants;
import com.taiger.nlp.cleanser.interfaces.OCRFailsDataAccess;
import com.taiger.nlp.cleanser.model.Rule;
import com.taiger.nlp.cleanser.repository.RuleRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OCRFailsDataAccessImpl implements OCRFailsDataAccess {
	
	@Autowired
	private RuleRepository rulesRepo;

	@Override
	public List<Rule> getRules() {
		return (List<Rule>) rulesRepo.findAll();
	}

	@Override
	public int setRule(Rule r) {
		if (r != null && !r.getA().isEmpty() && !r.getB().isEmpty()) {
			Rule inserted = rulesRepo.save(r);
			if (inserted == null) {
				return Constants.INSERT_ERROR;
			}
			return Constants.OK;
		}
		return Constants.UNKNOWN_ERROR;
	}

	@Override
	public List<String> applyRule(String left) {
		List<Rule> foundLeft = rulesRepo.findByA(left);
		List<Rule> foundRight = rulesRepo.findByB(left);
		
		List<String> result = new ArrayList<>();
		foundLeft.forEach(r -> result.add(r.getB()));
		foundRight.forEach(r -> result.add(r.getA()));
		
		return result;
	}

	@Override
	public int removeRule(Rule r) {
		List<Rule> result = rulesRepo.removeByAAndB(r.getA(), r.getB());
		if (result == null) {
			return Constants.DELETE_ERROR;
		}
		result = rulesRepo.removeByAAndB(r.getB(), r.getA());
		if (result == null) {
			return Constants.DELETE_ERROR;
		}
		
		return Constants.OK;
	}

}

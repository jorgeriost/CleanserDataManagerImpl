package com.taiger.nlp.cleanser.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.taiger.nlp.cleanser.interfaces.OCRFailsDataAccess;
import com.taiger.nlp.cleanser.model.OcrRule;
import com.taiger.nlp.cleanser.repository.RuleRepository;
import com.taiger.nlp.cleanser.tmp.Rule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OCRFailsDataAccessImpl implements OCRFailsDataAccess {
	
	private RuleRepository ruleRepo;
	

	public OCRFailsDataAccessImpl (RuleRepository ruleRepo) {
		Assert.notNull(ruleRepo,"'ruleRepo' must not be null");
		this.ruleRepo = ruleRepo;
	}

	@Override
	public List<Rule> getRules() {
		List<Rule> result = new ArrayList<>();
		((List<OcrRule>)ruleRepo.findAll()).forEach(r -> result.add(ocrRule2Rule(r)));
		return result;
	}

	public Rule setRule(Rule rule) {
		OcrRule r = rule2ocrRule(rule);
		
		if (checkRuleExistence(r)) {
			return rule;
		}
		
		r = ruleRepo.save(r);
		Assert.notNull(r,"rule saving failed");
		
		return ocrRule2Rule(r);
	}
	
	private boolean checkRuleExistence (OcrRule ocrRule) {
		checkOcrRuleCorrectness(ocrRule);
		List<OcrRule> rules = ruleRepo.findByAAndB(ocrRule.getA(), ocrRule.getB());
		if (!rules.isEmpty()) {
			return true;
		}
		rules = ruleRepo.findByAAndB(ocrRule.getB(), ocrRule.getA());
		return !rules.isEmpty();
	}

	@Override
	public Map<String, Double> applyRule(String left) {
		Assert.hasText(left, "'left' cant be empty");
		List<OcrRule> foundLeft = ruleRepo.findByA(left);
		List<OcrRule> foundRight = ruleRepo.findByB(left);
		
		Map<String, Double> result = new LinkedHashMap<>();
		foundLeft.forEach(r -> result.put(r.getB(), r.getWeight()));
		foundRight.forEach(r -> result.put(r.getA(), r.getWeight()));
		
		return result;
	}

	public Rule removeRule(Rule rule) {
		OcrRule ocrRule = rule2ocrRule(rule);
		
		if (checkRuleExistence(ocrRule)) {
			List<OcrRule> result = ruleRepo.removeByAAndB(ocrRule.getA(), ocrRule.getB());
			Assert.notNull(result,"rule deleting failed");
			if (!result.isEmpty()) {
				return ocrRule2Rule(result.get(0));
			}
		}
		
		return null;
	}
	
	private Rule ocrRule2Rule (OcrRule ocrRule) {
		checkOcrRuleCorrectness(ocrRule);
		Rule rule = new Rule();
		rule.setLeft(ocrRule.getA());
		rule.setRight(ocrRule.getB());
		rule.setWeight(ocrRule.getWeight());
		rule.setBefore("");
		rule.setOffset(0);
		
		return rule;
	}
	
	private OcrRule rule2ocrRule (Rule rule) {
		checkRuleCorrectness(rule);
		OcrRule ocrRule = new OcrRule();
		ocrRule.setA(rule.getLeft());
		ocrRule.setB(rule.getRight());
		ocrRule.setWeight(rule.getWeight());
		
		return ocrRule;
	}
	
	private void checkOcrRuleCorrectness (OcrRule ocrRule) {
		Assert.notNull(ocrRule,"'ocrRule' must not be null");
		Assert.hasText(ocrRule.getA(), "'ocrRule.a' must have text");
		Assert.hasText(ocrRule.getB(), "'ocrRule.b' must have text");
	}
	
	private void checkRuleCorrectness (Rule rule) {
		Assert.notNull(rule,"'ocrRule' must not be null");
		Assert.hasText(rule.getLeft(), "'rule.left' must have text");
		Assert.hasText(rule.getRight(), "'rule.right' must have text");
	}

}

package com.taiger.nlp.cleanser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.taiger.nlp.cleanser.impl.DictionaryDataAccessImpl;
import com.taiger.nlp.cleanser.impl.OCRFailsDataAccessImpl;
import com.taiger.nlp.cleanser.interfaces.OCRFailsDataAccess;
import com.taiger.nlp.cleanser.model.OcrRule;
import com.taiger.nlp.cleanser.model.Stage;
import com.taiger.nlp.cleanser.repository.CorrectionRepository;
import com.taiger.nlp.cleanser.repository.RuleRepository;
import com.taiger.nlp.cleanser.repository.StageRepository;
import com.taiger.nlp.cleanser.tmp.DocType;
import com.taiger.nlp.cleanser.tmp.Pair;
import com.taiger.nlp.cleanser.tmp.Rule;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
@DataJpaTest
public class CLeanserDataManagerApplicationTests {
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private CorrectionRepository correctionRepo;
	
	@Autowired
	private RuleRepository ruleRepo;
	
	@Autowired
	private StageRepository stageRepo;


	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void testRule () {
		
		OcrRule rule = new OcrRule();
		String a = "leftTest";
		String b = "rightTest";
		rule.setA(a);
		rule.setB(b);
		Rule r = new Rule(rule.getA(), rule.getB(), 0.5, 0, "");
		
		log.info("Trying to insert rule using impl.");
		OCRFailsDataAccessImpl ruleImpl = new OCRFailsDataAccessImpl (ruleRepo);
		r = ruleImpl.setRule(r);
		assertNotNull(r);
		r = ruleImpl.setRule(r);
		assertNull(r);
		
		Map<String, Double> consec = ruleImpl.applyRule(a);
		assertNotNull(consec);
		assertThat(consec.size() > 0).isTrue();
		String bc = (String) consec.keySet().toArray()[0];
		assertThat(bc.equals(b)).isTrue();
		
		r = new Rule(rule.getA(), rule.getB(), 0.5, 0, "");
		ruleImpl.removeRule(r);
		List<Rule> rules = ruleImpl.getRules();
		assertThat(rules.size() == 0).isTrue();
	}
	
	@Test
	public void testStageNCorrections () {
		DocType dt = new DocType("ine", "san", "mx", "es");
		Stage nstage = new Stage ();
		nstage.setType(dt.getType());
		nstage.setSubtype(dt.getSubtype());
		nstage.setLanguage(dt.getLanguage());
		nstage.setCountry(dt.getCountry());
		stageRepo.save(nstage);
		
		DictionaryDataAccessImpl dicImpl = new DictionaryDataAccessImpl(correctionRepo, stageRepo, dt);
		assertNotNull(dicImpl.getStage());
		assertThat(dicImpl.getStage().getType().equals("ine")).isTrue();
		
		dicImpl.setCorrection("casa", "c4sa");
		dicImpl.setCorrection("casa", "cas0");
		dicImpl.setCorrection("caso", "cas0");
		dicImpl.setCorrection("caso", "c4so");
		
		assertThat (dicImpl.checkWord("casa")).isTrue();
		assertThat (dicImpl.checkWord("caso")).isTrue();
		assertThat (dicImpl.checkWord("cosa")).isFalse();
		
		Set<String> dict = dicImpl.getWords();
		assertNotNull(dict);
		assertThat(dict.size() == 2).isTrue();
		assertThat(dict.contains("casa")).isTrue();
		assertThat(dict.contains("caso")).isTrue();
		assertThat(dict.contains("cosa")).isFalse();
		
		Set<String> corrs = dicImpl.getCorrection("c4sa");
		assertNotNull(corrs);
		assertThat(corrs.size() == 1).isTrue();
		assertThat(corrs.contains("casa")).isTrue();
		assertThat(corrs.contains("caso")).isFalse();
		
		corrs = dicImpl.getCorrection("cas0");
		assertNotNull(corrs);
		assertThat(corrs.size() == 2).isTrue();
		assertThat(corrs.contains("casa")).isTrue();
		assertThat(corrs.contains("caso")).isTrue();
		assertThat(corrs.contains("cosa")).isFalse();
		
		Map<String, Pair<String, Double>> kfs = dicImpl.getKnownFails(); 
		assertNotNull(kfs);
		assertThat(kfs.get("c4sa").getKey().equals("casa")).isTrue();
		
		Map<String, Set<String>> fullKnownFails = dicImpl.getAllKnownFails();
		assertNotNull(fullKnownFails);
		assertThat(fullKnownFails.get("c4sa").contains("casa")).isTrue();
		assertThat(fullKnownFails.get("cas0").contains("casa")).isTrue();
		assertThat(fullKnownFails.get("cas0").contains("caso")).isTrue();
		assertThat(fullKnownFails.get("c4so").contains("caso")).isTrue();
		
	}

}

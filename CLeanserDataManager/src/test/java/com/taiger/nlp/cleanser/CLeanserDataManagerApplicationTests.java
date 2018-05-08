package com.taiger.nlp.cleanser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.taiger.nlp.cleanser.impl.OCRFailsDataAccessImpl;
import com.taiger.nlp.cleanser.interfaces.OCRFailsDataAccess;
import com.taiger.nlp.cleanser.model.Correction;
import com.taiger.nlp.cleanser.model.Rule;
import com.taiger.nlp.cleanser.repository.CorrectionRepository;
import com.taiger.nlp.cleanser.repository.RuleRepository;
import com.taiger.nlp.cleanser.repository.StageRepository;

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
	
	private OCRFailsDataAccess rulesImpl;
	
	@Before
	public void setup () {
		rulesImpl = new OCRFailsDataAccessImpl();
		ReflectionTestUtils.setField(rulesImpl, "rulesRepo", ruleRepo);
	}

	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void setRule () {
		
		Rule rule = new Rule();
		String a = "leftTest";
		String b = "rightTest";
		rule.setA(a);
		rule.setB(b);
		
		entityManager.persist(rule);
		entityManager.flush();
		log.info("Test rule supposedly inserted.");
		List<Rule> inserted = ruleRepo.findByA(a);
		assertThat(ruleRepo.findByA(a).get(0).getA().equals(a)).isTrue();
		assertThat(ruleRepo.findByA(a).get(0).getB().equals(b)).isTrue();
		assertThat(ruleRepo.findByB(b).get(0).getA().equals(a)).isTrue();
		assertThat(ruleRepo.findByB(b).get(0).getB().equals(b)).isTrue();
		
		List<Rule> removed = ruleRepo.removeByA(rule.getA());
		log.info("Test rule found and removed.");
		assertThat(removed.get(0).getA().equals(a)).isTrue();
		assertThat(removed.get(0).getB().equals(b)).isTrue();
		
	}
	
	@Test
	public void insertWithImpl () {
		
		Rule rule = new Rule();
		String a = "leftTest";
		String b = "rightTest";
		rule.setA(a);
		rule.setB(b);
		//rule.setId(UUID.randomUUID());
		
		log.info("Trying to insert rule using impl.");

		rulesImpl.setRule(rule);
		List<String> consecuents = rulesImpl.applyRule(rule.getA());
		assertThat(consecuents.get(0).equals(rule.getB())).isTrue();
		
		
	}
	
	@Test
	public void testGetCorrections () {
		List<Correction> result = correctionRepo.listByStageAndKnownError("1eb073e8-4ee7-11e8-9c2d-fa7ae01bbebc", "c4sa");
		Correction first = (result != null && !result.isEmpty())? result.get(0): new Correction();
		assertThat(first.getId().equals("09047f98-4ee8-11e8-9c2d-fa7ae01bbebc")).isTrue();
	}

}

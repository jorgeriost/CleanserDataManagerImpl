package com.taiger.nlp.cleanser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.taiger.nlp.cleanser.model.Correction;


public interface CorrectionRepository extends PagingAndSortingRepository<Correction, String>{
	
	List<Correction> findByKnownError (String knownError);
	
	@Query(value="SELECT DISTINCT corrections.word " + 
			"FROM corrections INNER JOIN stage_correction " + 
			"ON stage_correction.correction_id=corrections.id " + 
			"WHERE stage_correction.stage_id=:stageId", nativeQuery=true)
	List<String> listWordsByStage (@Param("stageId")String stageId);
	
	@Query(value="SELECT DISTINCT corrections.known_error " + 
			"FROM corrections INNER JOIN stage_correction " + 
			"ON stage_correction.correction_id=corrections.id " + 
			"WHERE stage_correction.stage_id=:stageId", nativeQuery=true)
	List<String> listKnownErrorsByStage (@Param("stageId")String stageId);
	
	@Query(value="SELECT COUNT (DISTINCT corrections.word) "
			+ "FROM corrections INNER JOIN stage_correction "
			+ "ON stage_correction.correction_id=corrections.id "
			+ "WHERE stage_correction.stage_id=:stageId AND corrections.word=:word", nativeQuery=true)
	int checkWord (@Param("stageId") String stageId, @Param("word") String word);
	
	@Query(value="SELECT corrections.id, corrections.known_error, corrections.word "
			+ "FROM corrections INNER JOIN stage_correction "
			+ "ON stage_correction.correction_id=corrections.id "
			+ "WHERE stage_correction.stage_id=:stageId AND corrections.known_error=:knownError", nativeQuery=true)
	List<Correction> listByStageAndKnownError (@Param("stageId")String stageId, @Param("knownError")String knownError);
	
	@Query(value="SELECT corrections.id, corrections.known_error, corrections.word "
			+ "FROM corrections INNER JOIN stage_correction "
			+ "ON stage_correction.correction_id=corrections.id "
			+ "WHERE stage_correction.stage_id=:stageId", nativeQuery=true)
	List<Correction> listByStage (@Param("stageId")String stageId);
	
	
	List<Correction> findByWord (String word);

}

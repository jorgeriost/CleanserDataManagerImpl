package com.taiger.nlp.cleanser.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "corrections")
@Setter
@Getter
public class Correction {
	
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	String id;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope=Stage.class)
	@ManyToMany(mappedBy = "corrections", fetch = FetchType.EAGER)
	List<Stage> stages;
	
	String word;
	
	String knownError;
	
	
	public Correction () {
		id = "";
		word = "";
		knownError = "";
		stages = new ArrayList<>();
	}

}

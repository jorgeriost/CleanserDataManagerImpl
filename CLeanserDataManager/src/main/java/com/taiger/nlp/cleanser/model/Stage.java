package com.taiger.nlp.cleanser.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "stages")
@Getter
@Setter
public class Stage {

	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	String id;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id", scope=Correction.class)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(	name = "stage_correction", 
				joinColumns = @JoinColumn(name = "stage_id", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "correction_id", referencedColumnName = "id"))
	List<Correction> corrections;
	
	String type;
	
	String subtype;
	
	String country;
	
	String language;
	
	
	public Stage () {
		corrections = new ArrayList<>();
	}
	
}

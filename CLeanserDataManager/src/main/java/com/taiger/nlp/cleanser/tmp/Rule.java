package com.taiger.nlp.cleanser.tmp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

	String left;
	String right;
	Double weight;
	int offset;
	String before;
	
}

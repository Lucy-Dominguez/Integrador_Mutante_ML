package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class DnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

	private static final Pattern VALID_ROW = Pattern.compile("^[ATCG]+$");

	@Override
	public boolean isValid(String[] dna, ConstraintValidatorContext context) {
		if (dna == null || dna.length == 0) {
			return false;
		}

		int n = dna.length;
		for (String row : dna) {
			if (row == null || row.length() != n || !VALID_ROW.matcher(row).matches()) {
				return false;
			}
		}
		return true;
	}
}

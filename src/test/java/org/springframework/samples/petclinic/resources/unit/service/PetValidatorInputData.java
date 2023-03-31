package org.springframework.samples.petclinic.resources.unit.service;

import java.time.LocalDate;

public final class PetValidatorInputData {

	public static final String PET_NAME = "Lola";

	private static final int BIRTH_YEAR = 2010;

	private static final int BIRTH_MONTH = 3;

	private static final int BIRTH_DAY = 23;

	public static final LocalDate BIRTH_DATE = LocalDate.of(BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY);

}

package org.springframework.samples.petclinic.resources.unit.model;

import java.time.LocalDate;

public class PetUnitInputData {

	private static final String DATE_VALUE = "2007-07-07";

	public static final String PET_TYPE = "Cat";

	public static final String PET_TYPE2 = "Dog";

	public static final String DESCRIPTION = "Checkup";

	public static final LocalDate DATE = LocalDate.parse(DATE_VALUE);

	public static final int PET_TYPE_ID = 1;

	public static final int OWNER_ID = 1;

	public static final int OWNER_ID2 = 2;

	public static final int VISIT_ID = 1;

}

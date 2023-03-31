package org.springframework.samples.petclinic.resources.util;

import org.springframework.samples.petclinic.model.Visit;

import java.time.LocalDate;

public class VisitCreateUtils {

	public static Visit createVisit(int id, String description, LocalDate date) {
		Visit visit = new Visit();

		visit.setId(id);
		visit.setDescription(description);
		visit.setDate(date);

		return visit;
	}

	public static Visit createVisitWithPetId(int id, String description, int petId) {
		Visit visit = new Visit();

		visit.setId(id);
		visit.setDescription(description);
		visit.setPetId(petId);

		return visit;
	}

}

package org.springframework.samples.petclinic.resources.util;

import org.springframework.samples.petclinic.model.Visit;

import java.time.LocalDate;

public class VisitUtils {

	public static Visit createVisit(LocalDate visitDate, String description, int petId) {
		Visit visit = new Visit();
		visit.setDate(visitDate);
		visit.setDescription(description);
		visit.setPetId(petId);

		return visit;
	}

}

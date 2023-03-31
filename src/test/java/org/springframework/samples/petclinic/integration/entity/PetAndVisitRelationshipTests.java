package org.springframework.samples.petclinic.integration.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;
import org.springframework.samples.petclinic.resources.util.VisitCreateUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.samples.petclinic.resources.integration.entity.PetAndVisitRelationshipInputData.*;

public class PetAndVisitRelationshipTests {

	/**
	 * This tests the find all visits from a pet where the added visit has a reference to
	 * the pet and vice versa
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Find a visit from a pet")
	void testAllVisitsSuccess() {
		PetType type = PetCreateUtils.createPetTypeWithId(PET_TYPE_NAME, PET_TYPE_ID);
		Pet pet = PetCreateUtils.createPet(PET_NAME, type, PET_ID);

		Visit visit = VisitCreateUtils.createVisitWithPetId(VISIT_ID, DESCRIPTION, VISIT_PET_ID);

		pet.addVisit(visit);

		List<Visit> resultVisitList = pet.getVisits();

		assertEquals(1, resultVisitList.size());
		assertEquals(DESCRIPTION, resultVisitList.get(0).getDescription());
		assertEquals(VISIT_ID, resultVisitList.get(0).getId());
		assertEquals(VISIT_PET_ID, resultVisitList.get(0).getPetId());
	}

	/**
	 * This tests finding a visit from a pet where the visit has the pet ID; however, the
	 * pet does not have a reference to the visit.
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Find a visit from pet without setting the visit to pet")
	void testFindVisitFailure() {

		PetType type = PetCreateUtils.createPetTypeWithId(PET_TYPE_NAME, PET_TYPE_ID);
		Pet pet = PetCreateUtils.createPet(PET_NAME, type, PET_ID);

		Visit visit = VisitCreateUtils.createVisitWithPetId(VISIT_ID, DESCRIPTION, VISIT_PET_ID);

		assertEquals(0, pet.getVisits().size());
		assertEquals(PET_ID, visit.getPetId());
	}

}

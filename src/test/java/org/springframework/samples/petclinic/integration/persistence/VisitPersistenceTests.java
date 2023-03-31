package org.springframework.samples.petclinic.integration.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;
import org.springframework.samples.petclinic.resources.util.VisitCreateUtils;

import static org.springframework.samples.petclinic.resources.integration.persistence.VisitPersistenceInputData.*;

@DataJpaTest
public class VisitPersistenceTests {

	@Autowired
	VisitRepository visitRepository;

	/**
	 * This tests checks that a visit object is (a) Saved to its corresponding repository
	 * and (b) It can be fetched from it using the findByPetId() method invocation
	 * @author Aman Chana
	 * @author Ali T-Motamedi
	 */
	@Test
	@DisplayName("Persist and load a visit")
	void testPersistAndLoadVisit() {
		PetType type = PetCreateUtils.createPetTypeWithId(PET_TYPE_NAME, PET_TYPE_ID);
		Pet pet = PetCreateUtils.createPet(PET_NAME, type, PET_ID);

		Visit visit = VisitCreateUtils.createVisitWithPetId(VISIT_ID, DESCRIPTION, VISIT_PET_ID);

		pet.addVisit(visit);

		visitRepository.save(visit);

		List<Visit> visits = visitRepository.findByPetId(PET_ID);

		assertEquals(VISIT_ID, visits.get(0).getId());
		assertEquals(DESCRIPTION, visits.get(0).getDescription());
	}

	/**
	 * This tests the finding all visit retrieval method
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Find all visits")
	void testAllVisitsSuccess() {
		PetType type = PetCreateUtils.createPetTypeWithId(PET_TYPE_NAME, PET_TYPE_ID);
		Pet pet = PetCreateUtils.createPet(PET_NAME, type, PET_ID);

		Visit visit = VisitCreateUtils.createVisitWithPetId(VISIT_ID, DESCRIPTION, VISIT_PET_ID);

		pet.addVisit(visit);

		visitRepository.save(visit);

		assertTrue(visitRepository.findByPetId(VISIT_PET_ID).size() > 0);
	}

	/**
	 * This tests finding a visit that hasnt been persisted
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Find non existent visit")
	void testFindVisitFailure() {

		Visit visit = VisitCreateUtils.createVisitWithPetId(VISIT_ID, DESCRIPTION, VISIT_PET_ID);

		assertFalse(visitRepository.findByPetId(VISIT_PET_ID).contains(visit));
	}

}

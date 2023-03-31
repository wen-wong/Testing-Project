package org.springframework.samples.petclinic.integration.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.VetCreateUtils;

import static org.springframework.samples.petclinic.resources.integration.persistence.VetPersistenceInputData.*;

@DataJpaTest
public class VetPersistenceTests {

	@Autowired
	VetRepository vetRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	VisitRepository visitRepository;

	@BeforeEach
	public void persistenceSetUp() {
		vetRepository.deleteAll();
		visitRepository.deleteAll();
		petRepository.deleteAll();
		ownerRepository.deleteAll();
	}

	/**
	 * This tests checks that a vet object is (a) Saved to its corresponding repository
	 * and (b) It can be fetched from it using the findAll() method invocation
	 * @author Aman Chana
	 * @author Ali T-Motamedi
	 */
	@Test
	@DisplayName("Find all existing vets")
	void testFindAll() {
		Vet connor = VetCreateUtils.createVet(FIRSTNAME_ONE, LASTNAME_ONE, ID_ONE);
		Vet leon = VetCreateUtils.createVet(FIRSTNAME_TWO, LASTNAME_TWO, ID_TWO);
		Vet sid = VetCreateUtils.createVet(FIRSTNAME_THREE, LASTNAME_THREE, ID_THREE);

		vetRepository.save(connor);
		vetRepository.save(leon);
		vetRepository.save(sid);

		Iterator<Vet> listOfVets = vetRepository.findAll().iterator();

		assertEquals(connor.getFirstName(), listOfVets.next().getFirstName());
		assertEquals(leon.getFirstName(), listOfVets.next().getFirstName());
		assertEquals(sid.getFirstName(), listOfVets.next().getFirstName());
	}

}

package org.springframework.samples.petclinic.integration.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;

import static org.springframework.samples.petclinic.resources.integration.persistence.PetPersistenceInputData.*;

@DataJpaTest
public class PetPersistenceTests {

	@Autowired
	VisitRepository visitRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	/**
	 * This tests if we can create anew PetType
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Find pet type")
	public void testFindPetTypesSuccess() {
		PetType petType = PetCreateUtils.createPetType(PET_TYPE);

		petRepository.save(petType);

		assertTrue(petRepository.findPetTypes().contains(petType));

	}

	/**
	 * This tests trying to find a type that does not exist
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Find pet type that does not exist")
	public void testFindPetTypesFailure() {
		PetType petType = PetCreateUtils.createPetType(PET_TYPE2);

		assertFalse(petRepository.findPetTypes().contains(petType));
	}

	/**
	 * This tests to make sure that the findPetTypes() function returns the elements in
	 * the database
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Find all pet types")
	public void testFindAllPetTypes() {
		assertTrue(petRepository.findPetTypes().size() > 0);
	}

	/**
	 * This method tests the creating and saving of a new Pet
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Persist and load a pet")
	public void testPersistAndLoadPet() {
		PetType petType = PetCreateUtils.createPetTypeWithId(PET_TYPE, PET_TYPE_ID);

		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		Pet pet = PetCreateUtils.createPetWithOwner(owner, PET_NAME, petType, BIRTH_DATE, PET_ID);

		ownerRepository.save(owner);
		petRepository.save(pet);

		Pet resultPet = petRepository.findById(PET_ID);

		assertEquals(PET_NAME, resultPet.getName());
		assertEquals(PET_ID, resultPet.getId());
		assertEquals(PET_TYPE, resultPet.getType().getName());
		assertEquals(BIRTH_DATE, resultPet.getBirthDate());
	}

	/**
	 * This tests the findById function using a pre-existing element in the database
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Find pre-existing pet with valid ID")
	public void testFindByIdSuccess() {
		Pet resultPet = petRepository.findById(VALID_PET_ID);

		assertEquals(FIND_NAME, resultPet.getName());
		assertEquals(FIND_DATE, resultPet.getBirthDate().toString());
		assertEquals(VALID_PET_ID, resultPet.getOwner().getId());

	}

	/**
	 * This test the findById with an incorrect id
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Find pet with invalid ID")
	public void testFindByIdFailure() {
		assertNull(petRepository.findById(INCORRECT_PET_ID));
	}

}

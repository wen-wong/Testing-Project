package org.springframework.samples.petclinic.integration.entity;

import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.samples.petclinic.resources.integration.entity.OwnerAndPetRelationshipInputData.*;

public class OwnerAndPetRelationshipTests {

	private Owner owner;

	private Pet pet;

	@BeforeEach
	public void ownerAndPetRelationshipTestSetup() {
		owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		PetType petType = PetCreateUtils.createPetType(PET_TYPE);
		pet = PetCreateUtils.createSimplePet(PET_NAME, petType);
	}

	/**
	 * Add a pet to an owner
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Add a new pet to owner")
	public void testAddPetToOwner() {
		owner.addPet(pet);

		assertEquals(1, owner.getPets().size());
	}

	/**
	 * Set an owner to a pet
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Set an owner to a pet")
	public void testSetOwnerToPet() {
		pet.setOwner(owner);

		Owner resultOwner = pet.getOwner();

		assertEquals(FIRSTNAME, resultOwner.getFirstName());
		assertEquals(LASTNAME, resultOwner.getLastName());
		assertEquals(ADDRESS, resultOwner.getAddress());
		assertEquals(CITY, resultOwner.getCity());
		assertEquals(TELEPHONE, resultOwner.getTelephone());
		assertEquals(OWNER_ID, resultOwner.getId());
	}

	/**
	 * Get a pet from an owner
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Get a pet from an owner")
	public void testGetPetFromOwner() {
		owner.addPet(pet);

		Pet resultPet = owner.getPet(PET_NAME);

		assertEquals(PET_NAME, resultPet.getName());
		assertEquals(PET_TYPE, resultPet.getType().getName());
		assertNull(resultPet.getId());
	}

	/**
	 * Set multiple pets to an owner
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Set multiple pets for an owner")
	public void testSetPetsInternalForOwner() {
		Set<Pet> pets = new HashSet<>();
		pets.add(pet);
		pets.add(pet);

		owner.setPetsInternal(pets);

		Set<Pet> allPets = owner.getPetsInternal();

		assertEquals(allPets, pets);
	}

}

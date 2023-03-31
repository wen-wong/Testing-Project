package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.samples.petclinic.resources.unit.model.OwnerUnitInputData.*;

import java.util.*;

public class OwnerTests {

	private Owner owner;

	private Pet pet;

	private Set<Pet> pets;

	@BeforeEach
	public void ownerTestSetup() {
		owner = new Owner();
		pet = new Pet();
	}

	/**
	 * This tests the setter/getter for city
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set owner's city")
	public void testSetCity() {
		owner.setCity(CITY);
		assertEquals(CITY, owner.getCity());
	}

	/**
	 * This tests the setter/getter for address
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set owner's address")
	public void testSetAddress() {
		owner.setAddress(ADDRESS);
		assertEquals(ADDRESS, owner.getAddress());
	}

	/**
	 * This tests the telephone setter/getter
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set owner's telephone")
	public void testSetTelephone() {
		owner.setTelephone(TELEPHONE);
		assertEquals(TELEPHONE, owner.getTelephone());
	}

	/**
	 * This tests the setPetsInternal method
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set owner's pets internal with no pets")
	public void testSetPetsInternal() {
		pets = Collections.emptySet();
		owner.setPetsInternal(pets);
		assertEquals(pets, owner.getPetsInternal());
	}

	/**
	 * This tests the getPetsInternal but when the owner has no pets
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pets internal with no pets")
	public void testGetPetsInternalNoPets() {
		pets = owner.getPetsInternal();
		assertEquals(0, pets.size());
	}

	/**
	 * This tests the getPetsInternal but when the owner has one pet
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pets internal with one pet")
	public void testGetPetsInternalHasPets() {

		owner.addPet(pet);
		pets = owner.getPetsInternal();

		assertEquals(1, pets.size());
		assertEquals(pet, pets.toArray()[0]);
	}

	/**
	 * This tests addPet with a new Pet (pet without id)
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Add pet to an owner")
	public void testAddPetNewPet() {

		pet.setName(PET_NAME);
		owner.addPet(pet);

		List<Pet> pets = owner.getPets();

		assertEquals(1, pets.size());
		assertEquals(pet, owner.getPet(PET_NAME));

	}

	/**
	 * This tests addPet with a regular Pet (pet with id)
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Add pet with an ID to an owner")
	public void testAddPetWithId() {

		pet.setName(PET_NAME);

		owner.addPet(pet);
		pet.setId(PET_ID);

		List<Pet> pets = owner.getPets();

		assertEquals(1, pets.size());
	}

	/**
	 * This tests getPet with an empty input
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pet who has no pets")
	public void testGetPetNoPet() {
		assertNull(owner.getPet(""));
	}

	/**
	 * This tests getPet with a pet with ID
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pet who has one pet")
	public void testGetPetOnePet() {

		pet.setName(PET_NAME);

		owner.addPet(pet);
		pet.setId(PET_ID);

		assertEquals(pet, owner.getPet(PET_NAME));
	}

	/**
	 * This tests getPet with a pet with ID and a Pet without ID (2 pets) In this case we
	 * have IgnoreNew as false, meaning that getPet will get the pet regardless of if it
	 * has an id or not
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pets who has two new pets")
	public void testGetPetManyPetsNew() {

		pet.setName(PET_NAME);
		pet.setId(PET_ID);

		owner.addPet(pet);

		Pet pet2 = new Pet();
		pet2.setName(PET_NAME2);

		owner.addPet(pet2);

		assertEquals(pet2, owner.getPet(PET_NAME2));

	}

	/**
	 * This tests getPet with a pet with ID and a Pet without ID (2 pets) In this case we
	 * have IgnoreNew as true, meaning that getPet will get the pet ONLY if it has an ID
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get owner's pet who has one old pet and one new pet")
	public void getPetManyPetsIgnoreNewTest() {

		pet.setName(PET_NAME);
		pet.setId(PET_ID);

		owner.addPet(pet);

		Pet pet2 = new Pet();
		pet2.setName(PET_NAME2);

		owner.addPet(pet2);

		assertNull(owner.getPet(PET_NAME2, true));

	}

}

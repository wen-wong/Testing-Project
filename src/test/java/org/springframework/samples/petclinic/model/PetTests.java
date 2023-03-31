package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;
import org.springframework.samples.petclinic.resources.util.VisitCreateUtils;

import static org.springframework.samples.petclinic.resources.unit.model.OwnerUnitInputData.*;
import static org.springframework.samples.petclinic.resources.unit.model.PetUnitInputData.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

public class PetTests {

	private PetType type;

	private Pet pet;

	private Set<Visit> visits;

	@BeforeEach
	public void petTestSetup() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);
		type = PetCreateUtils.createPetTypeWithId(PET_TYPE, PET_TYPE_ID);
		pet = PetCreateUtils.createPetWithOwner(owner, PET_NAME, type, DATE, PET_ID);
	}

	/**
	 * Get an empty list of visits
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get a pet's visits who does not have any visits")
	public void testGetVisitsInternalEmpty() {
		visits = pet.getVisitsInternal();
		assertEquals(0, visits.size());
	}

	/**
	 * Get a list of visits with one entry
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get a pet's visits internal who has one visit")
	public void testGetVisitsInternalOne() {
		Visit visit = VisitCreateUtils.createVisit(VISIT_ID, DESCRIPTION, DATE);

		pet.addVisit(visit);

		visits = pet.getVisitsInternal();

		assertEquals(1, visits.size());
		assertEquals(visit, visits.toArray()[0]);
	}

	/**
	 * Test to add a visit to a pet
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Add a visit to a pet")
	public void testAddVisit() {
		Visit visit = VisitCreateUtils.createVisit(VISIT_ID, DESCRIPTION, DATE);

		pet.addVisit(visit);

		assertEquals(DATE, pet.getVisits().get(0).getDate());
		assertEquals(DESCRIPTION, pet.getVisits().get(0).getDescription());
		assertEquals(VISIT_ID, pet.getVisits().get(0).getId());

	}

	/**
	 * Test to set a pets visit internal
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set a pet's visits internal")
	public void testSetVisitsInternal() {
		visits = Collections.emptySet();

		pet.setVisitsInternal(visits);

		assertEquals(visits, pet.getVisitsInternal());
	}

	/**
	 * Test to set a pets birthdate
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set a pet's birth date")
	public void testSetBirthDate() {

		pet.setBirthDate(DATE);

		assertEquals(DATE, pet.getBirthDate());
	}

	/**
	 * Test to set a pets type
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set a pet's pet type")
	public void testSetType() {
		PetType type2 = PetCreateUtils.createPetTypeWithId(PET_TYPE2, PET_TYPE_ID);

		pet.setType(type2);

		assertEquals(type2, pet.getType());
	}

	/**
	 * Test to set a pets owner
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Set a pet's owner")
	public void testSetOwner() {

		Owner owner2 = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID2);

		pet.setOwner(owner2);

		assertEquals(FIRSTNAME, pet.getOwner().getFirstName());
		assertEquals(LASTNAME, pet.getOwner().getLastName());
		assertEquals(TELEPHONE, pet.getOwner().getTelephone());
		assertEquals(CITY, pet.getOwner().getCity());
		assertEquals(ADDRESS, pet.getOwner().getAddress());
	}

	/**
	 * Test to get a pets birthdate
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get a pet's birth date")
	public void testGetBirthDate() {
		assertEquals(DATE, pet.getBirthDate());
	}

	/**
	 * Test to get a pets type
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get a pet's pet type")
	public void testGetType() {
		assertEquals(type, pet.getType());
	}

	/**
	 * Test to get a pets owner
	 * @author Lucca Di Lullo
	 * @author Gabrielle Halpin
	 */
	@Test
	@DisplayName("Get a pet's owner")
	public void testGetOwner() {
		assertEquals(FIRSTNAME, pet.getOwner().getFirstName());
		assertEquals(LASTNAME, pet.getOwner().getLastName());
		assertEquals(TELEPHONE, pet.getOwner().getTelephone());
		assertEquals(CITY, pet.getOwner().getCity());
		assertEquals(ADDRESS, pet.getOwner().getAddress());
	}

}

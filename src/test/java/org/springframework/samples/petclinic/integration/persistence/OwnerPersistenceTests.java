package org.springframework.samples.petclinic.integration.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.samples.petclinic.resources.integration.persistence.OwnerPersistenceInputData.*;

@DataJpaTest
public class OwnerPersistenceTests {

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	VisitRepository visitRepository;

	@Autowired
	VetRepository vetRepository;

	@BeforeEach
	public void ownerPersistenceSetup() {
		vetRepository.deleteAll();
		visitRepository.deleteAll();
		petRepository.deleteAll();
		ownerRepository.deleteAll();
	}

	/**
	 * Persist a single owner and load the owner.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Persist and load an owner")
	public void testPersistAndLoadOwner() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		ownerRepository.save(owner);

		Collection<Owner> owners = ownerRepository.findAll();

		assertEquals(1, owners.size());
		assertEquals(FIRSTNAME, ((Owner) owners.toArray()[0]).getFirstName());
		assertEquals(LASTNAME, ((Owner) owners.toArray()[0]).getLastName());
		assertEquals(ADDRESS, ((Owner) owners.toArray()[0]).getAddress());
		assertEquals(CITY, ((Owner) owners.toArray()[0]).getCity());
		assertEquals(TELEPHONE, ((Owner) owners.toArray()[0]).getTelephone());
	}

	/**
	 * Find an owner using their ID.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Find an owner by Id")
	public void testFindOwnerById() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		ownerRepository.save(owner);

		Owner resultOwner = ownerRepository.findById(OWNER_ID);

		assertEquals(FIRSTNAME, resultOwner.getFirstName());
		assertEquals(LASTNAME, resultOwner.getLastName());
		assertEquals(ADDRESS, resultOwner.getAddress());
		assertEquals(CITY, resultOwner.getCity());
		assertEquals(TELEPHONE, resultOwner.getTelephone());
	}

	/**
	 * Find an owner with a custom ID using their ID. This test will return no owner
	 * because the repository generates an ID to the owner despite providing a custom ID.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Find an owner with a custom Id by Id")
	public void testFindOwnerWithCustomIdById() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, CUSTOM_OWNER_ID);

		ownerRepository.save(owner);

		Owner resultOwner = ownerRepository.findById(CUSTOM_OWNER_ID);

		assertNull(resultOwner);
	}

	/**
	 * Find an owner with their last name.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Find an owner by last name")
	public void testFindOwnerByLastName() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		ownerRepository.save(owner);

		Collection<Owner> owners = ownerRepository.findByLastName(LASTNAME);

		assertEquals(1, owners.size());
		assertEquals(FIRSTNAME, ((Owner) owners.toArray()[0]).getFirstName());
		assertEquals(LASTNAME, ((Owner) owners.toArray()[0]).getLastName());
		assertEquals(ADDRESS, ((Owner) owners.toArray()[0]).getAddress());
		assertEquals(CITY, ((Owner) owners.toArray()[0]).getCity());
		assertEquals(TELEPHONE, ((Owner) owners.toArray()[0]).getTelephone());
	}

	/**
	 * Find multiple owners with their last name.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Find multiple owners by last name")
	public void testFindMultipleOwnersByLastName() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);
		Owner owner2 = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		ownerRepository.save(owner);
		ownerRepository.save(owner2);

		Collection<Owner> owners = ownerRepository.findByLastName(LASTNAME);

		assertEquals(2, owners.size());
	}

}

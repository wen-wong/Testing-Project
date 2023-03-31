package org.springframework.samples.petclinic.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.samples.petclinic.resources.api.OwnerControllerInputData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTests {

	private static Owner owner;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository ownerRepository;

	@MockBean
	private VisitRepository visitRepository;

	@BeforeAll
	public static void ownerControllerBasicSetup() {
		owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);
	}

	@BeforeEach
	public void ownerControllerSetup() {
		when(ownerRepository.findById(1)).thenReturn(owner);
	}

	/**
	 * Creates an empty owner creation form
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Create owner creation form")
	public void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(model().size(1))
				.andExpect(model().attribute("owner",
						allOf(any(Owner.class), hasProperty("address", nullValue()), hasProperty("city", nullValue()),
								hasProperty("telephone", nullValue()), hasProperty("petsInternal", hasSize(0)))))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	/**
	 * Process an owner creation form with no inputs
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Owner Creation Form without any inputs")
	public void testProcessFindFormFailed() throws Exception {
		mockMvc.perform(post("/owners/new")).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	/**
	 * Process an owner creation form with valid inputs
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Owner Creation Form with all valid inputs")
	public void testProcessFindForm() throws Exception {
		mockMvc.perform(post("/owners/new").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", FIRSTNAME).param("lastName", LASTNAME).param("address", ADDRESS).param("city", CITY)
				.param("telephone", TELEPHONE)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/owners/null"));
	}

	/**
	 * Show owner by owner ID
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Show Owner by ID")
	public void testShowOwner() throws Exception {
		Owner owner2 = new Owner();
		Pet pet = new Pet();
		owner2.setId(OWNER_ID);
		owner2.setFirstName(FIRSTNAME);
		owner2.setLastName(LASTNAME);
		owner2.addPet(pet);

		when(ownerRepository.findById(owner2.getId())).thenReturn(owner2);
		when(visitRepository.findByPetId(pet.getId())).thenReturn(emptyList());

		mockMvc.perform(get("/owners/{ownerId}", OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner",
						allOf(any(Owner.class), hasProperty("firstName", equalTo("John")),
								hasProperty("lastName", equalTo("Doe")), hasProperty("city", nullValue()),
								hasProperty("telephone", nullValue()), hasProperty("petsInternal", hasSize(1)))))
				.andExpect(view().name("owners/ownerDetails"));
	}

	/**
	 * Show owner by owner ID with no pets
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Show Owner by ID with no pets")
	public void testShowOwnerNoPets() throws Exception {
		owner = new Owner();
		owner.setId(OWNER_ID);
		owner.setFirstName(FIRSTNAME);
		owner.setLastName(LASTNAME);

		when(ownerRepository.findById(OWNER_ID)).thenReturn(owner);

		mockMvc.perform(get("/owners/{ownerId}", OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("owner",
						allOf(any(Owner.class), hasProperty("firstName", equalTo("John")),
								hasProperty("lastName", equalTo("Doe")), hasProperty("city", nullValue()),
								hasProperty("telephone", nullValue()), hasProperty("petsInternal", hasSize(0)))))
				.andExpect(view().name("owners/ownerDetails"));
	}

	/**
	 * Creates a find owner form
	 *
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Find Owner Form")
	public void testInitFindOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/findOwners")).andExpect(model().size(1));
	}

	/**
	 * Process a find owner form with no parameters
	 *
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Process Find Owner Form with no parameters")
	public void testProcessFindOwnerFormNoParameter() throws Exception {
		mockMvc.perform(get("/owners")).andExpect(status().isOk())
				.andExpect(model().attribute("owner", hasProperty("lastName", equalTo("")))).andExpect(model().size(1));
	}

	/**
	 * Process a find owner form where no owners are found
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Process Find Owner Form no owners found")
	public void testProcessFindOwnerFormFailedNoOwnersFound() throws Exception {
		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"));
	}

	/**
	 * Process a find owner form where one owner is found
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Find Owner Form")
	public void testProcessFindOwnerFormOneOwner() throws Exception {
		owner = OwnerCreateUtils.createSimpleOwner(FIRSTNAME, LASTNAME);

		Collection<Owner> owners = new ArrayList<>();
		owners.add(owner);

		when(ownerRepository.findByLastName(LASTNAME)).thenReturn(owners);

		mockMvc.perform(get("/owners").param("lastName", LASTNAME)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/null"));

	}

	/**
	 * Process a find owner form where multiple owners with the same last name are found
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Find Owner Form with multiple owners with the same last name")
	public void testProcessFindOwnerFormMultipleOwners() throws Exception {
		owner = OwnerCreateUtils.createSimpleOwner(FIRSTNAME, LASTNAME);

		Owner owner2 = OwnerCreateUtils.createSimpleOwner(FIRSTNAME2, LASTNAME);

		Collection<Owner> owners = new ArrayList<>();
		owners.add(owner);
		owners.add(owner2);

		when(ownerRepository.findByLastName(LASTNAME)).thenReturn(owners);

		mockMvc.perform(get("/owners").param("lastName", LASTNAME)).andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"));

	}

	/**
	 * Process a find owner form where multiple owners with the same last name are found
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Find Owner Form with all valid inputs")
	public void testProcessFindOwnerFormSuccessValidInputs() throws Exception {
		mockMvc.perform(get("/owners").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("firstName", FIRSTNAME)
				.param("lastName", LASTNAME).param("address", ADDRESS).param("city", CITY)
				.param("telephone", TELEPHONE)).andExpect(status().isOk()).andExpect(model().size(1))
				.andExpect(model().attribute("owner",
						allOf(any(Owner.class), hasProperty("address", equalTo(ADDRESS)),
								hasProperty("city", equalTo(CITY)), hasProperty("telephone", equalTo(TELEPHONE)),
								hasProperty("petsInternal", hasSize(0)))));
	}

	/**
	 * Creates an update owner form
	 * @author Zoya Malhi
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Update Owner Form")
	public void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", OWNER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner")).andExpect(model().size(1))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	/**
	 * Process an update owner form without any inputs
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Process Update Owner Form without any inputs")
	public void testProcessUpdateOwnerFormFailed() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", OWNER_ID)).andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	/**
	 * Process an update owner form with valid inputs
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Process Update Owner Form with all valid inputs")
	public void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", OWNER_ID).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("firstName", FIRSTNAME).param("lastName", LASTNAME).param("address", ADDRESS).param("city", CITY)
				.param("telephone", TELEPHONE)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/owners/" + owner.getId()));
	}

}

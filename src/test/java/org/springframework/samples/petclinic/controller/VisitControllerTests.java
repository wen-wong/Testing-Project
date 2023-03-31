package org.springframework.samples.petclinic.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;
import org.springframework.samples.petclinic.resources.util.VisitUtils;

import static org.springframework.samples.petclinic.resources.api.VisitControllerInputData.*;

import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.test.web.servlet.MockMvc;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Aman Chana & Ali T-Motamedi
 */

@WebMvcTest(VisitController.class)
public class VisitControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository owners;

	@MockBean
	private PetRepository pets;

	@MockBean
	private VisitRepository visits;

	/**
	 * The setup method creates an owner, pet with correct petType and mocks the below
	 * repository method calls to avoid dependency related failures
	 * @author Aman Chana
	 */

	@BeforeEach
	void visitControllerSetup() {

		PetType dogType = PetCreateUtils.createPetTypeWithId(PET_TYPE_NAME, PET_TYPE_ID);
		Pet onlyPet = PetCreateUtils.createPet(PET_NAME, dogType, PET_ID);
		Owner owner = OwnerCreateUtils.createOwnerWithPet(OWNER_FIRSTNAME, OWNER_LASTNAME, ADDRESS, CITY, TELEPHONE,
				OWNER_ID, onlyPet);

		when(owners.findById(1)).thenReturn(owner);
		when(pets.findById(1)).thenReturn(onlyPet);
		when(pets.findPetTypes()).thenReturn(Lists.newArrayList(dogType));
		when(visits.findByPetId(1)).thenReturn(Collections.emptyList());
	}

	/**
	 * This tests when the URL below is called with no petId, if that is the case then it
	 * should raise the appropriate error
	 * @author Aman Chana
	 */
	@Test
	public void testInitNewVisitFormWithNullPetID() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/visits/new", (Object) null)).andExpect(status().is4xxClientError());
	}

	/**
	 * This tests when the URL below is called with a nonexistent petId, if that is the
	 * case then it should raise the appropriate error
	 * @author Aman Chana
	 */
	@Test
	@Disabled
	public void testInitNewVisitFormWithInvalidPetId() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/visits/new", 2)).andExpect(status().is5xxServerError());
	}

	/**
	 * This tests when the URL below is called with an existing petId, if that is the case
	 * then it should return a success status code indicating the client’s request was
	 * accepted and display "pets/createOrUpdateVisitForm"
	 * @author Aman Chana
	 */
	@Test
	public void testInitNewVisitFormWithValidPetId() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/visits/new", 1)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	/**
	 * This tests when the URL below is called with no parameters date and description, if
	 * that is the case then it should return a success status code indicating the
	 * client’s request was accepted and redisplay the same page since the error is
	 * handled
	 * @author Aman Chana
	 */
	@Test
	public void testProcessNewVisitFormWithNullVisit() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", 1, 1)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	/**
	 * This tests when the URL below is called with valid date and no description, if that
	 * is the case then it should return a success status code indicating the client’s
	 * request was accepted and redisplay the same page since the error is handled
	 * @author Aman Chana
	 */
	@Test
	public void testProcessNewVisitFormWithInvalidInputs() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", 1, 1).param("description", "").param("date",
				"2022-10-31")).andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	/**
	 * This tests when the URL below is called with an incorrect date (in the past) and
	 * valid description, if that is the case then it should return a success status code
	 * indicating the client’s request was accepted and redisplay the same page since the
	 * error is handled
	 * @author Aman Chana
	 */
	@Test
	@Disabled
	public void testProcessNewVisitFormWithIncorrectDate() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", 1, 1)
				.param("description", "Test Description Input 2nd case").param("date", "2000-12-12"))
				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	/**
	 * This tests when the URL below is called with valid date and description, if that is
	 * the case then it should return a status code indicating a redirect and redirect the
	 * user to the "/owners/{ownerId}" page
	 * @author Aman Chana
	 */
	@Test
	public void testProcessNewVisitFormWithValidInputs() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", 1, 1)
				.param("description", "Test Description Input").param("date", "2022-10-31"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/{ownerId}"));
	}

}

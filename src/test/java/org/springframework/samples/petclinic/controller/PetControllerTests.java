package org.springframework.samples.petclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.samples.petclinic.resources.api.PetControllerInputData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PetController.class)
public class PetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository ownerRepository;

	@MockBean
	private PetRepository petRepository;

	@BeforeEach
	public void petControllerSetup() {
		Owner owner = OwnerCreateUtils.createOwner(FIRSTNAME, LASTNAME, ADDRESS, CITY, TELEPHONE, OWNER_ID);

		when(ownerRepository.findById(OWNER_ID)).thenReturn(owner);

	}

	/**
	 * This tests that the initCreationForm function succeeds and redirects successfully
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Initiate Pet Creation Form")
	public void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", OWNER_ID)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	/**
	 * This function tests the creation of the processCreationForm() with a redirection
	 * towards pets/createOrUpdatePetForm
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Pet Creation Form for existing Pet")
	public void testProcessCreationFormPetNotNew() throws Exception {

		mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", PET_NAME).param("id", Integer.toString(PET_ID))).andExpect(status().isOk())
				.andExpect(model().attributeExists("pet"))
				.andExpect(model().attribute("pet",
						allOf(any(Pet.class), hasProperty("name", equalTo(PET_NAME)),
								hasProperty("id", equalTo(PET_ID)))))
				.andExpect(view().name("pets/createOrUpdatePetForm"));

	}

	/**
	 * This function tests the creation of the processCreationForm() with a redirection
	 * towards the owner's page
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Process Pet Creation Form with full information")
	public void testProcessCreationFormFullInfo() throws Exception {

		mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", PET_NAME).param("id", Integer.toString(PET_ID)).param("birthDate", BIRTH_DATE))
				.andExpect(status().is3xxRedirection());

	}

	/**
	 * This tests that the initUpdateForm function succeeds and redirects successfully
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Initiate Pet Update Form")
	public void testInitUpdateForm() throws Exception {
		PetType type = new PetType();
		type.setName(PET_TYPE);

		Pet pet = new Pet();
		pet.setId(PET_ID);
		pet.setName(PET_NAME);
		pet.setBirthDate(LocalDate.parse(BIRTH_DATE));
		pet.setType(type);

		when(petRepository.findById(PET_ID)).thenReturn(pet);

		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdatePetForm"));

	}

	/**
	 * This function tests the creation of the ProcessUpdateForm() with a redirection
	 * towards pets/createOrUpdatePetForm
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Process Pet Update Form")
	void testProcessUpdateForm() throws Exception {

		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("name", PET_NAME)
				.param("id", Integer.toString(PET_ID)).param("birthdate", BIRTH_DATE)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pet"))
				.andExpect(model().attribute("pet",
						allOf(any(Pet.class), hasProperty("name", equalTo(PET_NAME)),
								hasProperty("id", equalTo(PET_ID)))))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	/**
	 * This function tests the creation of the ProcessUpdateForm() with a redirection
	 * towards the owner's page
	 * @author Gabrielle Halpin
	 * @author Lucca Di Lullo
	 */
	@Test
	@DisplayName("Process Pet Update Form with full information")
	void testProcessUpdateFormFullInfo() throws Exception {

		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("name", PET_NAME)
				.param("id", Integer.toString(PET_ID)).param("birthDate", BIRTH_DATE))
				.andExpect(status().is3xxRedirection());
	}

}

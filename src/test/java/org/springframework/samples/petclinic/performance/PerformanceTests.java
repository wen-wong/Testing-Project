package org.springframework.samples.petclinic.performance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.samples.petclinic.PetClinicApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.samples.petclinic.resources.performance.PerformanceInputData.*;

@SpringBootTest(classes = PetClinicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PerformanceTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * Create an Owner to be tested for Run-Time Performance
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Create Owner Performance")
	public void testCreateOwner() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", FIRSTNAME);
		params.add("lastName", LASTNAME);
		params.add("address", ADDRESS);
		params.add("city", CITY);
		params.add("telephone", TELEPHONE);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		testRestTemplate.postForEntity("/owners/new", request, String.class);
	}

	/**
	 * Edit an Owner to be tested for Run-Time Performance
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Edit Owner Performance")
	public void testEditOwner() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", FIRSTNAME);
		params.add("lastName", LASTNAME);
		params.add("address", ADDRESS);
		params.add("city", CITY);
		params.add("telephone", TELEPHONE);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		testRestTemplate.postForEntity("/owners/1/edit", request, String.class);
	}

	/**
	 * Create a Pet to be tested for Run-Time Performance
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Create Pet Performance")
	public void testCreatePet() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", PET_NAME);
		params.add("type", TYPE);
		params.add("birthDate", BIRTH_DATE);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		testRestTemplate.postForEntity("/owners/1/pets/new", request, String.class);

	}

	/**
	 * Edit a Pet to be tested for Run-Time Performance
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Edit Pet Performance")
	public void testEditPet() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", PET_NAME);
		params.add("type", TYPE);
		params.add("birthDate", BIRTH_DATE);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		testRestTemplate.postForEntity("/owners/1/pets/1/edit", request, String.class);
	}

}

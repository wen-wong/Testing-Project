package org.springframework.samples.petclinic.acceptance.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class OwnerPetAcceptanceTests {

	@Before
	public void setup() {
		visitRepository.deleteAll();
		petRepository.deleteAll();
	}

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ResponseEntity<String> response;

	private Owner owner;

	/**
	 *
	 * An owner object was be created initially to then add a pet to the petRepository.
	 *
	 * @author Ali T.-Motamedi
	 * @author Aman Chana
	 * @author Brandon Wong
	 */
	@Given("the following Owner exists")
	public void setUp(DataTable table) {
		Map<String, String> example = table.asMaps().get(0);
		// Create Owner that will be used as a reference
		owner = new Owner();
		owner.setFirstName(example.get("firstName"));
		owner.setLastName(example.get("lastName"));
		owner.setAddress(example.get("address"));
		owner.setCity(example.get("city"));
		owner.setTelephone(example.get("telephone"));

		ownerRepository.save(owner);
	}

	@When("Owner adds a Pet")
	public void step(DataTable table) {
		Map<String, String> example = table.asMaps().get(0);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", example.get("name"));
		params.add("birthDate", example.get("birth date"));
		params.add("type", example.get("pet type"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		response = testRestTemplate.postForEntity("/owners/12/pets/new", requestEntity, String.class);
	}

	@Then("Pet is persisted to the database")
	public void petIsPersistedToTheDatabase() {
		assertNotNull(response);
		assertEquals(1, petRepository.findAllByName("Balthazar").size());
	}

	@Then("Pet is not persisted to the database")
	public void petIsNotPersistedToTheDatabase() {
		assertNotNull(response);
		assertEquals(0, petRepository.findAllByOwner(owner).size());
	}

	@And("the page will be redisplayed")
	public void thePageWillBeRedisplayed() {
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@DataTableType(replaceWithEmptyString = "[blank]")
	public String stringType(String cell) {
		return cell;
	}

}

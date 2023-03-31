package org.springframework.samples.petclinic.acceptance.stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.resources.util.OwnerCreateUtils;
import org.springframework.samples.petclinic.resources.util.PetCreateUtils;
import org.springframework.samples.petclinic.resources.util.VisitUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerAddsVisitAcceptanceTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private PetRepository petRepository;

	private Owner owner;

	private ResponseEntity<String> response;

	@Given("the Owner has the following")
	public void the_following_owner_exists(DataTable table) {
		Map<String, String> ownerTable = table.asMaps().get(0);

		Owner tempOwner = OwnerCreateUtils.createOwner(ownerTable.get("firstName"), ownerTable.get("lastName"),
				ownerTable.get("address"), ownerTable.get("city"), ownerTable.get("telephone"),
				Integer.parseInt(ownerTable.get("id")));

		ownerRepository.save(tempOwner);
		owner = ownerRepository.findById(Integer.parseInt(ownerTable.get("id")));
	}

	@Given("the Owner has the following Pet")
	public void the_owner_has_the_following_pet(DataTable table) {
		List<Map<String, String>> petListTable = table.asMaps();

		for (Map<String, String> petTable : petListTable) {
			PetType petType = PetCreateUtils.createPetTypeWithId(petTable.get("type"), 2);
			LocalDate birthDate = LocalDate.parse(petTable.get("birthDate"));
			Pet pet = PetCreateUtils.createPetWithOwner(owner, petTable.get("name"), petType, birthDate,
					Integer.parseInt(petTable.get("pet_id")));
			petRepository.save(pet);
		}
	}

	@Given("the Owner has the following Visit")
	public void theOwnerHasTheFollowingVisit(DataTable table) {
		List<Map<String, String>> visitListTable = table.asMaps();

		for (Map<String, String> visitTable : visitListTable) {
			LocalDate visitDate = LocalDate.parse(visitTable.get("visitDate"));
			Visit visit = VisitUtils.createVisit(visitDate, visitTable.get("description"),
					Integer.parseInt(visitTable.get("pet_id")));
			visitRepository.save(visit);
		}
	}

	@When("the Owner fetches the Add Visit Form for Pet with an ID of {int}")
	public void ownerFetchesTheAddVisitFormForPetWithAnIdOfInt(int petId) {
		response = testRestTemplate.getForEntity("/owners/" + owner.getId() + "/pets" + petId + "/visits/new",
				String.class);
	}

	@When("the Owner completes the Add Visit Form with the following Visit")
	public void ownerFetchesAVisit(DataTable table) {
		Map<String, String> visitTable = table.asMaps().get(0);

		MultiValueMap<String, String> visitParams = new LinkedMultiValueMap<>();
		visitParams.add("date", visitTable.get("visitDate"));
		visitParams.add("description", visitTable.get("description"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(visitParams, headers);

		response = testRestTemplate.postForEntity(
				"/owners/" + owner.getId() + "/pets/" + visitTable.get("pet_id") + "/visits/new", requestEntity,
				String.class);
	}

	@Then("the Owner redirects to a new page")
	public void theOwnerWillBeRedirectedToANewPage() {
		assertEquals(response.getStatusCode(), HttpStatus.FOUND);
	}

	@And("the visit with date {string} and description {string} and pet_id {int} is returned")
	public void theVisitWithDateAndDescriptionAndPet_idIsReturned(String visitDate, String description, int petId) {
		Visit visit = visitRepository.findByPetId(petId).get(0);
		LocalDate date = null;
		if (!visitDate.equals(" ")) {
			date = LocalDate.parse(visitDate);
		}

		assertEquals(date, visit.getDate());
		assertEquals(description, visit.getDescription());
	}

	@Then("the Owner returns to the same page")
	public void thePageIsNotRedirected() {
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Then("the Owner redirects to an error page")
	public void thePageWillBeRedirectToAnErrorPage() {
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}

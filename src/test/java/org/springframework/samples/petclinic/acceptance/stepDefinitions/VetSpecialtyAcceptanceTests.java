package org.springframework.samples.petclinic.acceptance.stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.persistence.VetRepository;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VetSpecialtyAcceptanceTests {

	@Autowired
	private VetRepository vetRepository;

	private Vet vet;

	@Before
	public void setup() {
		vetRepository.deleteAll();
	}

	/**
	 * Create the vet before proceeding
	 * @author Lucca Di Lullo
	 */
	@Given("the following Vet exists")
	public void theFollowingVetExists(DataTable table) {

		List<Map<String, String>> maps = table.asMaps();

		vet = new Vet();
		vet.setFirstName(maps.get(0).get("firstName"));
		vet.setLastName(maps.get(0).get("lastName"));

		// vetRepository.save(vet);

	}

	/**
	 * Adding a specialty to vet
	 * @author Lucca Di Lullo
	 */
	@When("Vet adds a Specialty")
	public void vetAddsASpecialty(DataTable table) {

		List<Map<String, String>> maps = table.asMaps();

		Specialty s = new Specialty();
		s.setName(maps.get(0).get("name"));
		s.setId(Integer.parseInt(maps.get(0).get("id")));

		if (!s.getName().equals(""))
			vet.addSpecialty(s);

		vetRepository.save(vet);

	}

	/**
	 * Show vet has this specialty
	 * @author Lucca Di Lullo
	 */
	@Then("Vet shall have {int} Specialties")
	public void vetShallHaveSpecialties(int i) {

		assertEquals(i, vet.getSpecialties().size());

	}

}

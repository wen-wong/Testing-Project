package org.springframework.samples.petclinic.integration.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.samples.petclinic.resources.integration.entity.VetAndVetsRelationshipInputData.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.resources.util.VetCreateUtils;

class VetAndVetsRelationshipTests {

	/**
	 * This tests checks that a vets object contains vet(s) added to it
	 * @author Aman Chana & Ali T-Motamedi
	 */
	@Test
	@DisplayName("Verify dependency between Vet and Vets")
	public void testDependencyBetweenVetAndVets() {

		Vet firstVet = VetCreateUtils.createVet(FIRST_NAME1, LAST_NAME1, ID_1);
		Vet secondVet = VetCreateUtils.createVet(FIRST_NAME2, LAST_NAME2, ID_2);
		Vet thirdVet = VetCreateUtils.createVet(FIRST_NAME3, LAST_NAME3, ID_3);

		Vets vets = new Vets(Lists.newArrayList(firstVet, secondVet, thirdVet));

		List<Vet> vetList = vets.getVetList();

		assertVet(firstVet, vetList.get(0));
		assertVet(secondVet, vetList.get(1));
		assertVet(thirdVet, vetList.get(2));
	}

	private void assertVet(Vet expectedVet, Vet resultVet) {
		assertEquals(expectedVet.getFirstName(), resultVet.getFirstName());
		assertEquals(expectedVet.getLastName(), resultVet.getLastName());
		assertEquals(expectedVet.getId(), resultVet.getId());
	}

}

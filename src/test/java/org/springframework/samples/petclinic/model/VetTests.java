/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.resources.util.VetCreateUtils;
import org.springframework.util.SerializationUtils;

import static org.springframework.samples.petclinic.resources.unit.model.VetUnitInputData.*;

/**
 * @author Dave Syer
 * @author Aman Chana & Ali T-Motamedi
 */
public class VetTests {

	private Vet connor;

	private Specialty cardiology, immunology, parasitology;

	Set<Specialty> setSpecialties = new HashSet<>();

	/**
	 * Implemented setUp() to avoid redundancy Did not test whether vet creation works
	 * since testSerialization() already does this
	 * @author Aman Chana
	 */
	@BeforeEach
	public void vetTestSetup() {
		connor = VetCreateUtils.createVet(FIRST, LAST, VET_ID);
		cardiology = VetCreateUtils.createSpecialty(S1, SPECIALTY_ID_ONE);
		immunology = VetCreateUtils.createSpecialty(S2, SPECIALTY_ID_TWO);
		parasitology = VetCreateUtils.createSpecialty(S3, SPECIALTY_ID_THREE);
		setSpecialties.addAll(Lists.newArrayList(cardiology, immunology, parasitology));
	}

	@Test
	public void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName(FIRST2);
		vet.setLastName(LAST2);
		vet.setId(123);
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	/**
	 * This tests that getSpecialtiesInternal() actually returns the specialties set by
	 * setSpecialtiesInternal() We verify this by checking if getSpecialties ⊆
	 * setSpecialties and setSpecialties ⊆ getSpecialties If both are true then we can
	 * conclude that getSpecialties = setSpecialties
	 * @author Aman Chana
	 */
	@Test
	@DisplayName("Verify specialities between Vet's getter and setter")
	public void testSetAndGetSpecialtiesInternal() {

		connor.setSpecialtiesInternal(setSpecialties);

		Set<Specialty> getSpecialties = connor.getSpecialtiesInternal();

		assertTrue(getSpecialties.containsAll(setSpecialties));
		assertTrue(setSpecialties.containsAll(getSpecialties));
	}

	/**
	 * This tests whether getNrOfSpecialties() returns the correct number of specialties
	 * associated with a vet object
	 * @author Aman Chana
	 */
	@Test
	@DisplayName("Get a vet's specialties")
	public void testNumberOfSpecialties() {

		connor.setSpecialtiesInternal(setSpecialties);

		assertEquals(3, connor.getNrOfSpecialties());
	}

	/**
	 * This tests whether addSpecialty() actually adds a specialty to a vet object
	 * @author Ali T-Motamedi
	 */
	@Test
	@DisplayName("Add multiple specialties to a vet")
	public void testAddSpecialties() {

		connor.addSpecialty(cardiology);
		connor.addSpecialty(immunology);
		connor.addSpecialty(parasitology);

		Set<Specialty> actualSpecialties = connor.getSpecialtiesInternal();

		assertTrue(actualSpecialties.contains(cardiology));
		assertTrue(actualSpecialties.contains(immunology));
		assertTrue(actualSpecialties.contains(parasitology));
	}

	/**
	 * This tests whether getSpecialties() returns a sorted list of specialties Note the
	 * elements are added in sorted order in the expected list (sortedSpecs) but are not
	 * added in sorted order when we add the specialties to the vet object
	 * @author Ali T-Motamedi
	 */
	@Test
	@DisplayName("Get a vet's sorted specialties")
	public void testSortedSpecs() {

		List<Specialty> sortedSpecs = new ArrayList<>();

		sortedSpecs.add(cardiology);
		sortedSpecs.add(immunology);
		sortedSpecs.add(parasitology);

		connor.addSpecialty(immunology);
		connor.addSpecialty(parasitology);
		connor.addSpecialty(cardiology);

		List<Specialty> actualSort = connor.getSpecialties();

		assertEquals(actualSort.get(0), sortedSpecs.get(0));
		assertEquals(actualSort.get(1), sortedSpecs.get(1));
		assertEquals(actualSort.get(2), sortedSpecs.get(2));
	}

}

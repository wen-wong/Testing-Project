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

package org.springframework.samples.petclinic.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.samples.petclinic.resources.api.VetControllerInputData.*;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.persistence.VetRepository;
import org.springframework.samples.petclinic.resources.util.VetCreateUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(VetController.class)
class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VetRepository vets;

	@BeforeEach
	void vetControllerSetup() {
		Vet john = VetCreateUtils.createVet(FIRSTNAME1, LASTNAME2, 1);
		Vet jane = VetCreateUtils.createVet(FIRSTNAME2, LASTNAME2, 2);
		given(this.vets.findAll()).willReturn(Lists.newArrayList(john, jane));
	}

	/**
	 * This tests whether a list of vets is displayed when the vets/vetList page is
	 * displayed
	 * @author Brandon Wong
	 * @author Ali T.-Motamedi
	 */

	@Test
	@DisplayName("Show Vet List HTML")
	void testShowVetListHtml() throws Exception {
		mockMvc.perform(get("/vets.html")).andExpect(status().isOk()).andExpect(model().attributeExists("vets"))
				.andExpect(view().name("vets/vetList"));
	}

	/**
	 * Show vet list in html and verify each vet instance in the list
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Show Vet List HTML and verify each vet on the list")
	void testShowVetListHtmlRevised() throws Exception {
		mockMvc.perform(get("/vets.html")).andExpect(status().isOk()).andExpect(model().attributeExists("vets"))
				.andExpect(view().name("vets/vetList"))
				.andExpect(model().attribute("vets",
						Matchers.allOf(Matchers.any(Vets.class),
								Matchers.hasProperty("vetList",
										Matchers.hasItems(Matchers.hasProperty("id", Matchers.equalTo(1)),
												Matchers.hasProperty("id", Matchers.equalTo(2)))))));
	}

	/**
	 * This tests whether a Vets object is returned rather than a Collection of Vet
	 * objects
	 * @author Brandon Wong
	 * @author Ali T.-Motamedi
	 */
	@Test
	@DisplayName("Show Vet List Resources as a JSON")
	void testShowResourcesVetList() throws Exception {
		mockMvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.vetList[0].id").value(1));
	}

}

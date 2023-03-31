package org.springframework.samples.petclinic.performance;

import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@LoadWith("scenario.properties")
@ExtendWith({ ParallelLoadExtension.class })
public class LoadPerformanceTest {

	@Test
	@DisplayName("Perform Run-Time Performance tests")
	@TestMappings({ @TestMapping(testClass = PerformanceTests.class, testMethod = "testCreateOwner"),
			@TestMapping(testClass = PerformanceTests.class, testMethod = "testEditOwner"),
			@TestMapping(testClass = PerformanceTests.class, testMethod = "testCreatePet"),
			@TestMapping(testClass = PerformanceTests.class, testMethod = "testEditPet") })
	public void testLoad() {
	}

}

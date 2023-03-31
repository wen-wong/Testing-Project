package org.springframework.samples.petclinic.integration.persistence;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Pet Clinic - Persistence Integration Test Suite")
@SelectClasses({ OwnerPersistenceTests.class, PetPersistenceTests.class, VisitPersistenceTests.class,
		VetPersistenceTests.class })
@Suite
public class PersistenceIntegrationTestSuite {

}

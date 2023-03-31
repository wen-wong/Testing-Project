package org.springframework.samples.petclinic.integration.entity;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Pet Clinic - Entity Integration Test Suite")
@SelectClasses({ VetAndVetsRelationshipTests.class, OwnerAndPetRelationshipTests.class,
		PetAndVisitRelationshipTests.class })
@Suite
public class EntityIntegrationTestSuite {

}

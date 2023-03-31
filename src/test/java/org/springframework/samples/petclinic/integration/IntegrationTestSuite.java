package org.springframework.samples.petclinic.integration;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.springframework.samples.petclinic.integration.entity.EntityIntegrationTestSuite;
import org.springframework.samples.petclinic.integration.persistence.PersistenceIntegrationTestSuite;

@SuiteDisplayName("Pet Clinic - Integration Test Suite")
@SelectClasses({ EntityIntegrationTestSuite.class, PersistenceIntegrationTestSuite.class })
@Suite
public class IntegrationTestSuite {

}

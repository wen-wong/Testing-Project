package org.springframework.samples.petclinic.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Pet Clinic - Controller Test Suite")
@SelectClasses({ OwnerControllerTests.class, PetControllerTests.class, VetControllerTests.class,
		VisitControllerTests.class, WelcomeControllerTests.class })
@Suite
public class ControllerTestSuite {

}

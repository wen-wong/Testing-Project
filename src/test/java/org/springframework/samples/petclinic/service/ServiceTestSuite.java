package org.springframework.samples.petclinic.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Pet Clinic - Service Test Suite")
@SelectClasses({ PetValidatorTests.class, PetTypeFormatterTests.class })
@Suite
public class ServiceTestSuite {

}

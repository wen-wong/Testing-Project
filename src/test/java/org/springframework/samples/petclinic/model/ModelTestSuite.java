package org.springframework.samples.petclinic.model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Pet Clinic - Model Test Suite")
@SelectClasses({ OwnerTests.class, PetTests.class, ValidatorTests.class, VetTests.class })
@Suite
public class ModelTestSuite {

}

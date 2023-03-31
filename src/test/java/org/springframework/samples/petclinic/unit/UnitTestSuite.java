package org.springframework.samples.petclinic.unit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.springframework.samples.petclinic.model.ModelTestSuite;
import org.springframework.samples.petclinic.service.ServiceTestSuite;

@SuiteDisplayName("Pet Clinic - Unit Test Suite")
@SelectClasses({ ModelTestSuite.class, ServiceTestSuite.class })
@Suite
public class UnitTestSuite {

}

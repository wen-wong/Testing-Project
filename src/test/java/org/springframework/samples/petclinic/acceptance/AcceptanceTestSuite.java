package org.springframework.samples.petclinic.acceptance;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@CucumberContextConfiguration()
@SpringBootTest(classes = PetClinicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTestSuite {

}

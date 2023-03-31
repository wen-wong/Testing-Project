package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.samples.petclinic.resources.unit.service.PetValidatorInputData.BIRTH_DATE;
import static org.springframework.samples.petclinic.resources.unit.service.PetValidatorInputData.PET_NAME;

@ExtendWith(MockitoExtension.class)
public class PetValidatorTests {

	private static PetValidator petValidator;

	private BindException errors;

	@Mock
	private Pet mockPet;

	@Mock
	private PetType mockPetType;

	@Mock
	private Person mockPerson;

	/**
	 * Instantiate PetValidator to run the test stubs.
	 * @author Brandon Wong
	 */
	@BeforeAll
	public static void petValidatorTestSetup() {
		petValidator = new PetValidator();
	}

	/**
	 * Reset the Pet and the Error classes before each test.
	 * @author Brandon Wong
	 */
	@BeforeEach
	public void petValidatorEachTestSetup() {
		errors = new BindException(mockPet, PET_NAME);
	}

	/**
	 * Validate the fields of a pet assuming all fields are valid.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a valid pet")
	public void testPetValidate() {
		when(mockPet.getName()).thenReturn(PET_NAME);
		when(mockPet.isNew()).thenReturn(false);
		when(mockPet.getBirthDate()).thenReturn(BIRTH_DATE);

		petValidator.validate(mockPet, errors);

		assertEquals(0, errors.getErrorCount());
	}

	/**
	 * Validate the fields of a pet assuming they have an invalid name. The validate
	 * method should catch the error on the name field.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a pet with an invalid name")
	public void testInvalidPetName() {
		when(mockPet.getName()).thenReturn(null);
		when(mockPet.isNew()).thenReturn(false);
		when(mockPet.getBirthDate()).thenReturn(BIRTH_DATE);

		petValidator.validate(mockPet, errors);

		FieldError field = errors.getFieldError("name");

		assertEquals(1, errors.getErrorCount());
		assertNotNull(field);
		assertEquals("name", field.getField());
	}

	/**
	 * Validate the fields of a pet assuming they have an invalid id. The fields of the
	 * pet should be valid.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a pet with an invalid id")
	public void testValidateWithInvalidId() {
		when(mockPet.getName()).thenReturn(PET_NAME);
		when(mockPet.isNew()).thenReturn(true);
		when(mockPet.getType()).thenReturn(mockPetType);
		when(mockPet.getBirthDate()).thenReturn(BIRTH_DATE);

		petValidator.validate(mockPet, errors);

		assertEquals(0, errors.getErrorCount());
	}

	/**
	 * Validate the fields of a pet assuming they have an invalid pet type. The fields of
	 * the pet should be valid.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a pet with an invalid pet type")
	public void testValidateWithInvalidPetType() {
		when(mockPet.getName()).thenReturn(PET_NAME);
		when(mockPet.isNew()).thenReturn(false);
		when(mockPet.getBirthDate()).thenReturn(BIRTH_DATE);

		petValidator.validate(mockPet, errors);

		assertEquals(0, errors.getErrorCount());
	}

	/**
	 * Validate the fields of a pet assuming they have an invalid id and pet type. The
	 * validate method should catch the error on the type field.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a pet with an invalid id and pet type")
	public void testValidateWithInvalidIdAndPetType() {
		when(mockPet.getName()).thenReturn(PET_NAME);
		when(mockPet.isNew()).thenReturn(true);
		when(mockPet.getType()).thenReturn(null);
		when(mockPet.getBirthDate()).thenReturn(BIRTH_DATE);

		petValidator.validate(mockPet, errors);

		FieldError field = errors.getFieldError("type");

		assertEquals(1, errors.getErrorCount());
		assertNotNull(field);
		assertEquals("type", field.getField());
	}

	/**
	 * Validate the fields of a pet assuming they have an invalid birthDate. The validate
	 * method should catch the error on the birthDate field.
	 * @author Brandon Wong
	 */
	@Test
	@DisplayName("Validate a pet with an invalid birthDate")
	public void testValidateWithInvalidBirthDate() {
		when(mockPet.getName()).thenReturn(PET_NAME);
		when(mockPet.isNew()).thenReturn(false);

		petValidator.validate(mockPet, errors);

		FieldError field = errors.getFieldError("birthDate");

		assertEquals(1, errors.getErrorCount());
		assertNotNull(field);
		assertEquals("birthDate", field.getField());
	}

	/**
	 *
	 * This test validates if a Pet instance is from the same class as the class being
	 * validated.
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Validate pet instance from same class.")
	public void testSupportsSameClass() {

		boolean isSupported = petValidator.supports(mockPet.getClass());
		assertTrue(isSupported);
	}

	/**
	 *
	 * This test validates if a Pet instance is from the super class as the class being
	 * validated.
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Validate pet instance from super class.")
	public void testSupportsSuperClass() {

		boolean isSupported = petValidator.supports(((NamedEntity) mockPet).getClass());
		assertTrue(isSupported);
	}

	/**
	 *
	 * This test validates if a Pet instance is not from the super class or the same class
	 * as the class being validated.
	 * @author Zoya Malhi
	 */
	@Test
	@DisplayName("Validate pet instance which is not from the same class or a superclass.")
	public void testSupportsInvalidClass() {

		boolean isSupported = petValidator.supports(mockPerson.getClass());
		assertFalse(isSupported);
	}

}

package org.springframework.samples.petclinic.resources.util;

import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;

public class VetCreateUtils {

	public static Vet createVet(String firstName, String lastName, int id) {
		Vet vet = new Vet();
		vet.setFirstName(firstName);
		vet.setLastName(lastName);
		vet.setId(id);
		return vet;
	}

	public static Specialty createSpecialty(String name, int id) {

		Specialty specialty = new Specialty();
		specialty.setName(name);
		specialty.setId(1);
		return specialty;

	}

}

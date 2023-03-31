package org.springframework.samples.petclinic.resources.util;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

import java.time.LocalDate;

public class PetCreateUtils {

	public static PetType createPetType(String name) {
		PetType type = new PetType();
		type.setName(name);

		return type;
	}

	public static PetType createPetTypeWithId(String name, int id) {
		PetType type = new PetType();
		type.setName(name);
		type.setId(id);

		return type;
	}

	public static Pet createSimplePet(String name, PetType petType) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setType(petType);

		return pet;
	}

	public static Pet createPet(String name, PetType petType, int id) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setType(petType);
		pet.setId(id);

		return pet;
	}

	public static Pet createPetWithOwner(Owner owner, String name, PetType petType, LocalDate date, int id) {
		Pet pet = new Pet();
		pet.setOwner(owner);
		pet.setName(name);
		pet.setType(petType);
		pet.setBirthDate(date);
		pet.setId(id);

		return pet;
	}

}

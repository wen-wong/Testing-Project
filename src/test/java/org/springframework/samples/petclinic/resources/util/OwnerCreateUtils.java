package org.springframework.samples.petclinic.resources.util;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;

public class OwnerCreateUtils {

	public static Owner createSimpleOwner(String firstName, String lastName) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);

		return owner;
	}

	public static Owner createOwner(String firstName, String lastName, String address, String city, String telephone,
			int id) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setTelephone(telephone);
		owner.setId(id);

		return owner;
	}

	public static Owner createOwnerWithPet(String firstName, String lastName, String address, String city,
			String telephone, int id, Pet onlyPet) {

		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setTelephone(telephone);
		owner.setId(id);
		owner.addPet(onlyPet);

		return owner;
	}

}

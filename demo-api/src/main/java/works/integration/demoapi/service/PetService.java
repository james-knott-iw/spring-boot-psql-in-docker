package works.integration.demoapi.service;

import java.util.List;

import works.integration.demoapi.entity.Person;
import works.integration.demoapi.entity.Pet;

public interface PetService {

    public Pet getPet(Long id);

    public List<Pet> getPets();

    public Pet savePet(Pet pet, Long id);

    public Pet updatePet(Long petId, Long personId, Pet pet);

    public void deletePet(Long id);

    public Person getPerson(Long id);
}

package works.integration.demoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import works.integration.demoapi.entity.Person;
import works.integration.demoapi.entity.Pet;
import works.integration.demoapi.exception.EntityNotFoundException;
import works.integration.demoapi.repository.PersonRepository;
import works.integration.demoapi.repository.PetRepository;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {

    PetRepository petRepository;
    PersonRepository personRepository;

    @Override
    public Pet getPet(Long id) {
        return unwrapPet(petRepository.findById(id), id);
    }

    @Override
    public List<Pet> getPets() {
        return (List<Pet>) petRepository.findAll();
    }

    @Override
    public Pet savePet(Pet pet, Long id) {
        Person person = PersonServiceImpl.unwrapPerson(personRepository.findById(id), id);
        pet.setPerson(person);
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long petId, Long personId, Pet pet) {
        Pet originalPet = unwrapPet(petRepository.findById(petId), petId);
        originalPet.setName(pet.getName());
        originalPet.setPerson(PersonServiceImpl.unwrapPerson(personRepository.findById(personId), personId));

        return petRepository.save(originalPet);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public Person getPerson(Long id) {
        Pet pet = unwrapPet(petRepository.findById(id), id);
        return pet.getPerson();
    }

    static Pet unwrapPet(Optional<Pet> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Pet.class);
    }

}

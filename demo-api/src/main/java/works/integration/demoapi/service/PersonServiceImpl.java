package works.integration.demoapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import works.integration.demoapi.entity.Person;
import works.integration.demoapi.entity.Pet;
import works.integration.demoapi.exception.EntityNotFoundException;
import works.integration.demoapi.repository.PersonRepository;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Person getPerson(Long id) {
        return unwrapPerson(personRepository.findById(id), id);
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> getPersons() {

        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Person originalPerson = unwrapPerson(personRepository.findById(id), id);

        originalPerson.setAddress(person.getAddress());
        originalPerson.setName(person.getName());
        return personRepository.save(originalPerson);

    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Set<Pet> getPets(Long id) {
        Person person = unwrapPerson(personRepository.findById(id), id);
        return person.getPets();
    }

    static Person unwrapPerson(Optional<Person> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Person.class);
    }

}

package works.integration.demoapi.repository;

import org.springframework.data.repository.CrudRepository;

import works.integration.demoapi.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}

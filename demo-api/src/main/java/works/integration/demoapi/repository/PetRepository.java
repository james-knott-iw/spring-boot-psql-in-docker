package works.integration.demoapi.repository;

import org.springframework.data.repository.CrudRepository;

import works.integration.demoapi.entity.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

}

package works.integration.demoapi.web;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import works.integration.demoapi.entity.Person;
import works.integration.demoapi.service.PersonService;
import works.integration.demoapi.entity.Pet;
import works.integration.demoapi.exception.ErrorResponse;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Person Controller", description = "Create, retrieve, update and delete person entities")
@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    PersonService personService;

    @Operation(summary = "Retrieve a Person by Id", description = "Returns a Person based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Person doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of Person", content = @Content(schema = @Schema(implementation = Person.class))),
    })

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.getPerson(id), HttpStatus.OK);
    }

    @Operation(summary = "Create Person", description = "Creates a person from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of a person", content = @Content(schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody @Valid Person person) {
        return new ResponseEntity<>(personService.savePerson(person), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieves persons", description = "Provides a list of all persons")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Person.class))))
    @GetMapping("/all")
    public ResponseEntity<List<Person>> getPersons() {
        return new ResponseEntity<>(personService.getPersons(), HttpStatus.OK);
    }

    @Operation(summary = "Update a Person by Id", description = "Updates a Person based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Person doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful Update of a Person", content = @Content(schema = @Schema(implementation = Person.class))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return new ResponseEntity<>(personService.updatePerson(id, person), HttpStatus.OK);
    }

    @Operation(summary = "Delete a Person by Id", description = "Deletes a Person based on Id")
    @ApiResponse(responseCode = "204", description = "Delete of a Person")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Retrieves a person's pets", description = "Provides a list of all a person's pets")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of person's pets", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pet.class))))
    @GetMapping("/{id}/pets")
    public ResponseEntity<Set<Pet>> getMethodName(@PathVariable Long id) {
        return new ResponseEntity<>(personService.getPets(id), HttpStatus.OK);
    }

}

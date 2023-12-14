package works.integration.demoapi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import works.integration.demoapi.entity.Person;
import works.integration.demoapi.entity.Pet;
import works.integration.demoapi.exception.ErrorResponse;
import works.integration.demoapi.service.PetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "PetController", description = "Create, retrieve, update and delete pet entities")
@RestController
@AllArgsConstructor
@RequestMapping("/pet")
public class PetController {

    PetService petService;

    @Operation(summary = "Create Pet", description = "Creates a pet from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of a pet", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/person/{id}")
    public ResponseEntity<Pet> postPet(@PathVariable Long id, @RequestBody Pet pet) {

        return new ResponseEntity<>(petService.savePet(pet, id), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a Pet by Id", description = "Returns a Pet based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pet doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of Pet", content = @Content(schema = @Schema(implementation = Pet.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {

        return new ResponseEntity<>(petService.getPet(id), HttpStatus.OK);
    }

    @Operation(summary = "Update a Pet by Id", description = "Updates a Pet based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Pet doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful Update of a Pet", content = @Content(schema = @Schema(implementation = Pet.class))),
    })
    @PutMapping("{petId}/person/{personId}")
    public ResponseEntity<Pet> putPet(@PathVariable Long petId, @PathVariable Long personId, @RequestBody Pet pet) {

        return new ResponseEntity<>(petService.updatePet(petId, personId, pet), HttpStatus.OK);
    }

    @Operation(summary = "Delete a Pet by Id", description = "Deletes a Pet based on Id")
    @ApiResponse(responseCode = "204", description = "Delete of a Pet")
    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Retrieves a pet's person", description = "Provides a pet's person")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of a pet's person", content = @Content(schema = @Schema(implementation = Person.class)))
    @GetMapping("/{id}/person")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return new ResponseEntity<>(petService.getPerson(id), HttpStatus.OK);
    }

}

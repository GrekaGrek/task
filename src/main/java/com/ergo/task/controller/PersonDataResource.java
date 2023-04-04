package com.ergo.task.controller;

import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.specification.PersonSearchCriteria;
import com.ergo.task.service.PersonDataServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonDataResource {

    private final PersonDataServiceImpl service;

    public PersonDataResource(PersonDataServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PersonDataDTO> addPerson(@Valid @RequestBody PersonDataDTO personData) {
        service.addPersonData(personData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/findPersonBy")
    public ResponseEntity<PersonDataDTO> findPersonBy(PersonSearchCriteria filter) {
        return service.findPersonBy(filter)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PersonDataDTO>> findAllPersons() {
        return ResponseEntity.ok(service.fetchAllPersons());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDataDTO> updatePersonData(@Valid @RequestBody PersonDataDTO personData,
                                                          @PathVariable Long id) {
        return ResponseEntity.ok(service.updatePersonData(personData, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable Long id) {
        service.deletePersonData(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

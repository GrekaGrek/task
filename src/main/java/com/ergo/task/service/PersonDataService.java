package com.ergo.task.service;

import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.specification.PersonSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface PersonDataService {

    List<PersonDataDTO> fetchAllPersons();
    void addPersonData(PersonDataDTO personData);
    Optional<PersonDataDTO> findPersonBy(PersonSearchCriteria filter);
    PersonDataDTO updatePersonData(PersonDataDTO personData, Long id);
    void deletePersonData(Long id);
}

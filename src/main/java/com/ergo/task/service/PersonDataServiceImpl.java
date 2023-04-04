package com.ergo.task.service;

import com.ergo.task.mapper.PersonDataMapper;
import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.PersonDataRepository;
import com.ergo.task.repository.specification.PersonDataSpecification;
import com.ergo.task.repository.specification.PersonSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

@Service
public class PersonDataServiceImpl implements PersonDataService {
    private static final Logger logger = LoggerFactory.getLogger(PersonDataServiceImpl.class);

    private final PersonDataMapper mapper;
    private final PersonDataRepository repository;
    private final PersonDataSpecification specification;

    public PersonDataServiceImpl(PersonDataMapper mapper, PersonDataRepository repository,
                                 PersonDataSpecification specification) {
        this.mapper = mapper;
        this.repository = repository;
        this.specification = specification;
    }

    @Override
    @Transactional
    public void addPersonData(PersonDataDTO item) {
        var id = item.personalId();
        logger.debug("Creating new Person with personal ID: {}", id);

        if (repository.findByPersonalId(id).isPresent()) {
            throw new EntityExistsException("Person with provided personal ID already exist");
        }
        repository.save(mapper.mapFromDTO(item));
    }

    @Override
    public Optional<PersonDataDTO> findPersonBy(PersonSearchCriteria filter) {
        var entity = repository
                .findOne(specification.findPersonByNameOrBOD(filter))
                .orElseThrow(personNotFound());

        return ofNullable(mapper.mapToDTO(entity));
    }

    @Override
    public List<PersonDataDTO> fetchAllPersons() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    @Transactional
    public PersonDataDTO updatePersonData(PersonDataDTO personData, Long id) {
        var entityToUpdate = mapper.mapUpdateFromDTO(id, personData);

        repository.save(entityToUpdate);
        logger.debug("Updated Person data with id: {}.", entityToUpdate.getId());

        return mapper.mapToDTO(entityToUpdate);
    }

    @Override
    @Transactional
    public void deletePersonData(Long id) {
        repository.deleteById(id);
    }

    private Supplier<EntityNotFoundException> personNotFound() {
        return () -> new EntityNotFoundException("Person information is not found");
    }
}

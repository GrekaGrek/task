package com.ergo.task.mapper;

import com.ergo.task.domain.PersonDataEntity;
import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.PersonDataRepository;
import org.springframework.stereotype.Component;

@Component
public class PersonDataMapper {

    private final PersonDataRepository repository;

    public PersonDataMapper(PersonDataRepository repository) {
        this.repository = repository;
    }

    public PersonDataDTO mapToDTO(PersonDataEntity entity) {
        if (entity == null) {
            return null;
        }
        return PersonDataDTO.builder()
                .personalId(entity.getPersonalId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .build();
    }

    public PersonDataEntity mapFromDTO(PersonDataDTO item) {
        if (item == null) {
            return null;
        }
        return new PersonDataEntity()
                .setPersonalId(item.personalId())
                .setFirstName(item.firstName())
                .setLastName(item.lastName())
                .setDateOfBirth(item.dateOfBirth())
                .setGender(item.gender())
                .setPhoneNumber(item.phoneNumber())
                .setEmail(item.email());
    }

    public PersonDataEntity mapUpdateFromDTO(Long id, PersonDataDTO item) {
        var entityToUpdate = repository.getReferenceById(id);

        entityToUpdate.setId(id);
        entityToUpdate.setPersonalId(item.personalId());
        entityToUpdate.setFirstName(item.firstName());
        entityToUpdate.setLastName(item.lastName());
        entityToUpdate.setDateOfBirth(item.dateOfBirth());
        entityToUpdate.setGender(item.gender());
        entityToUpdate.setPhoneNumber(item.phoneNumber());
        entityToUpdate.setEmail(item.email());

        return entityToUpdate;
    }
}

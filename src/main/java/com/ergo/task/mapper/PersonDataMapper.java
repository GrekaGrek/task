package com.ergo.task.mapper;

import com.ergo.task.domain.PersonDataEntity;
import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.PersonDataRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PersonDataMapper {

    private final PersonDataRepository repository;

    public PersonDataMapper(PersonDataRepository repository) {
        this.repository = repository;
    }

    private final Function<PersonDataEntity, PersonDataDTO> convertEntity =
            entity -> new PersonDataDTO.Builder()
                    .with(b -> {
                        b.personalId = entity.getPersonalId();
                        b.firstName = entity.getFirstName();
                        b.lastName = entity.getLastName();
                        b.dateOfBirth = entity.getDateOfBirth();
                        b.gender = entity.getGender();
                        b.phoneNumber = entity.getPhoneNumber();
                        b.email = entity.getEmail();
                    })
                    .build();

    public PersonDataDTO mapToDTO(PersonDataEntity entity) {
        if (entity == null) {
            return null;
        }
        return convertEntity.apply(entity);
    }

    private final Function<PersonDataDTO, PersonDataEntity> convertDTO =
            item -> new PersonDataEntity()
                    .setPersonalId(item.personalId())
                    .setFirstName(item.firstName())
                    .setLastName(item.lastName())
                    .setDateOfBirth(item.dateOfBirth())
                    .setGender(item.gender())
                    .setPhoneNumber(item.phoneNumber())
                    .setEmail(item.email());

    public PersonDataEntity mapFromDTO(PersonDataDTO item) {
        if (item == null) {
            return null;
        }
        return convertDTO.apply(item);
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

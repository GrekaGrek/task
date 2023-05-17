package com.ergo.task.service;

import com.ergo.task.domain.PersonDataEntity;
import com.ergo.task.mapper.PersonDataMapper;
import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.PersonDataRepository;
import com.ergo.task.repository.specification.PersonDataSpecification;
import com.ergo.task.repository.specification.PersonSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ergo.task.enums.PersonGender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonDataServiceImplTest {
    public static final String PERSONAL_ID = "11122444555666";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Testor";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2001, 1, 15);
    public static final String PHONE_NUMBER = "+2344454566575";
    public static final String EMAIL = "personal@yahahoo.com";

    @Mock
    private PersonDataMapper mockMapper;
    @Mock
    private PersonDataRepository mockRepository;
    @Mock
    private PersonDataSpecification mockSpecification;


    @InjectMocks
    private PersonDataServiceImpl service;

    @Test
    void createPersonDataSuccessfully() {
        var entity = createEntity();
        var item = createDTO();

        when(mockMapper.mapFromDTO(item)).thenReturn(entity);
        when(mockRepository.save(entity)).thenReturn(entity);

        service.addPersonData(item);

        verify(mockMapper).mapFromDTO(item);
        verify(mockRepository).save(entity);
    }

    @Test
    void createPersonDataWithExceptionCauseRecordExistInDB() {
        var entity = createEntity();
        var item = createDTO();

        when(mockRepository.findByPersonalId(entity.getPersonalId())).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.addPersonData(item)).isInstanceOf(EntityExistsException.class);

        verify(mockMapper, never()).mapFromDTO(item);
        verify(mockRepository, never()).save(entity);
    }

    @Test
    void findPersonByFirstName() {
        var entity = createEntity();
        var item = createDTO();
        var filter = new PersonSearchCriteria(FIRST_NAME, null);

        when(mockRepository.findOne(mockSpecification.findPersonByNameOrBOD(filter))).thenReturn(Optional.ofNullable(entity));
        when(mockMapper.mapToDTO(entity)).thenReturn(item);

        var filterResult = service.findPersonBy(filter);

        assertThat(filterResult)
                .isNotNull()
                .hasValue(item);

        verify(mockRepository).findOne((Specification<PersonDataEntity>) any());
        verify(mockSpecification, times(2)).findPersonByNameOrBOD(filter);
        verify(mockMapper).mapToDTO(entity);
    }

    @Test
    void personNotFoundByFirstName() {
        var entity = createEntity();
        var name = "Viktors";
        var filter = new PersonSearchCriteria(name, null);

        assertThatThrownBy(() -> service.findPersonBy(filter)).isInstanceOf(EntityNotFoundException.class);

        verify(mockRepository).findOne((Specification<PersonDataEntity>) any());
        verify(mockSpecification, times(1)).findPersonByNameOrBOD(filter);
        verify(mockMapper, never()).mapToDTO(entity);
    }

    @Test
    void fetchAllPersons() {
        List<PersonDataEntity> persons = new ArrayList<>();

        when(mockRepository.findAll()).thenReturn(persons);

        List<PersonDataDTO> actualResult = service.fetchAllPersons();

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(persons);

        verify(mockRepository).findAll();
    }

    @Test
    void updatePersonData() {
        var id = 1L;
        var entity = createEntity();
        var item = createDTO();

        when(mockMapper.mapUpdateFromDTO(id, item)).thenReturn(entity);
        when(mockRepository.save(entity)).thenReturn(entity);
        when(mockMapper.mapToDTO(entity)).thenReturn(item);

        var actualUpdate = service.updatePersonData(item, id);

        assertThat(actualUpdate)
                .isNotNull()
                .isEqualTo(item);

        verify(mockMapper).mapUpdateFromDTO(id, item);
        verify(mockRepository).save(entity);
        verify(mockMapper).mapToDTO(entity);
    }

    @Test
    void updatePersonDataFailedIdIsNull() {
        var entity = createEntity();
        var item = createDTO();

        assertThatThrownBy(() -> service.updatePersonData(item, null)).isInstanceOf(NullPointerException.class);

        verify(mockRepository, never()).save(entity);
        verify(mockMapper, never()).mapToDTO(entity);
    }

    @Test
    void deletePersonData() {
        var entity = createEntity();
        entity.setId(1L);

        service.deletePersonData(entity.getId());

        verify(mockRepository).deleteById(entity.getId());
    }

    private PersonDataEntity createEntity() {
        return new PersonDataEntity()
                .setPersonalId(PERSONAL_ID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setDateOfBirth(DATE_OF_BIRTH)
                .setGender(MALE)
                .setPhoneNumber(PHONE_NUMBER)
                .setEmail(EMAIL);
    }

    private PersonDataDTO createDTO() {
        return new PersonDataDTO.Builder()
                .with(b -> {
                    b.personalId = PERSONAL_ID;
                    b.firstName = FIRST_NAME;
                    b.lastName = LAST_NAME;
                    b.dateOfBirth = DATE_OF_BIRTH;
                    b.gender = MALE;
                    b.phoneNumber = PHONE_NUMBER;
                    b.email = EMAIL;
                })
                .build();
    }
}
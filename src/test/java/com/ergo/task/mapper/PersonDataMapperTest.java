package com.ergo.task.mapper;

import com.ergo.task.domain.PersonDataEntity;
import com.ergo.task.model.PersonDataDTO;
import com.ergo.task.repository.PersonDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.ergo.task.enums.PersonGender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonDataMapperTest {
    public static final String PERSONAL_ID = "11122444555666";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Testor";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2001, 1, 15);
    public static final String PHONE_NUMBER = "+2344454566575";
    public static final String EMAIL = "personal@yahahoo.com";

    @Mock
    private PersonDataRepository mockRepository;

    @InjectMocks
    private PersonDataMapper mapperMock;

    @Test
    void mapToDTO() {
        var entity = fetchEntity();
        var expectedResult = fetchDTO();

        PersonDataDTO mappingResult = mapperMock.mapToDTO(entity);

        assertThat(mappingResult)
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void mapFromDTO() {
        var dto = fetchDTO();
        var expectedResult = fetchEntity();

        PersonDataEntity actualResult = mapperMock.mapFromDTO(dto);

        assertThat(actualResult)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void mapUpdateFromDTO() {
        var dto = fetchDTO();
        var expectedResult = fetchEntity();
        var id = 1L;
        expectedResult.setId(id);
        when(mockRepository.getReferenceById(1L)).thenReturn(expectedResult);

        PersonDataEntity actualResult = mapperMock.mapUpdateFromDTO(id, dto);

        assertThat(actualResult)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);

        verify(mockRepository).getReferenceById(id);
    }

    private PersonDataEntity fetchEntity() {
        return new PersonDataEntity()
                .setPersonalId(PERSONAL_ID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setDateOfBirth(DATE_OF_BIRTH)
                .setGender(MALE)
                .setPhoneNumber(PHONE_NUMBER)
                .setEmail(EMAIL);
    }

    private PersonDataDTO fetchDTO() {
        return PersonDataDTO.builder()
                .personalId(PERSONAL_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .dateOfBirth(DATE_OF_BIRTH)
                .gender(MALE)
                .phoneNumber(PHONE_NUMBER)
                .email(EMAIL)
                .build();
    }
}